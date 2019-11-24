import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_" +
                "%D1%81%D1%82%D0%B0%D0%BD%D1%86%D0%B8%D0%B9_%D0%9C%D0%BE%D1%81%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%BE" +
                "%D0%B3%D0%BE_%D0%BC%D0%B5%D1%82%D1%80%D0%BE%D0%BF%D0%BE%D0%BB%D0%B8%D1%82%D0%B5%D0%BD%D0%B0").get();

//        Element table = doc.select("style").first();
//        String string = table.toString();
//        string = string.substring(string.indexOf("a:hover"), string.lastIndexOf("line-15 a:hover")+41);
//        ArrayList<String> colors = new ArrayList<>();
//
//        for (int i = 0; i < string.length() ; i++) {
//            if (string.charAt(i) == '#') {
//                colors.add(string.substring(i, i+7));
//            }
//        }
//        Iterator iterator = colors.iterator();
//        while (iterator.hasNext()){
//            System.out.println(iterator.next());
//        }
    }
      //  String link = href.get(1).attr("src");
}
