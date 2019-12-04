package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Timer;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import Controller.paint_frame_controller;

public class View {
	private JFrame frame;
	private JPanel mainPanel;
	private paint_frame_controller paint_frame;
	
	private JLabel generationLabel;
	private JLabel allowpaintLabel;
	private JLabel lbthresholdsvalues;
	private JLabel hbthresholdsvalues;
	private JLabel lsthresholdsvalues;
	private JLabel hsthresholdsvalues;
	private JLabel isTorusLabel;
	private JLabel threshold_change_invalid;
	private JLabel timeLabel;
	private JLabel sizeLabel;
	
	private JTextField threshold_change;

	private JButton threshold_changeBtn;
	private JButton nextGenBtn;
	private JButton startBtn;
	private JButton resizeBtn;
	private JButton randomBtn;
	private JButton clearBtn;
	private JButton paintBtn;
	private JButton torusBtn;

	private JSlider timeSlider;
	private JSlider sizeSlider;
	
	private JComboBox<String> thresholdCombo;
	
	private Timer timer;
	private int timerSpeed;
	private int generation;
	private int paint_frame_Size = 60;
	private int low_birth_threshold = 3;
	private int high_birth_threshold = 3;
	private int low_survive_threshold = 2;
	private int high_survive_threshold = 3;
	private int thresholdCombo_selection;
	private int paint_view_dimension;
	
	public View() {
		frame = new JFrame();
		frame.setTitle("Game Of Life");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(new Dimension(1000, 600));

		this.timerSpeed = 50;
		this.generation = 0;
		this.paint_frame_Size = paint_frame_Size;

		main_panel();
		paint_frame();
		option_panel();

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void init_view() {
		
		main_panel();
		paint_frame();
		option_panel();
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}

	private void main_panel() {
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.LIGHT_GRAY);
		frame.setContentPane(mainPanel);
	}

	private void paint_frame() {
		int multiplier = 11;
		if (paint_frame_Size >= 95 && paint_frame_Size <= 200) {
			multiplier = 5;
		} else if (paint_frame_Size > 200 && paint_frame_Size <= 250) {
			multiplier = 4;
		} else if (paint_frame_Size > 250 && paint_frame_Size <= 300) {
			multiplier = 3;
		} else if (paint_frame_Size > 300 && paint_frame_Size <= 500) {
			multiplier = 2;
		}
		setPaint_view_dimension(paint_frame_Size * multiplier);
		paint_frame = new paint_frame_controller(paint_frame_Size * multiplier, paint_frame_Size, low_birth_threshold,
				high_birth_threshold, low_survive_threshold, high_survive_threshold);
		mainPanel.add(paint_frame);
	}

