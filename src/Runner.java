import java.time.Duration;

public class Runner {
    public static void main(String[] args) {

        Metro metro = new Metro();
        metro.createNewLine(Color.RED);
        metro.createFirstStationInLine(Color.RED, "Спортивная", Duration.ZERO, null);
    }
}