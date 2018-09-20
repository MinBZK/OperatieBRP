/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.model.gedeeld.Land;
import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.gedeeld.Plaats;
import nl.bzk.brp.model.gedeeld.SoortIndicatie;
import nl.bzk.brp.model.gedeeld.SoortRelatie;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonIndicatie;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import nl.bzk.brp.web.bevraging.VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoord;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test voor de binding van een {@link VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoord} instantie, waarbij
 * tevens het antwoord wordt gevalideerd tegen de XSD.
 */
public class VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoordBindingTest extends
    AbstractVraagBerichtBindingUitTest<VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoord>
{

    @Override
    public Class<VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoord> getBindingClass() {
        return VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoord.class;
    }

    @Test
    public void testOutBindingMetLeegResultaat() throws JiBXException {
        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(null);

        final VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoord response =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoord(resultaat);
        String xml = marshalObject(response);
        Assert.assertNotNull(xml);

        String verwachteWaarde = bouwVerwachteAntwoordBericht("20120325143506789");
        Assert.assertEquals(verwachteWaarde, xml);

        valideerOutputTegenSchema(xml);
    }

    @Test
    public void testOutBindingMetMelding() throws JiBXException {
        List<Melding> meldingen = new ArrayList<Melding>();
        meldingen.add(new Melding(SoortMelding.INFO, MeldingCode.ALG0001, "TEST"));

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(meldingen);
        VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoord response =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoord(resultaat);

        String xml = marshalObject(response);
        Assert.assertNotNull(xml);

        String verwachteWaarde = bouwVerwachteAntwoordBericht("20120325143506789", "I", "ALG0001", "TEST", null);
        Assert.assertEquals(verwachteWaarde, xml);

        valideerOutputTegenSchema(xml);
    }

    @Test
    public void testOutBindingMetPersoonInResultaat() throws JiBXException {
        Partij gemeente = new Partij();
        gemeente.setGemeentecode("0010");

        Land nederland = new Land();
        nederland.setLandcode("0031");

        Plaats amsterdam = new Plaats();
        amsterdam.setWoonplaatscode("0020");

        OpvragenPersoonResultaat resultaat = bouwOpvragenPersoonResultaatVoorCompleetPersoonMetRelaties(gemeente,
            nederland, amsterdam, SoortRelatie.GEREGISTREERD_PARTNERSCHAP);

        final VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoord response =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoord(resultaat);

        // Indicaties
        voegIndicatieToeAanPersoon(resultaat.getGevondenPersonen().iterator().next(), SoortIndicatie.VERSTREKKINGSBEPERKING, true);
        voegIndicatieToeAanPersoon(resultaat.getGevondenPersonen().iterator().next(), SoortIndicatie.BEHANDELD_ALS_NEDERLANDER, true);

        String xml = marshalObject(response);
        Assert.assertNotNull(xml);
        // Voor het gemak alle bsn's vervangen.
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "burgerservicenummer", "123456789");

        String verwachteWaarde = bouwVerwachteAntwoordBericht("20120325143506789", null, null, null,
            "vraagPersonenOpAdresInclusiefBetrokkenhedenAntwoordBindingResultaat.xml");
        Assert.assertEquals(verwachteWaarde, xml);

        valideerOutputTegenSchema(xml);
    }

    /**
     * Voegt een {@link PersoonIndicatie} toe aan de opgegeven persoon, waarbij de indicatie die wordt toegevoegd wordt
     * opgebouwd op basis van de opgegeven soort van de indicatie en de opgegeven waarde.
     *
     * @param persoon de persoon aan wie de indicatie wordt toegevoegd.
     * @param soort de soort van de indicatie die moet worden toegevoegd.
     * @param waarde de waarde van de indicatie die moet worden toegevoegd.
     */
    private void voegIndicatieToeAanPersoon(final Persoon persoon, final SoortIndicatie soort, final boolean waarde) {
        PersoonIndicatie persoonIndicatie = new PersoonIndicatie();
        persoonIndicatie.setPersoon(persoon);
        persoonIndicatie.setSoort(soort);
        persoonIndicatie.setWaarde(waarde);
        persoon.voegPersoonIndicatieToe(persoonIndicatie);
    }

    @Override
    public String getBerichtElementNaam() {
        return "bevragen_VraagPersonenOpAdresInclusiefBetrokkenheden_Antwoord";
    }

    @Override
    protected String getSchemaBestand() {
        return "/xsd/BRP_Bevraging_Berichten.xsd";
    }
}
