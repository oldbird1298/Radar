/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radar.udp.Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dgerontop
 */
public class udpClient {
    DatagramSocket sock = null;
    
    public void udpClient (String host, int port) {
        try {
            sock = new DatagramSocket();
            InetAddress ip = InetAddress.getByName(host);
            
            while (true) {
                String alive = "Alive";
                byte[] b = alive.getBytes();
                
                DatagramPacket dp = new DatagramPacket(b, b.length, ip, port);
                sock.send(dp);
                
                //now receive reply
                //buffer to receive reply
                byte[] buffer = new byte[65536];
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                sock.receive(reply);
                
                byte[] data = new byte[65536];
                String s = new String(data, 0 , reply.getLength());
                echo(reply.getAddress().getHostAddress() + " : " + reply.getPort() + " " + s);
            }
        } catch (SocketException ex) {
            Logger.getLogger(udpClient.class.getName()).log(Level.SEVERE, null, ex);
        }catch (IOException e) {
            System.err.println("IOException" + e);
        }
        
    
    }
    
    private static void echo (String msg) {
        System.out.println(msg);
    }
    
    public void sendData (String host, int port, String msg) {
        try {
            sock = new DatagramSocket();
            InetAddress ipAddrr = InetAddress.getByName(host);
            byte[] buffer = msg.getBytes();
            DatagramPacket sendDp = new DatagramPacket(buffer, buffer.length, ipAddrr, port);
            sock.send(sendDp);
            
            
        } catch (SocketException ex) {
            Logger.getLogger(udpClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(udpClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(udpClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    }
    
}
