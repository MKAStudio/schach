import java.util.List;

public class Pawn extends Figure {
    public Pawn(PositionCoordinate aktuellePositionCoordinate, boolean isColorWhite, Schachbrett surface) {
        super(aktuellePositionCoordinate, isColorWhite, "P", surface);

    }

    @Override
    protected List<Vektor> getZuege() {
        if (isColorWhite()) {
            return List.of(new Vektor(0, 1, false, false), new Vektor(1, 1, false, true), new Vektor(-1, 1, false, true));
            
        }
        else {
            return List.of(new Vektor(0, -1, false, false), new Vektor(1, -1, false, true), new Vektor(-1, -1, false, true));
        }
    }
    
    @Override
    public String getFigureType() {
    	return "P";
    }
    
    @Override
    protected boolean hitLogic(List<Move> moeglichePositionen, Vektor vekt1, PositionCoordinate newPos, boolean ignoreColor, boolean ignoreAttackedFields, boolean ignoreKings) {
		boolean stop = false;
		if (newPos.col >= 0 && newPos.col < 8 && newPos.row >= 0 && newPos.row < 8) {
			if (!vekt1.canHit()) {
				if (getSurface().getFigureAtCoordinate(newPos) == null) {
					stop = handlePosition(moeglichePositionen, newPos, ignoreColor, false, ignoreKings);
				}
			}
			else {
				Schachbrett surf = getSurface();
				Figure fig = surf.getFigureAtCoordinate(newPos);
				if (fig != null) {
					stop = handlePosition(moeglichePositionen, newPos, ignoreColor, false, ignoreKings);
				}
			}
		}
		return stop;
	}

    @Override
    public List<Move> getMoves(boolean ignoreColor) {
    	return getMoves(ignoreColor, true, false);
    }
    
    @Override
    public List<Move> getMoves(boolean ignoreColor, boolean ignoreAttackedFields, boolean ignoreKings) {
        List<Move> moves = super.getMoves(ignoreColor, ignoreAttackedFields, ignoreKings);
        
        //Bauernumwandlung?
        for (int i=0; i < moves.size(); i++) {
        	if ((moves.get(i).getLastPosition().getRow() == 7 || moves.get(i).getLastPosition().getRow() == 0) && (moves.get(i).getPawnConversion().equals("") || moves.get(i).getPawnConversion().equals(null))) {
        		Move move = moves.get(i);
        		moves.remove(i);
        		moves.add(editMovePawnConversion(move, "Q"));
        		moves.add(editMovePawnConversion(move, "R"));
        		moves.add(editMovePawnConversion(move, "N"));
        		moves.add(editMovePawnConversion(move, "B"));
        	}
        }
        
        System.out.println(moves);
        //Doppelzug
        PositionCoordinate newPos;
        if (aktuellePositionCoordinate.getRow() == getBaseRow()) {
        	System.out.println(aktuellePositionCoordinate.getRow() + ", " + getBaseRow());
            Vektor vekt = getZuege().get(0).getScaleableVektor();
            newPos = this.aktuellePositionCoordinate.addVektor(vekt.scaleVektor(2));
            if (validatePosition(newPos, ignoreColor, ignoreKings) != PositionType.INVALID_POSITON) {
                moves.add(new Move(getFigureType() + getPosition().getCoordinate() + newPos.getCoordinate(), getFigureType(), getPosition(), newPos, false, false, true, ""));
            }
        }
        //ente pasente
        Move lastMove = getSurface().getHistory().getLastMove();
        if (lastMove != null) {
            if (lastMove.getFigureStr().equals("P") && lastMove.isDoubleMove()) {
                 PositionCoordinate lastMovePosition = lastMove.getLastPosition();
                 List<Vektor> neigthbourVektors = List.of(new Vektor(1,0), new Vektor(-1,0));
                for (int i = 0; i < neigthbourVektors.size();i++) {
                    PositionCoordinate neigthbourPosition = aktuellePositionCoordinate.addVektor(neigthbourVektors.get(i));
                    if (neigthbourPosition.equals(lastMovePosition)) {
                    	System.out.println("Der Fehler liegt in addMoveEn!");
                        moves = addMoveEn(moves, neigthbourVektors.get(i), isColorWhite());
                    }
                }
            }
        }
        return moves;
    }

	private Move editMovePawnConversion(Move move, String figureStr) {
		Move newMove = move.clone();
		newMove.setPawnConversion(figureStr);
		newMove.setMoveStr(move.getMoveStr() + "-" + figureStr);
		return newMove;
	}

	private List<Move> addMoveEn(List<Move> moves, Vektor neigthbourVektor, boolean isColorWhite) {
		PositionCoordinate newPos;
		int color = isColorWhite? 1: -1;
		
		if (new Vektor(1, 0, false).equals(neigthbourVektor)) {
		    newPos = aktuellePositionCoordinate.addVektor(new Vektor(1, color));
		    moves.add(new Move(getFigureType() + getPosition().getCoordinate() + "x" + newPos.getCoordinate() + "en", getFigureType(), getPosition(), newPos, true, true, false, null));
		}
		else {
		    newPos = aktuellePositionCoordinate.addVektor((new Vektor(-1, color)));
		    moves.add(new Move(getFigureType() + getPosition().getCoordinate() + "x" + newPos.getCoordinate() + "en", getFigureType(), isColorWhite(), getPosition(), newPos, true, true, false, null));
		}
		return moves;
	}

    public int getBaseRow() {
        if (isColorWhite()) {return 1;}
        else {return 6;}
    }
    
    @Override
    public String getUnicodeSymbol() {
    	if (isColorWhite()) {
    		return "♙";
    	}
    	else {
    		return "♙";
    	}
    }
}
