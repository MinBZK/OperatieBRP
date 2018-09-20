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
public class ControleEmigratieTest {

    @Mock
    @Named(value = "verzoekControleOudAnummerIsNietGevuld")
    private VerzoekControle verzoekControleOudAnummerIsNietGevuld;

    @Mock
    @Named(value = "plZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelAnummer")
    private PlZoeker plZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelAnummer;

    @Mock
    @Named(value = "lijstControleEen")
    private LijstControle lijstControleEen;

    @Mock
    @Named(value = "plControleBijhoudingsPartijOngelijk")
    private PlControle plControleBijhoudingsPartijOngelijk;
    @Mock
    @Named(value = "plControleBijhoudingsPartijGelijkRni")
    private PlControle plControleBijhoudingsPartijGelijkRni;
    @Mock
    @Named(value = "plControleVorigAnummerGelijk")
    private PlControle plControleVorigAnummerGelijk;
    @Mock
    @Named(value = "plControleHistorieAnummerGelijk")
    private PlControle plControleHistorieAnummerGelijk;
    @Mock
    @Named(value = "plControleDezelfdePersoon")
    private PlControle plControleDezelfdePersoon;
    @Mock
    @Named(value = "plControleAangebodenAdressenGelijk")
    private PlControle plControleAangebodenAdressenGelijk;
    @Mock
    @Named(value = "plControleGevondenVersienummerGelijkOfKleiner")
    private PlControle plControleGevondenVersienummerGelijkOfKleiner;
    @Mock
    @Named(value = "plControleGevondenDatumtijdstempelOuder")
    private PlControle plControleGevondenDatumtijdstempelOuder;

    @InjectMocks
    private ControleEmigratie subject;

    @Before
    public void setupLogging() {
        SynchronisatieLogging.init();
    }

    @Test
    public void ok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Arrays.asList(dbPersoonslijst), true, true, true, true, true, true, true, true, true, true);
        Assert.assertTrue(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void verzoekControleOudAnummerIsNietGevuldNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Arrays.asList(dbPersoonslijst), false, true, true, true, true, true, true, true, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void lijstControleEenNok() {
        setup(Collections.<BrpPersoonslijst>emptyList(), true, false, true, true, true, true, true, true, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void plControleBijhoudingsPartijOngelijkNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Arrays.asList(dbPersoonslijst), true, true, false, true, true, true, true, true, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void plControleBijhoudingsPartijGelijkRniNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Arrays.asList(dbPersoonslijst), true, true, true, true, true, true, true, true, true, false);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void plControleVorigAnummerGelijkNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Arrays.asList(dbPersoonslijst), true, true, true, false, true, true, true, true, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void plControleHistorieAnummerGelijkNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Arrays.asList(dbPersoonslijst), true, true, true, true, false, true, true, true, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void plControleDezelfdePersoonNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Arrays.asList(dbPersoonslijst), true, true, true, true, true, false, true, true, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void plControleAangebodenAdressenGelijkNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Arrays.asList(dbPersoonslijst), true, true, true, true, true, true, false, true, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void plControleGevondenVersienummerGelijkOfKleinerNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Arrays.asList(dbPersoonslijst), true, true, true, true, true, true, true, false, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void plControleGevondenDatumtijdstempelOuderNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Arrays.asList(dbPersoonslijst), true, true, true, true, true, true, true, true, false, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    private void setup(
        final List<BrpPersoonslijst> plZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelAnummerResult,
        final boolean verzoekControleOudAnummerIsNietGevuldResult,
        final boolean lijstControleEenResult,
        final boolean plControleBijhoudingsPartijOngelijkResult,
        final boolean plControleVorigAnummerGelijkResult,
        final boolean plControleHistorieAnummerGelijkResult,
        final boolean plControleDezelfdePersoonResult,
        final boolean plControleGevondenAdressenKomenVoorInHistorieAangebodenAdressenResult,
        final boolean plControleGevondenVersienummerGelijkOfKleinerResult,
        final boolean plControleGevondenDatumtijdstempelOuderResult,
        final boolean plControleBijhoudingsPartijOngelijkRniResult)
    {
        Mockito.when(plZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelAnummer.zoek(Matchers.any(VerwerkingsContext.class))).thenReturn(
            plZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelAnummerResult);

        Mockito.when(verzoekControleOudAnummerIsNietGevuld.controleer(Matchers.any(SynchroniseerNaarBrpVerzoekBericht.class))).thenReturn(
            verzoekControleOudAnummerIsNietGevuldResult);

        Mockito.when(lijstControleEen.controleer(Matchers.anyListOf(BrpPersoonslijst.class))).thenReturn(lijstControleEenResult);

        Mockito.when(plControleBijhoudingsPartijOngelijk.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class)))
               .thenReturn(plControleBijhoudingsPartijOngelijkResult);
        Mockito.when(plControleBijhoudingsPartijGelijkRni.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class)))
               .thenReturn(plControleBijhoudingsPartijOngelijkRniResult);
        Mockito.when(plControleVorigAnummerGelijk.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class))).thenReturn(
            plControleVorigAnummerGelijkResult);
        Mockito.when(plControleHistorieAnummerGelijk.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class))).thenReturn(
            plControleHistorieAnummerGelijkResult);
        Mockito.when(plControleDezelfdePersoon.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class))).thenReturn(
            plControleDezelfdePersoonResult);
        Mockito.when(plControleAangebodenAdressenGelijk.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class)))
               .thenReturn(plControleGevondenAdressenKomenVoorInHistorieAangebodenAdressenResult);
        Mockito.when(
                   plControleGevondenVersienummerGelijkOfKleiner.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class)))
               .thenReturn(plControleGevondenVersienummerGelijkOfKleinerResult);
        Mockito.when(plControleGevondenDatumtijdstempelOuder.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class)))
               .thenReturn(plControleGevondenDatumtijdstempelOuderResult);
    }
}
