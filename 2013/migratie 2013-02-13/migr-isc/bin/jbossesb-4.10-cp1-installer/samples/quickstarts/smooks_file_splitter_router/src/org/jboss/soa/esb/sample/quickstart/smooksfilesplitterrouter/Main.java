/*
 * JBoss, Home of Professional Open Source Copyright 2006, JBoss Inc., and
 * individual contributors as indicated by the @authors tag. See the
 * copyright.txt in the distribution for a full listing of individual
 * contributors.
 * 
 * This is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This software is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this software; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA, or see the FSF
 * site: http://www.fsf.org.
 */
package org.jboss.soa.esb.sample.quickstart.smooksfilesplitterrouter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * 
 * @author <a href="mailto:daniel.bevenius@gmail.com">Daniel Bevenius</a>			
 *
 */
public class Main
{
	private static final String LINE_SEP = System.getProperty( "line.separator" );
	
	public static void main( String[] args  ) throws IOException
	{
		if ( args.length != 1 )
		{
			printUsage();
			return;
		}
		
		String fileName = args[0];
		System.out.println(fileName);
		System.out.println("Please specify nr of order-items to generate");
		System.out.print(">");
		//String nrofLineItems = getInput("Please specify nr of order-items to generate >");
		//InputOrderGenerator.main( new String{} { fileName, nrofLineItems );
	}
	
	private static String getInput( final String message) 
	{
		String line = null;
        try 
        {
            BufferedReader in = new BufferedReader( new InputStreamReader( System.in ) );
            line = in.readLine();
        } 
        catch (IOException e) 
        {
        	e.printStackTrace();
        }
        return line;
    }
	
	public static void printUsage()
	{
		System.err.println( "Usage: Main filename");
	}

}
