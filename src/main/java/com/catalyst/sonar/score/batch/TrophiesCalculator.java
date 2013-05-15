package com.catalyst.sonar.score.batch;

/**
 * 
 * @author lsajeev
 * 
 */
public class TrophiesCalculator {
	//TODO work in progress
	public static final double VALUE = 90.0;

	/**
	 * checks if the metrics value is above 90% and adds trophy points
	 * @param points
	 * @return
	 */
	public static double calculateConsistentTrophyPoints(double points) {
		double consistentPoints = points;
		double trophyPoints = 0;
		int days = 0;
		if ((consistentPoints > VALUE)) {
			trophyPoints++;
		}
		else{
			trophyPoints = 0;
		}
		return trophyPoints;
	}
}
