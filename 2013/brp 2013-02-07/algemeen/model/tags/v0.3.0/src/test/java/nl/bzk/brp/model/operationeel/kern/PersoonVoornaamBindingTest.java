/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Volgnummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voornaam;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamStandaardGroepBericht;
import nl.bzk.brp.model.binding.AbstractBindingUitTest;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
//TODO Refactor fix, test tijdelijk uitgezet
public class PersoonVoornaamBindingTest extends AbstractBindingUitTest<PersoonVoornaamModel> {

    @Test
    public void testBindingOut() throws JiBXException {
        PersoonVoornaamBericht persoonVoornaam = new PersoonVoornaamBericht();
        persoonVoornaam.setVolgnummer(new Volgnummer(12));

        PersoonVoornaamStandaardGroepBericht gegevens = new PersoonVoornaamStandaardGroepBericht();
        gegevens.setNaam(new Voornaam("Piet"));
        persoonVoornaam.setStandaard(gegevens);
        String xml = marshalObject(new PersoonVoornaamModel(persoonVoornaam, new PersoonModel(new PersoonBericht())));

        final String expectedXml =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<brp:Objecttype_PersoonVoornaam xmlns:brp=\"http://www.bprbzk.nl/BRP/0001\" "
                + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" + "<brp:volgnummer>12</brp:volgnummer>"
                + "<brp:naam>Piet</brp:naam>" + "</brp:Objecttype_PersoonVoornaam>";
        Assert.assertEquals(expectedXml, xml);
    }

    @Override
    protected Class<PersoonVoornaamModel> getBindingClass() {
        return PersoonVoornaamModel.class;
    }
}
