/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package patolli.application;

import patolli.game.online.server.ServerManager;

public class Server {

    private static final int PORT = 95;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        final ServerManager server = ServerManager.getInstance();

        server.setPort(PORT);

        if (!server.start()) {
            System.exit(1);
        }
    }

}
