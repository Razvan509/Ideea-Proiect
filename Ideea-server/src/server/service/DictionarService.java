/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Razvan
 */
public class DictionarService extends UnicastRemoteObject{
    
    private EntityManagerFactory emf;
    
    public DictionarService() throws RemoteException{
        emf = Persistence.createEntityManagerFactory("Ideea-serverPU");
    }
    
}
