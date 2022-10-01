package patolli;

import java.util.ArrayList;
import spaces.Space;
import spaces.CentralSpace;
import spaces.ExteriorSpace;
import spaces.SquareSpace;
import spaces.TriangleSpace;

/**
 *
 * @author Alec_
 */
public class Board {

    private final ArrayList<Player> players;
    private final ArrayList<Space> spaces = new ArrayList<>();

    public Board(final int squares, final int triangles, final ArrayList<Player> players) {
        this.players = players;
        createBoard(squares, triangles);
    }

    //Método que crea el tablero de Patolli
    private void createBoard(final int squares, final int triangles) {
        //total: 52 - triagnle: 16 - exterior: 8 - central: 4
        //Dividir en varios metodos
        for (int index = 0; index < 4; index++) {
            for (int side = 0; side < 2; side++) {
                for (int square = 0; square < squares; square++) {
                    spaces.add(new SquareSpace());
                }

                if (side == 0) {
                    for (int triangle = 0; triangle < triangles; triangle++) {
                        spaces.add(new TriangleSpace());
                    }
                }

                if (side < 1) {
                    for (int exterior = 0; exterior < 2; exterior++) {
                        spaces.add(new ExteriorSpace());
                    }

                    for (int triangle = 0; triangle < triangles; triangle++) {
                        spaces.add(new TriangleSpace());
                    }
                }
            }

            spaces.add(new CentralSpace());
        }
    }

//    public void regresarToken(Token token, Space space) {
//        if (space instanceof CentralSpace) {
//
//        } else {
//            //Si a la ficha que es actualmente dueña de la casilla le caen encima, devuelve al que le cayo encima
//            if (token.getOwner() !=) {
//
//            }
//        }
//    }
    
//    //Para cuando ya hay fichas adentro
//    public void moverToken(Token token) {
//        //Establecer quien esta lanzando los dados
//        switch (Patolli.lanzarDados(5)) {
//            case 0 ->
//                playersDemand(token);
//            case 1 ->
//                token.setPosition(token.getPosition() + 1); //Tratar de arreglar la posición para que funcione como Space
//            case 2 ->
//                token.setPosition(token.getPosition() + 2);
//            case 3 ->
//                token.setPosition(token.getPosition() + 3);
//            case 4 ->
//                token.setPosition(token.getPosition() + 4);
//            case 5 ->
//                token.setPosition(token.getPosition() + 10);
//            default ->
//                throw new AssertionError();
//        }
//    }
//
//    public void insertarToken(Player player) {
//        //Si tú como jugador no tienes fichas dentro, en tu primer tirada usa esto
//        //Establecer quien esta lanzando los dados
//        if (Patolli.lanzarDados(5) >= 1) {
//            //Ingresar ficha
//            Token token = new Token(player);
//            tokens.add(token);
//            //Establecer en la posición que debe iniciar
//        }
//    }

    public void playersDemand(Token token) {
        for (Player player : Patolli.getPlayers()) {
            if (player.equals(token.getOwner())) {
                player.setBag(player.getBag() - ((Patolli.getBet() * 2) * (Patolli.getPlayers().size() - 1)));
                if (player.getBag() <= 0) {
                    //Establecer que perdio el juego y ponerlo en gris pero que continue para los demás
                }
            } else {
                player.setBag(player.getBag() + ((Patolli.getBet() * 2)));
            }
        }
    }

    //NUEVOS MÉTODOS
    //Poner un token en el tablero, position -1 si lo eliminaron, -2 si salio por la meta
    public void insertToken(Token token, int position) {
        //Tambien podemos setearle al token su entrada en el juego (patolli) ya que nunca va a cambiar
        token.setInitialPosition(position);
        //En X posición agrega Y token
        spaces.get(position).insertToken(token);
    }

    //Este método borrar los tokens
    public void removeToken(Token token, int position) {
        //En X posición elimina Y token
        spaces.get(position).removeToken(token);
    }

    //MÉTODO PARA MOVER LOS TOKENS Y REGISTRARLOS DENTRO DEL TABLERO
    public void move(Token token, int position) {
        //Elimina el token de la posición previa y ponlo en la nueva posición
        
////////////        //Apliicar para que no se sobrepase la posición, que lo mueva del final del arreglo de vuelta al principio 
////////////        /**
////////////         * 
////////////         */
        
        //Obtenemos la posición en la que estaba
        //Removemos su antigua posición
        //spaces.get(token.getActualPosition()).removeToken(token);
        removeToken(token, position);
        //Establecemos el token en la nueva posición
        token.setActualPosition(position);
        spaces.get(position).insertToken(token);
    }

    //Método para sacer la ficha y por ende llego a la meta
    public void goalToken(Token token, int newPosition) {
        //Checar que venga de atras, que de donde viene es de atras de la inicial y que la de enfrente sea la inicial
        int last = token.getActualPosition();
        int goal = token.getInitialPosition();
        
        //Si la sig. posición sobrepasa el arreglo regresar al inicio del mismo arreglo
        if (newPosition > boardSize()) {
            if ((newPosition - boardSize()) >= goal) {
                token.setActualPosition(-2);
                spaces.get(newPosition).removeToken(token);
            }
        }
        //Checar si viene desde atras, osea le dio la vuelta al tablero desde la posición inicial
        if (last < goal && newPosition >= goal) {
            spaces.get(newPosition).removeToken(token);
            token.setActualPosition(-2);
        }
    }
    
    //Método para saber el tamaño del tablero
    public int boardSize() {
        return spaces.size();
    }
}
