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
import java.util.List;
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
        //PropertyConfigurator.configure(Paths.get(pathToLog4j).toString());
    }

    @Override
    public void adaugaAngajat(Angajat angajat) throws RemoteException {
        EntityManager em = emf.createEntityManager();
        AngajatDao angajatDao = new AngajatDao(em);
        angajat.setUsername(angajat.getUsername().replaceAll("\\s+",""));
        
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
        username = username.replaceAll("\\s+","");
        a.setUsername(username);
        a.setPassword(password);
        em.getTransaction().commit();
        
        em.close();
    }

    @Override
    public List<Angajat> getAll() throws RemoteException {
        EntityManager em = emf.createEntityManager();
        AngajatDao angajatDao = new AngajatDao(em); 
        
        List<Angajat> angajati = angajatDao.getAll();
        em.close();
        return angajati;
    }

    @Override
    public List<Angajat> getAngajatiBYStare(String stare) throws RemoteException {
        EntityManager em = emf.createEntityManager();
        AngajatDao angajatDao = new AngajatDao(em); 
        
        List<Angajat> angajati = angajatDao.getAngajatiByStare(stare);
        em.close();
        return angajati;
    }

    @Override
    public void modificaAngajat(Angajat a) throws RemoteException {
        EntityManager em = emf.createEntityManager();
        AngajatDao angajatDao = new AngajatDao(em); 
        
        em.getTransaction().begin();
        angajatDao.modificaAngajat(a);
        em.getTransaction().commit();
    }
    
    
}
