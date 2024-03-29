package team.cs6365.payfive.ui.transaction;

import java.math.BigDecimal;

import android.widget.TextView;
import org.json.JSONException;

import team.cs6365.payfive.PayFive;
import team.cs6365.payfive.R;
import team.cs6365.payfive.database.TransactionDataSource;
import team.cs6365.payfive.model.Formatter;
import team.cs6365.payfive.model.Transaction;
import team.cs6365.payfive.model.User;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
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

	private PayFive payfive;

	private static final String TAG = "~~~PayFive~~~ ## ScanToPayFragment ## ";

	private Transaction scanned;
	private Boolean isScanned = false;

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

	public static final int REQUEST_CODE_SCANBARCODE = 1;
	public static final String SCANBARCODE_RESULT = "barcode_result";

	private TextView txtAmount, txtName, txtDescription;
	private TextView tvScanStatus;

	private String amount = "";
	private String email = "";
	private String name = "";
	private String description = "";

	private Button btnConfirm;

	private Activity act;

	// private Transaction t;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_scan_to_pay, container,
				false);

		act = getActivity();
		payfive = (PayFive) act.getApplication();

		act.setTitle("Scan to Pay");
		setHasOptionsMenu(true);

		/* PayPal service */
		Intent intent = new Intent(getActivity(), PayPalService.class);

		intent.putExtra(PaymentActivity.EXTRA_PAYPAL_ENVIRONMENT,
				CONFIG_ENVIRONMENT);
		intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, CONFIG_CLIENT_ID);
		intent.putExtra(PaymentActivity.EXTRA_RECEIVER_EMAIL,
				CONFIG_RECEIVER_EMAIL);

		act.startService(intent);

		// --------------------

		scanned = new Transaction();
		/*
		scanned.setRecipient(new User("Jin", "test@test.test"));
		scanned.setAmount(0.01);
		scanned.setDesc("Test description");
		*/
		
		/* VIEW ELEMENTS */
		btnConfirm = (Button) view.findViewById(R.id.btn_confirm);
		btnConfirm.setOnClickListener(this);

		txtAmount = (TextView) view.findViewById(R.id.txtAmount);
		txtName = (TextView) view.findViewById(R.id.txtName);
		txtDescription = (TextView) view.findViewById(R.id.txtDescription);
		tvScanStatus = (TextView) view.findViewById(R.id.tv_scan_status);

		tvScanStatus
				.setText("Scan QR code by clicking the icon on the top right");

		// create an item based on the scanned
		/*
		 * List<Item> l = new ArrayList<Item>(); Item i = new Item();
		 * i.setPrice(scanned.getAmount()); i.setDescription(scanned.getDesc());
		 * l.add(i); t = new Transaction(0, l, scanned.getRecipient(),
		 * scanned.getSender(), scanned.getAmount(), "", scanned.getDesc(),
		 * true);
		 */
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
		if (requestCode == REQUEST_CODE_SCANBARCODE) {
			if (resultCode == Activity.RESULT_OK) {
				isScanned = true; // set the flag to true to enable confirm
									// button
				String result = data.getStringExtra(SCANBARCODE_RESULT);
				String[] strings = result.split(";");
				amount = strings[0];
				email = strings[1];
				name = strings[2];
				for (int i = 3; i < strings.length; i++) {
					description += strings[i];
				}
				txtName.setText(name);
				txtAmount.setText(amount);
				txtDescription.setText(description);
				tvScanStatus.setText("Transaction Info");
				
				// transaction from qr code
				scanned.setRecipient(new User(name, email));
				scanned.setSender(new User());
				scanned.setSendType(true);
				scanned.setAmount(Double.valueOf(Formatter.formatPrice(Double.valueOf(amount))));
				scanned.setDesc(description);
				
				Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT)
						.show();
			}
			if (resultCode == Activity.RESULT_CANCELED) {
				// Write your code if there's no result
			}
		} else {
			if (resultCode == Activity.RESULT_OK) {
				PaymentConfirmation confirm = data
						.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
				if (confirm != null) {
					try {
						Log.i("paymentExample", confirm.toJSONObject()
								.toString(4));
						// TODO: need to test confirmation
						if (payfive.DEBUG)
							Log.d(TAG, "payment confirmation received");

						// TODO: send 'confirm' to your server for verification.
						// see
						// https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
						// for more details.

						// add transaction to db for history
						Log.d(TAG,
								"adding successful transaction to history db");
						TransactionDataSource tds = new TransactionDataSource(
								act);
						tds.open();
						tds.addTransaction(scanned);
						tds.close();

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

			if (isScanned) {
				passToPaypal(scanned);
			} else {
				Toast.makeText(
						getActivity(),
						"There is no scanned transaction. Please scan a transaction using QR icon.",
						Toast.LENGTH_LONG).show();
			}
			break;

		default:
			break;

		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.scan_to_pay_frag, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// // The action bar home/up action should open or close the drawer.
		// // ActionBarDrawerToggle will take care of this.
		// if (mDrawerToggle.onOptionsItemSelected(item)) {
		// return true;
		// }
		// Handle action buttons
		switch (item.getItemId()) {
		case R.id.action_scan_qr:
			// action bar icon scan qr is clicked
			Toast.makeText(getActivity(), "Scan QR code", Toast.LENGTH_SHORT)
					.show();
			// TODO: Here make an intent to scan qr activity
			// and launch activity for result
			Intent intent = new Intent(getActivity(), ScanBarCodeActivity.class);
			startActivityForResult(intent, REQUEST_CODE_SCANBARCODE);

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

}