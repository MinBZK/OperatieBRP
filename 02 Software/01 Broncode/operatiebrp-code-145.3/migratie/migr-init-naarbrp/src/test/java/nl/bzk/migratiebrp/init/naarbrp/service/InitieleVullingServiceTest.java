/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.text.ParseException;
import javax.sql.DataSource;
import nl.bzk.migratiebrp.init.naarbrp.domein.ConversieResultaat;
import nl.bzk.migratiebrp.init.naarbrp.repository.AfnemersIndicatieRepository;
import nl.bzk.migratiebrp.init.naarbrp.repository.AutorisatieRepository;
import nl.bzk.migratiebrp.init.naarbrp.repository.PersoonRepository;
import nl.bzk.migratiebrp.init.naarbrp.service.impl.InitieleVullingServiceImpl;
import nl.bzk.migratiebrp.init.naarbrp.verwerker.impl.VerzendPersoonBerichtVerwerker;
import nl.bzk.migratiebrp.util.excel.ExcelData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.jms.core.JmsTemplate;

/**
 * Test de service voor het versturen van LO3-berichten.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ExcelData.class)
@PowerMockIgnore("javax.management.*")
public class InitieleVullingServiceTest {

    @Mock
    private PersoonRepository persoonRepository;

    @Mock
    private AutorisatieRepository autorisatieRepository;

    @Mock
    private AfnemersIndicatieRepository afnemersIndicatieRepository;

    @Mock
    private JmsTemplate jmsTemplate;

    @Mock
    private DataSource dataSource;

    @InjectMocks
    private InitieleVullingServiceImpl service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(service);

        service.setBatchPersoon(100);
        service.setBatchAutorisatie(100);
        service.setBatchAfnemersindicatie(100);
        service.setBatchProtocollering(10);
        service.setAantalProtocollering(1000);
    }

    @Test
    public void testLeesAlleBerichten() throws ParseException {

        service.synchroniseerPersonen();

        verify(persoonRepository).verwerkLo3Berichten(argThat(new ConversieResultaatMatcher()), any(VerzendPersoonBerichtVerwerker.class), anyInt());

        verifyNoMoreInteractions(autorisatieRepository);
        verifyNoMoreInteractions(afnemersIndicatieRepository);
        verifyNoMoreInteractions(persoonRepository);
        verifyNoMoreInteractions(dataSource);
        verifyNoMoreInteractions(jmsTemplate);
    }

    @Test
    public void testCreateInitAfnemersIndicatieTabel() {
        service.laadInitAfnemersIndicatieTabel();

        verify(afnemersIndicatieRepository).laadInitVullingAfnIndTable();
        verifyNoMoreInteractions(persoonRepository);
        verifyNoMoreInteractions(autorisatieRepository);
        verifyNoMoreInteractions(afnemersIndicatieRepository);
        verifyNoMoreInteractions(dataSource);
        verifyNoMoreInteractions(jmsTemplate);
    }

    @Test
    public void testCreateInitAutorisatieRegelTabel() {
        service.laadInitAutorisatieRegelTabel();

        verify(autorisatieRepository).laadInitVullingAutTable();
        verifyNoMoreInteractions(persoonRepository);
        verifyNoMoreInteractions(autorisatieRepository);
        verifyNoMoreInteractions(afnemersIndicatieRepository);
        verifyNoMoreInteractions(dataSource);
        verifyNoMoreInteractions(jmsTemplate);
    }

    @Test
    public void testCreateAndFillInitVullingTable() throws ParseException {
        service.laadInitieleVullingTable();

        verify(persoonRepository).laadInitVullingTable();
        verifyNoMoreInteractions(persoonRepository);
        verifyNoMoreInteractions(autorisatieRepository);
        verifyNoMoreInteractions(afnemersIndicatieRepository);
        verifyNoMoreInteractions(dataSource);
        verifyNoMoreInteractions(jmsTemplate);
    }

    public static final class ConversieResultaatMatcher extends ArgumentMatcher<ConversieResultaat> {
        @Override
        public boolean matches(final Object argument) {
            return argument instanceof ConversieResultaat && ConversieResultaat.TE_VERZENDEN == argument;
        }
    }
}
