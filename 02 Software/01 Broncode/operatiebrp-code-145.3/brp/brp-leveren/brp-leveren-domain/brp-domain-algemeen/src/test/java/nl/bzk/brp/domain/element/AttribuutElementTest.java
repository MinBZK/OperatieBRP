/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.element;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;
import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.DatabaseType;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.ElementBasisType;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortElementAutorisatie;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class AttribuutElementTest {

    @Test
    public void testMethoden() {
        final AttribuutElement element = getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM.getId());
        Assert.assertEquals("Persoon.SamengesteldeNaam.Geslachtsnaamstam", element.getNaam());
        Assert.assertEquals("Geslachtsnaamstam", element.getElementNaam());
        Assert.assertEquals(3094, element.getId().intValue());
        Assert.assertEquals(20120101, (int) element.getDatumAanvangGeldigheid());
        Assert.assertNull(null, element.getDatumEindeGeldigheid());
        Assert.assertNull(null, element.getAliasVan());
        Assert.assertEquals(ElementBasisType.STRING, element.getDatatype());
        Assert.assertEquals("geslachtsnaamstam", element.getXmlNaam());
        Assert.assertNull(element.getMininumLengte());
        Assert.assertEquals("", element.getInverseAssociatieIdentCode());
        Assert.assertEquals(Element.PERSOON_SAMENGESTELDENAAM.getId(), element.getGroepId());
        Assert.assertEquals(getGroepElement(Element.PERSOON_SAMENGESTELDENAAM.getId()), element.getGroep());
        Assert.assertFalse(element.isActieAanpassingGeldigheid());
        Assert.assertFalse(element.isActieInhoud());
        Assert.assertFalse(element.isActieVerval());
        Assert.assertFalse(element.isDatumAanvangGeldigheid());
        Assert.assertFalse(element.isDatumEindeGeldigheid());
        Assert.assertFalse(element.isDatumTijdRegistratie());
        Assert.assertFalse(element.isVoorkomenTbvLeveringMutaties());
        Assert.assertFalse(element.isNadereAanduidingVerval());
        Assert.assertFalse(element.isActieVervalTbvLevermutaties());
        Assert.assertFalse(element.isVerantwoording());
        Assert.assertEquals(SoortElementAutorisatie.AANBEVOLEN, element.getAutorisatie());
        Assert.assertNotNull(element.toString());
    }

    @Test
    public void testAttributen() {

        for (AttribuutElement element : ElementHelper.getAttributen()) {

            Assert.assertEquals(element.isActieInhoud(), AttribuutElement.BRP_ACTIE_INHOUD.equals(element.getElementNaam()));
            Assert.assertEquals(element.isVerantwoording(), element.isActieInhoud() || element.isActieVerval() || element.isActieAanpassingGeldigheid());
            Assert.assertEquals(element.isActieAanpassingGeldigheid(), AttribuutElement.BRP_ACTIE_AANPASSING_GELDIGHEID.equals(element.getElementNaam()));
            Assert.assertEquals(element.isActieVerval(), AttribuutElement.BRP_ACTIE_VERVAL.equals(element.getElementNaam()));
            Assert.assertEquals(element.isDatumAanvangGeldigheid(), AttribuutElement.DATUM_AANVANG_GELDIGHEID.equals(element.getElementNaam()));
            Assert.assertEquals(element.isDatumEindeGeldigheid(), AttribuutElement.DATUM_EINDE_GELDIGHEID.equals(element.getElementNaam()));
            Assert.assertEquals(element.isDatumTijdRegistratie(), AttribuutElement.DATUM_TIJD_REGISTRATIE.equals(element.getElementNaam()));
            Assert.assertEquals(element.isDatumTijdVerval(), AttribuutElement.DATUM_TIJD_VERVAL.equals(element.getElementNaam()));
            Assert
                    .assertEquals(element.isVoorkomenTbvLeveringMutaties(), AttribuutElement.VOORKOMEN_TBV_LEVERING_MUTATIES.equals(element.getElementNaam()));
            Assert.assertEquals(element.isNadereAanduidingVerval(), AttribuutElement.NADERE_AANDUIDING_VERVAL.equals(element.getElementNaam()));
            Assert.assertEquals(element.isActieVervalTbvLevermutaties(),
                    AttribuutElement.BRP_ACTIE_VERVAL_TBV_LEVERING_MUTATIES.equals(element.getElementNaam()));
        }
    }

    @Test
    public void testIsStamgegevenReferentie() {
        //geen ref, type == null
        Assert.assertFalse(getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_DATUMAANVANGGELDIGHEID.getId()).isStamgegevenReferentie());
        //geen ref, type != null
        Assert.assertFalse(getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_ACTIEINHOUD.getId()).isStamgegevenReferentie());

    }

    @Test
    public void testIsGetal() {
        //varchar
        Assert.assertFalse(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER).isGetal());
        //varchar
        Assert.assertFalse(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER).isGetal());
        //smallint
        Assert.assertTrue(getAttribuutElement(Element.HUWELIJKGEREGISTREERDPARTNERSCHAP_GEMEENTEAANVANGCODE).isGetal());
        //integer
        Assert.assertTrue(getAttribuutElement(Element.HUWELIJKGEREGISTREERDPARTNERSCHAP_DATUMAANVANG).isGetal());
        //varchar
        Assert.assertFalse(getAttribuutElement(Element.HUWELIJKGEREGISTREERDPARTNERSCHAP_BUITENLANDSEREGIOEINDE).isGetal());
    }

    @Test
    public void testIsString() {
        //varchar, geen minlengte, geen stamgeg.ref
        Assert.assertTrue(getAttribuutElement(Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL1).isString());
        //varchar, minlengte<>maxlengte
        Assert.assertTrue(getAttribuutElement(Element.PERSOON_REISDOCUMENT_AUTORITEITVANAFGIFTE).isString());
        //text
        Assert.assertTrue(getAttribuutElement(Element.BIJHOUDINGSPLAN_BIJHOUDINGSVOORSTELVERZOEKBERICHT).isString());

        //smallint, minlengte==maxlengte
        Assert.assertFalse(getAttribuutElement(Element.PERSOON_MIGRATIE_SOORTCODE).isString());
        //getal
        Assert.assertFalse(getAttribuutElement(Element.HUWELIJKGEREGISTREERDPARTNERSCHAP_DATUMAANVANG).isString());
    }

    @Test
    public void testAlias() {
        Assert.assertEquals(getAttribuutElement(Element.RELATIE_BUITENLANDSEREGIOAANVANG),
                getAttribuutElement(Element.FAMILIERECHTELIJKEBETREKKING_BUITENLANDSEREGIOAANVANG).getAliasVan());

        Assert.assertNull(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER).getAliasVan());
    }

    @Test
    public void print() {
        for (AttribuutElement attribuutElement : ElementHelper.getAttributen()) {
            final DatabaseType typeidentdb = attribuutElement.getElement().getElementWaarde().getTypeidentdb();
            if (typeidentdb != null && typeidentdb == DatabaseType.VARCHAR
                    && (attribuutElement.getMininumLengte() == null && !attribuutElement.isStamgegevenReferentie()))
                System.out.println(attribuutElement.toString());
        }
    }


}
