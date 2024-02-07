import java.time.Duration;
import java.util.*;
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

    /**
     * Создание первой станции на линии метро
     * @param name Название станции.
     * @param color Цвет линии метро.
     * @param transferStations Список станций на пересадку.
     */
    public void createFirstStationInLine(Color color,
                                         String name,
                                         List<Station> transferStations) {
        if (!isLineExists(color)) {
            System.out.println("Line not exist. Build a subway line first");
            return;
        }

        if (!lineIsEmpty(color)) {
            System.out.println("Line is not empty, cannot create first station in Line");
            return;
        }

        if (isStationExists(name)) {
            System.out.println("Station is already exist.");
        } else {
            Line currentLine = getLine(color);
            currentLine.getStations().add(new Station(name, currentLine,this));
        }
    }

    /**
     * Создание первой станции на линии метро
     * @param name Название станции.
     * @param color Цвет линии метро.
     * @param transferStations Список станций на пересадку.
     */
    public void createLastStationInLine(Color color,
                                        String name,
                                        Duration duration,
                                        List<Station> transferStations) {

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
     * Определение станции пересадки с одной линии на другую.
     * @param startLine линия с которой осуществляется пересадка.
     * @param endLine линия на которую осуществляется пересадка.
     * @return Станция на линии startLine с которой можно осуществить пересадку.
     */
    private Station getTransferStation(Line startLine, Line endLine) {
        return startLine.getStations()
                .stream()
                .flatMap(station -> station.getTransferStations().stream())
                .filter(station -> endLine.equals(station.getLine()))
                .findFirst().orElseThrow();
    }

    /**
     * Считаем количество перегонов между двумя станциями на одной линии.
     * Вначале считаем в прямом направлении, затем в обратном.
     * @param startStation Начальная станция, с которой движемся
     * @param endStation Конечная станция, до которой хотим доехать.
     * @return RuntimeException если нет перегона между станциями.
     */
    public int numberOfRunsBetweenStations(Station startStation, Station endStation) {
        int numberOfRuns = calculateNumberOfRunsForwardDirection(startStation, endStation);
        if (numberOfRuns != -1) {
            return numberOfRuns;
        }
        numberOfRuns = calculateNumberOfRunsBackwardsDirection(startStation, endStation);
        if (numberOfRuns != -1) {
            return numberOfRuns;
        }
        throw new RuntimeException("Нет пути из станции "
                    + startStation.getName()
                    + " в станцию "
                    + endStation.getName());
    }

    private int calculateNumberOfRunsForwardDirection(Station startStation, Station endStation) {
        int numberOfRuns = 0;
        Station currentStation = startStation;
        while (currentStation != null && !currentStation.equals(endStation)) {
            currentStation = currentStation.getNextStation();
            numberOfRuns++;
        }
        return currentStation != null ? numberOfRuns : -1;
    }

    private int calculateNumberOfRunsBackwardsDirection(Station startStation, Station endStation) {
        int numberOfRuns = 0;
        Station currentStation = startStation;
        while (currentStation != null && !currentStation.equals(endStation)) {
            currentStation = currentStation.getPrevStation();
            numberOfRuns++;
        }
        return currentStation != null ? numberOfRuns : -1;
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

    public Set<Line> getLines() {
        return lines;
    }

    @Override
    public String toString() {
        return "Metro{" +
                "city='" + city + '\'' +
                ", lines=" + lines +
                '}';
    }
}
