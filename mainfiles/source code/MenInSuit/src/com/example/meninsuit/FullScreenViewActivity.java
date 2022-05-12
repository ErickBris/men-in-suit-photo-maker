package com.example.meninsuit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

public class FullScreenViewActivity extends Activity {
	Bitmap bitmap;
	Global mGlobal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.layout_fullscreen_image);

		mGlobal = ((Global) getApplicationContext());
		bitmap = mGlobal.getBm1();
		
		ImageView iv = (ImageView) findViewById(R.id.imgDisplay);
//		if ( !getIntent().getExtras().get(Intent.EXTRA_STREAM).equals(null)) 
//		{
//			Uri uri = (Uri) getIntent().getExtras().get(Intent.EXTRA_STREAM);
//			Log.i("path", "++"+uri);
//				iv.setImageURI(uri);
//		}else {
//			
//			iv.setImageBitmap(bitmap);
//		}
	 
	
		iv.setImageBitmap(bitmap);

		Button btnShare, btnClose;

		btnClose = (Button) findViewById(R.id.btnClose);
		btnShare = (Button) findViewById(R.id.btnShare);
	 
		btnShare.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String pathofBmp = Images.Media.insertImage(
						getContentResolver(), bitmap, "title", null);
				Uri bmpUri = Uri.parse(pathofBmp);

				Intent sharingIntent = new Intent(Intent.ACTION_SEND);
				sharingIntent.setType("image/*");
				sharingIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
				startActivity(Intent.createChooser(sharingIntent,
						"Share image using"));
			}
		});
		// close button click event
		btnClose.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				FullScreenViewActivity.this.finish();
			}
		});

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finish();
		super.onBackPressed();
	}
}
