/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radar.udp.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import radar.udp.Client.udpClient;

/**
 *
 * @author dgerontop
 */
public class udpServer {

    DatagramSocket sock = null;

    public void udpServer(int port) {

        try {
            //creating a server sockeet, parameter is local port number
            sock = new DatagramSocket(port);

            //buffer to receive incoming data
            byte[] buffer = new byte[65536];
            DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);

            //communication loop
            while (true) {
                sock.receive(incoming);
                byte[] data = incoming.getData();
                String s = new String(data, 0, incoming.getLength());
                echo(incoming.getAddress().getHostAddress() + " " + incoming.getPort() + " " + s);
                s = "OK : " + s;
                DatagramPacket dp = new DatagramPacket(s.getBytes(), s.getBytes().length, incoming.getAddress(), incoming.getPort());
                sock.send(dp);
                
            }

        } catch (SocketException ex) {
            Logger.getLogger(udpServer.class.getName()).log(Level.SEVERE, null, ex);
        }catch (IOException e) {
            System.err.println("IOException" + e);
        }

    }
    
    
    public void sendDataTo (String host, int port, String msg) {
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
    
    private static void echo(String msg) {
        System.out.println(msg);
    }

}
