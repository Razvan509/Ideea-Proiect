/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author razvan
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Proiect.getAll", query = "SELECT p FROM Proiect p"),
    @NamedQuery(name = "Proiect.findByNume", query = "SELECT p FROM Proiect p WHERE p.nume = :nume")
})
public class Proiect implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @OneToMany(mappedBy = "proiect",cascade = CascadeType.ALL)
    private List<Activitate> activitati;
    
    @Column(nullable = false)
    private String nume;
    
    @Column(nullable = false)
    private int nrEtaje;
    
    @Column(nullable = false)
    private String beneficiar;
    
    @Column(nullable = false)
    private String adresa;
    
    @Column(nullable = false)
    private String numeArhitect;
    
    @Column(nullable = false)
    private int buget;
    
    @Column(nullable = false)
    private String suprafataEtaj;
    
    @Column(nullable = false)
    private int nrOreAlocate;
    
    @Column(nullable = false)
    private int nrEtajeSubsol;
    
    @Column(nullable = false)
    private float pret;
    
    @Column(nullable = false)
    private int corpuri;
    
    @Column(nullable = false)
    private int stare;

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getNrEtaje() {
        return nrEtaje;
    }

    public void setNrEtaje(int nrEtaje) {
        this.nrEtaje = nrEtaje;
    }

    public String getBeneficiar() {
        return beneficiar;
    }

    public void setBeneficiar(String beneficiar) {
        this.beneficiar = beneficiar;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getNumeArhitect() {
        return numeArhitect;
    }

    public void setNumeArhitect(String numeArhitect) {
        this.numeArhitect = numeArhitect;
    }

    public int getBuget() {
        return buget;
    }

    public void setBuget(int buget) {
        this.buget = buget;
    }

    public String getSuprafataEtaj() {
        return suprafataEtaj;
    }

    public void setSuprafataEtaj(String suprafataEtaj) {
        this.suprafataEtaj = suprafataEtaj;
    }

    public float getPret() {
        return pret;
    }

    public void setPret(float pret) {
        this.pret = pret;
    }

    public int getCorpuri() {
        return corpuri;
    }

    public void setCorpuri(int corpuri) {
        this.corpuri = corpuri;
    }

    public int getStare() {
        return stare;
    }

    public void setStare(int stare) {
        this.stare = stare;
    }
    
    
    
    

    @Override
    public String toString() {
        return nume + " " + adresa + " " + buget;
    }

    
    public boolean equals(Proiect obj) {
        if (obj == null) return false;
        if (!nume.equals(obj.nume)) return false;
        if (!adresa.equals(obj.adresa)) return false;
        if (!beneficiar.equals(obj.beneficiar)) return false;
        if (obj.buget != buget) return false;
        if (obj.nrEtaje != nrEtaje) return false;
        if (obj.nrEtajeSubsol != nrEtajeSubsol) return false;
        if (!numeArhitect.equals(obj.numeArhitect)) return false;
        if (obj.pret != pret) return false;
        if (obj.suprafataEtaj.equals(suprafataEtaj)) return false;
        return true;
        
    }
    
    

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Activitate> getActivitati() {
        return activitati;
    }

    public void setActivitati(List<Activitate> activitati) {
        this.activitati = activitati;
    }

    public int getNrOreAlocate() {
        return nrOreAlocate;
    }

    public void setNrOreAlocate(int nrOreAlocate) {
        this.nrOreAlocate = nrOreAlocate;
    }

    public int getNrEtajeSubsol() {
        return nrEtajeSubsol;
    }

    public void setNrEtajeSubsol(int nrEtajeSubsol) {
        this.nrEtajeSubsol = nrEtajeSubsol;
    }
    
    
    
    
}
