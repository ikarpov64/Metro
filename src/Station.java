import java.time.Duration;
import java.util.List;

public class Station {
    private final String name;
    private Station previsionStation;
    private Station nextStation;
    private Duration timeToNextStation;
    private Line line;
    private Line changeLines;
    private Metro metro;


    public Station(String name, Station previsionStation,
                   Station nextStation, Duration timeToNextStation,
                   Line line, Line changeLines, Metro metro) {
        this.name = name;
        this.previsionStation = previsionStation;
        this.nextStation = nextStation;
        this.timeToNextStation = timeToNextStation;
        this.line = line;
        this.changeLines = changeLines;
        this.metro = metro;
    }

    public String getName() {
        return name;
    }

    public Station getPrevisionStation() {
        return previsionStation;
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

    public Line getChangeLines() {
        return changeLines;
    }

    public Metro getMetro() {
        return metro;
    }
}
