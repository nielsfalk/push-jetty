package de.blau.research.niels.webSocketsPlayground.pushBattleship.bean;

import de.blau.research.niels.webSocketsPlayground.pushBattleship.game.Field;
import de.blau.research.niels.webSocketsPlayground.pushBattleship.game.Match;
import org.json.JSONObject;
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
            if (game.isSecondPlayerJoined()) {
                player = second;
                simpleResponse("secondEntered");
            } else {
                player = first;
            }
        }
        return game;
    }

    public void nextGame() {
        simpleResponse("playerLeft");
        gameApp.deletePendingGame(game);
        player = null;
        game = null;
    }

    public void fire() {
        if (getGame().match.getWinner() != null) {
            JSONObject jsonObject = baseResponse();
            respond(jsonObject);
        } else if (!getGame().isSecondPlayerJoined()) {
            simpleResponse("wait");
        } else if (getGame().match.whoSNext() != player) {
            simpleResponse("othersTurn");
        } else {
            Field.CellState shoot = getGame().match.shoot(player, firePosition);
            JSONObject jsonObject = baseResponse();
            jsonObject.put("fired", shoot.name());
            jsonObject.put("position", firePosition);

            respond(jsonObject);
        }
    }

    void simpleResponse(String command) {
        JSONObject jsonObject = baseResponse();
        jsonObject.put(command, true);
        respond(jsonObject);
    }

    private JSONObject baseResponse() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("player", player.name());
        if (getGame().match.getWinner() != null) {
            jsonObject.put("winner", getGame().match.getWinner().name());

        }
        return jsonObject;
    }

    private void respond(JSONObject jsonObject) {
        PushContextFactory.getDefault().getPushContext().push("/battleShip" + getGame().getChanelId(), jsonObject.toString());
    }

    @SuppressWarnings("UnusedDeclaration")//ManagedProperty
    public void setGameApp(GameApp gameApp) {
        this.gameApp = gameApp;
    }

    public Match.Player getPlayer() {
        if (player == null) {
            getGame();
        }
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
