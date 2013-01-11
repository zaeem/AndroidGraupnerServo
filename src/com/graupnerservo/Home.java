package com.graupnerservo;

import java.util.ArrayList;
import java.util.List;

import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

import com.graupnerservo.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class Home extends Activity {
	protected AndroidApplication app;
	protected Dialog myDialog;
	private static final int SELECT_Stellzeit = 100;

	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		app = (AndroidApplication) getApplication();
		super.onCreate(savedInstanceState);
		this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.home);
		// TODO Put your code here
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	/**
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();
		// TODO Put your code here
		this.setButtons();
	}
	
	private void setButtons(){
		if (app.selectedHVValue.length() != 0
				|| app.selectedBrushlessValue.length() != 0) {
			Button btn = (Button) findViewById(R.id.btn_HVBrushless);
			valueSelected(btn);
			TextView txt = (TextView) findViewById(R.id.txtFilterHV);
			txt.setText("-");
			if (app.selectedHVValue.length() != 0) {
				txt.setText(app.selectedHVValue);
			}

			txt = (TextView) findViewById(R.id.txtFilterBrushless);
			txt.setText("-");
			if (app.selectedBrushlessValue.length() != 0) {
				txt.setText(app.selectedBrushlessValue);
			}
		} else {
			Button btn = (Button) findViewById(R.id.btn_HVBrushless);
			unselectValue(btn);
		}

		if (app.selectedLagerung.length() != 0
				|| app.selectedGetriebe.length() != 0) {
			Button btn = (Button) findViewById(R.id.btn_LagerungGetriebe);
			valueSelected(btn);
			TextView txt2 = (TextView) findViewById(R.id.txtFilterLagerung);
			TextView txt3 = (TextView) findViewById(R.id.txtFilterGetriebe);
			txt2.setText("-");
			txt3.setText("-");
			if (app.selectedLagerung.length() != 0) {
				txt2.setText(app.selectedLagerung);
			}
			if (app.selectedGetriebe.length() != 0) {
				txt3.setText(app.selectedGetriebe);
			}
		} else {
			Button btn = (Button) findViewById(R.id.btn_LagerungGetriebe);
			unselectValue(btn);
		}

		if (app.size2.length() != 0) {
			Button btn = (Button) findViewById(R.id.btn_grosse);
			valueSelected(btn);
			TextView txt = (TextView) findViewById(R.id.txtFilterGrosse);
			txt.setText(app.size2 + " mm");
		}
		if (app.selectedStellzeit.length() != 0) {
			Button btn = (Button) findViewById(R.id.btn_haltemoment);
			valueSelected(btn);
			TextView txt = (TextView) findViewById(R.id.txtFilterStellzeit);
			txt.setText(app.selectedStellzeit.replace('.', ',') + " sec bei 6,0 Volt");
		}else{
			Button btn = (Button) findViewById(R.id.btn_haltemoment);
			unselectValue(btn);
			TextView txt = (TextView) findViewById(R.id.txtFilterStellzeit);
			txt.setText("-");
		}

		if (app.selectedStellmoment.length() != 0) {
			Button btn = (Button) findViewById(R.id.btn_stellmoment);
			valueSelected(btn);
			TextView txt = (TextView) findViewById(R.id.txtFilterStellmoment);
			txt.setText(app.selectedStellmoment + " N/cm");
		}
		if (app.selectedGewicht.length() != 0) {
			Button btn = (Button) findViewById(R.id.btn_gewicht);
			valueSelected(btn);
			TextView txt = (TextView) findViewById(R.id.txtFilterGewicht);
			txt.setText(app.selectedGewicht + " g");
		}
	}
	public void selectStellzeit(View v) {
		/*Intent i = new Intent();
		i.setClass(this, SelectStellzeit.class);
		startActivityForResult(i, SELECT_Stellzeit); */
		
		String backButtonTitle = "Zurück";
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(R.layout.stellzeitwheel, null);

		final String[] values1 = new String[1];
		values1[0] = "0";
		
		final String[] values2 = new String[2];
		values2[0] = "0";
		values2[1] = "1";
		
		final String[] values = new String[10];
		values[0] = "0";
		values[1] = "1";
		values[2] = "2";
		values[3] = "3";
		values[4] = "4";
		values[5] = "5";
		values[6] = "6";
		values[7] = "7";
		values[8] = "8";
		values[9] = "9";
		int selectedIndex1 = 0;
		int selectedIndex2 = 0;
		int selectedIndex3 = 0;
		String str = app.selectedStellzeit.replace(",", "");
		if(app.selectedStellzeit!=null && app.selectedStellzeit.length()>0){
			backButtonTitle = "Löschen";
			if(str.length() == 4){
				selectedIndex3 = Integer.parseInt(str.substring(3,4));
			}
			if(str.length() >= 3){
				selectedIndex2 = Integer.parseInt(str.substring(2, 3));
			}
			if(str.length() >= 2){
				selectedIndex1 = Integer.parseInt(str.substring(1, 2));
			}
		}
		final WheelView wheel1 = (WheelView) view.findViewById(R.id.wheel1);
		wheel1.setVisibleItems(5);
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(
				this, values1);
		adapter.setItemResource(R.layout.wheel_text_item);
		adapter.setItemTextResource(R.id.text);
		adapter.text_lines = 1;
		wheel1.setViewAdapter(adapter);
		wheel1.setCurrentItem(0);

		ArrayWheelAdapter<String> adapter1 = new ArrayWheelAdapter<String>(
				this, values2);
		adapter1.setItemResource(R.layout.wheel_text_item);
		adapter1.setItemTextResource(R.id.text);
		adapter1.text_lines = 1;
		
		final WheelView wheel2 = (WheelView) view.findViewById(R.id.wheel2);
		wheel2.setVisibleItems(5);
		wheel2.setViewAdapter(adapter1);
		wheel2.setCurrentItem(selectedIndex1);
		
		ArrayWheelAdapter<String> adapter2 = new ArrayWheelAdapter<String>(
				this, values);
		adapter2.setItemResource(R.layout.wheel_text_item);
		adapter2.setItemTextResource(R.id.text);
		adapter2.text_lines = 1;
		final WheelView wheel3 = (WheelView) view.findViewById(R.id.wheel3);
		wheel3.setVisibleItems(5);
		wheel3.setViewAdapter(adapter2);
		wheel3.setCurrentItem(selectedIndex2);
		
		final WheelView wheel4 = (WheelView) view.findViewById(R.id.wheel4);
		wheel4.setVisibleItems(5);
		wheel4.setViewAdapter(adapter2);
		wheel4.setCurrentItem(selectedIndex3);
		
		builder.setTitle("Stellzeit (sec)");
		builder.setNegativeButton(backButtonTitle,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// here you can add functions
						app.selectedStellzeit = "";
						TextView txt2 = (TextView) findViewById(R.id.txtFilterStellzeit);
						txt2.setText("-");
						Button btn = (Button) findViewById(R.id.btn_haltemoment);
						unselectValue(btn);
						dialog.dismiss();
					}
				});
		builder.setPositiveButton("Auswahl",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						TextView txt2 = (TextView) findViewById(R.id.txtFilterStellzeit);
						if (wheel2.getCurrentItem() == 0 && wheel3.getCurrentItem() == 0 && wheel4.getCurrentItem() == 0) {
							app.selectedStellzeit = "";
							txt2.setText("-");
						} else {
							if(wheel4.getCurrentItem()!=0){
								app.selectedStellzeit = "0,"+values2[wheel2.getCurrentItem()]+values[wheel3.getCurrentItem()]+values[wheel4.getCurrentItem()];
							}else{
								if(wheel3.getCurrentItem()!=0){
									app.selectedStellzeit = "0,"+values2[wheel2.getCurrentItem()]+values[wheel3.getCurrentItem()];
								}else{
									if(wheel2.getCurrentItem()!=0){
										app.selectedStellzeit = "0,"+values2[wheel2.getCurrentItem()];
									}else{
										app.selectedStellzeit = "";
										txt2.setText("-");
									}
								}
							}
							txt2.setText(app.selectedStellzeit+" sec bei 6,0 Volt");
						}
						if (app.selectedStellzeit.length() != 0
								|| app.selectedStellzeit.length() != 0) {
							Button btn = (Button) findViewById(R.id.btn_haltemoment);
							valueSelected(btn);
						} else {
							Button btn = (Button) findViewById(R.id.btn_haltemoment);
							unselectValue(btn);
						}
						dialog.dismiss();
					}
				});
		builder.setView(view);
		builder.show();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) { 
	    super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 
	    switch(requestCode) { 
	    case SELECT_Stellzeit:
	        if(resultCode == RESULT_OK){  
	        	this.setButtons();
	        }
	    }
	}

	public void selectHVBrushless(View v) {
		String backButtonTitle = "Zurück";
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(R.layout.moreoptions, null);

		TextView wheel1Label = (TextView) view.findViewById(R.id.titleWheel2);
		TextView wheel2Label = (TextView) view.findViewById(R.id.titleWheel3);

		wheel1Label.setText("HV");
		wheel2Label.setText("Brushless");

		final String[] size2 = new String[3];
		size2[0] = "-";
		size2[1] = "JA";
		size2[2] = "NEIN";
		int selectedIndex = 0;
		for (int i = 1; i < size2.length; i++) {
			if (size2[i].equalsIgnoreCase(app.selectedHVValue)) {
				selectedIndex = i;
				backButtonTitle = "Löschen";
			}
		}
		final WheelView size2Wheel = (WheelView) view.findViewById(R.id.size_2);
		size2Wheel.setVisibleItems(3);
		ArrayWheelAdapter<String> adapter2 = new ArrayWheelAdapter<String>(
				this, size2);
		adapter2.setItemResource(R.layout.wheel_text_item);
		adapter2.setItemTextResource(R.id.text);
		adapter2.text_lines = 2;
		size2Wheel.setViewAdapter(adapter2);
		size2Wheel.setCurrentItem(selectedIndex);

		selectedIndex = 0;
		for (int i = 1; i < size2.length; i++) {
			if (size2[i].equalsIgnoreCase(app.selectedBrushlessValue)) {
				selectedIndex = i;
				backButtonTitle = "Löschen";
			}
		}
		final WheelView size3Wheel = (WheelView) view.findViewById(R.id.size_3);
		size3Wheel.setVisibleItems(5);
		ArrayWheelAdapter<String> adapter3 = new ArrayWheelAdapter<String>(
				this, size2);
		adapter3.setItemResource(R.layout.wheel_text_item);
		adapter3.setItemTextResource(R.id.text);
		adapter3.text_lines = 2;
		size3Wheel.setViewAdapter(adapter3);
		size3Wheel.setCurrentItem(selectedIndex);
		builder.setTitle("HV/Brushless");
		builder.setNegativeButton(backButtonTitle,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// here you can add functions
						app.selectedHVValue = "";
						app.selectedBrushlessValue = "";
						TextView txt2 = (TextView) findViewById(R.id.txtFilterHV);
						TextView txt3 = (TextView) findViewById(R.id.txtFilterBrushless);
						txt2.setText("-");
						txt3.setText("-");
						Button btn = (Button) findViewById(R.id.btn_HVBrushless);
						unselectValue(btn);
						dialog.dismiss();
					}
				});
		builder.setPositiveButton("Auswahl",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						TextView txt2 = (TextView) findViewById(R.id.txtFilterHV);
						TextView txt3 = (TextView) findViewById(R.id.txtFilterBrushless);
						if (size2Wheel.getCurrentItem() != 0) {
							app.selectedHVValue = size2[size2Wheel
									.getCurrentItem()];
							txt2.setText(app.selectedHVValue);
						} else {
							app.selectedHVValue = "";
							txt2.setText("-");
						}
						if (size3Wheel.getCurrentItem() != 0) {
							app.selectedBrushlessValue = size2[size3Wheel
									.getCurrentItem()];
							txt3.setText(app.selectedBrushlessValue);
						} else {
							app.selectedBrushlessValue = "";
							txt3.setText("-");
						}
						if (app.selectedHVValue.length() != 0
								|| app.selectedBrushlessValue.length() != 0) {
							Button btn = (Button) findViewById(R.id.btn_HVBrushless);
							valueSelected(btn);
						} else {
							Button btn = (Button) findViewById(R.id.btn_HVBrushless);
							unselectValue(btn);
						}
						dialog.dismiss();
					}
				});
		builder.setView(view);
		builder.show();
	}

	public void selectLagerungGetriebe(View v) {
		String backButtonTitle = "Zurück";
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(R.layout.moreoptions, null);

		TextView wheel1Label = (TextView) view.findViewById(R.id.titleWheel2);
		TextView wheel2Label = (TextView) view.findViewById(R.id.titleWheel3);

		wheel1Label.setText("Lagerung");
		wheel2Label.setText("Getriebe");

		List<String> items = new ArrayList<String>();
		String[] projection = new String[] { GraupnerContentProvider.LAGERUNG };
		String sortOrder = GraupnerContentProvider.LAGERUNG + "+0 asc";
		Cursor cursor = managedQuery(GraupnerContentProvider.CONTENT_URI,
				projection, null, null, sortOrder);
		items = new ArrayList<String>();

		if (cursor != null) {
			while (cursor.moveToNext()) {
				if (cursor.getString(0) != null)
					items.add(cursor.getString(0));
			}
		}

		final String[] size2 = new String[items.size() + 1];
		size2[0] = "-";
		int selectedIndex = 0;
		for (int i = 0; i < items.size(); i++) {
			size2[i + 1] = items.get(i);
			if (items.get(i).equalsIgnoreCase(app.selectedLagerung)) {
				selectedIndex = i + 1;
				backButtonTitle = "Löschen";
			}
		}
		final WheelView size2Wheel = (WheelView) view.findViewById(R.id.size_2);
		size2Wheel.setVisibleItems(3);
		ArrayWheelAdapter<String> adapter2 = new ArrayWheelAdapter<String>(
				this, size2);
		adapter2.setItemResource(R.layout.wheel_text_item);
		adapter2.setItemTextResource(R.id.text);
		adapter2.text_lines = 2;
		size2Wheel.setViewAdapter(adapter2);
		size2Wheel.setCurrentItem(selectedIndex);

		projection = new String[] { GraupnerContentProvider.GETRIEBE };
		sortOrder = GraupnerContentProvider.GETRIEBE + "+0 asc";
		cursor = managedQuery(GraupnerContentProvider.CONTENT_URI, projection,
				null, null, sortOrder);
		items = new ArrayList<String>();

		if (cursor != null) {
			while (cursor.moveToNext()) {
				if (cursor.getString(0) != null)
					items.add(cursor.getString(0));
			}
		}

		final String[] size3 = new String[items.size() + 1];
		size3[0] = "-";
		selectedIndex = 0;
		for (int i = 0; i < items.size(); i++) {
			size3[i + 1] = items.get(i);
			if (items.get(i).equalsIgnoreCase(app.selectedGetriebe)) {
				selectedIndex = i + 1;
				backButtonTitle = "Löschen";
			}
		}
		final WheelView size3Wheel = (WheelView) view.findViewById(R.id.size_3);
		size3Wheel.setVisibleItems(5);
		ArrayWheelAdapter<String> adapter3 = new ArrayWheelAdapter<String>(
				this, size3);
		adapter3.setItemResource(R.layout.wheel_text_item);
		adapter3.setItemTextResource(R.id.text);
		adapter3.text_lines = 2;
		size3Wheel.setViewAdapter(adapter3);
		size3Wheel.setCurrentItem(selectedIndex);
		builder.setTitle("Lagerung/Getriebe");
		builder.setNegativeButton(backButtonTitle,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// here you can add functions
						app.selectedLagerung = "";
						app.selectedGetriebe = "";
						TextView txt2 = (TextView) findViewById(R.id.txtFilterLagerung);
						TextView txt3 = (TextView) findViewById(R.id.txtFilterGetriebe);
						txt2.setText("-");
						txt3.setText("-");
						Button btn = (Button) findViewById(R.id.btn_LagerungGetriebe);
						unselectValue(btn);
						dialog.dismiss();
					}
				});
		builder.setPositiveButton("Auswahl",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						TextView txt2 = (TextView) findViewById(R.id.txtFilterLagerung);
						TextView txt3 = (TextView) findViewById(R.id.txtFilterGetriebe);
						if (size2Wheel.getCurrentItem() != 0) {
							app.selectedLagerung = size2[size2Wheel
									.getCurrentItem()];
							txt2.setText(app.selectedLagerung);
						} else {
							app.selectedLagerung = "";
							txt2.setText("-");
						}
						if (size3Wheel.getCurrentItem() != 0) {
							app.selectedGetriebe = size3[size3Wheel
									.getCurrentItem()];
							txt3.setText(app.selectedGetriebe);
						} else {
							app.selectedGetriebe = "";
							txt3.setText("-");
						}
						if (app.selectedLagerung.length() != 0
								|| app.selectedGetriebe.length() != 0) {
							Button btn = (Button) findViewById(R.id.btn_LagerungGetriebe);
							valueSelected(btn);
						} else {
							Button btn = (Button) findViewById(R.id.btn_LagerungGetriebe);
							unselectValue(btn);
						}
						dialog.dismiss();
					}
				});
		builder.setView(view);
		builder.show();
	}

	public void selectGrosse(View v) {
		String backButtonTitle = "Zurück";
		final String[] string_array = new String[] { "9", "10", "11", "12", "13", "14", "15", "16"
				, "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29","30" };
		int selectedIndex = 0;
		for (int i = 0; i < string_array.length; i++) {
			if (string_array[i].equalsIgnoreCase(app.size2)) {
				selectedIndex = i;
				backButtonTitle = "Löschen";
			}
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(R.layout.wheel_view, null);
		final WheelView wheel = (WheelView) view.findViewById(R.id.wheelView);
		wheel.setVisibleItems(5);
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
				string_array);
		adapter.setItemResource(R.layout.wheel_text_item);
		adapter.setItemTextResource(R.id.text);
		wheel.setViewAdapter(adapter);
		wheel.setCurrentItem(selectedIndex);
		builder.setView(view);
		builder.setTitle("Maximale Breite ( mm )");
		builder.setNegativeButton(backButtonTitle,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						app.size2 = "";
						TextView txt = (TextView) findViewById(R.id.txtFilterGrosse);
						txt.setText("-");
						Button btn = (Button) findViewById(R.id.btn_grosse);
						unselectValue(btn);
						dialog.dismiss();
					}
				});
		builder.setPositiveButton("Auswahl",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						app.size2 = string_array[wheel.getCurrentItem()];
						TextView txt = (TextView) findViewById(R.id.txtFilterGrosse);
						txt.setText(app.size2 + " mm");
						Button btn = (Button) findViewById(R.id.btn_grosse);
						valueSelected(btn);
						dialog.dismiss();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}
	public void valueSelected(Button btn) {
		btn.setSelected(true);
		app.selectedText = "";
	}

	public void unselectValue(Button btn) {
		btn.setSelected(false);
	}

	public void selectStellmoment(View v) {
		String backButtonTitle = "Zurück";
		final String[] string_array = new String[] { "6-30", "31-60", "61-90",
				"91-120", "121-150", "151-180", "181-210", "211-240",
				"241-270", "271-300", "301-310" };
		int selectedIndex = 0;
		for (int i = 0; i < string_array.length; i++) {
			if (string_array[i].equalsIgnoreCase(app.selectedStellmoment)) {
				selectedIndex = i;
				backButtonTitle = "Löschen";
			}
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(R.layout.wheel_view, null);
		final WheelView wheel = (WheelView) view.findViewById(R.id.wheelView);

		builder.setTitle("Stellmoment 6,0 V (N/cm)");
		builder.setNegativeButton(backButtonTitle,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// here you can add functions
						app.selectedStellmoment = "";
						TextView txt = (TextView) findViewById(R.id.txtFilterStellmoment);
						txt.setText("-");
						Button btn = (Button) findViewById(R.id.btn_stellmoment);
						unselectValue(btn);
						dialog.dismiss();
					}
				});
		builder.setPositiveButton("Auswahl",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						app.selectedStellmoment = string_array[wheel
								.getCurrentItem()];
						TextView txt = (TextView) findViewById(R.id.txtFilterStellmoment);
						txt.setText(app.selectedStellmoment + " N/cm");
						Button btn = (Button) findViewById(R.id.btn_stellmoment);
						valueSelected(btn);
						dialog.dismiss();
					}
				});
		wheel.setVisibleItems(5);
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
				string_array);
		adapter.setItemResource(R.layout.wheel_text_item);
		adapter.setItemTextResource(R.id.text);
		wheel.setViewAdapter(adapter);
		wheel.setCurrentItem(selectedIndex);
		builder.setView(view);
		AlertDialog alert = builder.create();
		alert.show();
	}

	public void selectGewicht(View v) {
		String backButtonTitle = "Zurück";
		final String[] string_array = new String[] { "5", "6", "7",
				"8", "9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29"
				,"30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50"
				,"51","52","53","54","55","56","57","58","59","60","61","62","63","64","65","66","67","68","69","70","71"
				,"72","73","74","75"};
		int selectedIndex = 0;
		for (int i = 0; i < string_array.length; i++) {
			if (string_array[i].equalsIgnoreCase(app.selectedGewicht)) {
				selectedIndex = i;
				backButtonTitle = "Löschen";
			}
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(R.layout.wheel_view, null);
		final WheelView wheel = (WheelView) view.findViewById(R.id.wheelView);
		wheel.setVisibleItems(5);

		builder.setTitle("Maximales Gewicht (g)");
		builder.setNegativeButton(backButtonTitle,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// here you can add functions
						app.selectedGewicht = "";
						TextView txt = (TextView) findViewById(R.id.txtFilterGewicht);
						txt.setText("-");
						Button btn = (Button) findViewById(R.id.btn_gewicht);
						unselectValue(btn);
						dialog.dismiss();
					}
				});
		builder.setPositiveButton("Auswahl",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						app.selectedGewicht = string_array[wheel
								.getCurrentItem()];
						TextView txt = (TextView) findViewById(R.id.txtFilterGewicht);
						txt.setText(app.selectedGewicht + " g");
						Button btn = (Button) findViewById(R.id.btn_gewicht);
						valueSelected(btn);
						dialog.dismiss();
					}
				});
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
				string_array);
		adapter.setItemResource(R.layout.wheel_text_item);
		adapter.setItemTextResource(R.id.text);
		wheel.setViewAdapter(adapter);
		wheel.setCurrentItem(selectedIndex);
		builder.setView(view);
		AlertDialog alert = builder.create();
		alert.show();
	}

	public void resetAllValues(View v) {
		this.resetAll();
	}

	public void gotoImpressum(View v) {
		Intent i = new Intent();
		i.setClass(this, Impressum.class);
		startActivity(i);
	}

	public void gotoResults(View v) {
		String[] projection = new String[] { GraupnerContentProvider.SERVO,
				GraupnerContentProvider.ITEM_NUMBER,
				GraupnerContentProvider.SIZE1, GraupnerContentProvider.SIZE2,
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
				GraupnerContentProvider.GEWICHT, GraupnerContentProvider.HV,
				GraupnerContentProvider.TEXT };
		String sortOrder = GraupnerContentProvider.SERVO + " asc";
		String selection = "";
		ArrayList<String> params = new ArrayList<String>();
		app.selectedText = "";
		if (app.selectedStellzeit != null
				&& app.selectedStellzeit.length() != 0) {
			selection += "cast((REPLACE("
					+ GraupnerContentProvider.STELLZEIT_6_0V
					+ ",',','.')) AS FLOAT)" + " <= ?  AND ";
			params.add(app.selectedStellzeit.replace(",","."));
		}

		if (app.selectedStellmoment != null
				&& app.selectedStellmoment.length() != 0) {
			String limits[] = app.selectedStellmoment.split("-");
			selection += "cast((REPLACE("
					+ GraupnerContentProvider.STELLMOMENT_6_0V
					+ ",',','.')) AS FLOAT)" + " >= ? AND " + "cast((REPLACE("
					+ GraupnerContentProvider.STELLMOMENT_6_0V
					+ ",',','.')) AS FLOAT)" + " <= ? AND ";
			params.add(limits[0]);
			params.add(limits[1]);
		}

		if (app.selectedGewicht != null && app.selectedGewicht.length() != 0) {
			selection += "cast((REPLACE(" + GraupnerContentProvider.GEWICHT
					+ ",',','.')) AS FLOAT)" + " <= ?  AND ";
			params.add(app.selectedGewicht);
		}
		if (app.size2 != null && app.size2.length() != 0) {
			selection += "cast((REPLACE(" + GraupnerContentProvider.SIZE2
					+ ",',','.')) AS FLOAT)" + " <= ?  AND ";
			params.add(app.size2);
		}
		if (app.selectedHVValue != null && app.selectedHVValue.length() != 0) {
			selection += GraupnerContentProvider.HV + " = ? AND ";
			params.add(app.selectedHVValue);
		}
		if (app.selectedBrushlessValue != null
				&& app.selectedBrushlessValue.length() != 0) {
			selection += GraupnerContentProvider.BS + " = ? AND ";
			params.add(app.selectedBrushlessValue);
		}
		if (app.selectedLagerung != null && app.selectedLagerung.length() != 0) {
			selection += GraupnerContentProvider.LAGERUNG + " = ? AND ";
			params.add(app.selectedLagerung);
		}
		if (app.selectedGetriebe != null && app.selectedGetriebe.length() != 0) {
			selection += GraupnerContentProvider.GETRIEBE + " = ? AND ";
			params.add(app.selectedGetriebe);
		}
		selection += "1";
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
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Kein Ergebnis gefunden");
			builder.setNegativeButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// here you can add functions
							dialog.dismiss();
						}
					});
			AlertDialog alert = builder.create();
			alert.show();
		}
	}

	public void gotoFaceBook(View v) {
		Intent i = new Intent();
		i.setClass(this, WebViewer.class);
		startActivity(i);
	}

	public void resetAll() {
		this.resetValues();
		this.resetAllButtons();
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
		TextView txt = (TextView) findViewById(R.id.txtFilterHV);
		TextView txt2 = (TextView) findViewById(R.id.txtFilterLagerung);
		TextView txt3 = (TextView) findViewById(R.id.txtFilterGetriebe);
		txt.setText("-");
		txt2.setText("-");
		txt3.setText("-");
		txt = (TextView) findViewById(R.id.txtFilterGrosse);
		txt.setText("-");
		txt = (TextView) findViewById(R.id.txtFilterStellzeit);
		txt.setText("-");
		txt = (TextView) findViewById(R.id.txtFilterStellmoment);
		txt.setText("-");
		txt = (TextView) findViewById(R.id.txtFilterGewicht);
		txt.setText("-");
		txt = (TextView) findViewById(R.id.txtFilterBrushless);
		txt.setText("-");
	}

	public void resetAllButtons() {
		Button btn = (Button) findViewById(R.id.btn_grosse);
		btn.setSelected(false);

		btn = (Button) findViewById(R.id.btn_gewicht);
		btn.setSelected(false);

		btn = (Button) findViewById(R.id.btn_haltemoment);
		btn.setSelected(false);

		btn = (Button) findViewById(R.id.btn_stellmoment);
		btn.setSelected(false);

		btn = (Button) findViewById(R.id.btn_LagerungGetriebe);
		btn.setSelected(false);

		btn = (Button) findViewById(R.id.btn_HVBrushless);
		btn.setSelected(false);
	}
}
