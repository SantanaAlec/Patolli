/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patolli.game.online.server.threads;

import java.awt.Color;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import patolli.game.Player;
import patolli.game.online.ClientUtils;
import patolli.game.online.server.Channel;
import patolli.game.online.server.Group;
import patolli.game.tokens.Token;
import patolli.game.utils.Authentication;
import patolli.game.utils.Console;
import patolli.game.utils.StringUtils;

public class SocketProtocol extends SocketThread {

    private Command command = new Command();

    private PlayerCommand playerCommand = new PlayerCommand();

    private PregameCommand pregameCommand = new PregameCommand();

    private GameCommand gameCommand = new GameCommand();

    private final Authentication auth = new Authentication();

    /**
     *
     * @param socket
     * @param player
     * @throws IOException
     */
    public SocketProtocol(final Socket socket, final Player player) throws IOException {
        super(socket, player);
        command = new Command();
    }

    /**
     *
     * @param input
     * @return
     */
    public boolean validateCommand(final String input) {
        return input.substring(0, 1).equals("/");
    }

    /**
     *
     * @param syntaxes
     * @param index
     * @return
     */
    private String getSyntax(final String[] syntaxes, final int index) {
        if (index < 0) {
            return "";
        }

        if (syntaxes.length == 0) {
            return "";
        }

        if (index >= syntaxes.length) {
            return "";
        }

        return syntaxes[index];
    }

    /**
     *
     * @param msg
     * @throws IOException
     */
    @Override
    public void execute(final byte[] msg) throws IOException {
        final String input = new String(msg);

        if (validateCommand(input)) {
            executeCommand(input);
        } else {
            if (getChannel() != null) {
                SocketStreams.sendTo(getChannel(), getPlayer().getName() + ": " + input);
            } else if (getGroup() != null) {
                // send messages
                SocketStreams.sendTo(getGroup(), getPlayer().getName() + ": " + input);
            }
        }

        Console.WriteLine("SocketProtocol", getPlayer().getName() + ": " + input);
    }

    /**
     *
     * @param message
     */
    @Override
    public void executeCommand(final String message) {
        final String[] syntaxes = message.split("\\s+");
        final String execute = syntaxes[0];

        switch (execute) {
            case "/help" -> {
                command.help();
            }
            case "/?" -> {
                command.help();
            }
            case "/leavegroup" -> {
                command.leaveGroup();
            }
            case "/leavechannel" -> {
                command.leaveChannel();
            }
            case "/joinchannel" -> {
                command.joinChannel(getSyntax(syntaxes, 1), getSyntax(syntaxes, 2));
            }
            case "/disconnect" -> {
                command.disconnect();
            }
            case "/joingroup" -> {
                command.joinGroup(getSyntax(syntaxes, 1), getSyntax(syntaxes, 2));
            }
            case "/creategroup" -> {
                command.createGroup(getSyntax(syntaxes, 1), getSyntax(syntaxes, 2));
            }
            case "/createchannel" -> {
                command.createChannel(getSyntax(syntaxes, 1), getSyntax(syntaxes, 2));
            }
            case "/kick" -> {
                command.kick(getSyntax(syntaxes, 1));
            }
            case "/ban" -> {
                command.ban(getSyntax(syntaxes, 1));
            }
            case "/op" -> {
                command.op(getSyntax(syntaxes, 1));
            }
            case "/deop" -> {
                command.deop(getSyntax(syntaxes, 1));
            }
            case "/list" -> {
                command.list();
            }

            // Player
            case "/setname" -> {
                playerCommand.setPlayerName(getSyntax(syntaxes, 1));
            }
            case "/setcolor" -> {
                playerCommand.setColor(getSyntax(syntaxes, 1));
            }

            // Pregame
            case "/startgame" -> {
                pregameCommand.startGame();
            }
            case "/stopgame" -> {
                pregameCommand.stopGame();
            }
            case "/setsquares" -> {
                pregameCommand.setSquares(getSyntax(syntaxes, 1));
            }
            case "/settriangles" -> {
                pregameCommand.setTriangles(getSyntax(syntaxes, 1));
            }
            case "/setbet" -> {
                pregameCommand.setBet(getSyntax(syntaxes, 1));
            }
            case "/setmaxtokens" -> {
                pregameCommand.setMaxTokens(getSyntax(syntaxes, 1));
            }

            // Game
            case "/play" -> {
                gameCommand.play(getSyntax(syntaxes, 1));
            }

            // Unknown
            default -> {
                command.unknown(getSyntax(syntaxes, 0));
            }
        }
    }

