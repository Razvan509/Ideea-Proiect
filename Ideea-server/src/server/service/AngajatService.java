/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.service;

import server.dao.AngajatDao;
import db.Angajat;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import static java.lang.System.setOut;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    //private static final Logger log = Logger.getLogger(AngajatService.class.getName());
    
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
        
        Date data = new Date();
        SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");
        try {
            System.setOut(new PrintStream("log/logger"+f.format(data)+".log"));
            System.out.println("Eroare " +username);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AngajatService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
