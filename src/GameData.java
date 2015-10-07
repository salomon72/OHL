/*
 handles the management of the objects to be rendered and when to remove them. 
 Also controls the spawning of enemies and firing of enemies
 */

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
enum PHASE{ONE,TWO,THREE};

public class GameData {
   
    public static int MAXHEALTH = 30;
    final List<GameFigure> figures;
    int count = 0;
    boolean FINISHED = false;
    boolean bossTime = false;
    boolean BOSS = false;
    boolean isStarting = true;
    private int enemiesSpawned;
    private static PHASE phase;
    //public int health = MAXHEALTH;
    ScoreObserver score = new ScoreObserver();
    String separator = System.getProperty("file.separator");
    String imagePath = System.getProperty("user.dir");
    String explosion = imagePath + separator + "images" +separator +"BigExplosionSoundEffect.mp3";//load game over screen from image file
    String missilelaunch = imagePath + separator + "images" + separator+"SoundEffectMissileLaunch.mp3"; 
    String collide = imagePath + separator + "images" + separator+"collide.mp3"; 
    private boolean stateChanged = false;
    public void setStateChanged(boolean b)
    {
        stateChanged = b;
        try {
               Thread.sleep(1000);//interval in which enemies are spawned
           } catch (InterruptedException ex) {
               Logger.getLogger(GameData.class.getName()).log(Level.SEVERE, null, ex);
           }
        startSpawner();
        startFiring();//thread that has enemies fire
    }
    public int getHealth()
    {
        
        return score.health;
    }
    public static PHASE getphase()
    {
        return phase;
    }
    public static void setphase(PHASE p)
    {
        phase = p;
    }
    public GameData() {

        figures = Collections.synchronizedList(new ArrayList<GameFigure>());
        //spawn player ship and add to figures list here
        GameFigureFactory factory = new Factory("Ship");//example of player ship spawn
        figures.add(factory.createFigure());//adds player ship to figures
        startSpawner();//spawner thread for enemies
        startFiring();//thread that has enemies fire
    }

    public void spawnEnemy() {
        //use the GameFigureFactory to create new instances of enemy objects
        GameFigureFactory factory = new Factory("Enemy");
        figures.add(factory.createFigure());
    }

    public void spawnBoss() {
        //seperate method to spawn boss 
    }

