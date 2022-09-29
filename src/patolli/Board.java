/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patolli;

import java.util.ArrayList;
import patolli.spaces.CentralSpace;
import patolli.spaces.ExteriorSpace;
import patolli.spaces.Space;
import patolli.spaces.SquareSpace;
import patolli.spaces.TriangleSpace;

/**
 *
 * @author Alec_
 */
public class Board {

    private final ArrayList<Player> players;
    private final ArrayList<Space> spaces = new ArrayList<>();
    private final ArrayList<Token> tokens = new ArrayList<>();

    public Board(final int squares, final int triangles, final ArrayList<Player> players) {
        this.players = players;
        initializeBoard(squares, triangles);
    }

    private void initializeBoard(final int squares, final int triangles) {
        //total: 56 - triagnle: 16 - exterior: 8 - central: 4
        for (int index = 0; index < 4; index++) {
            for (int side = 0; side < 2; side++) {
                for (int square = 0; square < squares; square++) {
                    spaces.add(new SquareSpace());
                }

                if (side == 0) {
                    for (int triangle = 0; triangle < triangles; triangle++) {
                        spaces.add(new TriangleSpace());
                    }
                }

                if (side < 1) {
                    for (int exterior = 0; exterior < 2; exterior++) {
                        spaces.add(new ExteriorSpace());
                    }

                    for (int triangle = 0; triangle < triangles; triangle++) {
                        spaces.add(new TriangleSpace());
                    }
                }
            }

            spaces.add(new CentralSpace());
        }
    }

    public void regresarToken(Token token, Space space) {
        if (space instanceof CentralSpace) {

        } else {
            //Si a la ficha que es actualmente dueña de la casilla le caen encima, devuelve al que le cayo encima
            if (token.getOwner() !=) {

            }
        }
    }

    //Para cuando ya hay fichas adentro
    public void moverToken(Token token) {
        //Establecer quien esta lanzando los dados
        switch (Patolli.lanzarDados(5)) {
            case 0 -> playersDemand(token);
            case 1 -> token.setPosition(token.getPosition()+1); //Tratar de arreglar la posición para que funcione como Space
            case 2 -> token.setPosition(token.getPosition()+2);
            case 3 -> token.setPosition(token.getPosition()+3);
            case 4 -> token.setPosition(token.getPosition()+4);
            case 5 -> token.setPosition(token.getPosition()+10);
            default -> throw new AssertionError();
        }
    }

    public void insertarToken(Player player) {
        //Si tú como jugador no tienes fichas dentro, en tu primer tirada usa esto
        //Establecer quien esta lanzando los dados
        if (Patolli.lanzarDados(5) >= 1) {
            //Ingresar ficha
            Token token = new Token(player);
            tokens.add(token);
            //Establecer en la posición que debe iniciar
        }
    }

    public void playersDemand(Token token) {
        for (Player player : Patolli.getPlayers()) {
            if (player.equals(token.getOwner())) {
                player.setBag(player.getBag() - ((Patolli.getBet() * 2) * (Patolli.getPlayers().size() - 1)));
                if (player.getBag() <= 0) {
                    //Establecer que perdio el juego y ponerlo en gris pero que continue para los demás
                }
            } else {
                player.setBag(player.getBag() + ((Patolli.getBet() * 2)));
            }
        }
    }
}
