package com.example.meninsuit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridViewActivity extends Activity {

	int[] gallery_grid_Images = { 
			 
			R.drawable.montaje24, R.drawable.montaje25, R.drawable.montaje26,
			R.drawable.montaje27, R.drawable.montaje28, R.drawable.montaje29,
			R.drawable.montaje30, R.drawable.montaje31, R.drawable.montaje32,
			R.drawable.d1, R.drawable.d2,
			R.drawable.d3, R.drawable.d4, R.drawable.d5,
			R.drawable.d6, R.drawable.d7, R.drawable.d8,
			R.drawable.d9, R.drawable.d10, R.drawable.d11,
			R.drawable.d12, R.drawable.d13, R.drawable.d14,
			R.drawable.d15, R.drawable.d16, R.drawable.d17,
			R.drawable.d18, R.drawable.d19, R.drawable.d20,
			R.drawable.d21, R.drawable.d22, R.drawable.d23,
			
			R.drawable.montaje33, R.drawable.montaje34, R.drawable.montaje35,
			R.drawable.montaje36, R.drawable.montaje37, R.drawable.montaje38,
			R.drawable.montaje39, R.drawable.montaje40, R.drawable.montaje41,
			R.drawable.montaje42, R.drawable.montaje43, R.drawable.montaje44,
			R.drawable.montaje45, R.drawable.montaje46, R.drawable.montaje47,
			R.drawable.montaje48, R.drawable.montaje49, R.drawable.montaje50,
			R.drawable.montaje51, R.drawable.montaje52, R.drawable.montaje53,
			R.drawable.montaje54, R.drawable.montaje55, R.drawable.montaje56,
			R.drawable.montaje57, R.drawable.montaje58, R.drawable.montaje59,
			R.drawable.montaje60, R.drawable.montaje61, R.drawable.montaje62,
			R.drawable.montaje63, R.drawable.montaje64, R.drawable.montaje65,
			R.drawable.montaje66, R.drawable.montaje67, };

	
	AsyncTaskLoadFiles myAsyncTaskLoadFiles;

	public class AsyncTaskLoadFiles extends AsyncTask<Void, String, Void> {

		
		ImageAdapter myTaskAdapter;

		public AsyncTaskLoadFiles(ImageAdapter adapter) {
			myTaskAdapter = adapter;
		}

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {

			return null;
		}

		
		@Override
		protected void onPostExecute(Void result) {
			myTaskAdapter.notifyDataSetChanged();
			super.onPostExecute(result);
		}

	}

	public class ImageAdapter extends BaseAdapter {

		private Context mContext;

		public ImageAdapter(Context c) {
			mContext = c;
		}

		@Override
		public int getCount() {
			return gallery_grid_Images.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return gallery_grid_Images[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView;
			if (convertView == null) { // if it's not recycled, initialize some
										// attributes
				imageView = new ImageView(mContext);
				imageView.setLayoutParams(new GridView.LayoutParams(220, 220));
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setPadding(8, 8, 8, 8);
				
			} else {
				imageView = (ImageView) convertView;
			}

			imageView.setImageResource(gallery_grid_Images[position]);
			return imageView;
		}

		
	}

	ImageAdapter myImageAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grid_view);

		final GridView gridview = (GridView) findViewById(R.id.grid_view);
		myImageAdapter = new ImageAdapter(this);
		gridview.setAdapter(myImageAdapter);

		gridview.setOnItemClickListener(myOnItemClickListener);

	}

	OnItemClickListener myOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			
			Bundle conData = new Bundle();
		    conData.putString("param_result", ""+position);
		    Intent intent = new Intent();
		    intent.putExtras(conData);
		    setResult(RESULT_OK, intent);
		    finish();
			
			
		/*	Intent i = new Intent(GridViewActivity.this,
					FullScreenViewActivity.class);
			i.putExtra("position", position);
			startActivity(i);*/

		}
	};

}
