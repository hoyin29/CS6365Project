package team.cs6365.payfive.ui.customer;

import team.cs6365.payfive.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * ViewCartFragment shows items currently in the cart for the current customer.
 * Customers can go back to menu to shop or check out items in the cart.
 * 
 * @author Jin
 */
public class NewTransactionFragment extends Fragment {
	// public static final String ARG_PLANET_NUMBER = "planet_number";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.frag_new_transaction, container,
				false);

		getActivity().setTitle("New Transaction");
		return rootView;
	}
}