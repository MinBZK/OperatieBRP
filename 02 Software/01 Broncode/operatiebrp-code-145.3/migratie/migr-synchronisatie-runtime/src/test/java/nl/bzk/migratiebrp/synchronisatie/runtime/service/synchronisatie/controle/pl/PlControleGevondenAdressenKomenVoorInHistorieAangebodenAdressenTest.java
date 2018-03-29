/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl;

import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoud;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlControleGevondenAdressenKomenVoorInHistorieAangebodenAdressenTest {

    private static final String HUISNUMMERTOEVOEGING = "TO";
    private static final String BUITENLANDS_ADRES_REGEL6 = "Rgl6";
    private static final String BUITENLANDS_ADRES_REGEL5 = "Rgl5";
    private static final String BUITENLANDS_ADRES_REGEL4 = "Rgl4";
    private static final String BUITENLANDS_ADRES_REGEL3 = "Rgl3";
    private static final String BUITENLANDS_ADRES_REGEL2 = "Rgl2";
    private static final String BUITENLANDS_ADRES_REGEL1 = "Rgl1";
    private static final String GEMEENTE_CODE = "0516";
    private static final String WOONPLAATS = "Stad";
    private static final String POSTCODE = "1234RE";
    private static final char HUISLETTER = 'a';
    private final PlControleAangebodenAdressenKomenVoorInGevondenAdressen subject =
            new PlControleAangebodenAdressenKomenVoorInGevondenAdressen();

    @Before
    public void setupLogging() {
        SynchronisatieLogging.init();
    }

    @Test
    public void testAangebodenBevatMeer() {

        final BrpGroep<BrpAdresInhoud> adres1 = maakAdres(19901002, 19951213, 16, null, null, "2252EB", null, "0626", null, null, null, null, null, null);
        final BrpGroep<BrpAdresInhoud> adres2 =
                maakAdres(19951213, 19980622, 16, null, null, "2252EB", "Voorschoten", "0626", null, null, null, null, null, null);
        final BrpGroep<BrpAdresInhoud> adres3 = maakAdres(19980622, 20110316, 20, null, null, "2518EH", null, "0518", null, null, null, null, null, null);
        final BrpGroep<BrpAdresInhoud> adres4NietVervallen =
                maakAdres(20110316, null, 304, null, null, "2521CG", null, "0518", null, null, null, null, null, null);
        final BrpGroep<BrpAdresInhoud> adres4 = maakAdres(20110316, 20140101, 304, null, null, "2521CG", null, "0518", null, null, null, null, null, null);
        final BrpGroep<BrpAdresInhoud> adres5 =
                maakAdres(20140101, 20140101, 300, null, null, "3018HH", "Rotterdam", "0599", null, null, null, null, null, null);
        final BrpGroep<BrpAdresInhoud> adres6 =
                maakAdres(20140101, null, 300, null, null, "3018HH", "Rotterdam", "0599", null, null, null, null, null, null);

        final BrpPersoonslijst aangebodenPersoonslijst = maakPl(adres6, adres5, adres3, adres1, adres2, adres4);
        final BrpPersoonslijst dbPersoonslijstAlleenHistorie = maakPl(adres4NietVervallen, adres3, adres2, adres1);

        Assert.assertFalse(
                "Resultaat controle zou false moeten zijn",
                subject.controleer(new VerwerkingsContext(null, null, null, aangebodenPersoonslijst), dbPersoonslijstAlleenHistorie));
    }

    @Test
    public void testHistorieVergelijking() {

        // Mogelijkheden historie:
        // 1) actueel DB PL komt voor in de historie van de aangeboden PL.
        // 2) actueel DB PL komt niet voor in de historie van de aangeboden PL.
        // 3) Historie + actueel DB PL komt voor in de historie van de aangeboden PL.
        // 4) Historie + actueel DB PL komt niet voor in de historie van de aangeboden PL.

        // 1) actueel DB PL komt voor in de historie van de aangeboden PL.
        Assert.assertFalse(
                subject.controleer(
                        new VerwerkingsContext(
                                null,
                                null,
                                null,
                                maakPl(
                                        maakAdres(
                                                20100101,
                                                null,
                                                1,
                                                HUISLETTER,
                                                HUISNUMMERTOEVOEGING,
                                                POSTCODE,
                                                WOONPLAATS,
                                                GEMEENTE_CODE,
                                                BUITENLANDS_ADRES_REGEL1,
                                                BUITENLANDS_ADRES_REGEL2,
                                                BUITENLANDS_ADRES_REGEL3,
                                                BUITENLANDS_ADRES_REGEL4,
                                                BUITENLANDS_ADRES_REGEL5,
                                                BUITENLANDS_ADRES_REGEL6),
                                        maakAdres(
                                                20010101,
                                                20100101,
                                                1,
                                                HUISLETTER,
                                                HUISNUMMERTOEVOEGING,
                                                POSTCODE,
                                                WOONPLAATS,
                                                GEMEENTE_CODE,
                                                BUITENLANDS_ADRES_REGEL1,
                                                BUITENLANDS_ADRES_REGEL2,
                                                BUITENLANDS_ADRES_REGEL3,
                                                BUITENLANDS_ADRES_REGEL4,
                                                BUITENLANDS_ADRES_REGEL5,
                                                BUITENLANDS_ADRES_REGEL6))),
                        maakPl(maakAdres(
                                20010101,
                                null,
                                1,
                                HUISLETTER,
                                HUISNUMMERTOEVOEGING,
                                POSTCODE,
                                WOONPLAATS,
                                GEMEENTE_CODE,
                                BUITENLANDS_ADRES_REGEL1,
                                BUITENLANDS_ADRES_REGEL2,
                                BUITENLANDS_ADRES_REGEL3,
                                BUITENLANDS_ADRES_REGEL4,
                                BUITENLANDS_ADRES_REGEL5,
                                BUITENLANDS_ADRES_REGEL6))));

        // 2) actueel DB PL komt niet voor in de historie van de aangeboden PL.
        Assert.assertFalse(
                subject.controleer(
                        new VerwerkingsContext(
                                null,
                                null,
                                null,
                                maakPl(
                                        maakAdres(
                                                20010101,
                                                null,
                                                1,
                                                HUISLETTER,
                                                HUISNUMMERTOEVOEGING,
                                                POSTCODE,
                                                WOONPLAATS,
                                                GEMEENTE_CODE,
                                                BUITENLANDS_ADRES_REGEL1,
                                                BUITENLANDS_ADRES_REGEL2,
                                                BUITENLANDS_ADRES_REGEL3,
                                                BUITENLANDS_ADRES_REGEL4,
                                                BUITENLANDS_ADRES_REGEL5,
                                                BUITENLANDS_ADRES_REGEL6))),
                        maakPl(
                                maakAdres(
                                        20010101,
                                        null,
                                        1,
                                        HUISLETTER,
                                        HUISNUMMERTOEVOEGING,
                                        POSTCODE,
                                        WOONPLAATS,
                                        GEMEENTE_CODE,
                                        BUITENLANDS_ADRES_REGEL1,
                                        BUITENLANDS_ADRES_REGEL2,
                                        BUITENLANDS_ADRES_REGEL3,
                                        BUITENLANDS_ADRES_REGEL4,
                                        BUITENLANDS_ADRES_REGEL5,
                                        null))));

        // 3) Historie + actueel DB PL komt voor in de historie van de aangeboden PL.
        Assert.assertFalse(
                subject.controleer(
                        new VerwerkingsContext(
                                null,
                                null,
                                null,
                                maakPl(
                                        maakAdres(
                                                20140101,
                                                null,
                                                1,
                                                HUISLETTER,
                                                HUISNUMMERTOEVOEGING,
                                                POSTCODE,
                                                WOONPLAATS,
                                                GEMEENTE_CODE,
                                                BUITENLANDS_ADRES_REGEL1,
                                                BUITENLANDS_ADRES_REGEL2,
                                                BUITENLANDS_ADRES_REGEL3,
                                                BUITENLANDS_ADRES_REGEL4,
                                                BUITENLANDS_ADRES_REGEL5,
                                                BUITENLANDS_ADRES_REGEL6),
                                        maakAdres(
                                                20130101,
                                                20140101,
                                                1,
                                                HUISLETTER,
                                                HUISNUMMERTOEVOEGING,
                                                POSTCODE,
                                                WOONPLAATS,
                                                GEMEENTE_CODE,
                                                BUITENLANDS_ADRES_REGEL1,
                                                BUITENLANDS_ADRES_REGEL2,
                                                BUITENLANDS_ADRES_REGEL3,
                                                BUITENLANDS_ADRES_REGEL4,
                                                BUITENLANDS_ADRES_REGEL5,
                                                BUITENLANDS_ADRES_REGEL6),
                                        maakAdres(
                                                20100101,
                                                20130101,
                                                1,
                                                HUISLETTER,
                                                HUISNUMMERTOEVOEGING,
                                                POSTCODE,
                                                WOONPLAATS,
                                                GEMEENTE_CODE,
                                                BUITENLANDS_ADRES_REGEL1,
                                                BUITENLANDS_ADRES_REGEL2,
                                                BUITENLANDS_ADRES_REGEL3,
                                                BUITENLANDS_ADRES_REGEL4,
                                                BUITENLANDS_ADRES_REGEL5,
                                                BUITENLANDS_ADRES_REGEL6),
                                        maakAdres(
                                                20010101,
                                                20100101,
                                                1,
                                                HUISLETTER,
                                                HUISNUMMERTOEVOEGING,
                                                POSTCODE,
                                                WOONPLAATS,
                                                GEMEENTE_CODE,
                                                BUITENLANDS_ADRES_REGEL1,
                                                BUITENLANDS_ADRES_REGEL2,
                                                BUITENLANDS_ADRES_REGEL3,
                                                BUITENLANDS_ADRES_REGEL4,
                                                BUITENLANDS_ADRES_REGEL5,
                                                BUITENLANDS_ADRES_REGEL6))),
                        maakPl(
                                maakAdres(
                                        20130101,
                                        null,
                                        1,
                                        HUISLETTER,
                                        HUISNUMMERTOEVOEGING,
                                        POSTCODE,
                                        WOONPLAATS,
                                        GEMEENTE_CODE,
                                        BUITENLANDS_ADRES_REGEL1,
                                        BUITENLANDS_ADRES_REGEL2,
                                        BUITENLANDS_ADRES_REGEL3,
                                        BUITENLANDS_ADRES_REGEL4,
                                        BUITENLANDS_ADRES_REGEL5,
                                        BUITENLANDS_ADRES_REGEL6),
                                maakAdres(
                                        20100101,
                                        20130101,
                                        1,
                                        HUISLETTER,
                                        HUISNUMMERTOEVOEGING,
                                        POSTCODE,
                                        WOONPLAATS,
                                        GEMEENTE_CODE,
                                        BUITENLANDS_ADRES_REGEL1,
                                        BUITENLANDS_ADRES_REGEL2,
                                        BUITENLANDS_ADRES_REGEL3,
                                        BUITENLANDS_ADRES_REGEL4,
                                        BUITENLANDS_ADRES_REGEL5,
                                        BUITENLANDS_ADRES_REGEL6),
                                maakAdres(
                                        20010101,
                                        20100101,
                                        1,
                                        HUISLETTER,
                                        HUISNUMMERTOEVOEGING,
                                        POSTCODE,
                                        WOONPLAATS,
                                        GEMEENTE_CODE,
                                        BUITENLANDS_ADRES_REGEL1,
                                        BUITENLANDS_ADRES_REGEL2,
                                        BUITENLANDS_ADRES_REGEL3,
                                        BUITENLANDS_ADRES_REGEL4,
                                        BUITENLANDS_ADRES_REGEL5,
                                        BUITENLANDS_ADRES_REGEL6))));

        // 4) Historie + actueel DB PL komt voor in de historie van de aangeboden PL.
        Assert.assertTrue(
                subject.controleer(
                        new VerwerkingsContext(
                                null,
                                null,
                                null,
                                maakPl(
                                        maakAdres(
                                                20140101,
                                                null,
                                                1,
                                                HUISLETTER,
                                                HUISNUMMERTOEVOEGING,
                                                POSTCODE,
                                                WOONPLAATS,
                                                GEMEENTE_CODE,
                                                BUITENLANDS_ADRES_REGEL1,
                                                BUITENLANDS_ADRES_REGEL2,
                                                BUITENLANDS_ADRES_REGEL3,
                                                BUITENLANDS_ADRES_REGEL4,
                                                BUITENLANDS_ADRES_REGEL5,
                                                BUITENLANDS_ADRES_REGEL6),
                                        maakAdres(
                                                20130101,
                                                20140101,
                                                1,
                                                HUISLETTER,
                                                HUISNUMMERTOEVOEGING,
                                                POSTCODE,
                                                WOONPLAATS,
                                                GEMEENTE_CODE,
                                                BUITENLANDS_ADRES_REGEL1,
                                                BUITENLANDS_ADRES_REGEL2,
                                                BUITENLANDS_ADRES_REGEL3,
                                                BUITENLANDS_ADRES_REGEL4,
                                                BUITENLANDS_ADRES_REGEL5,
                                                BUITENLANDS_ADRES_REGEL6),
                                        maakAdres(
                                                20010101,
                                                20130101,
                                                1,
                                                HUISLETTER,
                                                HUISNUMMERTOEVOEGING,
                                                POSTCODE,
                                                WOONPLAATS,
                                                GEMEENTE_CODE,
                                                BUITENLANDS_ADRES_REGEL1,
                                                BUITENLANDS_ADRES_REGEL2,
                                                BUITENLANDS_ADRES_REGEL3,
                                                BUITENLANDS_ADRES_REGEL4,
                                                BUITENLANDS_ADRES_REGEL5,
                                                BUITENLANDS_ADRES_REGEL6))),
                        maakPl(
                                maakAdres(
                                        20130101,
                                        null,
                                        1,
                                        HUISLETTER,
                                        HUISNUMMERTOEVOEGING,
                                        POSTCODE,
                                        WOONPLAATS,
                                        GEMEENTE_CODE,
                                        BUITENLANDS_ADRES_REGEL1,
                                        BUITENLANDS_ADRES_REGEL2,
                                        BUITENLANDS_ADRES_REGEL3,
                                        BUITENLANDS_ADRES_REGEL4,
                                        BUITENLANDS_ADRES_REGEL5,
                                        BUITENLANDS_ADRES_REGEL6),
                                maakAdres(
                                        20100101,
                                        20130101,
                                        1,
                                        HUISLETTER,
                                        HUISNUMMERTOEVOEGING,
                                        POSTCODE,
                                        WOONPLAATS,
                                        GEMEENTE_CODE,
                                        BUITENLANDS_ADRES_REGEL1,
                                        BUITENLANDS_ADRES_REGEL2,
                                        BUITENLANDS_ADRES_REGEL3,
                                        BUITENLANDS_ADRES_REGEL4,
                                        BUITENLANDS_ADRES_REGEL5,
                                        BUITENLANDS_ADRES_REGEL6),
                                maakAdres(
                                        20010101,
                                        20100101,
                                        1,
                                        HUISLETTER,
                                        HUISNUMMERTOEVOEGING,
                                        POSTCODE,
                                        WOONPLAATS,
                                        GEMEENTE_CODE,
                                        BUITENLANDS_ADRES_REGEL1,
                                        BUITENLANDS_ADRES_REGEL2,
                                        BUITENLANDS_ADRES_REGEL3,
                                        BUITENLANDS_ADRES_REGEL4,
                                        BUITENLANDS_ADRES_REGEL5,
                                        BUITENLANDS_ADRES_REGEL6))));

    }

    @Test
    public void testInhoudelijkeVergelijking() {

        // Mogelijkheden vergelijking:
        // 1) Actueel adres DB PL inhoudelijk gelijk aan historie adres aangeboden PL.
        // 2) Actueel adres DB PL inhoudelijk ongelijk aan historie adres aangeboden PL.
        // 3) Historisch + actueel adressen DB PL inhoudelijk gelijk aan historie aangeboden PL.
        // 4) Historisch + actueel adressen DB PL inhoudelijk ongelijk aan historie aangeboden PL.
        // 5) Historisch + actueel adressen DB PL uitgebreider dan historie + actueel aangeboden PL.

        // 1) Actueel adres DB PL inhoudelijk gelijk aan historie adres aangeboden PL.
        Assert.assertFalse(
                subject.controleer(
                        new VerwerkingsContext(
                                null,
                                null,
                                null,
                                maakPl(
                                        maakAdres(
                                                20100101,
                                                null,
                                                1,
                                                HUISLETTER,
                                                HUISNUMMERTOEVOEGING,
                                                POSTCODE,
                                                WOONPLAATS,
                                                GEMEENTE_CODE,
                                                BUITENLANDS_ADRES_REGEL1,
                                                BUITENLANDS_ADRES_REGEL2,
                                                BUITENLANDS_ADRES_REGEL3,
                                                BUITENLANDS_ADRES_REGEL4,
                                                BUITENLANDS_ADRES_REGEL5,
                                                BUITENLANDS_ADRES_REGEL6),
                                        maakAdres(
                                                20010101,
                                                20100101,
                                                1,
                                                HUISLETTER,
                                                HUISNUMMERTOEVOEGING,
                                                POSTCODE,
                                                WOONPLAATS,
                                                GEMEENTE_CODE,
                                                BUITENLANDS_ADRES_REGEL1,
                                                BUITENLANDS_ADRES_REGEL2,
                                                BUITENLANDS_ADRES_REGEL3,
                                                BUITENLANDS_ADRES_REGEL4,
                                                BUITENLANDS_ADRES_REGEL5,
                                                BUITENLANDS_ADRES_REGEL6))),
                        maakPl(maakAdres(
                                20010101,
                                null,
                                1,
                                HUISLETTER,
                                HUISNUMMERTOEVOEGING,
                                POSTCODE,
                                WOONPLAATS,
                                GEMEENTE_CODE,
                                BUITENLANDS_ADRES_REGEL1,
                                BUITENLANDS_ADRES_REGEL2,
                                BUITENLANDS_ADRES_REGEL3,
                                BUITENLANDS_ADRES_REGEL4,
                                BUITENLANDS_ADRES_REGEL5,
                                BUITENLANDS_ADRES_REGEL6))));

        // 2) Actueel adres DB PL inhoudelijk ongelijk aan historie adres aangeboden PL.
        Assert.assertFalse(
                subject.controleer(
                        new VerwerkingsContext(
                                null,
                                null,
                                null,
                                maakPl(
                                        maakAdres(
                                                20100101,
                                                null,
                                                1,
                                                HUISLETTER,
                                                HUISNUMMERTOEVOEGING,
                                                POSTCODE,
                                                WOONPLAATS,
                                                GEMEENTE_CODE,
                                                BUITENLANDS_ADRES_REGEL1,
                                                BUITENLANDS_ADRES_REGEL2,
                                                BUITENLANDS_ADRES_REGEL3,
                                                BUITENLANDS_ADRES_REGEL4,
                                                BUITENLANDS_ADRES_REGEL5,
                                                BUITENLANDS_ADRES_REGEL6),
                                        maakAdres(
                                                20010101,
                                                20100101,
                                                1,
                                                HUISLETTER,
                                                HUISNUMMERTOEVOEGING,
                                                POSTCODE,
                                                WOONPLAATS,
                                                GEMEENTE_CODE,
                                                BUITENLANDS_ADRES_REGEL1,
                                                BUITENLANDS_ADRES_REGEL2,
                                                BUITENLANDS_ADRES_REGEL3,
                                                BUITENLANDS_ADRES_REGEL4,
                                                BUITENLANDS_ADRES_REGEL5,
                                                null))),
                        maakPl(maakAdres(
                                20010101,
                                null,
                                1,
                                HUISLETTER,
                                HUISNUMMERTOEVOEGING,
                                POSTCODE,
                                WOONPLAATS,
                                GEMEENTE_CODE,
                                BUITENLANDS_ADRES_REGEL1,
                                BUITENLANDS_ADRES_REGEL2,
                                BUITENLANDS_ADRES_REGEL3,
                                BUITENLANDS_ADRES_REGEL4,
                                BUITENLANDS_ADRES_REGEL5,
                                BUITENLANDS_ADRES_REGEL6))));

        // 3) Historisch + actueel adressen DB PL inhoudelijk gelijk aan historie + actueel aangeboden PL.
        Assert.assertFalse(
                subject.controleer(
                        new VerwerkingsContext(
                                null,
                                null,
                                null,
                                maakPl(
                                        maakAdres(
                                                20140101,
                                                null,
                                                1,
                                                HUISLETTER,
                                                HUISNUMMERTOEVOEGING,
                                                POSTCODE,
                                                WOONPLAATS,
                                                GEMEENTE_CODE,
                                                BUITENLANDS_ADRES_REGEL1,
                                                BUITENLANDS_ADRES_REGEL2,
                                                BUITENLANDS_ADRES_REGEL3,
                                                BUITENLANDS_ADRES_REGEL4,
                                                BUITENLANDS_ADRES_REGEL5,
                                                BUITENLANDS_ADRES_REGEL6),
                                        maakAdres(
                                                20100101,
                                                20140101,
                                                1,
                                                HUISLETTER,
                                                HUISNUMMERTOEVOEGING,
                                                POSTCODE,
                                                WOONPLAATS,
                                                GEMEENTE_CODE,
                                                BUITENLANDS_ADRES_REGEL1,
                                                BUITENLANDS_ADRES_REGEL2,
                                                BUITENLANDS_ADRES_REGEL3,
                                                BUITENLANDS_ADRES_REGEL4,
                                                BUITENLANDS_ADRES_REGEL5,
                                                BUITENLANDS_ADRES_REGEL6),
                                        maakAdres(
                                                20010101,
                                                20100101,
                                                1,
                                                HUISLETTER,
                                                HUISNUMMERTOEVOEGING,
                                                POSTCODE,
                                                WOONPLAATS,
                                                GEMEENTE_CODE,
                                                BUITENLANDS_ADRES_REGEL1,
                                                BUITENLANDS_ADRES_REGEL2,
                                                BUITENLANDS_ADRES_REGEL3,
                                                BUITENLANDS_ADRES_REGEL4,
                                                BUITENLANDS_ADRES_REGEL5,
                                                BUITENLANDS_ADRES_REGEL6))),
                        maakPl(
                                maakAdres(
                                        20010101,
                                        null,
                                        1,
                                        HUISLETTER,
                                        HUISNUMMERTOEVOEGING,
                                        POSTCODE,
                                        WOONPLAATS,
                                        GEMEENTE_CODE,
                                        BUITENLANDS_ADRES_REGEL1,
                                        BUITENLANDS_ADRES_REGEL2,
                                        BUITENLANDS_ADRES_REGEL3,
                                        BUITENLANDS_ADRES_REGEL4,
                                        BUITENLANDS_ADRES_REGEL5,
                                        BUITENLANDS_ADRES_REGEL6),
                                maakAdres(
                                        20010101,
                                        20100101,
                                        1,
                                        HUISLETTER,
                                        HUISNUMMERTOEVOEGING,
                                        POSTCODE,
                                        WOONPLAATS,
                                        GEMEENTE_CODE,
                                        BUITENLANDS_ADRES_REGEL1,
                                        BUITENLANDS_ADRES_REGEL2,
                                        BUITENLANDS_ADRES_REGEL3,
                                        BUITENLANDS_ADRES_REGEL4,
                                        BUITENLANDS_ADRES_REGEL5,
                                        BUITENLANDS_ADRES_REGEL6))));

        // 4) Historisch + actueel adressen DB PL inhoudelijk ongelijk aan historie + actueel aangeboden PL.
        Assert.assertFalse(
                subject.controleer(
                        new VerwerkingsContext(
                                null,
                                null,
                                null,
                                maakPl(
                                        maakAdres(
                                                20140101,
                                                null,
                                                1,
                                                HUISLETTER,
                                                HUISNUMMERTOEVOEGING,
                                                POSTCODE,
                                                WOONPLAATS,
                                                GEMEENTE_CODE,
                                                BUITENLANDS_ADRES_REGEL1,
                                                BUITENLANDS_ADRES_REGEL2,
                                                BUITENLANDS_ADRES_REGEL3,
                                                BUITENLANDS_ADRES_REGEL4,
                                                BUITENLANDS_ADRES_REGEL5,
                                                BUITENLANDS_ADRES_REGEL6),
                                        maakAdres(
                                                20100101,
                                                20140101,
                                                1,
                                                HUISLETTER,
                                                HUISNUMMERTOEVOEGING,
                                                POSTCODE,
                                                WOONPLAATS,
                                                GEMEENTE_CODE,
                                                BUITENLANDS_ADRES_REGEL1,
                                                BUITENLANDS_ADRES_REGEL2,
                                                BUITENLANDS_ADRES_REGEL3,
                                                BUITENLANDS_ADRES_REGEL4,
                                                BUITENLANDS_ADRES_REGEL5,
                                                BUITENLANDS_ADRES_REGEL6),
                                        maakAdres(
                                                20010101,
                                                20100101,
                                                1,
                                                HUISLETTER,
                                                HUISNUMMERTOEVOEGING,
                                                POSTCODE,
                                                WOONPLAATS,
                                                GEMEENTE_CODE,
                                                BUITENLANDS_ADRES_REGEL1,
                                                BUITENLANDS_ADRES_REGEL2,
                                                BUITENLANDS_ADRES_REGEL3,
                                                BUITENLANDS_ADRES_REGEL4,
                                                BUITENLANDS_ADRES_REGEL5,
                                                null))),
                        maakPl(
                                maakAdres(
                                        20010101,
                                        null,
                                        1,
                                        HUISLETTER,
                                        HUISNUMMERTOEVOEGING,
                                        POSTCODE,
                                        WOONPLAATS,
                                        GEMEENTE_CODE,
                                        BUITENLANDS_ADRES_REGEL1,
                                        BUITENLANDS_ADRES_REGEL2,
                                        BUITENLANDS_ADRES_REGEL3,
                                        BUITENLANDS_ADRES_REGEL4,
                                        BUITENLANDS_ADRES_REGEL5,
                                        BUITENLANDS_ADRES_REGEL6),
                                maakAdres(
                                        20010101,
                                        20100101,
                                        1,
                                        HUISLETTER,
                                        HUISNUMMERTOEVOEGING,
                                        POSTCODE,
                                        WOONPLAATS,
                                        GEMEENTE_CODE,
                                        BUITENLANDS_ADRES_REGEL1,
                                        BUITENLANDS_ADRES_REGEL2,
                                        BUITENLANDS_ADRES_REGEL3,
                                        BUITENLANDS_ADRES_REGEL4,
                                        BUITENLANDS_ADRES_REGEL5,
                                        BUITENLANDS_ADRES_REGEL6))));

        // 5) Historisch + actueel adressen DB PL uitgebreider dan historie + actueel aangeboden PL.
        Assert.assertTrue(
                subject.controleer(
                        new VerwerkingsContext(
                                null,
                                null,
                                null,
                                maakPl(
                                        maakAdres(
                                                20010101,
                                                null,
                                                1,
                                                HUISLETTER,
                                                HUISNUMMERTOEVOEGING,
                                                POSTCODE,
                                                WOONPLAATS,
                                                GEMEENTE_CODE,
                                                BUITENLANDS_ADRES_REGEL1,
                                                BUITENLANDS_ADRES_REGEL2,
                                                BUITENLANDS_ADRES_REGEL3,
                                                BUITENLANDS_ADRES_REGEL4,
                                                BUITENLANDS_ADRES_REGEL5,
                                                BUITENLANDS_ADRES_REGEL6),
                                        maakAdres(
                                                20010101,
                                                20100101,
                                                1,
                                                HUISLETTER,
                                                HUISNUMMERTOEVOEGING,
                                                POSTCODE,
                                                WOONPLAATS,
                                                GEMEENTE_CODE,
                                                BUITENLANDS_ADRES_REGEL1,
                                                BUITENLANDS_ADRES_REGEL2,
                                                BUITENLANDS_ADRES_REGEL3,
                                                BUITENLANDS_ADRES_REGEL4,
                                                BUITENLANDS_ADRES_REGEL5,
                                                BUITENLANDS_ADRES_REGEL6))), maakPl(
                                maakAdres(
                                        20140101,
                                        null,
                                        1,
                                        HUISLETTER,
                                        HUISNUMMERTOEVOEGING,
                                        POSTCODE,
                                        WOONPLAATS,
                                        GEMEENTE_CODE,
                                        BUITENLANDS_ADRES_REGEL1,
                                        BUITENLANDS_ADRES_REGEL2,
                                        BUITENLANDS_ADRES_REGEL3,
                                        BUITENLANDS_ADRES_REGEL4,
                                        BUITENLANDS_ADRES_REGEL5,
                                        BUITENLANDS_ADRES_REGEL6),
                                maakAdres(
                                        20100101,
                                        20140101,
                                        1,
                                        HUISLETTER,
                                        HUISNUMMERTOEVOEGING,
                                        POSTCODE,
                                        WOONPLAATS,
                                        GEMEENTE_CODE,
                                        BUITENLANDS_ADRES_REGEL1,
                                        BUITENLANDS_ADRES_REGEL2,
                                        BUITENLANDS_ADRES_REGEL3,
                                        BUITENLANDS_ADRES_REGEL4,
                                        BUITENLANDS_ADRES_REGEL5,
                                        BUITENLANDS_ADRES_REGEL6),
                                maakAdres(
                                        20010101,
                                        20100101,
                                        1,
                                        HUISLETTER,
                                        HUISNUMMERTOEVOEGING,
                                        POSTCODE,
                                        WOONPLAATS,
                                        GEMEENTE_CODE,
                                        BUITENLANDS_ADRES_REGEL1,
                                        BUITENLANDS_ADRES_REGEL2,
                                        BUITENLANDS_ADRES_REGEL3,
                                        BUITENLANDS_ADRES_REGEL4,
                                        BUITENLANDS_ADRES_REGEL5,
                                        BUITENLANDS_ADRES_REGEL6))));


    }

    @Test
    public void testInhoudelijkeVergelijkingNulls() {

        // Mogelijkheden vergelijking:
        // 1) Actueel adres DB PL inhoudelijk gelijk aan historie adres aangeboden PL.
        // 2) Historisch + actueel adressen DB PL inhoudelijk gelijk aan historie aangeboden PL.

        // 1) Actueel adres DB PL inhoudelijk gelijk aan historie adres aangeboden PL.
        Assert.assertFalse(
                subject.controleer(
                        new VerwerkingsContext(
                                null,
                                null,
                                null,
                                maakPl(
                                        maakAdres(20100101, null, null, null, null, null, null, null, null, null, null, null, null, null),
                                        maakAdres(20010101, 20100101, null, null, null, null, null, null, null, null, null, null, null, null))),
                        maakPl(maakAdres(20010101, null, null, null, null, null, null, null, null, null, null, null, null, null))));

        // 2) Historisch + actueel adres DB PL inhoudelijk gelijk aan historie adres aangeboden PL.
        Assert.assertFalse(
                subject.controleer(
                        new VerwerkingsContext(
                                null,
                                null,
                                null,
                                maakPl(
                                        maakAdres(20014101, null, null, null, null, null, null, null, null, null, null, null, null, null),
                                        maakAdres(20100101, 20140101, null, null, null, null, null, null, null, null, null, null, null, null),
                                        maakAdres(20010101, 20100101, null, null, null, null, null, null, null, null, null, null, null, null))),
                        maakPl(
                                maakAdres(20100101, null, null, null, null, null, null, null, null, null, null, null, null, null),
                                maakAdres(20010101, 20100101, null, null, null, null, null, null, null, null, null, null, null, null))));

    }

    @Test
    public void testGeenAdresStapels() {

        final BrpGroep<BrpAdresInhoud> nullAdresStapel = null;

        // 1) Controleer geen stapel in DB PL tegen stapel op aangeboden PL.
        Assert.assertFalse(
                subject.controleer(
                        new VerwerkingsContext(
                                null,
                                null,
                                null,
                                maakPl(
                                        maakAdres(
                                                20100101,
                                                null,
                                                1,
                                                HUISLETTER,
                                                HUISNUMMERTOEVOEGING,
                                                POSTCODE,
                                                WOONPLAATS,
                                                GEMEENTE_CODE,
                                                BUITENLANDS_ADRES_REGEL1,
                                                BUITENLANDS_ADRES_REGEL2,
                                                BUITENLANDS_ADRES_REGEL3,
                                                BUITENLANDS_ADRES_REGEL4,
                                                BUITENLANDS_ADRES_REGEL5,
                                                BUITENLANDS_ADRES_REGEL6))),
                        maakPl(nullAdresStapel)));

        // 2) Controleer stapel in DB PL tegen geen stapel op aangeboden PL.
        Assert.assertTrue(
                subject.controleer(
                        new VerwerkingsContext(null, null, null, maakPl(nullAdresStapel)),
                        maakPl(
                                maakAdres(
                                        20100101,
                                        null,
                                        1,
                                        HUISLETTER,
                                        HUISNUMMERTOEVOEGING,
                                        POSTCODE,
                                        WOONPLAATS,
                                        GEMEENTE_CODE,
                                        BUITENLANDS_ADRES_REGEL1,
                                        BUITENLANDS_ADRES_REGEL2,
                                        BUITENLANDS_ADRES_REGEL3,
                                        BUITENLANDS_ADRES_REGEL4,
                                        BUITENLANDS_ADRES_REGEL5,
                                        BUITENLANDS_ADRES_REGEL6))));

    }

    BrpGroep<BrpAdresInhoud> maakAdres(
            final Integer datumIngang,
            final Integer datumEinde,
            final Integer huisnummer,
            final Character huisletter,
            final String huisnummertoevoeging,
            final String postcode,
            final String woonplaatsnaam,
            final String gemeenteCode,
            final String buitenlandsAdresRegel1,
            final String buitenlandsAdresRegel2,
            final String buitenlandsAdresRegel3,
            final String buitenlandsAdresRegel4,
            final String buitenlandsAdresRegel5,
            final String buitenlandsAdresRegel6) {
        final BrpAdresInhoud inhoud =
                BrpStapelHelper.adres(
                        "W",
                        'A',
                        'I',
                        datumIngang,
                        gemeenteCode,
                        null,
                        null,
                        null,
                        null,
                        null,
                        huisnummer,
                        huisletter,
                        huisnummertoevoeging,
                        postcode,
                        woonplaatsnaam,
                        null,
                        null,
                        buitenlandsAdresRegel1,
                        buitenlandsAdresRegel2,
                        buitenlandsAdresRegel3,
                        buitenlandsAdresRegel4,
                        buitenlandsAdresRegel5,
                        buitenlandsAdresRegel6,
                        null,
                        null);

        return BrpStapelHelper.groep(
                inhoud,
                BrpStapelHelper.his(datumIngang, datumEinde, datumIngang, datumEinde),
                BrpStapelHelper.act(1, datumIngang),
                datumEinde == null ? null : BrpStapelHelper.act(2, datumEinde),
                datumEinde == null ? null : BrpStapelHelper.act(2, datumEinde));
    }

    BrpPersoonslijst maakPl(final BrpGroep<BrpAdresInhoud>... adressen) {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        if (adressen[0] != null) {
            builder.adresStapel(BrpStapelHelper.stapel(adressen));
        }

        return builder.build();
    }

}
