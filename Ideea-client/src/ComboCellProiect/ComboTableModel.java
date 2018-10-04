/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ComboCellProiect;

import Enum.TopicsEnum;
import client.controller.ProiectController;
import db.Proiect;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import org.apache.log4j.PropertyConfigurator;
import ro.top.service.ClientNotificationAsyncController;
import ro.top.service.ClientNotificationController;
import ro.top.subscriber.Subscriber;

/**
 *
 * @author Razvan
 */
public class ComboTableModel extends AbstractTableModel implements Subscriber{
    private String[] columnNames = {"Nr. crt","Nume","Adresa","Buget","Ore alocate","Ore lucrate",
                "Procent ore","Corpuri","Stare"};
    private List<Proiect> proiecte;
    private final String pathToLog4j = Paths.get("./log4j.properties").toString();
    public static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ComboTableModel.class);
    private List<Long> oreLucrate;
    
    public ComboTableModel(List<Proiect> proiecte){
        this.proiecte = proiecte;
        PropertyConfigurator.configure(Paths.get(pathToLog4j).toString());
        oreLucrate = new ArrayList<>();
        for(int i=0;i<proiecte.size();i++){
            oreLucrate.add(-1L);
        }
        try {
            ClientNotificationController.getInstance().addSubscriber(TopicsEnum.PROIECT_ORA_MODIFICAT, this);
        } catch (Exception ex) {
            logger.error(ex);
        }
    }

    @Override
    public int getRowCount() {
        return proiecte.size();
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Class<?> getColumnClass(int column) {
        return getValueAt(0, column).getClass();
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Proiect proiect = proiecte.get(rowIndex);
            long ore;
            //System.out.println("getValueAt" + proiect.getNume());
            switch(columnIndex){
                case 0:
                    return rowIndex+1;
                case 1:
                    return proiect.getNume();
                case 2:
                    return proiect.getAdresa();
                case 3:
                    return proiect.getBuget();
                case 4:
                    return proiect.getNrOreAlocate();
                case 5:
                    if(oreLucrate.get(rowIndex)==-1){
                        oreLucrate.set(rowIndex,ProiectController.getInstance().oreProiect(proiect));
                    }
                    return oreLucrate.get(rowIndex);
                case 6:{
                    if(oreLucrate.get(rowIndex)==-1){
                        oreLucrate.set(rowIndex, ProiectController.getInstance().oreProiect(proiect));
                    }
                    ore = oreLucrate.get(rowIndex);
                    DecimalFormat numberFormat = new DecimalFormat("0.000");
                    if (proiect.getNrOreAlocate()>0)
                        return numberFormat.format((ore*100.0)/proiect.getNrOreAlocate()) + " %";
                    return 0 + " %";
                }
                case 7:
                    return proiect.getCorpuri(); 
                case 8:{
                    switch(proiect.getStare()){
                        case 0: return "In derulare";
                        case 1: return "Suspendat";
                        case 2: return "Terminat";
                    }
                }
                default:
                    return new Object();
            }
        } catch (RemoteException ex) {
            logger.error("Eroare la luarea orelor pentru un proiect!", ex);
            return null;
        }
        //return null;
    }
    
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Proiect proiect = proiecte.get(rowIndex);
        System.out.println("setValueAt"); 
        if (columnIndex==7){
            switch((String)value){
                case "In derulare":{
                    proiect.setStare(0);
                    break;
                }
                case "Suspendat":{
                    proiect.setStare(1);
                    break;
                }
                case "Terminat":{
                    proiect.setStare(2);
                    break;
                }
            }
            
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 8;
    } 
    
    public void updateProiecte(List<Proiect> proiecte){
        this.proiecte = proiecte;
        //oreLucrate.clear();
        for(int i=0;i<proiecte.size();i++){
            oreLucrate.set(i,-1L);
        }
    }

    @Override
    public void newNotification(String string) {
        switch(string){
            case TopicsEnum.PROIECT_ORA_MODIFICAT:{
                for(int i=0;i<proiecte.size();i++){
                    oreLucrate.set(i,-1L);
                }
                System.out.println("ceva");
            }break;
        }
    }

    @Override
    public void newDataNotification(Object o, String string) {
        
    }
    
}
