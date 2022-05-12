package com.example.meninsuit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

@SuppressLint("NewApi")
public class CaptureActivity extends Activity implements SurfaceHolder.Callback {

	Camera camera;
	SurfaceView surfaceView;
	SurfaceHolder surfaceHolder;

	ProgressDialog progress;

	ImageButton mFill_button;
	ImageButton mRotate_button;
	ImageButton mHelp_button;
	ImageButton mInfo_button;
	ImageButton mFroze_button;
	ImageButton mCapture_button;
	ImageButton mShare_but;

	Boolean isShowIcon = true;
	Boolean isPause = false;
	Boolean isCapture = false;
	RelativeLayout mCapture_id_rl;

	int Rotation = 90;

	Handler handler;

	File file;
	static ViewFlipper viewFlipper;
	PictureCallback jpegCallback;
	public Bitmap bitmaptemp = null;

	int[] gallery_grid_Images = { 
//			R.drawable.montaje1, R.drawable.montaje2,
//			R.drawable.montaje3, R.drawable.montaje4, R.drawable.montaje5,
//			R.drawable.montaje6, R.drawable.montaje7, R.drawable.montaje8,
//			R.drawable.montaje9, R.drawable.montaje10, R.drawable.montaje11,
//			R.drawable.montaje12, R.drawable.montaje13, R.drawable.montaje14,
//			R.drawable.montaje15, R.drawable.montaje16, R.drawable.montaje17,
//			R.drawable.montaje18, R.drawable.montaje19, R.drawable.montaje20,
//			R.drawable.montaje21, R.drawable.montaje22, R.drawable.montaje23,
			
			 
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

	private final GestureDetector detector = new GestureDetector(
			new MyGestureDetector());

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_capture);

		mFill_button = (ImageButton) findViewById(R.id.fill_button);
		mRotate_button = (ImageButton) findViewById(R.id.rotate_button);
		mHelp_button = (ImageButton) findViewById(R.id.help_button);
		mInfo_button = (ImageButton) findViewById(R.id.info_button);
		mFroze_button = (ImageButton) findViewById(R.id.froze_button);
		mCapture_button = (ImageButton) findViewById(R.id.capture_button);
		mShare_but = (ImageButton) findViewById(R.id.share_but);
		mCapture_id_rl = (RelativeLayout) findViewById(R.id.capture_id_rl);

		progress = new ProgressDialog(CaptureActivity.this);

		progress.setMessage("Please wait...");
		progress.setTitle("");

		viewFlipper = (ViewFlipper) findViewById(R.id.flipper);

		viewFlipper.setBackgroundColor(Color.TRANSPARENT);

