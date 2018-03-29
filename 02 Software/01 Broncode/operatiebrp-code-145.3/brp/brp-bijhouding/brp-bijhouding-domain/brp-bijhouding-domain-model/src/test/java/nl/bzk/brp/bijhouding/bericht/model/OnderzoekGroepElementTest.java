/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.StatusOnderzoek;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Test;

/**
 * Testen voor {@link OnderzoekGroepElement}.
 */
public class OnderzoekGroepElementTest extends AbstractElementTest {

    @Test
    public void getDatumAanvang() {
        final DatumElement datumAanvang = new DatumElement(20170101);
        final OnderzoekGroepElement onderzoekGroepElement = OnderzoekGroepElement.getInstance("ci_onderzoek_groep", datumAanvang.getWaarde(), null, null, null);
        assertEquals(datumAanvang, onderzoekGroepElement.getDatumAanvang());
    }

    @Test
    public void getDatumEinde() {
        final DatumElement datumEinde = new DatumElement(20170101);
        final OnderzoekGroepElement onderzoekGroepElement = OnderzoekGroepElement.getInstance("ci_onderzoek_groep", null, datumEinde.getWaarde(), null, null);
        assertEquals(datumEinde, onderzoekGroepElement.getDatumEinde());
    }

    @Test
    public void getOmschrijving() {
        final StringElement omschrijving = StringElement.getInstance("test omschrijving");
        final OnderzoekGroepElement onderzoekGroepElement = OnderzoekGroepElement.getInstance("ci_onderzoek_groep", null, null, omschrijving.getWaarde(), null);
        assertEquals(omschrijving, onderzoekGroepElement.getOmschrijving());
    }

    @Test
    public void getStatusNaam() {
        final StringElement statusNaam = StringElement.getInstance("test statusNaam");
        final OnderzoekGroepElement onderzoekGroepElement = OnderzoekGroepElement.getInstance("ci_onderzoek_groep", null, null, null, statusNaam.getWaarde());
        assertEquals(statusNaam, onderzoekGroepElement.getStatusNaam());
    }

    @Test
    public void getStatusOnderzoekGeenMatch() {
        final StringElement statusNaam = StringElement.getInstance("onbekende status");
        final OnderzoekGroepElement onderzoekGroepElement = OnderzoekGroepElement.getInstance("ci_onderzoek_groep", null, null, null, statusNaam.getWaarde());
        assertNull(onderzoekGroepElement.getStatusOnderzoek());
    }

    @Test
    public void getStatusOnderzoekMatch() {
        final StringElement statusNaam = StringElement.getInstance(StatusOnderzoek.GESTAAKT.getNaam());
        final OnderzoekGroepElement onderzoekGroepElement = OnderzoekGroepElement.getInstance("ci_onderzoek_groep", null, null, null, statusNaam.getWaarde());
        assertEquals(StatusOnderzoek.GESTAAKT, onderzoekGroepElement.getStatusOnderzoek());
    }

    @Test
    public void valideerInhoudGoed() {
        final BijhoudingVerzoekBericht verzoekBericht = mock(BijhoudingVerzoekBericht.class);
        when(verzoekBericht.getDatumOntvangst()).thenReturn(new DatumElement(DatumUtil.vandaag()));
        final OnderzoekGroepElement onderzoekGroepElement = OnderzoekGroepElement.getInstance("ci_onderzoek_groep", 20170101, null, null, null);
        onderzoekGroepElement.setVerzoekBericht(verzoekBericht);
        final List<MeldingElement> meldingen = onderzoekGroepElement.valideerInhoud();
        controleerRegels(meldingen);
    }

    @Test
    public void valideerInhoudFout() {
        final BijhoudingVerzoekBericht verzoekBericht = mock(BijhoudingVerzoekBericht.class);
        final int vandaag = DatumUtil.vandaag();
        when(verzoekBericht.getDatumOntvangst()).thenReturn(new DatumElement(vandaag));
        final OnderzoekGroepElement onderzoekGroepElement = OnderzoekGroepElement.getInstance("ci_onderzoek_groep", vandaag + 1, null, null, null);
        onderzoekGroepElement.setVerzoekBericht(verzoekBericht);
        final List<MeldingElement> meldingen = onderzoekGroepElement.valideerInhoud();
        controleerRegels(meldingen, Regel.R2595);
    }

}
