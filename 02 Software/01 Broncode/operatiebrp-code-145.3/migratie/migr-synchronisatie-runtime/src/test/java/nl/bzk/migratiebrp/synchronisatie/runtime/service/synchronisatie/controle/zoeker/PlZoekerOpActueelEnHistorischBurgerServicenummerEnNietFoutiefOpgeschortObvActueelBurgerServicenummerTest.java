/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.zoeker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.pl.PlService;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PlZoekerOpActueelEnHistorischBurgerServicenummerEnNietFoutiefOpgeschortObvActueelBurgerServicenummerTest {

    @Mock
    private PlService plService;

    private PlZoekerOpActueelEnHistorischBurgerServicenummerEnNietFoutiefOpgeschortObvActueelBurgerServicenummer subject;

    @Before
    public void setupLogging() {
        SynchronisatieLogging.init();
        subject = new PlZoekerOpActueelEnHistorischBurgerServicenummerEnNietFoutiefOpgeschortObvActueelBurgerServicenummer(plService);
    }

    @Test
    public void testGevonden() {
        final BrpPersoonslijst brpPersoonslijst = maakPl("1111", "2222", "3333");
        final BrpPersoonslijst dbPersoonslijst = maakPl("1111", "2222");

        Mockito.when(plService.zoekNietFoutievePersoonslijstOpActueelBurgerservicenummer("1111")).thenReturn(dbPersoonslijst);
        Mockito.when(plService.zoekNietFoutievePersoonslijstenOpHistorischBurgerservicenummer("1111")).thenReturn(Collections.emptyList());

        final VerwerkingsContext context = new VerwerkingsContext(null, null, null, brpPersoonslijst);

        final List<BrpPersoonslijst> resultaat = subject.zoek(context);
        Assert.assertNotNull(resultaat);
        Assert.assertEquals(1, resultaat.size());
        Assert.assertSame(dbPersoonslijst, resultaat.get(0));

        Mockito.verify(plService).zoekNietFoutievePersoonslijstOpActueelBurgerservicenummer("1111");
        Mockito.verify(plService).zoekNietFoutievePersoonslijstenOpHistorischBurgerservicenummer("1111");
        Mockito.verifyNoMoreInteractions(plService);

        // Uit cache
        final List<BrpPersoonslijst> cachedResultaat = subject.zoek(context);
        Assert.assertNotNull(cachedResultaat);
        Assert.assertEquals(1, cachedResultaat.size());
        Assert.assertSame(dbPersoonslijst, cachedResultaat.get(0));

        Mockito.verifyNoMoreInteractions(plService);
    }

    @Test
    public void testNietGevonden() {
        final BrpPersoonslijst brpPersoonslijst = maakPl("1111", "2222", "3333");

        Mockito.when(plService.zoekNietFoutievePersoonslijstOpActueelBurgerservicenummer("1111")).thenReturn(null);
        Mockito.when(plService.zoekNietFoutievePersoonslijstenOpHistorischBurgerservicenummer("1111")).thenReturn(Collections.emptyList());

        final VerwerkingsContext context = new VerwerkingsContext(null, null, null, brpPersoonslijst);

        final List<BrpPersoonslijst> resultaat = subject.zoek(context);
        Assert.assertNotNull(resultaat);
        Assert.assertEquals(0, resultaat.size());

        Mockito.verify(plService).zoekNietFoutievePersoonslijstOpActueelBurgerservicenummer("1111");
        Mockito.verify(plService).zoekNietFoutievePersoonslijstenOpHistorischBurgerservicenummer("1111");
        Mockito.verifyNoMoreInteractions(plService);

        // Uit cache
        final List<BrpPersoonslijst> cachedResultaat = subject.zoek(context);
        Assert.assertNotNull(cachedResultaat);
        Assert.assertEquals(0, cachedResultaat.size());

        Mockito.verifyNoMoreInteractions(plService);
    }

    private BrpPersoonslijst maakPl(final String... burgerservicenummers) {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        final List<BrpGroep<BrpIdentificatienummersInhoud>> groepen = new ArrayList<>();
        for (int i = 0; i < burgerservicenummers.length; i++) {
            final BrpIdentificatienummersInhoud inhoud = new BrpIdentificatienummersInhoud(null, new BrpString(burgerservicenummers[i]));
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
