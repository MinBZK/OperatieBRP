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

public class PlControleAangebodenAdressenGelijkTest {

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
    private final PlControleAangebodenAdressenGelijk subject = new PlControleAangebodenAdressenGelijk();

    @Before
    public void setupLogging() {
        SynchronisatieLogging.init();
    }

    @Test
    public void testVergelijkingActueelGelijk() {

        // 1) actueel aangeboden PL komt voor in de historie van de DB PL.
        Assert.assertTrue(subject.controleer(
            new VerwerkingsContext(null, null, null, maakPl(maakAdres(
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
                maakPl(maakAdres(
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

    @Test
    public void testVergelijkingActueelKomtNietOvereen() {

        // 2) actueel aangeboden PL komt niet voor in de historie van de DB PL.
        Assert.assertFalse(subject.controleer(
            new VerwerkingsContext(null, null, null, maakPl(maakAdres(
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
                null))),
                maakPl(maakAdres(
                    20020101,
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

    @Test
    public void testVergelijkingActueelEnHistorie() {
        // 3) Historie + actueel aangeboden PL komt voor in de historie van de DB PL.
        Assert.assertTrue(subject.controleer(
            new VerwerkingsContext(null, null, null, maakPl(
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
                            BUITENLANDS_ADRES_REGEL6))),
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
                                                BUITENLANDS_ADRES_REGEL6))));
    }

    @Test
    public void testVergelijkingActueelEnHistorieOngelijk() {
        // 4) Historie + actueel aangeboden PL komt niet voor in de historie van de DB PL.
        Assert.assertFalse(subject.controleer(
            new VerwerkingsContext(null, null, null, maakPl(
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
                            BUITENLANDS_ADRES_REGEL6))),
                            maakPl(
                                maakAdres(
                                    20140101,
                                    null,
                                    3,
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
                                            BUITENLANDS_ADRES_REGEL6))));

    }

    @Test
    public void testInhoudelijkeVergelijkingNulls() {

        // Mogelijkheden vergelijking:
        // 1) Actueel adres aangeboden PL inhoudelijk gelijk aan historie adres DB PL.
        // 2) Historisch + actueel adressen aangeboden PL inhoudelijk gelijk aan historie aangeboden PL.

        // 1) Actueel adres aangeboden PL inhoudelijk gelijk aan historie adres DB PL.
        Assert.assertTrue(subject.controleer(
            new VerwerkingsContext(null, null, null, maakPl(maakAdres(
                20010101,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null))),
                maakPl(
                    maakAdres(20100101, null, null, null, null, null, null, null, null, null, null, null, null, null),
                    maakAdres(20010101, 20100101, null, null, null, null, null, null, null, null, null, null, null, null))));

        // 2) Historisch + actueel adres aangeboden PL inhoudelijk gelijk aan historie adres DB PL.
        Assert.assertTrue(subject.controleer(
            new VerwerkingsContext(null, null, null, maakPl(
                maakAdres(20100101, null, null, null, null, null, null, null, null, null, null, null, null, null),
                maakAdres(20010101, 20100101, null, null, null, null, null, null, null, null, null, null, null, null))),
                maakPl(
                    maakAdres(20014101, null, null, null, null, null, null, null, null, null, null, null, null, null),
                    maakAdres(20100101, 20140101, null, null, null, null, null, null, null, null, null, null, null, null),
                    maakAdres(20010101, 20100101, null, null, null, null, null, null, null, null, null, null, null, null))));

    }

    @Test
    public void testGeenAdresStapels() {

        final BrpGroep<BrpAdresInhoud> nullAdresStapel = null;

        // 1) Controleer geen stapel in aangeboden PL tegen stapel op DB PL.
        Assert.assertFalse(subject.controleer(
            new VerwerkingsContext(null, null, null, maakPl(nullAdresStapel)),
            maakPl(maakAdres(
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

        // 2) Controleer stapel in aangeboden PL tegen geen stapel op DB PL.
        Assert.assertFalse(subject.controleer(
            new VerwerkingsContext(null, null, null, maakPl(maakAdres(
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

    }

    BrpGroep<BrpAdresInhoud> maakAdres(
        final int datumIngang,
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
        final String buitenlandsAdresRegel6)
        {
        final BrpAdresInhoud inhoud =
                BrpStapelHelper.adres(
                    "W",
                    'A',
                    'I',
                    20000101,
                    null,
                    null,
                    gemeenteCode,
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
            BrpStapelHelper.his(datumIngang, datumEinde, datumIngang, null),
            BrpStapelHelper.act(1, datumIngang),
            null,
            datumEinde == null ? null : BrpStapelHelper.act(2, datumEinde));
        }

    @SafeVarargs
    private final BrpPersoonslijst maakPl(final BrpGroep<BrpAdresInhoud>... adressen) {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        if (adressen[0] != null) {
            builder.adresStapel(BrpStapelHelper.stapel(adressen));
        }

        return builder.build();
    }

}
