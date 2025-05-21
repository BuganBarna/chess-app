package chess.state;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import java.util.*;

/**
 * A játék logikája
 */

@Value
@AllArgsConstructor
@With
public class ChessState {

    private static final int HEIGHT = 2;

    private static final int WIDE = 3;

    private static final int KING = 0;

    private static final int ROOK = 1;

    private static final int ROOK_TWO = 2;

    private static final int BISHOP = 3;

    private static final int BISHOP_TWO = 4;

    private final List<Position> positions;

    private static final List<Position> BASIC_STATE = List.of(
            new Position(0, 0),
            new Position(1, 0),
            new Position(1, 1),
            new Position(0, 1),
            new Position(0, 2)
    );

    /**
     * Sakktáblán figurak poziciójának felvétele
     * {@code ChessState} létrehozása
     */
    public ChessState() {
        this.positions = BASIC_STATE;
    }

    /**
     * Adott figurának lehetséges lépését ellenőrzi
     * @param direction A figura adott poziciójától való lépés
     * @param chessPiece Figura azonosítója
     * @return érvényes lépés sikerességét adja vissza
     */
    public boolean checkMove(Position.Direction direction, final int chessPiece) {
        if(chessPiece == KING) {
            return switch (direction) {
                case UP -> canMoveUp(chessPiece);
                case RIGHT -> canMoveRight(chessPiece);
                case DOWN -> canMoveDown(chessPiece);
                case LEFT -> canMoveLeft(chessPiece);
                case DIAGONAL_TOP_RIGHT -> canMoveDiagonalTopRight(chessPiece);
                case DIAGONAL_TOP_LEFT -> canMoveDiagonalTopLeft(chessPiece);
                case DIAGONAL_DOWN_RIGHT -> canMoveDiagonalDownRight(chessPiece);
                case DIAGONAL_DOWN_LEFT -> canMoveDiagonalDownLeft(chessPiece);
            };
        }
        if(chessPiece == ROOK || chessPiece == ROOK_TWO) {
            return switch (direction) {
                case UP -> canMoveUp(chessPiece);
                case RIGHT -> canMoveRight(chessPiece);
                case DOWN -> canMoveDown(chessPiece);
                case LEFT -> canMoveLeft(chessPiece);
                default -> false;
            };
        }
        if(chessPiece == BISHOP || chessPiece == BISHOP_TWO) {
            return switch (direction) {
                case DIAGONAL_TOP_RIGHT -> canMoveDiagonalTopRight(chessPiece);
                case DIAGONAL_TOP_LEFT -> canMoveDiagonalTopLeft(chessPiece);
                case DIAGONAL_DOWN_RIGHT -> canMoveDiagonalDownRight(chessPiece);
                case DIAGONAL_DOWN_LEFT -> canMoveDiagonalDownLeft(chessPiece);
                default -> false;
            };
        }
        return false;
    }

    /**
     * Adott figura mozgatása a sakktáblán
     * @param direction A figura adott poziciójától való lépés
     * @param chessPiece Figura azonosítója
     * @return Új sakktáblát adja vissza a figura új pozícójával
     */
    public ChessState doMove(
            @NonNull final Position.Direction direction,
            final int chessPiece) {
        if(chessPiece == KING) {
            return switch (direction) {
                case UP:
                    yield move(Position.Direction.UP, chessPiece);
                case RIGHT:
                    yield move(Position.Direction.RIGHT, chessPiece);
                case DOWN:
                    yield move(Position.Direction.DOWN, chessPiece);
                case LEFT:
                    yield move(Position.Direction.LEFT, chessPiece);
                case DIAGONAL_TOP_RIGHT:
                    yield move(Position.Direction.DIAGONAL_TOP_RIGHT, chessPiece);
                case DIAGONAL_TOP_LEFT:
                    yield move(Position.Direction.DIAGONAL_TOP_LEFT, chessPiece);
                case DIAGONAL_DOWN_RIGHT:
                    yield move(Position.Direction.DIAGONAL_DOWN_RIGHT, chessPiece);
                case DIAGONAL_DOWN_LEFT:
                    yield move(Position.Direction.DIAGONAL_DOWN_LEFT, chessPiece);
            };
        }

        if(chessPiece == ROOK || chessPiece == ROOK_TWO ) {
            return switch (direction) {
                case UP:
                    yield move(Position.Direction.UP, chessPiece);
                case RIGHT:
                    yield move(Position.Direction.RIGHT, chessPiece);
                case DOWN:
                    yield move(Position.Direction.DOWN, chessPiece);
                case LEFT:
                    yield move(Position.Direction.LEFT, chessPiece);
                default:
                    yield null;
            };
        }

        if(chessPiece == BISHOP || chessPiece == BISHOP_TWO) {
            return switch (direction) {
                case DIAGONAL_TOP_RIGHT:
                    yield move(Position.Direction.DIAGONAL_TOP_RIGHT, chessPiece);
                case DIAGONAL_TOP_LEFT:
                    yield move(Position.Direction.DIAGONAL_TOP_LEFT, chessPiece);
                case DIAGONAL_DOWN_RIGHT:
                    yield move(Position.Direction.DIAGONAL_DOWN_RIGHT, chessPiece);
                case DIAGONAL_DOWN_LEFT:
                    yield move(Position.Direction.DIAGONAL_DOWN_LEFT, chessPiece);
                default:
                    yield null;
            };
        }
        return this;
    }

