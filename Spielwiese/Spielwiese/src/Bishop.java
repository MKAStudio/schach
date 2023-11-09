import java.util.List;

public class Bishop extends Figure {
    public Bishop(PositionCoordinate aktuellePositionCoordinate, boolean isColorWhite, Schachbrett surface) {
        super(aktuellePositionCoordinate, isColorWhite, "B", surface);
    }

    @Override
    protected List<Vektor> getZuege() {
        return List.of(
                new Vektor(1,1, true),
                new Vektor(-1,1, true),
                new Vektor(1,-1, true),
                new Vektor(-1,-1, true));
    }
    
    @Override
    public String getFigureType() {
    	return "B";
    }
    
    @Override
    public String getUnicodeSymbol() {
    	if (isColorWhite()) {
    		return "♗";
    	}
    	else {
    		return "♝";
    	}
    }
}
