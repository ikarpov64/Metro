package org.javaacadmey.metro;

import org.javaacadmey.metro.Exception.SellTicketException;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import static org.javaacadmey.metro.Color.*;

public class Runner {
    public static void main(String[] args) {
        // 1. Этап
        // Создаем пустое метро
        Metro metro = new Metro();

        // Создаем ветки метро
        metro.createNewLine(RED);
        metro.createNewLine(BLUE);

        // Создаем станции на ветках метро
        // Начальные станции
        metro.createFirstStationInLine(RED, "Спортивная", null);
        metro.createFirstStationInLine(BLUE, "Пацанская", null);

        // Поочередно вплоть до конечной
        metro.createLastStationInLine(RED, "Медведковская", Duration.ofSeconds(141), null);
        metro.createLastStationInLine(RED, "Молодежная", Duration.ofSeconds(118), null);
        metro.createLastStationInLine(RED, "Пермь 1", Duration.ofSeconds(180), null);
        metro.createLastStationInLine(RED, "Пермь 2", Duration.ofSeconds(130), null);
        metro.createLastStationInLine(RED, "Дворец Культуры", Duration.ofSeconds(266), null);

        metro.createLastStationInLine(BLUE, "Улица Кирова", Duration.ofSeconds(90), null);
        metro.createLastStationInLine(BLUE, "Тяжмаш", Duration.ofSeconds(107), null);
        metro.createLastStationInLine(BLUE, "Нижнекамская", Duration.ofSeconds(199), null);
        metro.createLastStationInLine(BLUE, "Соборная", Duration.ofSeconds(108), null);

        // Устанавливаем станции пересадки
        metro.getStationByName("Пермь 1").setTransferStations(List.of(metro.getStationByName("Тяжмаш")));
        metro.getStationByName("Тяжмаш").setTransferStations(List.of(metro.getStationByName("Пермь 1")));

        System.out.println(metro);
        System.out.println("_______________________________________________");

        // Получаем станцию пересадки с одной линии на другую.
        Station stationRedToBlue = metro.getTransferStation(RED, BLUE);
        Station stationBlueToRed = metro.getTransferStation(BLUE, RED);
        System.out.println("Станция пересадки с красной линии на синюю: " + stationRedToBlue.getName());
        System.out.println("Станция пересадки с синей линии на красную: " + stationBlueToRed.getName());
        System.out.println("_______________________________________________");

        // Считаем количество перегонов.
        Station firstRed = metro.getStationByName("Спортивная");
        Station lastBlue = metro.getStationByName("Соборная");

        int numberOfRuns = metro.numberOfRunsBetweenStationsOutsideLines(firstRed, lastBlue);
        System.out.printf("Количество перегонов со станции %s на станцию %s: %s\n",
                firstRed.getName(),
                lastBlue.getName(),
                numberOfRuns);
        System.out.println("_______________________________________________");

        // Продаем билеты
        try {
            firstRed.sellTicket("Спортивная", "Соборная");
            firstRed.sellTicket("Спортивная", "Молодежная");
            lastBlue.sellTicket("Соборная", "Спортивная");
            lastBlue.sellTicket("Соборная", "Тяжмаш");
            lastBlue.sellTicket("Соборная", "Пермь 1");
            lastBlue.sellTicket("Соборная", "Дворец Культуры");
        } catch (SellTicketException exception) {
            System.out.println(exception.getMessage());
        }

        // Абонементы.
        firstRed.sellMonthlyTicket();
        firstRed.sellMonthlyTicket();
        firstRed.sellMonthlyTicket();
        lastBlue.sellMonthlyTicket();
        lastBlue.sellMonthlyTicket();
        lastBlue.sellMonthlyTicket();

        System.out.println("Проданные абонементы: " + metro.getSubscribers());

        // Проверяем абонемент на срок действия, через 35 дней он будет неактивным.
        boolean ticketExpiration = metro.checkMonthlyTicketExpirationDate(
                "a0005", LocalDate.now().plusDays(35));
        System.out.println(ticketExpiration);

        // Продлеваем абонемент через 40 дней
        firstRed.subscriptionRenewal("a0005", LocalDate.now().plusDays(40));
        System.out.println("Проданные абонементы: " + metro.getSubscribers());
        System.out.println("__________________________________________________");

        //Печатаем доходы со всех касс:
        metro.getTotalIncome();
    }
}
