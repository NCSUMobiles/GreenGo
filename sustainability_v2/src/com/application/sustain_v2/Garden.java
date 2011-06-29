package com.application.sustain_v2;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.application.sustain.R;

public class Garden extends Activity {

	List<PieDetailsItem> PieData = new ArrayList<PieDetailsItem>(0);
	AnimationDrawable flower_Animation1;
	AnimationDrawable flower_Animation2;
	AnimationDrawable flower_Animation3;
	int i = 0;
	public static String deviceID = null;
	int[] number_images = new int[50];

	ImageButton viewChart;
	ImageButton Garden;
	ImageButton info;
	ImageButton home;
	TextView txt;
	sustainability_v2 sustain = new sustainability_v2();

	/** Called when the activity is first created. */
	public static int calculate_flowers() {
		sustainability_v2 obj = new sustainability_v2();
		obj.sum_smallDistance = 0;
		obj.sum_largeDistance = 0;
		obj.prev_points = 25;

		server_connection scon = new server_connection();
		scon.getPoints(deviceID);

		int points = (int) obj.calculate_points() + scon.points;
		Log.d("new_points", Integer.toString(points));
		int flowers_count = 0;
		for (int i = 50; i < 5000; i = i + 50) {
			if (points < 50) {
				flowers_count = 1;
				break;
			}
			if (points >= i && points <= (i + 50)) {
				//flowers_count = flowers_count + 1;
				break;
			} else {
				flowers_count = flowers_count + 1;
			}
		}

		Log.d("flower", Integer.toString(flowers_count));
		return flowers_count;

	}

	public void show_info() {
		Intent i = new Intent(this, Info.class);
		startActivity(i);
	}
	
	public void page_home() {
		Intent i = new Intent(this, sustainability_v2.class);
		startActivity(i);
	}

	public void chart_create() {
		Intent i = new Intent(this, ChartPage.class);
		Log.d("create", "create");
		startActivity(i);
		Log.d("start", "start");
	}

	public void total_chart_create() {
		Intent i = new Intent(this, activity_chart.class);
		Log.d("create", "create");
		startActivity(i);
		Log.d("start", "start");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.garden);
		deviceID = Secure.getString(getBaseContext().getContentResolver(),
				Secure.ANDROID_ID);

		// Get the TableLayout
		int rows_count;

		this.info = (ImageButton) this.findViewById(R.id.info);

		this.info.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				show_info();

			}
		});

		this.viewChart = (ImageButton) this.findViewById(R.id.create_chart);

		this.viewChart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("create", "button clicked");
				chart_create();
			}
		});

		this.Garden = (ImageButton) this.findViewById(R.id.Garden);
		Garden.setImageResource(R.drawable.gardenpressed);
		this.Garden.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("create", "button clicked");

			}
		});

		this.home = (ImageButton) this.findViewById(R.id.home);
        
		this.home.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("create", "button clicked");
				page_home();
			}
		});

		int flowers_count = calculate_flowers();
		if (flowers_count % 3 == 0) {
			rows_count = flowers_count / 3;
			Log.d("Rows",Integer.toString(rows_count));

		} else {
			rows_count = flowers_count / 3;
			rows_count = rows_count + 1;

		}
		Log.d("rows", Integer.toString(rows_count));
		if (rows_count == 1) {
			if (flowers_count % 3 == 0) {
				number_images[i] = 3;
			} else
				number_images[i] = flowers_count % 3;
		} else {
			i = 0;
			while (i < rows_count - 1) {
				number_images[i] = 3;
				Log.d("loop", Integer.toString(number_images[i]));
				++i;
			}
			Log.d("i", Integer.toString(i));
			flowers_count = calculate_flowers();
            if(flowers_count% 3==0)
            {
            	number_images[i]=3;
            }
            else
			number_images[i] = flowers_count % 3;
			Log.d("loop", Integer.toString(number_images[i]));
		}

		TableLayout tl = (TableLayout) findViewById(R.id.maintable);

		// Go through each item in the array
		for (int current = 0; current < rows_count; current++) {

			TableRow tr_text = new TableRow(this);

			tr_text.setId(300 + current);
			tr_text.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));

			TextView txt1 = new TextView(this);
			txt1.setId(400 + current);
			txt1.setText("");
			txt1.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT));
			tr_text.addView(txt1);

			TextView txt2 = new TextView(this);
			txt2.setId(400 + current);
			txt2.setText("");
			txt2.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT));
			tr_text.addView(txt2);

			TextView txt3 = new TextView(this);
			txt3.setId(400 + current);
			txt3.setText("");
			txt3.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT));
			tr_text.addView(txt3);

			tl.addView(tr_text, new TableLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT

			));

			// Create a TableRow and give it an ID
			TableRow tr = new TableRow(this);
			tr.setId(100 + current);
			Log.d("images", Integer.toString(number_images[current]));
			tr.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			if (number_images[current] == 3)
			// Create a TextView to house the name of the province
			{
				ImageView name = new ImageView(this);
				name.setId(200 + current);
				name.setImageResource(R.drawable.flower1);
				name.setLayoutParams(new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,
						0x03));

				tr.addView(name);

				// Create a TextView to house the value of the after-tax income
				ImageView image2 = new ImageView(this);
				image2.setId(200 + current);
				image2.setImageResource(R.drawable.flower1);
				image2.setLayoutParams(new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				tr.addView(image2);

				// Add the TableRow to the TableLayout
				ImageView image3 = new ImageView(this);
				image3.setId(200 + current);
				image3.setImageResource(R.drawable.flower1);
				image3.setLayoutParams(new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				tr.addView(image3);

			} else if (number_images[current] == 2) {
				ImageView name = new ImageView(this);
				name.setId(200 + current);

				// name.setBackgroundResource(R.drawable.frames);

				name.setImageResource(R.drawable.flower1);
				// flower_Animation1 = (AnimationDrawable) name.getBackground();
				// name.setLayoutParams(new LayoutParams(
				// LayoutParams.MATCH_PARENT,
				// LayoutParams.WRAP_CONTENT));
				name.bringToFront();
				tr.addView(name);

				ImageView image2 = new ImageView(this);
				image2.setId(200 + current + 1);

				// image2.setBackgroundResource(R.drawable.frames);
				image2.setImageResource(R.drawable.flower1);

				// flower_Animation2 = (AnimationDrawable)
				// image2.getBackground();
				image2.setLayoutParams(new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				tr.addView(image2);
				image2.bringToFront();

			} else if (number_images[current] == 1) {
				ImageView name = new ImageView(this);
				name.setId(200 + current);
				name.setImageResource(R.drawable.mflower);
				name.setLayoutParams(new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,
						0x03));
				tr.addView(name);

			}
			tl.addView(tr, new TableLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		}
		txt=(TextView)findViewById(R.id.text);  
		txt.setText("Flower count: " + flowers_count+"\n");
	}

}
