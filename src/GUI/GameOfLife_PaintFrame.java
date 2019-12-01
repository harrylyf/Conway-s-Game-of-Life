package GUI;

import cell.cell;
import cell.cell_paint;
import cell.cells_board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class GameOfLife_PaintFrame extends JPanel implements MouseMotionListener, MouseListener {

	private cells_board cells;
	private ArrayList<cell_paint> sections;

	private int dimension;
	private int count;

	private boolean allow_paint;

	public GameOfLife_PaintFrame(int dimension, int count, int lb, int hb, int ls, int hs) {
		this.dimension = dimension;
		this.count = count;
		this.allow_paint = false;

		setPreferredSize(new Dimension(dimension, dimension));
		setBackground(Color.WHITE);

		this.cells = new cells_board(count, lb, hb, ls, hs);
		this.sections = new ArrayList<>();
		generateSections();

		addMouseListener(this);
		addMouseMotionListener(this);
	}

	private void generateSections() {
		int cellSize = dimension / count;
		for (int row = 0; row < count; row++) {
			for (int column = 0; column < count; column++) {
				sections.add(new cell_paint(column * cellSize, row * cellSize, cellSize));
			}
		}

		for (int i = 0; i < sections.size(); i++) {
			cell_paint s = sections.get(i);
			cell cell = cells.getCells().get(i);
			s.setCell(cell);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		drawLines(g);
		fillCells(g);
	}

	private void drawLines(Graphics g) {
		int cellSize = dimension / count;
		g.setColor(Color.LIGHT_GRAY);

		for (int row = 0; row < count; row++) {
			int y = row * cellSize;
			g.drawLine(0, y, dimension, y);
		}

		for (int column = 0; column < count; column++) {
			int x = column * cellSize;
			g.drawLine(x, 0, x, dimension);
		}

		g.drawLine(0, dimension - 1, dimension, dimension - 1);
		g.drawLine(dimension - 1, 0, dimension - 1, dimension);
	}

	private void fillCells(Graphics g) {
		g.setColor(Color.BLACK);

		for (cell_paint s : sections) {
			if (s.getCell().isActive())
				g.fillRect(s.getX() + 1, s.getY() + 1, s.getSize() - 1, s.getSize() - 1);
		}

	}

	public cells_board getCells() {
		return cells;
	}

	public void setCells(int lb, int hb, int ls, int hs) {
		cells.setLow_birth_threshold(lb);
		cells.setHigh_birth_threshold(hb);
		cells.setLow_survive_threshold(ls);
		cells.setHigh_survive_threshold(hs);
	}

	public void setAllowPainting(boolean allow_paint) {
		this.allow_paint = allow_paint;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (allow_paint) {
			for (cell_paint s : sections) {

				int mouseX = e.getX();
				int mouseY = e.getY();

				if (mouseX > s.getX() && mouseY > s.getY() && mouseX < s.getX() + s.getSize()
						&& mouseY < s.getY() + s.getSize() && s.getCell().isActive()) {
					s.getCell().setActive(false);
					repaint();
				} else if (mouseX > s.getX() && mouseY > s.getY() && mouseX < s.getX() + s.getSize()
						&& mouseY < s.getY() + s.getSize() && !s.getCell().isActive()) {
					s.getCell().setActive(true);
					repaint();
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (allow_paint) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				switchCellsState(e, true);
			} else if (SwingUtilities.isRightMouseButton(e)) {
				switchCellsState(e, false);
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	private void switchCellsState(MouseEvent e, boolean newState) {
		for (cell_paint s : sections) {
			int mouseX = e.getX();
			int mouseY = e.getY();
			if (mouseX > s.getX() && mouseY > s.getY() && mouseX < s.getX() + s.getSize()
					&& mouseY < s.getY() + s.getSize()) {
				s.getCell().setActive(newState);
				repaint();
			}
		}
	}

}
