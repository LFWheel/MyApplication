package com.eeesys.frame.net.impl;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.eeesys.frame.R;
import com.eeesys.frame.net.inter.DecryptionListener;
import com.eeesys.frame.net.inter.NetError;
import com.eeesys.frame.net.inter.NetWorkListener;
import com.eeesys.frame.net.inter.URLCallBack;
import com.eeesys.frame.net.model.URLComplete;
import com.eeesys.frame.utils.L;
import com.eeesys.frame.utils.NetUtils;
import com.eeesys.frame.utils.ToastTool;
import com.eeesys.frame.utils.Tools;

public class NetWorkImpl implements NetWorkListener, URLCallBack, DecryptionListener, NetError {
	
	public static final int GET = 0, POST = 1;
	public boolean SHOWDIALOG = true;
	private static final String TAG = "com.eeesys.frame.impl.NetWorkImpl";
	private static final int STATUS_CODE_SUCCESS = 200;
	private String encode = "UTF-8";
	private NetWorkTask netWorkTask;
	private int methord = 0; // 默认为0 GET方式请求，非零为POST
	private URLCallBack callBack;
	private HttpURLConnection conn = null;
	private boolean isEncrpt = false; // 是否有加密
	private Handler mHandler;
	private NetError netError;
	private DecryptionListener decryption;

	public void setSHOWDIALOG(boolean sHOWDIALOG) {
		SHOWDIALOG = sHOWDIALOG;
	}

	public boolean isEncrpt() {
		return isEncrpt;
	}

	public void setEncrpt(boolean isEncrpt) {
		setEncrpt(isEncrpt, null);
	}

	public void setEncrpt(boolean isEncrpt, DecryptionListener decryption) {
		this.isEncrpt = isEncrpt;
		if(isEncrpt){
			setDecryption(decryption);
		}
	}

	public void setCallBack(URLCallBack callBack) {
		if (callBack == null) {
			this.callBack = this;
		} else {
			this.callBack = callBack;
		}
	}

	public void setDecryption(DecryptionListener decryption) {
		if (decryption == null) {
			this.decryption = this;
		} else {
			this.decryption = decryption;
		}
	}

	public void setNetError(NetError netError) {
		this.netError = netError;
	}

	private enum requestMethord {
		GET, POST
	};

	private Context context;

	public NetWorkImpl(Context context) {
		this(context, 0);
	}

	public NetWorkImpl(Context context, int requestMethord) {
		this.context = context;
		setCallBack(null);
		setDecryption(null);
		if (requestMethord != 0) {
			methord = POST;
		} else {
			methord = GET;
		}
	}

	public int getMethord() {
		return methord;
	}

