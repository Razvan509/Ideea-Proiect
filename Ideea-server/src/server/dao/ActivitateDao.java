/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.dao;

import db.Activitate;
import db.Angajat;
import db.Proiect;
import java.util.Date;
import java.util.List;
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
    
    public List<Activitate> getActivitatiAngajatProiect(Angajat a,Proiect p){
        Query query = em.createQuery("SELECT a FROM Activitate a WHERE a.proiect = :proiect AND a.angajat = :angajat");
        query.setParameter("proiect", p);
        query.setParameter("angajat", a);
        
        try{
            return query.getResultList();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Activitate> getActivitatiAngajatZi(Angajat a,Date data){
        Query query = em.createQuery("SELECT a FROM Activitate a Where a.angajat = :angajat AND a.dataPontaj = :data");
        query.setParameter("angajat",a);
        query.setParameter("data", data);
        
        try{
            return query.getResultList();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
