/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ComboCellAngajat;

import ComboCellProiect.ComboTableModel;
import client.controller.AngajatController;
import client.gui.AngajatiActiviFrame;
import client.gui.AngajatiInactiviFrame;
import db.Angajat;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author Razvan
 */
public class AngajatCellEditor extends AbstractCellEditor
        implements TableCellEditor, ActionListener{
    
    private String stare;
    private List<String> stari;
    private final String pathToLog4j = Paths.get("./log4j.properties").toString();
    public static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(AngajatCellEditor.class);
    
    public AngajatCellEditor(List<String> stari){
        this.stari = stari;
        PropertyConfigurator.configure(Paths.get(pathToLog4j).toString());
    }

    @Override
    public Object getCellEditorValue() {
        return stare;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value instanceof String){
            stare = (String) value;
        }
        
        JComboBox<String> combo = new JComboBox<String>();
        
        for(String s: stari){
            combo.addItem(s);
        }
        
        combo.setSelectedItem(stare);
        combo.addActionListener(this);
        
        /*if (isSelected) {
            combo.setBackground(table.getSelectionBackground());
        } else {
            combo.setBackground(table.getSelectionForeground());
        }*/
        
        return combo;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            JComboBox c = (JComboBox) e.getSource();
            stare = (String)c.getSelectedItem();
            //System.out.println(stare);
            JTable t = (JTable) c.getParent();
            int index = t.getSelectedRow();
            
            List<Angajat> angajati = AngajatController.getInstance().getAngajatiByStare(t.getName());
            angajati.get(index).setStare((String)c.getSelectedItem());
            if(t.getName().equals("activ") && c.getSelectedItem().equals("inactiv")){
                angajati.get(index).setDataIesire(new Date());
            }else{
                angajati.get(index).setDataIesire(null);
            }
            AngajatController.getInstance().modificaAngajat(angajati.get(index));
            if (AngajatiActiviFrame.isVisi()) AngajatiActiviFrame.afisare();
            if (AngajatiInactiviFrame.isVisi()) AngajatiInactiviFrame.afisare();
        } catch (RemoteException ex) {
            logger.error("Nu s.a putut efectua modificarea unui angajat!",ex);
        }
    }
    
}
