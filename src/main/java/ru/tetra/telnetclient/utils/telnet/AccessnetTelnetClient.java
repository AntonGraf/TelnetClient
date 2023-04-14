package ru.tetra.telnetclient.utils.telnet;

public interface AccessnetTelnetClient {

    boolean isConnected();
    void connect();
    boolean isAuthorization();
    void authorization();
    void sendCommand(String command);
    void disconnect();
}
