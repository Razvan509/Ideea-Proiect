/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ComboCell;

import client.controller.ProiectController;
import client.gui.AdminFrame;
import client.gui.ProiecteSuspendateFrame;
import client.gui.ProiecteTerminateFrame;
import db.Proiect;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Razvan
 */
public class ComboCellEditor extends AbstractCellEditor
        implements TableCellEditor, ActionListener{
    
    private String stare;
    private List<String> stari;
    
    public ComboCellEditor(List<String> stari){
        this.stari = stari;
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
            int index2=-1;
            
            switch(t.getName()){
                case "In derulare":
                    index2=0;
                    break;
                case "Suspendat":
                    index2=1;
                    break;
                case "Terminat":
                    index2=2;
                    break;
            }
            
            List<Proiect> proiecte = ProiectController.getInstance().getAllProjectsByStare(index2);
            proiecte.get(index).setStare(c.getSelectedIndex());
            ProiectController.getInstance().modifyProject(proiecte.get(index));
            AdminFrame.afisare();
            if (ProiecteSuspendateFrame.isVisi()) ProiecteSuspendateFrame.afisare();
            if (ProiecteTerminateFrame.isVisi()) ProiecteTerminateFrame.afisare();
        } catch (RemoteException ex) {
            Logger.getLogger(ComboCellEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
