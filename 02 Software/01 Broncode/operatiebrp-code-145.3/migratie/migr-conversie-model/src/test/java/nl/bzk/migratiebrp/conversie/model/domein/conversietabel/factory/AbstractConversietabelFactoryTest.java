/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory;

import static org.junit.Assert.assertTrue;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingInhoudingOfVermissingReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortNederlandsReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie.BrpProtocolleringsniveauCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AangeverRedenWijzigingVerblijfPaar;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AdellijkeTitelPredikaatPaar;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.VoorvoegselScheidingstekenPaar;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AangifteAdreshouding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RNIDeelnemerCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import org.junit.Test;


public class AbstractConversietabelFactoryTest {

    TestClass test = new TestClass();

    @Test
    public void testCreateGeslachtsaanduidingConversietabel() throws Exception {
        final Object obj = test.createGeslachtsaanduidingConversietabel();
        assertTrue(obj instanceof Conversietabel);
    }

    @Test
    public void testCreateNaamgebruikConversietabel() throws Exception {
        final Object obj = test.createNaamgebruikConversietabel();
        assertTrue(obj instanceof Conversietabel);
    }

    @Test
    public void testCreateFunctieAdresConversietabel() throws Exception {
        final Object obj = test.createFunctieAdresConversietabel();
        assertTrue(obj instanceof Conversietabel);

    }

    @Test
    public void testCreateSoortRelatieConversietabel() throws Exception {
        final Object obj = test.createSoortRelatieConversietabel();
        assertTrue(obj instanceof Conversietabel);

    }

    @Test
    public void testCreateAanduidingUitgeslotenKiesrechtConversietabel() throws Exception {
        final Object obj = test.createAanduidingUitgeslotenKiesrechtConversietabel();
        assertTrue(obj instanceof Conversietabel);
    }

    @Test
    public void testCreateAanduidingEuropeesKiesrechtConversietabel() throws Exception {
        final Object obj = test.createAanduidingEuropeesKiesrechtConversietabel();
        assertTrue(obj instanceof Conversietabel);
    }

    @Test
    public void testCreateIndicatiePKConversietabel() throws Exception {
        final Object obj = test.createIndicatiePKConversietabel();
        assertTrue(obj instanceof Conversietabel);
    }

    @Test
    public void testCreateIndicatieGeheimConversietabel() throws Exception {
        final Object obj = test.createIndicatieGeheimConversietabel();
        assertTrue(obj instanceof Conversietabel);
    }

    @Test
    public void testCreateAanduidingHuisnummerConversietabel() throws Exception {
        final Object obj = test.createAanduidingHuisnummerConversietabel();
        assertTrue(obj instanceof Conversietabel);
    }

    @Test
    public void testCreateSignaleringConversietabel() throws Exception {
        final Object obj = test.createSignaleringConversietabel();
        assertTrue(obj instanceof Conversietabel);
    }

    @Test
    public void testCreateIndicatieDocumentConversietabel() throws Exception {
        final Object obj = test.createIndicatieDocumentConversietabel();
        assertTrue(obj instanceof Conversietabel);
    }

    @Test
    public void testCreateIndicatieCurateleConversietabel() throws Exception {
        final Object obj = test.createIndicatieCurateleConversietabel();
        assertTrue(obj instanceof Conversietabel);
    }

    private class TestClass extends AbstractConversietabelFactory {

        @Override
        public Conversietabel<Lo3AdellijkeTitelPredikaatCode, AdellijkeTitelPredikaatPaar> createAdellijkeTitelPredikaatConversietabel() {
            return null;
        }

        @Override
        public Conversietabel<Lo3GemeenteCode, BrpPartijCode> createPartijConversietabel() {
            return null;
        }

        @Override
        public Conversietabel<Lo3GemeenteCode, BrpGemeenteCode> createGemeenteConversietabel() {
            return null;
        }

        @Override
        public Conversietabel<Lo3LandCode, BrpLandOfGebiedCode> createLandConversietabel() {
            return null;
        }

        @Override
        public Conversietabel<Lo3RedenOntbindingHuwelijkOfGpCode, BrpRedenEindeRelatieCode> createRedenEindeRelatieConversietabel() {
            return null;
        }

        @Override
        public Conversietabel<Lo3AanduidingVerblijfstitelCode, BrpVerblijfsrechtCode> createVerblijfsrechtConversietabel() {
            return null;
        }

        @Override
        public Conversietabel<Lo3NationaliteitCode, BrpNationaliteitCode> createNationaliteitConversietabel() {
            return null;
        }

        @Override
        public Conversietabel<Lo3RedenNederlandschapCode, BrpRedenVerkrijgingNederlandschapCode> createRedenOpnameNationaliteitConversietabel() {
            return null;
        }

        @Override
        public Conversietabel<Lo3RedenNederlandschapCode, BrpRedenVerliesNederlandschapCode> createRedenBeeindigingNationaliteitConversietabel() {
            return null;
        }

        @Override
        public Conversietabel<Lo3AangifteAdreshouding, AangeverRedenWijzigingVerblijfPaar> createAangeverRedenWijzigingVerblijfConversietabel() {
            return null;
        }

        @Override
        public Conversietabel<Lo3SoortNederlandsReisdocument, BrpSoortNederlandsReisdocumentCode> createSoortReisdocumentConversietabel() {
            return null;
        }

        @Override
        public Conversietabel<Lo3AanduidingInhoudingVermissingNederlandsReisdocument, BrpAanduidingInhoudingOfVermissingReisdocumentCode>
        createAanduidingInhoudingVermissingReisdocumentConversietabel() {
            return null;
        }

        @Override
        public Conversietabel<Lo3RedenOpschortingBijhoudingCode, BrpNadereBijhoudingsaardCode> createRedenOpschortingBijhoudingConversietabel() {
            return null;
        }

        @Override
        public Conversietabel<Lo3RNIDeelnemerCode, BrpPartijCode> createRNIDeelnemerConversietabel() {
            return null;
        }

        @Override
        public Conversietabel<Lo3String, VoorvoegselScheidingstekenPaar> createVoorvoegselScheidingstekenConversietabel() {
            return null;
        }

        @Override
        public Conversietabel<String, String> createWoonplaatsnaamConversietabel() {
            return null;
        }

        @Override
        public Conversietabel<Character, BrpSoortDocumentCode> createSoortRegisterSoortDocumentConversietabel() {
            return null;
        }

        @Override
        public Conversietabel<Integer, BrpProtocolleringsniveauCode> createVerstrekkingsbeperkingConversietabel() {
            return null;
        }

        @Override
        public Conversietabel<String, String> createLo3RubriekConversietabel() {
            return null;
        }

        @Override
        public Conversietabel<Lo3NationaliteitCode, BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer>
        createAutoriteitVanAfgifteBuitenlandsPersoonsnummertabel() {
            return null;
        }
    }
}
