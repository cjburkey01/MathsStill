package com.cjburkey.mathsstill.math;

public abstract class Vector {

	private final int size;
	private double[] values;
	
	public Vector(int size) {
		this.size = size;
		values = new double[this.size];
		for (int i = 0; i < size; i ++) {
			set(i, 0.0d);
		}
	}
	
	public final double getMagnitude() {
		double radicant = 0.0d;
		for (int i = 0; i < size; i ++) {
			radicant += Math.pow(get(i), 2);
		}
		return Math.sqrt(radicant);
	}
	
	public final void normalize() {
		double magnitude = getMagnitude();
		for (int i = 0; i < size; i ++) {
			set(i, get(i) / magnitude);
		}
	}
	
	public final String toString() {
		StringBuilder out = new StringBuilder();
		out.append('(');
		for (int i = 0; i < size; i ++) {
			out.append(String.format("%02.2f", get(i)));
			if (i < size - 1) {
				out.append(',');
				out.append(' ');
			}
		}
		out.append(')');
		return out.toString();
	}
	
	public final void set(Vector other) {
		int max = Math.min(other.size, size);
		for (int i = 0; i < max; i ++) {
			set(i, other.get(i));
		}
	}
	
	protected final void set(int val, double value) {
		verify(val);
		values[val] = value;
	}
	
	protected final double get(int val) {
		verify(val);
		return values[val];
	}
	
	protected final void verify(int val) {
		if (val < 0 || val >= size) {
			throw new RuntimeException("Value '" + val + "' (+1) is out of vector size bound. Max size: " + size);
		}
	}
	
}