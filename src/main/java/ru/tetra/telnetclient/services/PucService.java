package ru.tetra.telnetclient.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.tetra.telnetclient.enums.Commands;
import ru.tetra.telnetclient.utils.parser.tetraComponents.software.Puc;
import ru.tetra.telnetclient.utils.telnet.PucClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class PucService {

    private final ConfigurationService configurationService;
    private final List<PucClient> pucClients = new ArrayList<>();

    public PucService(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

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

    public int createPucClientFromFile(MultipartFile file) {
        configurationService.importConfiguration(file);
        List<Puc> pucs = configurationService.getAllPucs();
        pucClients.clear();
        int addedCount = 0;
        for (Puc puc : pucs) {
            pucClients.add(new PucClient(puc.getIpAddress()));
            addedCount++;
        }
        return addedCount;
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
