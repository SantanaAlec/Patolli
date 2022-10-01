/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spaces;


import java.util.ArrayList;
import patolli.Player;
import patolli.Token;

public abstract class Space {

    private final ArrayList<Token> tokens = new ArrayList<>();

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public Token getToken(final int index) {
        return tokens.get(index);
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

    //Método para saber si la casilla tiene dueño
    public Player getOwner() {
        //Si tiene dueño dime quien es
        if (!tokens.isEmpty()) {
            return tokens.get(0).getOwner();
        }
        //Sino envia un null
        return null;
    }
}
