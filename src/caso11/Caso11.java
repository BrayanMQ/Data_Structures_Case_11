
package caso11;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import modelo.ArbolAVL;
import modelo.JSONReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import modelo.JSONData;
import modelo.Nodo;
import modelo.Palabra;

public class Caso11 {

    public static void main(String[] args) throws IOException {
        
         //Cargar json
        JSONReader jsonReader = new JSONReader("src\\jsonFile\\links.json");
        JSONData jsonData = jsonReader.convertJSonToData();
        ArbolAVL<Character, Palabra> arbolAVL = new ArbolAVL<>(); //Arbol query 1
        
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
                        string = string.toLowerCase();
                        if (match.matches()) {
                            Nodo<Character, Palabra> nodoBuscado = arbolAVL.find(string.charAt(0));
                            Palabra palabra = new Palabra(string);
                            
                            if (nodoBuscado != null) { //Si existe el nodo con el primer caracter
                                int index = nodoBuscado.getListaElementos().indexOf(palabra);
                                
                                if (index == -1) { //No existe la palabra en el nodo
                                    palabra.getListaLinks().add(jsonLink);
                                    nodoBuscado.getListaElementos().add(palabra);
                                } else { //Si existe la palabra en el nodo
                                    
                                    if (!nodoBuscado.getListaElementos().get(index).getListaLinks().contains(jsonLink)){
                                        palabra.getListaLinks().add(jsonLink);
                                    }
                                }
                            } else { //Si no existe la palabra en el nodo
                                palabra.getListaLinks().add(jsonLink);
                                arbolAVL.insert(string.charAt(0), palabra);
                            }
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

        arbolAVL.printTree();
        
    }
}
