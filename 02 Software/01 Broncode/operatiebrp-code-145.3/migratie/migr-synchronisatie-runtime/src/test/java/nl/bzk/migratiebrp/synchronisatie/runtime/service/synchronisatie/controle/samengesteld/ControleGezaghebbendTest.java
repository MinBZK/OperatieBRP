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
public class ControleGezaghebbendTest {

    @Mock
    private PlZoeker plZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelAnummer;
    @Mock
    private LijstControle lijstControleEen;
    @Mock
    private PlControle plControleBijhoudingsPartijGelijk;
    @Mock
    private PlControle plControleGevondenVersienummerGelijkOfKleiner;
    @Mock
    private PlControle plControleGevondenDatumtijdstempelGelijkOfOuder;

    private ControleGezaghebbend subject;

    @Before
    public void setupLogging() {
        SynchronisatieLogging.init();
        subject = new ControleGezaghebbend(null);
        ReflectionTestUtils
                .setField(subject, "plZoekerObvActueelAnummer", plZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelAnummer);
        ReflectionTestUtils.setField(subject, "lijstControleEen", lijstControleEen);
        ReflectionTestUtils.setField(subject, "plControleBijhoudingsPartijGelijk", plControleBijhoudingsPartijGelijk);
        ReflectionTestUtils.setField(subject, "plControleGevondenVersienummerGelijkOfKleiner", plControleGevondenVersienummerGelijkOfKleiner);
        ReflectionTestUtils.setField(subject, "plControleGevondenDatumtijdstempelGelijkOfOuder", plControleGevondenDatumtijdstempelGelijkOfOuder);

    }

    @Test
    public void testOk() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Collections.singletonList(dbPersoonslijst), true, true, true, true);
        Assert.assertTrue(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void testLijstControleEenNok() {
        setup(Collections.emptyList(), false, true, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void testPlControleBijhoudingsPartijGelijkNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Collections.singletonList(dbPersoonslijst), true, false, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void testPlControleGevondenVersienummerGelijkOfKleinerNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Collections.singletonList(dbPersoonslijst), true, true, false, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void testPlControleGevondenDatumtijdstempelGelijkOfOuderNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Collections.singletonList(dbPersoonslijst), true, true, true, false);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    private void setup(
            final List<BrpPersoonslijst> plZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelAnummerResult,
            final boolean lijstControleEenResult,
            final boolean plControleBijhoudingsPartijGelijkResult,
            final boolean plControleGevondenVersienummerGelijkOfKleinerResult,
            final boolean plControleGevondenDatumtijdstempelGelijkOfOuderResult) {
        Mockito.when(plZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelAnummer.zoek(Matchers.any(VerwerkingsContext.class))).thenReturn(
                plZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelAnummerResult);

        Mockito.when(lijstControleEen.controleer(Matchers.anyListOf(BrpPersoonslijst.class))).thenReturn(lijstControleEenResult);

        Mockito.when(plControleBijhoudingsPartijGelijk.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class)))
                .thenReturn(plControleBijhoudingsPartijGelijkResult);
        Mockito.when(
                plControleGevondenVersienummerGelijkOfKleiner.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class)))
                .thenReturn(plControleGevondenVersienummerGelijkOfKleinerResult);
        Mockito.when(
                plControleGevondenDatumtijdstempelGelijkOfOuder.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class)))
                .thenReturn(plControleGevondenDatumtijdstempelGelijkOfOuderResult);
    }
}
