/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package patolli.game.application;

import net.kaw.dradacorus.online.KoboldClient;

public class PatolliRunClient {

    private static final String SERVER_IP = "6.tcp.ngrok.io";
    private static final int SERVER_PORT = 18213;
    private static final String[] ARGS = {};

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        KoboldClient client = new KoboldClient(SERVER_IP, SERVER_PORT);

        if (!client.run(ARGS)) {
            System.exit(1);
        }
    }

}
