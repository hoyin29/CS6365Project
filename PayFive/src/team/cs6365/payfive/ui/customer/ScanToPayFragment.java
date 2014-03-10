package team.cs6365.payfive.ui.customer;

import java.math.BigDecimal;

import org.json.JSONException;

import team.cs6365.payfive.R;
import team.cs6365.payfive.model.Transaction;
import team.cs6365.payfive.model.User;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/* PayPal library */
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
public class ScanToPayFragment extends Fragment implements OnClickListener {

	private static final String TAG = "PayFive! - ScanToPayFragment";
	private static final boolean DEBUG = true;

	private Transaction scanned;

	/* --------- PAYPAL STUFF --------- */
	// set to PaymentActivity.ENVIRONMENT_PRODUCTION to move real money.
	// set to PaymentActivity.ENVIRONMENT_SANDBOX to use your test credentials
	// from https://developer.paypal.com
	// set to PaymentActivity.ENVIRONMENT_NO_NETWORK to kick the tires without
	// communicating to PayPal's servers.
	private static final String CONFIG_ENVIRONMENT = PaymentActivity.ENVIRONMENT_SANDBOX;

	// note that these credentials will differ between live & sandbox
	// environments.
	private static final String CONFIG_CLIENT_ID = "AXMkqxCO-osYaQqqk3Q3IU1q9ZnDwywVuzCdr4eXKlBceyCdr6Q1mpKxMzeB";
	// when testing in sandbox, this is likely the -facilitator email address.
	private static final String CONFIG_RECEIVER_EMAIL = "ppsandbox6911@gmail.com";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_scan_to_pay, container,
				false);

		getActivity().setTitle("Scan to Pay");

		/* PayPal service */
		Intent intent = new Intent(getActivity(), PayPalService.class);

		intent.putExtra(PaymentActivity.EXTRA_PAYPAL_ENVIRONMENT,
				CONFIG_ENVIRONMENT);
		intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, CONFIG_CLIENT_ID);
		intent.putExtra(PaymentActivity.EXTRA_RECEIVER_EMAIL,
				CONFIG_RECEIVER_EMAIL);

		getActivity().startService(intent);

		// --------------------

		scanned = new Transaction();
		scanned.setRecipient(new User("Jin", "test@test.test"));
		scanned.setAmount(0.01);
		scanned.setDesc("Test description");

		/* VIEW ELEMENTS */
		Button btnConfirm = (Button) view.findViewById(R.id.btn_confirm);
		btnConfirm.setOnClickListener(this);

		return view;
	}

	/* with the necessary parameters, start Paypal activity */
	public void passToPaypal(Transaction t) {
		PayPalPayment thingToBuy = new PayPalPayment(new BigDecimal(
				String.valueOf(t.getAmount())), "USD", t.getRecipient()
				.getName());

		Intent intent = new Intent(getActivity(), PaymentActivity.class);

		intent.putExtra(PaymentActivity.EXTRA_PAYPAL_ENVIRONMENT,
				CONFIG_ENVIRONMENT);
		intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, CONFIG_CLIENT_ID);
		intent.putExtra(PaymentActivity.EXTRA_RECEIVER_EMAIL, t.getRecipient()
				.getPaypalId());

		// It's important to repeat the clientId here so that the SDK has it
		// if Android restarts your app midway through the payment UI flow.
		intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, CONFIG_CLIENT_ID);
		intent.putExtra(PaymentActivity.EXTRA_PAYER_ID, t.getRecipient()
				.getPaypalId());
		intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

		startActivityForResult(intent, 0);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			PaymentConfirmation confirm = data
					.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
			if (confirm != null) {
				try {
					Log.i("paymentExample", confirm.toJSONObject().toString(4));
					// TODO: need to test confirmation
					if (DEBUG)
						Log.d(TAG, "payment confirmation received");

					// TODO: send 'confirm' to your server for verification.
					// see
					// https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
					// for more details.

				} catch (JSONException e) {
					Log.e("paymentExample",
							"an extremely unlikely failure occurred: ", e);
				}
			}
		} else if (resultCode == Activity.RESULT_CANCELED) {
			Log.i("paymentExample", "The user canceled.");
		} else if (resultCode == PaymentActivity.RESULT_PAYMENT_INVALID) {
			Log.i("paymentExample",
					"An invalid payment was submitted. Please see the docs.");
		}
	}

	@Override
	public void onDestroy() {
		getActivity().stopService(
				new Intent(getActivity(), PayPalService.class));
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {

		Log.d(TAG, "ON CLICK");
		switch (v.getId()) {

		/* confirm and pay button on click */
		case R.id.btn_confirm:
			// EditText etReceiverEmail =
			// (EditText)findViewById(R.id.etReceiverEmail);
			// EditText etPaymentAmount = (EditText)
			// findViewById(R.id.etPaymentAmount);
			// EditText etDescription = (EditText)
			// findViewById(R.id.etDescription);
			passToPaypal(scanned);
			break;

		default:
			break;

		}
	}
}