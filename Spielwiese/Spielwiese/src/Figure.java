import java.util.ArrayList;
import java.util.List;

public abstract class Figure {

    protected PositionCoordinate aktuellePositionCoordinate;
    private String text;
    private boolean isMoved;
    private String figureType;
    
    public Schachbrett getSurface() {
        return surface;
    }
    
    public boolean isMoved() {
    	return isMoved;
    }
    
    private Schachbrett surface;
    private boolean isColorWhite;

    public Figure(PositionCoordinate aktuellePositionCoordinate, boolean isColorWhite, String text, Schachbrett surface) {
        this.aktuellePositionCoordinate = aktuellePositionCoordinate;
        this.isColorWhite = isColorWhite;
        this.text = text;
        this.surface = surface;
    }

    abstract protected List<Vektor> getZuege();
    
    abstract public String getFigureType();
    
    abstract public String getUnicodeSymbol();
    
    public List<Move> getMovesWithoutCastling(boolean ignoreColor, boolean ignoreAttackedFields, boolean ignoreKings) {
    	//Wird für getAttackedField benötigt, damit sich die Könige nicht zur Rochadenprüfung gegenseitig Rekursiv aufrufen.
    	//(Eigentlich nur King!!!!!)
    	return getMoves(ignoreColor, ignoreAttackedFields, ignoreKings);
    }
    public List<Move> getMoves(boolean ignoreColor) {
    	return getMoves(ignoreColor, true, false);
    }
    public List<Move> getMoves(boolean ignoreColor, boolean ignoreAttackedFields, boolean ignoreKings) {
        //gibt alle moeglichen Zuege aus, die nicht außerhalb des Feldes liegen.
        List<Move> moeglichePositionen = new ArrayList();

        for (int i = 0; i < getZuege().size(); i++) {
            Vektor vekt1 = getZuege().get(i);
            if (vekt1.getScale()) {
                boolean stop = false;
                for (int a=1; a<8 && !stop; a++) {
                    PositionCoordinate newPos = this.aktuellePositionCoordinate.addVektor(vekt1.scaleVektor(a));
                    stop = hitLogic(moeglichePositionen, vekt1, newPos, ignoreColor, ignoreAttackedFields, ignoreKings);
                }
            }
            else {
                PositionCoordinate newPos = this.aktuellePositionCoordinate.addVektor(vekt1);
                //Gueltigkeit des Zuges Prüfen
                //liegt das Zielfeld ausserhalb des Spielfeldes?
                hitLogic(moeglichePositionen, vekt1, newPos, ignoreColor, ignoreAttackedFields, ignoreKings);
            }
        }
        return moeglichePositionen;
    }

	/**
	 * @param moeglichePositionen
	 * @param vekt1
	 * @param newPos
	 */
	protected boolean hitLogic(List<Move> moeglichePositionen, Vektor vekt1, PositionCoordinate newPos, boolean ignoreColor, boolean ignoreAttackedFields, boolean ignoreKings) {
		boolean stop = false;
		stop = handlePosition(moeglichePositionen, newPos, ignoreColor, ignoreAttackedFields, ignoreKings);
		return stop;
	}

    protected boolean handlePosition(List<Move> moeglichePositionen, PositionCoordinate newPos, boolean ignoreColor, boolean ignoreAttackedFields, boolean ignoreKings) { //ignoreAttackedFields: Used for @Override Function of King
        PositionType positionType = validatePosition(newPos, ignoreColor, ignoreKings);
        switch (positionType) {
            case INVALID_POSITON: return true;
            case VALID_POSITION: moeglichePositionen.add(new Move(getFigureType() + getPosition().getCoordinate() + newPos.getCoordinate(), getFigureType(), isColorWhite(), getPosition(), newPos)); return false; //new Move: ignoriert ob eine Figur geschlagen wird oder nicht!
            case OPPOSITE_FIGURE: String hit = (isColorWhite() != surface.getFigureColorAtCoordinate(newPos))? "x":"";
            	moeglichePositionen.add(new Move(getFigureType() + getPosition().getCoordinate() + hit + newPos.getCoordinate(), getFigureType(), isColorWhite(), getPosition(), newPos, (hit == "x")? true:false)); return true;
            case OPPOSITE_FIGURE_KING: 
            	hit = (isColorWhite() != surface.getFigureColorAtCoordinate(newPos))? "x":"";
            	moeglichePositionen.add(new Move(getFigureType() + getPosition().getCoordinate() + hit + newPos.getCoordinate(), getFigureType(), isColorWhite(), getPosition(), newPos, (hit == "x")? true:false));
            	if (ignoreKings) {
            		return false;
            	}
            	else {
            		return true;
            	}
            	
            default: throw new IllegalArgumentException("Ungültige Position: " + positionType);
        }
    }

    public void setPosition(PositionCoordinate Pos) {
        this.aktuellePositionCoordinate = Pos;
        isMoved = true;
    }
    
    public PositionCoordinate getPosition() {
    	return this.aktuellePositionCoordinate;
    }

    protected PositionType validatePosition(PositionCoordinate pos, boolean ignoreColor, boolean ignoreKings) {
        boolean valid = pos.col >= 0 && pos.col < 8 && pos.row >= 0 && pos.row < 8;
        System.out.println(pos);
        if (valid) {
            if (surface.getCollision(pos)) {
            	if (ignoreColor) {
            		if (ignoreKings && surface.getFigureAtCoordinate(pos).getFigureType() == "K") {
            			return PositionType.OPPOSITE_FIGURE_KING;
            		}
            		else {
            			return PositionType.OPPOSITE_FIGURE;
            		}
            	}
            	else {
	                if (surface.getFigureColorAtCoordinate(pos) == isColorWhite) {
	                    return PositionType.INVALID_POSITON;
	                }
	                else {
	                	if (ignoreKings && surface.getFigureAtCoordinate(pos).getFigureType() == "K") {
	            			return PositionType.OPPOSITE_FIGURE_KING;
	            		}
	            		else {
	            			return PositionType.OPPOSITE_FIGURE;
	            		}
	                }
            	}
            }
            else {
                return PositionType.VALID_POSITION;
            }
        }
        else {
            return PositionType.INVALID_POSITON;
        }
    }
    
    public enum PositionType {
        INVALID_POSITON, 
        VALID_POSITION,
        OPPOSITE_FIGURE,
        OPPOSITE_FIGURE_KING;
    }

    public String getText() {
        return text;
    }

    public boolean isColorWhite() {
        return isColorWhite;
    }
    
    public void setIsColorWhite(boolean isColorWhite) {
    	this.isColorWhite = isColorWhite;
    }
}