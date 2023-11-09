import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenu;

public class SchachbrettWindow {

	private JFrame frame;
	private SchachbrettGUI schachbrett;
	private JTextField txtFieldMove;
	private JTextField txtFieldField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SchachbrettWindow window = new SchachbrettWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SchachbrettWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		schachbrett = new SchachbrettGUI(frame, 20, frame.getWidth(), 100, frame.getHeight());
		schachbrett.setBounds(10, 28, 224, 224);
        schachbrett.setMinimumSize(new Dimension((int)(frame.getWidth()/5*4), (int)(frame.getHeight()/5*4)));
        frame.getContentPane().add(schachbrett);
        
        txtFieldMove = new JTextField();
        txtFieldMove.setBounds(298, 28, 128, 20);
        frame.getContentPane().add(txtFieldMove);
        txtFieldMove.setColumns(10);
        
        JLabel lblNewLabel = new JLabel("Zug:");
        lblNewLabel.setBounds(244, 28, 29, 20);
        frame.getContentPane().add(lblNewLabel);
        
        JButton btnDoMove = new JButton("Do Move");
        btnDoMove.setBounds(298, 59, 91, 23);
        btnDoMove.addActionListener(new BtnDoMoveListener());
        frame.getContentPane().add(btnDoMove);
        
        JLabel lblZug = new JLabel("waiting for task");
        lblZug.setBounds(244, 238, 182, 14);
        frame.getContentPane().add(lblZug);
        
        JButton btnReset = new JButton("Reset Board");
        btnReset.addActionListener(new BtnResetListener());
        btnReset.setBounds(298, 204, 91, 23);
        frame.getContentPane().add(btnReset);
        
        JLabel lblFeld = new JLabel("Feld:");
        lblFeld.setBounds(244, 93, 29, 20);
        frame.getContentPane().add(lblFeld);
        
        txtFieldField = new JTextField();
        txtFieldField.setBounds(298, 93, 128, 20);
        frame.getContentPane().add(txtFieldField);
        txtFieldField.setColumns(10);
        
        JButton btnShowMoves = new JButton("Show Moves");
        btnShowMoves.addActionListener(new BtnShowMovesListener());
        btnShowMoves.setBounds(298, 124, 91, 23);
        frame.getContentPane().add(btnShowMoves);
	
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	class BtnDoMoveListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String input = txtFieldMove.getText(); //Koordinaten überprüfen ob korrekt eingegeben
            System.out.println(input);
            Move move = MoveFactory.stringToMove(input);
            System.out.println("BtnDoMoveListener: move=" + move);
            schachbrett.doMove(move);
            schachbrett.repaint();
        }
    }
	class BtnResetListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            schachbrett.resetBoard();
            schachbrett.repaint();
        }
    }
	
	class BtnShowMovesListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String input = txtFieldField.getText(); //Koordinaten überprüfen ob korrekt eingegeben
            System.out.println(input);
            
            PositionCoordinate position = Position.getCoordinate(input);
            schachbrett.setActiveFigure(schachbrett.getFigureAtCoordinate(position));
            schachbrett.repaint();
            
        }
    }
}
