/*
 * Copyright 2012 PayPal.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package team.cs6365.payfive.ui.main;

import org.json.JSONException;
import org.json.JSONObject;

import team.cs6365.payfive.PayFive;
import team.cs6365.payfive.R;
import team.cs6365.payfive.util.AccessHelperConnect;
import team.cs6365.payfive.util.AsyncConnection;
import team.cs6365.payfive.util.AsyncConnection.AsyncConnectionListener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * This {@link Activity} is used to enable the user to login at PayPal and
 * accept the usage of his data in the embedding application.<br/>
 * Therefore the LoginActivity uses a {@link WebView} that loads the
 * {@link AccessHelper}'s urls.
 * 
 * @author tmesserschmidt@paypal.com
 * 
 */
public class LoginActivity extends Activity {

	private static final String TAG = "~~~PayFive~~~ ## LoginActivity ## ";

	public static final String TYPE = "type";

	/*
	 * The id and secret are used to identify your application. Those
	 * credentials can be obtained at developer.papyal.com
	 */

	private WebView webView;
	private ProgressDialog progress;
	private AccessHelperConnect helper;

	private PayFive payfive;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		payfive = (PayFive) getApplication();

		if (payfive.DEBUG)
			Log.d(TAG, "LoginActivity onCreate()");

		webView = new WebView(this);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new PPWebViewClient());

		setContentView(webView);

		helper = AccessHelperConnect.init(payfive.CLIENT_ID,
				payfive.CLIENT_SECRET);

		progress = ProgressDialog.show(LoginActivity.this,
				getString(R.string.progress_loading_title),
				getString(R.string.progress_loading_msg));

		if (payfive.DEBUG)
			Log.d(TAG, "In LoginActivity, AuthURL: " + helper.getAuthUrl());
		webView.loadUrl(helper.getAuthUrl());
	}

	private class PPWebViewClient extends WebViewClient {

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			if (progress != null && progress.isShowing()) {
				progress.dismiss();
				progress = null;
			}
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			System.out.println("URL: " + url);
			if (payfive.DEBUG)
				Log.d(TAG, "AccessCodeUrl: " + helper.getAccessCodeUrl());
			if (payfive.DEBUG)
				Log.d(TAG, "CodeParameter: " + helper.getCodeParameter());

			if (url.contains(payfive.ACCESS_DENIED)) {
				setResult(RESULT_CANCELED);
				finish();
				return true;
			} else if ((url.startsWith(helper.getAccessCodeUrl()) || url
					.startsWith(PayFive.REDIRECT_URL))
					&& url.contains(helper.getCodeParameter())) {
				getAccessToken(url);
				return true;
			}
			return super.shouldOverrideUrlLoading(view, url);
		}

		private void getAccessToken(String url) {
			final Uri uri = Uri.parse(url);
			final String code = uri.getQueryParameter("code");
			final String urlParams = helper.getTokenServiceParameters(code);
			final String urlString = helper.getTokenServiceUrl();

			new AsyncConnection(new AsyncConnectionListener() {
				public void connectionDone(String result) {
					try {
						final JSONObject object = new JSONObject(result);
						final String accessToken = object
								.getString("access_token");
						if (payfive.DEBUG)
							Log.d(TAG,
									"AccessToken: "
											+ object.optString("access_token"));
						if (accessToken != null && !accessToken.equals("")) {
							getProfile(accessToken);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}).execute(AsyncConnection.METHOD_POST, urlString, urlParams);
		}

		private void getProfile(String accessToken) {
			final String urlString = helper.getProfileUrl(accessToken);

			new AsyncConnection(new AsyncConnectionListener() {
				public void connectionDone(String result) {
					setResult(RESULT_OK, new Intent().putExtra(
							AccessHelperConnect.DATA_PROFILE, result));
					finish();
				}
			}).execute(AsyncConnection.METHOD_GET, urlString);
		}
	}
}
