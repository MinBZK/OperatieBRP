/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, JBoss Inc., and others contributors as indicated 
 * by the @authors tag. All rights reserved. 
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors. 
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
 * MA  02110-1301, USA.
 * 
 * (C) 2005-2006,
 * @author JBoss Inc.
 */
package org.jboss.soa.esb.samples.quickstarts.bpm_orchestration1.test;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;

public class SendTestMessage {
    
	public SendTestMessage() {
	}
	
	public void sendMessage(String inputDirectory, String fileExtension, String fileContents) {    	
    	String prefix = Long.toString(Calendar.getInstance().getTimeInMillis());
    	File x = null;
    	try {
    		x = File.createTempFile(prefix + "_",fileExtension,new File(inputDirectory));
    		BufferedWriter out = new BufferedWriter(new FileWriter(x));
    		out.write(fileContents.toCharArray());
    		out.close();
    	} catch (Exception e) {
    		System.out.println("Error while writing the file: " + x.getAbsolutePath());
    		System.out.println(e.getMessage());
    	}		
	}
	
    public static void main(String args[]) throws Exception
    {               
    	SendTestMessage stm = new SendTestMessage();
    	stm.sendMessage(args[0], args[1], args[2]);
    }
    
}
