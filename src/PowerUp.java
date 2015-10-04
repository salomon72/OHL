
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
    
    private int speed;
    
    
    public PowerUp(int type){      
        
        if(type > 1){
            enabled = true;
            this.type = type;
            createPower();
            
        }
        else 
            enabled = false;
        
        pRect = new Rectangle(x, y, radius, radius);       
    }
    
    private void createPower(){        
        if(type == 2){ // additional life
            radius = 10;
            color = Color.ORANGE;
        }
        else if(type == 3){ // missile power up
            radius = 15;
            color = Color.BLUE;
        }
        else if(type == 4){ // launcher shield
            radius = 20;
            color = Color.GREEN;
        }
        
        else if(type == 5){ // launcher shield
            radius = 20;
            color = Color.BLUE;
        }
        
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
    
    public void setLocation(int x, int y){
        this.x = x;
        this.y = y;
        if(pRect != null)
            pRect.setLocation(x, y);
        
    }

    public Rectangle getPower() {
        return pRect;
    }
    public void setPower(boolean b) {
        if(!b){
            pRect = null;
            state = STATE_DONE;
        }
    }      

    @Override
    public void render(Graphics g) {
        if(pRect != null && released){
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
        if(pRect != null){
            x -= 5;
            pRect.setLocation(x, y);
            if (y + radius > GamePanel.PWIDTH) {
                state = STATE_DONE;
            }   
        }
        
    }

    @Override
    public int getState() {
      return state;
    }
    
    @Override
    public void updateState(int state) {
        
    }

    @Override
    public int isMissile() {
        return 30;
    }

    @Override
    public int isPlayer() {
        return 31;
    }

    @Override
    public void Health(int i) {
        return;
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
        return;
    }

    @Override
    public void removeObserver(Observer o) {
        return;
    }

    @Override
    public void notifyObservers(int amount) {
       return;
    }

    @Override
    public void setAttributes(Image i, int health) {
        
    }

    @Override
    public Rectangle collision() {
        return pRect;
    }
    
}
