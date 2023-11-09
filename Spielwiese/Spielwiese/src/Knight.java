import java.util.List;

public class Knight extends Figure {

    public Knight(PositionCoordinate aktuellePositionCoordinate, boolean isColorWhite, Schachbrett surface) {
        super(aktuellePositionCoordinate, isColorWhite, "N", surface);
    }

    @Override
    protected List<Vektor> getZuege() {
        return List.of(
                new Vektor(1,2),
                new Vektor(1,-2),
                new Vektor(2,1),
                new Vektor(2,-1),
                new Vektor(-1,2),
                new Vektor(-1,-2),
                new Vektor(-2,1),
                new Vektor(-2,-1));
    }
    
    @Override
    public String getFigureType() {
    	return "N";
    }
    
    @Override
    public String getUnicodeSymbol() {
    	if (isColorWhite()) {
    		return "♘";
    	}
    	else {
    		return "♞";
    	}
    }
}