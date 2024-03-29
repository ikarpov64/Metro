package org.javaacadmey.metro;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Metro {
    private static final String TICKET_TEMPLATE = "a%04d";
    private static final int SUBSCRIBERS_LIMIT = 9999;
    private final String city = "Пермь";
    private Set<Line> lines = new HashSet<>();
    private HashMap<String, LocalDate> subscribers = new HashMap<>();

    /**
     * Создание новой линии метро.
     * @param color Цвет линии
     * @exception RuntimeException если линия с таким цветом существует.
     */
    public void createNewLine(Color color) {
        if (!isLineExists(color)) {
            lines.add(new Line(color, this));
        } else {
            throw new RuntimeException("Такая линия уже существует. " + color.color);
        }
    }

    /**
     * Добавление номера билета в список абонентов.
     * @param ticketNumber Уникальный номер проездного билета.
     * @param date Дата окончания срока действия проездного билета.
     */
    public void addSubscribers(String ticketNumber, LocalDate date) {
        subscribers.put(ticketNumber, date);
    }

    /**
     * Проверка срока действия проездного билета.
     * @param ticketNumber Уникальный номер проездного билета.
     * @param date Дата проверки проездного билета.
     * @return true если билет просрочен
     */
    public boolean checkMonthlyTicketExpirationDate(String ticketNumber, LocalDate date) {
        LocalDate expirationDate = subscribers.get(ticketNumber);
        return expirationDate.isBefore(date);
    }

    /**
     * Генерация уникального номера на проездной билет.
     * @return Номер проездного билета.
     * @exception RuntimeException при достижении лимита проданных билетов.
     */
    public String generateTicketNumber() {
        if (subscribers.size() < SUBSCRIBERS_LIMIT) {
            return String.format(TICKET_TEMPLATE, subscribers.size());
        } else {
            throw new RuntimeException("Закончились абонементы на проезд.");
        }
    }

    /**
     * Создание первой станции на линии метро
     * @param name Название станции.
     * @param color Цвет линии метро.
     * @param transferStations Список станций на пересадку.
     */
    public void createFirstStationInLine(Color color,
                                         String name,
                                         List<Station> transferStations) {
        if (!isLineExists(color)) {
            throw new RuntimeException("Линии не существует. Вначале создайте линию метро.");
        }

        if (!lineIsEmpty(color)) {
            throw new RuntimeException("На линии уже существует начальная станция.");
        }

        if (isStationExists(name)) {
            throw new RuntimeException("Линии с таким именем уже существует в метро.");
        } else {
            Line currentLine = getLine(color);
            currentLine.getStations().add(new Station(name, currentLine,this));
        }
    }

    /**
     * Создание последней станции на линии метро.
     * @param name Название станции.
     * @param color Цвет линии метро.
     * @param duration Время перегона от предыдущей станции к текущей станции.
     * @param transferStations Список станций на пересадку.
     */
    public void createLastStationInLine(Color color,
                                        String name,
                                        Duration duration,
                                        List<Station> transferStations) {

        if (!isLineExists(color)) {
            throw new RuntimeException("Линии не существует. Вначале создайте линию метро.");
        }

        if (isStationExists(name)) {
            throw new RuntimeException("Линии с таким именем уже существует в метро.");
        }

        if (duration.getSeconds() == 0) {
            throw new RuntimeException("Время перегона не может быть равным нулю.");
        }

        if (lineIsEmpty(color)) {
            throw new RuntimeException("Начальной станции не существует, вначале создайте начальную станцию.");
        }

        Line currentLine = getLine(color);
        LinkedList<Station> stations = currentLine.getStations();
        Station lastStation = stations.getLast();

        if (lastStation.getNextStation() != null) {
            throw new RuntimeException("Невозможно создать станцию. Предыдущая станция уже имеет следующую.");
        }

        Station currentStation = new Station(name, currentLine, this);
        currentStation.setPrevStation(lastStation);
        lastStation.setTimeToNextStation(duration);
        lastStation.setNextStation(currentStation);
        stations.add(currentStation);
    }

    /**
     * Получение Станции по имени.
     * @param name Название станции
     * @return найденная станция во всех линиях.
     * @exception NoSuchElementException если станции не существует.
     */
    public Station getStationByName(String name) {
        return lines.stream()
                .flatMap(line -> line.getStations().stream())
                .filter(station -> name.equals(station.getName()))
                .findFirst()
                .orElseThrow();
    }

    /**
     * Получение линии метро по цвету.
     * @param color Цвет линии метро которую ищем.
     * @return Линия метро.
     * @exception NoSuchElementException если линии не существует.
     */
    private Line getLine(Color color) {
        return lines.stream()                                   // Получаем поток линий
                .filter(line -> color.equals(line.getColor()))  // Фильтруем по цвету линии
                .findFirst()                                    // Берем первую попавшуюся линию по цвету
                .orElseThrow();                                 // Возвращаем найденную линию
    }

    /**
     * Определение станции пересадки с одной линии на другую.
     * @param startLine линия с которой осуществляется пересадка.
     * @param endLine линия на которую осуществляется пересадка.
     * @return Станция на линии startLine с которой можно осуществить пересадку.
     */
    private Station getTransferStation(Line startLine, Line endLine) {
        return endLine.getStations()
                .stream()
                .flatMap(station -> station.getTransferStations().stream())
                .filter(station -> startLine.equals(station.getLine()))
                .findFirst().orElseThrow();
    }

    /**
     * Определение станции пересадки с одной линии на другую.
     * @param startLine цвет линии с которой осуществляется пересадка.
     * @param endLine цвет линии на которую осуществляется пересадка.
     * @return Станция на линии startLine с которой можно осуществить пересадку.
     */
    public Station getTransferStation(Color startLine, Color endLine) {
        return getLine(endLine).getStations()
                .stream()
                .flatMap(station -> station.getTransferStations().stream())
                .filter(station -> getLine(startLine).equals(station.getLine()))
                .findFirst().orElseThrow();
    }

    /**
     * Считаем количество перегонов между станциями на разных линиях.
     * Если у станций совпадают линии, идет расчет количества перегонов на одной линии.
     * Если линии не совпадают, ищутся станции пересадки, затем происходит расчет:
     * Линия1: startStation - СтанцияПересадки, Линия2: СтанцияПересадки - endStation.
     * @param startStation станция посадки и начала движения.
     * @param endStation станция пункта назначения.
     * @return Количество перегонов между станциями. -1 если маршрута не существует.
     */
    public int numberOfRunsBetweenStationsOutsideLines(Station startStation, Station endStation) {
        if (startStation.getLine().equals(endStation.getLine())) {
            return numberOfRunsBetweenStationsWithinLine(startStation, endStation);
        }
        try {
            Station transferStationStartLine = getTransferStation(startStation.getLine(), endStation.getLine());
            Station transferStationEndLine = getTransferStation(endStation.getLine(), startStation.getLine());
            int numbersOfRuns = numberOfRunsBetweenStationsWithinLine(startStation, transferStationStartLine);
            numbersOfRuns += numberOfRunsBetweenStationsWithinLine(transferStationEndLine, endStation);
            return numbersOfRuns;
        } catch (NoSuchElementException ignored) {
            throw new RuntimeException(String.format("Невозможно проложить маршрут от станции %s к станции %s.\n",
                    startStation.getName(), endStation.getName()));
        }
    }

    /**
     * Считаем количество перегонов между двумя станциями на одной линии.
     * Вначале считаем в прямом направлении, затем в обратном.
     * @param startStation Начальная станция, с которой движемся
     * @param endStation Конечная станция, до которой хотим доехать.
     * @return RuntimeException если нет перегона между станциями.
     */
    private int numberOfRunsBetweenStationsWithinLine(Station startStation, Station endStation) {
        int numberOfRuns = calculateNumberOfRunsForwardDirection(startStation, endStation);
        if (numberOfRuns != -1) {
            return numberOfRuns;
        }
        numberOfRuns = calculateNumberOfRunsBackwardsDirection(startStation, endStation);
        if (numberOfRuns != -1) {
            return numberOfRuns;
        }
        throw new RuntimeException("Нет пути из станции "
                + startStation.getName()
                + " в станцию "
                + endStation.getName());
    }

    /**
     * Подсчет количества перегонов с одной станции на другой в прямом направлении.
     * @param startStation станция начала движения
     * @param endStation точка назначения.
     * @return -1 если нет перегона в прямом направлении.
     */
    private int calculateNumberOfRunsForwardDirection(Station startStation, Station endStation) {
        int numberOfRuns = 0;
        Station currentStation = startStation;
        while (currentStation != null && !currentStation.equals(endStation)) {
            currentStation = currentStation.getNextStation();
            numberOfRuns++;
        }
        return currentStation != null ? numberOfRuns : -1;
    }

    /**
     * Подсчет количества перегонов с одной станции на другой в обратном направлении.
     * @param startStation станция начала движения
     * @param endStation точка назначения.
     * @return -1 если нет перегона в обратном направлении.
     */
    private int calculateNumberOfRunsBackwardsDirection(Station startStation, Station endStation) {
        int numberOfRuns = 0;
        Station currentStation = startStation;
        while (currentStation != null && !currentStation.equals(endStation)) {
            currentStation = currentStation.getPrevStation();
            numberOfRuns++;
        }
        return currentStation != null ? numberOfRuns : -1;
    }

    /**
     * Проверка существования линии по цвету.
     */
    private boolean isLineExists(Color color) {
        return lines.stream()                                        // Получаем поток линий
                .anyMatch(line -> color.equals(line.getColor()));    // Проверяем существует ли линия с цветом
    }

    /**
     * Проверка на наличие станций в линии.
     */
    private boolean lineIsEmpty (Color color) {
        return lines.stream()                                       // Получаем поток линий
                .filter(line -> color.equals(line.getColor()))      // Фильтруем по цвету линии
                .anyMatch(line -> line.getStations().isEmpty());    // Проверяем список станций в линии на пустоту
    }

    /**
     * Проверка на существование станции по имени.
     */
    public boolean isStationExists(String name) {
        return lines.stream()                                          // Получаем поток линий
                .flatMap(line -> line.getStations().stream())          // Преобразуем каждую линию в поток её станций
                .anyMatch(station -> name.equals(station.getName()));  // Проверяем существование станции по имени
    }

    public void getTotalIncome() {
        Map<LocalDate, BigDecimal> incomeByDate =
                lines.stream() // Получаем поток линий
                .flatMap(line -> line.getStations().stream())    // Преобразуем каждую линию в поток её станций
                .flatMap(station -> station.getTicketOffice().getIncome().entrySet().stream()) // Получаем поток записей доходов
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        BigDecimal::add));

        incomeByDate.forEach((date, income) ->
                System.out.println(date + "=" + income));
    }

    public HashMap<String, LocalDate> getSubscribers() {
        return subscribers;
    }

    @Override
    public String toString() {
        return "Metro{" +
                "city='" + city + '\'' +
                ", lines=" + lines +
                '}';
    }
}
