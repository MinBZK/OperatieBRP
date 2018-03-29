/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.element;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;
import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerantwoordingCategorie;
import org.junit.Assert;
import org.junit.Test;

/**
 * GroepElementTest.
 */
public class GroepElementTest {


    @Test
    public void isIdentiteitGroep() throws Exception {
        assertTrue(getGroepElement(Element.PERSOON_INDICATIE_IDENTITEIT.getId()).isIdentiteitGroep());
    }

    @Test
    public void isStandaardGroep() throws Exception {
        assertTrue(getGroepElement(Element.PERSOON_VOORNAAM_STANDAARD.getId()).isStandaardGroep());
    }

    @Test
    public void testGetAttribuutMetElementNaam() throws Exception {
        Assert.assertNotNull(getGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS.getId()).getAttribuutMetElementNaam("Burgerservicenummer"));
    }

    @Test(expected = IllegalStateException.class)
    public void testGetAttribuutMetNietBestaandeElementNaam() {
        getGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS.getId()).getAttribuutMetElementNaam("Nietbestaandenaam");
    }

    @Test
    public void testIsFormeel() throws Exception {
        // historiepatroon F+M
        Assert.assertFalse(getGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS.getId()).isFormeel());
        Assert.assertTrue(getGroepElement(Element.PERSOON_AFGELEIDADMINISTRATIEF.getId()).isFormeel());
        Assert.assertTrue(getGroepElement(Element.BETROKKENHEID_IDENTITEIT.getId()).isFormeel());
    }

    @Test
    public void testIsMaterieel() throws Exception {
        // historiepatroon F+M
        assertTrue(getGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS.getId()).isMaterieel());
        assertFalse(getGroepElement(Element.PERSOON_AFGELEIDADMINISTRATIEF.getId()).isMaterieel());
    }

    @Test
    public void testIsIndicatie() throws Exception {
        assertTrue(getGroepElement(Element.PERSOON_INDICATIE_IDENTITEIT.getId()).isIndicatie());
    }

    @Test
    public void testIsAliasVan() throws Exception {
        assertTrue(
                getGroepElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE.getId()).getAliasVan().equals(getGroepElement
                        (Element.PERSOON_GEBOORTE.getId())));
    }

    @Test
    public void getVerantwoordingCategorie() throws Exception {
        assertEquals(VerantwoordingCategorie.A, getGroepElement(Element.PERSOON_GEBOORTE.getId()).getVerantwoordingCategorie());
        assertEquals(VerantwoordingCategorie.G, getGroepElement(Element.PERSOON_IDENTITEIT.getId()).getVerantwoordingCategorie());
        assertEquals(VerantwoordingCategorie.D, getGroepElement(Element.PERSOON_AFNEMERINDICATIE_STANDAARD.getId()).getVerantwoordingCategorie());
    }

    @Test
    public void bevatAttribuut() throws Exception {
        assertFalse(getGroepElement(Element.PERSOON_IDENTITEIT.getId()).bevatAttribuut("xxx"));
        assertTrue(getGroepElement(Element.PERSOON_GEBOORTE.getId()).bevatAttribuut(
                getAttribuutElement(Element.PERSOON_GEBOORTE_GEMEENTECODE.getId()).getElementNaam()));
    }

    @Test
    public void testIdentificerend() {
        assertFalse(getGroepElement(Element.PERSOON_IDENTITEIT.getId()).isIdentificerend());
        assertTrue(getGroepElement(Element.PERSOON_GEBOORTE.getId()).isIdentificerend());
        assertTrue(getGroepElement(Element.PERSOON_GESLACHTSAANDUIDING.getId()).isIdentificerend());
        assertTrue(getGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS.getId()).isIdentificerend());
        assertTrue(getGroepElement(Element.PERSOON_SAMENGESTELDENAAM.getId()).isIdentificerend());
        assertTrue(getGroepElement(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE.getId()).isIdentificerend());
        assertTrue(getGroepElement(Element.GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING.getId()).isIdentificerend());
        assertTrue(getGroepElement(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM.getId()).isIdentificerend());
        assertTrue(getGroepElement(Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS.getId()).isIdentificerend());
    }

    @Test
    public void testSpecials() {

        final GroepElement groepElement = getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId());
        assertEquals(getAttribuutElement(Element.PERSOON_ADRES_ACTIEINHOUD.getId()), groepElement.getActieInhoudAttribuut());
        assertEquals(getAttribuutElement(Element.PERSOON_ADRES_ACTIEVERVAL.getId()), groepElement.getActieVervalAttribuut());
        assertEquals(getAttribuutElement(Element.PERSOON_ADRES_ACTIEAANPASSINGGELDIGHEID.getId()), groepElement.getActieAanpassingGeldigheidAttribuut());
        assertEquals(getAttribuutElement(Element.PERSOON_ADRES_ACTIEVERVALTBVLEVERINGMUTATIES.getId()), groepElement
                .getActieVervalTbvLeveringMutatiesAttribuut());
        assertEquals(getAttribuutElement(Element.PERSOON_ADRES_NADEREAANDUIDINGVERVAL.getId()), groepElement.getNadereAanduidingVervalAttribuut());
        assertEquals(getAttribuutElement(Element.PERSOON_ADRES_TIJDSTIPREGISTRATIE.getId()), groepElement.getDatumTijdRegistratieAttribuut());
        assertEquals(getAttribuutElement(Element.PERSOON_ADRES_TIJDSTIPVERVAL.getId()), groepElement.getDatumTijdVervalAttribuut());
        assertEquals(getAttribuutElement(Element.PERSOON_ADRES_DATUMAANVANGGELDIGHEID.getId()), groepElement.getDatumAanvangGeldigheidAttribuut());
        assertEquals(getAttribuutElement(Element.PERSOON_ADRES_DATUMEINDEGELDIGHEID.getId()), groepElement.getDatumEindeGeldigheidAttribuut());
        assertEquals(getAttribuutElement(Element.PERSOON_ADRES_INDICATIEVOORKOMENTBVLEVERINGMUTATIES.getId()),
                groepElement.getIndicatieTbvLeveringMutatiesAttribuut());

        final GroepElement afnemerindicatie = getGroepElement(Element.PERSOON_AFNEMERINDICATIE_STANDAARD.getId());
        assertEquals(getAttribuutElement(Element.PERSOON_AFNEMERINDICATIE_DIENSTINHOUD.getId()), afnemerindicatie.getDienstInhoudAttribuut());
        assertEquals(getAttribuutElement(Element.PERSOON_AFNEMERINDICATIE_DIENSTVERVAL.getId()), afnemerindicatie.getDienstVervalAttribuut());
    }

    @Test
    public void testIsVerantwoordingsgroep() {
        Assert.assertFalse(ElementHelper.getGroepElement(Element.PERSOON_GEBOORTE).isVerantwoordingsgroep());
        Assert.assertTrue(ElementHelper.getGroepElement(Element.DOCUMENT_IDENTITEIT).isVerantwoordingsgroep());
    }

    @Test
    public void isOnderzoeksgroep() {
        Assert.assertFalse(ElementHelper.getGroepElement(Element.PERSOON_GEBOORTE).isOnderzoeksgroep());
        Assert.assertTrue(ElementHelper.getGroepElement(Element.GEGEVENINONDERZOEK_IDENTITEIT).isOnderzoeksgroep());
    }


}
