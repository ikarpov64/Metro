import java.time.Duration;

public class Runner {
    public static void main(String[] args) {

        // Создаем пустое метро
        Metro metro = new Metro();
        System.out.println(metro); // Вывод: Metro{city='Пермь', lines=[]}
        System.out.println("____________________________________________________");

        // Пытаемся создать станцию без линий
        metro.createFirstStationInLine(Color.RED, "Спортивная", Duration.ZERO, null); // Вывод: Line not exist
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
        metro.createFirstStationInLine(Color.RED, "Спортивная", Duration.ZERO, null);
        System.out.println(metro); // Вывод: Metro{city='Пермь', lines=[Line{color=RED, stations=[Station{name='Спортивная', changeLines=RED}]}, Line{color=BLUE, stations=[]}]}
        System.out.println("____________________________________________________");

        // Создаем еще одну первую станцию на первой ветке
        metro.createFirstStationInLine(Color.RED, "Спортивная1", Duration.ZERO, null);
        System.out.println(metro); // Вывод: Line is not empty, cannot create first station in Line
        System.out.println("____________________________________________________");

        // Создаем станцию на синей ветке с существующим именем.
        metro.createFirstStationInLine(Color.BLUE, "Спортивная", Duration.ZERO, null);
        System.out.println(metro);
        // Station is already exist.
        // Metro{city='Пермь', lines=[Line{color=RED, stations=[Station{name='Спортивная', changeLines=RED}, Station{name='Спортивная1', changeLines=RED}]}, Line{color=BLUE, stations=[]}]}

    }
}