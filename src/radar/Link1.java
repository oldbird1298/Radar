/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radar;

/**
 *
 * @author dgerontop
 */
public class Link1 {
    
    String type;
    String remote;
    String name;
    String mps;
    String baud;
    String port;
    
    @Override
    public String toString() {
        String format = String.format("Link : (%-3s)%-10s%20s->%s %20s :%s | %s,%s", remote, name, "MPS", mps, "PORT", port , baud, type);
        return format; 
    
    }
    
}
