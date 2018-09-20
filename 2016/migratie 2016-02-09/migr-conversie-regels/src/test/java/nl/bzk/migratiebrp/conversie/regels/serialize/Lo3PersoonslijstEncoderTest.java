/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.serialize;

import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Builder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.serialize.PersoonslijstDecoder;
import nl.bzk.migratiebrp.conversie.model.serialize.PersoonslijstEncoder;
import nl.bzk.migratiebrp.conversie.model.testutils.VerplichteStapel;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijst;
import nl.bzk.migratiebrp.conversie.regels.AbstractComponentTest;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.Lo3HistorieConversie;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.Lo3InhoudNaarBrpConversieStap;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.util.common.EncodingConstants;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Deze class test het contract van de Lo3Persoonslijst Encoder.
 * 
 */
public class Lo3PersoonslijstEncoderTest extends AbstractComponentTest {

    private static final String GESLACHTSAANDUIDING_MAN = "M";
    private static final String GESLACHTSAANDUIDING_VROUW = "V";
    private static final int BSN_NUMMER = 987654321;
    private static final long A_NUMMER = 1234567890L;
    private static final int DATE2 = 20110102;
    private static final int DATE = 20110101;
    private static final String INDICATIE_ONJUIST = "O";
    private static final String AANDUIDING_NAAMGEBRUIK = "E";
    private static final String NATIONALITEIT_CODE = "0055";
    private static final String VOORNAMEN = "Piet Jan";
    private static final String ADELIJKE_TITEL_PREDIKAAT = "PS";
    private static final String VOORVOEGSEL = "van";
    private static final String GESLACHTSNAAM = "HorenZeggen";
    private static final int GEBOORTEDATUM = 19800101;
    private static final String GEMEENTE_CODE = "1234";
    private static final String LAND_CODE = "5678";

    @Inject
    private Lo3InhoudNaarBrpConversieStap lo3InhoudNaarBrpConversieStap;
    @Inject
    private Lo3HistorieConversie lo3HistorieConversie;

    @Before
    public void setUp() {
        Logging.initContext();
    }

    @After
    public void tearDown() {
        Logging.destroyContext();
    }

    @Test
    public void testSerializeEnDeserialize() throws UnsupportedEncodingException {
        final Lo3Persoonslijst persoonslijst = maakEenvoudigeLo3Persoonslijst();
        final ByteArrayOutputStream encodeBuffer = new ByteArrayOutputStream();
        // Encoderen LO3 persoonslijst
        PersoonslijstEncoder.encodePersoonslijst(persoonslijst, encodeBuffer);
        String serializedPersoonslijst = encodeBuffer.toString(EncodingConstants.CHARSET_NAAM);
        // Decoderen LO3 persoonslijst
        final Lo3Persoonslijst decodedPersoonslijst = PersoonslijstDecoder.decodeLo3Persoonslijst(new ByteArrayInputStream(encodeBuffer.toByteArray()));
        assertNotNull(decodedPersoonslijst);

        encodeBuffer.reset();
        // Her-encoderen van gedecodeerde LO3 persoonslijst
        PersoonslijstEncoder.encodePersoonslijst(decodedPersoonslijst, encodeBuffer);
        String serializedPersoonslijst2 = encodeBuffer.toString(EncodingConstants.CHARSET_NAAM);
        Assert.assertEquals(serializedPersoonslijst, serializedPersoonslijst2);

        final TussenPersoonslijst tussenPersoonslijst = lo3InhoudNaarBrpConversieStap.converteer(persoonslijst);
        final BrpPersoonslijst brpPersoonslijst = lo3HistorieConversie.converteer(tussenPersoonslijst);
        encodeBuffer.reset();
        PersoonslijstEncoder.encodePersoonslijst(brpPersoonslijst, encodeBuffer);
        serializedPersoonslijst = encodeBuffer.toString(EncodingConstants.CHARSET_NAAM);
        final BrpPersoonslijst decodedBrpPersoonslijst = PersoonslijstDecoder.decodeBrpPersoonslijst(new ByteArrayInputStream(encodeBuffer.toByteArray()));
        assertNotNull(decodedBrpPersoonslijst);

        encodeBuffer.reset();
        PersoonslijstEncoder.encodePersoonslijst(decodedBrpPersoonslijst, encodeBuffer);
        serializedPersoonslijst2 = encodeBuffer.toString(EncodingConstants.CHARSET_NAAM);
        Assert.assertEquals(serializedPersoonslijst, serializedPersoonslijst2);
    }

