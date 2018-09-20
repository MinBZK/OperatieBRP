/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.notificator.runnable;

import nl.bzk.brp.serialisatie.notificator.exceptions.CommandException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * Plaats persoon op queue runnable test.
 */
@RunWith(MockitoJUnitRunner.class)
public class PlaatsPersoonOpQueueRunnableTest {

    private static final int PERSOON_ID = 1;

    @Mock
    private JmsTemplate persoonSerialisatieJmsTemplate;

    private PlaatsPersoonOpQueueRunnable plaatsPersoonOpQueueRunnable;

    @Before
    public void pre() {
        plaatsPersoonOpQueueRunnable = new PlaatsPersoonOpQueueRunnable();
    }

    @Test
    public void testRunHappyFlow() {
        setPersoonId();
        setPersoonSerialisatieJmsTemplate();

        plaatsPersoonOpQueueRunnable.run();

        Mockito.verify(persoonSerialisatieJmsTemplate).send(Matchers.any(MessageCreator.class));
    }

    @Test(expected = CommandException.class)
    public void testRunZonderPersoonId() {
        setPersoonSerialisatieJmsTemplate();

        plaatsPersoonOpQueueRunnable.run();

        Mockito.verify(persoonSerialisatieJmsTemplate).send(Matchers.any(MessageCreator.class));
    }

    @Test(expected = CommandException.class)
    public void testRunZonderPersoonSerialisatieJmsTemplate() {
        setPersoonId();

        plaatsPersoonOpQueueRunnable.run();
    }

    @Test(expected = CommandException.class)
    public void testRunFoutVanAanroepPersoonSerialisatieJmsTemplate() {
        setPersoonSerialisatieJmsTemplate();
        setPersoonId();

        Mockito.doThrow(org.springframework.jms.IllegalStateException.class).when(persoonSerialisatieJmsTemplate)
                .send(Matchers.any(MessageCreator.class));

        plaatsPersoonOpQueueRunnable.run();
    }


    /**
     * Zet de persoon id.
     */
    private void setPersoonId() {
        plaatsPersoonOpQueueRunnable.setPersoonId(PERSOON_ID);
    }

    /**
     * Zet de persoon serialisatie jms template.
     */
    private void setPersoonSerialisatieJmsTemplate() {
        plaatsPersoonOpQueueRunnable.setPersoonSerialisatieJmsTemplate(persoonSerialisatieJmsTemplate);
    }
}
