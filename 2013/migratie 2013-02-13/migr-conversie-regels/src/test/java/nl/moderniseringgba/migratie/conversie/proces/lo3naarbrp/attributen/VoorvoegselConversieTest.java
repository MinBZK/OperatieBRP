/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.AbstractComponentTest;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Builder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratiePersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieStapel;
import nl.moderniseringgba.migratie.conversie.validatie.InputValidationException;
import nl.moderniseringgba.migratie.testutils.ReflectionUtil;

import org.junit.Test;

public class VoorvoegselConversieTest extends AbstractComponentTest {

    @Inject
    private PersoonConverteerder persoonConverteerder;

    @Test
    @SuppressWarnings("unchecked")
    public void testConverteerVoorvoegsel() throws InputValidationException {
        final List<Lo3Categorie<Lo3PersoonInhoud>> persoonCategorieen =
                new ArrayList<Lo3Categorie<Lo3PersoonInhoud>>();

        persoonCategorieen.add(Lo3Builder.createLo3Persoon(1234567890L, 987654321L, "Piet Jan", "JH", "van",
                "HorenZeggen", 19800101, "00000", "6030", "M", "E", null, null, "O", 19800101, 19800102));

        final Lo3Stapel<Lo3PersoonInhoud> persoonStapel = new Lo3Stapel<Lo3PersoonInhoud>(persoonCategorieen);
        final MigratiePersoonslijstBuilder builder = new MigratiePersoonslijstBuilder();

        persoonConverteerder.converteer(persoonStapel, builder);

        final MigratieStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaam =
                (MigratieStapel<BrpSamengesteldeNaamInhoud>) ReflectionUtil.getField(builder,
                        "samengesteldeNaamStapel");
        final BrpSamengesteldeNaamInhoud naamInhoud = samengesteldeNaam.getMeestRecenteElement().getInhoud();
        assertEquals("van", naamInhoud.getVoorvoegsel());
        assertEquals(new Character(' '), naamInhoud.getScheidingsteken());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testConverteerVoorvoegselApostrof() throws InputValidationException {
        final List<Lo3Categorie<Lo3PersoonInhoud>> persoonCategorieen =
                new ArrayList<Lo3Categorie<Lo3PersoonInhoud>>();

        persoonCategorieen.add(Lo3Builder.createLo3Persoon(1234567890L, 987654321L, "Pierre", "JH", "d'", "Artagnan",
                19800101, "00000", "6030", "M", "E", null, null, "O", 19800101, 19800102));

        final Lo3Stapel<Lo3PersoonInhoud> persoonStapel = new Lo3Stapel<Lo3PersoonInhoud>(persoonCategorieen);
        final MigratiePersoonslijstBuilder builder = new MigratiePersoonslijstBuilder();

        persoonConverteerder.converteer(persoonStapel, builder);

        final MigratieStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaam =
                (MigratieStapel<BrpSamengesteldeNaamInhoud>) ReflectionUtil.getField(builder,
                        "samengesteldeNaamStapel");
        final BrpSamengesteldeNaamInhoud naamInhoud = samengesteldeNaam.getMeestRecenteElement().getInhoud();
        assertEquals("d", naamInhoud.getVoorvoegsel());
        assertEquals(new Character('\''), naamInhoud.getScheidingsteken());
    }

}
