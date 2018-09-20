/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding;

import java.io.IOException;

import nl.bzk.brp.business.dto.BerichtStuurgegevens;
import nl.bzk.brp.business.dto.bevraging.PersonenOpAdresInclusiefBetrokkenhedenVraag;
import nl.bzk.brp.business.dto.bevraging.VraagPersonenOpAdresInclusiefBetrokkenhedenBericht;
import nl.bzk.brp.model.binding.AbstractBindingInTest;
import nl.bzk.brp.utils.XmlUtils;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;


public class VraagPersonenOpAdresInclusiefBetrokkenhedenBerichtBindingTest extends
        AbstractBindingInTest<VraagPersonenOpAdresInclusiefBetrokkenhedenBericht>
{

    @Override
    protected Class<VraagPersonenOpAdresInclusiefBetrokkenhedenBericht> getBindingClass() {
        return VraagPersonenOpAdresInclusiefBetrokkenhedenBericht.class;
    }

    @Test
    public void testBindingMaxBericht() throws JiBXException, IOException {
        String xml = leesBestand("bevragen_VraagPersonenOpAdresInclusiefBetrokkenheden_MAX.xml");

        //TODO fix xml
        //XmlUtils.valideerOutputTegenSchema(xml, "/xsd/BRP_Bevraging_Berichten.xsd");

        VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);

        BerichtStuurgegevens stuurgegevens = bericht.getBerichtStuurgegevens();
        Assert.assertNotNull(stuurgegevens);
        Assert.assertEquals("app", stuurgegevens.getApplicatie());
        Assert.assertEquals("org", stuurgegevens.getOrganisatie());
        Assert.assertEquals("ref", stuurgegevens.getReferentienummer());
        Assert.assertNull(stuurgegevens.getCrossReferentienummer());

        PersonenOpAdresInclusiefBetrokkenhedenVraag vraag = bericht.getVraag();
        Assert.assertNotNull(vraag);
        Assert.assertEquals("123456789", vraag.getZoekCriteria().getBurgerservicenummer());
        Assert.assertEquals("0034", vraag.getZoekCriteria().getGemeenteCode());
        Assert.assertEquals("nor", vraag.getZoekCriteria().getNaamOpenbareRuimte());
        Assert.assertEquals("12", vraag.getZoekCriteria().getHuisnummer());
        Assert.assertEquals("A", vraag.getZoekCriteria().getHuisletter());
        Assert.assertEquals("I", vraag.getZoekCriteria().getHuisnummertoevoeging());
        Assert.assertEquals("1066DG", vraag.getZoekCriteria().getPostcode());
    }

    @Test
    public void testBindingMinBericht() throws IOException, JiBXException {
        final String xml = leesBestand("bevragen_VraagPersonenOpAdresInclusiefBetrokkenheden_MIN.xml");

        //TODO fix xml
        //XmlUtils.valideerOutputTegenSchema(xml, "/xsd/BRP_Bevraging_Berichten.xsd");

        VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);
        BerichtStuurgegevens berichtStuurgegevens = bericht.getBerichtStuurgegevens();
        Assert.assertNotNull(berichtStuurgegevens);
        Assert.assertEquals("app", berichtStuurgegevens.getApplicatie());
        Assert.assertEquals("org", berichtStuurgegevens.getOrganisatie());
        Assert.assertEquals("ref", berichtStuurgegevens.getReferentienummer());
    }

    @Test
    public void testBindingMinNilBericht() throws IOException, JiBXException {
        final String xml = leesBestand("bevragen_VraagPersonenOpAdresInclusiefBetrokkenheden_MIN-nil.xml");
        XmlUtils.valideerOutputTegenSchema(xml, "/xsd/BRP_Bevraging_Berichten.xsd");

        VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);
        BerichtStuurgegevens berichtStuurgegevens = bericht.getBerichtStuurgegevens();
        Assert.assertNotNull(berichtStuurgegevens);
        Assert.assertEquals("app", berichtStuurgegevens.getApplicatie());
        Assert.assertEquals("org", berichtStuurgegevens.getOrganisatie());
        Assert.assertEquals("ref", berichtStuurgegevens.getReferentienummer());
    }

    @Override
    protected String getSchemaBestand() {
        // TODO Auto-generated method stub
        return null;
    }

}