    public class Command {

        /**
         *
         */
        public Command() {
        }

        /**
         *
         */
        public void help() {
            final StringBuilder sb = new StringBuilder();

            sb.append("List of commands:");

            sb.append("\n");

            sb.append("/help");
            sb.append(", ");
            sb.append("/leavegroup");
            sb.append(", ");
            sb.append("/leavechannel");
            sb.append(", ");
            sb.append("/joinchannel");
            sb.append(", ");
            sb.append("/disconnect");
            sb.append(", ");
            sb.append("/joingroup");
            sb.append(", ");
            sb.append("/creategroup");
            sb.append(", ");
            sb.append("/createchannel");
            sb.append(", ");
            sb.append("/kick");
            sb.append(", ");
            sb.append("/ban");
            sb.append(", ");
            sb.append("/op");
            sb.append(", ");
            sb.append("/deop");
            sb.append(", ");
            sb.append("/list");

            sb.append("\n");

            // Player
            sb.append("/setname");
            sb.append(", ");
            sb.append("/setcolor");

            sb.append("\n");

            // Pregame
            sb.append("/startgame");
            sb.append(", ");
            sb.append("/stopgame");
            sb.append(", ");
            sb.append("/setsquares");
            sb.append(", ");
            sb.append("/settriangles");
            sb.append(", ");
            sb.append("/setbet");
            sb.append(", ");
            sb.append("/setmaxtokens");

            sb.append("\n");

            // Game
            sb.append("/play");

            SocketStreams.send(getOuter(), sb.toString());
        }

        /**
         *
         */
        public void leaveGroup() {
            if (getChannel() != null) {
                SocketStreams.send(getOuter(), "You can't leave a group if you are in a channel");
                return;
            }

            if (getGroup() == null) {
                SocketStreams.send(getOuter(), "You are not currently in a group");
                return;
            }

            SocketStreams.sendTo(getGroup(), getOuter().getPlayer().getName() + " left the group");

            getGroup().kick(getOuter());
        }

        /**
         *
         */
        public void leaveChannel() {
            if (getChannel() == null) {
                SocketStreams.send(getOuter(), "You are not currently in a channel");
                return;
            }

            SocketStreams.sendTo(getGroup(), getOuter().getPlayer().getName() + " left the channel");

            getChannel().kick(getOuter());
        }

        /**
         *
         * @param argument
         * @param entry
         */
        public void joinChannel(final String argument, final String entry) {
            if (!StringUtils.isNumeric(argument)) {
                SocketStreams.send(getOuter(), "You didn't select a valid channel");
                return;
            }

            if (getGroup() == null) {
                SocketStreams.send(getOuter(), "You need to be in a group in order to select a channel");
                return;
            }

            if (getChannel() != null) {
                SocketStreams.send(getOuter(), "You are already in a channel");
                return;
            }

            if (retrieveChannels().isEmpty()) {
                SocketStreams.send(getOuter(), "This group has no channels");
                return;
            }

            int index = Integer.parseInt(argument);

            if (index < 0) {
                SocketStreams.send(getOuter(), "The channel you selected is invalid");
                return;
            }

            if (index >= retrieveChannels().size()) {
                SocketStreams.send(getOuter(), "The channel you selected is invalid");
                return;
            }

            Channel channel = retrieveChannels().get(index);

            if (ClientUtils.isBanned(channel.getBlacklist(), getOuter())) {
                SocketStreams.send(getOuter(), "You are banned from the channel");
                return;
            }

            if (channel.hasPassword()) {
                if (entry.isEmpty()) {
                    SocketStreams.send(getOuter(), "A password is required to join the channel");
                    return;
                }

                if (!auth.authenticate(entry.toCharArray(), channel.getPassword())) {
                    SocketStreams.send(getOuter(), "Wrong password");
                    return;
                }

                getGroup().addClientToChannel(channel, getOuter());
                SocketStreams.send(getOuter(), "Joined channel " + getChannel().getName());
                return;
            }

            getGroup().addClientToChannel(channel, getOuter());

            SocketStreams.sendTo(getChannel(), getOuter().getPlayer().getName() + " joined the channel");
        }

