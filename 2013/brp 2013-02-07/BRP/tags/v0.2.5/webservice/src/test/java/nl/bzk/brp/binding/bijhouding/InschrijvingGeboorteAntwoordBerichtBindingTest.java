/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bijhouding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import junit.framework.Assert;
import nl.bzk.brp.binding.AbstractBindingUitIntegratieTest;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingResultaat;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Geslachtsaanduiding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortbericht;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortmelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.PersoonAdresBuilder;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.web.AntwoordBerichtFactory;
import nl.bzk.brp.web.AntwoordBerichtFactoryImpl;
import nl.bzk.brp.web.bijhouding.InschrijvingGeboorteAntwoordBericht;
import org.jibx.runtime.JiBXException;
import org.junit.Before;
import org.junit.Test;


public class InschrijvingGeboorteAntwoordBerichtBindingTest extends
    AbstractBindingUitIntegratieTest<InschrijvingGeboorteAntwoordBericht>
{

    private AntwoordBerichtFactory antwoordBerichtFactory;

    @Before
    public void init() {
        antwoordBerichtFactory = new AntwoordBerichtFactoryImpl();
    }

    private static final String RESULTAAT_NODE_NAAM = "afstamming_InschrijvingAangifteGeboorte_BijhoudingResponse";

    @Override
    public Class<InschrijvingGeboorteAntwoordBericht> getBindingClass() {
        return InschrijvingGeboorteAntwoordBericht.class;
    }

    @Test
    public void testLeegAntwoordBericht() throws JiBXException {
        final BijhoudingResultaat resultaat = new BijhoudingResultaat(new ArrayList<Melding>());
        resultaat.setTijdstipRegistratie(new Date());
        final InschrijvingGeboorteAntwoordBericht bericht =
                (InschrijvingGeboorteAntwoordBericht) antwoordBerichtFactory.bouwAntwoordBericht(
                        bouwIngaandBericht(Soortbericht.AFSTAMMING_INSCHRIJVINGAANGIFTEGEBOORTE_BIJHOUDING), resultaat);

        String xml = marshalObject(bericht);
        Assert.assertNotNull(xml);

        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipRegistratie", "20120325143506789");
        Assert.assertEquals(getBerichtResultaatTemplate(RESULTAAT_NODE_NAAM, "G", null, null, null,
            "20120325143506789", true), xml);

        valideerTegenSchema(xml);
    }

    @Test
    public void testAntwoordMetBijgehoudenPersonen() throws JiBXException {
        Melding[] meldingen = { new Melding(Soortmelding.INFORMATIE, MeldingCode.ACT0001) };
        final BijhoudingResultaat resultaat = new BijhoudingResultaat(Arrays.asList(meldingen));

        PersoonBericht pers1 = PersoonBuilder.bouwPersoon("123456782", Geslachtsaanduiding.MAN, 19930205, null, null, null, null);
        PersoonBericht pers2 = PersoonBuilder.bouwPersoon("123456781", Geslachtsaanduiding.MAN, 19930205, null, null, null, null);
        PersoonBericht pers3 = PersoonBuilder.bouwPersoon("123456780", Geslachtsaanduiding.MAN, 19930205, null, null, null, null);
        resultaat.voegPersoonToe(pers1);
        resultaat.voegPersoonToe(pers2);
        resultaat.voegPersoonToe(pers3);

        pers1.setAdressen(new ArrayList<PersoonAdresBericht>());
        pers2.setAdressen(new ArrayList<PersoonAdresBericht>());
        pers3.setAdressen(new ArrayList<PersoonAdresBericht>());

        PersoonAdresBericht adres = PersoonAdresBuilder.bouwWoonadres(null, 12, "1234AB", null, null, null);
        pers1.getAdressen().add(adres);
        pers2.getAdressen().add(adres);
        pers3.getAdressen().add(adres);

        resultaat.markeerVerwerkingAlsFoutief();
        final InschrijvingGeboorteAntwoordBericht bericht =
                (InschrijvingGeboorteAntwoordBericht) antwoordBerichtFactory.bouwAntwoordBericht(
                        bouwIngaandBericht(Soortbericht.AFSTAMMING_INSCHRIJVINGAANGIFTEGEBOORTE_BIJHOUDING), resultaat);

        String xml = marshalObject(bericht);
        Assert.assertNotNull(xml);

        Assert.assertEquals(
            getBerichtResultaatTemplate(RESULTAAT_NODE_NAAM, "F", "I", meldingen,
                new String[]{ "123456780", "123456781", "123456782" }, null, false), xml);
        valideerTegenSchema(xml);
    }

    @Override
    protected String getSchemaBestand() {
        return "/xsd/BRP_Afstamming_Berichten.xsd";
    }
}
