/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3RedenOpschortingBijhoudingCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.testutils.StapelUtils;
import nl.bzk.migratiebrp.conversie.model.testutils.VerplichteStapel;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import org.junit.Test;

public class Lo3PersoonslijstOpschonerTest {
    private static final String GEM_CODE = "0518";

    @Test
    public void testOpschonenSchoon() {
        Logging.initContext();
        final Lo3PersoonslijstOpschoner opschoner = new Lo3PersoonslijstOpschoner();
        final Lo3Persoonslijst pl = Lo3PersoonslijstOpschonerTest.buildLo3Persoonslijst(false);
        final Lo3Persoonslijst schoonPl = opschoner.opschonen(pl);
        Assert.assertEquals(pl, schoonPl);
        Logging.destroyContext();
    }

    @Test
    public void testOpschonenWarning() {
        Logging.initContext();
        final Lo3PersoonslijstOpschoner opschoner = new Lo3PersoonslijstOpschoner();
        final Lo3Persoonslijst pl = Lo3PersoonslijstOpschonerTest.buildLo3Persoonslijst(false);
        Logging.log(pl.getOuder2Stapel().getLaatsteElement().getLo3Herkomst(), LogSeverity.WARNING, SoortMeldingCode.LENGTE, null);
        final Lo3Persoonslijst schoonPl = opschoner.opschonen(pl);
        Assert.assertEquals(pl, schoonPl);
        Logging.destroyContext();
    }

    @Test
    public void testOpschonenError() {
        Logging.initContext();
        final Lo3PersoonslijstOpschoner opschoner = new Lo3PersoonslijstOpschoner();
        final Lo3Persoonslijst pl = Lo3PersoonslijstOpschonerTest.buildLo3Persoonslijst(false);
        Logging.log(pl.getOuder2Stapel().getLaatsteElement().getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.LENGTE, null);
        Assert.assertTrue(Logging.getLogging().containSeverityLevelError());
        final Lo3Persoonslijst schoonPl = opschoner.opschonen(pl);
        Assert.assertTrue(Logging.getLogging().containSeverityLevelError());
        Assert.assertEquals(pl, schoonPl);
        Logging.destroyContext();
    }

    @Test
    public void testOpschonenErrorOpgeschortPL() {
        Logging.initContext();
        final Lo3PersoonslijstOpschoner opschoner = new Lo3PersoonslijstOpschoner();
        final Lo3Persoonslijst pl = Lo3PersoonslijstOpschonerTest.buildLo3Persoonslijst(true);
        Logging.log(pl.getOuder2Stapel().getLaatsteElement().getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.LENGTE, null);
        Assert.assertTrue(Logging.getLogging().containSeverityLevelError());
        final Lo3Persoonslijst schoonPl = opschoner.opschonen(pl);
        Assert.assertFalse(Logging.getLogging().containSeverityLevelError());
        Assert.assertNotSame(pl, schoonPl);
        Logging.destroyContext();
    }

    @Test
    public void testOpschonenAfgevoerdError() {
        Logging.initContext();
        final Lo3PersoonslijstOpschoner opschoner = new Lo3PersoonslijstOpschoner();

        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(VerplichteStapel.createPersoonStapel());

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
                new Lo3Documentatie(-1000, new Lo3GemeenteCode(GEM_CODE), Lo3String.wrap("Inschr-Akte"), null, null, null, null, null);

        categorieen.add(new Lo3Categorie<>(inhoud, documentatie, historie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0)));

        builder.inschrijvingStapel(new Lo3Stapel<>(categorieen));
        builder.verblijfplaatsStapel(VerplichteStapel.createVerblijfplaatsStapel());
        builder.ouder1Stapel(VerplichteStapel.createOuder1Stapel());
        builder.ouder2Stapel(VerplichteStapel.createOuder2Stapel());
        final Lo3Persoonslijst pl = builder.build();