        /**
         *
         */
        public void disconnect() {
            getOuter().disconnect();
        }

        /**
         *
         * @param argument
         * @param entry
         */
        public void joinGroup(final String argument, final String entry) {
            if (!StringUtils.isNumeric(argument)) {
                SocketStreams.send(getOuter(), "You didn't select a valid group");
                return;
            }

            if (getGroup() != null) {
                SocketStreams.send(getOuter(), "You are already in a group");
                return;
            }

            if (retrieveGroups().isEmpty()) {
                SocketStreams.send(getOuter(), "No groups are available");
                return;
            }

            int index = Integer.parseInt(argument);

            if (index < 0) {
                SocketStreams.send(getOuter(), "You didn't select a valid group");
                return;
            }

            if (index >= retrieveGroups().size()) {
                SocketStreams.send(getOuter(), "You didn't select a valid group");
                return;
            }

            Group group = retrieveGroups().get(index);

            if (ClientUtils.isBanned(group.getBlacklist(), getOuter())) {
                SocketStreams.send(getOuter(), "You are banned from the group");
                return;
            }

            if (group.hasPassword()) {
                if (entry.isEmpty()) {
                    SocketStreams.send(getOuter(), "A password is required to join the group");
                    return;
                }

                if (!auth.authenticate(entry.toCharArray(), group.getPassword())) {
                    SocketStreams.send(getOuter(), "Wrong password");
                    return;
                }

                server.addClientToGroup(group, getOuter());
                SocketStreams.send(getOuter(), "Joined group " + getGroup().getName());
                return;
            }

            server.addClientToGroup(group, getOuter());

            SocketStreams.sendTo(getGroup(), getOuter().getPlayer().getName() + " joined the group");
        }

        /**
         *
         * @param name
         * @param password
         */
        public void createGroup(final String name, final String password) {
            if (name.isEmpty()) {
                SocketStreams.send(getOuter(), "A name is required for the group");
                return;
            }

            if (!password.isEmpty()) {
                server.createGroup(getOuter(), name, password);
            } else {
                server.createGroup(getOuter(), name);
            }

            SocketStreams.send(getOuter(), "Created group " + getGroup().getName());
        }

        /**
         *
         * @param name
         * @param password
         */
        public void createChannel(final String name, final String password) {
            if (getGroup() == null) {
                SocketStreams.send(getOuter(), "You need to be in a group to create a channel");
                return;
            }

            if (name.isEmpty()) {
                SocketStreams.send(getOuter(), "A name is required for the channel");
                return;
            }

            Channel channel;

            if (!password.isEmpty()) {
                channel = getGroup().createChannel(getOuter(), name, password);
            } else {
                channel = getGroup().createChannel(getOuter(), name);
            }

            if (channel != null) {
                setChannel(channel);
                SocketStreams.send(getOuter(), "Created channel " + getChannel().getName());
            }
        }

        /**
         *
         * @param argument
         */
        public void kick(final String argument) {
            if (!StringUtils.isNumeric(argument)) {
                SocketStreams.send(getOuter(), "You need to select the index of a player");
                return;
            }

            if (getGroup() == null && getChannel() == null) {
                SocketStreams.send(getOuter(), "You need at least to be in a group to kick a player");
                return;
            }

            int index = Integer.parseInt(argument);

            if (getChannel() != null) {
                if (!ClientUtils.isOperator(getChannel().getOperators(), getOuter())) {
                    SocketStreams.send(getOuter(), "You are not an operator of this channel");
                    return;
                }

                SocketThread client = getChannel().getClients().get(index);

                getChannel().kick(client);

                SocketStreams.send(client, "You have been kicked from the channel");

                SocketStreams.sendTo(getChannel(), client.getPlayer().getName() + " was kicked from channel");
                return;
            }

            if (!ClientUtils.isOperator(getGroup().getOperators(), getOuter())) {
                SocketStreams.send(getOuter(), "You are not an operator of this group");
                return;
            }

            SocketThread client = getGroup().getClients().get(index);

            getGroup().kick(client);

            SocketStreams.send(client, "You have been kicked from the channel");

            SocketStreams.sendTo(getGroup(), client.getPlayer().getName() + " was kicked from channel");
        }

