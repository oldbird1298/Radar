/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import jdk.nashorn.internal.parser.TokenType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author dgerontop
 */
public class Tui {

    Scanner commands = new Scanner(System.in);
    final String os = System.getProperty("os.name");
    String state = new String();
    String args = new String();
    String args2 = new String();
    String id = new String();
    ShowSystem showProp = new ShowSystem();
    ExecuteShellCommand obj = new ExecuteShellCommand();

    public Tui() {
        state = "started";
        System.out.printf("The tui is : %s\n", state);

    }

    public void tuishellStart() {
        do {
            System.out.flush();
            //clearScreen();
            System.out.println(os);
            try {
                if (os.contains("Windows")) {
                    Runtime.getRuntime().exec("cls");

                } else {
                    Runtime.getRuntime().exec("clear");
                }
            } catch (final Exception e) {

            }
            System.out.println(" ");
            System.out.println("Status will be printed here");
            System.out.printf("%s@%s : ", showProp.getUserName(), showProp.getUserDir());
            args2 = "";
            id = "";
            args = commands.nextLine();

            if (args.startsWith("start")) {
                String[] results = args.split(" ");
                args = results[0];
                for (int i = 1; i < results.length; i++) {
                    String tempString = results[i];
                    tempString = tempString.concat(" ");
                    args2 = args2.concat(tempString);
//                    System.out.println(args2);
                }
            }
            if (args.startsWith("stop")) {
                String[] results = args.split(" ");
                args = results[0];
                for (int i = 1; i < results.length; i++) {
                    String tempString = results[i];
                    tempString = tempString.concat(" ");
                    args2 = args2.concat(tempString);
//                    System.out.println(args2);
                }
            }

            switch (args) {
                case "status":
                    try {
                        //Get the DOM Builder Factory
                        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                        //Get the DOM Builder
                        DocumentBuilder builder = factory.newDocumentBuilder();
                        //Load and parse the XML Document
                        //Document contains the complete XML as a Tree.
                        Document configSensor = builder.parse(new File("ConfigSensor.xml"));

                        List<Sensor> sensList = new ArrayList<>();

                        //Iterating throught the nodes and extracting Sensrors form the XML
                        NodeList sensorNode = configSensor.getDocumentElement().getChildNodes();

                        for (int temp = 0; temp < sensorNode.getLength(); temp++) {
                            Node node = sensorNode.item(temp);
                            if (node instanceof Element) {
                                Sensor sen = new Sensor();
                                sen.type = node.getAttributes().getNamedItem("type").getNodeValue();

                                NodeList childnodes = node.getChildNodes();
                                for (int temp2 = 0; temp2 < childnodes.getLength(); temp2++) {
                                    Node cnode = childnodes.item(temp2);
                                    if (cnode instanceof Element) {
                                        String content = cnode.getLastChild().getTextContent().trim();
                                        switch (cnode.getNodeName()) {
                                            case "name":
                                                sen.name = content;
                                                break;
                                            case "id":
                                                sen.id = content;
                                                break;
                                            case "mps":
                                                sen.mps = content;
                                                break;
                                            case "port":
                                                sen.port = content;
                                                break;
                                            case "baud":
                                                sen.baud = content;
                                                break;

                                        }
                                    }
                                }
                                sensList.add(sen);
                            }

                        }
                        for (Sensor sen : sensList) {
                            //Prepei na VALW to ps -ef an einai trexei to programma kai dump an einai online
                            if (!getInfo(sen.id)) {
                                System.out.println(sen + "| running");
                            }else {
                                System.out.println(sen + "| inactive");
                            }
                        }
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
//                    for (int i = 0; i < 10; i++) {
//                        System.out.printf("Radar %20s -> %20s\n", "MPS", "PORT");

                    break;
                case "show":

                    showProp.showSystem();
                    System.out.println("TMHMA PROG. AER.");
                    break;
                case "start":
                    //CHECK WHAT TO START SENSOR,LINK OR DATA;
                    String[] testArg2 = args2.split(" ");
                    args2 = testArg2[0];
                    //STARTING SENSOR
                    if (args2.equals("sensor")) {
                        for (int i = 1; i < testArg2.length; i++) {
                            //EAN EINAI ALL XEKINAEI OLA TA RADAR POU EINAI STO ConfigSensor.xml alliws mazeuei ta id
                            if (testArg2[i].equals("all")) {
                                System.out.println("Starting all radars local");
                                try {
                                    List<Sensor> startSenList = readSensorXML();
                                    for (Sensor startSenList1 : startSenList) {
                                        String printStartingData = "Starting Sensor : ";
                                        String startSen = startSensorCommand(startSenList1.id, startSenList1.mps, startSenList1.port, startSenList1.baud);
                                        System.out.println(startSen);
                                        System.out.println(printStartingData + startSenList1.id);
                                    }
                                } catch (ParserConfigurationException ex) {
                                    Logger.getLogger(Tui.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (SAXException ex) {
                                    Logger.getLogger(Tui.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (IOException ex) {
                                    Logger.getLogger(Tui.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                String command = "uname -a";
                                String output = obj.executeCommand(command);
                                System.out.println(output);

                            } else {
                                id = id.concat(testArg2[i]);

                            }

                        }//END OF FOR- LOOP FOR SUPPLEMENTARY ARGS AFTER SENSOR
                        if (id.contains(",")) {
                            System.out.println("Yes it contains ,");
                            String testID[] = id.split(",");
                            try {
                                List<Sensor> startSenList = readSensorXML();
                                int index = 0;
                                for (int i = 0; i < testID.length; i++) {
                                    int j = 0;
                                    for (Sensor startSenList2 : startSenList) {
                                        if (testID[i].equals(startSenList2.id)) {
                                            j = 1;
                                            String printStartingData = "Starting Sensor : ";
                                            System.out.println(printStartingData + startSenList2.id);
                                            String startSen = startSensorCommand(startSenList2.id, startSenList2.mps, startSenList2.port, startSenList2.baud);
                                            System.out.println(startSen);
                                        }
                                    }
                                    if (j != 1) {
                                        System.out.printf("The %s ID of Sensor is not in the ConfigSensorXML.\n", testID[i]);
                                    }
                                    index++;
                                }
                            } catch (ParserConfigurationException ex) {
                                Logger.getLogger(Tui.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (SAXException ex) {
                                Logger.getLogger(Tui.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(Tui.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        } else {
                            id.trim();
                            System.out.println(id);
                            try {
                                List<Sensor> startSenList = readSensorXML();
                                for (Sensor startSenList3 : startSenList) {
                                    if (id.equals(startSenList3.id)) {
                                        String printStartingData = "Starting Sensor : ";
                                        System.out.println(printStartingData + startSenList3.id);
                                        String startSen = startSensorCommand(startSenList3.id, startSenList3.mps, startSenList3.port, startSenList3.baud);
                                        System.out.println(startSen);
                                    }

                                }
                            } catch (ParserConfigurationException ex) {
                                Logger.getLogger(Tui.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (SAXException ex) {
                                Logger.getLogger(Tui.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(Tui.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                        System.out.printf("Auto einai to args2 : %s\nKai auto einai to id : %s\n", args2, id);
                    } else if (args2.equals("link")) {
                        System.out.println("Link will started here");

                    } else {
                        System.out.println("For help of start press help start");
                    }
//                    System.out.println(args + " : " + args2);
                    break;
                case "stop":
                    testArg2 = args2.split(" ");
                    args2 = testArg2[0];
                    if (args2.equals("sensor")) {
                        System.out.println("Auto einai to args2 : " + args2);
                    } else {
                        System.out.println("For help of start press help start");
                    }
//                    System.out.println(args + " : " + args2);
                    break;
                case "exit":
                    state = "exit";
                    break;
                default:
                    System.out.printf("The available commands are :\n %s\n%s\n%s\n", "status", "show", "exit");
            }

        } while (!state.equals(
                "exit"));

    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();

    }

    private String startSensorCommand(String id, String mps, String port, String baud) {
        String startSensor = "./radar_receive.exe -s 0 " + "-r " + id + " -p " + mps + ":" + port + " -b " + baud + " -e notnrzi &";
        String sensorOutPut = obj.executeCommand(startSensor);
        System.out.println(startSensor);
        return sensorOutPut;

    }

    private boolean getInfo(String sensorID) {
        String ourPID = "ps -u mio -f -o \"pid,args\" | grep \"-r " + sensorID + "\" | grep bin_sun4v_sunOS/radar_receive.exe | naw \'{print $1}\'";
        String isRunning;
        isRunning = obj.executeCommand(ourPID);
        //System.out.println(ourPID + " ::::::" + isRunning + " ::::::");
        if (isRunning.equals("")) {
            return true;
        } else {

            return false;
        }
    }

    public List<Sensor> readSensorXML() throws ParserConfigurationException, SAXException, IOException {
        //Get the DOM Builder Factory
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //Get the DOM Builder
        DocumentBuilder builder = factory.newDocumentBuilder();
        //Load and parse the XML Document
        //Document contains the complete XML as a Tree.
        Document configSensor = builder.parse(new File("ConfigSensor.xml"));

        List<Sensor> sensList = new ArrayList<>();

        //Iterating throught the nodes and extracting Sensrors form the XML
        NodeList sensorNode = configSensor.getDocumentElement().getChildNodes();
        for (int temp = 0; temp < sensorNode.getLength(); temp++) {
            Node node = sensorNode.item(temp);
            if (node instanceof Element) {
                Sensor sen = new Sensor();
                sen.type = node.getAttributes().getNamedItem("type").getNodeValue();

                NodeList childnodes = node.getChildNodes();
                for (int temp2 = 0; temp2 < childnodes.getLength(); temp2++) {
                    Node cnode = childnodes.item(temp2);
                    if (cnode instanceof Element) {
                        String content = cnode.getLastChild().getTextContent().trim();
                        switch (cnode.getNodeName()) {
                            case "name":
                                sen.name = content;
                                break;
                            case "id":
                                sen.id = content;
                                break;
                            case "mps":
                                sen.mps = content;
                                break;
                            case "port":
                                sen.port = content;
                                break;
                            case "baud":
                                sen.baud = content;
                                break;

                        }
                    }
                }
                sensList.add(sen);
            }

        }
//        for (Sensor sen : sensList) {
//            System.out.println(sen);
//        }
        return sensList;
    }

}
