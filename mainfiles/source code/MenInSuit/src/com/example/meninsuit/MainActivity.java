package com.example.meninsuit;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	Button btn,rate,more,share,folder;
	InterstitialAd interstitial;
	com.google.android.gms.ads.AdRequest adRequest_banner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		folder=(Button) findViewById(R.id.button12);
		btn = (Button) findViewById(R.id.button1);
		rate = (Button) findViewById(R.id.button1_rate);
		more = (Button) findViewById(R.id.button1_more);
		share = (Button) findViewById(R.id.button1_share);
		
		AdView adView = (AdView) this.findViewById(R.id.adView);
		 adRequest_banner = new com.google.android.gms.ads.AdRequest.Builder().build(); 
		adView.loadAd(adRequest_banner);
		
		interstitial = new InterstitialAd(this);
	    interstitial.setAdUnitId(getResources().getString(R.string.adUnitId_intersitial));
//	    adRequest_banner = new com.google.android.gms.ads.AdRequest.Builder().build();
	     interstitial.loadAd(adRequest_banner);
	     
	     folder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				interstitial.show();
				Intent i = new Intent(MainActivity.this,
						FolderViewActivity.class);

				startActivity(i);
				 
			}
		});

		btn.setOnClickListener(new OnClickListener() {

			@SuppressLint("SdCardPath")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				interstitial.show();
				Intent i = new Intent(MainActivity.this, CaptureActivity.class);
				startActivity(i);
//				finish();

			}
		});
		
		
		rate.setOnClickListener(new OnClickListener() {

			 
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
				interstitial.show();
				final String appName = getPackageName();
				try {
				    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+appName)));
				} catch (android.content.ActivityNotFoundException anfe) {
				    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="+appName)));
				}
				
				 
			}
		});
		
		more.setOnClickListener(new OnClickListener() {

		 
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					interstitial.show();
				    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=UNITYANDROIDS")));
				} catch (android.content.ActivityNotFoundException anfe) {
				    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=UNITYANDROIDS")));
				}
			}
		});
		

		share.setOnClickListener(new OnClickListener() {

			@SuppressLint("SdCardPath")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				interstitial.show();
				String shareBody =  "http://play.google.com/store/apps/details?id=com.karma.photo_hordings";
			    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
			        sharingIntent.setType("text/plain");
			        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Man In Suit  : - ");
			        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
			        startActivity(Intent.createChooser(sharingIntent, shareBody));

			}
		});
		
	}

	 

}
