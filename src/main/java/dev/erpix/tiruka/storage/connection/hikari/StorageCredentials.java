package dev.erpix.tiruka.storage.connection.hikari;

public record StorageCredentials(
        String address,
        String port,
        String username,
        String password,
        String database
) { }

