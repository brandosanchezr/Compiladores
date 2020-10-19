
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
        
        AFN miAFN = new AFN();
        miAFN = miAFN.crearBasico('c');
        System.out.println(miAFN);
    }
    
}
