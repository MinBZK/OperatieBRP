/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Collections;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.lo3.AbstractLo3BerichtTestBasis;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3CategorieWaardeFormatter;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3VerwijzingFormatter;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerwijzingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import org.junit.Test;

public class Tv01BerichtTest extends AbstractLo3BerichtTestBasis {

    @Test
    public void testVertaal() {
        final Tv01Bericht tv01Bericht = new Tv01Bericht();
        tv01Bericht.setMessageId(MessageIdGenerator.generateId());
        assertEquals("Tv01", tv01Bericht.getBerichtType());
        assertEquals(null, tv01Bericht.getStartCyclus());
    }

    @Test
    public void testVerwijsgegevens() throws IOException, BerichtInhoudException, ClassNotFoundException {
        Lo3VerwijzingFormatter verwijzingFormatter = new Lo3VerwijzingFormatter();
        Lo3VerwijzingInhoud lo3VerwijzingInhoud = Lo3StapelHelper.lo3Verwijzing(123456789L, 6234324, "Jan",
                null,
                null,
                "Jansen",
                19700101,
                "0518",
                "6030",
                "0626",
                20170101,
                0);
        final Lo3CategorieWaardeFormatter formatter = new Lo3CategorieWaardeFormatter();
        formatter.categorie(Lo3CategorieEnum.CATEGORIE_21);
        verwijzingFormatter.format(lo3VerwijzingInhoud, formatter);
        final Tv01Bericht tv01Bericht = new Tv01Bericht(formatter.getList());
        tv01Bericht.setMessageId(MessageIdGenerator.generateId());
        testFormatAndParseBericht(tv01Bericht);
        assertEquals("Tv01", tv01Bericht.getBerichtType());
        assertEquals(null, tv01Bericht.getStartCyclus());
        assertEquals(Collections.singletonList(null), tv01Bericht.getGerelateerdeAnummers());
        assertEquals(formatter.getList().size(), tv01Bericht.getCategorieen().size());
        assertEquals(formatter.getList(), tv01Bericht.getCategorieen());
    }

}
