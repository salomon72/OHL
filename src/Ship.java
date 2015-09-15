/*
 GameFigure for player ship, follows GameFigure interface
 */

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Ship implements GameFigure {
    boolean mouseable = true; // able to move the ship using the mouse to control the spaceship
                            // false : means user should use key directions
    
    Image playerImage;
    float x, y;
    int state = STATE_TRAVELING;
    private int health;
    private ArrayList<Observer> observers;

    public Ship(float x, float y) {
        String imagePath = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        Image i = getImage(imagePath + separator + "images" + separator
                + "ship1.png");
        this.setAttributes(i, 10);
        this.observers = new ArrayList<>();
        this.x = x;
        this.y = y;
    }

    public static Image getImage(String fileName) {
        Image image = null;
        try {
            image = ImageIO.read(new File(fileName));
        } catch (IOException ioe) {
            System.out.println("Error: Cannot open image:" + fileName);
            JOptionPane.showMessageDialog(null, "Error: Cannot open image:" + fileName);
        }
        return image;
    }

    // Missile shoot location
    @Override
    public float getXofMissileShoot() {
        return x + 30;
    }

    @Override
    public float getYofMissileShoot() {
        return y + 17;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(playerImage, (int) x, (int) y, null);
    }

    @Override
    public void update() {
        // set boundaries of the ship (player):
        
        // on X axis
        if(x < GamePanel.WIDTH)
            x = GamePanel.WIDTH;
        
        if(x > GamePanel.PWIDTH - playerImage.getWidth(null))
            x = GamePanel.PWIDTH - playerImage.getWidth(null);
        
        // on Y axis
        if(y < GamePanel.HEIGHT)
            y = GamePanel.HEIGHT;
        
        if(y > GamePanel.PHEIGHT - playerImage.getHeight(null))
            y = GamePanel.PHEIGHT - playerImage.getHeight(null);
    }

    @Override
    public int getState() {
        return state;
    }

    @Override
    public Rectangle collision() {
        return new Rectangle((int) x, (int) y, 40, 47);
    }

    @Override
    public int isMissile() {
        return 3;
    }

    @Override
    public void updateState(int state) {
        this.state = state;
    }

    @Override
    public int isPlayer() {
        return 0;
    }

    @Override
    public void Health(int i) {
        health -= i;
        if (health == 0) {
            state = 0;
        }
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(int amount) {
        for (Observer o : observers) {
            o.update(amount);
        }
    }

    @Override
    public void setAttributes(Image i, int health) {
        this.health = health;
        playerImage = i;
    }

    @Override
    public float getXcoor() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    @Override
    public float getYcoor() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

}
