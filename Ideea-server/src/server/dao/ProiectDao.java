/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.dao;

import db.Proiect;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
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
            List<Proiect> list = query.getResultList();
            return list;
        }catch(Exception e){
            return null;
        }
    }
    
    public List<Proiect> getAllProjectsByStare(int stare){
        Query query = em.createQuery("SELECT p FROM Proiect p WHERE p.stare = :stare ORDER BY p.nume ASC");
        query.setParameter("stare", stare);
        try{
            List<Proiect> list = query.getResultList();
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public Proiect findByNume(String nume){
        Query query = em.createNamedQuery("Proiect.findByNume");
        query.setParameter("nume", nume);
        
        
        try{
            return (Proiect) query.getSingleResult();
        }catch(Exception e){
            //e.printStackTrace();
            return null;
        }
    }
    
    public long oreProiect(Proiect proiect){
        Query query = em.createQuery("SELECT SUM(a.oreMunca) FROM Activitate a WHERE a.proiect = :proiect",Long.class);
        Query q = em.createQuery("SELECT SUM(a.minuteMunca) FROM Activitate a WHERE a.proiect = :proiect",Long.class);
        query.setParameter("proiect", proiect);
        q.setParameter("proiect", proiect);
        
        try{
            Object o1 = query.getSingleResult();
            Object o2 = query.getSingleResult();
            if (o1 == null) return 0;
            if (o2 == null) return 0;
            long l1 = (long) o1;
            long l2 = (long) o2;
            
            l1+=(l2/60);
            return l1;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    
    public void modifyProject(Proiect p){
        em.merge(p);
    }
    
    public void deleteCache(){
        em.getEntityManagerFactory().getCache().evictAll();
    }
    
    public Proiect findById(int id){
        Query q = em.createQuery("SELECT p FROM Proiect p WHERE p.id = :id");
        q.setParameter("id", id);
        try{
            return (Proiect)q.getSingleResult();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
}
