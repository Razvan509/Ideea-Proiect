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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import raport.RaportBrut;
import raport.RaportOreAngajat;
import rmi.IActivitateService;
import rmi.Pair;

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
    
    public List<Activitate> getActivitatiAngajatZi(Angajat a,Date dataa) throws RemoteException{
        return activitateService.getActivitatiAngajatZi(a, dataa);
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
    
    public Pair getOreProiectByAngajatBetweenDate(Proiect proiect,Angajat angajat,Date start,Date end) throws RemoteException{
        return activitateService.getOreProiectByAngajatBetweenDate(proiect, angajat,start,end);
    }
    
    public long getOreProiectBetweenDate(Proiect proiect,Date startDate,Date endDate) throws RemoteException{
        return activitateService.getOreProiectBetweenDate(proiect, startDate, endDate);
    }
    
    public int getOreAngajatLuna(Angajat a) throws RemoteException{
        return activitateService.getOreAngajatLuna(a);
    }
    
    public List<Activitate> getActivitatiTip(String comanda, int[] cmd,int cod) throws RemoteException{
        return activitateService.getActivitatiTip(comanda, cmd, cod);
    }
    
    public Activitate getFirstActivityProject(Proiect p) throws RemoteException{
        return activitateService.getFirstActivityProject(p);
    }
    
    public List<Angajat> getAngajatiOnProject(Proiect p) throws RemoteException{
        return activitateService.getAngajatiOnProject(p);
    }
    
    public int getOreProiectByAngajat(Proiect p, Angajat a) throws RemoteException{
        return activitateService.getOreProiectByAngajat(p, a);
    }
    
    public List<Angajat> getAngajatiOnProjectBetweenDates(Proiect p, Date start, Date end) throws RemoteException{
        return activitateService.getAngajatiOnProjectBetweenDates(p,start,end);
    }
    
    public List<Activitate> getRaport(String raport) throws RemoteException{
        return activitateService.getRaport(raport);
    }
    
    public List<Activitate> getRaportProiect(String raport,Proiect p) throws RemoteException{
        return activitateService.getRaportProiect(raport,p);
    }
    
    public List<RaportBrut> createView(String query) throws RemoteException{
        try{
            return activitateService.createView(query);
        }catch(Exception e){
            //JOptionPane.showMessageDialog(null, "Nu exista pontaje pentru ce ai ales!");
            e.printStackTrace();
        }
        return null;
    }
    
    public ArrayList<RaportOreAngajat> getRaportOreAngajat(String select) throws RemoteException{
        return activitateService.getRaportOreAngajat(select);
    }
    
    public ArrayList<RaportBrut> getRaportOrd(String select) throws RemoteException{
        return activitateService.getRaportOrd(select);
    }
    
     public long getAngajatTimpPontajBetweenDates(Date startDate, Date endDate, Angajat ang) throws RemoteException {
         return activitateService.getAngajatTimpPontajBetweenDates(startDate, endDate, ang);
     }
}
