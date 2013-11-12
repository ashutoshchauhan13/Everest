package com.sixthsensemobilesolutions.everest.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sixthsensemobilesolutions.android.network.NetworkRequestManager;
import com.sixthsensemobilesolutions.android.network.NetworkRequestManager.RequestListener;
import com.sixthsensemobilesolutions.android.network.NetworkRequestManager.ResponeType;
import com.sixthsensemobilesolutions.everest.EverestSampleApplication;
import com.sixthsensemobilesolutions.everest.R;
import com.sixthsensemobilesolutions.everest.request.PeachnoteMelodySearchRequest;
import com.sixthsensemobilesolutions.everest.response.PeachnoteResponse;
import com.sixthsensemobilesolutions.everest.util.Util;

public class PeachnoteMelodySearchActivity extends Activity {
	private NetworkRequestManager mNetworkRequestManager;
	private TextView mPeachnoteMelodySearchText;
	private ProgressBar mProgressBar;
	private PeachnoteResponseListener mPeachnoteResponseListener;
	private static String PEACHNOTE_MELODY_SEARCH_URL = "http://www.peachnote.com/rest/api/v0/scoreSearch?site=loc.gov&type=chordAffine&q=62+0+1+2+0+-2+-1&app_id=abe33d61&app_key=e1181cc6f688fa721bc2cc225340be74";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_peachnote_melody_search);
		mPeachnoteMelodySearchText = (TextView) findViewById(R.id.peachnote_response_text);
		mProgressBar = (ProgressBar) findViewById(R.id.peachnote_response_progress);
		mNetworkRequestManager = ((EverestSampleApplication) this.getApplication()).getServiceLocator().getNetworkRequestManager();
		mPeachnoteResponseListener = new PeachnoteResponseListener();
		mNetworkRequestManager.submitRequest(new PeachnoteMelodySearchRequest(PEACHNOTE_MELODY_SEARCH_URL), mPeachnoteResponseListener);
		mProgressBar.setVisibility(View.VISIBLE);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mNetworkRequestManager.cancelRequest(mPeachnoteResponseListener);
	}

	private class PeachnoteResponseListener implements RequestListener<PeachnoteResponse[]> {

		@Override
		public void onSuccess(PeachnoteResponse[] response) {
			mPeachnoteMelodySearchText.setText("Response of the request is: \n No of composers are: " + response.length);
			if (response.length > 0) {
				mPeachnoteMelodySearchText.setText(mPeachnoteMelodySearchText.getText() + "\n\nFirst object of the response is =\n" + response[0].toString());
			}
			mProgressBar.setVisibility(View.GONE);
		}

		@Override
		public void onFailure(ResponeType failureType, int httpResponseCode) {
			if (failureType == ResponeType.UNKNOWN_ERROR && !Util.isOnline(PeachnoteMelodySearchActivity.this)) {
				mPeachnoteMelodySearchText.setText("Response failed due to no network connection");
			} else {
				mPeachnoteMelodySearchText.setText("request failed");
			}
			mProgressBar.setVisibility(View.GONE);
		}

	}

}
