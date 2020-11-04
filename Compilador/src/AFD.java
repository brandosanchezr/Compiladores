/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.List;

/**
 *
 * @author Brando Sanchez
 * @author Alejandro Colin
 * @author Alexis Trujillo
 * 
 */
public class AFD extends AFN{

    List<List<Integer>> tabla;

    public AFD(AFN pre, List<List<Integer>> tn) {
        setAlfabeto(pre.getAlfabeto());
        setEdoInicial(pre.getEdoInicial());
        setEdosAFN(pre.getEdosAFN());
        setEdosAceptacion(pre.getEdosAceptacion());
        setId(getId());
        tabla = tn;
    }

    public void setTabla(List<List<Integer>> t)
    {
        tabla = t;
    }

    public List<List<Integer>> getTabla()
    {
        return tabla;
    }

    @Override
    public String toString() { 
        return String.format("ID AFN: " + id + "\n" +
                             "Estado inicial: " + edoInicial.toString() + "\n" +
                             "Alfabeto: " + alfabeto.toString() + "\n" +
                             "Estados de aceptacion: " + edosAceptacion.toString() + "\n" +
                             "Estados del AFD: " + edosAFN.toString() +"\n" +
                             "Tabla AFD: " + tabla.toString() +"\n"
        ); 
    }

}
