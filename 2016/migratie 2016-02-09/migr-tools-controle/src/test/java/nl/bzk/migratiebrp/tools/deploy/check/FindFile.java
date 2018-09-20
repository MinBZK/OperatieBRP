/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.deploy.check;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.junit.Assert;
import org.junit.Test;

public class FindFile {

	@Test
	public void findFileInJar() throws IOException {
		//findFileInJar("D:\\mGBA\\runtime-22\\test-tooling\\migr-test-isc\\lib", "TransactionSynchronizationRegistry.class");
		findFileInJar("D:\\mGBA\\runtime-22\\test-tooling\\migr-test-isc\\lib", "javax/transaction/");
	}

	private void findFileInJar(String directoryName, String fileName) throws IOException {
		File directory = new File(directoryName);
		Assert.assertTrue(directory.isDirectory());

		for(File file : directory.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File directory, String name) {
				return name.endsWith(".jar");
			}})) {
			
			System.out.println("Looking in: " + file.getCanonicalPath());
			JarFile jarFile = new JarFile(file);
			Enumeration<JarEntry> entries = jarFile.entries();
			while(entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				
				if(!entry.isDirectory() && entry.getName().contains(fileName)) {
					System.out.println("--------------------------------> File found: " + entry.toString());
				}
			}
		}
	}
	
}
