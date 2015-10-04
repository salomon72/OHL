/*
 handles the management of the objects to be rendered and when to remove them. 
 Also controls the spawning of enemies and firing of enemies
 */

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameData {

    final List<GameFigure> figures;
    int count = 0;
    boolean FINISHED = false;
    boolean bossTime = false;
    boolean BOSS = false;
    private int enemiesSpawned;
    ScoreObserver score = new ScoreObserver();

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
                                Missile2 m = new Missile2(f.getXofMissileShoot(), f.getYofMissileShoot());
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

    private void addPower(int x, int y){
        PowerUp pw = new PowerUp(4);
        pw.setLocation(x,y+5);
        pw.setReleased(true);
        pw.setEnabled(true);
        pw.updateState(1);
        figures.add(pw);
    }
    public void update() {
        List<GameFigure> remove = new ArrayList<>();//list of all game figures marked for removal
        GameFigure h;
        GameFigure f;
        GameFigure g;
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
                    if (g.isMissile() == 0 && (f.isMissile() != 1 || f.isMissile() != 0)) { //
                        if (g.collision().intersects(f.collision()) && f.isPlayer() >= 1) {
                            g.updateState(0);
                            f.Health(1);//subtract 1 from GameFigure health
                        }
                        if (f.getState() == GameFigure.STATE_DONE) {//remove destroyed GameFigures
                            f.registerObserver(score);//update score upone each enemy destroyed
                            if (f.isPlayer() == 1) {
                                addPower((int) f.getXcoor(),(int) f.getYcoor());
                                f.notifyObservers(5);
                            }
                            if (f.isPlayer() == 2) {
                                f.notifyObservers(10);
                            }
                            if (f.isPlayer() == 3) {
                                f.notifyObservers(300);
                            }
                            remove.add(f);//remove destroyed enemies from figures list
                        }
                    }
                    if (g.isMissile() == 1 && (f.isMissile() != 1 || f.isMissile() != 0)) {//collision with player
                        if (g.collision().intersects(f.collision()) && f.isPlayer() == 0) {
                            g.updateState(0);
                            f.Health(1);//subtract 1 from GameFigure health
                        }
                        if (f.getState() == GameFigure.STATE_DONE) {
                            remove.add(f);
                        }
                    }
                    
                    if (g.isMissile() == 30 && (f.isMissile() != 30 || f.isMissile() != 0)) {//collision with power
                        System.out.println("is power UP");
                        if (g.collision().intersects(f.collision()) && f.isPlayer() == 0) {
                            g.updateState(0);
                            f.Health(1);//subtract 1 from GameFigure health
                        }
                        if (f.getState() == GameFigure.STATE_DONE) {
                            remove.add(f);
                        }
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
