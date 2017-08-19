package com.cjburkey.mathsstill.render;

import java.util.ArrayList;
import java.util.List;
import com.cjburkey.mathsstill.math.Vector2;
import com.cjburkey.mathsstill.render.element.IRenderElement;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;

public class RenderHandler {
	
	private Canvas canvas;
	private final List<IRenderElement> elements = new ArrayList<>();
	
	/*
	 * Render elements
	 */
	
	public void addElement(IRenderElement e) {
		elements.add(e);
	}
	
	public void removeElement(IRenderElement e) {
		elements.remove(e);
	}
	
	public IRenderElement[] getElements() {
		return elements.toArray(new IRenderElement[elements.size()]);
	}
	
	public void render(Transform transform) {
		for (int i = 0; i < elements.size(); i ++) {
			if (elements.get(i) != null) {
				IRenderElement element = elements.get(i);
				element.render(this, transform);
			}
		}
	}
	
	/*
	 * Init and requirements
	 */
	
	public void init(Canvas canvas) {
		this.canvas = canvas;
	}
	
	public Canvas getCanvas() {
		return canvas;
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
	
	public GraphicsContext getGraphics() {
		return canvas.getGraphicsContext2D();
	}
	
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