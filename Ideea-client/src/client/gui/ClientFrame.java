/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.gui;

import Enum.TopicsEnum;
import client.controller.ActivitateController;
import client.controller.DictionarController;
import client.controller.ProiectController;
import db.Activitate;
import db.Angajat;
import db.Proiect;
import java.awt.Dimension;
import java.awt.Font;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import org.apache.log4j.PropertyConfigurator;
import ro.top.service.ClientNotificationAsyncController;
import ro.top.service.ClientNotificationController;
import ro.top.subscriber.Subscriber;


/**
 *
 * @author razvan
 */
public class ClientFrame extends javax.swing.JFrame implements Subscriber{

    public Angajat angajat;
    private int cod;
    private Proiect proiect;
    private Calendar today;
    private DefaultListModel model;
    private int sumaOre=0;
    private int sumaMinute=0;
    private boolean admin;
    private final String pathToLog4j = Paths.get("./log4j.properties").toString();
    public static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ClientFrame.class);
    
    public ClientFrame(Angajat angajat,boolean admin){
        initComponents();
        PropertyConfigurator.configure(Paths.get(pathToLog4j).toString());
        
        jLabel10.setText("");
        jLabel8.setText("");
        
        
        
        this.angajat = angajat;
        this.admin = admin;
        
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle(angajat.getNume());
        
        if(admin) setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        else setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        populareComboProiect();
        populareComboEtapa();
        populareComboEtaj();
        
        //jComboBox6.setLayout(new BorderLayout());
        
        
        //jComboBox1.setSize(100, 50);
        jComboBox1.setPreferredSize(new Dimension(200,28));
        //jComboBox1.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXX");
        jComboBox1.setMaximumSize(new Dimension(100,50));
        
        /*jComboBox3.setVisible(false);
        jLabel3.setVisible(false);*/
        
        jLabel12.setVisible(false);
        jTextField1.setVisible(false);
        
        jLabel4.setVisible(false);
        jComboBox4.setVisible(false);
        
        jLabel5.setVisible(false);
        jComboBox6.setVisible(false);
        
        //jComboBox1.setEditable(true);
        
        
        jTextArea1.setEditable(false);
        
        today = Calendar.getInstance();
        
        jDateChooser1.setDate(new Date());

        jTextField3.setText("0");
        jList1.setFont(new Font("Tahoma", Font.PLAIN, 18));
        
        getRootPane().setDefaultButton(jButton1);
        //populareLista();
        
        //jDateChooser1
        try {
            logger.info(angajat.getNume() + " a pornit aplicatia! " + ClientNotificationController.getLocalIp());
            //jButton1.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "COPY");
            ClientNotificationAsyncController.getInstance().addSubscriber(TopicsEnum.PROIECT_ORA_MODIFICAT, this);
            ClientNotificationAsyncController.getInstance().addSubscriber(TopicsEnum.PROIECT_STARE_MODIFICAT, this);
            logger.info(angajat.getNume()+" a dat subscribe!");
            Activitate lastActiv = ActivitateController.getInstance().getLastDateActivityByAngajat(angajat);
            logger.info(angajat.getNume()+" si-a luat ultima activitate!");
            today.add(Calendar.DATE,-2);
            if(lastActiv==null){
                JOptionPane.showMessageDialog(null, "Nu ai mai introdus un "
                + "pontaj de ceva timp!");
            }
            else{
                if(today.getTime().after(lastActiv.getDataPontaj())){
                    JOptionPane.showMessageDialog(null, "Nu ai mai introdus un "
                    + "pontaj de ceva timp!");
                }
            }
            
            if(lastActiv!=null){
                popularePontaj(lastActiv);
                proiect = lastActiv.getProiect();
            }
            
        } catch (RemoteException ex) {
            logger.error(angajat.getNume() + " are eroarea: ",ex);
            JOptionPane.showMessageDialog(null, "Eroare la conectare");
        }catch(Exception ex){
            logger.error(angajat.getNume() + " are eroarea: ",ex);
            
        }
        today = Calendar.getInstance();
    }
    
    public void popularePontaj(Activitate lastActiv){
        jComboBox1.setSelectedItem(lastActiv.getProiect().getNume());
        jComboBox2.setSelectedIndex((lastActiv.getCod()/10000)-11);
        int index1 = (lastActiv.getCod()/100)%100;
        int index2 = lastActiv.getCod()%100;
        int index3 = lastActiv.getCod()/10000;
        if(index1!=0 || index3!=11) {
            if(index3 == 15){
                jComboBox3.setVisible(false);
                jLabel3.setVisible(false);
            }else{
                jComboBox3.setSelectedIndex(index1-1);
                jComboBox3.setVisible(true);
                jLabel3.setVisible(true);
            }
        }else{
            jComboBox2.setVisible(false);
            jLabel2.setVisible(false);
            jComboBox3.setVisible(false);
            jLabel3.setVisible(false);
        }
        if(index2!=0) {
            jComboBox4.setSelectedIndex(index2-1);
            jComboBox4.setVisible(true);
            jLabel3.setVisible(true);
        }
        if(lastActiv.getEtaj()!=null) {
            jComboBox6.setSelectedItem(lastActiv.getEtaj());
            jComboBox6.setVisible(true);
            jLabel5.setVisible(true);
        }
        String corp = lastActiv.getCorp()+"";
        if(lastActiv.getProiect().getCorpuri()>1) {
            jComboBox5.setSelectedItem(corp);
            jComboBox5.setVisible(true);
            jLabel9.setVisible(true);
        }
        
        jTextField2.setText(lastActiv.getOreMunca()+"");
        jTextField3.setText(lastActiv.getMinuteMunca()+"");
    }
    
    public void populareComboProiect(){
        try {
            List<Proiect> lista = ProiectController.getInstance().getAllProjectsByStare(0);
            
            for(Proiect p : lista){
                jComboBox1.addItem(p.getNume());
            }
        } catch (RemoteException ex) {
            logger.error(angajat.getNume() + " are eroarea: ",ex);
            JOptionPane.showMessageDialog(null, "Eroare");
        }catch(Exception ex){
            logger.error(angajat.getNume() + " are eroarea: ",ex);
        }
    }
    
    public void populareComboEtaj(){
        jComboBox6.removeAllItems();
        if(proiect != null){
            for(int i=-proiect.getNrEtajeSubsol();i<=proiect.getNrEtaje();i++){
                if(i<0){
                    jComboBox6.addItem("subsol "+i);
                }else{
                    if(i==0){
                        jComboBox6.addItem("parter");
                    }else{
                        jComboBox6.addItem("etaj "+i);
                    }
                }
            }
        }
    }
    
    public void populareComboEtapa(){
        jComboBox2.addItem("Discutii");
        jComboBox2.addItem("Proiectare");
        jComboBox2.addItem("Santier");
        jComboBox2.addItem("Reproiectare");
        jComboBox2.addItem("Documentare Tehnica");
    }
    
    public void populareLista(){
        try {
            //jList1.setBackground(Color.PINK);
            sumaOre=0;
            sumaMinute=0;
            Date data = jDateChooser1.getDate();
            List<Activitate> activitati = ActivitateController.getInstance().getActivitatiAngajatZi(angajat, data);
            model = new DefaultListModel();
            jList1.setModel(model);
            model.clear();
            
            for(Activitate a:activitati){
                String s = a.toString() + " " +DictionarController.getInstance().findByCod(a.getCod());
                if(a.getEtaj()!=null) s = s + " " + a.getEtaj();
                if(a.getProiect().getCorpuri()>1 && !a.getProiect().getNume().equals("Ofertare")) s = s + " corp " + a.getCorp();
                if(a.getDetalii()!= null && a.getDetalii() != "") s = s + " " + a.getDetalii();
                model.addElement(s);
                sumaOre+=a.getOreMunca();
                if (sumaMinute+a.getMinuteMunca()>59){
                    sumaOre++;
                    sumaMinute+=a.getMinuteMunca()-60;
                }else{
                    sumaMinute+=a.getMinuteMunca();
                }
            }
            
            SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy");
            
            if(sumaOre>8){
                JOptionPane.showMessageDialog(null, "Vezi ca ai lucrat mai mult de 8 ore in data de: " + 
                f.format(data));
            }
            
            jLabel8.setText(sumaOre+" ore");
            jLabel10.setText(sumaMinute+" minute");
            
        } catch (RemoteException ex) {
            logger.error(angajat.getNume() + " are eroarea: ",ex);
            JOptionPane.showMessageDialog(null, "Eroare");
        }catch(Exception ex){
            logger.error(angajat.getNume() + " are eroarea: ",ex);
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

        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBox1.setMaximumSize(new java.awt.Dimension(100, 28));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Proiect:");

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton1.setText("Adauga");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Etapa:");

        jComboBox2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Stadiu:");

        jComboBox3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Element:");

        jComboBox4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Etaj:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Ore:");

        jTextField2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField2.setMinimumSize(new java.awt.Dimension(39, 28));
        jTextField2.setName(""); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Minute:");

        jTextField3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Corp:");

        jComboBox5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jDateChooser1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jDateChooser1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser1PropertyChange(evt);
            }
        });

        jList1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jList1);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("jLabel8");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("jLabel10");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("Total:");

        jComboBox6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setText("Alte detalii:");

        jMenu1.setText("Delogare");
        jMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu1MouseClicked(evt);
            }
        });
        jMenuBar2.add(jMenu1);

        jMenu2.setText("Modifica pontaje");
        jMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu2MouseClicked(evt);
            }
        });
        jMenuBar2.add(jMenu2);

        jMenu3.setText("Modifica informatii");
        jMenu3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu3MouseClicked(evt);
            }
        });
        jMenuBar2.add(jMenu3);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel9))
                                .addGap(38, 38, 38)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 97, Short.MAX_VALUE)
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel10)
                                .addGap(456, 456, 456))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 538, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(117, 117, 117))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(190, 190, 190)
                                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(129, 129, 129)
                                .addComponent(jLabel12)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel8)
                            .addComponent(jLabel10))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(44, 44, 44))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel12)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try{                                         
            Activitate pontaj = new Activitate();
            pontaj.setAngajat(angajat);
            pontaj.setDataOra(new Date());
            if(jComboBox6.isVisible()) pontaj.setEtaj((String)jComboBox6.getSelectedItem());
            int ore = Integer.parseInt(jTextField2.getText());
            int minute = Integer.parseInt(jTextField3.getText());
            if (ore==0 && minute==0){
                JOptionPane.showMessageDialog(null, "Nu poti muncii 0 ore si 0 minute!");
                return;
            }
            if (ore >= 0 && ore <17){
                int temp = sumaOre + ore;
                int min = (minute + sumaMinute)/60;
                if (temp+min>16) {
                    JOptionPane.showMessageDialog(null, "Nu poti lucra mai mult de 16 ore pe zi!");
                    return;
                }else{
                    if (temp+min==16 && (minute + sumaMinute)%60>0){
                        JOptionPane.showMessageDialog(null, "Nu poti lucra mai mult de 16 ore pe zi!");
                        return;
                    }else pontaj.setOreMunca(ore);
                        
                }
                
            }
            else {
                JOptionPane.showMessageDialog(null, "Numar de ore prea mare sau negativ!");
                return;
            }
            
            if (minute >= 0 && minute < 60)
                pontaj.setMinuteMunca(minute);
            else {
                JOptionPane.showMessageDialog(null, "Numar de minute incorect!");
                return;
            }
            if (jComboBox3.isVisible()) cod = cod*100 + jComboBox3.getSelectedIndex() + 1;
            else cod*=100;
            if (jComboBox4.isVisible()) cod = cod*100 + jComboBox4.getSelectedIndex() + 1;
            else{
                cod*=100;
            }
            //JOptionPane.showMessageDialog(null, cod + " " + pontaj.getDataOra().toString());
            pontaj.setCod(cod);
            pontaj.setProiect(proiect);
            Date data = new Date();
            if (jDateChooser1.getDate() == null){
                JOptionPane.showMessageDialog(null, "Cand anume?");
                return;
            }
            data = jDateChooser1.getDate();
            
            if (data.after(today.getTime())){
                JOptionPane.showMessageDialog(null, "Viitorul este necunoscut!");
                today = Calendar.getInstance();
                return;
            }else{
                today.add(Calendar.DATE,-8);
                if(data.before(today.getTime())&&!admin){
                    JOptionPane.showMessageDialog(null, "Data introdusa nu este valida! Poti inscrie un pontaj cu maxim 7 zile in urma!");
                    today = Calendar.getInstance();
                    return;
                }
                else{
                    pontaj.setDataPontaj(data);
                }
                today = Calendar.getInstance();
            }
            if (jComboBox5.isVisible())
                pontaj.setCorp(((String)jComboBox5.getSelectedItem()).charAt(0));
            if (jTextField1.isVisible()) pontaj.setDetalii(jTextField1.getText());
            
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(null, "Pontajul dumneavoastra: "+pontaj.toString()+" " 
                    +"\nDoriti sa continuati? ","Confirmare!",dialogButton);
            if (dialogResult == JOptionPane.YES_OPTION){
                if(DictionarController.getInstance().findByCod(cod) != null){
                    ActivitateController.getInstance().adaugaActivitate(pontaj);
                    ClientNotificationAsyncController.getInstance().postNotification(TopicsEnum.PROIECT_ORA_MODIFICAT);
                    //JOptionPane.showMessageDialog(null, "Bau!");
                }else{
                    JOptionPane.showMessageDialog(null, "Ceva nu a mers bine, mai incercati o data! ");
                    logger.error("Un cod s-a generat prost!" + cod);
                }
            }
            cod = jComboBox2.getSelectedIndex()+11;
            populareLista();
        }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(null, "Nu ati introdus un numar!");
        }catch (RemoteException ex) {
            logger.error(angajat.getNume() + " are eroarea: ",ex);
            JOptionPane.showMessageDialog(null, "Eroare");
        }catch(Exception ex){
            logger.error(angajat.getNume() + " are eroarea: ",ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        String etapa = jComboBox2.getSelectedItem().toString();
        jComboBox3.removeAllItems();
        jComboBox4.removeAllItems();
        switch(etapa){
            case "Discutii":{
                cod = 11;
                jLabel3.setVisible(true);
                jComboBox3.setVisible(true);
                
                jComboBox3.addItem("Beneficiar");
                jComboBox3.addItem("Colaborator");
                jLabel4.setVisible(false);
                jComboBox4.setVisible(false);
                
                jLabel5.setVisible(false);
                jComboBox6.setVisible(false);
            }break;
            case "Proiectare":{
                cod = 12;
                jLabel3.setVisible(true);
                jComboBox3.setVisible(true);
                
                jComboBox3.addItem("Calcul");
                jComboBox3.addItem("DTAC");
                jComboBox3.addItem("DE");
                jComboBox3.addItem("Coordonare");
                jComboBox3.addItem("Verificare");
                jComboBox3.addItem("Editie");
                //jComboBox3.addItem("Neatribuit");
            }break;
            case "Santier":{
                cod = 13;
                jLabel3.setVisible(true);
                jComboBox3.setVisible(true);
                
                jComboBox3.addItem("Fundatie");
                jComboBox3.addItem("Planseu");
                jComboBox3.addItem("Elemente verticale");
                jComboBox3.addItem("Altele");
                jLabel4.setVisible(false);
                jComboBox4.setVisible(false);
                
                
                //jTextField1.setSize(390, 200);
            }break;
            case "Reproiectare":{
                cod = 14;
                jLabel3.setVisible(true);
                jComboBox3.setVisible(true);
                
                jComboBox3.addItem("Calcul");
                jComboBox3.addItem("DTAC");
                jComboBox3.addItem("DE");
                jLabel4.setVisible(true);
                jComboBox4.setVisible(true);
            }break;
            case "Documentare Tehnica":{
                cod = 15;
                
                jLabel3.setVisible(false);
                jComboBox3.setVisible(false);
                
                jLabel4.setVisible(false);
                jComboBox4.setVisible(false);
                
                jLabel5.setVisible(false);
                jComboBox6.setVisible(false);
            }break;
        }
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        int index = jComboBox3.getSelectedIndex()+1;
        jComboBox4.removeAllItems();
        switch(cod){
            case 11:{
                //cod = (cod*100 + index)*10000; 
            }break;
            case 12:{
                //cod = cod*100 + index;
                
                switch(index){
                    case 1:{
                        jLabel4.setVisible(true);
                        jComboBox4.setVisible(true);
                        jComboBox4.addItem("Global");
                        jComboBox4.addItem("Fundatie");
                        jComboBox4.addItem("Cofraje");
                        jComboBox4.addItem("Elemente verticale");
                        jComboBox4.addItem("Grinzi");
                        jComboBox4.addItem("Placi");
                        jComboBox4.addItem("Goluri");
                        jComboBox4.addItem("Scari");
                        jComboBox4.addItem("Altele");
                    }break;
                    case 2:{
                        jLabel4.setVisible(true);
                        jComboBox4.setVisible(true);
                        jComboBox4.setEnabled(true);
                        jComboBox4.addItem("Parte scrisa");
                        jComboBox4.addItem("Parte desenata");
                        jComboBox4.addItem("Altele");
                        
                        jLabel5.setVisible(false);
                        jComboBox6.setVisible(false);
                    }break;
                    case 3:{
                        jLabel4.setVisible(true);
                        jComboBox4.setVisible(true);
                        jComboBox4.setEnabled(true);
                        jComboBox4.addItem("Fundatie");
                        jComboBox4.addItem("Cofraje");
                        jComboBox4.addItem("Elemente verticale");
                        jComboBox4.addItem("Grinzi");
                        jComboBox4.addItem("Placi");
                        jComboBox4.addItem("Goluri");
                        jComboBox4.addItem("Scari");
                        jComboBox4.addItem("Altele");
                        jComboBox4.addItem("Parte scrisa");
                    }break;
                    case 4:{
                        jLabel4.setVisible(true);
                        jComboBox4.setVisible(true);
                        jComboBox4.setEnabled(true);
                        jComboBox4.addItem("Global");
                        jComboBox4.addItem("Fundatie");
                        jComboBox4.addItem("Cofraje");
                        jComboBox4.addItem("Elemente verticale");
                        jComboBox4.addItem("Grinzi");
                        jComboBox4.addItem("Placi");
                        jComboBox4.addItem("Goluri");
                        jComboBox4.addItem("Scari");
                        jComboBox4.addItem("Altele");
                    }break;
                    case 5:{
                        jLabel4.setVisible(true);
                        jComboBox4.setVisible(true);
                        jComboBox4.setEnabled(true);
                        jComboBox4.addItem("Global");
                        jComboBox4.addItem("Fundatie");
                        jComboBox4.addItem("Cofraje");
                        jComboBox4.addItem("Elemente verticale");
                        jComboBox4.addItem("Grinzi");
                        jComboBox4.addItem("Placi");
                        jComboBox4.addItem("Goluri");
                        jComboBox4.addItem("Scari");
                        jComboBox4.addItem("Altele");
                        
                    }break;
                    
                    case 6:{
                        jLabel4.setVisible(false);
                        jComboBox4.setVisible(false);
                        
                        jLabel5.setVisible(true);
                        jComboBox6.setVisible(true); 
                    }break;
                }
            }break;
            case 13:{
                if (jComboBox3.getSelectedItem() != null && jComboBox3.getSelectedItem().equals("Neatribuit")){
                    jLabel5.setVisible(false);
                    jComboBox6.setVisible(false);
                }else{
                    jLabel5.setVisible(true);
                    jComboBox6.setVisible(true);
                    
                    if(jComboBox3.getSelectedItem()!=null && jComboBox3.getSelectedItem().equals("Fundatie")){
                        jComboBox6.removeAllItems();
                        jComboBox6.addItem("Fundatie");
                        jComboBox6.addItem("Mustati");
                    }else populareComboEtaj();
                    if(jComboBox3.getSelectedItem() != null && jComboBox3.getSelectedItem().equals("Altele")){
                        jLabel12.setVisible(true);
                        jTextField1.setVisible(true);
                    }else{
                        jLabel12.setVisible(false);
                        jTextField1.setVisible(false);
                        jTextField1.setText("");
                    }        
                }
                
            }break;
            case 14:{
                switch(index){
                    case 1:{
                        jComboBox4.setEnabled(true);
                        jComboBox4.addItem("Global");
                        jComboBox4.addItem("Fundatie");
                        jComboBox4.addItem("Cofraje");
                        jComboBox4.addItem("Elemente verticale");
                        jComboBox4.addItem("Grinzi");
                        jComboBox4.addItem("Placi");
                        jComboBox4.addItem("Goluri");
                        jComboBox4.addItem("Scari");
                        jComboBox4.addItem("Altele");
                    }break;
                    case 2:{
                        jComboBox4.setEnabled(true);
                        jComboBox4.addItem("Parte scrisa");
                        jComboBox4.addItem("Parte desenata");
                        jComboBox4.addItem("Altele");
                        
                        jLabel5.setVisible(false);
                        jComboBox6.setVisible(false);
                    }break;
                    case 3:{
                        jComboBox4.setEnabled(true);
                        jComboBox4.addItem("Fundatie");
                        jComboBox4.addItem("Cofraje");
                        jComboBox4.addItem("Elemente verticale");
                        jComboBox4.addItem("Grinzi");
                        jComboBox4.addItem("Placi");
                        jComboBox4.addItem("Goluri");
                        jComboBox4.addItem("Scari");
                        jComboBox4.addItem("Altele");
                        jComboBox4.addItem("Parte desenata");
                    }break;
                }
            }
        }
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        int index1 = jComboBox3.getSelectedIndex();
        int index2 = jComboBox4.getSelectedIndex();
        
        
        if((cod == 12 || cod == 14) && (index1 == 0 || index1 == 3) && index2 == 0){
            jLabel5.setVisible(false);
            jComboBox6.setVisible(false); 
            jComboBox6.removeAll();
        }else{
            jLabel5.setVisible(true);
            jComboBox6.setVisible(true);
            if(jComboBox4.getSelectedItem()!=null && jComboBox4.getSelectedItem().equals("Fundatie")){
                jComboBox6.removeAllItems();
                jComboBox6.addItem("Fundatie");
                jComboBox6.addItem("Mustati");
            }else populareComboEtaj();
        }
        
        if(cod == 12 || cod == 14){
            if(index1 == 1){
                jLabel5.setVisible(false);
                jComboBox6.setVisible(false); 
                jComboBox6.removeAll();
            }
        }
        
        if((cod == 12 ||cod == 14) && jComboBox4.getSelectedItem() != null && jComboBox4.getSelectedItem().equals("Altele")){
            jLabel12.setVisible(true);
            jTextField1.setVisible(true);
        }else{
            jLabel12.setVisible(false);
            jTextField1.setVisible(false);
            jTextField1.setText("");
        }        
        
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        if(!jComboBox1.getSelectedItem().equals("Ofertare") && !jComboBox1.getSelectedItem().equals("Concediu de Odihna")
                && !jComboBox1.getSelectedItem().equals("Concediu medical") && !jComboBox1.getSelectedItem().equals("Sarbatori legale")){
            jComboBox2.setVisible(true);
            jComboBox3.setVisible(true);
            
            jTextField1.setVisible(false);
            jLabel12.setVisible(false);
            jTextField1.setText("");
            
            
            jLabel2.setVisible(true);
            jLabel3.setVisible(true);
            
            if(jComboBox2.isVisible() && jComboBox2.getSelectedIndex()==4){
               jLabel3.setVisible(false);
               jComboBox3.setVisible(false);
            }
            
            try {
                jComboBox5.removeAllItems();
                jTextArea1.setText(null);
                proiect = ProiectController.getInstance().findByNume((String)jComboBox1.getSelectedItem());
                populareComboEtaj();
                jTextArea1.append("Nume proiect: "+proiect.getNume()+"\n");
                jTextArea1.append("Nume beneficiar: "+proiect.getBeneficiar()+"\n");
                jTextArea1.append("Adresa: "+proiect.getAdresa()+"\n");
                jTextArea1.append("Numar de etaje: "+proiect.getNrEtaje()+"\n");
                jTextArea1.append("Numar de etaje subsol: "+proiect.getNrEtajeSubsol()+"\n");
                jTextArea1.append("Nume arhitect: "+proiect.getNumeArhitect()+"\n");
                jTextArea1.append("Numar de corpuri: "+proiect.getCorpuri()+"\n");

                if (proiect.getCorpuri()>1){
                    jLabel9.setVisible(true);
                    jComboBox5.setVisible(true);

                    for(int i=0;i<proiect.getCorpuri();i++){
                        char litera = 'A';
                        litera+=i;
                        jComboBox5.addItem("" + litera);
                    }
                }else{
                    jLabel9.setVisible(false);
                    jComboBox5.setVisible(false);

                }
            } catch (RemoteException ex) {
                Logger.getLogger(ClientFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            cod = 11;
            jComboBox2.setVisible(false);
            jComboBox3.setVisible(false);
            jComboBox4.setVisible(false);
            jComboBox5.setVisible(false);
            jComboBox6.setVisible(false);
            
            jLabel2.setVisible(false);
            jLabel3.setVisible(false);
            jLabel4.setVisible(false);
            jLabel5.setVisible(false);
            jLabel9.setVisible(false);
            
            jTextArea1.setText(null);
            
            jTextField1.setVisible(true);
            jLabel12.setVisible(true);
            
            try {
                proiect = ProiectController.getInstance().findByNume((String)jComboBox1.getSelectedItem());
            } catch (RemoteException ex) {
                logger.error(angajat.getNume() + " are eroarea: ",ex);
            }
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jDateChooser1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser1PropertyChange
       populareLista();
    }//GEN-LAST:event_jDateChooser1PropertyChange

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jList1MouseClicked

    private void jMenu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu1MouseClicked
        dispose();
        //ClientNotificationAsyncController.getInstance().close();
        logger.info("S-a oprit aplicatia!");
        new LoginFrame().setVisible(true);
    }//GEN-LAST:event_jMenu1MouseClicked

    private void jMenu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu2MouseClicked
        new AlegePontajFrame(angajat,admin,this);
        populareLista();
    }//GEN-LAST:event_jMenu2MouseClicked

    private void jMenu3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu3MouseClicked
        new SetareAngajatFrame(angajat.getId()).setVisible(true);
    }//GEN-LAST:event_jMenu3MouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        logger.info(angajat.getNume() + " a oprit aplicatia!");
        //ClientNotificationAsyncController.getInstance().close();
    }//GEN-LAST:event_formWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables

    @Override
    public void newNotification(String string) {
        switch(string){
            default: 
                //System.out.println("Cine da eroare, ma-sa e curva!");
        }
    }

    @Override
    public void newDataNotification(Object o, String string) {
        
        switch(string){
            case TopicsEnum.PROIECT_STARE_MODIFICAT :{
                populareComboProiect();
            }
        }
    }
}
