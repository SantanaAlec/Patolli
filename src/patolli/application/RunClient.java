/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package patolli.application;

import patolli.game.online.Client;

public class RunClient {

    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 95;
    private static String[] testArgs = {"/setname Alec", "/creategroup p" , "/createchannel p"};

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Client client = Client.getInstance();

        client.setIp(SERVER_IP);
        client.setPort(SERVER_PORT);
        client.setArgs(testArgs);

        if (!client.run()) {
            System.exit(1);
        }
    }

}
