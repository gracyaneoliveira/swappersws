package br.edu.ifce.util;

import java.util.Calendar;

public class DateUtil {

	public static String getInitDate() {

		StringBuilder date = new StringBuilder();
		Calendar c = Calendar.getInstance();
		c.get(Calendar.DAY_OF_MONTH);
		
		//date.append("'");
		date.append(c.get(Calendar.YEAR));
		date.append("-");

		if (c.get(Calendar.MONTH) + 1 < 9) {
			date.append("0");
		}
		date.append(c.get(Calendar.MONTH) + 1);
		date.append("-");
		date.append("01");
		date.append(" ");
		date.append("00:00:01");
		//date.append("'");
		return date.toString();

	}

	public static String getEndDate() {

		StringBuilder date = new StringBuilder();
		Calendar c = Calendar.getInstance();
		c.get(Calendar.DAY_OF_MONTH);

		//date.append("'");
		date.append(c.get(Calendar.YEAR));
		date.append("-");

		if (c.get(Calendar.MONTH) + 1 < 9) {
			date.append("0");
		}

		date.append(c.get(Calendar.MONTH) + 1);
		date.append("-");
		date.append(c.get(Calendar.DAY_OF_MONTH));
		date.append(" ");
		date.append("23:59:59");
		//date.append("'");
		return date.toString();
	}

}
