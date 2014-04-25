package team.cs6365.payfive.ui.main;

import team.cs6365.payfive.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * AboutFragment shows developer and version information
 * 
 * @author Jin
 */
public class AboutFragment extends Fragment {
	// public static final String ARG_PLANET_NUMBER = "planet_number";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.frag_about, container, false);

		getActivity().setTitle("About");
		return rootView;
	}
}