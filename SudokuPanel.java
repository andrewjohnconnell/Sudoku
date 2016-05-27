

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class SudokuPanel extends JPanel  {

	static int NUM_OF_TILES = 81;
	static int WIN_WIDTH = 600; // The window itself is fluid in how it can be
	static int WIN_HEIGHT = 400; // resized: these are just reasonable defaults for the window.

	Puzzle puzRef;
	Grid gridRef;
	GridLayout layout = new GridLayout(9,9,1,1);
	SudokuButton[] btn = new SudokuButton[NUM_OF_TILES]; 
	JLabel[] lbls = new JLabel[NUM_OF_TILES];
	SudokuButton butRef;
	SudokuFrame mainFrame;
	int revealThese;

	public SudokuPanel(SudokuFrame frame, Puzzle p, Grid g, int Tiles) {
		mainFrame = frame;
		puzRef = p;
		gridRef = g;
		revealThese = Tiles;
		this.setPreferredSize(new Dimension(WIN_WIDTH, WIN_HEIGHT));
		this.setLayout(layout);
		mainFrame.frame.setLocationRelativeTo(null);
	}

	public void confirmComplete() {
		int confirmCnt = 0;
		for (SudokuButton sbtn: btn) {
			if (sbtn.buttonValue == gridRef.Grid[sbtn.buttonRef][2]) {
				confirmCnt++;
			}
		}
		if (confirmCnt==81) {
			//System.out.print("COMPLETE!\n");
			clearScreen();
			for (SudokuButton sbtn: btn) {
				sbtn.setEnabled(true);
				sbtn.setBackground(new Color(1 * sbtn.buttonRef, 2 * sbtn.buttonRef, 3 * sbtn.buttonRef));
				pasteWord(29, "LEVEL");
				pasteWord(45, "COMPLETED");
				sbtn.setEnabled(false);
			}
		}
		mainFrame.frame.revalidate();

	}
	
	public void clearScreen() {
		for (SudokuButton sbtn: btn) {
			buttonThrow("", sbtn);
		}
	}
	public void pasteWord(int initButton, String putWord) {
		char[] theWord = putWord.toCharArray();
		for (char c: theWord) {
			for (SudokuButton sbtn: btn) {
				if (sbtn.buttonRef == initButton) {
					buttonThrow(""+c, sbtn);
				}
			}
			initButton++;
		}
	}
	
	public void openNew(String textIn, SudokuButton sbtn) {
		sbtn.setBackground(new Color(180,180,220));
		sbtn.setForeground(new Color(20,20,20));
		sbtn.setEnabled(true);
	}

	public void buttonThrow(String textIn, SudokuButton sbtn) {
		sbtn.setBackground(new Color(90,50,120));
		sbtn.setText(textIn + "");
	}

	public void initialDisplay() {
		for (int x = 0; x < NUM_OF_TILES; x++) {	
			generateButton(x);
		}		
	}

	private void generateButton (int x) {
		btn[x] = new SudokuButton(mainFrame, x, puzRef, gridRef);
		colour_backgrounds(x);
		lbls[x] = new JLabel();
		lbls[x].setText(String.valueOf(x));
	}
	
	private void colour_backgrounds(int x) {
		select_Orange(x);
		select_Green(x);
		select_Blue(x);
	}

	private void select_Orange(int x) {
		switch(x) {
		case 0: case 1: case 2:
		case 9:	case 10: case 11:
		case 18: case 19: case 20:
		case 60: case 61: case 62:
		case 69: case 70: case 71:
		case 78: case 79: case 80:
			btn[x].setBackground(new Color(254, 244, 222));
		}
	}

	private void select_Green(int x) {
		switch(x) {
		case 3: case 4: case 5:
		case 12: case 13: case 14:
		case 21: case 22: case 23:
		case 33: case 34: case 35:
		case 42: case 43: case 44:
		case 51: case 52: case 53:
		case 27: case 28: case 29:
		case 36: case 37: case 38:
		case 45: case 46: case 47:
		case 57: case 58: case 59:
		case 66: case 67: case 68:
		case 75: case 76: case 77:
			btn[x].setBackground(new Color(245, 255, 220));
		}
	}

	private void select_Blue(int x) {
		switch(x) {
		case 6: case 7: case 8:
		case 15: case 16: case 17:
		case 24: case 25: case 26:
		case 30: case 31: case 32:
		case 39: case 40: case 41:
		case 48: case 49: case 50:
		case 54: case 55: case 56:
		case 63: case 64: case 65:
		case 72: case 73: case 74:
			btn[x].setBackground(new Color(210, 210, 245));
		}
	}

	public void putVisible() {
		this.setVisible(true);
	}

	// Randomly select buttons and disable them
	public void sealButtons() {
		int[] assBtn = new int[revealThese];
		int chkThis;
		int xC = 0;

		for (int x = 0; x < revealThese; x++) {
			xC = 0; // Ensure reset to 0 at invocation.
			chkThis = (int)(Math.random() * NUM_OF_TILES);
			if (Integer.valueOf(chkThis).equals(Integer.valueOf(0))) { x--; } 
			else {
				for (int through = 0; through < revealThese; through++)	
					if (Integer.valueOf(chkThis).equals(Integer.valueOf(assBtn[through])))	xC++;
				x = checkAmendxCx(assBtn, x, chkThis, xC);
			}
		}		
	}	

	private int checkAmendxCx(int[] assBtn, int x, int chkThis, int xC) {
		if (Integer.valueOf(xC).equals(Integer.valueOf(0))) {
			amendAssBtn(assBtn, x, chkThis);
		} else {
			x--;
		}
		return x;
	}

	private void amendAssBtn(int[] assBtn, int x, int chkThis) {
		assBtn[x] = chkThis;
		btn[assBtn[x]].visButton = 0;
		btn[assBtn[x]].setEnabled(false);
		btn[assBtn[x]].getDisabledIcon();
		btn[assBtn[x]].setFocusable(false);
	}

	public void populateButtons() {
		for (int x = 0; x < NUM_OF_TILES; x++) {
			btn[x].addAListener(btn[x]);
		}
	}

	public void displayButtons() {
		for (int x = 0; x < NUM_OF_TILES; x++) {
			this.add(btn[x]);
		}
	}

	public void setButtonsOnScreen() {
		for (int x = 0; x < NUM_OF_TILES; x++) {
			btn[x].isThisEnabled(); 
			btn[x].setVisible(false);
		}
	}

	public void paintComponent(Graphics g) {
	}
}
