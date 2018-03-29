/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.samengesteld;

import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.verzoek.VerzoekControle;
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
public class ControleBevatBlokkeringsinformatieTest {

    @Mock
    private VerzoekControle verzoekControleBerichtVanSoortLg01;
    @Mock
    private PlControle plControleBevatDatumIngangBlokkering;

    private ControleBevatBlokkeringsinformatie subject;

    @Before
    public void setupLogging() {
        SynchronisatieLogging.init();
        subject = new ControleBevatBlokkeringsinformatie();
        ReflectionTestUtils.setField(subject, "verzoekControleBerichtVanSoortLg01", verzoekControleBerichtVanSoortLg01);
        ReflectionTestUtils.setField(subject, "plControleBevatDatumIngangBlokkering", plControleBevatDatumIngangBlokkering);
    }

    @Test
    public void testOk() {
        new BrpPersoonslijstBuilder().build();
        setup(true, true);
        Assert.assertTrue(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void testControleVerzoekLg01Nok() {
        new BrpPersoonslijstBuilder().build();
        setup(true, false);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    @Test
    public void testPlControleBevatDatumIngangBlokkeringNok() {
        new BrpPersoonslijstBuilder().build();
        setup(true, false);
        Assert.assertFalse(subject.controleer(new VerwerkingsContext(null, null, null, null)));
    }

    private void setup(final boolean berichtVerzoekControleBerichtVanSoortLg01, final boolean plControleBevatDatumIngangBlokkeringResult) {
        Mockito.when(verzoekControleBerichtVanSoortLg01.controleer(Matchers.any(SynchroniseerNaarBrpVerzoekBericht.class)))
                .thenReturn(berichtVerzoekControleBerichtVanSoortLg01);
        Mockito.when(plControleBevatDatumIngangBlokkering.controleer(Matchers.any(VerwerkingsContext.class), Matchers.any(BrpPersoonslijst.class)))
                .thenReturn(plControleBevatDatumIngangBlokkeringResult);
    }
}
