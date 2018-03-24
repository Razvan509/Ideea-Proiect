/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.service;

import db.Proiect;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import rmi.IProiectService;
import server.dao.ProiectDao;

/**
 *
 * @author razvan
 */
public class ProiectService extends UnicastRemoteObject implements IProiectService{
    
    private EntityManagerFactory emf;
    
    public ProiectService() throws RemoteException{
       emf = Persistence.createEntityManagerFactory("Ideea-serverPU");
    }

    @Override
    public void adaugaProiect(Proiect proiect) throws RemoteException {
        EntityManager em = emf.createEntityManager();
        ProiectDao proiectDao = new ProiectDao(em);
        
        em.getTransaction().begin();
        proiectDao.adaugaProiect(proiect);
        em.getTransaction().commit();
        
        em.close();
    }

    @Override
    public List<Proiect> getAll() throws RemoteException {
        EntityManager em = emf.createEntityManager();
        ProiectDao proiectDao = new ProiectDao(em);
        
        return proiectDao.getAll();
    }
}
