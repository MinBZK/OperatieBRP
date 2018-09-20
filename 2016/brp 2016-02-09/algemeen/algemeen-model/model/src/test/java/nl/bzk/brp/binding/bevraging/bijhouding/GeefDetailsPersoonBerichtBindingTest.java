/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bevraging.bijhouding;

import java.io.IOException;
import nl.bzk.brp.binding.AbstractBindingInIntegratieTest;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Historievorm;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtZoekcriteriaPersoonGroepBericht;
import nl.bzk.brp.model.bevraging.bijhouding.GeefDetailsPersoonBericht;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;


public class GeefDetailsPersoonBerichtBindingTest extends AbstractBindingInIntegratieTest<GeefDetailsPersoonBericht> {

    @Override
    public Class<GeefDetailsPersoonBericht> getBindingClass() {
        return GeefDetailsPersoonBericht.class;
    }

    @Test
    public void testOpvragenPersoonMaxBericht() throws JiBXException, IOException {
        String xml = leesBestand("bevragen_GeefDetailsPersoon_MAX.xml");
        valideerTegenSchema(xml);

        GeefDetailsPersoonBericht bericht = unmarshalObject(xml);
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
        Assert.assertEquals(20140101, parameters.getPeilmomentMaterieelResultaat().getWaarde().intValue());
        Assert.assertEquals("Wed Jan 01 12:00:00 CET 2014", parameters.getPeilmomentFormeelResultaat().getWaarde()
                .toString());
        Assert.assertEquals(Historievorm.MATERIEEL_FORMEEL, parameters.getHistorievorm().getWaarde());

        BerichtZoekcriteriaPersoonGroepBericht criteria = bericht.getZoekcriteriaPersoon();
        Assert.assertEquals("comid.criteria", criteria.getCommunicatieID());
        Assert.assertNotNull(criteria);
        Assert.assertEquals(Integer.valueOf("123456789"), criteria.getBurgerservicenummer().getWaarde());
        Assert.assertEquals("111222333", criteria.getObjectSleutel().getWaarde());
    }

    @Test
    public void testOpvragenPersoonDetailsMinBericht() throws IOException, JiBXException {
        String xml = leesBestand("bevragen_GeefDetailsPersoon_MIN.xml");
        valideerTegenSchema(xml);

        GeefDetailsPersoonBericht bericht = unmarshalObject(xml);

        BerichtStuurgegevensGroepBericht stuurgegevens = bericht.getStuurgegevens();

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
        Assert.assertNull(criteria.getObjectSleutel());
    }

    @Override
    protected String getSchemaBestand() {
        return getSchemaUtils().getXsdBijhoudingBevragingBerichten();
    }
}
