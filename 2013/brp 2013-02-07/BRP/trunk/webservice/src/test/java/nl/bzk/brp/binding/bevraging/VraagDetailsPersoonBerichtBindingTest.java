/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bevraging;

import java.io.IOException;

import nl.bzk.brp.binding.AbstractBindingInIntegratieTest;
import nl.bzk.brp.business.dto.bevraging.DetailsPersoonVraag;
import nl.bzk.brp.business.dto.bevraging.VraagDetailsPersoonBericht;
import nl.bzk.brp.model.logisch.ber.BerichtStuurgegevensGroep;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;

public class VraagDetailsPersoonBerichtBindingTest extends AbstractBindingInIntegratieTest<VraagDetailsPersoonBericht> {

    @Override
    public Class<VraagDetailsPersoonBericht> getBindingClass() {
        return VraagDetailsPersoonBericht.class;
    }

    @Test
    public void testOpvragenPersoonMaxBericht() throws JiBXException, IOException {
        String xml = leesBestand("bevragen_VraagDetailsPersoon_MAX.xml");
        valideerTegenSchema(xml);

        VraagDetailsPersoonBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);

        BerichtStuurgegevensGroep stuurgegevens = bericht.getStuurgegevens();
        Assert.assertEquals("app", stuurgegevens.getApplicatie().getWaarde());
        Assert.assertEquals("org", stuurgegevens.getOrganisatie().getWaarde());
        Assert.assertEquals("ref", stuurgegevens.getReferentienummer().getWaarde());
        Assert.assertNull(stuurgegevens.getCrossReferentienummer());

        DetailsPersoonVraag vraag = bericht.getVraag();
        Assert.assertNotNull(vraag);
        Assert.assertEquals(Integer.valueOf("111222333"), vraag.getZoekCriteria().getBurgerservicenummer().getWaarde());
    }

    @Test
    public void testOpvragenPersoonDetailsMinBericht() throws IOException, JiBXException {
        String xml = leesBestand("bevragen_VraagDetailsPersoon_MIN.xml");
        valideerTegenSchema(xml);

        VraagDetailsPersoonBericht bericht = unmarshalObject(xml);

        BerichtStuurgegevensGroep stuurgegevens = bericht.getStuurgegevens();
        Assert.assertEquals("app", stuurgegevens.getApplicatie().getWaarde());
        Assert.assertEquals("org", stuurgegevens.getOrganisatie().getWaarde());
        Assert.assertEquals("ref", stuurgegevens.getReferentienummer().getWaarde());
        Assert.assertNull(stuurgegevens.getCrossReferentienummer());

        Assert.assertNotNull(bericht.getVraag());
        Assert.assertEquals(Integer.valueOf("123456789"), bericht.getVraag().getZoekCriteria().getBurgerservicenummer().getWaarde());
    }


    @Override
    protected String getSchemaBestand() {
        return "/xsd/BRP0100_Bevraging_Berichten.xsd";
    }
}
