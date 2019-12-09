import Metro.Connections;
import Metro.Line;
import Metro.Station;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.*;

public class Main {

    public static final String URL = "https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_" +
            "%D1%81%D1%82%D0%B0%D0%BD%D1%86%D0%B8%D0%B9_%D0%9C%D0%BE%D1%81%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%BE" +
            "%D0%B3%D0%BE_%D0%BC%D0%B5%D1%82%D1%80%D0%BE%D0%BF%D0%BE%D0%BB%D0%B8%D1%82%D0%B5%D0%BD%D0%B0";

    public static void main(String[] args) throws IOException
    {
        Parser metroParser = new Parser(URL);
        Object[] metro = metroParser.parse();
        ArrayList<Line> line = (ArrayList<Line>) metro[0];
        ArrayList<Station> stations = (ArrayList<Station>) metro[1];
        ArrayList<Connections> connections = (ArrayList<Connections>) metro[2];

        Map<String, List<String>> station = new HashMap<>();
        List<String> list = new ArrayList<>();

        for (int i = 0; i < line.size(); i++) {
            for (int j = 0; j < line.get(i).getStations().size(); j++) {
                list.add(line.get(i).getStations().get(j).getName());
            }
            station.put(line.get(i).getNumber(), list);
        }

        ForJson forJson = new ForJson();
        forJson.setStations(station);
        forJson.setLines(line);

        String json =
                new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().
                        create().toJson(forJson);

        System.out.println(json);
    }
}