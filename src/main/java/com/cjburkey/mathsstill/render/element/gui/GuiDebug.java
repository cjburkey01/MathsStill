package com.cjburkey.mathsstill.render.element.gui;

import com.cjburkey.mathsstill.math.Vector2;
import com.cjburkey.mathsstill.render.RenderHandler;
import com.cjburkey.mathsstill.render.Transform;
import javafx.scene.Cursor;
import javafx.scene.paint.Color;

public class GuiDebug extends GuiElement {
	
	private boolean shown = true;
	
	private int fps = 0;
	private double gridSize = 0.0d;
	private Cursor cursor = Cursor.DEFAULT;
	private boolean grid = false;
	private boolean gridOrigin = false;
	private Vector2 mouseScreen = Vector2.EMPTY();
	private Vector2 mouseGraph = Vector2.EMPTY();
	
	public GuiDebug(Vector2 pos) {
		super(pos);
	}

	public void update(int fps, double gridSize, Cursor cursor, boolean grid, boolean origin, Vector2 mouse, Vector2 mouseGraph) {
		this.fps = fps;
		this.gridSize = gridSize;
		this.cursor = cursor;
		this.grid = grid;
		gridOrigin = origin;
		mouseScreen = new Vector2(mouse);
		this.mouseGraph = new Vector2(mouseGraph);
	}

	public void render(RenderHandler renderer, Transform transform) {
		double between = 20;
		double x = topleft.getX();
		double y = topleft.getY();
		Color color = Color.BLACK;
		renderer.drawText(new Vector2(x, y), color, "FPS: " + fps);
		renderer.drawText(new Vector2(x, y + between), color, "Scale: " + String.format("%02.2f", transform.getScale()) + " pixels/unit");
		renderer.drawText(new Vector2(x, y + between * 2), color, "Grid Spacing: " + String.format("%02.2f", gridSize));
		renderer.drawText(new Vector2(x, y + between * 3), color, "Cursor: " + cursor);
		renderer.drawLine(new Vector2(x, y + between * 4.5), new Vector2(x + 100, y + between * 4.5), color);
		renderer.drawText(new Vector2(x, y + between * 5), color, "Grid: " + grid);
		renderer.drawText(new Vector2(x + 20, y + between * 6), color, "Origin: " + gridOrigin);
		renderer.drawText(new Vector2(mouseScreen.getX() + 10, mouseScreen.getY() + 10), color, mouseGraph);
	}

	public void init() {
	}

	public void show() {
		shown = true;
	}

	public void hide() {
		shown = false;
	}

	public boolean isShown() {
		return shown;
	}
	
}