package gol;

public class Game {
	public static void main(String[] args) {
        createGameWindow();
    }
    public static void createGameWindow() {
        GameModel gameModel = new GameModel();
        GameView gameView = new GameView();
        GameController gameController = new GameController(gameModel, gameView);

        
    }
}

