/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.dao;

import db.Activitate;
import db.Dictionar;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Razvan
 */
public class DictionarDao {
    
    private EntityManager em;
    
    public DictionarDao(EntityManager em){
        this.em = em;
    }
    
    public String findByCod(Activitate activitate){
        Query query = em.createQuery("SELECT d FROM Dictionar d WHERE d.cod = :cod",String.class);
        query.setParameter("cod", activitate.getCod());
        
        try{
            Dictionar d = (Dictionar)query.getSingleResult();
            return d.getDescriere();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
}
