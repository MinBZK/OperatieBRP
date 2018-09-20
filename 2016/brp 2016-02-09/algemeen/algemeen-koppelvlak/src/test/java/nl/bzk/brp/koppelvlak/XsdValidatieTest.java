/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.koppelvlak;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * Unit test om de XSD's van het koppelvlak te valideren.
 */
public class XsdValidatieTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(XsdValidatieTest.class);

    private FilenameFilter xsdFilter = new FilenameFilter() {
        @Override
        public boolean accept(final File dir, final String name) {
            final String lowercaseName = name.toLowerCase();
            if (lowercaseName.endsWith(".xsd")) {
                return true;
            } else {
                return false;
            }
        }
    };

    private final XsdValidator xsdValidator = new XsdValidator();

    @Test
    public void testXsd()  {
        final String XSD_PATH = "src/main/resources/xsd/BRP0200";
        final File directory = new File(XSD_PATH);

        //get all the xsd files from a directory

        final File[] fList = directory.listFiles(xsdFilter);
        final Map<String, String> validatieFouten = new HashMap<>();
        LOGGER.info("Aantal XSD's gevonden: {}", fList.length);
        for (final File file : fList) {
            if (file.isFile()) {
                LOGGER.debug("Valideer XSD '{}'", file.getName());
                try {
                    xsdValidator.valideer(null, XSD_PATH + "/" + file.getName());
                    LOGGER.info("XSD '{}' is valide", file.getName());
                } catch (final SAXException e) {
                    LOGGER.error("XSD {} valideert niet", file.getName(),e);
                    validatieFouten.put(file.getName(), e.getMessage());
                } catch (final IOException e) {
                    LOGGER.error("XSD {} kon niet gevonden worden in directory {}", file.getName(), XSD_PATH, e);
                    validatieFouten.put(file.getName(), e.getMessage());
                }

            }
        }
        assertEquals("XSD validatie fouten opgetreden", validatieFouten.size(), 0);
    }
}
