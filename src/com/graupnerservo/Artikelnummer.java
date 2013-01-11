package com.graupnerservo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class Artikelnummer extends Activity {
	protected AndroidApplication app;
	protected Artikelnummer currentActivity;
	protected Dialog myDialog;
	AutoCompleteTextView textView;

	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		currentActivity = this;
		super.onCreate(savedInstanceState);
		// TODO Put your code here
		app = (AndroidApplication) getApplication();
		this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.artikelnummer);

		List<String> item_names = new ArrayList<String>();
		
		/*String[] projection = new String[] { GraupnerContentProvider.ITEM_NUMBER };
		String sortOrder = GraupnerContentProvider.ITEM_NUMBER + " asc";
		Cursor cursor = managedQuery(GraupnerContentProvider.CONTENT_URI,
				projection, null, null, sortOrder);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				item_names.add(cursor.getString(0));
			}
		}*/

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, item_names);
		textView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
		textView.setAdapter(adapter);
	}

	/**
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();
		// TODO Put your code here
	}

	public void gotoImpressum(View v) {
		Intent i = new Intent();
		i.setClass(this, Impressum.class);
		startActivity(i);
	}

	public void resetValues() {
		app.selectedStellzeit = "";
		app.selectedStellmoment = "";
		app.selectedGetriebe = "";
		app.selectedGewicht = "";
		app.selectedHVValue = "";
		app.selectedBrushlessValue = "";
		app.selectedLagerung = "";
		app.size2 = "";
	}

	public void gotoResults(View v) {
		app.selectedText = textView.getText().toString();
		if (app.selectedText != null && app.selectedText.length() != 0) {
			resetValues();
			String[] projection = new String[] { GraupnerContentProvider.SERVO,
					GraupnerContentProvider.ITEM_NUMBER,
					GraupnerContentProvider.SIZE1,
					GraupnerContentProvider.SIZE2,
					GraupnerContentProvider.SIZE3,
					GraupnerContentProvider.LAGERUNG,
					GraupnerContentProvider.GETRIEBE,
					GraupnerContentProvider.STELLMOMENT_4_8V,
					GraupnerContentProvider.STELLMOMENT_6_0V,
					GraupnerContentProvider.STELLMOMENT_7_4_VOLT,
					GraupnerContentProvider.HALTEMOMENT_4_8V,
					GraupnerContentProvider.HALTEMOMENT_6_0V,
					GraupnerContentProvider.HALTEMOMENT_7_4V,
					GraupnerContentProvider.STELLZEIT_4_8V,
					GraupnerContentProvider.STELLZEIT_6_0V,
					GraupnerContentProvider.STELLZEIT_7_4V,
					GraupnerContentProvider.GEWICHT,
					GraupnerContentProvider.HV, GraupnerContentProvider.TEXT };
			String sortOrder = GraupnerContentProvider.SERVO + " asc";
			String selection = "";
			ArrayList<String> params = new ArrayList<String>();
			selection += GraupnerContentProvider.ITEM_NUMBER + " LIKE ?";
			params.add("%" + app.selectedText + "%");

			String[] paramsArray = params.toArray(new String[params.size()]);
			Cursor cursor = managedQuery(GraupnerContentProvider.CONTENT_URI,
					projection, selection, paramsArray, sortOrder);
			ArrayList<Servo> data = new ArrayList<Servo>();

			if (cursor != null) {
				while (cursor.moveToNext()) {
					Servo obj = new Servo();
					obj.servoName = cursor.getString(0);
					obj.servoArticleNumber = cursor.getString(1);
					obj.servoSize1 = cursor.getString(2);
					obj.servoSize2 = cursor.getString(3);
					obj.servoSize3 = cursor.getString(4);
					obj.servoLagerung = cursor.getString(5);
					obj.servoGetribe = cursor.getString(6);
					obj.servoStellmoment_4_8v = cursor.getString(7);
					obj.servoStellmoment_6_0v = cursor.getString(8);
					obj.servoStellmoment_7_4v = cursor.getString(9);
					obj.servoHaltemoment_4_8v = cursor.getString(10);
					obj.servoHaltemoment_6_0v = cursor.getString(11);
					obj.servoHaltemoment_7_4v = cursor.getString(12);
					obj.servoStellzeit_4_8v = cursor.getString(13);
					obj.servoStellzeit_6_0v = cursor.getString(14);
					obj.servoStellzeit_7_4v = cursor.getString(15);
					obj.servoGewicht = cursor.getString(16);
					obj.servoHVServo = cursor.getString(17);
					obj.servoDetailText = cursor.getString(18);
					data.add(obj);
				}
			}
			if (data.size() > 0) {
				Intent i = new Intent();
				i.setClass(this, ServoResults.class);
				startActivity(i);
				this.finish();
				return;
			}
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Kein Ergebnis gefunden");
		builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// here you can add functions
				dialog.dismiss();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public void gotoFaceBook(View v) {
		Intent i = new Intent();
		i.setClass(this, WebViewer.class);
		startActivity(i);
	}
}
