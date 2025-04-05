package dev.erpix.tiruka.storage.connection.hikari;

import dev.erpix.tiruka.storage.connection.StatementProcessor;

public class PostgreSQLConnectionFactory extends HikariConnectionFactory {

    public PostgreSQLConnectionFactory(StorageCredentials credentials) {
        super(credentials);
    }

    @Override
    public String getDriverClassName() {
        return "org.postgresql.Driver";
    }

    @Override
    public String getJdbcIdentifier() {
        return "postgresql";
    }

    @Override
    public String getName() {
        return "PostgreSQL";
    }

    @Override
    public StatementProcessor getStatementProcessor() {
        return stmt -> stmt.replace('\'', '"');
    }

}
