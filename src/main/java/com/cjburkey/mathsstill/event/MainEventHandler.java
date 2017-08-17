package com.cjburkey.mathsstill.event;

import com.cjburkey.mathsstill.MathsStill;
import com.cjburkey.mathsstill.math.Vector2;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class MainEventHandler {
	
	public static MainEventHandler self;
	
	public Vector2 mousePos = new Vector2();
	
	private MathsStill instance;
	
	public MainEventHandler(MathsStill instance) {
		self = this;
		this.instance = instance;
	}
	
	public void onMouseMove(MouseEvent e) {
		setPos(e);
	}
	
	public void onMouseDrag(MouseEvent e) {
		instance.getGraphingHandler().onMouseDrag(e);
		setPos(e);
	}
	
	public void onMousePress(MouseEvent e) {
		setPos(e);
	}
	
	public void onMouseScroll(ScrollEvent e) {
		instance.getGraphingHandler().onMouseScroll(e);
	}
	
	private void setPos(MouseEvent e) {
		mousePos.setX(e.getX());
		mousePos.setY(e.getY());
	}
	
}