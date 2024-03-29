package team.cs6365.payfive.ui.transaction;

import android.widget.*;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PaymentActivity;
import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;
import org.json.JSONException;
import org.json.JSONObject;

import team.cs6365.payfive.PayFive;
import team.cs6365.payfive.R;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import team.cs6365.payfive.model.Item;
import team.cs6365.payfive.model.Serializer;
import team.cs6365.payfive.ui.main.LoginActivity;
import team.cs6365.payfive.ui.main.MainActivity;
import team.cs6365.payfive.util.AccessHelperConnect;

import java.math.BigDecimal;

import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

/**
 * ViewCartFragment shows items currently in the cart for the current customer.
 * Customers can go back to menu to shop or check out items in the cart.
 * 
 * @author Jin
 */
public class NewTransactionFragment extends Fragment implements OnClickListener {
	private static final String TAG = "~~PayFive~~ NewTransactionFragment";
	public static final String EXTRA_QR_INFO = "extra:qr:info";

	private RelativeLayout btnLogin;
	private TextView tvPaypal, tvPaypalUser;

	private PayFive payfive;

	private Button btnGenQR;
	private Button btnScanSendersCard;

	private EditText etDescription;
	private EditText etAmount;

	private static final String MY_CARDIO_APP_TOKEN = "5937a8aff29748b483b94279c2b690a9";
	private int REQUEST_CODE_SCAN = 3; // arbitrary int
	private int REQUEST_CODE_PAY_WITH_CARD = 4;

	private static final String CONFIG_ENVIRONMENT = PaymentActivity.ENVIRONMENT_SANDBOX;

