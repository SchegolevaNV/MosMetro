package Metro;
import java.util.TreeMap;

public class Connections {

    private TreeMap<String, String> connections;

    public Connections (TreeMap<String, String> connection) {
        this.connections = connection;
    }

    public TreeMap<String, String> getConnections() {
        return connections;
    }
}
