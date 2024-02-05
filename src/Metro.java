import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Metro {
    private final String cityName = "Пермь";
    private Set<Line> lines;

    public void createNewLine(Color color) {
        lines.add(new Line(color, this));
    }

    public void createFirstStationInLine(Color color,
                                         String name,
                                         Duration duration,
                                         List<Station> transferList) {

//        lines.stream().filter(line -> line.getColor() == color)


    }

    public void createLastStationInLine(Color color,
                                        String name,
                                        Duration duration,
                                        List<Station> transferList) {

    }
}
