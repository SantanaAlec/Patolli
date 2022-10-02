/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities.spaces;

import entities.spaces.Space;

/**
 *
 * @author Alec_
 */
public class Token {

    private Player owner;
    //De donde sale y hasta donde tiene que llegar
    private int initialPosition;
    //Casilla donde esta
    private int actualPosition;

    public Token() {
    }

   public Token(Player owner, int position) {
        this.owner = owner;
        this.initialPosition = position;
    }
   
    //Establecer al dueño de la ficha
    public Token(Player owner) {
        this.owner = owner;
    }
    
    //Establecer casilla de entrada de la ficha
    public Token(int position) {
        this.initialPosition = position;
    }


    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public int getInitialPosition() {
        return initialPosition;
    }

    public void setInitialPosition(int initialPosition) {
        this.initialPosition = initialPosition;
    }

    public int getActualPosition() {
        return actualPosition;
    }

    public void setActualPosition(int actualPosition) {
        this.actualPosition = actualPosition;
    }

    //Posicionar token en el tablero (cuantas casillas avanza)
    public void advancePos(int count) {
        actualPosition += count;
    }
}
