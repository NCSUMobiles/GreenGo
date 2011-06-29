package com.application.sustain_v2;

import com.application.sustain.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class ChartPage extends Activity{

	private Button view_daily_Chart;
	private Button total_distance;
	ImageButton info;
	ImageButton viewChart;
	ImageButton Garden;
	ImageButton home;
	
	public void page_home() {
		Intent i = new Intent(this, sustainability_v2.class);
		startActivity(i);
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chartpage);
		
		
		this.info = (ImageButton) this.findViewById(R.id.info);
		this.info.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				show_info();

			}
		});

		this.viewChart = (ImageButton) this.findViewById(R.id.create_chart);
		viewChart.setImageResource(R.drawable.icondatapressed);
		this.viewChart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			    viewChart.setImageResource(R.drawable.icondatapressed);
			}
		});

		

		this.Garden = (ImageButton) this.findViewById(R.id.Garden);

		this.Garden.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("create", "button clicked");
				show_garden();
			}
		});
		
		this.view_daily_Chart = (Button) this.findViewById(R.id.create_daily_chart);

		this.view_daily_Chart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("create", "button clicked");
				chart_create();
			}
		});

		this.total_distance = (Button) this
				.findViewById(R.id.create_total_chart);

		this.total_distance.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("create", "button clicked");
				total_chart_create();
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
		
	}
	
	public void chart_create() {
		Intent i = new Intent(this, MainActivity.class);
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
	public void show_info() {
		Intent i = new Intent(this, Info.class);
		startActivity(i);
	}

	public void show_garden() {
		Intent i = new Intent(this, Garden.class);
		Log.d("create", "create");
		startActivity(i);
		Log.d("start", "start");
	}

	}

