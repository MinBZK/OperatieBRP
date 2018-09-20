/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 * 
 */
package nl.bzk.brp.generator;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import junit.framework.Assert;
import nl.bzk.brp.generator.java.AbstractBmrTest;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * 
 * 
 */
public class ModelGeneratorTest extends AbstractBmrTest {

    @Resource
    @Qualifier("javaModelGenerator")
    ModelGenerator generator;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * Test method for
     * {@link nl.bzk.brp.generator.JavaModelGenerator#genereer(java.lang.String, java.lang.String, java.util.Properties)}.
     * 
     * @throws IOException
     * @throws MissingArgumentException
     */
    @Test
    public final void testGenereer() throws IOException, MissingArgumentException {
        List<GenerationReport> reports = null;

        String path = System.getProperty("user.dir");
        System.out.println("Current working directory : " + path);
        String testDir = FilenameUtils.concat(FilenameUtils.concat(path, "target/generated-sources"), "model");
        FileUtils.forceMkdir(new File(testDir));

        Properties properties = new Properties();
        properties.put("verbose", true);


        List<String> elements = Arrays.asList(GenerationElements.ENUMERATIONS.name().toLowerCase(),
                GenerationElements.ATTRIBUTETYPES.name().toLowerCase());
        reports = generator.genereer(testDir, testDir, GenerationTargetModel.JAVA, elements, properties);

        Assert.assertTrue(reports.size() > 0);
    }

}
