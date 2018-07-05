/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.gui;

import client.controller.ProiectController;
import db.Angajat;
import db.Proiect;
import java.awt.Font;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author razvan
 */
public class AdminFrame extends javax.swing.JFrame {
    
    public static DefaultTableModel model;
    
    public AdminFrame(Angajat angajat) {
        initComponents();
        
        setLocationRelativeTo(null);
        setVisible(true);
        
        setTitle(angajat.getNume());
        jTable1.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 18));
        //model = (DefaultTableModel) jTable1.getModel();
        
        afisare();
    }
    
    public static void afisare(){
        try{
            model = new DefaultTableModel(null,new String[]{"Nume","Adresa","Buget","Ore alocate","Ore lucrate","Procent ore"});
            jTable1.setModel(model);
            List<Proiect> proiecte = ProiectController.getInstance().getAll();
            Object [] row = new Object[6];
            for (int i=0;i<proiecte.size();i++){
                long nrOre = (long)ProiectController.getInstance().oreProiect(proiecte.get(i));
                double procent = (nrOre*100.0)/proiecte.get(i).getNrOreAlocate();
                row[0] = proiecte.get(i).getNume();
                row[1] = proiecte.get(i).getAdresa();
                row[2] = proiecte.get(i).getBuget();
                row[3] = proiecte.get(i).getNrOreAlocate();
                row[4] = nrOre;
                row[5] = procent + "%";
                model.addRow(row);
            }
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

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nume", "Adresa", "Buget", "Ore alocate", "Ore lucrate", "Procent ore"
            }
        ));
        jTable1.setRowHeight(26);
        jScrollPane2.setViewportView(jTable1);

        jMenu1.setText("Angajat");

        jMenuItem1.setText("Adauga Angajat");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Proiect");

        jMenuItem2.setText("Adauga Proiect");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1060, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 737, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
       new AdaugaAngajatFrame();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        new AdaugaProiectFrame();
    }//GEN-LAST:event_jMenuItem2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JScrollPane jScrollPane2;
    private static javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
