package team.cs6365.payfive.ui.customer;

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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import team.cs6365.payfive.util.AccessHelperConnect;

/**
 * ViewCartFragment shows items currently in the cart for the current customer.
 * Customers can go back to menu to shop or check out items in the cart.
 * 
 * @author Jin
 */
public class NewTransactionFragment extends Fragment implements OnClickListener {
	private static final String TAG = "~~PayFive~~ NewTransactionFragment";

	private Button btnLogin;
	private TextView tvProfile;

	private PayFive payfive;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_new_transaction, container,
				false);

		payfive = (PayFive) getActivity().getApplication();

		btnLogin = (Button) view.findViewById(R.id.btn_login);
		btnLogin.setOnClickListener(this);

		tvProfile = (TextView) view.findViewById(R.id.tv_profile);

		getActivity().setTitle("New Transaction");
		return view;

	}

	@Override
	public void onClick(View pressed) {

		switch (pressed.getId()) {

		case R.id.btn_login:
			callPaypalLogin();

			break;
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
			String output = data
					.getStringExtra(AccessHelperConnect.DATA_PROFILE);
			if (PayFive.DEBUG)
				Log.d(TAG, "Result data: " + output);

			// Set the raw json representation as content of the TextView
			tvProfile.setText(data
					.getStringExtra(AccessHelperConnect.DATA_PROFILE));
			
			//TODO: call a method to enable scan card / generate qr code buttons
			
		} else {
			Toast.makeText(getActivity().getApplicationContext(),
					R.string.toast_login_failed, Toast.LENGTH_LONG).show();
		}
	}
}