
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

// Class to control the buttons to apply numbers to the grid

public class ApplyNumberButton extends JButton {

	int visButton;
	int buttonValue;
	boolean isSelected;
	ApplyNumbersFrame holder; 

	int actLimit = 0;
	int cntButts = 0;
	int buttonPressed = 0;
	
	static int INVALID_BUTTON = -1;
	static int BLANK_BUTTON = 11;
	static int APPLY_BUTTON = 12;
	static int BUTTON_NUMS = 9;
	
	String showThis="";
	String insertText="";
	
	// Generate a button based on int call and send to frame
	public ApplyNumberButton(int buttonNumber, ApplyNumbersFrame frame) {
		holder = frame;
		this.setBackground(Color.WHITE);
		this.setFocusable(false);
		setValue(buttonNumber);
		isSelected = false;
	}

	// Set the value of a button to buttonNumber and change string to match
	public void setValue(int buttonNumber) {
		buttonValue = buttonNumber+1;
		if (buttonNumber == BUTTON_NUMS) {
			buttonValue = INVALID_BUTTON;
			visButton = 0;
		} 
		if (buttonValue == -1) this.setText(String.valueOf(""));
		else {
			if (buttonValue == BLANK_BUTTON) visButton = visButtonPost("BLANK");
			else { if (buttonValue == APPLY_BUTTON) visButton = visButtonPost("APPLY");
			else visButton = visButtonPost(buttonValue+"");
			}
		}
	}

	// Post the value of a string to a button's text
	public int visButtonPost(String stringPost) {
		this.setText(String.valueOf(stringPost));
		return 1;
	}

	// Throw an action listener on to a button to catch mouse press events
	public void applyActionListener(ApplyNumberButton btn, ApplyNumberButton[] buttons, int[] selectedButtons, SudokuButton feedThrough) {
		if (btn.visButton == 1)
			btn.addActionListener(new SendPressListener(btn, buttons, selectedButtons, feedThrough));
	}
    
	// If the BLANK button is pressed, clear the cell on the grid of any text
	private int blankPressed(SoundHandler sound, ApplyNumberButton[] btnArray, 
			int[] selectedButtons, SudokuButton feedThrough, String insertText) {
		sound.playSound(new File("./Sounds/delete.wav"));
		holder.deselectAllButtons(btnArray, selectedButtons);
		feedThrough.setText(insertText);
		feedThrough.setValue(0); 
		holder.hideFrame();
		return 10;
	}
	
	// Whenever a button is pressed, conduct action attached to it and play associated sound.
	public int sendPress(ApplyNumberButton btn, ApplyNumberButton[] btnArray, int[] selectedButtons, SudokuButton feedThrough) {
		int[] Items = new int[BUTTON_NUMS];
		int singleItem = 0;
		SoundHandler sound = new SoundHandler();
		if (btn.visButton==1) 
			onButtonPress(sound, btnArray, selectedButtons, feedThrough, Items, singleItem, btn);
		holder.thisPuzzle.panel.confirmComplete();
		flashMenu();
		return buttonPressed;
	}

	// Cycle possible buttons that can be pressed, and conduct appropriate action
	private void onButtonPress(SoundHandler sound, ApplyNumberButton[] btnArray, 
			int[] selectedButtons, SudokuButton feedThrough, int[] Items, int singleItem, ApplyNumberButton btn) {
		if (buttonValue == BLANK_BUTTON) 
			buttonPressed = blankPressed(sound, btnArray, selectedButtons, feedThrough, insertText);
		if (buttonValue == APPLY_BUTTON) {
			buttonPressed = cycleButts (sound, selectedButtons, Items, btnArray);
			if (hideIfNoPress()) return;
			buttonsPressedTrue(feedThrough, Items, singleItem, selectedButtons);
		} else {
			buttonPressed = btn.buttonValue;
			if (btn.isSelected==false) { selectButton(btn, selectedButtons); } 
			else if (btn.isSelected==true) deselectButton(btn, selectedButtons);
		}
	}
	
	// If a button is pressed, check if it is a single value or multiple values.
	private void buttonsPressedTrue(SudokuButton feedThrough, int[] Items, int singleItem, int[] selectedButtons) {
		singleItem = multiButtPress(feedThrough, Items);
		declareMultiButts(singleItem, feedThrough, selectedButtons);
		setTextCloseWin(feedThrough);
	}
	
