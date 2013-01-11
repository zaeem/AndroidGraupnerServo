package com.graupnerservo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class Start extends Activity {
	protected AndroidApplication app;
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO Put your code here
		app = (AndroidApplication) getApplication();
		this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.start);
	}

	/**
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();
		// TODO Put your code here
		this.resetAll();		
	}
	private void resetAll(){
		app.selectedStellzeit = "";
		app.selectedStellmoment = "";
		app.selectedGetriebe = "";
		app.selectedGewicht = "";
		app.selectedHVValue = "";
		app.selectedBrushlessValue = "";
		app.selectedLagerung = "";
		app.size2 = "";
		app.selectedText = "";
	}
	public void gotoImpressum(View v) {
		Intent i = new Intent();
		i.setClass(this, Impressum.class);
		startActivity(i);
	}
	public void gotoFaceBook(View v) {
		Intent i = new Intent();
		i.setClass(this, WebViewer.class);
		startActivity(i);
	}
	public void gotoSelectArtikelnummer(View v) {
		this.resetAll();
		Intent i = new Intent();
		i.setClass(this, Artikelnummer.class);
		startActivity(i);
	}
	public void gotoSelectOtherFeatures(View v) {
		this.resetAll();
		Intent i = new Intent();
		i.setClass(this, Home.class);
		startActivity(i);
	}
}
