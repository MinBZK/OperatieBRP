/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.synchronisatie;

import java.util.Collections;

import nl.bzk.brp.binding.AbstractBindingUitIntegratieTest;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Bijhoudingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.model.bericht.ber.BerichtMeldingBericht;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatiePersoonAntwoordBericht;
import nl.bzk.brp.utils.TestDataBouwer;

import org.jibx.runtime.JiBXException;
import org.junit.Test;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;

import static org.junit.Assert.assertNotNull;


public class GeefSynchronisatiePersoonAntwoordBerichtBindingTest extends
        AbstractBindingUitIntegratieTest<GeefSynchronisatiePersoonAntwoordBericht>
{

    public static final Logger LOGGER = LoggerFactory
            .getLogger();

    @Override
    protected Class<GeefSynchronisatiePersoonAntwoordBericht> getBindingClass() {
        return GeefSynchronisatiePersoonAntwoordBericht.class;
    }

    @Override
    protected String getSchemaBestand() {
        return "brp0200_lvgSynchronisatie_Berichten.xsd";
    }

    @Test
    public void testBinding() throws JiBXException {
        GeefSynchronisatiePersoonAntwoordBericht bericht = new GeefSynchronisatiePersoonAntwoordBericht();

        bericht.setStuurgegevens(TestDataBouwer.getTestBerichtStuurgegevensGroepBericht());
        bericht.setResultaat(maakResultaatVoorAntwoordBericht(SoortMelding.GEEN, Verwerkingsresultaat.GESLAAGD,
                Bijhoudingsresultaat.VERWERKT));
        bericht.setMeldingen(Collections.<BerichtMeldingBericht>emptyList());

        String xml = marshalObject(bericht);

        LOGGER.info(xml);

        assertNotNull(xml);
        valideerTegenSchema(xml);
    }
}
