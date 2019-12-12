import Metro.Connections;
import Metro.Line;
import Metro.Station;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.util.*;

public class Parser {

    private static final Logger MARKLOGGER = LogManager.getLogger(Parser.class);
    private static final Marker URL_ERROR = MarkerManager.getMarker("URL_ERROR");
    private static final Marker PARSED_STATIONS = MarkerManager.getMarker("PARSED_STATIONS");

    private String url;

    ArrayList<Line> lines = new ArrayList<>();
    List<Station> stations = new ArrayList<>();

    public Parser(String url) {
        this.url = url;
    }

    public ParserResult parse() {

        Document doc = connectToUrl(url);
        Line lineForStation = new Line("", "", "");
        Line line8A = new Line("", "", "");

        for (int i = 3; i <= 5; i++) {
            Elements table = doc.select("table").get(i).select("tr");

            for (org.jsoup.nodes.Element element : table) {
                Elements td = element.select("td");

                if (!td.isEmpty()) {
                    String lineNumber = td.get(0).selectFirst("span.sortkey").text();
                    String color = td.get(0).select("[style^=background:#]").attr("style")
                            .replaceAll("background:", "");
                    String lineName = td.get(0).select("span[title$=линия]").attr("title");

                    if (lineName.isEmpty()) {
                        lineName = td.get(0).select("span[title]").attr("title");
                    }
                    Line line = new Line(lineNumber, lineName.replaceAll(" линия", ""), color);

                    if (!lines.contains(line)) {
                        lines.add(line);
                        lineForStation = line;
                    }
                    String stationName = td.get(1).selectFirst("a").text();
                    Station station = new Station(stationName, lineForStation.getNumber(), lineForStation.getColor());
                    lineForStation.addStation(station);
                    stations.add(station);
                    MARKLOGGER.info(PARSED_STATIONS, "/parseStations/ Station is parsed: {}", stationName);

                    if (lineForStation.getNumber().equals("8А")) {
                        line8A = lineForStation;
                    }

                    if (lineForStation.getNumber().equals("11")) {
                        if (!station.getName().equals("Деловой центр"))
                            line8A.addStation(station);
                    }
                }
            }
        }

        Map<String, List<String>> stationsToJson = new HashMap<>();
        List<Line> linesToJson = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            List<String> stations = new ArrayList<>();
            for (int j = 0; j < lines.get(i).getStations().size(); j++) {
                stations.add(lines.get(i).getStations().get(j).getName());
            }
            stationsToJson.put(lines.get(i).getNumber(), stations);
            linesToJson.add(new Line(lines.get(i)));
        }

        ParserResult result = new ParserResult(stationsToJson, linesToJson, parseConnection());
        return result;
    }

    private ArrayList<ArrayList<TreeMap<String,String>>> parseConnection() {

        Document doc = connectToUrl(url);
        List<Connections> connections = new ArrayList<>();

        for (int i = 3; i <= 5; i++) {
            Elements table = doc.select("table").get(i).select("tr");

            for (org.jsoup.nodes.Element element : table) {
                Elements td = element.select("td");

                if (!td.isEmpty()) {
                    String lineNumber = td.get(0).selectFirst("span.sortkey").text();
                    String stationName = td.get(1).selectFirst("a").text();
                    String lineConnectionNumber = td.get(3).select("span.sortkey").text();

                    if (!lineConnectionNumber.isEmpty()) {
                        String[] lineConnectionNumbers = lineConnectionNumber.split("\\s");
                        Elements stationConnectionNames = td.get(3).select("span[title]");
                        TreeMap<String, String> connectStations = new TreeMap<>();
                        connectStations.put(lineNumber, stationName);

                        for (int j = 0; j < stationConnectionNames.size(); j++) {
                            String stationConnectionName = stationConnectionNames.get(j).attr("title");
                            stationConnectionName = stationConnectionName.replaceAll(".+ станцию ", "");

                            for (Station station : stations) {
                                if (stationConnectionName.contains(station.getName())) {
                                    stationConnectionName = station.getName();
                                }
                                connectStations.put(lineConnectionNumbers[j], stationConnectionName);
                            }
                        }
                        Connections connection = new Connections(connectStations);
                        connections.add(connection);
                    }
                }
            }
        }

        for (int i = 0; i < connections.size(); i++) {
            for (int j = 0; j < connections.size(); j++) {
                if (connections.get(i).getConnections().equals(connections.get(j).getConnections()) && i!=j) {
                    connections.remove(connections.get(j));
                }
            }
        }

        ArrayList<ArrayList<TreeMap<String,String>>> connectionsToJson = new ArrayList<>();
        for (Connections connection : connections) {
            ArrayList<TreeMap<String, String>> thisConnection = new ArrayList<>();
            TreeMap<String, String> newConnection = connection.getConnections();
            newConnection.forEach((key, value) -> {
                TreeMap<String, String> station = new TreeMap<>();
                station.put("line", key);
                station.put("station", value);
                thisConnection.add(station);
            });
            connectionsToJson.add(thisConnection);
        }

        return connectionsToJson;
    }

    private Document connectToUrl(String url)
    {
        try {
            Document doc = Jsoup.connect(url).maxBodySize(0).get();
            return doc;
        } catch (Exception ex) {
            MARKLOGGER.info(URL_ERROR,"URL Error {}", ex.getMessage());
        }
        return null;
    }
}