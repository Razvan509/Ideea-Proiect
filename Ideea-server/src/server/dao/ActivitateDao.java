/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.dao;

import db.Activitate;
import db.Angajat;
import db.Proiect;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import rmi.Pair;
import static java.lang.Math.toIntExact;
import java.util.ArrayList;
import java.util.Iterator;
import raport.RaportBrut;
import raport.RaportOreAngajat;

/**
 *
 * @author razvan
 */
public class ActivitateDao {

    private EntityManager em;
    private final String pathToLog4j = Paths.get("./log4j.properties").toString();
    public static final Logger logger = Logger.getLogger(ActivitateDao.class);

    public ActivitateDao(EntityManager em) {
        this.em = em;
        PropertyConfigurator.configure(Paths.get(pathToLog4j).toString());
    }

    public void adaugaActivitate(Activitate activitate) {
        em.persist(activitate);
    }

    public List<Activitate> getActivitatiAngajatProiect(Angajat a, Proiect p) {
        Query query = em.createQuery("SELECT a FROM Activitate a WHERE a.proiect = :proiect AND a.angajat = :angajat");
        query.setParameter("proiect", p);
        query.setParameter("angajat", a);

        try {
            return query.getResultList();
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    public List<Activitate> getActivitatiAngajatZi(Angajat a, Date data) {
        Query query = em.createQuery("SELECT a FROM Activitate a Where a.angajat = :angajat AND a.dataPontaj = :data");
        query.setParameter("angajat", a);
        query.setParameter("data", data);

        try {
            List<Activitate> rez = query.getResultList();
            return rez;
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    public Activitate findById(int id) {
        Query query = em.createQuery("SELECT a FROM Activitate a WHERE a.id = :id");
        query.setParameter("id", id);

        try {
            return (Activitate) query.getSingleResult();
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    public Activitate modifyActivity(Activitate a) {
        return em.merge(a);
    }

    public void deleteActivity(Activitate a) {
        em.remove(a);
    }

    public Activitate getLastDateActivityByAngajat(Angajat angajat) {
        Query q = em.createQuery("SELECT a FROM Activitate a WHERE a.angajat = :angajat ORDER BY a.dataPontaj DESC");

        q.setParameter("angajat", angajat);
        List<Activitate> pontaj = q.setMaxResults(1).getResultList();

        if (pontaj.size() > 0) {
            return pontaj.get(0);
        }
        return null;
    }

    public List<Activitate> getActivitatiPerioada(Angajat angajat, Date startDate, Date endDate) {
        Query query = em.createQuery("SELECT a FROM Activitate a WHERE a.angajat = :angajat "
                + "AND a.dataPontaj BETWEEN :startDate AND :endDate ORDER BY a.dataPontaj DESC");
        query.setParameter("angajat", angajat);
        query.setParameter("startDate", startDate, TemporalType.DATE);
        query.setParameter("endDate", endDate, TemporalType.DATE);

        try {
            List<Activitate> pontaje = query.getResultList();
            return pontaje;
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    public List<Activitate> getAll() {
        Query q = em.createQuery("SELECT a FROM Activitate a");

        return q.getResultList();
    }

    public Pair getOreProiectByAngajatBetweenDate(Proiect proiect, Angajat angajat, Date start, Date end) {
        //Query q1 = em.createQuery("SELECT SUM(a.oreMunca) FROM Activitate a WHERE a.proiect = :proiect AND"
        //+ " a.angajat = :angajat AND a.dataPontaj BETWEEN :start AND :end");
        Query q2 = em.createQuery("SELECT SUM(a.minuteMunca) FROM Activitate a WHERE a.proiect = :proiect AND"
                + " a.angajat = :angajat AND a.dataPontaj BETWEEN :start AND :end");
        /*q1.setParameter("proiect", proiect);
        q1.setParameter("angajat", angajat);
        q1.setParameter("start", start);
        q1.setParameter("end",end);*/

        q2.setParameter("proiect", proiect);
        q2.setParameter("angajat", angajat);
        q2.setParameter("start", start);
        q2.setParameter("end", end);

        //long ore = (long)q1.getSingleResult();
        long minute = (long) q2.getSingleResult();
        long ore = minute / 60;
        minute = minute % 60;

        return new Pair(ore, minute);
    }

    public long getOreProiectBetweenDate(Proiect proiect, Date startDate, Date endDate) {
        //Query q1 = em.createQuery("SELECT SUM(a.oreMunca) FROM Activitate a WHERE a.proiect = :proiect"
        //+ " AND a.dataPontaj BETWEEN :start AND :end");
        Query q2 = em.createQuery("SELECT SUM(a.minuteMunca) FROM Activitate a WHERE a.proiect = :proiect "
                + " AND a.dataPontaj BETWEEN :start AND :end");
        /*q1.setParameter("proiect", proiect);
        q1.setParameter("start", startDate);
        q1.setParameter("end",endDate);*/

        q2.setParameter("proiect", proiect);
        q2.setParameter("start", startDate);
        q2.setParameter("end", endDate);

        //Object o1 = q1.getSingleResult();
        Object o2 = q2.getSingleResult();
        if (o2 != null) {
            long minute = (long) o2;
            //long ore = (long) o1;
            return minute / 60;
        }

        return 0;
    }

    public int getOreAngajatLuna(Angajat a) {
        Date first = new Date();
        first.setDate(1);
        //Query q1 = em.createQuery("SELECT SUM(a.oreMunca) FROM Activitate a WHERE a.angajat = :angajat AND a.dataPontaj BETWEEN"
        //+ " :start AND :end");
        Query q2 = em.createQuery("SELECT SUM(a.minuteMunca) FROM Activitate a WHERE a.angajat = :angajat AND a.dataPontaj BETWEEN"
                + " :start AND :end");
        /*q1.setParameter("angajat", a);
        q1.setParameter("start", first);
        q1.setParameter("end", new Date());*/

        q2.setParameter("angajat", a);
        q2.setParameter("start", first);
        q2.setParameter("end", new Date());

        //Object o1 = q1.getSingleResult();
        Object o2 = q2.getSingleResult();

        //if(o1==null) return 0;
        //long l1 = (long)o1;
        //int ore = toIntExact(l1);
        if (o2 == null) {
            return 0;
        }
        long minute = (long) o2;

        long ore = minute / 60;
        return toIntExact(ore);
        //return 0;
    }

    /*public List<Activitate> getActivitatiTip(String comanda,int[] cmd,int cod){
        
    }*/
    public Activitate getFirstActivityProject(Proiect p) {
        Query q = em.createQuery("SELECT a FROM Activitate a WHERE a.proiect = :proiect ORDER BY a.dataPontaj ASC");
        q.setParameter("proiect", p);
        List<Activitate> pontaj = q.setMaxResults(1).getResultList();

        if (pontaj.size() > 0) {
            return pontaj.get(0);
        }
        return null;
    }

    public List<Angajat> getAngajatiOnProject(Proiect p) {
        Query q = em.createQuery("SELECT DISTINCT ac.angajat FROM Angajat a, Activitate ac WHERE "
                + "a = ac.angajat AND ac.proiect = :proiect");
        q.setParameter("proiect", p);
        List<Angajat> angajati = (q.getResultList());
        return angajati;
    }

    public int getOreProiectByAngajat(Proiect p, Angajat a) {
        Query q = em.createQuery("SELECT SUM(a.minuteMunca) FROM Activitate a WHERE a.angajat = :angajat AND a.proiect = :proiect");
        q.setParameter("angajat", a);
        q.setParameter("proiect", p);

        long minute = (long) q.getSingleResult();
        return (int) (minute / 60);
    }

    public List<Angajat> getAngajatiOnProjectBetweenDates(Proiect p, Date start, Date end) {
        Query q = em.createQuery("SELECT DISTINCT ac.angajat FROM Angajat a, Activitate ac WHERE "
                + "a = ac.angajat AND ac.proiect = :proiect AND ac.dataPontaj BETWEEN :start AND :end");
        q.setParameter("proiect", p);
        q.setParameter("start", start);
        q.setParameter("end", end);
        List<Angajat> angajati = (q.getResultList());
        return angajati;
    }

    public List<Activitate> getRaport(String raport) {
        Query q = em.createQuery(raport);
        List<Activitate> activitati = q.getResultList();
        return activitati;
    }

    public List<Activitate> getRaportProiect(String raport, Proiect p) {
        Query q = em.createQuery(raport);
        q.setParameter("proiect", p);
        List<Activitate> activitati = q.getResultList();
        return activitati;
    }

    public List<RaportBrut> createView(String query) {
        Query q = em.createNativeQuery(query);
        Query q1 = em.createNativeQuery("SELECT * FROM rapoarte");

        q.executeUpdate();
        List<RaportBrut> result = (List<RaportBrut>) q1.getResultList();
        ArrayList<RaportBrut> l = new ArrayList<>();
        Iterator itr = result.iterator();
        while (itr.hasNext()) {
            Object[] obj = (Object[]) itr.next();
            //now you have one array of Object for each row
            int id = Integer.parseInt(String.valueOf(obj[0])); // don't know the type of column CLIENT assuming String 
            char corp = ' ';
            if (!String.valueOf(obj[1]).equals("")) {
                corp = String.valueOf(obj[1]).charAt(0);
            }
            long min = Long.parseLong(String.valueOf(obj[2]));
            String detalii = String.valueOf(obj[3]);
            String numeAngajat = String.valueOf(obj[4]);
            String descriere = String.valueOf(obj[5]);
            String numeProiect = String.valueOf(obj[6]);
            String etaj = String.valueOf(obj[7]);
            RaportBrut r = new RaportBrut();
            r.setId(id);
            r.setCorp(corp);
            r.setMinute(min);
            r.setDetalii(detalii);
            r.setNumeAngajat(numeAngajat);
            r.setDescriere(descriere);
            r.setNumeProiect(numeProiect);
            r.setEtaj(etaj);
            l.add(r);

        }
        return l;
    }

    public ArrayList<RaportOreAngajat> getRaportOreAngajat(String select) {
        Query q = em.createNativeQuery(select);

        List<RaportOreAngajat> result = q.getResultList();
        ArrayList<RaportOreAngajat> l = new ArrayList<>();
        Iterator itr = result.iterator();
        while (itr.hasNext()) {
            Object[] obj = (Object[]) itr.next();
            RaportOreAngajat r = new RaportOreAngajat();
            r.setNume(String.valueOf(obj[0]));
            double d = Double.parseDouble(String.valueOf(obj[1]));
            r.setOre((int) d);
            l.add(r);
        }
        return l;
    }

    public ArrayList<RaportBrut> getRaportOrd(String select) {
        Query q = em.createNativeQuery(select);

        List<RaportBrut> result = (List<RaportBrut>) q.getResultList();
        ArrayList<RaportBrut> l = new ArrayList<>();
        Iterator itr = result.iterator();

        while (itr.hasNext()) {
            Object[] obj = (Object[]) itr.next();
            String numeProiect = String.valueOf(obj[0]);
            String desc = String.valueOf(obj[1]);
            char corp = ' ';
            if (!String.valueOf(obj[2]).equals("")) {
                corp = String.valueOf(obj[2]).charAt(0);
            }
            String etaj = String.valueOf(obj[3]);
            String numeAngajat = String.valueOf(obj[4]);
            double sum = Double.parseDouble(String.valueOf(obj[5]));
            RaportBrut r = new RaportBrut();
            r.setNumeProiect(numeProiect);
            r.setDescriere(desc);
            r.setCorp(corp);
            r.setEtaj(etaj);
            r.setNumeAngajat(numeAngajat);
            r.setMinute((int) sum);
            l.add(r);
        }
        return l;
    }

    public long getAngajatTimpPontajBetweenDates(Date startDate, Date endDate, Angajat ang) {
        Query q = em.createQuery("SELECT SUM(a.minuteMunca) FROM Activitate a WHERE a.angajat = :angajat AND a.dataPontaj BETWEEN "
                + ":startDate AND :endDate");
        q.setParameter("angajat", ang);
        q.setParameter("startDate", startDate);
        q.setParameter("endDate", endDate);
        
        Object o = q.getSingleResult();
        long l = (long) o;
        return l;
    }
}
