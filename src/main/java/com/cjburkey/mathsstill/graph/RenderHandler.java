package com.cjburkey.mathsstill.graph;

import com.cjburkey.mathsstill.MathsStill;
import com.cjburkey.mathsstill.math.Vector2;
import com.cjburkey.mathsstill.window.GraphingCanvas;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;

public class RenderHandler {
	
	private static MathsStill instance;
	
	public static void init(MathsStill instance) {
		RenderHandler.instance = instance;
	}
	
	public static void drawLine(Vector2 start, Vector2 end, Paint stroke) {
		getGC().setStroke(stroke);
		getGC().strokeLine(start.getX(), start.getY(), end.getX(), end.getY());
	}
	
	public static void drawText(Vector2 pos, Paint fill, String text) {
		getGC().setFill(fill);
		getGC().setTextAlign(TextAlignment.LEFT);
		getGC().setTextBaseline(VPos.TOP);
		getGC().fillText(text, pos.getX(), pos.getY());
	}
	
	public static GraphicsContext getGC() {
		return instance.getGraph();
	}
	
	public static GraphingCanvas getCanvas() {
		return (GraphingCanvas) getGC().getCanvas();
	}
	
}