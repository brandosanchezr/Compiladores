
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
        this.alfabeto = new ArrayList<Character>();
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
        
        if(!this.getAlfabeto().contains(c));
            this.alfabeto.add(c);
       
        
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
    
    public AFN unir(AFN unAFN,int idNuevoAFN,int token){
        List<Character> nuevoAlfabeto = new ArrayList<Character>();
        
        nuevoAlfabeto.add('ɛ');        
        nuevoAlfabeto.addAll(this.getAlfabeto());        
        nuevoAlfabeto.addAll(unAFN.getAlfabeto());

        
        //EPSILON INICIALES
        Transicion epsilon1 = new Transicion('ɛ');
        Transicion epsilon2 = new Transicion('ɛ');
                //Set inicial = false
        this.getEdoInicial().setEdoInicial(false);
        unAFN.getEdoInicial().setEdoInicial(false);
      
        epsilon1.agregarDestino(this.getEdoInicial());
        epsilon2.agregarDestino(unAFN.getEdoInicial());
        List<Transicion> transicionesInic = new ArrayList<Transicion>();
        transicionesInic.add(epsilon1);
        transicionesInic.add(epsilon2);
    
        Estado nuevoInicial = new Estado(0,transicionesInic,true,false,0);
        
        //EPSILON FINALES
        int numEdos= this.getEdosAFN().size() + unAFN.getEdosAFN().size();
        Estado nuevoFinal = new Estado (numEdos+1,null,false,true,token);
        
        Transicion epsilon3 = new Transicion('ɛ');
        Transicion epsilon4 = new Transicion('ɛ');
        
        epsilon3.agregarDestino(nuevoFinal);
        epsilon4.agregarDestino(nuevoFinal);
            //Dos listas, una para cada Estado final de los automatas
        List<Transicion> transicionesFin1 = new ArrayList<>();
        List<Transicion> transicionesFin2 = new ArrayList<>();
        transicionesFin1.add(epsilon3);
        transicionesFin2.add(epsilon4);
        
          
        List<Estado> nuevoEdosAFN = new ArrayList<>();
        nuevoEdosAFN.add(nuevoInicial);
        List<Estado> nuevoAceptacion = new ArrayList<>();
        nuevoAceptacion.add(nuevoFinal);
        
        //Agregamos estados del PRIMER AUTOMATA
        
        List<Transicion> auxTransiciones1 = new ArrayList<>();
        
        int nuevoId = 1;  //El id = 0 lo tiene el inicial
        for (int i=0;i < this.getEdosAFN().size();i++){
            Estado aux = (Estado) this.getEdosAFN().get(i);
            aux.setId(nuevoId);
            if(i==this.getEdosAFN().size()-1){
                aux.setEdoFinal(false);    // Ya no es estado final
                if(aux.getTransciciones()!=null){
                    auxTransiciones1 = aux.getTransciciones();
                    auxTransiciones1.addAll(transicionesFin1);
                    aux.setTransciciones(auxTransiciones1);
                }else{
                    aux.setTransciciones(transicionesFin1);
                }
            }
            nuevoEdosAFN.add(aux);
            nuevoId++;
        }
        
        // Agregamos estados del SEGUNDO AUTOMATA
        
        List<Transicion> auxTransiciones2 = new ArrayList<>();
        for(int j = 0; j< unAFN.getEdosAFN().size();j++){
            Estado aux= (Estado) unAFN.getEdosAFN().get(j);
            aux.setId(nuevoId);
            if(j==unAFN.getEdosAFN().size()-1){
                aux.setEdoFinal(false);
                if(aux.getTransciciones()!=null){
                    auxTransiciones2 = aux.getTransciciones();
                    auxTransiciones2.addAll(transicionesFin2);
                    aux.setTransciciones(auxTransiciones2);
                }else{
                        aux.setTransciciones(transicionesFin2);
                }
            }
            nuevoEdosAFN.add(aux);
            nuevoId++;
        }
        nuevoFinal.setEdoFinal(true);
        nuevoEdosAFN.add(nuevoFinal);
        
        return new AFN(idNuevoAFN,nuevoInicial,nuevoAlfabeto,nuevoAceptacion,nuevoEdosAFN);
    }
    
    public AFN concatenar(AFN unAFN,int idNuevoAFN,int nuevoToken){
        //se unen los dos alfabetos de los AFN
        List<Character> nuevoAlfabeto = new ArrayList<Character>(this.getAlfabeto());
        nuevoAlfabeto.addAll(unAFN.getAlfabeto());
        
        List<Estado> nuevoEdosAFN = new ArrayList<Estado>();
        List<Estado> nuevoEdosAceptacion = new ArrayList<Estado>();
        List<Transicion> auxTransiciones = new ArrayList<Transicion>();
        int i=0;    //contador estados del nuevo automata
        //agregamos estados del PRIMER AFN
        for (i=0; i < this.getEdosAFN().size(); i++) {
            Estado aux = (Estado) this.getEdosAFN().get(i);
            aux.setId(i);   //cambiamos el id de los estados para el nuevo automata
            //cuando llega al ultimo, lo concatenamos con el primero del segundo AFN
            if(i==this.getEdosAFN().size()-1){
                aux.setEdoFinal(false); //quitamos aceptación al primer automata
                if(aux.getTransciciones()!=null){
                    auxTransiciones = aux.getTransciciones();
                    auxTransiciones.addAll(unAFN.getEdoInicial().getTransciciones());
                    aux.setTransciciones(auxTransiciones); //Las transiciónes del primero mas las del segundo
                }else{
                    aux.setTransciciones(unAFN.getEdoInicial().getTransciciones());
                }
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
    
    public AFN cerrarTransitiva(int idNuevoAFN, int token){
      List<Character> nuevoAlfabeto = new ArrayList<>(this.getAlfabeto());
      nuevoAlfabeto.add('ɛ');
      nuevoAlfabeto.addAll(this.getAlfabeto());
      
      //Epsilon interno Final -> Inicial
      Transicion epsilon1 = new Transicion('ɛ');
      epsilon1.agregarDestino(this.getEdoInicial());
      List<Transicion> interna = new ArrayList<>();
      interna.add(epsilon1);
      
      //Epsilon inicial
      Transicion epsilon2 = new Transicion('ɛ');
      epsilon2.agregarDestino(this.getEdoInicial());
      List<Transicion> inicialTrans = new ArrayList<>();
      inicialTrans.add(epsilon2);
      Estado nuevoInicial =new Estado(0,inicialTrans,true,false,0);
      
      //Epsilon final
      Transicion epsilon3 = new Transicion('ɛ');
      Estado nuevoFinal = new Estado(this.getEdosAFN().size()+1,null,false,true,token);
      epsilon3.agregarDestino(nuevoFinal);
      interna.add(epsilon3);
      
      //agregamos estados del UNICO AFN
      List<Estado> nuevoEdosAFN = new ArrayList<>();
      List<Estado> nuevoEdosAceptacion = new ArrayList<>();
      List<Transicion> auxTransiciones = new ArrayList<>();
      nuevoEdosAFN.add(nuevoInicial);
      int nuevoId = 1;
      
      for (int i=0;i < this.getEdosAFN().size();i++){
            Estado aux = (Estado) this.getEdosAFN().get(i);
            aux.setId(nuevoId);
            if(i==this.getEdosAFN().size()-1){
                aux.setEdoFinal(false);    // Ya no es estado final
                if(aux.getTransciciones()!=null){
                    auxTransiciones = aux.getTransciciones();
                    auxTransiciones.addAll(interna); //Agragar epsilon interno final
                    aux.setTransciciones(auxTransiciones);
                }else{
                    aux.setTransciciones(interna);
                }
            }
            nuevoEdosAFN.add(aux);
            nuevoId++;
        }
      
      nuevoEdosAFN.add(nuevoFinal);
      nuevoEdosAceptacion.add(nuevoFinal);
      this.getEdoInicial().setEdoInicial(false);
      
      return new AFN(idNuevoAFN,nuevoInicial,nuevoAlfabeto,nuevoEdosAceptacion,nuevoEdosAFN);  
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
