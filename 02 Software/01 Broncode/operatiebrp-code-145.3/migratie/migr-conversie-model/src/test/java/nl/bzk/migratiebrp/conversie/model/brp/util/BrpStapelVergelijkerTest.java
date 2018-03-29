/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.util;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorieTest;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapelVergelijker;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVoornaamInhoud;
import org.junit.Test;

public class BrpStapelVergelijkerTest {


    @Test
    public void vergelijkTest() {
        final BrpStapel<BrpVoornaamInhoud> stapel1 = createVoornaamStapel("Max", 1, 1);
        final BrpStapel<BrpVoornaamInhoud> stapel2 = createVoornaamStapel("Xam", 2, 1);
        StringBuilder result = new StringBuilder("");
        BrpStapelVergelijker.vergelijkStapels(result, stapel1, stapel2);
        assertEquals(2145, result.toString().replaceAll("\r\n", "\n").length());
    }

    @Test
    public void vergelijkTest1() {
        final BrpStapel<BrpVoornaamInhoud> stapel1 = null;
        final BrpStapel<BrpVoornaamInhoud> stapel2 = createVoornaamStapel("Xam", 2, 1);
        StringBuilder result = new StringBuilder("");
        BrpStapelVergelijker.vergelijkStapels(result, stapel1, stapel2);
        assertEquals(540, result.toString().replaceAll("\r\n", "\n").length());
    }

    @Test
    public void vergelijkTest2() {
        final BrpStapel<BrpVoornaamInhoud> stapel1 = createVoornaamStapel("Max", 1, 1);
        final BrpStapel<BrpVoornaamInhoud> stapel2 = null;
        StringBuilder result = new StringBuilder("");
        BrpStapelVergelijker.vergelijkStapels(result, stapel1, stapel2);
        assertEquals(540, result.toString().replaceAll("\r\n", "\n").length());
    }

    @Test
    public void vergelijkTest3() {
        final BrpStapel<BrpVoornaamInhoud> stapel1 = createVoornaamStapel("Max", 1, 1);
        final BrpStapel<BrpVoornaamInhoud> stapel2 = createVoornaamStapel("Xam", 2, 2);
        StringBuilder result = new StringBuilder("");
        BrpStapelVergelijker.vergelijkStapels(result, stapel1, stapel2);
        assertEquals(2668, result.toString().replaceAll("\r\n", "\n").length());
    }

    @Test
    public void vergelijkTest4() {
        final BrpStapel<BrpVoornaamInhoud> stapel1 = createVoornaamStapel("Max", 1, 3);
        final BrpStapel<BrpVoornaamInhoud> stapel2 = createVoornaamStapel("Xam", 2, 2);
        StringBuilder result = new StringBuilder("");
        BrpStapelVergelijker.vergelijkStapels(result, stapel1, stapel2);
        assertEquals(4756, result.toString().replaceAll("\r\n", "\n").length());
    }

    private BrpStapel<BrpVoornaamInhoud> createVoornaamStapel(final String voornaam, Integer volgnummer, final int aantal) {
        final List<BrpGroep<BrpVoornaamInhoud>> groepen = new LinkedList<>();
        for (int i = 0; i < aantal; i++) {
            groepen.add(createGroep(voornaam, volgnummer));
            volgnummer++;
        }
        return new BrpStapel<>(groepen);

    }

    private BrpGroep<BrpVoornaamInhoud> createGroep(final String voornaam, final Integer volgnummer) {
        return new BrpGroep<>(new BrpVoornaamInhoud(new BrpString(voornaam), new BrpInteger(volgnummer)), BrpHistorieTest.createdefaultInhoud(), null, null,
                null);
    }
}
