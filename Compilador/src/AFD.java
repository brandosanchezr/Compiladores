/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
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
        setId(pre.getId());
        tabla = tn;
    }

    public AFD(String nombreArchivo)
    {
        leerArchivoAFD(nombreArchivo);
    }

    public void setTabla(List<List<Integer>> t)
    {
        tabla = t;
    }

    public List<List<Integer>> getTabla()
    {
        return tabla;
    }

    public boolean crearArchivoAFD(String nombreArchivo)
    {
        try
        {
            FileWriter archivoOS = new FileWriter(nombreArchivo+".txt");
            archivoOS.write(edosAFN.size() + " " + alfabeto.size() + "\n");
            for(char c : alfabeto)
                archivoOS.write(c + " ");
            for(List<Integer> t : tabla)
            {
                archivoOS.write("\n" + t.get(0));
                for(int i = 1; i < t.size(); i++)
                    archivoOS.write(" " + t.get(i));
            }
            archivoOS.close();
        }
        catch(Exception e)
        {
            System.out.println("No se pudo crear el archivo " + nombreArchivo + ".txt" + " : " + e.toString());
            return false;
        }   
        return true;
    }
    public boolean leerArchivoAFD(String nombreArchivo)
    {
        try
        {
            FileReader archivoIS = new FileReader(nombreArchivo+".txt");
            Scanner scan = new Scanner(archivoIS);

            int tamEstados = scan.nextInt();
            int tamAlfabeto = scan.nextInt();
            scan.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            List<List<Integer>> nuevaTabla = new ArrayList<List<Integer>>();
            List<Character> nuevoAlfabeto = new ArrayList<Character>();
            
            for(int alf = 0; alf < tamAlfabeto; alf++ )
                    nuevoAlfabeto.add(scan.next().charAt(0));
            scan.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for(int edos = 0; edos < tamEstados; edos++)
            {
                List<Integer> nt = new ArrayList<Integer>();
                for(int alf = 0; alf <= tamAlfabeto; alf++ ) //<= por que leemos el token tambien
                    nt.add(scan.nextInt());
                nuevaTabla.add(nt);
                scan.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
            }

            generarPorTabla(nuevaTabla, nuevoAlfabeto);
            scan.close();
            archivoIS.close();
        }
        catch(Exception e)
        {
            System.out.println("No se pudo leer el archivo " + nombreArchivo + ".txt" + " : " + e.toString());
            return false;
        }
        return true;
    }

    public void generarPorTabla()
    {  
        this.alfabeto.remove(Character.valueOf('ɛ'));
        //Los estados del nuevo afd 
        List<Estado> nuevosEstados = new ArrayList<Estado>();
        List<Estado> nuevosEstadosAceptacion = new ArrayList<Estado>();
        //Creamos estados
        int tamañoAlfabeto = alfabeto.size();
        for(int edo = 0; edo < tabla.size(); edo++)
            nuevosEstados.add(new Estado(  edo  , null , false , false, -1));
        nuevosEstados.get(0).setEdoInicial(true);
        //Leemos tabla para hacer transiciones
        for(int edo = 0; edo < tabla.size(); edo++)
        {   
            List<Transicion> tr = new ArrayList<Transicion>();
            for(int a = tamañoAlfabeto; a >= 0; a--)
            {
                int valor = tabla.get(edo).get(a);
                if(valor >= 0)
                {
                    if(a == tamañoAlfabeto) // token
                    {
                        nuevosEstados.get(edo).setEdoFinal(true); 
                        nuevosEstados.get(edo).setToken(valor);  
                        nuevosEstadosAceptacion.add(nuevosEstados.get(edo));   
                        continue;
                    }
                    Transicion nueva_transicion = new Transicion(alfabeto.get(a));
                    nueva_transicion.agregarDestino(nuevosEstados.get(valor));
                    tr.add(nueva_transicion);  
                }
            }
            nuevosEstados.get(edo).setTransciciones(tr);
        }
        setId(0);
        setEdoInicial(nuevosEstados.get(0));
        setEdosAFN(nuevosEstados);
        setEdosAceptacion(nuevosEstadosAceptacion);
    }
    public void generarPorTabla(List<List<Integer>> t, List<Character> a)
    {
        this.tabla = t;
        this.alfabeto = a;
        generarPorTabla();
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
