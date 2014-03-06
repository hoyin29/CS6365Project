package team.cs6365.payfive.ui.admin;

import team.cs6365.payfive.R;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Fragment that appears in the "content_frame", shows a planet
 * 
 * @author Jin
 */
public class ManageMenuFragment extends Fragment {
	// public static final String ARG_PLANET_NUMBER = "planet_number";
	private FragmentManager fm;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.frag_manage_menu, container,
				false);

		getActivity().setTitle("Manage Menu");
		fm = getActivity().getSupportFragmentManager();

		Button btn = (Button) rootView.findViewById(R.id.btn_edit_menu_item);

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Button Clicked",
						Toast.LENGTH_SHORT).show();
				Intent in = new Intent(getActivity(),
						EditMenuItemActivity.class);
				startActivity(in);

			}
		});

		return rootView;
	}
}