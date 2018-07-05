/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.dao;

import db.Proiect;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author razvan
 */
public class ProiectDao {
    
    private EntityManager em;
    
    public ProiectDao(EntityManager em){
        this.em = em;
    }
    
    public void adaugaProiect(Proiect proiect){
        em.persist(proiect);
    }
    
    public List<Proiect> getAll(){
        Query query = em.createNamedQuery("Proiect.getAll");
        
        try{
            return query.getResultList();
        }catch(Exception e){
            return null;
        }
    }
    
    public Proiect findByNume(String nume){
        Query query = em.createNamedQuery("Proiect.findByNume");
        query.setParameter("nume", nume);
        
        try{
            return (Proiect) query.getSingleResult();
        }catch(Exception e){
            return null;
        }
    }
    
    public long oreProiect(Proiect proiect){
        Query query = em.createQuery("SELECT SUM(a.oreMunca) FROM Activitate a WHERE a.proiect = :proiect",Long.class);
        query.setParameter("proiect", proiect);
        
        try{
            Object o = query.getSingleResult();
            if (o == null) return 0;
            return (long)o;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    
}