	private void option_panel() {
		JPanel optionsPanel = new JPanel();
		optionsPanel.setLayout(new GridBagLayout());
		optionsPanel.setPreferredSize(new Dimension(300, 600));
		optionsPanel.setBackground(Color.LIGHT_GRAY);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.CENTER;

		lbthresholdsvalues = new JLabel("low birth threshold: " + paint_frame.getCells().getLow_birth_threshold());
		optionsPanel.add(lbthresholdsvalues, gbc);
		
		hbthresholdsvalues = new JLabel("high birth threshold: " + paint_frame.getCells().getHigh_birth_threshold());
		optionsPanel.add(hbthresholdsvalues, gbc);

		lsthresholdsvalues = new JLabel("low survive threshold: " + paint_frame.getCells().getLow_survive_threshold());
		optionsPanel.add(lsthresholdsvalues, gbc);

		hsthresholdsvalues = new JLabel("high survive threshold: " + paint_frame.getCells().getHigh_survive_threshold());
		optionsPanel.add(hsthresholdsvalues, gbc);

		optionsPanel.add(Box.createVerticalStrut(20), gbc);

		thresholdCombo = new JComboBox<>(
				new String[] { "default", "low birth", "high birth", "low survive", "high survive" });
		threshold_change = new JTextField(2);
		threshold_change.setDocument(new JTextFieldLimit(2));
		threshold_change.setEditable(false);
		threshold_changeBtn = new JButton("Change");
		optionsPanel.add(thresholdCombo);
		optionsPanel.add(threshold_change);
		optionsPanel.add(threshold_changeBtn);

		threshold_change_invalid = new JLabel();

		optionsPanel.add(Box.createVerticalStrut(5), gbc);
		optionsPanel.add(threshold_change_invalid, gbc);

		optionsPanel.add(Box.createVerticalStrut(20), gbc);
		optionsPanel.add(Box.createVerticalStrut(20), gbc);

		generationLabel = new JLabel("Generation: 0");
		optionsPanel.add(generationLabel, gbc);

		optionsPanel.add(Box.createVerticalStrut(10), gbc);
		
		nextGenBtn = new JButton("Next Generation");
		optionsPanel.add(nextGenBtn, gbc);

		startBtn = new JButton("Start");
		optionsPanel.add(startBtn, gbc);

		randomBtn = new JButton("Random");
		optionsPanel.add(randomBtn, gbc);

		clearBtn = new JButton("Clear");
		optionsPanel.add(clearBtn, gbc);

		allowpaintLabel = new JLabel("Paint Mode: Disabled");
		optionsPanel.add(allowpaintLabel, gbc);

		paintBtn = new JButton("Enable");
		optionsPanel.add(paintBtn, gbc);

		isTorusLabel = new JLabel("Torus Mode: Disabled");
		optionsPanel.add(isTorusLabel, gbc);

		torusBtn = new JButton("Enable");
		optionsPanel.add(torusBtn, gbc);

		optionsPanel.add(Box.createVerticalStrut(20), gbc);

		timeSlider = new JSlider(10, 1000);
		timeSlider.setValue(timerSpeed);
		optionsPanel.add(timeSlider, gbc);
		timeLabel = new JLabel("Update Delay: " + timeSlider.getValue() + " ms");
		optionsPanel.add(timeLabel, gbc);

		sizeSlider = new JSlider(10, 500);
		sizeSlider.setValue(paint_frame_Size);
		optionsPanel.add(sizeSlider, gbc);
		sizeLabel = new JLabel("Size: " + sizeSlider.getValue() + " x " + sizeSlider.getValue());
		optionsPanel.add(sizeLabel, gbc);

		resizeBtn = new JButton("Resize");
		optionsPanel.add(resizeBtn, gbc);

		mainPanel.add(optionsPanel);
	}

	class JTextFieldLimit extends PlainDocument {
		private int limit;

		JTextFieldLimit(int limit) {
			super();
			this.limit = limit;
		}

		JTextFieldLimit(int limit, boolean upper) {
			super();
			this.limit = limit;
		}

	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
			if (str == null)
				return;

