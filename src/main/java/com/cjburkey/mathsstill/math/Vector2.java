package com.cjburkey.mathsstill.math;

public final class Vector2 extends Vector {

	public Vector2() {
		super(2);
	}
	
	public Vector2(double x, double y) {
		this();
		setX(x);
		setY(y);
	}
	
	public Vector2(Vector2 copy) {
		this();
		setX(copy.getX());
		setY(copy.getY());
	}
	
	public void setX(double val) {
		set(0, val);
	}
	
	public void setY(double val) {
		set(1, val);
	}
	
	public double getX() {
		return get(0);
	}
	
	public double getY() {
		return get(1);
	}
	
	public void add(Vector2 in) {
		setX(getX() + in.getX());
		setY(getY() + in.getY());
	}
	
	public void sub(Vector2 in) {
		setX(getX() - in.getX());
		setY(getY() - in.getY());
	}
	
	public void mul(Vector2 in) {
		setX(getX() * in.getX());
		setY(getY() * in.getY());
	}
	
	public void mul(double in) {
		setX(getX() * in);
		setY(getY() * in);
	}
	
	public void div(Vector2 in) {
		setX(getX() / in.getX());
		setY(getY() / in.getY());
	}
	
	public void div(double in) {
		setX(getX() / in);
		setY(getY() / in);
	}
	
}