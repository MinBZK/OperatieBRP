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
public class ControleNieuwePersoonslijstTest {

    @Mock
    @Named(value = "verzoekControleOudAnummerIsNietGevuld")
    private VerzoekControle verzoekControleOudAnummerIsNietGevuld;

    @Mock
    @Named(value = "plZoekerOpAnummerObvActueelAnummer")
    private PlZoeker plZoekerOpAnummerObvActueelAnummer;

    @Mock
    @Named(value = "lijstControleGeen")
    private LijstControle lijstControleGeen;

    @Mock
    @Named(value = "plControleAnummerHistorischGelijk")
    private PlControle plControleAnummerHistorischGelijk;

    @InjectMocks
    private ControleNieuwePersoonslijst subject;

    @Before
    public void setupLogging() {
        SynchronisatieLogging.init();
    }

    @Test
    public void testOk() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Arrays.asList(dbPersoonslijst), true, true, true);
        Assert.assertTrue(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void testVerzoekControleOudAnummerIsNietGevuldNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Arrays.asList(dbPersoonslijst), false, true, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void testLijstControleGeenNok() {
        setup(Collections.<BrpPersoonslijst>emptyList(), true, false, true);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void testPlControleAnummerHistorischGelijkNok() {
        final BrpPersoonslijst dbPersoonslijst = new BrpPersoonslijstBuilder().build();
        setup(Arrays.asList(dbPersoonslijst), true, true, false);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    private void setup(
        final List<BrpPersoonslijst> plZoekerOpAnummerObvActueelAnummerResult,
        final boolean verzoekControleOudAnummerIsNietGevuldResult,
        final boolean lijstControleGeenResult,
        final boolean plControleAnummerHistorischGelijkResult)
    {
        Mockito.when(plZoekerOpAnummerObvActueelAnummer.zoek(Matchers.any(VerwerkingsContext.class))).thenReturn(plZoekerOpAnummerObvActueelAnummerResult);

        Mockito.when(verzoekControleOudAnummerIsNietGevuld.controleer(Matchers.any(SynchroniseerNaarBrpVerzoekBericht.class))).thenReturn(
            verzoekControleOudAnummerIsNietGevuldResult);

        Mockito.when(lijstControleGeen.controleer(Matchers.anyListOf(BrpPersoonslijst.class))).thenReturn(lijstControleGeenResult);

        Mockito.when(plControleAnummerHistorischGelijk.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class)))
               .thenReturn(plControleAnummerHistorischGelijkResult);
    }
}
