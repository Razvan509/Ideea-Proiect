/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controller;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JOptionPane;
import rmi.IDictionarService;

/**
 *
 * @author Razvan
 */
public class DictionarController {
    private IDictionarService dictionarService;
    
    private DictionarController(){
        try{
            Registry registry = LocateRegistry.getRegistry(IpNumber.getIp(),4444);
            //Registry registry = LocateRegistry.getRegistry("localhost",4444);
            dictionarService = (IDictionarService) registry.lookup("dictionarservice");
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eroare la conectare");
        }
    }
    
    private static final class SingeltonHolder{
         private static final DictionarController singleton = new DictionarController();
    }
    
    public static DictionarController getInstance(){
        return DictionarController.SingeltonHolder.singleton;
    }
    
    public String findByCod(int cod) throws RemoteException{
        return dictionarService.findByCod(cod);
    }
}
