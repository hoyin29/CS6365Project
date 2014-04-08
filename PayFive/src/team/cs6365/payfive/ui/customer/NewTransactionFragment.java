package team.cs6365.payfive.ui.customer;

import android.widget.*;
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

import team.cs6365.payfive.util.AccessHelperConnect;

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
        if(MainActivity.isLoggedIn){
            tvPaypal.setText("Receiver PayPal:");
        }
        else{
		    tvPaypal.setText("Not logged into PayPal");
        }

        etDescription = (EditText) view.findViewById(R.id.et_desc);
        etAmount = (EditText) view.findViewById(R.id.et_amt);

        StringBuilder sb = new StringBuilder("").append(MainActivity.email).append("\n")
                .append(MainActivity.name).append("\n").append(MainActivity.phone);

        tvPaypalUser.setText(sb.toString());

		getActivity().setTitle("New Transaction");
		return view;

	}

	@Override
	public void onClick(View pressed) {

		switch (pressed.getId()) {

            case R.id.rl_btn_paypal_login:
                callPaypalLogin();
                break;
            case R.id.btn_gen_qr:
                Intent intent = new Intent(getActivity(), CreateQRCodeActivity.class);
                String[] strings = {etDescription.getText().toString(), etAmount.getText().toString(),MainActivity.email,MainActivity.name};
                intent.putExtra(EXTRA_QR_INFO, strings);
                startActivity(intent);
                break;
            case R.id.btn_scan:
                onScanCardPressed();
                break;

		}
	}

    private void onScanCardPressed(){

        Intent scanIntent = new Intent(getActivity(), CardIOActivity.class);

        // required for authentication with card.io
        scanIntent.putExtra(CardIOActivity.EXTRA_APP_TOKEN, MY_CARDIO_APP_TOKEN);

        // customize these values to suit your needs.
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: true
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false

        // hides the manual entry button
        // if set, developers should provide their own manual entry mechanism in the app
        scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, false); // default: false

        // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
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
            MainActivity.isLoggedIn=true;
			JSONObject paypalUser = new JSONObject();
			try {
				paypalUser = new JSONObject(paypalUserResult);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			MainActivity.email = paypalUser.optString("email");
			MainActivity.name = paypalUser.optString("name");
			MainActivity.phone = paypalUser.optString("phone_number");

			// TODO: save these info in activity not in frag

			if (PayFive.DEBUG)
				Log.d(TAG, "Result data: " + paypalUserResult);

			// Set the raw json representation as content of the TextView
			tvPaypal.setText("Receiver PayPal:");
			StringBuilder sb = new StringBuilder("").append(MainActivity.email).append("\n")
					.append(MainActivity.name).append("\n").append(MainActivity.phone);

			tvPaypalUser.setText(sb.toString());

			// TODO: call a method to enable scan card / generate qr code
			// buttons

		}
        else if(requestCode == REQUEST_CODE_SCAN){
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
                Bundle bundle = new Bundle();
                bundle.putString(PayWithCreditCardActivity.EXTRAS_CARD_NUMBER,scanResult.cardNumber);
                bundle.putInt(PayWithCreditCardActivity.EXTRAS_EXP_MONTH,scanResult.expiryMonth);
                bundle.putInt(PayWithCreditCardActivity.EXTRAS_EXP_YEAR,scanResult.expiryYear);
                Intent intent = new Intent(getActivity(), PayWithCreditCardActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent,REQUEST_CODE_PAY_WITH_CARD);
            }
        }
        else {
			Toast.makeText(getActivity().getApplicationContext(),
					R.string.toast_login_failed, Toast.LENGTH_LONG).show();
			tvPaypal.setText("Not logged into PayPal");
		}
	}
}