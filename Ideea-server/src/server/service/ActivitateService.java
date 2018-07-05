/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.service;

import db.Activitate;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import rmi.IActivitateService;
import server.dao.ActivitateDao;
import server.dao.DictionarDao;

/**
 *
 * @author razvan
 */
public class ActivitateService extends UnicastRemoteObject implements IActivitateService{
    
    private EntityManagerFactory emf;
    
    public ActivitateService() throws RemoteException{
        emf = Persistence.createEntityManagerFactory("Ideea-serverPU");
    }

    @Override
    public void adaugaActivitate(Activitate activitate) throws RemoteException {
        EntityManager em = emf.createEntityManager();
        ActivitateDao activitateDao = new ActivitateDao(em);
        
        em.getTransaction().begin();
        activitateDao.adaugaActivitate(activitate);
        em.getTransaction().commit();
        
        em.close();
    }

    @Override
    public String findByCod(Activitate activitate) throws RemoteException {
        EntityManager em = emf.createEntityManager();
        DictionarDao dictionarDao = new DictionarDao(em);
        
        return dictionarDao.findByCod(activitate);
        
        //em.close();
    }
    
    
}
