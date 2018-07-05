/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import db.Activitate;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author razvan
 */
public interface IActivitateService extends Remote{
    public void adaugaActivitate(Activitate activitate) throws RemoteException;
    public String findByCod(Activitate activitate) throws RemoteException;
}
