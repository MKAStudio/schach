import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Schachbrett {

    private final int maxRow = 8;
    private final int maxCol = 8;
    private History history;
    Figure activeFigure = null; //Liste aller Figuren auf dem Schachbrett.
    Figure[][] schachbrett = new Figure[8][8]; //Liste aller Felder auf dem Schachfeld, jede Figur Speichert ihre Farbe Selber, Liste enthält verweise zu den Figuren. (Ueber ID?)

    public Schachbrett() {
        history = new History();
    }
    
    public Schachbrett(Figure[][] schachbrett) {
        this.schachbrett = schachbrett;
    	history = new History();
    }

    public void moveFigureToCoordinate(PositionCoordinate StartCoordinate, PositionCoordinate GoalCoordinate) {
        Figure aktuelleFigur = getFigureAtCoordinate(StartCoordinate);
        if (aktuelleFigur != null) {
            List<Move> moeglicheZuege = aktuelleFigur.getMoves(false);
            //extract GoalCoordinates
//            List<PositionCoordinate> moeglicheZuegeGoalCoordinates = new ArrayList();
//            for (Move zug: moeglicheZuege) {
//            	moeglicheZuegeGoalCoordinates.add(zug.getLastPosition());
//            }
            
//            if (moeglicheZuegeGoalCoordinates.contains(GoalCoordinate)) {
//                schachbrett[StartCoordinate.getRow()][StartCoordinate.getCol()] = null;
//                schachbrett[GoalCoordinate.getRow()][GoalCoordinate.getCol()] = aktuelleFigur;
//                aktuelleFigur.setPosition(GoalCoordinate);
//            }
            
            boolean valid = false;
            for (Move zug: moeglicheZuege) {
            	System.out.println("Schachbrett.moveFigureToCoordinate: Zug: " + zug);
            	if (zug.getLastPosition().equals(GoalCoordinate)) {
            		valid = true;
            		schachbrett[StartCoordinate.getRow()][StartCoordinate.getCol()] = null;
                    schachbrett[GoalCoordinate.getRow()][GoalCoordinate.getCol()] = aktuelleFigur;
                    aktuelleFigur.setPosition(GoalCoordinate);
            		if (zug.isEn()) {
            			if(aktuelleFigur.isColorWhite()) {
            				PositionCoordinate secondGoalCoordinate = GoalCoordinate.addVektor(new Vektor(0, -1));
            				schachbrett[secondGoalCoordinate.getRow()][secondGoalCoordinate.getCol()] = null;
            			}
            		}
            		if (zug.isCastling()) {
            			if (zug.isShortCastling()) {
            				if (aktuelleFigur.isColorWhite()) {
            					schachbrett[0][5] = schachbrett[7][0];
            					schachbrett[7][0] = null;
            				}
            				else {
            					schachbrett[7][5] = schachbrett[7][7];
            					schachbrett[7][7] = null;
            				}
            			}
            			else {
            				if (aktuelleFigur.isColorWhite() ) {
            					schachbrett[0][3] = schachbrett[0][0];
            					schachbrett[0][0] = null;
            				}
            				else {
            					schachbrett[7][3] = schachbrett[7][0];
            					schachbrett[7][0] = null;
            				}
            			}
            		}
            	}
            }
            if(!valid) {
                //Error Zug ist nicht moeglich.
                System.out.println("Error: Zug ist nicht moeglich");
            }
        }
        else {
            //Error keine Figur vorhanden.
            System.out.println("Error: Keine Figur gefunden");
        }
    }
    
    public void doMove(Move move) {
    	if (move.isAddFigure()) {
    		Figure figure;
    		figure = figureStrToFigure(move);
    		addFigure(figure, move.getFirstPosition(), move);
    	}
    	else {
    		//if isHitFigure == true => if figureColor == sameColor
    		if (getFigureColorAtCoordinate(move.getFirstPosition()) == getFigureColorAtCoordinate(move.getLastPosition())) {
				System.out.println("Error: Figure can't hit Figure from same Color");
			}
    		else {
    			System.out.println("doMove: move=" + move);
    			System.out.println(move.getFigureStr() + move.getPawnConversion());
    			if (move.getFigureStr().equals("P") && !move.getPawnConversion().equals("")) {
    				//Add Figure, delete Pawn
    				System.out.println("addFigure, deletePawn");
    				PositionCoordinate firstPosition = move.getFirstPosition();
    				if (getFigureAtCoordinate(firstPosition) != null) {
    					Figure newFigure = figureStrToFigure(move, move.getPawnConversion());
    					newFigure.setIsColorWhite(this.getFigureColorAtCoordinate(firstPosition));
    					this.schachbrett[firstPosition.getRow()][firstPosition.getCol()] = null;
    					this.schachbrett[move.getLastPosition().getRow()][move.getLastPosition().getCol()] = newFigure;
    				}
    				else {
    					System.out.println(new NullPointerException("No Figure at First Coordinate found"));
    				}
    			}
    			else {
    				moveFigureToCoordinate(move.getFirstPosition(), move.getLastPosition());
    			}
    		}
    	}
    	history.addtoHistory(move);
    }

	private Figure figureStrToFigure(Move move) {
		Figure figure;
		switch (move.getFigureStr()) {
		case "P": figure = new Pawn(move.getFirstPosition(), move.isWhite(), this); break;
		case "Q": figure = new Queen(move.getFirstPosition(), move.isWhite(), this); break;
		case "K": figure = new King(move.getFirstPosition(), move.isWhite(), this); break;
		case "R": figure = new Rook(move.getFirstPosition(), move.isWhite(), this); break;
		case "B": figure = new Bishop(move.getFirstPosition(), move.isWhite(), this); break;
		case "N": figure = new Knight(move.getFirstPosition(), move.isWhite(), this); break;
		default: System.out.println("FigureStr not Found"); figure = new Pawn(move.getFirstPosition(), move.isWhite(), this); break;
		}
		return figure;
	}
	
	private Figure figureStrToFigure(Move move, String figureStr) {
		Figure figure;
		switch (figureStr) {
		case "P": figure = new Pawn(move.getFirstPosition(), move.isWhite(), this); break;
		case "Q": figure = new Queen(move.getFirstPosition(), move.isWhite(), this); break;
		case "K": figure = new King(move.getFirstPosition(), move.isWhite(), this); break;
		case "R": figure = new Rook(move.getFirstPosition(), move.isWhite(), this); break;
		case "B": figure = new Bishop(move.getFirstPosition(), move.isWhite(), this); break;
		case "N": figure = new Knight(move.getFirstPosition(), move.isWhite(), this); break;
		default: System.out.println("FigureStr not Found"); figure = new Pawn(move.getFirstPosition(), move.isWhite(), this); break;
		}
		return figure;
	}
    public Figure getFigureAtCoordinate(PositionCoordinate feld) {
        //return Null is field is empty
        return schachbrett[feld.getRow()][feld.getCol()];
    }

    public Figure getFigure(int x, int y) {
        return schachbrett[x][y];
    }

    public void addFigure(Figure figure, PositionCoordinate position, String zug) {
        if (getFigureAtCoordinate(position) == null) {
            schachbrett[position.getRow()][position.getCol()] = figure;
            this.activeFigure = figure;
            history.addtoHistory(MoveFactory.stringToMove("+" + zug));
        }
        else {;}//error?
    }

    public void addFigure(Figure figure, PositionCoordinate position, Move move) {
        if (getFigureAtCoordinate(position) == null) {
            schachbrett[position.getRow()][position.getCol()] = figure;
            this.activeFigure = figure;
            history.addtoHistory(move);
        }
        else {;}//error?
    }
    
    public void setFigures(List<Move> lstMove) {
    	clear();
    	for (Move move: lstMove) {
    		doMove(move);
    	}
    }
    
    public void setFigure(Figure figure, PositionCoordinate position) {
        clear();
        schachbrett[position.getRow()][position.getCol()] = figure;
        this.activeFigure = figure;
//        history.clear();
//        history.addtoHistory("+" + );
    }
    
    public void setBoardPosition(Figure[][] position) {
    	clear();
    	schachbrett = position;
    }

    public boolean getCollision(PositionCoordinate pos) {
        if (schachbrett[pos.getRow()][pos.getCol()] != null) {
//            System.out.println("collision");
            return true;

        }
        else {
            return false;
        }
    }

    public Boolean getFigureColorAtCoordinate(PositionCoordinate pos) {
        //returns null wenn keine Figur vorhanden.
    	System.out.println("getFigureColotAtCoordinate: pos=" + pos);
        Figure aktuelleFigure = schachbrett[pos.getRow()][pos.getCol()];
        if (aktuelleFigure != null) {
            return aktuelleFigure.isColorWhite();
        }
        else {
        	//throw new NullPointerException("Keine Figur gefunden.");
            System.out.println(new NullPointerException("Keine Figur gefunden."));
            return null; 
        }
    }


    public Figure getActiveFigure() {
        return activeFigure;
    }
    
    public void setActiveFigure(Figure figure) {
    	activeFigure = figure;
    }

    public History getHistory() {
        return history;
    }
    
    public void setHistory(History history) {
    	this.history = history;
    }
    
    public void clear() {
    	schachbrett = new Figure[8][8];
    }
    
    public void resetBoard() {
    	List<Move> figures = new ArrayList();
    	figures.add(new Move("+WRA1", "R", true, new PositionCoordinate(0, 0)));
    	figures.add(new Move("+WNB1", "N", true, new PositionCoordinate(1, 0)));
    	figures.add(new Move("+WBC1", "B", true, new PositionCoordinate(2, 0)));
    	figures.add(new Move("+WQD1", "Q", true, new PositionCoordinate(3, 0)));
    	figures.add(new Move("+WKE1", "K", true, new PositionCoordinate(4, 0)));
    	figures.add(new Move("+WBF1", "B", true, new PositionCoordinate(5, 0)));
    	figures.add(new Move("+WNG1", "N", true, new PositionCoordinate(6, 0)));
    	figures.add(new Move("+WRH1", "R", true, new PositionCoordinate(7, 0)));
    	
    	for (int i = 0; i < 8; i++) {
        	figures.add(new Move("+WP" + Position.getCoordinate(new PositionCoordinate(i,1)), "P", true, new PositionCoordinate(i, 1)));
    	}
    	//Black
    	figures.add(new Move("+WRA1", "R", false, new PositionCoordinate(0, 7)));
    	figures.add(new Move("+WNB1", "N", false, new PositionCoordinate(1, 7)));
    	figures.add(new Move("+WBC1", "B", false, new PositionCoordinate(2, 7)));
    	figures.add(new Move("+WQD1", "Q", false, new PositionCoordinate(3, 7)));
    	figures.add(new Move("+WKE1", "K", false, new PositionCoordinate(4, 7)));
    	figures.add(new Move("+WBF1", "B", false, new PositionCoordinate(5, 7)));
    	figures.add(new Move("+WNG1", "N", false, new PositionCoordinate(6, 7)));
    	figures.add(new Move("+WRH1", "R", false, new PositionCoordinate(7, 7)));
    	
    	for (int i = 0; i < 8; i++) {
        	figures.add(new Move("+WP" + Position.getCoordinate(new PositionCoordinate(i,6)), "P", false, new PositionCoordinate(i, 6)));
    	}
    	
    	setFigures(figures);
    	this.activeFigure = null;
    	history.clearHistory();
    }
    
    public List<PositionCoordinate> getAttackedFields(Figure baseFigure, boolean isWhite) {
    	List<PositionCoordinate> fields = new ArrayList();
    	for (int x=0; x < 8; x++) {
    		for (int y = 0; y < 8; y++) {
    			Figure figure = schachbrett[x][y];
    			if (figure != null && figure != baseFigure && figure.isColorWhite() != isWhite) {
    				List<Move> moves = figure.getMoves(true); //Ruft Alle Figuren auf, bei getCheck auch den König!!!!!
    				for (Move move: moves) {
    					PositionCoordinate field = move.getLastPosition(); 
    					if (!fields.contains(field)) {
    						fields.add(field);
    					}
    				}
    			}
    		}
    	}
    	
    	return fields;
    }
    public void pawnConvention(PositionCoordinate position, String newFigure, boolean isWhite) {
//    	if (schachbrett[position.getCol()][position.getRow()] != null) {
    		
		switch (newFigure) {
		case "Q": schachbrett[position.getCol()][position.getRow()] = new Queen(position, isWhite, this); break;
		case "R": schachbrett[position.getCol()][position.getRow()] = new Rook(position, isWhite, this); break;
		case "B": schachbrett[position.getCol()][position.getRow()] = new Bishop(position, isWhite, this); break;
		case "N": schachbrett[position.getCol()][position.getRow()] = new Knight(position, isWhite, this); break;
		}
//    	}
//    	else {
//    		System.out.println(new NullPointerException("Kein Bauer auf Zielfeld"));
//    	}
    }
    public List<Move> getMoves (PositionCoordinate Position, boolean ignoreColor) {
    	if (getFigureAtCoordinate(Position) == null) {
    		throw new IllegalArgumentException("Figure Not Found");
    	}
    	else {
    		return getFigureAtCoordinate(Position).getMoves(ignoreColor);
    	}
    }
    
    public List<Move> getAllMoves() {
    	List<Move> moves = new ArrayList();
    	for (int x=0; x < 8; x++) {
    		for (int y = 0; y < 8; y++) {
    			Figure figure = schachbrett[x][y];
    			if (figure != null) {
    				moves.addAll(figure.getMoves(false));
    			}
    		}
    	}
    	return moves;
    }
}
