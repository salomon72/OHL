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
    static int health;
    private ArrayList<Observer> observers;

    private int missile = -1;
    private boolean blinking;
    private long blinkTimer;

    public PHASE getphase() {
        throw new UnsupportedOperationException("Not implement!");
    }
    private OPERATION cando = OPERATION.ALL;

    public OPERATION canDo() {
        return cando;
    }

    public int getMyType() {
        return 1;
    }

    public int get() {
        return health;
    }

    public int getDamage() {
        return 2;
    }

    public Ship(float x, float y) {
        String imagePath = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        Image i = getImage(imagePath + separator + "images" + separator
                + "playerShip.png");
        this.setAttributes(i, GameData.MAXHEALTH);
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
        return x + 47;
    }

    @Override
    public float getYofMissileShoot() {
        return y + 39;
    }

    @Override
    public void render(Graphics g) {
        if (blinking) {
            long elapsed = (System.nanoTime() - blinkTimer) / 1000000;
            if (elapsed / 100 % 2 == 0) {
                return;
            }
        }
        g.drawImage(playerImage, (int) x, (int) y, null);
    }

    @Override
    public void update() {

        if (blinking) {
            long elapsed = (System.nanoTime() - blinkTimer) / 1000000;
            if (elapsed > 1000) {
                blinking = false;
            }
        }

        // set boundaries of the ship (player):
        // on X axis
        if (x <= GamePanel.WIDTH - playerImage.getWidth(null) / 2) {
            x = GamePanel.WIDTH - playerImage.getWidth(null) / 2;
        }

        if (x > GamePanel.PWIDTH + playerImage.getWidth(null) / 2) {
            x = GamePanel.PWIDTH + playerImage.getWidth(null) / 2;
        }

        // on Y axis
        if (y < GamePanel.HEIGHT - playerImage.getHeight(null) / 2) {
            y = GamePanel.HEIGHT - playerImage.getHeight(null) / 2;
        }

        if (y > GamePanel.PHEIGHT - playerImage.getHeight(null) / 2) {
            y = GamePanel.PHEIGHT - playerImage.getHeight(null) / 2;
        }
    }

    @Override
    public int getState() {
        return state;
    }

    @Override
    public Rectangle collision() {
        return new Rectangle((int) x, (int) y + playerImage.getHeight(null) / 5,
                playerImage.getWidth(null), playerImage.getHeight(null) / 2);
    }

    @Override
    public int isMissile() {
        return missile;
    }

    @Override
    public void setMissile(int m) {
        missile = 11;
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
        if (blinking) {
            return; // don't do anything if blinking 
        }
        health -= i;
        if (health == 0) {
            state = GameFigure.STATE_DONE;;
        }

        if (i > 0) {
            blinking = true;
            blinkTimer = System.nanoTime();
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

    @Override
    public void setState(int s) {
        this.state = s;
    }

}
