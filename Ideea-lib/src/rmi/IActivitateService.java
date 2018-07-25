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
import java.util.Date;
import java.util.List;

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
    public Date getLastDateActivityByAngajat(Angajat angajat) throws RemoteException;
    public List<Activitate> getActivitatiPerioada(Angajat angajat,Date startDate,Date endDate) throws RemoteException;
}
