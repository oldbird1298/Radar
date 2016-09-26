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
public class ShowSystem {
    private final String osName = System.getProperty("os.name");
    private final String osArch = System.getProperty("os.arch");
    private final String osVersion = System.getProperty("os.Version");
    private final String userHome = System.getProperty("user.home");
    private final String userName = System.getProperty("user.name");
    private final String userDir = System.getProperty("user.dir");
    
    public void showSystem() {
        System.out.printf("Operation System Name : %10s\nOperation System Arch : %10s\nOperation System Ver. : %10s\nUser Home Dir : %10s\nUser Name : %10s\n", osName, osArch, osVersion, userHome, userName );
    
    }
    
    public String getSysOs () {
        return osName;
    
    }

    public String getUserDir() {
        return userDir;
    }

    public String getUserName() {
        return userName;
    }
    
    
    
    
    
    
}
