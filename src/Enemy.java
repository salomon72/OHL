//import static GameFigure.STATE_TRAVELING;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Enemy implements GameFigure {

    Image enemyImage;
    float x, y;
    int w, h;
    int state = STATE_TRAVELING;
    private int health;
    private PHASE phase;
    private OPERATION cando = OPERATION.FLY;
    private int damage;

    public OPERATION canDo() {
        return cando;
    }

    public PHASE getphase() {
        return phase;
    }
    private ArrayList<Observer> observers;
    private int type;

    public int getDamage() {
        if (type == 0) {
            return 1;
        } else {
            return type;
        }
    }

    public int getMyType() {
        return type;
    }

    public int getHealth() {
        return health;
    }

    Enemy(float x, float y, int height, int weight) {
        String imagePath = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        Image i = null;
        phase = GameData.getphase();
        if (GameData.getphase() == PHASE.ONE) {
            i = getImage(imagePath + separator + "images" + separator
                    + "Enemy0.png");
            type = 0;
            damage = 1;
        } else if (GameData.getphase() == PHASE.TWO) {
            Random randomGenerator = new Random();
            int temp = randomGenerator.nextInt(4);
            temp++;
            i = getImage(imagePath + separator + "images" + separator
                    + "enemy" + Integer.toString(temp) + ".png");
            type = temp;
            damage = type;
        } else if (GameData.getphase() == PHASE.THREE) {
            Random randomGenerator = new Random();
            int temp = randomGenerator.nextInt(7);
            // temp++;
            i = getImage(imagePath + separator + "images" + separator
                    + "enemy" + Integer.toString(temp) + ".png");
            type = temp;
        }
        //if(type==0|| type == 2 ||type == 4||type == 5||type == 6||type == 7)
        if (type == 0 || type == 4 || type == 5 || type == 6 || type == 7) {
            cando = OPERATION.FLY;
        } else if (type == 1 || type == 3) {
            cando = OPERATION.SWIM;
        }
        this.setAttributes(i, 5);
        this.observers = new ArrayList<>();
        this.x = x;
        this.y = y;
        if (GameData.getphase() == PHASE.TWO) {
            if (this.canDo() == OPERATION.SWIM) {
                if (this.y < SPACE + MARGIN) {
                    this.y = SPACE + MARGIN;
                }
                //submarine tank
            } else if (this.canDo() == OPERATION.FLY) {
                //airplane
                if (this.y > SPACE - MARGIN) {
                    this.y = SPACE - MARGIN;
                }
            }
        }

        w = weight;
        h = height;
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

    @Override
    public void render(Graphics g) {
        g.drawImage(enemyImage, (int) x, (int) y, null);
    }

    @Override
    public void update() {
        Random rand = new Random();
        int dx = rand.nextInt(3) - 1;
        // move randomly in 4 direction
        this.x += 2 * dx;
        int dy = rand.nextInt(3) - 1;
        this.y += 2 * dy;
        if (GameData.getphase() == PHASE.TWO) {
            if (this.canDo() == OPERATION.SWIM) {
                if (this.y < SPACE + MARGIN) {
                    this.y = SPACE + MARGIN;
                }
                if (y > 590) {
                    y = 580;
                }
                //submarine tank
            } else if (this.canDo() == OPERATION.FLY) {
                //airplane
                if (this.y > SPACE - MARGIN) {
                    this.y = SPACE - MARGIN;
                }
                if (y <= 0) {
                    y = 5;
                }
            }
        }
    }

    @Override
    public void updateState(int state) {
        this.state = state;
    }

    @Override
    public int getState() {
        return state;
    }

    @Override
    public int isMissile() {
        return -1;
    }

    @Override
    public int isPlayer() {
        return 1;
    }

    @Override
    public void Health(int i) {
        health -= i;
        if (health == 0) {
            state = 0;
        }
    }

    @Override
    public float getXofMissileShoot() {
        // CHECK
        return x - 30;
    }

    @Override
    public float getYofMissileShoot() {
        return y + 17;
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
        enemyImage = i;
    }

    @Override
    public Rectangle collision() {
        // CHANGE SIZE OF IMAGE
        return new Rectangle((int) x, (int) y, 30, 44);
    }

}
