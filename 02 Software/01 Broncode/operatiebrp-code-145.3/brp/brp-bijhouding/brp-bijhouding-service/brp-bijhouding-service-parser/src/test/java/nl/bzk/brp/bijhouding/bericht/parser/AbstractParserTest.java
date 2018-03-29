/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.parser;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import nl.bzk.brp.bijhouding.bericht.model.BooleanElement;
import nl.bzk.brp.bijhouding.bericht.model.CharacterElement;
import nl.bzk.brp.bijhouding.bericht.model.DatumElement;
import nl.bzk.brp.bijhouding.bericht.model.DatumTijdElement;
import nl.bzk.brp.bijhouding.bericht.model.OngeldigeWaardeException;
import nl.bzk.brp.bijhouding.bericht.model.StringElement;
import nl.bzk.brp.bijhouding.bericht.validation.SchemaValidationHelper;
import org.junit.Assert;
import org.xml.sax.SAXException;

/**
 * Bevat basis functies voor het testen van de verschillende parsers.
 */
public abstract class AbstractParserTest {

    protected final void valideerTegenSchema(final InputStream xmlStream) {
        valideerTegenSchema(xmlStream, SchemaValidationHelper.getSchema());
    }

    protected final void valideerTegenIscSchema(final InputStream xmlStream) {
        valideerTegenSchema(xmlStream, SchemaValidationHelper.getIscSchema());
    }

    protected void assertEqualStringElement(final String expected, final StringElement actual) {
        if (expected == null) {
            Assert.assertNull(actual);
        } else {
            Assert.assertEquals(new StringElement(expected), actual);
        }
    }

    protected void assertEqualCharacterElement(final Character expected, final CharacterElement actual) {
        if (expected == null) {
            Assert.assertNull(actual);
        } else {
            Assert.assertEquals(new CharacterElement(expected), actual);
        }
    }

    protected void assertEqualDatumElement(final String expected, final DatumElement actual) {
        if (expected == null) {
            Assert.assertNull(actual);
        } else {
            try {
                Assert.assertEquals(DatumElement.parseWaarde(expected), actual);
            } catch (OngeldigeWaardeException e) {
                Assert.fail("expected is geen valide datum: " + e.getMessage());
            }
        }
    }

    protected void assertEqualDatumTijdElement(final String expected, final DatumTijdElement actual) {
        if (expected == null) {
            Assert.assertNull(actual);
        } else {
            try {
                Assert.assertEquals(DatumTijdElement.parseWaarde(expected), actual);
            } catch (OngeldigeWaardeException e) {
                Assert.fail("expected is geen valide datum / tijd: " + e.getMessage());
            }
        }
    }

    protected void assertEqualBooleanElement(final String expected, final BooleanElement actual) {
        if (expected == null) {
            Assert.assertNull(actual);
        } else {
            try {
                Assert.assertEquals(BooleanElement.parseWaarde(expected), actual);
            } catch (OngeldigeWaardeException e) {
                Assert.fail("expected is geen valide Ja / Nee waarde: " + e.getMessage());
            }
        }
    }

    private void valideerTegenSchema(final InputStream xmlStream, final Schema schema) {
        try {
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlStream));
        } catch (
                SAXException
                        | IOException e)
        {
            Assert.fail(e.getMessage());
        }
    }
}
