package com.graupnerservo;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ServosAdapter extends ArrayAdapter<Servo> {
	Context context;
	int layoutResourceId;
	ArrayList<Servo> data = null;

	public ServosAdapter(Context context, int layoutResourceId,
			ArrayList<Servo> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ServoHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new ServoHolder();
			holder.txtServoHeading = (TextView) row
					.findViewById(R.id.txtServoHeading);
			holder.txtArtikle = (TextView) row.findViewById(R.id.txtItemNumber);
			holder.txtGewicht = (TextView) row.findViewById(R.id.txtGewicht);
			holder.txtGrosse = (TextView) row.findViewById(R.id.txtGrosse);
			holder.txtLagerung = (TextView) row.findViewById(R.id.txtLagerung);
			holder.txtStellzeit = (TextView) row.findViewById(R.id.txtStellzeit);
			holder.txtGetriebe = (TextView) row.findViewById(R.id.txtGetriebe);
			holder.txtHV = (TextView) row.findViewById(R.id.txtHVServo);
			holder.txtBrushless = (TextView) row
					.findViewById(R.id.txtBrushless);
			holder.txtPrice = (TextView) row
					.findViewById(R.id.txtPrice);
			holder.imgView = (ImageView) row.findViewById(R.id.servoImage);
			holder.redLine = (LinearLayout) row.findViewById(R.id.rowRedLine);
			holder.detailButton = (Button) row.findViewById(R.id.btnDetail);
			row.setTag(holder);
		} else {
			holder = (ServoHolder) row.getTag();
		}

		Servo servo = data.get(position);
		holder.txtServoHeading.setText(servo.servoName);
		holder.txtArtikle.setText(servo.servoArticleNumber);
		holder.txtGewicht.setText(servo.servoGewicht);
		holder.txtGrosse.setText(servo.getCombineSize() + " mm");
		holder.txtLagerung.setText(servo.servoLagerung);
		holder.txtStellzeit.setText(servo.servoStellzeit_6_0v);
		holder.txtGetriebe.setText(servo.servoGetribe);
		holder.txtHV.setText(servo.servoHVServo);
		holder.txtBrushless.setText(servo.servoBrushless);
		holder.txtPrice.setText(servo.servoPrice);
		holder.detailButton.setTag(position);
		if (servo.servoImage != null && servo.servoImage != "") {
			try {
				Drawable drawable = getAssetImage(this.context,
						servo.servoImage);
				holder.imgView.setImageDrawable(drawable);
				holder.imgView.setVisibility(ImageView.VISIBLE);
				
			} catch (Exception e) {
				// TODO: handle exception
				holder.imgView.setVisibility(ImageView.INVISIBLE);
			}
		}
		if(data.size() -1 == position){
			holder.redLine.setVisibility(LinearLayout.INVISIBLE);
		}else{
			holder.redLine.setVisibility(LinearLayout.VISIBLE);
		}
		return row;
	}

	public static Drawable getAssetImage(Context context, String filename)
			throws IOException {
		AssetManager assets = context.getResources().getAssets();
		InputStream buffer = new BufferedInputStream((assets.open("img"
				+ filename + ".png")));
		Bitmap bitmap = BitmapFactory.decodeStream(buffer);
		return new BitmapDrawable(bitmap);
	}

	static class ServoHolder {
		TextView txtServoHeading;
		TextView txtArtikle;
		TextView txtGewicht;
		TextView txtGrosse;
		TextView txtLagerung;
		TextView txtStellzeit;
		TextView txtGetriebe;
		TextView txtHV;
		TextView txtBrushless;
		TextView txtPrice;
		ImageView imgView;
		LinearLayout redLine;
		Button detailButton;
	}
}