        Logging.log(pl.getOuder2Stapel().getLaatsteElement().getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.LENGTE, null);
        Assert.assertTrue(Logging.getLogging().containSeverityLevelError());

        final Lo3Persoonslijst opgeschoondePl = opschoner.opschonen(pl);
        Assert.assertTrue(opgeschoondePl.isDummyPl());

        Logging.destroyContext();
    }

    @Test
    public void testOpschonenErrorOnjuist() {
        Logging.initContext();
        final Lo3PersoonslijstOpschoner opschoner = new Lo3PersoonslijstOpschoner();
        final Lo3Persoonslijst pl = Lo3PersoonslijstOpschonerTest.buildLo3Persoonslijst(false);
        Logging.log(pl.getOuder1Stapel().getLaatsteElement().getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.PRE001, null);
        final Lo3Categorie<Lo3OuderInhoud> ouder1element = pl.getOuder1Stapel().getLaatsteElement();
        final Lo3Categorie<Lo3OuderInhoud> onjuistOuder1Element =
                new Lo3Categorie<>(
                        ouder1element.getInhoud(),
                        ouder1element.getDocumentatie(),
                        new Lo3Historie(
                                Lo3IndicatieOnjuist.O,
                                ouder1element.getHistorie().getIngangsdatumGeldigheid(),
                                ouder1element.getHistorie().getDatumVanOpneming()),
                        ouder1element.getLo3Herkomst());

        final List<Lo3Categorie<Lo3OuderInhoud>> ouder1stapelInhoud = new ArrayList<>();
        ouder1stapelInhoud.add(onjuistOuder1Element);

        final Lo3PersoonslijstBuilder builder2 = new Lo3PersoonslijstBuilder(pl);
        builder2.ouder1Stapel(new Lo3Stapel<>(ouder1stapelInhoud));
        final Lo3Persoonslijst pl2 = builder2.build();

        Assert.assertTrue(Logging.getLogging().containSeverityLevelError());
        final Lo3Persoonslijst schoonPl = opschoner.opschonen(pl2);
        Assert.assertFalse(Logging.getLogging().containSeverityLevelError());
        Assert.assertNull(schoonPl.getOuder1Stapel());
        Logging.destroyContext();
    }

    private Lo3Stapel<Lo3NationaliteitInhoud> maakNationaliteitStapel(final boolean gevuldeRijOnjuist) {
        final Lo3Datum defaultDatum = new Lo3Datum(19900101);
        final Lo3Herkomst c4Lo3Herkomst0 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 0);
        final Lo3Herkomst c4Lo3Herkomst1 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 0, 1);

        final Lo3NationaliteitInhoud c4InhoudDuits = new Lo3NationaliteitInhoud(new Lo3NationaliteitCode("6029"), null, null, null, null);
        final Lo3NationaliteitInhoud c4InhoudLegeRij = new Lo3NationaliteitInhoud(null, null, null, null, null);
        final Lo3Historie c4HistorieLegeRij = new Lo3Historie(null, defaultDatum, defaultDatum);

        final Lo3IndicatieOnjuist onjuist = gevuldeRijOnjuist ? new Lo3IndicatieOnjuist("O") : null;
        final Lo3Historie historie = new Lo3Historie(onjuist, new Lo3Datum(19910101), new Lo3Datum(19890101));

        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen = new ArrayList<>();
        categorieen.add(new Lo3Categorie<>(c4InhoudDuits, null, historie, c4Lo3Herkomst1));
        categorieen.add(new Lo3Categorie<>(c4InhoudLegeRij, null, c4HistorieLegeRij, c4Lo3Herkomst0));

        return new Lo3Stapel<>(categorieen);
    }

    @Test
    public void testOpschonenPre050GevuldeRijOnjuist() {
        Logging.initContext();
        final Lo3PersoonslijstOpschoner opschoner = new Lo3PersoonslijstOpschoner();
        final Lo3Persoonslijst pl = Lo3PersoonslijstOpschonerTest.buildLo3Persoonslijst(false);
        final Lo3Stapel<Lo3NationaliteitInhoud> nationaliteitStapel = maakNationaliteitStapel(true);

        final Lo3Categorie<Lo3NationaliteitInhoud> nationaliteitLegeRij = nationaliteitStapel.get(1);
        Logging.log(nationaliteitLegeRij.getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.PRE050, null);

        pl.getNationaliteitStapels().add(nationaliteitStapel);

        Assert.assertTrue(Logging.getLogging().containSeverityLevelError());
        final Lo3Persoonslijst schoonPl = opschoner.opschonen(pl);
        Assert.assertFalse(Logging.getLogging().containSeverityLevelError());
        Assert.assertTrue(schoonPl.getNationaliteitStapels().isEmpty());
        Logging.destroyContext();
    }

    // Preconditie 056 kan niet optreden op een Nationaliteit, maar dat weet de opschoner niet.
    @Test
    public void testOpschonenPre056GevuldeRijOnjuist() {
        Logging.initContext();
        final Lo3PersoonslijstOpschoner opschoner = new Lo3PersoonslijstOpschoner();
        final Lo3Persoonslijst pl = Lo3PersoonslijstOpschonerTest.buildLo3Persoonslijst(false);
        final Lo3Stapel<Lo3NationaliteitInhoud> nationaliteitStapel = maakNationaliteitStapel(true);

        final Lo3Categorie<Lo3NationaliteitInhoud> nationaliteitLegeRij = nationaliteitStapel.get(1);
        Logging.log(nationaliteitLegeRij.getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.PRE056, null);

        pl.getNationaliteitStapels().add(nationaliteitStapel);

        Assert.assertTrue(Logging.getLogging().containSeverityLevelError());
        final Lo3Persoonslijst schoonPl = opschoner.opschonen(pl);
        Assert.assertFalse(Logging.getLogging().containSeverityLevelError());
        Assert.assertTrue(schoonPl.getNationaliteitStapels().isEmpty());
        Logging.destroyContext();
    }

    @Test
    public void testOpschonenPre050GevuldeRijJuist() {
        Logging.initContext();
        final Lo3PersoonslijstOpschoner opschoner = new Lo3PersoonslijstOpschoner();
        final Lo3Persoonslijst pl = Lo3PersoonslijstOpschonerTest.buildLo3Persoonslijst(false);
        final Lo3Stapel<Lo3NationaliteitInhoud> nationaliteitStapel = maakNationaliteitStapel(false);

        final Lo3Categorie<Lo3NationaliteitInhoud> nationaliteitLegeRij = nationaliteitStapel.get(1);
        Logging.log(nationaliteitLegeRij.getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.PRE050, null);

        pl.getNationaliteitStapels().add(nationaliteitStapel);

        Assert.assertTrue(Logging.getLogging().containSeverityLevelError());
        final Lo3Persoonslijst schoonPl = opschoner.opschonen(pl);
        Assert.assertFalse(Logging.getLogging().containSeverityLevelError());
        Assert.assertFalse(schoonPl.getNationaliteitStapels().isEmpty());
        Assert.assertEquals(schoonPl.getNationaliteitStapels().size(), 1);
        Logging.destroyContext();
    }

    @Test
    public void testOpschonenErrorLeeg() {
        Logging.initContext();
        final Lo3PersoonslijstOpschoner opschoner = new Lo3PersoonslijstOpschoner();
        final Lo3Persoonslijst pl = Lo3PersoonslijstOpschonerTest.buildLo3Persoonslijst(false);
        Logging.log(pl.getOuder1Stapel().getLaatsteElement().getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.PRE001, null);
        final Lo3Categorie<Lo3OuderInhoud> ouder1element = pl.getOuder1Stapel().getLaatsteElement();
        final Lo3Categorie<Lo3OuderInhoud> leegOuder1Element =
                new Lo3Categorie<>(new Lo3OuderInhoud(), ouder1element.getDocumentatie(), ouder1element.getHistorie(), ouder1element.getLo3Herkomst());

        final List<Lo3Categorie<Lo3OuderInhoud>> ouder1stapelInhoud = new ArrayList<>();
        ouder1stapelInhoud.add(leegOuder1Element);

        final Lo3PersoonslijstBuilder builder2 = new Lo3PersoonslijstBuilder(pl);
        builder2.ouder1Stapel(new Lo3Stapel<>(ouder1stapelInhoud));
        final Lo3Persoonslijst pl2 = builder2.build();

        Assert.assertTrue(Logging.getLogging().containSeverityLevelError());
        final Lo3Persoonslijst schoonPl = opschoner.opschonen(pl2);
        Assert.assertFalse(Logging.getLogging().containSeverityLevelError());
        Assert.assertNull(schoonPl.getOuder1Stapel());
        Logging.destroyContext();
    }

    @Test
    public void testOpschonenAfgevoerdGeenAnr() {
        Logging.initContext();
        final Lo3PersoonslijstOpschoner opschoner = new Lo3PersoonslijstOpschoner();

        final List<Lo3Categorie<Lo3PersoonInhoud>> cat01Categorieen = new ArrayList<>();
        cat01Categorieen.add(VerplichteStapel.buildPersoon(null, "Klaas", "Jansen", 19900101, "0363", null, 19950101, 19950110, 6, GEM_CODE, "A3"));

        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(StapelUtils.createStapel(cat01Categorieen));

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
                new Lo3Documentatie(-1000, new Lo3GemeenteCode(GEM_CODE), Lo3String.wrap("Inschr-Akte"), null, null, null, null, null);

        categorieen.add(new Lo3Categorie<>(inhoud, documentatie, historie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0)));

        builder.inschrijvingStapel(new Lo3Stapel<>(categorieen));
        builder.verblijfplaatsStapel(VerplichteStapel.createVerblijfplaatsStapel());
        builder.ouder1Stapel(VerplichteStapel.createOuder1Stapel());
        builder.ouder2Stapel(VerplichteStapel.createOuder2Stapel());
        final Lo3Persoonslijst pl = builder.build();

        Logging.log(pl.getOuder2Stapel().getLaatsteElement().getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.LENGTE, null);
        Assert.assertTrue(Logging.getLogging().containSeverityLevelError());

        final Lo3Persoonslijst opgeschoondePl = opschoner.opschonen(pl);
        Assert.assertFalse(opgeschoondePl.isDummyPl());
        // Zonder a-nummer kan geen dummy gemaakt worden. Errors blijven dus staan.
        Assert.assertTrue(Logging.getLogging().containSeverityLevelError());

        Logging.destroyContext();
    }

    @Test
    public void testOpschonenLegeCat02En03() {
        Logging.initContext();
        final Lo3PersoonslijstOpschoner opschoner = new Lo3PersoonslijstOpschoner();

        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(VerplichteStapel.createPersoonStapel());

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
                new Lo3Documentatie(-1000, new Lo3GemeenteCode(GEM_CODE), Lo3String.wrap("Inschr-Akte"), null, null, null, null, null);

        categorieen.add(new Lo3Categorie<>(inhoud, documentatie, historie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0)));

        builder.inschrijvingStapel(new Lo3Stapel<>(categorieen));
        builder.verblijfplaatsStapel(VerplichteStapel.createVerblijfplaatsStapel());
        final Lo3Persoonslijst pl = builder.build();

        Logging.log(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_02, -1, -1), LogSeverity.ERROR, SoortMeldingCode.PRE065, null);
        Logging.log(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_03, -1, -1), LogSeverity.ERROR, SoortMeldingCode.PRE066, null);
        Assert.assertTrue(Logging.getLogging().containSeverityLevelError());

        final Lo3Persoonslijst opgeschoondePl = opschoner.opschonen(pl);
        Assert.assertTrue(opgeschoondePl.isDummyPl());

        Logging.destroyContext();
    }

    @Test
    public void testIsErrorInJuisteCategorieStapelsNull() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Lo3PersoonslijstOpschoner opschoner = new Lo3PersoonslijstOpschoner();
        final Method pm = getDeclareMethodIsErrorInJuisteCat();
        Assert.assertFalse((boolean) pm.invoke(opschoner, null, Lo3CategorieEnum.CATEGORIE_02, Logging.getLogging()));
    }

    @Test
    public void testIsErrorInJuisteCategorieNoLogging() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Lo3PersoonslijstOpschoner opschoner = new Lo3PersoonslijstOpschoner();
        final Method pm = getDeclareMethodIsErrorInJuisteCat();
        Assert.assertFalse((boolean) pm.invoke(opschoner, Collections.EMPTY_LIST, Lo3CategorieEnum.CATEGORIE_02, Logging.getLogging()));
    }

    @Test
    public void testIsErrorInJuisteCategorieWithCorrectLogging() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Lo3PersoonslijstOpschoner opschoner = new Lo3PersoonslijstOpschoner();
        final Method pm = getDeclareMethodIsErrorInJuisteCat();
        Logging.initContext();
        Logging.log(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_02, 0, 0), LogSeverity.ERROR, SoortMeldingCode.PRE065, null);

        final Lo3Stapel<Lo3OuderInhoud> stapel = VerplichteStapel.createOuder1Stapel();
        final List<Lo3Stapel<Lo3OuderInhoud>> stapels = new ArrayList<>();
        stapels.add(stapel);

        Assert.assertTrue((boolean) pm.invoke(opschoner, stapels, Lo3CategorieEnum.CATEGORIE_02, Logging.getLogging()));
    }

    @Test
    public void testIsErrorInJuisteCategorieWithLoggingForWrongCat() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Lo3PersoonslijstOpschoner opschoner = new Lo3PersoonslijstOpschoner();
        final Method pm = getDeclareMethodIsErrorInJuisteCat();
        Logging.initContext();
        Logging.log(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_03, 0, 0), LogSeverity.ERROR, SoortMeldingCode.PRE065, null);

        final Lo3Stapel<Lo3OuderInhoud> stapel = VerplichteStapel.createOuder1Stapel();
        final List<Lo3Stapel<Lo3OuderInhoud>> stapels = new ArrayList<>();
        stapels.add(stapel);

        Assert.assertFalse((boolean) pm.invoke(opschoner, stapels, Lo3CategorieEnum.CATEGORIE_02, Logging.getLogging()));
    }

    private Method getDeclareMethodIsErrorInJuisteCat() throws NoSuchMethodException {
        final Class[] cArg = new Class[3];
        cArg[0] = List.class;
        cArg[1] = Lo3CategorieEnum.class;
        cArg[2] = Logging.class;
        final Method pm = Lo3PersoonslijstOpschoner.class.getDeclaredMethod("isErrorInJuisteCategorie", cArg);
        pm.setAccessible(true);
        return pm;
    }

    private static Lo3Persoonslijst buildLo3Persoonslijst(final boolean opgeschort) {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(VerplichteStapel.createPersoonStapel());
        if (opgeschort) {
            builder.inschrijvingStapel(VerplichteStapel.createInschrijvingStapelOpgeschort());
        } else {
            builder.inschrijvingStapel(VerplichteStapel.createInschrijvingStapel());
        }
        builder.verblijfplaatsStapel(VerplichteStapel.createVerblijfplaatsStapel());
        builder.ouder1Stapel(VerplichteStapel.createOuder1Stapel());
        builder.ouder2Stapel(VerplichteStapel.createOuder2Stapel());

        return builder.build();
    }
}
