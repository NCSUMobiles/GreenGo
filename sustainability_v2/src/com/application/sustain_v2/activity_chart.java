package com.application.sustain_v2;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.sustain_v2.View_PieChart;
import com.application.sustain_v2.PieDetailsItem;
import com.application.sustain_v2.sustainability_v2;
import com.application.sustain.R;

public class activity_chart extends Activity {

	List<PieDetailsItem> PieData = new ArrayList<PieDetailsItem>(0);
	TextView txt;
	/** Called when the activity is first created. */
	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		if (activeNetworkInfo != null)
			return true;
		else
			return false;
	}
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.d("Sub","On-Create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart);
        
        sustainability_v2 obj=new sustainability_v2();
        obj.sum_smallDistance=0;
        obj.sum_largeDistance=0;
        obj.prev_points=25;
        obj.calculate_Distance();
        
        rank_calc obj_rank = new rank_calc();
        txt=(TextView)findViewById(R.id.value);        
        String deviceID;
		deviceID = Secure.getString(getBaseContext().getContentResolver(),
				Secure.ANDROID_ID);
		obj_rank.getRank(deviceID);
		if (isNetworkAvailable()) {
			if (obj_rank.rank == null) {
				txt.setText("\t Sorry,Server problem");				
			} else {
				txt.setText(" Ranked " + obj_rank.rank + " among "+ obj_rank.count + " users");				
			}
		} else
		{
			txt.setText("\t Sorry,No network coverage!");			
		}
		AnimationSet c = (AnimationSet) AnimationUtils.loadAnimation(this, R.layout.flash);
	       
        c.setRepeatMode(Animation.REVERSE);
        c.setRepeatCount(Animation.INFINITE);
		txt.clearAnimation();
        txt.startAnimation(c);
        
        server_connection scon=new server_connection();
     
        scon.getDistance(deviceID);
        Log.d("Walking",Integer.toString(scon.walking_distance));
        Log.d("Other",Integer.toString(scon.mv_distance));
        
        //------------------------------------------------------------------------------------------
        // Used vars declaration
        //------------------------------------------------------------------------------------------
        PieDetailsItem Item;
        Random mNumGen  = new Random();
        int MaxPieItems = mNumGen.nextInt(20);
        Log.d("Sub",Integer.toString(MaxPieItems));
        int MaxCount  = 0;
        int ItemCount = 0;
        String line;
        String dist_walk_FileName = "smallDistance";
        String storageDir = "/mnt/sdcard/Android/data";
        File dist_walk_File = new File(storageDir, dist_walk_FileName);
        
		try {
		BufferedReader	br = new BufferedReader(new FileReader(dist_walk_File));
		
			while ((line = br.readLine()) != null) {
				ItemCount+=Integer.parseInt(line);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		 catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
            
        	ItemCount  = (int)sustainability_v2.sum_smallDistance + scon.walking_distance;
        	Log.d("small",Integer.toString(ItemCount));
        	Item       = new PieDetailsItem();
        	Item.Count = ItemCount;
        	Item.Label = "Valeur " + mNumGen.nextInt(1000);
        	Item.Color = 0xff33CC33;
        	PieData.add(Item);
        	MaxCount += ItemCount;
        	ItemCount  = (int)sustainability_v2.sum_largeDistance + scon.mv_distance;
        	Item       = new PieDetailsItem();
        	Item.Count = ItemCount;
        	Log.d("large",Integer.toString(ItemCount));
        	Item.Label = "Valeur " + mNumGen.nextInt(1000);
        	Item.Color = 0xffFF3333;
        	PieData.add(Item);
        	MaxCount += ItemCount;
        	Log.d("Count-Item",Integer.toString(MaxCount));
    
        	
        	
        	
        //------------------------------------------------------------------------------------------
        // Size => Pie size
        //------------------------------------------------------------------------------------------
        int Size = 400;
        //------------------------------------------------------------------------------------------
        // BgColor  => The background Pie Color
        //------------------------------------------------------------------------------------------
        int BgColor = 0xff336699;
        //------------------------------------------------------------------------------------------
        // mBackgroundImage  => Temporary image will be drawn with the content of pie view
        //------------------------------------------------------------------------------------------
        Bitmap mBackgroundImage = Bitmap.createBitmap(Size, Size, Bitmap.Config.RGB_565);
        //------------------------------------------------------------------------------------------
        // Generating Pie view
        //------------------------------------------------------------------------------------------
        View_PieChart PieChartView = new View_PieChart( this );
        PieChartView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        PieChartView.setGeometry(Size, Size, 2, 2, 2, 2);
        PieChartView.setSkinParams(BgColor);
        PieChartView.setData(PieData, MaxCount);
        PieChartView.invalidate();
        //------------------------------------------------------------------------------------------
        // Draw Pie Vien on Bitmap canvas
        //------------------------------------------------------------------------------------------
        PieChartView.draw(new Canvas(mBackgroundImage));
        PieChartView = null;
        //------------------------------------------------------------------------------------------
        // Create a new ImageView to add to main layout
        //------------------------------------------------------------------------------------------
        ImageView mImageView = new ImageView(this);
	    mImageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	    mImageView.setBackgroundColor(BgColor);
	    mImageView.setImageBitmap( mBackgroundImage );
	    //------------------------------------------------------------------------------------------
        // Finaly add Image View to target view !!!
        //------------------------------------------------------------------------------------------
	    
        LinearLayout TargetPieView =  (LinearLayout) findViewById(R.id.pie_container);
	    TargetPieView.addView(mImageView);
	    
    }
}
