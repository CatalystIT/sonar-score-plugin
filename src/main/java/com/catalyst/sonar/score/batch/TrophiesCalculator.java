package com.catalyst.sonar.score.batch;

/**
 * 
 * @author lsajeev
 * 
 */
public class TrophiesCalculator {
	public static final int LOWEST_TROPHY_POINTS = 0;
	public static final double BASE_TROPHY_POINTS = 500.0;
	public static final double VALUE = 90.0;

	public static double calculateTrophyPoints(double earnedPoints) {
		double trophyPoints = 0;
		double points = earnedPoints;
		trophyPoints = Math.round(points / BASE_TROPHY_POINTS);

		if (trophyPoints <= LOWEST_TROPHY_POINTS) {
			trophyPoints = LOWEST_TROPHY_POINTS;
		}
		return trophyPoints;
	}

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
