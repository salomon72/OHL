/*
 handles the management of the objects to be rendered and when to remove them. 
 Also controls the spawning of enemies and firing of enemies
 */

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

enum PHASE {

    ONE, TWO, THREE
};

public class GameData {

    public static int MAXHEIGHT = 550;
    public static int MINHEIGHT = 20;
    public static int MAXENEMY = 35;

    public static int MAXHEALTH = 5;
    final List<GameFigure> figures;
    int count = 0;
    int countEnemy = 0;
    boolean FINISHED = false;
    boolean gameEnd = false;
    boolean bossTime = false;
    boolean BOSS = false;
    boolean isStarting = true;
    boolean isBossDied = false;
    boolean isBossCreated = false;
    private int enemiesSpawned;
    private static PHASE phase;
    ScoreObserver score = new ScoreObserver();
    String separator = System.getProperty("file.separator");
    String imagePath = System.getProperty("user.dir");
    String explosion = imagePath + separator + "images" + separator + "BigExplosionSoundEffect.mp3";//load game over screen from image file
    String missilelaunch = imagePath + separator + "images" + separator + "SoundEffectMissileLaunch.mp3";
    String collide = imagePath + separator + "images" + separator + "collide.mp3";
    private boolean stateChanged = false;
    public Thread Stage1Spawner;
    public Thread Stage2Spawner;
    public Thread Stage3Spawner;
    public Thread enemyShootThread;

    public GameData() throws IOException {

        figures = Collections.synchronizedList(new ArrayList<GameFigure>());
        //spawn player ship and add to figures list here
        GameFigureFactory factory = new Factory("Ship");//example of player ship spawn
        figures.add(factory.createFigure());//adds player ship to figures
        setStage1Spawner();//spawner thread for enemies
        setStage2Spawner();
        setStage3Spawner();
        Stage1Spawner.start();//starts the enemy spawner
        startFiring();//thread that has enemies fire
        enemyShootThread.start();
    }

    public void setStateChanged(int stage, boolean cutscene) throws IOException, InterruptedException {
        Stage1Spawner.interrupt();
        Stage2Spawner.interrupt();
        Stage3Spawner.interrupt();
        enemyShootThread.interrupt();
        removeEnemies();
        if (stage == 1) {
            setStage1Spawner();
            Stage1Spawner.start();
            startFiring();
            enemyShootThread.start();
        } else if (stage == 2) {
            setStage2Spawner();
            startFiring();
            enemyShootThread.start();
            if (!cutscene) {
                Stage2Spawner.start();
            }
        } else if (stage == 3) {
            setStage3Spawner();
            startFiring();
            enemyShootThread.start();
            if (!cutscene) {
                Stage3Spawner.start();
            }
        }
        if (cutscene) {
            Stage1Spawner.interrupt();
            Stage2Spawner.interrupt();
            Stage3Spawner.interrupt();
            removeEnemies();
        }
    }

    public void removeEnemies() {
        ArrayList<GameFigure> remove = new ArrayList<>();
        for (int i = 1; i < figures.size(); i++) {
            remove.add(figures.get(i));
        }
        figures.removeAll(remove);
    }

