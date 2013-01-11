/*
 ******************************************************************************
 * Parts of this code sample are licensed under Apache License, Version 2.0   *
 * Copyright (c) 2009, Android Open Handset Alliance. All rights reserved.    *
 *                                                                            *                                                                         *
 * Except as noted, this code sample is offered under a modified BSD license. *
 * Copyright (C) 2010, Motorola Mobility, Inc. All rights reserved.           *
 *                                                                            *
 * For more details, see MOTODEV_Studio_for_Android_LicenseNotices.pdf        * 
 * in your installation folder.                                               *
 ******************************************************************************
 */

package com.graupnerservo;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.graupnerservo.R;

public class ServoResults extends ListActivity {
	protected AndroidApplication app;
	private ListView listView1;
	private ServosAdapter adapter;

	// //SELECT * FROM Vorlage where cast((REPLACE(Size1,',','.')) AS FLOAT) <=
	// 23
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		app = (AndroidApplication) getApplication();
		setContentView(R.layout.servo_results_list_view);
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
				GraupnerContentProvider.BS, GraupnerContentProvider.TEXT,
				GraupnerContentProvider.IMAGE,GraupnerContentProvider.PRICE };
		String sortOrder = GraupnerContentProvider.SERVO + " asc";
		String selection = "";
		ArrayList<String> params = new ArrayList<String>();
		if (app.selectedText != null && app.selectedText.length() != 0) {
			selection += GraupnerContentProvider.ITEM_NUMBER + " LIKE ?";
			params.add("%" + app.selectedText + "%");
		} else {
			if (app.selectedStellzeit != null
					&& app.selectedStellzeit.length() != 0) {
				selection += "cast((REPLACE("
						+ GraupnerContentProvider.STELLZEIT_6_0V
						+ ",',','.')) AS FLOAT)" + " <= ?  AND ";
				params.add(app.selectedStellzeit.replace(",", "."));
			}

			if (app.selectedStellmoment != null
					&& app.selectedStellmoment.length() != 0) {
				String limits[] = app.selectedStellmoment.split("-");
				selection += "cast((REPLACE("
						+ GraupnerContentProvider.STELLMOMENT_6_0V
						+ ",',','.')) AS FLOAT)" + " >= ? AND "
						+ "cast((REPLACE("
						+ GraupnerContentProvider.STELLMOMENT_6_0V
						+ ",',','.')) AS FLOAT)" + " <= ? AND ";
				params.add(limits[0]);
				params.add(limits[1]);
			}

			if (app.selectedGewicht != null
					&& app.selectedGewicht.length() != 0) {
				selection += "cast((REPLACE(" + GraupnerContentProvider.GEWICHT
						+ ",',','.')) AS FLOAT)" + " <= ?  AND ";
				params.add(app.selectedGewicht);
			}
			if (app.size2 != null && app.size2.length() != 0) {
				selection += "cast((REPLACE(" + GraupnerContentProvider.SIZE2
						+ ",',','.')) AS FLOAT)" + " <= ?  AND ";
				params.add(app.size2);
			}
			if (app.selectedHVValue != null
					&& app.selectedHVValue.length() != 0) {
				selection += GraupnerContentProvider.HV + " = ? AND ";
				params.add(app.selectedHVValue);
			}
			if (app.selectedBrushlessValue != null
					&& app.selectedBrushlessValue.length() != 0) {
				selection += GraupnerContentProvider.BS + " = ? AND ";
				params.add(app.selectedBrushlessValue);
			}
			if (app.selectedLagerung != null
					&& app.selectedLagerung.length() != 0) {
				selection += GraupnerContentProvider.LAGERUNG + " = ? AND ";
				params.add(app.selectedLagerung);
			}
			if (app.selectedGetriebe != null
					&& app.selectedGetriebe.length() != 0) {
				selection += GraupnerContentProvider.GETRIEBE + " = ? AND ";
				params.add(app.selectedGetriebe);
			}
			selection += "1";
		}
		String[] paramsArray = params.toArray(new String[params.size()]);
		Cursor cursor = managedQuery(GraupnerContentProvider.CONTENT_URI,
				projection, selection, paramsArray, sortOrder);
		ArrayList<Servo> data = new ArrayList<Servo>();

		if (cursor != null) {
			while (cursor.moveToNext()) {
				Servo servo = new Servo();
				servo.servoName = cursor.getString(0);
				servo.servoArticleNumber = cursor.getString(1);
				servo.servoSize1 = cursor.getString(2);
				servo.servoSize2 = cursor.getString(3);
				servo.servoSize3 = cursor.getString(4);
				servo.servoLagerung = cursor.getString(5);
				servo.servoGetribe = cursor.getString(6);
				servo.servoStellmoment_4_8v = cursor.getString(7);
				servo.servoStellmoment_6_0v = cursor.getString(8);
				servo.servoStellmoment_7_4v = cursor.getString(9);
				servo.servoHaltemoment_4_8v = cursor.getString(10);
				servo.servoHaltemoment_6_0v = cursor.getString(11);
				servo.servoHaltemoment_7_4v = cursor.getString(12);
				servo.servoStellzeit_4_8v = cursor.getString(13);
				servo.servoStellzeit_6_0v = cursor.getString(14);
				servo.servoStellzeit_7_4v = cursor.getString(15);
				servo.servoGewicht = cursor.getString(16);
				servo.servoHVServo = cursor.getString(17);
				servo.servoBrushless = cursor.getString(18);
				servo.servoDetailText = cursor.getString(19);
				servo.servoImage = cursor.getString(20);
				servo.servoPrice = cursor.getString(21);
				if (servo.servoName == null || servo.servoName.length() == 0) {
					servo.servoName = "N/A";
				}
				if (servo.servoPrice == null || servo.servoPrice.length() == 0) {
					servo.servoPrice = "N/A";
				}else{
					servo.servoPrice = servo.servoPrice+" Euro";
				}
				if (servo.servoArticleNumber == null
						|| servo.servoArticleNumber.length() == 0) {
					servo.servoArticleNumber = "N/A";
				}
				if (servo.servoGewicht == null
						|| servo.servoGewicht.length() == 0) {
					servo.servoGewicht = "N/A";
				} else {
					servo.servoGewicht = servo.servoGewicht.replace('.', ',')
							+ " g";
				}
				if (servo.servoLagerung == null
						|| servo.servoLagerung.length() == 0) {
					servo.servoLagerung = "N/A";
				}
				if (servo.servoGetribe == null
						|| servo.servoGetribe.length() == 0) {
					servo.servoGetribe = "N/A";
				}
				if (servo.servoHVServo == null
						|| servo.servoHVServo.length() == 0) {
					servo.servoHVServo = "N/A";
				}
				if (servo.servoStellmoment_4_8v == null
						|| servo.servoStellmoment_4_8v.length() == 0) {
					servo.servoStellmoment_4_8v = "N/A";
				} else {
					servo.servoStellmoment_4_8v = servo.servoStellmoment_4_8v
							.replace('.', ',') + " N/cm bei 4,8 Volt";
				}
				if (servo.servoHaltemoment_4_8v == null
						|| servo.servoHaltemoment_4_8v.length() == 0) {
					servo.servoHaltemoment_4_8v = "N/A";
				} else {
					servo.servoHaltemoment_4_8v = servo.servoHaltemoment_4_8v
							.replace('.', ',') + " N/cm bei 4,8 Volt";
				}
				if (servo.servoStellzeit_4_8v == null
						|| servo.servoStellzeit_4_8v.length() == 0) {
					servo.servoStellzeit_4_8v = "N/A";
				} else {
					servo.servoStellzeit_4_8v = servo.servoStellzeit_4_8v
							.replace('.', ',') + " sec bei 4,8 Volt";
				}
				if (servo.servoStellmoment_6_0v == null
						|| servo.servoStellmoment_6_0v.length() == 0) {
					servo.servoStellmoment_6_0v = "N/A";
				} else {
					servo.servoStellmoment_6_0v = servo.servoStellmoment_6_0v
							.replace('.', ',') + " N/cm bei 6,0 Volt";
				}
				if (servo.servoHaltemoment_6_0v == null
						|| servo.servoHaltemoment_6_0v.length() == 0) {
					servo.servoHaltemoment_6_0v = "N/A";
				} else {
					servo.servoHaltemoment_6_0v = servo.servoHaltemoment_6_0v
							.replace('.', ',') + " N/cm bei 6,0 Volt";
				}
				if (servo.servoStellzeit_6_0v == null
						|| servo.servoStellzeit_6_0v.length() == 0) {
					servo.servoStellzeit_6_0v = "N/A";
				} else {
					servo.servoStellzeit_6_0v = servo.servoStellzeit_6_0v
							.replace('.', ',') + " sec bei 6,0 Volt";
				}
				if (servo.servoStellmoment_7_4v == null
						|| servo.servoStellmoment_7_4v.length() == 0) {
					servo.servoStellmoment_7_4v = "N/A";
				} else {
					servo.servoStellmoment_7_4v = servo.servoStellmoment_7_4v
							.replace('.', ',') + " N/cm bei 7,7 Volt";
				}
				if (servo.servoHaltemoment_7_4v == null
						|| servo.servoHaltemoment_7_4v.length() == 0) {
					servo.servoHaltemoment_7_4v = "N/A";
				} else {
					servo.servoHaltemoment_7_4v = servo.servoHaltemoment_7_4v
							.replace('.', ',') + " N/cm bei 7,7 Volt";
				}
				if (servo.servoStellzeit_7_4v == null
						|| servo.servoStellzeit_7_4v.length() == 0) {
					servo.servoStellzeit_7_4v = "N/A";
				} else {
					servo.servoStellzeit_7_4v = servo.servoStellzeit_7_4v
							.replace('.', ',') + " sec bei 7,7 Volt";
				}
				if (servo.servoDetailText == null
						|| servo.servoDetailText.length() == 0) {
					servo.servoDetailText = "";
				}
				data.add(servo);
			}
		}
		adapter = new ServosAdapter(this, R.layout.row_item, data);

		listView1 = getListView();

		/*TextView header = (TextView) getLayoutInflater().inflate(
				R.layout.header, null);
		if (data.size() > 1) {
			header.setText("Es wurden " + data.size() + " Servos gefunden");
		} else {
			header.setText("Es wurde " + data.size() + " Servo gefunden");
		}
		listView1.addHeaderView(header);*/

		listView1.setAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView lv, View v, int position, long id) {
		//if (position > 0) { condition if header view is used
			Intent i = new Intent();
			i.setClass(this, ServoDetail.class);
			Servo obj = adapter.data.get(position);
			Bundle bundle = new Bundle();
			bundle.putSerializable("Servo", obj);
			i.putExtras(bundle);
			startActivity(i);
		//}
	}
	public void gotoDetail(View v){
		int position = Integer.parseInt(v.getTag().toString());
		Intent i = new Intent();
		i.setClass(this, ServoDetail.class);
		Servo obj = adapter.data.get(position);
		Bundle bundle = new Bundle();
		bundle.putSerializable("Servo", obj);
		i.putExtras(bundle);
		startActivity(i);
	}
}