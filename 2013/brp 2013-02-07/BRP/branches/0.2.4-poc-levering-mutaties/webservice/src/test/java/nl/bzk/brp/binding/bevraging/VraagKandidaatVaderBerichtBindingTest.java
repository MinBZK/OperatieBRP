/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bevraging;

import java.io.IOException;

import junit.framework.Assert;
import nl.bzk.brp.binding.AbstractBindingInIntegratieTest;
import nl.bzk.brp.business.dto.bevraging.KandidaatVaderVraag;
import nl.bzk.brp.business.dto.bevraging.VraagKandidaatVaderBericht;
import nl.bzk.brp.business.dto.bevraging.VraagOpties;
import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.groep.logisch.BerichtStuurgegevensGroep;
import org.jibx.runtime.JiBXException;
import org.junit.Test;

public class VraagKandidaatVaderBerichtBindingTest extends AbstractBindingInIntegratieTest<VraagKandidaatVaderBericht> {

    @Test
    public void testBindingMaxBericht() throws IOException, JiBXException {
        final String xml = leesBestand("bevragen_VraagKandidaatVader_MAX.xml");
        valideerTegenSchema(xml);

        final VraagKandidaatVaderBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);
        BerichtStuurgegevensGroep berichtStuurgegevens = bericht.getBerichtStuurgegevensGroep();
        Assert.assertNotNull(berichtStuurgegevens);
        Assert.assertEquals("mGBA", berichtStuurgegevens.getOrganisatie().getWaarde());
        Assert.assertEquals("BRP", berichtStuurgegevens.getApplicatie().getWaarde());
        Assert.assertEquals("123", berichtStuurgegevens.getReferentienummer().getWaarde());
        KandidaatVaderVraag vaderVraag = bericht.getVraag();
        Assert.assertNotNull(vaderVraag);
        Assert.assertEquals("123456789", vaderVraag.getZoekCriteria().getBurgerservicenummer().getWaarde());

        VraagOpties vraagOptie = bericht.getVraag().getVraagOpties();
        Assert.assertEquals(Ja.Ja, vraagOptie.getHistorieFormeel());
        Assert.assertEquals(Ja.Ja, vraagOptie.getHistorieMaterieel());
        Assert.assertEquals("piet", vraagOptie.getAanschouwer().getWaarde());
    }

    @Test
    public void testBindingMinBericht() throws IOException, JiBXException {
        final String xml = leesBestand("bevragen_VraagKandidaatVader_MIN.xml");
        valideerTegenSchema(xml);

        VraagKandidaatVaderBericht vaderBericht = unmarshalObject(xml);
        Assert.assertNotNull(vaderBericht.getBerichtStuurgegevensGroep());
        KandidaatVaderVraag vaderVraag = vaderBericht.getVraag();
        Assert.assertNotNull(vaderVraag);
        Assert.assertEquals("123456789", vaderVraag.getZoekCriteria().getBurgerservicenummer().getWaarde());
        Assert.assertEquals(19830101, vaderVraag.getZoekCriteria().getGeboortedatumKind().intValue());

        VraagOpties vraagOptie = vaderBericht.getVraag().getVraagOpties();
        Assert.assertNull(vraagOptie);
    }

    @Test
    public void testBindingNilBericht() throws IOException, JiBXException {
        final String xml = leesBestand("bevragen_VraagKandidaatVader_MIN-nil.xml");
        valideerTegenSchema(xml);

        VraagKandidaatVaderBericht vaderBericht = unmarshalObject(xml);
        Assert.assertNotNull(vaderBericht.getBerichtStuurgegevensGroep());
        KandidaatVaderVraag vaderVraag = vaderBericht.getVraag();
        Assert.assertNotNull(vaderVraag);
        Assert.assertEquals("123456789", vaderVraag.getZoekCriteria().getBurgerservicenummer().getWaarde());
        Assert.assertEquals(19830101, vaderVraag.getZoekCriteria().getGeboortedatumKind().intValue());

        VraagOpties vraagOptie = vaderBericht.getVraag().getVraagOpties();
        Assert.assertNull(vraagOptie);
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
