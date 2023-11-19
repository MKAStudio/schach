import java.util.List;

public class MoveFactory {
    public static Move stringToMove(String moveStr) {
        PositionCoordinate firstCoordinate;
        PositionCoordinate lastCoordinate;
        String figure;
        Character firstChar = moveStr.charAt(0);
        if (firstChar == '+') {
        	boolean isWhite;
        	String isWhiteStr = moveStr.substring(1,2);
        	switch (isWhiteStr) {
        	case "W": isWhite = true; break;
        	case "w": isWhite = true; moveStr = moveStr.replaceFirst("w", "W"); break;
        	case "B": isWhite = false; break;
        	case "b": isWhite = false; moveStr = moveStr.replaceFirst("b", "B"); break;
        	default: System.out.println("Exception: " + isWhiteStr + " is not a Valid input"); System.out.println("isWhite is set to true."); isWhite=true;
        	}
            figure = moveStr.substring(2, 3);
            if (figure.equals(" ")) {
                figure = "P";
                moveStr = moveStr.replaceFirst(" ", "P");
            }
            firstCoordinate = Position.getCoordinate(moveStr.substring(3));
            return new Move(moveStr, figure, isWhite, firstCoordinate);
        }
        else {
            figure = moveStr.substring(0,1);
            if (figure.equals(" ")) {
                figure = "P";
                moveStr = moveStr.replaceFirst(" ", "P");
            }
            firstCoordinate = Position.getCoordinate(moveStr.substring(1,3));
            boolean isHitFigure = false;
            boolean isEn = false;
            String pawnConversion = "";
            if (moveStr.substring(3,4).equals("x")) {
                //hit Figure
            	isHitFigure = true;
            	if (moveStr.length() == 6) {
	                lastCoordinate = Position.getCoordinate(moveStr.substring(4));
            	}
            	else {
            		lastCoordinate = Position.getCoordinate(moveStr.substring(4, 6));
            		if (moveStr.substring(6).equals("en")) {
            			if (figure.equals("P")) {
            				isEn = true;
            			}
            			else {
            				throw new IllegalArgumentException("Only the Pawn can hit En.");
            			}
            		}
            		else if (moveStr.substring(6, 7).equals("-")) {
            			//Bauernumwandlung
            			if (figure.equals("P")) {
//            				System.out.println(moveStr.substring(7,8));
            				pawnConversion = moveStr.substring(7,8);
            			}
            			else {
            				throw new IllegalArgumentException("Only the pawn can be converted into another piece.");
            			}
            		}
            		else {
            			throw new IllegalArgumentException("Zu Lang!");
            		}
            	}
            }
            else {
            	System.out.println(moveStr.length());
            	if (moveStr.length() > 5) {
            		lastCoordinate = Position.getCoordinate(moveStr.substring(3,5));
            		if (moveStr.substring(5, 6).equals("-")) {
            			pawnConversion = moveStr.substring(6, 7);
//            			System.out.println("pawnConversion=" + pawnConversion);
            		}
            	}
            	else {
//            		System.out.println(moveStr.substring(3));
            		lastCoordinate = Position.getCoordinate(moveStr.substring(3));
            	}
            }
            boolean isDoubleMove = false;
            if (figure.equals("P")) {
            	if (firstCoordinate.getRow() == 1 || firstCoordinate.getRow() == 6) {
            		if (lastCoordinate.getRow() == 3 || lastCoordinate.getRow() == 4) {
            			isDoubleMove = true;
            		}
}
            }
            return new Move(moveStr, figure, firstCoordinate, lastCoordinate, isHitFigure, isEn, isDoubleMove, pawnConversion);
        }
    }

