/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patolli;

import patolli.spaces.Space;
import java.util.ArrayList;
import patolli.spaces.CentralSpace;
import patolli.spaces.ExteriorSpace;
import patolli.spaces.SquareSpace;
import patolli.spaces.TriangleSpace;

/**
 *
 * @author Alec_
 */
public class Board {

    private ArrayList<Player> players;
    private ArrayList<Space> spaces = new ArrayList<>();
    private ArrayList<Token> tokens = new ArrayList<>();

    public Board(ArrayList<Player> players) {
        this.players = players;
    }

    private void setBoard() {
        //total: 56 - triagnle: 16 - exterior: 8 - central: 4
        final int squares = 5;
        final int triangles = 2;

        for (int index = 0; index < 4; index++) {
            for (int side = 0; side < 2; side++) {
                for (int square = 0; square < squares; square++) {
                    spaces.add(new SquareSpace());
                }

                for (int triangle = 0; triangle < triangles; triangle++) {
                    spaces.add(new TriangleSpace());
                }

                if (side < 1) {
                    for (int exterior = 0; exterior < 2; exterior++) {
                        spaces.add(new ExteriorSpace());
                    }
                }
            }

            spaces.add(new CentralSpace());
        }
    }
}
