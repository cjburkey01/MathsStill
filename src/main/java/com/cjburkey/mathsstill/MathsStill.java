package com.cjburkey.mathsstill;

import com.cjburkey.mathsstill.cursor.CursorHandler;
import com.cjburkey.mathsstill.graph.GraphingHandler;
import com.cjburkey.mathsstill.graph.RenderHandler;
import com.cjburkey.mathsstill.loop.RenderLoop;
import com.cjburkey.mathsstill.window.WindowHandler;
import javafx.application.Application;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class MathsStill extends Application {
	
	public static final String version = "0.0.1";

	private WindowHandler windowHandler;
	private RenderLoop renderLoop;
	private GraphingHandler graphingHandler;
	private CursorHandler cursorHandler;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage stage) {
		System.out.println("Building...");
		windowHandler = new WindowHandler();
		renderLoop = new RenderLoop(() -> render());
		windowHandler.buildMainWindow(this, stage);
		RenderHandler.init(this);
		graphingHandler = new GraphingHandler(this);
		cursorHandler = new CursorHandler();
		System.out.println("Built.");
		
		System.out.println("Launching...");
		windowHandler.showMainWindow();
		renderLoop.start();
		System.out.println("Launched.");
	}
	
	private void render() {
		windowHandler.onUpdate(renderLoop.getFps());
		getGraph().translate(getGraph().getCanvas().getWidth() / 2, getGraph().getCanvas().getHeight() / 2);
		graphingHandler.render();
		cursorHandler.render();
		getGraph().translate(-getGraph().getCanvas().getWidth() / 2, -getGraph().getCanvas().getHeight() / 2);
	}
	
	public void exit() {
		windowHandler.exit();
		renderLoop.stop();
	}
	
	public GraphicsContext getGraph() {
		return windowHandler.getGraphingCanvas().getGraphics();
	}
	
	public GraphingHandler getGraphingHandler() {
		return graphingHandler;
	}
	
	public RenderLoop getRenderLoop() {
		return renderLoop;
	}
	
	public CursorHandler getCursorHandler() {
		return cursorHandler;
	}
	
}