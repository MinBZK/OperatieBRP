/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3;

import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper.lo3Akt;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper.lo3His;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper.lo3Kind;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KindInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;

import org.junit.Before;
import org.junit.Test;

public class Lo3CategorieTest {

    Lo3Historie historie1;
    Lo3Categorie<Lo3KindInhoud> kindRij1;

    Lo3Historie historie2;
    Lo3Categorie<Lo3KindInhoud> kindRij2;

    Lo3Historie historie3;
    Lo3Categorie<Lo3KindInhoud> kindRij3;

    Lo3Historie historie4;
    Lo3Categorie<Lo3KindInhoud> kindRij4;

    Lo3Historie historie5;
    Lo3Categorie<Lo3KindInhoud> kindRij5;

    @Before
    public void setUp() {
        final Lo3KindInhoud kindInhoud = lo3Kind(1234567890L, 123456789, "Piet", null, null, "Janssen", 20120101, "1901", "6069");
        final Lo3Documentatie documentatie = lo3Akt(1);

        historie1 = lo3His(null, 20120101, 20120101);
        kindRij1 = new Lo3Categorie<>(kindInhoud, documentatie, historie1, null);

        historie2 = lo3His(null, 20100101, 20100101);
        kindRij2 = new Lo3Categorie<>(kindInhoud, documentatie, historie2, null);

        historie3 = lo3His(null, 20010101, 20010101);
        kindRij3 = new Lo3Categorie<>(kindInhoud, documentatie, historie3, null);

        historie4 = lo3His(null, 20010101, 20000101);
        final Lo3Herkomst herkomst4 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_02, 0, 3);
        kindRij4 = new Lo3Categorie<>(kindInhoud, documentatie, historie4, herkomst4);

        historie5 = lo3His(null, 20010101, 20000101);
        final Lo3Herkomst herkomst5 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_02, 0, 4);
        kindRij5 = new Lo3Categorie<>(kindInhoud, documentatie, historie5, herkomst5);
    }

    @Test
    public void testOplopend() {
        final List<Lo3Categorie<Lo3KindInhoud>> cat1 = new ArrayList<>();
        cat1.add(kindRij1);
        cat1.add(kindRij2);
        cat1.add(kindRij3);
        cat1.add(kindRij4);
        cat1.add(kindRij5);

        testAndAssert(cat1);
    }

    @Test
    public void testAflopend() {
        final List<Lo3Categorie<Lo3KindInhoud>> cat1 = new ArrayList<>();
        cat1.add(kindRij4);
        cat1.add(kindRij5);
        cat1.add(kindRij3);
        cat1.add(kindRij2);
        cat1.add(kindRij1);

        testAndAssert(cat1);
    }

    @Test
    public void testRandom() {
        final List<Lo3Categorie<Lo3KindInhoud>> cat1 = new ArrayList<>();
        cat1.add(kindRij2);
        cat1.add(kindRij3);
        cat1.add(kindRij4);
        cat1.add(kindRij5);
        cat1.add(kindRij1);

        testAndAssert(cat1);
    }

    private void testAndAssert(final List<Lo3Categorie<Lo3KindInhoud>> cat1) {
        Collections.sort(cat1, Lo3Categorie.DATUM_GELDIGHEID);
        assertEquals(kindRij1, cat1.get(0));
        assertEquals(kindRij2, cat1.get(1));
        assertEquals(kindRij3, cat1.get(2));
        assertEquals(kindRij4, cat1.get(3));
        assertEquals(kindRij5, cat1.get(4));
    }

}
