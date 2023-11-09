import java.util.List;

public class Queen extends Figure{
    public Queen(PositionCoordinate aktuellePositionCoordinate, boolean isColorWhite, Schachbrett surface) {
        super(aktuellePositionCoordinate, isColorWhite, "Q", surface);
    }

    @Override
    protected List<Vektor> getZuege() {
        return List.of(
                new Vektor(1,0, true),
                new Vektor(-1,0, true),
                new Vektor(0,1, true),
                new Vektor(0,-1, true),
                new Vektor(1,1, true),
                new Vektor(-1,1, true),
                new Vektor(1,-1, true),
                new Vektor(-1,-1, true));
    }
    
    @Override
    public String getFigureType() {
    	return "Q";
    }
    
    @Override
    public String getUnicodeSymbol() {
    	if (isColorWhite()) {
    		return "♕";
    	}
    	else {
    		return "♛";
    	}
    }
}


