
import java.util.ArrayList;
import java.util.Iterator;
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
public class AFN {
    
    int id;
    Estado edoInicial;
    List<Character> alfabeto;
    List<Estado> edosAceptacion;
    List<Estado> edosAFN;

    public AFN() {
    }

    public AFN(int id, Estado edoInicial, List<Character> alfabeto, List<Estado> edosAceptacion, List<Estado> edosAFN) {
        this.id = id;
        this.edoInicial = edoInicial;
        this.alfabeto = alfabeto;
        this.edosAceptacion = edosAceptacion;
        this.edosAFN = edosAFN;
    }
    
    //-----------------  BLOQUE GET  -----------------
    public int getId() {
        return id;
    }
    
    public Estado getEdoInicial() {
        return edoInicial;
    }

    public List<Character> getAlfabeto() {
        return alfabeto;
    }

    public List<Estado> getEdosAceptacion() {
        return edosAceptacion;
    }

    public List<Estado> getEdosAFN() {
        return edosAFN;
    }
    
    //-----------------  BLOQUE SET  -----------------

    public void setId(int id) {
        this.id = id;
    }

    public void setEdoInicial(Estado edoInicial) {
        this.edoInicial = edoInicial;
    }

    public void setAlfabeto(List<Character> alfabeto) {
        this.alfabeto = alfabeto;
    }

    public void setEdosAceptacion(List<Estado> edosAceptacion) {
        this.edosAceptacion = edosAceptacion;
    }

    public void setEdosAFN(List<Estado> edosAFN) {
        this.edosAFN = edosAFN;
    }
    
    //Metodos
    
    public AFN crearBasico(Character c){
        
        List<Character> alfabeto = new ArrayList<Character>(c);
        
        List<Estado> edosAFN = new ArrayList<Estado>();
        
        Estado segundoEdo = new Estado(1, null, false, true, 10);
        
        Transicion unaTransicion = new Transicion(c);
        unaTransicion.agregarDestino(segundoEdo);
        
        List<Transicion> transiciones = new ArrayList<Transicion>();
        transiciones.add(unaTransicion);
        
        Estado edoInicial = new Estado(0,transiciones,true,false,0);
        edosAFN.add(edoInicial);
        edosAFN.add(segundoEdo);
     
        List<Estado> edosAceptacion = new ArrayList<Estado>();
        
        for (Iterator<Estado> iterator = edosAFN.iterator(); iterator.hasNext();) {
            Estado next = iterator.next();
            if(next.isEdoFinal())
                edosAceptacion.add(next);
        }
        
        return new AFN(0, edoInicial, alfabeto, edosAceptacion, edosAFN);
    }
    
    public AFN unir(AFN unAFN){
        return new AFN();
    }
    
    public AFN concatenar(AFN unAFN,int idNuevoAFN,int nuevoToken){
        //se unen los dos alfabetos de los AFN
        List<Character> nuevoAlfabeto = new ArrayList<Character>(this.getAlfabeto());
        nuevoAlfabeto.addAll(unAFN.getAlfabeto());
        
        List<Estado> nuevoEdosAFN = new ArrayList<Estado>();
        List<Estado> nuevoEdosAceptacion = new ArrayList<Estado>();
        int i=0;    //contador estados del nuevo automata
        //agregamos estados del PRIMER AFN
        for (i=0; i < this.getEdosAFN().size(); i++) {
            Estado aux = (Estado) this.getEdosAFN().get(i);
            aux.setId(i);   //cambiamos el id de los estados para el nuevo automata
            //cuando llega al ultimo, lo concatenamos con el primero del segundo AFN
            if(i==this.getEdosAFN().size()-1){
                aux.setEdoFinal(false); //quitamos aceptación al primer automata
                aux.setTransciciones(unAFN.getEdoInicial().getTransciciones()); //Las transiciónes del segundo serán las del primero
            }
            nuevoEdosAFN.add(aux);
        }
        //agregamos estados del SEGUNDO AFN
        for (int j =1;j<unAFN.getEdosAFN().size();j++){
            Estado aux = (Estado) unAFN.getEdosAFN().get(j);
            aux.setId(i);
            //Agregamos estado de aceptación
            if(aux.isEdoFinal()){
                aux.setToken(nuevoToken);
                nuevoEdosAceptacion.add(aux);
            }
            nuevoEdosAFN.add(aux);
            i++;
        }
        return new AFN(idNuevoAFN,this.edoInicial,nuevoAlfabeto,nuevoEdosAceptacion,nuevoEdosAFN);
    }
    
    public AFN cerrarTransitiva(){
      return new AFN();  
    }
    
    public AFN cerrarKleen(){
        return new AFN();
    }
    
    public AFN opcional(){
        return new AFN();
    }
    
    public List<Estado> cerrarEpsilon(List<Estado> estados){
        return new ArrayList<>();
    }
    
    public List<Estado> cerrarEpsilon(Estado unEstado){
        return new ArrayList<>();
    }
    
    public List<Estado> mover(List<Estado> estados, Character simbolo){
        return new ArrayList<>();
    }
    
    public List<Estado> mover(Estado unEstado, Character simbolo){
        return new ArrayList<>();
    }
    
    public  List<Estado> irA(List<Estado> estados, Character simbolo){
        return cerrarEpsilon(mover(estados, simbolo));
    }
    
    public int analizarCadena(String cadena){
        return -1;
    }
    
    public AFD convertirAFN(){
        return new AFD();
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
