package patolli;

import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Alec_
 */
public class Player {

    private String name;
    //private String id;
    private Color color; // color de jugador/fichas
    private ArrayList <Token> tokens; //min 2, max 6
    private int bag; // Fondos del jugador para bet

    public Player(String name, Color color, int bag) {
        this.name = name;
        this.color = color;
        this.tokens = new ArrayList<>();
        this.bag = bag;
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

    public void setTokens(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    public int getBag() {
        return bag;
    }

    public void setBag(int bag) {
        this.bag = bag;
    }

    @Override
    public String toString() {
        return "Player{" + "nombre=" + name + ", color=" + color + ", fichas=" + tokens + ", bolsa=" + bag + '}';
    }
}
