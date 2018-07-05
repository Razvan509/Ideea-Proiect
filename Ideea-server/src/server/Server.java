/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import db.Activitate;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.gui.ServerFrame;
import server.service.ActivitateService;

/**
 *
 * @author razvan
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new ServerFrame().setVisible(true);
    }
    
}
