/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bevraging.bijhouding;

import java.io.IOException;
import nl.bzk.brp.binding.AbstractBindingInIntegratieTest;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtZoekcriteriaPersoonGroepBericht;
import nl.bzk.brp.model.bevraging.bijhouding.BepaalKandidaatVaderBericht;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;


public class BepaalKandidaatVaderBerichtBindingTest extends
    AbstractBindingInIntegratieTest<BepaalKandidaatVaderBericht>
{

    @Test
    public void testBindingMaxBericht() throws IOException, JiBXException {
        final String xml = leesBestand("bevragen_BepaalKandidaatVader_MAX.xml");
        valideerTegenSchema(xml);

        final BepaalKandidaatVaderBericht bericht = unmarshalObject(xml);

        Assert.assertNotNull(bericht);
        BerichtStuurgegevensGroepBericht stuurgegevens = bericht.getStuurgegevens();

        Assert.assertEquals("stuurgeg1", stuurgegevens.getCommunicatieID());
        Assert.assertEquals("app", stuurgegevens.getZendendeSysteem().getWaarde());
        Assert.assertEquals("000101", stuurgegevens.getZendendePartijCode());
        Assert.assertEquals("12345678-1234-1234-1234-123456789123", stuurgegevens.getReferentienummer().getWaarde());
        Assert.assertNull(stuurgegevens.getCrossReferentienummer());
        Assert.assertNotNull(stuurgegevens.getDatumTijdVerzending());
        Assert.assertNull(stuurgegevens.getDatumTijdOntvangst());

        BerichtZoekcriteriaPersoonGroepBericht criteria = bericht.getZoekcriteriaPersoon();
        Assert.assertNotNull(criteria);
        Assert.assertEquals("comid.criteria", criteria.getCommunicatieID());
        Assert.assertEquals(Integer.valueOf(123456789), criteria.getBurgerservicenummer().getWaarde());
        Assert.assertEquals(Integer.valueOf(19830101), criteria.getGeboortedatumKind().getWaarde());
    }

    @Test
    public void testBindingMinBericht() throws IOException, JiBXException {
        final String xml = leesBestand("bevragen_BepaalKandidaatVader_MIN.xml");
        valideerTegenSchema(xml);

        BepaalKandidaatVaderBericht vaderBericht = unmarshalObject(xml);
        Assert.assertNotNull(vaderBericht.getStuurgegevens());
        Assert.assertEquals("stuurgeg1", vaderBericht.getStuurgegevens().getCommunicatieID());

        Assert.assertNull(vaderBericht.getParameters());

        BerichtZoekcriteriaPersoonGroepBericht criteria = vaderBericht.getZoekcriteriaPersoon();
        Assert.assertEquals("comid.criteria", criteria.getCommunicatieID());
        Assert.assertNotNull(criteria);
        Assert.assertEquals(Integer.valueOf(123456789), criteria.getBurgerservicenummer().getWaarde());
        Assert.assertEquals(Integer.valueOf(19830101), criteria.getGeboortedatumKind().getWaarde());

        Assert.assertNull(vaderBericht.getParameters());
    }

    @Override
    protected Class<BepaalKandidaatVaderBericht> getBindingClass() {
        return BepaalKandidaatVaderBericht.class;
    }

    @Override
    protected String getSchemaBestand() {
        return getSchemaUtils().getXsdBijhoudingBevragingBerichten();
    }
}
