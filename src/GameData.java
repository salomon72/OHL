/*
 handles the management of the objects to be rendered and when to remove them.
 Also controls the spawning of enemies and firing of enemies
 */

import java.awt.Image;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
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
    static int countEnemy = 0;
    boolean FINISHED = false;
    boolean gameEnd = false;
    boolean bossTime = false;
    boolean BOSS = false;
    boolean isStarting = true;
    boolean isBossDied = false;
    static boolean isBossCreated = false;
    private int enemiesSpawned;
    private static PHASE phase;
    ScoreObserver score = new ScoreObserver();
    String separator = System.getProperty("file.separator");
    String imagePath = System.getProperty("user.dir");
    String explosion = imagePath + separator + "images" + separator + "BigExplosionSoundEffect.mp3";//load game over screen from image file
    String missilelaunch = imagePath + separator + "images" + separator + "SoundEffectMissileLaunch.mp3";
    String collide = imagePath + separator + "images" + separator + "collide.mp3";
    public Thread Stage1Spawner;
    public Thread Stage2Spawner;
    public Thread Stage3Spawner;
    public Thread enemyShootThread;

    public static Image playerImage;

    public GameData() throws IOException {

        figures = Collections.synchronizedList(new ArrayList<GameFigure>());
        GameFigureFactory factory = new Factory("Ship");//example of player ship spawn
        figures.add(factory.createFigure());//adds player ship to figures
        setStage1Spawner();//spawner thread for enemies
        setStage2Spawner();
        setStage3Spawner();
        Stage1Spawner.start();//starts the enemy spawner
        startFiring();//thread that has enemies fire
        enemyShootThread.start();
        playerImage = Ship.playerImage;
    }

    public void setStateChanged(int stage, boolean cutscene) throws IOException, InterruptedException {
        Stage1Spawner.interrupt();
        Stage2Spawner.interrupt();
        Stage3Spawner.interrupt();
        enemyShootThread.interrupt();
        removeEnemies();
        if (stage == 1) {
            phase = PHASE.ONE;
            setStage1Spawner();
            Stage1Spawner.start();
            startFiring();
            enemyShootThread.start();
            Ship.playerImage = playerImage;
        } else if (stage == 2) {
            phase = PHASE.TWO;
            Main.missileLevel = 2;
            setStage2Spawner();
            startFiring();
            enemyShootThread.start();
            if (!cutscene) {
                Stage2Spawner.start();
            }
            Ship.playerImage = playerImage;
        } else if (stage == 3) {
            phase = PHASE.THREE;
            setStage3Spawner();
            startFiring();
            enemyShootThread.start();
            if (!cutscene) {
                Stage3Spawner.start();
            }
            Ship.playerImage = playerImage;
        }
        if (cutscene) {
            Stage1Spawner.interrupt();
            Stage2Spawner.interrupt();
            Stage3Spawner.interrupt();
            removeEnemies();
            CutsceneShip cship = new CutsceneShip(0, GamePanel.PHEIGHT / 2);
            synchronized (figures) {
                figures.add(cship);
            }
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
        Stage1Spawner = new Thread(() -> {
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
                            Thread.sleep(2000);
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
                    if (count % 5 == 0) {
                        enemy = stage1.powerupEnemy();
                        synchronized (figures) {
                            figures.add(enemy);
                        }
                    }
                    try {
                        Thread.sleep(1200);
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
                    if (count % 5 == 0) {
                        enemy = stage1.powerupEnemy();
                        synchronized (figures) {
                            figures.add(enemy);
                        }
                    }
                    if (count == 22) {
                        try {
                            Thread.sleep(2000);
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
                    if (count % 5 == 0) {
                        enemy = stage1.powerupEnemy();
                        synchronized (figures) {
                            figures.add(enemy);
                        }
                    }
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException ex) {
                        return;
                    }
                    if (count == 28 && repeat == true) {
                        count = 6;
                        repeat = false;
                    }
                }
            }
            countEnemy = MAXENEMY;
        });

    }

    public final void setStage2Spawner() throws IOException {
        final Stage2 stage2 = new Stage2();
        count = 0;
        countEnemy = 0;

        Stage2Spawner = new Thread(() -> {
            while (countEnemy < MAXENEMY && !Thread.interrupted()) {//loop spawns enemies
                while (count <= 15) {
                    synchronized (figures) {
                        Enemy enemy = stage2.getEnemy1();
                        figures.add(enemy);
                    }
                    try {
                        Thread.sleep(1000);//interval in which enemies are spawned
                    } catch (InterruptedException ex) {
                        return;
                    }
                    if (count % 2 != 0) {
                        synchronized (figures) {
                            Enemy enemy = stage2.getEnemy2();
                            figures.add(enemy);
                        }
                    }
                    if (count % 3 == 0) {
                        synchronized (figures) {
                            Enemy enemy = stage2.getEnemy3();
                            figures.add(enemy);
                        }
                    }
                    if (count % 5 == 0) {
                        Enemy enemy = stage2.powerupEnemy();
                        synchronized (figures) {
                            figures.add(enemy);
                        }
                    }
                    count++;
                }
                while (count >= 10 && count <= 15) {
                    synchronized (figures) {
                        Enemy enemy = stage2.getEnemy3();
                        figures.add(enemy);
                    }
                    synchronized (figures) {
                        Enemy enemy = stage2.getEnemy4();
                        figures.add(enemy);
                    }
                    try {
                        Thread.sleep(1000);//interval in which enemies are spawned
                    } catch (InterruptedException ex) {
                        return;
                    }
                    count++;
                }
                while (count >= 15 && count <= 30) {
                    synchronized (figures) {
                        Enemy enemy = stage2.getEnemy1();
                        figures.add(enemy);
                    }
                    try {
                        Thread.sleep(1000);//interval in which enemies are spawned
                    } catch (InterruptedException ex) {
                        return;
                    }
                    if (count % 2 != 0) {
                        synchronized (figures) {
                            Enemy enemy = stage2.getEnemy2();
                            figures.add(enemy);
                        }
                    }
                    if (count % 10 == 0) {
                        synchronized (figures) {
                            Enemy enemy = stage2.getEnemy3();
                            figures.add(enemy);
                        }
                    }
                    if (count % 5 == 0) {
                        Enemy enemy = stage2.powerupEnemy();
                        synchronized (figures) {
                            figures.add(enemy);
                        }
                    }
                    count++;
                }
                while (count >= 30 && count <= 35) {
                    synchronized (figures) {
                        Enemy enemy = stage2.getEnemy3();
                        figures.add(enemy);
                    }
                    synchronized (figures) {
                        Enemy enemy = stage2.getEnemy4();
                        figures.add(enemy);
                    }
                    if (count % 5 == 0) {
                        Enemy enemy = stage2.powerupEnemy();
                        synchronized (figures) {
                            figures.add(enemy);
                        }
                    }
                    try {
                        Thread.sleep(1000);//interval in which enemies are spawned
                    } catch (InterruptedException ex) {
                        return;
                    }
                    count++;
                }
                countEnemy = MAXENEMY;
            }
        });
    }

    public final void setStage3Spawner() throws IOException {
        final Stage3 stage3 = new Stage3();
        count = 0;
        countEnemy = 0;
        Stage3Spawner = new Thread(() -> {
            while (countEnemy < MAXENEMY && !Thread.interrupted()) {//loop spawns enemies
                stage3.resetCount();
                while (count < 6) {
                    if (Thread.interrupted()) {
                        return;
                    }
                    Enemy enemy = stage3.getEnemy1();
                    synchronized (figures) {
                        figures.add(enemy);
                    }
                    if (count % 5 == 0) {
                        enemy = stage3.powerupEnemy();
                        synchronized (figures) {
                            figures.add(enemy);
                        }
                    }
                    count++;
                    if (count == 6) {
                        try {
                            Thread.sleep(1750);
                        } catch (InterruptedException ex) {
                            return;
                        }
                    }
                }

                stage3.resetCount();
                while (count <= 11 && count >= 6) {
                    if (Thread.interrupted()) {
                        return;
                    }
                    Enemy enemy = stage3.getEnemy1();
                    synchronized (figures) {
                        figures.add(enemy);
                    }
                    if (count % 5 == 0) {
                        enemy = stage3.powerupEnemy();
                        synchronized (figures) {
                            figures.add(enemy);
                        }
                    }
                    count++;
                    if (count == 12) {
                        try {
                            Thread.sleep(1750);
                        } catch (InterruptedException ex) {
                            return;
                        }
                    }
                }
                stage3.resetCount();
                while (count <= 17 && count >= 12) {
                    if (Thread.interrupted()) {
                        return;
                    }
                    Enemy enemy = stage3.getEnemy1();
                    synchronized (figures) {
                        figures.add(enemy);
                    }
                    if (count % 5 == 0) {
                        enemy = stage3.powerupEnemy();
                        synchronized (figures) {
                            figures.add(enemy);
                        }
                    }
                    count++;
                    if (count == 18) {
                        try {
                            Thread.sleep(1750);
                        } catch (InterruptedException ex) {
                            return;
                        }
                    }
                }
                while (count <= 50 && count >= 18) {
                    Enemy enemy = stage3.getEnemy3();
                    synchronized (figures) {
                        figures.add(enemy);
                    }
                    if (count % 2 == 0) {
                        enemy = stage3.getEnemy2();
                        synchronized (figures) {
                            figures.add(enemy);
                        }
                    }
                    if (count % 3 == 0) {
                        enemy = stage3.getEnemy4();
                        synchronized (figures) {
                            figures.add(enemy);
                        }
                    }
                    if (count % 5 == 0) {
                        enemy = stage3.powerupEnemy();
                        synchronized (figures) {
                            figures.add(enemy);
                        }
                    }
                    count++;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        return;
                    }
                }
                countEnemy = MAXENEMY;
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

    public void spawnBossStage1() {
        GameFigureFactory factory = new Factory("BossStage1");
        synchronized (figures) {
            figures.add(factory.createFigure());
        }
        countEnemy++;
    }

    public void spawnBossStage2() {
        GameFigureFactory factory = new Factory("BossStage2");
        synchronized (figures) {
            figures.add(factory.createFigure());
        }
        countEnemy++;
    }

    public void spawnBossStage3() {
        GameFigureFactory factory = new Factory("BossStage3");
        synchronized (figures) {
            figures.add(factory.createFigure());
        }
        countEnemy++;
    }

    public final void startFiring() {
        enemyShootThread = new Thread(() -> {
            GameFigure f;
            while (!Thread.interrupted()) {
                //FINISHED indicated whether the game is over or not. Enemies will fire if on the screen as long as the game is not over
                for (int i = 0; i < figures.size(); i++) {
                    int temp = 1 + (int) (Math.random() * 100); //random generator for enemies to fire randomly
                    int type = figures.get(i).getMyType();//get enemy to fire
                    if ((type == 7 && temp <= 75) || (temp <= 75 && temp >= 30)) {
                        //if random number is between 90 and 30, enemy will fire
                        f = figures.get(i);//get enemy to fire
                        if (f.isPlayer() == 1 || f.isPlayer() == 2) {
                            //check is the object collected from list is the player, do not fire if player.
                            int tempMissile = f.getMyType();
                            if (type == 7 || type == 8 || type == 9) {
                                tempMissile = type;
                            }
                            if (figures.get(i).getState() != GameFigure.STATE_EXPLODING) {
                                Missile2 m = new Missile2(f.getXofMissileShoot(), f.getYofMissileShoot(), tempMissile);
                                synchronized (figures) {
                                    figures.add(m);
                                }
                            }
                        }
                    }
                }
                try {
                    Thread.sleep(1000);//interval between enemy fire
                } catch (InterruptedException ex) {
                    return;
                }
            }
        });
    }

    private void addPower(int r, GameFigure f) {
        synchronized (figures) {
            //if ship is already shooting 2 missiles at once, change power to health 
            if (r == 3 && Main.missileLevel > 1) {
                r = 2;
            }
            if (r == 2 && Ship.health <= 5) {
                r = 4;
            }
            if (r == 4 && Ship.health > 5) {
                if (GameData.phase == PHASE.TWO || GameData.phase == PHASE.THREE) {
                    r = 5;
                } else {
                    r = 2;
                }
            }

            PowerUp pw = new PowerUp(r);
            pw.setLocation((int) f.getXcoor(), (int) f.getYcoor() + 5);
            pw.setReleased(true);
            pw.setEnabled(true);
            pw.updateState(1);
            figures.add(pw);
            f.notifyObservers(5 + r);
        }

    }

    private void playMissileCheck(GameFigure f) {
        GameFigure g;
        synchronized (figures) {
            for (int i = 0; i < figures.size(); i++) {
                g = figures.get(i);
                if (!f.equals(g) && g.isPlayer() == 1) {
                    if (f.collision().intersects(g.collision())) {
                        f.updateState(GameFigure.STATE_DONE);
                        if (Ship.upgrade) {
                            g.Health(3);//subtract 3 from Enemy's health if ship is upgraded
                        } else {
                            g.Health(1); // else substract 1 from Enemy's health
                        }
                        if (g.getState() == GameFigure.STATE_DONE) {
                            f.registerObserver(score);//update score upone each enemy destroyed                   

                            int r;
                            if (GameData.phase == PHASE.ONE) { //powerups for stage 1
                                if (g.containsPowerup()) {
                                    r = (int) (Math.random() * (3) + 2);
                                    addPower(r, f);// release POWER
                                    f.notifyObservers(5 + r);
                                } else {
                                    f.notifyObservers(5);
                                }
                            } else if (GameData.phase == PHASE.TWO || GameData.phase == PHASE.THREE) { //powerups for stage 3
                                if (g.containsPowerup()) {
                                    r = (int) (Math.random() * (4) + 2);
                                    addPower(r, f);// release POWER
                                    f.notifyObservers(5 + r);
                                } else {
                                    f.notifyObservers(5);
                                }
                            }
                        }
                    }
                } else if (!f.equals(g) && g.isMissile() == 1) {
                    if (f.collision().intersects(g.collision())) {
                        f.Health(1);
                        g.Health(1);
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
                            g.Health(1);//subtract 1 from Player's health 
                        } else if (g.getState() == GameFigure.SHIELD) {
                            Shield.count--;
                            if (Shield.count <= 0) {
                                f.updateState(GameFigure.STATE_DONE);
                            }
                            if (Shield.count == 0) {
                                g.setState(GameFigure.STATE_TRAVELING);
                            }
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

                        if (f.isMissile() == 40) { // shield
                            Shield.count++;
                            g.setState(GameFigure.SHIELD);
                            f.updateState(GameFigure.STATE_DONE);
                        } else if (f.isMissile() == 41) { // multi-missile
                            f.updateState(GameFigure.STATE_DONE);
                            if (GameData.phase == PHASE.ONE) {
                                Main.missileLevel = 2;
                            } else if (GameData.phase == PHASE.TWO || GameData.phase == PHASE.THREE) {
                                if (!Ship.upgrade) {
                                    if (Main.missileLevel < 3) {
                                        Main.missileLevel++;
                                    }
                                } else {
                                    Main.missileLevel = 1;
                                }

                            }

                        } else if (f.isMissile() == 42) { // health bonus
                            f.updateState(GameFigure.STATE_DONE);
                            g.Health(-1);//subtract 1 from Player's health

                        } else if (f.isMissile() == 43) { // Power bonus
                            f.updateState(GameFigure.STATE_DONE);
                            g.Health(11);//subtract 1 from Player's health                            
                        }
                    }
                }
            }
        }
    }

    public void update() {
        List<GameFigure> remove = new ArrayList<>();//list of all game figures marked for removal
        GameFigure f;
        synchronized (figures) {
            enemiesSpawned = 0;//count of enemies on game panel
            for (int i = 0; i < figures.size(); i++) {
                GameFigure d = figures.get(i);
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
                if (d.isPlayer() == 0) {
                    if (d.getXcoor() <= -50 || d.getXcoor() >= GamePanel.PWIDTH + 50) {
                        try {
                            if (GameData.phase == PHASE.ONE) {
                                Animator.endcutscene = true;
                                GamePanel.nextStage = 2;
                                GamePanel.stageChange = true;
                                setStateChanged(2, false);

                            } else if (GameData.phase == PHASE.TWO) {
                                Animator.endcutscene = true;
                                GamePanel.nextStage = 3;
                                GamePanel.stageChange = true;
                                setStateChanged(3, false);
                            }
                            remove.add(d);
                        } catch (IOException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
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
                if (f.getState() == GameFigure.STATE_DONE && f.isPlayer() != 0) {
                    remove.add(f);
                }
            }

            if (Ship.health < 1) {
                FINISHED = true;
                figures.get(0).setState(Ship.STATE_TRAVELING);
            } //check enemy is over then finish
            if (GameData.phase == PHASE.ONE) {
                if (countEnemy == MAXENEMY + 1) {
                    isBossDied = true;
                }
                if (countEnemy >= MAXENEMY && enemiesSpawned == 0) {
                    if (!isBossCreated) {
                        spawnBossStage1();
                        isBossCreated = true;
                    } else if (isBossDied) {
                        FINISHED = true;
                        isBossDied = false;
                        isBossCreated = false;
                    }
                }
            } else if (GameData.phase == PHASE.TWO) {
                if (countEnemy == MAXENEMY + 1) {
                    isBossDied = true;
                }
                if (countEnemy >= MAXENEMY && enemiesSpawned == 0) {
                    if (!isBossCreated) {
                        spawnBossStage2();
                        isBossCreated = true;
                    } else if (isBossDied) {
                        FINISHED = true;
                        isBossDied = false;
                        isBossCreated = false;
                    }
                }
            } else {//phase 3
                if (countEnemy == MAXENEMY + 1 && enemiesSpawned == 0) {
                    isBossDied = true;
                }
                if (countEnemy >= MAXENEMY && enemiesSpawned == 0) {
                    if (!isBossCreated) {
                        spawnBossStage3();
                        try {
                            // Thread.sleep(10);
                        } catch (Exception ex) {
                            System.out.println(ex.getCause());
                        }
                        isBossCreated = true;
                    } else if (isBossDied) {
                        FINISHED = true;
                        gameEnd = true;
                    }
                }
            }
            figures.removeAll(remove);
        }
    }
}
