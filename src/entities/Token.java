/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

public class Token {

    private final Player owner;

    private final int initialPos;

    private int currentPos;

    public Token(final Player owner, final int initialPos) {
        this.owner = owner;
        this.initialPos = initialPos;
        this.currentPos = initialPos;
    }

    public Player getOwner() {
        return owner;
    }

    public int getInitialPos() {
        return initialPos;
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(final int currentPos) {
        this.currentPos = currentPos;
    }

    public void advancePos(final int advance) {
        currentPos += advance;
    }

}
