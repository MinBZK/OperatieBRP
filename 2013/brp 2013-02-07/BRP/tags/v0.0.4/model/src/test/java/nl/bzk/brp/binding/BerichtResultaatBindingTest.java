/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding;

import java.util.Arrays;

import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test class voor de binding van de {@link BerichtResultaat} class.
 */
public class BerichtResultaatBindingTest extends AbstractBindingTest<BerichtResultaat> {

    @Override
    protected Class<BerichtResultaat> getBindingClass() {
        return BerichtResultaat.class;
    }

    @Test
    public void testBerichtResultaatBindingOutMetGoedResultaatEnGeenMeldingen() throws JiBXException {
        BerichtResultaat resultaat = new BerichtResultaat(null);

        String xml = marshalObject(resultaat);
        Assert.assertNotNull(xml);
        Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<BerichtResultaat xmlns=\"http://www.brp.bzk.nl/bijhouden\">"
                + "<resultaat>GOED</resultaat>"
                + "</BerichtResultaat>", xml);
    }

    @Test
    public void testBerichtResultaatBindingOutMetGoedResultaatEnEenMelding() throws JiBXException {
        BerichtResultaat resultaat = new BerichtResultaat(
                Arrays.asList(new Melding(SoortMelding.INFO, MeldingCode.ALG0001, "Test omschrijving")));


        String xml = marshalObject(resultaat);
        Assert.assertNotNull(xml);
        Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<BerichtResultaat xmlns=\"http://www.brp.bzk.nl/bijhouden\">"
                + "<resultaat>GOED</resultaat>"
                + "<meldingen>"
                + "<melding><soort>INFO</soort><code>ALG0001</code><omschrijving>Test "
                + "omschrijving</omschrijving></melding>"
                + "</meldingen>"
                + "</BerichtResultaat>", xml);
    }

    @Test
    public void testBerichtResultaatBindingOutMetFoutResultaatEnMeerdereMeldingen() throws JiBXException {
        BerichtResultaat resultaat = new BerichtResultaat(
                Arrays.asList(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0001, "Fout opgetreden"),
                        new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.ALG0001, "Nog een fout opgetreden")));

        String xml = marshalObject(resultaat);
        Assert.assertNotNull(xml);
        Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<BerichtResultaat xmlns=\"http://www.brp.bzk.nl/bijhouden\">"
                + "<resultaat>FOUT</resultaat>"
                + "<meldingen>"
                + "<melding><soort>FOUT_ONOVERRULEBAAR</soort><code>ALG0001</code><omschrijving>Fout "
                + "opgetreden</omschrijving></melding>"
                + "<melding><soort>FOUT_OVERRULEBAAR</soort><code>ALG0001</code><omschrijving>Nog een fout "
                + "opgetreden</omschrijving></melding>"
                + "</meldingen>"
                + "</BerichtResultaat>", xml);
    }

}
