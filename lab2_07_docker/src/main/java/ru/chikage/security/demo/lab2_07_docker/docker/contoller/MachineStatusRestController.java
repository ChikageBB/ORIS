package ru.chikage.security.demo.lab2_07_docker.docker.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.chikage.security.demo.lab2_07_docker.docker.model.MachineStatus;
import ru.chikage.security.demo.lab2_07_docker.docker.service.MachineStatusService;

@RestController
@RequiredArgsConstructor
public class MachineStatusRestController {

    private final MachineStatusService service;

    @GetMapping("/api/status/{id}")
    public ResponseEntity<MachineStatus> getMachineStatus(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(service.getStatus(id));
    }

    @PostMapping("/api/resource")
    public ResponseEntity<String> setResource(@RequestParam("resource") Double resource, @RequestParam("id") Integer id) {
        return ResponseEntity.ok("{\"status\":\"success\"}");
    }
}
