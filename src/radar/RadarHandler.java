/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radar;

import org.w3c.dom.Document;

/**
 *
 * @author dgerontop
 */
public class RadarHandler {

    private String Radar1 = "Hortiatis";
    private String Radar2 = "Parnis";
    private String Radar3 = "Vitsi";
    private String Radar4 = "Lefkas";
    private int total = 4;
    private int isOpen = 0;
    private int a = 1;
    private Document dom;

    public RadarHandler() {
        String display;
        displayMessage();
        

    }

    private void displayMessage() {
        System.out.printf("Welcome to Radar Handler\n");
        System.out.printf("\n%s %20s", "Radar", "Name\n");

    }

    

    

    /**
     * I take a xml element and the tag name, look for the tag and get the text
     * content i.e for <employee><name>John</name></employee> xml snippet if the
     * Element points to employee node and tagName is 'name' I will return John
     */
    
        
    
    
}
