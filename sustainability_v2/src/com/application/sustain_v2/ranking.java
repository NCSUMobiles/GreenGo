package com.application.sustain_v2;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

import com.application.sustain.R;

public class ranking extends Activity {
	TextView txt;
	public String value = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.rank);
		txt = (TextView) this.findViewById(R.id.txt);
		rank_calc obj = new rank_calc();
		String deviceID;
		deviceID = Secure.getString(getBaseContext().getContentResolver(),
				Secure.ANDROID_ID);
		obj.getRank(deviceID);
		if (isNetworkAvailable()) {
			if (obj.rank == null) {
				txt.setText("Sorry!!"
						+ "\n"
						+ "Unable to connect to the server due to technical reasons!");
				value = "Sorry Unable to connect to the server due to technical reasons!";
			} else {
				txt.setText("Your rank is" + "\n\n" + obj.rank + " among "
						+ obj.count + "\n\n" + "users");
				value = "Your rank is" + "\n\n" + obj.rank + " among "
						+ obj.count + "\n\n" + "users";
			}
		} else {
			txt.setText("There is a problem with the network connection!");
			value = "There is a problem with the network connection!";
		}

	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		if (activeNetworkInfo != null)
			return true;
		else
			return false;
	}
}
