package ru.tetra.telnetclient.utils.parser.tetraComponents;

/**
 * Create by graf-anton on 12.03.2021
 */
public class Database {

    //Имя БД
    private String name;
    //Порт
    private int portNumber;
    //Имя сервера
    private String serverName;
    //Имя БД
    private String databaseName;

    public Database(String name, int portNumber, String serverName, String databaseName) {
        this.name = name;
        this.portNumber = portNumber;
        this.serverName = serverName;
        this.databaseName = databaseName;
    }

}
