package team.cs6365.payfive.model;

import java.text.DecimalFormat;

public class Formatter {

	public Formatter() {

	}

	public static String formatPrice(double p) {
		DecimalFormat df = new DecimalFormat("#.00");
		return df.format(p);
	}

	public static String formatName(String n) {
		if (n.length() > 15) {
			return n.substring(0, 12) + "...";
		}
		return n;
	}
}
