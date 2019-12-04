package Model;

public class cell_paint {
	private cell cell;
    private int x;
    private int y;
    private int size;
    
    public cell_paint(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public void setCell(cell cell) {
        this.cell = cell;
    }

    public cell getCell() {
        return cell;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }
    
}
