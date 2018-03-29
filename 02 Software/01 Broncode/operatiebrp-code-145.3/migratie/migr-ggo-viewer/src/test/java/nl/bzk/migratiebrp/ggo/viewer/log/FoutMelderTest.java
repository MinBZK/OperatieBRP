/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.log;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoFoutRegel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test class voor de FoutMelder.
 */
@RunWith(MockitoJUnitRunner.class)
public class FoutMelderTest {
    private final FoutMelder foutMelder = new FoutMelder();

    @Test
    public void testEmptyLogRegels() {
        assertTrue("Moet initieel leeg zijn", foutMelder.getFoutRegels().isEmpty());
    }

    @Test
    public void testLogMetOmschrijving() {
        final LogSeverity expectedLogSeverity = LogSeverity.ERROR;
        final String expectedCode = "PRE102";
        final String expectedOmschrijving = "Test Preconditie Log";
        foutMelder.log(expectedLogSeverity, expectedCode, expectedOmschrijving);

        assertEquals("1 foutregel", 1, foutMelder.getFoutRegels().size());
        assertFoutRegels(foutMelder.getFoutRegels().get(0), expectedLogSeverity, expectedCode, expectedOmschrijving);
    }

    @Test
    public void testLogMetExceptie() {
        final LogSeverity expectedLogSeverity = LogSeverity.ERROR;
        final String expectedCode = "PRE102";
        final String omschrijving = "Test Preconditie Log";
        final String expectedOmschrijving = "IllegalStateException - Test Preconditie Log";
        foutMelder.log(expectedLogSeverity, expectedCode, new IllegalStateException(omschrijving));

        assertEquals("1 foutregel", 1, foutMelder.getFoutRegels().size());
        assertFoutRegels(foutMelder.getFoutRegels().get(0), expectedLogSeverity, expectedCode, expectedOmschrijving);
    }

    @Test
    public void testLogMetExceptie2() {
        final LogSeverity expectedLogSeverity = LogSeverity.ERROR;
        final String expectedCode = "PRE102";
        final String expectedOmschrijving = "IllegalStateException - at ";
        foutMelder.log(expectedLogSeverity, expectedCode, new IllegalStateException());

        assertEquals("1 foutregel", 1, foutMelder.getFoutRegels().size());
        assertFoutRegels(foutMelder.getFoutRegels().get(0), expectedLogSeverity, expectedCode, expectedOmschrijving);
    }

    @Test
    public void testLogMetExceptie3() {
        final LogSeverity expectedLogSeverity = LogSeverity.ERROR;
        final String expectedCode = "PRE102";
        final String expectedOmschrijving = "IllegalStateException - java.lang.IllegalStateException";
        final Exception exception = new IllegalStateException();
        exception.setStackTrace(new StackTraceElement[0]);
        foutMelder.log(expectedLogSeverity, expectedCode, exception);

        assertEquals("1 foutregel", 1, foutMelder.getFoutRegels().size());
        assertFoutRegels(foutMelder.getFoutRegels().get(0), expectedLogSeverity, expectedCode, expectedOmschrijving);
    }

    @Test
    public void testFoutRegelObject() {
        final GgoFoutRegel foutRegel1 = new GgoFoutRegel(null, LogSeverity.ERROR, null, "code", "omschrijving");
        final GgoFoutRegel foutRegel2 = new GgoFoutRegel(null, LogSeverity.ERROR, null, "code", "omschrijving");
        assertEquals(foutRegel1, foutRegel2);
        assertEquals(foutRegel1, foutRegel1);
        assertFalse(foutRegel1.equals(new Object()));
        assertTrue(foutRegel1.hashCode() == foutRegel2.hashCode());
        assertEquals(foutRegel1.toString(), foutRegel2.toString());
    }

    private void assertFoutRegels(
            final GgoFoutRegel foutRegel,
            final LogSeverity expectedLogSeverity,
            final String expectedCode,
            final String expectedOmschrijving) {
        assertEquals("LogSeverity", expectedLogSeverity, foutRegel.getSeverity());
        assertEquals("Code", expectedCode, foutRegel.getCode());
        assertTrue("Omschrijving", foutRegel.getOmschrijving().startsWith(expectedOmschrijving));
    }
}
