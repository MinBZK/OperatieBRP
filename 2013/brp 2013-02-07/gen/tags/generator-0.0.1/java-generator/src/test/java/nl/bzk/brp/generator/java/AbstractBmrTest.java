/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generator.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import junit.framework.Assert;
import nl.bzk.brp.metaregister.dataaccess.AttribuutTypeDao;
import nl.bzk.brp.metaregister.model.Laag;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


/**
*
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:/generator-context.xml")
@TransactionConfiguration
@Transactional
public class AbstractBmrTest extends AbstractTransactionalJUnit4SpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(AbstractBmrTest.class);

    @Before
    public void init() {
        Laag.LOGISCH.set();
    }

    @Test
    public void testSpringContext() {
        Assert.assertNotNull(this.applicationContext);
        // Verifieer dat de model generator aanwezig is
        Assert.assertTrue(this.applicationContext.containsBean("java-generator"));
        // Verifieer dat de AttribuutTypeDao er is
        Assert.assertTrue((this.applicationContext.getBeansOfType(AttribuutTypeDao.class) != null));
        logger.debug("Beans:" + StringUtils.join(this.applicationContext.getBeanDefinitionNames(), "\n"));
    }

    /**
     * Zorg ervoor dat de directory structuur waar de sources in gegenereerd moeten worden bestaat. Altijd genereren in
     * de huidige working directory met als subdirectory target/generated-sources
     * 
     * @param subDirectory de subdirectory waarin gegenereerd moet worden. Als deze leeg is, is generated-sources de
     *            directe plek
     * @return the string
     * @throws IOException Als de directory niet gemaakt kan worden wordt deze exceptie gegooid.
     */
    protected String prepareGeneratedSourcesDirectory(String subDirectory) throws IOException {

        String generatedSourcesDirectory =
            FilenameUtils.concat(System.getProperty("user.dir"), "target/generated-sources");
        if (!StringUtils.isBlank(subDirectory)) {
            generatedSourcesDirectory = FilenameUtils.concat(generatedSourcesDirectory, subDirectory);
        }
        FileUtils.forceMkdir(new File(generatedSourcesDirectory));
        return generatedSourcesDirectory;
    }

    protected String getVerwachtResultaat(final String resourceNaam) {
        InputStream inputStream = getClass().getResourceAsStream(resourceNaam);
        logger.debug("Default charset:" + Charset.defaultCharset());
        Writer writer = new StringWriter();
        try {
            IOUtils.copy(inputStream, writer, "UTF-8");
        } catch (IOException e) {
           logger.error("Fout bij het ophalen van het verwachter resultaat voor " + resourceNaam, e);
        }
        return toUnixString(writer);
    }

    protected String toUnixString(final Writer writer) {
        return writer.toString().replace("\r", "");
    }
}
