package Metro;

public class Station implements Comparable<Station>
{
    private String line;
    private String name;
    private String lineColor;

    public Station(String name, String line, String lineColor) {
        this.name = name;
        this.line = line;
        this.lineColor = lineColor;
    }

    public String getLine()
    {
        return line;
    }

    public String getName()
    {
        return name;
    }

    public String getLineColor() {
        return lineColor;
    }

    @Override
    public int compareTo(Station station)
    {
        int lineComparison = line.compareTo(station.getLine());
        if(lineComparison != 0) {
            return lineComparison;
        }
        return name.compareToIgnoreCase(station.getName());
    }

    @Override
    public boolean equals(Object obj)
    {
        return compareTo((Station) obj) == 0;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
