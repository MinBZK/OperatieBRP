/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.koppelvlak;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import org.apache.cxf.BusFactory;
import org.apache.cxf.tools.common.ToolContext;
import org.apache.cxf.tools.common.ToolException;
import org.apache.cxf.tools.validator.internal.WSDL11Validator;
import org.junit.Test;

/**
 * Validator voor het valideren van de brp wsdl's.
 */
public class WSDLValidatorTest {

    private static final String WSDL_DIR = "src/main/resources/wsdl/";

    @Test
    public void testValidWsdls() {
        final String[] wsdlFiles = new File(WSDL_DIR).list((dir, name) -> name.endsWith(".wsdl"));
        assertNotNull("Dit is geen geldige directory met WSDL bestanden: " + WSDL_DIR, wsdlFiles);
        assertTrue("Er zijn geen WSDL bestanden gevonden in directory: " + WSDL_DIR, wsdlFiles.length > 0);
        for (final String wsdl : wsdlFiles) {
            valideerWsdl(WSDL_DIR + wsdl);
        }
    }

    private void valideerWsdl(final String wsdl) {
        final ToolContext env = new ToolContext();
        env.put("validate", "all");
        env.put("wsdlurl", wsdl);
        final WSDL11Validator wsdlValidator = new WSDL11Validator(null, env, BusFactory.getDefaultBus());
        try {
            assertTrue(wsdlValidator.isValid());
        } catch (ToolException te) {
            fail("fouten in wsdl: " + wsdl + "\n" + te.getMessage());
        }
    }
}
