package chess.state;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {
    Position position;
    @BeforeEach
    void initializeLocalPosition() {
        position = new Position(1,1);
    }
    @Test
    void getNeighbor() {
        assertEquals(new Position(1,2),position.getNeighbor(Position.Direction.RIGHT));
        assertEquals(new Position(1,0),position.getNeighbor(Position.Direction.LEFT));
        assertEquals(new Position(0,1),position.getNeighbor(Position.Direction.UP));
        assertEquals(new Position(2,1),position.getNeighbor(Position.Direction.DOWN));
        assertEquals(new Position(0,2),position.getNeighbor(Position.Direction.DIAGONAL_TOP_RIGHT));
        assertEquals(new Position(0,0),position.getNeighbor(Position.Direction.DIAGONAL_TOP_LEFT));
        assertEquals(new Position(2,2),position.getNeighbor(Position.Direction.DIAGONAL_DOWN_RIGHT));
        assertEquals(new Position(2,0),position.getNeighbor(Position.Direction.DIAGONAL_DOWN_LEFT));
    }

    @Test
    void testToString() {
        assertEquals("(1,1)", position.toString());
    }

    @Test
    void getRow() {
        assertEquals(1, position.getRow());
    }

    @Test
    void getCol() {
        assertEquals(1, position.getCol());
    }

    void testOf() {
        Optional<Position.Direction> testDirectionUP = Position.Direction.of(-1,0);
        Optional<Position.Direction> testDirectionRIGHT = Position.Direction.of(0,1);
        Optional<Position.Direction> testDirectionDOWN = Position.Direction.of(1,0);
        Optional<Position.Direction> testDirectionLEFT = Position.Direction.of(0,-1);
        Optional<Position.Direction> testDirectionDIAGONAL_TOP_RIGHT = Position.Direction.of(-1,1);
        Optional<Position.Direction> testDirectionDIAGONAL_TOP_LEFT = Position.Direction.of(-1,-1);
        Optional<Position.Direction> testDirectionDIAGONAL_DOWN_RIGHT = Position.Direction.of(1,1);
        Optional<Position.Direction> testDirectionDIAGONAL_DOWN_LEFT = Position.Direction.of(1,-0);
        Optional<Position.Direction> testDirectionFalseRow = Position.Direction.of(-2,-1);
        Optional<Position.Direction> testDirectionFalseCol = Position.Direction.of(0,5);

        assertTrue(testDirectionUP.isPresent());
        assertTrue(testDirectionRIGHT.isPresent());
        assertTrue(testDirectionDOWN.isPresent());
        assertTrue(testDirectionLEFT.isPresent());
        assertFalse(testDirectionFalseRow.isPresent());
        assertFalse(testDirectionFalseCol.isPresent());

        assertEquals(Position.Direction.UP, testDirectionUP.get());
        assertEquals(Position.Direction.RIGHT, testDirectionRIGHT.get());
        assertEquals(Position.Direction.DOWN, testDirectionDOWN.get());
        assertEquals(Position.Direction.LEFT, testDirectionLEFT.get());
        assertEquals(Position.Direction.DIAGONAL_TOP_RIGHT, testDirectionDIAGONAL_TOP_RIGHT.get());
        assertEquals(Position.Direction.DIAGONAL_TOP_LEFT, testDirectionDIAGONAL_TOP_LEFT.get());
        assertEquals(Position.Direction.DIAGONAL_DOWN_RIGHT, testDirectionDIAGONAL_DOWN_RIGHT.get());
        assertEquals(Position.Direction.DIAGONAL_TOP_LEFT, testDirectionDIAGONAL_TOP_LEFT.get());
        assertEquals(Position.Direction.DIAGONAL_DOWN_LEFT, testDirectionDIAGONAL_DOWN_LEFT.get());



    }


    @Test
    public void testHashCode() {
        Position positionFirst = new Position(0, 1);
        Position positionSecond = new Position(0, 1);

        assertEquals(positionFirst, positionSecond);

        int hashCodeFirst = positionFirst.hashCode();
        int hashCodeTwo = positionSecond.hashCode();

        assertEquals(hashCodeFirst, hashCodeTwo);
    }

}