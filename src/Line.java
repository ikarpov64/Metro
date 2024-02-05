import java.util.List;

public class Line {
    private final Color color;
    private final Metro metro;
    private List<Station> stations;

    public Line(Color color, Metro metro) {
        this.color = color;
        this.metro = metro;

    }
}
