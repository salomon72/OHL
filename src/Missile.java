/*
 GameFigure class for the missle that the player ship fires, follows GameFigure interface
 */

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Missile extends Ellipse2D.Float implements GameFigure {

    Point2D.Float target;
    private int state = STATE_TRAVELING;
    private static final int UNIT_TRAVEL_DISTANCE = 5;
    int health = 1;
    private final ArrayList<Observer> observers;
    int type;
    static String imagePath = System.getProperty("user.dir");
    static String separator = System.getProperty("file.separator");
    static Image missileImage = getImage(imagePath + separator + "images" + separator
            + "missile.png");

    public PHASE getphase() {
        return GameData.getphase();
    }
    private OPERATION cando = OPERATION.ALL;

    public OPERATION canDo() {
        return cando;
    }

    public int getDamage() {
        if (type == 0) {
            return 1;
        } else {
            return type;
        }
    }

    public Missile(float x, float y, int type) {
        this.observers = new ArrayList<>();
        setFrameFromCenter(x, y, x, y);
        this.type = type;
    }

    public int getMyType() {
        return type;
    }

    public int get() {
        return health;
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

    static public void upgradeMissile() {
        missileImage = getImage(imagePath + separator + "images" + separator
                + "missileUpgra.gif");
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(missileImage, (int) x, (int) y, null);
    }

    @Override
    public void update() {
        x += UNIT_TRAVEL_DISTANCE;
    }

    public void updateState() {
        if (state == STATE_TRAVELING) {
            double distance = target.distance(getCenterX(), getCenterY());
            boolean targetReached = distance <= 2.0;
            if (targetReached) {

            }
        }
    }

    @Override
    public int getState() {
        return state;
    }

    @Override
    public Rectangle collision() {
        return new Rectangle((int) x, (int) y, 23, 17);
    }

    @Override
    public int isMissile() {
        return 0;
    }

    @Override
    public void updateState(int state) {
        this.state = state;
    }

    @Override
    public int isPlayer() {
        return -1;
    }

    @Override
    public void Health(int i) {
        health -= i;
        if (health <= 0) {
            state = 0;
        }
    }

    @Override
    public float getXofMissileShoot() {
        return x;
    }

    @Override
    public float getYofMissileShoot() {
        return y;
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
        observers.stream().forEach((o) -> {
            o.update(amount);
        });
    }

    @Override
    public void setAttributes(Image i, int health) {
    }

    @Override
    public float getXcoor() {
        return x;
    }

    @Override
    public float getYcoor() {
        return y;
    }

    @Override
    public void setState(int s) {

    }

    @Override
    public void setMissile(int m) {

    }

    @Override
    public boolean containsPowerup() {
        return false;
    }
}
