/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.gui;

import ComboCellAngajat.AngajatCellEditor;
import ComboCellAngajat.AngajatCellRenderer;
import ComboCellAngajat.AngajatTableModel;
import client.controller.AngajatController;
import db.Angajat;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author Razvan
 */
public class AngajatiActiviFrame extends javax.swing.JFrame {
    private static AngajatTableModel model;
    private static JTable table;
    private final String pathToLog4j = Paths.get("./log4j.properties").toString();
    public static final Logger logger = Logger.getLogger(AngajatiActiviFrame.class);

    /**
     * Creates new form AngajatiFrame
     */
    public AngajatiActiviFrame() {
        initComponents();
        PropertyConfigurator.configure(Paths.get(pathToLog4j).toString());
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(new BorderLayout());
        
        table = new JTable();
        afisare();
        
        table.setFont(new Font("Tahoma", Font.PLAIN, 18));
        table.setRowHeight(30);
        table.setName("activ");
        //table.getColumnModel().getColumn(0).setPreferredWidth(10);
        
        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setPreferredSize(new Dimension(700, 700));
         
        add(scrollpane, BorderLayout.CENTER);
    }
    
    public static boolean isVisi(){
        if(table!=null) return true;
        return false;
    }
    
    public static void afisare(){
        try{
            List<Angajat> angajati = AngajatController.getInstance().getAngajatiByStare("activ");
            List<String> stari = new ArrayList<>();
            stari.add("activ");
            stari.add("inactiv");
            
            model = new AngajatTableModel(angajati);

            table.setModel(model);
            table.setDefaultRenderer(Angajat.class, new AngajatCellRenderer());
            table.setDefaultEditor(String.class, new AngajatCellEditor(stari));
            table.getColumnModel().getColumn(0).setMaxWidth(50);
            
        }catch(RemoteException e){
            logger.error(e);
            JOptionPane.showMessageDialog(null, "Eroare");
        }catch(Exception ex){
            logger.error(ex);
        }
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 644, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 594, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
