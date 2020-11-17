
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Brando Sanchez
 * @author Alejandro Colin
 * @author Alexis Trujillo
 * 
 */


public class Compilador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
            //Automata basico 1
        //AFN miAFN = new AFN();
        //miAFN = miAFN.crearBasico('c');
            //Automata básico 2
        //AFN miAFN2 = new AFN();
        //miAFN2 = miAFN2.crearBasico('d');
            //Automata Unido 1 y 2
        //AFN unidoAFN = miAFN.unir(miAFN2, 4, 30);
            //Automata concatenando 1 y 2
        //AFN concatAFN = miAFN.concatenar(miAFN2, 3,20);
            //Automata Cerradura Transitiva
        //AFN transitiva = miAFN.cerrarTransitiva(5, 40);
            //Automata Cerradura Kleene
        //AFN kleene = miAFN.cerrarKleen(6, 50);
        //Automata Opcional
        //AFN opcional =  miAFN.opcional(7, 60);
        //System.out.println(opcional);
//        System.out.println("Crear un AFN mediante thompson");
//        System.out.println("Ingresa el caracter para el AFN: ");
//        Scanner escaneo = new Scanner(System.in);
//        char caracter;
//        caracter = escaneo.next().charAt(0);
//        AFN miAFN = new AFN();
//        miAFN = miAFN.crearBasico(caracter);
          Menu miMenu = new Menu();
          miMenu.iniciar();
        
        
//        List<Transicion> transiciones_e = new ArrayList<>();
//        List<Transicion> transiciones_a = new ArrayList<>();
//        
//        Estado e = new Estado(0, transiciones_e, true, false, 0);
//        Estado a = new Estado(1, transiciones_a, true, false, 0);
//        Estado b = new Estado(2, new ArrayList<>(), true, false, 0);
//        Estado c = new Estado(3, new ArrayList<>(), true, false, 0);
//        Estado x = new Estado(4, new ArrayList<>(), true, false, 0);
//        
//        Transicion t_1 = new Transicion('ɛ');
//        t_1.agregarDestino(a);
//        //t_1.agregarDestino(b);
//        t_1.agregarDestino(c);       
//        transiciones_e.add(t_1);
//        e.setTransciciones(transiciones_e);
//        System.out.println(e);
//        
//        Transicion t_2 = new Transicion('ɛ');
//        t_2.agregarDestino(e);
//        t_2.agregarDestino(x);
//        transiciones_a.add(t_2);
//        a.setTransciciones(transiciones_a);
//        System.out.println(a);
//        
//        System.out.println(b);
//        System.out.println(c);
//        System.out.println(x);
//        
//        List<Estado> cerradura_e = AFN.cerrarEpsilon(e);
//        
//        cerradura_e.stream().forEach( (edo)->{
//            System.out.println(edo.getId());
//        } );

//        AFN miAFN = new AFN();
//        miAFN = miAFN.crearBasico('L');
//        
//        AFN miAFN_2 = new AFN();
//        miAFN_2 = miAFN.crearBasico('D');
//        
//        //Automata Unido 1 y 2
//        AFN unidoAFN = miAFN.unir(miAFN_2, 4, 30);
//        
//        //Automata Cerradura Kleene
//        AFN kleene = unidoAFN.cerrarKleen(6, 50);
        
//        kleene.getEdosAFN().stream().forEach((edo)->{
//            System.out.println(kleene.getEdosAFN().indexOf(edo));
//        });
        
