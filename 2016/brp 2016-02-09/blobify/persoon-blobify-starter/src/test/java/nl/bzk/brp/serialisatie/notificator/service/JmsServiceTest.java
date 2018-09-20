/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.notificator.service;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import nl.bzk.brp.serialisatie.notificator.exceptions.CommandException;
import nl.bzk.brp.serialisatie.notificator.repository.PersoonIdRepository;
import nl.bzk.brp.serialisatie.notificator.service.impl.JmsServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@RunWith(MockitoJUnitRunner.class)
public class JmsServiceTest {

    @InjectMocks
    private JmsServiceImpl jmsService = new JmsServiceImpl();

    @Mock
    private JmsTemplate persoonSerialisatieJmsTemplate;

    @Mock
    private ConnectionFactory connectionFactory;

    @Mock
    private Destination destination;

    @Mock
    private PersoonIdRepository persoonIdRepository;

    @Mock
    private ThreadPoolTaskExecutor executor;

    private List<Integer> persoonIdLijst = new ArrayList<Integer>();

    private int persoonId1 = 123;
    private int persoonId2 = 456;
    private int persoonId3 = 789;

    @Before
    public void pre() {
        when(persoonSerialisatieJmsTemplate.getConnectionFactory()).thenReturn(connectionFactory);
        when(persoonSerialisatieJmsTemplate.getDefaultDestination()).thenReturn(destination);

        persoonIdLijst.add(persoonId1);
        persoonIdLijst.add(persoonId2);
        persoonIdLijst.add(persoonId3);
    }

    @Test
    public void testCreeerEnPubliceerJmsBerichtenVoorPersonen() {
        jmsService.creeerEnPubliceerJmsBerichtenVoorPersonen(persoonIdLijst);
        verify(executor, times(persoonIdLijst.size())).execute(any(Runnable.class));
    }

    @Test(expected = CommandException.class)
    public void testCreeerEnPubliceerJmsBerichtenZonderGeconfigureerdeNotificator() {
        when(persoonSerialisatieJmsTemplate.getConnectionFactory()).thenReturn(null);

        jmsService.creeerEnPubliceerJmsBerichtenVoorPersonen(persoonIdLijst);
    }

    @Test
    public void testCreeerEnPubliceerJmsBerichtenVoorAllePersonenBatchVan1() {
        final int aantalIdsPerBatch = 1;

        when(persoonIdRepository.vindPersoonIds(1, aantalIdsPerBatch))
                .thenReturn(persoonIdLijst.subList(0, 1));
        when(persoonIdRepository.vindPersoonIds(persoonId1 + 1, aantalIdsPerBatch))
                .thenReturn(persoonIdLijst.subList(1, 2));
        when(persoonIdRepository.vindPersoonIds(persoonId2 + 1, aantalIdsPerBatch))
                .thenReturn(persoonIdLijst.subList(2, 3));

        jmsService.creeerEnPubliceerJmsBerichtenVoorAllePersonen(aantalIdsPerBatch, 0, 1);

        verify(executor, times(3)).execute(any(Runnable.class));
    }

    @Test
    public void testCreeerEnPubliceerJmsBerichtenVoorAllePersonenBatchVan3() {
        final int aantalIdsPerBatch = 3;

        when(persoonIdRepository.vindPersoonIds(1, aantalIdsPerBatch))
                .thenReturn(persoonIdLijst);

        jmsService.creeerEnPubliceerJmsBerichtenVoorAllePersonen(aantalIdsPerBatch, 0, 1);

        verify(executor, times(3)).execute(any(Runnable.class));
    }

    @Test
    public void testCreeerEnPubliceerJmsBerichtenVoorAllePersonenWachttijd() {
        final int aantalIdsPerBatch = 1;
        final int wachttijd = 1;

        when(persoonIdRepository.vindPersoonIds(0, aantalIdsPerBatch))
                .thenReturn(persoonIdLijst.subList(0, 1));
        when(persoonIdRepository.vindPersoonIds(persoonId1, aantalIdsPerBatch))
                .thenReturn(persoonIdLijst.subList(1, 2));
        when(persoonIdRepository.vindPersoonIds(persoonId2, aantalIdsPerBatch))
                .thenReturn(persoonIdLijst.subList(2, 3));

        final long starttijd = System.currentTimeMillis();
        jmsService.creeerEnPubliceerJmsBerichtenVoorAllePersonen(aantalIdsPerBatch, wachttijd, 1);
        final long procestijd = System.currentTimeMillis() - starttijd;

        assertTrue(procestijd > wachttijd * persoonIdLijst.size());
    }

    @Test
    public void testCreeerEnPubliceerJmsBerichtenVoorAllePersonenVanafId10() {
        final int aantalIdsPerBatch = 1;

        when(persoonIdRepository.vindPersoonIds(10, aantalIdsPerBatch))
                .thenReturn(persoonIdLijst);

        jmsService.creeerEnPubliceerJmsBerichtenVoorAllePersonen(aantalIdsPerBatch, 0, 10);

        verify(executor, times(3)).execute(any(Runnable.class));
    }

}
