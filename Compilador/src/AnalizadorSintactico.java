/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Brando Sanchez
 */
public class AnalizadorSintactico {
    
    private AFD unAFD;
    private String sigma;
    ResultadoAnalizadorLex resultLexic;
    AnalizadorLexico lexic;
    
    public AnalizadorSintactico() {
    }

    public AnalizadorSintactico(AFD unAFD, String sigma) {
        this.unAFD = unAFD;
        this.sigma = sigma;
        resultLexic  = new ResultadoAnalizadorLex();
        lexic = new AnalizadorLexico(unAFD,sigma);
    }
    
    public boolean Ini(){
        int token;
        if(E()){
            token = lexic.yyLex().getToken();
            if(token == 0){
                return true;
            }
        }
        return false;
    }
    public boolean E(){
        float w=0;
        if(T(w)){
            if(Ep(w))
                return true;
        }
        return false;
    }
    
    public boolean Ep(float v){
        int token;
        float w=0;
        token = lexic.yyLex().getToken();
        
        if(token == 10 || token == 20){ //asumiendo que 10 es el token de suma y 20 el de resta
            if(T(w))
                //accion semantica
                if(token==10){v = v + w;}
                if(token==20){v = v - w;}
                if(Ep(v))
                    return true;
            return false;
        }
        //considerando epsilon
        lexic.regresarToken();
        
        return true;
        
    }
    public boolean T(float w){
        
        if(F())
            if(Tp(w))
                return true;
        return false;
    }
    
    public boolean Tp(float v){
        int token;
        float w=0;
        token = lexic.yyLex().getToken();
        
        if(token == 30 || token == 40){ //asumiendo que 30 es el token de PRODUCTO y 40 el de DIVISION
            if(F())
                if(Tp(v))
                    return true;
            return false;
        }
        //considerando epsilon
        lexic.regresarToken();
        
        return true;
        
    }
    
    public boolean F(){
        
        int token;
        
        token = lexic.yyLex().getToken();
        //Asumiendo que 50 es PAR_I, 60 PAR_D Y 70 NUM.
        switch(token){
            case 50: 
                if(E()){
                    token = lexic.yyLex().getToken();
                    if(token == 60)
                        return true;
                }
                return false;
            case 70:
                return true;
        }
        return false;
    }
}
