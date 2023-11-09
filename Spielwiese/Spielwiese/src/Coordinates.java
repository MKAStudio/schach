import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Coordinates {
    private Integer col = 0;
    private Integer row = 0;

    public Coordinates(int col, int row) {
        this.col = col;
        this.row = row;
    }
    public Coordinates(List<Integer> colRow) {
        this.col = colRow.get(0);
        this.row = colRow.get(1);
    }
//Listenlaenge Pruefen?
    public List<Integer> getCoordinates() {
        List<Integer> res = new ArrayList();
        res.add(col);
        res.add(row);

        return res;
    }
    public Integer getCol() {
        return this.col;
    }

    public Integer getRow() {
        return  this.row;
    }
}
