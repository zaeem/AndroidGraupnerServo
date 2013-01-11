package com.graupnerservo;

import java.io.Serializable;

public class Servo implements Serializable{
	private static final long serialVersionUID = 4068188167994381987L;
	public String servoName;
	public String servoArticleNumber;
	public String servoImage;
	public String servoSize1;
	public String servoSize2;
	public String servoSize3;
	public String servoLagerung;
	public String servoGetribe;
	public String servoGewicht;
	
	public String servoStellmoment_4_8v;
	public String servoStellmoment_6_0v;
	public String servoStellmoment_7_4v;
	
	public String servoHaltemoment_4_8v;
	public String servoHaltemoment_6_0v;
	public String servoHaltemoment_7_4v;
	
	public String servoStellzeit_4_8v;
	public String servoStellzeit_6_0v;
	public String servoStellzeit_7_4v;
	
	public String servoHVServo;
	public String servoBrushless;
	public String servoDetailText;
	public String servoPrice;

	public Servo(){
        super();
    }
	
	public String getCombineSize(){
		return this.servoSize1+"x"+this.servoSize2+"x"+this.servoSize3;
	}
}
