package com.ladinc.html;

import com.ladinc.core.McpCah;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class McpCahHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
		return new McpCah();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(480, 320);
	}
}
