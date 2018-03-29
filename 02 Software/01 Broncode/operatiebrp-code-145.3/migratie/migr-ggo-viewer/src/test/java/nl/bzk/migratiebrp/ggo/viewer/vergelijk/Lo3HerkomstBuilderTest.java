/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.vergelijk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.testutils.StapelUtils;
import nl.bzk.migratiebrp.conversie.model.testutils.VerplichteStapel;
import nl.bzk.migratiebrp.ggo.viewer.Lo3PersoonslijstTestHelper;
import nl.gba.gbav.impl.util.configuration.ServiceLocatorSpringImpl;
import nl.gba.gbav.util.configuration.ServiceLocator;
import org.junit.Test;

public class Lo3HerkomstBuilderTest {

    private final Lo3HerkomstBuilder lo3HerkomstBuilder = new Lo3HerkomstBuilder();

    static {
        // Nodig voor de GBAV dependencies.
        if (!ServiceLocator.isInitialized()) {
            final String id = System.getProperty("gbav.deployment.id", "gbavContext");
            final String context = System.getProperty("gbav.deployment.context", "classpath:gbavconfig/deploymentContext.xml");
            ServiceLocator.initialize(new ServiceLocatorSpringImpl(context, id));
        }
    }

    @Test
    public void testHerkomstBuilderGelijk() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(VerplichteStapel.createPersoonStapel());
        builder.inschrijvingStapel(VerplichteStapel.createInschrijvingStapel());

        final Lo3Persoonslijst origineel = builder.build();
        final Lo3Persoonslijst teruggeconverteerd = Lo3PersoonslijstTestHelper.kopieerLo3Persoonslijst(origineel, false);

        final Lo3Persoonslijst teruggeconverteerdMetHerkomst =
                lo3HerkomstBuilder.kopieerTerugconversiePlMetHerkomst(origineel, teruggeconverteerd);
        assertNotNull("Er moet een resultaat zijn.", teruggeconverteerdMetHerkomst);
        assertEquals(
                "Herkomst zou gelijk moeten zijn.",
                origineel.getPersoonStapel().get(0).getLo3Herkomst().toString(),
                teruggeconverteerdMetHerkomst.getPersoonStapel().get(0).getLo3Herkomst().toString());
    }

    @Test
    public void testHerkomstBuilderVerschil() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(VerplichteStapel.createPersoonStapel());
        builder.inschrijvingStapel(VerplichteStapel.createInschrijvingStapel());
        final Lo3Persoonslijst origineel = builder.build();

        final Lo3PersoonslijstBuilder builder2 = new Lo3PersoonslijstBuilder();
        final List<Lo3Categorie<Lo3PersoonInhoud>> persoonCategorieen = new ArrayList<>();
        persoonCategorieen.add(VerplichteStapel.buildPersoon(
                "1000000000",
                "Zlaas",
                "Jansen",
                VerplichteStapel.GEBOORTE_DATUM,
                "0363",
                null,
                19950101,
                19950110,
                6,
                "0518",
                "3A"));
        persoonCategorieen.add(VerplichteStapel.buildPersoon(
                "1000000000",
                "Klaas",
                "Jansen",
                VerplichteStapel.GEBOORTE_DATUM,
                "0363",
                null,
                19960101,
                19960110,
                6,
                "0518",
                "3A"));
        builder2.persoonStapel(StapelUtils.createStapel(persoonCategorieen));

        builder2.inschrijvingStapel(VerplichteStapel.createInschrijvingStapel());
        Lo3Persoonslijst teruggeconverteerd = builder2.build();
        teruggeconverteerd = Lo3PersoonslijstTestHelper.kopieerLo3Persoonslijst(teruggeconverteerd, false);

        final Lo3Persoonslijst teruggeconverteerdMetHerkomst =
                lo3HerkomstBuilder.kopieerTerugconversiePlMetHerkomst(origineel, teruggeconverteerd);
        assertNotNull("Er moet een resultaat zijn.", teruggeconverteerdMetHerkomst);
        assertEquals(
                "Herkomst zou gelijk moeten zijn.",
                origineel.getPersoonStapel().get(0).getLo3Herkomst().toString(),
                teruggeconverteerdMetHerkomst.getPersoonStapel().get(0).getLo3Herkomst().toString());
    }
}
