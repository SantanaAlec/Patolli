/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application;

import entities.Player;
import entities.Token;
import entities.spaces.CentralSpace;
import entities.spaces.ExteriorSpace;
import entities.spaces.Space;
import entities.spaces.SquareSpace;
import entities.spaces.TriangleSpace;
import java.util.ArrayList;

public class Board {

    private static Board instance;

    /**
     * Singleton pattern to keep a single instance of this class program running
     *
     * @return The instance of the program is returned, if there's none a new one is created
     */
    public static Board getInstance() {
        if (instance == null) {
            instance = new Board();
        }

        return instance;
    }

    private final ArrayList<Space> spaces = new ArrayList<>();

    private final int SQUARES_AMOUNT = 5, TRIANGLES_AMOUNT = 1;

    private Board() {
    }

    public void init() {
        createBoard();
    }

    private void createBoard() {
        resetBoard();

        for (int index = 0; index < 4; index++) {
            addBlade(SQUARES_AMOUNT, TRIANGLES_AMOUNT);

            addCenterSpace();
        }
    }

    private void addBlade(final int squares, final int triangles) {
        final int RIGHT = 0, LEFT = 1;
        for (int side = RIGHT; side < 2; side++) {
            addSquareSpaces(squares);

            if (side == RIGHT) {
                addTriangleSpaces(triangles);
                addExteriorSpace();
            } else if (side == LEFT) {
                addExteriorSpace();
                addTriangleSpaces(triangles);
            }
        }
    }

    private void addSquareSpaces(final int amount) {
        for (int index = 0; index < amount; index++) {
            spaces.add(new SquareSpace());
        }
    }

    private void addTriangleSpaces(final int amount) {
        for (int index = 0; index < amount; index++) {
            spaces.add(new TriangleSpace());
        }
    }

    private void addExteriorSpace() {
        spaces.add(new ExteriorSpace());
    }

    private void addCenterSpace() {
        spaces.add(new CentralSpace());
    }

    public int getBoardSize() {
        return spaces.size();
    }

    public void resetBoard() {
        this.spaces.clear();
    }

    public void insertTokenAtPos(final Token token, final int pos) {
        token.setCurrentPos(pos);
        getSpace(pos).setToken(token);
    }

    public void removeTokenAtPos(final int pos) {
        getTokenAtPos(pos).setCurrentPos(-1);
        getSpace(pos).setToken(null);
    }

    public Token getTokenAtPos(final int pos) {
        return getSpace(pos).getToken();
    }

    public int getTokenPos(final Token token) {
        return token.getCurrentPos();
    }

    public void removeTokensFromPlayer(final Player player) {
        for (Token token : player.getTokens()) {
            removeTokenAtPos(token.getCurrentPos());
        }
    }

    public void moveTokenToPos(final int prevPos, final int nextPos) {
        insertTokenAtPos(getTokenAtPos(prevPos), nextPos);
        removeTokenAtPos(prevPos);
    }

    public Space getTokenSpace(final Token token) {
        return getSpace(getTokenPos(token));
    }

    public Space getSpace(final int index) {
        return spaces.get(index - 1);
    }

    public ArrayList<Space> getSpaces() {
        return spaces;
    }

}
