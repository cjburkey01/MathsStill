package com.cjburkey.mathsstill.render;

import javafx.animation.AnimationTimer;
import javafx.concurrent.Task;

public class RenderLoop {
	
	private AnimationTimer timer;
	private Thread thread;
	private boolean running = false;
	
	private int frames = 0;
	private int fps = 0;
	private long lastFpsCheck = System.nanoTime();
	private boolean firstRun = true;
	
	public RenderLoop(Runnable call) {
		timer = new AnimationTimer() {
			public void handle(long current) {
				if (running) {
					if (firstRun) {
						firstRun = false;
						System.out.println("Render loop started.");
					}
					frames ++;
					long now = System.nanoTime();
					if (now - lastFpsCheck >= 1000000000L) {
						fps = frames;
						frames = 0;
						lastFpsCheck = now;
					}
					call.run();
				} else {
					timer.stop();
				}
			}
		};
		
		thread = new Thread(new Task<Void>() {
			public Void call() {
				timer.start();
				return null;
			}
		});
	}
	
	public void start() {
		running = true;
		thread.start();
	}
	
	public void stop() {
		running = false;
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public int getFps() {
		return fps;
	}
	
}