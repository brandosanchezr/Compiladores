import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alejandro Colin
 * 
 */
public class GeneradorAnalizadorLex {
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
    String nombreArchivo; 
    AFD afdAnalizado;

    public GeneradorAnalizadorLex(String s)
    {
        nombreArchivo = s;
    }

    private AFD generarClasesLexicas()
    {
        List<AFN> clasesLexicas = new ArrayList<AFN>();
        AFN f = new AFN();
        clasesLexicas.add(f.crearBasico('|', OR));
        clasesLexicas.add(f.crearBasico('&', CONC));
        clasesLexicas.add(f.crearBasico('+', CERR_POS));
        clasesLexicas.add(f.crearBasico('*', CERR_KLEEN));
        clasesLexicas.add(f.crearBasico('?', OPC));
        clasesLexicas.add(f.crearBasico('(', P_I));
        clasesLexicas.add(f.crearBasico(')', P_D));
        clasesLexicas.add(f.crearBasico('[', C_I));
        clasesLexicas.add(f.crearBasico(']', C_D));
        clasesLexicas.add(f.crearBasico('-', GUION));
        AFN simb1, simb2, simb3, simb4, simb5;
        simb1 = f.crearBasico( 'a' , 'z', SIMB);
        simb2 = f.crearBasico( 'A' , 'Z', SIMB);
        simb3 = f.crearBasico( '0' , '9', SIMB);
        simb4 = f.crearBasico( '\\' , SIMB);
        simb5 = f.crearBasico( (char) 0x00 , (char) 0x7F, SIMB);
        simb1 = simb1.unir(simb2, SIMB, SIMB);
        simb1 = simb1.unir(simb3, SIMB, SIMB);
        simb4 = simb4.concatenar(simb5, SIMB, SIMB);
        simb1 = simb1.unir(simb4, SIMB, SIMB);
        clasesLexicas.add(simb1);
        System.out.println(simb1.getAlfabeto().toString());
        AFN afnParaAfd = new AFN();
        afnParaAfd = afnParaAfd.unirParaAFD(clasesLexicas, 0);
        AFD nuevoAfd = afnParaAfd.convertirAFN();

        return nuevoAfd;
    }
    
    private boolean leerArchivo()
    {
        List<AFN> expresiones = new ArrayList<AFN>();

        try
        {
            FileReader archivoIS = new FileReader(nombreArchivo+".txt");
            Scanner scan = new Scanner(archivoIS);
            AFD ClasesLexicas = generarClasesLexicas();
            while(scan.hasNextLine())
            {
                String cadena = scan.next();
                int token = Integer.parseInt(scan.next());
                AnalizadorLexico Lexic = new AnalizadorLexico(ClasesLexicas, cadena);
                DescensoRecursivo desc = new DescensoRecursivo(Lexic, token);
                AFN exp = desc.getAfn();
                expresiones.add(exp);
            }

            scan.close();
            archivoIS.close();
        }
        catch(Exception e)
        {
            System.out.println("No se pudo abrir el archivo " + nombreArchivo + ".txt" + " : " + e.toString());
            return false;
        }

        AFN paraUnir = new AFN();
        paraUnir = paraUnir.unirParaAFD(expresiones, 0);
        
        afdAnalizado = paraUnir.convertirAFN();
        
        return true;
    }

    public AFD getAFDAnalizado()
    {
        if(leerArchivo())
            return afdAnalizado;
        else return null;
    }
    /*
    public AnalizadorLexico generarAnalizador(String cadena)
    {
        if(leerArchivo())
            return new AnalizadorLexico(afdAnalizado, cadena);
        else
            return null;
    }
    */
}

