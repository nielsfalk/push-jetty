package de.blau.research.niels.webSocketsPlayground.pushBattleship.game;


import org.junit.Test;

import java.util.Arrays;

import static de.blau.research.niels.webSocketsPlayground.pushBattleship.game.Field.CellState.hit;
import static de.blau.research.niels.webSocketsPlayground.pushBattleship.game.Field.CellState.tried;
import static de.blau.research.niels.webSocketsPlayground.pushBattleship.game.Ship.Direction.horizontal;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FieldTest {

    @Test
    public void randomField() {
        Field field = new Field();
        assertThat(field.ships.size(), is(7));
        System.out.println(field);
    }

    @Test
    public void hitAllPositions_allShipsShouldSink() {
        Field field = new Field();
        assertThat(field.allShipsDestroyed(), is(false));

        for (int y = 0; y < field.rules.fieldSize.y; y++) {
            for (int x = 0; x < field.rules.fieldSize.x; x++) {
                field.shout(new Position(x, y));
            }
        }
        assertThat(field.allShipsDestroyed(), is(true));
    }

    @Test
    public void fieldWithOnShip_destroyShip_allShipsShouldSink() {
        Ship ship = new Ship(3, new Position(0, 0), horizontal);
        Field field = new Field(Arrays.asList(ship));
        assertThat(field.shout(new Position(9, 9)), is(tried));
        assertThat(field.shout(new Position(0, 0)), is(hit));
        assertThat(field.shout(new Position(1, 0)), is(hit));
        assertThat(field.allShipsDestroyed(), is(false));
        assertThat(field.shout(new Position(2, 0)), is(hit));
        assertThat(field.allShipsDestroyed(), is(true));
    }
}
