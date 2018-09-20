/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActieBron;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.exceptions.PreconditieException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RNIDeelnemerCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Validatie;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.AbstractComponentTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;


public class BrpDocumentatieConverteerderTest extends AbstractComponentTest {

    private static final String PRE085_FOUTMELDING = "PRE085 zou gegooid moeten worden";
    private static final BrpPartijCode BRP_PARTIJ_MIGRATIE = BrpPartijCode.MIGRATIEVOORZIENING;
    // Partij/gemeente is Haren(Groningen)
    private static final BrpPartijCode BRP_PARTIJ_DOCUMENT = new BrpPartijCode(1701);
    // Partij/gemeente is Usquert
    private static final BrpPartijCode BRP_PARTIJ_DOCUMENT_2 = new BrpPartijCode(4601);
    private static final Lo3GemeenteCode LO3_GEMEENTE_CODE = new Lo3GemeenteCode("0017");
    private static final BrpSoortDocumentCode BRP_SOORT_DOCUMENT_AKTE = new BrpSoortDocumentCode("Geboorteakte");
    private static final BrpSoortDocumentCode BRP_SOORT_DOCUMENT_DOCUMENT = BrpSoortDocumentCode.HISTORIE_CONVERSIE;
    private static final BrpString AKTE_NUMMER = new BrpString("1 ABCD1", null);
    private static final BrpString AKTE_NUMMER_2 = new BrpString("1 ABCD2", null);
    private static final BrpString DOC_OMSCHRIJVING = new BrpString("Omschrijving document", null);
    private static final BrpString DOC_OMSCHRIJVING_2 = new BrpString("Omschrijving document 2", null);
    private static final int DATUM_ONTLENING = 20120101;
    private static final BrpDatumTijd DATUM_OPNEMING = BrpDatumTijd.fromDatumTijd(20120102120000L, null);
    private static final BrpString LEGE_BRPSTRING = new BrpString("", null);
    private static final String RECHTS_OMSCHRIJVING = "rechts omschrijving";
    private static final DecimalFormat GEMEENTE_CODE_FORMAT = new DecimalFormat("0000");


    @Inject
    private BrpDocumentatieConverteerder conv = new BrpDocumentatieConverteerder();

    @Before
    public void setup(){
        ConversietabelFactory conversietabelFactory = mock(ConversietabelFactory.class);
        final Conversietabel<Lo3RNIDeelnemerCode, BrpPartijCode> convTabel = mock(Conversietabel.class);
        when(convTabel.valideerBrp(argThat(new Rnideelnemer()))).thenReturn(Boolean.TRUE);
        when(convTabel.valideerBrp(argThat(new NotRnideelnemer()))).thenReturn(Boolean.FALSE);
        when(conversietabelFactory.createRNIDeelnemerConversietabel()).thenReturn(convTabel);
        ReflectionTestUtils.setField(conv,"conversietabelFactory",conversietabelFactory);

        BrpAttribuutConverteerder attribuutConverteerder = mock(BrpAttribuutConverteerder.class);
        when(attribuutConverteerder.converteerRNIDeelnemer(any(BrpPartijCode.class))).thenReturn(Lo3RNIDeelnemerCode.STANDAARD);
        when(attribuutConverteerder.converteerString(any(BrpString.class))).thenCallRealMethod();
        final ArgumentCaptor<BrpPartijCode> brpPartijCode = ArgumentCaptor.forClass(BrpPartijCode.class);
        when(attribuutConverteerder.converteerGemeenteCode(brpPartijCode.capture())).thenAnswer(
                new Answer<Lo3GemeenteCode>() {
                    @Override
                    public Lo3GemeenteCode answer(InvocationOnMock invocationOnMock) throws Throwable {
                        if(brpPartijCode.getValue().getWaarde()==null) {
                            return new Lo3GemeenteCode(LO3_GEMEENTE_CODE.getWaarde().toString(), brpPartijCode.getValue().getOnderzoek());
                        }else{
                            final Lo3GemeenteCode resultaat;
                            BrpPartijCode input = brpPartijCode.getValue();
                            if (BrpPartijCode.MINISTER.equals(input)) {
                                resultaat = Lo3GemeenteCode.RNI;
                            } else {
                                resultaat = new Lo3GemeenteCode(GEMEENTE_CODE_FORMAT.format(input.getWaarde() / 100));
                            }

                            return resultaat;
                        }
                    }
                }
        );

        ReflectionTestUtils.setField(conv,"converteerder",attribuutConverteerder);

    }

