package com.cjburkey.mathsstill.cursor;

import com.cjburkey.mathsstill.graph.RenderHandler;
import javafx.scene.Cursor;

public class CursorHandler {
	
	private Cursor currentCursor;
	
	public CursorHandler() {
		setCursor(Cursor.CROSSHAIR);
	}
	
	public void setCursor(Cursor cursor) {
		if (cursor != null) {
			currentCursor = cursor;
		}
	}
	
	public Cursor getCurrentCursor() {
		return currentCursor;
	}
	
	public void render() {
		if (currentCursor != null) {
			RenderHandler.getCanvas().setCursor(currentCursor);
		}
		setCursor(Cursor.CROSSHAIR);
	}
	
}