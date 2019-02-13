/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import db.Activitate;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.service.ActivitateService;

/**
 *
 * @author Razvan
 */
public class Minute {
    
    
    
    /*public static void main(String[] args) {
        try {
            ActivitateService as = new ActivitateService(); 
            List<Activitate> activitati = as.getAll();
            for(Activitate a:activitati){
                a.setMinuteMunca(a.getOreMunca()*60 + a.getMinuteMunca());
                as.modifyActivity(a);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Minute.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
}
