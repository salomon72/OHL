
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.WritableRaster;
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
    private boolean containsPowerup = false;

    public OPERATION canDo() {
        return cando;
    }

    public PHASE getphase() {
        return phase;
    }
    private PowerUp power;
    int type;

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

    public int get() {
        return health;
    }
    private ArrayList<Observer> observers;

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
            int temp = randomGenerator.nextInt(3);
            temp += 1;
            i = getImage(imagePath + separator + "images" + separator
                    + "enemy" + Integer.toString(temp) + ".png");
            type = temp;
            damage = type;
        } else if (GameData.getphase() == PHASE.THREE) {
            Random randomGenerator = new Random();
            int temp = randomGenerator.nextInt(3);
            temp += 4;
            i = getImage(imagePath + separator + "images" + separator
                    + "enemy" + Integer.toString(temp) + ".png");
            type = temp;
        }
        this.setAttributes(i, 6);
        this.observers = new ArrayList<>();
        this.x = x;
        this.y = y;
        w = weight;
        h = height;
        power = new PowerUp(3);

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
        if (state != STATE_DEAD) {
            g.drawImage(enemyImage, (int) x, (int) y, null);
        }

        if (power.isEnabled() && power.isReleased()) {
            power.render(g);
        }
    }

    @Override
    public void update() {
        Random rand = new Random();
        int dx = rand.nextInt(3) - 1;
        int dy = rand.nextInt(3) - 1;
        if (this.type == 7)//boss
        {
            this.x += 2 * dx;
            this.y += 8 * dy;
        } else {//normal
            this.x += 2 * dx;
            this.y += 2 * dy;
        }

        if (GameData.getphase() == PHASE.TWO) {
            if (this.canDo() == OPERATION.SWIM) {
                if (this.y < SPACE + MARGIN) {
                    this.y = SPACE + MARGIN;
                }
                if (y > GameData.MAXHEIGHT) {
                    y = GameData.MAXHEIGHT;
                }
                //submarine tank
            } else if (this.canDo() == OPERATION.FLY) {
                //airplane
                if (this.y > SPACE - MARGIN) {
                    this.y = SPACE - MARGIN;
                }
                if (y <= GameData.MINHEIGHT) {
                    y = GameData.MINHEIGHT;
                }
            }
        } else if (GameData.getphase() == PHASE.THREE) {
            if (this.canDo() == OPERATION.SWIM) {
                if (this.y < GROUND + MARGIN) {
                    this.y = GROUND + MARGIN;
                }
                //submarine tank
            } else if (this.canDo() == OPERATION.FLY) {
                //airplane
                if (this.y > GROUND - MARGIN) {
                    this.y = GROUND - MARGIN;
                }
            } else {//all
                if (y <= GameData.MINHEIGHT) {
                    y = GameData.MINHEIGHT;
                }
                if (y > GameData.MAXHEIGHT) {
                    y = GameData.MAXHEIGHT;
                }
            }
        } else {
            if (y <= GameData.MINHEIGHT) {
                y = GameData.MINHEIGHT;
            }
            if (y > GameData.MAXHEIGHT) {
                y = GameData.MAXHEIGHT;
            }
        }
        if (health > 0) {
            power.setLocation((int) this.x, (int) this.y);
        } else {
            power.setReleased(true);
        }

        if (power.isEnabled() && power.getPower() != null) {
            power.update();
        }

    }
    
    private static BufferedImage colorImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        WritableRaster raster = image.getRaster();

        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                int[] pixels = raster.getPixel(xx, yy, (int[]) null);
                pixels[0] = 100;
                pixels[1] = 100;
                pixels[2] = 100;
                raster.setPixel(xx, yy, pixels);
            }
        }
        return image;
    }

    @Override
    public void updateState(int state) {
        this.state = state;
    }

    @Override
    public void setState(int s) {

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
        if (health <= 0) {
            state = STATE_DONE;
        }
    }

    @Override
    public float getXofMissileShoot() {
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

    @Override
    public void setMissile(int m) {

    }
    
    public void setPowerup(){
        FilteredImageSource filteredImageSource = new FilteredImageSource(enemyImage.getSource(), new ColorShift());
        enemyImage = Toolkit.getDefaultToolkit().createImage(filteredImageSource);
        containsPowerup = true;
    }
    
    @Override
    public boolean containsPowerup() {
        return containsPowerup;
    }

}
