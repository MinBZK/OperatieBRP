/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp;

import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper.lo3Cat;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper.lo3Inschrijving;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper.lo3Ouder;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper.lo3Persoon;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper.lo3Stapel;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper.lo3Verblijfplaats;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieOnjuistEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.regels.proces.AbstractConversieTest;
import org.junit.Before;
import org.junit.Test;

public class OverlijdenIntegraleConversieTest extends AbstractConversieTest {

    private static final Lo3IndicatieOnjuistEnum JUIST = null;
    private static final Lo3IndicatieOnjuistEnum ONJUIST = Lo3IndicatieOnjuistEnum.ONJUIST;
    private static final Lo3GemeenteCode GEMEENTE_CODE = new Lo3GemeenteCode("0599");
    private static final Lo3LandCode LAND_CODE = Lo3LandCode.NEDERLAND;

    private int voorkomen;
    private final List<Lo3Categorie<Lo3OverlijdenInhoud>> lo3Categorieen = new LinkedList<>();
    private BrpPersoonslijst brpPl;

    @Before
    public void reset() {
        voorkomen = 0;
        lo3Categorieen.clear();
    }

    @Test
    public void testCasus01() throws OngeldigePersoonslijstException {
        maakLegeRij(JUIST, 20120101, 20120102);
        maakGevuldeRij(JUIST, 20120101, 20130102);

        converteer();

        assertFalse(brpPl.getOverlijdenStapel().bevatActueel());
    }

    @Test
    public void testCasus02() throws OngeldigePersoonslijstException {
        maakLegeRij(JUIST, 20120101, 20120102);
        maakGevuldeRij(ONJUIST, 20120101, 20130102);

        converteer();

        assertNull(brpPl.getOverlijdenStapel());
    }

    @Test
    public void testCasus03() throws OngeldigePersoonslijstException {
        maakGevuldeRij(JUIST, 20120101, 20120102);
        maakLegeRij(JUIST, 20120101, 20130102);

        converteer();

        assertTrue(brpPl.getOverlijdenStapel().bevatActueel());
    }

    @Test
    public void testCasus04() throws OngeldigePersoonslijstException {
        maakGevuldeRij(JUIST, 20120101, 20120102);
        maakLegeRij(ONJUIST, 20120101, 20130102);

        converteer();

        assertTrue(brpPl.getOverlijdenStapel().bevatActueel());
    }

    @Test
    public void testCasus05() throws OngeldigePersoonslijstException {
        maakLegeRij(JUIST, 20120101, 20120102);
        maakGevuldeRij(JUIST, 20120101, 20130102);
        maakGevuldeRij(ONJUIST, 20120101, 20110102);

        converteer();

        assertFalse(brpPl.getOverlijdenStapel().bevatActueel());
    }

    @Test
    public void testCasus06() throws OngeldigePersoonslijstException {
        maakLegeRij(JUIST, 20120101, 20120102);
        maakGevuldeRij(ONJUIST, 20120101, 20130102);
        maakGevuldeRij(ONJUIST, 20120101, 20110102);

        converteer();

        assertFalse(brpPl.getOverlijdenStapel().bevatActueel());
    }

    private void maakLegeRij(final Lo3IndicatieOnjuistEnum onjuist, final int geldigheid, final int opneming) {
        lo3Categorieen.add(new Lo3Categorie<>(new Lo3OverlijdenInhoud(null, null, null), null, new Lo3Historie(onjuist != null ? onjuist.asElement()
                                                                                                                              : null, new Lo3Datum(
            geldigheid), new Lo3Datum(opneming)), volgendeHerkomst()));
    }

    private void maakGevuldeRij(final Lo3IndicatieOnjuistEnum onjuist, final int geldigheid, final int opneming) {
        lo3Categorieen.add(new Lo3Categorie<>(new Lo3OverlijdenInhoud(new Lo3Datum(geldigheid), GEMEENTE_CODE, LAND_CODE), null, new Lo3Historie(
            onjuist != null ? onjuist.asElement() : null,
            new Lo3Datum(geldigheid),
            new Lo3Datum(opneming)), volgendeHerkomst()));
    }

    private void converteer() throws OngeldigePersoonslijstException {

        final Lo3Persoonslijst lo3Pl = preconditiesService.verwerk(maakLo3Pl());

        brpPl = converteerLo3NaarBrpService.converteerLo3Persoonslijst(lo3Pl);
    }

    private Lo3Herkomst volgendeHerkomst() {
        voorkomen += 1;
        return new Lo3Herkomst(voorkomen == 1 ? Lo3CategorieEnum.CATEGORIE_06 : Lo3CategorieEnum.CATEGORIE_56, 0, voorkomen);
    }

    private Lo3Persoonslijst maakLo3Pl() {
        return new Lo3PersoonslijstBuilder().persoonStapel(
                                                lo3Stapel(lo3Cat(
                                                    lo3Persoon(
                                                        1111111111L,
                                                        "voornaam",
                                                        "geslNaam",
                                                        19620101,
                                                        GEMEENTE_CODE.getWaarde(),
                                                        LAND_CODE.getWaarde(),
                                                        "M"),
                                                    Lo3CategorieEnum.CATEGORIE_01)))
                                            .ouder1Stapel(
                                                lo3Stapel(lo3Cat(
                                                    lo3Ouder(
                                                        2222222222L,
                                                        "ouder1",
                                                        "geslNaam",
                                                        19320101,
                                                        GEMEENTE_CODE.getWaarde(),
                                                        LAND_CODE.getWaarde(),
                                                        "V",
                                                        19620101),
                                                    Lo3CategorieEnum.CATEGORIE_02)))
                                            .ouder2Stapel(
                                                lo3Stapel(lo3Cat(
                                                    lo3Ouder(
                                                        3333333333L,
                                                        "ouder2",
                                                        "geslNaam",
                                                        19320101,
                                                        GEMEENTE_CODE.getWaarde(),
                                                        LAND_CODE.getWaarde(),
                                                        "M",
                                                        19620101),
                                                    Lo3CategorieEnum.CATEGORIE_03)))
                                            .inschrijvingStapel(
                                                lo3Stapel(lo3Cat(
                                                    lo3Inschrijving(null, null, null, 19620101, null, 0, 2, 20130101000000000L, null),
                                                    Lo3CategorieEnum.CATEGORIE_07)))
                                            .verblijfplaatsStapel(
                                                lo3Stapel(lo3Cat(
                                                    lo3Verblijfplaats(GEMEENTE_CODE.getWaarde(), 19620101, 19620101, "straat", 1, "1111AA", "A"),
                                                    Lo3CategorieEnum.CATEGORIE_08)))
                                            .overlijdenStapel(new Lo3Stapel<>(lo3Categorieen))
                                            .build();
    }
}
