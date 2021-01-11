/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trujillo
 */
public class NodoRegla {
    String strSimb;
    boolean terminal;
    NodoRegla siguiente;
    
    public NodoRegla(){
        terminal = false;
        siguiente = null;
    }
    public NodoRegla(String s){
        strSimb = s;
        terminal = false;
        siguiente = null;
    }
    public NodoRegla(String s, boolean t){
        strSimb = s;
        terminal = t;
        siguiente = null;
    }
    //Set
    public void setEdoSiguiente(NodoRegla n){
        this.siguiente = n;
    }
    public void setStrSimb(String s){
    strSimb =s;
    }
    public void setTerminal(boolean t){
    terminal = t;
    }
}
