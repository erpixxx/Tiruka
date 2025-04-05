package dev.erpix.tiruka.storage.connection;

@FunctionalInterface
public interface StatementProcessor {

    String process(String statement);

    default StatementProcessor combine(StatementProcessor processor) {
        return stmt -> process(processor.process(stmt));
    }

}
