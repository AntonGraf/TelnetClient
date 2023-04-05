package ru.tetra.telnetclient.utils.telnet;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelnetClient {

    static final Integer DEFAULT_TELNET_PORT = 23;
    Thread writer;
    final String ip;
    final int port;
    InputStream telnetInputStream;
    OutputStream telnetOutputStream;
    Socket socket;
    boolean open;

    public TelnetClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public TelnetClient(String ip) {
        this.ip = ip;
        port = DEFAULT_TELNET_PORT;
    }

    public void start() {
        openSocket();
        Thread reader = new Thread(this::read);
    }

    public void sendCommand(String command) {
        try {
            telnetOutputStream.write((command + "\n").getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        closeSocket();
    }

    public boolean isOpen() {
        return open && !socket.isClosed();
    }

    private void openSocket() {
        try {
            socket = new Socket(ip, port);
            telnetInputStream = socket.getInputStream();
            telnetOutputStream = socket.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeSocket() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        open = false;
    }

    private void read() {

        while(open) {
            try {
                System.out.print((char) telnetInputStream.read());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
