/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ComboCellProiect;

import client.controller.ProiectController;
import db.Proiect;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Razvan
 */
public class ComboTableModel extends AbstractTableModel{
    private String[] columnNames = {"Nr. crt","Nume","Adresa","Buget","Ore alocate","Ore lucrate",
                "Procent ore","Corpuri","Stare"};
    private List<Proiect> proiecte = new ArrayList<>();
    
    public ComboTableModel(List<Proiect> proiecte){
        this.proiecte = proiecte;
    }

    @Override
    public int getRowCount() {
        return proiecte.size();
    }
    
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Class getColumnClass(int column) {
        return getValueAt(0, column).getClass();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Proiect proiect = proiecte.get(rowIndex);
            long ore;
            
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
                    return ProiectController.getInstance().oreProiect(proiect);
                case 6:{
                    ore = ProiectController.getInstance().oreProiect(proiect);
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
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ComboTableModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return null;
    }
    
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Proiect proiect = proiecte.get(rowIndex);
         
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
    
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 8;
    }  
    
}
