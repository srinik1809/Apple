package com.apple.sample.assignment;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import com.apple.sample.assignment.util.MyConstants;
import com.apple.sample.assignment.util.StringUtil;

public class SoftwareInventoryOperatorImpl implements SoftwareInventoryOperator {
	
	
	//These are RAM variables but needs to be filled with DB values in real scenarios
	private List<MySoftware> mySWList=null;
	private List<String> installedSWs=null;
	
	public SoftwareInventoryOperatorImpl()
	{
		mySWList = new ArrayList<MySoftware>();
		installedSWs=new ArrayList<String>();
	}
	
	/**
	 * This method is to add dependency list to the Inventory DB to know, each software dependent on which other softwares
	 * Dependency list to be passed with agreed delimiter token
	 * @param command
	 */
	public void addDependency(String command)
	{
		Assert.notNull(command, "Empty Command Can't be accepted for DEPEND Command");
		String[] swNames=StringUtil.getTokens(command, MyConstants.command_delim);
		
		MySoftware mySW= new MySoftware();
		//Add first name as main sw name
		mySW.setSwName(swNames[0]);
		
		//If it has at least one dependency then add here
		if(swNames.length > 1)
		{
		    for(int i=1;i<swNames.length;i++)
		    	mySW.addDepencySW(swNames[i]);	
		}	
		//For now storing in RAM these Objects but here we can call our DataAccessObject(DAO) to store in RDBMS or Graph DB to store dependency tree properly
		mySWList.add(mySW);

		
	}
	/**
	 *  Get the name of the s/w to be installed. Get dependency s/w list we configured earlier and install those before installing main s/w
	 * 
	 */

	public void installSoftware(String swTobeInstalled)
	{
		Assert.notNull(swTobeInstalled, "Installing Software Name can't null");
		
		//Check whether this software has any dependency to install them first, here you can retrieve from GraphDB if we store them in DB 
		for(MySoftware obj: mySWList)
		{
			//This s/w has dependency so install those dependencies first...
			if(swTobeInstalled.equals(obj.getSwName()))
			{
			    List<String> dpList= obj.getDepencySWList();
			    for(String swNm : dpList)
			    {
			    	 install(swNm,true);
			    		
			    }
				    
			}
		}
		
		//Now install the requested s/w as all dependencies installed already
		  install(swTobeInstalled,false);
	}
	
	/**
	 * 
	 * For now this is Dummy Method but it needs to call actual install commands and throw Custom Exception in case any issues while installing requested s/w
	 */
	
	private void install(String swTobeInstalled, boolean isUtilitySW)
	{
		if(!installedSWs.contains(swTobeInstalled))
    	{
		System.out.println("  Installing  "+swTobeInstalled);
		installedSWs.add(swTobeInstalled);
    	}
		else if(!isUtilitySW)
			System.out.println("  "+swTobeInstalled+ "  is already installed");
			
	}
	
	/***
	 *  This method just lists already installed s/ws. This could be in embedded DB or some config file. For now, just stored in RAM/Memory.
	 */
	public void listInstalledSoftwares()
	{
		
		for(String swName : installedSWs)
		{
			System.out.println("  "+swName);
		}
	}
	
	/**
	 *  Takes the name of the s/w to be uninstalled. If it is main s/w and doesn't needed by any other s/w then it uninstalls 
	 *  and also checks dependent s/ws and if they also can be uninstalled and no dependency then it uninstalls them also.
	 */
	public void removeSoftware(String swNm)
	{
		//if it is not installed s/w list then just mention same and no action required.
		if(!installedSWs.contains(swNm))
		{
			System.out.println("  "+swNm+" is not installed");
			return;			
		}
		
		
		//check whether it is Main s/w and not utility s/w
		for(MySoftware obj: mySWList)
		{
			
			if(swNm.equals(obj.getSwName()))
			{
			    if(canRemoveSoftware("", swNm))
			    {
				  uninstallSoftware(swNm);
				  
			    } 
			    else
			    {
			         System.out.println("  "+swNm+ " is still Needed");
			         return;
			    }     
			    
				//Get dependency software list and see can be removed them also
				 List<String> dpList= obj.getDepencySWList();
				    for(String swTobeRemoved : dpList)
				    {
				    	if(canRemoveSoftware(swNm, swTobeRemoved))
				    		uninstallSoftware(swTobeRemoved);
				    					    			    		
				    }
				return;
			}
		}	
	if(canRemoveSoftware("", swNm)) //this could be utility s/w
		 uninstallSoftware(swNm);
     else
         System.out.println("  "+swNm+ " is still Needed");
		  	 
	}
	
	
	/**
	 * 
	 * For now this is Dummy Method but it needs to call actual uninstall commands and throw Custom Exception in case any issues while uninstalling requested s/w
	 */
	private void uninstallSoftware(String swNm)
	{
		
		System.out.println("  Removing "+swNm);
		installedSWs.remove(swNm);
	}
	
	private boolean canRemoveSoftware(String mainSWNm, String swTobeRemoved)
	{
		List<String> mainSWList= new ArrayList<String>();
		for(MySoftware obj: mySWList)
		{
			 List<String> dpList= obj.getDepencySWList();
			 for(String dpSW : dpList)
			    {
				  if(swTobeRemoved.equals(dpSW))
					  //If this main s/w needs this utility s/w and it is installed
					  if(installedSWs.contains(obj.getSwName()))
					    mainSWList.add(obj.getSwName());
			    }
		}
		
		//if more than one s/w needs this utility s/w then you can't remove
		if(mainSWList.size() >1)
		return false;
		//only one main dependent s/w is there and it is same as given main software then allow to remove this
		else if (mainSWList.size() ==1 && mainSWList.contains(mainSWNm))
			return true;
		else  //last scenario is utility software and no dependencies are there...
			return true;
	}
	
	
}
