/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorieTest;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNaamgebruikGeslachtsnaamstam;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import org.junit.Assert;
import org.junit.Test;

public class BrpNaamgebruikInhoudTest {
    public static BrpNaamgebruikInhoud createInhoud() {
        return createInhoud(null);
    }

    public static BrpStapel<BrpNaamgebruikInhoud> createStapel() {
        return createStapel(null);
    }

    @Test
    public void test(){
        final BrpNaamgebruikInhoud inhoud = createInhoud();
        Assert.assertEquals("Predicaat",inhoud.getPredicaatCode().getWaarde());
    }

    public static BrpStapel<BrpNaamgebruikInhoud> createStapel(String voornamen) {
        List<BrpGroep<BrpNaamgebruikInhoud>> groepen = new ArrayList<>();
        BrpGroep<BrpNaamgebruikInhoud> groep = new BrpGroep<>(createInhoud(voornamen), BrpHistorieTest.createdefaultInhoud(), null, null, null);
        groepen.add(groep);
        return new BrpStapel<>(groepen);
    }

    public static BrpNaamgebruikInhoud createInhoud(String voornamen) {
        if (voornamen == null) {
            voornamen = "Vincent Pieter";
        }

        return new BrpNaamgebruikInhoud(
                BrpNaamgebruikCode.E,
                BrpBoolean.wrap(false, null),
                new BrpPredicaatCode("Predicaat"),
                new BrpAdellijkeTitelCode("Baron"),
                new BrpString(voornamen),
                new BrpString("van der"),
                new BrpCharacter(' '),
                new BrpNaamgebruikGeslachtsnaamstam("Langeachternaam", Collections.emptySet()));
    }
}
