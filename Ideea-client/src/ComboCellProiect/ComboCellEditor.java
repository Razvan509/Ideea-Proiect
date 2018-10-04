/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ComboCellProiect;

import Enum.TopicsEnum;
import client.controller.ProiectController;
import client.gui.AdminFrame;
import client.gui.ProiecteSuspendateFrame;
import client.gui.ProiecteTerminateFrame;
import db.Proiect;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.List;
import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import org.apache.log4j.PropertyConfigurator;
import ro.top.service.ClientNotificationAsyncController;
import ro.top.service.ClientNotificationController;

/**
 *
 * @author Razvan
 */
public class ComboCellEditor extends AbstractCellEditor
        implements TableCellEditor, ActionListener{
    
    private String stare;
    //private String[] stari;
    private final String pathToLog4j = Paths.get("./log4j.properties").toString();
    private static JComboBox<String> combo;
    public static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ComboCellEditor.class);
    
    public ComboCellEditor(JComboBox comb){
        //this.stari = stari;
        combo = comb;
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
            System.out.println("ActionPerformed");
            JComboBox c = (JComboBox) e.getSource();
            stare = (String)c.getSelectedItem();
            //System.out.println(stare);
            JTable t = (JTable) c.getParent();
            if (t!=null){
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
                ClientNotificationAsyncController.getInstance().postDataNotification(proiecte.get(index), TopicsEnum.PROIECT_STARE_MODIFICAT);
                ProiectController.getInstance().modifyProject(proiecte.get(index));
                AdminFrame.afisare();
                if (ProiecteSuspendateFrame.isVisi()) ProiecteSuspendateFrame.afisare();
                if (ProiecteTerminateFrame.isVisi()) ProiecteTerminateFrame.afisare();
            }
        } catch (RemoteException ex) {
            logger.error("Nu s.a putut efectua modificarea unui proiect!",ex);
        }
    }
    
}
