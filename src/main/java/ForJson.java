import Metro.Line;

import java.util.*;

public class ForJson {

    private Map<String, List<String>> stations = new HashMap<>();
    private List<Line> lines = new ArrayList<>();
    private ArrayList<ArrayList<TreeMap<String,String>>> connections;

    public Map<String, List<String>> getStations() {
        return stations;
    }
    public void setStations(Map<String, List<String>> stations) {
        this.stations = stations;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public void setConnections(ArrayList<ArrayList<TreeMap<String,String>>> connections) {
        this.connections = connections;
    }

    public ArrayList<ArrayList<TreeMap<String,String>>> getConnections() {
        return connections;
    }
}
