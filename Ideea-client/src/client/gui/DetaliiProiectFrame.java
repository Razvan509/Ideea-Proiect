/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.gui;

import client.controller.ActivitateController;
import client.controller.ProiectController;
import static client.gui.AdminFrame.logger;
import db.Activitate;
import db.Angajat;
import db.Proiect;
import java.awt.Dimension;
import java.awt.Font;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import rmi.Pair;

/**
 *
 * @author Razvan
 */
public class DetaliiProiectFrame extends javax.swing.JFrame {
    private Proiect proiect;
    DefaultListModel model1 = new DefaultListModel();
    DefaultListModel model2 = new DefaultListModel();
    DefaultListModel model3 = new DefaultListModel();
    /**
     * Creates new form DetaliiProiectFrame
     */
    public DetaliiProiectFrame(Proiect proiect) {
        initComponents();
        this.proiect = proiect;
        
        jTextArea1.setText(null);
        jTextArea1.append("Nume proiect: "+proiect.getNume()+"\n");
        jTextArea1.append("Nume beneficiar: "+proiect.getBeneficiar()+"\n");
        jTextArea1.append("Adresa: "+proiect.getAdresa()+"\n");
        jTextArea1.append("Numar de etaje: "+proiect.getNrEtaje()+"\n");
        jTextArea1.append("Numar de etaje subsol: "+proiect.getNrEtajeSubsol()+"\n");
        jTextArea1.append("Nume arhitect: "+proiect.getNumeArhitect()+"\n");
        jTextArea1.append("Numar de corpuri: "+proiect.getCorpuri()+"\n");
        try {
            jTextArea1.append("Total ore: " + ProiectController.getInstance().oreProiect(proiect));
        } catch (RemoteException ex) {
            logger.error(ex,ex);
        }
        
        JPanel jPanel1 = new JPanel();
        JPanel jPanel2 = new JPanel();
        JPanel jPanel3 = new JPanel();
        
        JList jList1 = new JList();
        JList jList2 = new JList();
        JList jList3 = new JList();
        
        JScrollPane sp1 = new JScrollPane(jList1);
        JScrollPane sp2 = new JScrollPane(jList2);
        JScrollPane sp3 = new JScrollPane(jList3);
        
        //jList1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        sp1.setPreferredSize(new Dimension(300, 500));
        sp2.setPreferredSize(new Dimension(300, 500));
        sp3.setPreferredSize(new Dimension(300, 500));
        
        jList1.setVisibleRowCount(19);
        jList2.setVisibleRowCount(19);
        jList3.setVisibleRowCount(19);
        
        jPanel1.add(sp1);
        jPanel2.add(sp2);
        jPanel3.add(sp3);
        
        /*jPanel1.setBackground(Color.red);
        jPanel2.setBackground(Color.blue);
        jPanel3.setBackground(Color.green);*/
        
        jList1.setModel(model1);
        jList2.setModel(model2);
        jList3.setModel(model3);
        
        jList1.setFont(new Font("Tahoma", Font.PLAIN, 20));
        jList2.setFont(new Font("Tahoma", Font.PLAIN, 20));
        jList3.setFont(new Font("Tahoma", Font.PLAIN, 20));
        
        
        
        jTextArea1.setEditable(false);
        
        jTabbedPane1.addTab("Angajat", jPanel1);
        jTabbedPane1.addTab("Luna", jPanel2);
        jTabbedPane1.addTab("Angajat si Luna", jPanel3);
        
        
        
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        
        
        populareTabbedPaneAngajat();
        populareTabbedPaneLuna();
        populareTabbedPaneAmbele();
    }
    
