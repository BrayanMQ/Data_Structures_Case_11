
package modelo;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Ronny
 */
public class Palabra implements Comparable<Palabra>{
    private String palabra;
    private ArrayList<String> listaLinks;

    public Palabra(String palabra) {
        this.palabra = palabra;
        this.listaLinks = new ArrayList<>();
    }

    public String getPalabra() {
        return palabra;
    }

    public ArrayList<String> getListaLinks() {
        return listaLinks;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Palabra other = (Palabra) obj;
        if (!Objects.equals(this.palabra, other.palabra)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Palabra t) {
        return 1;
    }
    
    
    
}
