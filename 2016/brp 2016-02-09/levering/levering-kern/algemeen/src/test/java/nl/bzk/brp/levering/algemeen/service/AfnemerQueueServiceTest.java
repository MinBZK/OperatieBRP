/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.algemeen.service;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.levering.algemeen.service.impl.AfnemerQueueServiceImpl;
import nl.bzk.brp.levering.business.toegang.leveringsautorisatie.ToegangLeveringsautorisatieService;
import nl.bzk.brp.levering.business.toegang.leveringsautorisatie.cache.LeveringAutorisatieCache;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijRol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class AfnemerQueueServiceTest {

    @Mock
    private ToegangLeveringsautorisatieService toegangLeveringsautorisatieService;

    @Mock
    private LeveringAutorisatieCache leveringAutorisatieCache;

    @InjectMocks
    private final AfnemerQueueService afnemerQueueService = new AfnemerQueueServiceImpl();

    @Before
    public final void pre() {
        final List<ToegangLeveringsautorisatie> toegangAbonnnementen = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            final Partij partij = TestPartijBuilder.maker().metCode(i).maak();

            final ToegangLeveringsautorisatie tla = TestToegangLeveringautorisatieBuilder.maker()
                .metGeautoriseerde(new PartijRol(partij, null, null, null)).maak();
            toegangAbonnnementen.add(tla);
        }
        Mockito.when(toegangLeveringsautorisatieService.geefGeldigeLeveringsautorisaties()).thenReturn(toegangAbonnnementen);
    }

    @Test
    public final void testHaalAfnemerQueueNamen() {
        final List<PartijCodeAttribuut> afnemerPartijCodes = afnemerQueueService.haalPartijCodesWaarvoorGeleverdMoetWorden();

        Assert.assertNotNull(afnemerPartijCodes);
        Assert.assertThat(afnemerPartijCodes.size(), CoreMatchers.is(4));
    }
}
