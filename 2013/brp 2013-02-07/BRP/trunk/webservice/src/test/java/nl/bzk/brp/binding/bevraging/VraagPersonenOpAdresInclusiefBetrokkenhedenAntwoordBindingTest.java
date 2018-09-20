/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bevraging;

import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.*;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.*;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.web.AntwoordBerichtFactory;
import nl.bzk.brp.web.AntwoordBerichtFactoryImpl;
import nl.bzk.brp.web.bevraging.VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoordBericht;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


/**
 * Unit test voor de binding van een {@link nl.bzk.brp.web.bevraging.VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoordBericht} instantie, waarbij
 * tevens het antwoord wordt gevalideerd tegen de XSD.
 */
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
                        bouwIngaandBericht(SoortBericht.A_L_G_GEEF_BEWONERS_OP_ADRES_INCLUSIEF_BETROKKENHEDEN_VI),
                        resultaat);
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
                        bouwIngaandBericht(SoortBericht.A_L_G_GEEF_BEWONERS_OP_ADRES_INCLUSIEF_BETROKKENHEDEN_VI),
                        resultaat);

        String xml = marshalObject(response);
        Assert.assertNotNull(xml);

        String verwachteWaarde = bouwVerwachteAntwoordBericht("20120325143506789", "I", "ALG0001", "TEST", null);
        Assert.assertEquals(verwachteWaarde, xml);

        valideerTegenSchema(xml);
    }

    @Test
    public void testOutBindingMetPersoonInResultaat() throws JiBXException, ParseException {
        Partij gemeente =
                new TestPartij(null, null, new GemeenteCode((short) 10), null, null, null, null, null, StatusHistorie.A,
                        StatusHistorie.A);
        Land nederland = new TestLand(new Landcode((short) 31), null, null, null, null);
        Plaats amsterdam = new TestPlaats(new Woonplaatscode((short) 20), null, null, null);

        OpvragenPersoonResultaat resultaat = bouwOpvragenPersoonResultaatVoorCompleetPersoonMetRelaties(gemeente,
                nederland, amsterdam, SoortRelatie.GEREGISTREERD_PARTNERSCHAP);

        final VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoordBericht response =
                (VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoordBericht) antwoordBerichtFactory.bouwAntwoordBericht(
                        bouwIngaandBericht(SoortBericht.A_L_G_GEEF_BEWONERS_OP_ADRES_INCLUSIEF_BETROKKENHEDEN_VI),
                        resultaat);

        // Indicaties
        voegIndicatieToeAanPersoon(resultaat.getGevondenPersonen().iterator().next(),
                SoortIndicatie.INDICATIE_VERSTREKKINGSBEPERKING, Ja.J);
        voegIndicatieToeAanPersoon(resultaat.getGevondenPersonen().iterator().next(),
                SoortIndicatie.INDICATIE_BEHANDELD_ALS_NEDERLANDER, Ja.J);

        String xml = marshalObject(response);
        Assert.assertNotNull(xml);
        // Voor het gemak alle bsn's vervangen.
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "burgerservicenummer", "123456789");

        System.out.print(xml);
        valideerTegenSchema(xml);

        String verwachteWaarde = bouwVerwachteAntwoordBericht("20120325143506789", null, null, null,
                "vraagPersonenOpAdresInclusiefBetrokkenhedenAntwoordBindingResultaat.xml");
        Assert.assertEquals(verwachteWaarde, xml);


    }

    @Override
    public String getBerichtElementNaam() {
        return "ALG_GeefPersonenOpAdresMetBetrokkenheden_ViR";
    }

}
