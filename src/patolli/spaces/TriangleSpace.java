/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patolli.spaces;

import patolli.Patolli;
import patolli.Player;
import patolli.Token;

/**
 *
 * @author Alec_
 */
public class TriangleSpace implements Space {

    private Token currentToken;

    @Override
    public void fixBet() {
        //Establecer quien le pagara a los demás jugadores
        playersDemand();
    }

    @Override
    public void deleteToken() {
//        int lastPosition = currentToken.getPosition();
//        //Si al caer hay otra ficha te devuelve a tu antigua posición
//        if (currentToken.getOwner() != lastToken.getOwner()) {
//            //Si al que esta le caen encima, devuelve al nuevo
//        }
    }


    public void playersDemand() {
        for (Player player : Patolli.getPlayers()) {
            if (player.equals(currentToken.getOwner())) {
                player.setBag(player.getBag() - ((Patolli.getBet() * 2) * (Patolli.getPlayers().size() - 1)));
                if (player.getBag() <= 0) {
                   //Establecer que perdio el juego y ponerlo en gris pero que continue para los demás
                }
            } else {
                player.setBag(player.getBag() + ((Patolli.getBet() * 2)));
            }
        }
    }
    
        public Token getCurrentToken() {
        return currentToken;
    }

    //Establecemos que la casilla tiene un currentToken
    public void setCurrentToken(Token currentToken) {
        this.currentToken = currentToken;
    }

    //Establecemos el antiguo dueño de la casilla
}
