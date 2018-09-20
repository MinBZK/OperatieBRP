/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.model.binding.AbstractBindingTest;
import nl.bzk.brp.model.gedeeld.GeslachtsAanduiding;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import nl.bzk.brp.model.logisch.groep.PersoonGeboorte;
import nl.bzk.brp.model.logisch.groep.PersoonGeslachtsAanduiding;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import nl.bzk.brp.web.bevraging.BevragingAntwoordBericht;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;

public class BevragingAntwoordBerichtBindingTest extends AbstractBindingTest<BevragingAntwoordBericht> {

    @Override
    protected Class<BevragingAntwoordBericht> getBindingClass() {
        return BevragingAntwoordBericht.class;
    }

    @Test
    public void testOutBindingMetLeegResultaat() throws JiBXException {
        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(null);

        final BevragingAntwoordBericht response = new BevragingAntwoordBericht(resultaat);
        String xml = marshalObject(response);
        Assert.assertNotNull(xml);
        Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
            + "<OpvragenPersoonResultaat xmlns=\"" + NAMESPACE_BRP + "\">"
            + "<aantal>0</aantal>"
            + "</OpvragenPersoonResultaat>", xml);
    }

    @Test
    public void testOutBindingMetResultaatEnMelding() throws JiBXException {
        List<Melding> meldingen = new ArrayList<Melding>();
        meldingen.add(new Melding(SoortMelding.INFO, MeldingCode.ALG0001, "TEST"));

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(meldingen);
        BevragingAntwoordBericht response = new BevragingAntwoordBericht(resultaat);

        String xml = marshalObject(response);
        Assert.assertNotNull(xml);
        Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
            + "<OpvragenPersoonResultaat xmlns=\"" + NAMESPACE_BRP + "\">"
            + "<aantal>0</aantal>"
            + "<meldingen>"
            + "<bas:melding xmlns:bas=\"http://www.brp.bzk.nl/basis\">"
            + "<bas:soort>INFO</bas:soort>"
            + "<bas:code>ALG0001</bas:code>"
            + "<bas:omschrijving>TEST</bas:omschrijving>"
            + "</bas:melding>"
            + "</meldingen>"
            + "</OpvragenPersoonResultaat>", xml);
    }

    @Test
    public void testOutBindingMetPersoonInResultaat() throws JiBXException {
        Set<Persoon> gevondenPersonen = new HashSet<Persoon>();

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
        gevondenPersonen.add(pers);
        pers.setAdressen(new HashSet<PersoonAdres>());

        PersoonAdres adr = new PersoonAdres();
        adr.setPostcode("1234AB");
        adr.setHuisnummer("12");
        pers.getAdressen().add(adr);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(null);
        resultaat.setGevondenPersonen(gevondenPersonen);

        final BevragingAntwoordBericht response = new BevragingAntwoordBericht(resultaat);

        String xml = marshalObject(response);
        Assert.assertNotNull(xml);
        Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
            + "<OpvragenPersoonResultaat xmlns=\"" + NAMESPACE_BRP + "\">"
            + "<aantal>1</aantal>"
            + "<persoon>"
            + "<A-nummer>987654321</A-nummer>"
            + "<bsn>123456789</bsn>"
            + "<geboortedatum>19930205</geboortedatum>"
            + "<geslachtsAanduiding>M</geslachtsAanduiding>"
            + "<geslachtsNaam>van der Vliet</geslachtsNaam>"
            + "<adres>"
            + "<huisnummer>12</huisnummer>"
            + "<postcode>1234AB</postcode>"
            + "</adres>"
            + "<voornaam>Kees</voornaam>"
            + "</persoon>"
            + "</OpvragenPersoonResultaat>", xml);
    }
}
