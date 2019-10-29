/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
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
    private JSONReader jsonReader;
    private JSONData jsonData;
    private ArbolAVL<Character, Palabra> arbolAVL; //Arbol query 1
    private ArbolAVL<Integer, Palabra> arbolAVL2;
    
    private static TreeManager singleTree;
    
    private ArrayList<Palabra> listaPalabras;
    private boolean banderaQ2;
    private boolean banderaQ3;
    private String linkAux;
    
    private Hashtable<String, ArbolAVL> hashLinks;
    
    private ArbolAVL<Integer, Palabra> arbolAVL3;

    public TreeManager() {
       this.jsonReader = new JSONReader(PATH_FILE);
       this.jsonData = jsonReader.convertJSonToData();
       this.arbolAVL = new ArbolAVL<>();
       this.arbolAVL2 = new ArbolAVL<>();
       this.listaPalabras = new ArrayList<>();
       this.banderaQ2 = true;
       this.banderaQ3 = true;
       this.hashLinks = new Hashtable<>();
    }
    
    public static TreeManager getInstance(){
        if (singleTree == null) {
            singleTree = new TreeManager();
        }
        return singleTree;
    }
    
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
                        
                        query_1(string, jsonLink);
                        
                        query_2(string, jsonLink);
                        
                        query_3(string, jsonLink);
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
    
    
    public void query_1(String pPalabra, String pJsonLink){
        Nodo<Character, Palabra> nodoBuscado = arbolAVL.find(pPalabra.charAt(0));
        Palabra palabra = new Palabra(pPalabra);

        if (nodoBuscado != null) { //Si existe el nodo con el primer caracter
            int index = nodoBuscado.getListaElementos().indexOf(palabra);

            if (index == -1) { //No existe la palabra en el nodo
                palabra.getListaLinks().add(pJsonLink);
                nodoBuscado.getListaElementos().add(palabra);
            } else { //Si existe la palabra en el nodo

                if (!nodoBuscado.getListaElementos().get(index).getListaLinks().contains(pJsonLink)) {
                    nodoBuscado.getListaElementos().get(index).getListaLinks().add(pJsonLink);
                }
            }
        } else { //Si no existe la palabra en el nodo
            palabra.getListaLinks().add(pJsonLink);
            arbolAVL.insert(pPalabra.charAt(0), palabra);
        }
    }
    
    public void query_2(String pPalabra, String pJsonLink){
        if (banderaQ2) {
            linkAux = pJsonLink;
            banderaQ2 = false;
        }
        
        if (linkAux != pJsonLink) {
            for (Palabra palabra : listaPalabras) {
                Nodo<Integer, Palabra> nodoBuscado = arbolAVL2.find(palabra.getCantidadRepeticiones());
                if (nodoBuscado != null) { //Si existe el nodo con el primer caracter
                    nodoBuscado.getListaElementos().add(palabra);
                }else{
                    arbolAVL2.insert(palabra.getCantidadRepeticiones(), palabra);
                }
            }
            listaPalabras.clear();
            linkAux = pJsonLink;
        }
        
        Palabra palabra = new Palabra(pPalabra);
        if (!listaPalabras.contains(palabra)) {
            palabra.getListaLinks().add(pJsonLink);
            listaPalabras.add(palabra);
        }else{
            int index = listaPalabras.indexOf(palabra);
            listaPalabras.get(index).aumentarCantidadRepeticiones();
        }  
    }
    
    public void query_3(String pPalabra, String pJsonLink){
        if (banderaQ3) {
            linkAux = pJsonLink;
            banderaQ3 = false;
        }
        
        if (linkAux != pJsonLink) {
            arbolAVL3 = new ArbolAVL<>();
            for (Palabra palabra : listaPalabras) {
                Nodo<Integer, Palabra> nodoBuscado = arbolAVL3.find(palabra.getCantidadRepeticiones());
                if (nodoBuscado != null) { //Si existe el nodo con el primer caracter
                    nodoBuscado.getListaElementos().add(palabra);
                }else{
                    arbolAVL3.insert(palabra.getCantidadRepeticiones(), palabra);
                }
            }
            listaPalabras.clear();
            hashLinks.put(linkAux, arbolAVL3);
            linkAux = pJsonLink;
        }
        
        Palabra palabra = new Palabra(pPalabra);
        if (!listaPalabras.contains(palabra)) {
            palabra.getListaLinks().add(pJsonLink);
            listaPalabras.add(palabra);
        }else{
            int index = listaPalabras.indexOf(palabra);
            listaPalabras.get(index).aumentarCantidadRepeticiones();
        }
    }
    
    public ArrayList<String> buscarLinks(String pPalabra){
        Palabra palabra = new Palabra(pPalabra);
        Nodo<Character, Palabra> nodoBuscado = arbolAVL.find(pPalabra.charAt(0));
        
        if (nodoBuscado != null) {
            int index = nodoBuscado.getListaElementos().indexOf(palabra);
            if (index != -1) {
                return nodoBuscado.getListaElementos().get(index).getListaLinks();
            }
        }
        return null;
    }
    
    public ArrayList<Palabra> buscarRango(String pMinimo, String pMaximo){
        ArrayList<Palabra> listaPalabras = new ArrayList<>();
        for (int i = Integer.parseInt(pMinimo); i <= Integer.parseInt(pMaximo); i++) {
            Nodo<Integer, Palabra> nodoBuscado = arbolAVL2.find(i);
            if (nodoBuscado != null) {
                listaPalabras.addAll(nodoBuscado.getListaElementos());
            }
            
        }
        return listaPalabras;
    }
    
    public ArrayList<Palabra> buscarTop(String pDominio){
        int contador = TOP_CINCO;
        int contador2 = 1;
        int llaveTemporal = 0;
        ArrayList<Palabra> listaPalabras = new ArrayList<>();
        ArbolAVL<Integer, Palabra> arbolAVLBuscar = hashLinks.get(pDominio);
        Nodo<Integer, Palabra> nodoBuscado = arbolAVLBuscar.findMax();
        while (contador!=0) {
            if (nodoBuscado != null) {
                llaveTemporal = nodoBuscado.getLlave();
                for (Palabra palabra : nodoBuscado.getListaElementos()) {
                    listaPalabras.add(palabra);
                    contador--;
                    contador2 = 1;
                }
            }
            try {
                nodoBuscado = arbolAVLBuscar.find(llaveTemporal - contador2);
            } catch (Exception e) {
                contador2++;
            }
        }
        return listaPalabras;
    }
    
    
}
