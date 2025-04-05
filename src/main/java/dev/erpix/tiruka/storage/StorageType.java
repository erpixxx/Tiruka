package dev.erpix.tiruka.storage;

public enum StorageType {

    H2("H2"),
    MARIADB("MariaDB"),
    MYSQL("MySQL"),
    POSTGRESQL("PostgreSQL"),
    SQLITE("SQLite");

    private final String name;

    StorageType(String name) {
        this.name = name;
    }

    public static StorageType from(String name) {
        for (StorageType type : values()) {
            if (type.name.equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

}
