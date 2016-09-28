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
public class Radar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        String mode;
        if (args[1] != null) {
            if (args[1].equals("server")) {
                 mode = args[1];
            }else {
                 mode = "client";
            }
        }else {
            mode = "standalone"; 
        }
        
        RadarHandler test = new RadarHandler();
       Tui test2 = new Tui();
       test2.tuishellStart(mode);
       
    }
    
}
