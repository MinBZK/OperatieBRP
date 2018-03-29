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
public class ControleAnummerWijzigingTest {

    @Mock
    private PlZoeker plZoekerObvActueelVorigAnummer;
    @Mock
    private PlZoeker plZoekerObvActueelAnummer;
    @Mock
    private PlZoeker plZoekerObvActueelBsn;
    @Mock
    private LijstControle lijstControleEen;
    @Mock
    private LijstControle lijstControleGeen;
    @Mock
    private LijstControle lijstControleGeenBsn;
    @Mock
    private PlControle plControleGevondenBlokkeringssituatieIsJuist;
    @Mock
    private PlControle plControleBijhoudingsPartijGelijk;
    @Mock
    private PlControle plControleBijhoudingsPartijGelijkVerzendendeGemeente;
    @Mock
    private PlControle plControleDezelfdePersoon;
    @Mock
    private PlControle plControleActueelBsnGelijk;
    @Mock
    private PlControle plControleGevondenVersienummerKleiner;
    @Mock
    private PlControle plControleGevondenDatumtijdstempelOuder;

    private ControleAnummerWijziging subject;

    @Before
    public void setupLogging() {
        SynchronisatieLogging.init();
        subject = new ControleAnummerWijziging(null, null);
        ReflectionTestUtils.setField(subject, "plZoekerObvActueelVorigAnummer", plZoekerObvActueelVorigAnummer);
        ReflectionTestUtils.setField(subject, "plZoekerObvActueelAnummer", plZoekerObvActueelAnummer);
        ReflectionTestUtils.setField(subject, "plZoekerObvActueelBsn", plZoekerObvActueelBsn);
        ReflectionTestUtils.setField(subject, "lijstControleEen", lijstControleEen);
        ReflectionTestUtils.setField(subject, "lijstControleGeen", lijstControleGeen);
        ReflectionTestUtils.setField(subject, "lijstControleGeenBsn", lijstControleGeenBsn);
        ReflectionTestUtils.setField(subject, "plControleGevondenBlokkeringssituatieIsJuist", plControleGevondenBlokkeringssituatieIsJuist);
        ReflectionTestUtils.setField(subject, "plControleBijhoudingsPartijGelijk", plControleBijhoudingsPartijGelijk);
        ReflectionTestUtils.setField(subject, "plControleBijhoudingsPartijGelijkVerzendendeGemeente", plControleBijhoudingsPartijGelijkVerzendendeGemeente);
        ReflectionTestUtils.setField(subject, "plControleDezelfdePersoon", plControleDezelfdePersoon);
        ReflectionTestUtils.setField(subject, "plControleActueelBsnGelijk", plControleActueelBsnGelijk);
        ReflectionTestUtils.setField(subject, "plControleGevondenVersienummerKleiner", plControleGevondenVersienummerKleiner);
        ReflectionTestUtils.setField(subject, "plControleGevondenDatumtijdstempelOuder", plControleGevondenDatumtijdstempelOuder);
    }

