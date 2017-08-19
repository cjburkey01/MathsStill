package com.cjburkey.mathsstill.render;

import com.cjburkey.mathsstill.math.Vector2;

public class Transform {
	
	private final Vector2 offset;
	private double scale;
	
	public Transform() {
		this(Vector2.EMPTY(), 1.0d);
	}
	
	public Transform(Vector2 offset) {
		this(offset, 1.0d);
	}
	
	public Transform(double scale) {
		this(Vector2.EMPTY(), scale);
	}
	
	public Transform(Vector2 offset, double scale) {
		this.offset = new Vector2(offset);
		this.scale = scale;
	}
	
	public void setScale(double scale) {
		this.scale = scale;
	}
	
	public void setOffset(Vector2 vec) {
		this.offset.set(vec);
	}
	
	public void addOffset(Vector2 add) {
		offset.add(add);
	}
	
	public void addScale(double add) {
		scale += add;
	}
	
	public Vector2 getOffset() {
		return new Vector2(offset);
	}
	
	public double getScale() {
		return scale;
	}
	
	public Vector2 transform(Vector2 in) {
		Vector2 out = new Vector2(in);
		out.add(offset);
		out.mul(scale);
		return out;
	}
	
	public Vector2 untransform(Vector2 in) {
		Vector2 out = new Vector2(in);
		out.div(scale);
		out.sub(offset);
		return out;
	}
	
}