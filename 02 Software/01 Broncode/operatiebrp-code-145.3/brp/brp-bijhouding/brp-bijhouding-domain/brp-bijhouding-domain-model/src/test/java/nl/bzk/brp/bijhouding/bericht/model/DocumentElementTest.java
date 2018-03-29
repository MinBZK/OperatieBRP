/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.COMMUNICATIE_ID_ATTRIBUUT;
import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.OBJECTTYPE_ATTRIBUUT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Document;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import org.junit.Before;
import org.junit.Test;

/**
 * Unittest voor {@link DocumentElement}.
 */
public class DocumentElementTest extends AbstractElementTest {

    private static final StringElement STRING_ELEMENT_HUWELIJKSAKTE = new StringElement("3abcd");
    private Map<String, String> attributes;
    private final String GEBOORTEAKTE_NAAM = "Geboorteakte";
    private final String BESLUIT_OVERIG_NAAM = "Besluit overig";
    private final String HUWELIJKSAKTE_NAAM = "Huwelijksakte";
    private final String NIET_BESTAAND_NAAM = "bestaat niet";

    private final SoortDocument SOORT_DOCUMENT_HUWELIJKSAKTE = new SoortDocument(HUWELIJKSAKTE_NAAM, HUWELIJKSAKTE_NAAM);
    private final SoortDocument SOORT_DOCUMENT_BESLUIT_OVERIG = new SoortDocument(BESLUIT_OVERIG_NAAM, BESLUIT_OVERIG_NAAM);
    private final SoortDocument SOORT_DOCUMENT_GEBOORTEAKTE = new SoortDocument(GEBOORTEAKTE_NAAM, GEBOORTEAKTE_NAAM);

    private final Partij partij = new Partij("test partij", "000001");

    @Before
    public void setupDocumentElementTest() {
        attributes = new HashMap<>();
        attributes.put(OBJECTTYPE_ATTRIBUUT.toString(), "objecttype");
        attributes.put(COMMUNICATIE_ID_ATTRIBUUT.toString(), "ci_test");
        SOORT_DOCUMENT_GEBOORTEAKTE.setRegistersoort('1');
        SOORT_DOCUMENT_HUWELIJKSAKTE.setRegistersoort('3');

        when(getDynamischeStamtabelRepository().getSoortDocumentByNaam("")).thenReturn(null);
        when(getDynamischeStamtabelRepository().getSoortDocumentByNaam(HUWELIJKSAKTE_NAAM)).thenReturn(SOORT_DOCUMENT_HUWELIJKSAKTE);
        when(getDynamischeStamtabelRepository().getSoortDocumentByNaam(GEBOORTEAKTE_NAAM)).thenReturn(SOORT_DOCUMENT_GEBOORTEAKTE);
        when(getDynamischeStamtabelRepository().getSoortDocumentByNaam(BESLUIT_OVERIG_NAAM)).thenReturn(SOORT_DOCUMENT_BESLUIT_OVERIG);
        when(getDynamischeStamtabelRepository().getSoortDocumentByNaam(NIET_BESTAAND_NAAM)).thenReturn(null);

        when(getDynamischeStamtabelRepository().getPartijByCode("1")).thenReturn(partij);
        when(getDynamischeStamtabelRepository().getPartijByCode("2")).thenReturn(null);
    }

