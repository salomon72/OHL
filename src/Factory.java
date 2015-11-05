/*
 class that is used to create new iinstances of GameFigure classes.
 */

import java.util.Random;

public class Factory implements GameFigureFactory {

    GameFigure type;
    String S;

    public Factory(String S) {
        this.S = S;
    }

    @Override
    public GameFigure createFigure() { //using a string to identify the type of GameFigure to create
        switch (S) {
            case "Ship":
                type = new Ship(0, GamePanel.PHEIGHT / 2 - 30);//example of object creation
                return type;
            case "Enemy": {
                Random randomGenerator = new Random();

                int temp = randomGenerator.nextInt(450);
                type = new Enemy(GamePanel.PWIDTH - 300, temp + 30, 81, 81);
                return type;
            }
            case "BossStage1": {
                Random randomGenerator = new Random();
                int temp = randomGenerator.nextInt(450);
                type = new Boss(GamePanel.PWIDTH - 400, GamePanel.PHEIGHT / 2, 2 * 81, 2 * 81, 9);
                return type;
            }
            case "BossStage2": {
                Random randomGenerator = new Random();
                int temp = randomGenerator.nextInt(450);
                type = new Boss(GamePanel.PWIDTH - 400, GamePanel.PHEIGHT / 2, 2 * 81, 2 * 81, 8);
                return type;
            }
            case "BossStage3": {
                Random randomGenerator = new Random();
                int temp = randomGenerator.nextInt(450);
                type = new Boss(GamePanel.PWIDTH - 400, GamePanel.PHEIGHT / 2, 2 * 81, 2 * 81, 7);
                return type;
            }
            case "case4":
                return type;
        }
        throw new IllegalArgumentException("No such GameFigure");
    }

}
