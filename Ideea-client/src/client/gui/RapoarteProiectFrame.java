/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.gui;

import client.controller.ActivitateController;
import client.controller.ProiectController;
import static client.gui.ClientFrame.logger;
import com.opencsv.CSVWriter;
import db.Activitate;
import db.Proiect;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import raport.RaportBrut;
import raport.RaportOreAngajat;

/**
 *
 * @author Razvan
 */
public class RapoarteProiectFrame extends javax.swing.JFrame {

    /**
     * Creates new form RapoarteProiectFrame
     */
    
    private int cod;
    private String []std = {"Fundatie","Cofraje","Elemente Verticale","Grinzi","Placi","Goluri","Scari","Altele"}; 
    private Proiect proiect;
    //private long total = 0;
    private DefaultListModel model1 = new DefaultListModel();
    private DefaultListModel model2 = new DefaultListModel();
    private List<Activitate> activitati;
    private ArrayList<RaportOreAngajat> raport = null;
    
    private String csvPath = "E:\\IdeeaProiect\\Test";
    private File file;
    
    public RapoarteProiectFrame() {
        initComponents();
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        
        
        
        
        jDateChooser1.setDate(new Date());
        jDateChooser2.setDate(new Date());
        
        jDateChooser1.setEnabled(false);
        jDateChooser2.setEnabled(false);
        //jTable1.setVisible(false);
        
        proiectComboBox.setPreferredSize(new Dimension(200,28));
                
        //setLayout(new BorderLayout());
        
        populareComboProiect();
        populareComboEtapaElement();
        
        perioadaCheckBox.setSelected(false);
        proiectCheckBox.setSelected(false);
        
        etajCheckBox.setSelected(false);
        corpCheckBox.setSelected(false);
        //stadiuCheckBox.setSelected(false);
        elementCheckBox.setSelected(false);
        
        proiectComboBox.setEnabled(false);
        etapaComboBox.setEnabled(true);
        etajComboBox.setEnabled(false);
        corpComboBox.setEnabled(false);
        //stadiuComboBox.setEnabled(false);
        elementComboBox.setEnabled(false);
        
        jTextArea1.setEditable(false);
            
        JPanel jPanel3 = new JPanel();
        JPanel jPanel4 = new JPanel();

        JList jList1 = new JList();
        JList jList2 = new JList();

        JScrollPane sp1 = new JScrollPane(jList1);
        JScrollPane sp2 = new JScrollPane(jList2);

        sp1.setPreferredSize(new Dimension(300, 500));
        sp2.setPreferredSize(new Dimension(500, 500));

        jList1.setVisibleRowCount(19);
        jList2.setVisibleRowCount(19);
        
        jPanel3.add(sp1);
        jPanel4.add(sp2);

        jList1.setModel(model1);
        jList2.setModel(model2);
        
        jList1.setFont(new Font("Tahoma", Font.PLAIN, 20));
        jList2.setFont(new Font("Tahoma", Font.PLAIN, 20));
        jTextArea1.setFont(new Font("Tahoma", Font.PLAIN, 20));

        jTabbedPane1.addTab("Angajat", jPanel3);
        jTabbedPane1.addTab("Detaliat", jPanel4);
    }
    
    private void populareComboEtapaElement(){
        etapaComboBox.addItem("Discutii");
        etapaComboBox.addItem("Proiectare");
        etapaComboBox.addItem("Santier");
        etapaComboBox.addItem("Reproiectare");
        etapaComboBox.addItem("Documentare Tehnica");
        
        elementComboBox.addItem("Global");
        for(String s:std){
            elementComboBox.addItem(s);
        }
        
        elementComboBox.addItem("Parte Scrisa");
        elementComboBox.addItem("Parte Desenata");
        elementComboBox.addItem("Modelare 3D");
        
    }
    
    private void populareComboProiect(){
        try {
            List<Proiect> lista = ProiectController.getInstance().getAll();
            
            for(Proiect p : lista){
                proiectComboBox.addItem(p.getNume());
            }
            
            
        } catch (RemoteException ex) {
            //logger.error(angajat.getNume() + " are eroarea: ",ex);
            logger.error(ex);
            JOptionPane.showMessageDialog(null, "Eroare");
        }catch(Exception ex){
            //logger.error(angajat.getNume() + " are eroarea: ",ex);
            logger.error(ex);
        }
    }
    
