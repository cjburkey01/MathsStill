package com.cjburkey.mathsstill.window;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GraphingCanvas extends Canvas {
	
	public GraphingCanvas() {
		update();
	}
	
	public void update() {
		getGraphics().clearRect(0, 0, getWidth(), getHeight());
		getGraphics().setFill(Color.WHITE);
		getGraphics().fillRect(0, 0, getWidth(), getHeight());
	}
	
	public boolean isResizable() {
		return true;
	}
	
	public double prefWidth(double height) {
		return getWidth();
	}
	
	public double prefHeight(double width) {
		return getHeight();
	}
	
	public GraphicsContext getGraphics() {
		return getGraphicsContext2D();
	}
}