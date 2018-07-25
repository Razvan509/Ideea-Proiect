/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ComboCellAngajat;

import client.controller.ProiectController;
import db.Angajat;
import db.Proiect;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Razvan
 */
public class AngajatTableModel extends AbstractTableModel{
    private String[] columnNames = {"Nr. crt","Nume","Data angajare","Data iesire","Stare"};
    private List<Angajat> angajati = new ArrayList<>();
    
    public AngajatTableModel(List<Angajat> angajati){
        this.angajati = angajati;
    }

    @Override
    public int getRowCount() {
        return angajati.size();
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
        Angajat angajat = angajati.get(rowIndex);
        long ore;
        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy");
        switch(columnIndex){
            case 0:
                return rowIndex+1;
            case 1:
                return angajat.getNume();
            case 2:
                if(angajat.getDataAngajare() != null) return f.format(angajat.getDataAngajare());
                else return"Nedeterminat";
            case 3:
                if(angajat.getDataIesire()!=null)return f.format(angajat.getDataIesire());
                else return "Inca activeaza";
            case 4:
                return angajat.getStare();
        }
        return null;
    }
    
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Angajat angajat = angajati.get(rowIndex);
         
        if (columnIndex==4){
            switch((String)value){
                case "activ":{
                    angajat.setStare("Activ");
                    break;
                }
                case "inactiv":{
                    angajat.setStare("Inactiv");
                    break;
                }
            }
            
        }
    }
    
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 4;
    }  
    
}
