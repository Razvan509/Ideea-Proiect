/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controller;

import db.Proiect;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import javax.swing.JOptionPane;
import rmi.IProiectService;

/**
 *
 * @author Razvan
 */
public class ProiectController {
    private IProiectService proiectService;
    
    private ProiectController(){
        
        try{
            //System.setProperty("java.rmi.server.hostname",IpNumber.getIp());
            Registry registry = LocateRegistry.getRegistry(IpNumber.getIp(),IpNumber.getPort());
            //Registry registry = LocateRegistry.getRegistry("localhost",4444);
            proiectService = (IProiectService) registry.lookup("proiectservice");
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eroare la conectare");
        }
    }
    
    private static final class SingeltonHolder{
         private static final ProiectController singleton = new ProiectController();
    }
    
    public static ProiectController getInstance(){
        return SingeltonHolder.singleton;
    }
    
    public void adaugaProiect(Proiect proiect) throws RemoteException{
        proiectService.adaugaProiect(proiect);
    }
    
    public List<Proiect> getAll() throws RemoteException{
        return proiectService.getAll();
    }
    
    public Proiect findByNume(String nume) throws RemoteException{
        return proiectService.findByNume(nume);
    }
    
    
    public long oreProiect(Proiect proiect) throws RemoteException{
        return proiectService.oreProiect(proiect);
    }
    
    public List<Proiect> getAllProjectsByStare(int stare) throws RemoteException{
        return proiectService.getAllProjectsByStare(stare);
    }
    
    public void modifyProject(Proiect proiect) throws RemoteException{
        proiectService.modifyProject(proiect);
    }
    
    public Proiect findById(int id) throws RemoteException{
        return proiectService.findById(id);
    }
}
