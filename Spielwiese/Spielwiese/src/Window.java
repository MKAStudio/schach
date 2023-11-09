import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Window {
    private JFrame frame;
    //private JLabel label;
//    private JTextField eingabeFeldPosition;
    private JTextField move;
//    private JButton ButAddFigure;
    private JButton ButMove;
    private SchachbrettGUI schachbrett; //Draw Area
//    private GridLayout Layout;

    public void start() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        Layout = new GridLayout(2,3);
//        frame.setLayout(Layout);




//        eingabeFeldPosition = new JTextField();
//        eingabeFeldPosition.setText("WRA1");
//        eingabeFeldPosition.setSize(5, 5);
//        eingabeFeldPosition.setMinimumSize(new Dimension(10, 10));

        move = new JTextField();
        move.setText("A1B1");
        move.setMinimumSize(new Dimension(15,15));

//        ButAddFigure = new JButton("Add Figure at Coordinate");
//        ButAddFigure.addActionListener(new ButAddFigureListener());
//        ButAddFigure.setMaximumSize(new Dimension((int)(frame.getWidth()/1*4), (int)(frame.getHeight()/1*4)));

        ButMove = new JButton("Move to Coordinate");
        ButMove.addActionListener(new ButMoveListener());
        ButMove.setMaximumSize(new Dimension((int)(frame.getWidth()/1*4), (int)(frame.getHeight()/1*4)));

        schachbrett = new SchachbrettGUI(frame, 20, frame.getWidth(), 100, frame.getHeight());
        schachbrett.setSize(frame.getWidth(), frame.getHeight());
        schachbrett.setMinimumSize(new Dimension((int)(frame.getWidth()/5*4), (int)(frame.getHeight()/5*4)));
//        frame.getContentPane().add(schachbrett);
//        frame.getContentPane().add(eingabeFeldPosition);
//        frame.getContentPane().add(ButAddFigure);
//        frame.getContentPane().add(ButMove);
//        frame.getContentPane().add(move);


//        frame.getContentPane().add(BorderLayout.WEST, eingabeFeldPosition);
        frame.getContentPane().add(BorderLayout.EAST, move);
        frame.getContentPane().add(BorderLayout.SOUTH, ButMove);
//        frame.getContentPane().add(BorderLayout.NORTH, ButAddFigure);
        frame.getContentPane().add(BorderLayout.CENTER, schachbrett);
        frame.setSize(500, 500); //500, 400
        frame.setVisible(true);

//        Figure aktiveFigure = new King(new PositionCoordinate(0,0), true, schachbrett);
//        schachbrett.addFigure(aktiveFigure,  new PositionCoordinate(0,0));
//        schachbrett.addFigure(new King(new PositionCoordinate(0,1), false, schachbrett), new PositionCoordinate(0,1));
    }

//    class ButAddFigureListener implements ActionListener {
//        public void actionPerformed(ActionEvent event) {
//            String input = eingabeFeldPosition.getText(); //Koordinaten Überprüfen ob Korekt eingegeben
//            PositionCoordinate inputCoordinates = Position.getCoordinate(input.substring(2));
//            System.out.println(inputCoordinates);
//            //Wenn Figur auf Feld: mögliche Züge ausgeben
//            //Wenn mögliche Züge aktuell ausgegeben und input = mögliches Zielfeld (möglicher Zug) dann: Bewege Figur
//            //Wenn weder Figur aus Feld noch möglicher Zug: Platziere neue Figur
//
//            //Platziere neue Figur
//            Figure aktuelleFigur = schachbrett.getFigureAtCoordinate(inputCoordinates);
//            boolean colorWhite = input.charAt(0) == 'W' ? true : false;
//            if (aktuelleFigur == null) {
//                String fig = input.substring(1,2);
//                switch (fig) {
//                    case "K": schachbrett.addFigure(new King(inputCoordinates, colorWhite, schachbrett.getModel()), inputCoordinates, "K" + Position.getCoordinate(inputCoordinates.getRow(), inputCoordinates.getCol()));
//                    case "Q": schachbrett.addFigure(new Queen(inputCoordinates, colorWhite, schachbrett.getModel()), inputCoordinates, "Q" + Position.getCoordinate(inputCoordinates.getRow(), inputCoordinates.getCol()));
//                    case "R": schachbrett.addFigure(new Rook(inputCoordinates, colorWhite, schachbrett.getModel()), inputCoordinates, "R" + Position.getCoordinate(inputCoordinates.getRow(), inputCoordinates.getCol()));
//                    case "N": schachbrett.addFigure(new Knight(inputCoordinates,colorWhite, schachbrett.getModel()), inputCoordinates, "N" + Position.getCoordinate(inputCoordinates.getRow(), inputCoordinates.getCol()));
//                    case "B": schachbrett.addFigure(new Bishop(inputCoordinates,colorWhite, schachbrett.getModel()), inputCoordinates, "B" + Position.getCoordinate(inputCoordinates.getRow(), inputCoordinates.getCol()));
//                    case "P": schachbrett.addFigure(new Pawn(inputCoordinates,colorWhite, schachbrett.getModel()), inputCoordinates, "P" + Position.getCoordinate(inputCoordinates.getRow(), inputCoordinates.getCol()));
//                }
////                schachbrett.addFigure(new King(inputCoordinates, schachbrett), inputCoordinates);
//            }
//            else {
//                // Feld ist schon belegt.
//            }
//            schachbrett.repaint();
//        }
//    }

    class ButMoveListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String input = move.getText(); //Koordinaten überprüfen ob korrekt eingegeben
            System.out.println(input);
//            PositionCoordinate inputStartCoordinates = Position.getCoordinate(input.substring(0, 2));
//            System.out.println("inputStartCoordinates: " + inputStartCoordinates);

//            PositionCoordinate inputGoalCoordinate = Position.getCoordinate(input.substring(2,4));
//            schachbrett.moveFigureToCoordinate(inputStartCoordinates, inputGoalCoordinate);
            schachbrett.doMove(MoveFactory.stringToMove(input));
            schachbrett.repaint();
        }
    }

    public static void main(String[] args) {
        Window gui = new Window();
        gui.start();

    }
}
