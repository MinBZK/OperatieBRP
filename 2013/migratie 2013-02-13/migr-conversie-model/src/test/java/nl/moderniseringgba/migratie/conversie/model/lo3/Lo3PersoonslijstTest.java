/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datumtijdstempel;

import org.junit.Test;

public class Lo3PersoonslijstTest {

    private static final Lo3Historie HISTORIE = new Lo3Historie(null, new Lo3Datum(20000101), new Lo3Datum(20000101));

    @Test(expected = NullPointerException.class)
    public void testIsGroep80VanInschrijvingStapelLeegNull() {
        new Lo3PersoonslijstBuilder().build().isGroep80VanInschrijvingStapelLeeg();
    }

    @Test
    public void testIsGroep80VanInschrijvingStapelLeeg1() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.inschrijvingStapel(maakInschrijvingStapel(new Lo3InschrijvingInhoud()));
        assertTrue(builder.build().isGroep80VanInschrijvingStapelLeeg());
    }

    @Test
    public void testIsGroep80VanInschrijvingStapelLeeg2() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.inschrijvingStapel(maakInschrijvingStapel(new Lo3InschrijvingInhoud(), new Lo3InschrijvingInhoud()));
        assertTrue(builder.build().isGroep80VanInschrijvingStapelLeeg());
    }

    @Test
    public void testIsGroep80VanInschrijvingStapelNietLeeg() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.inschrijvingStapel(maakInschrijvingStapel(new Lo3InschrijvingInhoud(), new Lo3InschrijvingInhoud(
                null, null, null, null, null, null, 1, null, null)));
        assertFalse(builder.build().isGroep80VanInschrijvingStapelLeeg());
    }

    @Test
    public void testMaakKopieMetDefaultGroep80VoorInschrijvingStapel() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.inschrijvingStapel(maakInschrijvingStapel(new Lo3InschrijvingInhoud(), new Lo3InschrijvingInhoud()));
        final Lo3Persoonslijst kopie = builder.build().maakKopieMetDefaultGroep80VoorInschrijvingStapel();
        assertEquals(2, kopie.getInschrijvingStapel().size());
        final Iterator<Lo3Categorie<Lo3InschrijvingInhoud>> inschrijvingIter =
                kopie.getInschrijvingStapel().iterator();
        final Lo3Categorie<Lo3InschrijvingInhoud> inschrijving1 = inschrijvingIter.next();
        final Lo3Categorie<Lo3InschrijvingInhoud> inschrijving2 = inschrijvingIter.next();
        assertEquals(Integer.valueOf(1), inschrijving1.getInhoud().getVersienummer());
        assertEquals(new Lo3Datumtijdstempel(20070401000000000L), inschrijving1.getInhoud().getDatumtijdstempel());
        assertEquals(Integer.valueOf(1), inschrijving2.getInhoud().getVersienummer());
        assertEquals(new Lo3Datumtijdstempel(20070401000000000L), inschrijving2.getInhoud().getDatumtijdstempel());
    }

    @Test(expected = IllegalStateException.class)
    public void testMaakKopieMetDefaultGroep80VoorInschrijvingStapelFout() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.inschrijvingStapel(maakInschrijvingStapel(new Lo3InschrijvingInhoud(), new Lo3InschrijvingInhoud(
                null, null, null, null, null, null, 1, null, null)));
        builder.build().maakKopieMetDefaultGroep80VoorInschrijvingStapel();
    }

    private Lo3Stapel<Lo3InschrijvingInhoud> maakInschrijvingStapel(final Lo3InschrijvingInhoud... inschrijvingen) {
        final List<Lo3Categorie<Lo3InschrijvingInhoud>> stapel = new ArrayList<Lo3Categorie<Lo3InschrijvingInhoud>>();
        for (final Lo3InschrijvingInhoud inschrijving : inschrijvingen) {
            stapel.add(new Lo3Categorie<Lo3InschrijvingInhoud>(inschrijving, null, HISTORIE, null));
        }
        return new Lo3Stapel<Lo3InschrijvingInhoud>(stapel);
    }

}
