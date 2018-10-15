/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import java.io.Serializable;

/**
 *
 * @author lane
 */
public class Pair implements Serializable{
    private long x;
    private long y;
    
    public Pair(){
        x = y = 0;
    }
    
    public Pair(long a,long b){
        x = a;
        y = b;
    }

    public long getX() {
        return x;
    }

    public void setX(long x) {
        this.x = x;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }
    
    
}
