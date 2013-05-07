package com.siemens.stem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Defines the class that implements the tab controls that are used
 * to enter the data for the ramp.
 * @author Jon Masters
 * 
 */
public class MainActivity extends Activity {

	/**
	 * defines the filename for storing the settings.
	 */
	static private String SETTINGS_FILE = "settings";
	
	/**
	 * Holds the settings for the app.
	 */
	private SettingsData settings;
	
	/**
	 * Holds a reference to the worksheet data.
	 */
	private WorksheetData worksheetData;
	
	/**
	 * Called by the framework to build the options menu.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * Called by the framework when the activity enters the created state. This is where
	 * all the initialization needs to happen.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// set up the tab control
		buildTabs();
		
		// load the data for the settings if it is available
		loadSettingsData();	
		
		// add the settings data to the settings tab
		updateSettingsControls();
		
		// set up the button handlers
		Button settingsSendButton = (Button)findViewById(R.id.buttonSettingsSend);
		settingsSendButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// save the settings
				MainActivity.this.readSettingsControls();
				MainActivity.this.saveSettingsData();
				
				// get a reference to the settings
				SettingsData rampSettings = MainActivity.this.settings;
				
				
				// send the config data to the ramp
				try {
					
					RampWriter.writeRampSettings(
							InetAddress.getByName(rampSettings.getHost()), 
							rampSettings.getPort(), 
							rampSettings.getSpeedDisplayOnTime(), 
							rampSettings.getSimulationPauseTime(), 
							rampSettings.getSpeedLimitThreshold());
					
				} catch (IllegalArgumentException e) {
					MainActivity.this.displayError(
							String.format(MainActivity.this.getString(
									R.string.ramp_connection_error), e.getMessage()));
					e.printStackTrace();
				} catch (UnknownHostException e) {
					MainActivity.this.displayError(
							String.format(MainActivity.this.getString(
									R.string.ramp_connection_error), e.getMessage()));
					e.printStackTrace();
				} catch (IOException e) {
					MainActivity.this.displayError(
							String.format(MainActivity.this.getString(
									R.string.ramp_connection_error), e.getMessage()));
					e.printStackTrace();
				}
			}
		});
		
		Button easySendButton = (Button)findViewById(R.id.buttonEasySend);
		easySendButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// read the data from the controls into the data object
				MainActivity.this.readEasyWorksheetDataControls();
				
				// get an easy ref to the data
				WorksheetData wsd = MainActivity.this.worksheetData;
				SettingsData sd = MainActivity.this.settings;
				
				try {
					
					RampWriter.writeEasySettings(
							InetAddress.getByName(sd.getHost()), 
							sd.getPort(), 
							wsd.getDistanceBetweenLoops(), 
							wsd.getDistanceConversionFactor(), 
							wsd.getTimeConversionFactor(), 
							wsd.getCarScale(), 
							wsd.getBallDropTime());
					
				} catch (IllegalArgumentException e) {
					MainActivity.this.displayError(
							String.format(MainActivity.this.getString(
									R.string.ramp_connection_error), e.getMessage()));
					e.printStackTrace();
				} catch (UnknownHostException e) {
					MainActivity.this.displayError(
							String.format(MainActivity.this.getString(
									R.string.ramp_connection_error), e.getMessage()));
				} catch (IOException e) {
					MainActivity.this.displayError(
							String.format(MainActivity.this.getString(
									R.string.ramp_connection_error), e.getMessage()));
					e.printStackTrace();
				}
			}
		});

		Button hardSendButton = (Button)findViewById(R.id.buttonHardSend);
		hardSendButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// read the data from the controls into the data object
				MainActivity.this.readHardWorksheetDataControls();
				
				// get an easy ref to the data
				WorksheetData wsd = MainActivity.this.worksheetData;
				SettingsData sd = MainActivity.this.settings;
				
				try {
					
					RampWriter.writeHardSettings(
							InetAddress.getByName(sd.getHost()), 
							sd.getPort(), 
							wsd.getDistanceBetweenLoops(), 
							wsd.getCarScale(), 
							wsd.getAccelerationOfGravity(), 
							wsd.getBallDropTime(), 
							wsd.getCarAcceleration());
					
				} catch (IllegalArgumentException e) {
					MainActivity.this.displayError(
							String.format(MainActivity.this.getString(
									R.string.ramp_connection_error), e.getMessage()));
					e.printStackTrace();
				} catch (UnknownHostException e) {
					MainActivity.this.displayError(
							String.format(MainActivity.this.getString(
									R.string.ramp_connection_error), e.getMessage()));
					e.printStackTrace();
				} catch (IOException e) {
					MainActivity.this.displayError(
							String.format(MainActivity.this.getString(
									R.string.ramp_connection_error), e.getMessage()));
					e.printStackTrace();
				}
			}
		});

	}
	
	/**
	 * Displays an error to the user.
	 * 
	 * @param errorText - the text to display.
	 */
	private void displayError(String errorText) {
		Toast.makeText(this, errorText, Toast.LENGTH_LONG).show();
	}
	
