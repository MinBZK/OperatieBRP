/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGezagMinderjarige;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.regels.tabel.ConversietabelFactoryImpl;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Unit test voor {@link Lo3AttribuutConverteerder}.
 */
public class Lo3AttribuutConverteerderTest {


    private final static Lo3AttribuutConverteerder converteerder = new Lo3AttribuutConverteerder(new ConversietabelFactoryImpl());
    private static final Lo3Onderzoek ONDERZOEK = new Lo3Onderzoek(new Lo3Integer(110000), new Lo3Datum(20150101), null);
    private static final BrpBoolean EXPECTED_TRUE_ZONDER_ONDERZOEK = new BrpBoolean(true, null);
    private static final BrpBoolean EXPECTED_TRUE_MET_ONDERZOEK = new BrpBoolean(true, ONDERZOEK);
    private static final BrpBoolean EXPECTED_FALSE_ZONDER_ONDERZOEK = new BrpBoolean(false, null);
    private static final BrpBoolean EXPECTED_FALSE_MET_ONDERZOEK = new BrpBoolean(false, ONDERZOEK);

    @Test
    public void testConverteerOuder1HeeftGezag() {
        assertEquals(EXPECTED_TRUE_ZONDER_ONDERZOEK, converteerder.converteerOuder1HeeftGezag(new Lo3IndicatieGezagMinderjarige("1")));
        assertEquals(EXPECTED_TRUE_MET_ONDERZOEK, converteerder.converteerOuder1HeeftGezag(new Lo3IndicatieGezagMinderjarige("1", ONDERZOEK)));

        assertEquals(EXPECTED_TRUE_ZONDER_ONDERZOEK, converteerder.converteerOuder1HeeftGezag(new Lo3IndicatieGezagMinderjarige("1D")));
        assertEquals(EXPECTED_TRUE_MET_ONDERZOEK, converteerder.converteerOuder1HeeftGezag(new Lo3IndicatieGezagMinderjarige("1D", ONDERZOEK)));

        assertEquals(EXPECTED_TRUE_ZONDER_ONDERZOEK, converteerder.converteerOuder1HeeftGezag(new Lo3IndicatieGezagMinderjarige("12")));
        assertEquals(EXPECTED_TRUE_MET_ONDERZOEK, converteerder.converteerOuder1HeeftGezag(new Lo3IndicatieGezagMinderjarige("12", ONDERZOEK)));

        assertEquals(EXPECTED_FALSE_ZONDER_ONDERZOEK, converteerder.converteerOuder1HeeftGezag(new Lo3IndicatieGezagMinderjarige("2")));
        assertEquals(EXPECTED_FALSE_MET_ONDERZOEK, converteerder.converteerOuder1HeeftGezag(new Lo3IndicatieGezagMinderjarige("2", ONDERZOEK)));

        assertEquals(EXPECTED_FALSE_ZONDER_ONDERZOEK, converteerder.converteerOuder1HeeftGezag(new Lo3IndicatieGezagMinderjarige("2D")));
        assertEquals(EXPECTED_FALSE_MET_ONDERZOEK, converteerder.converteerOuder1HeeftGezag(new Lo3IndicatieGezagMinderjarige("2D", ONDERZOEK)));

        assertNull(converteerder.converteerOuder1HeeftGezag(null));
        assertNull(converteerder.converteerOuder1HeeftGezag(new Lo3IndicatieGezagMinderjarige(null)));
        assertNull(converteerder.converteerOuder1HeeftGezag(new Lo3IndicatieGezagMinderjarige(null, ONDERZOEK)));
    }

    @Test
    public void testConverteerOuder2HeeftGezag() {
        assertEquals(EXPECTED_TRUE_ZONDER_ONDERZOEK, converteerder.converteerOuder2HeeftGezag(new Lo3IndicatieGezagMinderjarige("2")));
        assertEquals(EXPECTED_TRUE_MET_ONDERZOEK, converteerder.converteerOuder2HeeftGezag(new Lo3IndicatieGezagMinderjarige("2", ONDERZOEK)));

        assertEquals(EXPECTED_TRUE_ZONDER_ONDERZOEK, converteerder.converteerOuder2HeeftGezag(new Lo3IndicatieGezagMinderjarige("2D")));
        assertEquals(EXPECTED_TRUE_MET_ONDERZOEK, converteerder.converteerOuder2HeeftGezag(new Lo3IndicatieGezagMinderjarige("2D", ONDERZOEK)));

        assertEquals(EXPECTED_TRUE_ZONDER_ONDERZOEK, converteerder.converteerOuder2HeeftGezag(new Lo3IndicatieGezagMinderjarige("12")));
        assertEquals(EXPECTED_TRUE_MET_ONDERZOEK, converteerder.converteerOuder2HeeftGezag(new Lo3IndicatieGezagMinderjarige("12", ONDERZOEK)));

        assertEquals(EXPECTED_FALSE_ZONDER_ONDERZOEK, converteerder.converteerOuder2HeeftGezag(new Lo3IndicatieGezagMinderjarige("1")));
        assertEquals(EXPECTED_FALSE_MET_ONDERZOEK, converteerder.converteerOuder2HeeftGezag(new Lo3IndicatieGezagMinderjarige("1", ONDERZOEK)));

        assertEquals(EXPECTED_FALSE_ZONDER_ONDERZOEK, converteerder.converteerOuder2HeeftGezag(new Lo3IndicatieGezagMinderjarige("1D")));
        assertEquals(EXPECTED_FALSE_MET_ONDERZOEK, converteerder.converteerOuder2HeeftGezag(new Lo3IndicatieGezagMinderjarige("1D", ONDERZOEK)));

        assertNull(converteerder.converteerOuder2HeeftGezag(null));
        assertNull(converteerder.converteerOuder2HeeftGezag(new Lo3IndicatieGezagMinderjarige(null)));
        assertNull(converteerder.converteerOuder2HeeftGezag(new Lo3IndicatieGezagMinderjarige(null, ONDERZOEK)));
    }
}
