import java.util.List;

public class Rook extends Figure {
    public Rook(PositionCoordinate aktuellePositionCoordinate, boolean isColorWhite, Schachbrett surface) {
        super(aktuellePositionCoordinate, isColorWhite, "R", surface);
    }
    
    @Override
    protected List<Vektor> getZuege() {
        return List.of(
                new Vektor(1,0, true),
                new Vektor(-1,0, true),
                new Vektor(0,1, true),
                new Vektor(0,-1, true));
    }
    
    @Override
    public String getFigureType() {
    	return "R";
    }
    
    @Override
    public String getUnicodeSymbol() {
    	if (isColorWhite()) {
    		return "♖";
    	}
    	else {
    		return "♜";
    	}
    }
}
