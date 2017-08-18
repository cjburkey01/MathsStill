package com.cjburkey.mathsstill.render;

import com.cjburkey.mathsstill.math.Vector2;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;

public class RenderHandler {
	
	private Canvas canvas;
	
	public void init(Canvas canvas) {
		this.canvas = canvas;
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	public GraphicsContext getGraphics() {
		return canvas.getGraphicsContext2D();
	}
	
	public Vector2 getSize() {
		return new Vector2(canvas.getWidth(), canvas.getHeight());
	}
	
	public Vector2 getHalfSize() {
		Vector2 s = getSize();
		s.div(2);
		return s;
	}
	
	/*
	 * Being drawing code
	 */
	
	public void drawLine(Vector2 start, Vector2 end, Paint stroke) {
		getGraphics().setStroke(stroke);
		getGraphics().strokeLine(start.getX(), start.getY(), end.getX(), end.getY());
	}
	
	public void drawText(Vector2 pos, Paint fill, Object text) {
		getGraphics().setFill(fill);
		getGraphics().setTextAlign(TextAlignment.LEFT);
		getGraphics().setTextBaseline(VPos.TOP);
		String out = (text == null) ? "null" : text.toString();
		getGraphics().fillText(out, pos.getX(), pos.getY());
	}
	
}