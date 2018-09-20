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


/** Unit test class voor de binding van de {@link nl.bzk.brp.web.bijhouding.VerhuizingAntwoordBericht} class. */
public class VerhuizingAntwoordBerichtBindingTest extends AbstractBindingTest<VerhuizingAntwoordBericht> {

    private static final String RESULTAAT_NODE_NAAM = "migratie_Verhuizing_BijhoudingResponse";

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

        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipRegistratie");
        Assert.assertEquals(getBerichtResultaatTemplate(RESULTAAT_NODE_NAAM, "G", "G", null, null), xml);
    }

    @Test
    public void testBindingMetGoedResultaatEnEenMelding() throws JiBXException {
        Melding[] meldingen = {
            new Melding(SoortMelding.INFO, MeldingCode.ALG0001, "Test omschrijving")
        };

        BerichtResultaat resultaat = new BerichtResultaat(Arrays.asList(meldingen));

        VerhuizingAntwoordBericht antwoord = new VerhuizingAntwoordBericht(resultaat);
        String xml = marshalObject(antwoord);
        Assert.assertNotNull(xml);

        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipRegistratie");
        Assert.assertEquals(getBerichtResultaatTemplate(RESULTAAT_NODE_NAAM, "G", "I", meldingen, null), xml);
    }

    @Test
    public void testBindingMetFoutResultaatEnMeerdereMeldingen() throws JiBXException {
        Melding[] meldingen = {
            new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0001, "Fout opgetreden"),
            new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.ALG0001, "Nog een fout opgetreden")
        };

        BerichtResultaat resultaat = new BerichtResultaat(Arrays.asList(meldingen));
        resultaat.markeerVerwerkingAlsFoutief();

        VerhuizingAntwoordBericht antwoord = new VerhuizingAntwoordBericht(resultaat);
        String xml = marshalObject(antwoord);
        Assert.assertNotNull(xml);

        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipRegistratie");
        Assert.assertEquals(getBerichtResultaatTemplate(RESULTAAT_NODE_NAAM, "F", "F", meldingen, null), xml);
    }

}
