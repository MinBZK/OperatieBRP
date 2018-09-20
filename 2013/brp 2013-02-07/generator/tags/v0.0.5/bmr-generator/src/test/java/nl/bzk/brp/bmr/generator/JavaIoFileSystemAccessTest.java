/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import nl.bzk.brp.bmr.generator.utils.JavaIoFileSystemAccess;

import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test voor de {@link JavaIoFileSystemAccess} class.
 */
public class JavaIoFileSystemAccessTest {

    /**
     * Test dat een file in een bestaande directory geschreven kan worden.
     *
     * @throws IOException als de geschreven file niet teruggelezen kan worden.
     */
    @Test
    public void testGenerateFile() throws IOException {
        JavaIoFileSystemAccess fsa = new JavaIoFileSystemAccess(".");
        fsa.generateFile("FileSystemAccessContents.txt", "Dit is de inhoud");
        File file = new File("./FileSystemAccessContents.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String inhoud = reader.readLine();
        reader.close();
        file.delete();
        Assert.assertEquals("Dit is de inhoud", inhoud);
    }
}
