package com.cjburkey.mathsstill.render.element;

import com.cjburkey.mathsstill.math.Vector2;
import com.cjburkey.mathsstill.render.RenderHandler;
import com.cjburkey.mathsstill.render.Transform;
import javafx.scene.paint.Color;

public class ElementLine implements IRenderElement {
	
	private boolean shown = true;
	private final Vector2 start;
	private final Vector2 end;
	private Color color;
	
	public ElementLine(Vector2 start, Vector2 end, Color color) {
		this.start = new Vector2(start);
		this.end = new Vector2(end);
		this.color = color;
	}
	
	public ElementLine() {
		this(Vector2.EMPTY(), Vector2.EMPTY(), Color.BLACK);
	}
	
	public void init() {
	}
	
	public void render(RenderHandler renderer, Transform transform) {
		renderer.drawLine(transform.transform(start), transform.transform(end), color);
	}
	
	public boolean isShown() {
		return shown;
	}
	
	public void show() {
		shown = true;
	}
	
	public void hide() {
		shown = false;
	}
	
	public void setStart(Vector2 start) {
		this.start.set(start);
	}
	
	public void setEnd(Vector2 end) {
		this.end.set(end);
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
}