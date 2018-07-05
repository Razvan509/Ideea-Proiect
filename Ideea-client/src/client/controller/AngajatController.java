/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controller;

import db.Angajat;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JOptionPane;
import rmi.IActivitateService;
import rmi.IAngajatService;

/**
 *
 * @author Razvan
 */
public class AngajatController {
    private IAngajatService angajatService;
    
    private AngajatController(){
        
        try{
            Registry registry = LocateRegistry.getRegistry("localhost",4444);
            angajatService = (IAngajatService) registry.lookup("angajatservice");
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eroare la conectare");
        }
    }
    
    private static final class SingeltonHolder{
         private static final AngajatController singleton = new AngajatController();
    }
    
    public static AngajatController getInstance(){
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
}