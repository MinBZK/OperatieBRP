/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding;

import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.model.attribuuttype.GemeenteCode;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.attribuuttype.LandCode;
import nl.bzk.brp.model.attribuuttype.PlaatsCode;
import nl.bzk.brp.model.objecttype.statisch.Land;
import nl.bzk.brp.model.objecttype.statisch.Partij;
import nl.bzk.brp.model.objecttype.statisch.Plaats;
import nl.bzk.brp.model.objecttype.statisch.SoortIndicatie;
import nl.bzk.brp.model.objecttype.statisch.SoortRelatie;
import nl.bzk.brp.web.bevraging.VraagKandidaatVaderAntwoord;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

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
        ReflectionTestUtils.setField(gemeente, "gemeenteCode", new GemeenteCode("0010"));

        Land nederland = new Land();
        ReflectionTestUtils.setField(nederland, "landCode", new LandCode("0031"));

        Plaats amsterdam = new Plaats();
        ReflectionTestUtils.setField(amsterdam, "code", new PlaatsCode("0020"));

        OpvragenPersoonResultaat resultaat = bouwOpvragenPersoonResultaatVoorCompleetPersoonMetRelaties(gemeente,
            nederland, amsterdam, SoortRelatie.GEREGISTREERD_PARTNERSCHAP);

        final VraagKandidaatVaderAntwoord response =
            new VraagKandidaatVaderAntwoord(resultaat);

        // Indicaties
        voegIndicatieToeAanPersoon(resultaat.getGevondenPersonen().iterator().next(),
            SoortIndicatie.VERSTREKKINGSBEPERKING, JaNee.Ja);
        voegIndicatieToeAanPersoon(resultaat.getGevondenPersonen().iterator().next(),
            SoortIndicatie.BEHANDELD_ALS_NEDERLANDER, JaNee.Ja);

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
