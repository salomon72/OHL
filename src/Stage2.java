
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class Stage2 implements Stage {

    private final BufferedImage backgroundImage;
    private final int backgroundWidth;
    private int count = 0;
    private final Image waterEnemy;
    private final Image airEnemy;

    public Stage2() throws IOException {
        String imagePath = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        File file = new File(imagePath + separator + "images" + separator
                + "Stage2Background.gif");
        backgroundImage = ImageIO.read(file);
        file = new File(imagePath + separator + "images" + separator
                + "enemy2.png");
        airEnemy = ImageIO.read(file);
        file = new File(imagePath + separator + "images" + separator
                + "enemy3.png");
        waterEnemy = ImageIO.read(file);
        backgroundWidth = backgroundImage.getWidth(null);
    }

    public Enemy getEnemy1() {
        Random randomGenerator = new Random();
        int temp = randomGenerator.nextInt(300) + 10;
        Enemy enemy = new Enemy(GamePanel.PWIDTH + 10, temp, 81, 81) {
            @Override
            public void update() {
                this.x -= 1;
            }
        };
        enemy.enemyImage = airEnemy;
        enemy.type = 2;
        count++;
        return enemy;
    }

    @Override
    public void resetCount() {
        count = 0;
    }

    public Enemy getEnemy2() {
        Random randomGenerator = new Random();
        int temp = randomGenerator.nextInt(100) + GamePanel.PHEIGHT - 150;
        Enemy enemy = new Enemy(GamePanel.PWIDTH + 10, temp, 81, 81) {
            @Override
            public void update() {
                this.x -= 1;
            }
        };
        enemy.enemyImage = waterEnemy;
        enemy.type = 3;
        return enemy;
    }

    public Enemy getEnemy3() {

        Enemy enemy = new Enemy(GamePanel.PWIDTH + 10, GamePanel.PHEIGHT / 2, 81, 81) {
            private int count = 0;
            float dx;
            float dy;
            float length;

            @Override
            public void update() {
                dx = Ship.x + 200 - this.x;
                dy = Ship.y + 20 - this.y;
                length = (float) Math.sqrt(dx * dx + dy * dy);
                dx /= length;
                dy /= length;
                count++;
                this.x += dx;
                this.y += dy;
            }
        };
        enemy.enemyImage = airEnemy;
        enemy.type = 2;
        count++;
        return enemy;
    }

    public Enemy getEnemy4() {
        Random randomGenerator = new Random();
        int temp = randomGenerator.nextInt(100) + GamePanel.PHEIGHT - 150;
        Enemy enemy = new Enemy(GamePanel.PWIDTH + 10, temp, 81, 81) {
            private int count = 0;
            float dx;
            float dy;
            float length;

            @Override
            public void update() {
                dx = Ship.x + 200 - this.x;
                dy = Ship.y + 20 - this.y;
                length = (float) Math.sqrt(dx * dx + dy * dy);
                dx /= length;
                dy /= length;
                count++;
                this.x += dx;
            }
        };
        enemy.enemyImage = waterEnemy;
        enemy.type = 3;
        count++;
        return enemy;
    }

    @Override
    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }

    @Override
    public int getBackgroundWidth() {
        return backgroundWidth;
    }

    @Override
    public BufferedImage getPlanetImage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
