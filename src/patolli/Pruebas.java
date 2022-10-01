/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package patolli;

import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Alec_
 */
public class Pruebas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Player player1 = new Player("Alec", Color.BLUE, 1000);
        Player player2 = new Player("Demian", Color.GREEN, 1000);
        Player player3 = new Player("Santana", Color.RED, 1000);
        Player player4 = new Player("Celaya", Color.MAGENTA, 1000);

        ArrayList<Player> jug = new ArrayList<>();
        jug.add(player1);
        jug.add(player2);
        jug.add(player3);
        jug.add(player4);

        Board board = new Board(3, 2, jug);
        System.out.println(board.boardSize());
    }
}
