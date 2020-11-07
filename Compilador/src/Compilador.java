
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
