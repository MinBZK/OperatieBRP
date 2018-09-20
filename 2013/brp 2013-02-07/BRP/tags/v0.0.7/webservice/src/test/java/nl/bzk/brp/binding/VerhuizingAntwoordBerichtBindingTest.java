/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding;

import java.util.Arrays;

import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.model.binding.AbstractBindingTest;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import nl.bzk.brp.web.bijhouding.VerhuizingAntwoordBericht;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test class voor de binding van de {@link nl.bzk.brp.web.bijhouding.VerhuizingAntwoordBericht} class.
 */
public class VerhuizingAntwoordBerichtBindingTest extends AbstractBindingTest<VerhuizingAntwoordBericht> {

    @Override
    protected Class<VerhuizingAntwoordBericht> getBindingClass() {
        return VerhuizingAntwoordBericht.class;
    }

    @Test
    public void testBindingMetGoedResultaatEnGeenMeldingen() throws JiBXException {
        BerichtResultaat resultaat = new BerichtResultaat(null);
        VerhuizingAntwoordBericht antwoord = new VerhuizingAntwoordBericht(resultaat);
        String xml = marshalObject(antwoord);
        Assert.assertNotNull(xml);
        Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<BerichtResultaat xmlns=\"http://www.brp.nl/brp/0001\">"
                + "<resultaat>G</resultaat>"
                + "</BerichtResultaat>", xml);
    }

    @Test
    public void testBindingMetGoedResultaatEnEenMelding() throws JiBXException {
        BerichtResultaat resultaat = new BerichtResultaat(
                Arrays.asList(new Melding(SoortMelding.INFO, MeldingCode.ALG0001, "Test omschrijving")));

        VerhuizingAntwoordBericht antwoord = new VerhuizingAntwoordBericht(resultaat);
        String xml = marshalObject(antwoord);
        Assert.assertNotNull(xml);
        Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<BerichtResultaat xmlns=\"http://www.brp.nl/brp/0001\">"
                + "<resultaat>G</resultaat>"
                + "<meldingen>"
                + "<bas:melding xmlns:bas=\"http://www.brp.bzk.nl/basis\">"
                + "<bas:soort>INFO</bas:soort><bas:code>ALG0001</bas:code><bas:omschrijving>Test "
                + "omschrijving</bas:omschrijving></bas:melding>"
                + "</meldingen>"
                + "</BerichtResultaat>", xml);
    }

    @Test
    public void testBindingMetFoutResultaatEnMeerdereMeldingen() throws JiBXException {
        BerichtResultaat resultaat = new BerichtResultaat(
                Arrays.asList(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0001, "Fout opgetreden"),
                        new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.ALG0001, "Nog een fout opgetreden")));
        resultaat.markeerVerwerkingAlsFoutief();

        VerhuizingAntwoordBericht antwoord = new VerhuizingAntwoordBericht(resultaat);
        String xml = marshalObject(antwoord);
        Assert.assertNotNull(xml);
        Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<BerichtResultaat xmlns=\"http://www.brp.nl/brp/0001\">"
                + "<resultaat>F</resultaat>"
                + "<meldingen>"
                + "<bas:melding xmlns:bas=\"http://www.brp.bzk.nl/basis\">"
                + "<bas:soort>FOUT_ONOVERRULEBAAR</bas:soort><bas:code>ALG0001</bas:code><bas:omschrijving>Fout "
                + "opgetreden</bas:omschrijving></bas:melding>"
                + "<bas:melding xmlns:bas=\"http://www.brp.bzk.nl/basis\">"
                + "<bas:soort>FOUT_OVERRULEBAAR</bas:soort><bas:code>ALG0001</bas:code><bas:omschrijving>Nog een fout "
                + "opgetreden</bas:omschrijving></bas:melding>"
                + "</meldingen>"
                + "</BerichtResultaat>", xml);
    }

}
