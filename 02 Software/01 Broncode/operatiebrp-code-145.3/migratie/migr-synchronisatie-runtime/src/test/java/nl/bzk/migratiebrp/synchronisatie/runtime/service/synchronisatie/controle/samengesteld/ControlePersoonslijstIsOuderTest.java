/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.samengesteld;

import java.util.Collections;
import java.util.List;
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
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class ControlePersoonslijstIsOuderTest {

    @Mock
    private VerzoekControle verzoekControleBerichtVanSoortLg01;
    @Mock
    private PlZoeker plZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelAnummer;
    @Mock
    private LijstControle lijstControleEen;
    @Mock
    private PlControle plControleGevondenBlokkeringssituatieIsJuist;
    @Mock
    private PlControle plControleAangebodenAdressenKomenVoorInGevondenAdressen;
    @Mock
    private PlControle plControleVorigAnummerGelijk;
    @Mock
    private PlControle plControleHistorieAnummerGelijk;
    @Mock
    private PlControle plControleDezelfdePersoon;
    @Mock
    private PlControle plControleGevondenVersienummerGroter;
    @Mock
    private PlControle plControleGevondenDatumtijdstempelNieuwer;

    private ControlePersoonslijstIsOuder subject;

    @Before
    public void setupLogging() {
        SynchronisatieLogging.init();
        subject = new ControlePersoonslijstIsOuder(null, null);
        ReflectionTestUtils.setField(subject, "verzoekControleBerichtVanSoortLg01", verzoekControleBerichtVanSoortLg01);
        ReflectionTestUtils
                .setField(subject, "plZoekerObvActueelAnummer", plZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelAnummer);
        ReflectionTestUtils.setField(subject, "lijstControleEen", lijstControleEen);
        ReflectionTestUtils.setField(subject, "plControleGevondenBlokkeringssituatieIsJuist", plControleGevondenBlokkeringssituatieIsJuist);
        ReflectionTestUtils.setField(subject, "plControleVorigAnummerGelijk", plControleVorigAnummerGelijk);
        ReflectionTestUtils.setField(subject, "plControleHistorieAnummerGelijk", plControleHistorieAnummerGelijk);
        ReflectionTestUtils.setField(subject, "plControleDezelfdePersoon", plControleDezelfdePersoon);
        ReflectionTestUtils.setField(subject, "plControleGevondenVersienummerGroter", plControleGevondenVersienummerGroter);
        ReflectionTestUtils.setField(subject, "plControleGevondenDatumtijdstempelNieuwer", plControleGevondenDatumtijdstempelNieuwer);
        ReflectionTestUtils
                .setField(subject, "plControleAangebodenAdressenKomenVoorInGevondenAdressen", plControleAangebodenAdressenKomenVoorInGevondenAdressen);
    }

    @Test
    public void ok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Collections.singletonList(dbPersoonslijst), true, true, true, true, true, true, true, true, true);
        Assert.assertTrue(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void bijhoudiingssituatieNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Collections.singletonList(dbPersoonslijst), false, true, true, true, true, true, true, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void verzoekControleOudAnummerIsNietGevuldNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Collections.singletonList(dbPersoonslijst), true, false, true, true, true, true, true, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void lijstControleEenNok() {
        setup(Collections.emptyList(), true, true, false, true, true, true, true, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void plControleVorigAnummerGelijkNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Collections.singletonList(dbPersoonslijst), true, true, true, false, true, true, true, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void plControleHistorieAnummerGelijkNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Collections.singletonList(dbPersoonslijst), true, true, true, true, false, true, true, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void plControleDezelfdePersoonNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Collections.singletonList(dbPersoonslijst), true, true, true, true, true, false, true, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void plControleGevondenVersienummerGroterNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Collections.singletonList(dbPersoonslijst), true, true, true, true, true, true, false, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void plControleGevondenDatumtijdstempelNieuwerNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Collections.singletonList(dbPersoonslijst), true, true, true, true, true, true, true, false, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void plControleAangebodenAdressenKomenVoorInGevondenAdressenNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Collections.singletonList(dbPersoonslijst), true, true, true, true, true, true, true, true, false);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    private void setup(
            final List<BrpPersoonslijst> plZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelAnummerResult,
            final boolean resultControleBijhoudingssituatie,
            final boolean controleBerichtVanSoortLg01,
            final boolean lijstControleEenResult,
            final boolean plControleVorigAnummerGelijkResult,
            final boolean plControleHistorieAnummerGelijkResult,
            final boolean plControleDezelfdePersoonResult,
            final boolean plControleGevondenVersienummerGroterResult,
            final boolean plControleGevondenDatumtijdstempelNieuwerResult, boolean plControleAangebodenAdressenKomenVoorInGevondenAdressenResult) {
        Mockito.when(plZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelAnummer.zoek(Matchers.any(VerwerkingsContext.class)))
                .thenReturn(plZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelAnummerResult);

        Mockito.when(plControleGevondenBlokkeringssituatieIsJuist.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class)))
                .thenReturn(resultControleBijhoudingssituatie);

        Mockito.when(verzoekControleBerichtVanSoortLg01.controleer(Matchers.any(SynchroniseerNaarBrpVerzoekBericht.class)))
                .thenReturn(controleBerichtVanSoortLg01);

        Mockito.when(lijstControleEen.controleer(Matchers.anyListOf(BrpPersoonslijst.class))).thenReturn(lijstControleEenResult);

        Mockito.when(plControleVorigAnummerGelijk.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class)))
                .thenReturn(plControleVorigAnummerGelijkResult);
        Mockito.when(plControleHistorieAnummerGelijk.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class)))
                .thenReturn(plControleHistorieAnummerGelijkResult);
        Mockito.when(plControleDezelfdePersoon.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class)))
                .thenReturn(plControleDezelfdePersoonResult);
        Mockito.when(plControleGevondenVersienummerGroter.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class)))
                .thenReturn(plControleGevondenVersienummerGroterResult);
        Mockito.when(plControleGevondenDatumtijdstempelNieuwer.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class)))
                .thenReturn(plControleGevondenDatumtijdstempelNieuwerResult);
        Mockito.when(
                plControleAangebodenAdressenKomenVoorInGevondenAdressen.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class)))
                .thenReturn(plControleAangebodenAdressenKomenVoorInGevondenAdressenResult);
    }
}
