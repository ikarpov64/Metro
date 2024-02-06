import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Metro {
    private final String city = "Пермь";
    private Set<Line> lines = new HashSet<>();

    public void createNewLine(Color color) {
        if (!isLineExists(color)) {
            lines.add(new Line(color, this));
        } else {
            System.out.println("Такая линия уже существует. " + color.color);
        }
    }


    public void createFirstStationInLine(Color color,
                                         String name,
                                         Duration duration,
                                         List<Station> changeStations) {
        if (!isLineExists(color)) {
            System.out.println("Line not exist. Build a subway line first");
            // Тут нужно эксепшн так как линия не создана.
        }

        if (!lineIsEmpty(color)) {
            System.out.println("Line is not empty, cannot create first station in Line");
            // Тут эксепшн потому что первая линия уже имеется.
        }

        if (isStationExists(name)) {
            System.out.println("Station is already exist.");
            // Тут эксепшн так как станция в метро с таким именем уже имеется.
        } else {
            lines.stream()
                    .filter(line -> line.getColor() == color) // Фильтруем по красной линии
                    .findFirst()                              // Берем первую попавшуюся красную линию
                    .ifPresent(line -> line.getStations()
                            .add(new Station(name, line,this))); // Добавляем станцию в линию RED
        }
    }

    private boolean lineIsEmpty (Color color) {
        return lines.stream()
                .filter(line -> line.getColor() == color)
                .anyMatch(line -> line.getStations().isEmpty());
    }

    private boolean isStationExists(String name) {
        return lines.stream()                                          // Получаем поток линий
                .flatMap(line -> line.getStations().stream())          // Преобразуем каждую линию в поток её станций
                .anyMatch(station -> station.getName().equals(name));  // Проверяем, есть ли среди станций станция name
    }

    private boolean isLineExists(Color color) {
        return lines.stream().anyMatch(line -> line.getColor() == color);
    }

    public void createLastStationInLine(Color color,
                                        String name,
                                        Duration duration,
                                        List<Station> transferList) {

    }

    @Override
    public String toString() {
        return "Metro{" +
                "city='" + city + '\'' +
                ", lines=" + lines +
                '}';
    }
}
