package com.apple.sample.assignment;

public interface SoftwareInventoryOperator {
	
	
	public void addDependency(String command);
	public void installSoftware(String swTobeInstalled);
	public void listInstalledSoftwares();
	public void removeSoftware(String swNm);

}
