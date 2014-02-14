package com.sixthsensemobilesolutions.android.request;

public abstract class BaseRequest<T> {

	private String mPostData;

	public enum HttpMethodType {

		GET("GET"),
		POST("POST"),
		PUT("PUT"),
		DELETE("DELETE"),
		OTHER("UNKNOWN");

		private String methodName;

		HttpMethodType(String methodName) {
			this.methodName = methodName;
		}

		public String getMethodName() {
			return methodName;
		}
	}

	/*
	 * A request will not be executed if it is invalid
	 */
	public abstract boolean isValid();

	public abstract boolean isContentTypeGZIP();

	public abstract HttpMethodType getHttpMethodType();

	public abstract String getRequestUrl();

	public boolean isBasicAuthNeeded() {
		return false;
	}

	public String getBasicAuthUserName() {
		return null;
	}

	public String getBasicAuthPwd() {
		return null;
	}

	public abstract Class<T> getResponseClass();

	public String getPostData() {
		return mPostData;
	}

	/**
	 * Override this method to supply the post data if you are requesting POST
	 * HttpMethod
	 * 
	 * @return
	 * 
	 * @return
	 * 
	 * @return
	 */
	public BaseRequest<T> setPostData(String postData) {
		mPostData = postData;
		return this;
	}

	public boolean isHttpsRequest() {
		return false;
	}

}
