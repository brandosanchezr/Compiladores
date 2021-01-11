/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trujillo
 */
public class Regla {
    String strSimb;
    NodoRegla list;
    
    public Regla(String s, NodoRegla l){
    strSimb = s;
    list = l;
    }
    //gets
    public String getStrSimb(){
    return this.strSimb;
    }
    public NodoRegla getList(){
    return this.list;
    }
}
