package com.quinten.bindIt.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.quinten.bindIt.BillBryson;
import com.quinten.bindIt.Reference;

public class GwtLauncher extends GwtApplication {
	@Override
	public GwtApplicationConfiguration getConfig () {
		GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(Reference.WIDTH, Reference.HEIGHT);
		return cfg;
	}

	@Override
	public ApplicationListener getApplicationListener () {
		return new BillBryson();
	}
}