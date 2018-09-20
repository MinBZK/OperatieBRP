/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.BijzondereSituatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.testutils.VerplichteStapel;
import nl.bzk.migratiebrp.conversie.regels.proces.AbstractLoggingTest;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3SplitsenGerelateerdeOuders;
import org.junit.Test;

/**
 * Test voor het splitsen van gerelateerde ouders.
 */
public class Lo3SplitsenGerelateerdeOudersTest extends AbstractLoggingTest {

    private static final int HISTORISCH_OUDERSCHAP = 19800101;

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB016)
    public void testBijzondereConditieMeerdereOuder1Personen() {
        final Lo3Categorie<Lo3OuderInhoud> ouder1Actueel = VerplichteStapel.createOuder(1000000000L, Lo3CategorieEnum.CATEGORIE_02);
        final Lo3Categorie<Lo3OuderInhoud> ouder1Historie =
                VerplichteStapel.createOuder(2000000000L, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_02, 0, 1), new Lo3Datum(HISTORISCH_OUDERSCHAP));

        final List<Lo3Categorie<Lo3OuderInhoud>> ouders = new ArrayList<>();
        ouders.add(ouder1Actueel);
        ouders.add(ouder1Historie);

        Lo3SplitsenGerelateerdeOuders.splitsOuders(new Lo3Stapel<>(ouders));

        assertAantalInfos(1);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB016, 1);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB016)
    public void testBijzondereConditieMeerdereOuder1ZelfdePersonen() {
        final long anummer = 1000000000L;
        final Lo3Categorie<Lo3OuderInhoud> ouder1Actueel = VerplichteStapel.createOuder(anummer, Lo3CategorieEnum.CATEGORIE_02);
        final Lo3Categorie<Lo3OuderInhoud> ouder1Historie =
                VerplichteStapel.createOuder(anummer, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_02, 0, 1), new Lo3Datum(HISTORISCH_OUDERSCHAP));

        final List<Lo3Categorie<Lo3OuderInhoud>> ouders = new ArrayList<>();
        ouders.add(ouder1Actueel);
        ouders.add(ouder1Historie);

        Lo3SplitsenGerelateerdeOuders.splitsOuders(new Lo3Stapel<>(ouders));

        assertAantalInfos(0);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB016, 0);
    }
}
