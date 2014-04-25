package team.cs6365.payfive.ui.transaction;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import team.cs6365.payfive.R;

/**
 * Created by Sehoon on 4/7/14.
 */
public class PayWithCreditCardActivity extends Activity {

	private TextView txtCardNumber;
	private TextView txtExpDate;
	private EditText etCvv;
	private EditText etFirstName;
	private EditText etLastName;

	private Button btnPay;

	final public static String EXTRAS_CARD_NUMBER = "cardnumber";
	final public static String EXTRAS_EXP_MONTH = "expmonth";
	final public static String EXTRAS_EXP_YEAR = "expyear";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay_with_credit_card);
		txtCardNumber = (TextView) findViewById(R.id.txtCardNumber);
		txtExpDate = (TextView) findViewById(R.id.txtExpiryDate);
		etCvv = (EditText) findViewById(R.id.et_cvv);
		etFirstName = (EditText) findViewById(R.id.et_firstname);
		etLastName = (EditText) findViewById(R.id.et_lastname);
		btnPay = (Button) findViewById(R.id.btn_pay);

		Bundle bundle = getIntent().getExtras();
		txtCardNumber.setText(bundle.getString(EXTRAS_CARD_NUMBER));
		int expMonth = bundle.getInt(EXTRAS_EXP_MONTH);
		int expyear = bundle.getInt(EXTRAS_EXP_YEAR);
		txtExpDate.setText(expMonth + "/" + expyear);

		btnPay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Implement pay with card with PayPal API
			}
		});
	}
}
