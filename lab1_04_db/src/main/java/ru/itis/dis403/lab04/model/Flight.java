package ru.itis.dis403.lab04.model;

import java.time.LocalDateTime;
import java.util.Date;

public class Flight {
    private String flightId;
    private Date scheduledDeparture;
    private Date scheduledArrival;
    private String status;
    private String departureAirport;
    private String arrivalAirport;


    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public Date getScheduledDeparture() {
        return scheduledDeparture;
    }

    public void setScheduledDeparture(Date scheduledDeparture) {
        this.scheduledDeparture = scheduledDeparture;
    }

    public Date getScheduledArrival() {
        return scheduledArrival;
    }

    public void setScheduledArrival(Date scheduledArrival) {
        this.scheduledArrival = scheduledArrival;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public Flight(String flightId, Date scheduledDeparture, Date scheduledArrival, String status, String departureAirport, String arrivalAirport) {
        this.flightId = flightId;
        this.scheduledDeparture = scheduledDeparture;
        this.scheduledArrival = scheduledArrival;
        this.status = status;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
    }
}
