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

package team.cs6365.payfive.util;

import team.cs6365.payfive.PayFive;
import android.net.Uri;
import android.util.Log;

/**
 * This class is used to enable fast usage of PayPal Access and OpenID Connect.
 * 
 * @author tmesserschmidt@paypal.com
 * 
 */
public class AccessHelperConnect {
	
	private static final String TAG = "~~~PayFive~~~ ## AccessHelperConnect ## ";

	public static final String DATA_PROFILE = Uri
			.encode("profile email phone https://uri.paypal.com/services/paypalattributes");
	// public static final String DATA_PROFILE = Uri
	// .encode("profile email https://uri.paypal.com/services/paypalattributes");
	// private static final String URL_REDIRECT = "http://localhost:3000/";
//	private static final String URL_REDIRECT = Uri
//			.encode("https://github.com/hoyin29/CS6365Project");
	private static final String URL_REDIRECT = Uri
			.encode(PayFive.REDIRECT_URL);

	private static final String PARAM_CLIENT_ID = "client_id=";
	private static final String PARAM_CLIENT_SECRET = "client_secret=";
	private static final String PARAM_REDIRECT_URI = "redirect_uri=";
	private static final String PARAM_SCOPE = "scope=";
	private static final String PARAM_SCHEMA = "schema=";
	private static final String PARAM_RESPONSE_TYPE = "response_type=";
	private static final String PARAM_CODE = "code=";
	private static final String PARAM_ACCESS_TOKEN = "access_token=";
	private static final String PARAM_GRANT_TYPE = "grant_type=authorization_code";
	private static final String VALUE_RESPONSE_TYPE = "code";
	private static final String URL_AUTHORIZE = "https://www.sandbox.paypal.com/webapps/auth/protocol/openidconnect/v1/authorize";
	// private static final String URL_AUTHORIZE =
	// "https://www.paypal.com/webapps/auth/protocol/openidconnect/v1/authorize";
	private static final String URL_TOKENSERVICE = "https://api.sandbox.paypal.com/v1/identity/openidconnect/tokenservice";
//	private static final String URL_TOKENSERVICE = "https://api.paypal.com/v1/identity/openidconnect/tokenservice";
	private static final String URL_PROFILE = "https://api.sandbox.paypal.com/v1/identity/openidconnect/userinfo";
//	private static final String URL_PROFILE = "https://api.paypal.com/v1/identity/openidconnect/userinfo";

	private static final String SCHEMA = "openid";

	private static String valueClientId = null;
	private static String valueClientSecret = null;

	public static final String TOKEN_URL = URL_REDIRECT + "?scope";

	/**
	 * Not going to be exposed.
	 * 
	 * @param clientId
	 * @param clientSecret
	 */
	public AccessHelperConnect(final String clientId, final String clientSecret) {
		valueClientId = clientId;
		valueClientSecret = clientSecret;
	}

	/**
	 * Initializes an instance of AccessHelper and returns it.
	 * 
	 * @param clientId
	 * @param clientSecret
	 * @return the AccessHelper
	 */
	public static AccessHelperConnect init(final String clientId,
			final String clientSecret) {
		return new AccessHelperConnect(clientId, clientSecret);
	}

	/**
	 * Returns the application's authorization URL for PayPal Access.
	 * 
	 * @return the authorization URL as {@link String}
	 */
	public String getAuthUrl() {
		final StringBuilder authUrlBuilder = new StringBuilder();
		authUrlBuilder.append(URL_AUTHORIZE).append("?")
				.append(PARAM_CLIENT_ID).append(valueClientId).append("&")
				.append(PARAM_SCOPE).append(DATA_PROFILE).append("&")
				.append(PARAM_REDIRECT_URI).append(Uri.encode(URL_REDIRECT))
				.append("&").append(PARAM_RESPONSE_TYPE)
				.append(VALUE_RESPONSE_TYPE);
		Log.d(TAG, "AuthURL in AccessHelperConnect: " + authUrlBuilder.toString());
		return authUrlBuilder.toString();
	}

	/**
	 * Returns the Access Token url.
	 * 
	 * @return the Access Token url
	 */
	public String getTokenServiceUrl() {
		return URL_TOKENSERVICE;
	}

	/**
	 * Creates the needed parameters to get the Authorization Token.
	 * 
	 * @param code
	 *            the code from the Token Service
	 * @return the needed parameters
	 */
	public String getTokenServiceParameters(final String code) {
		final StringBuilder paramsBuilder = new StringBuilder();
		paramsBuilder.append(PARAM_CLIENT_ID).append(valueClientId).append("&")
				.append(PARAM_REDIRECT_URI).append(Uri.encode(URL_REDIRECT))
				.append("&").append(PARAM_GRANT_TYPE).append("&")
				.append(PARAM_CLIENT_SECRET).append(valueClientSecret)
				.append("&").append(PARAM_CODE).append(code);
		return paramsBuilder.toString();
	}

	/**
	 * Returns the URL for requesting profile information.
	 * 
	 * @param accessToken
	 * @return the profile url including the Access Token
	 */
	public String getProfileUrl(final String accessToken) {
		final StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(URL_PROFILE).append("?").append(PARAM_SCHEMA)
				.append(SCHEMA).append("&").append(PARAM_ACCESS_TOKEN)
				.append(accessToken);
		return urlBuilder.toString();
	}

	/**
	 * Returns the URL which can be converted to an URI to extract the access
	 * code
	 * 
	 * @return the callback URL
	 */
	public String getAccessCodeUrl() {
		return TOKEN_URL;
	}

	/**
	 * Returns the code parameter that can be used to check incoming URLs
	 * 
	 * @return the code parameter
	 */
	public String getCodeParameter() {
		return PARAM_CODE;
	}
}
