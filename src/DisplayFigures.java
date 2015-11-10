
import java.awt.Graphics;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author davidalba
 */
public interface DisplayFigures {
   
    public void render(Graphics g);//draws the figure on the GamePanel
    
    public float getXcoor();

    public float getYcoor();
    
    public int get();
    
     public void update();
}
