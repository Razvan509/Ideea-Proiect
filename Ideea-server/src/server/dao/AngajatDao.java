/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.dao;

import db.Angajat;
import java.nio.file.Paths;
import java.util.List;
import org.apache.log4j.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.PropertyConfigurator;
import server.service.AngajatService;

/**
 *
 * @author razvan
 */
public class AngajatDao{
    
    private EntityManager em;
    private final String pathToLog4j = Paths.get("./log4j.properties").toString();
    public static final Logger logger = Logger.getLogger(AngajatDao.class);
    
    public AngajatDao(EntityManager em){
        this.em = em;
        PropertyConfigurator.configure(Paths.get(pathToLog4j).toString());
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
        Query query = em.createQuery("SELECT a FROM Angajat a WHERE a.admin = 0 ORDER BY a.nume ASC");
        try{
            List<Angajat> list = query.getResultList();
            return list;
        }catch(Exception e){
            logger.error(e);
            return null;
        }
    }
    
    public List<Angajat> getAngajatiByStare(String stare){
        Query query = em.createQuery("SELECT a FROM Angajat a WHERE a.stare = :stare AND a.admin = 0 ORDER BY a.nume ASC");
        query.setParameter("stare", stare);
        
        try{
            return query.getResultList();
        }catch(Exception e){
            logger.error(e);
            return null;
        }
    }
    
    public void modificaAngajat(Angajat a){
        em.merge(a);
    }
}
