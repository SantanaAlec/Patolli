/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patolli;

import patolli.spaces.Space;
import java.util.ArrayList;
import patolli.spaces.CentralSpace;
import patolli.spaces.ExteriorSpace;
import patolli.spaces.SquareSpace;
import patolli.spaces.TriangleSpace;

/**
 *
 * @author Alec_
 */
public class Board {
    private ArrayList<Player> players;
    private ArrayList<Space> spaces = new ArrayList<>();
    private ArrayList<Token> tokens = new ArrayList<>();

    public Board(ArrayList<Player> players) {
        this.players = players;
    }
    
    private void setBoard (){
        //total: 56 - triagnle: 16 - exterior: 8 - central: 4
//        for (int i = 1; i <= 56; i++) {
//            
//        }

        //Aspa 1-No centrales
        //Comunes - 1er lado
        Space space1 = new SquareSpace();
        Space space2 = new SquareSpace();
        Space space3 = new SquareSpace();
        //Triangulares - 1er lado
        Space space4 = new TriangleSpace();
        Space space5 = new TriangleSpace();
        //Exteriores
        Space space6 = new ExteriorSpace();
        Space space7 = new ExteriorSpace();
        //Triangulares - 2do lado
        Space space8 = new TriangleSpace();
        Space space9 = new TriangleSpace();
        //Comunes - 2do lado
        Space space10 = new SquareSpace();
        Space space11 = new SquareSpace();
        Space space12 = new SquareSpace();
        //Central
        Space space13 = new CentralSpace();
        
         //Aspa 2-No centrales
        //Comunes - 1er lado
        Space space14 = new SquareSpace();
        Space space15 = new SquareSpace();
        Space space16 = new SquareSpace();
        //Triangulares - 1er lado
        Space space17 = new TriangleSpace();
        Space space18 = new TriangleSpace();
        //Exteriores
        Space space19 = new ExteriorSpace();
        Space space20 = new ExteriorSpace();
        //Triangulares - 2do lado
        Space space21 = new TriangleSpace();
        Space space22 = new TriangleSpace();
        //Comunes - 2do lado
        Space space23 = new SquareSpace();
        Space space24 = new SquareSpace();
        Space space25 = new SquareSpace();
         //Central
        Space space26 = new CentralSpace();
        
        //Aspa 3-No centrales
        //Comunes - 1er lado
        Space space27 = new SquareSpace();
        Space space28 = new SquareSpace();
        Space space29 = new SquareSpace();
        //Triangulares - 1er lado
        Space space30 = new TriangleSpace();
        Space space31 = new TriangleSpace();
        //Exteriores
        Space space32 = new ExteriorSpace();
        Space space33 = new ExteriorSpace();
        //Triangulares - 2do lado
        Space space34 = new TriangleSpace();
        Space space35 = new TriangleSpace();
        //Comunes - 2do lado
        Space space36 = new SquareSpace();
        Space space37 = new SquareSpace();
        Space space38 = new SquareSpace();
        //Central
        Space space39 = new CentralSpace();
        
        //Aspa 4-No centrales
        //Comunes - 1er lado
        Space space40 = new SquareSpace();
        Space space41 = new SquareSpace();
        Space space42 = new SquareSpace();
        //Triangulares - 1er lado
        Space space43 = new TriangleSpace();
        Space space44 = new TriangleSpace();
        //Exteriores
        Space space45 = new ExteriorSpace();
        Space space46 = new ExteriorSpace();
        //Triangulares - 2do lado
        Space space47 = new TriangleSpace();
        Space space48 = new TriangleSpace();
        //Comunes - 2do lado
        Space space49 = new SquareSpace();
        Space space50 = new SquareSpace();
        Space space51 = new SquareSpace();
        //Central
        Space space52 = new CentralSpace();
    }

}
