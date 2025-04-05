package dev.erpix.tiruka.storage.connection.hikari;

import dev.erpix.tiruka.storage.connection.StatementProcessor;

public class MySQLConnectionFactory extends HikariConnectionFactory {

    public MySQLConnectionFactory(StorageCredentials credentials) {
        super(credentials);
    }

    @Override
    public String getDriverClassName() {
        return "com.mysql.cj.jdbc.Driver";
    }

    @Override
    public String getJdbcIdentifier() {
        return "mysql";
    }

    @Override
    public String getName() {
        return "MySQL";
    }

    @Override
    public StatementProcessor getStatementProcessor() {
        return s -> s.replace('\'', '`');
    }

}
