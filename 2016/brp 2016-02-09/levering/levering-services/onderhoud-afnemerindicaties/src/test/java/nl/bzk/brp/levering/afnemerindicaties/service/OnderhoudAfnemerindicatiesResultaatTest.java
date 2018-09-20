/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nl.bzk.brp.model.validatie.Melding;
import org.junit.Assert;
import org.junit.Test;


public class OnderhoudAfnemerindicatiesResultaatTest {

    @Test
    public final void testOnderhoudAfnemerindicatiesResultaatMeldingenWordenDoorgegeven() {
        final List<Melding> meldingen = new ArrayList<>();

        final OnderhoudAfnemerindicatiesResultaat afnemerindicatiesResultaat =
                new OnderhoudAfnemerindicatiesResultaat(meldingen);

        Assert.assertEquals(meldingen, afnemerindicatiesResultaat.getMeldingen());
    }

    @Test
    public final void testMutabilityTijdstipregistratie() {
        final OnderhoudAfnemerindicatiesResultaat afnemerindicatiesResultaat =
            new OnderhoudAfnemerindicatiesResultaat(new ArrayList<Melding>());

        final Date datum = new Date();
        afnemerindicatiesResultaat.setTijdstipRegistratie(datum);
        Assert.assertEquals(datum, afnemerindicatiesResultaat.getTijdstipRegistratie());

        // Wijzig de datum en check of de tijdstipregistratie van de afnemerindicatie niet (mee) is gewijzigd.
        datum.setTime(0);
        Assert.assertNotEquals(datum, afnemerindicatiesResultaat.getTijdstipRegistratie());

        // Haal tijdstip op, wijzig het en controleer dat het opnieuw ophalen niet de gewijzigde versie retourneert.
        final Date datumOpgehaald = afnemerindicatiesResultaat.getTijdstipRegistratie();
        Assert.assertEquals(datumOpgehaald, afnemerindicatiesResultaat.getTijdstipRegistratie());
        datumOpgehaald.setTime(0);
        Assert.assertNotEquals(datumOpgehaald, afnemerindicatiesResultaat.getTijdstipRegistratie());
    }

    @Test
    public void testZettenTijdstipregistratieAlsNull() {
        final OnderhoudAfnemerindicatiesResultaat afnemerindicatiesResultaat =
            new OnderhoudAfnemerindicatiesResultaat(new ArrayList<Melding>());

        // Check wel dat het zetten van het tijdstip tegen null kan
        afnemerindicatiesResultaat.setTijdstipRegistratie(null);
        Assert.assertNull(afnemerindicatiesResultaat.getTijdstipRegistratie());
    }
}
