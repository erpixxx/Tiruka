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

public class H2ConnectionFactory extends FlatfileConnectionFactory {

    private static final Logger logger = LoggerFactory.getLogger(H2ConnectionFactory.class);

    private Constructor<?> connection;

    public H2ConnectionFactory(TirukaApp tiruka, Path path) {
        super(tiruka, path);
    }

    @Override
    public void init() {
        try {
            Class<?> connectionClass = Class.forName("org.h2.jdbc.JdbcConnection");
            connection = connectionClass.getDeclaredConstructor(
                    String.class, Properties.class, String.class, Object.class, boolean.class);
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
                    "jdbc:h2:./" + path.getFileName().toString(), new Properties(), null, null, false);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            logger.error("Could not create connection", e);
        }
        return null;
    }

    @Override
    public String getName() {
        return "H2";
    }

    @Override
    public StatementProcessor getStatementProcessor() {
        return stmt -> stmt.replace('\'', '`')
                .replace("LIKE", "ILIKE");
    }

}
