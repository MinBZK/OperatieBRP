/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding;

import java.io.IOException;

import junit.framework.Assert;
import nl.bzk.brp.business.dto.BerichtStuurgegevens;
import nl.bzk.brp.business.dto.bevraging.KandidaatVaderVraag;
import nl.bzk.brp.business.dto.bevraging.VraagKandidaatVaderBericht;
import nl.bzk.brp.model.binding.AbstractBindingInTest;
import org.jibx.runtime.JiBXException;
import org.junit.Test;

public class VraagKandidaatVaderBerichtBindingTest extends AbstractBindingInTest<VraagKandidaatVaderBericht> {

    @Test
    public void testBindingMaxBericht() throws IOException, JiBXException {
        final String xml = leesBestand("bevragen_VraagKandidaatVader_MAX.xml");
        valideerOutputTegenSchema(xml);

        final VraagKandidaatVaderBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);
        BerichtStuurgegevens berichtStuurgegevens = bericht.getBerichtStuurgegevens();
        Assert.assertNotNull(berichtStuurgegevens);
        Assert.assertEquals("mGBA", berichtStuurgegevens.getOrganisatie());
        Assert.assertEquals("BRP", berichtStuurgegevens.getApplicatie());
        Assert.assertEquals("123", berichtStuurgegevens.getReferentienummer());
        KandidaatVaderVraag vaderVraag = bericht.getVraag();
        Assert.assertNotNull(vaderVraag);
        Assert.assertEquals("123456789", vaderVraag.getZoekCriteria().getBurgerservicenummer().getWaarde());
    }

    @Test
    public void testBindingMinBericht() throws IOException, JiBXException {
        final String xml = leesBestand("bevragen_VraagKandidaatVader_MIN.xml");
        valideerOutputTegenSchema(xml);

        VraagKandidaatVaderBericht vaderBericht = unmarshalObject(xml);
        Assert.assertNotNull(vaderBericht.getBerichtStuurgegevens());
        KandidaatVaderVraag vaderVraag = vaderBericht.getVraag();
        Assert.assertNotNull(vaderVraag);
        Assert.assertEquals("123456789", vaderVraag.getZoekCriteria().getBurgerservicenummer().getWaarde());
        Assert.assertEquals(19830101, vaderVraag.getZoekCriteria().getGeboortedatumKind().intValue());
    }

    @Test
    public void testBindingNilBericht() throws IOException, JiBXException {
        final String xml = leesBestand("bevragen_VraagKandidaatVader_MIN-nil.xml");
        valideerOutputTegenSchema(xml);

        VraagKandidaatVaderBericht vaderBericht = unmarshalObject(xml);
        Assert.assertNotNull(vaderBericht.getBerichtStuurgegevens());
        KandidaatVaderVraag vaderVraag = vaderBericht.getVraag();
        Assert.assertNotNull(vaderVraag);
        Assert.assertEquals("123456789", vaderVraag.getZoekCriteria().getBurgerservicenummer().getWaarde());
        Assert.assertEquals(19830101, vaderVraag.getZoekCriteria().getGeboortedatumKind().intValue());
    }

    @Override
    protected Class<VraagKandidaatVaderBericht> getBindingClass() {
        return VraagKandidaatVaderBericht.class;
    }

    @Override
    protected String getSchemaBestand() {
        return "/xsd/BRP_Bevraging_Berichten.xsd";
    }
}
