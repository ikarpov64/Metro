import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedList;
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
        metro.createFirstStationInLine(Color.RED, "Спортивная", null);
        System.out.println(metro); // Вывод: Metro{city='Пермь', lines=[Line{color=RED, stations=[Station{name='Спортивная', changeLines=RED}]}, Line{color=BLUE, stations=[]}]}
        System.out.println("____________________________________________________");

        // Создаем еще одну первую станцию на первой ветке
        metro.createFirstStationInLine(Color.RED, "Спортивная1", null);
        System.out.println(metro); // Вывод: Line is not empty, cannot create first station in Line
        System.out.println("____________________________________________________");

        // Создаем станцию на синей ветке с существующим именем.
        metro.createFirstStationInLine(Color.BLUE, "СпортивнаяБлу", null);
        System.out.println(metro);
        // Station is already exist.
        // Metro{city='Пермь', lines=[Line{color=RED, stations=[Station{name='Спортивная', changeLines=RED}, Station{name='Спортивная1', changeLines=RED}]}, Line{color=BLUE, stations=[]}]}
        System.out.println("____________________________________________________");

        metro.createLastStationInLine(Color.RED, "Спортивная1", Duration.ofSeconds(150), null);
        System.out.println(metro);
        System.out.println("____________________________________________________");



        metro.createLastStationInLine(Color.RED, "Спортивная2", Duration.ofSeconds(150), null);
        metro.createLastStationInLine(Color.RED, "Спортивная3", Duration.ofSeconds(150), null);
        metro.createLastStationInLine(Color.RED, "Спортивная4", Duration.ofSeconds(150), null);
        System.out.println(metro);
        System.out.println("____________________________________________________");

        // Подсчет количества перегонов на одной линии.
        Station first = metro.getLines().stream().filter(line -> Color.RED.equals(line.getColor())).findFirst().get().getStations().getFirst();
        Station last = metro.getLines().stream().filter(line -> Color.RED.equals(line.getColor())).findFirst().get().getStations().getLast();
        Station blue = metro.getLines().stream().filter(line -> Color.BLUE.equals(line.getColor())).findFirst().get().getStations().getLast();
        System.out.println(metro.numberOfRunsBetweenStationsWithinLine(first, last));

        first.setTransferStations(new ArrayList<>(List.of(blue)));
        blue.setTransferStations(new ArrayList<>(List.of(first)));

        // Поиск станции пересадки на линиях

        Line lineRed = metro.getLines().stream().filter(l -> Color.RED.equals(l.getColor())).findFirst().orElseThrow();
        Line lineBlue = metro.getLines().stream().filter(l -> Color.BLUE.equals(l.getColor())).findFirst().orElseThrow();
        System.out.println(metro);

        System.out.println(metro.getTransferStation(lineRed, lineBlue));
        System.out.println(metro.getTransferStation(lineBlue, lineRed));




    }
}