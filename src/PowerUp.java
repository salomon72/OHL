
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

public class PowerUp implements GameFigure {

    private Rectangle pRect;
    private boolean enabled = true;
    private int x;
    private int y;
    private int type;
    private int state = STATE_TRAVELING;
    private Color color;
    private int radius;
    private boolean released;

    private int dx;
    private int dy;
    private double speed;
    private double rad;

    private int code = -1;

    public PowerUp(int type) {
        if (type > 1) {
            enabled = true;
            this.type = type;
            createPower();
        } else {
            enabled = false;
        }
        pRect = new Rectangle(x, y, radius, radius);
    }

    private void createPower() {
        if (type == 2) { // shield for 1 enemy's missile
            speed = 5;
            radius = 10;
            color = Color.ORANGE;
            code = 40;
        } else if (type == 3) { // 2 missiles @ once

            speed = 8;
            radius = 15;
            color = Color.BLUE;
            code = 41;
        } else if (type == 4) { // increase player health
            speed = 10;
            radius = 15;
            color = Color.GREEN;
            code = 42;
        } else if (type == 5) { // bonus
            speed = 15;
            radius = 15;
            color = Color.LIGHT_GRAY;
            code = 43;
        }

        speed = 10;
        double angle = Math.random() * 30 + 1;
        rad = Math.toRadians(angle);
        dx = (int) (Math.cos(rad) * speed);
        dy = (int) (Math.sin(rad) * speed);

    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setReleased(boolean released) {
        this.released = released;
    }

    public boolean isReleased() {
        return released;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
        if (pRect != null) {
            pRect.setLocation(x, y);
        }

    }

    public Rectangle getPower() {
        return pRect;
    }

    public void setPower(boolean b) {
        if (!b) {
            pRect = null;
            state = STATE_DONE;
        }
    }

    @Override
    public void render(Graphics g) {
        if (pRect != null && released) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(color);
            g2d.fillRect(pRect.x, pRect.y, pRect.width, pRect.height);
            g2d.setColor(color.darker());
            g2d.setStroke(new BasicStroke(4));
            g2d.drawRect(pRect.x, pRect.y, pRect.width, pRect.height);
            g2d.setStroke(new BasicStroke(2));
        }
    }

    @Override
    public void update() {
        if (pRect != null || state != STATE_DONE) {
            x += dx;
            y += dy;

            // set WIDTH boundaries
            if (x + radius > GamePanel.PWIDTH) {
                dx = -dx;
                x = GamePanel.PWIDTH - radius;
            } else if (x - radius < 0) {
                dx = -dx;
                x = radius;
            }
            // set HEIGHT boundaries
            if (y + radius > GamePanel.PHEIGHT) {
                dy = -dy;
                y = GamePanel.PHEIGHT - radius;
            } else if (y - radius < 0) {
                dy = -dy;
                y = radius;
            }
            pRect.setLocation((int) x - radius, (int) y - radius); // set the possition of the rectangle 
        }
    }

    @Override
    public int getState() {
        return state;
    }

    @Override
    public void updateState(int state) {
        this.state = state;
    }

    @Override
    public int isMissile() {
        if (type > 1) {
            return code;
        } else {
            return -1;
        }
    }

    @Override
    public void setMissile(int m) {

    }

    @Override
    public int isPlayer() {
        return 31;
    }

    @Override
    public void Health(int i) {

    }

    @Override
    public float getXofMissileShoot() {
        return 0;
    }

    @Override
    public float getYofMissileShoot() {
        return 0;
    }

    @Override
    public float getXcoor() {
        return pRect.x;
    }

    @Override
    public float getYcoor() {
        return pRect.y;
    }

    @Override
    public void registerObserver(Observer o) {
    }

    @Override
    public void removeObserver(Observer o) {
    }

    @Override
    public void notifyObservers(int amount) {
    }

    @Override
    public void setAttributes(Image i, int health) {

    }

    @Override
    public Rectangle collision() {
        return pRect;
    }

    @Override
    public void setState(int s) {

    }

    @Override
    public int getDamage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PHASE getphase() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OPERATION canDo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getMyType() {
        return 0;
    }

    @Override
    public int get() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean containsPowerup() {
        return false;
    }
}
