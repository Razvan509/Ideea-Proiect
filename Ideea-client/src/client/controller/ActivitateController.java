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
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import rmi.IActivitateService;
import ro.top.service.ClientNotificationController;

/**
 *
 * @author Razvan
 */
public class ActivitateController {
    
    private IActivitateService activitateService;
    
    private ActivitateController(){
        
        try{
            //System.setProperty("java.rmi.server.hostname",IpNumber.getIp());
            Registry registry = LocateRegistry.getRegistry(IpNumber.getIp(),IpNumber.getPort());
            //Registry registry = LocateRegistry.getRegistry("localhost",4444);
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
    
    public List<Activitate> getActivitatiAngajatProiect(Angajat a,Proiect p) throws RemoteException{
        return activitateService.getActivitatiAngajatProiect(a, p);
    }
    
    public List<Activitate> getActivitatiAngajatZi(Angajat a,Date data) throws RemoteException{
        return activitateService.getActivitatiAngajatZi(a, data);
    }
    
    public Activitate findById(int id) throws RemoteException{
        return activitateService.findById(id);
    }
    
    public void modifyActivity(Activitate a)throws RemoteException{
        activitateService.modifyActivity(a);
    }
    
    public void deleteActivity(Activitate a)throws RemoteException{
        activitateService.deleteActivity(a);
    }
    
    public Activitate getLastDateActivityByAngajat(Angajat angajat) throws RemoteException{
        return activitateService.getLastDateActivityByAngajat(angajat);
    }
    
    public List<Activitate> getActivitatiPerioada(Angajat angajat,Date startDate,Date endDate) throws RemoteException{
        return activitateService.getActivitatiPerioada(angajat, startDate, endDate);
    }
    
}
