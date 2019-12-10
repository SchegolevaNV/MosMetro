import Metro.Connections;
import Metro.Line;
import Metro.Station;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.*;

public class Parser {

    private String url;
    ArrayList<Line> lines = new ArrayList<>();
    ArrayList<Station> stations = new ArrayList<>();
    Object[] objects = new Object[]{};

    public Parser(String url) {
        this.url = url;
    }

    public Object[] parse() throws IOException {

        Document doc = Jsoup.connect(url).maxBodySize(0).get();
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
        return objects = new Object[]{lines, stations, parseConnection()};
    }

    private ArrayList<Connections> parseConnection() throws IOException {

        Document doc = Jsoup.connect(url).maxBodySize(0).get();
        ArrayList<Connections> connections = new ArrayList<>();

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
        return connections;
    }
}