package org.zique.messenger.resources.beans;

import javax.ws.rs.QueryParam;

/**
 * Used to model the third technique of using parameters. This bean's member
 * variables are initialized with the appropriate parameters based on
 * annotations. Parameters are received through the REST call.
 * 
 * @author Zique Yuutaka
 *
 */
public class MessageFilterBean {

	private @QueryParam("year") int year;
	private @QueryParam("start")int start;
	private @QueryParam("size") int size;
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	

}
