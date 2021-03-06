package com.cjburkey.mathsstill.window;

import com.cjburkey.mathsstill.MathsStill;
import com.cjburkey.mathsstill.event.MainEventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class WindowHandler {
	
	private MainEventHandler eventHandler;
	
	private Stage mainStage;
	private VBox mainRoot;
	private Scene mainScene;
	private GraphingCanvas mainCanvas;
	
	public void buildMainWindow(MathsStill ms, Stage stage) {
		Rectangle2D screen = Screen.getPrimary().getVisualBounds();
		
		eventHandler = new MainEventHandler(ms);
		mainStage = stage;
		mainRoot = new VBox();
		mainScene = new Scene(mainRoot);
		mainCanvas = new GraphingCanvas();

		mainCanvas.setOnMouseMoved(e -> eventHandler.onMouseMove(e));
		mainCanvas.setOnMouseDragged(e -> eventHandler.onMouseDrag(e));
		mainCanvas.setOnMousePressed(e -> eventHandler.onMousePress(e));
		mainCanvas.setOnScroll(e -> eventHandler.onMouseScroll(e));
		mainCanvas.widthProperty().bind(mainRoot.widthProperty());
		mainCanvas.heightProperty().bind(mainRoot.heightProperty());
		
		mainRoot.getChildren().addAll(mainCanvas);
		mainRoot.setPrefWidth(screen.getWidth() / 2);
		mainRoot.setPrefHeight(screen.getHeight() / 2);
		
		mainStage.setFullScreen(false);
		mainStage.setFullScreenExitHint(null);
		mainStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		mainStage.setMinWidth(screen.getWidth() / 3);
		mainStage.setMinHeight(screen.getHeight() / 3);
		mainStage.setOnCloseRequest(e -> exit());
		mainStage.setTitle("MathsStill - " + MathsStill.version);
		mainStage.setScene(mainScene);
	}
	
	public void showMainWindow() {
		mainStage.show();
		mainStage.sizeToScene();
		mainStage.centerOnScreen();
	}
	
	public void onUpdate(int fps) {
		if (mainCanvas != null) {
			mainCanvas.update();
		}
	}
	
	public void exit() {
		mainStage.hide();
	}
	
	public GraphingCanvas getGraphingCanvas() {
		return mainCanvas;
	}
	
}