    @Test
    public void testSoortnaamEnAktenummerLeeg() {
        final DocumentElement element =
                new DocumentElement(
                        attributes,
                        new StringElement(""),
                        null,
                        null,
                        new StringElement("1"));
        final List<MeldingElement> meldingen = element.valideer();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1605, meldingen.get(0).getRegel());
    }

    @Test
    public void testSoortnaamLeegAktenummerGevuld() {
        final DocumentElement element =
                new DocumentElement(
                        attributes,
                        new StringElement(""),
                        STRING_ELEMENT_HUWELIJKSAKTE,
                        null,
                        new StringElement("1"));
        final List<MeldingElement> meldingen = element.valideer();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1605, meldingen.get(0).getRegel());
    }

    @Test
    public void testRegisterSoortIsCorrectVoorAkte() {
        final DocumentElement element =
                new DocumentElement(
                        attributes,
                        new StringElement(HUWELIJKSAKTE_NAAM),
                        STRING_ELEMENT_HUWELIJKSAKTE,
                        null,
                        new StringElement("1"));
        assertEquals(0, element.valideer().size());
    }

    @Test
    public void testRegisterSoortNietCorrectVoorAkte() {
        final DocumentElement element =
                new DocumentElement(
                        attributes,
                        new StringElement(GEBOORTEAKTE_NAAM),
                        STRING_ELEMENT_HUWELIJKSAKTE,
                        null,
                        new StringElement("1"));
        final List<MeldingElement> meldingen = element.valideer();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1848, meldingen.get(0).getRegel());
    }

    @Test
    public void testRegisterSoortLeegAkteLeegOmschrijvingGevuld() {
        final DocumentElement element =
                new DocumentElement(
                        attributes,
                        new StringElement(BESLUIT_OVERIG_NAAM),
                        null,
                        new StringElement("Document omschrijving"),
                        new StringElement("1"));
        final List<MeldingElement> meldingen = element.valideer();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void testRegisterSoortLeegAkteGevuld() {
        final DocumentElement element =
                new DocumentElement(
                        attributes,
                        new StringElement(BESLUIT_OVERIG_NAAM),
                        STRING_ELEMENT_HUWELIJKSAKTE,
                        null,
                        new StringElement("1"));
        final List<MeldingElement> meldingen = element.valideer();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1849, meldingen.get(0).getRegel());
    }

    @Test
    public void testRegisterSoortGevuldAkteLeeg() {
        final DocumentElement element =
                new DocumentElement(attributes, new StringElement(GEBOORTEAKTE_NAAM), null, null, new StringElement("1"));
        final List<MeldingElement> meldingen = element.valideer();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1849, meldingen.get(0).getRegel());
    }

    @Test
    public void testRegisterSoortGevuldOmschrijvingGevuld() {
        final DocumentElement element =
                new DocumentElement(
                        attributes,
                        new StringElement(GEBOORTEAKTE_NAAM),
                        null,
                        new StringElement("Omschrijving"),
                        new StringElement("1"));
        final List<MeldingElement> meldingen = element.valideer();
        assertEquals(2, meldingen.size());
        assertEquals(Regel.R1849, meldingen.get(0).getRegel());
        assertEquals(Regel.R1850, meldingen.get(1).getRegel());
    }

    @Test
    public void testRegisterSoortLeegOmschrijvingGevuld() {
        final DocumentElement element =
                new DocumentElement(
                        attributes,
                        new StringElement(BESLUIT_OVERIG_NAAM),
                        null,
                        new StringElement("Omschrijving"),
                        new StringElement("1"));
        final List<MeldingElement> meldingen = element.valideer();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void testRegisterSoortOnbekend() {
        final DocumentElement element =
                new DocumentElement(
                        attributes,
                        new StringElement(NIET_BESTAAND_NAAM),
                        null,
                        new StringElement("Omschrijving"),
                        new StringElement("1"));
        final List<MeldingElement> meldingen = element.valideer();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1605, meldingen.get(0).getRegel());
    }

    @Test
    public void testMaakDocumentEntiteit() {
        final DocumentElement element =
                new DocumentElement(
                        attributes,
                        new StringElement(HUWELIJKSAKTE_NAAM),
                        STRING_ELEMENT_HUWELIJKSAKTE,
                        null,
                        new StringElement("1"));
        final List<MeldingElement> meldingen = element.valideer();
        assertEquals(0, meldingen.size());
        final Document entiteit = element.maakDocumentEntiteit();
        assertNull(entiteit.getId());
        assertEquals(SOORT_DOCUMENT_HUWELIJKSAKTE, entiteit.getSoortDocument());
        assertEquals(STRING_ELEMENT_HUWELIJKSAKTE.getWaarde(), entiteit.getAktenummer());
        assertEquals(partij, entiteit.getPartij());
        assertNull(entiteit.getOmschrijving());
    }

    @Test
    public void testPartijOnbekend() {
        final DocumentElement element =
                new DocumentElement(
                        attributes,
                        new StringElement(BESLUIT_OVERIG_NAAM),
                        null,
                        new StringElement("Omschrijving"),
                        new StringElement("2"));
        final List<MeldingElement> meldingen = element.valideer();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2235, meldingen.get(0).getRegel());
    }

}
