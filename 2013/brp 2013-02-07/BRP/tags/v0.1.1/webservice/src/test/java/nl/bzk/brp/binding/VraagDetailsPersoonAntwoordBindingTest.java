/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import nl.bzk.brp.web.bevraging.VraagDetailsPersoonAntwoord;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;


public class VraagDetailsPersoonAntwoordBindingTest
    extends AbstractVraagBerichtBindingUitTest<VraagDetailsPersoonAntwoord>
{

    @Override
    public Class<VraagDetailsPersoonAntwoord> getBindingClass() {
        return VraagDetailsPersoonAntwoord.class;
    }

    @Test
    public void testOutBindingMetLeegResultaat() throws JiBXException {
        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(null);

        final VraagDetailsPersoonAntwoord response = new VraagDetailsPersoonAntwoord(resultaat);
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
        VraagDetailsPersoonAntwoord response = new VraagDetailsPersoonAntwoord(resultaat);

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
            nederland, amsterdam, SoortRelatie.HUWELIJK);

        final VraagDetailsPersoonAntwoord response = new VraagDetailsPersoonAntwoord(resultaat);

        String xml = marshalObject(response);
        Assert.assertNotNull(xml);
        //Voor het gemak alle bsn's vervangen.
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "burgerservicenummer", "123456789");

        String verwachteWaarde = bouwVerwachteAntwoordBericht("20120325143506789", null, null, null,
            "vraagDetailsPersoonMetRelatiesAntwoordBindingResultaat.xml");
        Assert.assertEquals(verwachteWaarde, xml);

        valideerOutputTegenSchema(xml);
    }

    @Test
    public void testIndicatieBinding() throws JiBXException, IOException {
        Partij gemeente = new Partij();
        gemeente.setGemeentecode("0010");

        Land nederland = new Land();
        nederland.setLandcode("0031");

        Plaats amsterdam = new Plaats();
        amsterdam.setWoonplaatscode("0020");

        Set<Persoon> gevondenPersonen = new HashSet<Persoon>();
        Persoon opTeVragenPersoon = maakPersoon(1L, gemeente, amsterdam, nederland, "persoon");

        // Indicaties
        voegIndicatieToeAanPersoon(opTeVragenPersoon, SoortIndicatie.VERSTREKKINGSBEPERKING, true);
        voegIndicatieToeAanPersoon(opTeVragenPersoon, SoortIndicatie.ONDER_CURATELE, false);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(null);
        gevondenPersonen.add(opTeVragenPersoon);
        resultaat.setGevondenPersonen(gevondenPersonen);

        final VraagDetailsPersoonAntwoord response = new VraagDetailsPersoonAntwoord(resultaat);

        String xml = marshalObject(response);
        Assert.assertNotNull(xml);
        //Voor het gemak alle bsn's vervangen.
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "burgerservicenummer", "123456789");

        String verwachteWaarde = bouwVerwachteAntwoordBericht("20120325143506789", null, null, null,
            "vraagDetailsPersoonMetIndicatiesAntwoordBindingResultaat.xml");
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
        return "bevragen_VraagDetailsPersoon_Antwoord";
    }

    @Override
    protected String getSchemaBestand() {
        return "/xsd/BRP_Bevraging_Berichten.xsd";
    }
}
