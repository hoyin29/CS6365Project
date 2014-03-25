package team.cs6365.payfive;

import android.app.Application;
import android.content.Context;

public class PayFive extends Application {

	private static Context context;

	/* Development */
	public static final boolean DEBUG = true;
	public static final String TAG = "~~PayFive~~ ";

	/* Paypal related constants */
	public static final String REDIRECT_URL = "http://m.naver.com";

	public static final int RESULT_OK = 200;

	public static final String CLIENT_ID = "AXMkqxCO-osYaQqqk3Q3IU1q9ZnDwywVuzCdr4eXKlBceyCdr6Q1mpKxMzeB";
	public static final String CLIENT_SECRET = "EHbkvBAp6lWLJLB1fr0lhDxBEBaFZX-Bms9TwIl3pa1th2tVMjfr5Pk48bor";

	public static final String ACCESS_DENIED = "access_denied";

	/* ~~~~ METHODS ~~~~ */
	public void onCreate() {
		super.onCreate();
		// set context
		PayFive.context = getApplicationContext();
	}

	/* method for global static context */
	public static Context getContext() {
		return PayFive.context;
	}

}