    @Test
    public void testGeenActie() {
        assertNull(conv.maakDocumentatie(null));
    }

    /**
     * Test DEF049
     */
    @Test
    public void testLegeActie() {
        final BrpActie brpActie = new BrpActie(1L, null, BRP_PARTIJ_MIGRATIE, null, null, null, 0, null);

        final Lo3Documentatie lo3Doc = conv.maakDocumentatie(brpActie);
        assertNotNull(lo3Doc);
        assertNull(lo3Doc.getBeschrijvingDocument());
        assertNull(lo3Doc.getGemeenteAkte());
    }

    /**
     * Test DEF049
     */
    @Test
    public void testLegeDocumentStapels() {
        final BrpActie brpActie =
                new BrpActie(1L, null, BRP_PARTIJ_MIGRATIE, null, null, new ArrayList<BrpActieBron>(), 0, null);
        final Lo3Documentatie lo3Doc = conv.maakDocumentatie(brpActie);
        assertNotNull(lo3Doc);
        assertNull(lo3Doc.getBeschrijvingDocument());
        assertNull(lo3Doc.getGemeenteAkte());
    }

    /**
     * Test DEF048
     */
    @Test
    public void testEnkelDocumentAkte() {
        final BrpDocumentInhoud docInhoud = new BrpDocumentInhoud(BRP_SOORT_DOCUMENT_AKTE, null, AKTE_NUMMER, null, BRP_PARTIJ_DOCUMENT);

        final BrpActie brpActie = maakActie(maakDocumentStapel(docInhoud));
        final Lo3Documentatie lo3Doc = conv.maakDocumentatie(brpActie);
        assertNotNull(lo3Doc);
        assertEquals(LO3_GEMEENTE_CODE, lo3Doc.getGemeenteAkte());
        assertEquals(Lo3String.wrap(BrpString.unwrap(AKTE_NUMMER)), lo3Doc.getNummerAkte());
        assertNull(lo3Doc.getBeschrijvingDocument());
        assertNull(lo3Doc.getGemeenteDocument());
        assertNull(lo3Doc.getDatumDocument());
    }

    /**
     * Test DEF047
     */
    @Test
    public void testEnkelDocumentDocument() {
        final BrpDocumentInhoud docInhoud = new BrpDocumentInhoud(BRP_SOORT_DOCUMENT_DOCUMENT, null, null, DOC_OMSCHRIJVING, BRP_PARTIJ_DOCUMENT);

        final BrpActie brpActie = maakActie(maakDocumentStapel(docInhoud));

        final Lo3Documentatie lo3Doc = conv.maakDocumentatie(brpActie);
        assertNotNull(lo3Doc);
        assertNull(lo3Doc.getGemeenteAkte());
        assertNull(lo3Doc.getNummerAkte());
        assertEquals(Lo3String.wrap(BrpString.unwrap(DOC_OMSCHRIJVING)), lo3Doc.getBeschrijvingDocument());
        assertEquals(LO3_GEMEENTE_CODE, lo3Doc.getGemeenteDocument());
        assertEquals(new Lo3Datum(DATUM_ONTLENING), lo3Doc.getDatumDocument());
    }

    /**
     * Test DEF050
     */
    @Test
    public void testMeerdereDocumentenAlleDocument() {
        final BrpDocumentInhoud docInhoud = new BrpDocumentInhoud(BRP_SOORT_DOCUMENT_DOCUMENT, null, null, DOC_OMSCHRIJVING, BRP_PARTIJ_DOCUMENT);
        final BrpDocumentInhoud docInhoud2 = new BrpDocumentInhoud(BRP_SOORT_DOCUMENT_DOCUMENT, null, null, DOC_OMSCHRIJVING_2, BRP_PARTIJ_DOCUMENT);

        final List<BrpActieBron> actieBronnen = new ArrayList<>();
        actieBronnen.add(maakActieBron(docInhoud2));
        actieBronnen.add(maakActieBron(docInhoud));

        final BrpActie brpActie = maakActie(actieBronnen);

        final Lo3Documentatie lo3Doc = conv.maakDocumentatie(brpActie);
        assertNotNull(lo3Doc);
        assertNull(lo3Doc.getGemeenteAkte());
        assertNull(lo3Doc.getNummerAkte());
        assertEquals(Lo3String.wrap(DOC_OMSCHRIJVING_2 + ", " + DOC_OMSCHRIJVING), lo3Doc.getBeschrijvingDocument());
        assertEquals(LO3_GEMEENTE_CODE, lo3Doc.getGemeenteDocument());
        assertEquals(new Lo3Datum(DATUM_ONTLENING), lo3Doc.getDatumDocument());
    }

