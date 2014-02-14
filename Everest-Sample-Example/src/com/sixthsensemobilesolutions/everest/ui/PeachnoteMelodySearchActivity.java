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
import com.sixthsensemobilesolutions.everest.request.TeamRequest;
import com.sixthsensemobilesolutions.everest.request.UsersRequest;
import com.sixthsensemobilesolutions.everest.response.PeachnoteResponse;
import com.sixthsensemobilesolutions.everest.response.TeamResponse;
import com.sixthsensemobilesolutions.everest.response.UsersResponse;
import com.sixthsensemobilesolutions.everest.util.Util;

public class PeachnoteMelodySearchActivity extends Activity {
	private NetworkRequestManager mNetworkRequestManager;
	private TextView mPeachnoteMelodySearchText;
	private ProgressBar mProgressBar;
	private PeachnoteResponseListener mPeachnoteResponseListener;
	private UsersResponseListener mUsersResponseListener;
	private TextView mTeamResponseText;
	private TeamResponseListener mTeamResponseListener;
	private static String PEACHNOTE_MELODY_SEARCH_URL = "http://www.peachnote.com/rest/api/v0/scoreSearch?site=loc.gov&type=chordAffine&q=62+0+1+2+0+-2+-1&app_id=abe33d61&app_key=e1181cc6f688fa721bc2cc225340be74";
	private static String USERS_REQUEST_URL = "http://www.json-generator.com/j/bTafSxDyqa?indent=4";
	private static String TEAM_REQUEST_URL = "http://www.json-generator.com/j/bOSwjOtZBu?indent=4";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_peachnote_melody_search);
		mPeachnoteMelodySearchText = (TextView) findViewById(R.id.peachnote_response_text);
		mTeamResponseText = (TextView) findViewById(R.id.team_response_text);
		mProgressBar = (ProgressBar) findViewById(R.id.peachnote_response_progress);
		mNetworkRequestManager = ((EverestSampleApplication) this.getApplication()).getServiceLocator().getNetworkRequestManager();
		mPeachnoteResponseListener = new PeachnoteResponseListener();
		//		mNetworkRequestManager.submitRequest(new PeachnoteMelodySearchRequest(PEACHNOTE_MELODY_SEARCH_URL), mPeachnoteResponseListener);
		mUsersResponseListener = new UsersResponseListener();
		mTeamResponseListener = new TeamResponseListener();
		mNetworkRequestManager.submitRequest(new UsersRequest(USERS_REQUEST_URL), mUsersResponseListener);
		mNetworkRequestManager.submitRequest(new TeamRequest(TEAM_REQUEST_URL), mTeamResponseListener);
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
		//		mNetworkRequestManager.cancelRequest(mPeachnoteResponseListener);
		mNetworkRequestManager.cancelRequest(mUsersResponseListener);
		mNetworkRequestManager.cancelRequest(mTeamResponseListener);
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
		public void onFailure(ResponeType failureType, int httpResponseCode, final String errorMsg) {
			if (failureType == ResponeType.UNKNOWN_ERROR && !Util.isOnline(PeachnoteMelodySearchActivity.this)) {
				mPeachnoteMelodySearchText.setText("Response failed due to no network connection");
			} else {
				mPeachnoteMelodySearchText.setText("request failed");
			}
			mProgressBar.setVisibility(View.GONE);
		}

	}

	private class UsersResponseListener implements RequestListener<UsersResponse> {

		@Override
		public void onSuccess(UsersResponse response) {
			mPeachnoteMelodySearchText.setText("Response of the request is:" + response.toString());
			mProgressBar.setVisibility(View.GONE);
		}

		@Override
		public void onFailure(ResponeType failureType, int httpResponseCode, final String errorMsg) {
			if (failureType == ResponeType.UNKNOWN_ERROR && !Util.isOnline(PeachnoteMelodySearchActivity.this)) {
				mPeachnoteMelodySearchText.setText("Response failed due to no network connection");
			} else {
				mPeachnoteMelodySearchText.setText("request failed");
			}
			mProgressBar.setVisibility(View.GONE);
		}

	}

	private class TeamResponseListener implements RequestListener<TeamResponse> {

		@Override
		public void onSuccess(TeamResponse response) {
			mTeamResponseText.setText("Response of the request is:" + response.toString());
			mProgressBar.setVisibility(View.GONE);
		}

		@Override
		public void onFailure(ResponeType failureType, int httpResponseCode, final String errorMsg) {
			mProgressBar.setVisibility(View.GONE);
		}

	}

}
