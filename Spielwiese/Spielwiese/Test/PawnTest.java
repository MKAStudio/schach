import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PawnTest {
	private static Schachbrett testBoard = new Schachbrett();
	
	private static Stream<Arguments> getMovesTest() {
		return Stream.of(
				//Move
				Arguments.of(setFigures(List.of(new Pawn(new PositionCoordinate(2,2), true, testBoard))), new PositionCoordinate(2,2), List.of(new Move("PC3C4", "P", new PositionCoordinate(2,2), new PositionCoordinate(2,3))), new History()),
				Arguments.of(setFigures(List.of(new Pawn(new PositionCoordinate(2,1), true, testBoard))), new PositionCoordinate(2,1), List.of(new Move("PC2C3", "P", new PositionCoordinate(2,1), new PositionCoordinate(2,2)), new Move("PC2C4", "P", new PositionCoordinate(2,1), new PositionCoordinate(2,3))), new History()),
				//Hit Figures
				Arguments.of(setFigures(List.of(new Pawn(new PositionCoordinate(2,2), true, testBoard), new Pawn(new PositionCoordinate(3,2), true, testBoard))), new PositionCoordinate(2,2), List.of(new Move("PC3C4", "P", new PositionCoordinate(2,2), new PositionCoordinate(2,3))), new History()),
				Arguments.of(setFigures(List.of(new Pawn(new PositionCoordinate(2,2), true, testBoard), new Pawn(new PositionCoordinate(3,3), true, testBoard))), new PositionCoordinate(2,2), List.of(new Move("PC3C4", "P", new PositionCoordinate(2,2), new PositionCoordinate(2,3))), new History()),
				Arguments.of(setFigures(List.of(new Pawn(new PositionCoordinate(2,2), true, testBoard), new Pawn(new PositionCoordinate(1,3), true, testBoard))), new PositionCoordinate(2,2), List.of(new Move("PC3C4", "P", new PositionCoordinate(2,2), new PositionCoordinate(2,3))), new History()),
				
				Arguments.of(setFigures(List.of(new Pawn(new PositionCoordinate(2,2), true, testBoard), new Pawn(new PositionCoordinate(3,2), false, testBoard))), new PositionCoordinate(2,2), List.of(new Move("PC3C4", "P", new PositionCoordinate(2,2), new PositionCoordinate(2,3))), new History()),
				Arguments.of(setFigures(List.of(new Pawn(new PositionCoordinate(2,2), true, testBoard), new Pawn(new PositionCoordinate(3,3), false, testBoard))), new PositionCoordinate(2,2), List.of(new Move("PC3C4", "P", new PositionCoordinate(2,2), new PositionCoordinate(2,3)), new Move("PC3xD4", "P", new PositionCoordinate(2,2), new PositionCoordinate(3,3), true)), new History()),
				Arguments.of(setFigures(List.of(new Pawn(new PositionCoordinate(2,2), true, testBoard), new Pawn(new PositionCoordinate(1,3), false, testBoard))), new PositionCoordinate(2,2), List.of(new Move("PC3C4", "P", new PositionCoordinate(2,2), new PositionCoordinate(2,3)), new Move("PC3xB4", "P", new PositionCoordinate(2,2), new PositionCoordinate(1,3), true)), new History()),
				//en(?)
				//pawnConversion
				Arguments.of(setFigures(List.of(new Pawn(new PositionCoordinate(2,6), true, testBoard))), new PositionCoordinate(2,6), List.of(new Move("PC7C8-Q", "P", new PositionCoordinate(2,6), new PositionCoordinate(2,7), false, false, false, "Q"), new Move("PC7C8-R", "P", new PositionCoordinate(2,6), new PositionCoordinate(2,7), false, false, false, "R"), new Move("PC7C8-N", "P", new PositionCoordinate(2,6), new PositionCoordinate(2,7), false, false, false, "N"), new Move("PC7C8-B", "P", new PositionCoordinate(2,6), new PositionCoordinate(2,7), false, false, false, "B")), new History()),
				//en
				Arguments.of(setFigures(List.of(new Pawn(new PositionCoordinate(2, 4), true, testBoard), new Pawn(new PositionCoordinate(1, 4), true, testBoard))), new PositionCoordinate(2,4), List.of(new Move("PC5C6", "P", new PositionCoordinate(2,4), new PositionCoordinate(2,5)), new Move("PC5xB6en", "P", new PositionCoordinate(2,4), new PositionCoordinate(1,5), true, true, false, null)), new History(List.of(new Move("PB7B4", "P", new PositionCoordinate(1,6), new PositionCoordinate(1,4), false, false, true, null))))
				);
	}
	
	@ParameterizedTest
	@MethodSource
	//@Test
	void getMovesTest(Figure[][] position, PositionCoordinate testPosition, List<Move> expectedMoves, History history) {
		//Schachbrett testBoard = new Schachbrett(position);
		testBoard.setBoardPosition(position);
		testBoard.setHistory(history);
		
		List<Move> resultMoves = testBoard.getMoves(testPosition, false);
		for (Move move: resultMoves) {
			System.out.println(move);
		}
		assertEquals(expectedMoves, resultMoves);
	}
	
	static Figure[][] setFigures(List<Figure> figures) {
		Figure[][] board = new Figure[8][8];
		for (int i=0; i<figures.size(); i++) {
			PositionCoordinate position = figures.get(i).getPosition();
			board[position.getRow()][position.getCol()] = figures.get(i);
		}
		return board;
	}

}
