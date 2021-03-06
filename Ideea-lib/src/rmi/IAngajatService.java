/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import db.Angajat;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author razvan
 */
public interface IAngajatService extends Remote{
    
    public void adaugaAngajat(Angajat angajat) throws RemoteException;
    public Angajat findByUsernameAndPass(String username, String password) throws RemoteException;
    public void setareAngajat(long id, String username, String password) throws RemoteException;
    public List<Angajat> getAll() throws RemoteException;
    public List<Angajat> getAngajatiBYStare(String stare) throws RemoteException;
    public void modificaAngajat(Angajat a)throws RemoteException;
}
