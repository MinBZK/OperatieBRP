/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.testutils.VerplichteStapel;
import nl.bzk.migratiebrp.conversie.regels.proces.AbstractConversieTest;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;

import org.junit.Assert;
import org.junit.Test;

public class Lo3HistorieConversieAlternatieveSorteringTest extends AbstractConversieTest {

    private static final Lo3Datum DEFAULT_DATUM = new Lo3Datum(19900101);

    private Lo3PersoonslijstBuilder maakPersoonslijstBuilder() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(VerplichteStapel.createPersoonStapel());
        builder.ouder1Stapel(VerplichteStapel.createOuderStapel(1111111111L, Lo3CategorieEnum.CATEGORIE_02));
        builder.ouder2Stapel(VerplichteStapel.createOuderStapel(2222222222L, Lo3CategorieEnum.CATEGORIE_03));
        builder.inschrijvingStapel(VerplichteStapel.createInschrijvingStapel());
        builder.verblijfplaatsStapel(VerplichteStapel.createVerblijfplaatsStapel());
        return builder;
    }

    /**
     * Maakt een nationaliteit stapel aan met 2 voorkomens. 1 gevulde (al dan niet onjuist) en 1 lege juiste rij.
     * 
     * @param historie
     *            historie voor de gevulde rij. Hiermee kan de gevulde rij onjuist gemaakt worden
     * @return een Lo3 nationaliteit stapel met 2 voorkomens
     */
    private Lo3Stapel<Lo3NationaliteitInhoud> maakNationaliteitStapel(final Lo3Historie historie) {
        final Lo3Herkomst c4Lo3Herkomst0 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 0);
        final Lo3Herkomst c4Lo3Herkomst1 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 0, 1);
        final Lo3NationaliteitInhoud c4InhoudDuits = new Lo3NationaliteitInhoud(new Lo3NationaliteitCode("6029"), null, null, null);
        final Lo3NationaliteitInhoud c4InhoudLegeRij = new Lo3NationaliteitInhoud(null, null, null, null);
        final Lo3Historie c4HistorieLegeRij = new Lo3Historie(null, DEFAULT_DATUM, DEFAULT_DATUM);

        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen = new ArrayList<>();
        categorieen.add(new Lo3Categorie<>(c4InhoudDuits, null, historie, c4Lo3Herkomst1));
        categorieen.add(new Lo3Categorie<>(c4InhoudLegeRij, null, c4HistorieLegeRij, c4Lo3Herkomst0));
        return new Lo3Stapel<>(categorieen);
    }

    @Test
    public void nationaliteitLegeRijOnjuisteGevuldeRij() throws OngeldigePersoonslijstException {
        final Lo3Historie historie = new Lo3Historie(new Lo3IndicatieOnjuist("O"), new Lo3Datum(19910101), new Lo3Datum(19890101));

        final Lo3PersoonslijstBuilder expectedBuilder = maakPersoonslijstBuilder();
        final Lo3Persoonslijst expectedLo3PL = expectedBuilder.build();

        final Lo3PersoonslijstBuilder builder = maakPersoonslijstBuilder();
        builder.nationaliteitStapel(maakNationaliteitStapel(historie));

        final Lo3Persoonslijst lo3PL = builder.build();
        final Lo3Persoonslijst checkedPL = preconditiesService.verwerk(lo3PL);
        Assert.assertNotNull(checkedPL);
        Assert.assertEquals(expectedLo3PL, checkedPL);

        final LogRegel regel = getLogRegel(Logging.getLogging().getRegels(), SoortMeldingCode.PRE050);
        Assert.assertNotNull(regel);
        Assert.assertEquals(LogSeverity.SUPPRESSED, regel.getSeverity());

        final BrpPersoonslijst brpPl = converteerLo3NaarBrpService.converteerLo3Persoonslijst(checkedPL);
        Assert.assertNotNull(brpPl);
        final List<BrpStapel<BrpNationaliteitInhoud>> brpNationaliteitInhoud = brpPl.getNationaliteitStapels();
        Assert.assertNotNull(brpNationaliteitInhoud);
    }

    @Test
    public void nationaliteitLegeRijJuistGevuldeRijWelPre050() throws OngeldigePersoonslijstException {
        final Lo3Historie historie = new Lo3Historie(null, DEFAULT_DATUM, new Lo3Datum(19910101));

        final Lo3PersoonslijstBuilder builder = maakPersoonslijstBuilder();
        builder.nationaliteitStapel(maakNationaliteitStapel(historie));

        final Lo3Persoonslijst lo3PL = builder.build();
        final Lo3Persoonslijst checkedPL = preconditiesService.verwerk(lo3PL);
        Assert.assertNotNull(checkedPL);
        Assert.assertEquals(lo3PL, checkedPL);

        final LogRegel regel = getLogRegel(Logging.getLogging().getRegels(), SoortMeldingCode.PRE050);
        Assert.assertNotNull(regel);
        Assert.assertEquals(LogSeverity.SUPPRESSED, regel.getSeverity());

        final BrpPersoonslijst brpPl = converteerLo3NaarBrpService.converteerLo3Persoonslijst(checkedPL);
        Assert.assertNotNull(brpPl);
        final List<BrpStapel<BrpNationaliteitInhoud>> brpNationaliteitInhoud = brpPl.getNationaliteitStapels();
        Assert.assertNotNull(brpNationaliteitInhoud);
        Assert.assertEquals(1, brpNationaliteitInhoud.size());
    }

    @Test
    public void nationaliteitLegeRijJuistGevuldeRijGeenPre050() throws OngeldigePersoonslijstException {
        final Lo3Historie historie = new Lo3Historie(null, new Lo3Datum(19890101), DEFAULT_DATUM);

        final Lo3PersoonslijstBuilder builder = maakPersoonslijstBuilder();
        builder.nationaliteitStapel(maakNationaliteitStapel(historie));

        final Lo3Persoonslijst lo3PL = builder.build();
        final Lo3Persoonslijst checkedPL = preconditiesService.verwerk(lo3PL);
        Assert.assertNotNull(checkedPL);
        Assert.assertEquals(lo3PL, checkedPL);

        final LogRegel regel = getLogRegel(Logging.getLogging().getRegels(), SoortMeldingCode.PRE050);
        Assert.assertNull(regel);
        final BrpPersoonslijst brpPl = converteerLo3NaarBrpService.converteerLo3Persoonslijst(checkedPL);

        Assert.assertNotNull(brpPl);
        final List<BrpStapel<BrpNationaliteitInhoud>> brpNationaliteitInhoud = brpPl.getNationaliteitStapels();
        Assert.assertNotNull(brpNationaliteitInhoud);
        Assert.assertEquals(1, brpNationaliteitInhoud.size());
    }

    private LogRegel getLogRegel(final Set<LogRegel> regels, final SoortMeldingCode soortMeldingCode) {
        for (final LogRegel regel : regels) {
            if (regel.getSoortMeldingCode().isPreconditie() && soortMeldingCode.name().equals(regel.getSoortMeldingCode().name())) {
                return regel;
            }
        }
        return null;
    }
}
