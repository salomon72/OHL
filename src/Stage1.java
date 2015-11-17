
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class Stage1 implements Stage {

    private final BufferedImage backgroundImage;
    private final BufferedImage planetImage;
    private final int backgroundWidth;
    private int count = 0;

    public Stage1() throws IOException {
        String imagePath = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        File file = new File(imagePath + separator + "images" + separator
                + "Stage1Background.gif");
        backgroundImage = ImageIO.read(file);
        file = new File(imagePath + separator + "images" + separator
                + "planet.png");
        planetImage = ImageIO.read(file);
        backgroundWidth = backgroundImage.getWidth(null);
    }

    @Override
    public void resetCount() {
        count = 1;
    }

    public Enemy getEnemy1() {
        Enemy enemy = new Enemy(GamePanel.PWIDTH + 10, 0 + count * 75, 81, 81) {
            private int count = 0;
            private boolean reverse = false;

            @Override
            public void update() {
                count++;
                if (count <= 80) {
                    this.x -= 2;
                } else if (count >= 80 && count <= 120) {
                    this.x -= 2;
                    this.y += 1;
                } else if (reverse) {
                    this.y -= 1;
                } else if (!reverse) {
                    this.y += 1;
                }
                if (this.y >= GamePanel.PHEIGHT - enemyImage.getHeight(null)) {
                    reverse = true;
                }
                if (this.y <= 0) {
                    reverse = false;
                }
            }
        };
        count++;
        return enemy;
    }

    public Enemy getEnemy2() {
        Random randomGenerator = new Random();
        int temp = randomGenerator.nextInt(450);
        Enemy enemy = new Enemy(GamePanel.PWIDTH + 10, temp, 81, 81) {
            @Override
            public void update() {
                this.x -= 2;
            }
        };
        return enemy;
    }

    public Enemy getEnemy3() {
        Enemy enemy = new Enemy(GamePanel.PWIDTH + 10, 0 + count * 60, 81, 81) {
            private int count = 0;

            @Override
            public void update() {
                count++;
                if (count >= 0) {
                    this.x -= 1;
                }
            }
        };
        count++;
        return enemy;
    }

    public Enemy getEnemy4() {
        Enemy enemy = new Enemy(GamePanel.PWIDTH + 10, 0 + count * 75, 81, 81) {
            private int count = 0;
            private boolean reverse = false;

            @Override
            public void update() {
                count++;
                if (count <= 130) {
                    this.x -= 2;
                } else if (count >= 130 && count <= 180) {
                    this.x -= 2;
                    this.y += 1;
                } else if (reverse) {
                    this.y -= 1;
                } else if (!reverse) {
                    this.y += 1;
                }
                if (this.y >= GamePanel.PHEIGHT - enemyImage.getHeight(null)) {
                    reverse = true;
                }
                if (this.y <= 0) {
                    reverse = false;
                }
            }
        };
        count++;
        return enemy;
    }

    public Enemy powerupEnemy() {
        Random randomGenerator = new Random();
        int temp = randomGenerator.nextInt(450);
        Enemy enemy = new Enemy(GamePanel.PWIDTH + 10, temp, 81, 81) {
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
                dx *= 2.5;
                dy *= 2.5;
                this.x += dx;
                this.y += dy;
            }
        };
        enemy.setPowerup();
        return enemy;
    }

    @Override
    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }

    @Override
    public BufferedImage getPlanetImage() {
        return planetImage;
    }

    @Override
    public int getBackgroundWidth() {
        return backgroundWidth;
    }
}
