import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SchachbrettGUI extends JPanel {
    private final int maxRow = 8;
    private final int maxCol = 8;
    JFrame surface;
    //Grenzen des Schachbrettes auf der GUI
    Integer x1 = 20; Integer x2 = 50;
    Integer y1 = 100; Integer y2 = 100;

    private Schachbrett schachbrett;

    public SchachbrettGUI(JFrame surface, Integer x1, Integer x2, Integer y1, Integer y2) {
        this.schachbrett = new  Schachbrett();
        this.surface = surface;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    public SchachbrettGUI(JFrame surface) {
        this.schachbrett = new  Schachbrett();
        this.surface = surface;
        }
    
    public int getCellSize() { //kürzer?
        int size;
        int width = this.getWidth();
        double sizex = width / (double)maxCol;
        int height = this.getHeight();
        double sizey = height / (double)maxRow;
        if (sizex < sizey) {
            size = (int)sizex;
        }
        else {
            size = (int)sizey;
        }
        return size;
    }
    public void paintComponent (Graphics g) {
        //Graphicmethode, Zeichnet das Schachfeld
//    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//    	String[] arfonts = ge.getAvailableFontFamilyNames();
//    	for (int i=0; i < arfonts.length; i++) {
//    		System.out.println(arfonts[i]);
//    	}
    	    	
        Integer cellSize = getCellSize();
        List<Move> moves = new ArrayList();
        if (schachbrett.getActiveFigure() != null) {
            moves = schachbrett.getActiveFigure().getMoves(false);
//            System.out.println("moves: " + moves);
        }
        paintChessboard(g, cellSize);
        for (Move m: moves) {
        	System.out.println(m);
        }
        
        paintFigures(g, cellSize, moves);
    }

    private void paintFigures(Graphics g, Integer cellSize, List<Move> moves) {
    	//extract Coordinates
    	List<PositionCoordinate> moveCoordinates= new ArrayList();
    	for (Move move: moves) {
    		moveCoordinates.add(move.getLastPosition());
    	}
    	//paint
        for (int x=0; x < 8; x++) {
            for (int y= 0; y < 8; y++){
                Figure figure = schachbrett.getFigure(x, y);
                if (figure != null) {
                    if (figure.isColorWhite()) {
                        g.setColor(new Color(255, 230, 150));
                    }
                    else {
                        g.setColor(new Color(67, 38,22));

                    }
                    //g.setFont(new Font("Arial", Font.BOLD,(int) cellSize));
                    Font font = new Font("Misc Symbols", Font.BOLD,(int) cellSize);
                    //Font font = new Font("FreeSerif", Font.BOLD,(int) cellSize);
//                    drawCenteredString(g, figure.getText(), new Rectangle(cellSize * y, cellSize * x, cellSize, cellSize), font );
                    drawCenteredString(g, figure.getUnicodeSymbol(), new Rectangle(cellSize * y, cellSize * x, cellSize, cellSize), font );
                }
                if (moveCoordinates.contains(new PositionCoordinate(y,x))) {
                    g.setColor(Color.blue);
                    g.fillOval(cellSize * y + (int)(cellSize /2 - (int)(cellSize /3)/2), cellSize * x + (int)(cellSize /2 - (int)(cellSize /3)/2), (int)(cellSize /3), (int)(cellSize /3));
//                    System.out.println("Oval " + Position.getCoordinate(y,x) + " Drawn");
                }
            }
        }
    }
    
    public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }

    private void paintChessboard(Graphics g, Integer cellSize) {
        for (int x=0; x < 8; x++) {
            Color feldFarbe = toogleColor(x);

            for (int y= 0; y < 8; y++){
                g.setColor(feldFarbe);
                g.fillRect(cellSize *x, cellSize *y, cellSize, cellSize);
                feldFarbe = feldFarbe.equals(Color.white) ? Color.black : Color.white;
            }
        }
    }

    private Color toogleColor(int x) {
        Color feldFarbe;
        if (x % 2 == 0) {
            feldFarbe = Color.black;
        }
        else {
            feldFarbe = Color.white;
        }
        return feldFarbe;
    }

    public void moveFigureToCoordinate(PositionCoordinate inputStartCoordinates, PositionCoordinate inputGoalCoordinate) {
        schachbrett.moveFigureToCoordinate(inputStartCoordinates, inputGoalCoordinate);
    }
    
    public void doMove(Move move) {
    	schachbrett.doMove(move);
    }
    
    public Schachbrett getModel() {
        return schachbrett;
    }

    public void addFigure(Figure figure, PositionCoordinate inputCoordinates, String s) {
        schachbrett.addFigure(figure, inputCoordinates, s);
    }

    public Figure getFigureAtCoordinate(PositionCoordinate inputCoordinates) {
        return schachbrett.getFigureAtCoordinate(inputCoordinates);
    }
    
    public void resetBoard() {
    	schachbrett.resetBoard();
    }
    
    public void setActiveFigure(Figure figure) {
    	schachbrett.setActiveFigure(figure);
    }
    
    public List<PositionCoordinate> getAttackedFields(Figure baseFigure, boolean isWhite) {
    	return schachbrett.getAttackedFields(baseFigure, isWhite);
    }
}