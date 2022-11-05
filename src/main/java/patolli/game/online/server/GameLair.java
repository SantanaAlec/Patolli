/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patolli.game.online.server;

import java.util.ArrayList;
import java.util.List;
import net.kaw.dradacorus.online.ExtendableLair;
import net.kaw.dradacorus.online.IDragonServer;
import net.kaw.dradacorus.online.IKoboldSocket;
import net.kaw.dradacorus.online.utils.SocketHelper;
import patolli.game.Game;
import patolli.game.InvalidSettingsException;
import patolli.game.online.client.PlayerSocket;

public class GameLair extends ExtendableLair {

    private Game game;
    private boolean running = false;

    public GameLair(IDragonServer dragon, String name) {
        super(dragon, name);
        game = new Game(this);
    }

    public GameLair(IDragonServer dragon, String name, String password) {
        super(dragon, name, password);
        game = new Game(this);
    }

    public void startGame() {
        if (running) {
            SocketHelper.Output.sendTo(this, "There´s already a game in this lair");
            return;
        }

        List<PlayerSocket> players = new ArrayList<>();

        for (int i = 0; i < game.getPreferences().getMaxPlayers() && i < getKobolds().size(); i++) {
            players.add((PlayerSocket) getKobolds().get(i));
        }

        for (PlayerSocket player : players) {
            player.getPlayer().getBalance().set(game.getPreferences().getInitBalance());
        }

        game.getSettings().add(players);

        try {
            game.getPreferences().validate();
        } catch (InvalidSettingsException ex) {
            SocketHelper.Output.sendTo(this, ex.getMessage());
            return;
        }

        if (!game.init()) {
            SocketHelper.Output.sendTo(this, "Failed at creating game");
        }
    }

    public void stopGame() {
        if (!running) {
            SocketHelper.Output.sendTo(this, "There´s no game in this lair");
            return;
        }
        game = new Game(this);
        SocketHelper.Output.sendTo(this, "Game has stopped");
        running = false;

    }

    public Game getGame() {
        return game;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void kick(IKoboldSocket client) {
        if (game.getGameLayer().isRunning()) {
            game.getPlayerlist().remove((PlayerSocket) client);
        }

        super.kick(client);
    }

}
