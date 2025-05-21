package chess.state;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChessStateTest {

    private static final ChessState chessWinState;
    private static final ChessState chessNormalState;
    private static final ChessState chessBasicState;
    private static final ChessState chessBishopMoveState;

    private static final ChessState chessRookMoveState;

    static {
        chessNormalState = new ChessState(List.of(
            new Position(1, 2),
            new Position(1, 0),
            new Position(1, 1),
            new Position(0, 1),
            new Position(0, 2)
        ));
        chessWinState = new ChessState(List.of(
                new Position(1, 2),
                new Position(1, 0),
                new Position(1, 1),
                new Position(0, 0),
                new Position(0, 1)
        ));
        chessBasicState = new ChessState();

        chessBishopMoveState= new ChessState(List.of(
                new Position(0, 0),
                new Position(1, 0),
                new Position(1, 1),
                new Position(1, 2),
                new Position(0, 2)
        ));

        chessRookMoveState = new ChessState(List.of(
                new Position(1, 2),
                new Position(0, 0),
                new Position(1, 1),
                new Position(0, 1),
                new Position(0, 2)
        ));

    }

    @Test
    void ChessStateConstructorTest() {
        List<Position> chessBasicTest = List.of(
                new Position(0,0),
                new Position(1,0),
                new Position(1,1),
                new Position(0,1),
                new Position(0,2)
        );

        assertEquals(chessBasicTest, chessBasicState.getChessStateListPosition());
    }

    @Test
    void checkMoveKing() {
        assertTrue(chessWinState.checkMove(Position.Direction.UP, 0));
        assertFalse(chessNormalState.checkMove(Position.Direction.UP, 0));
        assertFalse(chessNormalState.checkMove(Position.Direction.DOWN, 0));
        assertFalse(chessNormalState.checkMove(Position.Direction.RIGHT, 0));
        assertFalse(chessNormalState.checkMove(Position.Direction.LEFT, 0));
        assertFalse(chessNormalState.checkMove(Position.Direction.DIAGONAL_TOP_LEFT, 0));
        assertFalse(chessNormalState.checkMove(Position.Direction.DIAGONAL_TOP_RIGHT, 0));
        assertFalse(chessNormalState.checkMove(Position.Direction.DIAGONAL_DOWN_LEFT, 0));
        assertFalse(chessNormalState.checkMove(Position.Direction.DIAGONAL_DOWN_RIGHT, 0));

    }

    @Test
    void checkMoveRook() {
        assertTrue(chessBasicState.checkMove(Position.Direction.RIGHT,2));
        assertFalse(chessBasicState.checkMove(Position.Direction.LEFT,1));
        assertTrue(chessNormalState.checkMove(Position.Direction.UP, 1));
        assertFalse(chessNormalState.checkMove(Position.Direction.DOWN, 1));

    }
    @Test
    void checkMoveBishop() {
        assertTrue(chessBasicState.checkMove(Position.Direction.DIAGONAL_DOWN_RIGHT,3));
        assertFalse(chessBasicState.checkMove(Position.Direction.DIAGONAL_DOWN_LEFT,3));
        assertFalse(chessBasicState.checkMove(Position.Direction.RIGHT,5));
        assertFalse(chessNormalState.checkMove(Position.Direction.DIAGONAL_TOP_LEFT, 0));
        assertFalse(chessNormalState.checkMove(Position.Direction.DIAGONAL_TOP_RIGHT, 0));
    }

    @Test
    void doMove() {
        assertEquals(chessBasicState, chessBasicState.doMove(Position.Direction.UP, 5));
        assertEquals(chessBishopMoveState, chessBasicState.doMove(Position.Direction.DIAGONAL_DOWN_RIGHT,3));
        assertEquals(chessRookMoveState,chessNormalState.doMove(Position.Direction.UP,1));

    }

    @Test
    void getPosition() {
        assertEquals(new Position(0,0),chessBasicState.getPosition(0));
        assertEquals(new Position(1,0),chessBasicState.getPosition(1));
        assertEquals(new Position(1,1),chessBasicState.getPosition(2));
        assertEquals(new Position(0,1),chessBasicState.getPosition(3));
        assertEquals(new Position(0,2),chessBasicState.getPosition(4));

    }

    @Test
    void checkWin() {
        assertFalse(chessNormalState.checkWin());
        assertTrue(chessWinState.checkWin());

    }

    @Test
    void testToString() {
        assertEquals("ChessState(positions=[(0,0), (1,0), (1,1), (0,1), (0,2)])", chessBasicState.toString());
    }
}