package de.blau.research.niels.webSocketsPlayground.pushBattleship.bean;

import de.blau.research.niels.webSocketsPlayground.pushBattleship.game.Field;
import de.blau.research.niels.webSocketsPlayground.pushBattleship.game.Match;
import org.primefaces.push.PushContextFactory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.List;

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
    private String firePosition;

    public GameApp.Game getGame() {
        if (game == null) {
            game = gameApp.joinOrCreateMatch();
            player = game.isSecondPlayerJoined() ? second : first;
        }
        return game;
    }

    public void fire() {
        Field.CellState shoot = getGame().match.shoot(player, firePosition);
        PushContextFactory.getDefault().getPushContext().push("/battleShip" + getGame().getChanelId(), shoot.name());
    }

    @SuppressWarnings("UnusedDeclaration")//ManagedProperty
    public void setGameApp(GameApp gameApp) {
        this.gameApp = gameApp;
    }

    public Match.Player getPlayer() {
        return player;
    }

    public List<List<Field.DrawableCell>> getMyField() {
        return getGame().match.fields.get(player).toDrawable(false);
    }

    public List<List<Field.DrawableCell>> getEnemyField() {
        return getGame().match.fields.get(player.getEnemy()).toDrawable(true);
    }

    public void setFirePosition(String firePosition) {
        this.firePosition = firePosition;
    }

    public String getFirePosition() {
        return firePosition;
    }
}
