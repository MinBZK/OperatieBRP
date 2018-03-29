/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.parser;

import java.util.Iterator;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.bijhouding.bericht.model.ActieElement;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.BmrGroep;
import nl.bzk.brp.bijhouding.bericht.model.BronReferentieElement;
import nl.bzk.brp.bijhouding.bericht.model.GedeblokkeerdeMeldingElement;
import nl.bzk.brp.bijhouding.bericht.model.MeldingElement;
import nl.bzk.brp.bijhouding.bericht.model.ObjectSleutelIndex;

import org.junit.Test;
import org.mockito.Mock;

import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests voor BijhoudingVerzoekBerichtParser.
 */
public class FoutInReferentiesTest {

    private static final String FOUT_BERICHT = "R1838_foutInReferentiesBericht.xml";

    @Mock
    private ObjectSleutelIndex objectSleutelIndex;

    @Test
    public void testParsenFoutBericht() throws ParseException {
        final BijhoudingVerzoekBericht bericht = maakBericht(FOUT_BERICHT);
        final List<MeldingElement> meldingen = bericht.controleerReferentiesInBericht();
        assertEquals(3, meldingen.size());
        final GedeblokkeerdeMeldingElement gedeblokkeerdeMelding = bericht.getAdministratieveHandeling().getGedeblokkeerdeMeldingen().iterator().next();
        final Iterator<BronReferentieElement> iterator = bericht.getAdministratieveHandeling().getActies().iterator().next().getBronReferenties().iterator();
        final BronReferentieElement bronReferentie1 = iterator.next();
        final BronReferentieElement bronReferentie2 = iterator.next();
        final BronReferentieElement bronReferentie3 = iterator.next();
        assertTrue(bevatMeldingVoorR1838(meldingen, gedeblokkeerdeMelding));
        assertTrue(bevatMeldingVoorR1838(meldingen, bronReferentie1));
        assertFalse(bevatMeldingVoorR1838(meldingen, bronReferentie2));
        assertTrue(bevatMeldingVoorR1838(meldingen, bronReferentie3));
    }

    private boolean bevatMeldingVoorR1838(final List<MeldingElement> meldingen, final BmrGroep bmrGroep) {
        for (MeldingElement meldingElement : meldingen) {
            if (Regel.R1838.equals(meldingElement.getRegel()) && meldingElement.getReferentie().equals(bmrGroep)) {
                return true;
            }
        }
        return false;
    }

    BijhoudingVerzoekBericht maakBericht(final String file) throws ParseException {
        final BijhoudingVerzoekBerichtParser parser = new BijhoudingVerzoekBerichtParser();
        final InputStream resource = EenvoudigVoltrekkingHuwelijkNederlandParserTest.class.getResourceAsStream(file);
        final BijhoudingVerzoekBericht result = parser.parse(resource);
        result.setObjectSleutelIndex(getObjectSleutelIndex());
        return result;
    }

    private ObjectSleutelIndex getObjectSleutelIndex() {
        return objectSleutelIndex;
    }
}
