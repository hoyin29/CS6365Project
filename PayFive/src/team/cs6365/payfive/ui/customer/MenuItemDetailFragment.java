package team.cs6365.payfive.ui.customer;

import team.cs6365.payfive.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * MenuItemFragment shows detail (name, price, category, description, picture,
 * options, etc.) on a menu item selected from the menu list.
 * 
 * @author Jin
 */
public class MenuItemDetailFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.frag_menu_item_detail, container,
				false);

		getActivity().setTitle("Manage Menu");
		return rootView;
	}
}