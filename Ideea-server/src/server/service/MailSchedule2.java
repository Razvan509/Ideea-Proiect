/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.service;

import db.Activitate;
import db.Angajat;
import java.util.Calendar;
import static java.util.Calendar.FRIDAY;
import static java.util.Calendar.TUESDAY;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.TimerTask;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author razvan
 */
public class MailSchedule2 extends TimerTask  implements Runnable{
    private final String TIMEOUT = "15000";
    
    
    public MailSchedule2(){
        System.out.println(new Date() + " A pornit treaba cu mail-ul!"); 
    }

    @Override
    public void run() {
        Calendar c = Calendar.getInstance();
        if((c.get(Calendar.DAY_OF_WEEK) == TUESDAY || c.get(Calendar.DAY_OF_WEEK) == FRIDAY)&&
                c.get(Calendar.HOUR_OF_DAY) == 9){
            String to = "razvanrang@gmail.com";
            String from = "ideea.soft@gmail.com";
            String user = "ideea.soft@gmail.com";
            String pass = "jatnopjatnop";
            String host = "smtp.gmail.com";
            Properties props = System.getProperties();

            props.setProperty("mail.host", host);
            props.setProperty("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.fallback", "false");
            props.setProperty("mail.smtp.quitwait", "false");
            props.put("mail.smtp.connectiontimeout", TIMEOUT);
            props.put("mail.smtp.timeout", TIMEOUT);

            Session session = Session.getDefaultInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(user, pass);
                    }	
            });

            try{
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                
                
                String s = "Cei care nu au mai bagat un pontaj de 4 zile sau mai mult sunt: \n";
                Calendar today = Calendar.getInstance();
                today.add(Calendar.DATE,-5);
                
                Activitate lastActiv;
                AngajatService angajatService = new AngajatService();
                ActivitateService activitateService = new ActivitateService();
                
                List<Angajat> angajati = angajatService.getAngajatiBYStare("activ");
                
                for(int i=0;i<angajati.size();i++){
                    lastActiv = activitateService.getLastDateActivityByAngajat(angajati.get(i));
                if((lastActiv.getDataPontaj()==null || today.getTime().after(lastActiv.getDataPontaj())) && !angajati.get(i).isAdmin()){
                    s+= angajati.get(i).getNume() + "\n";
                }
            }

                message.setSubject("Angajatii nepontati!");
                message.setContent(s,"text/plain");

                Transport.send(message);
                System.out.println("Sent message successfully....");

            }catch(Exception e){

                e.printStackTrace();

            }
        }
    }
    
}
