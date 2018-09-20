/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.proces;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;

import org.junit.Test;

/**
 * Deze testcase test de volgende datum/tijd conversies tussen LO3 en BRP:
 * <ol>
 * <li>LO3 datum -> BRP timestamp</li>
 * <li>BRP timestamp -> LO3 datum</li>
 * </ol>
 * 
 */
public class DatumTijdConversieTest {

    /**
     * Test situatie 1: LO3 datum -> BRP timestamp
     * 
     * @throws ParseException
     */
    @Test
    public void testSituatie1() throws ParseException {
        final Lo3Datum lo3Datum = new Lo3Datum(20130601);

        final Calendar expected = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        expected.set(Calendar.YEAR, 2013);
        expected.set(Calendar.MONTH, Calendar.JUNE);
        expected.set(Calendar.DAY_OF_MONTH, 1);
        expected.set(Calendar.HOUR_OF_DAY, 1);
        expected.set(Calendar.MINUTE, 0);
        expected.set(Calendar.SECOND, 0);
        expected.set(Calendar.MILLISECOND, 0);

        assertEquals(expected.getTime(), BrpDatumTijd.fromLo3Datum(lo3Datum).getJavaDate());
    }

    /**
     * Test situatie 2: BRP timestamp -> LO3 datum
     */
    @Test
    public void testSituatie2() {
        final BrpDatumTijd brpDatumTijdZomer = BrpDatumTijd.fromDatumTijd(20130601223000L, null);
        final BrpDatumTijd brpDatumTijdWinter = BrpDatumTijd.fromDatumTijd(20130101223000L, null);

        assertEquals(new Lo3Datum(20130602), brpDatumTijdZomer.converteerNaarLo3Datum());
        assertEquals(new Lo3Datum(20130101), brpDatumTijdWinter.converteerNaarLo3Datum());
    }
}
