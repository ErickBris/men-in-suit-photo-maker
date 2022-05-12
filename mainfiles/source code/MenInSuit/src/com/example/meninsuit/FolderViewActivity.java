package com.example.meninsuit;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;

public class FolderViewActivity extends Activity {
	File[] mediaFiles;
	File imageDir;
	static GridView gridView;
	FolderViewImageAdapter adapter;
	Intent in;
	ImageButton btncam;
	private Utils utils;

	private int columnWidth;
	String name = null;
	ArrayList<Bitmap> bmpArray = new ArrayList<Bitmap>();
	ArrayList<String> fileName = new ArrayList<String>();
	public static final String TAG = "Album3Activity";

	public void onCreate(Bundle savedInstanceState) {
		imageDir = new File(Environment.getExternalStorageDirectory()
				.toString() + "/MenInDemo");
		utils = new Utils(this);

		super.onCreate(savedInstanceState);
		if ((imageDir.exists())) {
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.activity_grid_view);
			mediaFiles = imageDir.listFiles();
			for (File file : mediaFiles) {
				bmpArray.add(convertToBitmap(file));
				fileName.add(readFileName(file));

			}

			adapter = new FolderViewImageAdapter(this, bmpArray, fileName);
			gridView = (GridView) findViewById(R.id.grid_view);

			InitilizeGridLayout();

			gridView.setAdapter(adapter);

			gridView.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {

				}
			});
		}
	}// onCreate

	public static Bitmap convertToBitmap(File file) {
		URL url = null;
		try {
			url = file.toURL();
		} catch (MalformedURLException e1) {
			// Log.d(TAG, e1.toString());
		}// catch

		Bitmap bmp = null;
		try {
			bmp = BitmapFactory.decodeStream(url.openStream());
			// bmp.recycle();
		} catch (Exception e) {
			// Log.d(TAG, "Exception: "+e.toString());
		}// catch
		return bmp;
	}// convertToBitmap

	private void InitilizeGridLayout() {
		Resources r = getResources();
		float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				AppConstant.GRID_PADDING, r.getDisplayMetrics());

		columnWidth = (int) ((utils.getScreenWidth() - ((AppConstant.NUM_OF_COLUMNS + 1) * padding)) / AppConstant.NUM_OF_COLUMNS);

		gridView.setNumColumns(AppConstant.NUM_OF_COLUMNS);
		gridView.setColumnWidth(columnWidth);
		gridView.setStretchMode(GridView.NO_STRETCH);
		gridView.setPadding((int) padding, (int) padding, (int) padding,
				(int) padding);
		gridView.setHorizontalSpacing((int) padding);
		gridView.setVerticalSpacing((int) padding);
	}

	public String readFileName(File file) {
		String name = file.getName();
		return name;
	}// readFileName
}// class