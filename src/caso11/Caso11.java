
package caso11;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import modelo.JSONReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import modelo.JSONData;

public class Caso11 {

    public static void main(String[] args) throws IOException {
         //Cargar json
        JSONReader jsonReader = new JSONReader("src\\jsonFile\\links.json");
        JSONData jsonData = jsonReader.convertJSonToData();
        
        //Expresiones regulares
        Pattern patronPalabras = Pattern.compile("[a-zA-Z]{4,}");
        Pattern patronLinks = Pattern.compile("^https://.*");

        for (String jsonLink : jsonData.getListaLinks()) {

                Document doc = Jsoup.connect(jsonLink).get();

                //Obtener palabras de un website
                Elements lineas = doc.getElementsByTag("p");

                for (Element linea : lineas) {
                    String texto = linea.text();
                    String[] palabrasPagina = texto.split(" ");

                    for (String string : palabrasPagina) {
                        Matcher match = patronPalabras.matcher(string);
                        if (match.matches()) {
                            System.out.println(string);
                        }
                    }
                }

                //Obtener links de un website
                Elements links = doc.getElementsByTag("a");
                int anchura = jsonData.getAnchura();
                
                for (Element link : links) {
                    String l = link.attr("href");
                    
                    Matcher match = patronLinks.matcher(l);
                    if (match.matches()) {
                        System.out.println(l); //Meter en una cola
                        anchura--; 
                    }

                    if (anchura == 0) {
                        break;
                    }
   
                }
        }
    }
}
