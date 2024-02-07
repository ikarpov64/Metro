import java.time.Duration;
import java.util.*;

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
                                         List<Station> changeStations) {
        if (!isLineExists(color)) {
            System.out.println("Line not exist. Build a subway line first");
            return;
            // Тут нужно эксепшн так как линия не создана.
        }

        if (!lineIsEmpty(color)) {
            System.out.println("Line is not empty, cannot create first station in Line");
            return;
            // Тут эксепшн потому что первая линия уже имеется.
        }

        if (isStationExists(name)) {
            System.out.println("Station is already exist.");
            // Тут эксепшн так как станция в метро с таким именем уже имеется.
        } else {
            createStationInLine(color, name);
        }
    }

    public void createLastStationInLine(Color color,
                                        String name,
                                        Duration duration,
                                        List<Station> transferList) {

        if (!isLineExists(color)) {
            System.out.println("Line not exist. Build a subway line first");
            return;
            // Тут нужно эксепшн так как линия не создана.
        }

        if (isStationExists(name)) {
            System.out.println("Station is already exist.");
            return;
            // Тут эксепшн так как станция в метро с таким именем уже имеется.
        }

        if (duration.getSeconds() == 0) {
            System.out.println("Transit time cannot be zero");
            return;
        }

        if (lineIsEmpty(color)) {
            System.out.println("The first station does not exist, create the first station in the line first");
            return;
        }

        Line currentLine = getLine(color);
        LinkedList<Station> stations = currentLine.getStations();
        Station lastStation = stations.getLast();
        Station currentStation = new Station(name, currentLine, this);
        currentStation.setPrevStation(lastStation);
        lastStation.setTimeToNextStation(duration);
        lastStation.setNextStation(currentStation);
        stations.add(currentStation);
    }


    private void createStationInLine(Color color, String name) {
        lines.stream()
                .filter(line -> color.equals(line.getColor()))  // Фильтруем по цвету линии
                .findFirst()                                    // Берем первую попавшуюся линию по цвету
                .ifPresent(line -> line.getStations()
                        .add(new Station(name, line,this))); // Добавляем станцию в линию по цвету
    }

    private Line getLine(Color color) {
        return lines.stream()                                   // Получаем поток линий
                .filter(line -> color.equals(line.getColor()))  // Фильтруем по цвету линии
                .findFirst()                                    // Берем первую попавшуюся линию по цвету
                .orElseThrow();                                 // Возвращаем найденную линию
    }

    private Station isPrevisionStationExist(Color color) {
//        if (!lineIsEmpty(color)) {
            Optional<Line> line = lines.stream()
                    .filter(l -> l.getColor() == color)
                    .findFirst();

            return line.map(Line::getStations)
                    .flatMap(stations -> stations.isEmpty() ? Optional.empty() : Optional.of(stations.getLast()))
                    .orElse(null);

//            Station station = lines.stream().filter(line -> line.getColor() == color)
//                    .findFirst().get().getStations().getLast();
//        }
//        return null;
    }

    /**
     * Проверка существования линии по цвету.
     */
    private boolean isLineExists(Color color) {
        return lines.stream()                                        // Получаем поток линий
                .anyMatch(line -> color.equals(line.getColor()));    // Проверяем существует ли линия с цветом
    }

    /**
     * Проверка на наличие станций в линии.
     */
    private boolean lineIsEmpty (Color color) {
        return lines.stream()                                       // Получаем поток линий
                .filter(line -> color.equals(line.getColor()))      // Фильтруем по цвету линии
                .anyMatch(line -> line.getStations().isEmpty());    // Проверяем список станций в линии на пустоту
    }

    /**
     * Проверка на существование станции по имени.
     */
    private boolean isStationExists(String name) {
        return lines.stream()                                          // Получаем поток линий
                .flatMap(line -> line.getStations().stream())          // Преобразуем каждую линию в поток её станций
                .anyMatch(station -> name.equals(station.getName()));  // Проверяем существование станции по имени
    }

    @Override
    public String toString() {
        return "Metro{" +
                "city='" + city + '\'' +
                ", lines=" + lines +
                '}';
    }
}