    //Test Method for String to Move()
    public static void main(String[] args) {
    	
    	System.out.println("Tests: White, Black");
        System.out.println(stringToMove("+WQE2"));
        System.out.println(stringToMove("+wQE2"));
        System.out.println(stringToMove("+BQE2"));
        System.out.println(stringToMove("+bQE2"));
        
        System.out.println("Tests: Pawn: {'P', ' '}");
        System.out.println(stringToMove("+WPE2"));
        System.out.println(stringToMove("+W E2"));
        
        System.out.println("Tests: isHit, isHit en");
        System.out.println(stringToMove("RA2xA3"));
        System.out.println(stringToMove("RA2A3"));
        System.out.println(stringToMove("PA5xB6en"));
        System.out.println(stringToMove(" A4xB6en"));
        
        System.out.println("Tests: PawnConversion");
        System.out.println(stringToMove("PA7A8-Q"));
        System.out.println(stringToMove("PA7A8-R"));
        System.out.println(stringToMove("PA7A8-N"));
        System.out.println(stringToMove("PA7A8-B"));
        System.out.println(stringToMove("PA7xA8-Q"));
        
        System.out.println("Tests: DoubleMove");
        System.out.println(stringToMove("PA2A4"));
        System.out.println(stringToMove("PA7A5"));
        
        System.out.println("Exceptions:");
        System.out.println(stringToMove("+QE2")); //result: Exception, isWhite = true
//        System.out.println(StringToMove("e4"));
    }
    
