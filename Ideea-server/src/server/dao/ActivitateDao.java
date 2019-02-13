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
import rmi.Pair;
import static java.lang.Math.toIntExact;

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
            List<Activitate> rez = query.getResultList();
            return rez;
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
    
    public Pair getOreProiectByAngajatBetweenDate(Proiect proiect,Angajat angajat,Date start,Date end){
        Query q1 = em.createQuery("SELECT SUM(a.oreMunca) FROM Activitate a WHERE a.proiect = :proiect AND"
                + " a.angajat = :angajat AND a.dataPontaj BETWEEN :start AND :end");
        Query q2 = em.createQuery("SELECT SUM(a.minuteMunca) FROM Activitate a WHERE a.proiect = :proiect AND"
                + " a.angajat = :angajat AND a.dataPontaj BETWEEN :start AND :end");
        q1.setParameter("proiect", proiect);
        q1.setParameter("angajat", angajat);
        q1.setParameter("start", start);
        q1.setParameter("end",end);
        
        q2.setParameter("proiect", proiect);
        q2.setParameter("angajat", angajat);
        q2.setParameter("start", start);
        q2.setParameter("end",end);
        
        long ore = (long)q1.getSingleResult();
        long minute = (long) q2.getSingleResult();
        //long ore = minute/60;
        minute = minute%60;
        
        return new Pair(ore,minute);
    }
    
    public long getOreProiectBetweenDate(Proiect proiect,Date startDate,Date endDate){
        Query q1 = em.createQuery("SELECT SUM(a.oreMunca) FROM Activitate a WHERE a.proiect = :proiect"
                + " AND a.dataPontaj BETWEEN :start AND :end");
        Query q2 = em.createQuery("SELECT SUM(a.minuteMunca) FROM Activitate a WHERE a.proiect = :proiect "
                + " AND a.dataPontaj BETWEEN :start AND :end");
        q1.setParameter("proiect", proiect);
        q1.setParameter("start", startDate);
        q1.setParameter("end",endDate);
        
        q2.setParameter("proiect", proiect);
        q2.setParameter("start", startDate);
        q2.setParameter("end",endDate);
        
        Object o1 = q1.getSingleResult();
        Object o2 = q2.getSingleResult();
        if(o2!=null){
            long minute = (long) o2;
            long ore = (long) o1;
            return ore;
        }
        
        return 0;
    }
    
    public int getOreAngajatLuna(Angajat a){
        Date first = new Date();
        first.setDate(1);
        Query q1 = em.createQuery("SELECT SUM(a.oreMunca) FROM Activitate a WHERE a.angajat = :angajat AND a.dataPontaj BETWEEN"
                + " :start AND :end");
        Query q2 = em.createQuery("SELECT SUM(a.minuteMunca) FROM Activitate a WHERE a.angajat = :angajat AND a.dataPontaj BETWEEN"
                + " :start AND :end");
        q1.setParameter("angajat", a);
        q1.setParameter("start", first);
        q1.setParameter("end", new Date());
        
        q2.setParameter("angajat", a);
        q2.setParameter("start", first);
        q2.setParameter("end", new Date());
        
        Object o1 = q1.getSingleResult();
        Object o2 = q2.getSingleResult();
        
        if(o1==null) return 0;
        long l1 = (long)o1;
        int ore = toIntExact(l1);
        
        if(o2==null) return 0;
        long minute = (long) o2;
        
        //long ore = minute/60;
        return toIntExact(ore);
        //return 0;
    }
}
