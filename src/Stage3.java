
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class Stage3 implements Stage {

    private final BufferedImage backgroundImage;
    private final int backgroundWidth;
    private int count = 0;
    private final Image groundEnemy;
    private final Image airEnemy;
    private final Image airEnemy2;

    public Stage3() throws IOException {
        String imagePath = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        File file = new File(imagePath + separator + "images" + separator
                + "Stage3Background.gif");
        backgroundImage = ImageIO.read(file);
        file = new File(imagePath + separator + "images" + separator
                + "enemy5.png");
        airEnemy = ImageIO.read(file);
        file = new File(imagePath + separator + "images" + separator
                + "enemy6.png");
        airEnemy2 = ImageIO.read(file);
        file = new File(imagePath + separator + "images" + separator
                + "enemy4.png");
        groundEnemy = ImageIO.read(file);
        backgroundWidth = backgroundImage.getWidth(null);
    }

    public Enemy getEnemy1() {
        Enemy enemy = new Enemy(GamePanel.PWIDTH + 10, 30 + count * 75, 81, 81) {

            @Override
            public void update() {
                this.x -= 2;
            }
        };
        enemy.enemyImage = airEnemy;
        enemy.type = 5;
        count++;
        return enemy;
    }

    @Override
    public void resetCount() {
        count = 0;
    }

    public Enemy getEnemy2() {
        Random randomGenerator = new Random();
        int temp = randomGenerator.nextInt(75) + 430;
        Enemy enemy = new Enemy(GamePanel.PWIDTH + 10, temp, 81, 81) {
            @Override
            public void update() {
                this.x -= 1;
            }
        };
        enemy.enemyImage = groundEnemy;
        enemy.type = 4;
        return enemy;
    }

    public Enemy getEnemy3() {
        Random randomGenerator = new Random();
        int temp = randomGenerator.nextInt(400) + 10;
        Enemy enemy = new Enemy(GamePanel.PWIDTH + 10, temp, 81, 81) {
            @Override
            public void update() {
                this.x -= 1;
            }
        };
        enemy.enemyImage = airEnemy2;
        enemy.type = 6;
        return enemy;
    }
    
    public Enemy getEnemy4(){
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
        enemy.type = 5;
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
