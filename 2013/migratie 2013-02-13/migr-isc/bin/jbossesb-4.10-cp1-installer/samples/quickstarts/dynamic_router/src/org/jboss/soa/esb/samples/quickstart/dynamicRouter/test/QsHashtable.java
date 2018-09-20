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
 
package  org.jboss.soa.esb.samples.quickstart.dynamicRouter.test;

import java.util.Hashtable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.Enumeration;

public class QsHashtable {

	public static void writeHash(Hashtable theHashtable) {
		try {
			FileOutputStream fileOut = new FileOutputStream("hashtable.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(theHashtable);
			
			System.out.println("Writing Hashtable to disk");
			for (Enumeration e = theHashtable.keys(); e.hasMoreElements();) {
				String theKey = (String) e.nextElement();
				String theValue = (String) theHashtable.get(theKey);
				System.out.println("\tHashtable entry: key = " + theKey + ", value = " + theValue);
            }
			out.close();
			fileOut.close();
		} 
		catch (FileNotFoundException E) {
			E.printStackTrace();
		} 
		catch (IOException E) {
			E.printStackTrace();
		}

	} /* method */

	public static Hashtable readHash() {
		Hashtable theHashtable = new Hashtable();

		try {
			FileInputStream fileIn = new FileInputStream("hashtable.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			theHashtable = (Hashtable) in.readObject();
			in.close();
			fileIn.close();
		} 
		catch (ClassNotFoundException E) {
			E.printStackTrace();
		} 
		catch (FileNotFoundException E) {
			// If the file is not there - it's not a problem as we'll create it
		} 
		catch (IOException E) {
			E.printStackTrace();
		}

		return theHashtable;

	} /* method */

} /* class */