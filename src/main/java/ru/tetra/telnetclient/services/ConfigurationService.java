package ru.tetra.telnetclient.services;

import org.springframework.stereotype.Service;
import ru.tetra.telnetclient.utils.parser.NetworkConfigParser;
import ru.tetra.telnetclient.utils.parser.tetraComponents.Configuration;
import ru.tetra.telnetclient.utils.parser.tetraComponents.Network;

import java.io.File;

@Service
public class ConfigurationService {

    private Configuration tetraNetworkConfiguration = new Configuration("8.125", "7.0",
            "1.3");

    public void importConfiguration() {
        NetworkConfigParser parser = new NetworkConfigParser(new File("svl.xml"));
        parser.parseConfiguration();
        tetraNetworkConfiguration = parser.getConfigNetwork();
    }
    public Configuration getTetraNetworkConfiguration() {
        return tetraNetworkConfiguration;
    }

    public Network getNetwork() {
        return tetraNetworkConfiguration.getNetwork();
    }
}
