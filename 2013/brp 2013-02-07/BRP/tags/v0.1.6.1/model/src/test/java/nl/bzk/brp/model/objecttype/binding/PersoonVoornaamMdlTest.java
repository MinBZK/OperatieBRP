/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.binding;

import nl.bzk.brp.model.attribuuttype.Volgnummer;
import nl.bzk.brp.model.attribuuttype.Voornaam;
import nl.bzk.brp.model.binding.AbstractBindingUitTest;
import nl.bzk.brp.model.groep.impl.gen.AbstractPersoonVoornaamStandaardGroepMdl;
import nl.bzk.brp.model.groep.impl.usr.PersoonVoornaamStandaardGroepMdl;
import nl.bzk.brp.model.objecttype.impl.usr.PersoonVoornaamMdl;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class PersoonVoornaamMdlTest extends AbstractBindingUitTest<PersoonVoornaamMdl> {

    @Test
    public void testBindingOut() throws JiBXException {
        PersoonVoornaamMdl persoonVoornaam = new PersoonVoornaamMdl();
        ReflectionTestUtils.setField(persoonVoornaam, "volgnummer", new Volgnummer(12));

        AbstractPersoonVoornaamStandaardGroepMdl gegevens = new PersoonVoornaamStandaardGroepMdl();
        ReflectionTestUtils.setField(gegevens, "voornaam", new Voornaam("Piet"));
        ReflectionTestUtils.setField(persoonVoornaam, "gegevens", gegevens);
        String xml = marshalObjectTest(persoonVoornaam);

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
    protected Class<PersoonVoornaamMdl> getBindingClass() {
        return PersoonVoornaamMdl.class;
    }
}
