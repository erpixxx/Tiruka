package dev.erpix.tiruka;

public enum Dependency {

    H2(TirukaConstants.H2_GROUP_ID, TirukaConstants.H2_ARTIFACT_ID, TirukaConstants.H2_VERSION),
    SQLITE(TirukaConstants.SQLITE_GROUP_ID, TirukaConstants.SQLITE_ARTIFACT_ID, TirukaConstants.SQLITE_VERSION),
    MARIADB(TirukaConstants.MARIADB_GROUP_ID, TirukaConstants.MARIADB_ARTIFACT_ID, TirukaConstants.MARIADB_VERSION),
    MYSQL(TirukaConstants.MYSQL_GROUP_ID, TirukaConstants.MYSQL_ARTIFACT_ID, TirukaConstants.MYSQL_VERSION),
    POSTGRESQL(TirukaConstants.POSTGRESQL_GROUP_ID, TirukaConstants.POSTGRESQL_ARTIFACT_ID, TirukaConstants.POSTGRESQL_VERSION),
    REDIS(TirukaConstants.JEDIS_GROUP_ID, TirukaConstants.JEDIS_ARTIFACT_ID, TirukaConstants.JEDIS_VERSION),
    COMMONS_POOL2(TirukaConstants.COMMONS_POOL2_GROUP_ID, TirukaConstants.COMMONS_POOL2_ARTIFACT_ID, TirukaConstants.COMMONS_POOL2_VERSION);

    private final String groupId;
    private final String artifactId;
    private final String version;

    Dependency(String groupId, String artifactId, String version) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getVersion() {
        return version;
    }

}
