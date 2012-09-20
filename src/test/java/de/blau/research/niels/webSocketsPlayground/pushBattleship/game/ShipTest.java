package de.blau.research.niels.webSocketsPlayground.pushBattleship.game;

import org.junit.Test;

import static de.blau.research.niels.webSocketsPlayground.pushBattleship.game.Ship.Direction.horizontal;
import static de.blau.research.niels.webSocketsPlayground.pushBattleship.game.Ship.Direction.vertical;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ShipTest {
    private Ship ship;

    @Test
    public void verticalShip() {
        ship = new Ship(3, new Position(1, 1), vertical);
        assertHit(0, 0, false);
        assertHit(1, 0, false);
        assertHit(1, 1, true);
        assertHit(1, 2, true);
        assertThat(ship.isDestroyed(), is(false));
        assertHit(1, 3, true);
        assertThat(ship.isDestroyed(), is(true));
        assertHit(1, 4, false);
        for (int i = 0; i < 100; i++) {
            assertHit(0, i, false);
            assertHit(2, i, false);
        }
    }

    @Test
    public void horizontalShip() {
        ship = new Ship(3, new Position(1, 1), horizontal);
        assertHit(0, 0, false);
        assertHit(0, 1, false);
        assertHit(1, 1, true);
        assertHit(2, 1, true);
        assertThat(ship.isDestroyed(), is(false));
        assertHit(3, 1, true);
        assertThat(ship.isDestroyed(), is(true));
        assertHit(4, 1, false);
        for (int i = 0; i < 100; i++) {
            assertHit(i, 0, false);
            assertHit(i, 2, false);
        }
    }

    @Test
    public void collision() {
        assertThat(new Ship(3, new Position(1, 1), horizontal).isCollision(new Ship(3, new Position(3, 1), horizontal)), is(true));
        assertThat(new Ship(3, new Position(1, 1), horizontal).isCollision(new Ship(3, new Position(4, 1), horizontal)), is(false));
    }

    private void assertHit(int x, int y, boolean expected) {
        Position shot = new Position(x, y);
        assertThat(ship.hit(shot), is(expected));
        assertThat(ship.positions.contains(shot), is(expected));
    }
}