    public static Move stringToMove(String moveStr, Schachbrett schachbrett) {
        String editMoveStr = moveStr.toString();
    	PositionCoordinate firstCoordinate;
        PositionCoordinate lastCoordinate;
        String figureStr = "";
        boolean isEn = false;
        boolean isHitFigure = false;
        String pawnConversion = "";
        ConditionCoordinateStr firstCoordinateCC = new ConditionCoordinateStr(ConditionCoordinateStr.TYPE.NULL, "", editMoveStr);
        ConditionCoordinateStr lastCoordinateCC = new ConditionCoordinateStr(ConditionCoordinateStr.TYPE.NULL, "", editMoveStr);
        boolean isCastling;
        boolean isShortCastling;
        
        Character firstChar = editMoveStr.charAt(0);
        
        boolean isAddFigure = false;
        if (firstChar == '+') {
        	isAddFigure = true;
        	editMoveStr = editMoveStr.substring(1);
//            figure = moveStr.substring(2, 3);
//            if (figure.equals(" ")) {
//                figure = "P";
//                moveStr = moveStr.replaceFirst(" ", "P");
//            }
//            firstCoordinate = Position.getCoordinate(moveStr.substring(3));
//            return new Move(moveStr, figure, isWhite, firstCoordinate);
        }
        
        //White, Black
        Boolean isWhite;
    	String isWhiteStr = editMoveStr.substring(0,1);
    	switch (isWhiteStr) {
    	case "W": isWhite = true; editMoveStr = editMoveStr.substring(1); break;
    	case "w": isWhite = true; editMoveStr = editMoveStr.substring(1); moveStr = moveStr.replaceFirst("w", "W"); break;
    	case "B": isWhite = false; editMoveStr = editMoveStr.substring(1); break;
    	case "b": isWhite = false; editMoveStr = editMoveStr.substring(1); moveStr = moveStr.replaceFirst("b", "B"); break;
    	default: System.out.println("Exception: " + isWhiteStr + " is not a Valid input"); System.out.println("isWhite is set to null."); isWhite=null;
    	}
    	
    	isCastling = false;
    	isShortCastling = false;
    	if (editMoveStr.equals("O-O") || editMoveStr.equals("o-o") || editMoveStr.equals("0-0")) {
    		//Kurze Rochade
    		isCastling = true;
    		isShortCastling = true;
    		figureStr = "K";
    		editMoveStr = "";
    	}
    	if (moveStr.equals("O-O-O") || moveStr.equals("o-o-o") || moveStr.equals("0-0-0")) {
    		//lange Rochade
    		isCastling = true;
    		figureStr = "K";
    		editMoveStr = "";
    	}
    	
    	if (editMoveStr.length() > 0) {
            figureStr = editMoveStr.substring(0,1);
            
            switch (figureStr) {
        	case "Q": editMoveStr = editMoveStr.substring(1); break;
        	case "D": editMoveStr = editMoveStr.substring(1); moveStr = moveStr.replaceFirst("D", "Q"); break;
        	case "R": editMoveStr = editMoveStr.substring(1); break;
        	case "T": editMoveStr = editMoveStr.substring(1); moveStr = moveStr.replaceFirst("T", "R"); break;
        	case "N": editMoveStr = editMoveStr.substring(1); break;
        	case "S": editMoveStr = editMoveStr.substring(1); moveStr = moveStr.replaceFirst("S", "N"); break;
        	case "B": editMoveStr = editMoveStr.substring(1); break;
        	case "L": editMoveStr = editMoveStr.substring(1); moveStr = moveStr.replaceFirst("L", "B"); break;
        	case "K": editMoveStr = editMoveStr.substring(1); break;
        	case "P": editMoveStr = editMoveStr.substring(1); break;
        	case " ": editMoveStr = editMoveStr.substring(1); moveStr = moveStr.replaceFirst(" ", "P"); figureStr = "P"; break;        	
        	default: System.out.println("Exception: " + figureStr + " is not a Valid input or Pawn"); figureStr="P"; System.out.println("figureStr is set to P");
        	}
            
            if (figureStr.equals("P") && editMoveStr.length() > 0) {
            	//isEn = (editMoveStr.substring(editMoveStr.length() - 3).equals("en")) ? true: false; 
            	System.out.println(editMoveStr + ", " + editMoveStr.length());
            	if (editMoveStr.substring(editMoveStr.length() - 2).equals("en")) { //-2 oder -3?
            		isEn = true;
            		editMoveStr = editMoveStr.substring(0, editMoveStr.length() - 3); //-2 oder -3?
            	}
            		
            	if (editMoveStr.substring(editMoveStr.length() - 2, editMoveStr.length() - 1).equals("-")) {
            		pawnConversion = editMoveStr.substring(editMoveStr.length() - 1); //Switch to get Exceptions?
            		editMoveStr = editMoveStr.substring(0, editMoveStr.length() - 2);
            	}
            }
            
            //lastCoordinate
            lastCoordinateCC = getCoordinateStr(editMoveStr, editMoveStr.length() - 2); //Rc3xc5 length = 6; c5: (length - 2,length - 1)
            editMoveStr = lastCoordinateCC.getEditMoveStr();

            if (editMoveStr.length() > 0) {
	            if ((editMoveStr.substring(editMoveStr.length() - 1).equals("x") || editMoveStr.substring(0, 1).equals("X")) ) {
	            	isHitFigure = true;
	            	editMoveStr = editMoveStr.substring(0, editMoveStr.length() - 1);
	            }
	            
	            //Bedingungen firstCoordinate
	            firstCoordinateCC = getCoordinateStr(editMoveStr, editMoveStr.length() - 2);
	            editMoveStr = firstCoordinateCC.getEditMoveStr();
	            String conditionFirstCoordinate = firstCoordinateCC.getCoordinateStr();
            }

            //get firstPositionCoordinate
    	}
            
            String firstCoordinateStr = "";
            //firstCoordinate
            if (isAddFigure == false) {
            	List <Move> possibleMoves = schachbrett.getAllMoves();
//            	List <Move> possibleMoves = schachbrett.getMoves(Position.getCoordinate(firstCoordinateCC.getCoordinateStr()), false);
            	if (isCastling == false) {
	            	for (Move move: possibleMoves) {
	            		//Enum conditionFirstCoordinateStrType == NULL, ROW, COL, ROWCOL
	            		//Type == NULL: move.goalCoordinate = goalCoordinate
	            		//Type == ROW, COL, ROWCOL move.goalCoordinate = goalCoordinate if move.goalCoordinate.contains(Value of(ROW, COL, ROWCOL))
	            		boolean valid = false;
	            		switch (firstCoordinateCC.getType()) { //prüfen ob es noch weitere mögliche Züge gibt und wenn Fehler ausgeben?
		            	case NULL:
		            		valid = validate(figureStr, isAddFigure, isWhite, isCastling, isShortCastling, isEn, pawnConversion,
									firstCoordinateCC, lastCoordinateCC, isHitFigure, move);
		            	case ROW:
		            		if (move.getLastPosition().getCoordinateB() == lastCoordinateCC.getCoordinateStr() && move.getFirstPosition().getRow().toString() == firstCoordinateCC.getCoordinateStr()) {
		            			valid = validate(figureStr, isAddFigure, isWhite, isCastling, isShortCastling, isEn, pawnConversion,
										firstCoordinateCC, lastCoordinateCC, isHitFigure, move);
	            			}
		            	case COL:
		            		if (move.getLastPosition().getCoordinateB() == lastCoordinateCC.getCoordinateStr() && move.getFirstPosition().getCol().toString() == firstCoordinateCC.getCoordinateStr()) {
		            		valid = validate(figureStr, isAddFigure, isWhite, isCastling, isShortCastling, isEn, pawnConversion,
									firstCoordinateCC, lastCoordinateCC, isHitFigure, move);
	            			}
		            	case ROWCOL:
		            		if (move.getLastPosition().getCoordinateB().equals(lastCoordinateCC.getCoordinateStr()) && move.getFirstPosition().getCoordinateB().equals(firstCoordinateCC.getCoordinateStr())) {
		            		valid = validate(figureStr, isAddFigure, isWhite, isCastling, isShortCastling, isEn, pawnConversion,
									firstCoordinateCC, lastCoordinateCC, isHitFigure, move);
	            			}
		            	}
	            		if (valid) {
	            			return move;
	            		}
	            	}
            	}
            	else {
            		for (Move move : possibleMoves) {
            			if (move.isCastling()== isCastling && move.isShortCastling() == isShortCastling) {
            				if (isWhite == null) {
            					Boolean activeColor = schachbrett.getActiveColor();
            					if (activeColor == null) {
            						System.out.println(new Exception("activeColor == null, keine Prüfung nach weiteren möglichen Zügen!"));
            						//validate() ?
            						return move;
            					}
            					else {
            						//validate() ?
            						if (move.isWhite() == isWhite) {
            							//validate() ?
            							return move;
            						}
            					}
            					
            				}
            				else if (move.isWhite() == isWhite) {
            					//validate() ?
            					return move;
            				}
            			}
            		}
            	}
//            if (editMoveStr.substring(0, 1).equals())
//            firstCoordinate = Position.getCoordinate(moveStr.substring(1,3));
//            boolean isHitFigure = false;
//            boolean isEn = false;
//            String pawnConversion = "";
            
//            boolean isDoubleMove = false;
//            if (figureStr.equals("P")) {
//            	if (firstCoordinate.getRow() == 1 || firstCoordinate.getRow() == 6) {
//            		if (lastCoordinate.getRow() == 3 || lastCoordinate.getRow() == 4) {
//            			isDoubleMove = true;
//            		}
//            	}
//            }
        }
            else {
            	return new Move(moveStr, figureStr, isWhite, Position.getCoordinate(lastCoordinateCC.getCoordinateStr()));
            }
            throw new IllegalArgumentException(moveStr + " is not a Valid input");
    }

