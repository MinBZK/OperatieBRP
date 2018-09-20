/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.zoeker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.pl.PlService;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PlZoekerOpAnummerObvActueelAnummerTest {

    @Mock
    private PlService plService;

    @InjectMocks
    private PlZoekerOpAnummerObvActueelAnummer subject;

    @Before
    public void setupLogging() {
        SynchronisatieLogging.init();
    }

    @Test
    public void test() {
        final BrpPersoonslijst brpPersoonslijst = maakPl(1111, 2222, 3333);
        final BrpPersoonslijst dbPersoonslijst1a = maakPl(1111, 2222);
        final BrpPersoonslijst dbPersoonslijst1b = maakPl(1111, 2222);
        final BrpPersoonslijst dbPersoonslijst2a = maakPl(1111, 2222);
        final BrpPersoonslijst dbPersoonslijst2b = maakPl(1111, 2222);
        final BrpPersoonslijst dbPersoonslijst2c = maakPl(1111, 2222);
        final List<BrpPersoonslijst> lijst1 = Arrays.asList(dbPersoonslijst1a, dbPersoonslijst1b);
        final List<BrpPersoonslijst> lijst2 = Arrays.asList(dbPersoonslijst2a, dbPersoonslijst2b, dbPersoonslijst2c);

        Mockito.when(plService.zoekPersoonslijstenOpActueelAnummer(1111L)).thenReturn(lijst1);
        Mockito.when(plService.zoekPersoonslijstenOpHistorischAnummer(1111L)).thenReturn(lijst2);

        final VerwerkingsContext context = new VerwerkingsContext(null, null, null, brpPersoonslijst);

        final List<BrpPersoonslijst> resultaat = subject.zoek(context);
        Assert.assertNotNull(resultaat);
        Assert.assertEquals(5, resultaat.size());

        Mockito.verify(plService).zoekPersoonslijstenOpActueelAnummer(1111L);
        Mockito.verify(plService).zoekPersoonslijstenOpHistorischAnummer(1111L);
        Mockito.verifyNoMoreInteractions(plService);

        // Uit cache
        final List<BrpPersoonslijst> cachedResultaat = subject.zoek(context);
        Assert.assertNotNull(cachedResultaat);
        Assert.assertEquals(5, cachedResultaat.size());

        Mockito.verifyNoMoreInteractions(plService);
    }

    private BrpPersoonslijst maakPl(final int... anummers) {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        final List<BrpGroep<BrpIdentificatienummersInhoud>> groepen = new ArrayList<>();
        for (int i = 0; i < anummers.length; i++) {
            final BrpIdentificatienummersInhoud inhoud = new BrpIdentificatienummersInhoud(new BrpLong((long) anummers[i]), null);
            final Integer datumAanvang = (2010 - i) * 10000 + 101;
            final Integer datumEinde = i == 0 ? null : (2011 - i) * 10000 + 101;
            final BrpHistorie historie = BrpStapelHelper.his(datumAanvang, datumEinde, datumAanvang, datumEinde);
            final BrpActie actieInhoud = BrpStapelHelper.act(1, datumAanvang);

            groepen.add(new BrpGroep<>(inhoud, historie, actieInhoud, null, null));
        }

        builder.identificatienummersStapel(new BrpStapel<>(groepen));

        return builder.build();
    }

}
