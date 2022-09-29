/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application;

public class Main {

    private static final Game game = Game.getInstance();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //game();
        game.run();
    }

    public static Game getGame() {
        return game;
    }

}
