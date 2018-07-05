/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controller;

import db.Activitate;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JOptionPane;
import rmi.IActivitateService;

/**
 *
 * @author Razvan
 */
public class ActivitateController {
    
    private IActivitateService activitateService;
    
    private ActivitateController(){
        
        try{
            Registry registry = LocateRegistry.getRegistry("localhost",4444);
            activitateService = (IActivitateService) registry.lookup("activitateservice");
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eroare la conectare");
        }
    }
    
    private static final class SingeltonHolder{
         private static final ActivitateController singleton = new ActivitateController();
    }
    
    public static ActivitateController getInstance(){
        return SingeltonHolder.singleton;
    }
    
    public void adaugaActivitate(Activitate activitate) throws RemoteException{
        activitateService.adaugaActivitate(activitate);
    }
    
}
