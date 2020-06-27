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

    public EntityManagerFactory getEmf() {
        return emf;
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
        
        List<Proiect> proiecte = proiectDao.getAll();
        em.close();
        
        return proiecte;       
    }

    @Override
    public Proiect findByNume(String nume) throws RemoteException {
        EntityManager em = emf.createEntityManager();
        ProiectDao proiectDao = new ProiectDao(em);
        
        Proiect p = proiectDao.findByNume(nume);
        em.close();
        
        return p;
    }

    @Override
    public long oreProiect(Proiect proiect) throws RemoteException {
        EntityManager em = emf.createEntityManager();
        ProiectDao proiectDao = new ProiectDao(em);
        
        long ore = ((Long) proiectDao.oreProiect(proiect)).longValue();
        em.close();
        return ore;
    }

    @Override
    public List<Proiect> getAllProjectsByStare(int stare) throws RemoteException {
        EntityManager em = emf.createEntityManager();
        ProiectDao proiectDao = new ProiectDao(em);
        
        List<Proiect> proiecte = proiectDao.getAllProjectsByStare(stare);
        
        em.close();
        
        return proiecte;
    }

    @Override
    public void modifyProject(Proiect proiect) throws RemoteException {
        EntityManager em = emf.createEntityManager();
        ProiectDao proiectDao = new ProiectDao(em);
        
        em.getTransaction().begin();
        //Proiect p = findByNume(proiect.getNume());
        proiectDao.modifyProject(proiect);
        em.getTransaction().commit();
        
        em.close();
    }
    
    public void deleteCache(){
        EntityManager em = emf.createEntityManager();
        ProiectDao proiectDao = new ProiectDao(em);
        
        proiectDao.deleteCache();
        em.close();
    }

    @Override
    public Proiect findById(int id) throws RemoteException {
        EntityManager em = emf.createEntityManager();
        ProiectDao proiectDao = new ProiectDao(em);
        
        Proiect proiect = proiectDao.findById(id);
        em.close();
        return proiect;
    }
}
