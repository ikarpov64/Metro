import java.util.Set;

public class Metro {
    private final String cityName = "Пермь";
    private Set<Line> lines;

    public void createNewLine(Color color) {
        lines.add(new Line(color, this));
    }
}
