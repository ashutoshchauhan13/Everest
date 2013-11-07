package com.sixthsensemobilesolutions.android.request;

import com.sixthsensemobilesolutions.android.network.NetworkRequestManager.ResponeType;

public class BaseResponse<T> {

	private T response;
	private int httpResponseCode;

	private ResponeType responeType;

	public BaseResponse() {

	}

	public BaseResponse(int httpResponseCode) {
		this.httpResponseCode = httpResponseCode;
	}

	public BaseResponse(T response, ResponeType responeType, int httpResponseCode) {
		this.response = response;
		this.responeType = responeType;
		this.httpResponseCode = httpResponseCode;
	}

	public T getResponse() {
		return response;
	}

	public ResponeType getResponseType() {
		return responeType;
	}

	public void setResponse(T response) {
		this.response = response;
	}

	public void setResponeType(ResponeType responeType) {
		this.responeType = responeType;
	}

	public int getHttpResponseCode() {
		return httpResponseCode;
	}

	public void setHttpResponseCode(int httpResponseCode) {
		this.httpResponseCode = httpResponseCode;
	}

}
