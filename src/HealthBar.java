/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author davidalba
 */
import javax.swing.JFrame;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author davidalba
 */
public class HealthBar extends JFrame implements GameFigure {
    //private double maxHP;
    //private ImageIcon health0 = new ImageIcon("heart.png");
    //private ImageIcon health1 = new ImageIcon("heart.png");
    //private ImageIcon health2 = new ImageIcon("heart.png");
    //private ImageIcon health3 = new ImageIcon("heart.png");

    Point2D.Float target;
    private int state = STATE_TRAVELING;
    private final ArrayList<Observer> observers;

    private GridLayout world;
    private Bar[] healthbar;
    private int health;
    private int size;
    private Image healthimage;
    private Image healthimagebreak;

    public PHASE getphase() {
        return GameData.getphase();
    }
    private OPERATION cando = OPERATION.ALL;

    public int getDamage() {
        return 1;
    }

    public OPERATION canDo() {
        return cando;
    }

    public HealthBar(int s, String name) {
        this.observers = new ArrayList<>();

        String imagePath = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");

        healthimage = getImage(imagePath + separator + "images" + separator
                + "heart.png");
        //super(1,name);
        add(new JLabel());

        //size = 5;
        int health = 5;
       //healthbar = new Bar[size];

        //world = new GridLayout(1,size,1,1);
        //this.setLayout(world);
    }

    public Image getHealthimage() {
        return healthimage;
    }

    public void setHealthimage(Image healthimage) {
        this.healthimage = healthimage;
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

    public int get() {

        return health;
    }

    public int getMyType() {
        return 0;
    }
    /*
     public void update(int num){
     health = num;
     for(int j=0; j< size; j++)
     healthbar[j].setIcon(health0);
     int length = num/GameData.MAXHEALTH+1;
     for(int i=0;i<length;i++){
     if(length<5 && length>=3) healthbar[i].setIcon(health3);
     if(length<3) healthbar[i].setIcon(health2);
     if(length>=5) healthbar[i].setIcon(health1);
     }
     }*/

    /* private void build(ImageIcon im){
     for(int i = 0; i < size; i++){
     healthbar[i] = new Bar(im);
     this.add(healthbar[i]);
     }
     }*/
    @Override
    public void render(Graphics g) {
        // g.drawImage(healthimage, (int) x, (int) y, null);
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateState(int state) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int isMissile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int isPlayer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float getYofMissileShoot() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float getXcoor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float getYcoor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registerObserver(Observer o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeObserver(Observer o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyObservers(int amount) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setAttributes(Image i, int health) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Rectangle collision() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void setFrameFromCenter(int x, int y, int x0, int y0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private class Bar extends JLabel {

        //Default Bar constructor.
        public Bar() {
            super();
        }

		// Bar constructor.
        //the ImageIcon used in the bar 
        public Bar(ImageIcon im) {
            super();
            this.setIcon(im);
        }
    }
}
