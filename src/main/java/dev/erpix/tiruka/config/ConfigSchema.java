package dev.erpix.tiruka.config;

public class ConfigSchema {

    private int version;
    private String primaryLanguage;
    private String appToken;
    private Profile profile;
    private Features features;
    private Cache cache;
    private Storage storage;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getPrimaryLanguage() {
        return primaryLanguage;
    }

    public void setPrimaryLanguage(String primaryLanguage) {
        this.primaryLanguage = primaryLanguage;
    }

    public String getAppToken() {
        return appToken;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Features getFeatures() {
        return features;
    }

    public void setFeatures(Features features) {
        this.features = features;
    }

    public Cache getCache() {
        return cache;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public static class Profile {

        private Activity activity;
        private String status;

        public Activity getActivity() {
            return activity;
        }

        public void setActivity(Activity activity) {
            this.activity = activity;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public static class Activity {

            private int type;
            private String name;
            private String url;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            @Override
            public String toString() {
                return "Activity{" +
                        "type=" + type +
                        ", name='" + name + '\'' +
                        ", url='" + url + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "Profile{" +
                    "activity=" + activity +
                    ", status='" + status + '\'' +
                    '}';
        }

    }

    public static class Features {

        private Economy economy;
        private LevelingSystem levelingSystem;
        private Logging logging;

        public Economy getEconomy() {
            return economy;
        }

        public void setEconomy(Economy economy) {
            this.economy = economy;
        }

        public LevelingSystem getLevelingSystem() {
            return levelingSystem;
        }

        public void setLevelingSystem(LevelingSystem levelingSystem) {
            this.levelingSystem = levelingSystem;
        }

        public Logging getLogging() {
            return logging;
        }

        public void setLogging(Logging logging) {
            this.logging = logging;
        }

        public static class Economy {

            private boolean enabled;
            private DefaultSettings defaultSettings;

            public boolean isEnabled() {
                return enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }

            public DefaultSettings getDefaultSettings() {
                return defaultSettings;
            }

            public void setDefaultSettings(DefaultSettings defaultSettings) {
                this.defaultSettings = defaultSettings;
            }

            public static class DefaultSettings {

                private String currencySymbol;
                private int dailyBaseValue;
                private int dailyStreakBonus;
                private int weeklyStreakBonus;
                private int monthlyStreakBonus;
                private int chatMessageValueMin;
                private int chatMessageValueMax;
                private int chatMessageInterval;
                private int voiceActivityValueMin;
                private int voiceActivityValueMax;
                private int voiceActivityInterval;

                public String getCurrencySymbol() {
                    return currencySymbol;
                }

                public void setCurrencySymbol(String currencySymbol) {
                    this.currencySymbol = currencySymbol;
                }

                public int getDailyBaseValue() {
                    return dailyBaseValue;
                }

                public void setDailyBaseValue(int dailyBaseValue) {
                    this.dailyBaseValue = dailyBaseValue;
                }

                public int getDailyStreakBonus() {
                    return dailyStreakBonus;
                }

                public void setDailyStreakBonus(int dailyStreakBonus) {
                    this.dailyStreakBonus = dailyStreakBonus;
                }

                public int getWeeklyStreakBonus() {
                    return weeklyStreakBonus;
                }

                public void setWeeklyStreakBonus(int weeklyStreakBonus) {
                    this.weeklyStreakBonus = weeklyStreakBonus;
                }

                public int getMonthlyStreakBonus() {
                    return monthlyStreakBonus;
                }

                public void setMonthlyStreakBonus(int monthlyStreakBonus) {
                    this.monthlyStreakBonus = monthlyStreakBonus;
                }

                public int getChatMessageValueMin() {
                    return chatMessageValueMin;
                }

                public void setChatMessageValueMin(int chatMessageValueMin) {
                    this.chatMessageValueMin = chatMessageValueMin;
                }

                public int getChatMessageValueMax() {
                    return chatMessageValueMax;
                }

                public void setChatMessageValueMax(int chatMessageValueMax) {
                    this.chatMessageValueMax = chatMessageValueMax;
                }

                public int getChatMessageInterval() {
                    return chatMessageInterval;
                }

                public void setChatMessageInterval(int chatMessageInterval) {
                    this.chatMessageInterval = chatMessageInterval;
                }

                public int getVoiceActivityValueMin() {
                    return voiceActivityValueMin;
                }

                public void setVoiceActivityValueMin(int voiceActivityValueMin) {
                    this.voiceActivityValueMin = voiceActivityValueMin;
                }

                public int getVoiceActivityValueMax() {
                    return voiceActivityValueMax;
                }

                public void setVoiceActivityValueMax(int voiceActivityValueMax) {
                    this.voiceActivityValueMax = voiceActivityValueMax;
                }

                public int getVoiceActivityInterval() {
                    return voiceActivityInterval;
                }

                public void setVoiceActivityInterval(int voiceActivityInterval) {
                    this.voiceActivityInterval = voiceActivityInterval;
                }

                @Override
                public String toString() {
                    return "DefaultSettings{" +
                            "currencySymbol='" + currencySymbol + '\'' +
                            ", dailyBaseValue=" + dailyBaseValue +
                            ", dailyStreakBonus=" + dailyStreakBonus +
                            ", weeklyStreakBonus=" + weeklyStreakBonus +
                            ", monthlyStreakBonus=" + monthlyStreakBonus +
                            ", chatMessageValueMin=" + chatMessageValueMin +
                            ", chatMessageValueMax=" + chatMessageValueMax +
                            ", chatMessageInterval=" + chatMessageInterval +
                            ", voiceActivityValueMin=" + voiceActivityValueMin +
                            ", voiceActivityValueMax=" + voiceActivityValueMax +
                            ", voiceActivityInterval=" + voiceActivityInterval +
                            '}';
                }

            }

            @Override
            public String toString() {
                return "Economy{" +
                        "enabled=" + enabled +
                        ", defaultSettings=" + defaultSettings +
                        '}';
            }

        }

        public static class LevelingSystem {

            private boolean enabled;
            private DefaultSettings defaultSettings;

            public boolean isEnabled() {
                return enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }

            public DefaultSettings getDefaultSettings() {
                return defaultSettings;
            }

            public void setDefaultSettings(DefaultSettings defaultSettings) {
                this.defaultSettings = defaultSettings;
            }

            public static class DefaultSettings {

                private int baseMaxExperience;
                private int chatMessageValueMin;
                private int chatMessageValueMax;
                private int chatMessageInterval;
                private int voiceActivityValueMin;
                private int voiceActivityValueMax;
                private int voiceActivityInterval;

                public int getBaseMaxExperience() {
                    return baseMaxExperience;
                }

                public void setBaseMaxExperience(int baseMaxExperience) {
                    this.baseMaxExperience = baseMaxExperience;
                }

                public int getChatMessageValueMin() {
                    return chatMessageValueMin;
                }

                public void setChatMessageValueMin(int chatMessageValueMin) {
                    this.chatMessageValueMin = chatMessageValueMin;
                }

                public int getChatMessageValueMax() {
                    return chatMessageValueMax;
                }

                public void setChatMessageValueMax(int chatMessageValueMax) {
                    this.chatMessageValueMax = chatMessageValueMax;
                }

                public int getChatMessageInterval() {
                    return chatMessageInterval;
                }

                public void setChatMessageInterval(int chatMessageInterval) {
                    this.chatMessageInterval = chatMessageInterval;
                }

                public int getVoiceActivityValueMin() {
                    return voiceActivityValueMin;
                }

                public void setVoiceActivityValueMin(int voiceActivityValueMin) {
                    this.voiceActivityValueMin = voiceActivityValueMin;
                }

                public int getVoiceActivityValueMax() {
                    return voiceActivityValueMax;
                }

                public void setVoiceActivityValueMax(int voiceActivityValueMax) {
                    this.voiceActivityValueMax = voiceActivityValueMax;
                }

                public int getVoiceActivityInterval() {
                    return voiceActivityInterval;
                }

                public void setVoiceActivityInterval(int voiceActivityInterval) {
                    this.voiceActivityInterval = voiceActivityInterval;
                }

                @Override
                public String toString() {
                    return "DefaultSettings{" +
                            "baseMaxExperience=" + baseMaxExperience +
                            ", chatMessageValueMin=" + chatMessageValueMin +
                            ", chatMessageValueMax=" + chatMessageValueMax +
                            ", chatMessageInterval=" + chatMessageInterval +
                            ", voiceActivityValueMin=" + voiceActivityValueMin +
                            ", voiceActivityValueMax=" + voiceActivityValueMax +
                            ", voiceActivityInterval=" + voiceActivityInterval +
                            '}';
                }

            }

            @Override
            public String toString() {
                return "LevelingSystem{" +
                        "enabled=" + enabled +
                        ", defaultSettings=" + defaultSettings +
                        '}';
            }

        }

        public static class Logging {

            private boolean enabled;
            private String logChannel;

            public boolean isEnabled() {
                return enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }

            public String getLogChannel() {
                return logChannel;
            }

            public void setLogChannel(String logChannel) {
                this.logChannel = logChannel;
            }

            @Override
            public String toString() {
                return "Logging{" +
                        "enabled=" + enabled +
                        ", logChannel='" + logChannel + '\'' +
                        '}';
            }

        }

        @Override
        public String toString() {
            return "Features{" +
                    "economy=" + economy +
                    ", levelingSystem=" + levelingSystem +
                    ", logging=" + logging +
                    '}';
        }

    }

    public static class Cache {

        private Memory memory;
        private Redis redis;

        public Memory getMemory() {
            return memory;
        }

        public void setMemory(Memory memory) {
            this.memory = memory;
        }

        public Redis getRedis() {
            return redis;
        }

        public void setRedis(Redis redis) {
            this.redis = redis;
        }

        public static class Memory {

            private int expireAfterAccess;
            private int expireAfterWrite;
            private int maximumSize;

            public int getExpireAfterAccess() {
                return expireAfterAccess;
            }

            public void setExpireAfterAccess(int expireAfterAccess) {
                this.expireAfterAccess = expireAfterAccess;
            }

            public int getExpireAfterWrite() {
                return expireAfterWrite;
            }

            public void setExpireAfterWrite(int expireAfterWrite) {
                this.expireAfterWrite = expireAfterWrite;
            }

            public int getMaximumSize() {
                return maximumSize;
            }

            public void setMaximumSize(int maximumSize) {
                this.maximumSize = maximumSize;
            }

            @Override
            public String toString() {
                return "Rules{" +
                        "expireAfterAccess=" + expireAfterAccess +
                        ", expireAfterWrite=" + expireAfterWrite +
                        ", maximumSize=" + maximumSize +
                        '}';
            }

        }

        public static class Redis {

            private boolean enabled;
            private int expireAfterAccess;
            private int expireAfterWrite;
            private Credentials credentials;
            private PoolSettings poolSettings;

            public boolean getEnabled() {
                return enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }

            public int getExpireAfterAccess() {
                return expireAfterAccess;
            }

            public void setExpireAfterAccess(int expireAfterAccess) {
                this.expireAfterAccess = expireAfterAccess;
            }

            public int getExpireAfterWrite() {
                return expireAfterWrite;
            }

            public void setExpireAfterWrite(int expireAfterWrite) {
                this.expireAfterWrite = expireAfterWrite;
            }

            public Credentials getCredentials() {
                return credentials;
            }

            public void setCredentials(Credentials credentials) {
                this.credentials = credentials;
            }

            public PoolSettings getPoolSettings() {
                return poolSettings;
            }

            public void setPoolSettings(PoolSettings poolSettings) {
                this.poolSettings = poolSettings;
            }

            public static class Credentials {

                private String hostname;
                private int port;
                private String username;
                private String password;
                private String database;

                public String getHostname() {
                    return hostname;
                }

                public void setHostname(String hostname) {
                    this.hostname = hostname;
                }

                public int getPort() {
                    return port;
                }

                public void setPort(int port) {
                    this.port = port;
                }

                public String getUsername() {
                    return username;
                }

                public void setUsername(String username) {
                    this.username = username;
                }

                public String getPassword() {
                    return password;
                }

                public void setPassword(String password) {
                    this.password = password;
                }

                public String getDatabase() {
                    return database;
                }

                public void setDatabase(String database) {
                    this.database = database;
                }

                @Override
                public String toString() {
                    return "Credentials{" +
                            "hostname='" + hostname + '\'' +
                            ", port=" + port +
                            ", username='" + username + '\'' +
                            ", password='" + password + '\'' +
                            ", database='" + database + '\'' +
                            '}';
                }

            }

            public static class PoolSettings {

                private int maximumPoolSize;
                private int maximumIdle;
                private int minimumIdle;

                public int getMaximumPoolSize() {
                    return maximumPoolSize;
                }

                public void setMaximumPoolSize(int maximumPoolSize) {
                    this.maximumPoolSize = maximumPoolSize;
                }

                public int getMaximumIdle() {
                    return maximumIdle;
                }

                public void setMaximumIdle(int maximumIdle) {
                    this.maximumIdle = maximumIdle;
                }

                public int getMinimumIdle() {
                    return minimumIdle;
                }

                public void setMinimumIdle(int minimumIdle) {
                    this.minimumIdle = minimumIdle;
                }

                @Override
                public String toString() {
                    return "PoolSettings{" +
                            "maximumPoolSize=" + maximumPoolSize +
                            ", maximumIdle=" + maximumIdle +
                            ", minimumIdle=" + minimumIdle +
                            '}';
                }

            }

        }

        @Override
        public String toString() {
            return "Cache{" +
                    "memory=" + memory +
                    ", redis=" + redis +
                    '}';
        }

    }

    public static class Storage {

        private String type;
        private Data data;

        public static class Data {

            private String hostname;
            private String port;
            private String username;
            private String password;
            private String database;
            private PoolSettings poolSettings;
            private String tablePrefix;

            public static class PoolSettings {

                private int connectionTimeout;
                private int maximumPoolSize;
                private int minimumIdle;

                public int getConnectionTimeout() {
                    return connectionTimeout;
                }

                public void setConnectionTimeout(int connectionTimeout) {
                    this.connectionTimeout = connectionTimeout;
                }

                public int getMaximumPoolSize() {
                    return maximumPoolSize;
                }

                public void setMaximumPoolSize(int maximumPoolSize) {
                    this.maximumPoolSize = maximumPoolSize;
                }

                public int getMinimumIdle() {
                    return minimumIdle;
                }

                public void setMinimumIdle(int minimumIdle) {
                    this.minimumIdle = minimumIdle;
                }

                @Override
                public String toString() {
                    return "PoolSettings{" +
                            "connectionTimeout=" + connectionTimeout +
                            ", maximumPoolSize=" + maximumPoolSize +
                            ", minimumIdle=" + minimumIdle +
                            '}';
                }

            }

            public String getHostname() {
                return hostname;
            }

            public void setHostname(String hostname) {
                this.hostname = hostname;
            }

            public String getPort() {
                return port;
            }

            public void setPort(String port) {
                this.port = port;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getDatabase() {
                return database;
            }

            public void setDatabase(String database) {
                this.database = database;
            }

            public PoolSettings getPoolSettings() {
                return poolSettings;
            }

            public void setPoolSettings(PoolSettings poolSettings) {
                this.poolSettings = poolSettings;
            }

            public String getTablePrefix() {
                return tablePrefix;
            }

            public void setTablePrefix(String tablePrefix) {
                this.tablePrefix = tablePrefix;
            }

            @Override
            public String toString() {
                return "Data{" +
                        "hostname='" + hostname + '\'' +
                        ", port='" + port + '\'' +
                        ", username='" + username + '\'' +
                        ", password='" + password + '\'' +
                        ", database='" + database + '\'' +
                        ", poolSettings=" + poolSettings +
                        ", tablePrefix='" + tablePrefix + '\'' +
                        '}';
            }

        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "Storage{" +
                    "type='" + type + '\'' +
                    ", data=" + data +
                    '}';
        }

    }

    @Override
    public String toString() {
        return "ConfigSchema{" +
                "version=" + version +
                ", primaryLanguage='" + primaryLanguage + '\'' +
                ", appToken='" + appToken + '\'' +
                ", profile=" + profile +
                ", features=" + features +
                ", storage=" + storage +
                '}';
    }

}
