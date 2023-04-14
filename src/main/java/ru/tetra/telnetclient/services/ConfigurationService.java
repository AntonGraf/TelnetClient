package ru.tetra.telnetclient.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.tetra.telnetclient.utils.parser.NetworkConfigParser;
import ru.tetra.telnetclient.utils.parser.enums.NetworkElementType;
import ru.tetra.telnetclient.utils.parser.tetraComponents.BaseStation;
import ru.tetra.telnetclient.utils.parser.tetraComponents.Configuration;
import ru.tetra.telnetclient.utils.parser.tetraComponents.Network;
import ru.tetra.telnetclient.utils.parser.tetraComponents.software.Puc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConfigurationService {

    private Configuration tetraNetworkConfiguration = new Configuration("8.125", "7.0",
            "1.3");

    public void importConfiguration(MultipartFile multipartFile) {
        NetworkConfigParser parser = null;
        String tempFilePath = "targetFile.tmp";
        Path path = Paths.get(tempFilePath);

        try {
            Files.write(path, multipartFile.getBytes());
            File file = new File(tempFilePath);
            parser = new NetworkConfigParser(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        parser.parseConfiguration();
        tetraNetworkConfiguration = parser.getConfigNetwork();

        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Configuration getTetraNetworkConfiguration() {
        return tetraNetworkConfiguration;
    }

    public Network getNetwork() {
        return tetraNetworkConfiguration.getNetwork();
    }

    public List<Puc> getAllPucs() {
        List<Puc> allPucs = new ArrayList<>();
        for (BaseStation baseStation: getAllBaseStations()) {
            allPucs.addAll(baseStation.getAllComponents()
                    .stream()
                    .filter(component -> component.getNcType().equals("PUC"))
                    .map(component -> (Puc) component)
                    .collect(Collectors.toList()));
        }
        return allPucs;
    }

    public List<BaseStation> getAllBaseStations() {
        List<BaseStation> baseStations = tetraNetworkConfiguration.getNetwork().getTetraComponents()
                .stream()
                .filter(tetraComponent -> tetraComponent.getElementType().equals(NetworkElementType.BS))
                .map(tetraComponent -> (BaseStation) tetraComponent)
                .collect(Collectors.toList());
        return baseStations;
    }
}
