import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
        HashSet<String> name = new HashSet<>();
        TreeMap<String, String> lines = new TreeMap<>();

        for (int i = 3; i <=5; i++)
        {
            ArrayList<Element> table = doc.select("table").get(i).select("tr");

            for (int j = 0; j < table.size(); j++)
            {
                String lineNumber = table.get(j).selectFirst("td").select("span.sortkey").text();
                System.out.println(lineNumber);
            }

//            ArrayList<Element> lineName = doc.select("table").get(i).select("span[title$=линия]");
//
//            for (int j = 0; j < lineName.size(); j++)
//            {
//                name.add(lineName.get(j).attr("title"));
//                //lines.put(lineNumber.get(j).text(),lineName.get(j).attr("title"));
//            }
//
//            if (lineName.isEmpty())
//            {
//                lineName = doc.select("table").get(i).select("span[title]");
//                lines.put(lineNumber.get(0).text(),lineName.get(0).attr("title"));
//            }
//        }
//
//        Iterator<String> i = name.iterator();
//        while (i.hasNext())
//            System.out.println(i.next());
//
//        for (Map.Entry<String, String> entry : lines.entrySet())
//        {
//        System.out.println("Key: " + entry.getKey() + ". Value: " + entry.getValue());
        }

    }
}

