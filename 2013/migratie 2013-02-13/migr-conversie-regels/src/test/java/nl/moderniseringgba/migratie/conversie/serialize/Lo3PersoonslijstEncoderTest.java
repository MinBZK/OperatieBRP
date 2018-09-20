/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.serialize;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.AbstractComponentTest;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Builder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratiePersoonslijst;
import nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.Lo3HistorieConversie;
import nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.Lo3InhoudNaarBrpConversieStap;
import nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.Lo3ToevoegenExtraDocumentatie;
import nl.moderniseringgba.migratie.conversie.validatie.InputValidationException;
import nl.moderniseringgba.migratie.testutils.VerplichteStapel;

import org.junit.Test;

/**
 * Deze class test het contract van de Lo3Persoonslijst Encoder.
 * 
 */
public class Lo3PersoonslijstEncoderTest extends AbstractComponentTest {

    private static final String GESLACHTSAANDUIDING_MAN = "M";
    private static final String GESLACHTSAANDUIDING_VROUW = "V";
    private static final long BSN_NUMMER = 987654321L;
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
    private Lo3ToevoegenExtraDocumentatie lo3ToevoegenExtraDocumentatie;
    @Inject
    private Lo3HistorieConversie lo3HistorieConversie;

    @Test
    public void testSerializeEnDeserialize() throws InputValidationException {
        final Lo3Persoonslijst persoonslijst = maakEenvoudigeLo3Persoonslijst();
        final ByteArrayOutputStream encodeBuffer = new ByteArrayOutputStream();
        PersoonslijstEncoder.encodePersoonslijst(persoonslijst, encodeBuffer);
        String serializedPersoonslijst = encodeBuffer.toString();
        System.out.println(serializedPersoonslijst);
        final Lo3Persoonslijst decodedPersoonslijst =
                PersoonslijstDecoder.decodeLo3Persoonslijst(new ByteArrayInputStream(encodeBuffer.toByteArray()));
        assertNotNull(decodedPersoonslijst);

        encodeBuffer.reset();
        PersoonslijstEncoder.encodePersoonslijst(decodedPersoonslijst, encodeBuffer);
        String serializedPersoonslijst2 = encodeBuffer.toString();
        assertEquals(serializedPersoonslijst, serializedPersoonslijst2);

        final MigratiePersoonslijst migratiePersoonslijst =
                lo3InhoudNaarBrpConversieStap.converteer(lo3ToevoegenExtraDocumentatie.converteer(persoonslijst));
        encodeBuffer.reset();
        PersoonslijstEncoder.encodePersoonslijst(migratiePersoonslijst, encodeBuffer);
        serializedPersoonslijst = encodeBuffer.toString();
        System.out.println(serializedPersoonslijst);
        final MigratiePersoonslijst decodedMigratiePersoonslijst =
                PersoonslijstDecoder
                        .decodeMigratiePersoonslijst(new ByteArrayInputStream(encodeBuffer.toByteArray()));
        assertNotNull(decodedMigratiePersoonslijst);

        encodeBuffer.reset();
        PersoonslijstEncoder.encodePersoonslijst(decodedMigratiePersoonslijst, encodeBuffer);
        serializedPersoonslijst2 = encodeBuffer.toString();
        assertEquals(serializedPersoonslijst, serializedPersoonslijst2);
        final BrpPersoonslijst brpPersoonslijst = lo3HistorieConversie.converteer(migratiePersoonslijst);
        encodeBuffer.reset();
        PersoonslijstEncoder.encodePersoonslijst(brpPersoonslijst, encodeBuffer);
        serializedPersoonslijst = encodeBuffer.toString();
        System.out.println(serializedPersoonslijst);
        final BrpPersoonslijst decodedBrpPersoonslijst =
                PersoonslijstDecoder.decodeBrpPersoonslijst(new ByteArrayInputStream(encodeBuffer.toByteArray()));
        assertNotNull(decodedBrpPersoonslijst);

        encodeBuffer.reset();
        PersoonslijstEncoder.encodePersoonslijst(decodedBrpPersoonslijst, encodeBuffer);
        serializedPersoonslijst2 = encodeBuffer.toString();
        assertEquals(serializedPersoonslijst, serializedPersoonslijst2);
    }

    @SuppressWarnings("unchecked")
    private Lo3Persoonslijst maakEenvoudigeLo3Persoonslijst() throws InputValidationException {
        final Lo3Categorie<Lo3NationaliteitInhoud> lo3Nationaliteit =
                Lo3Builder.createLo3Nationaliteit(NATIONALITEIT_CODE, null, null, null, null, DATE, DATE2);
        final Lo3Stapel<Lo3NationaliteitInhoud> lo3NationaliteitStapel =
                new Lo3Stapel<Lo3NationaliteitInhoud>(Arrays.asList(lo3Nationaliteit));
        final Lo3Categorie<Lo3PersoonInhoud> lo3Persoon1 =
                Lo3Builder.createLo3Persoon(A_NUMMER, BSN_NUMMER, VOORNAMEN, ADELIJKE_TITEL_PREDIKAAT, VOORVOEGSEL,
                        GESLACHTSNAAM, GEBOORTEDATUM, GEMEENTE_CODE, LAND_CODE, GESLACHTSAANDUIDING_VROUW,
                        AANDUIDING_NAAMGEBRUIK, null, null, INDICATIE_ONJUIST, DATE, DATE2);
        final Lo3Categorie<Lo3PersoonInhoud> lo3Persoon2 =
                Lo3Builder.createLo3Persoon(A_NUMMER, BSN_NUMMER, VOORNAMEN, ADELIJKE_TITEL_PREDIKAAT, VOORVOEGSEL,
                        GESLACHTSNAAM, GEBOORTEDATUM, GEMEENTE_CODE, LAND_CODE, GESLACHTSAANDUIDING_MAN,
                        AANDUIDING_NAAMGEBRUIK, null, null, null, DATE, DATE2);
        final Lo3Stapel<Lo3PersoonInhoud> lo3PersoonStapel =
                new Lo3Stapel<Lo3PersoonInhoud>(Arrays.asList(lo3Persoon1, lo3Persoon2));
        final Lo3Stapel<Lo3InschrijvingInhoud> lo3InschrijvingStapel =
                new Lo3Stapel<Lo3InschrijvingInhoud>(Arrays.asList(new Lo3Categorie<Lo3InschrijvingInhoud>(
                        new Lo3InschrijvingInhoud(null, null, null, new Lo3Datum(GEBOORTEDATUM), null, null, 1,
                                new Lo3Datumtijdstempel(20070401000000000L), null), null, Lo3Historie.NULL_HISTORIE,
                        null)));
        final Lo3Stapel<Lo3OuderInhoud> ouder = VerplichteStapel.createOuderStapel();
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaats = VerplichteStapel.createVerblijfplaatsStapel();
        final Lo3Persoonslijst result =
                new Lo3PersoonslijstBuilder().persoonStapel(lo3PersoonStapel).ouder1Stapel(ouder).ouder2Stapel(ouder)
                        .nationaliteitStapel(lo3NationaliteitStapel).inschrijvingStapel(lo3InschrijvingStapel)
                        .verblijfplaatsStapel(verblijfplaats).build();
        return result;
    }
}
