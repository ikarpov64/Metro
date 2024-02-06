import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Line {
    private final Color color;
    private final Metro metro;
    private LinkedList<Station> stations = new LinkedList<>();

    public Line(Color color, Metro metro) {
        this.color = color;
        this.metro = metro;

    }

    public Color getColor() {
        return color;
    }

    public Metro getMetro() {
        return metro;
    }

    public LinkedList<Station> getStations() {
        return stations;
    }

    @Override
    public String toString() {
        return "Line{" +
                "color=" + color +
                ", stations=" + stations +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return color == line.color && Objects.equals(metro, line.metro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, metro);
    }
}
