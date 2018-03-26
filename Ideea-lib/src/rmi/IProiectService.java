/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import db.Proiect;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author razvan
 */
public interface IProiectService extends Remote{
    public void adaugaProiect(Proiect proiect) throws RemoteException;
    public List<Proiect> getAll() throws RemoteException;
}