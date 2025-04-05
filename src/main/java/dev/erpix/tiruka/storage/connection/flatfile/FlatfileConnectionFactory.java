package dev.erpix.tiruka.storage.connection.flatfile;

import dev.erpix.tiruka.TirukaApp;
import dev.erpix.tiruka.storage.connection.ConnectionFactory;

import java.nio.file.Path;

public abstract class FlatfileConnectionFactory implements ConnectionFactory {

    protected final TirukaApp tiruka;
    protected final Path path;

    public FlatfileConnectionFactory(TirukaApp tiruka, Path path) {
        this.tiruka = tiruka;
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

}
