import java.util.ArrayList;
import java.util.List;

public class King extends Figure {
    public King(PositionCoordinate aktuellePositionCoordinate, boolean isColorWhite, Schachbrett surface) {
        super(aktuellePositionCoordinate, isColorWhite, "K", surface);
    }
    
    @Override
    public List<Move> getMovesWithoutCastling(boolean ignoreColor, boolean ignoreAttackedFields) {
    	return super.getMoves(ignoreColor, ignoreAttackedFields);
    }
    
    @Override
    public List<Move> getMoves(boolean ignoreColor, boolean ignoreAttackedFields) {
		List <Move> moves = super.getMoves(ignoreColor, ignoreAttackedFields);
    	Schachbrett surface = getSurface();
    	if (!isMoved()) {
    		if (isColorWhite()) {
    			moves = getMoveCastling(moves, surface, true);
	    	}
	    	else {
	    		moves = getMoveCastling(moves, surface, false);
	    	}
    	}
    	 	
    	return moves;
    }

	private List<Move> getMoveCastling(List<Move> moves, Schachbrett surface, boolean isColorWhite) {
		int color = isColorWhite? 0: 7;
		
		
		List<PositionCoordinate> goalFields = List.of(
				new PositionCoordinate(5, color),
				new PositionCoordinate(6, color)
				);
		
		List <PositionCoordinate> goalFieldsKing = List.of(
				new PositionCoordinate(4, color), //Königsfeld
				new PositionCoordinate(5, color),
				new PositionCoordinate(6, color)
				);
		
		PositionCoordinate goalFieldRook = new PositionCoordinate(7, color);
		
		moves = setMoveCastling(moves, surface, color, goalFields, goalFieldsKing, goalFieldRook, true);
		
		goalFields = List.of(
				new PositionCoordinate(1, color),
				new PositionCoordinate(2, color),
				new PositionCoordinate(3, color)
				);
		
		goalFieldsKing = List.of(
				new PositionCoordinate(2, color),
				new PositionCoordinate(3, color),
				new PositionCoordinate(4, color) //Königsfeld
				);
		
		goalFieldRook = new PositionCoordinate(0, color);
		
		moves = setMoveCastling(moves, surface, color, goalFields, goalFieldsKing, goalFieldRook, false);
		
		for (Move m : moves) {
			System.out.println(m);
		}
		return moves;
	}

	private List<Move> setMoveCastling(List<Move> moves, Schachbrett surface, int color, List<PositionCoordinate> goalFields, List<PositionCoordinate> goalFieldsKing, PositionCoordinate goalFieldRook, boolean isShortCastling) {
		if (validateCastling(goalFields, goalFieldsKing, goalFieldRook, surface)) {
			int castling = isShortCastling? 6 : 2;
			Move move = new Move(getFigureType() + getPosition().getCoordinate() + new PositionCoordinate(castling, color).getCoordinate(), getFigureType(), isColorWhite(), getPosition(), new PositionCoordinate(castling, color));
			move.setIsCastling(true, isShortCastling);
			moves.add(move);
			System.out.println("King.getMoves.setMoveCastling: " + "isCastling==true, isShortCastling==" + isShortCastling + ", move==" + move);
		}
		return moves;
	}



	/**
	 * @param moves
	 * @param surface
	 * @param valid
	 * @param goalFields
	 * @param goalFieldRook
	 */
	private boolean validateCastling(List<PositionCoordinate> goalFields, List<PositionCoordinate> goalFieldsKing, PositionCoordinate goalFieldRook, Schachbrett surface) {
		Figure goalFigure = surface.getFigureAtCoordinate(goalFieldRook);
		boolean valid = true;
		if (goalFigure == null) {
			return false;
		}
		if (goalFigure.getFigureType() == "R" && !goalFigure.isMoved()) {
			if (!isPositionCheck(aktuellePositionCoordinate)) {				
				for (PositionCoordinate field: goalFields) {
					if (surface.getFigureAtCoordinate(field) != null) {
						valid = false;
					}
				}
			}
			else {
				valid = false;
			}
		}
		else {
			valid = false;
		}
		
		for (PositionCoordinate pos: goalFieldsKing) {
			if (isPositionCheck(pos)) {
				valid = false;
			}
		}
		return valid;
	}
    
	
    protected boolean handleValidPosition(PositionType positionType) { //merge with handlePosition?
    	switch (positionType) {
        case INVALID_POSITON: return false;
        case VALID_POSITION: return true;
        case OPPOSITE_FIGURE: return false;
        default: throw new IllegalArgumentException("Ungültige Position: " + positionType);
    	}
    }
    
    @Override
    protected boolean handlePosition(List<Move> moeglichePositionen, PositionCoordinate newPos, boolean ignoreColor, boolean ignoreAttackedFields) {
        PositionType positionType = validatePosition(newPos, ignoreColor);

        switch (positionType) {
            case INVALID_POSITON: return true;
            case VALID_POSITION: 
            	if (!ignoreAttackedFields) {
	            	if (!isPositionCheck(newPos)) {
	            	moeglichePositionen.add(new Move(getFigureType() + getPosition().getCoordinate() + newPos.getCoordinate(), getFigureType(), isColorWhite(), getPosition(), newPos)); //new Move: ignoriert ob eine Figur geschlagen wird oder nicht!
	            	}
            	}
            	else {
            		moeglichePositionen.add(new Move(getFigureType() + getPosition().getCoordinate() + newPos.getCoordinate(), getFigureType(), isColorWhite(), getPosition(), newPos));
            	}
            	return false;
            case OPPOSITE_FIGURE: 
            	if (!ignoreAttackedFields) {
            		if (!isPositionCheck(newPos)) {
            			String hit = (isColorWhite() != getSurface().getFigureColorAtCoordinate(newPos))? "x":"";
            			moeglichePositionen.add(new Move(getFigureType() + getPosition().getCoordinate() + hit + newPos.getCoordinate(), getFigureType(), getPosition(), newPos, (hit == "x")? true : false));
            		}
            	}
            	else {
            		String hit = (isColorWhite() != getSurface().getFigureColorAtCoordinate(newPos))? "x":"";
        			moeglichePositionen.add(new Move(getFigureType() + getPosition().getCoordinate() + hit + newPos.getCoordinate(), getFigureType(), getPosition(), newPos, (hit == "x")? true : false));
            	}
            	return true;
            default: throw new IllegalArgumentException("Ungültige Position: " + positionType);
        }
    }
    
    @Override
    protected List<Vektor> getZuege() {

        return List.of(new Vektor(1,0),
                new Vektor(-1,0),
                new Vektor(0,1),
                new Vektor(0,-1),
                new Vektor(1,1),
                new Vektor(-1,1),
                new Vektor(1,-1),
                new Vektor(-1,-1));
    }
    
    public boolean isPositionCheck(PositionCoordinate position) {
    	List <PositionCoordinate> attackedFields = getSurface().getAttackedFields(this, isColorWhite());
    	if (attackedFields.contains(position)) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    @Override
    public String getFigureType() {
    	return "K";
    }
    
    @Override
    public String getUnicodeSymbol() {
    	if (isColorWhite()) {
    		return "♔";
    	}
    	else {
    		return "♚";
    	}
    }
}
