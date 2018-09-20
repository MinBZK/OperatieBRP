/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bijhouding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.binding.AbstractBindingUitIntegratieTest;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingResultaat;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Geslachtsaanduiding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import nl.bzk.brp.util.PersoonAdresBuilder;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.web.bijhouding.InschrijvingGeboorteAntwoordBericht;
import org.jibx.runtime.JiBXException;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class InschrijvingGeboorteAntwoordBerichtBindingTest extends
    AbstractBindingUitIntegratieTest<InschrijvingGeboorteAntwoordBericht>
{

    private static final String RESULTAAT_NODE_NAAM = "afstamming_InschrijvingAangifteGeboorte_BijhoudingResponse";

    @Override
    public Class<InschrijvingGeboorteAntwoordBericht> getBindingClass() {
        return InschrijvingGeboorteAntwoordBericht.class;
    }

    @Test
    public void testLeegAntwoordBericht() throws JiBXException {
        final BijhoudingResultaat resultaat = new BijhoudingResultaat(new ArrayList<Melding>());
        resultaat.setTijdstipRegistratie(new Date());
        final InschrijvingGeboorteAntwoordBericht bericht = new InschrijvingGeboorteAntwoordBericht(resultaat);

        String xml = marshalObject(bericht);
        Assert.assertNotNull(xml);

        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipRegistratie", "20120325143506789");
        Assert.assertEquals(getBerichtResultaatTemplate(RESULTAAT_NODE_NAAM, "G", null, null, null,
            "20120325143506789", true), xml);

        valideerTegenSchema(xml);
    }

    @Test
    public void testAntwoordMetBijgehoudenPersonen() throws JiBXException {
        Melding[] meldingen = { new Melding(SoortMelding.INFO, MeldingCode.ACT0001) };
        final BijhoudingResultaat resultaat = new BijhoudingResultaat(Arrays.asList(meldingen));
        resultaat.markeerVerwerkingAlsFoutief();
        final InschrijvingGeboorteAntwoordBericht bericht = new InschrijvingGeboorteAntwoordBericht(resultaat);
        List<Persoon> bijgehoudenPersonen = new ArrayList<Persoon>();
        ReflectionTestUtils.setField(bericht, "bijgehoudenPersonen", bijgehoudenPersonen);
        PersoonBericht pers = PersoonBuilder.bouwPersoon("123456789", Geslachtsaanduiding.MAN, 19930205, null, null, null, null);
        bijgehoudenPersonen.add(pers);
        pers.setAdressen(new ArrayList<PersoonAdresBericht>());

        PersoonAdresBericht adres = PersoonAdresBuilder.bouwWoonadres(null, 12, "1234AB", null, null, null);
        pers.getAdressen().add(adres);

        String xml = marshalObject(bericht);
        Assert.assertNotNull(xml);

        Assert.assertEquals(
            getBerichtResultaatTemplate(RESULTAAT_NODE_NAAM, "F", "I", meldingen,
                new String[]{ "123456789" }, null, false), xml);
        valideerTegenSchema(xml);
    }

    @Override
    protected String getSchemaBestand() {
        return "/xsd/BRP_Afstamming_Berichten.xsd";
    }
}
