package com.cjburkey.mathsstill;

import com.cjburkey.mathsstill.cursor.CursorHandler;
import com.cjburkey.mathsstill.graph.GraphingHandler;
import com.cjburkey.mathsstill.render.RenderHandler;
import com.cjburkey.mathsstill.render.RenderLoop;
import com.cjburkey.mathsstill.window.WindowHandler;
import javafx.application.Application;
import javafx.stage.Stage;

public class MathsStill extends Application {
	
	public static final String version = "0.0.1";
	public static final RenderHandler graphingRender = new RenderHandler();
	public static final RenderHandler graphingGuiRender = new RenderHandler();

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
		graphingRender.init(windowHandler.getGraphingCanvas());
		graphingGuiRender.init(windowHandler.getGraphingCanvas());
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
		graphingRender.centerGraphics();
		graphingHandler.render();
		cursorHandler.render();
		graphingRender.topLeftGraphics();
	}
	
	public void exit() {
		windowHandler.exit();
		renderLoop.stop();
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