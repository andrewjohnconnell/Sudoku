import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Create a listener for buttons for when they are pressed
public class SendPressListener implements ActionListener{

	ApplyNumberButton btn = null;
	ApplyNumberButton[] buttons = null;
	int[] selectedButtons = null;
	SudokuButton feedThrough = null;
	
	public SendPressListener(ApplyNumberButton btn, ApplyNumberButton[] buttons, 
			int[] selectedButtons, SudokuButton feedThrough) {
		this.btn = btn;
		this.buttons = buttons;
		this.selectedButtons = selectedButtons;
		this.feedThrough = feedThrough;
	}
	
	public void actionPerformed(ActionEvent e) {
			// If there is an action on the button, send a system response
			// Demonstrating the VALUE of the box in question
		btn.sendPress(btn, buttons, selectedButtons, feedThrough);
	}
	
}
