/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.notificator.commands;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.serialisatie.notificator.app.ContextParameterNames;
import nl.bzk.brp.serialisatie.notificator.exceptions.CommandException;
import nl.bzk.brp.serialisatie.notificator.service.JmsService;
import org.apache.commons.chain.Context;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PlaatsPersonenOpQueueStapTest {

    @InjectMocks
    private PlaatsPersonenOpQueueStap plaatsPersonenOpQueueStap = new PlaatsPersonenOpQueueStap();

    @Mock
    private JmsService jmsService;

    @Mock
    private Context context;

    private List<Integer> persoonIdLijst = new ArrayList<Integer>();

    @Before
    public void setup() {
        when(context.get(ContextParameterNames.PERSOON_ID_LIJST)).thenReturn(persoonIdLijst);
    }

    @Test
    public void testExecute() throws Exception {
        final boolean resultaat = plaatsPersonenOpQueueStap.execute(context);

        Mockito.verify(jmsService).creeerEnPubliceerJmsBerichtenVoorPersonen(persoonIdLijst);

        Assert.assertFalse(resultaat);
    }

    @Test(expected = CommandException.class)
    public void testExecuteMetFout() throws Exception {
        Mockito.doThrow(CommandException.class).when(jmsService)
                .creeerEnPubliceerJmsBerichtenVoorPersonen(persoonIdLijst);

        plaatsPersonenOpQueueStap.execute(context);
    }
}
