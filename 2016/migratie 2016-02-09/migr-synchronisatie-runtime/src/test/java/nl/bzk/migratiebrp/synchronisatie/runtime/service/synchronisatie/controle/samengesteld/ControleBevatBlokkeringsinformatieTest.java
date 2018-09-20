/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.samengesteld;

import javax.inject.Named;

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
public class ControleBevatBlokkeringsinformatieTest {

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
    @Named(value = "plControleVorigAnummerGelijk")
    private PlControle plControleVorigAnummerGelijk;
    @Mock
    @Named(value = "plControleHistorieAnummerGelijk")
    private PlControle plControleHistorieAnummerGelijk;
    @Mock
    @Named(value = "plControleDezelfdePersoon")
    private PlControle plControleDezelfdePersoon;
    @Mock
    @Named(value = "plControleBevatDatumIngangBlokkering")
    private PlControle plControleBevatDatumIngangBlokkering;
    @Mock
    @Named(value = "plControleAangebodenAdressenKomenVoorInHistorieGevondenAdressen")
    private PlControle plControleAangebodenAdressenKomenVoorinHistorieGevondenAdressen;
    @Mock
    @Named(value = "plControleGevondenVersienummerGelijkOfGroter")
    private PlControle plControleGevondenVersienummerGelijkOfGroter;
    @Mock
    @Named(value = "plControleVolledigGelijkMuvAdressenDatumtijdstempelEnVersienummer")
    private PlControle plControleVolledigGelijkMuvAdressenDatumtijdstempelEnVersienummer;

    @InjectMocks
    private ControleBevatBlokkeringsinformatie subject;

    @Before
    public void setupLogging() {
        SynchronisatieLogging.init();
    }

    @Test
    public void testOk() {
        new BrpPersoonslijstBuilder().build();
        setup(true);
        Assert.assertTrue(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void testPlControleBevatDatumIngangBlokkeringNok() {
        new BrpPersoonslijstBuilder().build();
        setup(false);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    private void setup(final boolean plControleBevatDatumIngangBlokkeringResult) {
        Mockito.when(plControleBevatDatumIngangBlokkering.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class)))
               .thenReturn(plControleBevatDatumIngangBlokkeringResult);
    }
}
