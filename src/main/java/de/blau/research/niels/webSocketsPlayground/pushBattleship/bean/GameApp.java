package de.blau.research.niels.webSocketsPlayground.pushBattleship.bean;

import de.blau.research.niels.webSocketsPlayground.pushBattleship.game.Match;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 * User: niles
 * Date: 21.09.12
 * Time: 00:51
 */
@ManagedBean
@ApplicationScoped
public class GameApp {

    private Game pendingGame;

    public synchronized Game joinOrCreateMatch() {
        if (pendingGame == null) {
            pendingGame = new Game();
            return pendingGame;
        } else {
            Game result = pendingGame;
            result.secondPlayerJoined = true;
            pendingGame = null;
            return result;
        }
    }

    public static class Game {
        private static int chanelIdCounter = 0;
        public final Match match = new Match();
        private boolean secondPlayerJoined = false;
        private final int chanelId;

        public Game() {
            chanelId = chanelIdCounter++;
        }

        public boolean isSecondPlayerJoined() {
            return secondPlayerJoined;
        }

        public int getChanelId() {
            return chanelId;
        }
    }
}
