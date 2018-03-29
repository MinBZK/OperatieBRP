/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.util;

import static org.junit.Assert.assertEquals;

import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.testutils.VerplichteStapel;
import org.junit.Test;

public class VerwerkerUtilTest {

    @Test
    public void testZeroPad() throws Exception {
        assertEquals("001", VerwerkerUtil.zeroPad("1", 3));
        assertEquals("022", VerwerkerUtil.zeroPad("22", 3));
        assertEquals("0333", VerwerkerUtil.zeroPad("333", 4));
        assertEquals("4444", VerwerkerUtil.zeroPad("4444", 4));
    }

    @Test
    public void testMaakCatNrHistorisch() throws Exception {
        assertEquals(1, VerwerkerUtil.maakCatNrHistorisch(1, 0));
        assertEquals(51, VerwerkerUtil.maakCatNrHistorisch(1, 1));

        assertEquals(Lo3CategorieEnum.CATEGORIE_08, VerwerkerUtil.maakCatNrHistorisch(Lo3CategorieEnum.CATEGORIE_08, 0));
        assertEquals(Lo3CategorieEnum.CATEGORIE_58, VerwerkerUtil.maakCatNrHistorisch(Lo3CategorieEnum.CATEGORIE_58, 3));
    }

    @Test
    public void testKopieerLo3Persoonslijst() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(VerplichteStapel.createPersoonStapel());
        builder.inschrijvingStapel(VerplichteStapel.createInschrijvingStapel());
        final Lo3Persoonslijst origineel = builder.build();
        final Lo3Persoonslijst kopie = VerwerkerUtil.kopieerLo3Persoonslijst(origineel);
        assertEquals(origineel, kopie);
    }

}