    private void populareComboEtaj(){
        etajComboBox.removeAllItems();
        etajComboBox.addItem("Fundatie");
        etajComboBox.addItem("Mustati");
        
        
        if(proiect != null){
            for(int i=-proiect.getNrEtajeSubsol();i<=proiect.getNrEtaje();i++){
                if(i<0){
                    etajComboBox.addItem("subsol "+i);
                }else{
                    if(i==0){
                        etajComboBox.addItem("parter");
                    }else{
                        etajComboBox.addItem("etaj "+i);
                    }
                }
            }
        }
        
        
    }
    
    private void populareComboCorp(){
        corpComboBox.removeAllItems();
        for(int i=0;i<proiect.getCorpuri();i++){
            char litera = 'A';
            litera+=i;
            corpComboBox.addItem("" + litera);
        }
    }
    
    
    
    private void populareAngajatPane(){
        model1.clear();
        long sum = 0;
        String select="select r.ang_nume,sum(r.act_min)/60 from rapoarte r group by r.ang_nume";
        try {
            raport = ActivitateController.getInstance().getRaportOreAngajat(select);
            raport.forEach((r) -> model1.addElement(r));
            
            for(RaportOreAngajat r:raport){
                sum+=r.getOre();
            }
            model1.addElement("Total ore: " + sum);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
        
    }
    
    private void populareDetaliatPane(){
        model2.clear();
        String select = "select distinct aux.* from (select pr_nume,dict_desc,act_corp,act_etaj,ang_nume,sum(act_min)/60 from rapoarte \n" +
                        "group by pr_nume,dict_desc,act_corp,act_etaj,ang_nume with rollup) aux\n" +
                        "order by aux.pr_nume ASC, aux.dict_desc ASC, aux.act_corp ASC, aux.act_etaj ASC, aux.ang_nume ASC";
        System.out.println(select);
        try {
            ArrayList<RaportBrut> act = ActivitateController.getInstance().getRaportOrd(select);
            //act.forEach(System.out::println);
            
            for(int i=1;i<act.size();i++){
                RaportBrut r = act.get(i);
                if(r.getDescriere().equals("null") && r.getCorp() == 'n' && r.getEtaj().equals("null") && r.getNumeAngajat().equals("null")){
                    model2.addElement(r.getNumeProiect() + " " + r.getMinute());
                }else{
                    if(r.getCorp() == 'n' && r.getEtaj().equals("null") && r.getNumeAngajat().equals("null")){
                        model2.addElement("-> " + r.getDescriere()+ " " + r.getMinute());
                    }else{
                        if(r.getEtaj().equals("null") && r.getNumeAngajat().equals("null")){
                            if (r.getCorp() != ' ' && r.getCorp() != '-'){
                                model2.addElement("--> Corp " + r.getCorp() + " " + r.getMinute());
                            }else model2.addElement("--> Corp neatribuit " + r.getMinute());
                        }else{
                            if(r.getNumeAngajat().equals("null")){
                                if(!r.getEtaj().equals("-")){
                                    model2.addElement("---> " + r.getEtaj() + " " + r.getMinute());
                                }else model2.addElement("---> Etaj neatribuit " + r.getMinute());
                            }else{
                                model2.addElement("----> " + r.getNumeAngajat()+ " " + r.getMinute());
                            }
                        }
                    }
                }
                
                
                
            }
        } catch (RemoteException ex) {
            ex.printStackTrace();
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        perioadaCheckBox = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        proiectComboBox = new javax.swing.JComboBox<>();
        proiectCheckBox = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        etapaComboBox = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        etajComboBox = new javax.swing.JComboBox<>();
        etajCheckBox = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        corpComboBox = new javax.swing.JComboBox<>();
        corpCheckBox = new javax.swing.JCheckBox();
        jButton2 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        stadiuComboBox = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        elementComboBox = new javax.swing.JComboBox<>();
        elementCheckBox = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(51, 51, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(700, 32767));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("De la:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Pana la:");

        jDateChooser1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jDateChooser2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        perioadaCheckBox.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        perioadaCheckBox.setText("Perioada");
        perioadaCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                perioadaCheckBoxActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Proiect:");

        proiectComboBox.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        proiectComboBox.setPreferredSize(new java.awt.Dimension(200, 28));
        proiectComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                proiectComboBoxActionPerformed(evt);
            }
        });

        proiectCheckBox.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        proiectCheckBox.setText("Proiect");
        proiectCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                proiectCheckBoxActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Etapa:");