    /**
     * Test DEF050
     */
    @Test
    public void testMeerdereDocumentenAlleAkten() {
        final BrpDocumentInhoud docInhoud = new BrpDocumentInhoud(BRP_SOORT_DOCUMENT_AKTE, null, AKTE_NUMMER, null, BRP_PARTIJ_DOCUMENT);
        final BrpDocumentInhoud docInhoud2 = new BrpDocumentInhoud(BRP_SOORT_DOCUMENT_AKTE, null, AKTE_NUMMER_2, null, BRP_PARTIJ_DOCUMENT);

        final List<BrpActieBron> actieBronnen = new ArrayList<>();
        actieBronnen.add(maakActieBron(docInhoud2));
        actieBronnen.add(maakActieBron(docInhoud));

        final BrpActie brpActie = maakActie(actieBronnen, false);

        final Lo3Documentatie lo3Doc = conv.maakDocumentatie(brpActie);
        assertNotNull(lo3Doc);
        assertNull(lo3Doc.getGemeenteAkte());
        assertNull(lo3Doc.getNummerAkte());
        assertEquals(Lo3String.wrap(AKTE_NUMMER_2 + ", " + AKTE_NUMMER), lo3Doc.getBeschrijvingDocument());
        assertEquals(LO3_GEMEENTE_CODE, lo3Doc.getGemeenteDocument());
        assertEquals(DATUM_OPNEMING.converteerNaarLo3Datum(), lo3Doc.getDatumDocument());
    }

    /**
     * Test DEF050
     */
    @Test
    public void testMeerdereDocumentenGemengdDocumentEnAkte() {
        final BrpDocumentInhoud docInhoud = new BrpDocumentInhoud(BRP_SOORT_DOCUMENT_DOCUMENT, null, null, DOC_OMSCHRIJVING, BRP_PARTIJ_DOCUMENT);
        final BrpDocumentInhoud docInhoud2 = new BrpDocumentInhoud(BRP_SOORT_DOCUMENT_AKTE, null, AKTE_NUMMER_2, null, BRP_PARTIJ_DOCUMENT);

        final List<BrpActieBron> actieBronnen = new ArrayList<>();
        actieBronnen.add(maakActieBron(docInhoud2));
        actieBronnen.add(maakActieBron(docInhoud));

        final BrpActie brpActie = maakActie(actieBronnen);

        final Lo3Documentatie lo3Doc = conv.maakDocumentatie(brpActie);
        assertNotNull(lo3Doc);
        assertNull(lo3Doc.getGemeenteAkte());
        assertNull(lo3Doc.getNummerAkte());
        assertEquals(Lo3String.wrap(AKTE_NUMMER_2 + ", " + DOC_OMSCHRIJVING), lo3Doc.getBeschrijvingDocument());
        assertEquals(LO3_GEMEENTE_CODE, lo3Doc.getGemeenteDocument());
        assertEquals(new Lo3Datum(DATUM_ONTLENING), lo3Doc.getDatumDocument());
    }

