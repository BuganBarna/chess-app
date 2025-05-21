package chess.state;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import java.util.Arrays;
import java.util.Optional;

/**
 * Figura pozicíójának reprezentálása
 * {@code @Value}
 */
@Value
@AllArgsConstructor
public class Position {
    int row;
    int col;

    /**
     * Adott figura következő lépésének pozicíóját adja vissza
     * @param direction Lépés iránya
     * @return Figura pozicíója
     */
    public Position getNeighbor(@NonNull final Direction direction) {

        return new Position(row + direction.getRowChange(), col + direction.getColChange());
    }

    /**
     * Figura jelenlegi pozicíójánka kiíratása
     * @return Figura pozicíója szövegesen
     */
    @Override
    public String toString() {
        return String.format("(%d,%d)", row, col);
    }

    /**
     * Lehetséges irányokat reprezentáló osztály
     */
    @Getter
    @AllArgsConstructor
    public enum Direction {

        UP(-1, 0),
        RIGHT(0, 1),
        DOWN(1, 0),
        LEFT(0, -1),
        DIAGONAL_TOP_RIGHT(-1, 1),
        DIAGONAL_TOP_LEFT(-1, -1),
        DIAGONAL_DOWN_RIGHT(1, 1),
        DIAGONAL_DOWN_LEFT(1, -1);


        private final int rowChange;
        private final int colChange;

        /**
         * Irány meghatározás
         * @param rowChange sor koordináta
         * @param colChange oszlop koordináta
         * @return Koordináta alapján meghatározott irány
         */
        public static Optional<Direction> of(
                final int rowChange,
                final int colChange) {

            return Arrays.stream(Direction.values())
                    .filter(d -> d.rowChange == rowChange && d.colChange == colChange)
                    .findFirst();
        }


    }
}
