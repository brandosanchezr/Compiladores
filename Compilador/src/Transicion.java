
import java.util.ArrayList;
  import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Brando Sanchez
 */
public class Transicion {
    
    Character simbolo;
    List<Estado> edosDestinos;

    public Transicion(char simbolo, List<Estado> edosDestinos) {
        this.simbolo = simbolo;
        this.edosDestinos = edosDestinos;
    }

    public Transicion() {
        simbolo = '\0';
        edosDestinos = new ArrayList<Estado>();
    }

    public List<Estado> getEdosDestinos() {
        return edosDestinos;
    }

    public char getSimbolo() {
        return simbolo;
    }

    public void setEdosDestinos(List<Estado> edosDestinos) {
        this.edosDestinos = edosDestinos;
    }

    public void setSimbolo(char simbolo) {
        this.simbolo = simbolo;
    }

    @Override
    public String toString() {
        return "Simbolo: " + simbolo + "\n" +
               "Estados Destinos: " + edosDestinos;
    }
    
    
    
}
