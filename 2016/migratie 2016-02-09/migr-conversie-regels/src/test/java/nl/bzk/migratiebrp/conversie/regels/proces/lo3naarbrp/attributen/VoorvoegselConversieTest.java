/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Builder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;
import nl.bzk.migratiebrp.conversie.regels.AbstractComponentTest;
import nl.bzk.migratiebrp.conversie.regels.testutils.ReflectionUtil;
import org.junit.Test;

public class VoorvoegselConversieTest extends AbstractComponentTest {

    @Inject
    private PersoonConverteerder persoonConverteerder;

    @Test
    
    public void testConverteerVoorvoegsel() {
        final List<Lo3Categorie<Lo3PersoonInhoud>> persoonCategorieen = new ArrayList<>();

        persoonCategorieen.add(Lo3Builder.createLo3Persoon(
            1234567890L,
            987654321,
            "Piet Jan",
            "JH",
            "van",
            "HorenZeggen",
            19800101,
            "00000",
            "6030",
            "M",
            null,
            null,
            "E",
            "O",
            19800101,
            19800102,
            0));

        final Lo3Stapel<Lo3PersoonInhoud> persoonStapel = new Lo3Stapel<>(persoonCategorieen);
        final TussenPersoonslijstBuilder builder = new TussenPersoonslijstBuilder();

        persoonConverteerder.converteer(persoonStapel, false, builder);

        final TussenStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaam =
                (TussenStapel<BrpSamengesteldeNaamInhoud>) ReflectionUtil.getField(builder, "samengesteldeNaamStapel");
        final BrpSamengesteldeNaamInhoud naamInhoud = samengesteldeNaam.getLaatsteElement().getInhoud();
        assertEquals("van", BrpString.unwrap(naamInhoud.getVoorvoegsel()));
        assertEquals(Character.valueOf(' '), BrpCharacter.unwrap(naamInhoud.getScheidingsteken()));
    }

    @Test
    
    public void testConverteerVoorvoegselApostrof() {
        final List<Lo3Categorie<Lo3PersoonInhoud>> persoonCategorieen = new ArrayList<>();

        persoonCategorieen.add(Lo3Builder.createLo3Persoon(
            1234567890L,
            987654321,
            "Pierre",
            "JH",
            "d'",
            "Artagnan",
            19800101,
            "00000",
            "6030",
            "M",
            null,
            null,
            "E",
            "O",
            19800101,
            19800102,
            0));

        final Lo3Stapel<Lo3PersoonInhoud> persoonStapel = new Lo3Stapel<>(persoonCategorieen);
        final TussenPersoonslijstBuilder builder = new TussenPersoonslijstBuilder();

        persoonConverteerder.converteer(persoonStapel, false, builder);

        final TussenStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaam =
                (TussenStapel<BrpSamengesteldeNaamInhoud>) ReflectionUtil.getField(builder, "samengesteldeNaamStapel");
        final BrpSamengesteldeNaamInhoud naamInhoud = samengesteldeNaam.getLaatsteElement().getInhoud();
        assertEquals("d", BrpString.unwrap(naamInhoud.getVoorvoegsel()));
        assertEquals(Character.valueOf('\''), BrpCharacter.unwrap(naamInhoud.getScheidingsteken()));
    }

}
