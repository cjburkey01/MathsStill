package com.cjburkey.mathsstill.graph;

import com.cjburkey.mathsstill.MathUtils;
import com.cjburkey.mathsstill.MathsStill;
import com.cjburkey.mathsstill.event.MainEventHandler;
import com.cjburkey.mathsstill.math.Vector2;
import com.cjburkey.mathsstill.render.RenderHandler;
import com.cjburkey.mathsstill.render.Transform;
import com.cjburkey.mathsstill.render.element.gui.GuiDebug;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;

public class GraphingHandler {
	
	private final MathsStill instance;
	
	private final GuiDebug debugInfoDisplay = new GuiDebug(new Vector2(10, 10));
	
	private double gridSize = 1.0d;
	private final Transform transform = new Transform(10.0d);
	
	public boolean displayGrid = true;
	public boolean displayOrigin = true;
	
	public GraphingHandler(MathsStill parent) {
		instance = parent;
		MathsStill.graphingGuiRender.addElement(debugInfoDisplay);
	}
	
	public void render() {
		renderGrid();
		MathsStill.graphingRender.render(transform);
		MathsStill.graphingRender.topLeftGraphics();
		MathsStill.graphingGuiRender.render(transform);
		MathsStill.graphingRender.centerGraphics();
		renderInfo();
	}
	
	public void onMouseDrag(MouseEvent e) {
		if (e != null && e.getButton() != null && (e.getButton().equals(MouseButton.MIDDLE) || e.getButton().equals(MouseButton.PRIMARY))) {
			Vector2 mouse = MainEventHandler.self.getMousePos();
			Vector2 diff = new Vector2(e.getX(), e.getY());
			diff.sub(mouse);
			diff.div(transform.getScale());
			transform.addOffset(diff);
			instance.getCursorHandler().setCursor(Cursor.CLOSED_HAND);
		}
	}
	
	public void onMouseScroll(ScrollEvent e) {
		transform.addScale(e.getDeltaY() * transform.getScale() * 0.001d);
		if (transform.getScale() < 10.0d) {
			if (transform.getScale() <= 5.5d) {
				if (transform.getScale() <= 2.5d) {
					if (transform.getScale() <= 1.25d) {
						gridSize = 16.0d;
					} else {
						gridSize = 8.0d;
					}
				} else {
					gridSize = 4.0d;
				}
			} else {
				gridSize = 2.0d;
			}
		} else {
			gridSize = 1.0d;
		}
		if (transform.getScale() < 1.0d) {
			transform.setScale(1.0d);
		}
		if (transform.getScale() > 100.0d) {
			transform.setScale(100.0d);
		}
		if (e.getDeltaY() < 0) {
			instance.getCursorHandler().setCursor(Cursor.S_RESIZE);
		} else {
			instance.getCursorHandler().setCursor(Cursor.N_RESIZE);
		}
	}
	
	private void renderGrid() {
		if (displayGrid) {
			RenderHandler rh = MathsStill.graphingRender;
			Color mainColor = Color.rgb(200, 200, 200);
			Color boldColor = Color.BLACK;

			double lineStartY = (-rh.getSize().getY() / 2) / transform.getScale();
			double lineEndY = (rh.getSize().getY()/ 2) / transform.getScale();
			double lineStartX = (-rh.getSize().getX() / 2) / transform.getScale();
			double lineEndX = (rh.getSize().getX() / 2) / transform.getScale();
			
			for (double x = -100.0d; x < rh.getHalfSize().getX() / transform.getScale(); x += gridSize) {
				double xx = MathUtils.round(x - transform.getOffset().getX(), gridSize);
				double xxx = -MathUtils.round(x + transform.getOffset().getX(), gridSize);
				double lsY = lineStartY - transform.getOffset().getY();
				double leY = lineEndY - transform.getOffset().getY();
				segment(new Vector2(xx, lsY), new Vector2(xx, leY), mainColor);
				if (x != 0) {
					segment(new Vector2(xxx, lsY), new Vector2(xxx, leY), mainColor);
				}
			}
			for (double y = -100.0d; y < rh.getHalfSize().getY() / transform.getScale(); y += gridSize) {
				double yy = MathUtils.round(y - transform.getOffset().getY(), gridSize);
				double yyy = -MathUtils.round(y + transform.getOffset().getY(), gridSize);
				double lsX = lineStartX - transform.getOffset().getX();
				double leX = lineEndX - transform.getOffset().getX();
				segment(new Vector2(lsX, yy), new Vector2(leX, yy), mainColor);
				if (y != 0) {
					segment(new Vector2(lsX, yyy), new Vector2(leX, yyy), mainColor);
				}
			}
			if (displayOrigin) {
				MathsStill.graphingRender.drawLine(graphCoordsToScreen(new Vector2(0.0d, -100)), graphCoordsToScreen(new Vector2(0.0d, 100)), boldColor);
				MathsStill.graphingRender.drawLine(graphCoordsToScreen(new Vector2(-100, 0.0d)), graphCoordsToScreen(new Vector2(100, 0.0d)), boldColor);
			}
		}
	}
	
	private void renderInfo() {
		Vector2 mouseScreenCoords = MainEventHandler.self.getMousePos();
		mouseScreenCoords.sub(MathsStill.graphingRender.getHalfSize());
		Vector2 mouseGraphCoords = screenCoordsToGraph(mouseScreenCoords);
		mouseScreenCoords.add(MathsStill.graphingRender.getHalfSize());
		int fps = instance.getRenderLoop().getFps();
		if (fps <= 5) {
			instance.getCursorHandler().setCursor(Cursor.WAIT);
		}
		
		debugInfoDisplay.update(fps, gridSize, instance.getCursorHandler().getCurrentCursor(), displayGrid, displayOrigin, mouseScreenCoords, mouseGraphCoords);
	}
	
	public Vector2 screenCoordsToGraph(Vector2 in) {
		Vector2 out = transform.untransform(in);
		out.setY(-out.getY());
		return out;
	}
	
	public Vector2 graphCoordsToScreen(Vector2 in) {
		return snap(transform.transform(in));
	}
	
	/**
	 * Draw segment from start to end
	 * @param start Screen coord
	 * @param end Screen coord
	 * @param color Color to draw
	 */
	private void segment(Vector2 start, Vector2 end, Color color) {
		MathsStill.graphingRender.drawLine(graphCoordsToScreen(start), graphCoordsToScreen(end), color);
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