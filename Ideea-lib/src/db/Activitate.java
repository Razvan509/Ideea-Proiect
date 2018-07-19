/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
    @JoinColumn(nullable = false)
    private Angajat angajat;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private Proiect proiect;
    
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataOra;
    
    @Column(nullable = false)
    private int cod;
    
    @Column(nullable = false)
    private int oreMunca;
    
    @Column(nullable = false)
    private int minuteMunca;
    
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataPontaj;
    
    private char corp;
    
    private String etaj;

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

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getOreMunca() {
        return oreMunca;
    }

    public void setOreMunca(int oreMunca) {
        this.oreMunca = oreMunca;
    }

    public String getEtaj() {
        return etaj;
    }

    public void setEtaj(String etaj) {
        this.etaj = etaj;
    }

    public int getMinuteMunca() {
        return minuteMunca;
    }

    public void setMinuteMunca(int minuteMunca) {
        this.minuteMunca = minuteMunca;
    }

    public Date getDataPontaj() {
        return dataPontaj;
    }

    public void setDataPontaj(Date dataPontaj) {
        this.dataPontaj = dataPontaj;
    }

    public char getCorp() {
        return corp;
    }

    public void setCorp(char corp) {
        this.corp = corp;
    }


    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return proiect.getNume() + " " + dateFormat.format(dataPontaj) + " " + oreMunca + " ore si "+minuteMunca + " minute";
    }
    
    
    
    
}
