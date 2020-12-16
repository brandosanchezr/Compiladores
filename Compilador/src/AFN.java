import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

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
    
    public void copiar(AFN f)
    {
        this.id = f.id;
        this.edoInicial = f.edoInicial;
        this.alfabeto = f.alfabeto;
        this.edosAceptacion = f.edosAceptacion;
        this.edosAFN = f.edosAFN;
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
    
    public AFN crearBasico(Character c, Character c_2,int idAFN){


        for(int i=0; i <= (int)(c_2-c); i++)
            {
                char n_c = (char)(((int)c)+i);
                if(!this.getAlfabeto().contains(n_c));
                    this.alfabeto.add(n_c);
            }
       
        
        List<Estado> edosAFN = new ArrayList<Estado>();
        
        Estado segundoEdo = new Estado(1, null, false, true, idAFN);
        
        Transicion unaTransicion = new Transicion(c, c_2);
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
    public AFN crearBasico(Character c, int idAFN){

        if(!this.getAlfabeto().contains(c));
            this.alfabeto.add(c);
       
        
        List<Estado> edosAFN = new ArrayList<Estado>();
        
        Estado segundoEdo = new Estado(1, null, false, true, idAFN);
        
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
        Transicion epsilon1 = new Transicion('ɛ','ɛ');
        Transicion epsilon2 = new Transicion('ɛ','ɛ');
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
        
        Transicion epsilon3 = new Transicion('ɛ','ɛ');
        Transicion epsilon4 = new Transicion('ɛ','ɛ');
        
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
            aux.setToken(0);
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
            aux.setToken(0);
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
        
        nuevoAlfabeto = nuevoAlfabeto.stream().distinct().collect(Collectors.toList()); 
        
        return new AFN(idNuevoAFN,nuevoInicial,nuevoAlfabeto,nuevoAceptacion,nuevoEdosAFN);
    }
    
    public AFN concatenar(AFN unAFN,int idNuevoAFN,int nuevoToken){
        //se unen los dos alfabetos de los AFN
        List<Character> nuevoAlfabeto = new ArrayList<Character>(this.getAlfabeto());
        nuevoAlfabeto.addAll(unAFN.getAlfabeto());
        
        List<Estado> nuevoEdosAFN = new ArrayList<>();
        List<Estado> nuevoEdosAceptacion = new ArrayList<>();
        List<Transicion> auxTransiciones = new ArrayList<>();
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
        
        nuevoAlfabeto = nuevoAlfabeto.stream().distinct().collect(Collectors.toList()); 
        
        return new AFN(idNuevoAFN,this.edoInicial,nuevoAlfabeto,nuevoEdosAceptacion,nuevoEdosAFN);
    }
    
    public AFN cerrarTransitiva(int idNuevoAFN, int token){
      List<Character> nuevoAlfabeto = new ArrayList<>(this.getAlfabeto());
      nuevoAlfabeto.add('ɛ');
      nuevoAlfabeto.addAll(this.getAlfabeto());
      
      //Epsilon interno Final -> Inicial
      Transicion epsilon1 = new Transicion('ɛ','ɛ');
      epsilon1.agregarDestino(this.getEdoInicial());
      List<Transicion> interna = new ArrayList<>();
      interna.add(epsilon1);
      
      //Epsilon inicial
      Transicion epsilon2 = new Transicion('ɛ','ɛ');
      epsilon2.agregarDestino(this.getEdoInicial());
      List<Transicion> inicialTrans = new ArrayList<>();
      inicialTrans.add(epsilon2);
      Estado nuevoInicial =new Estado(0,inicialTrans,true,false,0);
      
      //Epsilon final
      Transicion epsilon3 = new Transicion('ɛ','ɛ');
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
      
      nuevoAlfabeto = nuevoAlfabeto.stream().distinct().collect(Collectors.toList()); 
      
      return new AFN(idNuevoAFN,nuevoInicial,nuevoAlfabeto,nuevoEdosAceptacion,nuevoEdosAFN);  
    }
    
    public AFN cerrarKleen(int idNuevoAFN, int token){
      List<Character> nuevoAlfabeto = new ArrayList<>(this.getAlfabeto());
      nuevoAlfabeto.add('ɛ');
      nuevoAlfabeto.addAll(this.getAlfabeto());
      
      //Epsilon interno Final -> Inicial
      Transicion epsilon1 = new Transicion('ɛ','ɛ');
      epsilon1.agregarDestino(this.getEdoInicial());
      List<Transicion> interna = new ArrayList<>();
      interna.add(epsilon1);
      
      //Epsilon inicial
      Transicion epsilon2 = new Transicion('ɛ','ɛ');
      epsilon2.agregarDestino(this.getEdoInicial());
      List<Transicion> inicialTrans = new ArrayList<>();
      
      //Epsilon final
      Transicion epsilon3 = new Transicion('ɛ','ɛ');
      Estado nuevoFinal = new Estado(this.getEdosAFN().size()+1,null,false,true,token);
      epsilon3.agregarDestino(nuevoFinal);
      interna.add(epsilon3);
      
      Transicion epsilonKleene =new Transicion('ɛ','ɛ');
      epsilonKleene.agregarDestino(nuevoFinal);
      inicialTrans.add(epsilon2);
      inicialTrans.add(epsilonKleene);
      
      Estado nuevoInicial =new Estado(0,inicialTrans,true,false,0);
      
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
      
      nuevoAlfabeto = nuevoAlfabeto.stream().distinct().collect(Collectors.toList()); 
      
        return new AFN(idNuevoAFN,nuevoInicial,nuevoAlfabeto,nuevoEdosAceptacion,nuevoEdosAFN);
    }
    
    public AFN opcional(int idNuevoAFN, int token){
      List<Character> nuevoAlfabeto = new ArrayList<>(this.getAlfabeto());
      nuevoAlfabeto.add('ɛ');
      nuevoAlfabeto.addAll(this.getAlfabeto());
      List<Transicion> interna = new ArrayList<>();
      
      //Epsilon inicial
      Transicion epsilon2 = new Transicion('ɛ','ɛ');
      epsilon2.agregarDestino(this.getEdoInicial());
      List<Transicion> inicialTrans = new ArrayList<>();
      
      //Epsilon final
      Transicion epsilon3 = new Transicion('ɛ','ɛ');
      Estado nuevoFinal = new Estado(this.getEdosAFN().size()+1,null,false,true,token);
      epsilon3.agregarDestino(nuevoFinal);
      interna.add(epsilon3);
      
      Transicion epsilonKleene =new Transicion('ɛ','ɛ');
      epsilonKleene.agregarDestino(nuevoFinal);
      inicialTrans.add(epsilon2);
      inicialTrans.add(epsilonKleene);
      
      Estado nuevoInicial =new Estado(0,inicialTrans,true,false,0);
      
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
      
      nuevoAlfabeto = nuevoAlfabeto.stream().distinct().collect(Collectors.toList()); 
      
        return new AFN(idNuevoAFN,nuevoInicial,nuevoAlfabeto,nuevoEdosAceptacion,nuevoEdosAFN);
    }
    
    public List<Estado> cerrarEpsilon(List<Estado> estados){
        
        List<Estado> R = new ArrayList<>();
        
        estados.stream().forEach((edo)->{
            R.addAll(cerrarEpsilon(edo));
        });

        return R.stream().distinct().collect(Collectors.toList());
    }
    
    public List<Estado> cerrarEpsilon(Estado unEstado){
        
        Stack<Estado> S = new Stack<>();
        List<Estado>  R = new ArrayList<>();
        
        S.push(unEstado);
        
        while(!S.empty()){
            
            Estado p = S.pop();
            
            R.add(p);
            
            //System.out.println(p);
            
            if(p.getTransciciones()!= null){
               p.getTransciciones().stream().forEach((w)-> {
                    if(w.getSimbolo() == 'ɛ'){
                       List<Estado> edosDest = w.getEdosDestinos();
                       edosDest.stream().forEach( (unEdo)->{
                           if(!R.contains(unEdo)){
                               S.push(unEdo);
                           }
                       });
                    }
                }); 
            } 
        }
        return R;
    }
    
    public List<Estado> mover(List<Estado> estados, Character simbolo){
        List<Estado> R = new ArrayList<>();

        estados.stream().forEach((e)->{
            R.addAll(mover(e, simbolo));
        });
        
        return R.stream().distinct().collect(Collectors.toList());
    }
    
    public List<Estado> mover(Estado unEstado, Character simbolo){
        
        List<Estado> R = new ArrayList<>();
        
        if(unEstado.getTransciciones()!= null){
            unEstado.getTransciciones().stream().forEach((t)->{

                if(t.getSimbolo() <= simbolo && simbolo<= t.getSimbolo2()){

                    R.addAll(t.getEdosDestinos());
                }
            });
        }
        
        
        return R;
       
    }
    
    public List<Estado> irA(List<Estado> estados, Character simbolo){
        
        return cerrarEpsilon(mover(estados, simbolo));
    }
    
    public boolean validarCadena(String S){
        
        List<Estado> E;
        boolean bandera = false;
        int i =0;
        
        E = cerrarEpsilon(getEdoInicial());
        
        for(i=0; i< S.length(); i++){
            E = irA(E, S.charAt(i));
            if( E.size() == 0 )
                return false;
        }
        
        List<Estado> edos_Acep = getEdosAceptacion();
            
        for(i=0; i < E.size(); i++){
            if(edos_Acep.contains(E.get(i))){
                return true;
            }
        }
        
        return false;
    }
    public AFN unirParaAFD(List<AFN> listaAFN,int idAFN){
       int  numEstados=1;
       List<Transicion> inicialTrans = new ArrayList<>();
       List<Character> nuevoAlfabeto = new ArrayList<>();
       List<Estado> nuevoEdosAFN = new ArrayList<>();
       List<Estado> nuevoEdosAceptacion = new ArrayList<>();
       nuevoAlfabeto.add('ɛ');
       
       //Crear transiciones para estado inicial y nuevo Alfabeto
       for(int i=0;i<listaAFN.size();i++){
           //Transicion a inicial del AFN 
           Transicion inicialAFN = new Transicion('ɛ','ɛ');
           inicialAFN.agregarDestino(listaAFN.get(i).getEdoInicial());
           inicialTrans.add(inicialAFN);
           //Nuevo Alfabeto
           for(int j=0;j<listaAFN.get(i).getAlfabeto().size();j++){
               if(!nuevoAlfabeto.contains(listaAFN.get(i).getAlfabeto().get(j))){
                   nuevoAlfabeto.add(listaAFN.get(i).getAlfabeto().get(j));
               }
           }
       }
       Estado nuevoInicial = new Estado(0,inicialTrans,true,false,0);
       nuevoEdosAFN.add(nuevoInicial);
       
       //Setear nuevo iD de estado, cambiar iniciales
       //Agregar Estados al nuevo AFN, estados de aceptacion
       for(int n=0; n< listaAFN.size();n++){
           for(int m=0;m<listaAFN.get(n).getEdosAFN().size();m++){
               //Si es inicial
               if(listaAFN.get(n).getEdosAFN().get(m).isEdoInicial()){
                   listaAFN.get(n).getEdosAFN().get(m).setEdoInicial(false);
               }
               //Set nuevo Id
               listaAFN.get(n).getEdosAFN().get(m).setId(numEstados);
               numEstados++;
               //Si es final
               if(listaAFN.get(n).getEdosAFN().get(m).isEdoFinal()){
                   nuevoEdosAceptacion.add(listaAFN.get(n).getEdosAFN().get(m));
               }
               nuevoEdosAFN.add(listaAFN.get(n).getEdosAFN().get(m));
           }
           
       }
       return new AFN(idAFN,nuevoInicial,nuevoAlfabeto,nuevoEdosAceptacion,nuevoEdosAFN);  
    }
    
    public AFD convertirAFN(){
        
        List<List<Integer>> tablaTrancisiones = new ArrayList<>();
        List<Character> nuevoAlfabeto = alfabeto;
        nuevoAlfabeto.remove(Character.valueOf('ɛ'));

        //Los estados del nuevo afd 
        List<Estado> nuevosEstados = new ArrayList<Estado>();
        List<Estado> nuevosEstadosAceptacion = new ArrayList<Estado>();
        //Creamos el primer estado
        nuevosEstados.add(new Estado(0, new ArrayList<Transicion>(), true, false, -1));

        List<Subestado> edNoAnalizados = new ArrayList<Subestado>();
        List<Subestado> edAnalizados = new ArrayList<Subestado>();
        // Calculamos la cerradura E del estado inicial
        Subestado S0 = new Subestado(0, false);
        S0.setEstados(cerrarEpsilon( this.edoInicial ));
        edNoAnalizados.add(S0);

        int i = 0; //Indice
        int ie = 1; //Indice de estados existentes

        while(!edNoAnalizados.isEmpty()) //Mientras no sea igual a vacio
        {
            tablaTrancisiones.add(new ArrayList<Integer>());
            int idEstadoAnalizado = edNoAnalizados.get(0).getId();
            for( Character c : nuevoAlfabeto )
            {
                int indiceAlfabeto = nuevoAlfabeto.indexOf(Character.valueOf(c));

                Subestado Sn = new Subestado();
                Sn.setEstados(irA( edNoAnalizados.get(0).getEstados() , c ));

                if(!Sn.getEstados().isEmpty()) { //Si esta vacio ya no hagas nada mas.
                    // Hay que revisar si no existe ya ese grupo de estados en los analizados o no analizados
                    // Si existe crea la transicion a ese estado ya creado y si no crea la transicion a este 
                    // nuevo estado 

                    boolean repetido = false;
                    for( Subestado S : edNoAnalizados )
                    {
                        if( S.getEstados().equals(Sn.getEstados()) ) 
                        {
                            repetido = true;
                            Transicion t = new Transicion(c,c);
                            t.agregarDestino(nuevosEstados.get(S.getId()));
                            List<Transicion> tn =  nuevosEstados.get(idEstadoAnalizado).getTransciciones();
                            tn.add(t);
                            nuevosEstados.get(idEstadoAnalizado).setTransciciones(tn);

                            tablaTrancisiones.get(idEstadoAnalizado).add( S.getId() );
                        }
                    }
                    for( Subestado S : edAnalizados )
                    {
                        if( S.getEstados().equals(Sn.getEstados()) ) 
                        {
                            repetido = true;
                            Transicion t = new Transicion(c,c);
                            t.agregarDestino(nuevosEstados.get(S.getId()));
                            List<Transicion> tn =  nuevosEstados.get(idEstadoAnalizado).getTransciciones();
                            tn.add(t);
                            nuevosEstados.get(idEstadoAnalizado).setTransciciones(tn);

                            tablaTrancisiones.get(idEstadoAnalizado).add( S.getId() );
                        }
                    }

                    if(!repetido) //Si no se repitio
                    {   
                        //Crea un nuevo estado para el afd
                        nuevosEstados.add( new Estado(ie, new ArrayList<Transicion>(), false, false, 0) ); 

                        //checar si es edo final
                        for(Estado est : Sn.getEstados())
                        {
                            if(est.isEdoFinal())
                            {
                                nuevosEstados.get(ie).setEdoFinal(true);
                                nuevosEstados.get(ie).setToken(est.getToken());
                                nuevosEstadosAceptacion.add(nuevosEstados.get(ie));
                            }
                        }

                        //Agrega la transicion
                        List<Transicion> tn = nuevosEstados.get(idEstadoAnalizado).getTransciciones();
                        Transicion t = new Transicion(c,c);
                        t.agregarDestino(nuevosEstados.get(ie));
                        tn.add(t);
                        nuevosEstados.get(idEstadoAnalizado).setTransciciones(tn);

                        tablaTrancisiones.get(idEstadoAnalizado).add( ie );

                        Sn.setId(ie);
                        ie++; //Aumenta el indice del estado  
                        edNoAnalizados.add( Sn ); //Se agrega el conjunto a los no analizados
                    }
                }
                else{
                    tablaTrancisiones.get(idEstadoAnalizado).add(-1);
                }
            }
            tablaTrancisiones.get(idEstadoAnalizado).add( 
                (nuevosEstados.get(idEstadoAnalizado).isEdoFinal())? nuevosEstados.get(idEstadoAnalizado).getToken() : -1
             );
            //Agregamos el conjunto actual como estado analizado
            edAnalizados.add( edNoAnalizados.remove(0) ); 
            
            
        }
        //*/
        return new AFD(new AFN(this.id, nuevosEstados.get(0), nuevoAlfabeto, nuevosEstadosAceptacion, nuevosEstados), tablaTrancisiones);
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