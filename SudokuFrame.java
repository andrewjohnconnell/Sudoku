

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

public class SudokuFrame {
	
	Invoke invocation = null;
	Puzzle puzRef;
	Grid gridRef;
	JFrame frame;
	FrameMenu menu = null;
	SudokuPanel panel;
	boolean running = false;
	TitleListener grabListen = null;
	
	int lockCheck = 0;
	int putR = 1;
	int putG = 1;
	int putB = 1;
	int actLimit = 0;
	int flipR = 0;
	int flipG = 0;
	int flipB = 0;
	
	boolean flipSwitchR = false;
	boolean flipSwitchG = false;
	boolean flipSwitchB = false;  
	
	public SudokuFrame (Invoke thisGame, Puzzle p, Grid g) {
		invocation = thisGame;
		puzRef = p;
		gridRef = g;
		frame = new JFrame();
		
		try {
		    UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
		} catch (Exception e) {	
			e.printStackTrace();
		}
		FrameMenu dasMenu = new FrameMenu(frame, this);
		cramAndPack(dasMenu);
	}

	private void cramAndPack(FrameMenu dasMenu) {
		dasMenu.addMenu(frame);
		frame.setLayout(new BorderLayout());
		createWindow(1);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public TitleListener animateScreen() {
		boolean run = true;
		int delay = 1; //milliseconds
		TitleListener taskPerformer = new TitleListener(this);
		  Timer timer = new Timer(delay, taskPerformer);
			  timer.setRepeats(true);
			  timer.start();
		  return taskPerformer;
	}
	
	public void createWindow(int putMenu) {
		if (putMenu == 2) panel = new SudokuPanel(this, puzRef, gridRef, 75);
		if (putMenu == 3) panel = new SudokuPanel(this, puzRef, gridRef, 60);
		if (putMenu == 4) panel = new SudokuPanel(this, puzRef, gridRef, 45);
		if (putMenu == 1 || putMenu == 5) panel = new SudokuPanel(this, puzRef, gridRef, 30);
		if (putMenu == 6) panel = new SudokuPanel(this, puzRef, gridRef, 17);
		if (putMenu == 7) // Ultra easy mode for bug testing
			panel = new SudokuPanel(this, puzRef, gridRef, 80);
		initialiseDisplay();
	}
	
	private void initialiseDisplay() {
		panel.setBackground(new Color(200,180,200));
		panel.initialDisplay();
		frame.setBackground(new Color(200,180,200));
		frame.add(panel, BorderLayout.CENTER);
	}
	
	public void callSeal() {
		panel.sealButtons();
	}
	
	public void populateButtons() {
		panel.populateButtons();
	}
	
	public void displayButtons() {
		panel.displayButtons();
	}
	
	public void setButtonsOnScreen() {
		panel.setButtonsOnScreen();
	}
	
	public void callVis() {
		int cnt = 0;
		for (SudokuButton sbtn: panel.btn) {
			if (sbtn.buttonLive == 1) {
				//System.out.format("NOT LIVE + %d\n", cnt++);
				UIManager.getDefaults().put("Button.disabledText", Color.WHITE);
				sbtn.setEnabled(false);
			}
		}
		
		frame.setVisible(true);
		this.running = true;
	}
	
	public void showCells() {
		ArrayList<Integer> cellNums = new ArrayList<Integer>();
		for (int x = 0; x < 81; x++) cellNums.add(x);
		Random randNum = new Random();
		ShowListener taskPerformer = new ShowListener(cellNums, randNum, this); 
		Timer timer = new Timer(1, taskPerformer);
		timer.setRepeats(true);
		timer.start();
	 }

	public void quickOn() {
	 	for (SudokuButton sbtn : panel.btn) {
	 		sbtn.setVisible(true);
	 	}
	 	panel.revalidate();
		frame.revalidate();
		frame.repaint();
	}
	
	public void buildNewFrame() {
		this.frame.setTitle("Sudoku Sans Frontieres");
		grabListen = this.animateScreen();
		this.callSeal();
		this.populateButtons();
		this.setButtonsOnScreen();
		this.displayButtons();
		this.callVis();
		this.quickOn();	

	}
	
	public void buildFromFrame(Invoke thesePuzzles, int buildNum) {
		this.frame.setTitle("Sudoku Sans Frontieres");
		this.gridRef = thesePuzzles.g;
		this.puzRef = thesePuzzles.s;
		this.createWindow(buildNum);
		this.callSeal();
		this.populateButtons();
		this.setButtonsOnScreen();
		this.displayButtons();
		this.callVis();
		this.showCells();	
	}
	
	
	public void clearFrame() {
		this.frame.removeAll();
	}
	
}
