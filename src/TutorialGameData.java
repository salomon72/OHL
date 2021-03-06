
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TutorialGameData {

    final List<TutorialGameFigures> figures;
    int count = 0;
    boolean FINISHED = false;
    boolean bossTime = false;
    boolean BOSS = false;
    ScoreObserver score = new ScoreObserver();

    public TutorialGameData() {

        figures = Collections.synchronizedList(new ArrayList<TutorialGameFigures>());
        TutorialShip ship = new TutorialShip(100, 100);//example of player ship spawn
        figures.add(ship);//adds player ship to figures
        startSpawner();//spawner thread for enemies

    }

    public void spawnEnemy() {
        Random randomGenerator = new Random();
        int temp = randomGenerator.nextInt(500);
        TutorialEnemy enemy = new TutorialEnemy(1300, temp, 88, 88);
        figures.add(enemy);
    }

    public void spawnBoss() {

    }

    public final void startSpawner() {
        Thread enemySpawner;
        enemySpawner = new Thread(() -> {
            while (count < 35) {//loop spawns enemies, add condition to stop the spawning of enemies
                spawnEnemy();
                try {
                    Thread.sleep(750);//interval in which enemies are spawned
                } catch (InterruptedException ex) {
                    Logger.getLogger(GameData.class.getName()).log(Level.SEVERE, null, ex);
                }
                count++;
            }
        });
        enemySpawner.start();//starts the enemy spawner
    }

    public final void startFiring() {
        Thread enemyShoot;
        enemyShoot = new Thread(() -> {
            TutorialGameFigures f;
            while (!FINISHED) {//FINISHED indicated whether the game is over or not. Enemies will fire if on the screen as long as the game is not over
                for (int i = 0; i < figures.size(); i++) {
                    int temp = 1 + (int) (Math.random() * 100); //random generator for enemies to fire randomly
                    if (temp <= 90 && temp >= 30) {//if random number is between 90 and 30, enemy will fire
                        f = figures.get(i);//get enemy to fire
                        if (f.isPlayer() == 1 || f.isPlayer() == 2) {//check is the object collected from list is the player, do not fire if player.
                            TutorialMissile2 m = new TutorialMissile2(f.getXofMissileShoot(), f.getYofMissileShoot(), 1);
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
        });
        enemyShoot.start();//start the enemy shooting thread.
    }

    public void update() {
        List<TutorialGameFigures> remove = new ArrayList<>();//list of all game figures marked for removal
        TutorialGameFigures h;
        TutorialGameFigures f;
        TutorialGameFigures g;
        synchronized (figures) {
            for (int d = 0; d < figures.size(); d++) {
                h = figures.get(d);//get each GameFigure on game panel
                h.update();//update the movement of the GameFigure
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
                            if (f.isPlayer() == 1) {
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