	// Apply text to button on the grid, and close the pop up window.
	private void setTextCloseWin(SudokuButton feedThrough) {
		showThis=String.valueOf("<html>"+showThis+"</html>");
		feedThrough.setText(showThis);
		//System.out.format("%d\n", feedThrough.getValue());
		holder.hideFrame();
	}
	
	// If there are multiple buttons pressed, cycle appropriately
	private void declareMultiButts(int singleItem, SudokuButton feedThrough, int[] selectedButtons) {
		if (Integer.valueOf(singleItem).equals(1)) {
			feedThrough.setFont(new Font("Arial", Font.PLAIN, 18));
			for (int findItem = 0; findItem < BUTTON_NUMS; findItem++)
				huntForButtonSelected(feedThrough, findItem, selectedButtons);
		}
	}
	
	// Find selected number and lock if the tile checker is on.
	private void huntForButtonSelected(SudokuButton feedThrough, int findItem, int[] selectedButtons) {
		if (selectedButtons[findItem]==1) {
			feedThrough.setForeground(new Color(45,50,45));
			feedThrough.setValue(findItem+1);
			showThis=String.valueOf(feedThrough.buttonValue);
			if (holder.thisPuzzle.lockCheck == 1) placeListener(feedThrough);
		}
	}
	
	// Lock down a cell if the tile checker is enabled.
	private void placeListener(SudokuButton feedThrough) {
		if (feedThrough.buttonValue == feedThrough.gridRef.Grid[feedThrough.buttonRef][2]) {
			int delay = 1; //milliseconds
			ButtonListener taskPerformer = new ButtonListener(this, feedThrough, holder);
			Timer timer = new Timer(delay, taskPerformer);
			timerActs(feedThrough, timer);
			feedThrough.mainFrame.panel.revalidate();
		}
	}
	
	// Apply value from all buttons if user presses apply
	private int cycleButts (SoundHandler sound, int[] selectedButtons, int[] Items, ApplyNumberButton[] btnArray) {
		sound.playSound(new File("./Sounds/apply.wav"));
		for (int throughThese = 0; throughThese < BUTTON_NUMS; throughThese++) {
			if (selectedButtons[throughThese] == 1) {
				Items[throughThese]=btnArray[throughThese].buttonValue;
				cntButts++;
			}
		}
		return 11;
	}
	
	// Hide pop up window
	private boolean hideIfNoPress() {
		if (cntButts == 0) {
			holder.hideFrame();
			return true;
		}
		return false;
	}
	
	
	// If multiple buttons are pressed, display appropriate feed on the 
	// main game window, in the right cell
	private int multiButtPress(SudokuButton feedThrough, int[] Items) {
		int counter = 0;
		for (int appFig = 0; appFig < BUTTON_NUMS; appFig++) {
			feedThrough.setFont(new Font("Arial", Font.PLAIN, 10));
			if (Items[appFig] == 0) showThis += String.valueOf("_");  
			else {
				counter++;
				showThis += String.valueOf(Items[appFig]);
			} 
			if (appFig==2) { showThis += String.valueOf("<br>"); } 
			else if (appFig==5) showThis += String.valueOf("<br>");
			feedThrough.setValue(11);
		}
		return counter;
	}
	
	// Timer for lockout
	private void timerActs(SudokuButton feedThrough, Timer timer) {
		if (actLimit==40)  { 
			UIManager.getDefaults().put("Button.disabledText", Color.WHITE);
			feedThrough.setForeground(Color.WHITE);
			feedThrough.revalidate();
			timer.stop();;
		} else {
			timer.setRepeats(true);
			timer.start();
		}
	}
	
	// Make sure menu bars are put to bed if they inadvertently show up
	private void flashMenu() {
		holder.thisPuzzle.menu.toolsMenu.setSelected(true);
		holder.thisPuzzle.menu.toolsMenu.setSelected(false);
	}

	// Change colour of button on the pad if selected
	public void selectButton(ApplyNumberButton btn, int[] selectedButtons) {
		btn.isSelected=true;
		btn.setBackground(new Color(140,140,170));
		if (buttonValue < 11) selectedButtons[buttonValue-1]=1;
	}

	// Deselect button on the pad
	public void deselectButton(ApplyNumberButton btn, int[] selectedButtons) {
		btn.isSelected=false;
		btn.setBackground(Color.WHITE);
		selectedButtons[buttonValue-1]=0;
	}

	// Clear pad and clear cell on grid
	public void deactivateButton() {
		this.setText(String.valueOf(""));
		this.getDisabledIcon();
		this.setEnabled(false);
		this.setOpaque(false);
	}
}

