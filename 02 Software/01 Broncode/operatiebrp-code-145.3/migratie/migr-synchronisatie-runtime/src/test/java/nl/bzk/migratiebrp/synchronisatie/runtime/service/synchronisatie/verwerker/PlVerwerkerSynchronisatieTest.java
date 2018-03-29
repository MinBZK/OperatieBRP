/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker;

import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.Controle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleUitkomst;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.pl.PlService;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test beslisboom.
 */
@RunWith(MockitoJUnitRunner.class)
public class PlVerwerkerSynchronisatieTest {

    @Mock
    private Controle controleReguliereWijziging;
    @Mock
    private Controle controleReguliereVerhuizing;
    @Mock
    private Controle controleEmigratie;
    @Mock
    private Controle controleAnummerWijziging;
    @Mock
    private Controle controleWijzigingOverledenPersoon;
    @Mock
    private Controle controleNieuwePersoonslijst;
    @Mock
    private Controle controlePersoonslijstIsOuder;
    @Mock
    private Controle controleBevatBlokkeringsinformatie;
    @Mock
    private Controle controlePersoonslijstIsGelijk;
    @Mock
    private PlService plService;

    private PlVerwerkerSynchronisatie subject;

    @Before
    public void setup() {
        SynchronisatieLogging.init();
        subject = new PlVerwerkerSynchronisatie(controleReguliereWijziging,
                controleReguliereVerhuizing,
                controleEmigratie,
                controleAnummerWijziging,
                controleWijzigingOverledenPersoon,
                controleNieuwePersoonslijst,
                controlePersoonslijstIsOuder,
                controleBevatBlokkeringsinformatie,
                controlePersoonslijstIsGelijk,
                plService);
    }

    @Test
    public void testReguliereWijziging() {
        setupMock(true, false, false, false, false, false, false, false, true);
        Assert.assertTrue("pl moet vervangen worden", ControleUitkomst.VERVANGEN.equals(subject.controle(new VerwerkingsContext(null, null, null, null))));
    }

    @Test
    public void testReguliereVerhuizing() {
        setupMock(false, true, false, false, false, false, false, false, true);
        Assert.assertTrue("pl moet vervangen worden", ControleUitkomst.VERVANGEN.equals(subject.controle(new VerwerkingsContext(null, null, null, null))));
    }

    @Test
    public void testEmigratie() {
        setupMock(false, false, true, false, false, false, false, false, true);
        Assert.assertTrue("pl moet vervangen worden", ControleUitkomst.VERVANGEN.equals(subject.controle(new VerwerkingsContext(null, null, null, null))));
    }

    @Test
    public void testAnummerWijziging() {
        setupMock(false, false, false, true, false, false, false, false, true);
        Assert.assertTrue("pl moet vervangen worden", ControleUitkomst.VERVANGEN.equals(subject.controle(new VerwerkingsContext(null, null, null, null))));
    }

    @Test
    public void testWijzigingOverledenPersoon() {
        setupMock(false, false, false, false, true, false, false, false, true);
        Assert.assertTrue("pl moet vervangen worden", ControleUitkomst.VERVANGEN.equals(subject.controle(new VerwerkingsContext(null, null, null, null))));
    }

    @Test
    public void testNieuwePersoonslijst() {
        setupMock(false, false, false, false, false, true, false, false, true);
        Assert.assertTrue(
                "pl moet toegevoegd worden",
                ControleUitkomst.TOEVOEGEN.equals(subject.controle(new VerwerkingsContext(null, null, null, null))));
    }

    @Test
    public void testPersoonslijstIsOuder() {
        setupMock(false, false, false, false, false, false, true, false, false);
        Assert.assertTrue("pl moet genegeerd worden", ControleUitkomst.NEGEREN.equals(subject.controle(new VerwerkingsContext(null, null, null, null))));
    }

    @Test
    public void testBevatBlokkeringsinformatie() {
        setupMock(false, false, false, false, false, false, false, true, false);
        Assert.assertTrue("pl moet genegeerd worden", ControleUitkomst.NEGEREN.equals(subject.controle(new VerwerkingsContext(null, null, null, null))));
    }

    @Test
    public void testPersoonslijstIsGelijk() {
        setupMock(false, false, false, false, false, false, false, false, true);
        Assert.assertTrue("pl moet genegeerd worden", ControleUitkomst.NEGEREN.equals(subject.controle(new VerwerkingsContext(null, null, null, null))));
    }

    @Test
    public void testIsOnduidelijk() {
        setupMock(false, false, false, false, false, false, false, false, false);
        Assert.assertTrue("pl is onduidelijk", ControleUitkomst.ONDUIDELIJK.equals(subject.controle(new VerwerkingsContext(null, null, null, null))));
    }

    private void setupMock(
            final boolean testControleReguliereWijziging,
            final boolean testControleReguliereVerhuizing,
            final boolean testControleEmigratie,
            final boolean testControleANummerWijziging,
            final boolean testControleWijzigingenOverledenPersoon,
            final boolean testControleNieuwePeroonslijst,
            final boolean testControlePersoonslijstIsOuder,
            final boolean testControleBevatBlokkeringsinformatie,
            final boolean testControlePersoonslijstIsGelijk) {
        Mockito.when(controleReguliereWijziging.controleer(Matchers.any(VerwerkingsContext.class))).thenReturn(testControleReguliereWijziging);
        Mockito.when(controleReguliereVerhuizing.controleer(Matchers.any(VerwerkingsContext.class))).thenReturn(testControleReguliereVerhuizing);
        Mockito.when(controleEmigratie.controleer(Matchers.any(VerwerkingsContext.class))).thenReturn(testControleEmigratie);
        Mockito.when(controleAnummerWijziging.controleer(Matchers.any(VerwerkingsContext.class))).thenReturn(testControleANummerWijziging);
        Mockito.when(controleWijzigingOverledenPersoon.controleer(Matchers.any(VerwerkingsContext.class)))
                .thenReturn(testControleWijzigingenOverledenPersoon);
        Mockito.when(controleNieuwePersoonslijst.controleer(Matchers.any(VerwerkingsContext.class))).thenReturn(testControleNieuwePeroonslijst);
        Mockito.when(controlePersoonslijstIsOuder.controleer(Matchers.any(VerwerkingsContext.class))).thenReturn(testControlePersoonslijstIsOuder);
        Mockito.when(controleBevatBlokkeringsinformatie.controleer(Matchers.any(VerwerkingsContext.class)))
                .thenReturn(testControleBevatBlokkeringsinformatie);
        Mockito.when(controlePersoonslijstIsGelijk.controleer(Matchers.any(VerwerkingsContext.class))).thenReturn(testControlePersoonslijstIsGelijk);

    }
}
