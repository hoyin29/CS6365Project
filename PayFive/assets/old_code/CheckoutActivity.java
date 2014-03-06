package team.cs6365.payfive.ui.customer;

import team.cs6365.payfive.R;
import team.cs6365.payfive.R.layout;
import team.cs6365.payfive.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * CheckoutActivity receives information on the current item or items in cart
 * and processes checkout.
 * 
 * @author Jin
 */
public class CheckoutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.checkout, menu);
		return true;
	}

}
