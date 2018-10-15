/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controller;

import db.Activitate;
import db.Angajat;
import db.Proiect;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import javax.swing.JOptionPane;
import rmi.IActivitateService;
import rmi.IAngajatService;
import rmi.IProiectService;

/**
 *
 * @author razvan
 */
public class ClientController {
    
    private IAngajatService angajatService;
    private IActivitateService activitateService;
    private IProiectService proiectService;
    
    private ClientController(){
        
        try{
            Registry registry = LocateRegistry.getRegistry("localhost",4444);
            angajatService = (IAngajatService) registry.lookup("angajatservice");
            activitateService = (IActivitateService) registry.lookup("activitateservice");
            proiectService = (IProiectService) registry.lookup("proiectservice");
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eroare la conectare");
        }
    }
    
    private static final class SingeltonHolder{
         private static final ClientController singleton = new ClientController();
    }
    
    public static ClientController getInstance(){
        return SingeltonHolder.singleton;
    }
    
    public void adaugaAngajat(Angajat angajat) throws RemoteException{
        angajatService.adaugaAngajat(angajat);
    }
    
    public Angajat findByUsernameAndPass(String username, String password) throws RemoteException{
        return angajatService.findByUsernameAndPass(username, password);
    }
    
    public void setareAngajat(long id, String username, String password) throws RemoteException{
        angajatService.setareAngajat(id, username, password);
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
    
    public void adaugaActivitate(Activitate activitate) throws RemoteException{
        activitateService.adaugaActivitate(activitate);
    }
    
    public Object oreProiect(Proiect proiect) throws RemoteException{
        return proiectService.oreProiect(proiect);
    }
}
