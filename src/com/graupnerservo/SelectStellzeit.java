package com.graupnerservo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

public class SelectStellzeit extends Activity {
	protected AndroidApplication app;
	AutoCompleteTextView textView;
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO Put your code here
		app = (AndroidApplication) getApplication();
		this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.stellzeit);

		List<String> item_names = new ArrayList<String>();

		/*String[] projection = new String[] { GraupnerContentProvider.STELLZEIT_6_0V};
		String sortOrder = GraupnerContentProvider.STELLZEIT_6_0V + " asc";
		Cursor cursor = managedQuery(GraupnerContentProvider.CONTENT_URI,
				projection, null, null, sortOrder);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				//item_names.add(cursor.getString(0).replace(",","."));
				item_names.add(cursor.getString(0).replace(".",","));
			}
		}*/
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, item_names);
		textView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
		textView.setAdapter(adapter);
		textView.setText(app.selectedStellzeit);
		TextView txt = (TextView)findViewById(R.id.txtStellzeit);
		txt.setText(Html.fromHtml("Du kannst jetzt direkt die gewünschte Stellzeit<br />deines Servos eingeben (Beispiel: 0,12):"));
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
	public void selectValue(View v) {
		app.selectedStellzeit=textView.getText().toString();
		Intent intent = this.getIntent();
		this.setResult(RESULT_OK, intent);
		finish();
	}
	
	public void unSelectValue(View v) {
		app.selectedStellzeit="";
		Intent intent = this.getIntent();
		this.setResult(RESULT_OK, intent);
		finish();
	}

	public void gotoFaceBook(View v) {
		Intent i = new Intent();
		i.setClass(this, WebViewer.class);
		startActivity(i);
	}
}
