/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import nl.bzk.brp.business.stappen.BerichtContext;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieBerichtContext;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieResultaat;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatiePersoonBericht;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;
import org.junit.Test;

public class BerichtResultaatFactoryImplTest {

    @Test
    public void testCreeerBerichtResultaat() {
        final BerichtResultaatFactoryImpl berichtResultaatFactory = new BerichtResultaatFactoryImpl();

        final BerichtBericht bericht = new GeefSynchronisatiePersoonBericht();
        final BerichtContext berichtContext = new SynchronisatieBerichtContext(new BerichtenIds(0L, 1L), null, null, null);

        final SynchronisatieResultaat synchronisatieResultaat = berichtResultaatFactory.creeerBerichtResultaat(bericht, berichtContext);

        assertNotNull(synchronisatieResultaat);
        assertEquals(0, synchronisatieResultaat.getMeldingen().size());
    }
}
