/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.categorie;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import org.junit.Test;

public class Lo3InschrijvingInhoudTest {

    @Test
    public void testIsGroep80Gevuld() {
        final Lo3InschrijvingInhoud groep80Gevuld =
                new Lo3InschrijvingInhoud(null, null, null, null, null, null, null, null, new Lo3Integer(1), new Lo3Datumtijdstempel(1L), null);
        final Lo3InschrijvingInhoud groep80NietGevuld1 =
                new Lo3InschrijvingInhoud(null, null, null, null, null, null, null, null, new Lo3Integer(1), new Lo3Datumtijdstempel(1L), null);
        final Lo3InschrijvingInhoud groep80NietGevuld2 =
                new Lo3InschrijvingInhoud(null, null, null, null, null, null, null, null, new Lo3Integer(1), null, null);
        final Lo3InschrijvingInhoud groep80NietGevuld3 = new Lo3InschrijvingInhoud(null, null, null, null, null, null, null, null, null, null, null);
        assertFalse(groep80Gevuld.isGroep80Leeg());
        assertFalse(groep80NietGevuld1.isGroep80Leeg());
        assertFalse(groep80NietGevuld2.isGroep80Leeg());
        assertTrue(groep80NietGevuld3.isGroep80Leeg());
    }

    @Test
    public void testMaakKopieMetDefaultGroep80() {
        final Lo3InschrijvingInhoud groep80NietGevuld = new Lo3InschrijvingInhoud(null, null, null, null, null, null, null, null, null, null, null);
        assertNotNull(groep80NietGevuld.maakKopieMetDefaultGroep80().getVersienummer());
        assertNotNull(groep80NietGevuld.maakKopieMetDefaultGroep80().getDatumtijdstempel());
    }

    @Test(expected = IllegalStateException.class)
    public void testMaakKopieMetDefaultGroep80Fout1() {
        final Lo3InschrijvingInhoud groep80Gevuld =
                new Lo3InschrijvingInhoud(null, null, null, null, null, null, null, null, new Lo3Integer(1), new Lo3Datumtijdstempel(1L), null);
        assertNotNull(groep80Gevuld.maakKopieMetDefaultGroep80());
    }

    @Test(expected = IllegalStateException.class)
    public void testMaakKopieMetDefaultGroep80Fout2() {
        final Lo3InschrijvingInhoud groep80Gevuld =
                new Lo3InschrijvingInhoud(null, null, null, null, null, null, null, null, new Lo3Integer(1), null, null);
        assertNotNull(groep80Gevuld.maakKopieMetDefaultGroep80());
    }

    @Test(expected = IllegalStateException.class)
    public void testMaakKopieMetDefaultGroep80Fout3() {
        final Lo3InschrijvingInhoud groep80Gevuld =
                new Lo3InschrijvingInhoud(null, null, null, null, null, null, null, null, null, new Lo3Datumtijdstempel(1L), null);
        assertNotNull(groep80Gevuld.maakKopieMetDefaultGroep80());
    }
}
