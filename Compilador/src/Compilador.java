
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Brando Sanchez
 */


public class Compilador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
            //Automata basico 1
        AFN miAFN = new AFN();
        miAFN = miAFN.crearBasico('c');
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
        AFN opcional =  miAFN.opcional(7, 60);
        System.out.println(opcional);
    }
    
}
