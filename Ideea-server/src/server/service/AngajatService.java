/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.service;

import server.dao.AngajatDao;
import db.Angajat;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import rmi.IAngajatService;

/**
 *
 * @author razvan
 */
public class AngajatService extends UnicastRemoteObject implements IAngajatService{
    
    private EntityManagerFactory emf;
    
    public AngajatService() throws RemoteException{
        emf = Persistence.createEntityManagerFactory("Ideea-serverPU");
    }

    @Override
    public void adaugaAngajat(Angajat angajat) throws RemoteException {
        EntityManager em = emf.createEntityManager();
        AngajatDao angajatDao = new AngajatDao(em);
        
        em.getTransaction().begin();
        angajatDao.adaugaAngajat(angajat);
        em.getTransaction().commit();
        
        em.close();
    }

    @Override
    public Angajat findByUsernameAndPass(String username, String password) throws RemoteException {
        EntityManager em = emf.createEntityManager();
        AngajatDao angajatDao = new AngajatDao(em);
        Angajat angajat = angajatDao.findByUsernameAndPass(username, password);
        
        em.close();
        return angajat;
    }

    @Override
    public void setareAngajat(long id, String username, String password) throws RemoteException {
        EntityManager em = emf.createEntityManager();
        AngajatDao angajatDao = new AngajatDao(em);
        
        em.getTransaction().begin();
        Angajat a = angajatDao.findById(id);
        a.setUsername(username);
        a.setParola(password);
        em.getTransaction().commit();
        
        em.close();
    }
    
    
}
