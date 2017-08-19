package com.cjburkey.mathsstill.render.element;

import com.cjburkey.mathsstill.render.RenderHandler;
import com.cjburkey.mathsstill.render.Transform;

public interface IRenderElement {
	
	void init();
	void show();
	void hide();
	void render(RenderHandler renderer, Transform transformation);
	boolean isShown();
	
}