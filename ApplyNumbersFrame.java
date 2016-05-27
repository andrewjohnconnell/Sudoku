

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class ApplyNumbersFrame {
	static int BUTTON_NUMS = 9;
	static int BUTTON_PANEL = 12;
	static int WIN_WIDTH = 250;
	static int WIN_HEIGHT = 120;
	static int X_LOC = 150;
	static int Y_LOC = 175;
	
	static int registerOne = 14;
	static int amendASCIIandArray = 49;
	
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	SudokuFrame thisPuzzle;
	SudokuButton returnTo;
	ApplyNumberButton[] buttons = new ApplyNumberButton[12];
	int[] selectedButtons = new int[BUTTON_NUMS];
	
	// Build a pop up window to choose numbers from to populate the grid.
	
	public ApplyNumbersFrame(SudokuFrame applyPuzzle, SudokuButton btn) {
		produceFrame(applyPuzzle, btn);
	    killOnOffFocus();
		try {
		    UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
		} catch (Exception e) {	
			e.printStackTrace();
		}			
	}
	
	// Create frame for correct puzzle and correct button within that puzzle based on
	// what gets fed through in the caller
	private void produceFrame(SudokuFrame applyPuzzle, SudokuButton btn) {
		thisPuzzle = applyPuzzle;
		returnTo = btn;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setSize(WIN_WIDTH, WIN_HEIGHT);
		frame.setLocationRelativeTo(btn);
		panel.setLayout(new GridLayout(4,3,0,0));
	}
	
	// Ensure this frame is made invisible if focus is lost.  Kills all links to it, and 
	// gc will come and pick it up later.
	private void killOnOffFocus() {
		frame.addFocusListener(new FocusListener() {
	        public void focusLost(FocusEvent e) {
	            frame.setVisible(false);
	        }
	        public void focusGained(FocusEvent e) {}
		});
	}

	// Populate the panel with buttons.
	public void populatePanel() {
		for (int x = 0; x < BUTTON_PANEL; x++) {
			sortButtons(x);
		}
		if (returnTo.getText().length() != 0) {
			char[] searchThis = returnTo.getText().toCharArray();
			conductSearch(searchThis);
		}
	}
	
	// Sort buttons to populate panel called in above method
	public void sortButtons(int x) {
		buttons[x] = new ApplyNumberButton(x, this);
		buttons[x].applyActionListener(buttons[x], buttons, selectedButtons, returnTo);
		buttons[x].setBackground(Color.WHITE);
		buttons[x].setText(String.valueOf(x+1));
		if (x==9) buttons[x].deactivateButton();
		if (x==10) buttons[x].setText(String.valueOf("Blank"));
		if (x==11) buttons[x].setText(String.valueOf("Apply"));
		panel.add(buttons[x]);
	}
	
	// Search string in populatePanel - if num valid, create num button
	public void conductSearch(char[] searchThis) {
		if (searchThis.length >= registerOne) {
			for (char c : searchThis) {
				int isNum = (int)c - amendASCIIandArray;
				if (isNum > 0 && isNum <= 9) buttons[isNum].selectButton(buttons[isNum], selectedButtons);
			}
		}
	}
	
	// Generate frame and place on screen
	public void generateFrame(int xPos, int yPos) {
		populatePanel();
		frame.add(panel);
		panel.setVisible(true);
		frame.setVisible(false);
	}
	
	// Show the frame
	public void showFrame() {
		frame.setVisible(true);
	}
	
	// Deselect all buttons 
	public void deselectAllButtons(ApplyNumberButton[] buttons, int[] selected) {
		for (int x = 0; x < BUTTON_NUMS; x++) {
			if (buttons[x].isSelected) buttons[x].deselectButton(buttons[x], selected);
		}
	}
	
	// Hide this frame
	public void hideFrame() {
		frame.setVisible(false);
		
	}
	
}
