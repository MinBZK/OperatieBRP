/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bevraging;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Landcode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Woonplaatscode;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Land;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.web.AntwoordBerichtFactory;
import nl.bzk.brp.web.AntwoordBerichtFactoryImpl;
import nl.bzk.brp.web.bevraging.VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoordBericht;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;


/**
 * Unit test voor de binding van een {@link nl.bzk.brp.web.bevraging.VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoordBericht} instantie, waarbij
 * tevens het antwoord wordt gevalideerd tegen de XSD.
 */
@Ignore
public class VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoordBindingTest extends
    AbstractVraagBerichtBindingUitIntegratieTest<VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoordBericht>
{

    private AntwoordBerichtFactory antwoordBerichtFactory;

    @Before
    public void init() {
        antwoordBerichtFactory = new AntwoordBerichtFactoryImpl();
    }

    @Override
    public Class<VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoordBericht> getBindingClass() {
        return VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoordBericht.class;
    }

    @Test
    public void testOutBindingMetLeegResultaat() throws JiBXException {
        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(null);

        final VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoordBericht response =
            (VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoordBericht) antwoordBerichtFactory.bouwAntwoordBericht(
                bouwIngaandBericht(SoortBericht.A_L_G_GEEF_BEWONERS_OP_ADRES_INCLUSIEF_BETROKKENHEDEN_VI), resultaat);
        String xml = marshalObject(response);
        Assert.assertNotNull(xml);

        String verwachteWaarde = bouwVerwachteAntwoordBericht("20120325143506789");
        Assert.assertEquals(verwachteWaarde, xml);

        valideerTegenSchema(xml);
    }

    @Test
    public void testOutBindingMetMelding() throws JiBXException {
        List<Melding> meldingen = new ArrayList<Melding>();
        meldingen.add(new Melding(SoortMelding.INFORMATIE, MeldingCode.ALG0001, "TEST"));

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(meldingen);
        final VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoordBericht response =
            (VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoordBericht) antwoordBerichtFactory.bouwAntwoordBericht(
                bouwIngaandBericht(SoortBericht.A_L_G_GEEF_BEWONERS_OP_ADRES_INCLUSIEF_BETROKKENHEDEN_VI), resultaat);

        String xml = marshalObject(response);
        Assert.assertNotNull(xml);

        String verwachteWaarde = bouwVerwachteAntwoordBericht("20120325143506789", "I", "ALG0001", "TEST", null);
        Assert.assertEquals(verwachteWaarde, xml);

        valideerTegenSchema(xml);
    }

    @Test
    public void testOutBindingMetPersoonInResultaat() throws JiBXException, ParseException {
        Partij gemeente = Mockito.mock(Partij.class);
        Mockito.when(gemeente.getCode()).thenReturn(new GemeenteCode("0010"));

        Land nederland = Mockito.mock(Land.class);
        Mockito.when(nederland.getCode()).thenReturn(new Landcode("0031"));

        Plaats amsterdam = Mockito.mock(Plaats.class);
        Mockito.when(amsterdam.getCode()).thenReturn(new Woonplaatscode("0020"));

        OpvragenPersoonResultaat resultaat = bouwOpvragenPersoonResultaatVoorCompleetPersoonMetRelaties(gemeente,
            nederland, amsterdam, SoortRelatie.GEREGISTREERD_PARTNERSCHAP);

        final VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoordBericht response =
            (VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoordBericht) antwoordBerichtFactory.bouwAntwoordBericht(
                bouwIngaandBericht(SoortBericht.A_L_G_GEEF_BEWONERS_OP_ADRES_INCLUSIEF_BETROKKENHEDEN_VI), resultaat);

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
            "vraagPersonenOpAdresInclusiefBetrokkenhedenAntwoordBindingResultaat.xml");
        Assert.assertEquals(verwachteWaarde, xml);

        valideerTegenSchema(xml);
    }

    @Override
    public String getBerichtElementNaam() {
        return "bevragen_VraagPersonenOpAdresInclusiefBetrokkenheden_Antwoord";
    }

}
