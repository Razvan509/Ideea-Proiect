/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.dao;

import db.Angajat;
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
}
