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
import rmi.IActivitateService;

/**
 *
 * @author razvan
 */
public class ActivitateService extends UnicastRemoteObject implements IActivitateService{
    
    private EntityManagerFactory emf;
    
    public ActivitateService() throws RemoteException{
        emf = Persistence.createEntityManagerFactory("Ideea-serverPU");
    }
}
