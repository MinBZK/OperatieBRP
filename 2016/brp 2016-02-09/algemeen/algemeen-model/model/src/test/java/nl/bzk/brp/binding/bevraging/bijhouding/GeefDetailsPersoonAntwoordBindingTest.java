/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bevraging.bijhouding;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;

import java.io.IOException;
import nl.bzk.brp.binding.bevraging.AbstractVraagBerichtBindingUitIntegratieTest;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.bevraging.bijhouding.GeefDetailsPersoonAntwoordBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.predikaat.HuidigeSituatiePredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.BetrokkenheidHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.RelatieHisVolledigView;
import nl.bzk.brp.model.validatie.Melding;
import org.custommonkey.xmlunit.XMLUnit;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;


public class GeefDetailsPersoonAntwoordBindingTest extends
    AbstractVraagBerichtBindingUitIntegratieTest<GeefDetailsPersoonAntwoordBericht>
{



    @Override
    public Class<GeefDetailsPersoonAntwoordBericht> getBindingClass() {
        return GeefDetailsPersoonAntwoordBericht.class;
    }

    @BeforeClass
    public static void beforeClass() {
        XMLUnit.setIgnoreWhitespace(true);
    }

    @Test
    public void testOutBindingMetLeegResultaat() throws JiBXException, IOException, SAXException {
        final GeefDetailsPersoonAntwoordBericht response = new GeefDetailsPersoonAntwoordBericht();
        response.setStuurgegevens(maakStuurgegevensVoorAntwoordBericht("12345678-1234-1234-1234-123456789123",
            "12345678-1234-1234-1234-123456789123"));
        response.setResultaat(maakBerichtResultaat(SoortMelding.GEEN));
        String xml = marshalObject(response);
        Assert.assertNotNull(xml);

        valideerTegenSchema(xml);

        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipVerzending", "2012-03-25T14:35:06.789+01:00");
        final String verwachteWaarde = bouwVerwachteAntwoordBericht("antwoordbericht_geefDetailsPersoon_leeg_resultaat.xml");
        assertXMLEqual(verwachteWaarde, xml);
    }

    @Test
    public void testOutBindingMetMelding() throws JiBXException, IOException, SAXException {
        final Melding melding = new Melding(SoortMelding.INFORMATIE, Regel.ALG0001, "TEST", null, null);

        final GeefDetailsPersoonAntwoordBericht response = new GeefDetailsPersoonAntwoordBericht();
        response.setStuurgegevens(maakStuurgegevensVoorAntwoordBericht("12345678-1234-1234-1234-123456789123",
            "12345678-1234-1234-1234-123456789123"));
        response.setResultaat(maakBerichtResultaat(SoortMelding.INFORMATIE));

        response.setMeldingen(maakBerichtMeldingenBericht(melding));
        String xml = marshalObject(response);
        Assert.assertNotNull(xml);

        valideerTegenSchema(xml);

        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipVerzending", "2012-03-25T14:35:06.789+01:00");

        final String verwachteWaarde = bouwVerwachteAntwoordBericht("antwoordbericht_geefDetailsPersoon_met_melding.xml");
        assertXMLEqual(verwachteWaarde, xml);

        valideerTegenSchema(xml);
    }

    @Test
    public void testOutBindingMetPersoonInResultaat() throws Exception {
        final GeefDetailsPersoonAntwoordBericht response = new GeefDetailsPersoonAntwoordBericht();
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
        for (BetrokkenheidHisVolledigView betrokkenheidHisVolledigView: persoonHisVolledigView.getBetrokkenheden()) {
            RelatieHisVolledigView relatie = (RelatieHisVolledigView) betrokkenheidHisVolledigView.getRelatie();
            relatie.setMagLeveren(true);
//            ReflectionTestUtils.setField(relatie, "iD", 5678);
        }
        response.voegGevondenPersoonToe(persoonHisVolledigView);

        String xml = marshalObject(response);
        Assert.assertNotNull(xml);

        // Vervang alle tijdstipRegistratie en technischeSleutel waardes met een vaste waarde:
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipRegistratie", "2012-01-01T00:00:00.000+01:00");
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "datumTijdEindeVolgen", "2012-01-01T00:00:00.000+01:00");
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipVerzending", "2012-03-25T14:35:06.789+01:00");
        // Objectsleutels en voorkomensleutels zijn random, dus even het response aanpassen:
        xml = xml.replaceAll("brp:objectSleutel=\"[0-9]*\"", "brp:objectSleutel=\"X\"");
        xml = xml.replaceAll("brp:voorkomenSleutel=\"[0-9]*\"", "brp:voorkomenSleutel=\"X\"");

        valideerTegenSchema(xml);

        final String verwachteWaarde = bouwVerwachteAntwoordBericht("antwoordbericht_geefDetailsPersoon.xml");

        assertXMLEqual(verwachteWaarde, xml);
    }

    @Override
    protected String getSchemaBestand() {
        return getSchemaUtils().getXsdBijhoudingBevragingBerichten();
    }
}
