/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.toegang.leveringsautorisatie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.dataaccess.repository.ToegangLeveringsautorisatieRepository;
import nl.bzk.brp.levering.business.toegang.leveringsautorisatie.cache.LeveringAutorisatieCache;
import nl.bzk.brp.levering.business.toegang.leveringsautorisatie.cache.LeveringAutorisatieCacheImpl;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijRol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

/**
 * LeveringAutorisatieCacheImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class LeveringAutorisatieCacheImplTest {

    @Mock
    private ToegangLeveringsautorisatieRepository toegangLeveringsautorisatieRepository;

    @Mock
    private ApplicationContext applicationContext;

    @InjectMocks
    private LeveringAutorisatieCache leveringAutorisatieCache = new LeveringAutorisatieCacheImpl();


    @Before
    public void setup() {


        final List<ToegangLeveringsautorisatie> toegangLeveringsautorisaties = new ArrayList<>();

        final Partij partij = TestPartijBuilder.maker().metCode(1).maak();
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER, DatumAttribuut.vandaag(), null);

        final Leveringsautorisatie leveringsAutorisatie1 = TestLeveringsautorisatieBuilder.maker().metId(1).maak();
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie1 = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie
            (leveringsAutorisatie1).metDatumIngang(DatumAttribuut.gisteren()).metId(1).metGeautoriseerde(partijRol).maak();
        toegangLeveringsautorisaties.add(toegangLeveringsautorisatie1);

        final Leveringsautorisatie leveringsAutorisatie2Ongeldig = TestLeveringsautorisatieBuilder.maker().metId(1).maak();
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie2Ongeldig = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie
            (leveringsAutorisatie2Ongeldig).metDatumIngang(DatumAttribuut.morgen()).metId(2).metGeautoriseerde(partijRol).maak();
        toegangLeveringsautorisaties.add(toegangLeveringsautorisatie2Ongeldig);

        Mockito.when(toegangLeveringsautorisatieRepository.haalAlleToegangLeveringsautorisatieOp()).thenReturn(toegangLeveringsautorisaties);

        leveringAutorisatieCache.herlaad();
    }

    @Test
    public void geefLeveringsautorisatie() {
        final Leveringsautorisatie leveringsAutorisatie1 = leveringAutorisatieCache.geefLeveringsautorisatie(1);
        assertNotNull(leveringsAutorisatie1);
        final Leveringsautorisatie leveringsAutorisatie2 = leveringAutorisatieCache.geefLeveringsautorisatie(2);
        assertNull(leveringsAutorisatie2);
    }

    @Test
    public void geefLeveringsautorisatieZonderControle() {
        final Leveringsautorisatie leveringsAutorisatie1 = leveringAutorisatieCache.geefLeveringsautorisatie(1);
        assertNotNull(leveringsAutorisatie1);
        final Leveringsautorisatie leveringsAutorisatie2 = leveringAutorisatieCache.geefLeveringsautorisatie(2);
        assertNull(leveringsAutorisatie2);
    }

    @Test
    public void geefGeldigeToegangleveringsautorisaties() {
        final List<ToegangLeveringsautorisatie> toegangLeveringsautorisaties = leveringAutorisatieCache.geefGeldigeToegangleveringsautorisaties();
        assertEquals(1, toegangLeveringsautorisaties.size());
    }

    @Test
    public void geefLeveringsautorisatieMetPartijCodeZonderControle() {
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie1 = leveringAutorisatieCache.geefToegangleveringautorisatieZonderControle(1, 1);
        assertNotNull(toegangLeveringsautorisatie1);
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie2 = leveringAutorisatieCache.geefToegangleveringautorisatieZonderControle(1, -1);
        assertNull(toegangLeveringsautorisatie2);

    }
}