        etapaComboBox.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        etapaComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                etapaComboBoxActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Etaj:");

        etajComboBox.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        etajCheckBox.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        etajCheckBox.setText("Etaj");
        etajCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                etajCheckBoxActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Corp:");

        corpComboBox.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        corpCheckBox.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        corpCheckBox.setText("Corp");
        corpCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                corpCheckBoxActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton2.setText("Raport");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Stadiu:");

        stadiuComboBox.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        stadiuComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stadiuComboBoxActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Element:");

        elementComboBox.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        elementCheckBox.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        elementCheckBox.setText("Element");
        elementCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                elementCheckBoxActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton3.setText("Export");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(perioadaCheckBox))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel7))
                                        .addGap(30, 30, 30)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(etapaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(proiectComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(stadiuComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(elementComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(etajComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(corpComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel8))
                                .addGap(31, 31, 31)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(proiectCheckBox)
                                    .addComponent(corpCheckBox)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(elementCheckBox)
                                            .addComponent(etajCheckBox))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING))))))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(perioadaCheckBox)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(proiectComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proiectCheckBox))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(etapaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(stadiuComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(elementComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                                .addComponent(elementCheckBox)
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(etajComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(etajCheckBox)
                                .addGap(26, 26, 26)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(corpCheckBox))
                            .addComponent(corpComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jButton2)
                        .addGap(31, 31, 31)
                        .addComponent(jButton3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 51, 153));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 603, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
        //JOptionPane.showMessageDialog(null, "ba!");
        //wjPanel1.setBounds(jPanel1.getX(), jPanel1.getY(), this.getWidth()-jPanel1.getX(), this.getHeight());
        //jTable1.setBounds(jTable1.getX(), jTable1.getY(), this.getWidth()-jTable1.getX(), this.getHeight());
        //jTable1.reshape(jTable1.getX(), jTable1.getY(), this.getWidth()-jTable1.getX(), this.getHeight());
        
    }//GEN-LAST:event_formWindowStateChanged

    private void perioadaCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_perioadaCheckBoxActionPerformed
        if (perioadaCheckBox.isSelected()){
            jDateChooser1.setEnabled(true);
            jDateChooser2.setEnabled(true);
        }else{
            jDateChooser1.setEnabled(false);
            jDateChooser2.setEnabled(false);
        }
    }//GEN-LAST:event_perioadaCheckBoxActionPerformed

    private void proiectCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_proiectCheckBoxActionPerformed
        if(proiectCheckBox.isSelected()){
            proiectComboBox.setEnabled(true);
            
        }else{
            proiectComboBox.setEnabled(false);
        }
        
        if(proiectComboBox.getSelectedItem().equals("Ofertare") || proiectComboBox.getSelectedItem().equals("Concediu de Odihna")
            || proiectComboBox.getSelectedItem().equals("Concediu medical") || proiectComboBox.getSelectedItem().equals("Sarbatori legale")){
            
            etajCheckBox.setEnabled(false);
            corpCheckBox.setEnabled(false);
            //stadiuCheckBox.setEnabled(false);
            elementCheckBox.setEnabled(false);

            etapaComboBox.setEnabled(false);
            etajComboBox.setEnabled(false);
            corpComboBox.setEnabled(false);
            stadiuComboBox.setEnabled(false);
            elementComboBox.setEnabled(false);
        }
        
        
    }//GEN-LAST:event_proiectCheckBoxActionPerformed

    private void elementCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_elementCheckBoxActionPerformed
        if(elementCheckBox.isSelected()){
            elementComboBox.setEnabled(true);
            
        }else{
            elementComboBox.setEnabled(false);
        }
    }//GEN-LAST:event_elementCheckBoxActionPerformed

    private void etajCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_etajCheckBoxActionPerformed
        if(etajCheckBox.isSelected()){
            etajComboBox.setEnabled(true);
        }else{
            etajComboBox.setEnabled(false);
        }
    }//GEN-LAST:event_etajCheckBoxActionPerformed

    private void corpCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_corpCheckBoxActionPerformed
        if(corpCheckBox.isSelected()){
            corpComboBox.setEnabled(true);
        }else{
            corpComboBox.setEnabled(false);
        }
    }//GEN-LAST:event_corpCheckBoxActionPerformed

    private void proiectComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_proiectComboBoxActionPerformed
        if(proiectComboBox.getSelectedItem().equals("Ofertare") || proiectComboBox.getSelectedItem().equals("Concediu de Odihna")
           || proiectComboBox.getSelectedItem().equals("Concediu medical") || proiectComboBox.getSelectedItem().equals("Sarbatori legale")){
            
            etajCheckBox.setEnabled(false);
            corpCheckBox.setEnabled(false);
            //stadiuCheckBox.setEnabled(false);
            elementCheckBox.setEnabled(false);
         
            etajCheckBox.setSelected(false);
            corpCheckBox.setSelected(false);
            //stadiuCheckBox.setSelected(false);
            elementCheckBox.setSelected(false);

            etapaComboBox.setEnabled(false);
            etajComboBox.setEnabled(false);
            corpComboBox.setEnabled(false);
            stadiuComboBox.setEnabled(false);
            elementComboBox.setEnabled(false);
        }else{
            try {
                proiect = ProiectController.getInstance().findByNume((String)proiectComboBox.getSelectedItem());
            } catch (RemoteException ex) {
                Logger.getLogger(RapoarteProiectFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            etapaComboBox.setEnabled(true);
            stadiuComboBox.setEnabled(true);
            etajCheckBox.setEnabled(true);
            corpCheckBox.setEnabled(true);
            //stadiuCheckBox.setEnabled(true);
            elementCheckBox.setEnabled(true);
            
            
            populareComboEtaj();
            populareComboCorp();
        }
    }//GEN-LAST:event_proiectComboBoxActionPerformed

    private void etapaComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_etapaComboBoxActionPerformed
        
        String etapa = etapaComboBox.getSelectedItem().toString();
        stadiuComboBox.removeAllItems();
        switch(etapa){
            case "Discutii":{

                stadiuComboBox.addItem("Beneficiar");
                stadiuComboBox.addItem("Colaborator");

            }break;
            case "Proiectare":{

                stadiuComboBox.addItem("Calcul");
                stadiuComboBox.addItem("DTAC");
                stadiuComboBox.addItem("DE");
                stadiuComboBox.addItem("Coordonare");
                stadiuComboBox.addItem("Verificare");
                stadiuComboBox.addItem("Editie");

            }break;
            case "Santier":{

                stadiuComboBox.addItem("Fundatie");
                stadiuComboBox.addItem("Planseu");
                stadiuComboBox.addItem("Elemente verticale");
                stadiuComboBox.addItem("Altele");

            }break;
            case "Reproiectare":{

                stadiuComboBox.addItem("Calcul");
                stadiuComboBox.addItem("DTAC");
                stadiuComboBox.addItem("DE");

            }break;
        }
        
    }//GEN-LAST:event_etapaComboBoxActionPerformed

    private void stadiuComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stadiuComboBoxActionPerformed
        /*if(stadiuCheckBox.isSelected()){
            if(etapaComboBox.getSelectedItem().equals("Proiectare")){
                switch ((String)stadiuComboBox.getSelectedItem()){
                    case "Calcul":{
                        elementComboBox.addItem("Global");
                        for(String s:std){
                            elementComboBox.addItem(s);
                        }
                        
                    }break;
                    
                    case "DTAC":{
                        elementComboBox.addItem("Parte scrisa");
                        elementComboBox.addItem("Parte desenata");
                        elementComboBox.addItem("Altele");
                        
                        etajCheckBox.setEnabled(false);
                        etajCheckBox.setSelected(false);
                        etajComboBox.setEnabled(false);
                    }break;
                }
            }
        }*/
    }//GEN-LAST:event_stadiuComboBoxActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        /*String select = "SELECT a FROM Activitate a WHERE ";
        if(proiectComboBox.getSelectedItem().equals("Ofertare") || proiectComboBox.getSelectedItem().equals("Concediu de Odihna")
           || proiectComboBox.getSelectedItem().equals("Concediu medical") || proiectComboBox.getSelectedItem().equals("Sarbatori legale")){
        
            select += " a.proiect = :proiect";
            
            
        }else{
            select += "FUNCTION('TRUNCATE',a.cod/10000,0) = " + (etapaComboBox.getSelectedIndex() + 11);
            if(perioadaCheckBox.isSelected()){
                Date start = jDateChooser1.getDate();
                Date end = jDateChooser2.getDate();
                select += " AND a.dataPontaj BETWEEN " + start + " AND " + end + " ";
            }

            
            
            
            select += " AND FUNCTION('TRUNCATE',mod(a.cod/100,100),0) = " + (stadiuComboBox.getSelectedIndex() + 1);
            
            
            if(elementCheckBox.isSelected() && elementComboBox.isEnabled()){
                select += " AND mod(a.cod,100) = ";
                try{
                    switch((String)stadiuComboBox.getSelectedItem()){
                        case "Calcul":{
                            select+= (1 + elementComboBox.getSelectedIndex());
                        }break;
                        case "DTAC":{
                            if (elementComboBox.getSelectedIndex() == 8) select += 3;
                            else select+= (elementComboBox.getSelectedIndex() - 8);
                        }break;
                        case "DE":{
                            if (elementComboBox.getSelectedIndex() == 11) select += 10;
                            else select+= elementComboBox.getSelectedIndex();
                        }break;
                        case "Coordonare":{
                            select+= (1 + elementComboBox.getSelectedIndex());
                        }break;
                        case "Verificare":{
                            select+= (1 + elementComboBox.getSelectedIndex());
                        }break;
                    }
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, "Alege ceva la casuta stadiu!");
                }
            }
            
            if(etajCheckBox.isSelected()){
                select += " AND a.etaj = " + etajComboBox.getSelectedItem();
            }
            if(corpCheckBox.isSelected()){
                select += " AND a.corp = " + corpComboBox.getSelectedItem();
            }
            
            
            
        }
        
        //JOptionPane.showMessageDialog(null, select);
        
        List<Activitate> raport = null;
        try {
            if(proiectCheckBox.isSelected()){
                select += " AND a.proiect = :proiect ";
                proiect = ProiectController.getInstance().findByNume((String)proiectComboBox.getSelectedItem());
                raport = ActivitateController.getInstance().getRaportProiect(select,proiect);
            }
            System.out.println(select);
            //JOptionPane.showMessageDialog(null, select);
            if (raport==null) raport = ActivitateController.getInstance().getRaport(select);
            if (raport!=null)JOptionPane.showMessageDialog(null, "Numar de pontaje gasite: " + raport.size());
            
            if (!proiectCheckBox.isSelected()){
                //de completat pt mai multe proiecte
            }
            activitati = raport;
            
            
            
            populareAngajatPane(raport);
            Thread thread = new Thread(){
                @Override
                public void run(){
                  populareDetaliatPane();
                }
            };

            thread.start();
            
            
        } catch (RemoteException ex) {
            logger.error(ex,ex);
        } catch (IllegalArgumentException il){
            JOptionPane.showMessageDialog(null, "Nu exista pontaje pentru ce ai selectat!");
        }*/
        String select2 = "CREATE or replace view rapoarte AS \n" +
            "select a.id as act_id, a.CORP as act_corp, a.MINUTEMUNCA as act_min, a.DETALII as act_det, j.NUME as ang_nume, d.DESCRIERE as dict_desc, p.NUME as pr_nume, a.etaj as act_etaj \n" +
            "from ACTIVITATE a, PROIECT p, ANGAJAT j, DICTIONAR d \n" +
            "where a.PROIECT_ID = p.ID\n" +
            "and j.ID = a.ANGAJAT_ID \n" +
            "and a.COD = d.COD \n";
        
        
            

        SimpleDateFormat sdf = new SimpleDateFormat("d.M.Y");


        if (perioadaCheckBox.isSelected()){
            select2 += "and a.dataPontaj between " + "str_to_date('"+sdf.format(jDateChooser1.getDate())+"','%d.%m.%Y')" 
            + " and " + "str_to_date('"+sdf.format(jDateChooser2.getDate())+"','%d.%m.%Y')" + "\n";
        }
        
        if(!proiectComboBox.getSelectedItem().equals("Ofertare") && !proiectComboBox.getSelectedItem().equals("Concediu de Odihna")
           && !proiectComboBox.getSelectedItem().equals("Concediu medical") && !proiectComboBox.getSelectedItem().equals("Sarbatori legale"))
        {

            select2 += "and truncate(a.cod/10000,0) = " + (etapaComboBox.getSelectedIndex() + 11) + "\n"; 
            select2 += "and truncate(mod(a.cod/100,100),0) = " + (stadiuComboBox.getSelectedIndex() + 1)+ "\n";

            if(elementCheckBox.isSelected() && elementComboBox.isEnabled()){
                select2 += " AND mod(a.cod,100) = ";
                try{
                    switch((String)stadiuComboBox.getSelectedItem()){
                        case "Calcul":{
                            select2+= (1 + elementComboBox.getSelectedIndex());
                        }break;
                        case "DTAC":{
                            if (elementComboBox.getSelectedIndex() == 8) select2 += 3;
                            else select2+= (elementComboBox.getSelectedIndex() - 8);
                        }break;
                        case "DE":{
                            if (elementComboBox.getSelectedIndex() == 11) select2 += 10;
                            else select2+= elementComboBox.getSelectedIndex();
                        }break;
                        case "Coordonare":{
                            select2+= (1 + elementComboBox.getSelectedIndex());
                        }break;
                        case "Verificare":{
                            select2+= (1 + elementComboBox.getSelectedIndex());
                        }break;

                    }
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, "Alege ceva la casuta stadiu!");
                }
                select2 += '\n';
            }

            if(etajCheckBox.isSelected()){
                select2 += "AND a.etaj = " + "'"+etajComboBox.getSelectedItem()+"'"+ "\n";
            }
            if(corpCheckBox.isSelected()){
                select2 += "AND a.corp = " + "'"+corpComboBox.getSelectedItem()+"'"+ "\n";
            }

            if(proiectCheckBox.isSelected()){
                try {
                    proiect = ProiectController.getInstance().findByNume((String)proiectComboBox.getSelectedItem());
                    select2 += " and a.proiect_id = " + proiect.getId()+ "\n";
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        
        //select2+=";";
            System.out.println(select2);

            try {
                List<RaportBrut> act = ActivitateController.getInstance().createView(select2);

                for(RaportBrut r:act){
                    jTextArea1.append(r.toString()+'\n');
                }
                System.out.println(act.size());

                populareAngajatPane();
                populareDetaliatPane();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            } catch(Exception e){
                JOptionPane.showMessageDialog(null, "Nu exista pontaje pentru ce ai selectat!");
            }
        }else{
            try {
                proiect = ProiectController.getInstance().findByNume((String)proiectComboBox.getSelectedItem());
                select2 += " and a.proiect_id = " + proiect.getId()+ "\n";
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
            
            System.out.println(select2);

            try {
                List<RaportBrut> act = ActivitateController.getInstance().createView(select2);

                for(RaportBrut r:act){
                    jTextArea1.append(r.toString()+'\n');
                }
                System.out.println(act.size());

                populareAngajatPane();
                //populareDetaliatPane();
                
                model2.clear();
                
            } catch (RemoteException ex) {
                ex.printStackTrace();
            } catch(Exception e){
                JOptionPane.showMessageDialog(null, "Nu exista pontaje pentru ce ai selectat!");
            }
        }
            
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (raport == null){
            JOptionPane.showMessageDialog(null, "Inca nu ai generat un raport!");
            return;
        }
        
        
        
        JFileChooser chooser;
        String choosertitle = "Alege un folder";
        chooser = new JFileChooser(); 
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(choosertitle);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //
        // disable the "All files" option.
        //
        chooser.setAcceptAllFileFilterUsed(false);
        //    
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
            
            System.out.println("getCurrentDirectory(): "
                    +  chooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : "
                    +  chooser.getSelectedFile());
            //String fileName = JOptionPane.showInputDialog("Numele fisierului (fara extensie!): ");
            file = new File(chooser.getSelectedFile()+"\\pontaj.csv");
            try {
                boolean result = Files.deleteIfExists(file.toPath());
            } catch (IOException ex) {
                Logger.getLogger(RapoarteProiectFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {
                FileWriter outputfile = new FileWriter(file,true); 

                CSVWriter writer = new CSVWriter(outputfile);
                for (RaportOreAngajat r : raport){
                    String[] data = {r.getNume(),r.getOre()+""};
                    writer.writeNext(data);
                }


                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }else {
            JOptionPane.showMessageDialog(null, "Nu ai ales nimic!");
        }
        
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox corpCheckBox;
    private javax.swing.JComboBox<String> corpComboBox;
    private javax.swing.JCheckBox elementCheckBox;
    private javax.swing.JComboBox<String> elementComboBox;
    private javax.swing.JCheckBox etajCheckBox;
    private javax.swing.JComboBox<String> etajComboBox;
    private javax.swing.JComboBox<String> etapaComboBox;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JCheckBox perioadaCheckBox;
    private javax.swing.JCheckBox proiectCheckBox;
    private javax.swing.JComboBox<String> proiectComboBox;
    private javax.swing.JComboBox<String> stadiuComboBox;
    // End of variables declaration//GEN-END:variables
}
