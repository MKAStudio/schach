import java.util.List;
import java.lang.Integer;

public class Vektor {
    private Coordinates coordinates;
    private boolean scale = false;
    private boolean canHit;

    public Vektor(int x, int y, boolean scale) {
        this.coordinates = new Coordinates(x, y);
        this.scale = scale;
        canHit = true;
    }

    public Vektor(int x, int y) {
        this.coordinates = new Coordinates(x,y);
        this.scale = false;
        canHit = true;
    }

    public Vektor(List<Integer> xy) {
        this.coordinates = new Coordinates(xy);
        this.scale = false;
        canHit = true;
    }
    
    public Vektor (int x, int y, boolean scale, boolean canHit) {
    	this.coordinates = new Coordinates(x,y);
    	this.scale = scale;
    	this.canHit = canHit;
    }

    public Vektor scaleVektor(int Faktor) {
        if (scale) {
            return new Vektor(coordinates.getCol() * Faktor, coordinates.getRow() * Faktor, this.scale);
        }
        else {
            return null; //null oder Nullvektor?
        }
    }

    public Coordinates getVektorCoordinates() {
        return coordinates; //this.x, this.y
    }

    public boolean getScale() {
        return scale;
    }
    
    public boolean canHit() {
    	return canHit;
    }

    public Vektor getScaleableVektor() {
        return new Vektor(coordinates.getCol(), coordinates.getRow(), true);
    }

	public boolean equals(Vektor vektor) {
		if (vektor.getVektorCoordinates().getRow() == this.coordinates.getRow() && vektor.getVektorCoordinates().getCol() == this.coordinates.getCol() && vektor.getScale() == this.scale) {
			return true;
		}
		else {
			return false;
		}
	}
    
}
