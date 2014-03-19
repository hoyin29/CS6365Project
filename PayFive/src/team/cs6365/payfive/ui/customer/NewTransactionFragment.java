package team.cs6365.payfive.ui.customer;

import android.widget.*;
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

    private EditText etDescription;
    private EditText etAmount;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_new_transaction, container,
				false);

		payfive = (PayFive) getActivity().getApplication();

        btnGenQR = (Button) view.findViewById(R.id.btn_gen_qr);
        btnGenQR.setOnClickListener(this);

		btnLogin = (RelativeLayout) view.findViewById(R.id.rl_btn_paypal_login);
		btnLogin.setOnClickListener(this);

		tvPaypal = (TextView) view.findViewById(R.id.tv_paypal);
		tvPaypalUser = (TextView) view.findViewById(R.id.tv_paypal_user);

		tvPaypal.setText("Not logged into PayPal");
		tvPaypalUser.setText("");

        etDescription = (EditText) view.findViewById(R.id.et_desc);
        etAmount = (EditText) view.findViewById(R.id.et_amt);

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
                String[] strings = {etDescription.getText().toString(), etAmount.getText().toString()};
                intent.putExtra(EXTRA_QR_INFO, strings);
                startActivity(intent);

		}
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

			JSONObject paypalUser = new JSONObject();
			try {
				paypalUser = new JSONObject(paypalUserResult);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String email = paypalUser.optString("email");
			String name = paypalUser.optString("name");
			String phone = paypalUser.optString("phone_number");

			// TODO: save these info in activity not in frag

			if (PayFive.DEBUG)
				Log.d(TAG, "Result data: " + paypalUserResult);

			// Set the raw json representation as content of the TextView
			tvPaypal.setText("Receiver PayPal:");
			StringBuilder sb = new StringBuilder("").append(email).append("\n")
					.append(name).append("\n").append(phone);

			tvPaypalUser.setText(sb.toString());

			// TODO: call a method to enable scan card / generate qr code
			// buttons

		} else {
			Toast.makeText(getActivity().getApplicationContext(),
					R.string.toast_login_failed, Toast.LENGTH_LONG).show();
			tvPaypal.setText("Not logged into PayPal");
		}
	}
}