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
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3PersoonslijstFormatter;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Assert;
import org.junit.Test;

public class Ap01BerichtTest extends AbstractLo3BerichtTest {

    private static final String BRON_AFNEMER = "0599";
    private static final String DOEL_GEMEENTE = "0600";

    @Test
    public void testEmpty() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final String av01 = "00000000Ap01000000";
        final Lo3Bericht parsed = getFactory().getBericht(av01);
        Assert.assertEquals(OngeldigeInhoudBericht.class, parsed.getClass());
    }

    @Test
    public void test() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Ap01Bericht bericht = new Ap01Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.setCategorieen(new Lo3PersoonslijstFormatter().format(maakPersoonslijst(1231434134L, null, BRON_AFNEMER)));
        bericht.setBronGemeente(DOEL_GEMEENTE);
        bericht.setDoelGemeente(BRON_AFNEMER);

        testFormatAndParseBericht(bericht);
    }

    @Test
    public void testCyclusEnGetters() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Ap01Bericht bericht = new Ap01Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        final Lo3Persoonslijst persoonslijst = maakPersoonslijst(1231434134L, null, BRON_AFNEMER);
        final Lo3PersoonslijstFormatter formatter = new Lo3PersoonslijstFormatter();
        final List<Lo3CategorieWaarde> categorieen = formatter.format(persoonslijst);
        bericht.setCategorieen(categorieen);
        bericht.setBronGemeente(BRON_AFNEMER);
        bericht.setDoelGemeente(DOEL_GEMEENTE);

        testFormatAndParseBericht(bericht);

        Assert.assertEquals("Ap01", bericht.getBerichtType());
        Assert.assertEquals("uc1003-plaatsen", bericht.getStartCyclus());
        Assert.assertEquals(categorieen, bericht.getCategorieen());

        final Ap01Bericht controleBericht = new Ap01Bericht();
        controleBericht.setCategorieen(categorieen);
        controleBericht.setBronGemeente(BRON_AFNEMER);
        controleBericht.setDoelGemeente(DOEL_GEMEENTE);
        controleBericht.setMessageId(bericht.getMessageId());

        Assert.assertTrue(bericht.equals(bericht));
        Assert.assertTrue(controleBericht.equals(bericht));

        final Af01Bericht af01Bericht = new Af01Bericht();

        Assert.assertFalse(bericht.equals(af01Bericht));
    }
}
