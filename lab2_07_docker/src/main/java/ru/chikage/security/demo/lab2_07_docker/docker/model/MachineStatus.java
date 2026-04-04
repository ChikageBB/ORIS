package ru.chikage.security.demo.lab2_07_docker.docker.model;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class MachineStatus {
    private Double temp1;
    private Double temp2;
    private Double temp3;
    private Double pressure;
    private Double resource;
}