        /**
         *
         * @param argument
         */
        public void ban(final String argument) {
            if (!StringUtils.isNumeric(argument)) {
                SocketStreams.send(getOuter(), "You need to select the index of a player");
                return;
            }

            if (getGroup() == null && getChannel() == null) {
                SocketStreams.send(getOuter(), "You need at least to be in a group to ban a player");
                return;
            }

            int index = Integer.parseInt(argument);

            if (getChannel() != null) {
                if (!ClientUtils.isOperator(getChannel().getOperators(), getOuter())) {
                    SocketStreams.send(getOuter(), "You are not an operator of this channel");
                    return;
                }

                SocketThread client = getChannel().getClients().get(index);

                getChannel().ban(client);

                SocketStreams.send(client, "You have been banned from the channel");

                SocketStreams.sendTo(getChannel(), client.getPlayer().getName() + " was banned from channel");
                return;
            }

            if (!ClientUtils.isOperator(getGroup().getOperators(), getOuter())) {
                SocketStreams.send(getOuter(), "You are not an operator of this group");
                return;
            }

            SocketThread client = getGroup().getClients().get(index);

            getGroup().ban(client);

            SocketStreams.send(client, "You have been banned from the group");

            SocketStreams.sendTo(getGroup(), client.getPlayer().getName() + " was banned from group");
        }

        /**
         *
         * @param argument
         */
        public void op(final String argument) {
            if (!StringUtils.isNumeric(argument)) {
                SocketStreams.send(getOuter(), "Argument is not valid");
                return;
            }

            if (getGroup() == null && getChannel() == null) {
                SocketStreams.send(getOuter(), "You need to be at least in a group for that");
                return;
            }

            int index = Integer.parseInt(argument);

            if (getChannel() != null) {
                if (!ClientUtils.isOperator(getChannel().getOperators(), getOuter())) {
                    SocketStreams.send(getOuter(), "You are not an operator of this channel");
                    return;
                }

                SocketThread client = getChannel().getClients().get(index);

                if (ClientUtils.isOperator(getChannel().getOperators(), client)) {
                    SocketStreams.send(getOuter(), client.getPlayer().getName() + " is already an operator");
                    return;
                }

                getChannel().op(client);

                SocketStreams.send(getOuter(), client.getPlayer().getName() + " is now an operator");

                SocketStreams.send(client, "You are now an operator");
                return;
            }

            if (!ClientUtils.isOperator(getGroup().getOperators(), getOuter())) {
                SocketStreams.send(getOuter(), "You are not an operator of this group");
                return;
            }

            SocketThread client = getGroup().getClients().get(index);

            SocketStreams.send(getOuter(), client.getPlayer().getName() + " is now an operator");

            getGroup().op(client);

            SocketStreams.send(getOuter(), client.getPlayer().getName() + " is now an operator");

            SocketStreams.send(client, "You are now an operator");
        }

        /**
         *
         * @param argument
         */
        public void deop(final String argument) {
            if (!StringUtils.isNumeric(argument)) {
                SocketStreams.send(getOuter(), "Argument is not valid");
                return;
            }

            if (getGroup() == null && getChannel() == null) {
                SocketStreams.send(getOuter(), "You need to be at least in a group for that");
                return;
            }

            int index = Integer.parseInt(argument);

            if (getChannel() != null) {
                if (!ClientUtils.isOperator(getChannel().getOperators(), getOuter())) {
                    SocketStreams.send(getOuter(), "You are not an operator of this channel");
                    return;
                }

                SocketThread client = getChannel().getClients().get(index);

                if (!ClientUtils.isOperator(getChannel().getOperators(), client)) {
                    SocketStreams.send(getOuter(), client.getPlayer().getName() + " is not an operator");
                    return;
                }

                getChannel().deop(client);

                SocketStreams.send(getOuter(), client.getPlayer().getName() + " is no longer an operator");

                SocketStreams.send(client, "You are no longer an operator");
                return;
            }

            if (!ClientUtils.isOperator(getGroup().getOperators(), getOuter())) {
                SocketStreams.send(getOuter(), "You are not an operator of this group");
                return;
            }

            SocketThread client = getGroup().getClients().get(index);

            getGroup().deop(client);

            SocketStreams.send(getOuter(), client.getPlayer().getName() + " is no longer an operator");

            SocketStreams.send(client, "You are no longer an operator");
        }

