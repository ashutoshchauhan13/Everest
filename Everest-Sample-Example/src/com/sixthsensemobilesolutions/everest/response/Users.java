
package com.sixthsensemobilesolutions.everest.response;

import java.util.List;

public class Users{
   	private String _id;
   	private String age;
   	private String name;

 	public String get_id(){
		return this._id;
	}
	public void set_id(String _id){
		this._id = _id;
	}
 	public String getAge(){
		return this.age;
	}
	public void setAge(String age){
		this.age = age;
	}
 	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	@Override
	public String toString() {
		return "Users [_id=" + _id + ", age=" + age + ", name=" + name + "]";
	}
}
