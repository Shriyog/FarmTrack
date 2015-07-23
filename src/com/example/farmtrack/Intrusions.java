package com.example.farmtrack;

public class Intrusions {
	
	//private variables
	int _id;
	String iDate;
	String iTime;
	
	// Empty constructor
	public Intrusions(){
		
	}
	// constructor
	public Intrusions(int id, String name, String _phone_number){
		this._id = id;
		this.iDate = name;
		this.iTime = _phone_number;
	}
	
	// constructor
	public Intrusions(String name, String _phone_number){
		this.iDate = name;
		this.iTime = _phone_number;
	}
	// getting ID
	public int getID(){
		return this._id;
	}
	
	// setting id
	public void setID(int id){
		this._id = id;
	}
	
	// getting date
	public String getDate(){
		return this.iDate;
	}
	
	// setting date
	public void setDate(String date){
		this.iDate = date;
	}
	
	// getting time
	public String getTime(){
		return this.iTime;
	}
	
	// setting time
	public void setTime(String time){
		this.iTime = time;
	}
}
