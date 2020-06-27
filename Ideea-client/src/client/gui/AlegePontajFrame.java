/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.gui;

import Enum.TopicsEnum;
import client.controller.ActivitateController;
import client.controller.DictionarController;
import db.Activitate;
import db.Angajat;
import db.Proiect;
import java.awt.event.MouseEvent;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import org.apache.log4j.PropertyConfigurator;
import ro.top.service.ClientNotificationAsyncController;

/**
 *
 * @author Razvan
 */
public class AlegePontajFrame extends javax.swing.JFrame {

    
    private Angajat angajat;
    private DefaultListModel model;
    private ClientFrame client;
    private boolean admin;
    private final String pathToLog4j = Paths.get("./log4j.properties").toString();
    public static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(AlegePontajFrame.class);
    /**
     * Creates new form ModificaPontajFrame
     */
    public AlegePontajFrame(Angajat angajat,boolean admin,ClientFrame client) {
        initComponents();
        PropertyConfigurator.configure(Paths.get(pathToLog4j).toString());
        
        this.angajat = angajat;
        this.client = client;
        this.admin = admin;
        
        
        jDateChooser1.setDate(new Date());
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(angajat.getNume());
        
        populareLista();
    }
    
    public final void populareLista(){
        try {
            model = new DefaultListModel();
            Date data = jDateChooser1.getDate();
            List<Activitate> activitati = ActivitateController.getInstance().getActivitatiAngajatZi(angajat, data);
            
            jList1.setModel(model);
            model.clear();
            
            for(Activitate a:activitati){
                model.addElement(a.toString() + " " +DictionarController.getInstance().findByCod(a.getCod())+" "+a.getId());
            }
        } catch (RemoteException ex) {
            logger.error(ex);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jList1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("(Alege ziua si apasa dublu click pe pontajul dorit)");

        jDateChooser1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jDateChooser1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser1PropertyChange(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(117, 117, 117)
                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(160, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        Date data = jDateChooser1.getDate();
        Calendar today = Calendar.getInstance();
        today.add(Calendar.DATE,-8);
        if(data.before(today.getTime())&&!admin){
            JOptionPane.showMessageDialog(null, "Puteti modifica un pontaj cu maxim 7 zile in urma!");
            return;
        }
        if (evt.getButton() == MouseEvent.BUTTON1){
            if (evt.getClickCount()==2){
                Pattern lastIntPattern = Pattern.compile("[^0-9]+([0-9]+)$");
                String pontaj = jList1.getSelectedValue();
                Matcher matcher;
                if(pontaj != null)
                    matcher = lastIntPattern.matcher(pontaj);
                else
                    return;
                if (matcher.find()){
                    try {
                        String str = matcher.group(1);
                        int lastNumber = Integer.parseInt(str);
                        Activitate activitate = ActivitateController.getInstance().findById(lastNumber);
                        String []options = {"Modifica","Sterge"};
                        int option = -1;
                        option = JOptionPane.showOptionDialog(null, "Doriti sa modificati pontajul sau sa il stergeti?",
                                null, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                                options, null);
                        if(option == 0){
                            Proiect proiect = activitate.getProiect();
                            new ModificaPontajFrame(angajat, proiect, activitate,admin,client).setVisible(true);
                            dispose();
                        }else{
                            if (option == 1){
                                ActivitateController.getInstance().deleteActivity(activitate);
                                JOptionPane.showMessageDialog(null, "Pontaj sters");
                                ClientNotificationAsyncController.getInstance().postNotification(TopicsEnum.PROIECT_ORA_MODIFICAT);
                                if(!admin) client.populareLista();
                                //dispose();
                                populareLista();
                            }
                        }
  
                    } catch (RemoteException ex) {
                        JOptionPane.showMessageDialog(null, "Ceva nu a mers bine!");
                        logger.error(ex);
                    }catch(Exception ex){
                        logger.error(ex);
                    }
                }
            }
        }
    }//GEN-LAST:event_jList1MouseClicked

    private void jDateChooser1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser1PropertyChange
        populareLista();
    }//GEN-LAST:event_jDateChooser1PropertyChange

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
