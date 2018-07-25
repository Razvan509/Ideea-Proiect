/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author razvan
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Angajat.findByUsernameAndPassword", query = "SELECT a FROM Angajat a WHERE a.username = :username AND a.password = :password")
})
public class Angajat implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @OneToMany(mappedBy = "angajat",cascade = CascadeType.ALL)
    private List<Activitate> activitati;
    
    @Column(nullable = false)
    private String nume;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String username;
    
    private boolean admin;
    
    @Column(nullable = false)
    private String stare;
    
    @Temporal(TemporalType.DATE)
    private Date dataAngajare;
    
    @Temporal(TemporalType.DATE)
    private Date dataIesire;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }    

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String parola) {
        this.password = parola;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Activitate> getActivitati() {
        return activitati;
    }

    public void setActivitati(List<Activitate> activitati) {
        this.activitati = activitati;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }


    public String getStare() {
        return stare;
    }

    public void setStare(String stare) {
        this.stare = stare;
    }

    public Date getDataAngajare() {
        return dataAngajare;
    }

    public void setDataAngajare(Date dataAngajare) {
        this.dataAngajare = dataAngajare;
    }

    public Date getDataIesire() {
        return dataIesire;
    }

    public void setDataIesire(Date dataIesire) {
        this.dataIesire = dataIesire;
    }
    
    
    

    @Override
    public String toString() {
        return nume;
    }
    
    
    
}
