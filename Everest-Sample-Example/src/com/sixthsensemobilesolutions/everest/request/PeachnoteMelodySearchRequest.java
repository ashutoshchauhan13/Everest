package com.sixthsensemobilesolutions.everest.request;

import android.text.TextUtils;

import com.sixthsensemobilesolutions.android.request.BaseRequest;
import com.sixthsensemobilesolutions.everest.response.PeachnoteResponse;

public class PeachnoteMelodySearchRequest extends BaseRequest<PeachnoteResponse[]> {

	private String url;

	public PeachnoteMelodySearchRequest(String url) {
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
	public Class<PeachnoteResponse[]> getResponseClass() {
		return PeachnoteResponse[].class;
	}

}
