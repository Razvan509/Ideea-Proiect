/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author razvan
 */
@Entity
public class Activitate implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @ManyToOne
    private Angajat angajat;
    
    @ManyToOne
    private Proiect proiect;
    
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataOra;
    
    @Column(nullable = false)
    private String etapaProiectare;
    
    @Column(nullable = false)
    private int oreMunca;

    public long getId() {
        return id;
    }

    public Angajat getAngajat() {
        return angajat;
    }

    public void setAngajat(Angajat angajat) {
        this.angajat = angajat;
    }

    public Proiect getProiect() {
        return proiect;
    }

    public void setProiect(Proiect proiect) {
        this.proiect = proiect;
    }

    public Date getDataOra() {
        return dataOra;
    }

    public void setDataOra(Date dataOra) {
        this.dataOra = dataOra;
    }

    public String getEtapaProiectare() {
        return etapaProiectare;
    }

    public void setEtapaProiectare(String etapaProiectare) {
        this.etapaProiectare = etapaProiectare;
    }

    public int getOreMunca() {
        return oreMunca;
    }

    public void setOreMunca(int oreMunca) {
        this.oreMunca = oreMunca;
    }

    
    
    @Override
    public String toString() {
        return proiect.getNume() + angajat.toString() + dataOra.toLocaleString() + oreMunca + etapaProiectare;
    }
    
    
    
    
}
