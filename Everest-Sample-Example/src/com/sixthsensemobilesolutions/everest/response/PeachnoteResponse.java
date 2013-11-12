
package com.sixthsensemobilesolutions.everest.response;


public class PeachnoteResponse{
   	private String composer;
   	private String count;
   	private String id;
   	private String pagecount;
   	private String pages;
   	private String title;
   	private String year;

 	public String getComposer(){
		return this.composer;
	}
	public void setComposer(String composer){
		this.composer = composer;
	}
 	public String getCount(){
		return this.count;
	}
	public void setCount(String count){
		this.count = count;
	}
 	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}
 	public String getPagecount(){
		return this.pagecount;
	}
	public void setPagecount(String pagecount){
		this.pagecount = pagecount;
	}
 	public String getPages(){
		return this.pages;
	}
	public void setPages(String pages){
		this.pages = pages;
	}
 	public String getTitle(){
		return this.title;
	}
	public void setTitle(String title){
		this.title = title;
	}
 	public String getYear(){
		return this.year;
	}
	public void setYear(String year){
		this.year = year;
	}
	@Override
	public String toString() {
		return "PeachnoteResponse [composer=" + composer + ", count=" + count + ", id=" + id + ", pagecount=" + pagecount + ", pages=" + pages + ", title=" + title + ", year=" + year + "]";
	}
}
