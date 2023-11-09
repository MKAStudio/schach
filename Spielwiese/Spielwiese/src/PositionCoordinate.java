import java.util.Objects;

public class PositionCoordinate implements Position{

    public final int col;
    public final int row;

    public PositionCoordinate(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public String getCoordinate() {
        return Position.getCoordinate(this.col, this.row);
    }

    public PositionCoordinate getCoordinate(String input){
        return Position.getCoordinate(input);
    }

    public PositionCoordinate addVektor(Vektor vekt) {
        //fügt den Vektor vekt zur Position hinzu und gibt neue Position zurück (Bewegt die Figur/mögliche Bewegung)
        return Position.addVektor(vekt, col, row);
    }

    public Integer getCol() {
        return this.col;
    }

    public Integer getRow() {
        return this.row;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PositionCoordinate positionCoordinate = (PositionCoordinate) o;
        return col == positionCoordinate.getCol() && row == positionCoordinate.getRow();
    }

    @Override
    public int hashCode() {
        return Objects.hash(col, row);
    }

    @Override
    public String toString() {
        return "PositionCoordinate{" +
                "col=" + col +
                ", row=" + row +
                '}';
    }
}

