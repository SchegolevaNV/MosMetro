import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.*;

public class Parser {

    private String url;

    public Parser (String url) {
        this.url = url;
    }
    Object[] objects = new Object[]{2};

    public Object[] parse() throws IOException {
        Document doc = Jsoup.connect(url).maxBodySize(0).get();
        ArrayList<Line> lines = new ArrayList<>();
        ArrayList<Station> stations = new ArrayList<>();
        Line lineForStation = new Line("", "", "");

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
                }
            }
        }

        List<Station> stations11 = new ArrayList<>();
        int linePosition = 0;

        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).getNumber().equals("11")) {
                stations11 = lines.get(i).getStations();
            }
            if (lines.get(i).getNumber().equals("8А"))
            {
                linePosition = i;
            }
        }

        for (int j = 1; j < stations11.size(); j++) {
                lines.get(linePosition).addStation(stations11.get(j));
        }

        return objects = new Object[]{lines, stations};
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
