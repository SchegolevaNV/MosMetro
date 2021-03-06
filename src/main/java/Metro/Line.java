package Metro;

import java.util.ArrayList;
import java.util.List;

public class Line implements Comparable<Line>
{
    private String number;
    private String name;
    private String color;
    private List<Station> stations;

    public Line (String number, String name, String color) {
        this.number = number;
        this.name = name;
        this.color = color;
        stations = new ArrayList();
    }

    public Line (Line line) {
        this.number = line.getNumber();
        this.name = line.getName();
        this.color = line.getColor();
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public void addStation (Station station) {
        stations.add(station);
    }

    public List<Station> getStations() {
        return stations;
    }

    @Override
    public int compareTo (Line line)
    {
        return CharSequence.compare(number, line.getNumber());
    }

    @Override
    public boolean equals (Object obj)
    {
        return compareTo((Line) obj) == 0;
    }
}
