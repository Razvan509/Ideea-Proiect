/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.gui;

import ComboCellProiect.ComboCellEditor;
import ComboCellProiect.ComboCellRenderer;
import ComboCellProiect.ComboTableModel;
import client.controller.ProiectController;
import static client.gui.ProiecteSuspendateFrame.afisare;
import db.Proiect;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 *
 * @author Razvan
 */
public class ProiecteTerminateFrame extends javax.swing.JFrame {

    /**
     * Creates new form ProiecteTerminateFrame
     */
    
    private static ComboTableModel model;
    private static JTable table;
    
    public ProiecteTerminateFrame() {
        initComponents();
        
        table =  new JTable();
        
        setTitle("Proiecte terminate!");
        setLocationRelativeTo(null);
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        afisare();
        
        table.setFont(new Font("Tahoma", Font.PLAIN, 18));
        table.setRowHeight(30);
        table.setName("Terminat");
        
        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setPreferredSize(new Dimension(500, 300));
        setLayout(new BorderLayout());
         
        add(scrollpane, BorderLayout.CENTER);
    }
    
    public static boolean isVisi(){
        if(table!=null) return true;
        return false;
    }
    
    public static void afisare(){
        try{
            List<Proiect> proiecte = ProiectController.getInstance().getAllProjectsByStare(2);
            List<String> stari = new ArrayList<>();
            stari.add("In derulare");
            stari.add("Suspendat");
            stari.add("Terminat");
            
            model = new ComboTableModel(proiecte);
            //table.set();
            table.setModel(model);
            table.setDefaultRenderer(Proiect.class, new ComboCellRenderer());
            table.setDefaultEditor(String.class, new ComboCellEditor(stari));
            table.getColumnModel().getColumn(0).setMaxWidth(50);
            
            
            
            
        }catch(Exception e){
            e.printStackTrace();
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
        setPreferredSize(new java.awt.Dimension(1060, 747));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 585, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 464, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
