package entities.spaces;

import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Alec_
 */
public class Player {

    private String name;
    private Color color; // color de jugador/fichas
    private ArrayList <Token> tokens; //Lista de tokens en partida, max 6
    private int bag; // Fondos del jugador para bet
    private int currentToken;

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

    public int getCurrentToken() {
        return currentToken;
    }

    public void setCurrentToken(int currentToken) {
        this.currentToken = currentToken;
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
    public boolean isBroke(){
        return bag <= 0;
    }
    
    //Método para aumentar el orden de la ficha
    public void nextToken(){
        currentToken++;
        if (currentToken > tokens.size()) {
            currentToken = 0;
        }
    }
    
    public Token getNextToken(){
        return tokens.get(currentToken);
    }
}
