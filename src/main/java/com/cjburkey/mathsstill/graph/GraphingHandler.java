package com.cjburkey.mathsstill.graph;

import com.cjburkey.mathsstill.MathsStill;
import com.cjburkey.mathsstill.event.MainEventHandler;
import com.cjburkey.mathsstill.math.Vector2;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class GraphingHandler {
	
	private final MathsStill instance;
	
	private double gridSize = 1.0d;
	private Vector2 offset = new Vector2(0, 0);
	private double zoom = 10.0d;

	public boolean displayGrid = true;
	public boolean displayOrigin = true;
	
	public GraphingHandler(MathsStill parent) {
		instance = parent;
	}
	
	public void render() {
		GraphicsContext gc = instance.getGraph();
		drawGrid(gc);
		drawText();
	}
	
	public void onMouseDrag(MouseEvent e) {
		if (e != null && e.getButton() != null && (e.getButton().equals(MouseButton.MIDDLE) || e.getButton().equals(MouseButton.PRIMARY))) {
			Vector2 mouse = MainEventHandler.self.mousePos;
			Vector2 diff = new Vector2(e.getX(), e.getY());
			diff.sub(mouse);
			diff.div(zoom);
			offset.add(diff);
			instance.getCursorHandler().setCursor(Cursor.CLOSED_HAND);
		}
	}
	
	public void onMouseScroll(ScrollEvent e) {
		zoom += e.getDeltaY() * zoom * 0.001d;
		if (zoom < 5.0d) {
			zoom = 5.0d;
		}
		if (zoom > 100.0d) {
			zoom = 100.0d;
		}
		if (e.getDeltaY() < 0) {
			instance.getCursorHandler().setCursor(Cursor.S_RESIZE);
		} else {
			instance.getCursorHandler().setCursor(Cursor.N_RESIZE);
		}
	}
	
	private void drawText() {
		int fps = instance.getRenderLoop().getFps();
		double x = -RenderHandler.getCanvas().getWidth() / 2 + 10;
		double y = -RenderHandler.getCanvas().getHeight() / 2 + 10;
		int between = 20;
		Paint color = Color.BROWN;
		RenderHandler.drawText(new Vector2(x, y), color, "FPS: " + fps);
		RenderHandler.drawText(new Vector2(x, y + between), color, "Scale: " + String.format("%02.2f", zoom));
		RenderHandler.drawText(new Vector2(x, y + between * 2), color, "Grid Spacing: " + String.format("%02.2f", gridSize));
		RenderHandler.drawText(new Vector2(x, y + between * 3), color, "Offset: " + offset);
		RenderHandler.drawText(new Vector2(x, y + between * 4), color, "Cursor: " + instance.getCursorHandler().getCurrentCursor());
		RenderHandler.drawLine(new Vector2(x, y + between * 5 + between / 2), new Vector2(x + 100, y + between * 5 + between / 2), color);
		RenderHandler.drawText(new Vector2(x, y + between * 6), color, "Draw Grid: " + displayGrid);
		RenderHandler.drawText(new Vector2(x, y + between * 7), color, "Draw Origin: " + displayOrigin);
	}
	
	private void drawGrid(GraphicsContext gc) {
		if (displayGrid) {
			Canvas c = gc.getCanvas();
			double lineStartY = snap(-c.getHeight() / 2);
			double lineEndY = snap(c.getHeight() / 2);
			double lineStartX = snap(-c.getWidth() / 2);
			double lineEndX = snap(c.getWidth() / 2);
			Paint faint = Color.rgb(200, 200, 200);
			for (double x = gridSize; x < c.getWidth() / 2; x += gridSize) {
				RenderHandler.drawLine(transform(new Vector2(x, lineStartY)), transform(new Vector2(x, lineEndY)), faint);
				RenderHandler.drawLine(transform(new Vector2(-x, lineStartY)), transform(new Vector2(-x, lineEndY)), faint);
			}
			for (double y = gridSize; y < c.getHeight() / 2; y += gridSize) {
				RenderHandler.drawLine(transform(new Vector2(lineStartX, y)), transform(new Vector2(lineEndX, y)), faint);
				RenderHandler.drawLine(transform(new Vector2(lineStartX, -y)), transform(new Vector2(lineEndX, -y)), faint);
			}
			Paint originColor = faint;
			if (displayOrigin) {
				originColor = Color.BLACK;
			}
			RenderHandler.drawLine(transform(new Vector2(0.0d, -c.getHeight() / 2)), transform(new Vector2(0.0d, c.getHeight() / 2)), originColor);
			RenderHandler.drawLine(transform(new Vector2(-c.getWidth() / 2, 0.0d)), transform(new Vector2(c.getWidth() / 2, 0.0d)), originColor);
		}
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