
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
public class Subestado {
    int id;
    List<Estado> estados = new ArrayList<>();
    boolean bandera;

    public Subestado() {
    }

    public Subestado(int id, boolean bandera) {
        this.id = id;
        this.bandera = bandera;
    }

    public List<Estado> getEstados() {
        return estados;
    }

    public int getId() {
        return id;
    }

    public boolean isBandera() {
        return bandera;
    }

    public void setBandera(boolean bandera) {
        this.bandera = bandera;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("ID subestado: " + id + "\n" +
                             "Estados: " + estados.toString() +
                             "Bandera: " + bandera
        ); 
    }
    
    
}
