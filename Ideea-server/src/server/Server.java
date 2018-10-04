/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.nio.file.Paths;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JOptionPane;
import org.apache.log4j.PropertyConfigurator;
import ro.top.main.NotificationServer;
import server.gui.ServerFrame;
import server.service.ActivitateService;
import server.service.AngajatService;
import server.service.DictionarService;
import server.service.ProiectService;

/**
 *
 * @author razvan
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    
    private static final String pathToLog4j = Paths.get("./log4j.properties").toString();
    public static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ServerFrame.class);
    
    public static void main(String[] args) {
        //new ServerFrame().setVisible(true);
        NotificationServer.start(4445, false);
        PropertyConfigurator.configure(Paths.get(pathToLog4j).toString());
        
        logger.info("A pornit serverul!");
        
        
        
        try{
            
            System.setProperty("java.rmi.server.hostname","192.168.153.5");
            Registry registry = LocateRegistry.createRegistry(4444);
            registry.rebind("activitateservice",new ActivitateService());
            registry.rebind("proiectservice",new ProiectService());
            registry.rebind("angajatservice",new AngajatService());
            registry.rebind("dictionarservice",new DictionarService());
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Eroare!");
            logger.error(e,e);
        }
    }
    
}
