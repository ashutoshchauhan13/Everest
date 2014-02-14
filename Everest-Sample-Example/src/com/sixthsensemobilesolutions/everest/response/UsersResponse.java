
package com.sixthsensemobilesolutions.everest.response;

import java.util.List;

public class UsersResponse{
   	private List<Users> users;

 	public List<Users> getUsers(){
		return this.users;
	}
	public void setUsers(List<Users> users){
		this.users = users;
	}
	@Override
	public String toString() {
		return "UsersResponse [users=" + users + "]";
	}
}
