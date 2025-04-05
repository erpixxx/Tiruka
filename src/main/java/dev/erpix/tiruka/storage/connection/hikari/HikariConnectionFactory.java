package dev.erpix.tiruka.storage.connection.hikari;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.erpix.tiruka.storage.connection.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class HikariConnectionFactory implements ConnectionFactory {

    private static final Logger logger = LoggerFactory.getLogger(HikariConnectionFactory.class);

    private final StorageCredentials credentials;
    private HikariDataSource dataSource;

    public HikariConnectionFactory(StorageCredentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public void init() {
        HikariConfig config = new HikariConfig();

        config.setPoolName("Tiruka-" + getName() + "-HikariCP");

        config.setDriverClassName(getDriverClassName());
        config.setJdbcUrl("jdbc:" + getJdbcIdentifier() + "://" + credentials.address() + ":" + credentials.port() + "/" + credentials.database());
        config.setUsername(credentials.username());
        config.setPassword(credentials.password());

        dataSource = new HikariDataSource(config);
    }

    @Override
    public void close() {
        if (dataSource != null) dataSource.close();
    }

    @Override
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            logger.error("Could not get database connection", e);
        }
        return null;
    }

    public abstract String getDriverClassName();

    public abstract String getJdbcIdentifier();

}