	/**
	 * Updates the controls in the activity tabs to reflect the settings data.
	 */
	private void updateSettingsControls() {
		
		// we want to leave the controls empty if the settings are at the 
		// default values
		if (this.settings.getHost() != null) {
			EditText host = (EditText)findViewById(R.id.editTextSettingsHost);
			host.setText(this.settings.getHost());
		}
	
		if (this.settings.getPort() != 0) {
			EditText port = (EditText)findViewById(R.id.editTextSettingsPort);
			port.setText(String.valueOf(this.settings.getPort()));
		}
		
		if (this.settings.getSpeedDisplayOnTime() != 0.0f) {
			EditText sdot = (EditText)findViewById(R.id.editTextSpeedDisplayOnTime);
			sdot.setText(String.valueOf(this.settings.getSpeedDisplayOnTime()));
		}
		
		if (this.settings.getSimulationPauseTime() != 0.0f) {
			EditText spt = (EditText)findViewById(R.id.editTextSimulationPauseTime);
			spt.setText(String.valueOf(this.settings.getSimulationPauseTime()));
		}
	
		if (this.settings.getSpeedLimitThreshold() != 0.0f) {
			EditText slt = (EditText)findViewById(R.id.editTextSpeedLimitThreshold);
			slt.setText(String.valueOf(this.settings.getSpeedLimitThreshold()));
		}
	}

	/**
	 * Reads the settings from the controls into the settings object.
	 */
	private void readSettingsControls() {
		
		EditText host = (EditText)findViewById(R.id.editTextSettingsHost);
		String hostText = host.getText() == null || host.getText().length() == 0 ?
				null : host.getText().toString();
		this.settings.setHost(hostText);
	
		EditText port = (EditText)findViewById(R.id.editTextSettingsPort);
		String portText = port.getText() == null || port.getText().length() == 0 ?
				"0" : port.getText().toString();
		this.settings.setPort(Integer.parseInt(portText));
		
		EditText sdot = (EditText)findViewById(R.id.editTextSpeedDisplayOnTime);
		String sdotText = sdot.getText() == null || sdot.getText().length() == 0 ?
				"0.0" : sdot.getText().toString();
		this.settings.setSpeedDisplayOnTime(Float.parseFloat(sdotText));
		
		EditText spt = (EditText)findViewById(R.id.editTextSimulationPauseTime);
		String sptText = spt.getText() == null || spt.getText().length() == 0 ?
				"0.0" : spt.getText().toString();
		this.settings.setSimulationPauseTime(Float.parseFloat(sptText));

		EditText slt = (EditText)findViewById(R.id.editTextSpeedLimitThreshold);
		String sltText = slt.getText() == null || slt.getText().length() == 0 ?
				"0.0" : slt.getText().toString();
		this.settings.setSpeedLimitThreshold(Integer.parseInt(sltText));
	}

