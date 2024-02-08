package org.javaacadmey.metro;

import org.javaacadmey.metro.Exception.StationNotExistException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

public class TicketOffice {
    private final BigDecimal TAX = BigDecimal.valueOf(20);
    private final BigDecimal COST_OF_ONE_RUN = BigDecimal.valueOf(5);
    private HashMap<LocalDate, BigDecimal> income = new HashMap<>();

    public void sellTicket(Station station, String startStation, String endStation, LocalDate date)
            throws StationNotExistException {
        Metro metro = station.getMetro();

        if (metro == null) {
            System.out.println("Метро не существует");
            throw new StationNotExistException("dfd");
        }

        if (!metro.isStationExists(startStation) || !metro.isStationExists(endStation)) {
            System.out.println("Ошибка: Станции не существует. Билет не продан.");
            return;
        }

        Station finalStation = metro.getStationByName(endStation);
        Station startingStation = metro.getStationByName(startStation);
        if (startingStation.equals(finalStation)) {
            System.out.println("Ошибка: Станция начала равна конечной станции.");
            return;
        }

        int numberOfRuns = metro
                .numberOfRunsBetweenStationsOutsideLines(startingStation, finalStation);

        if (numberOfRuns == 0) {
            System.out.println("Нельзя продать билет без перегонов.");
            return;
        }

        BigDecimal ticketPrice = BigDecimal.valueOf(numberOfRuns).multiply(COST_OF_ONE_RUN).add(TAX);

        if (income.containsKey(date)) {
            BigDecimal incomeByDate = income.get(date);
            income.put(date, incomeByDate.add(ticketPrice));
        } else {
            income.put(date, ticketPrice);
        }
    }

    @Override
    public String toString() {
        return "TicketOffice{" +
                "income=" + income +
                '}';
    }
}
