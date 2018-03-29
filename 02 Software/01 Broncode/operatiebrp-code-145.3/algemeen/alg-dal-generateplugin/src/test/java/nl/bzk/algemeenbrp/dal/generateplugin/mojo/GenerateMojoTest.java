/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.generateplugin.mojo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GenerateMojoTest {

    private File sourceDirectory;
    private File targetDirectory;

    @Before
    public void setupDirectories() throws IOException {
        sourceDirectory = Files.createTempDirectory("test-in-").toFile();
        targetDirectory = Files.createTempDirectory("test-out-").toFile();
    }

    @After
    public void deleteDirectories() throws IOException {
        FileUtils.deleteDirectory(sourceDirectory);
        FileUtils.deleteDirectory(targetDirectory);
    }

    @Test
    public void test() throws IOException, MojoFailureException, MojoExecutionException {
        try (InputStream in = GenerateMojo.class.getResourceAsStream("/test/Generate.template");
             OutputStream out = new FileOutputStream(new File(sourceDirectory, "Generate.test"))) {
            IOUtils.copy(in, out);
        }

        final GenerateMojo subject = new GenerateMojo();
        subject.setSource(sourceDirectory);
        subject.setDestination(targetDirectory);
        subject.setPackageName("nl.bzk.algemeenbrp.test");
        subject.execute();

        final String result;
        try (InputStream in = new FileInputStream(new File(targetDirectory, "nl/bzk/algemeenbrp/test/Generate.test"))) {
            result = IOUtils.toString(in);
        }
        Assert.assertEquals("***before***\n"
                + "test\n"
                + "\n"
                + "***after***\n", result);
    }
}
