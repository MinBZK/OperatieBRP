/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import java.io.IOException;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.lo3.AbstractLo3BerichtTest;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3PersoonslijstFormatter;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Assert;
import org.junit.Test;

public class Lg01BerichtTest extends AbstractLo3BerichtTest {

    private static final String BRON_GEMEENTE = "0599";
    private static final String DOEL_GEMEENTE = "0600";

    @Test
    public void testEmpty() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Lg01Bericht bericht = new Lg01Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        testFormatAndParseBericht(bericht);
    }

    @Test
    public void test() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Lg01Bericht bericht = new Lg01Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.setLo3Persoonslijst(maakPersoonslijst(1231434134L, null, BRON_GEMEENTE));
        bericht.setBronGemeente(DOEL_GEMEENTE);
        bericht.setDoelGemeente(BRON_GEMEENTE);

        testFormatAndParseBericht(bericht);
    }

    @Test
    public void testCyclusEnGetters() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Lg01Bericht bericht = new Lg01Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        final Lo3Persoonslijst persoonslijst = maakPersoonslijst(1231434134L, null, BRON_GEMEENTE);
        final Lo3PersoonslijstFormatter formatter = new Lo3PersoonslijstFormatter();
        final List<Lo3CategorieWaarde> categorieen = formatter.format(persoonslijst);
        bericht.setLo3Persoonslijst(persoonslijst);
        bericht.setBronGemeente(BRON_GEMEENTE);
        bericht.setDoelGemeente(DOEL_GEMEENTE);

        testFormatAndParseBericht(bericht);

        Assert.assertEquals("uc202", bericht.getStartCyclus());
        Assert.assertEquals(persoonslijst, bericht.getLo3Persoonslijst());
        Assert.assertEquals(categorieen, bericht.getCategorieen());

        final Lg01Bericht controleBericht = new Lg01Bericht();
        controleBericht.setCategorieen(categorieen);
        controleBericht.setBronGemeente(BRON_GEMEENTE);
        controleBericht.setDoelGemeente(DOEL_GEMEENTE);
        controleBericht.setMessageId(bericht.getMessageId());

        Assert.assertTrue(bericht.equals(bericht));
        Assert.assertTrue(controleBericht.equals(bericht));

        final Lf01Bericht lf01Bericht = new Lf01Bericht();

        Assert.assertFalse(bericht.equals(lf01Bericht));
    }
}
