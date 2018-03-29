/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.samengesteld;

import java.util.Collections;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.lijst.LijstControle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.zoeker.PlZoeker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class ControleNieuwePersoonslijstTest {

    @Mock
    private PlZoeker plZoekerOpAnummerObvActueelAnummer;

    @Mock
    private PlZoeker plZoekerOpAnummerObvActueelBurgerServicenummer;

    @Mock
    private LijstControle lijstControleGeen;

    @Mock
    private LijstControle lijstControleGeenBsn;

    @Mock
    private PlControle plControleBijhoudingsPartijGelijkVerzendendeGemeente;

    @Mock
    private PlControle plControleAnummerHistorischGelijk;

    @Mock
    private PlControle plControleNietOpgeschortMetCodeF;

    private ControleNieuwePersoonslijst subject;

    @Before
    public void setupLogging() {
        SynchronisatieLogging.init();
        subject = new ControleNieuwePersoonslijst(null);
        ReflectionTestUtils.setField(subject, "plZoekerObvActueelAnummer", plZoekerOpAnummerObvActueelAnummer);
        ReflectionTestUtils.setField(subject, "plZoekerObvActueelBurgerServicenummer", plZoekerOpAnummerObvActueelBurgerServicenummer);
        ReflectionTestUtils.setField(subject, "lijstControleGeen", lijstControleGeen);
        ReflectionTestUtils.setField(subject, "lijstControleGeenBsn", lijstControleGeenBsn);
        ReflectionTestUtils.setField(subject, "plControleBijhoudingsPartijGelijkVerzendendeGemeente", plControleBijhoudingsPartijGelijkVerzendendeGemeente);
        ReflectionTestUtils.setField(subject, "plControleAnummerHistorischGelijk", plControleAnummerHistorischGelijk);
        ReflectionTestUtils.setField(subject, "plControleNietOpgeschortMetCodeF", plControleNietOpgeschortMetCodeF);
    }

    @Test
    public void testOk() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Collections.singletonList(dbPersoonslijst), Collections.emptyList(), true, true, true, true, true);
        Assert.assertTrue(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void testLijstControleGeenNok() {
        setup(Collections.emptyList(), Collections.emptyList(), false, true, true, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void testLijstControleGeenBsnNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Collections.emptyList(), Collections.singletonList(dbPersoonslijst), true, false, true, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void testVerzendendePartijNok() {
        setup(Collections.emptyList(), Collections.emptyList(), true, true, false, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void testPlControleAnummerHistorischGelijkNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Collections.singletonList(dbPersoonslijst), Collections.emptyList(), true, true, true, false, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void testPlControleNietOpgeschortRedenFNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        final Lo3Persoonslijst lo3Persoonslijst = new Lo3PersoonslijstBuilder().build();
        setup(Collections.singletonList(dbPersoonslijst), Collections.emptyList(), true, true, true, true, false);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, lo3Persoonslijst, null)));
    }

    private void setup(
            final List<BrpPersoonslijst> plZoekerOpAnummerObvActueelAnummerResult,
            final List<BrpPersoonslijst> plZoekerOpAnummerObvActueelBurgerServicenummerResult,
            final boolean lijstControleGeenResult,
            final boolean lijstControleGeenBsnResult,
            final boolean resultControleVerzendendeGemeente,
            final boolean plControleAnummerHistorischGelijkResult,
            final boolean plControleNietOpgeschortMetCodeFResult) {
        Mockito.when(plZoekerOpAnummerObvActueelAnummer.zoek(Matchers.any(VerwerkingsContext.class))).thenReturn(plZoekerOpAnummerObvActueelAnummerResult);
        Mockito.when(plZoekerOpAnummerObvActueelBurgerServicenummer.zoek(Matchers.any(VerwerkingsContext.class)))
                .thenReturn(plZoekerOpAnummerObvActueelBurgerServicenummerResult);

        Mockito.when(lijstControleGeen.controleer(Matchers.anyListOf(BrpPersoonslijst.class))).thenReturn(lijstControleGeenResult);
        Mockito.when(lijstControleGeenBsn.controleer(Matchers.anyListOf(BrpPersoonslijst.class))).thenReturn(lijstControleGeenBsnResult);

        Mockito.when(
                plControleBijhoudingsPartijGelijkVerzendendeGemeente.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst
                        .class)))
                .thenReturn(resultControleVerzendendeGemeente);

        Mockito.when(plControleAnummerHistorischGelijk.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class)))
                .thenReturn(plControleAnummerHistorischGelijkResult);

        Mockito.when(plControleNietOpgeschortMetCodeF.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class)))
                .thenReturn(plControleNietOpgeschortMetCodeFResult);
    }
}
