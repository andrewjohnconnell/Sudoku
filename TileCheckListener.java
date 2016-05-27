import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;


public class TileCheckListener implements ActionListener{

	SudokuFrame gameWin = null;
	JMenuItem item = null;
	
	public TileCheckListener(SudokuFrame gameWin, JMenuItem item) {
		this.gameWin = gameWin;
		this.item = item;
	}

	public void actionPerformed(ActionEvent e) {
		if (gameWin.lockCheck == 1) {
    		gameWin.lockCheck = 0;
    		item.setText("Enable Tile Check");
    	} else {
    		gameWin.lockCheck = 1;
    		for (SudokuButton sbtn: gameWin.panel.btn) sbtn.getValue();
    		item.setText("Disable Tile Check");
    	}
	}
	
	
	
}