			if ((getLength() + str.length()) <= limit) {
				super.insertString(offset, str, attr);
			}
		}
	}
	
	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JLabel getAllowpaintLabel() {
		return allowpaintLabel;
	}

	public void setAllowpaintLabel(JLabel allowpaintLabel) {
		this.allowpaintLabel = allowpaintLabel;
	}

	public JButton getClearBtn() {
		return clearBtn;
	}

	public void setClearBtn(JButton clearBtn) {
		this.clearBtn = clearBtn;
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	public paint_frame_controller getPaint_frame() {
		return paint_frame;
	}

	public void setPaint_frame(paint_frame_controller paint_frame) {
		this.paint_frame = paint_frame;
	}

	public JLabel getGenerationLabel() {
		return generationLabel;
	}

	public void setGenerationLabel(JLabel generationLabel) {
		this.generationLabel = generationLabel;
	}

	public JLabel getLbthresholdsvalues() {
		return lbthresholdsvalues;
	}

	public void setLbthresholdsvalues(JLabel lbthresholdsvalues) {
		this.lbthresholdsvalues = lbthresholdsvalues;
	}

	public JLabel getHbthresholdsvalues() {
		return hbthresholdsvalues;
	}

	public void setHbthresholdsvalues(JLabel hbthresholdsvalues) {
		this.hbthresholdsvalues = hbthresholdsvalues;
	}

	public JLabel getLsthresholdsvalues() {
		return lsthresholdsvalues;
	}

	public void setLsthresholdsvalues(JLabel lsthresholdsvalues) {
		this.lsthresholdsvalues = lsthresholdsvalues;
	}

	public JLabel getHsthresholdsvalues() {
		return hsthresholdsvalues;
	}

	public void setHsthresholdsvalues(JLabel hsthresholdsvalues) {
		this.hsthresholdsvalues = hsthresholdsvalues;
	}

	public JLabel getIsTorusLabel() {
		return isTorusLabel;
	}

	public void setIsTorusLabel(JLabel isTorusLabel) {
		this.isTorusLabel = isTorusLabel;
	}

	public JLabel getThreshold_change_invalid() {
		return threshold_change_invalid;
	}

	public void setThreshold_change_invalid(String text, Color color) {
		threshold_change_invalid.setForeground(color);
		threshold_change_invalid.setText(text);
//		this.threshold_change_invalid = threshold_change_invalid;
	}

	public JLabel getTimeLabel() {
		return timeLabel;
	}

	public void setTimeLabel(JLabel timeLabel) {
		this.timeLabel = timeLabel;
	}

	public JLabel getSizeLabel() {
		return sizeLabel;
	}

	public void setSizeLabel(JLabel sizeLabel) {
		this.sizeLabel = sizeLabel;
	}

	public JTextField getThreshold_change() {
		return threshold_change;
	}

	public void setThreshold_change(String text) {
		threshold_change.setText(text);
		this.threshold_change = threshold_change;
	}

	public JButton getThreshold_changeBtn() {
		return threshold_changeBtn;
	}

	public void setThreshold_changeBtn(JButton threshold_changeBtn) {
		this.threshold_changeBtn = threshold_changeBtn;
	}

	public JButton getNextGenBtn() {
		return nextGenBtn;
	}

	public void setNextGenBtn(JButton nextGenBtn) {
		this.nextGenBtn = nextGenBtn;
	}

	public JButton getStartBtn() {
		return startBtn;
	}

	public void setStartBtn(JButton startBtn) {
		this.startBtn = startBtn;
	}

	public JButton getResizeBtn() {
		return resizeBtn;
	}

	public void setResizeBtn(JButton resizeBtn) {
		this.resizeBtn = resizeBtn;
	}

	public JButton getRandomBtn() {
		return randomBtn;
	}

	public void setRandomBtn(JButton randomBtn) {
		this.randomBtn = randomBtn;
	}

	public JButton getPaintBtn() {
		return paintBtn;
	}

	public void setPaintBtn(JButton paintBtn) {
		this.paintBtn = paintBtn;
	}

	public JButton getTorusBtn() {
		return torusBtn;
	}

	public void setTorusBtn(JButton torusBtn) {
		this.torusBtn = torusBtn;
	}

	public JSlider getTimeSlider() {
		return timeSlider;
	}

	public void setTimeSlider(JSlider timeSlider) {
		this.timeSlider = timeSlider;
	}

	public JSlider getSizeSlider() {
		return sizeSlider;
	}

	public void setSizeSlider(JSlider sizeSlider) {
		this.sizeSlider = sizeSlider;
	}

	public JComboBox<String> getThresholdCombo() {
		return thresholdCombo;
	}

	public void setThresholdCombo(JComboBox<String> thresholdCombo) {
		this.thresholdCombo = thresholdCombo;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public int getTimerSpeed() {
		return timerSpeed;
	}

	public void setTimerSpeed(int timerSpeed) {
		this.timerSpeed = timerSpeed;
	}

	public int getGeneration() {
		return generation;
	}

	public void setGeneration(int generation) {
		this.generation = generation;
	}

	public int getPaint_frame_Size() {
		return paint_frame_Size;
	}

	public void setPaint_frame_Size(int paint_frame_Size) {
		this.paint_frame_Size = paint_frame_Size;
	}

	public int getLow_birth_threshold() {
		return low_birth_threshold;
	}

	public void setLow_birth_threshold(int low_birth_threshold) {
		this.low_birth_threshold = low_birth_threshold;
		paint_frame.getCells().setLow_birth_threshold(low_birth_threshold);
	}

	public int getHigh_birth_threshold() {
		return high_birth_threshold;
	}

	public void setHigh_birth_threshold(int high_birth_threshold) {
		this.high_birth_threshold = high_birth_threshold;
		paint_frame.getCells().setHigh_birth_threshold(high_birth_threshold);
	}

	public int getLow_survive_threshold() {
		return low_survive_threshold;
	}

	public void setLow_survive_threshold(int low_survive_threshold) {
		this.low_survive_threshold = low_survive_threshold;
		paint_frame.getCells().setLow_survive_threshold(low_survive_threshold);
	}

	public int getHigh_survive_threshold() {
		return high_survive_threshold;
	}

	public void setHigh_survive_threshold(int high_survive_threshold) {
		paint_frame.getCells().setHigh_survive_threshold(high_survive_threshold);
		this.high_survive_threshold = high_survive_threshold;
	}

	public int getThresholdCombo_selection() {
		return thresholdCombo_selection;
	}

	public void setThresholdCombo_selection(int thresholdCombo_selection) {
		this.thresholdCombo_selection = thresholdCombo_selection;
	}

	public int getPaint_view_dimension() {
		return paint_view_dimension;
	}

	public void setPaint_view_dimension(int paint_view_dimension) {
		this.paint_view_dimension = paint_view_dimension;
	}
}