    private boolean isEmpty(@NonNull final Position position) {

        return positions.stream()
                .noneMatch(p -> Objects.equals(p, position));
    }

    /**
     * Adott figura pozíciója
     * @param n Figura azonosítója
     * @return Figura pozicíója
     */
    public Position getPosition(final int n) {
        return positions.get(n);
    }

    private boolean canMoveUp(final int chessPiece) {
        return positions.get(chessPiece).getRow() > 0 && isEmpty(positions.get(chessPiece).getNeighbor(Position.Direction.UP));
    }

    private boolean canMoveDown(final int chessPiece) {
        return (getPosition(chessPiece).getRow() < HEIGHT - 1) && isEmpty(getPosition(chessPiece).getNeighbor(Position.Direction.DOWN));
    }

    private boolean canMoveRight(final int chessPiece) {
        return (getPosition(chessPiece).getCol() < WIDE - 1) && isEmpty(getPosition(chessPiece).getNeighbor(Position.Direction.RIGHT));
    }

    private boolean canMoveLeft(final int chessPiece) {
        return getPosition(chessPiece).getCol() > 0 && isEmpty(getPosition(chessPiece).getNeighbor(Position.Direction.LEFT));
    }

    private boolean canMoveDiagonalTopRight(final int chessPiece) {
        return (getPosition(chessPiece).getRow() > 0 && getPosition(chessPiece).getCol() < WIDE -1)
                && isEmpty(getPosition(chessPiece).getNeighbor(Position.Direction.DIAGONAL_TOP_RIGHT));
    }

    private boolean canMoveDiagonalTopLeft(final int chessPiece) {
        return (getPosition(chessPiece).getRow() > 0 && getPosition(chessPiece).getCol() > 0)
                && isEmpty(getPosition(chessPiece).getNeighbor(Position.Direction.DIAGONAL_TOP_LEFT));
    }

    private boolean canMoveDiagonalDownRight(final int chessPiece) {
        return (getPosition(chessPiece).getRow() < 1 && getPosition(chessPiece).getCol() < WIDE -1)
                && isEmpty(getPosition(chessPiece).getNeighbor(Position.Direction.DIAGONAL_DOWN_RIGHT));
    }

    private boolean canMoveDiagonalDownLeft(final int chessPiece) {
        return (getPosition(chessPiece).getRow() < 1 && getPosition(chessPiece).getCol() > 0)
                && isEmpty(getPosition(chessPiece).getNeighbor(Position.Direction.DIAGONAL_DOWN_LEFT));
    }

    private ChessState move(@NonNull Position.Direction direction,
                            @NonNull final int chessPiece) {

        final var newPositions = new ArrayList<>(this.positions);
        final var newPosition = getPosition(chessPiece).getNeighbor(direction);
        newPositions.set(chessPiece, newPosition);
        return withPositions(Collections.unmodifiableList(newPositions));
    }

    /**
     * Ellenőrzi hogy a sakktáblán lévő figurák elérték-e a célállapotott
     * @return Célállapot sikeressége
     */
    public boolean checkWin() {
        if(Objects.equals(getPosition(KING), new Position(1,2)))
        {
            if(Objects.equals(getPosition(ROOK), new Position(1,0)) || Objects.equals(getPosition(ROOK), new Position(1,1)))
            {
                if(Objects.equals(getPosition(ROOK_TWO), new Position(1,0)) || Objects.equals(getPosition(ROOK_TWO), new Position(1,1)))
                {
                    if(Objects.equals(getPosition(BISHOP), new Position(0,0)) || Objects.equals(getPosition(BISHOP), new Position(0,1)))
                    {
                        return Objects.equals(getPosition(BISHOP_TWO), new Position(0, 0)) || Objects.equals(getPosition(BISHOP_TWO), new Position(0, 1));

                    }
                }
            }
        }
        return false;
    }

    /**
     * Sakktábla figuráinak pozicíója
     * @return figurák pozicíója listában
     */
    public final List<Position> getChessStateListPosition() {
        return  positions;
    }


}
