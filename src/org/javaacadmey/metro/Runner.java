package org.javaacadmey.metro;

import org.javaacadmey.metro.Exception.StationNotExistException;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Runner {
    public static void main(String[] args) {

        // Создаем пустое метро
        Metro metro = new Metro();
        System.out.println(metro); // Вывод: Metro{city='Пермь', lines=[]}
        System.out.println("____________________________________________________");

        // Пытаемся создать станцию без линий
        metro.createFirstStationInLine(Color.RED, "Спортивная", null); // Вывод: Line not exist
        System.out.println(metro); // Вывод: Metro{city='Пермь', lines=[]}
        System.out.println("____________________________________________________");

        // Создаем ветки метро, красную и синиюю.
        metro.createNewLine(Color.RED);
        System.out.println(metro); // Вывод: Metro{city='Пермь', lines=[Line{color=RED, stations=[]}]}
        metro.createNewLine(Color.RED); // Вывод: Такая линия уже существует. Красная
        metro.createNewLine(Color.BLUE);
        System.out.println(metro); // Metro{city='Пермь', lines=[Line{color=BLUE, stations=[]}, Line{color=RED, stations=[]}]}
        System.out.println("____________________________________________________");

        // Создаем первую станцию на красной ветке
        metro.createFirstStationInLine(Color.RED, "СпортивнаяRed1", null);
        System.out.println(metro); // Вывод: Metro{city='Пермь', lines=[Line{color=RED, stations=[Station{name='Спортивная', changeLines=RED}]}, Line{color=BLUE, stations=[]}]}
        System.out.println("____________________________________________________");

        // Создаем еще одну первую станцию на первой ветке
        metro.createFirstStationInLine(Color.RED, "СпортивнаяRed1", null);
        System.out.println(metro); // Вывод: Line is not empty, cannot create first station in Line
        System.out.println("____________________________________________________");

        // Создаем станцию на синей ветке с существующим именем.
        metro.createFirstStationInLine(Color.BLUE, "СпортивнаяBlue1", null);
        System.out.println(metro);
        // Station is already exist.
        // Metro{city='Пермь', lines=[Line{color=RED, stations=[Station{name='Спортивная', changeLines=RED}, Station{name='Спортивная1', changeLines=RED}]}, Line{color=BLUE, stations=[]}]}
        System.out.println("____________________________________________________");

        metro.createLastStationInLine(Color.RED, "СпортивнаяRed2", Duration.ofSeconds(150), null);
        metro.createLastStationInLine(Color.RED, "СпортивнаяRed3", Duration.ofSeconds(150), null);
        metro.createLastStationInLine(Color.RED, "СпортивнаяRed4", Duration.ofSeconds(150), null);
        metro.createLastStationInLine(Color.BLUE, "СпортивнаяBlue2", Duration.ofSeconds(150), null);
        metro.createLastStationInLine(Color.BLUE, "СпортивнаяBlue3", Duration.ofSeconds(150), null);
        metro.createLastStationInLine(Color.BLUE, "СпортивнаяBlue4", Duration.ofSeconds(150), null);
        System.out.println(metro);
        System.out.println("____________________________________________________");

        // Подсчет количества перегонов на одной линии.
        Station firstRed = metro.getStationByName("СпортивнаяRed1");
        Station secondRed = metro.getStationByName("СпортивнаяRed2");
        Station thirdRed = metro.getStationByName("СпортивнаяRed3");
        Station fourthRed = metro.getStationByName("СпортивнаяRed4");
        Station firstBlue = metro.getStationByName("СпортивнаяBlue1");
        Station secondBlue = metro.getStationByName("СпортивнаяBlue2");
        Station thirdBlue = metro.getStationByName("СпортивнаяBlue3");
        Station fourthBlue = metro.getStationByName("СпортивнаяBlue4");

//        System.out.println(metro.numberOfRunsBetweenStationsWithinLine(fourthRed, fourthBlue));

        firstRed.setTransferStations(new ArrayList<>(List.of(firstBlue)));
        firstBlue.setTransferStations(new ArrayList<>(List.of(firstRed)));

        // Поиск станции пересадки на линиях
        Line lineRed = metro.getLine(Color.RED);
        Line lineBlue = metro.getLine(Color.BLUE);
        System.out.println(metro);

        System.out.println(metro.getTransferStation(lineRed, lineBlue));
        System.out.println(metro.getTransferStation(lineBlue, lineRed));

        System.out.println("____________________________________________________");

        System.out.println(metro.numberOfRunsBetweenStationsOutsideLines(firstRed, firstBlue));
        System.out.println(metro.numberOfRunsBetweenStationsOutsideLines(fourthBlue, fourthRed));

        System.out.println("____________________________________________________");

        try {
            firstRed.sellTicket("СпортивнаяRed1", "СпортивнаяBlue1");
//        firstRed.sellTicket("СпортивнаяRed4", "СпортивнаяBlue4");
        } catch (StationNotExistException e) {
            System.out.println("Не удалоась продать билет");
        }
        System.out.println(firstRed.ticketOffice);



    }
}
