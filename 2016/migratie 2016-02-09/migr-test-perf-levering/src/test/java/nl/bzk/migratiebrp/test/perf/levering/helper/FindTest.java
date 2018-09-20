/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.perf.levering.helper;

import java.io.File;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.junit.Test;

//@org.junit.Ignore
public class FindTest {

    // final String baseDir = "P:\\jboss-soa-p-5\\jboss-as\\client";
    final String baseDir = "D:\\mGBA\\jboss-soa-p-5\\jboss-as\\server\\default";
    final String filename = "TransformerFactory.class";

    @Test
    public void find() throws Exception {
        System.out.println("Start");
        FindTest.find(new File(baseDir), filename);
        System.out.println("Done");
    }

    private static void find(final File file, final String filename) throws Exception {
        if (file.exists()) {
            if (file.isDirectory()) {
                FindTest.findInDirectory(file, filename);
            } else if (file.isFile()) {
                FindTest.findInFile(file, filename);
            }
        }
    }

    private static void findInDirectory(final File directory, final String filename) throws Exception {
        System.out.println("Checking directory: " + directory.getName());
        for (final File file : directory.listFiles()) {
            FindTest.find(file, filename);
        }
    }

    private static void findInFile(final File file, final String filename) throws Exception {
        if (file.getName().endsWith(".jar")) {
            FindTest.findInZipfile(file, filename);
        }
    }

    private static void findInZipfile(final File file, final String filename) throws Exception {
        System.out.println("Checking file: " + file.getName());
        try (
            final ZipFile zip = new ZipFile(file)) {
            final Enumeration<? extends ZipEntry> e = zip.entries();
            while (e.hasMoreElements()) {
                final ZipEntry entry = e.nextElement();
                if (entry.getName().contains(filename)) {
                    System.out.println("File ------------------------------------------------------------> " + entry.getName());
                }
            }
        }
    }
}
