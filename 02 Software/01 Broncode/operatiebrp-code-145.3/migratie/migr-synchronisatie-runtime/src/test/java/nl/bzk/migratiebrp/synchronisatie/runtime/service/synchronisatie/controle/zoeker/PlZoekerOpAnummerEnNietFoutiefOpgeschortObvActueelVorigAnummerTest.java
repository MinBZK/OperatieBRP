/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.zoeker;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNummerverwijzingInhoud;
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
public class PlZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelVorigAnummerTest {

    @Mock
    private PlService plService;

    private PlZoekerOpActueelAnummerEnNietFoutiefOpgeschortObvActueelVorigAnummer subject;

    @Before
    public void setupLogging() {
        SynchronisatieLogging.init();
        subject = new PlZoekerOpActueelAnummerEnNietFoutiefOpgeschortObvActueelVorigAnummer(plService);
    }

    @Test
    public void testGevonden() {
        final BrpPersoonslijst brpPersoonslijst = maakPl("1111", "2222", "3333");
        final BrpPersoonslijst dbPersoonslijst = maakPl("1111", "2222");

        Mockito.when(plService.zoekNietFoutievePersoonslijstOpActueelAnummer("2222")).thenReturn(dbPersoonslijst);

        final VerwerkingsContext context = new VerwerkingsContext(null, null, null, brpPersoonslijst);

        final List<BrpPersoonslijst> resultaat = subject.zoek(context);
        Assert.assertNotNull(resultaat);
        Assert.assertEquals(1, resultaat.size());
        Assert.assertSame(dbPersoonslijst, resultaat.get(0));

        Mockito.verify(plService).zoekNietFoutievePersoonslijstOpActueelAnummer("2222");
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

        Mockito.when(plService.zoekNietFoutievePersoonslijstOpActueelAnummer("2222")).thenReturn(null);

        final VerwerkingsContext context = new VerwerkingsContext(null, null, null, brpPersoonslijst);

        final List<BrpPersoonslijst> resultaat = subject.zoek(context);
        Assert.assertNotNull(resultaat);
        Assert.assertEquals(0, resultaat.size());

        Mockito.verify(plService).zoekNietFoutievePersoonslijstOpActueelAnummer("2222");
        Mockito.verifyNoMoreInteractions(plService);

        // Uit cache
        final List<BrpPersoonslijst> cachedResultaat = subject.zoek(context);
        Assert.assertNotNull(cachedResultaat);
        Assert.assertEquals(0, cachedResultaat.size());

        Mockito.verifyNoMoreInteractions(plService);
    }

    @Test
    public void testGeenVorigAnummer() {
        final BrpPersoonslijst brpPersoonslijst = maakPl("1111");

        final VerwerkingsContext context = new VerwerkingsContext(null, null, null, brpPersoonslijst);

        final List<BrpPersoonslijst> resultaat = subject.zoek(context);
        Assert.assertNotNull(resultaat);
        Assert.assertEquals(0, resultaat.size());

        Mockito.verifyNoMoreInteractions(plService);

        // Uit cache
        final List<BrpPersoonslijst> cachedResultaat = subject.zoek(context);
        Assert.assertNotNull(cachedResultaat);
        Assert.assertEquals(0, cachedResultaat.size());

        Mockito.verifyNoMoreInteractions(plService);
    }

    private BrpPersoonslijst maakPl(final String anummer, final String... vorigAnummers) {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();

        builder.identificatienummersStapel(BrpStapelHelper.stapel(BrpStapelHelper.groep(new BrpIdentificatienummersInhoud(
                new BrpString(anummer),
                null), BrpStapelHelper.his(20000131), BrpStapelHelper.act(1, 20000131))));

        if (vorigAnummers != null && vorigAnummers.length > 0) {
            final List<BrpGroep<BrpNummerverwijzingInhoud>> groepen = new ArrayList<>();

            for (int i = 0; i < vorigAnummers.length; i++) {
                final Integer datumAanvang = 20000131 - i;
                final Integer datumEinde = i == 0 ? null : 20000132 - i;
                groepen.add(BrpStapelHelper.groep(
                        BrpStapelHelper.nummerverwijzing(vorigAnummers[i], null, null, null),
                        BrpStapelHelper.his(datumAanvang, datumEinde, datumAanvang, datumEinde),
                        BrpStapelHelper.act(1, datumAanvang)));
            }

            builder.nummerverwijzingStapel(BrpStapelHelper.stapel(groepen.toArray(new BrpGroep[groepen.size()])));
        }

        return builder.build();
    }

}
