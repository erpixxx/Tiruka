package dev.erpix.tiruka.storage.connection.hikari;

import dev.erpix.tiruka.storage.connection.StatementProcessor;

public class MariaDBConnectionFactory extends HikariConnectionFactory {

    public MariaDBConnectionFactory(StorageCredentials credentials) {
        super(credentials);
    }

    @Override
    public String getName() {
        return "MariaDB";
    }

    @Override
    public StatementProcessor getStatementProcessor() {
        return stmt -> stmt.replace('\'', '`');
    }

    @Override
    public String getDriverClassName() {
        return "org.mariadb.jdbc.Driver";
    }

    @Override
    public String getJdbcIdentifier() {
        return "mariadb";
    }

}
