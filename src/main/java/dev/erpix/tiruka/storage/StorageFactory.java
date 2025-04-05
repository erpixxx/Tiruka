package dev.erpix.tiruka.storage;

import dev.erpix.tiruka.Dependency;
import dev.erpix.tiruka.TirukaApp;
import dev.erpix.tiruka.storage.connection.flatfile.H2ConnectionFactory;
import dev.erpix.tiruka.storage.connection.flatfile.SQLiteConnectionFactory;
import dev.erpix.tiruka.storage.connection.hikari.MariaDBConnectionFactory;
import dev.erpix.tiruka.storage.connection.hikari.MySQLConnectionFactory;
import dev.erpix.tiruka.storage.connection.hikari.PostgreSQLConnectionFactory;
import dev.erpix.tiruka.storage.connection.hikari.StorageCredentials;

public final class StorageFactory {

    public static Storage create(StorageType type) {
        TirukaApp tiruka = TirukaApp.getInstance();
        var config = tiruka.getConfig().getStorage().getData();
        switch (type) {
            case H2 -> {
                tiruka.getDependencyLoader().load(Dependency.H2);
                return new Storage(tiruka, new H2ConnectionFactory(
                        tiruka, tiruka.getDataDirectory().resolve("tiruka-h2")
                ), config.getTablePrefix());
            }
            case SQLITE -> {
                tiruka.getDependencyLoader().load(Dependency.SQLITE);
                return new Storage(tiruka, new SQLiteConnectionFactory(
                        tiruka, tiruka.getDataDirectory().resolve("tiruka-sqlite.db")
                ), config.getTablePrefix());
            }
            case MARIADB -> {
                tiruka.getDependencyLoader().load(Dependency.MARIADB);
                StorageCredentials credentials = new StorageCredentials(
                        config.getHostname(),
                        config.getPort(),
                        config.getUsername(),
                        config.getPassword(),
                        config.getDatabase());
                return new Storage(tiruka, new MariaDBConnectionFactory(credentials), config.getTablePrefix());
            }
            case MYSQL -> {
                tiruka.getDependencyLoader().load(Dependency.MYSQL);
                StorageCredentials credentials = new StorageCredentials(
                        config.getHostname(),
                        config.getPort(),
                        config.getUsername(),
                        config.getPassword(),
                        config.getDatabase());
                return new Storage(tiruka, new MySQLConnectionFactory(credentials), config.getTablePrefix());
            }
            case POSTGRESQL -> {
                tiruka.getDependencyLoader().load(Dependency.POSTGRESQL);
                StorageCredentials credentials = new StorageCredentials(
                        config.getHostname(),
                        config.getPort(),
                        config.getUsername(),
                        config.getPassword(),
                        config.getDatabase());
                return new Storage(tiruka, new PostgreSQLConnectionFactory(credentials), config.getTablePrefix());
            }
            default -> throw new IllegalArgumentException("Unknown storage type.");
        }
    }

}
