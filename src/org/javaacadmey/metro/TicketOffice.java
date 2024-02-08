package org.javaacadmey.metro;

import org.javaacadmey.metro.Exception.SellTicketException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class TicketOffice {
    private final BigDecimal TAX = BigDecimal.valueOf(20);
    private final BigDecimal COST_OF_ONE_RUN = BigDecimal.valueOf(5);
    private HashMap<LocalDate, BigDecimal> income = new HashMap<>();

    public void sellTicket(Station station, String startStation, String endStation, LocalDate date)
            throws SellTicketException {

        Metro metro = station.getMetro();

        if (!metro.isStationExists(startStation)) {
            throw new SellTicketException(String.format("Станции %s не существует.", startStation));
        }
        if (!metro.isStationExists(endStation)) {
            throw new SellTicketException(String.format("Станции %s не существует.", endStation));
        }

        if (metro.getStationByName(startStation).equals(metro.getStationByName(endStation))) {
            throw new SellTicketException("Начальная станция равна конечной.");
        }

        Station startingStation = station.getMetro().getStationByName(startStation);
        Station finalStation = station.getMetro().getStationByName(endStation);
        int numberOfRuns = station.getMetro()
                .numberOfRunsBetweenStationsOutsideLines(startingStation, finalStation);

        if (numberOfRuns == 0) {
            throw new SellTicketException("Между этими станциями движения нет.");
        }

        BigDecimal ticketPrice = BigDecimal.valueOf(numberOfRuns).multiply(COST_OF_ONE_RUN).add(TAX);

        if (income.containsKey(date)) {
            BigDecimal incomeByDate = income.get(date);
            income.put(date, incomeByDate.add(ticketPrice));
        } else {
            income.put(date, ticketPrice);
        }

    }

    private void checkStations(Station station, String startStation, String endStation)
            throws SellTicketException {


    }


    @Override
    public String toString() {
        return "TicketOffice{" +
                "income=" + income +
                '}';
    }
}
