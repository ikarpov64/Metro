import java.time.Duration;
import java.util.ArrayList;

public class Station {
    private final String name;
    private final Line line;
    private final Metro metro;
    private final TicketOffice ticketOffice = new TicketOffice();
    private Station prevStation;
    private Station nextStation;
    private Duration timeToNextStation;
    private ArrayList<Station> transferStations = new ArrayList<>();


    public Station(String name, Station prevStation,
                   Station nextStation, Duration timeToNextStation,
                   Line line, ArrayList<Station> transferStations, Metro metro) {
        this.name = name;
        this.prevStation = prevStation;
        this.nextStation = nextStation;
        this.timeToNextStation = timeToNextStation;
        this.line = line;
        this.transferStations = transferStations;
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

    public ArrayList<Station> getTransferStations() {
        return transferStations;
    }

    public void setTransferStations(ArrayList<Station> transferStations) {
        this.transferStations = transferStations;
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
                ", changeLines="
                + transferStations.stream()
                .map(Station::getName)
                .reduce((name1, name2) -> name1 + ", " + name2)
                .orElse(null) +
                '}';
    }
}
