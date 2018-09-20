/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bevraging.bijhouding;

import java.io.IOException;
import nl.bzk.brp.binding.AbstractBindingInIntegratieTest;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtZoekcriteriaPersoonGroepBericht;
import nl.bzk.brp.model.bevraging.bijhouding.GeefPersonenOpAdresMetBetrokkenhedenBericht;
import nl.bzk.brp.utils.XmlUtils;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;


public class GeefPersonenOpAdresMetBetrokkenhedenBerichtBindingTest extends
    AbstractBindingInIntegratieTest<GeefPersonenOpAdresMetBetrokkenhedenBericht>
{

    @Override
    protected Class<GeefPersonenOpAdresMetBetrokkenhedenBericht> getBindingClass() {
        return GeefPersonenOpAdresMetBetrokkenhedenBericht.class;
    }

    @Override
    protected String getSchemaBestand() {
        return null;
    }

    @Test
    public void testBindingMaxBericht() throws JiBXException, IOException {
        String xml = leesBestand("bevragen_GeefPersonenOpAdresMetBetrokkenheden_MAX.xml");

        XmlUtils.valideerOutputTegenSchema(xml, getSchemaUtils().getXsdBijhoudingBevragingBerichten());

        GeefPersonenOpAdresMetBetrokkenhedenBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);

        BerichtStuurgegevensGroepBericht stuurgegevens = bericht.getStuurgegevens();

        Assert.assertEquals("stuurgeg1", stuurgegevens.getCommunicatieID());
        Assert.assertEquals("app", stuurgegevens.getZendendeSysteem().getWaarde());
        Assert.assertEquals("000101", stuurgegevens.getZendendePartijCode());
        Assert.assertEquals("12345678-1234-1234-1234-123456789123", stuurgegevens.getReferentienummer().getWaarde());
        Assert.assertNull(stuurgegevens.getCrossReferentienummer());
        Assert.assertNotNull(stuurgegevens.getDatumTijdVerzending());
        Assert.assertNull(stuurgegevens.getDatumTijdOntvangst());

        final BerichtParametersGroepBericht parameters = bericht.getParameters();
        Assert.assertEquals(20140101, parameters.getPeilmomentMaterieelSelectie().getWaarde().intValue());

        BerichtZoekcriteriaPersoonGroepBericht criteria = bericht.getZoekcriteriaPersoon();
        Assert.assertNotNull(criteria);
        Assert.assertEquals(Integer.valueOf(123456789), criteria.getBurgerservicenummer().getWaarde());
        Assert.assertEquals(34, criteria.getGemeenteCode().getWaarde().intValue());
        Assert.assertEquals("nor", criteria.getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals(Integer.valueOf(12), criteria.getHuisnummer().getWaarde());
        Assert.assertEquals("A", criteria.getHuisletter().getWaarde());
        Assert.assertEquals("I", criteria.getHuisnummertoevoeging().getWaarde());
        Assert.assertEquals("1066DG", criteria.getPostcode().getWaarde());
    }

    @Test
    public void testBindingMinBericht() throws IOException, JiBXException {
        final String xml = leesBestand("bevragen_GeefPersonenOpAdresMetBetrokkenheden_MIN.xml");

        XmlUtils.valideerOutputTegenSchema(xml, getSchemaUtils().getXsdBijhoudingBevragingBerichten());

        GeefPersonenOpAdresMetBetrokkenhedenBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);
        BerichtStuurgegevensGroepBericht stuurgegevens = bericht.getStuurgegevens();

        Assert.assertEquals("stuurgeg1", stuurgegevens.getCommunicatieID());
        Assert.assertEquals("app", stuurgegevens.getZendendeSysteem().getWaarde());
        Assert.assertEquals("000101", stuurgegevens.getZendendePartijCode());
        Assert.assertEquals("12345678-1234-1234-1234-123456789123", stuurgegevens.getReferentienummer().getWaarde());
        Assert.assertNull(stuurgegevens.getCrossReferentienummer());
        Assert.assertNotNull(stuurgegevens.getDatumTijdVerzending());
        Assert.assertNull(stuurgegevens.getDatumTijdOntvangst());

        Assert.assertNull(bericht.getParameters());

        BerichtZoekcriteriaPersoonGroepBericht criteria = bericht.getZoekcriteriaPersoon();
        Assert.assertNotNull(criteria);

        Assert.assertNull(criteria.getBurgerservicenummer());
        Assert.assertNull(criteria.getGemeenteCode());
        Assert.assertNull(criteria.getNaamOpenbareRuimte());
        Assert.assertNull(criteria.getHuisnummer());
        Assert.assertNull(criteria.getHuisletter());
        Assert.assertNull(criteria.getHuisnummertoevoeging());
        Assert.assertNull(criteria.getPostcode());
    }

}