    @Test
    public void testOk() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Collections.singletonList(dbPersoonslijst), Collections.emptyList(), Collections.singletonList(dbPersoonslijst),
                true, true, true, true, true, true, true, true, true, true);
        Assert.assertTrue(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void testLijstControleEenNok() {
        setup(Collections.emptyList(), Collections.emptyList(), Collections.emptyList(),
                false, true, true, true, true, true, true, true, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void testLijstControleGeenNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Collections.singletonList(dbPersoonslijst), Collections.singletonList(dbPersoonslijst), Collections.singletonList(dbPersoonslijst),
                true, false, true, true, true, true, true, true, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void testVerzendendeGemeente() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Collections.singletonList(dbPersoonslijst), Collections.emptyList(), Collections.singletonList(dbPersoonslijst),
                true, true, false, true, true, true, true, true, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void bijhoudingsituatieNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Collections.singletonList(dbPersoonslijst), Collections.emptyList(), Collections.singletonList(dbPersoonslijst),
                true, true, true, false, true, true, true, true, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void testPlControleBijhoudingsPartijGelijkNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Collections.singletonList(dbPersoonslijst), Collections.emptyList(), Collections.singletonList(dbPersoonslijst),
                true, true, true, true, false, true, true, true, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void testPlControleDezelfdePersoonNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Collections.singletonList(dbPersoonslijst), Collections.emptyList(), Collections.singletonList(dbPersoonslijst),
                true, true, true, true, true, false, true, true, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void bsnOk() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Collections.singletonList(dbPersoonslijst), Collections.emptyList(), Collections.emptyList(),
                true, true, true, true, true, true, false, true, true, true);
        Assert.assertTrue(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void bsnNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Collections.singletonList(dbPersoonslijst), Collections.emptyList(), Collections.singletonList(dbPersoonslijst),
                true, true, true, true, true, true, false, false, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void testPlControleGevondenVersienummerKleinerNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Collections.singletonList(dbPersoonslijst), Collections.emptyList(), Collections.singletonList(dbPersoonslijst),
                true, true, true, true, true, true, true, true, false, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void testPlControleGevondenDatumtijdstempelOuderNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Collections.singletonList(dbPersoonslijst), Collections.emptyList(), Collections.singletonList(dbPersoonslijst),
                true, true, true, true, true, true, true, true, true, false);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    private void setup(
            final List<BrpPersoonslijst> resultPlZoekerObvActueelVorigAnummer,
            final List<BrpPersoonslijst> resultPlZoekerObvActueelAnummer,
            final List<BrpPersoonslijst> resultPlZoekerObvActueelBsn,
            final boolean resultLijstControleEen,
            final boolean resultLijstControleGeen,
            final boolean resultControleVerzendendeGemeente,
            final boolean resultControleBijhoudingssituatie,
            final boolean resultPlControleBijhoudingsPartijGelijk,
            final boolean resultPlControleDezelfdePersoon,
            final boolean resultLijstControleGeenBsn,
            final boolean resultPlControleActueelBsnGelijk,
            final boolean resultPlControleGevondenVersienummerKleiner,
            final boolean resultPlControleGevondenDatumtijdstempelOuder) {
        Mockito.when(plZoekerObvActueelVorigAnummer.zoek(Matchers.any(VerwerkingsContext.class))).thenReturn(resultPlZoekerObvActueelVorigAnummer);
        Mockito.when(plZoekerObvActueelAnummer.zoek(Matchers.any(VerwerkingsContext.class))).thenReturn(resultPlZoekerObvActueelAnummer);
        Mockito.when(plZoekerObvActueelBsn.zoek(Matchers.any(VerwerkingsContext.class))).thenReturn(resultPlZoekerObvActueelBsn);

        Mockito.when(lijstControleEen.controleer(Matchers.anyListOf(BrpPersoonslijst.class))).thenReturn(resultLijstControleEen);
        Mockito.when(lijstControleGeen.controleer(Matchers.anyListOf(BrpPersoonslijst.class))).thenReturn(resultLijstControleGeen);
        Mockito.when(
                plControleBijhoudingsPartijGelijkVerzendendeGemeente.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class)))
                .thenReturn(resultControleVerzendendeGemeente);
        Mockito.when(plControleGevondenBlokkeringssituatieIsJuist.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class)))
                .thenReturn(resultControleBijhoudingssituatie);
        Mockito.when(plControleBijhoudingsPartijGelijk.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class)))
                .thenReturn(resultPlControleBijhoudingsPartijGelijk);
        Mockito.when(plControleDezelfdePersoon.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class)))
                .thenReturn(resultPlControleDezelfdePersoon);
        Mockito.when(plControleActueelBsnGelijk.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class)))
                .thenReturn(resultPlControleActueelBsnGelijk);
        Mockito.when(lijstControleGeenBsn.controleer(Matchers.anyListOf(BrpPersoonslijst.class))).thenReturn(resultLijstControleGeenBsn);
        Mockito.when(plControleGevondenVersienummerKleiner.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class)))
                .thenReturn(resultPlControleGevondenVersienummerKleiner);
        Mockito.when(plControleGevondenDatumtijdstempelOuder.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class)))
                .thenReturn(resultPlControleGevondenDatumtijdstempelOuder);
    }
}
