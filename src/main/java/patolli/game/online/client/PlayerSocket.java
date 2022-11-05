/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patolli.game.online.client;

import java.io.IOException;
import java.net.Socket;
import net.kaw.dradacorus.online.ExtendableKoboldSocket;
import net.kaw.dradacorus.online.IDragonServer;
import net.kaw.dradacorus.online.ILairActions;
import patolli.game.Player;
import patolli.game.online.server.GameLair;

public class PlayerSocket extends ExtendableKoboldSocket {

    private Player player;

    public PlayerSocket(IDragonServer dragon, ILairActions actions, Socket socket) throws IOException {
        super(dragon, actions, socket);
        this.player = new Player(getName());
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public GameLair getLair() {
        return (GameLair) super.getLair();
    }

    @Override
    public PatolliActions getActions() {
        return (PatolliActions) actions;
    }

}