        /**
         *
         */
        public void list() {
            if (getGroup() == null) {
                SocketStreams.send(getOuter(), "You need to be in a group first");
                return;
            }

            final StringBuilder sb = new StringBuilder();

            if (getChannel() != null) {
                if (getChannel().getClients().isEmpty()) {
                    return;
                }

                for (SocketThread client : getChannel().getClients()) {
                    sb.append(client.getPlayer().getName()).append(", ");
                }

                sb.delete(sb.length() - 2, sb.length());

                SocketStreams.send(getOuter(), sb.toString());
                return;
            }

            if (getGroup().getClients().isEmpty()) {
                return;
            }

            for (SocketThread client : getGroup().getClients()) {
                sb.append(client.getPlayer().getName()).append(", ");
            }

            sb.delete(sb.length() - 2, sb.length());

            SocketStreams.send(getOuter(), sb.toString());
        }

        public void unknown(final String command) {
            SocketStreams.send(getOuter(), "Unknown command: " + command);
        }

        /**
         *
         * @return
         */
        public List<Group> retrieveGroups() {
            return server.getGroups();
        }

        /**
         *
         * @return
         */
        public List<Channel> retrieveChannels() {
            return getGroup().getChannels();
        }

        /**
         *
         * @return
         */
        private SocketProtocol getOuter() {
            return SocketProtocol.this;
        }

    }

    public class PlayerCommand {

        /**
         *
         * @param name
         */
        public void setPlayerName(final String name) {
            getPlayer().setName(name);
            SocketStreams.send(getOuter(), "Name set to " + getPlayer().getName());
        }

        /**
         *
         * @param rgb
         */
        public void setColor(final String rgb) {
            if (!StringUtils.isValidHexaCode(rgb)) {
                SocketStreams.send(getOuter(), "You need to specify a color with the hex format #XXXXXX");
                return;
            }

            getPlayer().setColor(Color.decode(rgb));
            SocketStreams.send(getOuter(), "Color set to " + Integer.toHexString(getPlayer().getColor().getRGB()));
        }

        /**
         *
         * @return
         */
        private SocketProtocol getOuter() {
            return SocketProtocol.this;
        }

    }

    public class PregameCommand {

        /**
         *
         */
        public void startGame() {
            if (getChannel() == null) {
                SocketStreams.send(getOuter(), "You need to be in a channel to start a game");
                return;
            }

            if (!ClientUtils.isOperator(getChannel().getOperators(), getOuter())) {
                SocketStreams.send(getOuter(), "You need to be an operator in order to start the game");
                return;
            }

            getChannel().startGame();
        }

        /**
         *
         */
        public void stopGame() {
            if (getChannel() == null) {
                SocketStreams.send(getOuter(), "You need to be in a channel for that");
                return;
            }

            if (!ClientUtils.isOperator(getChannel().getOperators(), getOuter())) {
                SocketStreams.send(getOuter(), "Only an operator can stop the game");
                return;
            }

            getChannel().stopGame();
        }

        /**
         *
         * @param string
         */
        public void setSquares(final String string) {
            if (getChannel() == null) {
                SocketStreams.send(getOuter(), "You need to be in a channel to change game settings");
                return;
            }

            if (string.isEmpty()) {
                SocketStreams.send(getOuter(), "You need to specify the number of squares for the board");
                return;
            }

            if (!StringUtils.isNumeric(string)) {
                SocketStreams.send(getOuter(), "Argument is not valid");
                return;
            }

            final int squares = Integer.parseInt(string);

            if (squares <= 0) {
                SocketStreams.send(getOuter(), "Number of squares must not be 0 and must be positive");
                return;
            }

            getChannel().getPregame().getSettings().setSquares(squares);

            SocketStreams.sendTo(getChannel(), "Squares set to " + getChannel().getPregame().getSettings().getSquares());
        }

