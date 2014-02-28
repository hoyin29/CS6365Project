package team.cs6365.payfive.ui.admin;

import java.util.ArrayList;
import java.util.List;

import team.cs6365.payfive.R;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Fragment that appears in the "content_frame", shows a planet
 * 
 * @author Jin
 */
public class EditMenuItemActivity extends Activity {
	// public static final String ARG_PLANET_NUMBER = "planet_number";
	private Spinner spinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frag_edit_menu_item);

		setTitle("Edit Menu Item");

		spinner = (Spinner) findViewById(R.id.sp_category);
		List list = new ArrayList();
		list.add("Appetizer");
		list.add("Sandwich");
		list.add("Snack");
		list.add("Drink");
		ArrayAdapter categoryAdapter = new ArrayAdapter(this.getBaseContext(),
				android.R.layout.simple_spinner_item, list);
		categoryAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(categoryAdapter);

	}
}