    /**
     * Test DEF050
     */
    @Test
    public void testMeerdereDocumentenLeegDocumentEnGevuldAkte() {
        final BrpDocumentInhoud docInhoud =
                new BrpDocumentInhoud(BRP_SOORT_DOCUMENT_DOCUMENT, null, LEGE_BRPSTRING, DOC_OMSCHRIJVING, BRP_PARTIJ_DOCUMENT);
        final BrpDocumentInhoud docInhoud2 = new BrpDocumentInhoud(BRP_SOORT_DOCUMENT_AKTE, null, AKTE_NUMMER_2, LEGE_BRPSTRING, BRP_PARTIJ_DOCUMENT);

        final List<BrpActieBron> actieBronnen = new ArrayList<>();
        actieBronnen.add(maakActieBron(docInhoud2));
        actieBronnen.add(maakActieBron(docInhoud));

        final BrpActie brpActie = maakActie(actieBronnen);

        final Lo3Documentatie lo3Doc = conv.maakDocumentatie(brpActie);
        assertNotNull(lo3Doc);
        assertNull(lo3Doc.getGemeenteAkte());
        assertNull(lo3Doc.getNummerAkte());
        assertEquals(Lo3String.wrap(AKTE_NUMMER_2 + ", " + DOC_OMSCHRIJVING), lo3Doc.getBeschrijvingDocument());
        assertEquals(LO3_GEMEENTE_CODE, lo3Doc.getGemeenteDocument());
        assertEquals(new Lo3Datum(DATUM_ONTLENING), lo3Doc.getDatumDocument());
    }

    /**
     * Test PRE085
     */
    @Test
    public void testPreconditie085AllesNull() {
        final BrpDocumentInhoud docInhoud = new BrpDocumentInhoud(BRP_SOORT_DOCUMENT_DOCUMENT, null, null, null, BRP_PARTIJ_DOCUMENT);

        final BrpActie brpActie = maakActie(maakDocumentStapel(docInhoud));
        try {
            conv.maakDocumentatie(brpActie);
            fail(PRE085_FOUTMELDING);
        } catch (final PreconditieException pe) {
            assertEquals(SoortMeldingCode.PRE085.name(), pe.getPreconditieNaam());
            assertFalse(pe.getGroepen().isEmpty());
            assertTrue(pe.getGroepen().size() == 1);
            assertEquals(BrpDocumentInhoud.class.getName(), pe.getGroepen().iterator().next());
        }
    }

    /**
     * Test PRE085
     */
    @Test
    public void testPreconditie085AllesLeeg() {
        final BrpDocumentInhoud docInhoud = new BrpDocumentInhoud(BRP_SOORT_DOCUMENT_DOCUMENT, null, LEGE_BRPSTRING, LEGE_BRPSTRING, BRP_PARTIJ_DOCUMENT);

        final BrpActie brpActie = maakActie(maakDocumentStapel(docInhoud));
        try {
            conv.maakDocumentatie(brpActie);
            fail(PRE085_FOUTMELDING);
        } catch (final PreconditieException pe) {
            assertEquals(SoortMeldingCode.PRE085.name(), pe.getPreconditieNaam());
            assertFalse(pe.getGroepen().isEmpty());
            assertTrue(pe.getGroepen().size() == 1);
            assertEquals(BrpDocumentInhoud.class.getName(), pe.getGroepen().iterator().next());
        }
    }

    /**
     * Test PRE085
     */
    @Test
    public void testPreconditie085AkteNullDocumentLeeg() {
        final BrpDocumentInhoud docInhoud = new BrpDocumentInhoud(BRP_SOORT_DOCUMENT_DOCUMENT, null, null, LEGE_BRPSTRING, BRP_PARTIJ_DOCUMENT);

        final BrpActie brpActie = maakActie(maakDocumentStapel(docInhoud));
        try {
            conv.maakDocumentatie(brpActie);
            fail(PRE085_FOUTMELDING);
        } catch (final PreconditieException pe) {
            assertEquals(SoortMeldingCode.PRE085.name(), pe.getPreconditieNaam());
            assertFalse(pe.getGroepen().isEmpty());
            assertTrue(pe.getGroepen().size() == 1);
            assertEquals(BrpDocumentInhoud.class.getName(), pe.getGroepen().iterator().next());
        }
    }

    /**
     * Test PRE085
     */
    @Test
    public void testPreconditie085AkteLeegDocumentNull() {
        final BrpDocumentInhoud docInhoud = new BrpDocumentInhoud(BRP_SOORT_DOCUMENT_AKTE, null, LEGE_BRPSTRING, null, BRP_PARTIJ_DOCUMENT);

        final BrpActie brpActie = maakActie(maakDocumentStapel(docInhoud));
        try {
            conv.maakDocumentatie(brpActie);
            fail(PRE085_FOUTMELDING);
        } catch (final PreconditieException pe) {
            assertEquals(SoortMeldingCode.PRE085.name(), pe.getPreconditieNaam());
            assertFalse(pe.getGroepen().isEmpty());
            assertTrue(pe.getGroepen().size() == 1);
            assertEquals(BrpDocumentInhoud.class.getName(), pe.getGroepen().iterator().next());
        }
    }

