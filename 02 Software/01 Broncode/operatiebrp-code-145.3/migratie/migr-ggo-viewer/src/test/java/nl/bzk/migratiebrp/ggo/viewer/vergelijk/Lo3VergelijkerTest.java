/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.vergelijk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.testutils.StapelUtils;
import nl.bzk.migratiebrp.conversie.model.testutils.VerplichteStapel;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoVoorkomenVergelijking;
import nl.gba.gbav.impl.util.configuration.ServiceLocatorSpringImpl;
import nl.gba.gbav.util.configuration.ServiceLocator;
import org.junit.Test;

public class Lo3VergelijkerTest {

    private static final String DEN_HAAG = "0518";
    private final Lo3Vergelijker vergelijker = new Lo3Vergelijker();

    static {
        // Nodig voor de GBAV dependencies.
        if (!ServiceLocator.isInitialized()) {
            final String id = System.getProperty("gbav.deployment.id", "gbavContext");
            final String context = System.getProperty("gbav.deployment.context", "classpath:gbavconfig/deploymentContext.xml");
            ServiceLocator.initialize(new ServiceLocatorSpringImpl(context, id));
        }
    }

    @Test
    public void testVoorkomenVergelijking() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(VerplichteStapel.createPersoonStapel());
        builder.inschrijvingStapel(VerplichteStapel.createInschrijvingStapel());
        final Lo3Persoonslijst origineel = builder.build();

        final Lo3PersoonslijstBuilder builder2 = new Lo3PersoonslijstBuilder();
        builder2.persoonStapel(VerplichteStapel.createPersoonStapel());

        final Lo3InschrijvingInhoud inhoud =
                new Lo3InschrijvingInhoud(
                        null,
                        null,
                        null,
                        new Lo3Datum(18000101),
                        null,
                        null,
                        null,
                        null,
                        new Lo3Integer(1),
                        new Lo3Datumtijdstempel(19000101120000000L),
                        null);
        final Lo3Historie historie = new Lo3Historie(null, null, null);
        final Lo3Documentatie documentatie =
                new Lo3Documentatie(-1000, new Lo3GemeenteCode(DEN_HAAG), Lo3String.wrap("1Inschr-Akte"), null, null, null, null, null);

        final List<Lo3Categorie<Lo3InschrijvingInhoud>> categorieen = new ArrayList<>();
        categorieen.add(new Lo3Categorie<>(inhoud, documentatie, historie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0)));
        builder2.inschrijvingStapel(new Lo3Stapel<>(categorieen));
        final Lo3Persoonslijst teruggeconverteerd = builder2.build();

        final List<GgoVoorkomenVergelijking> resultaat = vergelijker.vergelijk(origineel, teruggeconverteerd);
        assertNotNull("Er moet een resultaat zijn.", resultaat);
        assertEquals("80.20", resultaat.get(0).getInhoud());
    }

    @Test
    public void testVoorkomenVergelijkingNietLeeg() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();

        final List<Lo3Categorie<Lo3PersoonInhoud>> persoonCategorieen = new ArrayList<>();
        persoonCategorieen.add(
                VerplichteStapel.buildPersoon(
                        "1000000000",
                        "Klaas",
                        "Jansen",
                        VerplichteStapel.GEBOORTE_DATUM,
                        "0363",
                        null,
                        19960101,
                        19960110,
                        6,
                        DEN_HAAG,
                        "3A"));
        persoonCategorieen.add(
                VerplichteStapel.buildPersoon(
                        "1000000000",
                        "Zlaas",
                        "Jansen",
                        VerplichteStapel.GEBOORTE_DATUM,
                        "0363",
                        null,
                        19950101,
                        19950110,
                        6,
                        DEN_HAAG,
                        "3A"));
        builder.persoonStapel(StapelUtils.createStapel(persoonCategorieen));

        builder.inschrijvingStapel(VerplichteStapel.createInschrijvingStapel());
        final Lo3Persoonslijst origineel = builder.build();

        final Lo3PersoonslijstBuilder builder2 = new Lo3PersoonslijstBuilder();

        final List<Lo3Categorie<Lo3PersoonInhoud>> persoonCategorieen2 = new ArrayList<>();
        persoonCategorieen2.add(
                VerplichteStapel.buildPersoon(
                        "1000000000",
                        "Klaas",
                        "Jansen",
                        VerplichteStapel.GEBOORTE_DATUM,
                        "0363",
                        null,
                        19960101,
                        19960110,
                        6,
                        DEN_HAAG,
                        "3A"));
        persoonCategorieen2.add(
                VerplichteStapel.buildPersoon(
                        "1000000000",
                        "Zlaas",
                        "Jansen",
                        VerplichteStapel.GEBOORTE_DATUM,
                        "0363",
                        null,
                        19950102,
                        19950111,
                        6,
                        "0519",
                        "3A"));
        builder2.persoonStapel(StapelUtils.createStapel(persoonCategorieen2));

        final Lo3InschrijvingInhoud inhoud =
                new Lo3InschrijvingInhoud(
                        null,
                        null,
                        null,
                        new Lo3Datum(18000101),
                        null,
                        null,
                        null,
                        null,
                        new Lo3Integer(1),
                        new Lo3Datumtijdstempel(19000101120000000L),
                        null);
        final Lo3Historie historie = new Lo3Historie(null, null, null);
        final Lo3Documentatie documentatie =
                new Lo3Documentatie(-1000, new Lo3GemeenteCode(DEN_HAAG), Lo3String.wrap("Inschr-Akte"), null, null, null, null, null);

        final List<Lo3Categorie<Lo3InschrijvingInhoud>> categorieen = new ArrayList<>();
        categorieen.add(new Lo3Categorie<>(inhoud, documentatie, historie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0)));
        builder2.inschrijvingStapel(new Lo3Stapel<>(categorieen));
        final Lo3Persoonslijst teruggeconverteerd = builder2.build();

        final List<GgoVoorkomenVergelijking> resultaat = vergelijker.vergelijk(origineel, teruggeconverteerd);
        assertNotNull("Er moet een resultaat zijn.", resultaat);
        for (final GgoVoorkomenVergelijking vergelijking : resultaat) {
            assertTrue("Er moet vulling zijn.", vergelijking.getInhoud() != null && !vergelijking.getInhoud().isEmpty());
        }
    }

    @Test
    public void testVoorkomenVergelijkingGelijk() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        final Lo3Persoonslijst origineel = builder.build();
        final Lo3Persoonslijst teruggeconverteerd = builder.build();

        final List<GgoVoorkomenVergelijking> resultaat = vergelijker.vergelijk(origineel, teruggeconverteerd);
        assertNotNull("Er moet een resultaat zijn.", resultaat);
        assertTrue("Er zouden geen verschillen moeten zijn.", resultaat.isEmpty());
    }
}
