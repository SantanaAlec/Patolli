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
    private ArrayList <Token> tokens; //Lista de tokens en partida, max 6
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

    //Métodos de utilidad
    //Creo un token que se va a meter al tablero y le asigno jugador
    public void addToken(Token token){
        tokens.add(token);
    }
    
    //Si pierde un token en las casillas centrales
    public void removeToken(Token token){
        tokens.remove(token);
    }
    
    //Cuantas fichas tengo en el tablero
    public int tokensInGame(){
        return tokens.size();
    }
    
    //Verificar si perdio el juego
    public boolean bagIsEmpty(){
        return bag <= 0;
    }
}