	public void setMethord(int methord) {
		if (methord != 0) {
			methord = POST;
		} else {
			methord = GET;
		}
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	@Override
	public void LoadUrl(String baseURL) {
		LoadUrl(baseURL, null);
	}

	@Override
	public void LoadUrl(String baseURL, Map<String, Object> urlParam) {
		LoadUrl(baseURL, urlParam, null);
	}

	@Override
	public void LoadUrl(String baseURL, Map<String, Object> urlParam,
			String cookie) {
		netWorkTask = new NetWorkTask(context, urlParam);
		netWorkTask.execute(baseURL, cookie);
	}

	@Override
	public void LoadUrlWithNoThread(String baseURL) {
		LoadUrlWithNoThread(baseURL, null);
	}

	@Override
	public void LoadUrlWithNoThread(String baseURL, Map<String, Object> urlParam) {
		LoadUrlWithNoThread(baseURL, urlParam, null);
	}

	@Override
	public void LoadUrlWithNoThread(String baseURL,
			Map<String, Object> urlParam, String cookie) {
		mHandler = new Handler(context.getMainLooper()) {

			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 0) {
					if(netError != null)
						netError.error();
					else
						error();
				} else if (msg.what == 1) {
					callBack.onFail((URLComplete) msg.obj);
				} else {
					URLComplete message = (URLComplete) msg.obj;
					callBack.onSucess(decrypt(message));
				}
			}
		};
		if (NetUtils.isConnected(context)) {
			URLComplete backInfo = getResult(baseURL, urlParam, cookie);
			Log.d(TAG, backInfo.getMessage());
			if (URLComplete.ERROR.equals(backInfo.getMessage())) {
				mHandler.sendMessage(mHandler.obtainMessage(1, backInfo));
			} else {
				mHandler.sendMessage(mHandler.obtainMessage(2, backInfo));
			}
		} else {
			mHandler.sendMessage(mHandler.obtainMessage(0));
		}

	}

	private URLComplete getResult(String baseURL, Map<String, Object> urlParam,
			String cookie) {
		// 如果参数不为空，添加参数到url中
		String param = new String();
		StringBuilder sb = new StringBuilder(param);
		if (urlParam != null && !urlParam.isEmpty()) {
			for (Map.Entry<String, Object> entry : urlParam.entrySet()) {
				sb.append(entry.getKey()).append("=")
						.append(Tools.encode(entry.getValue().toString()))
						.append("&");
			}
			param = sb.deleteCharAt(sb.length() - 1).toString();
		}

		Log.d("网络地址", baseURL);
		Log.d("网络参数", param);

		URLComplete urlComplete = new URLComplete();
		try {
			if (methord == 0) {
				baseURL += "?" + param;
			}
			URL url = new URL(baseURL);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(requestMethord.values()[methord].name());
			conn.setConnectTimeout(10000);
			if (cookie != null) {
				conn.setRequestProperty("Cookie", cookie);
			}
			if (methord != 0) {
				conn.setDoOutput(true);
				conn.setDoInput(true);
				conn.setRequestProperty("accept", "*/*");
				conn.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");
				conn.setRequestProperty("Content-Length",
						param.getBytes(encode).length + "");
				conn.getOutputStream().write(param.getBytes(encode));
			}
			urlComplete.setResponseCode(conn.getResponseCode());
			if (urlComplete.getResponseCode() == STATUS_CODE_SUCCESS) {
				List<String> list = conn.getHeaderFields().get("Set-Cookie");
				if (list != null) {
					StringBuilder strb = new StringBuilder();
					for (int i = 0; i < list.size(); i++) {
						strb.append(list.get(i)).append(";");
					}
					strb.deleteCharAt(strb.length() - 1);
					urlComplete.setCookie(strb.toString());
				}
				urlComplete.setMessage(Tools.streamToString(
						conn.getInputStream(), encode));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		}
		return urlComplete;

	}

	public void closeConnect() {
		if (netWorkTask != null) {
			netWorkTask.cancel(true);
			netWorkTask = null;
		}
		if (conn != null) {
			conn.disconnect();
			conn = null;
		}
	}

	private class NetWorkTask extends AsyncTask<String, Void, URLComplete> {

		private Map<String, Object> urlParam;
		private Context context;
		private ProgressDialog pd;

		public NetWorkTask(Context context, Map<String, Object> urlParam) {
			this.context = context;
			this.urlParam = urlParam;
		}

		@Override
		protected void onPreExecute() {
			if (SHOWDIALOG) {
				pd = new ProgressDialog(context);
				pd.setMessage("Loading...");
				pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				pd.setCanceledOnTouchOutside(false);
				pd.show();
			}
		}

		@Override
		protected URLComplete doInBackground(String... params) {
			if (NetUtils.isConnected(context)) {
				return getResult(params[0], urlParam, params[1]);
			} else {
				URLComplete urlComplete = new URLComplete();
				urlComplete.setResponseCode(-1000);
				return urlComplete;
			}
		}

		@Override
		protected void onPostExecute(URLComplete result) {
			if (pd != null && pd.isShowing()) {
				try{
					pd.dismiss();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			if (result.getResponseCode() == -1000) {
				if(netError != null)
					netError.error();
				else
					error();
				return;
			}
			Log.d(TAG, result.getMessage());
			if (URLComplete.ERROR.equals(result.getMessage())) {
				callBack.onFail(result);
			} else {
				callBack.onSucess(decrypt(result));
			}
		}
	}

	@Override
	public void onSucess(URLComplete urlComplete) {

	}

	@Override
	public void onFail(URLComplete urlComplete) {
		if (URLComplete.ERROR.equals(urlComplete.getMessage())) {
			ToastTool.show(context, context.getString(R.string.url_err));
		}
	}

	@Override
	public String decrypt(String mw) {
		return mw;
	}

	private URLComplete decrypt(URLComplete result) {
		if (isEncrpt) {
			if (decryption == null) {
				throw new RuntimeException("DecryptionListener不能为空");
			}
			String msg = decryption.decrypt(result.getMessage());
			L.d(TAG, msg);
			result.setMessage(msg);
		}
		return result;
	}

	@Override
	public void error() {
		ToastTool.show(context, "手机网络异常");
		NetUtils.openSetting(context);
	}


}
