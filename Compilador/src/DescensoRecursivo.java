/**
 *
 * @author Alejandro Colin
 * 
 */

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
                            f.copiar(f.crearBasico(t.lexema.charAt(0), t2.lexema.charAt(0), token));
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        else if(t.token == SIMB)
        {
            f.copiar(f.crearBasico(t.lexema.charAt(0), token));
            return true;
        }
        return false;
    }

    public AFN getAfn()
    {
        afnDeLaExpresion = new AFN();
        E(afnDeLaExpresion);
        return afnDeLaExpresion;
    }
}