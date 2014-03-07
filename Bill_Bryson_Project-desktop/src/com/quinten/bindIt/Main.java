package com.quinten.bindIt;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.quinten.bindIt.BillBryson;
import com.quinten.bindIt.Reference;

public class Main {
	public static void main(String[] args) {
		System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true");
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = Reference.NAME + " - " + Reference.VERSION;
		cfg.useGL20 = Reference.USE_GL_2_0;
		cfg.width = Reference.WIDTH;
		cfg.height = Reference.HEIGHT;
		
		new LwjglApplication(new BillBryson(), cfg);
	}
}
