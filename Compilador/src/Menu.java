
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
 */
public class Menu {
    
    List<AFN> list_afn = new ArrayList<>();
    List<AFD> list_afd = new ArrayList<>();
    
    public Menu() {
    }
    
    public void iniciar() throws IOException {
        
        
        boolean out = true;
        Scanner escaneo = new Scanner(System.in);
        char opcion;
        
        System.out.println("**************Menu**************");
        System.out.println("1.Crear un AFN mediante thompson.");
        System.out.println("2.Unir ANF's para un afn y afd para analizador lexico.");
        System.out.println("3.Convertir un AFN a AFD.");
        System.out.println("4.Concatenar AFN's.");
        System.out.println("5.Mostrar AFNS.");
        System.out.println("6.Mostrar un AFN especifico.");
        System.out.println("7.Cerradura transitiva.");
        System.out.println("8.Cerradura kleen.");
        System.out.println(".Salir.");
        
        while(out){

            System.out.println("\nIngresa una opcion:");
            opcion = escaneo.next().charAt(0);
            
            switch(opcion){
            
                case '1':
                    crearAFN();
                    break;

                case '2':
                    elegirAFNaUnir();
                    break;

                case '3':
                    elegirAFNaConvertir();
                    break;

                case '4':
                    elegirAFNaConcatenar();
                    break;
                    
                case '5':
                    mostrarAFNs();
                    break;

                case '6':
                    mostrarUnAFN();
                    break;
                
                case '7':
                    elegirAFNaCerrarT();
                    break;
                
                case '8':
                    elegirAFNaCerrarK();
                    break;
                    
                default:
                    System.out.println("Elige opcion valida.");
            }
        }
    }
    
    public void actualizarIds(){
        
        for(int i=0; i<list_afn.size(); i++){
            list_afn.get(i).setId(i);
        }
    }
    
    
    public void mostrarAFNs(){
        list_afn.stream().forEach((e)->{
            System.out.println("AFD ID: " + e.getId());
        });
    }
    
    
    public void crearAFN(){
        Scanner escaneo = new Scanner(System.in);
        
        System.out.println("Crear un AFN mediante thompson");
        
        System.out.println("Ingresa el primer caracter para el AFN: ");
        char caracter;
        caracter = escaneo.next().charAt(0);
        
        System.out.println("Ingresa el segundo caracter para el AFN: ");
        char caracter_2;
        caracter_2 = escaneo.next().charAt(0);
        
        System.out.println("Ingresa el token para el AFN: ");
        int token;
        token = escaneo.nextInt();
        
        if((int)caracter < (int)caracter_2){
            AFN miAFN = new AFN();
            miAFN = miAFN.crearBasico(caracter,caracter_2, token);
            list_afn.add(miAFN);
            actualizarIds();
            System.out.println("Creado con exito.");
        }else
            System.out.println("Caracteres invalidos en rango");
        

    }
    
    public void mostrarUnAFN(){
        mostrarAFNs();
        Scanner escaneo = new Scanner(System.in);
        int id;
        
        System.out.println("Ingresa el id del AFN a mostrar:");
        id = escaneo.nextInt();
        
        try {
            System.out.println(list_afn.get(id));
        } catch (Exception e) {
            System.out.println("ID NO VALIDO");
        }
        
        
    }
    public void elegirAFNaUnir(){
        mostrarAFNs();
        Scanner escaneo = new Scanner(System.in);
        int id_1, id_2, token;
                
        System.out.println("Ingresa el id del primer AFN a unir");
        id_1 = escaneo.nextInt();
        
        System.out.println("Ingresa el id del segundo AFN a unir");
        id_2 = escaneo.nextInt();
        
        System.out.println("Ingresa el token del nuevo AFN");
        token = escaneo.nextInt();
        
        try {
            AFN unidoAFN = list_afn.get(id_1).unir(list_afn.get(id_2), list_afn.size(), token);
            list_afn.add(unidoAFN);
        } catch (Exception e) {
            System.out.println("Error al unir(ID INVALIDO).");
        }
    }
    