    /**
     * Test PRE085
     */
    @Test
    public void testPreconditie085AktenummerDocOmschrijvingGevuld() {
        final BrpDocumentInhoud docInhoud =
                new BrpDocumentInhoud(
                    BRP_SOORT_DOCUMENT_AKTE,
                    null,
                    new BrpString("1 ABCD2", null),
                    new BrpString("Omschrijving", null),
                    BRP_PARTIJ_DOCUMENT);

        final BrpActie brpActie = maakActie(maakDocumentStapel(docInhoud));
        try {
            conv.maakDocumentatie(brpActie);
            fail(PRE085_FOUTMELDING);
        } catch (final PreconditieException pe) {
            assertEquals(SoortMeldingCode.PRE085.name(), pe.getPreconditieNaam());
            assertFalse(pe.getGroepen().isEmpty());
            assertTrue(pe.getGroepen().size() == 1);
            assertEquals(BrpDocumentInhoud.class.getName(), pe.getGroepen().iterator().next());
        }
    }

    /**
     * Test PRE108
     */
    @Test
    public void testPreconditie108VerschillendePartijen() {
        final BrpDocumentInhoud docInhoud = new BrpDocumentInhoud(BRP_SOORT_DOCUMENT_AKTE, null, AKTE_NUMMER, null, BRP_PARTIJ_DOCUMENT);
        final BrpDocumentInhoud docInhoud2 = new BrpDocumentInhoud(BRP_SOORT_DOCUMENT_DOCUMENT, null, null, DOC_OMSCHRIJVING, BRP_PARTIJ_DOCUMENT_2);

        final List<BrpActieBron> actieBronnen = new ArrayList<>();
        actieBronnen.add(maakActieBron(docInhoud2));
        actieBronnen.add(maakActieBron(docInhoud));

        final BrpActie brpActie = maakActie(actieBronnen);
        try {
            conv.maakDocumentatie(brpActie);
            fail("PRE108 zou gegooid moeten worden");
        } catch (final PreconditieException pe) {
            assertEquals(SoortMeldingCode.PRE108.name(), pe.getPreconditieNaam());
            assertFalse(pe.getGroepen().isEmpty());
            assertTrue(pe.getGroepen().size() == 1);
            assertEquals(BrpDocumentInhoud.class.getName(), pe.getGroepen().iterator().next());
        }
    }

    /**
     * Test Document partij leeg
     */
    @Test
    public void testDocumentPartijNull() {
        final BrpDocumentInhoud docInhoud = new BrpDocumentInhoud(BRP_SOORT_DOCUMENT_AKTE, null, AKTE_NUMMER, null, null);
        final BrpActie brpActie = maakActie(maakDocumentStapel(docInhoud));

        final Lo3Documentatie lo3Doc = conv.maakDocumentatie(brpActie);
        assertNotNull(lo3Doc);
        assertNull(lo3Doc.getGemeenteAkte());
        assertEquals(Lo3String.wrap(BrpString.unwrap(AKTE_NUMMER)), lo3Doc.getNummerAkte());
        assertNull(lo3Doc.getBeschrijvingDocument());
        assertNull(lo3Doc.getGemeenteDocument());
        assertNull(lo3Doc.getDatumDocument());
    }