//        List<Estado> edos = new ArrayList<>();
//        edos.add(kleene.getEdosAFN().get(0));
//        edos.add(kleene.getEdosAFN().get(1));
//        
//        List<Estado> cerradura_edos = AFN.cerrarEpsilon(edos);
//        
//        //System.out.println(kleene.getEdosAFN().get(0));
//        List<Estado> cerradura_e = AFN.cerrarEpsilon(kleene.getEdosAFN().get(0));
//        
//        
//        cerradura_edos.stream().forEach( (edo)->{
//            System.out.println(edo.getId());
//        } );
//        
//        System.out.println("\n");
//        cerradura_e.stream().forEach( (edo)->{
//            System.out.println(edo.getId());
//        } );
//        System.out.println(kleene.getEdosAFN().get(1));
//        List<Estado> mover = AFN.mover(kleene.getEdosAFN().get(1),'ɛ');
//        mover.stream().forEach( (edo)->{
//            System.out.println(edo.getId());
//        } );
//        List<Estado> edos = new ArrayList<>();
//        edos.add(kleene.getEdosAFN().get(0));
//        edos.add(kleene.getEdosAFN().get(1));
//        edos.add(kleene.getEdosAFN().get(7));
//        List<Estado> irA = AFN.irA(kleene.getEdosAFN(), 'L');
//        irA.stream().forEach( (edo)->{
//            System.out.println(edo.getId());
//        } );
      //------EJERCICIO------------------
        //TOKEN 10
        AFN basico1 = new AFN();
        basico1 = basico1.crearBasico('L','L',5);
        AFN basico2 = new AFN();
        basico2 = basico2.crearBasico('L','L',5);
        AFN basico3 = new AFN();
        basico3 = basico3.crearBasico('D','D',5);
        AFN uniones = basico2.unir(basico3, 0, 5);
            uniones = uniones.cerrarKleen(1, 5);
        AFN token10 = new AFN();
            token10 = basico1.concatenar(uniones, 2, 10);
        //TOKEN 20
        AFN basico4 = new AFN();
            basico4 = basico4.crearBasico('D','D',5);
        AFN token20 = new AFN();
            token20 = basico4.cerrarTransitiva(3, 20);
        //TOKEN 30
        AFN basico5 = new AFN();
            basico5 = basico5.crearBasico('D','D', 5);
        AFN deTrans1 = new AFN();
            deTrans1 = basico5.cerrarTransitiva(4, 5);
        AFN punto = new AFN();
            punto = punto.crearBasico('.','.', 5);
            deTrans1 = deTrans1.concatenar(punto, 5, 5);
        AFN basico6 = new AFN();
            basico6 = basico6.crearBasico('D','D', 5);
        AFN deTrans2 = new AFN();
            deTrans2 = basico6.cerrarTransitiva(6, 5);
        AFN token30 = new AFN();
            token30= deTrans1.concatenar(deTrans2, 7, 30);
        //TOKEN 4O
        AFN token40 = new AFN();
            token40 = token40.crearBasico('M','M', 40);
        //TOKEN 50
        AFN token50 = new AFN();
            token50 = token50.crearBasico('P','P', 50);
        //TOKEN 60
        AFN basico7 = new AFN();
            basico7 = basico7.crearBasico('E','E', 5);
        AFN basico8 = new AFN();
            basico8 = basico8.crearBasico('T','T', 5);
        AFN union = new AFN();
            union = basico7.unir(basico8, 8, 5);
        AFN token60 = new AFN();
            token60 = union.cerrarTransitiva(9, 60);
        //Unir para AFD
        List<AFN> aFNs = new ArrayList<>();
            aFNs.add(token10);
            aFNs.add(token20);
            aFNs.add(token30);
            aFNs.add(token40);
            aFNs.add(token50);
            aFNs.add(token60);
        AFN afnParaAFD = new AFN();
            afnParaAFD = afnParaAFD.unirParaAFD(aFNs, 1);
        AFD nuevoAFD = afnParaAFD.convertirAFN();
        String sigma = "DD.DDTTLLDEMEEP";
        AnalizadorLexico analizarNuevoAFD = new AnalizadorLexico(nuevoAFD,sigma);
        ResultadoAnalizadorLex yyLex1= new ResultadoAnalizadorLex();
                                yyLex1 = analizarNuevoAFD.yyLex();  //primer yyLex  DD,DD
                                System.out.println(yyLex1.toString());
                                yyLex1 = analizarNuevoAFD.yyLex();  //segudno yyLex     TT
                                System.out.println(yyLex1.toString());
                                yyLex1 = analizarNuevoAFD.yyLex();  //tercer yyLex  LLD
                                System.out.println(yyLex1.toString());
                                
                                analizarNuevoAFD.regresarToken();   //PRUEBA REGRESAR TOKEN
                                
                                yyLex1 = analizarNuevoAFD.yyLex();  //cuarto yyLex     E
                                System.out.println(yyLex1.toString());
                                yyLex1 = analizarNuevoAFD.yyLex();  //quinto yyLex      M
                                System.out.println(yyLex1.toString());
                                yyLex1 = analizarNuevoAFD.yyLex();  //sexto yyLex       EE
                                System.out.println(yyLex1.toString());
                                yyLex1 = analizarNuevoAFD.yyLex();  //septimo yyLex     P
                                System.out.println(yyLex1.toString());
                                yyLex1 = analizarNuevoAFD.yyLex();  //octavo yyLex      FIN 
                                System.out.println(yyLex1.toString());
                                
        //---System.out.println(nuevoAFD.toString());
            
            
        //uniones = uniones.cerrarKleen(1, 10);
        //uniones = basico1.concatenar(uniones, 3, 10);
        //---List<AFN> aFNs=new ArrayList<>();
        //---aFNs.add(basico1);
        //---aFNs.add(uniones);
        //---AFN afnParaAFD = new AFN();
        //---afnParaAFD = afnParaAFD.unirParaAFD(aFNs,1);
        //System.out.println(uniones.toString());

        //---AFD nuevoAFD = afnParaAFD.convertirAFN();

        //---System.out.println(nuevoAFD.toString());
        //System.out.println(afnParaAFD.toString());

//        AFN basico1 = new AFN();
//        basico1 = basico1.crearBasico('L');
//        AFN basico2 = new AFN();
//        basico2 = basico2.crearBasico('L');
//        AFN basico3 = new AFN();
//        basico3 = basico3.crearBasico('D');
//
//        AFN uniones = basico2.unir(basico3, 0, 10);
//        uniones = uniones.cerrarKleen(1, 10);
//        uniones = basico1.concatenar(uniones, 3, 10);
//
//        //System.out.println(uniones.toString());
//
//        AFD nuevoAFD = uniones.convertirAFN();
//
//        System.out.println(nuevoAFD.toString());

    }
    
}
