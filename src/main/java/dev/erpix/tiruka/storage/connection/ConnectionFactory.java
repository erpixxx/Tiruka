package dev.erpix.tiruka.storage.connection;

import java.sql.Connection;

public interface ConnectionFactory extends AutoCloseable {

    void init();

    void close();

    Connection getConnection();

    String getName();

    StatementProcessor getStatementProcessor();

}
