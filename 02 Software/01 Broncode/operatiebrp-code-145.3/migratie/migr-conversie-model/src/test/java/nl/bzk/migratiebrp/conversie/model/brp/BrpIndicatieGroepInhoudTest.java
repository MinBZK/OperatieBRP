/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOnderCurateleIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerstrekkingsbeperkingIndicatieInhoud;
import org.junit.Test;

public class BrpIndicatieGroepInhoudTest {

    @Test
    public void testEqualsMethod() {
        final BrpBoolean trueWaarde = new BrpBoolean(true);
        final BrpBoolean falseWaarde = new BrpBoolean(false);

        final BrpOnderCurateleIndicatieInhoud inhoud1 = new BrpOnderCurateleIndicatieInhoud(trueWaarde, null, null);
        final BrpOnderCurateleIndicatieInhoud inhoud2 = new BrpOnderCurateleIndicatieInhoud(falseWaarde, null, null);
        final BrpOnderCurateleIndicatieInhoud inhoud3 = new BrpOnderCurateleIndicatieInhoud(trueWaarde, null, null);
        final BrpOnderCurateleIndicatieInhoud inhoud4 = new BrpOnderCurateleIndicatieInhoud(trueWaarde, new BrpString("312"), null);
        final BrpOnderCurateleIndicatieInhoud inhoud5 = new BrpOnderCurateleIndicatieInhoud(trueWaarde, null, new BrpString("412"));
        final BrpOnderCurateleIndicatieInhoud inhoud6 = new BrpOnderCurateleIndicatieInhoud(trueWaarde, new BrpString("312"), new BrpString("412"));
        final BrpOnderCurateleIndicatieInhoud inhoud7 = new BrpOnderCurateleIndicatieInhoud(trueWaarde, new BrpString("001"), null);

        final BrpVerstrekkingsbeperkingIndicatieInhoud inhoudAndereIndicatie = new BrpVerstrekkingsbeperkingIndicatieInhoud(trueWaarde, null, null);

        assertTrue(inhoud1.equals(inhoud3));
        assertFalse(inhoud1.equals(inhoudAndereIndicatie));
        assertFalse(inhoud1.equals(inhoud2));
        assertFalse(inhoud2.equals(inhoud3));
        assertTrue(inhoud3.equals(inhoud1));
        assertTrue(inhoud4.equals(inhoud4));
        assertFalse(inhoud1.equals(inhoud4));
        assertFalse(inhoud4.equals(inhoud5));
        assertFalse(inhoud4.equals(inhoud6));
        assertFalse(inhoud4.equals(inhoud7));
    }
}
