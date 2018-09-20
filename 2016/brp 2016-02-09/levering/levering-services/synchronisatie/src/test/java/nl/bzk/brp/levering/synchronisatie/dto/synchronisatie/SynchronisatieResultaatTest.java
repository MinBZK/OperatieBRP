/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.dto.synchronisatie;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Test;

public class SynchronisatieResultaatTest {

    @Test
    public final void testSynchronisatieResultaatMeldingenWordenDoorgegeven() {
        final List<Melding> meldingen = new ArrayList<>();

        final SynchronisatieResultaat synchronisatieResultaat = new SynchronisatieResultaat(meldingen);

        assertEquals(meldingen, synchronisatieResultaat.getMeldingen());
    }

}