	private static boolean validate(String figureStr, boolean isAddFigure, Boolean isWhite, boolean isCastling,
			boolean isShortCastling, boolean isEn, String pawnConversion, ConditionCoordinateStr firstCoordinateCC, ConditionCoordinateStr lastCoordinateCC,
			boolean isHitFigure, Move move) {
		boolean valid = false;
		if (move.getLastPosition().getCoordinateB().equals(lastCoordinateCC.getCoordinateStr())) {
			//test andere Eigenschaften von Move??
			//take Move or firstPosition??
			//figureStr, firstCoordinate, lastCoordinate, isAddFigure, isWhite !null, Rochade (kurz, lang), isEn, pawnConversion, isHitFigure
			valid = true;
			if ((!move.getFigure().equals(figureStr) )) {
				valid = false;
			}
			if (move.isAddFigure() != isAddFigure) {
				valid = false;
			}
			if (move.isEn() != isEn) {
				valid = false;
			}
			if (!move.getPawnConversion().equals(pawnConversion)) {
				valid = false;
			}
			if (move.isHitFigure() != isHitFigure) {
				valid = false;
			}
			if (isWhite != null) {
				if (move.isWhite() != (boolean) isWhite) {
					valid = false;
				}
			}
			if (move.isCastling() != isCastling || move.isShortCastling() != isShortCastling) {
				valid = false;
			}
		}
		return valid;
	}

