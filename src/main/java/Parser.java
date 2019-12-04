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

    public Parser (String url) {
        this.url = url;
    }

    public Object[] parse() throws IOException {

        Document doc = Jsoup.connect(url).maxBodySize(0).get();
        Line lineForStation = new Line("", "", "");
        Line line8A = new Line("", "", "");

        for (int i = 3; i <= 5; i++) {
            Elements table = doc.select("table").get(i).select("tr");

            for (int j = 0; j < table.size(); j++) {
                Elements td = table.get(j).select("td");

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
                    Station station = new Station(stationName, lineForStation);
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
        parseConnection();
        return objects = new Object[]{lines, stations};
    }

    public Object[] parseConnection () throws IOException {

        Document doc = Jsoup.connect(url).maxBodySize(0).get();
        Station mainStation = new Station("", null);
        Station connectStation = new Station("", null);

        for (int i = 3; i <= 5; i++) {
            Elements table = doc.select("table").get(i).select("tr");

            for (int j = 0; j < table.size(); j++) {
                Elements td = table.get(j).select("td");

                if (!td.isEmpty()) {
                    String stationName = td.get(1).selectFirst("a").text();
                    String lineConnectionNumber = td.get(3).select("span.sortkey").text();

                    if (!lineConnectionNumber.isEmpty()) {
                        Elements lineConnectionNames = td.get(3).select("span[title]");
                        ArrayList<Station> connections = new ArrayList<>();

                        for (int k = 0; k < lineConnectionNames.size(); k++) {
                            String lineConnectionName = lineConnectionNames.get(k).attr("title");
                            lineConnectionName = lineConnectionName.replaceAll(".+ станцию ", "");

                            for (int l = 0; l < stations.size(); l++) {
                                if (lineConnectionName.contains(stations.get(l).getName())) {
                                    lineConnectionName = stations.get(l).getName();
                                    connectStation = stations.get(l);
                                }

                                if (stationName.contains(stations.get(l).getName()))
                                    mainStation = stations.get(l);
                            }
                            System.out.println(lineConnectionNumber + " / " + lineConnectionName);
                            connections.add(mainStation);
                            connections.add(connectStation);
                        }
                        Connections connection = new Connections(connections);
                       // System.out.println(connection.getConnections());
                    }
                }
            }
        }
        return null;
    }
}
/**===ТЕСТОВЫЙ КОД =======**/

//   TreeMap<String, String> lines = new TreeMap<>();
//   TreeSet<String> colors = new TreeSet<>();
//                    lines.put(lineNumber, lineName.replaceAll(" линия", ""));
//                    colors.add(color);
//        for (Map.Entry<String, String> entry : lines.entrySet())
//        {
//            System.out.println(entry.getKey() + " : " + entry.getValue());
//        }
//
//        Iterator iterator = colors.iterator();
//        while (iterator.hasNext()){
//            System.out.println(iterator.next());
//        }
