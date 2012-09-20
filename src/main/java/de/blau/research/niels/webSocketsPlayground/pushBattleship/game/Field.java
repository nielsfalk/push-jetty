package de.blau.research.niels.webSocketsPlayground.pushBattleship.game;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static de.blau.research.niels.webSocketsPlayground.pushBattleship.game.Field.Cell.tried;

public class Field {
    public final List<Ship> ships;
    private final List<Position> shipPositions = new ArrayList<Position>();
    private final List<Position> shots = new ArrayList<Position>();
    final Rules rules;

    /*only for test*/
    Field(List<Ship> ships) {
        this.ships = ships;
        this.rules = Rules.standard;
        for (Ship ship : ships) {
            shipPositions.addAll(ship.positions);
        }
    }

    public Field() {
        this(Rules.standard.generateShips());
    }


    public boolean allShipsDestroyed() {
        for (Ship ship : ships) {
            if (!ship.isDestroyed()) {
                return false;
            }
        }
        return true;
    }

    public Cell shout(Position shot) {
        shots.add(shot);
        for (Ship ship : ships) {
            if (ship.hit(shot)) {
                return Cell.hit;
            }
        }
        return tried;
    }

    public String toJson() {
        JSONObject ret = new JSONObject();
        ArrayList<String> ships = new ArrayList<String>();
        for (Position shipPosition : shipPositions) {
            ships.add('"' + shipPosition.toString() + '"');
        }
        ret.put("ships", ships);
        return ret.toString();
    }

    public static enum Rules {
        standard(new Position(20, 10), Arrays.asList(2, 2, 3, 3, 4, 4, 5));

        public final Position fieldSize;
        public final List<Integer> shipLengths;


        Rules(Position fieldSize, List<Integer> shipLengths) {
            this.fieldSize = fieldSize;
            this.shipLengths = shipLengths;
        }

        public List<Ship> generateShips() {
            List<Ship> result = new ArrayList<Ship>();
            for (Integer shipLength : shipLengths) {
                Ship ship;
                do {
                    ship = new Ship(shipLength, Position.random(this), Ship.Direction.random());
                } while (shipOutsideFieldOrInCollision(ship, result));
                result.add(ship);
            }
            return result;
        }

        private boolean shipOutsideFieldOrInCollision(Ship ship, List<Ship> otherShips) {
            for (Position shipPosition : ship.positions) {
                if (shipPosition.x >= fieldSize.x || shipPosition.y >= fieldSize.y) {
                    return true;
                }
            }
            for (Ship otherShip : otherShips) {
                if (ship.isCollision(otherShip)) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public String toString() {
        String result = "";
        for (int y = 0; y < rules.fieldSize.y; y++) {
            result += result.isEmpty() ? "" : "\n";
            for (int x = 0; x < rules.fieldSize.x; x++) {
                Position position = new Position(x, y);
                result += Cell.get(shipPositions.contains(position), shots.contains(position)).toChar();
            }
        }
        return result;
    }

    public static enum Cell {
        nothingYet(' '), tried('.'), notYetDetected('$'), hit('X');
        private final char c;


        Cell(char c) {
            this.c = c;
        }

        public char toChar() {
            return c;
        }

        public static Cell get(boolean shipHere, boolean alreadyShot) {
            return shipHere ? alreadyShot ? hit : notYetDetected : alreadyShot ? tried : nothingYet;
        }
    }

}
