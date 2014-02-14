
package com.sixthsensemobilesolutions.everest.response;


public class TeamResponse{
   	private Team team;

 	public Team getTeam(){
		return this.team;
	}
	public void setTeam(Team team){
		this.team = team;
	}
	@Override
	public String toString() {
		return "TeamResponse [team=" + team + "]";
	}
}
