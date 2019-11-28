import java.io.IOException;

public class Main {

    public static final String URL = "https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_" +
            "%D1%81%D1%82%D0%B0%D0%BD%D1%86%D0%B8%D0%B9_%D0%9C%D0%BE%D1%81%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%BE" +
            "%D0%B3%D0%BE_%D0%BC%D0%B5%D1%82%D1%80%D0%BE%D0%BF%D0%BE%D0%BB%D0%B8%D1%82%D0%B5%D0%BD%D0%B0";

    public static void main(String[] args) throws IOException
    {
        Parser metroParser = new Parser(URL);
        metroParser.parse();
    }
}

/**============TEST CODE================*/

//        Element codeForColors = doc.selectFirst("style");
//        String color = codeForColors.toString().substring(codeForColors.toString().indexOf("line-1 a:hover"));
//        ArrayList<String> colors = new ArrayList<>();
//
//        for (int i = 0; i < color.length() ; i++) {
//            if (color.charAt(i) == '#') {
//                colors.add(color.substring(i, i+7));
//            }
//        }

//        Element table = doc.select("table").get(8);
//        String num = table.select("span[class]").text();
//       // String num1 = num.text();
//        String name = table.select("a").text();
//        System.out.println(name);
//        System.out.println(num);
//        Attributes codeForLines = doc.selectFirst("div[data-list_classes]").attributes();
//        String [] names = codeForLines.toString().split("&quot;");
//
//        for (String string: names) {
//            System.out.println(string);
//        }
