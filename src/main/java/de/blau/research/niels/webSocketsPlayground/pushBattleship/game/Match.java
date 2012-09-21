package de.blau.research.niels.webSocketsPlayground.pushBattleship.game;

import java.util.HashMap;
import java.util.Map;

public class Match {
    public final Map<Player, Field> fields = new HashMap<Player, Field>();
    private Player nextToShoot = Player.first;
    private Player winner;

    public Match(Field fieldPlayer1, Field fieldPlayer2) {
        fields.put(Player.first, fieldPlayer1);
        fields.put(Player.second, fieldPlayer2);
    }

    public Match() {
        fields.put(Player.first, new Field());
        fields.put(Player.second, new Field());
    }

    public Player whoSNext() {
        return nextToShoot;
    }

    public Field.CellState shoot(Player player, Position position) {
        if (player != nextToShoot) {
            throw new IllegalArgumentException("not your turn " + player);
        }
        Player enemy = player.getEnemy();
        Field enemyField = fields.get(enemy);
        Field.CellState shout = enemyField.shout(position);
        if (enemyField.allShipsDestroyed()) {
            winner = player;
            nextToShoot = null;
        } else {
            if (shout == Field.CellState.tried)
                nextToShoot = enemy;
        }
        return shout;
    }

    public Player getWinner() {
        return winner;
    }

    public Field.CellState shoot(Player player, String text) {
        return shoot(player, new Position(text));
    }


    public static enum Player {
        first, second;

        public Player getEnemy() {
            if (this.equals(first)) {
                return second;
            }
            return first;
        }
    }
}
