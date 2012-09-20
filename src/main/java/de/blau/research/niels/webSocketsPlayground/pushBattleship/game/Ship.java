package de.blau.research.niels.webSocketsPlayground.pushBattleship.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Ship {
    private final int length;
    private final Direction direction;
    private final HashSet<Position> destroyedParts = new HashSet<Position>();
    public final ArrayList<Position> positions = new ArrayList<Position>();

    public Ship(int length, Position position, Direction direction) {
        this.length = length;
        this.direction = direction;
        generatePositions(position);
    }

    private void generatePositions(Position position) {
        for (int i = 0; i < length; i++) {
            int x = position.x;
            int y = position.y;
            if (direction == Direction.vertical) {
                y += i;
            } else {
                x += i;
            }
            positions.add(new Position(x, y));
        }
    }

    public boolean hit(Position shot) {

        boolean result = positions.contains(shot);
        if (result) {
            destroyedParts.add(shot);
        }
        return result;
    }

    public boolean isDestroyed() {
        return destroyedParts.size() == length;
    }

    public boolean isCollision(Ship other) {
        for (Position otherPosition : other.positions) {
            if (positions.contains(otherPosition)) {
                return true;
            }
        }
        return false;
    }


    public static enum Direction {
        vertical, horizontal;

        public static Direction random() {
            return values()[new Random().nextInt(values().length)];
        }
    }

}