    public void elegirAFNaConvertir(){
        mostrarAFNs();
        Scanner escaneo = new Scanner(System.in);
        int id;
                
        System.out.println("Ingresa el id del AFN a convertir");
        id = escaneo.nextInt();
        try {
            list_afd.add(list_afn.get(id).convertirAFN());
        } catch (Exception e) {
            System.out.println("ID NO VALIDO");
        }
        actualizarIds();
    }
    
    public void elegirAFNaConcatenar(){
        mostrarAFNs();
        Scanner escaneo = new Scanner(System.in);
        int id_1, id_2, token;
                
        System.out.println("Ingresa el id del primer AFN a concatenar");
        id_1 = escaneo.nextInt();
        
        System.out.println("Ingresa el id del segundo AFN a concatenar");
        id_2 = escaneo.nextInt();
        
        System.out.println("Ingresa el token del nuevo AFN");
        token = escaneo.nextInt();
        
        try {
            AFN concatenadoAFN = list_afn.get(id_1).concatenar(list_afn.get(id_2), list_afn.size(), token);
            list_afn.add(concatenadoAFN);
        } catch (Exception e) {
            System.out.println("Error al unir(ID INVALIDO).");
        }
        
        actualizarIds();
    }
    
    public void elegirAFNaCerrarT(){
        mostrarAFNs();
        Scanner escaneo = new Scanner(System.in);
        int id_1, token;
                
        System.out.println("Ingresa el id del AFN a aplicar cerradura transitiva");
        id_1 = escaneo.nextInt();
        
        System.out.println("Ingresa el token del nuevo AFN");
        token = escaneo.nextInt();
        
        try {
            AFN cerradoTAFN = list_afn.get(id_1).cerrarTransitiva(list_afn.size(), token);
            list_afn.add(cerradoTAFN);
        } catch (Exception e) {
            System.out.println("Error al unir(ID INVALIDO).");
        }
    }
    
    public void elegirAFNaCerrarK(){
        mostrarAFNs();
        Scanner escaneo = new Scanner(System.in);
        int id_1, token;
                
        System.out.println("Ingresa el id del AFN a aplicar cerradura KLEEN");
        id_1 = escaneo.nextInt();
        
        System.out.println("Ingresa el token del nuevo AFN");
        token = escaneo.nextInt();
        
        try {
            AFN cerradoKAFN = list_afn.get(id_1).cerrarKleen(list_afn.size(), token);
            list_afn.add(cerradoKAFN);
        } catch (Exception e) {
            System.out.println("Error al unir(ID INVALIDO).");
        }
    }
    
    public void elegirAFNaOpcional(){
        mostrarAFNs();
        Scanner escaneo = new Scanner(System.in);
        int id_1, token;
                
        System.out.println("Ingresa el id del AFN a aplicar operador opcional");
        id_1 = escaneo.nextInt();
        
        System.out.println("Ingresa el token del nuevo AFN");
        token = escaneo.nextInt();
        
        try {
            AFN opcionalAFN = list_afn.get(id_1).opcional(list_afn.size(), token);
            list_afn.add(opcionalAFN);
        } catch (Exception e) {
            System.out.println("Error al unir(ID INVALIDO).");
        }
    }
    
    public void validacionCadena(){
        
        mostrarAFNs();
        Scanner escaneo = new Scanner(System.in);
        int id;
        String cadena;        
        System.out.println("Ingresa el id del AFN a utilizar para validar la cadena:");
        id = escaneo.nextInt();
        
        System.out.println("Ingresa la cadena: ");
        cadena = escaneo.nextLine();
        
        
        if(list_afn.get(id).validarCadena(cadena)){
            System.out.println("La cadena es valida para dicho AFN");
        }
    }
}

