/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bevraging;

import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.model.attribuuttype.Gemeentecode;
import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.attribuuttype.Landcode;
import nl.bzk.brp.model.attribuuttype.PlaatsCode;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortIndicatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;
import nl.bzk.brp.web.bevraging.VraagKandidaatVaderAntwoord;
import org.junit.Assert;
import org.junit.Test;

public class VraagKandidaatVaderBerichtAntwoordBindingTest extends
    AbstractVraagBerichtBindingUitIntegratieTest<VraagKandidaatVaderAntwoord>
{

    @Override
    public Class<VraagKandidaatVaderAntwoord> getBindingClass() {
        return VraagKandidaatVaderAntwoord.class;
    }

    @Test
    public void testOutBindingMetKandidaatVaderInResultaat() throws Exception {
        Partij gemeente = new Partij();
        gemeente.setGemeentecode(new Gemeentecode((short) 10));

        Land nederland = new Land();
        nederland.setCode(new Landcode((short) 31));

        Plaats amsterdam = new Plaats();
        amsterdam.setCode(new PlaatsCode((short) 20));

        OpvragenPersoonResultaat resultaat = bouwOpvragenPersoonResultaatVoorCompleetPersoonMetRelaties(gemeente,
            nederland, amsterdam, SoortRelatie.GEREGISTREERD_PARTNERSCHAP);

        final VraagKandidaatVaderAntwoord response =
            new VraagKandidaatVaderAntwoord(resultaat);

        // Indicaties
        voegIndicatieToeAanPersoon(resultaat.getGevondenPersonen().iterator().next(),
            SoortIndicatie.VERSTREKKINGSBEPERKING, Ja.Ja);
        voegIndicatieToeAanPersoon(resultaat.getGevondenPersonen().iterator().next(),
            SoortIndicatie.BEHANDELD_ALS_NEDERLANDER, Ja.Ja);

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

    @Override
    protected String getSchemaBestand() {
        return "/xsd/BRP_Bevraging_Berichten.xsd";
    }
}