    @Test
    public void testRechtsOmschrijvingRNIDeelnemerMetOmschrijvingVerdrag(){
        final BrpDocumentInhoud docInhoud = new BrpDocumentInhoud(BRP_SOORT_DOCUMENT_AKTE, null, AKTE_NUMMER, null, null);
        BrpPartijCode code = new BrpPartijCode(99);
        final BrpActie brpActie = maakActie(maakDocumentStapel(docInhoud),code,new BrpString(RECHTS_OMSCHRIJVING));
        final Lo3Documentatie lo3Doc = conv.maakDocumentatie(brpActie);
        assertEquals(RECHTS_OMSCHRIJVING, lo3Doc.getOmschrijvingVerdrag().getWaarde());
    }
    @Test
    public void testRechtsOmschrijvingRNIDeelnemerZonderOmschrijvingVerdrag(){
        final BrpDocumentInhoud docInhoud = new BrpDocumentInhoud(BRP_SOORT_DOCUMENT_AKTE, null, AKTE_NUMMER, null, null);
        BrpPartijCode code = new BrpPartijCode(99);
        final BrpActie brpActie = maakActie(maakDocumentStapel(docInhoud),code,null);
        final Lo3Documentatie lo3Doc = conv.maakDocumentatie(brpActie);
        assertNull(lo3Doc.getOmschrijvingVerdrag());
    }

    private BrpActie maakActie(final BrpStapel<BrpDocumentInhoud> documenten,BrpPartijCode code,BrpString rechtsOmschrijving) {
        final BrpActieBron actieBron = new BrpActieBron(documenten, rechtsOmschrijving);
        return maakActie(Collections.singletonList(actieBron), true, code);
    }

    private BrpActie maakActie(final BrpStapel<BrpDocumentInhoud> documenten,BrpPartijCode code) {
        return maakActie(documenten,code,null);
    }

    private BrpActie maakActie(final BrpStapel<BrpDocumentInhoud> documenten) {
        final BrpActieBron actieBron = new BrpActieBron(documenten, null);
        return maakActie(Collections.singletonList(actieBron), true);
    }

    private BrpActie maakActie(final List<BrpActieBron> actieBronnen) {
        return maakActie(actieBronnen, true);
    }

    private BrpActie maakActie(final List<BrpActieBron> actieBronnen, final boolean heeftDatumOntlening) {
        return maakActie(actieBronnen,heeftDatumOntlening,BRP_PARTIJ_MIGRATIE);
    }

    private BrpActie maakActie(final List<BrpActieBron> actieBronnen, final boolean heeftDatumOntlening,BrpPartijCode partijcode) {
        BrpDatum datumOntlening = null;
        if (heeftDatumOntlening) {
            datumOntlening = new BrpDatum(DATUM_ONTLENING, null);
        }
        return new BrpActie(
                1L,
                BrpSoortActieCode.CONVERSIE_GBA,
                partijcode,
                DATUM_OPNEMING,
                datumOntlening,
                actieBronnen,
                0,
                null);
    }

    private BrpActieBron maakActieBron(final BrpDocumentInhoud... docInhouden) {
        final List<BrpGroep<BrpDocumentInhoud>> groepen = new ArrayList<>();
        for (final BrpDocumentInhoud inhoud : docInhouden) {
            groepen.add(new BrpGroep<>(inhoud, BrpHistorie.NULL_HISTORIE, null, null, null));
        }
        return new BrpActieBron(new BrpStapel<>(groepen), null);
    }

    private BrpStapel<BrpDocumentInhoud> maakDocumentStapel(final BrpDocumentInhoud... docInhouden) {
        final List<BrpGroep<BrpDocumentInhoud>> groepen = new ArrayList<>();
        for (final BrpDocumentInhoud inhoud : docInhouden) {
            groepen.add(new BrpGroep<>(inhoud, BrpHistorie.NULL_HISTORIE, null, null, null));
        }
        return new BrpStapel<>(groepen);
    }

    class Rnideelnemer extends ArgumentMatcher<BrpPartijCode> {
        public boolean matches(Object code) {
            return code == null || ((BrpPartijCode) code).getWaarde() == 99;
        }
    }
    class NotRnideelnemer extends ArgumentMatcher<BrpPartijCode> {
        public boolean matches(Object code) {
            return code != null && ((BrpPartijCode) code).getWaarde() != 99;
        }
    }

    class vertaalRechtsOmschrijving extends ArgumentMatcher<BrpString> {
        public boolean matches(Object brpString) {
            return brpString != null && ((BrpString)brpString).getWaarde().equals(RECHTS_OMSCHRIJVING);
        }
    }
    class notVertaalRechtsOmschrijving extends ArgumentMatcher<BrpString> {
        public boolean matches(Object brpString) {
            return brpString != null && ((BrpString)brpString).getWaarde().equals(RECHTS_OMSCHRIJVING);
        }
    }
}
