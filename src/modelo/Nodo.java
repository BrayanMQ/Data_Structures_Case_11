
package modelo;

import java.util.ArrayList;

public class Nodo<T, E>{
    public String listaOcurrencias = "";/*Lista de ocurrencias*/
    public T llave;/*Dato en el nodo*/
    public ArrayList<E> listaElementos;
    public Nodo<T, E> left;/*Hijo izquierdo*/
    public Nodo<T, E> right; /*Hijo derecho*/    
    public int height;  /*Altura del árbol AVL*/ 
    
    public Nodo(T pLlave, Nodo<T, E> NodoIzquierdo, Nodo<T, E> NodoDerecho){
        this.llave = pLlave;
        this.left = NodoIzquierdo;
        this.right = NodoDerecho;
        this.listaElementos = new ArrayList<>();
        this.height = 0;
    }

    public T getLlave() {
        return llave;
    }

    public void setLlave(T llave) {
        this.llave = llave;
    }
    
    public Nodo<T, E> getLeft() {
        return left;
    }

    public void setLeft(Nodo<T, E> left) {
        this.left = left;
    }

    public Nodo<T, E> getRight() {
        return right;
    }

    public void setRight(Nodo<T, E> right) {
        this.right = right;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getListaOcurrencias() {
        return listaOcurrencias;
    }  

    public ArrayList<E> getListaElementos() {
        return listaElementos;
    }
        
}
