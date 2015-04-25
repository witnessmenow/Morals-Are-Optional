package com.ladinc.morals.android;

import java.io.IOException;
import java.io.InputStream;

import tv.ouya.console.api.OuyaActivity;
import tv.ouya.console.api.OuyaController;
import tv.ouya.console.api.OuyaFacade;

import com.ladinc.core.McpCah;
import com.ladinc.core.controllers.GameControllerManager;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class McpCahActivity extends AndroidApplication {

	@Override
	public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
			config.useWakelock = true;
			
			Context context = getApplicationContext();
			
			Bundle developerInfo = new Bundle();
			
			byte[] applicationKey = null;
			
			// load the application key
			try {
				
				//You need to add you der.key from the developer console and put it in /res/raw
				InputStream inputStream = getResources().openRawResource(R.raw.key);
				applicationKey = new byte[inputStream.available()];
				inputStream.read(applicationKey);
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

	        developerInfo.putString(OuyaFacade.OUYA_DEVELOPER_ID, Secret.getDeveloperID());
	        developerInfo.putByteArray(OuyaFacade.OUYA_DEVELOPER_PUBLIC_KEY, applicationKey);
			
			//Gdx.app.error("mOuyaFacade","getting instance");
			GameControllerManager.M_OUYA_FACADE = OuyaFacade.getInstance();
			GameControllerManager.M_OUYA_FACADE.init(context, developerInfo);
			OuyaController.init(context);
			Log.e("onCreate", "OuyaFacade after init");
			
			//Gdx.app.error("mOuyaFacade","instance got");
			
			initialize(new McpCah(), config);
	}
}
