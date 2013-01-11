/*
 ******************************************************************************
 * Parts of this code sample are licensed under Apache License, Version 2.0   *
 * Copyright (c) 2009, Android Open Handset Alliance. All rights reserved.    *
 *																			  *																			*
 * Except as noted, this code sample is offered under a modified BSD license. *
 * Copyright (C) 2010, Motorola Mobility, Inc. All rights reserved.           *
 * 																			  *
 * For more details, see MOTODEV_Studio_for_Android_LicenseNotices.pdf        * 
 * in your installation folder.                                               *
 ******************************************************************************
 */
package com.graupnerservo;

import java.util.*;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.net.*;
import android.text.*;

public class GraupnerContentProvider extends ContentProvider {

	private DBOpener dbHelper;
	private static HashMap<String, String> GRAUPNER_PROJECTION_MAP;
	private static final String TABLE_NAME = "graupner";
	private static final String AUTHORITY = "com.graupnerservo.graupnercontentprovider";

	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + TABLE_NAME);
	public static final Uri SERVO_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/servo");
	public static final Uri ITEM_NUMBER_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/item_number");
	public static final Uri IMAGE_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/image");
	public static final Uri PRICE_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/price");
	public static final Uri SIZE1_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/size1");
	public static final Uri SIZE2_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/size2");
	public static final Uri SIZE3_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/size3");
	public static final Uri LAGERUNG_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/lagerung");
	public static final Uri GETRIEBE_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/getriebe");
	public static final Uri STELLMOMENT_4_8V_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/stellmoment_4_8v");
	public static final Uri STELLMOMENT_6_0V_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/stellmoment_6_0v");
	public static final Uri STELLMOMENT_7_4_VOLT_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/stellmoment_7_4_volt");
	public static final Uri HALTEMOMENT_4_8V_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/haltemoment_4_8v");
	public static final Uri HALTEMOMENT_6_0V_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/haltemoment_6_0v");
	public static final Uri HALTEMOMENT_7_4V_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/haltemoment_7_4v");
	public static final Uri STELLZEIT_4_8V_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/stellzeit_4_8v");
	public static final Uri STELLZEIT_6_0V_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/stellzeit_6_0v");
	public static final Uri STELLZEIT_7_4V_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/stellzeit_7_4v");
	public static final Uri GEWICHT_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/gewicht");
	public static final Uri HV_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/hv");
	public static final Uri BS_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/bs");
	public static final Uri FEATURE_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/feature");
	public static final Uri TEXT_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/text");

	public static final String DEFAULT_SORT_ORDER = "Servo ASC";

	private static final UriMatcher URL_MATCHER;

	private static final int GRAUPNER = 1;
	private static final int GRAUPNER_SERVO = 2;
	private static final int GRAUPNER_ITEM_NUMBER = 3;
	private static final int GRAUPNER_IMAGE = 4;
	private static final int GRAUPNER_PRICE = 5;
	private static final int GRAUPNER_SIZE1 = 6;
	private static final int GRAUPNER_SIZE2 = 7;
	private static final int GRAUPNER_SIZE3 = 8;
	private static final int GRAUPNER_LAGERUNG = 9;
	private static final int GRAUPNER_GETRIEBE = 10;
	private static final int GRAUPNER_STELLMOMENT_4_8V = 11;
	private static final int GRAUPNER_STELLMOMENT_6_0V = 12;
	private static final int GRAUPNER_STELLMOMENT_7_4_VOLT = 13;
	private static final int GRAUPNER_HALTEMOMENT_4_8V = 14;
	private static final int GRAUPNER_HALTEMOMENT_6_0V = 15;
	private static final int GRAUPNER_HALTEMOMENT_7_4V = 16;
	private static final int GRAUPNER_STELLZEIT_4_8V = 17;
	private static final int GRAUPNER_STELLZEIT_6_0V = 18;
	private static final int GRAUPNER_STELLZEIT_7_4V = 19;
	private static final int GRAUPNER_GEWICHT = 20;
	private static final int GRAUPNER_HV = 21;
	private static final int GRAUPNER_BS = 22;
	private static final int GRAUPNER_FEATURE = 23;
	private static final int GRAUPNER_TEXT = 24;

	// Content values keys (using column names)
	public static final String SERVO = "Servo";
	public static final String ITEM_NUMBER = "Item_number";
	public static final String IMAGE = "Image";
	public static final String PRICE = "Price";
	public static final String SIZE1 = "Size1";
	public static final String SIZE2 = "Size2";
	public static final String SIZE3 = "Size3";
	public static final String LAGERUNG = "Lagerung";
	public static final String GETRIEBE = "Getriebe";
	public static final String STELLMOMENT_4_8V = "Stellmoment_4_8V";
	public static final String STELLMOMENT_6_0V = "Stellmoment_6_0V";
	public static final String STELLMOMENT_7_4_VOLT = "Stellmoment_7_4_Volt";
	public static final String HALTEMOMENT_4_8V = "Haltemoment_4_8V";
	public static final String HALTEMOMENT_6_0V = "Haltemoment_6_0V";
	public static final String HALTEMOMENT_7_4V = "Haltemoment_7_4V";
	public static final String STELLZEIT_4_8V = "Stellzeit_4_8V";
	public static final String STELLZEIT_6_0V = "Stellzeit_6_0V";
	public static final String STELLZEIT_7_4V = "Stellzeit_7_4V";
	public static final String GEWICHT = "Gewicht";
	public static final String HV = "HV";
	public static final String BS = "BS";
	public static final String FEATURE = "Feature";
	public static final String TEXT = "Text";

	public boolean onCreate() {
		dbHelper = new DBOpener(getContext(), true);
		return (dbHelper == null) ? false : true;
	}

	public Cursor query(Uri url, String[] projection, String selection,
			String[] selectionArgs, String sort) {
		SQLiteDatabase mDB = dbHelper.getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		switch (URL_MATCHER.match(url)) {
		case GRAUPNER:
			qb.setTables(TABLE_NAME);
			qb.setProjectionMap(GRAUPNER_PROJECTION_MAP);
			break;
		case GRAUPNER_SERVO:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("servo='" + url.getPathSegments().get(2) + "'");
			break;
		case GRAUPNER_ITEM_NUMBER:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("item_number='" + url.getPathSegments().get(2) + "'");
			break;
		case GRAUPNER_IMAGE:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("image='" + url.getPathSegments().get(2) + "'");
			break;
		case GRAUPNER_PRICE:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("price='" + url.getPathSegments().get(2) + "'");
			break;
		case GRAUPNER_SIZE1:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("size1='" + url.getPathSegments().get(2) + "'");
			break;
		case GRAUPNER_SIZE2:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("size2='" + url.getPathSegments().get(2) + "'");
			break;
		case GRAUPNER_SIZE3:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("size3='" + url.getPathSegments().get(2) + "'");
			break;
		case GRAUPNER_LAGERUNG:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("lagerung='" + url.getPathSegments().get(2) + "'");
			break;
		case GRAUPNER_GETRIEBE:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("getriebe='" + url.getPathSegments().get(2) + "'");
			break;
		case GRAUPNER_STELLMOMENT_4_8V:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("stellmoment_4_8v='" + url.getPathSegments().get(2)
					+ "'");
			break;
		case GRAUPNER_STELLMOMENT_6_0V:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("stellmoment_6_0v='" + url.getPathSegments().get(2)
					+ "'");
			break;
		case GRAUPNER_STELLMOMENT_7_4_VOLT:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("stellmoment_7_4_volt='"
					+ url.getPathSegments().get(2) + "'");
			break;
		case GRAUPNER_HALTEMOMENT_4_8V:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("haltemoment_4_8v='" + url.getPathSegments().get(2)
					+ "'");
			break;
		case GRAUPNER_HALTEMOMENT_6_0V:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("haltemoment_6_0v='" + url.getPathSegments().get(2)
					+ "'");
			break;
		case GRAUPNER_HALTEMOMENT_7_4V:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("haltemoment_7_4v='" + url.getPathSegments().get(2)
					+ "'");
			break;
		case GRAUPNER_STELLZEIT_4_8V:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("stellzeit_4_8v='" + url.getPathSegments().get(2)
					+ "'");
			break;
		case GRAUPNER_STELLZEIT_6_0V:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("stellzeit_6_0v='" + url.getPathSegments().get(2)
					+ "'");
			break;
		case GRAUPNER_STELLZEIT_7_4V:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("stellzeit_7_4v='" + url.getPathSegments().get(2)
					+ "'");
			break;
		case GRAUPNER_GEWICHT:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("gewicht='" + url.getPathSegments().get(2) + "'");
			break;
		case GRAUPNER_HV:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("hv='" + url.getPathSegments().get(2) + "'");
			break;
		case GRAUPNER_BS:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("bs='" + url.getPathSegments().get(2) + "'");
			break;
		case GRAUPNER_FEATURE:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("feature='" + url.getPathSegments().get(2) + "'");
			break;
		case GRAUPNER_TEXT:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("text='" + url.getPathSegments().get(2) + "'");
			break;

		default:
			throw new IllegalArgumentException("Unknown URL " + url);
		}
		String orderBy = "";
		if (TextUtils.isEmpty(sort)) {
			orderBy = DEFAULT_SORT_ORDER;
		} else {
			orderBy = sort;
		}
		qb.setDistinct(true);
		Cursor c = qb.query(mDB, projection, selection, selectionArgs, null,
				null, orderBy);
		c.setNotificationUri(getContext().getContentResolver(), url);
		return c;
	}

	public String getType(Uri url) {
		switch (URL_MATCHER.match(url)) {
		case GRAUPNER:
			return "vnd.android.cursor.dir/vnd.com.graupnerservo.graupner";
		case GRAUPNER_SERVO:
			return "vnd.android.cursor.item/vnd.com.graupnerservo.graupner";
		case GRAUPNER_ITEM_NUMBER:
			return "vnd.android.cursor.item/vnd.com.graupnerservo.graupner";
		case GRAUPNER_IMAGE:
			return "vnd.android.cursor.item/vnd.com.graupnerservo.graupner";
		case GRAUPNER_PRICE:
			return "vnd.android.cursor.item/vnd.com.graupnerservo.graupner";
		case GRAUPNER_SIZE1:
			return "vnd.android.cursor.item/vnd.com.graupnerservo.graupner";
		case GRAUPNER_SIZE2:
			return "vnd.android.cursor.item/vnd.com.graupnerservo.graupner";
		case GRAUPNER_SIZE3:
			return "vnd.android.cursor.item/vnd.com.graupnerservo.graupner";
		case GRAUPNER_LAGERUNG:
			return "vnd.android.cursor.item/vnd.com.graupnerservo.graupner";
		case GRAUPNER_GETRIEBE:
			return "vnd.android.cursor.item/vnd.com.graupnerservo.graupner";
		case GRAUPNER_STELLMOMENT_4_8V:
			return "vnd.android.cursor.item/vnd.com.graupnerservo.graupner";
		case GRAUPNER_STELLMOMENT_6_0V:
			return "vnd.android.cursor.item/vnd.com.graupnerservo.graupner";
		case GRAUPNER_STELLMOMENT_7_4_VOLT:
			return "vnd.android.cursor.item/vnd.com.graupnerservo.graupner";
		case GRAUPNER_HALTEMOMENT_4_8V:
			return "vnd.android.cursor.item/vnd.com.graupnerservo.graupner";
		case GRAUPNER_HALTEMOMENT_6_0V:
			return "vnd.android.cursor.item/vnd.com.graupnerservo.graupner";
		case GRAUPNER_HALTEMOMENT_7_4V:
			return "vnd.android.cursor.item/vnd.com.graupnerservo.graupner";
		case GRAUPNER_STELLZEIT_4_8V:
			return "vnd.android.cursor.item/vnd.com.graupnerservo.graupner";
		case GRAUPNER_STELLZEIT_6_0V:
			return "vnd.android.cursor.item/vnd.com.graupnerservo.graupner";
		case GRAUPNER_STELLZEIT_7_4V:
			return "vnd.android.cursor.item/vnd.com.graupnerservo.graupner";
		case GRAUPNER_GEWICHT:
			return "vnd.android.cursor.item/vnd.com.graupnerservo.graupner";
		case GRAUPNER_HV:
			return "vnd.android.cursor.item/vnd.com.graupnerservo.graupner";
		case GRAUPNER_BS:
			return "vnd.android.cursor.item/vnd.com.graupnerservo.graupner";
		case GRAUPNER_FEATURE:
			return "vnd.android.cursor.item/vnd.com.graupnerservo.graupner";
		case GRAUPNER_TEXT:
			return "vnd.android.cursor.item/vnd.com.graupnerservo.graupner";

		default:
			throw new IllegalArgumentException("Unknown URL " + url);
		}
	}

	public Uri insert(Uri url, ContentValues initialValues) {
		SQLiteDatabase mDB = dbHelper.getWritableDatabase();
		long rowID;
		ContentValues values;
		if (initialValues != null) {
			values = new ContentValues(initialValues);
		} else {
			values = new ContentValues();
		}
		if (URL_MATCHER.match(url) != GRAUPNER) {
			throw new IllegalArgumentException("Unknown URL " + url);
		}

		rowID = mDB.insert("graupner", "graupner", values);
		if (rowID > 0) {
			Uri uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
			getContext().getContentResolver().notifyChange(uri, null);
			return uri;
		}
		throw new SQLException("Failed to insert row into " + url);
	}

	public int delete(Uri url, String where, String[] whereArgs) {
		SQLiteDatabase mDB = dbHelper.getWritableDatabase();
		int count;
		String segment = "";
		switch (URL_MATCHER.match(url)) {
		case GRAUPNER:
			count = mDB.delete(TABLE_NAME, where, whereArgs);
			break;
		case GRAUPNER_SERVO:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"servo="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_ITEM_NUMBER:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"item_number="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_IMAGE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"image="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_PRICE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"price="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_SIZE1:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"size1="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_SIZE2:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"size2="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_SIZE3:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"size3="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_LAGERUNG:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"lagerung="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_GETRIEBE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"getriebe="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_STELLMOMENT_4_8V:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"stellmoment_4_8v="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_STELLMOMENT_6_0V:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"stellmoment_6_0v="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_STELLMOMENT_7_4_VOLT:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(
					TABLE_NAME,
					"stellmoment_7_4_volt="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_HALTEMOMENT_4_8V:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"haltemoment_4_8v="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_HALTEMOMENT_6_0V:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"haltemoment_6_0v="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_HALTEMOMENT_7_4V:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"haltemoment_7_4v="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_STELLZEIT_4_8V:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"stellzeit_4_8v="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_STELLZEIT_6_0V:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"stellzeit_6_0v="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_STELLZEIT_7_4V:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"stellzeit_7_4v="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_GEWICHT:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"gewicht="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_HV:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"hv="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_BS:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"bs="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_FEATURE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"feature="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_TEXT:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"text="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;

		default:
			throw new IllegalArgumentException("Unknown URL " + url);
		}
		getContext().getContentResolver().notifyChange(url, null);
		return count;
	}

	public int update(Uri url, ContentValues values, String where,
			String[] whereArgs) {
		SQLiteDatabase mDB = dbHelper.getWritableDatabase();
		int count;
		String segment = "";
		switch (URL_MATCHER.match(url)) {
		case GRAUPNER:
			count = mDB.update(TABLE_NAME, values, where, whereArgs);
			break;
		case GRAUPNER_SERVO:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"servo="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_ITEM_NUMBER:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"item_number="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_IMAGE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"image="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_PRICE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"price="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_SIZE1:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"size1="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_SIZE2:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"size2="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_SIZE3:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"size3="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_LAGERUNG:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"lagerung="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_GETRIEBE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"getriebe="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_STELLMOMENT_4_8V:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"stellmoment_4_8v="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_STELLMOMENT_6_0V:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"stellmoment_6_0v="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_STELLMOMENT_7_4_VOLT:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(
					TABLE_NAME,
					values,
					"stellmoment_7_4_volt="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_HALTEMOMENT_4_8V:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"haltemoment_4_8v="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_HALTEMOMENT_6_0V:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"haltemoment_6_0v="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_HALTEMOMENT_7_4V:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"haltemoment_7_4v="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_STELLZEIT_4_8V:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"stellzeit_4_8v="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_STELLZEIT_6_0V:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"stellzeit_6_0v="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_STELLZEIT_7_4V:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"stellzeit_7_4v="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_GEWICHT:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"gewicht="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_HV:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"hv="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_BS:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"bs="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_FEATURE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"feature="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case GRAUPNER_TEXT:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"text="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;

		default:
			throw new IllegalArgumentException("Unknown URL " + url);
		}
		getContext().getContentResolver().notifyChange(url, null);
		return count;
	}

	static {
		URL_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase(), GRAUPNER);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/servo"
				+ "/*", GRAUPNER_SERVO);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/item_number"
				+ "/*", GRAUPNER_ITEM_NUMBER);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/image"
				+ "/*", GRAUPNER_IMAGE);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/price"
				+ "/*", GRAUPNER_PRICE);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/size1"
				+ "/*", GRAUPNER_SIZE1);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/size2"
				+ "/*", GRAUPNER_SIZE2);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/size3"
				+ "/*", GRAUPNER_SIZE3);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/lagerung"
				+ "/*", GRAUPNER_LAGERUNG);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/getriebe"
				+ "/*", GRAUPNER_GETRIEBE);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase()
				+ "/stellmoment_4_8v" + "/*", GRAUPNER_STELLMOMENT_4_8V);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase()
				+ "/stellmoment_6_0v" + "/*", GRAUPNER_STELLMOMENT_6_0V);
		URL_MATCHER
				.addURI(AUTHORITY, TABLE_NAME.toLowerCase()
						+ "/stellmoment_7_4_volt" + "/*",
						GRAUPNER_STELLMOMENT_7_4_VOLT);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase()
				+ "/haltemoment_4_8v" + "/*", GRAUPNER_HALTEMOMENT_4_8V);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase()
				+ "/haltemoment_6_0v" + "/*", GRAUPNER_HALTEMOMENT_6_0V);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase()
				+ "/haltemoment_7_4v" + "/*", GRAUPNER_HALTEMOMENT_7_4V);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase()
				+ "/stellzeit_4_8v" + "/*", GRAUPNER_STELLZEIT_4_8V);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase()
				+ "/stellzeit_6_0v" + "/*", GRAUPNER_STELLZEIT_6_0V);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase()
				+ "/stellzeit_7_4v" + "/*", GRAUPNER_STELLZEIT_7_4V);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/gewicht"
				+ "/*", GRAUPNER_GEWICHT);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/hv" + "/*",
				GRAUPNER_HV);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/bs" + "/*",
				GRAUPNER_BS);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/feature"
				+ "/*", GRAUPNER_FEATURE);
		URL_MATCHER.addURI(AUTHORITY,
				TABLE_NAME.toLowerCase() + "/text" + "/*", GRAUPNER_TEXT);

		GRAUPNER_PROJECTION_MAP = new HashMap<String, String>();
		GRAUPNER_PROJECTION_MAP.put(SERVO, "servo");
		GRAUPNER_PROJECTION_MAP.put(ITEM_NUMBER, "item_number");
		GRAUPNER_PROJECTION_MAP.put(IMAGE, "image");
		GRAUPNER_PROJECTION_MAP.put(PRICE, "price");
		GRAUPNER_PROJECTION_MAP.put(SIZE1, "size1");
		GRAUPNER_PROJECTION_MAP.put(SIZE2, "size2");
		GRAUPNER_PROJECTION_MAP.put(SIZE3, "size3");
		GRAUPNER_PROJECTION_MAP.put(LAGERUNG, "lagerung");
		GRAUPNER_PROJECTION_MAP.put(GETRIEBE, "getriebe");
		GRAUPNER_PROJECTION_MAP.put(STELLMOMENT_4_8V, "stellmoment_4_8v");
		GRAUPNER_PROJECTION_MAP.put(STELLMOMENT_6_0V, "stellmoment_6_0v");
		GRAUPNER_PROJECTION_MAP.put(STELLMOMENT_7_4_VOLT,
				"stellmoment_7_4_volt");
		GRAUPNER_PROJECTION_MAP.put(HALTEMOMENT_4_8V, "haltemoment_4_8v");
		GRAUPNER_PROJECTION_MAP.put(HALTEMOMENT_6_0V, "haltemoment_6_0v");
		GRAUPNER_PROJECTION_MAP.put(HALTEMOMENT_7_4V, "haltemoment_7_4v");
		GRAUPNER_PROJECTION_MAP.put(STELLZEIT_4_8V, "stellzeit_4_8v");
		GRAUPNER_PROJECTION_MAP.put(STELLZEIT_6_0V, "stellzeit_6_0v");
		GRAUPNER_PROJECTION_MAP.put(STELLZEIT_7_4V, "stellzeit_7_4v");
		GRAUPNER_PROJECTION_MAP.put(GEWICHT, "gewicht");
		GRAUPNER_PROJECTION_MAP.put(HV, "hv");
		GRAUPNER_PROJECTION_MAP.put(BS, "bs");
		GRAUPNER_PROJECTION_MAP.put(FEATURE, "feature");
		GRAUPNER_PROJECTION_MAP.put(TEXT, "text");

	}
}
