package com.cjburkey.mathsstill.render.element.gui;

import com.cjburkey.mathsstill.math.Vector2;
import com.cjburkey.mathsstill.render.element.IRenderElement;

public abstract class GuiElement implements IRenderElement {
	
	protected Vector2 topleft;
	
	public GuiElement(Vector2 pos) {
		this.topleft = new Vector2(pos);
	}
	
}