    public final void setStage1Spawner() throws IOException {
        final Stage1 stage1 = new Stage1();
        count = 0;
        Stage1Spawner = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean repeat = true;
                while (count < 28) {//loop spawns enemies
                    while (count < 6) {
                        if (Thread.interrupted()) {
                            return;
                        }
                        Enemy enemy = stage1.getEnemy1();
                        synchronized (figures) {
                            figures.add(enemy);
                        }
                        count++;
                        if (count == 6) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ex) {
                                return;
                            }
                        }
                    }
                    stage1.resetCount();
                    while (count >= 6 && count <= 15) {
                        if (Thread.interrupted()) {
                            return;
                        }
                        Enemy enemy = stage1.getEnemy2();
                        synchronized (figures) {
                            figures.add(enemy);
                        }
                        count++;
                        try {
                            Thread.sleep(750);
                        } catch (InterruptedException ex) {
                            return;
                        }
                    }
                    stage1.resetCount();
                    while (count >= 15 && count <= 21) {
                        if (Thread.interrupted()) {
                            return;
                        }
                        Enemy enemy = stage1.getEnemy4();
                        synchronized (figures) {
                            figures.add(enemy);
                        }
                        count++;
                        if (count == 22) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ex) {
                                return;
                            }
                        }
                    }
                    stage1.resetCount();
                    while (count >= 22 && count <= 27) {
                        if (Thread.interrupted()) {
                            return;
                        }
                        Enemy enemy = stage1.getEnemy3();
                        synchronized (figures) {
                            figures.add(enemy);
                        }
                        count++;
                        try {
                            Thread.sleep(900);
                        } catch (InterruptedException ex) {
                            return;
                        }
                        if (count == 28 && repeat == true) {
                            count = 6;
                            repeat = false;
                        }
                    }
                    countEnemy = MAXENEMY;
                }
            }
        });

    }

    public final void setStage2Spawner() throws IOException {
        final Stage2 stage2 = new Stage2();
        count = 0;
        countEnemy = 0;
        Stage2Spawner = new Thread(new Runnable() {
            @Override
            public void run() {
                while (countEnemy < MAXENEMY && !Thread.interrupted()) {//loop spawns enemies
                    while (count < 17) {
                        synchronized (figures) {
                            int temp = 1 + (int) (Math.random() * 2);
                            if (temp == 1) {
                                Enemy enemy = stage2.getEnemy1();
                                figures.add(enemy);
                            }
                            if (temp == 2) {
                                Enemy enemy = stage2.getEnemy2();
                                figures.add(enemy);
                            }
                        }
                        try {
                            Thread.sleep(750);//interval in which enemies are spawned
                        } catch (InterruptedException ex) {
                            return;
                        }
                        count++;
                    }
                    countEnemy = MAXENEMY;
                }
            }
        });

    }

    public final void setStage3Spawner() throws IOException {
        final Stage3 stage3 = new Stage3();
        count = 0;
        countEnemy = 0;
        Stage3Spawner = new Thread(new Runnable() {
            @Override
            public void run() {
                while (countEnemy < MAXENEMY && !Thread.interrupted()) {//loop spawns enemies
                    synchronized (figures) {
                        int temp = 1 + (int) (Math.random() * 3);
                        if (temp == 1) {
                            Enemy enemy = stage3.getEnemy1();
                            figures.add(enemy);
                        }
                        if (temp == 2) {
                            Enemy enemy = stage3.getEnemy2();
                            figures.add(enemy);
                        }
                        if (temp == 3) {
                            Enemy enemy = stage3.getEnemy3();
                            figures.add(enemy);
                        }
                    }
                    try {
                        Thread.sleep(500);//interval in which enemies are spawned
                    } catch (InterruptedException ex) {
                        return;
                    }
                    count++;
                    countEnemy++;
                }
            }
        });

    }

    public int getHealth() {
        return score.health;
    }

    public static PHASE getphase() {
        return phase;
    }

    public static void setphase(PHASE p) {
        phase = p;
    }

    public void spawnBoss() {
        //seperate method to spawn boss 
        GameFigureFactory factory = new Factory("Boss");
        synchronized (figures) {
            figures.add(factory.createFigure());
        }
        //System.out.println("%%%%%%%%%%%%%%%%%%%%spawn boss:"+figures.size());

    }

    public final void startSpawnBoss() {
        Thread enemySpawner;
        enemySpawner = new Thread(new Runnable() {
            @Override
            public void run() {
                spawnBoss();
                countEnemy++;
            }
        });
        enemySpawner.start();//starts the enemy spawner
    }

    public final void startFiring() {
        enemyShootThread = new Thread(new Runnable() {
            @Override
            public void run() {
                GameFigure f;
                while (!Thread.interrupted()) {//FINISHED indicated whether the game is over or not. Enemies will fire if on the screen as long as the game is not over
                    for (int i = 0; i < figures.size(); i++) {
                        int temp = 1 + (int) (Math.random() * 100); //random generator for enemies to fire randomly
                        //boss should fire more frequencly
                        int type = figures.get(i).getMyType();//get enemy to fire
                        if ((type == 7 && temp <= 95) || (temp <= 75 && temp >= 30)) {//if random number is between 90 and 30, enemy will fire
                            f = figures.get(i);//get enemy to fire
                            if (f.isPlayer() == 1 || f.isPlayer() == 2) {//check is the object collected from list is the player, do not fire if player.
                                int tempMissile = f.getMyType();

                                if (type == 7) {
                                    tempMissile = 1 + (int) (Math.random() * 7);
                                }
                                Missile2 m = new Missile2(f.getXofMissileShoot(), f.getYofMissileShoot(), tempMissile);
                                //System.out.println(f.getType());
                                String missilelaunch = imagePath + separator + "images" + separator + f.getMyType() + ".mp3";
                                ThreadPlayer.play(missilelaunch);
                                synchronized (figures) {
                                    figures.add(m);
                                }
                            }
                        }

                    }
                    try {
                        Thread.sleep(750);//interval between enemy fire
                    } catch (InterruptedException ex) {
                        return;
                    }
                }
            }
        }
        );
    }

    private void addPower(int r, int x, int y) {
        synchronized (figures) {
            PowerUp pw = new PowerUp(r);
            pw.setLocation(x, y + 5);
            pw.setReleased(true);
            pw.setEnabled(true);
            pw.updateState(1);
            figures.add(pw);
        }
    }

    //@@@@@ not done yet - Please Do Not Remove 
    private void playerCheck(GameFigure player) {
        GameFigure g;
        for (int i = 0; i < figures.size(); i++) {

        }

    }

    //@@@@@ not done yet - Please Do Not Remove 
    private void enemyCheck(GameFigure f) {

    }

    private void playMissileCheck(GameFigure f) {
        GameFigure g;
        synchronized (figures) {
            for (int i = 0; i < figures.size(); i++) {
                g = figures.get(i);
                if (!f.equals(g) && g.isPlayer() == 1) {
                    if (f.collision().intersects(g.collision())) {
                        f.updateState(GameFigure.STATE_DONE);
                        g.Health(1);//subtract 1 from Enemy's health

                        if (g.getState() == GameFigure.STATE_DONE) {
                            f.registerObserver(score);//update score upone each enemy destroyed                   

                            Random rand = new Random();
                            int r = rand.nextInt(10);
                            if (r > 1 && r <= 4) {
                                addPower(r, (int) f.getXcoor(), (int) f.getYcoor()); // release POWER
                                f.notifyObservers(5 + r);
                            } else {
                                f.notifyObservers(5);
                            }
                        }
                        //System.out.println("Score :  " + score.score);
                    }
                }
            }
        }
    }

    private void enemMissileCheck(GameFigure f) {
        GameFigure g;
        synchronized (figures) {
            for (int i = 0; i < figures.size(); i++) {
                g = figures.get(i);
                if (!f.equals(g) && g.isPlayer() == 0) {
                    if (f.collision().intersects(g.collision())) {
                        if (g.getState() == GameFigure.STATE_TRAVELING) {
                            f.updateState(GameFigure.STATE_DONE);
                            //g.Health(1);//subtract 1 from Player's health 
                        } else if (g.getState() == GameFigure.SHIELD) {
                            f.updateState(GameFigure.STATE_DONE);
                            g.setState(GameFigure.STATE_TRAVELING);
                            //System.out.println("State ==>  :  " + g.getState());
                        }
                    }
                }
            }
        }
    }

    private void powCheck(GameFigure f) {
        synchronized (figures) {

            GameFigure g;
            for (int i = 0; i < figures.size(); i++) {
                g = figures.get(i);
                if (!g.equals(f) && g.isPlayer() == 0 && f.getState() == GameFigure.STATE_TRAVELING) {
                    if (f.collision().intersects(g.collision())) {

                        if (f.isMissile() == 40) {
                            g.setState(GameFigure.SHIELD);
                            f.updateState(GameFigure.STATE_DONE);
                        } else if (f.isMissile() == 41) {
                            f.updateState(GameFigure.STATE_DONE);
                            //g.setMissile(11);                                                    
                        } else if (f.isMissile() == 42) {
                            f.updateState(GameFigure.STATE_DONE);
                            g.Health(-1);//subtract 1 from Player's health                            
                        }

                        //f.updateState(GameFigure.STATE_DONE);
                        // g.Health(-1);//subtract 1 from Player's health
                        //System.out.println("Collision");
                    }
                }
            }
        }
    }

    public void update() {
        //System.out.println("spawned: " + enemiesSpawned + " count: " + countEnemy);
        List<GameFigure> remove = new ArrayList<>();//list of all game figures marked for removal
        GameFigure f;
        GameFigure h;
        synchronized (figures) {
            enemiesSpawned = 0;//count of enemies on game panel
            for (GameFigure d : figures) {
                d.update();//update the movement of the GameFigure
                if (d.isPlayer() == 1) {
                    enemiesSpawned++;
                    if (d.getXcoor() <= -50) {
                        remove.add(d);
                    }
                }
                if (d.isPlayer() == -1) {
                    if (d.getXcoor() <= -50 || d.getXcoor() >= GamePanel.PWIDTH + 50) {
                        remove.add(d);
                    }
                }
            }
            for (int i = 0; i < figures.size(); i++) {
                f = figures.get(i);
                f.update(); // call update from all classes.

                if (f.isMissile() == 0) { // if player's missile
                    playMissileCheck(f);
                } else if (f.isMissile() == 1) { // if enemy's missile
                    enemMissileCheck(f);
                } else if (f.isPlayer() == 31) { // if power 
                    powCheck(f);
                }
                /*
                 else if(f.isPlayer() == 30){
                 powerCheck(f);
                 }
                 */

                if (f.getState() == GameFigure.STATE_DONE && f.isPlayer() != 0) {
                    remove.add(f);
                }
            }

            //score.health = figures.get(0).get();
            if (Ship.health <= 0) {
                ThreadPlayer.play(this.explosion);
                FINISHED = true;
                figures.get(0).setState(Ship.STATE_TRAVELING);
                System.out.println("Game end!Player health empty");
            }
            //check enemy is over then finish
            if (GameData.phase == PHASE.ONE || GameData.phase == PHASE.TWO) {
                if (countEnemy >= MAXENEMY && enemiesSpawned == 0) {
                    FINISHED = true;
                    System.out.println("Game end!Player win1");
                }
            } else {//phase 3
                if (countEnemy == MAXENEMY + 1) {
                    isBossDied = true;
                }
                if (countEnemy >= MAXENEMY && enemiesSpawned == 0) {
                    if (!isBossCreated) {
                        startSpawnBoss();
                        try {
                            // Thread.sleep(10);
                        } catch (Exception ex) {
                            System.out.println(ex.getCause());
                        }
                        isBossCreated = true;
                        System.out.println("spawn boss");
                    } else if (isBossDied) {
                        FINISHED = true;
                        gameEnd = true;
                        System.out.println("Game end!Player win2");
                    }
                }
            }

            figures.removeAll(remove);
        }
    }
}
