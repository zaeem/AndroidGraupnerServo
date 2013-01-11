package com.graupnerservo;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class ServoDetail extends Activity {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	protected AndroidApplication app;
	protected Servo servo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO Put your code here
		app = (AndroidApplication) getApplication();
		this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.servodetail);
		TextView txt1 = (TextView) findViewById(R.id.txtFilterHV);
		TextView txt2 = (TextView) findViewById(R.id.txtFilterLagerung);
		TextView txt3 = (TextView) findViewById(R.id.txtFilterGetriebe);
		TextView txt4 = (TextView) findViewById(R.id.txtFilterGrosse);
		TextView txt5 = (TextView) findViewById(R.id.txtFilterStellzeit);
		TextView txt6 = (TextView) findViewById(R.id.txtFilterStellmoment);
		TextView txt7 = (TextView) findViewById(R.id.txtFilterGewicht);
		TextView txt8 = (TextView) findViewById(R.id.txtFilterBrushless);
		if (app.selectedHVValue.length() == 0) {
			txt1.setText("-");
		} else {
			txt1.setText(app.selectedHVValue);
		}
		if (app.selectedBrushlessValue.length() == 0) {
			txt8.setText("-");
		} else {
			txt8.setText(app.selectedBrushlessValue);
		}
		if (app.selectedLagerung.length() == 0) {
			txt2.setText("-");
		} else {
			txt2.setText(app.selectedLagerung);
		}
		if (app.selectedGetriebe.length() == 0) {
			txt3.setText("-");
		} else {
			txt3.setText(app.selectedGetriebe);
		}
		if (app.size2.length() == 0) {
			txt4.setText("-");
		} else {
			txt4.setText(app.size2 + " mm");
		}
		if (app.selectedStellzeit.length() == 0) {
			txt5.setText("-");
		} else {
			txt5.setText(app.selectedStellzeit.replace('.', ',') + " sec bei 6,0 Volt");
		}
		if (app.selectedStellmoment.length() == 0) {
			txt6.setText("-");
		} else {
			txt6.setText(app.selectedStellmoment + " N/cm");
		}
		if (app.selectedGewicht.length() == 0) {
			txt7.setText("-");
		} else {
			txt7.setText(app.selectedGewicht + " g");
		}
		Bundle bundel = getIntent().getExtras();
		try {
			servo = (Servo) bundel.get("Servo");
			TextView servoPrice = (TextView) findViewById(R.id.txtDetailServoPrice);
			TextView servoName = (TextView) findViewById(R.id.txtDetailServoName);
			TextView itemNumber = (TextView) findViewById(R.id.txtDetailServoItemNumber);
			TextView gewicht = (TextView) findViewById(R.id.txtDetailServoGewicht);
			TextView grosse = (TextView) findViewById(R.id.txtDetailServoGrosse);
			TextView laugerung = (TextView) findViewById(R.id.txtDetailServoLaugerung);
			TextView getriebe = (TextView) findViewById(R.id.txtDetailServoGetriebe);
			TextView hvServo = (TextView) findViewById(R.id.txtDetailServoHV);
			TextView brushlessServo = (TextView) findViewById(R.id.txtDetailServoBrushless);
			TextView stellmomente4_8 = (TextView) findViewById(R.id.txtDetailServoStellemomente4_8);
			TextView stellmomente6_0 = (TextView) findViewById(R.id.txtDetailServoStellemomente6_0);
			TextView stellmomente7_4 = (TextView) findViewById(R.id.txtDetailServoStellemomente7_4);

			TextView haltemomente4_8 = (TextView) findViewById(R.id.txtDetailServoHaltemomente4_8);
			TextView haltemomente6_0 = (TextView) findViewById(R.id.txtDetailServoHaltemomente6_0);
			TextView haltemomente7_4 = (TextView) findViewById(R.id.txtDetailServoHaltemomente7_4);

			TextView stellzeiten4_8 = (TextView) findViewById(R.id.txtDetailStellzeiten4_8);
			TextView stellzeiten6_0 = (TextView) findViewById(R.id.txtDetailStellzeiten6_0);
			TextView stellzeiten7_4 = (TextView) findViewById(R.id.txtDetailStellzeiten7_4);
			ImageView imgView = (ImageView) findViewById(R.id.servoImage);
			TextView detailText = (TextView) findViewById(R.id.txtDetailText);
			View separator1 = (View) findViewById(R.id.verticalSeparator1);
			View separator2 = (View) findViewById(R.id.verticalSeparator2);
			Boolean column1 = false;
			Boolean column2 = false;
			Boolean column3 = false;

			servoPrice.setText(servo.servoPrice);
			servoName.setText(servo.servoName);
			itemNumber.setText(servo.servoArticleNumber);
			gewicht.setText(servo.servoGewicht);
			grosse.setText(servo.getCombineSize() + " mm");
			laugerung.setText(servo.servoLagerung);
			getriebe.setText(servo.servoGetribe);
			hvServo.setText(servo.servoHVServo);
			brushlessServo.setText(servo.servoBrushless);
			if (servo.servoStellmoment_4_8v.equalsIgnoreCase("N/A")
					&& servo.servoHaltemoment_4_8v.equalsIgnoreCase("N/A")
					&& servo.servoStellzeit_4_8v.equalsIgnoreCase("N/A")) {

				stellmomente4_8.setVisibility(TextView.GONE);
				haltemomente4_8.setVisibility(TextView.GONE);
				stellzeiten4_8.setVisibility(TextView.GONE);
			} else {
				stellmomente4_8.setText(servo.servoStellmoment_4_8v);
				haltemomente4_8.setText(servo.servoHaltemoment_4_8v);
				stellzeiten4_8.setText(servo.servoStellzeit_4_8v);
				column1 = true;
			}
			if (servo.servoStellmoment_6_0v.equalsIgnoreCase("N/A")
					&& servo.servoHaltemoment_6_0v.equalsIgnoreCase("N/A")
					&& servo.servoStellzeit_6_0v.equalsIgnoreCase("N/A")) {

				stellmomente6_0.setVisibility(TextView.GONE);
				haltemomente6_0.setVisibility(TextView.GONE);
				stellzeiten6_0.setVisibility(TextView.GONE);
			} else {
				stellmomente6_0.setText(servo.servoStellmoment_6_0v);
				haltemomente6_0.setText(servo.servoHaltemoment_6_0v);
				stellzeiten6_0.setText(servo.servoStellzeit_6_0v);
				column2 = true;
			}
			if (servo.servoStellmoment_7_4v.equalsIgnoreCase("N/A")
					&& servo.servoHaltemoment_7_4v.equalsIgnoreCase("N/A")
					&& servo.servoStellzeit_7_4v.equalsIgnoreCase("N/A")) {

				stellmomente7_4.setVisibility(TextView.GONE);
				haltemomente7_4.setVisibility(TextView.GONE);
				stellzeiten7_4.setVisibility(TextView.GONE);
			} else {
				stellmomente7_4.setText(servo.servoStellmoment_7_4v);
				haltemomente7_4.setText(servo.servoHaltemoment_7_4v);
				stellzeiten7_4.setText(servo.servoStellzeit_7_4v);
				column3 = true;
			}
			if (column1 && column2) {
				separator1.setVisibility(View.VISIBLE);
			} else {
				separator1.setVisibility(View.GONE);
			}
			if (column2 && column3) {
				separator2.setVisibility(View.VISIBLE);
			} else {
				separator2.setVisibility(View.GONE);
			}
			detailText.setText(servo.servoDetailText);
			if (servo.servoImage != null && servo.servoImage != "") {
				try {
					Drawable drawable = getAssetImage(this, servo.servoImage);
					imgView.setImageDrawable(drawable);
				} catch (Exception e) {
					// TODO: handle exception
					ImageView icon = (ImageView)findViewById(R.id.zoomIcon);
					icon.setVisibility(ImageView.GONE);
				}
			}
		} catch (Exception e) {
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

	public void imageClick(View view) {
		Intent i = new Intent();
		i.setClass(this, ImageViewer.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("ServoImage", servo.servoImage);
		bundle.putSerializable("ServoName", servo.servoName);
		i.putExtras(bundle);
		startActivity(i);
	}
}
