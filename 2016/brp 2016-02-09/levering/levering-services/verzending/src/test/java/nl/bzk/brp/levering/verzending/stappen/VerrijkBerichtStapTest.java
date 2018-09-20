/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.verzending.stappen;

import nl.bzk.brp.levering.algemeen.LeveringConstanten;
import nl.bzk.brp.levering.verzending.context.BerichtContext;
import nl.bzk.brp.levering.verzending.service.impl.VerwerkContext;
import nl.bzk.brp.model.internbericht.SynchronisatieBerichtGegevens;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.jms.Message;

@RunWith(MockitoJUnitRunner.class)
public class VerrijkBerichtStapTest {

    private static final String TEST_BERICHT_GEGEVENS =
        "{\"administratieveHandelingId\":1,\"geleverdePersoonsIds\":[1,2],\"toegangLeveringsautorisatieId\":123455, "
            + "\"stuurgegevens\":{\"referentienummer\":\"referentie12345\"}}}";

    @InjectMocks
    private VerrijkBerichtStap verrijkBerichtStap = new VerrijkBerichtStap();

    @Test
    public final void testProcess() throws Exception {

        final Message jmsBericht = Mockito.mock(Message.class);
        Mockito.when(jmsBericht.getStringProperty(LeveringConstanten.JMS_PROPERTY_KEY_BERICHT_GEGEVENS)).thenReturn(TEST_BERICHT_GEGEVENS);

        final BerichtContext berichtContext = new BerichtContext(new VerwerkContext(0), jmsBericht);
        verrijkBerichtStap.process(berichtContext);

        final SynchronisatieBerichtGegevens synchronisatieBerichtGegevens = berichtContext.getSynchronisatieBerichtGegevens();
        Assert.assertNotNull(synchronisatieBerichtGegevens);
        Assert.assertEquals(Long.valueOf(1), synchronisatieBerichtGegevens.getAdministratieveHandelingId());
    }
}
