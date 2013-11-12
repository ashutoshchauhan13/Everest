package com.sixthsensemobilesolutions.everest;

import android.app.Application;

public class EverestSampleApplication extends Application {

	private ServiceLocator mServiceLocator;

	public ServiceLocator getServiceLocator() {
		return mServiceLocator;
	}

	public void onCreate() {
		if (mServiceLocator == null) {
			mServiceLocator = new ServiceLocator();
		}
	}
}
