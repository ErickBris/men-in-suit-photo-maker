package com.example.meninsuit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class FolderViewImageAdapter extends BaseAdapter {

	private Activity _activity;
	private int imageWidth;
	Global mGlobal;

	ArrayList<Bitmap> bmpArray = new ArrayList<Bitmap>();
	ArrayList<String> fileName = new ArrayList<String>();

	public FolderViewImageAdapter(FolderViewActivity activity,
			ArrayList<Bitmap> bmpArray, ArrayList<String> fileName) {
		// TODO Auto-generated constructor stub
		this._activity = activity;
		this.bmpArray = bmpArray;
		this.fileName = fileName;
		mGlobal = ((Global) _activity.getApplicationContext());
	}

	@Override
	public int getCount() {
		return this.bmpArray.size();
	}

	@Override
	public Object getItem(int position) {
		return this.bmpArray.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) {
			imageView = new ImageView(_activity);
			imageView.setLayoutParams(new GridView.LayoutParams(220, 220));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(8, 8, 8, 8);
			
			
		} else {
			imageView = (ImageView) convertView;
		}

		// get screen dimensions

		
	/*	imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setLayoutParams(new GridView.LayoutParams(LayoutParams.MATCH_PARENT,
				500));
	*/	
		
		imageView.setImageBitmap(bmpArray.get(position));
		
	//	new Async_Image(imageView, Uri).execute();

		// image view click listener
		imageView.setOnClickListener(new OnImageClickListener(position));

		return imageView;
	}

	class OnImageClickListener implements OnClickListener {

		int _postion;

		// constructor
		public OnImageClickListener(int position) {
			this._postion = position;
		}

		@Override
		public void onClick(View v) {
			
			
			mGlobal.setBm1(bmpArray.get(_postion));
			
			Log.i("clicked Img ... ",""+_postion);
			Intent i = new Intent(_activity, FullScreenViewActivity.class);
			_activity.startActivity(i);
			
			// on selecting grid view image
			// launch full screen activity

		}

	}

	/*
	 * Resizing image size
	 */
	public static Bitmap decodeFile(String filePath, int WIDTH, int HIGHT) {
		try {

			File f = new File(filePath);

			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			final int REQUIRED_WIDTH = WIDTH;
			final int REQUIRED_HIGHT = HIGHT;
			int scale = 1;

			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	class Async_Image extends AsyncTask<Object, String, String> {
		ImageView img;
		int iId;
		private String path = null;

		public Async_Image(ImageView img, int image_id) {
			// TODO Auto-generated constructor stub
			this.img = img;
			iId = image_id;
			// this.path = img.getTag().toString();
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

	//		img.setImageBitmap(bm);

		}

		@Override
		protected String doInBackground(Object... params) {
			// TODO Auto-generated method stub
			return null;
		}

	}

}
