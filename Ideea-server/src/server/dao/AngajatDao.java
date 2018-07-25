/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.dao;

import db.Angajat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author razvan
 */
public class AngajatDao{
    
    private EntityManager em;
    
    public AngajatDao(EntityManager em){
        this.em = em;
    }
    
    public void adaugaAngajat(Angajat angajat){
        em.persist(angajat);
    }
    
    public Angajat findById(long id){
        return em.find(Angajat.class, id);
    }
    
    public Angajat findByUsernameAndPass(String username, String password){
        Query query = em.createNamedQuery("Angajat.findByUsernameAndPassword");
        query.setParameter("username", username);
        query.setParameter("password", password);
        try{
            return (Angajat) query.getSingleResult();
        }catch(Exception e){
            return null;
        }
    }
    
    public List<Angajat> getAll(){
        Query query = em.createQuery("SELECT a FROM Angajat a ORDER BY a.nume ASC");
        try{
            List<Angajat> list = query.getResultList();
            return list;
        }catch(Exception e){
            return null;
        }
    }
    
    public List<Angajat> getAngajatiByStare(String stare){
        Query query = em.createQuery("SELECT a FROM Angajat a WHERE a.stare = :stare AND a.admin = 0 ORDER BY a.nume ASC");
        query.setParameter("stare", stare);
        
        try{
            return query.getResultList();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public void modificaAngajat(Angajat a){
        em.merge(a);
    }
}
