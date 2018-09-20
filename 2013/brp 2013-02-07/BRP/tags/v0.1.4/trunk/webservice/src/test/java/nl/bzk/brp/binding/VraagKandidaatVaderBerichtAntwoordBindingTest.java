/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding;

import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.model.gedeeld.Land;
import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.gedeeld.Plaats;
import nl.bzk.brp.model.gedeeld.SoortIndicatie;
import nl.bzk.brp.model.gedeeld.SoortRelatie;
import nl.bzk.brp.web.bevraging.VraagKandidaatVaderAntwoord;
import org.junit.Assert;
import org.junit.Test;

public class VraagKandidaatVaderBerichtAntwoordBindingTest extends
    AbstractVraagBerichtBindingUitTest<VraagKandidaatVaderAntwoord>
{

    @Override
    public Class<VraagKandidaatVaderAntwoord> getBindingClass() {
        return VraagKandidaatVaderAntwoord.class;
    }

    @Test
    public void testOutBindingMetKandidaatVaderInResultaat() throws Exception {
        Partij gemeente = new Partij();
        gemeente.setGemeentecode("0010");

        Land nederland = new Land();
        nederland.setLandcode("0031");

        Plaats amsterdam = new Plaats();
        amsterdam.setWoonplaatscode("0020");

        OpvragenPersoonResultaat resultaat = bouwOpvragenPersoonResultaatVoorCompleetPersoonMetRelaties(gemeente,
            nederland, amsterdam, SoortRelatie.GEREGISTREERD_PARTNERSCHAP);

        final VraagKandidaatVaderAntwoord response =
            new VraagKandidaatVaderAntwoord(resultaat);

        // Indicaties
        voegIndicatieToeAanPersoon(resultaat.getGevondenPersonen().iterator().next(),
            SoortIndicatie.VERSTREKKINGSBEPERKING, true);
        voegIndicatieToeAanPersoon(resultaat.getGevondenPersonen().iterator().next(),
            SoortIndicatie.BEHANDELD_ALS_NEDERLANDER, true);

        String xml = marshalObject(response);
        Assert.assertNotNull(xml);
        // Voor het gemak alle bsn's vervangen.
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "burgerservicenummer", "123456789");

        String verwachteWaarde = bouwVerwachteAntwoordBericht("20120325143506789", null, null, null,
            "vraagKandiidaatVaderAntwoordBindingResultaat.xml");
        Assert.assertEquals(verwachteWaarde, xml);

        valideerOutputTegenSchema(xml);
    }

    @Override
    public String getBerichtElementNaam() {
        return "bevragen_VraagKandidaatVader_Antwoord";
    }

    @Override
    protected String getSchemaBestand() {
        return "/xsd/BRP_Bevraging_Berichten.xsd";
    }
}
