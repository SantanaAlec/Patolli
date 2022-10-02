package patolli;

import entities.spaces.Token;
import java.util.ArrayList;
import entities.spaces.Space;
import entities.spaces.CentralSpace;
import entities.spaces.ExteriorSpace;
import entities.spaces.Player;
import entities.spaces.SquareSpace;
import entities.spaces.TriangleSpace;

/**
 *
 * @author Alec_
 */
public class Board {

    private static Board instance;

    /**
     * Singleton pattern to keep a single instance of this class program running
     *
     * @return The instance of the program is returned, if there's none a new
     * one is created
     */
    public static Board getInstance() {
        if (instance == null) {
            instance = new Board();
        }

        return instance;
    }

    private final ArrayList<Space> spaces = new ArrayList<>();

    private final int SQUARES_AMOUNT = 3, TRIANGLES_AMOUNT = 2;

    private Board() {
    }

    public void init() {
        createBoard();
    }

    private void createBoard() {
        resetBoard();

        for (int index = 0; index < 4; index++) {
            addBlade(SQUARES_AMOUNT, TRIANGLES_AMOUNT);

            addCenterSpace();
        }
    }

    private void addBlade(final int squares, final int triangles) {
        final int RIGHT = 0, LEFT = 1;
        for (int side = RIGHT; side < 2; side++) {
            addSquareSpaces(squares);

            if (side == RIGHT) {
                addTriangleSpaces(triangles);
                addExteriorSpace();
            } else if (side == LEFT) {
                addExteriorSpace();
                addTriangleSpaces(triangles);
            }
        }
    }

    private void addSquareSpaces(final int amount) {
        for (int index = 0; index < amount; index++) {
            spaces.add(new SquareSpace());
        }
    }

    private void addTriangleSpaces(final int amount) {
        for (int index = 0; index < amount; index++) {
            spaces.add(new TriangleSpace());
        }
    }

    private void addExteriorSpace() {
        spaces.add(new ExteriorSpace());
    }

    private void addCenterSpace() {
        spaces.add(new CentralSpace());
    }

    public int getBoardSize() {
        return spaces.size();
    }

    public void resetBoard() {
        this.spaces.clear();
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

        //Apliicar para que no se sobrepase la posición, que lo mueva del final del arreglo de vuelta al principio 
        //Si la posición llega a revasar el tamaño del tablero, llevalo al inicio del mismo
        if (position > boardSize()) {
            position = position - boardSize();
        }

        //Obtenemos la posición en la que estaba
        //Removemos su antigua posición
        //spaces.get(token.getActualPosition()).removeToken(token);
        removeToken(token, position);
        //Establecemos el token en la nueva posición
        token.setActualPosition(position);
        spaces.get(position).insertToken(token);
    }

    //Método para sacer la ficha y por ende llego a la meta
    public void goalToken(Token token, int nextPosistion) {
        //Checar que venga de atras, que de donde viene es de atras de la inicial y que la de enfrente sea la inicial
        int last = token.getActualPosition();
        int goal = token.getInitialPosition();

        //Si la sig. posición sobrepasa el arreglo regresar al inicio del mismo arreglo
        if (nextPosistion > boardSize()) {
            if ((nextPosistion - boardSize()) >= goal) {
                token.setActualPosition(-2);
                spaces.get(nextPosistion).removeToken(token);
            }
        }
        //Checar si viene desde atras, osea le dio la vuelta al tablero desde la posición inicial
        if (last < goal && nextPosistion >= goal) {
            spaces.get(nextPosistion).removeToken(token);
            token.setActualPosition(-2);
        }
    }

    public boolean isTokenAboutToWin(Token token, int nextPosistion) {
        //Checar que venga de atras, que de donde viene es de atras de la inicial y que la de enfrente sea la inicial
        int last = token.getActualPosition();
        int goal = token.getInitialPosition();

        //Si la sig. posición sobrepasa el arreglo regresar al inicio del mismo arreglo
        if (nextPosistion > boardSize()) {
            if ((nextPosistion - boardSize()) >= goal) {
                return true;
            }else{
                return false;
            }
        }
        //Checar si viene desde atras, osea le dio la vuelta al tablero desde la posición inicial
        if (last < goal && nextPosistion >= goal) {
            return true;
        }
        return false;
    }

    //Método para saber el tamaño del tablero
    public int boardSize() {
        return spaces.size();
    }

    /**
     * Método para limpiar tokens
     *
     * @param position
     */
    public void clearTokens(int position) {
        spaces.get(position).clearTokens();
    }

    public void removeTokensAtPos(final int pos) {
        getSpace(pos).clearTokens();
    }

    public Space getSpace(final int index) {
        return spaces.get(index);
    }

    public void removeTokensFromPlayer(final Player player) {
        for (Token token : player.getTokens()) {
            removeTokensAtPos(token.getActualPosition());
        }
    }
}
