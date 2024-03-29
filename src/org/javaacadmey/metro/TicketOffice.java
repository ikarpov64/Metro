package org.javaacadmey.metro;

import org.javaacadmey.metro.Exception.SellTicketException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

public class TicketOffice {
    private final BigDecimal TAX = BigDecimal.valueOf(20);
    private final BigDecimal COST_OF_ONE_RUN = BigDecimal.valueOf(5);
    private final BigDecimal COST_OF_MONTHLY_TICKET = BigDecimal.valueOf(3000);
    private static final int MONTHLY_TICKET_VALIDITY_PERIOD_IN_DAYS = 30;
    private HashMap<LocalDate, BigDecimal> income = new HashMap<>();

    /**
     * Продажа одноразового билета.
     * @param station Станция продажи билета.
     * @param startStation Станция отправления.
     * @param endStation Станция прибывания.
     * @param date Дата продажи билета.
     * @exception SellTicketException Если условия продажи не соблюдены.
     */
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
        updateIncome(date, ticketPrice);
    }

    /**
     * Продажа билета на месяц.
     * @param station Станция продажи билета.
     * @param date Дата продажи билета.
     */
    public void sellMonthlyTicket(Station station, LocalDate date) {
        Metro metro = station.getMetro();
        String ticketNumber = metro.generateTicketNumber();
        metro.addSubscribers(ticketNumber, date.plusDays(MONTHLY_TICKET_VALIDITY_PERIOD_IN_DAYS));
        updateIncome(date, COST_OF_MONTHLY_TICKET);
    }

    /**
     * Продление месячного билета (абонемента).
     * @param station Станция продажи/продления абонемента.
     * @param ticketNumber Номер билета для продления.
     * @param date Дата продления абонемента. Срок абонемента увеличивается с даты продления + 30 дней.
     * @exception RuntimeException если билета не существует.
     */
    public void subscriptionRenewal(Station station, String ticketNumber, LocalDate date) {
        Metro metro = station.getMetro();
        if (metro.getSubscribers().containsKey(ticketNumber)) {
            metro.addSubscribers(ticketNumber, date.plusDays(MONTHLY_TICKET_VALIDITY_PERIOD_IN_DAYS));
            updateIncome(date, COST_OF_MONTHLY_TICKET);
        } else {
            throw new RuntimeException("Нельзя продлить абонемент, номера билета не существует.");
        }
    }

    /**
     * Обновление доходов.
     * @param date Дата новой продажи.
     * @param value Сумма продажи.
     */
    private void updateIncome(LocalDate date, BigDecimal value) {
        if (income.containsKey(date)) {
            BigDecimal incomeByDate = income.get(date);
            income.put(date, incomeByDate.add(value));
        } else {
            income.put(date, value);
        }
    }

    public HashMap<LocalDate, BigDecimal> getIncome() {
        return income;
    }

    @Override
    public String toString() {
        return "TicketOffice{" +
                "income=" + income +
                '}';
    }
}
