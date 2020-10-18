
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
public class Estado {
    
    int id; //entero >= 0
    List<Transicion> transciciones;
    boolean edoInicial;
    boolean edoFinal;
    int token; // entero > 0 y solo si es de acceptacion

    public Estado() {
        id = -1;
        transciciones = new ArrayList<Transicion>();
        edoInicial = false;
        edoFinal = false;
        token = -1;
    }
    
    public Estado(int id, List<Transicion> transiciones, boolean edoInicial, boolean edoFinal, int token){
        this.id = id;
        this.transciciones = transiciones;
        this.edoInicial = edoInicial;
        this.edoFinal = edoFinal;
        this.token = token;
    }
    
    //-----------------  BLOQUE GET  -----------------
    public int getId(){
        return id;
    }
        
    public int getToken(){
        return token;
    }
    
    public boolean isEdoFinal() {
        return edoFinal;
    }

    public boolean isEdoInicial() {
        return edoInicial;
    }

    public List<Transicion> getTransciciones() {
        return transciciones;
    }
    
    ///-----------------  BLOQUE SET  -----------------

    public void setId(int id) {
        this.id = id;
    }

    public void setTransciciones(List<Transicion> transciciones) {
        this.transciciones = transciciones;
    }

    public void setEdoFinal(boolean edoFinal) {
        this.edoFinal = edoFinal;
    }

    public void setEdoInicial(boolean edoInicial) {
        this.edoInicial = edoInicial;
    }

    public void setToken(int token) {
        this.token = token;
    }
    
    @Override
    public String toString() { 
        return String.format("ID: " + id + "\n" +
                             "Transiciones: " + transciciones + "\n" +
                             "Estado inicial: " + edoInicial + "\n" +
                             "Estado final: " + edoFinal + "\n" +
                             "Token: " + token +"\n" 
        ); 
    }
    
}
