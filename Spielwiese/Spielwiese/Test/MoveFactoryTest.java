import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.management.ValueExp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class MoveFactoryTest {
	
	private static Stream<Arguments> test() {
		return Stream.of(
				//White, Black
				Arguments.of("+WQE2", new Move("+WQE2", "Q", true, new PositionCoordinate(4,1))),
				Arguments.of("+wQE2", new Move("+WQE2", "Q", true, new PositionCoordinate(4,1))),
				Arguments.of("+BQE2", new Move("+BQE2", "Q", false, new PositionCoordinate(4,1))),
				Arguments.of("+bQE2", new Move("+BQE2", "Q", false, new PositionCoordinate(4,1))),
				//Pawn
				Arguments.of("+WPE2", new Move("+WPE2", "P", true, new PositionCoordinate(4,1))),
				Arguments.of("+W E2", new Move("+WPE2", "P", true, new PositionCoordinate(4,1))),
				Arguments.of("PE2E3", new Move("PE2E3", "P", new PositionCoordinate(4,1), new PositionCoordinate(4,2), false, false, false, "")),
				Arguments.of(" E2E3", new Move("PE2E3", "P", new PositionCoordinate(4,1), new PositionCoordinate(4,2), false, false, false, "")),
				//Move
				Arguments.of("RA2A3", new Move("RA2A3", "R", new PositionCoordinate(0,1), new PositionCoordinate(0,2), false, false, false, "")),
				Arguments.of("KA2A3", new Move("KA2A3", "K", new PositionCoordinate(0,1), new PositionCoordinate(0,2), false, false, false, "")),
				Arguments.of("NA2B4", new Move("NA2B4", "N", new PositionCoordinate(0,1), new PositionCoordinate(1,3), false, false, false, "")),
				Arguments.of("QA2A3", new Move("QA2A3", "Q", new PositionCoordinate(0,1), new PositionCoordinate(0,2), false, false, false, "")),
				//Hit
				Arguments.of("RA2xA3", new Move("RA2xA3", "R", new PositionCoordinate(0,1), new PositionCoordinate(0,2), true, false, false, "")),
				Arguments.of("KA2xA3", new Move("KA2xA3", "K", new PositionCoordinate(0,1), new PositionCoordinate(0,2), true, false, false, "")),
				Arguments.of("NA2xB4", new Move("NA2xB4", "N", new PositionCoordinate(0,1), new PositionCoordinate(1,3), true, false, false, "")),
				Arguments.of("QA2xA3", new Move("QA2xA3", "Q", new PositionCoordinate(0,1), new PositionCoordinate(0,2), true, false, false, "")),
				Arguments.of("PA2xB3", new Move("PA2xB3", "P", new PositionCoordinate(0,1), new PositionCoordinate(1,2), true, false, false, "")),
				Arguments.of(" A2xB3", new Move("PA2xB3", "P", new PositionCoordinate(0,1), new PositionCoordinate(1,2), true, false, false, "")),
				Arguments.of("PA5xB6en", new Move("PA5xB6en", "P", new PositionCoordinate(0,4), new PositionCoordinate(1,5), true, true, false, "")),
				Arguments.of(" A5xB6en", new Move("PA5xB6en", "P", new PositionCoordinate(0,4), new PositionCoordinate(1,5), true, true, false, "")),
				Arguments.of("PA4xB3en", new Move("PA4xB3en", "P", new PositionCoordinate(0,3), new PositionCoordinate(1,2), true, true, false, "")),
				Arguments.of(" A4xB3en", new Move("PA4xB3en", "P", new PositionCoordinate(0,3), new PositionCoordinate(1,2), true, true, false, "")),
				//Double Move
				Arguments.of("PA2A4", new Move("PA2A4", "P", new PositionCoordinate(0,1), new PositionCoordinate(0,3), false, false, true, "")),
				Arguments.of("PA7A5", new Move("PA7A5", "P", new PositionCoordinate(0,6), new PositionCoordinate(0,4), false, false, true, "")),
				//pawnConversion
				Arguments.of("PA7A8-Q", new Move("PA7A8-Q", "P", new PositionCoordinate(0,6), new PositionCoordinate(0,7), false, false, false, "Q")),
				Arguments.of("PA2A1-Q", new Move("PA2A1-Q", "P", new PositionCoordinate(0,1), new PositionCoordinate(0,0), false, false, false, "Q")),
				Arguments.of("PA7A8-R", new Move("PA7A8-R", "P", new PositionCoordinate(0,6), new PositionCoordinate(0,7), false, false, false, "R")),
				Arguments.of("PA2A1-R", new Move("PA2A1-R", "P", new PositionCoordinate(0,1), new PositionCoordinate(0,0), false, false, false, "R")),
				Arguments.of("PA7A8-N", new Move("PA7A8-N", "P", new PositionCoordinate(0,6), new PositionCoordinate(0,7), false, false, false, "N")),
				Arguments.of("PA2A1-N", new Move("PA2A1-N", "P", new PositionCoordinate(0,1), new PositionCoordinate(0,0), false, false, false, "N")),
				Arguments.of("PA7A8-B", new Move("PA7A8-B", "P", new PositionCoordinate(0,6), new PositionCoordinate(0,7), false, false, false, "B")),
				Arguments.of("PA2A1-B", new Move("PA2A1-B", "P", new PositionCoordinate(0,1), new PositionCoordinate(0,0), false, false, false, "B"))
				);
	}
	//new Move(moveStr, figure, firstCoordinate, lastCoordinate, isHitFigure, isEn, isDoubleMove, pawnConversion)
	@ParameterizedTest
	@MethodSource
	//@Test
	void test(String data, Move expectedMove) {
		//fail("Not yet implemented");
		MoveFactory TestClass = new MoveFactory();
//		TestClass.stringToMove("+WQE2");
//		Move expectedMove = new Move("+WQE2", "Q", true, new PositionCoordinate(4, 1));
		assertEquals(expectedMove, TestClass.stringToMove(data));
	}
	
	private static Schachbrett testBoard = new Schachbrett();
	
	private static Stream<Arguments> testStringToMoveWithChessboard() {
		return Stream.of(
				//White, Black
				Arguments.of("+WQe2", new Move("+WQe2", "Q", true, new PositionCoordinate(4,1)), setFigures(List.of())),
				Arguments.of("+wQe2", new Move("+WQe2", "Q", true, new PositionCoordinate(4,1)), setFigures(List.of())),
				Arguments.of("+BQe2", new Move("+BQe2", "Q", false, new PositionCoordinate(4,1)), setFigures(List.of())),
				Arguments.of("+bQe2", new Move("+BQe2", "Q", false, new PositionCoordinate(4,1)), setFigures(List.of())),
				//Pawn
				Arguments.of("+WPe2", new Move("+WPe2", "P", true, new PositionCoordinate(4,1)), setFigures(List.of())),
				Arguments.of("+W e2", new Move("+WPe2", "P", true, new PositionCoordinate(4,1)), setFigures(List.of())),
				Arguments.of("Pe2e3", new Move("PE2E3", "P", true, new PositionCoordinate(4,1), new PositionCoordinate(4,2), false, false, false, ""), setFigures(List.of(new Pawn(new PositionCoordinate(4, 1), true, testBoard)))),
				Arguments.of(" e2e3", new Move("PE2E3", "P", true, new PositionCoordinate(4,1), new PositionCoordinate(4,2), false, false, false, ""), setFigures(List.of(new Pawn(new PositionCoordinate(4, 1), true, testBoard)))),
				Arguments.of("e3", new Move("PE2E3", "P", true, new PositionCoordinate(4,1), new PositionCoordinate(4,2), false, false, false, ""), setFigures(List.of(new Pawn(new PositionCoordinate(4, 1), true, testBoard)))),
				//Move
				Arguments.of("Ra2a3", new Move("RA2A3", "R", true, new PositionCoordinate(0,1), new PositionCoordinate(0,2), false, false, false, ""), setFigures(List.of(new Rook(new PositionCoordinate(0, 1), true, testBoard)))),
				Arguments.of("Ka2a3", new Move("KA2A3", "K", true, new PositionCoordinate(0,1), new PositionCoordinate(0,2), false, false, false, ""), setFigures(List.of(new King(new PositionCoordinate(0, 1), true, testBoard)))),
				Arguments.of("Na2b4", new Move("NA2B4", "N", true, new PositionCoordinate(0,1), new PositionCoordinate(1,3), false, false, false, ""), setFigures(List.of(new Knight(new PositionCoordinate(0, 1), true, testBoard)))),
				Arguments.of("Qa2a3", new Move("QA2A3", "Q", true, new PositionCoordinate(0,1), new PositionCoordinate(0,2), false, false, false, ""), setFigures(List.of(new Queen(new PositionCoordinate(0, 1), true, testBoard)))),
				//Hit
				Arguments.of("Ra2xa3", new Move("RA2xA3", "R", new PositionCoordinate(0,1), new PositionCoordinate(0,2), true, false, false, ""), setFigures(List.of(new Rook(new PositionCoordinate(0, 1), true, testBoard), new Rook(new PositionCoordinate(0, 2), false, testBoard)))),
				Arguments.of("Ka2xa3", new Move("KA2xA3", "K", new PositionCoordinate(0,1), new PositionCoordinate(0,2), true, false, false, ""), setFigures(List.of(new King(new PositionCoordinate(0, 1), true, testBoard), new Rook(new PositionCoordinate(0, 2), false, testBoard)))),
				Arguments.of("Na2xb4", new Move("NA2xB4", "N", new PositionCoordinate(0,1), new PositionCoordinate(1,3), true, false, false, ""), setFigures(List.of(new Knight(new PositionCoordinate(0, 1), true, testBoard), new Rook(new PositionCoordinate(1, 3), false, testBoard)))),
				Arguments.of("Qa2xa3", new Move("QA2xA3", "Q", new PositionCoordinate(0,1), new PositionCoordinate(0,2), true, false, false, ""), setFigures(List.of(new Queen(new PositionCoordinate(0, 1), true, testBoard), new Rook(new PositionCoordinate(0, 2), false, testBoard)))),
				Arguments.of("Pa2xb3", new Move("PA2xB3", "P", new PositionCoordinate(0,1), new PositionCoordinate(1,2), true, false, false, ""), setFigures(List.of(new Pawn(new PositionCoordinate(0, 1), true, testBoard), new Rook(new PositionCoordinate(1, 2), false, testBoard)))),
				Arguments.of(" a2xb3", new Move("PA2xB3", "P", new PositionCoordinate(0,1), new PositionCoordinate(1,2), true, false, false, ""), setFigures(List.of(new Pawn(new PositionCoordinate(0, 1), true, testBoard), new Rook(new PositionCoordinate(1, 2), false, testBoard)))),
//				Arguments.of("Pa5xb6en", new Move("PA5xB6en", "P", new PositionCoordinate(0,4), new PositionCoordinate(1,5), true, true, false, "")),
//				Arguments.of(" a5xb6en", new Move("PA5xB6en", "P", new PositionCoordinate(0,4), new PositionCoordinate(1,5), true, true, false, "")),
//				Arguments.of("Pa4xb3en", new Move("PA4xB3en", "P", new PositionCoordinate(0,3), new PositionCoordinate(1,2), true, true, false, "")),
//				Arguments.of(" a4xb3en", new Move("PA4xB3en", "P", new PositionCoordinate(0,3), new PositionCoordinate(1,2), true, true, false, "")),
				//Double Move
				Arguments.of("Pa2a4", new Move("PA2A4", "P", new PositionCoordinate(0,1), new PositionCoordinate(0,3), false, false, true, ""), setFigures(List.of(new Pawn(new PositionCoordinate(0, 1), true, testBoard)))),
				Arguments.of("Pa7a5", new Move("PA7A5", "P", new PositionCoordinate(0,6), new PositionCoordinate(0,4), false, false, true, ""), setFigures(List.of(new Pawn(new PositionCoordinate(0, 6), false, testBoard)))),
				//pawnConversion
				Arguments.of("Pa7a8-Q", new Move("PA7A8-Q", "P", true, new PositionCoordinate(0,6), new PositionCoordinate(0,7), false, false, false, "Q"), setFigures(List.of(new Pawn(new PositionCoordinate(0, 6), true, testBoard)))),
				Arguments.of("Pa2a1-Q", new Move("PA2A1-Q", "P", false, new PositionCoordinate(0,1), new PositionCoordinate(0,0), false, false, false, "Q"), setFigures(List.of(new Pawn(new PositionCoordinate(0, 1), false, testBoard)))),
				Arguments.of("Pa7a8-R", new Move("PA7A8-R", "P", true, new PositionCoordinate(0,6), new PositionCoordinate(0,7), false, false, false, "R"), setFigures(List.of(new Pawn(new PositionCoordinate(0, 6), true, testBoard)))),
				Arguments.of("Pa2a1-R", new Move("PA2A1-R", "P", false, new PositionCoordinate(0,1), new PositionCoordinate(0,0), false, false, false, "R"), setFigures(List.of(new Pawn(new PositionCoordinate(0, 1), false, testBoard)))),
				Arguments.of("Pa7a8-N", new Move("PA7A8-N", "P", true, new PositionCoordinate(0,6), new PositionCoordinate(0,7), false, false, false, "N"), setFigures(List.of(new Pawn(new PositionCoordinate(0, 6), true, testBoard)))),
				Arguments.of("Pa2a1-N", new Move("PA2A1-N", "P", false, new PositionCoordinate(0,1), new PositionCoordinate(0,0), false, false, false, "N"), setFigures(List.of(new Pawn(new PositionCoordinate(0, 1), false, testBoard)))),
				Arguments.of("Pa7a8-B", new Move("PA7A8-B", "P", true, new PositionCoordinate(0,6), new PositionCoordinate(0,7), false, false, false, "B"), setFigures(List.of(new Pawn(new PositionCoordinate(0, 6), true, testBoard)))),
				Arguments.of("Pa2a1-B", new Move("PA2A1-B", "P", false, new PositionCoordinate(0,1), new PositionCoordinate(0,0), false, false, false, "B"), setFigures(List.of(new Pawn(new PositionCoordinate(0, 1), false, testBoard)))),
				//isCastling
				Arguments.of("0-0", new Move(false, true, "KE1G1", "K", new PositionCoordinate(4,0), new PositionCoordinate(6,0), false, true, true, false, false, ""), setFigures(List.of(new King(new PositionCoordinate(4, 0), true, testBoard), new Rook(new PositionCoordinate(7, 0), true, testBoard)))),
				Arguments.of("o-o", new Move(false, true, "KE1G1", "K", new PositionCoordinate(4,0), new PositionCoordinate(6,0), false, true, true, false, false, ""), setFigures(List.of(new King(new PositionCoordinate(4, 0), true, testBoard), new Rook(new PositionCoordinate(7, 0), true, testBoard)))),
				Arguments.of("O-O", new Move(false, true, "KE1G1", "K", new PositionCoordinate(4,0), new PositionCoordinate(6,0), false, true, true, false, false, ""), setFigures(List.of(new King(new PositionCoordinate(4, 0), true, testBoard), new Rook(new PositionCoordinate(7, 0), true, testBoard)))),
				Arguments.of("W0-0", new Move(false, true, "KE1G1", "K", new PositionCoordinate(4,0), new PositionCoordinate(6,0), false, true, true, false, false, ""), setFigures(List.of(new King(new PositionCoordinate(4, 0), true, testBoard), new Rook(new PositionCoordinate(7, 0), true, testBoard)))),
				Arguments.of("Wo-o", new Move(false, true, "KE1G1", "K", new PositionCoordinate(4,0), new PositionCoordinate(6,0), false, true, true, false, false, ""), setFigures(List.of(new King(new PositionCoordinate(4, 0), true, testBoard), new Rook(new PositionCoordinate(7, 0), true, testBoard)))),
				Arguments.of("WO-O", new Move(false, true, "KE1G1", "K", new PositionCoordinate(4,0), new PositionCoordinate(6,0), false, true, true, false, false, ""), setFigures(List.of(new King(new PositionCoordinate(4, 0), true, testBoard), new Rook(new PositionCoordinate(7, 0), true, testBoard))))
				);
	}
	@ParameterizedTest
	@MethodSource
	void testStringToMoveWithChessboard(String data, Move expectedMove, Figure[][] position) {
		MoveFactory TestClass = new MoveFactory();
		testBoard.setBoardPosition(position);
		assertEquals (expectedMove, TestClass.stringToMove(data, testBoard));
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
