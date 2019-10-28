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
public class JSONData {
private ArrayList<String> listaLinks;
    private int anchura;
    private int profundidad;

    public JSONData(ArrayList<String> listaLinks, int anchura, int profundidad) {
        this.listaLinks = listaLinks;
        this.anchura = anchura;
        this.profundidad = profundidad;
    }

    public ArrayList<String> getListaLinks() {
        return listaLinks;
    }
    
    public int getAnchura() {
        return anchura;
    }

    public void setAnchura(int anchura) {
        this.anchura = anchura;
    }

    
    public int getProfundidad() {
        return profundidad;
    }

    @Override
    public String toString() {
        return "JSONData{" + "listaLinks=" + listaLinks + ", anchura=" + anchura + ", profundidad=" + profundidad + '}';
    } 
}
