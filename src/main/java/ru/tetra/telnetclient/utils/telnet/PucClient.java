package ru.tetra.telnetclient.utils.telnet;

import org.apache.commons.net.telnet.TelnetClient;
import ru.tetra.telnetclient.enums.Commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PucClient implements AccessnetTelnetClient {

    public static final int DEFAULT_PUC_PORT = 5100;
    public static final String DEFAULT_SUCCESS_ANSWER = "Command is okay";
    private String ip;
    private int port;
    private boolean connection;
    private boolean authorization;
    private Queue<String> messages;
    private TelnetClient telnetClient;
    private Thread readThread;
    private PrintStream out;

    public PucClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
        telnetClient = new TelnetClient();
        messages = new LinkedList<>();
    }

    public PucClient(String ip) {
        this.ip = ip;
        port = DEFAULT_PUC_PORT;
        telnetClient = new TelnetClient();
        messages = new LinkedList<>();
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    @Override
    public boolean isConnected() {
        return telnetClient.isConnected() && connection;
    }

    @Override
    public void connect() {
        try {
            telnetClient.connect(ip, port);
            readThread = new Thread(this::read);
            readThread.start();
            out = new PrintStream(telnetClient.getOutputStream());
            connection = checkAnswer("BSC " + ip + " (" + port + ") >");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isAuthorization() {
        return isConnected() && authorization;
    }

    @Override
    public void authorization() {
        if (!isConnected()) {
            connect();
        }

        out.println(Commands.AUTHORIZATION.getCommand());
        out.flush();
        authorization = checkAnswer("User Level: 3");
        messages.clear();
    }

    @Override
    public void sendCommand(String command) {
        if (!isAuthorization()) {
            authorization();
        }

        out.println(command);
        out.flush();
    }

    @Override
    public void disconnect() {
        try {
            telnetClient.disconnect();
            connection = false;
            authorization = false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkAnswer(String answer) {
        String readMessage;
        for (int i = 0; i < 3; i++) {
            while (!messages.isEmpty()) {
                readMessage = messages.remove();
                if (readMessage.contains(answer)) {
                    return true;
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public List<String> getCommandResult(String command, String successResult) {
        List<String> commandResult = new ArrayList<>();
        String readMessage;
        boolean commandStartResult = false;

        for (int i = 0; i < 3; i++) {

            while(!messages.isEmpty()) {
                readMessage = messages.remove();
                if (!commandStartResult && readMessage.contains(command)) {
                    commandStartResult = true;
                }
                if (commandStartResult) {
                    commandResult.add(readMessage);
                }
                if (readMessage.contains(successResult)) {
                    return commandResult;
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return commandResult;
    }

    public void clearMessage() {
        messages.clear();
    }

    private void read() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(telnetClient.getInputStream()));
        String readString;
        while (telnetClient.isConnected()) {
            try {
                while (((readString = reader.readLine()) != null)) {
                    messages.add(readString);
                    System.out.println(readString);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
    }
}
