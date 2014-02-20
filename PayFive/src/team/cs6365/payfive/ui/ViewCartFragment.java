package team.cs6365.payfive.ui;

import team.cs6365.payfive.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Fragment that appears in the "content_frame", shows a planet
 */
public class ViewCartFragment extends Fragment {
	// public static final String ARG_PLANET_NUMBER = "planet_number";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.frag_view_cart, container,
				false);
		
		getActivity().setTitle("View Cart");
		return rootView;
	}
}