/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import java.util.HashMap;
import java.util.Map;

import nl.bzk.brp.bevraging.business.berichtcmds.BrpBerichtCommand;
import nl.bzk.brp.bevraging.business.dto.BrpBerichtContext;
import org.aspectj.lang.JoinPoint;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test voor de {@link StapLogger} class.
 */
public class StapLoggerTest {

    private static Logger logger;

    @Mock
    private JoinPoint     joinPointMock;

    /**
     * Test of een bericht ook werkelijk wordt gelogd (zonder excepties) indien de joinPoint correct is 'gevuld'.
     */
    @Test
    public void testLog() {
        initJoinPointMock(2L);
        StapLogger stapLogger = new StapLogger();
        stapLogger.log(joinPointMock);

        Mockito.verify(logger).debug(Matchers.anyString());
    }

    /**
     * Initialiseert de {@link JoinPoint} mock door de argumenten te zetten naar een array met daarin slechts
     * een enkel {@link BrpBerichtCommand}, welke een {@link BrpBerichtContext} bevat met de opgegeven id.
     *
     * @param id de id van de bericht context in het bericht command dat als argument wordt gemockt.
     */
    private void initJoinPointMock(final long id) {
        BrpBerichtContext context = new BrpBerichtContext();
        context.setBerichtId(id);
        BrpBerichtCommand command = Mockito.mock(BrpBerichtCommand.class);
        Mockito.when(command.getContext()).thenReturn(context);
        Mockito.when(joinPointMock.getArgs()).thenReturn(new BrpBerichtCommand[] { command });
    }

    /**
     * Initialiseert de mocks (voor zover dit niet reeds is gedaan in de {@link #initClassLogger()} methode.
     */
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(joinPointMock.getTarget()).thenReturn(this);
        Mockito.reset(logger);
    }

    /**
     * Initialiseert de logger voor de {@link StapLogger} class, door voor die class reeds een mock versie
     * van een logger aan de loggermap van de {@link LoggerFactory} toe te voegen. Dit zorgt er voor dat de
     * {@link StapLogger} dus de mock als logger krijgt en niet een eigen instantie waar we vanuit de test
     * niet bij kunnen.
     */
    @BeforeClass
    public static void initClassLogger() {
        Map<String, Logger> loggers = new HashMap<String, Logger>();
        logger = Mockito.mock(Logger.class);
        loggers.put(StapLogger.class.getName(), logger);

        ReflectionTestUtils.setField(LoggerFactory.getILoggerFactory(), "loggerMap", loggers);
    }

}