	private static ConditionCoordinateStr getCoordinateStr(String editMoveStr, int beginIndex) {
		//returns List<String>.of(editMoveStr, conditionFirstCoordinate)
		String editConditionFirstCoordinate;
		
		editConditionFirstCoordinate = editMoveStr.substring(beginIndex, beginIndex + 1);
			
		String conditionFirstCoordinate = "";
		boolean isCol = false;
		boolean isRow = false;
		switch (editConditionFirstCoordinate) {
		case "a": conditionFirstCoordinate = "a"; isCol = true; break;
		case "b": conditionFirstCoordinate = "b"; isCol = true; break;
		case "c": conditionFirstCoordinate = "c"; isCol = true; break;
		case "d": conditionFirstCoordinate = "d"; isCol = true; break;
		case "e": conditionFirstCoordinate = "e"; isCol = true; break;
		case "f": conditionFirstCoordinate = "f"; isCol = true; break;
		case "g": conditionFirstCoordinate = "g"; isCol = true; break;
		case "h": conditionFirstCoordinate = "h"; isCol = true; break;
		default: System.out.println("Exception: " + conditionFirstCoordinate + " is not a Valid input");
		}
		
	    if (isCol) {
	    	if (editMoveStr.length() > beginIndex) { //> beginIndex + 1?
	    		if (beginIndex > 0) {
	    			editMoveStr = editMoveStr.substring(0, beginIndex) + editMoveStr.substring(beginIndex + 1);
	    		}
	    		else {
	    			editMoveStr = editMoveStr.substring(beginIndex + 1);
	    		}
	    	}
	    		
	    	else {
	    		editMoveStr = editMoveStr.substring(0, beginIndex + 1);
	    	}
    	}
	    editConditionFirstCoordinate = editMoveStr.substring(beginIndex, beginIndex + 1);
	    switch (editConditionFirstCoordinate) {
		case "1": conditionFirstCoordinate = conditionFirstCoordinate + "1"; isRow = true; break;
		case "2": conditionFirstCoordinate = conditionFirstCoordinate + "2"; isRow = true; break;
		case "3": conditionFirstCoordinate = conditionFirstCoordinate + "3"; isRow = true; break;
		case "4": conditionFirstCoordinate = conditionFirstCoordinate + "4"; isRow = true; break;
		case "5": conditionFirstCoordinate = conditionFirstCoordinate + "5"; isRow = true; break;
		case "6": conditionFirstCoordinate = conditionFirstCoordinate + "6"; isRow = true; break;
		case "7": conditionFirstCoordinate = conditionFirstCoordinate + "7"; isRow = true; break;
		case "8": conditionFirstCoordinate = conditionFirstCoordinate + "8"; isRow = true; break;
		default: System.out.println("Exception: " + conditionFirstCoordinate + " is not a Valid input");
	    }
		
	    if (isRow) {
	    	if (editMoveStr.length() > beginIndex) { //> beginIndex + 1?
	    		if (beginIndex > 0) {
	    			editMoveStr = editMoveStr.substring(0, beginIndex) + editMoveStr.substring(beginIndex + 1);
	    		}
	    		else {
	    			editMoveStr = editMoveStr.substring(beginIndex + 1);
	    		}
	    	}
	    		
	    	else if (editMoveStr.length() != 1){
	    		editMoveStr = editMoveStr.substring(0, beginIndex + 1);
	    	}
	    	else {
	    		editMoveStr = "";
	    	}
    	}
	    
	    if (!isRow && !isCol) {
	    	return new ConditionCoordinateStr(ConditionCoordinateStr.TYPE.NULL, conditionFirstCoordinate, editMoveStr);
	    }
	    else if (isRow && !isCol) {
			return new ConditionCoordinateStr(ConditionCoordinateStr.TYPE.ROW, conditionFirstCoordinate, editMoveStr);
		}
		else if (!isCol && isRow) {
			return new ConditionCoordinateStr(ConditionCoordinateStr.TYPE.COL, conditionFirstCoordinate, editMoveStr);
		}
		else { //(isCol && isRow)
			return new ConditionCoordinateStr(ConditionCoordinateStr.TYPE.ROWCOL, conditionFirstCoordinate, editMoveStr);
		}
		
	}
	
	static class ConditionCoordinateStr {
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return super.toString();
		}
		private enum TYPE{
			NULL,
			ROW,
			COL,
			ROWCOL;
		}
		private TYPE type;
		private String coordinateStr;
		private String editMoveStr;
		
		public TYPE getType() {
			return type;
		}
		public void setType(TYPE type) {
			this.type = type;
		}
		public String getCoordinateStr() {
			return coordinateStr;
		}
		public void setCoordinateStr(String coordinateStr) {
			this.coordinateStr = coordinateStr;
		}
		public String getEditMoveStr() {
			return editMoveStr;
		}
		public void setEditMoveStr(String editMoveStr) {
			this.editMoveStr = editMoveStr;
		}
		public ConditionCoordinateStr(MoveFactory.ConditionCoordinateStr.TYPE type, String coordinateStr,
				String editMoveStr) {
			this.type = type;
			this.coordinateStr = coordinateStr;
			this.editMoveStr = editMoveStr;
		}
		
		
		
		
	}
}
