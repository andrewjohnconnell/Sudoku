import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

// Class to put menu bars in a frame for the game.

public class FrameMenu {

	JFrame frame = null;
	JMenu fileMenu = null;
	JMenu gameMenu = null;
	JMenu toolsMenu = null;
	JMenuBar menuBar = null;
	SudokuFrame gameWin = null;
	int actLimit = 0;
	
	// Assign window for placing these menus in
	public FrameMenu (JFrame frame, SudokuFrame gameFrame) {
		this.frame = frame;
		this.gameWin = gameFrame;
	}
	
	// Add menu items to the assigned frame
	public void addMenu(JFrame frame) {
		this.frame = frame;
		gameWin.menu = this;
		menuBar = new JMenuBar();
		addMenuNames();
        addBellsAndWhistles();
        frame.setJMenuBar(menuBar);
	}
	
	// Place names for menus in frame
	private void addMenuNames() {
        fileMenu = new JMenu("File");
        gameMenu = new JMenu("Game");
        toolsMenu = new JMenu("Tools");
	}
	
	// Put in relevant menu bars for this menu
	private void addBellsAndWhistles() {
        menuBar.add(fileMenu);
        menuBar.add(gameMenu);
        addButtons();
        menuBar.add(toolsMenu);
        addCheck();
	}
	
	// Add buttons for menus
	private void addButtons() {
        addExitBtn();
        addUEGame();
        addVEGame();
        addEGame();
        addMGame();
        addHGame();
        addVHGame();
	}
	
	// Create a kill command
	private void addExitBtn() {
		JMenuItem item = new JMenuItem("Exit");
        item.addActionListener(new ActionListener(){
        	// Quick and easy kill command: justifying anonymous ActionListener
        	// as it only gets invoked to force a system exit.
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(item);
	}
	
	// Tile check enabler
	private void addCheck() {
		JMenuItem item = new JMenuItem("Enable Tile Check");
		item.addActionListener(new TileCheckListener(gameWin, item));
        toolsMenu.add(item);
    }
	
	// Test game mode
	private void addUEGame() {
		//invokeGame("New Test Game", 7);
	}
	
	// Very Easy
	private void addVEGame() {
		invokeGame("New Very Easy Game", 2);
	}
	
	// Easy
	private void addEGame() {
		invokeGame("New Easy Game", 3);
	}
	
	// Normal
	private void addMGame() {
		invokeGame("New Normal Game", 4);
	}

	// Hard
	private void addHGame() {
		invokeGame("New Hard Game", 5);
	}

	// If you play this, you need to be put in the insane asylum
	private void addVHGame() {
		invokeGame("New Very Hard Game", 6);
	}
	
	// Invoke the game to this window
	private void invokeGame(String gameName, int n) {
	JMenuItem item = new JMenuItem(gameName);
	gameWin.grabListen = null;
    item.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e) {
    		Invoke newWindow = new Invoke();
    		newWindow.run(n);
    		gameWin.frame.setVisible(false);
        }
    });
    gameMenu.add(item);
	}
	
}
