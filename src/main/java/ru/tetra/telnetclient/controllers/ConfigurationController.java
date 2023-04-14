package ru.tetra.telnetclient.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tetra.telnetclient.services.ConfigurationService;
import ru.tetra.telnetclient.utils.parser.tetraComponents.Network;

@RestController
@RequestMapping("/config")
public class ConfigurationController {

    private final ConfigurationService configurationService;

    public ConfigurationController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @GetMapping("/import")
    public void importConfig() {
        configurationService.importConfiguration();
    }

    @GetMapping("/info")
    public ResponseEntity<Network> getConfig() {
        return ResponseEntity.ok(configurationService.getNetwork());
    }
}
