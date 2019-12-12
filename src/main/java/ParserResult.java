import Metro.Line;
import java.util.*;

public class ParserResult {

    private List<Line> lines;
    private Map<String, List<String>> stations;
    private ArrayList<ArrayList<TreeMap<String,String>>> connections;


    public ParserResult(Map<String, List<String>> stations,
                        List<Line> lines,
                        ArrayList<ArrayList<TreeMap<String, String>>> connections) {
        this.stations = stations;
        this.lines = lines;
        this.connections = connections;
    }

    public Map<String, List<String>> getStations() {
        return stations;
    }
}
