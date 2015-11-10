import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author davidalba
 */
public class DisplayGameData {
    final List<GameFigure> figures;
    
     public DisplayGameData() {

        figures = Collections.synchronizedList(new ArrayList<GameFigure>());
       //GameFigureFactory factory = new Factory("Ship");//example of player ship spawn
        //figures.add(factory.createFigure());
        
        //factory = new Factory("DisplayEnemy");
       //figures.add(factory.createFigure());
        
        

    }
     
     
    public void update() {
        
            }
            
     }
    

