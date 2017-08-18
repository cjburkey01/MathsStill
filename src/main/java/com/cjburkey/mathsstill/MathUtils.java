package com.cjburkey.mathsstill;

public class MathUtils {
	
	public static double round(double amt, double round) {
		double rounded = Math.round(amt / round);
		return rounded * round;
	}
	
}