package com.siemens.stem;

/**
 * This is a model class that holds the worksheet data from the
 * easy and hard tabs.
 * @author Jon Masters
 *
 */
class WorksheetData {

	private int distanceBetweenLoops; 
	
	private int distanceConversionFactor;
	
	private int timeConversionFactor; 
	
	private int carScale;
	
	private int ballDropTime;
	
	private float accelerationOfGravity;
	
	private float carAcceleration;
	
	public int getDistanceBetweenLoops() {
		return distanceBetweenLoops;
	}
	
	public void setDistanceBetweenLoops(int distanceBetweenLoops) {
		this.distanceBetweenLoops = distanceBetweenLoops;
	}

	public int getDistanceConversionFactor() {
		return distanceConversionFactor;
	}

	public void setDistanceConversionFactor(int distanceConversionFactor) {
		this.distanceConversionFactor = distanceConversionFactor;
	}

	public int getTimeConversionFactor() {
		return timeConversionFactor;
	}

	public void setTimeConversionFactor(int timeConversionFactor) {
		this.timeConversionFactor = timeConversionFactor;
	}

	public int getCarScale() {
		return carScale;
	}

	public void setCarScale(int carScale) {
		this.carScale = carScale;
	}

	public int getBallDropTime() {
		return ballDropTime;
	}

	public void setBallDropTime(int ballDropTime) {
		this.ballDropTime = ballDropTime;
	}

	public float getAccelerationOfGravity() {
		return accelerationOfGravity;
	}

	public void setAccelerationOfGravity(float accelerationOfGravity) {
		this.accelerationOfGravity = accelerationOfGravity;
	}

	public float getCarAcceleration() {
		return carAcceleration;
	}

	public void setCarAcceleration(float carAcceleration) {
		this.carAcceleration = carAcceleration;
	}
}
