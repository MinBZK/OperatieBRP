/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.binding;

import nl.bzk.brp.model.attribuuttype.Volgnummer;
import nl.bzk.brp.model.attribuuttype.Voornaam;
import nl.bzk.brp.model.binding.AbstractBindingUitTest;
import nl.bzk.brp.model.groep.logisch.PersoonVoornaamStandaardGroep;
import nl.bzk.brp.model.objecttype.operationeel.PersoonVoornaamModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.groep.bericht.PersoonVoornaamStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonVoornaamBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class PersoonVoornaamMdlTest extends AbstractBindingUitTest<PersoonVoornaamModel> {

    @Test
    public void testBindingOut() throws JiBXException {
        PersoonVoornaamBericht persoonVoornaam = new PersoonVoornaamBericht();
        ReflectionTestUtils.setField(persoonVoornaam, "volgnummer", new Volgnummer(12));

        PersoonVoornaamStandaardGroep gegevens = new PersoonVoornaamStandaardGroepBericht();
        ReflectionTestUtils.setField(gegevens, "voornaam", new Voornaam("Piet"));
        ReflectionTestUtils.setField(persoonVoornaam, "gegevens", gegevens);
        String xml = marshalObjectTest(new PersoonVoornaamModel(persoonVoornaam, new PersoonModel(new PersoonBericht())));

        final String expectedXml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                        + "<brp:Objecttype_PersoonVoornaam xmlns:brp=\"http://www.bprbzk.nl/BRP/0001\" "
                        + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                        + "<brp:volgnummer>12</brp:volgnummer>"
                        + "<brp:naam>Piet</brp:naam>"
                        + "</brp:Objecttype_PersoonVoornaam>";
        Assert.assertEquals(expectedXml, xml);
    }

    @Override
    protected Class<PersoonVoornaamModel> getBindingClass() {
        return PersoonVoornaamModel.class;
    }
}
