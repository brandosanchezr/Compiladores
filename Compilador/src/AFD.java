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
public class AFD extends AFN{

    int[][] tabla;

    public AFD(AFN pre) {
        setAlfabeto(pre.getAlfabeto());
        setEdoInicial(pre.getEdoInicial());
        setEdosAFN(pre.getEdosAFN());
        setEdosAceptacion(pre.getEdosAceptacion());
        setId(getId());
        //tabla = t;
    }

    public void setTabla(int[][] t)
    {
        tabla = t;
    }

    public int[][] getTabla()
    {
        return tabla;
    }

    @Override
    public String toString() { 
        return String.format("ID AFN: " + id + "\n" +
                             "Estado inicial: " + edoInicial.toString() + "\n" +
                             "Alfabeto: " + alfabeto.toString() + "\n" +
                             "Estados de aceptacion: " + edosAceptacion.toString() + "\n" +
                             "Estados del ANF: " + edosAFN.toString() +"\n" 
        ); 
    }

}
