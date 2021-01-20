
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//version new 12/16/2020
/**
 *
 * @author Trujillo
 */
public class AnalizadorLexico {
    List<List<Integer>> tabla;
    String cadena;
    List<Character> alfabeto;
    List<List<Integer>> tokensGlobales = new ArrayList<>();
    int numGlobales;
    //yyLex
    int iniLexem;
    int finLexem;
    int auxFinLexem;
    boolean finDeCadena;
    
    public AnalizadorLexico(AFD afdEntrada,String cadenAnalizar){
        tabla = afdEntrada.getTabla();
        cadena = cadenAnalizar;
        alfabeto = afdEntrada.getAlfabeto();
        iniLexem=0;
        finLexem = -1;
        auxFinLexem = 0;
        finDeCadena=false;
        numGlobales=0;
        tokensGlobales.add(List.of(this.iniLexem,this.auxFinLexem));
        //Revisar que la cadena sea valida con el alfabeto
    }
    public ResultadoAnalizadorLex yyLex(){
        int estadoActual = 0;
        int token=0;
        char charActual= cadena.charAt(iniLexem);
        this.setAuxFinLexem(iniLexem);
        boolean estadoPrevioAceptacion = false;
        List<Character> lexemaChar;
        String lexema="";
        if(finLexem==cadena.length()-1)
            finDeCadena = true;
        while(auxFinLexem<cadena.length()){
            charActual =cadena.charAt(auxFinLexem);
            int numTransicion = buscarTransicion(estadoActual,charActual);
            if(numTransicion>=0){ //Si hay transicion
                estadoActual= numTransicion;
                int auxToken=this.getToken(estadoActual);
                if(auxToken>0){ //si es estado final
                    this.setFinLexem(auxFinLexem);
                    estadoPrevioAceptacion=true;
                    token=auxToken;
                }
                this.setAuxFinLexem(auxFinLexem+1);
            }else{  //Si no hay transicion
                if(estadoPrevioAceptacion==false){ //si no hubo aceptacion y no hubo transicion
                    //revisar
                  this.setAuxFinLexem(auxFinLexem+1);
                  this.setIniLexem(auxFinLexem);
                  estadoActual=0;
                  return new ResultadoAnalizadorLex(0,"ERROR");
                }else{  //si hubo un estado de aceptacion pero ya no hubo transicion
                        //regresar lexema y token
                        for(int i=iniLexem;i<=finLexem;i++)
                            lexema=lexema+Character.toString(cadena.charAt(i));
                        this.setIniLexem(auxFinLexem);
                        this.setFinLexem(-1);
                        //this.setAuxFinLexem(auxFinLexem+1); 
                        this.agregarTokenGlobal(List.of(this.iniLexem,this.auxFinLexem));
                        return new ResultadoAnalizadorLex(token,lexema);
                    }
            }
        }
        if(finDeCadena)
            return new ResultadoAnalizadorLex(0,"Fin de cadena");
        for(int i=iniLexem;i<=finLexem;i++)
            lexema=lexema+Character.toString(cadena.charAt(i));
        this.agregarTokenGlobal(List.of(this.iniLexem,this.auxFinLexem));
        return new ResultadoAnalizadorLex(token,lexema);
    }
    public void regresarToken(){
        if(finDeCadena)
           finDeCadena = false; 
        if(this.numGlobales!= 0){
            this.setIniLexem(this.tokensGlobales.get(this.numGlobales-1).get(0));
            this.setAuxFinLexem(this.tokensGlobales.get(this.numGlobales-1).get(1));
            this.tokensGlobales.remove(this.numGlobales);
            this.numGlobales = this.numGlobales -1;
        }

    }
    //------------- EdoLexic ---------------------
    public EdoLexic getEdo(){
        int iniLex=this.tokensGlobales.get(this.numGlobales).get(0);
        int finLexem =this.tokensGlobales.get(this.numGlobales).get(1);
        return new EdoLexic(iniLex,finLexem,this.numGlobales);
    }
  
      
    public void setEdo(EdoLexic edo){
        if(finDeCadena)
           finDeCadena = false; 
        if(this.numGlobales!= 0){
            this.setIniLexem(edo.getIniLex());
            this.setAuxFinLexem(edo.getFinLex());
            for(int i= this.numGlobales;i>edo.getGlobal();i--){
                this.tokensGlobales.remove(this.numGlobales);
                this.numGlobales = this.numGlobales -1;
            }
        }
    }
    // ------------------------------------------
    public int buscarTransicion(int estado,char caracter){
        int transicion = alfabeto.indexOf(caracter);;
        int nuevoEdo = tabla.get(estado).get(transicion);
        return nuevoEdo;
    }
    public int getToken(int estado){
        return this.tabla.get(estado).get(this.alfabeto.size());
    }
    public void agregarTokenGlobal(List<Integer> enteros){
                this.tokensGlobales.add(enteros);
                this.numGlobales=this.numGlobales+1;
    }
    
    public void setIniLexem(int nuevaPosicion){
        this.iniLexem = nuevaPosicion;
    }
    public void setFinLexem(int nuevaPosicion){
        this.finLexem = nuevaPosicion;
    }
    public void setAuxFinLexem(int nuevaPosicion){
        this.auxFinLexem = nuevaPosicion;
    }
    public int getPosicionActual(){
    return this.iniLexem;
    }
    
}
