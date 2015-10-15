/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author davidalba
 */

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public interface TutorialGameFigures {
    
    public void render(Graphics g);
    
    public void update();//updates position, for use on GameFigures that move themselves(non-player figures)

    public void updateState(int state);//changes state of GameFigure, states can be any string that indicates a change, 
    //such as the enemy is exploding so the state is STATE_EXPLODING which is an int 
    //that indicates exploding

    public int getState();//returns the state in int form

    public int isMissile();//returns an int value that is based on whether the object is a missle and what type of missle

    public int isPlayer();//return an int value that is based on whether the object is the player or not, a 0 indicates the player object

    public void Health(int i);//updates the health of the object, int i is subtracted from the current health of the object

    public float getXofMissileShoot();//X offset for missle to be fired, so that the missle comes out of a reasonable place on the object

    public float getYofMissileShoot();//X offset for missle to be fired, so that the missle comes out of a reasonable place on the object

    public float getXcoor();

    public float getYcoor();
    
    

    //void registerObserver(Observer o);//attach a score observer to the object

    //void removeObserver(Observer o);//remove a score observer from the object

    void notifyObservers(int amount);//notify all score observers attached to object, "amount" indicates the amount of score gained

    void setAttributes(Image i, int health);// sets the image and the initial health of the object

    public Rectangle collision();
    
    static final int STATE_TRAVELING = 1;
    static final int STATE_EXPLODING = 2;
    static final int STATE_DONE = 0;
    static final int STATE_ALIVE = 3;
    static final int STATE_DEAD = 4;
}
