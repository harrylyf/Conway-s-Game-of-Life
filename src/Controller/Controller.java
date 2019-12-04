package Controller;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JComboBox;

import Model.*;
import View.*;

public class Controller {
	
	private model model;
	private View view;
	private Timer timer;
	private int thresholdCombo_selection;
	
	public Controller(View v, model m) {
		  view = v;
		  model = m;	  
	}
	
	public void initController() {
		
		view.getLbthresholdsvalues().setText("low birth threshold: " + model.getLow_birth_threshold());
		
		view.getThreshold_changeBtn().addActionListener(e -> {
			
			if (thresholdCombo_selection == 0) {
				model.setLow_birth_threshold(3);
				view.setLow_birth_threshold(3);
				model.setHigh_birth_threshold(3);
				view.setHigh_birth_threshold(3);
				model.setLow_survive_threshold(2);
				view.setLow_survive_threshold(2);
				model.setHigh_survive_threshold(3);
				view.setHigh_survive_threshold(3);
				
				reset();

				view.getThresholdCombo().setSelectedIndex(thresholdCombo_selection);
				threshold_message_update();
				view.setThreshold_change_invalid("Successfully changed to Default Setting!", Color.green);
			}
			if (thresholdCombo_selection != 0 && view.getThreshold_change().getText().matches("[0-9]+")) {
				String change_int = view.getThreshold_change().getText();
				if (thresholdCombo_selection == 1) {
					model.setLow_birth_threshold(Integer.parseInt(change_int));
					view.setLow_birth_threshold(Integer.parseInt(change_int));
				} else if (thresholdCombo_selection == 2) {
					model.setHigh_birth_threshold(Integer.parseInt(change_int));
					view.setHigh_birth_threshold(Integer.parseInt(change_int));
				} else if (thresholdCombo_selection == 3) {
					model.setLow_survive_threshold(Integer.parseInt(change_int));
					view.setLow_survive_threshold(Integer.parseInt(change_int));
				} else if (thresholdCombo_selection == 4) {
					model.setHigh_survive_threshold(Integer.parseInt(change_int));
					view.setHigh_survive_threshold(Integer.parseInt(change_int));
				}

				reset();
				
				JComboBox<String> new_thresholdCombo = view.getThresholdCombo();
				new_thresholdCombo.setSelectedIndex(thresholdCombo_selection);
				view.setThreshold_change(change_int);
				threshold_message_update();
				view.setThreshold_change_invalid("Success!", Color.green);
			}
			if (thresholdCombo_selection != 0 && !view.getThreshold_change().getText().matches("[0-9]+")) {
				view.setThreshold_change_invalid("Invalid input: only integers 1-99 are accepted.", Color.red);
			}

		});

		view.getPaintBtn().addActionListener(e -> {
			if (view.getPaintBtn().getText().equals("Enable")) {
				view.getPaint_frame().setAllowPainting(true);
				view.getPaintBtn().setText("Disable");
				view.getAllowpaintLabel().setText("Paint Mode: Enabled");
			} else if (view.getPaintBtn().getText().equals("Disable")) {
				view.getPaint_frame().setAllowPainting(false);
				view.getPaintBtn().setText("Enable");
				view.getAllowpaintLabel().setText("Paint Mode: Disabled");
			}
		});

		view.getTorusBtn().addActionListener(e -> {
			if (view.getIsTorusLabel().getText().equals("Torus Mode: Disabled")) {
				view.getTorusBtn().setText("Disable");
				view.getIsTorusLabel().setText("Torus Mode: Enabled");
			} else if (view.getIsTorusLabel().getText().equals("Torus Mode: Enabled")) {
				view.getTorusBtn().setText("Enable");
				view.getIsTorusLabel().setText("Torus Mode: Disabled");
			}
		});

		view.getNextGenBtn().addActionListener(e -> {
			if (view.getIsTorusLabel().getText().equals("Torus Mode: Disabled")) {
				view.getPaint_frame().getCells().nextGeneration(false);
				view.getPaint_frame().repaint();

				view.setGeneration(view.getGeneration()+1);
				generation_label_update();
			} else if (view.getIsTorusLabel().getText().equals("Torus Mode: Enabled")) {
				view.getPaint_frame().getCells().nextGeneration(true);
				view.getPaint_frame().repaint();

				view.setGeneration(view.getGeneration()+1);
				generation_label_update();
			}
		});

		view.getRandomBtn().addActionListener(e -> {
			view.getPaint_frame().getCells().generateRandomCells();

			view.setGeneration(0);
			generation_label_update();
			stopTimer();
			view.getPaint_frame().repaint();
		});

		view.getClearBtn().addActionListener(e -> {
			view.getPaint_frame().getCells().resetCells();

			view.setGeneration(0);
			generation_label_update();
			stopTimer();
			view.getPaint_frame().repaint();
		});

		view.getStartBtn().addActionListener(e -> {
			if (view.getStartBtn().getText().equals("Start")) {
				startTimer();
			} else if (view.getStartBtn().getText().equals("Stop")) {
				stopTimer();
			}
		});

		view.getResizeBtn().addActionListener(e -> {
			reset();
		});

		view.getTimeSlider().addChangeListener(e -> {
			int value = view.getTimeSlider().getValue();
			view.setTimerSpeed(value);
			view.getTimeLabel().setText("Update Delay: " + value + " ms");
			if (timer != null) {
				stopTimer();
				startTimer();
			}
		});

		view.getSizeSlider().addChangeListener(e -> {
			int size_value = view.getSizeSlider().getValue();
			view.setPaint_frame_Size(size_value);
			view.getSizeLabel().setText("Size: " + size_value + " x " + size_value);
		});

		view.getThresholdCombo().addActionListener(e -> {
			if (view.getThresholdCombo().getSelectedIndex() == 0) {
				view.getThreshold_change().setText("");
				view.getThreshold_change().setEditable(false);
				thresholdCombo_selection = view.getThresholdCombo().getSelectedIndex();
			} else {
				view.getThreshold_change().setEditable(true);
				thresholdCombo_selection = view.getThresholdCombo().getSelectedIndex();
			}
		});
	}

