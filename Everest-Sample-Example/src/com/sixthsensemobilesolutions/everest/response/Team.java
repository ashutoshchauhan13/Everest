package com.sixthsensemobilesolutions.everest.response;

import java.util.Arrays;


public class Team {
	private String name;
	private String[] users;

	public String getName() {
		return this.name;
	}


	public String[]  getUsers() {
		return this.users;
	}


	@Override
	public String toString() {
		return "\nTeam [name=" + name + ", users=" + Arrays.asList(users) + "]\n";
	}
}
