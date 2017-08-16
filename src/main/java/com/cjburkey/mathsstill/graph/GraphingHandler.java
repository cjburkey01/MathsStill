package com.cjburkey.mathsstill.graph;

import com.cjburkey.mathsstill.MathsStill;
import com.cjburkey.mathsstill.math.Vector2;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GraphingHandler {
	
	private final MathsStill instance;
	
	private double gridSize = 1.0d;
	private Vector2 offset = new Vector2(0, 0);
	private double zoom = 15.0d;
	
	public GraphingHandler(MathsStill parent) {
		instance = parent;
	}
	
	public void render() {
		GraphicsContext gc = instance.getGraph();
		drawGrid(gc);
	}
	
	private void drawGrid(GraphicsContext gc) {
		Canvas c = gc.getCanvas();
		double lineStartY = snap(-c.getHeight() / 2);
		double lineEndY = snap(c.getHeight() / 2);
		double lineStartX = snap(-c.getWidth() / 2);
		double lineEndX = snap(c.getWidth() / 2);
		for (double x = gridSize; x < c.getWidth() / 2; x += gridSize) {
			RenderHandler.drawLine(transform(new Vector2(x, lineStartY)), transform(new Vector2(x, lineEndY)), Color.rgb(200, 200, 200));
			RenderHandler.drawLine(transform(new Vector2(-x, lineStartY)), transform(new Vector2(-x, lineEndY)), Color.rgb(200, 200, 200));
		}
		for (double y = gridSize; y < c.getHeight() / 2; y += gridSize) {
			RenderHandler.drawLine(transform(new Vector2(lineStartX, y)), transform(new Vector2(lineEndX, y)), Color.rgb(200, 200, 200));
			RenderHandler.drawLine(transform(new Vector2(lineStartX, -y)), transform(new Vector2(lineEndX, -y)), Color.rgb(200, 200, 200));
		}
		RenderHandler.drawLine(transform(new Vector2(0.0d, -c.getHeight() / 2)), transform(new Vector2(0.0d, c.getHeight() / 2)), Color.BLACK);
		RenderHandler.drawLine(transform(new Vector2(-c.getHeight() / 2, 0.0d)), transform(new Vector2(c.getHeight() / 2, 0.0d)), Color.BLACK);
	}
	
	private Vector2 transform(Vector2 in) {
		return snap(transformCoords(in));
	}
	
	private Vector2 transformCoords(Vector2 in) {
		Vector2 out = new Vector2(in);
		out.add(offset);
		out.mul(zoom);
		return out;
	}
	
	private Vector2 snap(Vector2 in) {
		Vector2 out = new Vector2(in);
		out.setX(snap(out.getX()));
		out.setY(snap(out.getY()));
		return out;
	}
	
	private double snap(double p) {
		boolean take = p < 0;
		return ((int) p) + ((take) ? -0.5d : 0.5d);
	}
	
}