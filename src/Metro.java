import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Metro {
    private final String cityName = "Пермь";
    private List<Line> lines = new ArrayList<>();

    public void createNewLine(Color color) {
        lines.add(new Line(color, this));
    }

    public void createFirstStationInLine(Color color,
                                         String name,
                                         Duration duration,
                                         Line changeLines) {
        if (lineNotExist(color)) {
            System.out.println("Line not available");
        }
        if (!stationNotExist(name)) {
            System.out.println("Station is already exist.");
        }

        if (!lineIsEmpty(color)) {
            System.out.println("Line is not empty, cannot create first station in Line");
        }

    }

    private boolean lineIsEmpty(Color color) {
        return lines == null
                || lines.stream()
                .filter(line -> line.getColor() == color)
                .map(Line::getStations)
                .findAny().isEmpty();
    }

    private boolean stationNotExist(String name) {
        return lines == null || lines.stream()
                .peek(line -> line.getStations()
                        .stream()
                        .peek(station -> station.getName().equals(name))
                        .findAny()
                        .isPresent())
                .isParallel();
    }

    private boolean lineNotExist(Color color) {
        return lines == null
                || lines.stream().noneMatch(line -> line.getColor() == color);
    }

    public void createLastStationInLine(Color color,
                                        String name,
                                        Duration duration,
                                        List<Station> transferList) {

    }
}
