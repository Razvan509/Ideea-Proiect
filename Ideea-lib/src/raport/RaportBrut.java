/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raport;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Razvan
 */
@Table(name = "rapoarte")
public class RaportBrut implements Serializable{
    @Id
    //@Column(name = "act_id")
    private long id;
    
    //@Column(name = "act_corp")
    private char corp;
    
    //@Column(name = "act_min")
    private long minute;
    
    //@Column(name = "act_det")
    private String detalii;
    
    //@Column(name = "ang_nume")
    private String numeAngajat;
    
    //@Column(name = "dict_desc")
    private String descriere;
    
    //@Column(name = "pr_nume")
    private String numeProiect;
    
    private String etaj;
    
    @Override
    public String toString() {
        StringBuilder rezultat = new StringBuilder(numeProiect);
        if (descriere != null) 
            rezultat.append(" " + descriere);
        if (corp != ' ' && corp != '-') 
            rezultat.append(" " + corp);
        if (!etaj.equals("-")) 
            rezultat.append(" " + etaj);
        rezultat.append(" " + numeAngajat);
        return rezultat.toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    

    public char getCorp() {
        return corp;
    }

    public void setCorp(char corp) {
        this.corp = corp;
    }

    public long getMinute() {
        return minute;
    }

    public void setMinute(long minute) {
        this.minute = minute;
    }

    public String getDetalii() {
        return detalii;
    }

    public void setDetalii(String detalii) {
        this.detalii = detalii;
    }

    public String getNumeAngajat() {
        return numeAngajat;
    }

    public void setNumeAngajat(String numeAngajat) {
        this.numeAngajat = numeAngajat;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getNumeProiect() {
        return numeProiect;
    }

    public void setNumeProiect(String numeProiect) {
        this.numeProiect = numeProiect;
    }

    public String getEtaj() {
        return etaj;
    }

    public void setEtaj(String etaj) {
        this.etaj = etaj;
    }

    
    
    
    
    
    
}
