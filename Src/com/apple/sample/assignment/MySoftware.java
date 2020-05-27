package com.apple.sample.assignment;

import java.util.ArrayList;
import java.util.List;

public class MySoftware {
	
	String swName;
	List<String> depencySWList=new ArrayList<>();
	
	
	
	public String getSwName() {
		return swName;
	}
	public void setSwName(String swName) {
		this.swName = swName;
	}
	
	public void addDepencySW(String depencySWNm)
	{
		depencySWList.add(depencySWNm);
	}
	public List<String> getDepencySWList()
	{
		return this.depencySWList;
	}
	

}
