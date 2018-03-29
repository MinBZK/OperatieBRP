/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import org.junit.Assert;
import nl.bzk.migratiebrp.conversie.model.BijzondereSituatie;
import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3RedenOpschortingBijhoudingCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.testutils.StapelUtils;
import nl.bzk.migratiebrp.conversie.model.testutils.VerplichteStapel;
import nl.bzk.migratiebrp.conversie.regels.proces.AbstractLoggingTest;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3.Lo3PersoonslijstPreconditiesTest;
import org.junit.Test;

public class PreconditiesServiceTest extends AbstractLoggingTest {

    @Inject
    private PreconditiesService subject;

    @Test
    public void testVerwerk() {
        final Lo3PersoonslijstPreconditiesTest helper = new Lo3PersoonslijstPreconditiesTest();

        try {
            subject.verwerk(helper.builder().persoonStapel(null).build());
            // fail if no exception thrown
            Assert.fail("OngeldigePersoonslijstException verwacht");
        } catch (final OngeldigePersoonslijstException e) {
            final Set<LogRegel> logRegels = Logging.getLogging().getRegels();
            Assert.assertEquals("Verwacht 1 logregel", 1, logRegels.size());
            Assert.assertEquals("Verwacht PRE047", "PRE047", logRegels.iterator().next().getSoortMeldingCode().toString());
        }
    }

    @Test
    public void testVerwerkAfgevoerdePlMetPrecondities() throws OngeldigePersoonslijstException {
        final Lo3Persoonslijst plNietGeschoond = builder(createPersoonStapel()).build();
        Logging.log(plNietGeschoond.getOuder2Stapel().getLaatsteElement().getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.LENGTE, null);
        final Lo3Persoonslijst pl = subject.verwerk(plNietGeschoond);
        // Check dat het een dummy pl is.
        Assert.assertNotNull("De pl zou niet null moeten zijn.", pl);
        Assert.assertEquals("Anr zou gelijk moeten zijn", pl.getActueelAdministratienummer(), "1000000000");
        Assert.assertNull(pl.getPersoonStapel().getLaatsteElement().getInhoud().getGeslachtsnaam());
        Assert.assertNull(pl.getPersoonStapel().getLaatsteElement().getInhoud().getVoornamen());
        Assert.assertNull(pl.getGezagsverhoudingStapel());
        Assert.assertNotNull(pl.getInschrijvingStapel());
        Assert.assertNull(pl.getKiesrechtStapel());
        Assert.assertNull(pl.getOverlijdenStapel());
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB011)
    @Preconditie(SoortMeldingCode.PRE005)
    public void testVerwerkAfgevoerdePlMetPreconditiesGeenAnr() {
        final Lo3Persoonslijst plNietGeschoond = builder(createPersoonStapelGeenAnr()).build();
        Logging.log(plNietGeschoond.getOuder2Stapel().getLaatsteElement().getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.LENGTE, null);
        try {
            subject.verwerk(plNietGeschoond);
            Assert.fail("Er zou een ongeldigePersoonslijstException op moeten treden.");
        } catch (final OngeldigePersoonslijstException e) {
            assertSoortMeldingCode(SoortMeldingCode.PRE005, 1);
            assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB011, 1);
        }
    }

    public Lo3PersoonslijstBuilder builder(final Lo3Stapel<Lo3PersoonInhoud> persoonStapel) {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(persoonStapel);

        final List<Lo3Categorie<Lo3InschrijvingInhoud>> categorieen = new ArrayList<>();

        final Lo3InschrijvingInhoud inhoud =
                new Lo3InschrijvingInhoud(
                        null,
                        new Lo3Datum(20110101),
                        new Lo3RedenOpschortingBijhoudingCode(Lo3RedenOpschortingBijhoudingCodeEnum.FOUT.getCode()),
                        new Lo3Datum(18000101),
                        null,
                        null,
                        null,
                        null,
                        new Lo3Integer(1),
                        new Lo3Datumtijdstempel(18000101120000000L),
                        null);
        final Lo3Historie historie = new Lo3Historie(null, null, null);
        final Lo3Documentatie documentatie =
                new Lo3Documentatie(-1000, new Lo3GemeenteCode("0518"), Lo3String.wrap("Inschr-Akte"), null, null, null, null, null);

        categorieen.add(new Lo3Categorie<>(inhoud, documentatie, historie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0)));

        builder.inschrijvingStapel(new Lo3Stapel<>(categorieen));
        builder.verblijfplaatsStapel(VerplichteStapel.createVerblijfplaatsStapel());
        builder.ouder1Stapel(VerplichteStapel.createOuder1Stapel());
        builder.ouder2Stapel(VerplichteStapel.createOuder2Stapel());
        return builder;
    }

    private Lo3Stapel<Lo3PersoonInhoud> createPersoonStapel() {
        return VerplichteStapel.createPersoonStapel();
    }

    private Lo3Stapel<Lo3PersoonInhoud> createPersoonStapelGeenAnr() {
        final List<Lo3Categorie<Lo3PersoonInhoud>> categorieen = new ArrayList<>();
        categorieen.add(VerplichteStapel.buildPersoon(null, "Klaas", "Jansen", 19900101, "0363", null, 19950101, 19950110, 6, "0518", "3A"));
        return StapelUtils.createStapel(categorieen);
    }
}
