package com.sixthsensemobilesolutions.android.network;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import com.sixthsensemobilesolutions.android.request.BaseRequest;
import com.sixthsensemobilesolutions.android.request.BaseRequest.HttpMethodType;
import com.sixthsensemobilesolutions.android.request.BaseResponse;

public class NetworkRequestManager {

	public static String TAG = "NetworkRequestManager";

	public enum ResponeType {
		SUCCESS,
		NO_CONNECTION,
		SLOW_OR_DISABLED_CONNECTION,
		INVALID_REQUEST,
		UNKNOWN_ERROR,
		PARSING_ERROR,
		REQUEST_FAILED
	}

	public interface RequestListener<T> {

		public void onSuccess(T response);

		public void onFailure(ResponeType failureType, int httpResponseCode);
	}

	@SuppressWarnings("rawtypes")
	private HashMap<RequestListener, RequestTask> requestMap;

	@SuppressWarnings("rawtypes")
	public NetworkRequestManager() {
		requestMap = new HashMap<RequestListener, RequestTask>();
	}

	/**
	 * Asynchronously executes the request and let the client know once the
	 * request is completed
	 * 
	 * @param <MyRequestTask>
	 * 
	 * @param request
	 *            - Request Object which contains request details
	 * @param requestListener
	 *            - A call back with success and failure
	 * @return true or false - Reflects weather the request is accepted or
	 *         rejected
	 */
	public <T, MyRequestTask> boolean submitRequest(BaseRequest<T> request, RequestListener<T> requestListener) {
		if (request == null || !request.isValid() || requestListener == null) {
			return false;
		}
		RequestTask<T> requestTask = new RequestTask<T>(request);
		requestMap.put(requestListener, requestTask);
		submitAsyncRequest(requestTask);
		return true;
	}

	private <T> void submitAsyncRequest(RequestTask<T> requestTask) {
		if (Build.VERSION.SDK_INT >= 11) {
			requestTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} else {
			requestTask.execute();
		}
	}

	public <T> boolean cancelRequest(RequestListener<T> requestListener) {
		if (requestListener == null) {
			return false;
		}
		RequestTask<?> requestTask = requestMap.get(requestListener);
		if (requestTask != null && !requestTask.isCancelled()) {
			return requestTask.cancel(true);
		}
		return false;
	}

	public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
		for (Entry<T, E> entry : map.entrySet()) {
			if (value.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}

	// TODO need to add the implementation for Froyo and less
	private class RequestTask<T> extends AsyncTask<Void, Void, BaseResponse<T>> {

		private static final int READ_TIME_OUT = 30000;
		private static final int CONNECT_TIME_OUT = 45000;
		private String postData;
		private URLConnection httpURLConnection;
		private BaseRequest<T> request;

		RequestTask(BaseRequest<T> request) {
			this.request = request;
			this.postData = request.getPostData();
		}

		@Override
		protected BaseResponse<T> doInBackground(Void... params) {
			URL url = null;
			BaseResponse<T> response = new BaseResponse<T>();
			InputStream inputStream = null;
			try {
				url = new URL(request.getRequestUrl());
				if (request.isHttpsRequest() || request.getRequestUrl().contains("https")) {
					httpURLConnection = (HttpsURLConnection) url.openConnection();
					SSLContext sc;
					sc = SSLContext.getInstance("TLS");
					sc.init(null, null, new java.security.SecureRandom());
					((HttpsURLConnection) httpURLConnection).setSSLSocketFactory(sc.getSocketFactory());
					((HttpsURLConnection) httpURLConnection).setRequestMethod(request.getHttpMethodType().getMethodName());

				} else {
					httpURLConnection = (HttpURLConnection) url.openConnection();
					((HttpURLConnection) httpURLConnection).setRequestMethod(request.getHttpMethodType().getMethodName());
				}
				httpURLConnection.setReadTimeout(READ_TIME_OUT);
				httpURLConnection.setConnectTimeout(CONNECT_TIME_OUT);
				httpURLConnection.setRequestProperty("Content-Type", "application/json");
				if (request.isBasicAuthNeeded()) {
					String basicAuth = "Basic " + new String(Base64.encode(getBasicAuthUserNameAndPwdPair(request.getBasicAuthUserName(), request.getBasicAuthPwd()).getBytes(), Base64.NO_WRAP));
					httpURLConnection.setRequestProperty("Authorization", basicAuth);
				}
				if (request.getHttpMethodType() == HttpMethodType.POST || request.getHttpMethodType() == HttpMethodType.PUT) {
					httpURLConnection.setDoInput(true);
					httpURLConnection.setDoOutput(true);
					if (postData != null) {
						OutputStream outputStream = httpURLConnection.getOutputStream();
						BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
						bufferedWriter.write(postData);
						bufferedWriter.flush();
						bufferedWriter.close();
					}
				}
				httpURLConnection.connect();
				int responseCode = getResponseCode();
				response = new BaseResponse<T>(responseCode);
				switch (responseCode) {
				case HttpURLConnection.HTTP_OK: {
					inputStream = httpURLConnection.getInputStream();
					@SuppressWarnings("unchecked")
					T parsedResponse = (T) JsonUtil.parseAsJson(request.isContentTypeGZIP(), inputStream, request.getResponseClass());
					response.setResponeType(ResponeType.SUCCESS);
					response.setResponse(parsedResponse);
					break;
				}
				default:
					response.setResponeType(ResponeType.REQUEST_FAILED);
				}

			} catch (SocketTimeoutException ex) {
				response.setResponeType(ResponeType.SLOW_OR_DISABLED_CONNECTION);
			} catch (IOException ex) {
				Log.d(TAG, " url = " + url + " error -" + ex.getMessage());
				response.setResponeType(ResponeType.UNKNOWN_ERROR);
			} catch (GsonParsingException ex) {
				response.setResponeType(ResponeType.PARSING_ERROR);
			} catch (Exception ex) {
				response.setResponeType(ResponeType.UNKNOWN_ERROR);
			} finally {
				disconnect();
			}
			return response;
		}

		private int getResponseCode() throws IOException {
			if (request.isHttpsRequest()) {
				return ((HttpsURLConnection) httpURLConnection).getResponseCode();

			} else {
				return ((HttpURLConnection) httpURLConnection).getResponseCode();

			}
		}

		private void disconnect() {
			if (request.isHttpsRequest()) {
				((HttpsURLConnection) httpURLConnection).disconnect();

			} else {
				((HttpURLConnection) httpURLConnection).disconnect();

			}
		}

		@SuppressWarnings("unchecked")
		protected void onPostExecute(BaseResponse<T> response) {
			RequestListener<T> requestListener = getKeyByValue(requestMap, this);
			if (requestListener == null) {
				return;
			}
			if (response != null && response.getResponseType() == ResponeType.SUCCESS) {
				requestListener.onSuccess(response.getResponse());
				return;
			}
			requestListener.onFailure(response.getResponseType(), response.getHttpResponseCode());
		}
	}

	public String getBasicAuthUserNameAndPwdPair(String userName, String pwd) {
		return userName + ":" + pwd;
	}

}
