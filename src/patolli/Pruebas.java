/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package patolli;

import entities.spaces.Player;
import java.awt.Color;

/**
 *
 * @author Alec_
 */
public class Pruebas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //Prueba #1
        Game game = Game.getInstance();

        Player jugador1 = new Player("Mateo", Color.yellow, 1000);
        Player jugador2 = new Player("Santiago", Color.GREEN, 1000);

        game.addPlayer(jugador1);

        game.addPlayer(jugador2);

        game.setBet(1000);

        game.run();

        game.firstThrow(jugador1);
        game.firstThrow(jugador2);
        if (jugador1.tokensInGame() > 0) {
            game.moverToken(jugador1.getTokens().get(0));
        }
        if (jugador2.tokensInGame() > 0) {
            game.moverToken(jugador2.getTokens().get(0));
        }
    }
}
