package Model;

import java.util.ArrayList;

public class model {

	private ArrayList<cell> cells;
	private int dimension;
	private int low_birth_threshold;
	private int high_birth_threshold;
	private int low_survive_threshold;
	private int high_survive_threshold;
	private boolean allow_paint;

	public model(int dimension, int lb, int hb, int ls, int hs) {
		this.cells = new ArrayList<>();
		this.dimension = dimension;
		this.low_birth_threshold = lb;
		this.high_birth_threshold = hb;
		this.low_survive_threshold = ls;
		this.high_survive_threshold = hs;

		initializeCells();
		generateRandomCells();
	}

	public void setAllowPainting(boolean allow_paint) {
		this.allow_paint = allow_paint;
	}
	
	public boolean isAllow_paint() {
		return allow_paint;
	}

	private void initializeCells() {
		for (int i = 0; i < dimension * dimension; i++) {
			cells.add(new cell());
		}
		setStatusBoard();
	}

	private int[][] setStatusBoard() {
		int[][] cells_status_board = new int[dimension][dimension];
		for (int row = 0; row < dimension; row++) {
			for (int column = 0; column < dimension; column++) {
				if (cells.get(row * dimension + column).isActive()) {
					cells_status_board[row][column] = 1;
				} else {
					cells_status_board[row][column] = 0;
				}
			}
		}
		return cells_status_board;
	}

	// make sure about 33.33% of the cell is active at first
	public void generateRandomCells() {
		resetCells();
		for (int row = 0; row < dimension; row++) {
			for (int column = 0; column < dimension; column++) {
				int rdn = (int) (Math.random() * 10);
				if (rdn % 3 == 0) {
					cells.get(row * dimension + column).setActive(true);
				}
			}
		}
	}

	public void resetCells() {
		for (cell c : cells)
			c.setActive(false);
	}

	public ArrayList<cell> getCells() {
		return cells;
	}

	public void nextGeneration(boolean is_torus) {
		int[][] cells_status_board = setStatusBoard();
		int[][] new_generation;
		if (is_torus) {
			new_generation = updateNextGeneration_torus(cells_status_board);
		} else {
			new_generation = updateNextGeneration(cells_status_board);
		}

		for (int row = 0; row < dimension; row++) {
			for (int column = 0; column < dimension; column++) {
				if (new_generation[row][column] == 1) {
					cells.get(row * dimension + column).setActive(true);
				} else {
					cells.get(row * dimension + column).setActive(false);
				}
			}
		}

	}
	
