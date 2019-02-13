/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.service;

import db.Activitate;
import db.Angajat;
import db.Proiect;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import rmi.IActivitateService;
import rmi.Pair;
import server.dao.ActivitateDao;

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
    public List<Activitate> getActivitatiAngajatProiect(Angajat a, Proiect p) throws RemoteException {
        EntityManager em = emf.createEntityManager();
        ActivitateDao activitateDao = new ActivitateDao(em);
        
        List<Activitate> activitati = activitateDao.getActivitatiAngajatProiect(a, p);
        em.close();
        
        return activitati;
    }

    @Override
    public List<Activitate> getActivitatiAngajatZi(Angajat a, Date data) {
        EntityManager em = emf.createEntityManager();
        ActivitateDao activitateDao = new ActivitateDao(em);
        List<Activitate> activitati = activitateDao.getActivitatiAngajatZi(a, data);
        
        em.close();
        
        return activitati;
    }

    @Override
    public Activitate findById(int id) throws RemoteException {
        EntityManager em = emf.createEntityManager();
        ActivitateDao activitateDao = new ActivitateDao(em);
        
        Activitate activitate = activitateDao.findById(id);
        em.close();
        return activitate;
    }

    @Override
    public void modifyActivity(Activitate a) throws RemoteException {
        EntityManager em = emf.createEntityManager();
        ActivitateDao activitateDao = new ActivitateDao(em);
        
        em.getTransaction().begin();
        activitateDao.modifyActivity(a);
        em.getTransaction().commit();
        
        em.close();
    }    

    @Override
    public void deleteActivity(Activitate a) throws RemoteException {
        EntityManager em = emf.createEntityManager();
        ActivitateDao activitateDao = new ActivitateDao(em);
        
        em.getTransaction().begin();
        a = activitateDao.modifyActivity(a);
        activitateDao.deleteActivity(a);
        em.getTransaction().commit();
        
        em.close();
    }

    @Override
    public Activitate getLastDateActivityByAngajat(Angajat angajat) throws RemoteException {
        EntityManager em = emf.createEntityManager();
        ActivitateDao activitateDao = new ActivitateDao(em);
        
        Activitate pontaj = activitateDao.getLastDateActivityByAngajat(angajat);
        em.close();
        return pontaj;
    }

    @Override
    public List<Activitate> getActivitatiPerioada(Angajat angajat, Date startDate, Date endDate) throws RemoteException {
        EntityManager em = emf.createEntityManager();
        ActivitateDao activitateDao = new ActivitateDao(em);
        
        List<Activitate> pontaje = activitateDao.getActivitatiPerioada(angajat, startDate, endDate);
        em.close();
        return pontaje;
    }
    
    
    public List<Activitate> getAll(){
        EntityManager em = emf.createEntityManager();
        ActivitateDao activitateDao = new ActivitateDao(em);
        
        List<Activitate> pontaje = activitateDao.getAll();
        em.close();
        return pontaje;  
        
    }
    
    public void close(){
        emf.close();
    }

    @Override
    public Pair getOreProiectByAngajatBetweenDate(Proiect proiect,Angajat angajat,Date start,Date end) throws RemoteException {
        EntityManager em = emf.createEntityManager();
        ActivitateDao activitateDao = new ActivitateDao(em);
        
        Pair rez = new Pair(0,0);
        rez = activitateDao.getOreProiectByAngajatBetweenDate(proiect, angajat,start,end);
        em.close();
        return rez;
    }

    @Override
    public long getOreProiectBetweenDate(Proiect proiect, Date startDate, Date endDate) throws RemoteException {
        EntityManager em = emf.createEntityManager();
        ActivitateDao activitateDao = new ActivitateDao(em);
        
        long rez = activitateDao.getOreProiectBetweenDate(proiect, startDate, endDate);
        em.close();
        return rez;
    }

    @Override
    public int getOreAngajatLuna(Angajat a) throws RemoteException {
        EntityManager em = emf.createEntityManager();
        ActivitateDao activitateDao = new ActivitateDao(em); 
        
        int rez = activitateDao.getOreAngajatLuna(a);
        em.close();
        return rez;
    }
    
}
