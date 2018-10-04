/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controller;

// Ip theo: 192.168.153.96
// Ip nas: 192.168.153.5
// Ip raz: 192.168.153.135
/**
 *
 * @author Razvan
 */
public class IpNumber {
    static String ip = "192.168.153.5";
    //static String ip = "localhost";
    static int port = 4444;
    
    public static String getIp(){
        return ip;
    }
    
    public static int getPort(){
        return port;
    }
}
