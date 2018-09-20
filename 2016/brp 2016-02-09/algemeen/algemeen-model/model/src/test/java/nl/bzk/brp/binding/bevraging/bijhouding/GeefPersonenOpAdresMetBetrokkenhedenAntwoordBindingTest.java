/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bevraging.bijhouding;

import java.io.IOException;
import java.text.ParseException;
import nl.bzk.brp.binding.bevraging.AbstractVraagBerichtBindingUitIntegratieTest;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.bevraging.bijhouding.GeefPersonenOpAdresMetBetrokkenhedenAntwoordBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.predikaat.HuidigeSituatiePredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.BetrokkenheidHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.RelatieHisVolledigView;
import org.custommonkey.xmlunit.XMLAssert;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;


/**
 * Unit test voor de binding van een
 * {@link nl.bzk.brp.model.bevraging.bijhouding.GeefPersonenOpAdresMetBetrokkenhedenAntwoordBericht} instantie, waarbij
 * tevens het antwoord wordt gevalideerd tegen de XSD.
 */
public class GeefPersonenOpAdresMetBetrokkenhedenAntwoordBindingTest extends
    AbstractVraagBerichtBindingUitIntegratieTest<GeefPersonenOpAdresMetBetrokkenhedenAntwoordBericht>
{

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public Class<GeefPersonenOpAdresMetBetrokkenhedenAntwoordBericht> getBindingClass() {
        return GeefPersonenOpAdresMetBetrokkenhedenAntwoordBericht.class;
    }

    @Test
    public void testOutBindingMetPersoonInResultaat() throws JiBXException, ParseException, IOException, SAXException {
        final GeefPersonenOpAdresMetBetrokkenhedenAntwoordBericht response =
            new GeefPersonenOpAdresMetBetrokkenhedenAntwoordBericht();
        response.setStuurgegevens(maakStuurgegevensVoorAntwoordBericht("12345678-1234-1234-1234-123456789123",
            "12345678-1234-1234-1234-123456789123"));
        response.setResultaat(maakBerichtResultaat(SoortMelding.GEEN));

        final PersoonHisVolledigImpl antwoordPersoon = maakAntwoordPersoon();
        voegKindBetrokkenheidToeVoorAntwoordPersoon(antwoordPersoon);
        voegOuderBetrokkenheidToeVoorAntwoordPersoon(antwoordPersoon);
        voegPartnerBetrokkenheidToeVoorAntwoordPersoon(antwoordPersoon);
        final PersoonHisVolledigView persoonHisVolledigView =
            new PersoonHisVolledigView(antwoordPersoon, new HuidigeSituatiePredikaat());
        zetObjectSleutels(persoonHisVolledigView);

        for (BetrokkenheidHisVolledigView betrokkenheidHisVolledigView : persoonHisVolledigView.getBetrokkenheden()) {
            ((RelatieHisVolledigView) betrokkenheidHisVolledigView.getRelatie()).setMagLeveren(true);
        }

        response.voegGevondenPersoonToe(persoonHisVolledigView);

        String xml = marshalObject(response);
        Assert.assertNotNull(xml);

        // Vervang alle tijdstipRegistratie waardes met een vaste waarde:
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipRegistratie", "2012-01-01T00:00:00.000+01:00");
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipVerzending", "2012-03-25T14:35:06.789+01:00");
        // Objectsleutels zijn random, dus even het response aanpassen:
        xml = xml.replaceAll("brp:objectSleutel=\"[0-9]*\"", "brp:objectSleutel=\"X\"");
        xml = xml.replaceAll("brp:voorkomenSleutel=\"[0-9]*\"", "brp:voorkomenSleutel=\"X\"");

        valideerTegenSchema(xml);

        final String verwachteWaarde =
                bouwVerwachteAntwoordBericht("antwoordbericht_geefPersonenOpAdresMetBetrokkenheden.xml");

        LOGGER.info(xml);
        LOGGER.info(verwachteWaarde);

        XMLAssert.assertXMLEqual(verwachteWaarde, xml);
    }

    @Override
    protected String getSchemaBestand() {
        return getSchemaUtils().getXsdBijhoudingBevragingBerichten();
    }
}
