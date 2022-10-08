/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
<<<<<<< Updated upstream:src/entities/Token.java
package entities.spaces;
=======
package patolli.game.tokens;

import patolli.game.Player;
>>>>>>> Stashed changes:src/patolli/game/tokens/Token.java

import entities.spaces.Space;

/**
 *
 * @author Alec_
 */
public class Token {

<<<<<<< Updated upstream:src/entities/Token.java
    private Player owner;
    //De donde sale y hasta donde tiene que llegar
    private int initialPosition;
    //Casilla donde esta
    private int actualPosition;
=======
    private final int index;

    private final int initialPos;
>>>>>>> Stashed changes:src/patolli/game/tokens/Token.java

    public Token() {
    }

<<<<<<< Updated upstream:src/entities/Token.java
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
=======
    public Token(final Player owner, final int index, final int initialPos) {
        this.owner = owner;
        this.index = index < 0 ? 0 : index;
        this.initialPos = initialPos;
        this.currentPos = initialPos;
>>>>>>> Stashed changes:src/patolli/game/tokens/Token.java
    }

    public void advancePos(final int advance) {
        currentPos += advance;
    }

    public void markAsFinished() {
        currentPos = -2;
    }

    public Player getOwner() {
        return owner;
    }

<<<<<<< Updated upstream:src/entities/Token.java
    public void setOwner(Player owner) {
        this.owner = owner;
=======
    public int getIndex() {
        return index;
    }

    public int getInitialPos() {
        return initialPos;
>>>>>>> Stashed changes:src/patolli/game/tokens/Token.java
    }

    public int getInitialPosition() {
        return initialPosition;
    }

    public void setInitialPosition(int initialPosition) {
        this.initialPosition = initialPosition;
    }

<<<<<<< Updated upstream:src/entities/Token.java
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
=======
>>>>>>> Stashed changes:src/patolli/game/tokens/Token.java
}
