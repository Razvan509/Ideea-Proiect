/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.dao;

import javax.persistence.EntityManager;

/**
 *
 * @author razvan
 */
public class ActivitateDao {
    
    private EntityManager em;
    
    public ActivitateDao(EntityManager em){
        this.em = em;
    }
}
