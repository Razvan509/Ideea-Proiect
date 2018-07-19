/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import rmi.IDictionarService;
import server.dao.DictionarDao;

/**
 *
 * @author Razvan
 */
public class DictionarService extends UnicastRemoteObject implements IDictionarService{
    
    private EntityManagerFactory emf;
    
    public DictionarService() throws RemoteException{
        emf = Persistence.createEntityManagerFactory("Ideea-serverPU");
    }
    
    @Override
    public String findByCod(int cod) throws RemoteException {
        EntityManager em = emf.createEntityManager();
        DictionarDao dictionarDao = new DictionarDao(em);
        String s = dictionarDao.findByCod(cod);
        
        em.close();
        
        return s;
   
    }
    
}
