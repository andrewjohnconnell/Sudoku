

public class Puzzle {

	// Grid is created as a separate object
	// Class arrays:
	//				ApplyFrom
	//				DiscountThese
	// Class integers:
	//				ApplyThis
	//				DTCount
	
	// Methods:
	//				generateRelVals
	//				populateApplyFrom
	//				readRow
	//				readColumn
	//				readBox
	//				resetDTCount
	//				amendApplyFrom

	static int NUM_BUTTONS = 9;
	static int DISCOUNT_CELLS = 27;
	static int THIRD_ROW = 2;
	static int TOTAL_CELLS = 81;
	
	int[] ApplyFrom = new int[NUM_BUTTONS];
	int[] DiscountThese = new int[DISCOUNT_CELLS];
	int DTCount = 0;
	
	// Generate relevant values that can be entered in the grid. Place zeroes where numbers have
	// been used up previously.
	public void generateRelVals(Grid g) {
		int cX = 0, cY = 0;
		for (int alongY = 0; alongY < NUM_BUTTONS; alongY++) {
			for (int alongX = 0; alongX < NUM_BUTTONS; alongX++) {
				g.Grid[alongX+(NUM_BUTTONS*alongY)][0] = cX;
				g.Grid[alongX+(NUM_BUTTONS*alongY)][1] = cY;
				if (cX == THIRD_ROW) { cX = 0; } 
				else { cX++; }
			}
			if (cY == THIRD_ROW) { cY = 0; } 
			else { cY++; }
		}
	}
	
	// Populate array ApplyFrom with numbers 1 to 9
	public void populateApplyFrom() {
		for (int x = 0; x < NUM_BUTTONS; x++) ApplyFrom[x] = x+1;
	}
	
	// Reset the discount this counter.
	public void resetDTCount() {
		DTCount = 0;
	}
	
	// Read all values from the row related to this cellNumber
	public void readRow(Grid g, int cellNumber) {
		int thisRow = cellNumber/NUM_BUTTONS;
		for (int rowC = 0; rowC < NUM_BUTTONS; rowC ++) {
			DiscountThese[DTCount] = g.Grid[((thisRow*NUM_BUTTONS)+rowC)][THIRD_ROW];
			DTCount++;
		}
	}
	
	// Read all values from the column related to this cellNumber
	public void readCol(Grid g, int cellNumber) {
		int thisCol = cellNumber%NUM_BUTTONS;
		for (int colC = 0; colC < NUM_BUTTONS; colC ++) {
			DiscountThese[DTCount] = g.Grid[(thisCol+(colC*NUM_BUTTONS))][THIRD_ROW];
			DTCount++;
		}
	}
	
	// Read all values from the 9 boxes within this box on the grid related to the cellNumber.
	public void readBox(Grid g, int cellNumber) {
		int thisInitCell = (cellNumber-(g.Grid[cellNumber][0])-((g.Grid[cellNumber][1])*NUM_BUTTONS));
		for (int tY = 0; tY <= THIRD_ROW; tY++)
			for (int tX = 0; tX <= THIRD_ROW; tX++) {
				DiscountThese[DTCount] = g.Grid[((thisInitCell)+tX+(tY*NUM_BUTTONS))][THIRD_ROW];
				DTCount++;
			}
	}
	
	public void amendApplyFrom(Grid g, int cellNumber) {
		// Takes the array DiscountThese and reduces the ApplyFrom array to a suitable size
		// by placing the values remaining in ApplyFrom > 0 in to a new array of
		// currently unspecified size and then choosing one of these numbers at random to try
		// slotting in the cell.  
		for (int aDT = 0; aDT < DISCOUNT_CELLS; aDT++)
			for (int aAF = 0; aAF < NUM_BUTTONS; aAF++)
				if (Integer.valueOf(DiscountThese[aDT]).equals(Integer.valueOf(ApplyFrom[aAF]))) ApplyFrom[aAF] = 0; 
	}
	
	// Apply a number to a cell within the grid.
	public void applyNum(Grid g, int cellNumber) {
		int notZero = 0;
		int[] takeThese = new int[NUM_BUTTONS];
		
		for (int tN = 0; tN < NUM_BUTTONS; tN++)
			if(Integer.valueOf(ApplyFrom[tN]).equals(Integer.valueOf(tN+1))) takeThese[notZero++] = ApplyFrom[tN]; 
		if (Integer.valueOf(notZero).equals(Integer.valueOf(0))) { } 
		else {
			int[] fromThese = new int[notZero];
			for (int nZ = 0; nZ < notZero; nZ++) fromThese[nZ] = takeThese[nZ];			
			g.Grid[cellNumber][THIRD_ROW] = fromThese[(int)(Math.random()*notZero)];
		}
	}
	
	// Methodically reads across rows, down columns, in boxes to make sure there are valid numbers
	// that can be entered.
	public void populateCell(Grid g, int c) {
		readRow(g,c);
		readCol(g,c);
		readBox(g,c);
		amendApplyFrom(g,c);
		applyNum(g,c);
		resetDTCount();
		populateApplyFrom();
	}
	
	// The *MAIN* method of this class - seeks to populate a grid.
	public void populateGrid(Grid g) {
		for (int pG = 0; pG < TOTAL_CELLS; pG++) populateCell(g, pG);
		checkForZero(g);
	}
	
	// Print to system what has been applied most recently
	public void dispLast(Grid g, int x) {
		System.out.println("Cell: " + x + " has had " + g.Grid[x][THIRD_ROW] + " applied.");
	}
	
	public void dispGrid(Grid g) {
//		for (int tG = 0; tG < TOTAL_CELLS; tG++) {
//			System.out.print(g.Grid[tG][THIRD_ROW]+"  ");
//			if ((tG%NUM_BUTTONS)==(NUM_BUTTONS - 1)) System.out.println();
//		}
//		System.out.println();
	}
	
	// Check if zero has been placed in any cells
	public void checkForZero(Grid g) {
		int chkNum = 0;
		for (int zChk = 0; zChk < TOTAL_CELLS; zChk++) {
			if (Integer.valueOf(g.Grid[zChk][THIRD_ROW]).equals(Integer.valueOf(0))) chkNum++;
		}
		if (chkNum==0) {
			this.dispGrid(g);
		} else {
			resetGrid(g);
			populateGrid(g);
		}
	}
	
	// Fetch value of a cell
	public int fetchCell(Grid g, int n) {
		return g.Grid[n][THIRD_ROW];
	}
	
	// Reset all values in a grid where a valid sudoku board has not been generated
	public void resetGrid(Grid g) {
		for (int rG = 0; rG < TOTAL_CELLS; rG++) g.Grid[rG][THIRD_ROW] = 0;
	}
	
}
