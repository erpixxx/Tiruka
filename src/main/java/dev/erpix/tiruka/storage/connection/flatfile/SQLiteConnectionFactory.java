package dev.erpix.tiruka.storage.connection.flatfile;

import dev.erpix.tiruka.TirukaApp;
import dev.erpix.tiruka.storage.connection.StatementProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.sql.Connection;
import java.util.Properties;

public class SQLiteConnectionFactory extends FlatfileConnectionFactory {

    private static final Logger logger = LoggerFactory.getLogger(SQLiteConnectionFactory.class);

    private Constructor<?> connection;

    public SQLiteConnectionFactory(TirukaApp tiruka, Path path) {
        super(tiruka, path);
    }

    @Override
    public void init() {
        try {
            Class<?> connectionClass = Class.forName("org.sqlite.jdbc4.JDBC4Connection");
            connection = connectionClass.getDeclaredConstructor(String.class, String.class, Properties.class);
        } catch (ClassNotFoundException e) {
            logger.error("Could not find driver class", e);
        } catch (NoSuchMethodException e) {
            logger.error("Could not find constructor for connection class", e);
        }
    }

    @Override
    public void close() {

    }

    @Override
    public Connection getConnection() {
        try {
            return (Connection) connection.newInstance(
                    "jdbc:sqlite:./" + path.getFileName().toString(), path.getFileName().toString() ,new Properties());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            logger.error("Could not create connection", e);
        }
        return null;
    }

    @Override
    public String getName() {
        return "SQLite";
    }

    @Override
    public StatementProcessor getStatementProcessor() {
        return stmt -> stmt.replace('\'', '`');
    }

}
