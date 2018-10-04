/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.dao;

import db.Activitate;
import db.Angajat;
import db.Proiect;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author razvan
 */
public class ActivitateDao {
    
    private EntityManager em;
    private final String pathToLog4j = Paths.get("./log4j.properties").toString();
    public static final Logger logger = Logger.getLogger(ActivitateDao.class);
    
    public ActivitateDao(EntityManager em){
        this.em = em;
        PropertyConfigurator.configure(Paths.get(pathToLog4j).toString());
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
            logger.error(e);
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
            logger.error(e);
            return null;
        }
    }
    
    public Activitate findById(int id){
        Query query = em.createQuery("SELECT a FROM Activitate a WHERE a.id = :id");
        query.setParameter("id", id);
        
        try{
            return (Activitate)query.getSingleResult();
        }catch(Exception e){
            logger.error(e);
            return null;
        }
    }
    
    public Activitate modifyActivity(Activitate a){
        return em.merge(a);
    }
    
    public void deleteActivity(Activitate a){
        em.remove(a);
    }
    
    public Activitate getLastDateActivityByAngajat(Angajat angajat){
        Query q = em.createQuery("SELECT a FROM Activitate a WHERE a.angajat = :angajat ORDER BY a.dataPontaj DESC");
        q.setParameter("angajat", angajat);
        List<Activitate> pontaj = q.setMaxResults(1).getResultList();
        
        if(pontaj.size()>0)
            return pontaj.get(0);
        return null;
    }
    
    public List<Activitate> getActivitatiPerioada(Angajat angajat,Date startDate,Date endDate){
        Query query = em.createQuery("SELECT a FROM Activitate a WHERE a.angajat = :angajat "
                + "AND a.dataPontaj BETWEEN :startDate AND :endDate ORDER BY a.dataPontaj DESC");
        query.setParameter("angajat", angajat);
        query.setParameter("startDate", startDate, TemporalType.DATE);
        query.setParameter("endDate", endDate, TemporalType.DATE);
        
        try{
            List<Activitate> pontaje = query.getResultList();
            return pontaje;
        }catch(Exception e){
            logger.error(e);
            return null;
        }
    }
    
    public List<Activitate> getAll(){
        Query q = em.createQuery("SELECT a FROM Activitate a");
        
        return q.getResultList();
    }
}
