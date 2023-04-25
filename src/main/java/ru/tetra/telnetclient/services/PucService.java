package ru.tetra.telnetclient.services;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.tetra.telnetclient.enums.Commands;
import ru.tetra.telnetclient.utils.parser.tetraComponents.software.Puc;
import ru.tetra.telnetclient.utils.telnet.PucClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return getPucAlarms(ip, PucClient.DEFAULT_PUC_PORT);
    }

    public List<String> getPucAlarms(String ip, int port) {
        PucClient pucClient = getPucClient(ip, port);
        return getPucAlarms(pucClient);
    }

    public List<String> getPucAlarms(PucClient pucClient) {
        String alarmCommand = Commands.ALARM.getCommand();
        pucClient.sendCommand(alarmCommand);

        wait(300);
        List<String> results = getResults(pucClient);
        if (results.isEmpty()) {
            wait(1000);
            results = getResults(pucClient);
        }
        return results;
    }

    public Map<String, List<String>> getAllPucsAlarms() {
        Map<String, List<String>> pucsAlarms = new HashMap<>();
        pucClients.stream()
                .parallel()
                .forEach(pucClient -> {
                    List<String> alarms = getPucAlarms(pucClient);
                    pucsAlarms.put(pucClient.getIp(), alarms);
                });
        return pucsAlarms;
    }

    public Map<String, String> getAllPucsIpAddressFirstTimeSource() {
        Map<String, String> timeSourcesIpAddress = new HashMap<>();
        pucClients.stream()
                .parallel()
                .forEach(pucClient -> {
                    String ipAddress = getIpAddressFirstTimeSource(pucClient);
                    timeSourcesIpAddress.put(pucClient.getIp(), ipAddress);
                });
        return timeSourcesIpAddress;
    }

    public String getIpAddressFirstTimeSource(PucClient pucClient) {
        String command = "p l -A sbue sbec 23 00";
        pucClient.sendCommand(command);

        wait(300);
        List<String> commandOutputs = pucClient.getCommandResult();
        if (commandOutputs.isEmpty()) {
            wait(1000);
            commandOutputs = pucClient.getCommandResult();
        }

        String result = commandOutputs.stream()
                .filter(output -> output.startsWith("SBUE SBEC 23 00"))
                .findFirst()
                .orElse(Strings.EMPTY);

        if (!result.isEmpty()) {
            String[] wordsInResult = result.split(" ");
            return wordsInResult[wordsInResult.length - 1];
        }
        return result;
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

    private List<String> getResults(PucClient pucClient) {
        List<String> results = pucClient.getCommandResult();
        if (results.isEmpty()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            results = pucClient.getCommandResult();
        }
        return results;
    }

    private void wait(int timeInMs) {
        try {
            Thread.sleep(timeInMs);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
