/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author Ronny
 */
public class Palabra {
    private String palabra;
    private ArrayList<String> listaLinks;

    public Palabra(String palabra, ArrayList<String> listaLinks) {
        this.palabra = palabra;
        this.listaLinks = new ArrayList<>();
    }

    public String getPalabra() {
        return palabra;
    }

    public ArrayList<String> getListaLinks() {
        return listaLinks;
    }
    
}
