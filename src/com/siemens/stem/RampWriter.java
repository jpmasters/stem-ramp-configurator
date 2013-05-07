package com.siemens.stem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * This is a static class that is used to write the configuration data to the ramp.
 * @author Jon Masters
 *
 */
class RampWriter {

	static String HEADWAY = "HEADWAY=%d\n";
	static String DISTCONV = "DISTCONV=%d\n";
	static String TIMECONV = "TIMECONV=%d\n";
	static String CARSCALE = "CARSCALE=%d\n";
	static String GRAVITY = "GRAVITY=%s\n";
	static String DROPTIME = "DROPTIME=%d\n";
	static String CARACCEL = "CARACCEL=%s\n";
	static String SPEEDDISPLAY = "SPEEDDISPLAY=%s\n";
	static String SPEEDLIMITTHRESH = "SPEEDLIMITTHRESH=%d\n";
	static String SIMPAUSE = "SIMPAUSE=%s\n";
	
	protected RampWriter(){}
	
	/**
	 * Writes the settings from the easy tab to the ramp
	 * @param host - the ramp ip address or host name
	 * @param port - the port the ramp is listening on
	 * @param distanceBetweenLoops - the distance between the loops used for speed calculation
	 * @param distanceConversionFactor - the distance conversion factor
	 * @param timeConversionFactor - the time conversion factor
	 * @param carScale - the scale of the model car
	 * @param ballDropTime - the time it takes for the ball to drop
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	static void writeEasySettings(InetAddress host, int port, int distanceBetweenLoops, 
			int distanceConversionFactor, int timeConversionFactor, int carScale, int ballDropTime) 
			throws IOException, IllegalArgumentException {
		
		Socket client = null;
		PrintWriter out = null;
		BufferedReader in = null;
		
		try {
			
			// open a connection to the ramp
			client = new Socket(host, port);
	        out = new PrintWriter(client.getOutputStream());
	        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
	        
	        // start by writing a line feed which should result in the test 'ramp>' back
	        // from the ramp
	        out.write("\n");	
	        out.flush();
	        readFromRamp(in);
	        
	        // write the settings out to the ramp
	        out.write(String.format(HEADWAY, distanceBetweenLoops));
	        out.flush();
	        readFromRamp(in);
	        
	        out.write(String.format(DISTCONV, distanceConversionFactor));
	        out.flush();
	        readFromRamp(in);
	        
	        out.write(String.format(TIMECONV, timeConversionFactor));
	        out.flush();
	        readFromRamp(in);
	        
	        out.write(String.format(CARSCALE, carScale));
	        out.flush();
	        readFromRamp(in);
	        
	        out.write(String.format(DROPTIME, ballDropTime));
	        out.flush();
	        readFromRamp(in);        
		}
		finally {
			// make sure we close anything that needs to be closed
			if (in != null) {
				in.close();
			}
			
			if (out != null) {
				out.close();
			}
			
			if (client != null && client.isConnected()) {
				client.close();
			}
		}
	}

	/**
	 * Writes the settings for the hard tab to the ramp.
	 * @param host - the ramp IP or host name
	 * @param port - the port the ramp is listening on
	 * @param distanceBetweenLoops - the distance between the loops
	 * @param carScale - the scale of the vehicle
	 * @param accelerationOfGravity - the gravitational constant
	 * @param ballDropTime - the time taken for the ball to drop
	 * @param carAcceleration - the acceleration of the car
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	static void writeHardSettings(InetAddress host, int port, int distanceBetweenLoops, 
			int carScale, float accelerationOfGravity, int ballDropTime, float carAcceleration) 
			throws IOException, IllegalArgumentException {
		
		Socket client = null;
		PrintWriter out = null;
		BufferedReader in = null;
		
		try {
			
			// open a connection to the ramp
			client = new Socket(host, port);
	        out = new PrintWriter(client.getOutputStream());
	        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
	        
	        // start by writing a line feed which should result in the test 'ramp>' back
	        // from the ramp
	        out.write("\n");	
	        out.flush();
	        readFromRamp(in);
	        
	        // write the settings out to the ramp
	        out.write(String.format(HEADWAY, distanceBetweenLoops));
	        out.flush();
	        readFromRamp(in);
	        
	        out.write(String.format(CARSCALE, carScale));
	        out.flush();
	        readFromRamp(in);
	        
	        out.write(String.format(GRAVITY, accelerationOfGravity));
	        out.flush();
	        readFromRamp(in);
	        	        
	        out.write(String.format(DROPTIME, ballDropTime));
	        out.flush();
	        readFromRamp(in);        
	        
	        out.write(String.format(CARACCEL, carAcceleration));
	        out.flush();
	        readFromRamp(in);

		}
		finally {
			// make sure we close anything that needs to be closed
			if (in != null) {
				in.close();
			}
			
			if (out != null) {
				out.close();
			}
			
			if (client != null && client.isConnected()) {
				client.close();
			}
		}
	}
	
	/**
	 * Writes the settings for the ramp to the ramp.
	 * @param host - the ramp IP or host name
	 * @param port - the port the ramp is listening on
	 * @param speedDisplayTime - the length of time the speed should be displayed
	 * @param simulationPauseTime - the gap between simulated runs in sim mode.
	 * @param speedLimitThreshold - the speed limit for the ramp
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	static void writeRampSettings(InetAddress host, int port, float speedDisplayTime, 
			float simulationPauseTime, int speedLimitThreshold) 
			throws IOException, IllegalArgumentException {
		
		Socket client = null;
		PrintWriter out = null;
		BufferedReader in = null;
		
		try {
			
			// open a connection to the ramp
			client = new Socket(host, port);
	        out = new PrintWriter(client.getOutputStream());
	        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
	        
	        // start by writing a line feed which should result in the test 'ramp>' back
	        // from the ramp
	        out.write("\n");	
	        out.flush();
	        readFromRamp(in);
	        
	        // write the settings out to the ramp
	        out.write(String.format(SPEEDDISPLAY, speedDisplayTime));
	        out.flush();
	        readFromRamp(in);
	        
	        out.write(String.format(SPEEDLIMITTHRESH, speedLimitThreshold));
	        out.flush();
	        readFromRamp(in);
	        
	        out.write(String.format(SIMPAUSE, simulationPauseTime));
	        out.flush();
	        readFromRamp(in);	        	        
		}
		finally {
			// make sure we close anything that needs to be closed
			if (in != null) {
				in.close();
			}
			
			if (out != null) {
				out.close();
			}
			
			if (client != null && client.isConnected()) {
				client.close();
			}
		}
	}	
	
	/**
	 * Reads the response to a command from the ramp.
	 * @param in - the buffered read around the socket connection inpu stream
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	private static void readFromRamp(BufferedReader in) throws IOException,
			IllegalArgumentException {
		
		// keep reading from the socket until we get the prompt
		StringBuffer sb = new StringBuffer();
		int nextChar = 0;
		while(nextChar != -1) {
			nextChar = in.read();
			char c = (char)nextChar;
			sb.append(c);
			if (sb.toString().endsWith("ramp>")) {
				break;
			}
		}
		
		// if we didn't end with ramp> then we're not talking to a ramp
		if (!sb.toString().endsWith("ramp>")) {
			throw new IllegalArgumentException(
				"The ramp did not respond as expected. Check that the host and port settings are correct.");
		}
	}
}
