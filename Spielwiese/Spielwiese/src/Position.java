import java.util.Arrays;
import java.util.List;

public interface Position {
    static PositionCoordinate addVektor(Vektor vekt, Integer col, Integer row) {
        //fügt den Vektor vekt zur Position hinzu und gibt neue Position zurück (Bewegt die Figur/mögliche Bewegung)
        Coordinates bewegungsvekt = vekt.getVektorCoordinates();
        Coordinates newPos = new Coordinates(col + bewegungsvekt.getCol(), row + bewegungsvekt.getRow());

        return new PositionCoordinate(newPos.getCol(), newPos.getRow());
    }
    static String getCoordinate(PositionCoordinate pos) {
        int col_coordinate = pos.getCol() + 1;

        String[] xCoordinates = {"A", "B", "C", "D", "E", "F", "G", "H"};
        String row_coordinate = xCoordinates[pos.getRow()];

        return row_coordinate + col_coordinate;
    }

    static String getCoordinate(Integer row, Integer col) {
        int col_coordinate = col + 1;

        String[] xCoordinates = {"A", "B", "C", "D", "E", "F", "G", "H"};
        String row_coordinate = xCoordinates[row];

        return row_coordinate + col_coordinate;
    }
    
    static String getCoordinateB(Integer row, Integer col) {
        int col_coordinate = col + 1;

        String[] xCoordinates = {"a", "b", "c", "d", "e", "f", "g", "h"};
        String row_coordinate = xCoordinates[row];

        return row_coordinate + col_coordinate;
    }

    static PositionCoordinate getCoordinate(String input){
        Character[] zeilen = {'A', 'a', 'B', 'b', 'C', 'c', 'D', 'd', 'E', 'e', 'F', 'f', 'G', 'g', 'H', 'h'};
        Character xCoordinate = input.charAt(0);
        int x = Arrays.asList(zeilen).indexOf(xCoordinate) / 2;
        if (x < 0) {
            throw new IllegalArgumentException("Eingabe ungültig: " + input);
        }
        int y = Integer.parseInt(input.substring(1)) - 1;
        return new PositionCoordinate(x, y);
    }


    static void main(String[] args) {
        Character[] chars = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
        List<Character> l = Arrays.asList(chars);

        System.out.println(l);
        System.out.println(l.contains('A'));
    }
}
