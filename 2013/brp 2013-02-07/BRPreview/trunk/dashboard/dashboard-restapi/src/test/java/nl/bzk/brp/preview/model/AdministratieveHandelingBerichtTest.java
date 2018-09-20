/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class AdministratieveHandelingBerichtTest {

    private AdministratieveHandelingBericht administratieveHandelingBericht;

    @Before
    public void setup() {
        final MeldingSoort meldingSoort1 = new MeldingSoort("I", "Informatie");
        final Melding melding1 = new Melding(meldingSoort1, "Test informatietekst");
        final MeldingSoort meldingSoort2 = new MeldingSoort("W", "Waarschuwing");
        final Melding melding2 = new Melding(meldingSoort2, "Test waarschuwingstekst");
        final List<Melding> meldingen = Arrays.asList(new Melding[]{melding1, melding2});

        final AdministratieveHandeling administratieveHandeling = new AdministratieveHandeling();
        administratieveHandeling.setPartij("Enkhuizen");
        final Calendar cal = Calendar.getInstance();
        cal.set(2013, 0, 10);
        administratieveHandeling.setTijdstipRegistratie(cal.getTime());
        administratieveHandeling.setSoortAdministratieveHandeling("onbekend");
        administratieveHandeling.getMeldingen().addAll(meldingen);

        administratieveHandelingBericht = new AdministratieveHandelingBericht(administratieveHandeling);
    }

    @Test
    public void testCreeerBerichtTekst() {
        String berichtTekst = administratieveHandelingBericht.creeerBerichtTekst();

        assertEquals("Onbekende administratieve handeling op 10-01-2013 in Enkhuizen van het soort onbekend.",
                berichtTekst);
    }

    @Test
    public void testCreeerDetailsTekst() {
        String berichtTekst = administratieveHandelingBericht.creeerDetailsTekst();

        assertEquals("Informatie: Test informatietekst\n"
                + "Waarschuwing: Test waarschuwingstekst", berichtTekst);
    }

}
