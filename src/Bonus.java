
import java.awt.Color;
import java.awt.Graphics;

public class Bonus {
    static boolean active;
    static int powerLevel;
    static int power;
    static int [] requiredPower = {1,2,3,4,5};
    Color color;
    
    static boolean slowMotion = false;
    static long slowTimer;
    static long slowTimerDiff;
    static int slowLength = 6000;
    
    public Bonus(){
        active  = false;
        //powerLevel = 0;
        //power = 0;
        //color = Color.LIGHT_GRAY;
        color = new Color(144,14,242);
    }

    public void setActive(boolean active) {
        Bonus.active = active;
    }

    public boolean isActive() {
        return active;
    }
    
    
    public void render(Graphics g){
        g.setColor(color);        
        g.fillRect(40,490, power * 20, 20);        
        g.setColor(color.darker());
        //System.out.println("powerLEvel : " + powerLevel + " ===> ["+requiredPower[powerLevel]+"]" );
        
        for(int i=0; i < requiredPower[powerLevel]; i++){ // draw Bonus System.
            g.drawRect(40+20*i, 490, 20, 20);
            //System.out.println(" i = " + i);
            
        }
        
        if(slowMotion && slowTimer !=0 ){ // draw slowdown meter
            g.setColor(new Color(255,0,0));
            g.drawRect(GamePanel.PHEIGHT/2, 100, 300, 40);
            g.fillRect(GamePanel.PHEIGHT/2, 100, (int)(300 - 300.0 * slowTimerDiff / slowLength), 40);
            Stage2.move1 =(float) 0.5;
            Stage2.move2 = (float )1;
            
        } else {
            Stage2.move1 = 1;
            Stage2.move2 = 2;
        }      
    }
    
    public void update(){
        if(powerLevel==4){
            powerLevel = 0;
            return;
        }
        
        if(slowMotion){
            if(slowTimer != 0){
                slowTimerDiff = (System.nanoTime() - slowTimer) / 1000000;
                if(slowTimerDiff > slowLength){
                    slowTimer = 0;
                    slowMotion = false;
                    //Bonus.powerLevel = 0;               
                } 
            }
        }        
        
    }

    
    public static void increasePower(int i){
        if(powerLevel==3){ //upgrade ship when powerLevel is 3
            if(!Ship.upgrade){
                Ship.upgradeShip(); 
                powerLevel -= 3;                
            }                   
        }
        else if(powerLevel ==4){            
            return;
        }
        
        power += i;
        if(power > requiredPower[powerLevel]){
            power -= requiredPower[powerLevel];
            powerLevel++;
        }
        //System.out.println("Level :  " + powerLevel);
    }

    public static int getPowerLevel() {
        return powerLevel;
    }

    public static int getPower() {
        return power;
    }

    public static int getRequiredPower() {
        return requiredPower[powerLevel];
    }
    
    
    
}
