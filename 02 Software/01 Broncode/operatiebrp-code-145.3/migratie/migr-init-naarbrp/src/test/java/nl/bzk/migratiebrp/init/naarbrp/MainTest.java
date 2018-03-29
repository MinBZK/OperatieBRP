/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.time.LocalDateTime;
import nl.bzk.migratiebrp.init.naarbrp.service.InitieleVullingService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MainTest {

    private final Main.Runner main;
    private final InitieleVullingService initieleVullingService;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream out = System.out;
    private final PrintStream err = System.err;

    public MainTest() {
        initieleVullingService = mock(InitieleVullingService.class);
        main = new Main.Runner(initieleVullingService);
    }

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(out);
        System.setErr(err);
    }

    @Test
    public void testGeenArgument() {
        main.parseAndExecute(new String[]{});
        assertThat(outContent.toString(), containsString("usage: Geen argument meegegeven"));
    }

    @Test
    public void testOnbekendeOptie() {
        main.parseAndExecute(new String[]{"-unknown"});
        assertThat(outContent.toString(), containsString("usage: main"));
    }

    @Test
    public void testOnbekendArgument() {
        main.parseAndExecute(new String[]{"unknown"});
        assertThat(outContent.toString(), containsString("usage: Onbekend argument"));
    }

    @Test
    public void testLaadPers() throws ParseException {
        main.parseAndExecute(new String[]{"-laad_pers"});
        verify(initieleVullingService, times(1)).laadInitieleVullingTable();
        verifyNoMoreInteractions(initieleVullingService);
    }

    @Test
    public void testLaadAut() throws ParseException {
        main.parseAndExecute(new String[]{"-laad_aut"});
        verify(initieleVullingService, times(1)).laadInitAutorisatieRegelTabel();
        verifyNoMoreInteractions(initieleVullingService);
    }

    @Test
    public void testLaadAfn() throws ParseException {
        main.parseAndExecute(new String[]{"-laad_afn"});
        verify(initieleVullingService, times(1)).laadInitAfnemersIndicatieTabel();
        verifyNoMoreInteractions(initieleVullingService);
    }

    @Test
    public void testLaadProt() {
        main.parseAndExecute(new String[]{"-laad_prot"});
        verify(initieleVullingService, times(1)).laadInitProtocolleringTabel();
        verifyNoMoreInteractions(initieleVullingService);
    }

    @Test
    public void testLaadProtVanaf() {
        main.parseAndExecute(new String[]{"-laad_prot", "2016-01-01T12:00:00.000"});
        verify(initieleVullingService, times(1)).laadInitProtocolleringTabel(LocalDateTime.of(2016, 1, 1, 12, 0));
        verifyNoMoreInteractions(initieleVullingService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLaadProtVerkeerdArgument() {
        main.parseAndExecute(new String[]{"-laad_prot", "bla"});
    }

    @Test
    public void testLaadProtVerkeerdArgumentErrorMessage() {
        try {
            main.parseAndExecute(new String[]{"-laad_prot", "bla"});
        } catch (final IllegalArgumentException ex) {
            assertEquals(ex.getMessage(), "De opgegeven vanafdatum 'bla' voldoet niet aan de formattering (yyyy-MM-dd'T'HH:mm:ss.SSS).");
        }
    }

    @Test
    public void testSyncPers() throws ParseException {
        main.parseAndExecute(new String[]{"-sync_pers"});
        verify(initieleVullingService, times(1)).synchroniseerPersonen();
        verifyNoMoreInteractions(initieleVullingService);
    }

    @Test
    public void testSyncAut() throws ParseException {
        main.parseAndExecute(new String[]{"-sync_aut"});
        verify(initieleVullingService, times(1)).synchroniseerAutorisaties();
        verifyNoMoreInteractions(initieleVullingService);
    }

    @Test
    public void testSyncAfn() throws ParseException {
        main.parseAndExecute(new String[]{"-sync_afn"});
        verify(initieleVullingService, times(1)).synchroniseerAfnemerIndicaties();
        verifyNoMoreInteractions(initieleVullingService);
    }

    @Test
    public void testSyncProt() {
        main.parseAndExecute(new String[]{"-sync_prot"});
        verify(initieleVullingService, times(1)).synchroniseerProtocollering();
        verifyNoMoreInteractions(initieleVullingService);
    }
}