    public final void startSpawner() {
        Thread enemySpawner;
        enemySpawner = new Thread(new Runnable() {
            @Override
            public void run() {
                while (count < 35) {//loop spawns enemies, add condition to stop the spawning of enemies
                    if(stateChanged)
                    {
                       stateChanged = false; 
                       isStarting = true;
                       //remove old enemy
                       //int index;
                       count = 0;
                       int i = figures.size()-1;
                       for(;i > 0;i--)
                       {
                           if(GameData.getphase()!= figures.get(i).getphase())
                           {
                               //remove enemy phase 1
                               figures.remove(i);
                              // System.out.println("Remove:"+i);
                           }        
                       }//
                       // System.out.println("Return!");
                       return;
                    }
                    spawnEnemy();
                    try {
                        Thread.sleep(750);//interval in which enemies are spawned
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GameData.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    count++;
                }
            }
        });
        enemySpawner.start();//starts the enemy spawner
    }

    public final void startFiring() {
        Thread enemyShoot;
        enemyShoot = new Thread(new Runnable() {
            @Override
            public void run() {
                GameFigure f;
                while (!FINISHED) {//FINISHED indicated whether the game is over or not. Enemies will fire if on the screen as long as the game is not over
                    for (int i = 0; i < figures.size(); i++) {
                        int temp = 1 + (int) (Math.random() * 100); //random generator for enemies to fire randomly
                        if (temp <= 90 && temp >= 30) {//if random number is between 90 and 30, enemy will fire
                            f = figures.get(i);//get enemy to fire
                            if (f.isPlayer() == 1 || f.isPlayer() == 2) {//check is the object collected from list is the player, do not fire if player.
                                Missile2 m = new Missile2(f.getXofMissileShoot(), f.getYofMissileShoot(),f.getMyType());
                                //System.out.println(f.getType());
                                ThreadPlayer.play(missilelaunch);
                                synchronized (figures) {
                                    figures.add(m);
                                }
                            }
                        }
                        try {
                            Thread.sleep(100);//interval between enemy fire
                        } catch (InterruptedException ex) {
                            Logger.getLogger(GameData.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
        );
        enemyShoot.start();//start the enemy shooting thread.
    }

    public void update() {
        List<GameFigure> remove = new ArrayList<>();//list of all game figures marked for removal
        GameFigure h;
        GameFigure f;
        GameFigure g;
        /*if(figures.size() > 0)
        {
            System.out.println("testing:x:"+figures.get(0).getXcoor()+",y:"+figures.get(0).getYcoor());
        }*/
        synchronized (figures) {
            enemiesSpawned = 0;//count of enemies on game panel
            for (int d = 0; d < figures.size(); d++) {
                h = figures.get(d);//get each GameFigure on game panel
                h.update();//update the movement of the GameFigure
                if (h.isPlayer() >= 1) {//check is GameFigure is player
                    enemiesSpawned++;//count each non-player GameFigure
                }
            }
            for (int j = 0; j < figures.size(); j++) {//collision detection loop, checks each GameFigure for collisions between figures.
                g = figures.get(j);
                if (g.getXcoor() >= 1296) {
                    remove.add(g);
                }
                if (g.getYcoor() >= 650) {
                    remove.add(g);
                }
                for (int i = 0; i < figures.size(); i++) {

                    f = figures.get(i);
                    if (f.getXofMissileShoot() < 0) {
                        f.updateState(0);
                    }
                    if (g.isMissile() == 0 && (f.isMissile() != 1 || f.isMissile() != 0)) {
                        if (g.collision().intersects(f.collision()) && f.isPlayer() >= 1) {
                            g.updateState(0);
                            f.Health(g.getDamage());//subtract 1 from GameFigure health
                            //enemy get damage
                            //System.out.println("who get damaged!,"+g.getMyType());
                            //get health of player
                        }
                        if (f.getState() == GameFigure.STATE_DONE) {//remove destroyed GameFigures
                            f.registerObserver(score);//update score upone each enemy destroyed
                            if (f.isPlayer() == 1) {
                                f.notifyObservers(5);
                                 figures.get(0).Health(-g.getDamage());//incrase health of player
                                ThreadPlayer.play(this.explosion);
                               // System.out.println("enemy die,explosion!");
                            }
                            if (f.isPlayer() == 2) {
                                f.notifyObservers(10);
                            }
                            if (f.isPlayer() == 3) {
                                f.notifyObservers(300);
                            }
                            remove.add(f);//remove destroyed enemies from figures list
                            ThreadPlayer.play(this.collide);
                            
                        }
                    }
                    if (g.isMissile() == 1 && (f.isMissile() != 1 || f.isMissile() != 0)) {
                        if (g.collision().intersects(f.collision()) && f.isPlayer() == 0) {
                            g.updateState(0);
                            f.Health(g.getDamage());//subtract 1 from GameFigure health
                            ThreadPlayer.play(this.collide);
                        }
                        if (f.getState() == GameFigure.STATE_DONE) {
                            remove.add(f);
                        }
                    }
                }
                score.health = figures.get(0).getHealth();
                //System.out.println("update1 Health:"+score.health);
               // System.out.println("Size:"+figures.size()+",enemy:"+enemiesSpawned);
                if(score.health <= 0)
                {
                    ThreadPlayer.play(this.explosion);
                    FINISHED = true;
                    System.out.println("Game end!Player health empty");
                    break;
                }
                if(enemiesSpawned >= 3 && this.isStarting)
                {
                    this.isStarting = false;
                }
                if(figures.size()>0 && !this.isStarting)
                {
                    //check enemy is over then finish
                   if(enemiesSpawned <= 0)
                   {
                       FINISHED = true;
                       System.out.println("Game end!Player win");
                   }
                }
                if (figures.get(0).isPlayer() == 0) {//if player is destoyed, end game
                } else {
                    FINISHED = true;
                }
                figures.removeAll(remove);//remove all GameFigures in remove list
            }
        }
    }
}
