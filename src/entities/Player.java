/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.awt.Color;
import java.util.ArrayList;
import java.util.UUID;

public class Player {

    private final UUID id;

    private String name;

    private Color color;

    private final ArrayList<Token> tokens = new ArrayList<>();

    private int currentToken = 1;

    private int balance;

    public Player(String name, Color color, int balance) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.color = color;
        this.balance = balance;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getCurrentToken() {
        return currentToken;
    }

    public String getIdString() {
        final StringBuilder sb = new StringBuilder();

        sb.append("(").append(id).append(")");
        return sb.toString();
    }

    public boolean isBroke() {
        return balance <= 0;
    }

    public Token createAndAssignToken(final int initialPos) {
        if (getTokensCount() < 6) {
            final Token token = new Token(this, initialPos);

            tokens.add(token);

            return token;
        }

        return null;
    }

    public Token getToken(final int index) {
        return tokens.get(index - 1);
    }

    public int getTokenIndex(final Token token) {
        if (!tokens.isEmpty()) {
            for (int i = 1; i <= getTokensCount(); i++) {
                if (getToken(i).equals(token)) {
                    return i;
                }
            }
        }

        return -1;
    }

    public Token getFirstToken() {
        if (!tokens.isEmpty()) {
            return getToken(1);
        }

        return null;
    }

    public Token getLastToken() {
        if (!tokens.isEmpty()) {
            return getToken(getTokensCount());
        }

        return null;
    }

    public int getTokensCount() {
        return tokens.size();
    }

    public int tokensInPlay() {
        if (tokens.isEmpty()) {
            return 0;
        }

        int count = 0;
        for (Token token : tokens) {
            if (token.getCurrentPos() >= 0) {
                count++;
            }
        }

        return count;
    }

    public boolean hasTokens() {
        if (tokens.isEmpty()) {
            return false;
        }

        return getTokensCount() > 0;
    }

    public boolean hasInsertedAllTokens() {
        return getTokensCount() >= 6;
    }

    public void clearTokens() {
        tokens.clear();
    }

    public void selectNextToken() {
        if (tokens.isEmpty()) {
            return;
        }

        if (currentToken > 1) {
            for (int i = currentToken; i <= getTokensCount(); i++) {
                if (getToken(i).getCurrentPos() >= 0) {
                    currentToken = i;
                    break;
                }
            }
        } else {
            for (int i = 1; i <= getTokensCount(); i++) {
                if (getToken(i).getCurrentPos() >= 0) {
                    currentToken = i;
                    break;
                }
            }
        }
    }

}