	/**
	 * Load the settings from a private file.
	 */
	private void loadSettingsData() {
		
		// check to see if the settings exist
		if (Arrays.asList(fileList()).contains(SETTINGS_FILE)) {

			try {

				// yes it does, try to load it
				ObjectInputStream ois = new ObjectInputStream(
						openFileInput(SETTINGS_FILE));

				this.settings = (SettingsData)ois.readObject();
				
			} catch (StreamCorruptedException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		// if there were any problems or the file didn't exist
		// use an new empty object
		if (this.settings == null) {
			// create an empty one to hold the data
			this.settings = new SettingsData();
		}
	}

	/**
	 * Save the settings data to a private file.
	 */
	private void saveSettingsData() {
	
		try {
			
			ObjectOutputStream oos = new ObjectOutputStream(openFileOutput(SETTINGS_FILE, 0));
			oos.writeObject(this.settings);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	/**
	 * Reads the worksheet data from the controls into the data object.
	 */
	private void readEasyWorksheetDataControls() {
		
		// make sure the worksheet data member has been created
		if (this.worksheetData == null) {
			this.worksheetData = new WorksheetData();
		}
		
		EditText loopDistCtrl = (EditText)findViewById(R.id.editTextEasyDistanceBetweenLoops);
		String loopDistText = loopDistCtrl.getText() == null || loopDistCtrl.getText().length() == 0 ?
				"0" : loopDistCtrl.getText().toString();
		this.worksheetData.setDistanceBetweenLoops(Integer.parseInt(loopDistText));
	
		EditText distConvCtrl = (EditText)findViewById(R.id.editTextEasyDistanceConversionFactor);
		String distConvText = distConvCtrl.getText() == null || distConvCtrl.getText().length() == 0 ?
				"0" : distConvCtrl.getText().toString();
		this.worksheetData.setDistanceConversionFactor(Integer.parseInt(distConvText));
		
		EditText timeConvCtrl = (EditText)findViewById(R.id.editTextEasyTimeConversion);
		String timeConvText = timeConvCtrl.getText() == null || timeConvCtrl.getText().length() == 0 ?
				"0" : timeConvCtrl.getText().toString();
		this.worksheetData.setTimeConversionFactor(Integer.parseInt(timeConvText));
		
		EditText carScaleCtrl = (EditText)findViewById(R.id.editTextEasyCarScale);
		String carScaleText = carScaleCtrl.getText() == null || carScaleCtrl.getText().length() == 0 ?
				"0" : carScaleCtrl.getText().toString();
		this.worksheetData.setCarScale(Integer.parseInt(carScaleText));

		EditText ballDropCtrl = (EditText)findViewById(R.id.editTextEasyBalldropTime);
		String ballDropText = ballDropCtrl.getText() == null || ballDropCtrl.getText().length() == 0 ?
				"0" : ballDropCtrl.getText().toString();
		this.worksheetData.setBallDropTime(Integer.parseInt(ballDropText));
	}
	
	/**
	 * Reads the worksheet data from the controls into the data object.
	 */
	private void readHardWorksheetDataControls() {
		
		// make sure the worksheet data member has been created
		if (this.worksheetData == null) {
			this.worksheetData = new WorksheetData();
		}
		
		EditText loopDistCtrl = (EditText)findViewById(R.id.editTextHardDistanceBetweenLoops);
		String loopDistText = loopDistCtrl.getText() == null || loopDistCtrl.getText().length() == 0 ?
				"0" : loopDistCtrl.getText().toString();
		this.worksheetData.setDistanceBetweenLoops(Integer.parseInt(loopDistText));

		EditText carScaleCtrl = (EditText)findViewById(R.id.editTextHardCarScale);
		String carScaleText = carScaleCtrl.getText() == null || carScaleCtrl.getText().length() == 0 ?
				"0" : carScaleCtrl.getText().toString();
		this.worksheetData.setCarScale(Integer.parseInt(carScaleText));

		EditText accGravityCtrl = (EditText)findViewById(R.id.editTextHardAccelerationGravity);
		String accGravityText = accGravityCtrl.getText() == null || accGravityCtrl.getText().length() == 0 ?
				"0.0" : accGravityCtrl.getText().toString();
		this.worksheetData.setAccelerationOfGravity(Float.parseFloat(accGravityText));
		
		EditText ballDropCtrl = (EditText)findViewById(R.id.editTextHardBallDropTime);
		String ballDropText = ballDropCtrl.getText() == null || ballDropCtrl.getText().length() == 0 ?
				"0" : ballDropCtrl.getText().toString();
		this.worksheetData.setBallDropTime(Integer.parseInt(ballDropText));

		EditText carAccelCtrl = (EditText)findViewById(R.id.editTextHardCarAcceleration);
		String carAccelText = carAccelCtrl.getText() == null || carAccelCtrl.getText().length() == 0 ?
				"0.0" : carAccelCtrl.getText().toString();
		this.worksheetData.setCarAcceleration(Float.parseFloat(carAccelText));
	}	
	
	/**
	 * Set up the tab control with the correct pages.
	 */
	private void buildTabs() {
		// get a reference to the tab host
		TabHost tabHost = (TabHost)findViewById(R.id.tabhost);
		
		// set it up
		tabHost.setup();
		
		// create the easy tab
		TabSpec spec1 = tabHost.newTabSpec("Easy");
		spec1.setContent(R.id.easyTab);
		spec1.setIndicator(this.getTabIndicator(tabHost.getContext(), R.string.tab_caption_easy));

		// create the hard tab
		TabSpec spec2 = tabHost.newTabSpec("Hard");
		spec2.setContent(R.id.hardTab);
		spec2.setIndicator(this.getTabIndicator(tabHost.getContext(), R.string.tab_caption_hard));
		
		// create the settings tab
		TabSpec spec3 = tabHost.newTabSpec("Settings");
		spec3.setContent(R.id.settingsTab);
		spec3.setIndicator(this.getTabIndicator(tabHost.getContext(), R.string.tab_caption_settings));
		
		// add them to the tab host
		tabHost.addTab(spec1);
		tabHost.addTab(spec2);
		tabHost.addTab(spec3);
	}
	
	/**
	 * Inflates the tab indicator, sets the caption and returns the new view.
	 * @param context
	 * @param title
	 * @return
	 */
	private View getTabIndicator(Context context, int title) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_layout, null);
        TextView tv = (TextView) view.findViewById(R.id.textView);
        tv.setText(title);
        return view;
    }
}
