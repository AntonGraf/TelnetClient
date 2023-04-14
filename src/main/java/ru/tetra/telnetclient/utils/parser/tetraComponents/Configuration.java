package ru.tetra.telnetclient.utils.parser.tetraComponents;

import java.util.List;

/**
 * Create by graf-anton on 12.03.2021
 */
public class Configuration {

    //Список БД
    private List<Database> databases;
    //TETRA-сеть
    private Network network;
    //Версия ПО
    private String version;
    //Версия программного пакета
    private String packageVersion;
    //Версия интерфейса
    private String interfaceVersion;

    public Configuration(String version, String packageVersion, String interfaceVersion) {
        this.version = version;
        this.packageVersion = packageVersion;
        this.interfaceVersion = interfaceVersion;
    }

    public List<Database> getDatabases() {
        return databases;
    }

    public void setDatabases(List<Database> databases) {
        this.databases = databases;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPackageVersion() {
        return packageVersion;
    }

    public void setPackageVersion(String packageVersion) {
        this.packageVersion = packageVersion;
    }

    public String getInterfaceVersion() {
        return interfaceVersion;
    }

    public void setInterfaceVersion(String interfaceVersion) {
        this.interfaceVersion = interfaceVersion;
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "databases=" + databases +
                ", network=" + network +
                ", version='" + version + '\'' +
                ", packageVersion='" + packageVersion + '\'' +
                ", interfaceVersion='" + interfaceVersion + '\'' +
                '}';
    }
}
