/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import db.Activitate;
import db.Angajat;
import db.Proiect;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import raport.RaportBrut;
import raport.RaportOreAngajat;


/**
 *
 * @author razvan
 */
public interface IActivitateService extends Remote{
    public void adaugaActivitate(Activitate activitate) throws RemoteException;
    public List<Activitate> getActivitatiAngajatProiect(Angajat a,Proiect p) throws RemoteException;
    public List<Activitate> getActivitatiAngajatZi(Angajat a,Date data) throws RemoteException;
    public Activitate findById(int id) throws RemoteException;
    public void modifyActivity(Activitate a) throws RemoteException;
    public void deleteActivity(Activitate a) throws RemoteException;
    public Activitate getLastDateActivityByAngajat(Angajat angajat) throws RemoteException;
    public List<Activitate> getActivitatiPerioada(Angajat angajat,Date startDate,Date endDate) throws RemoteException;
    public Pair getOreProiectByAngajatBetweenDate(Proiect proiect,Angajat angajat,Date start,Date end) throws RemoteException;
    public long getOreProiectBetweenDate(Proiect proiect,Date startDate,Date endDate) throws RemoteException;
    public int getOreAngajatLuna(Angajat a) throws RemoteException;
    public List<Activitate> getActivitatiTip(String comanda, int[] cmd,int cod) throws RemoteException;
    public Activitate getFirstActivityProject(Proiect proiect) throws RemoteException;
    public List<Angajat> getAngajatiOnProject(Proiect p) throws RemoteException;
    public int getOreProiectByAngajat(Proiect p, Angajat a) throws RemoteException;
    public List<Angajat> getAngajatiOnProjectBetweenDates(Proiect p,Date start, Date end) throws RemoteException;
    public List<Activitate> getRaport(String raport) throws RemoteException;
    public List<Activitate> getRaportProiect(String raport,Proiect p) throws RemoteException;
    public List<RaportBrut> createView(String query) throws RemoteException;
    public ArrayList<RaportOreAngajat> getRaportOreAngajat(String select) throws RemoteException;
    public ArrayList<RaportBrut> getRaportOrd(String select) throws RemoteException;
    public long getAngajatTimpPontajBetweenDates(Date startDate, Date endDate, Angajat ang) throws RemoteException;
}
