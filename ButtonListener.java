import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Button listener to conduct animations for locking

public class ButtonListener implements ActionListener{

	SudokuButton feedThrough = null;
	ApplyNumbersFrame holder = null;
	ApplyNumberButton caller = null;
	
	// Set listener to appropriate button on board
	public ButtonListener(ApplyNumberButton caller, SudokuButton feedThrough, ApplyNumbersFrame holder) {
		this.caller = caller;
		this.feedThrough = feedThrough;
		this.holder = holder;
	}
	
	// Count the buttons to make sure only valid buttons have listeners applied
	private int incCntBtn() {
		int cntBtn = 0;
		for (SudokuButton sbtn : holder.thisPuzzle.panel.btn) {
			if (!sbtn.isEnabled()) cntBtn++;
		}
		return cntBtn;
	}
	
	// Next three set R, G, B values.
	private int setRed() {
		int cur_R = feedThrough.getBackground().getRed();
		return (cur_R -= 3);
	}
	
	private int setGreen() {
		int cur_G = feedThrough.getBackground().getGreen();
		return (cur_G -= 3);
	}
	
	private int setBlue() {
		int cur_B = feedThrough.getBackground().getBlue();
		return (cur_B -= 3);
	}
	
	// Whether a button needs to be revalidated and updated.
	private void validate_AmendAct() {
		feedThrough.revalidate();
		feedThrough.setEnabled(false);
		caller.actLimit++;
	}
	
	// Make sure not black
	private boolean checkVals(int R, int G, int B) {
		if (R > 0 && G > 0 && B > 0) return true;
		return false;
	}
	
	// Change colours!
	private void amendColours() {
		if (caller.actLimit != 40) {
			int R = setRed(); int G = setGreen(); int B = setBlue();
			if (checkVals(R, G, B))	feedThrough.setBackground(new Color(R, G, B));									
			validate_AmendAct();
			try { Thread.sleep(1); }
			catch (InterruptedException e) { e.printStackTrace(); }
		}
	}
	
	// If an action is performed on the button and the lock is applied, shift the colours
	public void actionPerformed(ActionEvent evt) {
		if (incCntBtn() < 81) if (holder.thisPuzzle.lockCheck == 1) amendColours();
	}

	
}
