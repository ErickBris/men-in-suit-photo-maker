package com.example.meninsuit;

import android.app.Application;
import android.graphics.Bitmap;

public class Global extends Application {

	private Bitmap bm1;

	public Bitmap getBm1() {
		return bm1;
	}

	public void setBm1(Bitmap bm1) {
		this.bm1 = bm1;
	}

}
