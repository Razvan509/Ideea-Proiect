/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.gui;

import client.controller.ActivitateController;
import client.controller.DictionarController;
import db.Activitate;
import db.Angajat;
import db.Proiect;
import java.awt.Font;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author Razvan
 */
public class ModificaPontajFrame extends javax.swing.JFrame {

    /**
     * Creates new form ModificaPontajFrame
     */
    private Angajat angajat;
    private Proiect proiect;
    private Activitate activitate;
    private int sumaOre=0;
    private int sumaMinute=0;
    private boolean admin;
    private ClientFrame client;
    private final String pathToLog4j = Paths.get("./log4j.properties").toString();
    public static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ModificaPontajFrame.class);
    
    public ModificaPontajFrame(Angajat angajat,Proiect proiect,Activitate activitate,boolean admin,ClientFrame client) {
        initComponents();
        PropertyConfigurator.configure(Paths.get(pathToLog4j).toString());
        
        
        this.admin = admin;
        this.angajat = angajat;
        this.proiect = proiect;
        this.activitate = activitate;
        this.client = client;
        
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        
        afisare();
        calculeazaTimp(jDateChooser1.getDate());
        jLabel8.setText(sumaOre+" ore");
        jLabel9.setText(sumaMinute+" minute");
    }
    
    public void afisare(){
        try {
            jComboBox2.removeAllItems();
            for(int i=-proiect.getNrEtajeSubsol();i<=proiect.getNrEtaje();i++){
                if(i<0){
                    jComboBox2.addItem("subsol "+i);
                }else{
                    if(i==0){
                        jComboBox2.addItem("parter");
                    }else{
                        jComboBox2.addItem("etaj "+i);
                    }
                }
            }
            jTextField1.setText(activitate.toString() + " " +DictionarController.getInstance().findByCod(activitate.getCod()));
            jTextField1.setEditable(false);
            
            if (activitate.getEtaj()==null){
                jLabel4.setVisible(false);
                jComboBox2.setVisible(false);
            }else{
                jComboBox2.setSelectedItem(activitate.getEtaj());
            }
            char litera = 'A';
            if (proiect.getCorpuri()>1){
                for(int i=0;i<proiect.getCorpuri();i++){
                    jComboBox1.addItem(litera+"");
                    litera+=1;
                    
                }
                jComboBox1.setSelectedItem(activitate.getCorp()+"");
            }else{
                jLabel5.setVisible(false);
                jComboBox1.setVisible(false);
            }
            jTextField2.setText(activitate.getOreMunca()+"");
            jTextField3.setText(activitate.getMinuteMunca()+"");
            jDateChooser1.setDate(activitate.getDataPontaj());
            
            jTextPane1.setText(null);
            jTextPane1.setFont(new Font("Tahoma", Font.PLAIN, 18));
            Document doc = jTextPane1.getDocument();
            
            doc.insertString(doc.getLength(),"Nume proiect: "+proiect.getNume()+"\n",null);
            doc.insertString(doc.getLength(),"Nume beneficiar: "+proiect.getBeneficiar()+"\n",null);
            doc.insertString(doc.getLength(),"Adresa: "+proiect.getAdresa()+"\n",null);
            doc.insertString(doc.getLength(),"Numar de etaje: "+proiect.getNrEtaje()+"\n",null);
            doc.insertString(doc.getLength(),"Numar de etaje subsol: "+proiect.getNrEtajeSubsol()+"\n",null);
            doc.insertString(doc.getLength(),"Nume arhitect: "+proiect.getNumeArhitect()+"\n",null);
            doc.insertString(doc.getLength(),"Numar de corpuri: "+proiect.getCorpuri()+"\n",null);
            
            
            
            
        } catch (BadLocationException ex) {
            JOptionPane.showMessageDialog(null, "Eroare");
            logger.error(ex);
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(null, "Eroare");
            logger.error(ex);
        }catch(Exception ex){
            logger.error(ex);
        }
        
    }
    
    public void calculeazaTimp(Date data){
        try {
            sumaOre = sumaMinute = 0;
            List<Activitate> list = ActivitateController.getInstance().getActivitatiAngajatZi(angajat, data);
            for(Activitate a: list){
                sumaOre+=a.getOreMunca();
                if(sumaMinute+a.getMinuteMunca()>59){
                    sumaOre+=(sumaMinute+a.getMinuteMunca())/60;
                    sumaMinute+=(sumaMinute+a.getMinuteMunca())%60;
                }
            }
            
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(null, "Eroare");
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

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Pontaj vechi:");

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Ore:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Minute:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Etaj:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Corp:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Data:");

        jDateChooser1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jDateChooser1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser1PropertyChange(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton1.setText("Modifica");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jTextField3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jScrollPane1.setViewportView(jTextPane1);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel7.setText("Total:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("jLabel8");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("jLabel9");

        jComboBox2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField2)
                                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(48, 48, 48))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 90, Short.MAX_VALUE))
                            .addComponent(jTextField1)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(107, 107, 107)
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9))
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(jLabel8)
                        .addComponent(jLabel9)))
                .addGap(29, 29, 29))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try{
            int oreVechi = activitate.getOreMunca();
            int minVechi = activitate.getMinuteMunca();
            int ore = Integer.parseInt(jTextField2.getText());
            int minute = Integer.parseInt(jTextField3.getText());

            if(jComboBox2.isVisible()){
                activitate.setEtaj((String)jComboBox2.getSelectedItem());
            }
            if(jComboBox1.isVisible()){
                String s = (String)jComboBox1.getSelectedItem();
                activitate.setCorp(s.toCharArray()[0]);
            }
            
            Date data = jDateChooser1.getDate();
            Calendar today = Calendar.getInstance();
            
            calculeazaTimp(data);
            
            if (ore >= 0 && ore <17){
                int temp = sumaOre + ore - oreVechi;
                int min = (minute + sumaMinute - minVechi)/60;
                if (temp+min>16) {
                    JOptionPane.showMessageDialog(null, "Nu poti lucra mai mult de 16 ore pe zi!");
                    return;
                }else{
                    if (temp+min==16 && (minute + sumaMinute)%60>0){
                        JOptionPane.showMessageDialog(null, "Nu poti lucra mai mult de 16 ore pe zi!");
                        return;
                    }else {
                        activitate.setOreMunca(ore);
                        activitate.setMinuteMunca(minute);
                    }
                        
                }
                
            }
            else {
                JOptionPane.showMessageDialog(null, "Numar de ore prea mare sau negativ!");
                return;
            }
            
            if(admin){
                if (data.after(today.getTime())){
                    JOptionPane.showMessageDialog(null, "Viitorul este necunoscut!");
                    return;
                }
                activitate.setDataPontaj(data);
            }else{
                if (data.after(today.getTime())){
                    JOptionPane.showMessageDialog(null, "Viitorul este necunoscut!");
                    return;
                }else{
                    today.add(Calendar.DATE,-8);
                    if(data.before(today.getTime())){
                        JOptionPane.showMessageDialog(null, "Data introdusa nu este valida! Poti modifica un pontaj cu maxim 7 zile in urma!");
                        today = Calendar.getInstance();
                        return;
                    }
                    else{
                        activitate.setDataPontaj(data);
                    }
                }
            }

            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(null, "Pontajul dumneavoastra: "+activitate.toString()+" " 
                    +DictionarController.getInstance().findByCod(activitate.getCod())
                    +"\nDoriti sa continuati?","Confirmare!",dialogButton);
            if (dialogResult == JOptionPane.YES_OPTION){
                ActivitateController.getInstance().modifyActivity(activitate);
                if(!admin) client.populareLista();
            }else{
                activitate.setOreMunca(oreVechi);
                activitate.setMinuteMunca(minVechi);
                return;
            }
            dispose();
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Nu ati introdus un numar!");
            return;
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(null, "Eroare");
            logger.error(ex);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Eroare");
            logger.error(ex);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jDateChooser1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser1PropertyChange
        calculeazaTimp(jDateChooser1.getDate());
        jLabel8.setText(sumaOre+" ore");
        jLabel9.setText(sumaMinute+" minute");
    }//GEN-LAST:event_jDateChooser1PropertyChange

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables
}
