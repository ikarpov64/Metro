import java.time.Duration;
import java.util.List;

public class Station {
    private final String name;
    private Station previsionStation;
    private Station nextStation;
    private Duration timeToNextStation;
    private Line line;
    private List<Station> availableTransferStations;
    private Metro metro;


    public Station(String name, Station previsionStation,
                   Station nextStation, Duration timeToNextStation,
                   Line line, List<Station> availableTransferStations, Metro metro) {
        this.name = name;
        this.previsionStation = previsionStation;
        this.nextStation = nextStation;
        this.timeToNextStation = timeToNextStation;
        this.line = line;
        this.availableTransferStations = availableTransferStations;
        this.metro = metro;
    }
}
