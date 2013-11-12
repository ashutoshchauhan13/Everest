package com.sixthsensemobilesolutions.everest;

import com.sixthsensemobilesolutions.android.network.NetworkRequestManager;

public class ServiceLocator {

	NetworkRequestManager mNetworkRequestManager;


	public NetworkRequestManager getNetworkRequestManager() {
		if (mNetworkRequestManager == null) {
			mNetworkRequestManager = new NetworkRequestManager();
		}
		return mNetworkRequestManager;
	}
}
