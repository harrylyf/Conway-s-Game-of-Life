package GUI;

import GUI.GameOfLife_PaintFrame;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import java.awt.event.*;
import java.util.Timer;
import java.awt.*;
import java.util.TimerTask;

public class GameOfLifeMain extends JFrame {

	private JPanel mainPanel;
	
	private GameOfLife_PaintFrame paint_frame;
	
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

	public static void main(String[] args) {
		new GameOfLifeMain();
	}

	public GameOfLifeMain() {
		setTitle("Game Of Life");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(new Dimension(1000, 600));

		this.timerSpeed = 50;
		this.generation = 0;
		this.paint_frame_Size = paint_frame_Size;

		main_panel();
		paint_frame();
		option_panel();
		click_listener();
		check_window_close();
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void reset() {
		stopTimer();
		main_panel();
		paint_frame();
		option_panel();
		click_listener();
		check_window_close();
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void main_panel() {
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.LIGHT_GRAY);
		this.setContentPane(mainPanel);
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
		paint_frame = new GameOfLife_PaintFrame(paint_frame_Size * multiplier, paint_frame_Size, low_birth_threshold,
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

	private void threshold_message_update() {
		lbthresholdsvalues.setText("low birth threshold: " + paint_frame.getCells().getLow_birth_threshold());
		hbthresholdsvalues.setText("high birth threshold: " + paint_frame.getCells().getHigh_birth_threshold());
		lsthresholdsvalues
				.setText("low survive threshold: " + paint_frame.getCells().getLow_survive_threshold());
		hsthresholdsvalues
				.setText("high survive threshold: " + paint_frame.getCells().getHigh_survive_threshold());
		threshold_change_invalid.setForeground(Color.green);
	}
	
	private void click_listener() {

		threshold_changeBtn.addActionListener(e -> {
			if (thresholdCombo_selection == 0) {
				low_birth_threshold = 3;
				high_birth_threshold = 3;
				low_survive_threshold = 2;
				high_survive_threshold = 3;
				paint_frame.getCells().setLow_birth_threshold(low_birth_threshold);
				paint_frame.getCells().setHigh_birth_threshold(high_birth_threshold);
				paint_frame.getCells().setLow_survive_threshold(low_survive_threshold);
				paint_frame.getCells().setHigh_survive_threshold(high_survive_threshold);

				reset();

				thresholdCombo.setSelectedIndex(thresholdCombo_selection);
				threshold_message_update();
				threshold_change_invalid.setText("Successfully changed to Default Setting!");
			}
			if (thresholdCombo_selection != 0 && threshold_change.getText().matches("[0-9]+")) {
				String change_int = threshold_change.getText();
				if (thresholdCombo_selection == 1) {
					low_birth_threshold = Integer.parseInt(threshold_change.getText());
					paint_frame.getCells().setLow_birth_threshold(low_birth_threshold);

				} else if (thresholdCombo_selection == 2) {
					high_birth_threshold = Integer.parseInt(threshold_change.getText());
					paint_frame.getCells().setHigh_birth_threshold(high_birth_threshold);
				} else if (thresholdCombo_selection == 3) {
					low_survive_threshold = Integer.parseInt(threshold_change.getText());
					paint_frame.getCells().setLow_survive_threshold(low_survive_threshold);
				} else if (thresholdCombo_selection == 4) {
					high_survive_threshold = Integer.parseInt(threshold_change.getText());
					paint_frame.getCells().setHigh_survive_threshold(high_survive_threshold);
				}

				reset();

				thresholdCombo.setSelectedIndex(thresholdCombo_selection);
				threshold_change.setText(change_int);
				threshold_message_update();
				threshold_change_invalid.setText("Success!");
			}
			if (thresholdCombo_selection != 0 && !threshold_change.getText().matches("[0-9]+")) {
				threshold_change_invalid.setForeground(Color.red);
				threshold_change_invalid.setText("Invalid input: only integers 1-99 are accepted.");
			}

		});

		paintBtn.addActionListener(e -> {
			if (paintBtn.getText().equals("Enable")) {
				paint_frame.setAllowPainting(true);
				paintBtn.setText("Disable");
				allowpaintLabel.setText("Paint Mode: Enabled");
			} else if (paintBtn.getText().equals("Disable")) {
				paint_frame.setAllowPainting(false);
				paintBtn.setText("Enable");
				allowpaintLabel.setText("Paint Mode: Disabled");
			}
		});

		torusBtn.addActionListener(e -> {
			if (isTorusLabel.getText().equals("Torus Mode: Disabled")) {
				torusBtn.setText("Disable");
				isTorusLabel.setText("Torus Mode: Enabled");
			} else if (isTorusLabel.getText().equals("Torus Mode: Enabled")) {
				torusBtn.setText("Enable");
				isTorusLabel.setText("Torus Mode: Disabled");
			}
		});

		nextGenBtn.addActionListener(e -> {
			if (isTorusLabel.getText().equals("Torus Mode: Disabled")) {
				paint_frame.getCells().nextGeneration(false);
				paint_frame.repaint();

				generation++;
				generation_label_update();
			} else if (isTorusLabel.getText().equals("Torus Mode: Enabled")) {
				paint_frame.getCells().nextGeneration(true);
				paint_frame.repaint();

				generation++;
				generation_label_update();
			}
		});

		randomBtn.addActionListener(e -> {
			paint_frame.getCells().generateRandomCells();

			generation = 0;
			generation_label_update();
			stopTimer();
			paint_frame.repaint();
		});

		clearBtn.addActionListener(e -> {
			paint_frame.getCells().resetCells();

			generation = 0;
			generation_label_update();
			stopTimer();
			paint_frame.repaint();
		});

		startBtn.addActionListener(e -> {
			if (startBtn.getText().equals("Start")) {
				startTimer();
			} else if (startBtn.getText().equals("Stop")) {
				stopTimer();
			}
		});

		resizeBtn.addActionListener(e -> {
			reset();
		});

		timeSlider.addChangeListener(e -> {
			int value = timeSlider.getValue();
			timerSpeed = value;
			timeLabel.setText("Update Delay: " + value + " ms");
			if (timer != null) {
				stopTimer();
				startTimer();
			}
		});

		sizeSlider.addChangeListener(e -> {
			int size_value = sizeSlider.getValue();
			paint_frame_Size = size_value;
			sizeLabel.setText("Size: " + size_value + " x " + size_value);
		});

		thresholdCombo.addActionListener(e -> {
			thresholdCombo_selection = thresholdCombo.getSelectedIndex();
			if (thresholdCombo_selection == 0) {
				threshold_change.setText("");
				threshold_change.setEditable(false);
			} else {
				threshold_change.setEditable(true);
			}
		});
	}

	private void startTimer() {
		startBtn.setText("Stop");

		timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {

				if (isTorusLabel.getText().equals("Torus Mode: Disabled")) {
					paint_frame.getCells().nextGeneration(false);
					paint_frame.repaint();

					generation++;
					generation_label_update();
				} else if (isTorusLabel.getText().equals("Torus Mode: Enabled")) {
					paint_frame.getCells().nextGeneration(true);
					paint_frame.repaint();

					generation++;
					generation_label_update();
				}
			}
		}, 500, timerSpeed);
	}

	private void stopTimer() {
		startBtn.setText("Start");

		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	private void generation_label_update() {
		generationLabel.setText("Generation: " + generation);
	}

	private void check_window_close() {
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				if (timer != null) {
					timer.cancel();
					timer = null;
				}
			}
		});
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

}
