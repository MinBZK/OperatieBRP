/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.ws;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.xml.sax.SAXException;

/**
 * Unit test om de XSD's van het koppelvlak te valideren.
 */
public class XsdValidatieTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(XsdValidatieTest.class);
    private static final String XSD_PATH = "/xsd/BRP0200/";

    private final XsdValidator xsdValidator = new XsdValidator();

    @Test
    public void testXsd() throws IOException {
        // get all the xsd files from a directory
        final Map<String, Resource> resources = getXsdFiles();
        final Map<String, String> validatieFouten = new HashMap<>();
        LOGGER.info("Aantal XSD's gevonden: {}", resources.size());
        for (final Resource resource : resources.values()) {
            LOGGER.debug("Valideer XSD '{}'", resource.getFilename());
            try {
                xsdValidator.valideer(null, resource, resources);
                LOGGER.info("XSD '{}' is valide", resource.getFilename());
            } catch (final SAXException e) {
                LOGGER.error("XSD {} valideert niet", resource.getFilename(), e);
                validatieFouten.put(resource.getFilename(), e.getMessage());
            } catch (final IOException e) {
                LOGGER.error("XSD {} kon niet gevonden worden in directory {}", resource.getFilename(), XSD_PATH, e);
                validatieFouten.put(resource.getFilename(), e.getMessage());
            }
        }
        assertEquals("XSD validatie fouten opgetreden", validatieFouten.size(), 0);
    }

    private Map<String, Resource> getXsdFiles() throws IOException {
        final Map<String, Resource> result = new HashMap<>();
        final ClassLoader cl = this.getClass().getClassLoader();
        final ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
        for (final Resource resource : resolver.getResources("classpath*:" + XSD_PATH + "*.xsd")) {
            result.put(resource.getFilename(), resource);
        }
        return result;
    }
}
