import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.UIManager;

// Listener for title screen tiles 
public class TitleListener implements ActionListener{

	SudokuFrame caller = null;
	
	public TitleListener(SudokuFrame caller) {
		this.caller = caller;
	}
	
	public void actionPerformed(ActionEvent evt) {
		for (SudokuButton sbtn : caller.panel.btn) sbtn.setText("");
		displayTitle();
		for (SudokuButton sbtn: caller.panel.btn) manipulateButton(sbtn);
		try { Thread.sleep(10); }
		catch (InterruptedException e) { e.printStackTrace(); }		
	}
	  
	// Print title on front screen
	private void displayTitle() {
		caller.panel.pasteWord(18, "SU-DO-KU!");
		caller.panel.pasteWord(37, "WITHOUT");
		caller.panel.pasteWord(55, "BORDERS");
	}
		
	// Random colour
	private void manipulateButton(SudokuButton sbtn) {
		sbtn.setEnabled(false);
		Random randR = new Random();
	    Random randG = new Random();
	    Random randB = new Random();
	    int addR = randR.nextInt((1 - 1) + 1) + 1;
	    int addG = randG.nextInt((1 - 1) + 1) + 1;
	    int addB = randB.nextInt((1 - 1) + 1) + 1;
		manipulateColours(caller, addR, addG, addB, sbtn);
	}
	
	// Lock buttons
	private void manipulateColours(SudokuFrame caller, int addR, int addG, int addB, SudokuButton sbtn) {
	    manipulateR(caller, addR);
	    manipulateG(caller, addG);
	    manipulateB(caller, addB);
		UIManager.getDefaults().put("Button.disabledText", Color.YELLOW);
		sbtn.setEnabled(false);
		sbtn.setBackground(new Color(caller.flipR, caller.flipG, caller.flipB));
		caller.invocation.frame.frame.revalidate();
	}
		 
		// Switch R each tick
		private void manipulateR (SudokuFrame caller, int addR) {
			  if (caller.flipSwitchR == false) { caller.flipR += addR; } 
			  else { caller.flipR -= addR; }
			    if (caller.flipR >= 90) {
			    	caller.flipSwitchR = true;
			    	caller.flipR = 90;
			    }
				if (caller.flipR <= 70) {
					caller.flipSwitchR = false;
					caller.flipR = 70;
				}
		  }

		// Switch G each tick
		private void manipulateG (SudokuFrame caller, int addG) {
			  if (caller.flipSwitchG == false) { caller.flipG += addG; } 
			  else { caller.flipG -= addG; }
				if (caller.flipG >= 70) {
					caller.flipSwitchG = true;
					caller.flipG = 70;
				}
				if (caller.flipG <= 50) {
					caller.flipSwitchG = false;
					caller.flipG = 50;
				}
		  }
	
		  // Switch B each tick
		  private void manipulateB (SudokuFrame caller, int addB) {
			    if (caller.flipSwitchB == false) { caller.flipB += addB; } 
			    else { caller.flipB -= addB; }
				if (caller.flipB >= 130) {
					caller.flipSwitchB = true;
					caller.flipB = 130;
				}
				if (caller.flipB <= 108) {
					caller.flipSwitchB = false;
					caller.flipB = 108;
				}
		  }
	
}
