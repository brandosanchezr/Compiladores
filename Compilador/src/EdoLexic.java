/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trujillo
 */
public class EdoLexic {
    int ini;
    int fin;
    int global;
    public EdoLexic(int iniLexem, int finLexem,int numGlobal){
        ini = iniLexem;
        fin = finLexem;
        global = numGlobal;
    }
    public int getIniLex(){
    return this.ini;
    }
    public int getFinLex(){
    return this.fin;
    }
    public int getGlobal(){
    return this.global;
    }
}
