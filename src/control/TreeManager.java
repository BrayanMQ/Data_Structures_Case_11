/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.IOException;
import java.util.regex.Matcher;
import modelo.ArbolAVL;
import static modelo.IConstants.*;
import modelo.JSONData;
import modelo.JSONReader;
import modelo.Nodo;
import modelo.Palabra;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Ronny
 */
public class TreeManager {
    private JSONReader jsonReader = new JSONReader(PATH_FILE);
    private JSONData jsonData = jsonReader.convertJSonToData();
    private ArbolAVL<Character, Palabra> arbolAVL = new ArbolAVL<>(); //Arbol query 1
    
    
    
    public void indexarPalabras() throws IOException{
        
        for (String jsonLink : jsonData.getListaLinks()) {

            Document doc = Jsoup.connect(jsonLink).get();

            //Obtener palabras de un website
            Elements lineas = doc.getElementsByTag(TAG_LINEAS);

            for (Element linea : lineas) {
                String texto = linea.text();
                String[] palabrasPagina = texto.split(ESPACIO);

                for (String string : palabrasPagina) {
                    Matcher match = PATRON_PALABRAS.matcher(string);
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

                                if (!nodoBuscado.getListaElementos().get(index).getListaLinks().contains(jsonLink)) {
                                    nodoBuscado.getListaElementos().get(index).getListaLinks().add(jsonLink);
                                }
                            }
                        } else { //Si no existe la palabra en el nodo
                            palabra.getListaLinks().add(jsonLink);
                            arbolAVL.insert(string.charAt(0), palabra);
                        }
                    }
                }
            }

////////////////////            //Obtener links de un website
////////////////////            Elements links = doc.getElementsByTag(TAG_HIPERVINCULO);
////////////////////            int anchura = jsonData.getAnchura();
////////////////////
////////////////////            for (Element link : links) {
////////////////////                String l = link.attr(TAG_LINK);
////////////////////
////////////////////                Matcher match = PATRON_LINKS.matcher(l);
////////////////////                if (match.matches()) {
////////////////////                    System.out.println(l); //Meter en una cola
////////////////////                    anchura--;
////////////////////                }
////////////////////
////////////////////                if (anchura == 0) {
////////////////////                    break;
////////////////////                }
////////////////////            }
        }
        arbolAVL.printTree();
     }
    
    
}
