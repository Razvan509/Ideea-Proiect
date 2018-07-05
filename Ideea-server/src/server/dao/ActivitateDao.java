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
 * @author razvan
 */
public class ActivitateDao {
    
    private EntityManager em;
    
    public ActivitateDao(EntityManager em){
        this.em = em;
    }
    
    public void adaugaActivitate(Activitate activitate){
        em.persist(activitate);
    }
    
}
