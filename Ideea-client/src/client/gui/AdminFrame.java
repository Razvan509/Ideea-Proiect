/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.gui;

import ComboCellProiect.ComboCellEditor;
import ComboCellProiect.ComboCellRenderer;
import ComboCellProiect.ComboTableModel;
import Enum.TopicsEnum;
import client.controller.ActivitateController;
import client.controller.AngajatController;
import client.controller.ProiectController;
import db.Activitate;
import db.Angajat;
import db.Proiect;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import org.apache.log4j.PropertyConfigurator;
import ro.top.service.ClientNotificationController;
import ro.top.subscriber.Subscriber;

/**
 *
 * @author razvan
 */
public class AdminFrame extends javax.swing.JFrame implements Subscriber{
    
    private static ComboTableModel model;
    private static JComboBox combo;
    private static JTable table;
    private final String pathToLog4j = Paths.get("./log4j.properties").toString();
    public static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(AdminFrame.class);
    
    public AdminFrame(Angajat angajat) {
        initComponents();
        PropertyConfigurator.configure(Paths.get(pathToLog4j).toString());
        logger.info("A pornit aplicatia!");
        
        
        table = new JTable();
        String[] stari = new String[3];
        stari[0]="In derulare";
        stari[1]="Suspendat";
        stari[2]="Terminat";
        combo = new JComboBox(stari);
        
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle(angajat.getNume());
        setLayout(new BorderLayout());
        
        //logger.info("a inceput afisarea");
        afisare();
        //logger.info("s-a terminat afisarea");
        table.setFont(new Font("Tahoma", Font.PLAIN, 18));
        table.setRowHeight(30);
        table.setName("In derulare");
        table.setVisible(true);
        
        
        
        JScrollPane scrollpane = new JScrollPane(table);
        //scrollpane.setPreferredSize(new Dimension(500, 700));
        scrollpane.getVerticalScrollBar().setUnitIncrement(16); 
        scrollpane.setVisible(true);
        add(scrollpane);
        
        
        
        // ArrayList<String> angjPontVechi = new ArrayList<>();
        try {
            
            table.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e){
                    if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2){
                        try {
                            List<Proiect> proiecte = ProiectController.getInstance().getAllProjectsByStare(0);
                            int row = table.getSelectedRow();
                            new DetaliiProiectFrame(proiecte.get(row)).setVisible(true);
                            //JOptionPane.showMessageDialog(null, "S-a activat evenimentul boss pe randul "+row);
                        } catch (RemoteException ex) {
                            Logger.getLogger(AdminFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }
            });
            
            
            ClientNotificationController.getInstance().addSubscriber(TopicsEnum.PROIECT_ORA_MODIFICAT, this);
            //logger.info("s-a subscris la ora modif");
            Calendar today = Calendar.getInstance();
            today.add(Calendar.DATE,-5);
            List<Angajat> angajati = AngajatController.getInstance().getAngajatiByStare("activ");
            //logger.info("s-au luat angajatii");
            String output = "Cei care nu au mai bagat un pontaj de 4 zile sau mai mult sunt: \n";
            Activitate lastActiv;
            for(int i=0;i<angajati.size();i++){
                if(!(angajati.get(i).isAdmin())){
                    lastActiv = ActivitateController.getInstance().getLastDateActivityByAngajat(angajati.get(i));
                    if(lastActiv != null && (lastActiv.getDataPontaj()==null || today.getTime().after(lastActiv.getDataPontaj()))){
                        output+= angajati.get(i).getNume() + "\n";
                    }
                }   
            }
            //logger.info("s-au terminat activitatile");
            UIManager.put("OptionPane.messageFont", new Font("Tahoma", Font.BOLD, 14));
            JOptionPane.showMessageDialog(null, output);
            
        } catch (RemoteException ex) {
            logger.error("Eroare la preluarea angajatilor!",ex);
            JOptionPane.showMessageDialog(null, "Eroare");
        }catch(Exception ex){
            logger.error(ex,ex);
        }
    }
    
    public static void afisare(){
        try{
            List<Proiect> proiecte = ProiectController.getInstance().getAllProjectsByStare(0);
            //logger.info("s-au luat proiectele");
            
            model = new ComboTableModel(proiecte);
            //logger.info("model");
            table.setModel(model);
            //logger.info("s-a setat modelul");
            table.setDefaultRenderer(String.class, new ComboCellRenderer());
            //logger.info("s-a setat defaultRenderul");
            table.setDefaultEditor(String.class, new ComboCellEditor(combo));
            //logger.info("s-au facut modificarile");
            
            
            table.getColumnModel().getColumn(0).setMaxWidth(50);
            
        }catch(RemoteException e){
            logger.error(e);
            JOptionPane.showMessageDialog(null, "Eroare");
            
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

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jMenuBar1.setBackground(new java.awt.Color(51, 51, 255));
        jMenuBar1.setForeground(new java.awt.Color(255, 255, 255));
        jMenuBar1.setBorderPainted(false);

        jMenu1.setBackground(new java.awt.Color(51, 51, 255));
        jMenu1.setBorder(null);
        jMenu1.setForeground(new java.awt.Color(255, 255, 255));
        jMenu1.setText("Angajat");
        jMenu1.setBorderPainted(true);

        jMenuItem1.setBackground(new java.awt.Color(51, 51, 255));
        jMenuItem1.setForeground(new java.awt.Color(255, 255, 255));
        jMenuItem1.setText("Adauga Angajat");
        jMenuItem1.setBorder(null);
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem14.setBackground(new java.awt.Color(51, 51, 255));
        jMenuItem14.setForeground(new java.awt.Color(255, 255, 255));
        jMenuItem14.setText("Modifica Angajat");
        jMenuItem14.setBorder(null);
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem14);

        jMenuItem6.setBackground(new java.awt.Color(51, 51, 255));
        jMenuItem6.setForeground(new java.awt.Color(255, 255, 255));
        jMenuItem6.setText("Angajati Activi");
        jMenuItem6.setBorder(null);
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);

        jMenuItem8.setBackground(new java.awt.Color(51, 51, 255));
        jMenuItem8.setForeground(new java.awt.Color(255, 255, 255));
        jMenuItem8.setText("Angajati Inactivi");
        jMenuItem8.setBorder(null);
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem8);

        jMenuItem11.setBackground(new java.awt.Color(51, 51, 255));
        jMenuItem11.setForeground(new java.awt.Color(255, 255, 255));
        jMenuItem11.setText("Angajati Nepontati");
        jMenuItem11.setBorder(null);
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem11);

        jMenuItem10.setBackground(new java.awt.Color(51, 51, 255));
        jMenuItem10.setForeground(new java.awt.Color(255, 255, 255));
        jMenuItem10.setText("Adauga pontaj");
        jMenuItem10.setBorder(null);
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem10);

        jMenuItem7.setBackground(new java.awt.Color(51, 51, 255));
        jMenuItem7.setForeground(new java.awt.Color(255, 255, 255));
        jMenuItem7.setText("Modifica pontaj");
        jMenuItem7.setBorder(null);
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem7);

        jMenuItem9.setBackground(new java.awt.Color(51, 51, 255));
        jMenuItem9.setForeground(new java.awt.Color(255, 255, 255));
        jMenuItem9.setText("Pontaje");
        jMenuItem9.setBorder(null);
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem9);

        jMenuItem15.setBackground(new java.awt.Color(51, 51, 255));
        jMenuItem15.setForeground(new java.awt.Color(255, 255, 255));
        jMenuItem15.setText("Export");
        jMenuItem15.setBorder(null);
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem15);

        jMenuBar1.add(jMenu1);

        jMenu2.setBackground(new java.awt.Color(51, 51, 255));
        jMenu2.setForeground(new java.awt.Color(255, 255, 255));
        jMenu2.setText("Proiect");

        jMenuItem2.setBackground(new java.awt.Color(51, 51, 255));
        jMenuItem2.setForeground(new java.awt.Color(255, 255, 255));
        jMenuItem2.setText("Adauga Proiect");
        jMenuItem2.setBorder(null);
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem3.setBackground(new java.awt.Color(51, 51, 255));
        jMenuItem3.setForeground(new java.awt.Color(255, 255, 255));
        jMenuItem3.setText("Proiecte suspendate");
        jMenuItem3.setBorder(null);
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuItem4.setBackground(new java.awt.Color(51, 51, 255));
        jMenuItem4.setForeground(new java.awt.Color(255, 255, 255));
        jMenuItem4.setText("Proiecte terminate");
        jMenuItem4.setBorder(null);
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuItem5.setBackground(new java.awt.Color(51, 51, 255));
        jMenuItem5.setForeground(new java.awt.Color(255, 255, 255));
        jMenuItem5.setText("Modifica proiect");
        jMenuItem5.setBorder(null);
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuItem12.setBackground(new java.awt.Color(51, 51, 255));
        jMenuItem12.setForeground(new java.awt.Color(255, 255, 255));
        jMenuItem12.setText("Ore proiecte");
        jMenuItem12.setBorder(null);
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem12);

        jMenuItem13.setBackground(new java.awt.Color(51, 51, 255));
        jMenuItem13.setForeground(new java.awt.Color(255, 255, 255));
        jMenuItem13.setText("Rapoarte");
        jMenuItem13.setBorder(null);
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem13);

        jMenuBar1.add(jMenu2);

        jMenu3.setBackground(new java.awt.Color(51, 51, 255));
        jMenu3.setForeground(new java.awt.Color(255, 255, 255));
        jMenu3.setText("Delogare");
        jMenu3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu3MouseClicked(evt);
            }
        });
        jMenu3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu3ActionPerformed(evt);
            }
        });
        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1060, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 757, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
       new AdaugaAngajatFrame(null,false);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        new AdaugaProiectFrame();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        new ProiecteSuspendateFrame().setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        new ProiecteTerminateFrame().setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenu3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu3ActionPerformed
        dispose();
        new LoginFrame().setVisible(true);
    }//GEN-LAST:event_jMenu3ActionPerformed

    private void jMenu3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu3MouseClicked
        dispose();
        logger.info("S-a oprit aplicatia!");
        new LoginFrame().setVisible(true);
    }//GEN-LAST:event_jMenu3MouseClicked

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        try {
            JComboBox combo = new JComboBox();
            List<Proiect> proiecte = ProiectController.getInstance().getAll();
            combo.removeAllItems();
            combo.addItem("Selecteaza un proiect!");
            for (Proiect p:proiecte){
                combo.addItem(p);
            }
            JOptionPane.showMessageDialog(null, combo,"Ce proiect doriti sa modificati?",JOptionPane.QUESTION_MESSAGE);
            
            if(combo.getSelectedItem()!= "Selecteaza un proiect!"){ 
                Proiect proiect = (Proiect)combo.getSelectedItem();
            
            new ModificaProiectFrame(proiect).setVisible(true);
            }else{
                return;
            }
            
        } catch (RemoteException ex) {
            logger.error(ex);
            JOptionPane.showMessageDialog(null, "Eroare");
        }
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        new AngajatiActiviFrame().setVisible(true);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        try {
            JComboBox combo = new JComboBox();
            List<Angajat> angajati = AngajatController.getInstance().getAll();
            combo.removeAllItems();
            combo.addItem("Selecteaza un angajat!");
            for (Angajat p:angajati){
                combo.addItem(p);
            }
            JOptionPane.showMessageDialog(null, combo,"Selectati un angajat?",JOptionPane.QUESTION_MESSAGE);
            if(combo.getSelectedItem()!= "Selecteaza un angajat!"){ 
                Angajat angajat = (Angajat)combo.getSelectedItem();
                new AlegePontajFrame(angajat,true,null).setVisible(true);
            }else{
                return;
            }
        } catch (RemoteException ex) {
            logger.error(ex);
            JOptionPane.showMessageDialog(null, "Eroare");
        }
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        new AngajatiInactiviFrame().setVisible(true);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        new PontajeAngajatFrame().setVisible(true);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        try {
            JComboBox combo = new JComboBox();
            List<Angajat> angajati = AngajatController.getInstance().getAll();
            combo.removeAllItems();
            combo.addItem("Selectati un angajat!");
            for (Angajat p:angajati){
                combo.addItem(p);
            }
            JOptionPane.showMessageDialog(null, combo,"Selectati un angajat?",JOptionPane.QUESTION_MESSAGE);
            if(combo.getSelectedItem()!= "Selectati un angajat!"){ 
                Angajat angajat = (Angajat)combo.getSelectedItem();
                new ClientFrame(angajat,true).setVisible(true);
            }else{
                return;
            }
        } catch (RemoteException ex) {
            logger.error(ex);
            JOptionPane.showMessageDialog(null, "Eroare");
        }
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        logger.info("S-a oprit aplicatia!");
    }//GEN-LAST:event_formWindowClosing

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        new AngajatiNepontatiFrame().setVisible(true);
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        new OreProiectFrame().setVisible(true);
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        new RapoarteProiectFrame().setVisible(true);
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        try {
            JComboBox combo = new JComboBox();
            List<Angajat> angajati = AngajatController.getInstance().getAll();
            combo.removeAllItems();
            combo.addItem("Selectati un angajat!");
            for (Angajat p:angajati){
                combo.addItem(p);
            }
            JOptionPane.showMessageDialog(null, combo,"Selectati un angajat?",JOptionPane.QUESTION_MESSAGE);
            if(combo.getSelectedItem()!= "Selectati un angajat!"){ 
                Angajat angajat = (Angajat)combo.getSelectedItem();
                new AdaugaAngajatFrame(angajat,true).setVisible(true);
            }else{
                return;
            }
        } catch (RemoteException ex) {
            Logger.getLogger(AdminFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        new ExportFrame();
    }//GEN-LAST:event_jMenuItem15ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    // End of variables declaration//GEN-END:variables

    @Override
    public void newNotification(String string) {
        switch(string){
            case TopicsEnum.PROIECT_ORA_MODIFICAT:{
                table.repaint();
                
            }break;
        }
    }

    @Override
    public void newDataNotification(Object o, String string) {
        
    }
}