    private Lo3Persoonslijst maakEenvoudigeLo3Persoonslijst() {
        final Lo3Categorie<Lo3NationaliteitInhoud> lo3Nationaliteit =
                Lo3Builder.createLo3Nationaliteit(NATIONALITEIT_CODE, null, null, null, null, DATE, DATE2);
        final Lo3Stapel<Lo3NationaliteitInhoud> lo3NationaliteitStapel = new Lo3Stapel<>(Arrays.asList(lo3Nationaliteit));
        final Lo3Categorie<Lo3PersoonInhoud> lo3Persoon1 =
                Lo3Builder.createLo3Persoon(
                    A_NUMMER,
                    BSN_NUMMER,
                    VOORNAMEN,
                    ADELIJKE_TITEL_PREDIKAAT,
                    VOORVOEGSEL,
                    GESLACHTSNAAM,
                    GEBOORTEDATUM,
                    GEMEENTE_CODE,
                    LAND_CODE,
                    GESLACHTSAANDUIDING_VROUW,
                    null,
                    null,
                    AANDUIDING_NAAMGEBRUIK,
                    INDICATIE_ONJUIST,
                    DATE,
                    DATE2,
                    1);
        final Lo3Categorie<Lo3PersoonInhoud> lo3Persoon2 =
                Lo3Builder.createLo3Persoon(
                    A_NUMMER,
                    BSN_NUMMER,
                    VOORNAMEN,
                    ADELIJKE_TITEL_PREDIKAAT,
                    VOORVOEGSEL,
                    GESLACHTSNAAM,
                    GEBOORTEDATUM,
                    GEMEENTE_CODE,
                    LAND_CODE,
                    GESLACHTSAANDUIDING_MAN,
                    null,
                    null,
                    AANDUIDING_NAAMGEBRUIK,
                    null,
                    DATE,
                    DATE2,
                    0);
        final Lo3Stapel<Lo3PersoonInhoud> lo3PersoonStapel = new Lo3Stapel<>(Arrays.asList(lo3Persoon1, lo3Persoon2));
        final Lo3Stapel<Lo3InschrijvingInhoud> lo3InschrijvingStapel =
                new Lo3Stapel<>(Arrays.asList(new Lo3Categorie<>(new Lo3InschrijvingInhoud(
                    null,
                    null,
                    null,
                    new Lo3Datum(GEBOORTEDATUM),
                    null,
                    null,
                    null,
                    null,
                    new Lo3Integer(1),
                    new Lo3Datumtijdstempel(20070401000000000L),
                    null), null, null, Lo3Historie.NULL_HISTORIE, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0))));
        final Lo3Stapel<Lo3OuderInhoud> ouder1 = VerplichteStapel.createOuder1Stapel();
        final Lo3Stapel<Lo3OuderInhoud> ouder2 = VerplichteStapel.createOuder1Stapel();
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaats = VerplichteStapel.createVerblijfplaatsStapel();
        return new Lo3PersoonslijstBuilder().persoonStapel(lo3PersoonStapel)
                                            .ouder1Stapel(ouder1)
                                            .ouder2Stapel(ouder2)
                                            .nationaliteitStapel(lo3NationaliteitStapel)
                                            .inschrijvingStapel(lo3InschrijvingStapel)
                                            .verblijfplaatsStapel(verblijfplaats)
                                            .build();
    }
}
