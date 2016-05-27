import javax.swing.*;
import javax.swing.JFrame;

// Invoke the program

public class Invoke {
	
	SudokuFrame frame = null;
	static Invoke thisGame = null;
	Puzzle s = null;
	Grid g = null;
	
	public static void main (String[] args) {
		thisGame = new Invoke();
		thisGame.run(1);
	}
	
	// Run encapsulates the build and display of a new sudoku game
	public void run(int buildFrame) {
		genPop();
		frame = new SudokuFrame(this.thisGame, s, g);
		if (buildFrame == 1) {
			frame.frame.setLocationRelativeTo(null);
			frame.buildNewFrame();
		}
		if (buildFrame != 1) frame.buildFromFrame(this, buildFrame);
	}
	
	// Populate a grid on screen
	private void genPop() {
		g = new Grid();
		s = new Puzzle();
		s.generateRelVals(g);
		s.populateApplyFrom();
		s.populateGrid(g);
	}
	
}
