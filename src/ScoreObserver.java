/*
 observer class that keeps track of total score of the player, is attatched to GameFigures
 */

public class ScoreObserver implements Observer {

    int score = 0;

    @Override
    public void update(int amount) {
        score += amount;
    }

}
