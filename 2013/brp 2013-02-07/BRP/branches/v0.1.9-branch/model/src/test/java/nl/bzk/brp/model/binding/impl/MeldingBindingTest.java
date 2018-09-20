/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.binding.impl;

import junit.framework.Assert;
import nl.bzk.brp.model.binding.AbstractBindingUitTest;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.jibx.runtime.JiBXException;
import org.junit.Test;

/** Eenvoudige binding test voor het binden van een {@link Melding}. */
public class MeldingBindingTest extends AbstractBindingUitTest<Melding> {

    @Test
    public void testBinding() throws JiBXException {
        Melding melding = new Melding(SoortMelding.INFO, MeldingCode.BRBER00121);
        Assert.assertEquals(getMeldingResultaat(), marshalObject(melding));
    }

    @Test
    public void testBindingMetAttribuutEnverzendendId() throws JiBXException {
        Melding melding = new Melding(SoortMelding.INFO, MeldingCode.BRBER00121);
        melding.setAttribuutNaam("MijnAttribuut");
        melding.setVerzendendId("Any string will do");
        Assert.assertEquals(getMeldingResultaat2(), marshalObject(melding));
    }

    /**
     * Retourneert het resultaat dat verwacht wordt.
     *
     * @return het verwachte resultaat.
     */
    private String getMeldingResultaat() {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        sb.append("<brp:melding xmlns:brp=\"http://www.bprbzk.nl/BRP/0001\" xmlns:xsi=\"http://www.w3"
                + ".org/2001/XMLSchema-instance\">");
        sb.append("<brp:regelCode>BRBER00121</brp:regelCode>");
        sb.append("<brp:soortCode>I</brp:soortCode>");
        sb.append("<brp:melding>").append(MeldingCode.BRBER00121.getOmschrijving()).append("</brp:melding>");
        sb.append("</brp:melding>");
        return sb.toString();
    }

    private String getMeldingResultaat2() {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        sb.append("<brp:melding xmlns:brp=\"http://www.bprbzk.nl/BRP/0001\" xmlns:xsi=\"http://www.w3"
                + ".org/2001/XMLSchema-instance\" brp:cIDVerzendend=\"Any string will do\">");
        sb.append("<brp:regelCode>BRBER00121</brp:regelCode>");
        sb.append("<brp:soortCode>I</brp:soortCode>");
        sb.append("<brp:melding>")
                .append(MeldingCode.BRBER00121.getOmschrijving()).append("</brp:melding>")
                .append("<brp:attribuutNaam>MijnAttribuut</brp:attribuutNaam>")
                .append("</brp:melding>");
        return sb.toString();
    }

    @Override
    protected Class<Melding> getBindingClass() {
        return Melding.class;
    }
}
