package ru.itis.dis403.lab10.star;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.net.InetAddress;

public class ClientData {
    private Integer id;
    private String name;

    @JsonIgnore
    private InetAddress inetAddress;
    @JsonIgnore
    private int clientPort;


    public ClientData(Integer id, String name, InetAddress inetAddress, int clientPort) {
        this.id = id;
        this.name = name;
        this.inetAddress = inetAddress;
        this.clientPort = clientPort;
    }

    public String getClientId() {
        return name;
    }

    public void setClientId(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }

    public void setInetAddress(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }

    public int getClientPort() {
        return clientPort;
    }

    public void setClientPort(int clientPort) {
        this.clientPort = clientPort;
    }
}
