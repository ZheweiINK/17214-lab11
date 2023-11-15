package edu.cmu.cs.cs214.rec09.plugin;

import edu.cmu.cs.cs214.rec09.framework.core.GameFramework;
import edu.cmu.cs.cs214.rec09.framework.core.GamePlugin;
import edu.cmu.cs.cs214.rec09.games.TicTacToe;
import edu.cmu.cs.cs214.rec09.games.TicTacToe.Player;

/**
 * TicTacToe game plugin.
 */
public class TicTacToePlugin implements GamePlugin<Player> {
    private static final String GAME_NAME = "TicTacToe";
    private static final String UNKNOWN_SQUARE_STRING = "?";
    private static final String PLAYER_WON_MSG = "Player %s won!";
    private static final String PLAYERS_TIED_MSG = "The game is a tie.";
    private static final String SELECT_SQUARE_MSG = "Player %s, select a square.";

    private GameFramework framework;
    private TicTacToe game;

    private int lastPlayedX = -1;
    private int lastPlayedY = -1;

    @Override
    public String getGameName() {
        return GAME_NAME;
    }

    @Override
    public int getGridWidth() {
        return TicTacToe.SIZE;
    }

    @Override
    public int getGridHeight() {
        return TicTacToe.SIZE;
    }

    @Override
    public void onRegister(GameFramework f) {
        framework = f;
    }

    @Override
    public void onNewGame() {
        game = new TicTacToe();
        for (int i = 0; i < TicTacToe.SIZE; i++) {
            for (int j = 0; j < TicTacToe.SIZE; j++) {
                framework.setSquare(i, j, UNKNOWN_SQUARE_STRING);
            }
        }
        framework.setFooterText(String.format(SELECT_SQUARE_MSG, game.currentPlayer()));
    }

    @Override
    public boolean isMoveValid(int x, int y) {
        return game.isValidPlay(x, y);
    }

    @Override
    public boolean isMoveOver() {
        if (lastPlayedX == -1 || lastPlayedY == -1) {
            return false;
        }
        return game.currentPlayer() != game.getSquare(lastPlayedX, lastPlayedY);
    }

    @Override
    public void onMovePlayed(int x, int y) {
        game.play(x, y);
        framework.setSquare(x, y, game.getSquare(x, y).toString());
        if (!game.isOver()) {
            framework.setFooterText(String.format(SELECT_SQUARE_MSG, game.currentPlayer()));
        }
    }

    @Override
    public boolean isGameOver() {
        return game.isOver();
    }

    @Override
    public String getGameOverMessage() {
        Player winner = game.winner();
        if (winner != null) {
            return String.format(PLAYER_WON_MSG, winner);
        } else {
            return PLAYERS_TIED_MSG;
        }
    }

    @Override
    public void onGameClosed() {
    }

    @Override
    public Player currentPlayer() {
        return game.currentPlayer();
    }

    @Override
    public void onNewMove() {
        lastPlayedX = -1;
        lastPlayedY = -1;
        // throw new UnsupportedOperationException("Unimplemented method 'onNewMove'");
    }
}
