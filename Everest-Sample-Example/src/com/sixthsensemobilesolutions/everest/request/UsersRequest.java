package com.sixthsensemobilesolutions.everest.request;

import android.text.TextUtils;

import com.sixthsensemobilesolutions.android.request.BaseRequest;
import com.sixthsensemobilesolutions.everest.response.UsersResponse;

public class UsersRequest extends BaseRequest<UsersResponse> {

	private String url;

	public UsersRequest(String url) {
		this.url = url;
	}

	@Override
	public boolean isValid() {
		return !TextUtils.isEmpty(url);
	}

	@Override
	public boolean isContentTypeGZIP() {
		return false;
	}

	@Override
	public HttpMethodType getHttpMethodType() {
		return HttpMethodType.GET;
	}

	@Override
	public String getRequestUrl() {
		return url;
	}

	@Override
	public Class<UsersResponse> getResponseClass() {
		return UsersResponse.class;
	}

}
