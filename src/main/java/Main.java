import Metro.Connections;
import Metro.Line;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    public static final String URL = "https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_" +
            "%D1%81%D1%82%D0%B0%D0%BD%D1%86%D0%B8%D0%B9_%D0%9C%D0%BE%D1%81%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%BE" +
            "%D0%B3%D0%BE_%D0%BC%D0%B5%D1%82%D1%80%D0%BE%D0%BF%D0%BE%D0%BB%D0%B8%D1%82%D0%B5%D0%BD%D0%B0";

    private static final String OUTFILE = "src/main/resources/MosMetro.json";

    private static final Logger MARKLOGGER = LogManager.getLogger(Main.class);
    private static final Marker INVALID_FILE = MarkerManager.getMarker("INVALID_FILE");

    public static void main(String[] args) throws IOException
    {
        Parser metroParser = new Parser(URL);
        Object[] metro = metroParser.parse();
        List<Line> line = (ArrayList<Line>) metro[0];
        ArrayList<Connections> connections = (ArrayList<Connections>) metro[2];

        writeToJson(line, connections);
        linesFromJson();
    }

    private static void writeToJson(List<Line> line, ArrayList<Connections> connections)
    {
        Map<String, List<String>> stationsToJson = new HashMap<>();
        List<Line> linesToJson = new ArrayList<>();
        ArrayList<ArrayList<TreeMap<String,String>>> connectionsToJson = new ArrayList<>();

        for (int i = 0; i < line.size(); i++) {
            List<String> stations = new ArrayList<>();
            for (int j = 0; j < line.get(i).getStations().size(); j++) {
                stations.add(line.get(i).getStations().get(j).getName());
            }
            stationsToJson.put(line.get(i).getNumber(), stations);
            linesToJson.add(new Line(line.get(i)));
        }

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

        ForJson forJson = new ForJson();
        forJson.setStations(stationsToJson);
        forJson.setLines(linesToJson);
        forJson.setConnections(connectionsToJson);

        String json =
                new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().
                        create().toJson(forJson);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(OUTFILE, false));
            writer.append(json);
            writer.close();
        } catch (Exception e) {
            MARKLOGGER.info(INVALID_FILE, "/writeToJson/ Path is invalid or FS error: {}", e.getMessage());
        }
    }

    private static void linesFromJson() {
        {
            try {
                Gson mosMetro = new Gson();
                ForJson metro = mosMetro.fromJson(new FileReader(OUTFILE), ForJson.class);
                Map<String, List<String>> lines = metro.getStations();
                lines.forEach((key, value) -> {
                    String[] stations = value.toString().split(",");
                    System.out.println("Line number: " + key + " Stations: " + stations.length);
                });

            } catch (Exception ex) {
                MARKLOGGER.info(INVALID_FILE, "/linesFromJson/ Path is invalid or FS error: {}", ex.getMessage());
            }
        }

    }
}