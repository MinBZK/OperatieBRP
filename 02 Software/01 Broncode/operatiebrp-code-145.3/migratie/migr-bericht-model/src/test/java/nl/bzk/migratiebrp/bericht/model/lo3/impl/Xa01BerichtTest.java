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
import nl.bzk.migratiebrp.bericht.model.lo3.AbstractLo3BerichtTestBasis;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3CategorieWaardeFormatter;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Assert;
import org.junit.Test;

public class Xa01BerichtTest extends AbstractLo3BerichtTestBasis {

    private static final String BRON_GEMEENTE = "0600";
    private static final String DOEL_GEMEENTE = "0600";
    private static final List<Lo3CategorieWaarde> CATEGORIEEN = maakCategorieen();

    @Test
    public void testEmpty() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Xa01Bericht bericht = new Xa01Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        testFormatAndParseBericht(bericht);

        Assert.assertEquals(0, bericht.getCategorieen().size());

        Assert.assertEquals(0, bericht.getCategorieen().size());
        testFormatAndParseBericht(bericht);
        Assert.assertEquals(0, bericht.getCategorieen().size());
    }

    @Test
    public void testCyclusEnGetters() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Xa01Bericht bericht = new Xa01Bericht();
        bericht.setCategorieen(CATEGORIEEN);
        bericht.setBronPartijCode(BRON_GEMEENTE);
        bericht.setDoelPartijCode(DOEL_GEMEENTE);
        bericht.setMessageId(MessageIdGenerator.generateId());

        testFormatAndParseBericht(bericht);

        Assert.assertNull(bericht.getStartCyclus());

        final Xa01Bericht controleBericht = new Xa01Bericht();
        controleBericht.setCategorieen(CATEGORIEEN);
        controleBericht.setBronPartijCode(BRON_GEMEENTE);
        controleBericht.setDoelPartijCode(DOEL_GEMEENTE);
        controleBericht.setMessageId(bericht.getMessageId());

        Assert.assertEquals(bericht.hashCode(), controleBericht.hashCode());
        Assert.assertEquals(bericht.toString(), controleBericht.toString());
        Assert.assertTrue(bericht.equals(bericht));
        Assert.assertTrue(controleBericht.equals(bericht));
        Assert.assertEquals(CATEGORIEEN, bericht.getCategorieen());

        Assert.assertFalse(bericht.equals(new Lq01Bericht()));
    }

    private static List<Lo3CategorieWaarde> maakCategorieen() {
        final Lo3CategorieWaardeFormatter formatter = new Lo3CategorieWaardeFormatter();
        formatter.categorie(Lo3CategorieEnum.CATEGORIE_01);
        formatter.element(Lo3ElementEnum.ELEMENT_0110, "");
        formatter.element(Lo3ElementEnum.ELEMENT_0120, "");
        return formatter.getList();
    }
}
