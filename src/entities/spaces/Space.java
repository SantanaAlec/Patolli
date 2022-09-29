/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities.spaces;

import entities.Player;
import entities.Token;
import java.util.ArrayList;

public abstract class Space {

    private final ArrayList<Token> tokens = new ArrayList<>();

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public Token getToken(final int index) {
        return tokens.get(index - 1);
    }

    public void setToken(final int index, final Token token) {
        tokens.set(index, token);
    }

    public void insertToken(final Token token) {
        tokens.add(token);
    }

    public void removeToken(final Token token) {
        tokens.remove(token);
    }

    public void clearTokens() {
        tokens.clear();
    }

    public boolean hasTokens() {
        return !tokens.isEmpty();
    }

    public Player getOwner() {
        if (hasTokens()) {
            return tokens.get(0).getOwner();
        }

        return null;
    }

}
