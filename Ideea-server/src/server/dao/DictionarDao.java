/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.dao;

import db.Activitate;
import db.Dictionar;
import java.nio.file.Paths;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author Razvan
 */
public class DictionarDao {
    
    private EntityManager em;
    private final String pathToLog4j = Paths.get("./log4j.properties").toString();
    public static final Logger logger = Logger.getLogger(DictionarDao.class);
    
    public DictionarDao(EntityManager em){
        this.em = em;
        PropertyConfigurator.configure(Paths.get(pathToLog4j).toString());
    }
    
    public String findByCod(int cod){
        Query query = em.createQuery("SELECT d FROM Dictionar d WHERE d.cod = :cod",String.class);
        query.setParameter("cod", cod);
        
        try{
            Dictionar d = (Dictionar)query.getSingleResult();
            return d.getDescriere();
        }catch(Exception e){
            logger.error("Nu a fost gasit codul in dictionar!", e);
            return null;
        }
    }
    
}
