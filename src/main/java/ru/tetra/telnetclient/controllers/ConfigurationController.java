package ru.tetra.telnetclient.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.tetra.telnetclient.services.ConfigurationService;
import ru.tetra.telnetclient.utils.parser.tetraComponents.BaseStation;
import ru.tetra.telnetclient.utils.parser.tetraComponents.Configuration;
import ru.tetra.telnetclient.utils.parser.tetraComponents.Network;
import ru.tetra.telnetclient.utils.parser.tetraComponents.software.Puc;

import java.util.List;

@RestController
@RequestMapping("/config")
public class ConfigurationController {

    private final ConfigurationService configurationService;

    public ConfigurationController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void importConfig(@RequestParam("file") MultipartFile file) {
        configurationService.importConfiguration(file);
    }

    @GetMapping("/network")
    public ResponseEntity<Network> getNetwork() {
        return ResponseEntity.ok(configurationService.getNetwork());
    }

    @GetMapping("/info")
    public ResponseEntity<Configuration> getConfiguration() {
        return ResponseEntity.ok(configurationService.getTetraNetworkConfiguration());
    }

    @GetMapping("/allBs")
    public ResponseEntity<List<BaseStation>> getAllBaseStations() {
        return ResponseEntity.ok(configurationService.getAllBaseStations());
    }

    @GetMapping("/allPucs")
    public ResponseEntity<List<Puc>> getAllPucs() {
        return ResponseEntity.ok(configurationService.getAllPucs());
    }

}
