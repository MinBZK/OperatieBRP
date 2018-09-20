/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import nl.bzk.brp.bmr.generator.utils.ExtensionPointFileSystemAccess;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ExtensionPointFileSystemAccessTest {

    private static final String    TEMP_DIR              = System.getProperty("java.io.tmpdir");
    private static final String    SOURCE_DIRECTORY_NAAM = TEMP_DIR + "/ExtensionPointFileSystemAccessTest/source";
    private static final String    TARGET_DIRECTORY_NAAM = TEMP_DIR + "/ExtensionPointFileSystemAccessTest/target";
    private File                   sourceDirectory       = null;
    private File                   targetDirectory       = null;
    ExtensionPointFileSystemAccess access                = new ExtensionPointFileSystemAccess(TARGET_DIRECTORY_NAAM,
                                                                 SOURCE_DIRECTORY_NAAM);

    @Before
    public void setUp() throws Exception {
        sourceDirectory = new File(SOURCE_DIRECTORY_NAAM);
        targetDirectory = new File(TARGET_DIRECTORY_NAAM);
        Assert.assertTrue(sourceDirectory.mkdirs());
        Assert.assertTrue(targetDirectory.mkdirs());
    }

    @After
    public void tearDown() throws Exception {
        deleteRecursive(sourceDirectory.getParentFile());
    }

    /**
     * Test de method {@link ExtensionPointFileSystemAccess#exists(String)}.
     *
     * @throws Exception als er iets mislukt met filesystem access.
     */
    @Test
    public void testExists() throws Exception {
        File file = new File(sourceDirectory, "Bestaat");
        Assert.assertTrue(file.createNewFile());
        Assert.assertTrue(access.exists("Bestaat"));
        Assert.assertFalse(access.exists("BestaatNiet"));
    }

    /**
     * Test dat een file in de target directory wordt gegenereerd en niet in de source directory.
     *
     * @throws Exception  als er iets mislukt met filesystem access.
     */
    @Test
    public void testExtensionPointExists() throws Exception {
        String fileName = "SourceFile.java";
        String sourceCode = "public class SourceFile{}";
        new File(sourceDirectory, fileName).createNewFile();
        access.generateFile(fileName, sourceCode);
        Assert.assertFalse(new File(targetDirectory, fileName).exists());
        Assert.assertTrue(new File(sourceDirectory, fileName).exists());
    }

    /**
     * Test dat, als een file al in de source directory aanwezig is, die niet ook nog in de target directory wordt gegenereerd.
     *
     * @throws Exception
     */
    @Test
    public void testGenerateFile() throws Exception {
        String fileName = "SourceFile.java";
        String sourceCode = "public class SourceFile{}";
        access.generateFile(fileName, sourceCode);
        Assert.assertTrue(new File(targetDirectory, fileName).exists());
        Assert.assertFalse(new File(sourceDirectory, fileName).exists());
        BufferedReader reader = new BufferedReader(new FileReader(new File(targetDirectory, fileName)));
        Assert.assertEquals(sourceCode, reader.readLine());
        reader.close();
    }

    private void deleteRecursive(final File directory) {
        if ((directory != null) && directory.exists()) {
            for (File file : directory.listFiles()) {
                if (file.isDirectory()) {
                    deleteRecursive(file);
                }
                file.delete();
            }
            directory.delete();
        }
    }

}
