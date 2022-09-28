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
}
