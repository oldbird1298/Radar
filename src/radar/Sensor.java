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
public class Sensor {
    String type;
    String id;
    String name;
    String mps;
    String baud;
    String port;
    
    @Override
    public String toString(){
        String format = String.format("Radar : (%-3s)%-10s%20s->%s %20s :%s | %s,%s", id, name, "MPS", mps, "PORT", port , baud, type);
        return format; 
    }

    public String getId() {
        return id;
    }

    public String getMps() {
        return mps;
    }

    public String getBaud() {
        return baud;
    }

    public String getPort() {
        return port;
    }
    
    
    
}
