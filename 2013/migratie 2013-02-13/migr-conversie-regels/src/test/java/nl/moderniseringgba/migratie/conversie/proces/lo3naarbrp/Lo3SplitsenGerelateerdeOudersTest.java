/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp;

import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.BijzondereSituaties;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.moderniseringgba.migratie.conversie.proces.AbstractLoggingTest;
import nl.moderniseringgba.migratie.testutils.VerplichteStapel;

import org.junit.Before;
import org.junit.Test;

/**
 * Test voor het splitsen van gerelateerde ouders.
 */
public class Lo3SplitsenGerelateerdeOudersTest extends AbstractLoggingTest {

    private Lo3SplitsenGerelateerdeOuders splitser;

    @Before
    public void setUp() {
        splitser = new Lo3SplitsenGerelateerdeOuders();
    }

    @Test
    public void testBijzondereConditieMeerdereOuder1Personen() {
        final Lo3Categorie<Lo3OuderInhoud> ouder1Actueel = VerplichteStapel.createOuder(1000000000L);
        final Lo3Categorie<Lo3OuderInhoud> ouder1Historie =
                VerplichteStapel.createOuder(2000000000L, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_02, 0, 1));

        final List<Lo3Categorie<Lo3OuderInhoud>> ouders = new ArrayList<Lo3Categorie<Lo3OuderInhoud>>();
        ouders.add(ouder1Actueel);
        ouders.add(ouder1Historie);

        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.ouder1Stapel(new Lo3Stapel<Lo3OuderInhoud>(ouders));

        final Lo3Persoonslijst lo3Persoonslijst = builder.build();

        splitser.converteer(lo3Persoonslijst);

        assertAantalInfos(1);
        assertBijzondereSituatie(BijzondereSituaties.BIJZ_CONV_LB016);
    }

    @Test
    public void testBijzondereConditieMeerdereOuder1ZelfdePersonen() {
        final long anummer = 1000000000L;
        final Lo3Categorie<Lo3OuderInhoud> ouder1Actueel = VerplichteStapel.createOuder(anummer);
        final Lo3Categorie<Lo3OuderInhoud> ouder1Historie =
                VerplichteStapel.createOuder(anummer, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_02, 0, 1));

        final List<Lo3Categorie<Lo3OuderInhoud>> ouders = new ArrayList<Lo3Categorie<Lo3OuderInhoud>>();
        ouders.add(ouder1Actueel);
        ouders.add(ouder1Historie);

        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.ouder1Stapel(new Lo3Stapel<Lo3OuderInhoud>(ouders));

        final Lo3Persoonslijst lo3Persoonslijst = builder.build();

        splitser.converteer(lo3Persoonslijst);

        assertAantalInfos(0);
    }
}