	public void reset() {
		stopTimer();
		view.getFrame().setVisible(false);
		view.init_view();
		initController();
		check_window_close();
//		System.out.println(model.getLow_birth_threshold());
	}
	
	public void threshold_message_update() {
		view.getLbthresholdsvalues().setText("low birth threshold: " + model.getLow_birth_threshold());
		view.getHbthresholdsvalues().setText("high birth threshold: " + model.getHigh_birth_threshold());
		view.getLsthresholdsvalues()
				.setText("low survive threshold: " + model.getLow_survive_threshold());
		view.getHsthresholdsvalues()
				.setText("high survive threshold: " + model.getHigh_survive_threshold());
		view.getThreshold_change_invalid().setForeground(Color.green);
	}
	
	private void startTimer() {
		view.getStartBtn().setText("Stop");

		timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {

				if (view.getIsTorusLabel().getText().equals("Torus Mode: Disabled")) {
					view.getPaint_frame().getCells().nextGeneration(false);
					view.getPaint_frame().repaint();

					view.setGeneration(view.getGeneration()+1);
					generation_label_update();
				} else if (view.getIsTorusLabel().getText().equals("Torus Mode: Enabled")) {
					view.getPaint_frame().getCells().nextGeneration(true);
					view.getPaint_frame().repaint();

					view.setGeneration(view.getGeneration()+1);
					generation_label_update();
				}
			}
		}, 500, view.getTimerSpeed());
	}

	private void stopTimer() {
		view.getStartBtn().setText("Start");

		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	private void generation_label_update() {
		view.getGenerationLabel().setText("Generation: " + view.getGeneration());
	}

	private void check_window_close() {
		view.getFrame().addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				if (timer != null) {
					timer.cancel();
					timer = null;
				}
			}
		});
	}
}