        /**
         *
         * @param string
         */
        public void setTriangles(final String string) {
            if (getChannel() == null) {
                SocketStreams.send(getOuter(), "You need to be in a channel to change game settings");
                return;
            }

            if (string.isEmpty()) {
                SocketStreams.send(getOuter(), "You need to specify the number of triangles for the board");
                return;
            }

            if (!StringUtils.isNumeric(string)) {
                SocketStreams.send(getOuter(), "Argument is not valid");
                return;
            }

            final int triangles = Integer.parseInt(string);

            if (triangles <= 0) {
                SocketStreams.send(getOuter(), "Number of triangles must not be 0 and must be positive");
                return;
            }

            getChannel().getPregame().getSettings().setTriangles(triangles);

            SocketStreams.sendTo(getChannel(), "Triangles set to " + getChannel().getPregame().getSettings().getTriangles());
        }

        /**
         *
         * @param string
         */
        public void setBet(final String string) {
            if (getChannel() == null) {
                SocketStreams.send(getOuter(), "You need to be in a channel to change game settings");
                return;
            }

            if (string.isEmpty()) {
                SocketStreams.send(getOuter(), "You need to set a valid bet");
                return;
            }

            if (!StringUtils.isNumeric(string)) {
                SocketStreams.send(getOuter(), "Argument is not valid");
                return;
            }

            final int bet = Integer.parseInt(string);

            if (bet < 0) {
                SocketStreams.send(getOuter(), "Bet must not be 0 and must be positive");
                return;
            }

            getChannel().getPregame().getSettings().setBet(bet);

            SocketStreams.sendTo(getChannel(), "Bet set to " + getChannel().getPregame().getSettings().getBet());
        }

        /**
         *
         * @param string
         */
        public void setMaxTokens(final String string) {
            if (getChannel() == null) {
                SocketStreams.send(getOuter(), "You need to be in a channel to change game settings");
                return;
            }

            if (string.isEmpty()) {
                SocketStreams.send(getOuter(), "You need to set a valid amount of tokens");
                return;
            }

            if (!StringUtils.isNumeric(string)) {
                SocketStreams.send(getOuter(), "Argument is not valid");
                return;
            }

            final int maxTokens = Integer.parseInt(string);

            if (maxTokens < 0) {
                SocketStreams.send(getOuter(), "Max tokens must not be 0 and must be positive");
                return;
            }

            getChannel().getPregame().getSettings().setMaxTokens(maxTokens);

            SocketStreams.sendTo(getChannel(), "Max tokens set to " + getChannel().getPregame().getSettings().getMaxTokens());
        }

        /**
         *
         * @return
         */
        private SocketProtocol getOuter() {
            return SocketProtocol.this;
        }

    }

    public class GameCommand {

        /**
         *
         * @param string
         */
        public void play(final String string) {
            if (getChannel() == null) {
                SocketStreams.send(getOuter(), "You need to be in a channel in order to play");
                return;
            }

            if (getChannel().getGame() == null) {
                SocketStreams.send(getOuter(), "Game hasn't started");
                return;
            }

            if (getChannel().getGame().getCurrentClient().getPlayer() != getOuter().getPlayer()) {
                SocketStreams.send(getOuter(), "It's not your turn");
                return;
            }

            Token token = null;

            if (!string.isEmpty()) {
                if (StringUtils.isNumeric(string)) {
                    final int index = Integer.parseInt(string);

                    if (index >= 0) {
                        if (index < getPlayer().countTokens()) {
                            if (getPlayer().getToken(index).getCurrentPos() >= 0) {
                                token = getPlayer().getToken(index);
                            }
                        }
                    }
                }
            }

            getChannel().getGame().play(token);
        }

        /**
         *
         * @return
         */
        private SocketProtocol getOuter() {
            return SocketProtocol.this;
        }

    }

}
