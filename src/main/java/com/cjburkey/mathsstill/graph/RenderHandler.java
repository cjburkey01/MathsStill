package com.cjburkey.mathsstill.graph;

import com.cjburkey.mathsstill.MathsStill;
import com.cjburkey.mathsstill.math.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public class RenderHandler {
	
	private static MathsStill instance;
	
	public static void init(MathsStill instance) {
		RenderHandler.instance = instance;
	}
	
	public static void drawLine(Vector2 start, Vector2 end, Paint stroke) {
		getGC().setStroke(stroke);
		getGC().strokeLine(start.getX(), start.getY(), end.getX(), end.getY());
	}
	
	private static GraphicsContext getGC() {
		return instance.getGraph();
	}
	
}