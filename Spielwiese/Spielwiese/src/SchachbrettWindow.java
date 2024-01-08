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
import javax.swing.Box;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.Window.Type;
import net.miginfocom.swing.MigLayout;

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
        frame.getContentPane().setLayout(new MigLayout("", "[320.00px,grow,trailing][100px:22.02%:250px,right]", "[20px][6px][23px][6px][20px][78.00px,grow][14px]"));
        
        schachbrett = new SchachbrettGUI(frame, 20, frame.getWidth(), 100, frame.getHeight());
        frame.getContentPane().add(schachbrett, "cell 0 0 1 6,grow");
        schachbrett.setMinimumSize(new Dimension(120, 120));
        
        JButton btnReset = new JButton("Reset Board");
        frame.getContentPane().add(btnReset, "cell 1 4,growx,gapx 10,aligny top");
        
        JButton btnShowMoves = new JButton("Show Moves");
        frame.getContentPane().add(btnShowMoves, "cell 1 3,growx,gapx 10,aligny top");
        
        Box horizontalBox_2 = Box.createHorizontalBox();
        schachbrett.setLayout(new SpringLayout());
        frame.getContentPane().add(horizontalBox_2, "cell 1 0,growx,gapx 10,aligny top");
        
        JLabel lblFeld = new JLabel("Feld:");
        horizontalBox_2.add(lblFeld);
        
        txtFieldField = new JTextField();
        horizontalBox_2.add(txtFieldField);
        txtFieldField.setColumns(10);
        
        Box horizontalBox_1 = Box.createHorizontalBox();
        frame.getContentPane().add(horizontalBox_1, "cell 1 2,growx,gapx 10,aligny top");
        
        JLabel lblNewLabel = new JLabel("Zug:");
        horizontalBox_1.add(lblNewLabel);
        
        txtFieldMove = new JTextField();
        horizontalBox_1.add(txtFieldMove);
        txtFieldMove.setColumns(10);
        
        JButton btnDoMove = new JButton("Do Move");
        frame.getContentPane().add(btnDoMove, "cell 1 1,growx,gapx 10,aligny top");
        
        JLabel lblZug = new JLabel("waiting for task");
        lblZug.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblZug.setAlignmentX(Component.RIGHT_ALIGNMENT);
        frame.getContentPane().add(lblZug, "cell 0 6 2 1,alignx center,aligny baseline");
        btnDoMove.addActionListener(new BtnDoMoveListener());
        btnShowMoves.addActionListener(new BtnShowMovesListener());
        btnReset.addActionListener(new BtnResetListener());
	
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
