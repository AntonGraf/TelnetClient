package ru.tetra.telnetclient.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.tetra.telnetclient.services.PucService;
import ru.tetra.telnetclient.utils.telnet.PucClient;

import java.util.List;

@RestController
public class PucController {

    private PucService pucService;

    public PucController(PucService pucService) {
        this.pucService = pucService;
    }


    @GetMapping("/sendCommand")
    public void sendCommand(@RequestParam(name = "ip") String ip, @RequestParam(name = "port") int port,
                            @RequestParam(name = "command") String command) {
        pucService.sendCommand(ip, port, command);
    }

    @GetMapping("/disconnect")
    public void disconnect(@RequestParam(name = "ip") String ip, @RequestParam(name = "port") int port) {
        pucService.disconnect(ip, port);
    }

    @GetMapping("/disconnectAll")
    public void disconnectAll() {
        pucService.disconnectAll();
    }

    @GetMapping("/getAllPucs")
    public ResponseEntity<List<PucClient>> getAllPucs() {
        return ResponseEntity.ok(pucService.getPucClients());
    }

    @GetMapping("/geAlarms")
    public ResponseEntity<List<String>> getPucAlarms(@RequestParam(name = "ip") String ip) {
        return ResponseEntity.ok(pucService.getPucAlarms(ip));
    }

    @PostMapping(value = "/importPuc", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Integer> importPuc(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(pucService.createPucClientFromFile(file));
    }
}
