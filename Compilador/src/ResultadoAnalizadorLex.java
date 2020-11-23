/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trujillo
 */
public class ResultadoAnalizadorLex{
    int token;
    String lexema;
    public ResultadoAnalizadorLex(){
        this.token=0;
    }
    public ResultadoAnalizadorLex(int nuevoToken,String lexemaReconocido){
        token = nuevoToken;
        lexema = lexemaReconocido;
    }
 
    public int getToken(){
    return this.token;
    }
    public String getLexema(){
    return this.lexema;
    }
    @Override
    public String toString() { 
        return String.format("Token: " + Integer.toString(token) + "\n" +
                         "lexema: " + lexema +"\n"
    ); 
}
}