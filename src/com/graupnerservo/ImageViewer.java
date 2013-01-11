package com.graupnerservo;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class ImageViewer extends Activity {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.servo_image_viewer);
		// TODO Put your code here
		ImageView imageView = (ImageView) findViewById(R.id.imageView1);
		Bundle bundel = getIntent().getExtras();
		String imgename = (String) bundel.get("ServoImage");
		try {
			Drawable drawable = getAssetImage(this, imgename);
			imageView.setImageDrawable(drawable);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static Drawable getAssetImage(Context context, String filename)
			throws IOException {
		AssetManager assets = context.getResources().getAssets();
		InputStream buffer = new BufferedInputStream((assets.open("img"
				+ filename + ".png")));
		Bitmap bitmap = BitmapFactory.decodeStream(buffer);
		return new BitmapDrawable(bitmap);
	}

	/**
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();
		// TODO Put your code here
	}
	public void imageClick(View view) {
		this.finish();
	}
}
