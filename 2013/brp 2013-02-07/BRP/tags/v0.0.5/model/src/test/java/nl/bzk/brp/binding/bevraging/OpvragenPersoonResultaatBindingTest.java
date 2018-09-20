/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bevraging;

import java.util.ArrayList;
import java.util.HashSet;

import nl.bzk.brp.binding.AbstractBindingTest;
import nl.bzk.brp.binding.Melding;
import nl.bzk.brp.binding.MeldingCode;
import nl.bzk.brp.binding.SoortMelding;
import nl.bzk.brp.model.gedeeld.GeslachtsAanduiding;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import nl.bzk.brp.model.logisch.groep.PersoonGeboorte;
import nl.bzk.brp.model.logisch.groep.PersoonGeslachtsAanduiding;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;

public class OpvragenPersoonResultaatBindingTest extends AbstractBindingTest<OpvragenPersoonResultaat> {

    @Override
    protected Class<OpvragenPersoonResultaat> getBindingClass() {
        return OpvragenPersoonResultaat.class;
    }

    @Test
    public void testOutBindingMetLeegResultaat() throws JiBXException {
        final OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());
        resultaat.setAantal(9);
        String xml = marshalObject(resultaat);
        Assert.assertNotNull(xml);
        Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<OpvragenPersoonResultaat xmlns=\"http://www.brp.bzk.nl/bevraging\">"
                + "<aantal>9</aantal>"
                + "<meldingen/>"
                + "</OpvragenPersoonResultaat>", xml);
    }

    @Test
    public void testOutBindingMetResultaatEnMelding() throws JiBXException {
        final OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());
        resultaat.setAantal(9);
        resultaat.voegMeldingToe(new Melding(SoortMelding.INFO, MeldingCode.ALG0001, "TEST"));
        String xml = marshalObject(resultaat);
        Assert.assertNotNull(xml);
        Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<OpvragenPersoonResultaat xmlns=\"http://www.brp.bzk.nl/bevraging\">"
                + "<aantal>9</aantal>"
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
        final OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());
        resultaat.setAantal(9);
        resultaat.setGevondenPersonen(new HashSet<Persoon>());
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
        resultaat.getGevondenPersonen().add(pers);
        pers.setAdressen(new HashSet<PersoonAdres>());

        PersoonAdres adr = new PersoonAdres();
        adr.setPostcode("1234AB");
        adr.setHuisnummer("12");
        pers.getAdressen().add(adr);

        String xml = marshalObject(resultaat);
        Assert.assertNotNull(xml);
        Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<OpvragenPersoonResultaat xmlns=\"http://www.brp.bzk.nl/bevraging\">"
                + "<aantal>9</aantal>"
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
                + "<meldingen/>"
                + "</OpvragenPersoonResultaat>", xml);
    }
}
