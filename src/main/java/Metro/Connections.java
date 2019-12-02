package Metro;
import java.util.ArrayList;

public class Connections {

    private ArrayList<Station> connections = new ArrayList<>();

    public Connections (ArrayList<Station> connection) {
        this.connections = connection;
    }

    public ArrayList<Station> getConnections() {
        return connections;
    }
}