	// note that these credentials will differ between live & sandbox
	// environments.
	private static final String CONFIG_CLIENT_ID = "AXMkqxCO-osYaQqqk3Q3IU1q9ZnDwywVuzCdr4eXKlBceyCdr6Q1mpKxMzeB";
	// when testing in sandbox, this is likely the -facilitator email address.
	private static final String CONFIG_RECEIVER_EMAIL = "ppsandbox6911@gmail.com";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_new_transaction, container,
				false);

		payfive = (PayFive) getActivity().getApplication();

		btnGenQR = (Button) view.findViewById(R.id.btn_gen_qr);
		btnScanSendersCard = (Button) view.findViewById(R.id.btn_scan);

		btnGenQR.setOnClickListener(this);
		btnScanSendersCard.setOnClickListener(this);

		btnLogin = (RelativeLayout) view.findViewById(R.id.rl_btn_paypal_login);
		btnLogin.setOnClickListener(this);

		tvPaypal = (TextView) view.findViewById(R.id.tv_paypal);
		tvPaypalUser = (TextView) view.findViewById(R.id.tv_paypal_user);

		etDescription = (EditText) view.findViewById(R.id.et_desc);
		etAmount = (EditText) view.findViewById(R.id.et_amt);

		getActivity().setTitle("New Transaction");
		updatePaypalUserText();

		Bundle bundle = getArguments();
		if (bundle == null)
			Log.d(TAG, "bundle is null");
		else {
			Log.d(TAG, "bundle is not null");
			Item item = (Item) Serializer.deserialize(bundle
					.getByteArray("ITEM"));
			if (item != null) {
				Log.d(TAG, "came from customerviewactivity");
				Log.d(TAG, "item name: " + item.getName());
				Log.d(TAG, "price: " + item.getPrice());
				etDescription.setText(item.getDescription());
				etAmount.setText(String.valueOf(item.getPrice()));
			} else {
				Log.d(TAG, "not from custiomer view activity");
			}
		}
		return view;

	}

	@Override
	public void onClick(View pressed) {

		switch (pressed.getId()) {

		case R.id.rl_btn_paypal_login:
			callPaypalLogin();
			break;

		case R.id.btn_gen_qr:

			if (MainActivity.paypalUserEmail.equals("")) {
				Toast.makeText(getActivity(), "Please login to PayPal first.",
						Toast.LENGTH_SHORT).show();
			} else if (etAmount.getText().toString().equals("")) {
				Toast.makeText(getActivity(), "Please specify the amount.",
						Toast.LENGTH_SHORT).show();
			} else if (etAmount.getText().toString().equals("")) {
				Toast.makeText(getActivity(), "Please fill out description.",
						Toast.LENGTH_SHORT).show();
			} else {

				Intent intent = new Intent(getActivity(),
						CreateQRCodeActivity.class);
				String[] strings = { etDescription.getText().toString(),
						etAmount.getText().toString(),
						MainActivity.paypalUserEmail,
						MainActivity.paypalUserName };
				intent.putExtra(EXTRA_QR_INFO, strings);
				startActivity(intent);
			}
			break;

		case R.id.btn_scan:
			onBuyPressed();
			break;

		}
	}

	public void onBuyPressed() {
		boolean clear = true;
		if (MainActivity.paypalUserEmail.equals("")) {
			clear = false;
			Toast.makeText(getActivity(), "Please login to PayPal first.",
					Toast.LENGTH_SHORT).show();
		} else if (etAmount.getText().toString().equals("")) {
			clear = false;
			Toast.makeText(getActivity(), "Please specify the amount.",
					Toast.LENGTH_SHORT).show();
		} else if (etAmount.getText().toString().equals("")) {
			clear = false;
			Toast.makeText(getActivity(), "Please fill out description.",
					Toast.LENGTH_SHORT).show();
		}

		if (clear) {
			PayPalPayment thingToBuy = new PayPalPayment(new BigDecimal(
					etAmount.getText().toString()), "USD", etDescription
					.getText().toString());

			Intent intent = new Intent(getActivity(), PaymentActivity.class);

			intent.putExtra(PaymentActivity.EXTRA_PAYPAL_ENVIRONMENT,
					CONFIG_ENVIRONMENT);
			intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, CONFIG_CLIENT_ID);
			intent.putExtra(PaymentActivity.EXTRA_RECEIVER_EMAIL,
					MainActivity.paypalUserEmail);

			// It's important to repeat the clientId here so that the SDK has it
			// if Android restarts your
			// app midway through the payment UI flow.
			intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, CONFIG_CLIENT_ID);
			intent.putExtra(PaymentActivity.EXTRA_PAYER_ID,
					MainActivity.paypalUserEmail);
			intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

			startActivityForResult(intent, 0);
		}
	}

	private void onScanCardPressed() {

		Intent scanIntent = new Intent(getActivity(), CardIOActivity.class);

		// required for authentication with card.io
		scanIntent
				.putExtra(CardIOActivity.EXTRA_APP_TOKEN, MY_CARDIO_APP_TOKEN);

		// customize these values to suit your needs.
		scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default:
																		// true
		scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false); // default:
																		// false
		scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default:
																				// false

		// hides the manual entry button
		// if set, developers should provide their own manual entry mechanism in
		// the app
		scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, false); // default:
																				// false

		// MY_SCAN_REQUEST_CODE is arbitrary and is only used within this
		// activity.
		startActivityForResult(scanIntent, REQUEST_CODE_SCAN);
	}

	/* call LoginActivity for Paypal login using web view */
	private void callPaypalLogin() {

		final Intent loginIntent = new Intent(getActivity(),
				LoginActivity.class);
		if (PayFive.DEBUG)
			Log.d(TAG, "Launching LoginActivity inside callPaypalLogin()");
		startActivityForResult(loginIntent, R.id.LOGIN_REQUEST);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (PayFive.DEBUG)
			Log.d(TAG, "onActivityResult - resultCode: " + resultCode);
		if (requestCode == R.id.LOGIN_REQUEST
				&& resultCode == getActivity().RESULT_OK) {
			Toast.makeText(getActivity().getApplicationContext(),
					R.string.toast_login_ok, Toast.LENGTH_LONG).show();
			String paypalUserResult = data
					.getStringExtra(AccessHelperConnect.DATA_PROFILE);
			MainActivity.isLoggedIn = true;
			JSONObject paypalUser = new JSONObject();
			try {
				paypalUser = new JSONObject(paypalUserResult);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			MainActivity.paypalUserEmail = paypalUser.optString("email");
			MainActivity.paypalUserName = paypalUser.optString("name");
			MainActivity.paypalUserPhone = paypalUser.optString("phone_number");

			// TODO: save these info in activity not in frag

			if (PayFive.DEBUG)
				Log.d(TAG, "Result data: " + paypalUserResult);

			// Set the raw json representation as content of the TextView

			updatePaypalUserText();

			// TODO: call a method to enable scan card / generate qr code
			// buttons

		} else if (requestCode == REQUEST_CODE_SCAN) {
			if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
				CreditCard scanResult = data
						.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
				Bundle bundle = new Bundle();
				bundle.putString(PayWithCreditCardActivity.EXTRAS_CARD_NUMBER,
						scanResult.cardNumber);
				bundle.putInt(PayWithCreditCardActivity.EXTRAS_EXP_MONTH,
						scanResult.expiryMonth);
				bundle.putInt(PayWithCreditCardActivity.EXTRAS_EXP_YEAR,
						scanResult.expiryYear);
				Intent intent = new Intent(getActivity(),
						PayWithCreditCardActivity.class);
				intent.putExtras(bundle);
				startActivityForResult(intent, REQUEST_CODE_PAY_WITH_CARD);
			}
		} else {
			Toast.makeText(getActivity().getApplicationContext(),
					R.string.toast_login_failed, Toast.LENGTH_LONG).show();
			tvPaypal.setText("Not logged into PayPal");
		}
	}

	/* update displayed text about login status and paypal user info */
	private void updatePaypalUserText() {

		if (MainActivity.isLoggedIn()) {
			tvPaypal.setText("Receiver PayPal:");

			StringBuilder sb = new StringBuilder("")
					.append(MainActivity.paypalUserName).append("    (")
					.append(MainActivity.paypalUserPhone).append(")\n")
					.append(MainActivity.paypalUserEmail);
			tvPaypalUser.setText(sb.toString());

		} else {
			tvPaypal.setText("Not logged into PayPal");
		}

	}
}