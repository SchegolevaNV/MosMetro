import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class Parser
{
    private String url;

    public Parser (String url)
    {
        this.url = url;
    }

    public void parse() throws IOException
    {
        Document doc = Jsoup.connect(url).maxBodySize(0).get();
        TreeMap<String, String> lines = new TreeMap<>();
        TreeSet<String> colors = new TreeSet<>();

        for (int i = 3; i <= 5; i++) {
            Elements table = doc.select("table").get(i).select("tr");

            for (int j = 0; j < table.size(); j++) {
                Element td = table.get(j).selectFirst("td");
                if (td != null) {
                    String lineNumber = td.selectFirst("span.sortkey").text();
                    String color = td.select("[style^=background:#]").attr("style").replaceAll("background:", "");
                    String lineName = td.select("span[title$=линия]").attr("title");
                    if (lineName.isEmpty()) {
                        lineName = td.select("span[title]").attr("title");
                    }
                    lines.put(lineNumber, lineName);
                    colors.add(color);
                }
            }
        }
        for (Map.Entry<String, String> entry : lines.entrySet())
        {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        Iterator iterator = colors.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }


    }
}