    private void populareTabbedPaneLuna(){
        model2.clear();
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.set(end.get(Calendar.YEAR),end.get(Calendar.MONTH),1,0,0,0);
        try {
            Activitate first = ActivitateController.getInstance().getFirstActivityProject(proiect);
            System.out.println(first.toString());
            
            //System.out.println(end + " " + start);
            long sum = 0;
            long ore = ActivitateController.getInstance().getOreProiectBetweenDate(proiect, start.getTime(), end.getTime());
            sum+=ore;
            model2.addElement(end.getDisplayName(Calendar.MONTH,Calendar.LONG , Locale.getDefault()) + " " + 
                    end.get(Calendar.YEAR) + " ore: " + ore);
            System.out.println(start.getTime() + " "+ end.getTime());
            start.set(Calendar.MONTH , start.get(Calendar.MONTH)-1);
            end.set(Calendar.MONTH,end.get(Calendar.MONTH)-1);
            end.set(Calendar.DATE,end.getActualMaximum(Calendar.DATE));
            end.set(Calendar.HOUR,0);
            end.set(Calendar.MINUTE,0);
            System.out.println(start.getTime() + " "+ end.getTime());
            while(first.getDataPontaj().before(end.getTime())){
                ore = ActivitateController.getInstance().getOreProiectBetweenDate(proiect, start.getTime(), end.getTime());
                sum+=ore;
                model2.addElement(end.getDisplayName(Calendar.MONTH,Calendar.LONG , Locale.getDefault()) + " " + 
                    end.get(Calendar.YEAR) + " ore: " + ore);
                start.set(Calendar.MONTH , start.get(Calendar.MONTH)-1);
                end.set(Calendar.DATE,1);
                end.set(Calendar.MONTH,end.get(Calendar.MONTH)-1);
                end.set(Calendar.DATE,end.getActualMaximum(Calendar.DATE));
            }
            //System.out.println(new SimpleDateFormat("d").format(end));
            
        } catch (RemoteException ex) {
            logger.error(ex,ex);
        } catch (NullPointerException e){
            JOptionPane.showMessageDialog(null, "Proiectul nu are pontaje!");
        }
        
    }
    
    private void populareTabbedPaneAngajat(){
        try {
            model1.clear();
            List<Angajat> angajati = ActivitateController.getInstance().getAngajatiOnProject(proiect);
            int sum = 0;
            for(Angajat a:angajati){
                int ore = ActivitateController.getInstance().getOreProiectByAngajat(proiect, a);
                model1.addElement(a + " " +ore);
                sum += ore;
            }
            //model1.addElement("Total: " + sum);
        } catch (RemoteException ex) {
            logger.error(ex,ex);
        }
    }
    
    private void populareTabbedPaneAmbele(){
        model3.clear();
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.set(end.get(Calendar.YEAR),end.get(Calendar.MONTH),1,0,0,0);
        try {
            Activitate first = ActivitateController.getInstance().getFirstActivityProject(proiect);
            System.out.println(first.toString());
            
            //System.out.println(end + " " + start);
            long sum = 0;
            long ore = ActivitateController.getInstance().getOreProiectBetweenDate(proiect, start.getTime(), end.getTime());
            sum+=ore;
            model3.addElement(end.getDisplayName(Calendar.MONTH,Calendar.LONG , Locale.getDefault()) + " " + 
                    end.get(Calendar.YEAR) + " ore: " + ore);
            
            List<Angajat> angajati = ActivitateController.getInstance().getAngajatiOnProjectBetweenDates(proiect,
                start.getTime(),end.getTime());
            if (angajati.size()>0){
                for(Angajat a:angajati){
                    Pair x = ActivitateController.getInstance().getOreProiectByAngajatBetweenDate(proiect,
                            a,start.getTime(),end.getTime());
                    model3.addElement("-> " + a + " " +x.getX());

                }
            }
            System.out.println(start.getTime() + " "+ end.getTime());
            start.set(Calendar.MONTH , start.get(Calendar.MONTH)-1);
            end.set(Calendar.MONTH,end.get(Calendar.MONTH)-1);
            end.set(Calendar.DATE,end.getActualMaximum(Calendar.DATE));
            end.set(Calendar.HOUR,0);
            end.set(Calendar.MINUTE,0);
            System.out.println(start.getTime() + " "+ end.getTime());
            while(first.getDataPontaj().before(end.getTime())){
                ore = ActivitateController.getInstance().getOreProiectBetweenDate(proiect, start.getTime(), end.getTime());
                sum+=ore;
                model3.addElement(end.getDisplayName(Calendar.MONTH,Calendar.LONG , Locale.getDefault()) + " " + 
                    end.get(Calendar.YEAR) + " ore: " + ore);
                angajati = ActivitateController.getInstance().getAngajatiOnProjectBetweenDates(proiect,
                    start.getTime(),end.getTime());
                
                if(angajati.size()>0){
                    for(Angajat a:angajati){
                        long x = ActivitateController.getInstance().getOreProiectByAngajatBetweenDate(proiect,
                            a,start.getTime(),end.getTime()).getX();
                        model3.addElement("-> " + a + " " + x);

                    }
                }
                start.set(Calendar.MONTH , start.get(Calendar.MONTH)-1);
                end.set(Calendar.DATE,1);
                end.set(Calendar.MONTH,end.get(Calendar.MONTH)-1);
                end.set(Calendar.DATE,end.getActualMaximum(Calendar.DATE));
            }
            //System.out.println(new SimpleDateFormat("d").format(end));
            
        } catch (RemoteException ex) {
            logger.error(ex,ex);
        } catch (NullPointerException e){
            JOptionPane.showMessageDialog(null, "Proiectul nu are pontaje!");
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTabbedPane1 = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
