package team.cs6365.payfive.ui.customer;

import team.cs6365.payfive.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * MenuListFragment shows a list of available menu at the vendor.
 * 
 * @author Jin
 */
public class MenuListFragment extends Fragment {
	// public static final String ARG_PLANET_NUMBER = "planet_number";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.frag_menu_list, container,
				false);

		getActivity().setTitle("Menu List");
		return rootView;
	}
}