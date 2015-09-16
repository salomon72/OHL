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
    private ArrayList<Observer> observers;

    Enemy(float x, float y, int height, int weight)
    {
        String imagePath = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        Image i = getImage(imagePath + separator + "images" + separator
                + "Enemy4.png");
        this.setAttributes(i, 10);
        this.observers = new ArrayList<>();
        this.x = x;
        this.y = y;
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
        this.x += 2*dx;
        int dy = rand.nextInt(3) - 1;
        this.y += 2*dy;
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
        return 2;
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
        return x + 30;
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
