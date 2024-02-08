package org.javaacadmey.metro;

import org.javaacadmey.metro.Exception.SellTicketException;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Station {
    private final String name;
    private final Line line;
    private final Metro metro;
    public final TicketOffice ticketOffice = new TicketOffice();
    private Station prevStation;
    private Station nextStation;
    private Duration timeToNextStation;
    private List<Station> transferStations = new ArrayList<>();


    public Station(String name, Station prevStation,
                   Station nextStation, Duration timeToNextStation,
                   Line line, ArrayList<Station> transferStations, Metro metro) {
        this.name = name;
        this.prevStation = prevStation;
        this.nextStation = nextStation;
        this.timeToNextStation = timeToNextStation;
        this.line = line;
        this.transferStations = transferStations;
        this.metro = metro;
    }

    public Station(String name, Line line, Metro metro) {
        this.name = name;
        this.line = line;
        this.metro = metro;
    }

    public String getName() {
        return name;
    }

    public Station getPrevStation() {
        return prevStation;
    }

    public Station getNextStation() {
        return nextStation;
    }

    public Duration getTimeToNextStation() {
        return timeToNextStation;
    }

    public Line getLine() {
        return line;
    }

    public List<Station> getTransferStations() {
        return transferStations;
    }

    public void setTransferStations(List<Station> transferStations) {
        this.transferStations = transferStations;
    }

    public void sellTicket(String startStation, String endStation) throws SellTicketException {
        ticketOffice.sellTicket(this, startStation, endStation, LocalDate.now());
    }

    public void sellMonthlyTicket() {
        ticketOffice.sellMonthlyTicket(this, LocalDate.now());
    }

    public void subscriptionRenewal(String ticketNumber, LocalDate date) {
        ticketOffice.subscriptionRenewal(this, ticketNumber, date);
    }

    public Metro getMetro() {
        return metro;
    }

    public void setTimeToNextStation(Duration timeToNextStation) {
        this.timeToNextStation = timeToNextStation;
    }

    public void setPrevStation(Station prevStation) {
        this.prevStation = prevStation;
    }

    public void setNextStation(Station nextStation) {
        this.nextStation = nextStation;
    }

    public TicketOffice getTicketOffice() {
        return ticketOffice;
    }

    @Override
    public String toString() {
        return "Station{" +
                "name='" + name + '\'' +
                ", changeLines="
                + transferStations.stream()
                .map(Station::getLine)
                .map(Line::getColor)
                .map(color -> color.color)
                .reduce(String::concat)
                .orElse(null) +
                '}';
    }
}
