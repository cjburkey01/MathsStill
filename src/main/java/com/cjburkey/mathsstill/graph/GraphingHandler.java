package com.cjburkey.mathsstill.graph;

import java.util.Random;
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
		drawRay(new Vector2(-5, 5), new Vector2(5, 20));
		drawRay(new Vector2(4, 10), new Vector2(18, -12));
		drawRay(new Vector2(3, -3), new Vector2(0, 0));
		drawRay(new Vector2(0, 0), new Vector2(-3, -3));
		drawRay(new Vector2(0, 0), new Vector2(0, 2));
		drawRay(new Vector2(-2, 0), new Vector2(0, 0));
		drawDebugInfo();
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
		if (zoom < 10.0d) {
			if (zoom <= 5.5d) {
				gridSize = 4.0d;
			} else {
				gridSize = 2.0d;
			}
		} else {
			gridSize = 1.0d;
		}
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
	
	private void drawDebugInfo() {
		int fps = instance.getRenderLoop().getFps();
		double x = -RenderHandler.getCanvas().getWidth() / 2 + 10;
		double y = -RenderHandler.getCanvas().getHeight() / 2 + 10;
		int between = 20;
		Paint color = Color.BLACK;
		RenderHandler.drawText(new Vector2(x, y), color, "FPS: " + fps);
		RenderHandler.drawText(new Vector2(x, y + between), color, "Scale: " + String.format("%02.2f", zoom));
		RenderHandler.drawText(new Vector2(x, y + between * 2), color, "Grid Spacing: " + String.format("%02.2f", gridSize));
		RenderHandler.drawText(new Vector2(x, y + between * 3), color, "Offset: " + offset);
		RenderHandler.drawText(new Vector2(x, y + between * 4), color, "Cursor: " + instance.getCursorHandler().getCurrentCursor());
		RenderHandler.drawLine(new Vector2(x, y + between * 5.5), new Vector2(x + 100, y + between * 5.5), color);
		RenderHandler.drawText(new Vector2(x, y + between * 6), color, "Draw Grid: " + displayGrid);
		RenderHandler.drawText(new Vector2(x, y + between * 7), color, "Draw Origin: " + displayOrigin);
		RenderHandler.drawText(new Vector2(x, y + between * 8), color, "Time: " + System.currentTimeMillis() + " ms");
		if (fps < 6) {
			instance.getCursorHandler().setCursor(Cursor.WAIT);
		}
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
	
	private void drawRay(Vector2 start, Vector2 end) {
		Vector2 s = new Vector2(start);
		Vector2 e = new Vector2(end);
		s.setY(-s.getY());
		e.setY(-e.getY());
		RenderHandler.drawLine(transform(s), transform(e), Color.RED);
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