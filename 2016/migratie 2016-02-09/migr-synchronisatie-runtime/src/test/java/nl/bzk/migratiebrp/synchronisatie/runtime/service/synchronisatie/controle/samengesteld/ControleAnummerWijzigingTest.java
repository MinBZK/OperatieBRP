/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.samengesteld;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.inject.Named;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.lijst.LijstControle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.verzoek.VerzoekControle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.zoeker.PlZoeker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ControleAnummerWijzigingTest {

    @Mock
    @Named(value = "verzoekControleOudAnummerIsGevuld")
    private VerzoekControle verzoekControleOudAnummerIsGevuld;

    @Mock
    @Named(value = "plZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelVorigAnummer")
    private PlZoeker plZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelVorigAnummer;
    @Mock
    @Named(value = "plZoekerOpAnummerObvActueelAnummer")
    private PlZoeker plZoekerOpAnummerObvActueelAnummer;

    @Mock
    @Named(value = "lijstControleEen")
    private LijstControle lijstControleEen;
    @Mock
    @Named(value = "lijstControleGeen")
    private LijstControle lijstControleGeen;

    @Mock
    @Named(value = "plControleBijhoudingsPartijGelijk")
    private PlControle plControleBijhoudingsPartijGelijk;
    @Mock
    @Named(value = "plControleDezelfdePersoon")
    private PlControle plControleDezelfdePersoon;
    @Mock
    @Named(value = "plControleGevondenVersienummerKleiner")
    private PlControle plControleGevondenVersienummerKleiner;
    @Mock
    @Named(value = "plControleGevondenDatumtijdstempelOuder")
    private PlControle plControleGevondenDatumtijdstempelOuder;

    @InjectMocks
    private ControleAnummerWijziging subject;

    @Before
    public void setupLogging() {
        SynchronisatieLogging.init();
    }

    @Test
    public void testOk() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Arrays.asList(dbPersoonslijst), Collections.<BrpPersoonslijst>emptyList(), true, true, true, true, true, true, true);
        Assert.assertTrue(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void testVerzoekControleOudAnummerIsGevuldNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Arrays.asList(dbPersoonslijst), Collections.<BrpPersoonslijst>emptyList(), false, true, true, true, true, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void testLijstControleEenNok() {
        setup(Collections.<BrpPersoonslijst>emptyList(), Collections.<BrpPersoonslijst>emptyList(), true, false, true, true, true, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void testLijstControleGeenNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Arrays.asList(dbPersoonslijst), Arrays.asList(dbPersoonslijst), true, true, false, true, true, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void testPlControleBijhoudingsPartijGelijkNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Arrays.asList(dbPersoonslijst), Collections.<BrpPersoonslijst>emptyList(), true, true, true, false, true, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void testPlControleDezelfdePersoonNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Arrays.asList(dbPersoonslijst), Collections.<BrpPersoonslijst>emptyList(), true, true, true, true, false, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void testPlControleGevondenVersienummerKleinerNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Arrays.asList(dbPersoonslijst), Collections.<BrpPersoonslijst>emptyList(), true, true, true, true, true, false, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void testPlControleGevondenDatumtijdstempelOuderNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Arrays.asList(dbPersoonslijst), Collections.<BrpPersoonslijst>emptyList(), true, true, true, true, true, true, false);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    private void setup(
        final List<BrpPersoonslijst> resultPlZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelVorigAnummer,
        final List<BrpPersoonslijst> resultPlZoekerOpAnummerObvActueelAnummer,
        final boolean resultVerzoekControleOudAnummerIsGevuld,
        final boolean resultLijstControleEen,
        final boolean resultLijstControleGeen,
        final boolean resultPlControleBijhoudingsPartijGelijk,
        final boolean resultPlControleDezelfdePersoon,
        final boolean resultPlControleGevondenVersienummerKleiner,
        final boolean resultPlControleGevondenDatumtijdstempelOuder)
    {
        Mockito.when(plZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelVorigAnummer.zoek(Matchers.any(VerwerkingsContext.class))).thenReturn(
            resultPlZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelVorigAnummer);
        Mockito.when(plZoekerOpAnummerObvActueelAnummer.zoek(Matchers.any(VerwerkingsContext.class))).thenReturn(resultPlZoekerOpAnummerObvActueelAnummer);

        Mockito.when(verzoekControleOudAnummerIsGevuld.controleer(Matchers.any(SynchroniseerNaarBrpVerzoekBericht.class))).thenReturn(
            resultVerzoekControleOudAnummerIsGevuld);

        Mockito.when(lijstControleEen.controleer(Matchers.anyListOf(BrpPersoonslijst.class))).thenReturn(resultLijstControleEen);
        Mockito.when(lijstControleGeen.controleer(Matchers.anyListOf(BrpPersoonslijst.class))).thenReturn(resultLijstControleGeen);

        Mockito.when(plControleBijhoudingsPartijGelijk.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class)))
               .thenReturn(resultPlControleBijhoudingsPartijGelijk);
        Mockito.when(plControleDezelfdePersoon.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class))).thenReturn(
            resultPlControleDezelfdePersoon);
        Mockito.when(plControleGevondenVersienummerKleiner.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class)))
               .thenReturn(resultPlControleGevondenVersienummerKleiner);
        Mockito.when(plControleGevondenDatumtijdstempelOuder.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class)))
               .thenReturn(resultPlControleGevondenDatumtijdstempelOuder);
    }
}
