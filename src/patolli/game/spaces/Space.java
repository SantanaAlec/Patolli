/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patolli.game.spaces;

<<<<<<< Updated upstream:src/entities/spaces/Space.java

=======
>>>>>>> Stashed changes:src/patolli/game/spaces/Space.java
import java.util.ArrayList;
import patolli.game.Player;
import patolli.game.tokens.Token;

public abstract class Space {

    private final ArrayList<Token> tokens = new ArrayList<>();

    public ArrayList<Token> list() {
        return tokens;
    }

<<<<<<< Updated upstream:src/entities/spaces/Space.java
    public Token getToken(final int index) {
=======
    public Token get(final int index) {
>>>>>>> Stashed changes:src/patolli/game/spaces/Space.java
        return tokens.get(index);
    }

    public void set(final int index, final Token token) {
        tokens.set(index, token);
    }
<<<<<<< Updated upstream:src/entities/spaces/Space.java
    
    public void insertToken(final Token token) {
=======

    public void insert(final Token token) {
>>>>>>> Stashed changes:src/patolli/game/spaces/Space.java
        tokens.add(token);
    }

    public void remove(final Token token) {
        tokens.remove(token);
    }

    public void clear() {
        tokens.clear();
    }

    public boolean isEmpty() {
        return !tokens.isEmpty();
    }

    //Método para saber si la casilla tiene dueño
    public Player getOwner() {
<<<<<<< Updated upstream:src/entities/spaces/Space.java
        //Si tiene dueño dime quien es
        if (!tokens.isEmpty()) {
=======
        if (isEmpty()) {
>>>>>>> Stashed changes:src/patolli/game/spaces/Space.java
            return tokens.get(0).getOwner();
        }
        //Sino envia un null
        return null;
    }
}
