package com.nicknackhacks.dailyburn.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nicknackhacks.dailyburn.R;
import com.nicknackhacks.dailyburn.model.BodyLogEntry;
import com.nicknackhacks.dailyburn.model.FoodLogEntry;

public class BodyEntryWrapper {
	TextView value;
	TextView loggedOn;
	TextView delta;
	View row;

	public BodyEntryWrapper(View row) {
		this.row = row;
	}

	public void populateFrom(BodyLogEntry e) {
		getValue().setText(String.valueOf(e.getValue()));
		getLoggedOn().setText(e.getLoggedOn());
		getDelta().setText("+0");
	}

	public TextView getValue() {
		if (value == null) {
			value = (TextView) row.findViewById(R.id.entry_value);
		}
		return value;
	}

	public TextView getLoggedOn() {
		if (loggedOn == null) {
			loggedOn = (TextView) row.findViewById(R.id.entry_loggedOn);
		}
		return loggedOn;
	}

	public TextView getDelta() {
		if (delta == null) {
			delta = (TextView) row.findViewById(R.id.entry_delta);
		}
		return delta;
	}
}
