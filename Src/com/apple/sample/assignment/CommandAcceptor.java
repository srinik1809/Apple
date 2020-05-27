package com.apple.sample.assignment;

import java.util.Scanner;

import org.springframework.util.Assert;

import com.apple.sample.assignment.util.MyConstants;
import com.apple.sample.assignment.util.StringUtil;

public class CommandAcceptor {

	
	public void acceptCommand()
	{
       SoftwareInventoryOperator operator= new SoftwareInventoryOperatorImpl();
		
		// TODO Auto-generated method stub
		Scanner sc= new Scanner(System.in);
		String command="";
		while(true)
		{
			command=sc.nextLine();
			System.out.println(command);
   		 Assert.hasLength(command, "Command can't be empty string or NULL");
			 
		
				       if(command.startsWith("DEPEND"))
				       {
                          String[] commands= StringUtil.getTokens(command, MyConstants.command_delim);
                           if(commands.length >= 3)
				    	   operator.addDependency(command.substring(6).trim());
                           else
                        	   System.out.println("Invalid DEPEND Command received, Usage: DEPEND <SW Name> <Depend SW Name> <Depend SW Name> ....");  
				       }   
				       else if(command.startsWith("INSTALL"))
				       {
				    	   String[] commands= StringUtil.getTokens(command, MyConstants.command_delim);
				    	  if(commands.length==2)
				    	   operator.installSoftware(command.substring(7).trim());
				    	  else
				    		  System.out.println("Invalid INSTALL Command received, Usage: INSTALL <SW Name>");
				       }  
				       else  if(command.startsWith("REMOVE"))
				       {
				    	   String[] commands= StringUtil.getTokens(command, MyConstants.command_delim);
				    	  if(commands.length==2)
				    	   operator.removeSoftware(command.substring(7).trim());
				    	  else
				    		  System.out.println("Invalid REMOVE Command received, Usage: INSTALL <SW Name>");
				       } 
				       else  if(command.startsWith("LIST"))
				       {
				    	   operator.listInstalledSoftwares();
				       }
				       else if("END".equals(command))
				       {
				    	    sc.close();
							break;
				       }	
				       else
				    	   System.out.println("Invalid Command Received !!! Valid commands DEPEND | INSTALL | REMOVE | LIST | END"); 
				    	   
		
			
		}

	}
}
