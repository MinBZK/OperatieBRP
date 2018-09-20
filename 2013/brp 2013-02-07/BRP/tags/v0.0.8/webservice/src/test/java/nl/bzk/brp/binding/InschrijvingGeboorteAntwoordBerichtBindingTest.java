/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.model.binding.AbstractBindingTest;
import nl.bzk.brp.model.gedeeld.GeslachtsAanduiding;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import nl.bzk.brp.model.logisch.groep.PersoonGeboorte;
import nl.bzk.brp.model.logisch.groep.PersoonGeslachtsAanduiding;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.web.bijhouding.InschrijvingGeboorteAntwoordBericht;
import org.jibx.runtime.JiBXException;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class InschrijvingGeboorteAntwoordBerichtBindingTest
    extends AbstractBindingTest<InschrijvingGeboorteAntwoordBericht>
{

    private static final String RESULTAAT_NODE_NAAM =
        "afstamming_InschrijvingAangifteGeboorte_BijhoudingResponse_v0001";

    @Override
    protected Class<InschrijvingGeboorteAntwoordBericht> getBindingClass() {
        return InschrijvingGeboorteAntwoordBericht.class;
    }

    @Test
    public void testLeegAntwoordBericht() throws JiBXException {
        final BerichtResultaat resultaat = new BerichtResultaat(new ArrayList<Melding>());
        final InschrijvingGeboorteAntwoordBericht bericht = new InschrijvingGeboorteAntwoordBericht(resultaat);

        String xml = marshalObject(bericht);
        Assert.assertNotNull(xml);

        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipRegistratie");
        Assert.assertEquals(getBerichtResultaatTemplate(RESULTAAT_NODE_NAAM, "G", "G", null, null), xml);
    }

    @Test
    public void testAntwoordMetBijgehoudenPersonen() throws JiBXException {
        final BerichtResultaat resultaat = new BerichtResultaat(new ArrayList<Melding>());
        final InschrijvingGeboorteAntwoordBericht bericht = new InschrijvingGeboorteAntwoordBericht(resultaat);
        List<Persoon> bijgehoudenPersonen = new ArrayList<Persoon>();
        ReflectionTestUtils.setField(bericht, "bijgehoudenPersonen", bijgehoudenPersonen);
        Persoon pers = new Persoon();
        pers.setGeslachtsNaam("van der Vliet");
        pers.setVoornamen("Kees");
        pers.setPersoonGeboorte(new PersoonGeboorte());
        pers.getPersoonGeboorte().setDatumGeboorte(19930205);
        pers.setIdentificatienummers(new PersoonIdentificatienummers());
        pers.getIdentificatienummers().setBurgerservicenummer("123456789");
        pers.getIdentificatienummers().setAdministratienummer("987654321");
        pers.setPersoonGeslachtsAanduiding(new PersoonGeslachtsAanduiding());
        pers.getPersoonGeslachtsAanduiding().setGeslachtsAanduiding(GeslachtsAanduiding.MAN);
        bijgehoudenPersonen.add(pers);
        pers.setAdressen(new HashSet<PersoonAdres>());

        PersoonAdres adr = new PersoonAdres();
        adr.setPostcode("1234AB");
        adr.setHuisnummer("12");
        pers.getAdressen().add(adr);

        String xml = marshalObject(bericht);
        Assert.assertNotNull(xml);

        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipRegistratie");
        Assert.assertEquals(getBerichtResultaatTemplate(RESULTAAT_NODE_NAAM, "G", "G", null,
            new String[]{ "123456789" }), xml);
    }

}
