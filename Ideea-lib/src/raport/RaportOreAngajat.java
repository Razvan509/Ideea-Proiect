/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raport;

import java.io.Serializable;

/**
 *
 * @author Razvan
 */
public class RaportOreAngajat implements Serializable{
    private String nume;
    private int ore;

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getOre() {
        return ore;
    }

    public void setOre(int ore) {
        this.ore = ore;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(nume + " " + ore/60 + " ore ");
        if (ore%60 != 0) sb.append(ore%60 + " minute");
        return sb.toString();
    }
    
    
    
}
