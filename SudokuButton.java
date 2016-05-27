
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

// Class for buttons to go on the main game window
public class SudokuButton extends JButton {
	
	SudokuFrame mainFrame;
	ApplyNumbersFrame applyNums;
	Puzzle puzRef;
	Grid gridRef;
	int buttonRef;
	int buttonValue;
	int sealed = 0;
	int visButton;
	int buttonLive = 0;

	// Constructor receives frame, number for button and relatioin to puzzle
	public SudokuButton(SudokuFrame frame, int btnNumber, Puzzle p, Grid g) {
		
		mainFrame = frame;
		visButton = 1;
		puzRef = p;
		gridRef = g;
		buttonRef = btnNumber;
		buttonValue = p.fetchCell(g, btnNumber);
		
		this.setText(String.valueOf(buttonRef+""));
		// Now we need to take the value assigned to the cell in the grid
		// and pop that in place of the cell number
		this.setText(buttonValue+"");
		this.setFocusable(false);
		this.setFont(new Font("Arial", Font.PLAIN, 18));
		
	}

	// Throw a new action listener on to the button
	public void addAListener(SudokuButton btn) {
		if (btn.visButton==1) {
			btn.addActionListener(sudokuButton(btn));
		} else {
			amendLockStatus_Buttons(btn);
			buttonLive = 1;
		}
	}
	
	// SET COLOURS AND LOCKS
	void amendLockStatus_Buttons(SudokuButton btn) {
		// If uC, cL, cR, or lC - deep green
					switch (buttonRef) {
					//Green
					case 3: case 4: case 5:
					case 12:case 13: case 14:
					case 21: case 22: case 23:
					case 27: case 28: case 29:
					case 36: case 37: case 38:
					case 45: case 46: case 47:
					case 33: case 34: case 35:
					case 42: case 43: case 44:
					case 51: case 52: case 53:
					case 57: case 58: case 59:
					case 66: case 67: case 68:
					case 75: case 76: case 77:
						btn.setBackground(new Color(125, 135, 100));
						btn.setForeground(new Color(235,230,235));
						break;
					//Orange
					case 0: case 1: case 2:
					case 9:	case 10: case 11:
					case 18: case 19: case 20:
					case 60: case 61: case 62:
					case 69: case 70: case 71:
					case 78: case 79: case 80:
						btn.setBackground(new Color(134, 124, 102));
						btn.setForeground(new Color(235,230,235));
						break;
					//Blue
					case 54: case 55: case 56:
					case 63: case 64: case 65:
					case 72: case 73: case 74:
					case 6: case 7: case 8:
					case 15: case 16: case 17:
					case 24: case 25: case 26:
					case 30: case 31: case 32:
					case 39: case 40: case 41:
					case 48: case 49: case 50:
						btn.setBackground(new Color(90, 90, 125));
						btn.setForeground(new Color(235,230,235));
						break;
					default:
					btn.setBackground(new Color(100,70,100));
					btn.setForeground(new Color(235,230,235));
					break;
					}
					
					btn.visButton = 0;
					btn.setEnabled(false);
					btn.setFocusable(false);
					btn.setForeground(new Color(255,255,255));
	}
	
	// Action listener for the buttons
	public ActionListener sudokuButton(SudokuButton btn) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// If there is an action on the button, send a system response
				// Demonstrating the NUMBER of the box in question
				if(btn.isVisible()==true) {
					//System.out.println("Walloped box: " + buttonRef);	
					SoundHandler sound = new SoundHandler();
					ApplyNumbersFrame applyNum = new ApplyNumbersFrame(mainFrame, btn);
					applyNum.generateFrame(50, 50);
					applyNum.frame.setVisible(true);
				} else {
					if(btn.isVisible()==false) {
						btn.setVisible(false);
					}
				}	
			}
		};
	}
	
	// Set value of a box
	public void setValue(int value) {
		this.buttonValue = value;
	}

	// Get value of a box
	public int getValue() {
		// Use to test if values are being returned by a button
		if(buttonValue==0) {
			//System.out.println("Unassigned");
			return buttonValue;
		} else {
			//System.out.println(buttonValue);
			return buttonValue;
		}
	}
	
	// Is a box enabled?
	public void isThisEnabled() {
		if (this.isEnabled()) {
			this.setText(String.valueOf(""));
			buttonValue=0;
		} else {
			this.setEnabled(true);
		}
	}
	
	protected void paintComponent() { }
	
}
