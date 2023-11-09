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

}
//public static void main(String[] args) {
//	
//	System.out.println("Tests: White, Black");
//    System.out.println(stringToMove("+WQE2"));
//    System.out.println(stringToMove("+wQE2"));
//    System.out.println(stringToMove("+BQE2"));
//    System.out.println(stringToMove("+bQE2"));
//    
//    System.out.println("Tests: Pawn: {'P', ' '}");
//    System.out.println(stringToMove("+WPE2"));
//    System.out.println(stringToMove("+W E2"));
//    
//    System.out.println("Tests: isHit, isHit en");
//    System.out.println(stringToMove("RA2xA3"));
//    System.out.println(stringToMove("RA2A3"));
//    System.out.println(stringToMove("PA5xB6en"));
//    System.out.println(stringToMove(" A4xB6en"));
//    
//    System.out.println("Tests: PawnConversion");
//    System.out.println(stringToMove("PA7A8-Q"));
//    System.out.println(stringToMove("PA7A8-R"));
//    System.out.println(stringToMove("PA7A8-N"));
//    System.out.println(stringToMove("PA7A8-B"));
//    System.out.println(stringToMove("PA7xA8-Q"));
//    
//    System.out.println("Tests: DoubleMove");
//    System.out.println(stringToMove("PA2A4"));
//    System.out.println(stringToMove("PA7A5"));
//    
//    System.out.println("Exceptions:");
//    System.out.println(stringToMove("+QE2")); //result: Exception, isWhite = true
////    System.out.println(StringToMove("e4"));
//}