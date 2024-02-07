import java.time.Duration;
import java.util.List;

public class Station {
    private final String name;
    private Station prevStation;
    private Station nextStation;
    private Duration timeToNextStation;
    private Line line;
    private Station changeStation;
    private Metro metro;


    public Station(String name, Station prevStation,
                   Station nextStation, Duration timeToNextStation,
                   Line line, Station changeStation, Metro metro) {
        this.name = name;
        this.prevStation = prevStation;
        this.nextStation = nextStation;
        this.timeToNextStation = timeToNextStation;
        this.line = line;
        this.changeStation = changeStation;
        this.metro = metro;
    }

    public Station(String name, Line line, Metro metro) {
        this.name = name;
        this.line = line;
        this.metro = metro;
    }

    public String getName() {
        return name;
    }

    public Station getPrevStation() {
        return prevStation;
    }

    public Station getNextStation() {
        return nextStation;
    }

    public Duration getTimeToNextStation() {
        return timeToNextStation;
    }

    public Line getLine() {
        return line;
    }

    public Station getChangeStation() {
        return changeStation;
    }

    public Metro getMetro() {
        return metro;
    }

    public void setTimeToNextStation(Duration timeToNextStation) {
        this.timeToNextStation = timeToNextStation;
    }

    public void setPrevStation(Station prevStation) {
        this.prevStation = prevStation;
    }

    public void setNextStation(Station nextStation) {
        this.nextStation = nextStation;
    }

    @Override
    public String toString() {
        return "Station{" +
                "name='" + name + '\'' +
                ", changeLines=" + line.getColor() +
                '}';
    }
}
