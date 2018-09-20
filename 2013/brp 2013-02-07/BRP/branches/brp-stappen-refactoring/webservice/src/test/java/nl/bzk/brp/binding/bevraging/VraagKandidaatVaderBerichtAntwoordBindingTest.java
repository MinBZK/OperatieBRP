/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bevraging;

import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Landcode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Woonplaatscode;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Land;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.web.AntwoordBerichtFactory;
import nl.bzk.brp.web.AntwoordBerichtFactoryImpl;
import nl.bzk.brp.web.bevraging.VraagKandidaatVaderAntwoordBericht;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

@Ignore
public class VraagKandidaatVaderBerichtAntwoordBindingTest extends
    AbstractVraagBerichtBindingUitIntegratieTest<VraagKandidaatVaderAntwoordBericht>
{

    private AntwoordBerichtFactory antwoordBerichtFactory;

    @Before
    public void init() {
        antwoordBerichtFactory = new AntwoordBerichtFactoryImpl();
    }

    @Override
    public Class<VraagKandidaatVaderAntwoordBericht> getBindingClass() {
        return VraagKandidaatVaderAntwoordBericht.class;
    }

    @Test
    public void testOutBindingMetKandidaatVaderInResultaat() throws Exception {
        Partij gemeente = Mockito.mock(Partij.class);
        Mockito.when(gemeente.getCode()).thenReturn(new GemeenteCode("0010"));

        Land nederland = Mockito.mock(Land.class);
        Mockito.when(nederland.getCode()).thenReturn(new Landcode("0031"));

        Plaats amsterdam = Mockito.mock(Plaats.class);
        Mockito.when(amsterdam.getCode()).thenReturn(new Woonplaatscode("0020"));

        OpvragenPersoonResultaat resultaat = bouwOpvragenPersoonResultaatVoorCompleetPersoonMetRelaties(gemeente,
            nederland, amsterdam, SoortRelatie.GEREGISTREERD_PARTNERSCHAP);

        final VraagKandidaatVaderAntwoordBericht response =
            (VraagKandidaatVaderAntwoordBericht) antwoordBerichtFactory.bouwAntwoordBericht(
                bouwIngaandBericht(SoortBericht.A_L_G_BEPAAL_KANDIDAAT_VADER_VI), resultaat);

        // Indicaties
        voegIndicatieToeAanPersoon(resultaat.getGevondenPersonen().iterator().next(),
            SoortIndicatie.INDICATIE_VERSTREKKINGSBEPERKING, Ja.J);
        voegIndicatieToeAanPersoon(resultaat.getGevondenPersonen().iterator().next(),
            SoortIndicatie.INDICATIE_BEHANDELD_ALS_NEDERLANDER, Ja.J);

        String xml = marshalObject(response);
        Assert.assertNotNull(xml);
        // Voor het gemak alle bsn's vervangen.
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "burgerservicenummer", "123456789");

        String verwachteWaarde = bouwVerwachteAntwoordBericht("20120325143506789", null, null, null,
            "vraagKandiidaatVaderAntwoordBindingResultaat.xml");
        Assert.assertEquals(verwachteWaarde, xml);

        valideerTegenSchema(xml);
    }

    @Override
    public String getBerichtElementNaam() {
        return "bevragen_VraagKandidaatVader_Antwoord";
    }

}
