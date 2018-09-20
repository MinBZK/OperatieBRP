/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bevraging.bijhouding;

import nl.bzk.brp.binding.bevraging.AbstractVraagBerichtBindingUitIntegratieTest;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.bevraging.bijhouding.BepaalKandidaatVaderAntwoordBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.predikaat.HuidigeSituatiePredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


public class BepaalKandidaatVaderBerichtAntwoordBindingTest extends
    AbstractVraagBerichtBindingUitIntegratieTest<BepaalKandidaatVaderAntwoordBericht>
{

    @Override
    public Class<BepaalKandidaatVaderAntwoordBericht> getBindingClass() {
        return BepaalKandidaatVaderAntwoordBericht.class;
    }

    @BeforeClass
    public static void beforeClass() {
        XMLUnit.setIgnoreWhitespace(true);
    }

    @Test
    public void testOutBindingMetKandidaatVaderInResultaat() throws Exception {
        // Test een keer met en een keer zonder adressen.
        test(true);
        test(false);
    }

    private void test(final boolean heeftAdressen) throws Exception {
        final BepaalKandidaatVaderAntwoordBericht response = new BepaalKandidaatVaderAntwoordBericht();
        response.setStuurgegevens(maakStuurgegevensVoorAntwoordBericht("12345678-1234-1234-1234-123456789123",
                "12345678-1234-1234-1234-123456789123"));
        response.setResultaat(maakBerichtResultaat(SoortMelding.GEEN));

        final PersoonHisVolledigImpl antwoordPersoon = maakAntwoordPersoon();
        voegKindBetrokkenheidToeVoorAntwoordPersoon(antwoordPersoon);
        voegOuderBetrokkenheidToeVoorAntwoordPersoon(antwoordPersoon);
        voegPartnerBetrokkenheidToeVoorAntwoordPersoon(antwoordPersoon);
        if (!heeftAdressen) {
            antwoordPersoon.getAdressen().clear();
        }
        final PersoonHisVolledigView persoonHisVolledigView =
                new PersoonHisVolledigView(antwoordPersoon, new HuidigeSituatiePredikaat());
        zetObjectSleutels(persoonHisVolledigView);
        response.voegGevondenPersoonToe(persoonHisVolledigView);

        String xml = marshalObject(response);
        Assert.assertNotNull(xml);

        // Vervang alle tijdstipRegistratie waardes met een vaste waarde:
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipRegistratie", "2012-01-01T00:00:00.000+01:00");
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipVerzending", "2012-03-25T14:35:06.789+01:00");

        xml = xml.replaceAll("brp:objectSleutel=\"[0-9]*\"", "brp:objectSleutel=\"X\"");
        xml = xml.replaceAll("brp:voorkomenSleutel=\"[0-9]*\"", "brp:voorkomenSleutel=\"X\"");

        valideerTegenSchema(xml);

        // Check alleen tegen verwachte XML in geval van volledige output
        if (heeftAdressen) {
            final String verwachteWaarde = bouwVerwachteAntwoordBericht("antwoordbericht_bepaalKandidaatVader.xml");

            XMLAssert.assertXMLEqual(verwachteWaarde, xml);
        }
    }

    @Override
    protected String getSchemaBestand() {
        return getSchemaUtils().getXsdBijhoudingBevragingBerichten();
    }
}
