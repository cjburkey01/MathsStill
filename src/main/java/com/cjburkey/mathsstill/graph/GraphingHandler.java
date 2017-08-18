package com.cjburkey.mathsstill.graph;

import com.cjburkey.mathsstill.MathUtils;
import com.cjburkey.mathsstill.MathsStill;
import com.cjburkey.mathsstill.event.MainEventHandler;
import com.cjburkey.mathsstill.math.Vector2;
import com.cjburkey.mathsstill.render.RenderHandler;
import javafx.scene.Cursor;
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
		GraphicsContext gc = MathsStill.graphingRender.getGraphics();
		drawGrid(gc);
		drawDebugInfo();
	}
	
	public void onMouseDrag(MouseEvent e) {
		if (e != null && e.getButton() != null && (e.getButton().equals(MouseButton.MIDDLE) || e.getButton().equals(MouseButton.PRIMARY))) {
			Vector2 mouse = MainEventHandler.self.getMousePos();
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
		Vector2 mouseScreenCoords = MainEventHandler.self.getMousePos();
		mouseScreenCoords.sub(MathsStill.graphingRender.getHalfSize());
		Vector2 mouseGraphCoords = screenCoordsToGraph(mouseScreenCoords);
		//drawSegment(new Vector2(0.0d, 0.0d), mouseGraphCoords);
		mouseScreenCoords.add(new Vector2(10.0d, 10.0d));
		
		int fps = instance.getRenderLoop().getFps();
		double x = -MathsStill.graphingRender.getCanvas().getWidth() / 2 + 10;
		double y = -MathsStill.graphingRender.getCanvas().getHeight() / 2 + 10;
		int between = 20;
		Paint color = Color.BLACK;
		MathsStill.graphingRender.drawText(new Vector2(x, y), color, "FPS: " + fps);
		MathsStill.graphingRender.drawText(new Vector2(x, y + between), color, "Scale: " + String.format("%02.2f", zoom));
		MathsStill.graphingRender.drawText(new Vector2(x, y + between * 2), color, "Grid Spacing: " + String.format("%02.2f", gridSize));
		MathsStill.graphingRender.drawText(new Vector2(x, y + between * 3), color, "Cursor: " + instance.getCursorHandler().getCurrentCursor());
		MathsStill.graphingRender.drawLine(new Vector2(x, y + between * 4.5), new Vector2(x + 100, y + between * 4.5), color);
		MathsStill.graphingRender.drawText(new Vector2(x, y + between * 5), color, "Grid: " + displayGrid);
		MathsStill.graphingRender.drawText(new Vector2(x + 20, y + between * 6), color, "Origin: " + displayOrigin);
		MathsStill.graphingRender.drawText(mouseScreenCoords, color, mouseGraphCoords);
		if (fps < 6) {
			instance.getCursorHandler().setCursor(Cursor.WAIT);
		}
	}
	
	private void drawGrid(GraphicsContext gc) {
		if (displayGrid) {
			RenderHandler rh = MathsStill.graphingRender;
			Color mainColor = Color.rgb(200, 200, 200);
			Color boldColor = Color.BLACK;

			double lineStartY = (-rh.getSize().getY() / 2) / zoom;
			double lineEndY = (rh.getSize().getY()/ 2) / zoom;
			double lineStartX = (-rh.getSize().getX() / 2) / zoom;
			double lineEndX = (rh.getSize().getX() / 2) / zoom;
			
			for (double x = 0.0d; x < rh.getHalfSize().getX() / zoom; x += gridSize) {
				double xx = MathUtils.round(x - offset.getX(), gridSize);
				double xxx = MathUtils.round(x + offset.getX(), gridSize);
				double lsY = MathUtils.round(lineStartY - offset.getY(), gridSize);
				double leY = MathUtils.round(lineEndY - offset.getY(), gridSize);
				rh.drawLine(renderCoords(new Vector2(xx, lsY)), renderCoords(new Vector2(xx, leY)), mainColor);
				if (x != 0) {
					rh.drawLine(renderCoords(new Vector2(-xxx, lsY)), renderCoords(new Vector2(-xxx, leY)), mainColor);
				}
			}
			for (double y = 0.0d; y < rh.getHalfSize().getY() / zoom; y += gridSize) {
				double yy = MathUtils.round(y - offset.getY(), gridSize);
				double yyy = MathUtils.round(y + offset.getY(), gridSize);
				double lsX = MathUtils.round(lineStartX - offset.getX(), gridSize);
				double leX = MathUtils.round(lineEndX - offset.getX(), gridSize);
				rh.drawLine(renderCoords(new Vector2(lsX, yy)), renderCoords(new Vector2(leX, yy)), mainColor);
				if (y != 0) {
					rh.drawLine(renderCoords(new Vector2(lsX, -yyy)), renderCoords(new Vector2(leX, -yyy)), mainColor);
				}
			}
			if (displayOrigin) {
				MathsStill.graphingRender.drawLine(renderCoords(new Vector2(0.0d, -100)), renderCoords(new Vector2(0.0d, 100)), boldColor);
				MathsStill.graphingRender.drawLine(renderCoords(new Vector2(-100, 0.0d)), renderCoords(new Vector2(100, 0.0d)), boldColor);
			}
		}
	}
	
	public Vector2 screenCoordsToGraph(Vector2 in) {
		Vector2 out = new Vector2(in);
		out.div(zoom);
		out.sub(offset);
		out.setY(-out.getY());
		return out;
	}
	
	public void drawSegment(Vector2 start, Vector2 end) {
		Vector2 s = new Vector2(start);
		Vector2 e = new Vector2(end);
		s.setY(-s.getY());
		e.setY(-e.getY());
		MathsStill.graphingRender.drawLine(renderCoords(s), renderCoords(e), Color.RED);
	}
	
	private Vector2 renderCoords(Vector2 in) {
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