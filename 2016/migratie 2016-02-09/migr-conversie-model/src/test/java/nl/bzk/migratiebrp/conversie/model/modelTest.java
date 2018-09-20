/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model;

import static org.junit.Assert.assertEquals;

import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilderTest;

import org.junit.Test;

public class modelTest {

    @Test
    public void testDefinities() {
        Definities result = Definities.valueOf("DEF015");
        Definities def = Definities.DEF015;
        assertEquals(def.getOmschrijving(), result.getOmschrijving());
        assertEquals(def.getCode(), result.getCode());
        assertEquals(def.toString(), result.toString());

    }

    @Test
    public void testRequirements() {
        Requirements result = Requirements.valueOf("CCA04");
        Requirements req = Requirements.CCA04;
        assertEquals(req.getOmschrijving(), result.getOmschrijving());
        assertEquals(req.getCode(), result.getCode());
        assertEquals(req.toString(), result.toString());

    }

    @Test
    public void testPersoonsLijstInterface() {
        Persoonslijst pl = getPersoonsLijst(1212121212L);
        assertEquals(1212121212, pl.getActueelAdministratienummer().longValue());
    }

    private Persoonslijst getPersoonsLijst(Long aNummer) {
        BrpPersoonslijstBuilder bld = BrpPersoonslijstBuilderTest.maakMinimaleBuilder(aNummer);
        return bld.build();
    }
}
