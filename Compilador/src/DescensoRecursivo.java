/**
 *
 * @author Alejandro Colin
 * 
 */
import java.util.ArrayList;
import java.util.List;

public class DescensoRecursivo {
    AnalizadorLexico lexic;
    int token;
    AFN afnDeLaExpresion;
    static int OR = 10;
    static int CONC = 20;
    static int CERR_POS = 30;
    static int CERR_KLEEN = 40;
    static int OPC = 50;
    static int P_I = 60;
    static int P_D = 70;
    static int C_I = 80;
    static int C_D = 90;
    static int GUION = 100;
    static int SIMB = 110;
    List<Regla> ArrReglas;
    List<String> simbNoTerminales;
    
    public DescensoRecursivo(AnalizadorLexico lex, int tok)
    {
        lexic = lex;
        token = tok;
        // Descenso recursivo
        // E -> TEp
        // Ep -> or TEp | ɛ
        // T -> CTp
        // Tp -> &CTp | ɛ
        // C -> FCp
        // Cp -> +Cp | *Cp | ?Cp | ɛ
        // F -> (E) | [SIMB - SIMB] | SIMB   
    }
                    
    private boolean E(AFN f)
    {
        // E -> TEp
        if(T(f))
            if(Ep(f))
                return true;
        return false;
    }
    private boolean Ep(AFN f)
    {
        // Ep -> or TEp | ɛ
        ResultadoAnalizadorLex t;
        AFN f2 = new AFN();
        t = lexic.yyLex();
        if(t.token == OR)
        {
            if(T(f2))
                f.copiar(f.unir(f2, f.getId()+1, token));
                if(Tp(f))
                    return true;
            return false;
        }
        lexic.regresarToken();
        return true;
    }
    private boolean T(AFN f)
    {
        // T -> CTp
        if(C(f))
            if(Tp(f))
                return true;
        return false;
    }
    private boolean Tp(AFN f)
    {
        // Tp -> &CTp | ɛ
        ResultadoAnalizadorLex t;
        AFN f2 = new AFN();
        t = lexic.yyLex();
        if(t.token == CONC)
        {
            if(C(f2))
            {
                f.copiar(f.concatenar(f2, f.getId()+1, token));
                if(Tp(f))
                    return true;
            }
            return false;
        }
        lexic.regresarToken();
        return true;
    }
    private boolean C(AFN f)
    {
        // C -> FCp
        if(F(f))
            if(Cp(f))
                return true;
        return false;
    }
    private boolean Cp(AFN f)
    {
        // Cp -> +Cp | *Cp | ?Cp | ɛ
        ResultadoAnalizadorLex t;
        t = lexic.yyLex();
        if(t.token == CERR_POS)
        {
            f.copiar(f.cerrarTransitiva(f.getId()+1, token));
            if(Cp(f))
                return true;
            return false;
        }
        else if(t.token == CERR_KLEEN)
        {
            f.copiar(f.cerrarKleen(f.getId()+1, token));
            if(Cp(f))
                return true;
            return false;
        }
        else if(t.token == OPC)
        {
            f.copiar(f.opcional(f.getId()+1, token));
            if(Cp(f))
                return true;
            return false;
        }
        lexic.regresarToken();
        return true;
    }
    private boolean F(AFN f)
    {
        // F -> (E) | [SIMB - SIMB] | SIMB   
        ResultadoAnalizadorLex t, t2, t3;
        t = lexic.yyLex();
        if(t.token == P_I)
        {
            if(E(f))
            {    
                t = lexic.yyLex();
                if(t.token == P_D) return true;
            }
            return false;
        }
        else if(t.token == C_I)
        {
            t = lexic.yyLex();
            if(t.token == SIMB)
            {
                t2 = lexic.yyLex();
                if(t2.token == GUION)
                {
                    t2 = lexic.yyLex();
                    if(t2.token == SIMB)
                    {
                        t3 = lexic.yyLex();
                        if(t3.token == C_D)
                        {
                            char c1 = t.lexema.charAt(0);
                            if(c1 =='\\') c1 = t.lexema.charAt(1);
                            char c2 = t2.lexema.charAt(0);
                            if(c2 =='\\') c2 = t2.lexema.charAt(1);
                            f.copiar(f.crearBasico(c1, c2, token));
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        else if(t.token == SIMB)
        {
            char c = t.lexema.charAt(0);
            if(c=='\\') c = t.lexema.charAt(1);
            f.copiar(f.crearBasico(c, token));
            return true;
        }
        return false;
    }

    public AFN getAfn()
    {
        afnDeLaExpresion = new AFN();
        boolean iniciar = E(afnDeLaExpresion);
        if(iniciar) return afnDeLaExpresion;
        return null;
    }
    //--------------- Acciones semánticas ---------------------
    //  me falta obtener los token "sc,or,simbolo,flecha" de los globales
    public boolean g(){
        return reglas();
    }
    
    public boolean reglas(){
        int tok;
        int sc = 20; //se cambia por el token que tenga el caracter ';'
        if(regla()){
            tok = lexic.yyLex().getToken();
            if(tok == sc)
                if(reglasP())
                    return true;
            return false;
        }
        return false;
    }
    
   public boolean reglasP(){
       int tok;
       int sc =20;//se cambia por el token que tenga el caracter ';'
       EdoLexic e = lexic.getEdo();
       if(regla()){
           tok = lexic.yyLex().getToken();
           if(tok == sc)
               if(reglasP())
                   return true;
           return false;
       }
       lexic.setEdo(e); //Equivalente a regresar Token
       return true;
   }
   public boolean regla(){
       SimbLadoIzq simbLadoIzq = new SimbLadoIzq();
       int tok;
       int flecha= 30; //se cambia por el token que tenga el caracter "->"
       if(ladoIzq(simbLadoIzq)){//Obtenemos la cadena del simbolo del lado Izquierdo
           tok = lexic.yyLex().getToken();
           if(tok == flecha) //Tokens.flecha
               if(ladosDerechos(simbLadoIzq.getStrSimbLadoIzq()))//Pasamos la cadena del simbolo de lado Izquierdo
                   return true;
           return false;
       }
       return false;
   }
   public boolean ladoIzq(SimbLadoIzq s){
       int tok;
       tok = lexic.yyLex().getToken();
       int simbolo = 40;
       if(tok == simbolo){//Tokens.simbolo
           s.setStrSimbLadoIzq(lexic.yyLex().getLexema());
           return true;
       }
       return false;
       }
   public boolean ladosDerechos(String simbLadoIzq){
       NodoRegla nodoSimb = new NodoRegla();
       if(ladoDerecho(nodoSimb)){
           Regla aux= new Regla(simbLadoIzq,nodoSimb);
           simbNoTerminales.add(simbLadoIzq);
           ArrReglas.add(aux);
           if(ladosDerechosP(simbLadoIzq))
               return true;
       }
       return false;
   }
   
   public boolean ladosDerechosP(String s){
       int tok;
       NodoRegla nodoSimb = new NodoRegla();
       int or= 50;
       tok= lexic.yyLex().getToken();
       if(tok == or){
           if(ladoDerecho(nodoSimb)){
               Regla aux = new Regla(s,nodoSimb);
               ArrReglas.add(aux);
               if(ladosDerechosP(s)){
                   return true;
               }
            return false;
           }
           lexic.regresarToken();;
           return true;
       }
       return false;
   }
   
   public boolean ladoDerecho(NodoRegla nodoSimb){
       return listaSimbolos(nodoSimb);
   }
   
   public boolean listaSimbolos(NodoRegla nodoSimb){
       NodoRegla nodoAux = new NodoRegla();
       int tok;
       int simbolo = 60;
       tok = lexic.yyLex().getToken();
       
       if(tok == simbolo){
           nodoSimb = new NodoRegla(lexic.yyLex().getLexema());
           if(listaSimbolosP(nodoAux)){
               nodoSimb.setEdoSiguiente(nodoAux);
               return true;
           }
       }
       return false;
   }
   public boolean listaSimbolosP(NodoRegla nodoSimb){
       NodoRegla nodoAux = new NodoRegla();
       int tok;
       tok = lexic.yyLex().getToken();
       int simbolo = 60;
       if(tok == simbolo){
           nodoSimb = new NodoRegla(lexic.yyLex().getLexema());
           if(listaSimbolosP(nodoAux)){
               nodoSimb.setEdoSiguiente(nodoAux);
               return true;
           }
           return false;
       }
       nodoSimb = null;
       lexic.regresarToken();
       return true;
   }
   public List<String> First(NodoRegla l){
       NodoRegla aux;
       List<String> R = new ArrayList<>();
       aux = l;
       if(aux.isTerminal() || (aux.getStr() == "ɛ")){
           R.add(aux.getStr());
           return R;
       }
       
       R.clear();
       for(int i=0; i<ArrReglas.size();i++){
           if(ArrReglas.get(i).getStrSimb() == aux.getStr())
           {
               List<String> c = First(ArrReglas.get(i).getList());
               if((c.contains("ɛ")) && (aux.siguiente != null)){
                   c.remove("ɛ");
                   c.addAll(First(aux.siguiente));
               }
               R.addAll(c);
           }
       }
       return R;
   }
          // puede regresar List<int(tokens)>
   public List<String> Follow(String A){
       List<String> R = new ArrayList<>();
       List<String> aux =new ArrayList<>();
       NodoRegla n;
       String simbInicial ="inicial"; //Obtener inicial de G
       if(A == simbInicial)
           R.add("$");
       for(int i =0; i<ArrReglas.size();i++){
           n = buscarSimbolo(A,i);
           if(n== null)
               continue;
           if(n.siguiente==null){
               if(A==ArrReglas.get(i).getStrSimb())
                   continue;
               R.addAll(Follow(ArrReglas.get(i).getStrSimb()));
           }
           else{
               aux=First(n.siguiente);
               if(aux.contains("ɛ")){
                   aux.remove("ɛ");
                   R.addAll(aux);
                   if(A != ArrReglas.get(i).getStrSimb()){
                       R.addAll(Follow(ArrReglas.get(i).getStrSimb()));
                   }
               }
               else{
                   R.addAll(aux);
               }
           }
       }
       return R;
   }
   public NodoRegla buscarSimbolo(String A, int indice){
       boolean finNodos =true;
       NodoRegla aux =ArrReglas.get(indice).getList();
       while(finNodos){
           if(aux==null){
               finNodos =true;
           }
           else{
               if(A==aux.getStr()){
                   return aux;
               }
               else{
                   aux=aux.getSiguiente();
               }
           }
       }
       return null;
   }
}