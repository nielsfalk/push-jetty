package de.blau.research.niels.webSocketsPlayground.pushBattleship.bean;

import de.blau.research.niels.webSocketsPlayground.pushBattleship.game.Match;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import static de.blau.research.niels.webSocketsPlayground.pushBattleship.game.Match.Player.first;
import static de.blau.research.niels.webSocketsPlayground.pushBattleship.game.Match.Player.second;

/**
 * User: niles
 * Date: 21.09.12
 * Time: 00:49
 */
@ManagedBean
@SessionScoped
public class PlayerSession {
    @ManagedProperty(value = "#{gameApp}")
    private GameApp gameApp;
    private Match.Player player;
    private GameApp.Game game;

    public GameApp.Game getGame() {
        if (game == null) {
            game = gameApp.joinOrCreateMatch();
            player = game.isSecondPlayerJoined() ? second : first;
        }
        return game;
    }

    public void setGameApp(GameApp gameApp) {
        this.gameApp = gameApp;
    }

    public Match.Player getPlayer() {
        return player;
    }
}
