import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

// Listener to remove all values on completion of a level and display level complete
public class ShowListener implements ActionListener {

	ArrayList <Integer> cellNums = null;
	Random randNum = null;
	SudokuFrame mainFrame = null;
	
	public ShowListener(ArrayList <Integer> cellNums, Random randNum, SudokuFrame mainFrame) {
		this.cellNums = cellNums;
		this.randNum = randNum;
		this.mainFrame = mainFrame;
	}
	
	public void actionPerformed(ActionEvent evt) {
		  try {
			  try {
				  if (!cellNums.isEmpty()) {
					  int thisCell = randNum.nextInt(cellNums.size());
					  paintPanel(thisCell);
					  Thread.sleep(2);
				  }
			  } catch (InterruptedException e) { e.printStackTrace();	}
		  }
	  catch (Exception e) { e.printStackTrace(); }
    }
	
	private void paintPanel(int thisCell) {
		mainFrame.panel.btn[((int)cellNums.get(thisCell))].setVisible(true);
		mainFrame.panel.revalidate();
		mainFrame.frame.revalidate();
		mainFrame.frame.repaint();
		cellNums.remove(thisCell);
	}
	
}
