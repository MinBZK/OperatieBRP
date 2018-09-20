/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.controle.runtime;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import nl.moderniseringgba.migratie.controle.impl.ControleServiceImpl;
import nl.moderniseringgba.migratie.controle.impl.SelectieServiceImpl;
import nl.moderniseringgba.migratie.controle.rapport.ControleRapport;
import nl.moderniseringgba.migratie.controle.rapport.Opties;

import org.apache.commons.cli.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Deze class test de main. Dit wordt met powermock gedaan omdat er dingen gemockt moeten worden die met mockito niet
 * gemockt kunnen worden. Helaas werkt powermock niet goed samen met Emma, de coverage lijkt 0% maar is veel hoger! Zie
 * issue http://code.google.com/p/powermock/issues/detail?id=402
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ Main.class, ControleServiceImpl.class, SelectieServiceImpl.class })
public class MainTest {

    @Test
    public void testMainHappy() {
        final String[] args =
                new String[] { "-config", "config.properties", "-vanaf", "01012002", "-tot", "01012013", "-type",
                        "ALLE_PERSONEN", "-niveau", "VOLLEDIGE_PL", };
        try {
            System.setProperty("config", "src/test/resources/config.properties");
            final ClassPathXmlApplicationContext appContextMock =
                    PowerMockito.mock(ClassPathXmlApplicationContext.class);
            PowerMockito.whenNew(ClassPathXmlApplicationContext.class).withParameterTypes(String[].class)
                    .withArguments(Matchers.any(String[].class)).thenReturn(appContextMock);

            final ControleServiceImpl controleService = PowerMockito.mock(ControleServiceImpl.class);
            PowerMockito.when(appContextMock.getBean("controleServiceImpl")).thenReturn(controleService);

            final SelectieServiceImpl selectieService = PowerMockito.mock(SelectieServiceImpl.class);
            PowerMockito.when(appContextMock.getBean("selectieServiceImpl")).thenReturn(selectieService);

            PowerMockito.when(
                    selectieService.selecteerPLen(Matchers.any(Opties.class), Matchers.any(ControleRapport.class)))
                    .thenReturn(new HashSet<Long>());

            Main.setSpringConfig("classpath:test.xml");
            Main.main(args);

            Mockito.verify(controleService).controleerPLen(Matchers.any(ArrayList.class), Matchers.any(Opties.class),
                    Matchers.any(ControleRapport.class));

            Mockito.verify(selectieService).selecteerPLen(Matchers.any(Opties.class),
                    Matchers.any(ControleRapport.class));

            PowerMockito.verifyNoMoreInteractions(controleService, selectieService);
        } catch (final Exception e) {
            e.printStackTrace();
            fail("Exception opgetreden bij het draaien van init vulling.");
        }
    }

    @Test
    public void testMainNoConfig() {
        System.setProperty("config", "config.properties");
        final String[] args = new String[] {};
        Main.setSpringConfig("classpath:controle-beans-test.xml");
        try {
            Main.main(args);
            fail("Er had een IllegalArgumentException op moeten treden.");
        } catch (final Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void testMainRequiredOptions() throws ParseException {
        final String[] args = new String[] { "-config", "config.properties" };

        System.setProperty("config", "src/test/resources/config.properties");
        Main.setSpringConfig("classpath:test.xml");
        try {
            Main.main(args);
            fail("Er had een ParseException op moeten treden.");
        } catch (final Exception e) {
            assertTrue(e instanceof ParseException);
        }
    }

    @Test
    public void testMainConfigNotFound() throws IOException {
        final String[] args =
                new String[] { "-config", "sconfig.properties", "-vanaf", "01012002", "-tot", "01012013", "-type",
                        "ALLE_PERSONEN", "-niveau", "VOLLEDIGE_PL", };

        System.setProperty("config", "src/test/resources/config.properties");
        Main.setSpringConfig("classpath:test.xml");
        try {
            Main.main(args);
            fail("Er had een IOException op moeten treden.");
        } catch (final Exception e) {
            assertTrue(e instanceof IOException);
        }
    }
}
