package dev.erpix.tiruka;

import dev.erpix.tiruka.cache.CacheFactory;
import dev.erpix.tiruka.cache.CacheManager;
import dev.erpix.tiruka.command.console.impl.CacheConsoleCommand;
import dev.erpix.tiruka.command.console.impl.GuildsConsoleCommand;
import dev.erpix.tiruka.command.discord.impl.*;
import dev.erpix.tiruka.event.GenericEventListener;
import dev.erpix.tiruka.event.handler.SlashCommandHandler;
import dev.erpix.tiruka.metrics.MetricsCollector;
import dev.erpix.tiruka.model.*;
import dev.erpix.tiruka.storage.Storage;
import dev.erpix.tiruka.storage.StorageFactory;
import dev.erpix.tiruka.storage.StorageType;
import io.prometheus.client.exporter.HTTPServer;
import io.prometheus.client.hotspot.DefaultExports;
import joptsimple.OptionSet;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import dev.erpix.tiruka.command.CommandRegistrar;
import dev.erpix.tiruka.command.console.impl.HelpConsoleCommand;
import dev.erpix.tiruka.command.console.impl.VersionConsoleCommand;
import dev.erpix.tiruka.config.ConfigLoader;
import dev.erpix.tiruka.config.ConfigSchema;
import dev.erpix.tiruka.console.Console;
import dev.erpix.tiruka.locale.LocalizationLoader;
import dev.erpix.tiruka.logging.GuildLogger;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TirukaApp {

    private static final Logger logger = LoggerFactory.getLogger(TirukaApp.class);

    private static TirukaApp instance;

    private CacheManager cache;
    private CommandRegistrar commandRegistrar;
    private ConfigSchema config;
    private Console console;
    private Path dataDirectory;
    private DependencyLoader dependencyLoader;
    private CountDownLatch latch = new CountDownLatch(1);
    private long startTime;
    private ShardManager shardManager;
    private Storage storage;

    private HTTPServer prometheusServer;
    private ScheduledExecutorService metricsScheduler = Executors.newScheduledThreadPool(1);

    private final CountDownLatch startUpLatch = new CountDownLatch(1);

    public TirukaApp(OptionSet options) {
        try {

            instance = this;
            startTime = System.currentTimeMillis();

            logger.info("Running Tiruka v{}", TirukaConstants.VERSION);

            try {
                DefaultExports.initialize();
                prometheusServer = new HTTPServer(9090);
                metricsScheduler.scheduleAtFixedRate(MetricsCollector::updateMetrics, 0, 5, TimeUnit.SECONDS);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                dataDirectory = Paths.get(
                        TirukaApp.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
            } catch (URISyntaxException e) {
                throw new StartupException("Failed to resolve the path of the .jar file.", e);
            }

            config = ConfigLoader.load();
            LocalizationLoader.load(config.getPrimaryLanguage());

            dependencyLoader = new DependencyLoader();

            String type = config.getStorage().getType();
            StorageType storageType = StorageType.from(type);
            if (storageType == null) {
                throw new StartupException("Unknown storage type '" + type + "'. " +
                        "Please use one of the following: " + Arrays.toString(StorageType.values()));
            }
            storage = StorageFactory.create(storageType);
            storage.init();

            cache = CacheFactory.create();

            commandRegistrar = new CommandRegistrar();
            commandRegistrar.registerAll(
                    new TesterCommand(),
                    new BtnTestCommand(),

                    new AvatarCommand(),
                    new ComponentCommand(),
                    new EditCommand(),
                    new EmojiCommand(),
                    new SendCommand(),
                    new PurgeCommand(),
                    new ReactionRoleCommand(this),
                    new RollCommand(),
                    new ShipCommand(),
                    new DynamicVCCommand(this),
                    new VersionCommand(),
                    new VoteReactionsCommand(this)
            );

            String token = config.getAppToken();
            if (options.has("token")) {
                token = (String) options.valueOf("token");
            }

            try {
                var profile = config.getProfile();
                var activity = profile.getActivity();
                shardManager = DefaultShardManagerBuilder.createDefault(token)
                        .enableCache(CacheFlag.ACTIVITY)
                        .enableCache(CacheFlag.EMOJI)
                        .enableIntents(GatewayIntent.GUILD_MEMBERS)
                        .enableIntents(GatewayIntent.GUILD_PRESENCES)
                        .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                        .setActivity(Activity.of(
                                Activity.ActivityType.fromKey(activity.getType()),
                                activity.getName(),
                                activity.getUrl()))
                        .setStatus(OnlineStatus.fromKey(profile.getStatus()))
                        .addEventListeners(new ListenerAdapter() {
                            @Override
                            public void onReady(@NotNull ReadyEvent event) {
                                startUpLatch.countDown();
                            }
                        })
                        .addEventListeners(new GenericEventListener() {{
                            registerHandler(new SlashCommandHandler(commandRegistrar));
                        }})
                        .build();
            } catch (InvalidTokenException e) {
                throw new StartupException("Provided app token is invalid.", e);
            }

            // Wait until all shards are ready
            try {
                startUpLatch.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            commandRegistrar.update(shardManager);

            console = new Console();
            commandRegistrar.registerAll(
                    new CacheConsoleCommand(),
                    new GuildsConsoleCommand(),
                    new HelpConsoleCommand(),
                    new VersionConsoleCommand());
            console.start();

        } catch (StartupException e) {
            logger.error("An error occurred during startup:", e);
        }
    }

    public static TirukaApp getInstance() {
        if (instance == null) throw new IllegalStateException("App instance is not initialized");
        return instance;
    }

    public void shutdown() {
        if (shardManager != null) shardManager.shutdown();
        if (console != null) console.shutdown();
        if (prometheusServer != null) prometheusServer.close();
        metricsScheduler.shutdown();
        if (storage != null) storage.close();
        latch.countDown();
    }

    public void shutdownAwait() {
        shutdown();
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public CacheManager getCache() {
        return cache;
    }

    public CommandRegistrar getCommandRegistrar() {
        return commandRegistrar;
    }

    public ConfigSchema getConfig() {
        return config;
    }

    public Console getConsole() {
        return console;
    }

    public Path getDataDirectory() {
        return dataDirectory;
    }

    public DependencyLoader getDependencyLoader() {
        return dependencyLoader;
    }

    public InputStream getResourceAsStream(String name) {
        return TirukaApp.class.getClassLoader().getResourceAsStream(name);
    }

    public ShardManager getShardManager() {
        return shardManager;
    }

    public Storage getStorage() {
        return storage;
    }

    public long getUptime() {
        return System.currentTimeMillis() - startTime;
    }

}
