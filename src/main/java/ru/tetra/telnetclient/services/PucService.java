package ru.tetra.telnetclient.services;

import org.springframework.stereotype.Service;
import ru.tetra.telnetclient.enums.Commands;
import ru.tetra.telnetclient.utils.telnet.PucClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class PucService {

    private final List<PucClient> pucClients = new ArrayList<>();
    public void sendCommand(String ip, int port, String command) {
        PucClient pucClient = getPucClient(ip, port);
        pucClient.sendCommand(command);
    }

    public void disconnect(String ip, int port) {
        PucClient pucClient = getPucClient(ip, port);
        pucClient.disconnect();
    }

    public List<PucClient> getPucClients() {
        return pucClients;
    }

    public void disconnectAll() {
        pucClients.stream()
                .parallel()
                .forEach(pucClient -> disconnect(pucClient.getIp(), pucClient.getPort()));
    }

    public List<String> getPucAlarms(String ip) {
        PucClient pucClient = getPucClient(ip, PucClient.DEFAULT_PUC_PORT);
        String alarmCommand = Commands.ALARM.getCommand();
        pucClient.sendCommand(alarmCommand);
        return pucClient.getCommandResult(alarmCommand, PucClient.DEFAULT_SUCCESS_ANSWER);
    }
    private PucClient getPucClient(String ip, int port) {
        for (PucClient pucClient : pucClients) {
            if (pucClient.getIp().equals(ip) && pucClient.getPort() == port) {
                return pucClient;
            }
        }
        PucClient pucClient = new PucClient(ip, port);
        pucClients.add(pucClient);
        return pucClient;
    }



}