		viewFlipper.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				CaptureActivity.this.detector.onTouchEvent(arg1);
				return true;
			}
		});
		for (int k = 0; k < gallery_grid_Images.length; k++) {
			setFlipperImage(this.gallery_grid_Images[k]);
		}

		mShare_but.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent i = new Intent(CaptureActivity.this,
						FolderViewActivity.class);

				startActivity(i);

			}
		});

		mFill_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isShowIcon) {
					isShowIcon = false;
					mRotate_button.setVisibility(ImageButton.INVISIBLE);
					mHelp_button.setVisibility(ImageButton.INVISIBLE);
					mInfo_button.setVisibility(ImageButton.INVISIBLE);
					mFroze_button.setVisibility(ImageButton.INVISIBLE);
					mShare_but.setVisibility(ImageButton.INVISIBLE);
					mFill_button.setImageResource(R.drawable.size2);

				} else {
					isShowIcon = true;
					mRotate_button.setVisibility(ImageButton.VISIBLE);
					mHelp_button.setVisibility(ImageButton.VISIBLE);
					mInfo_button.setVisibility(ImageButton.VISIBLE);
					mFroze_button.setVisibility(ImageButton.VISIBLE);
					mShare_but.setVisibility(ImageButton.VISIBLE);
					mFill_button.setImageResource(R.drawable.size);
				}

			}
		});

		mRotate_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Rotation = (Rotation + 90) % 360;
				camera.setDisplayOrientation(Rotation);

			}
		});

		mHelp_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				AlertDialog.Builder localBuilder = new AlertDialog.Builder(
						CaptureActivity.this);
				localBuilder.setTitle(R.string.titlehelp);
				localBuilder.setIcon(R.drawable.icon);
				localBuilder.setMessage(R.string.texthelp);
				localBuilder.show();

			}
		});

		mFroze_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Log.i("isPause", "" + isPause);

				Intent i = new Intent(CaptureActivity.this,
						GridViewActivity.class);

				startActivityForResult(i, 1111);

			}

		});

		mCapture_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				CaptureActivity.this.handler = new Handler() {
					@Override
					public void handleMessage(Message msg) {
						// TODO Auto-generated method stub
						super.handleMessage(msg);
						// CaptureActivity.this.progress.dismiss();
						new DownloadFileFromURL().execute();

					}
				};

				if (!isCapture) {
					isCapture = true;
					CaptureActivity.this.camera.takePicture(null, null,
							jpegCallback);

				} else {
					Toast.makeText(getApplicationContext(), "Wait...",
							Toast.LENGTH_SHORT).show();
					// progress.dismiss();
					// CaptureActivity.this.handler.sendEmptyMessage(0);
				}

			}
		});

		mInfo_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder localBuilder = new AlertDialog.Builder(
						CaptureActivity.this);
				localBuilder.setTitle(R.string.app_name);
				localBuilder.setIcon(R.drawable.icon);
				localBuilder.setMessage(R.string.textAbout);
				localBuilder.setPositiveButton(R.string.rateButtonText,
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface paramDialogInterface,
									int paramInt) {
								Intent localIntent = new Intent(
										"android.intent.action.VIEW",
										Uri.parse(getResources().getString(
												R.string.rateus_url)));
								CaptureActivity.this.startActivity(localIntent);
								// onPressed();
							}
						});
				localBuilder.setNegativeButton(R.string.moreAppsButton,
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface paramDialogInterface,
									int paramInt) {
								Intent goToMarket = null;
								goToMarket = new Intent(Intent.ACTION_VIEW, Uri
										.parse(getResources().getString(
												R.string.moreapp_url)));
								startActivity(goToMarket);
								// onPressed();
							}
						});
				localBuilder.show();
			}
		});

		surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		jpegCallback = new PictureCallback() {
			public void onPictureTaken(byte[] data, Camera camera) {

				Log.i("inside jpegcallback", "" + data);

				if (isCapture) {

					bitmaptemp = BitmapFactory.decodeByteArray(data, 0,
							data.length);
					CaptureActivity.this.handler.sendEmptyMessage(0);
				}
				refreshCamera();
			}
		};

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Log.i("requestcode", "" + requestCode);
		Log.i("resultcode", "" + resultCode);
		Log.i("data", "" + data);

		if (requestCode == 1111) {
			if (resultCode == RESULT_OK) {

				Bundle res = data.getExtras();
				String result = res.getString("param_result");
				int position_ = Integer.parseInt(result);
				Log.i("position selected", "" + position_);
				CaptureActivity.viewFlipper.setDisplayedChild(position_);

			}

		} else if (requestCode == 1212) {

			if (resultCode == RESULT_OK) {

				Uri selectedImageUri = data.getData();

				Intent i = new Intent(CaptureActivity.this,
						FullScreenViewActivity.class);
				i.putExtra("UriOfSelectedImage", selectedImageUri);
				CaptureActivity.this.startActivity(i);

			}

		}

	}

	private void setFlipperImage(int paramInt) {
		ImageView localImageView = new ImageView(getApplicationContext());
		localImageView.setBackgroundResource(paramInt);
		viewFlipper.addView(localImageView);
	}

	class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
		MyGestureDetector() {

		}

		public boolean onFling(MotionEvent paramMotionEvent1,
				MotionEvent paramMotionEvent2, float paramFloat1,
				float paramFloat2) {
			try {
				if ((paramMotionEvent1.getX() - paramMotionEvent2.getX() > 120.0F)
						&& (Math.abs(paramFloat1) > 200.0F)) {
					CaptureActivity.viewFlipper.setInAnimation(AnimationUtils
							.loadAnimation(CaptureActivity.this,
									R.drawable.left_in));
					CaptureActivity.viewFlipper.setOutAnimation(AnimationUtils
							.loadAnimation(CaptureActivity.this,
									R.drawable.left_out));
					CaptureActivity.viewFlipper.showNext();
					return true;
				}
				if ((paramMotionEvent2.getX() - paramMotionEvent1.getX() > 120.0F)
						&& (Math.abs(paramFloat1) > 200.0F)) {
					CaptureActivity.viewFlipper.setInAnimation(AnimationUtils
							.loadAnimation(CaptureActivity.this,
									R.drawable.right_in));
					CaptureActivity.viewFlipper.setOutAnimation(AnimationUtils
							.loadAnimation(CaptureActivity.this,
									R.drawable.right_out));
					CaptureActivity.viewFlipper.showPrevious();
					return true;
				}
			} catch (Exception localException) {
				localException.printStackTrace();
			}
			return false;
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		try {
			// open the camera

			camera = Camera.open();
			camera.setDisplayOrientation(Rotation);
		} catch (RuntimeException e) {
			// check for exceptions
			System.err.println(e);
			return;
		}
		Camera.Parameters param;
		param = camera.getParameters();

		// modify parameter
		// param.setPreviewSize(352, 288);
		// camera.setParameters(param);
		// not support in samsung devices

		try {
			// The Surface has been created, now tell the camera where to draw
			// the preview.
			camera.setPreviewDisplay(surfaceHolder);
			camera.startPreview();
		} catch (Exception e) {
			// check for exceptions
			System.err.println(e);
			return;
		}

	}

	public void refreshCamera() {
		if (surfaceHolder.getSurface() == null) {
			// preview surface does not exist
			return;
		}

		// stop preview before making changes
		try {
			camera.stopPreview();
		} catch (Exception e) {
			// ignore: tried to stop a non-existent preview
		}

		// set preview size and make any resize, rotate or
		// reformatting changes here
		// start preview with new settings
		try {
			camera.setPreviewDisplay(surfaceHolder);
			camera.startPreview();
		} catch (Exception e) {

		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

		refreshCamera();

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		if (camera != null) {
			camera.stopPreview();
			camera.setPreviewCallback(null);
			camera.release();
			camera = null;
		}
	}

	private String captureImage(Bitmap bmp) {
		// TODO Auto-generated method stub

		OutputStream output;

		Calendar cal = Calendar.getInstance();

		// Find the SD Card path
		File filepath = Environment.getExternalStorageDirectory();

		// Create a new folder in SD Card
		File dir = new File(filepath.getAbsolutePath() + "/MenInDemo/");
		dir.mkdirs();

		String mImagename = "image" + cal.getTimeInMillis() + ".png";

		// Create a name for the saved image
		file = new File(dir, mImagename);

		// Show a toast message on successful save
		/*
		 * Toast.makeText(CaptureActivity.this, "Image Saved to SD Card",
		 * Toast.LENGTH_SHORT).show();
		 */
		try {

			output = new FileOutputStream(file);
			// Compress into png format image from 0% - 100%
			bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
			output.flush();
			output.close();
		}

		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mImagename;

	}

	class DownloadFileFromURL extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progress.show();
		}

		@Override
		protected String doInBackground(String... f_url) {
			Log.i("asynk call ", "from");
			Bitmap newBitmap;

			Bitmap bitmap;

			RelativeLayout L1 = (RelativeLayout) findViewById(R.id.capture_id_rl);
			bitmap = Bitmap.createBitmap(L1.getWidth(), L1.getHeight(),
					Config.ARGB_8888);

			bitmap = ThumbnailUtils.extractThumbnail(bitmap, bitmap.getWidth(),
					bitmap.getHeight());

			Canvas b = new Canvas(bitmap);
			L1.draw(b);

			Matrix matrix = new Matrix();
			matrix.postRotate(Rotation);

			newBitmap = Bitmap
					.createBitmap(bitmaptemp, 0, 0, bitmaptemp.getWidth(),
							bitmaptemp.getHeight(), matrix, true);
			bitmaptemp.recycle();

			newBitmap = Bitmap.createScaledBitmap(newBitmap, bitmap.getWidth(),
					(int) (bitmap.getHeight()), true);

			Bitmap bmOverlay = Bitmap.createBitmap(bitmap.getWidth(),
					bitmap.getHeight(), bitmap.getConfig());

			Canvas canvas = new Canvas(bmOverlay); // Overlaying the 2
													// bitmaps
			canvas.drawBitmap(newBitmap, 0, 0, null);
			canvas.drawBitmap(bitmap, 0, 0, null);
			String pathvalue = captureImage(bmOverlay);

			bitmap.recycle();
			newBitmap.recycle();
			bmOverlay.recycle();
			return pathvalue;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// CaptureActivity.this.progress.dismiss();
			progress.dismiss();
			Toast.makeText(CaptureActivity.this, "Image Saved...",
					Toast.LENGTH_SHORT).show();
			isPause = false;
			isCapture = false;
			camera.startPreview();
			mFroze_button.setImageResource(R.drawable.select);
			
			File filepath = Environment.getExternalStorageDirectory();

			// Create a new folder in SD Card
			File dir = new File(filepath.getAbsolutePath() + "/MenInDemo/");
			
//			Intent intent = new Intent(CaptureActivity.this,FullScreenViewActivity.class);
//			  intent.putExtra(Intent.EXTRA_STREAM, Uri.parse ("file://"  +dir+"/"+result));
//			  startActivity(intent);
			Log.i("path",""+dir+result);
			 

		}

	}

}
