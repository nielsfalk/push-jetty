package de.blau.research.niels.webSocketsPlayground.pushBattleship.game;

import java.util.Random;

public class Position {
    public final int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(String text) {
        String[] split = text.split("-");
        x = Integer.parseInt(split[0]);
        y = Integer.parseInt(split[1]);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Position) {
            Position other = (Position) o;
            return other.x == x && other.y == y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (x * 10000) + y;
    }

    @Override
    public String toString() {
        return x + "-" + y;
    }

    public static Position random(Field.Rules rules) {
        Random random = new Random();
        return new Position(random.nextInt(rules.fieldSize.x), random.nextInt(rules.fieldSize.y));
    }

}
