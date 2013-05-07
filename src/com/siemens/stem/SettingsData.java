package com.siemens.stem;

import java.io.Serializable;

/**
 * The serialisable settings for the ramp configurator.
 * @author Jon Masters
 *
 */
public final class SettingsData implements Serializable {

	/**
	 * Version information for the class.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The host name or IP address of ramp.
	 */
	private String host;
	
	/**
	 * The port for the ramp.
	 */
	private int port;
	
	/**
	 * The time to display the speed after each run.
	 */
	private float speedDisplayOnTime;
	
	/**
	 * The pause time between simulations.
	 */
	private float simulationPauseTime;
	
	/**
	 * The sped limit for the ramp.
	 */
	private int speedLimitThreshold;

	/**
	 * Gets the host name / ip
	 * @return the host / ip
	 */
	public String getHost() {
		return host;
	}

	/**
	 * sets the host name / ip
	 * @param host
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * Get the ramp port.
	 * @return the tcp port for the ramp
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Sets the ramp tcp port.
	 * @param port
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Gets the speed display on time.
	 * @return the time the speed is displayed for.
	 */
	public float getSpeedDisplayOnTime() {
		return speedDisplayOnTime;
	}

	/**
	 * Sets the speed display on time.
	 * @param speedDisplayOnTime
	 */
	public void setSpeedDisplayOnTime(float speedDisplayOnTime) {
		this.speedDisplayOnTime = speedDisplayOnTime;
	}

	/**
	 * Gets the simulation pause time.
	 * @return the simulation pause time.
	 */
	public float getSimulationPauseTime() {
		return simulationPauseTime;
	}

	/**
	 * sets the simulation pause time.
	 * @param simulationPauseTime
	 */
	public void setSimulationPauseTime(float simulationPauseTime) {
		this.simulationPauseTime = simulationPauseTime;
	}

	/**
	 * Gets the speed limit for the ramp.
	 * @return the speed limit.
	 */
	public int getSpeedLimitThreshold() {
		return speedLimitThreshold;
	}

	/**
	 * sets the ramp speed limit.
	 * @param speedLimitThreshold
	 */
	public void setSpeedLimitThreshold(int speedLimitThreshold) {
		this.speedLimitThreshold = speedLimitThreshold;
	}
}