	public int[][] updateNextGeneration_torus(int[][] status_board) {
		int m = status_board.length, n = status_board[0].length;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				int lives = 0;

				//torus check
                if(i == 0) {
                	lives += status_board[m - 1][j] == 1 || status_board[m - 1][j] == 2 ? 1 : 0;
                }
                if(j == 0){
                    lives += status_board[i][n - 1] == 1 || status_board[i][n - 1] == 2 ? 1 : 0;
                }
                if(i == m - 1){
                    lives += status_board[0][j] == 1 || status_board[0][j] == 2 ? 1 : 0;
                }
                if(j == n - 1){
                    lives += status_board[i][0] == 1 || status_board[i][0] == 2 ? 1 : 0;
                }
                if(i == 0 && j == 0){
                    lives += status_board[m - 1][n - 1] == 1 || status_board[m - 1][n - 1] == 2 ? 1 : 0;
                }
                if(i > 0 && j == 0) {
                	lives += status_board[i - 1][n - 1] == 1 || status_board[i - 1][n - 1] == 2 ? 1 : 0;
                }
                if(i == 0 && j > 0) {
                	lives += status_board[m - 1][j - 1] == 1 || status_board[m - 1][j - 1] == 2 ? 1 : 0;
                }
                if(i == m - 1 && j == n - 1){
                    lives += status_board[0][0] == 1 || status_board[0][0] == 2 ? 1 : 0;
                }
                if(i == m - 1 && j < n - 1) {
                	lives += status_board[0][j + 1] == 1 || status_board[0][j + 1] == 2 ? 1 : 0;
                }
                if(i < m - 1 && j == n - 1) {
                	lives += status_board[i + 1][0] == 1 || status_board[i + 1][0] == 2 ? 1 : 0;
                }
                if(i == 0 && j == n - 1){
                    lives += status_board[m - 1][0] == 1 || status_board[m - 1][0] == 2 ? 1 : 0;
                }
                if(i == 0 && j < n - 1) {
                	lives += status_board[m - 1][j + 1] == 1 || status_board[m - 1][j + 1] == 2 ? 1 : 0;
                }
                if(i > 0 && j == n - 1) {
                	lives += status_board[i - 1][0] == 1 || status_board[i - 1][0] == 2 ? 1 : 0;
                }
                
                if(i == m - 1 && j == 0){
                    lives += status_board[0][n - 1] == 1 || status_board[0][n - 1] == 2 ? 1 : 0;
                }
                if(i < m - 1 && j == 0) {
                	lives += status_board[i + 1][n - 1] == 1 || status_board[i + 1][n - 1] == 2 ? 1 : 0;
                }
                if(i == m - 1 && j > 0) {
                	lives += status_board[0][j - 1] == 1 || status_board[0][j - 1] == 2 ? 1 : 0;
                }
                
                
                //normal check
                // check upper
                if(i > 0){
                    lives += status_board[i - 1][j] == 1 || status_board[i - 1][j] == 2 ? 1 : 0;
                }
                // check left
                if(j > 0){
                    lives += status_board[i][j - 1] == 1 || status_board[i][j - 1] == 2 ? 1 : 0;
                }
                // check lower
                if(i < m - 1){
                    lives += status_board[i + 1][j] == 1 || status_board[i + 1][j] == 2 ? 1 : 0;
                }
                // check right
                if(j < n - 1){
                    lives += status_board[i][j + 1] == 1 || status_board[i][j + 1] == 2 ? 1 : 0;
                }
                // check upper left
                if(i > 0 && j > 0){
                    lives += status_board[i - 1][j - 1] == 1 || status_board[i - 1][j - 1] == 2 ? 1 : 0;
                }
                // check lower right
                if(i < m - 1 && j < n - 1){
                    lives += status_board[i + 1][j + 1] == 1 || status_board[i + 1][j + 1] == 2 ? 1 : 0;
                }
                // check upper right
                if(i > 0 && j < n - 1){
                    lives += status_board[i - 1][j + 1] == 1 || status_board[i - 1][j + 1] == 2 ? 1 : 0;
                }
                // check lower left
                if(i < m - 1 && j > 0){
                    lives += status_board[i + 1][j - 1] == 1 || status_board[i + 1][j - 1] == 2 ? 1 : 0;
                }
                // update the status based on surroudings
                if(status_board[i][j] == 0) {
                	if(lives == this.getLow_birth_threshold() || lives == this.getHigh_birth_threshold()) {
                		status_board[i][j] = 3;
                	}
                } else if(status_board[i][j] == 1){
                	if(lives != this.getLow_survive_threshold() && lives != this.getHigh_survive_threshold()) { 
                		status_board[i][j] = 2;
                	}
                }
            }
        }
		// decode
		int[][] new_status_board = new int[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				new_status_board[i][j] = status_board[i][j] % 2;
			}
		}
		return new_status_board;
	}

	public int[][] updateNextGeneration(int[][] status_board) {
		int m = status_board.length, n = status_board[0].length;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				int lives = 0;
				
				// check upper
                if(i > 0){
                    lives += status_board[i - 1][j] == 1 || status_board[i - 1][j] == 2 ? 1 : 0;
                }
                // check left
                if(j > 0){
                    lives += status_board[i][j - 1] == 1 || status_board[i][j - 1] == 2 ? 1 : 0;
                }
                // check lower
                if(i < m - 1){
                    lives += status_board[i + 1][j] == 1 || status_board[i + 1][j] == 2 ? 1 : 0;
                }
                // check right
                if(j < n - 1){
                    lives += status_board[i][j + 1] == 1 || status_board[i][j + 1] == 2 ? 1 : 0;
                }
                // check upper left
                if(i > 0 && j > 0){
                    lives += status_board[i - 1][j - 1] == 1 || status_board[i - 1][j - 1] == 2 ? 1 : 0;
                }
                // check lower right
                if(i < m - 1 && j < n - 1){
                    lives += status_board[i + 1][j + 1] == 1 || status_board[i + 1][j + 1] == 2 ? 1 : 0;
                }
                // check upper right
                if(i > 0 && j < n - 1){
                    lives += status_board[i - 1][j + 1] == 1 || status_board[i - 1][j + 1] == 2 ? 1 : 0;
                }
                // check lower left
                if(i < m - 1 && j > 0){
                    lives += status_board[i + 1][j - 1] == 1 || status_board[i + 1][j - 1] == 2 ? 1 : 0;
                }
                // update the status based on surroudings
                if(status_board[i][j] == 0) {
                	if(lives == this.getLow_birth_threshold() || lives == this.getHigh_birth_threshold()) {
                		status_board[i][j] = 3;
                	}
                } else if(status_board[i][j] == 1){
                	if(lives != this.getLow_survive_threshold() && lives != this.getHigh_survive_threshold()) { 
                		status_board[i][j] = 2;
                	}
                }
            }
        }
		// decode
		int[][] new_status_board = new int[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				new_status_board[i][j] = status_board[i][j] % 2;
			}
		}

		return new_status_board;

	}
	
	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}
	
	public void setLow_survive_threshold(int low_survive_threshold) {
		this.low_survive_threshold = low_survive_threshold;
	}

	public int getLow_survive_threshold() {
		return low_survive_threshold;
	}

	public int getHigh_birth_threshold() {
		return high_birth_threshold;
	}

	public void setHigh_birth_threshold(int high_birth_threshold) {
		this.high_birth_threshold = high_birth_threshold;
	}

	public int getLow_birth_threshold() {
		return low_birth_threshold;
	}

	public void setLow_birth_threshold(int low_birth_threshold) {
		this.low_birth_threshold = low_birth_threshold;
	}

	public int getHigh_survive_threshold() {
		return high_survive_threshold;
	}

	public void setHigh_survive_threshold(int high_survive_threshold) {
		this.high_survive_threshold = high_survive_threshold;
	}
}