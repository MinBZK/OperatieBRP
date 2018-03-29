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
public class ControlePersoonslijstIsGelijkTest {

    @Mock
    private VerzoekControle verzoekControleBerichtVanSoortLg01;
    @Mock
    private PlZoeker plZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelAnummer;
    @Mock
    private LijstControle lijstControleEen;
    @Mock
    private PlControle plControleGevondenBlokkeringssituatieIsJuist;
    @Mock
    private PlControle plControleVersienummerGelijk;
    @Mock
    private PlControle plControleDatumtijdstempelGelijk;
    @Mock
    private PlControle plControleVolledigGelijk;

    private ControlePersoonslijstIsGelijk subject;

    @Before
    public void setupLogging() {
        SynchronisatieLogging.init();
        subject = new ControlePersoonslijstIsGelijk(null, null, null, null);
        ReflectionTestUtils.setField(subject, "verzoekControleBerichtVanSoortLg01", verzoekControleBerichtVanSoortLg01);
        ReflectionTestUtils
                .setField(subject, "plZoekerObvActueelAnummer", plZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelAnummer);
        ReflectionTestUtils.setField(subject, "lijstControleEen", lijstControleEen);
        ReflectionTestUtils.setField(subject, "plControleGevondenBlokkeringssituatieIsJuist", plControleGevondenBlokkeringssituatieIsJuist);
        ReflectionTestUtils.setField(subject, "plControleVersienummerGelijk", plControleVersienummerGelijk);
        ReflectionTestUtils.setField(subject, "plControleDatumtijdstempelGelijk", plControleDatumtijdstempelGelijk);
        ReflectionTestUtils.setField(subject, "plControleVolledigGelijk", plControleVolledigGelijk);

    }

    @Test
    public void ok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Collections.singletonList(dbPersoonslijst), true, true, true, true, true, true);
        Assert.assertTrue(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void bijhoudingssituatieNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Collections.singletonList(dbPersoonslijst), false, true, true, true, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void verzoekControleOudAnummerIsNietGevuldNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Collections.singletonList(dbPersoonslijst), true, false, true, true, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void testLijstControleEenNok() {
        setup(Collections.emptyList(), true, true, false, true, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void plControleVersienummerGelijkNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Collections.singletonList(dbPersoonslijst), true, true, true, false, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void plControleDatumtijdstempelGelijkNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Collections.singletonList(dbPersoonslijst), true, true, true, true, false, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void plControleVolledigGelijkNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Collections.singletonList(dbPersoonslijst), true, true, true, true, true, false);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    private void setup(
            final List<BrpPersoonslijst> plZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelAnummerResult,
            final boolean resultControleBijhoudingssituatie,
            final boolean berichtVerzoekControleBerichtVanSoortLg01,
            final boolean lijstControleEenResult,
            final boolean plControleVersienummerGelijkResult,
            final boolean plControleDatumtijdstempelGelijkResult,
            final boolean plControleVolledigGelijkResult) {
        Mockito.when(plZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelAnummer.zoek(Matchers.any(VerwerkingsContext.class)))
                .thenReturn(plZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelAnummerResult);

        Mockito.when(verzoekControleBerichtVanSoortLg01.controleer(Matchers.any(SynchroniseerNaarBrpVerzoekBericht.class)))
                .thenReturn(berichtVerzoekControleBerichtVanSoortLg01);
        Mockito.when(plControleGevondenBlokkeringssituatieIsJuist.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class)))
                .thenReturn(resultControleBijhoudingssituatie);
        Mockito.when(lijstControleEen.controleer(Matchers.anyListOf(BrpPersoonslijst.class))).thenReturn(lijstControleEenResult);

        Mockito.when(plControleVersienummerGelijk.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class)))
                .thenReturn(plControleVersienummerGelijkResult);
        Mockito.when(plControleDatumtijdstempelGelijk.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class)))
                .thenReturn(plControleDatumtijdstempelGelijkResult);
        Mockito.when(plControleVolledigGelijk.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class)))
                .thenReturn(plControleVolledigGelijkResult);
    }
}
