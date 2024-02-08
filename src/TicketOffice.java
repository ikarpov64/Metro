import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

public class TicketOffice {
    private final BigDecimal TAX = BigDecimal.valueOf(20);
    private final BigDecimal COST_OF_ONE_RUN = BigDecimal.valueOf(5);
    private HashMap<LocalDate, BigDecimal> income = new HashMap<>();

    public void sellTicket(Station station, String startStation, String endStation, LocalDate date) {

        int numberOfRuns = station.getMetro()
                .numberOfRunsBetweenStationsOutsideLines(startStation, endStation);

        BigDecimal ticketPrice = BigDecimal.valueOf(numberOfRuns).multiply(COST_OF_ONE_RUN).add(TAX);

        if (income.containsKey(date)) {
            BigDecimal incomeByDate = income.get(date);
            income.put(date, incomeByDate.add(ticketPrice));
        } else {
            income.put(date, ticketPrice);
        }

    }
}
