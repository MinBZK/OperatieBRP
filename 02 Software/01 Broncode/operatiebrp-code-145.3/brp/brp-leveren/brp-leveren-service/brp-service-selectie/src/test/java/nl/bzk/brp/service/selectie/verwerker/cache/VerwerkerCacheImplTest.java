/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.verwerker.cache;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;

import com.google.common.collect.Sets;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.expressie.SelectieLijst;
import nl.bzk.brp.domain.internbericht.selectie.SelectieAutorisatieBericht;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * VerwerkerCacheImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class VerwerkerCacheImplTest {

    @Mock
    private LeveringsautorisatieService leveringsautorisatieService;
    @Mock
    private SelectieLijstMakerService selectieLijstMakerService;

    @InjectMocks
    private VerwerkerCacheImpl verwerkerAutorisatieCache;

    @Test
    public void testAutorisatieCache() {
        final Dienst dienst = AutAutUtil.zoekDienst(TestAutorisaties.metSoortDienst(SoortDienst.SELECTIE), SoortDienst
                .SELECTIE);
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(
                TestAutorisaties.maak(Rol.AFNEMER, dienst), dienst);

        final ToegangLeveringsAutorisatie toegang = autorisatiebundel.getToegangLeveringsautorisatie();
        toegang.setId(1);
        Mockito.when(leveringsautorisatieService.geefToegangLeveringsAutorisatie(Mockito.anyInt())).thenReturn(toegang);

        final SelectieAutorisatieBericht selectieAutorisatieBericht = new SelectieAutorisatieBericht();
        selectieAutorisatieBericht.setToegangLeveringsAutorisatieId(toegang.getId());
        selectieAutorisatieBericht.setDienstId(1);
        selectieAutorisatieBericht.setSelectietaakId(1);
        final Autorisatiebundel autorisatiebundelUitCache = verwerkerAutorisatieCache.getAutorisatiebundel(selectieAutorisatieBericht, 1);

        assertEquals(autorisatiebundel.getToegangLeveringsautorisatie(), autorisatiebundelUitCache.getToegangLeveringsautorisatie());

        //haal nog een keer op, 1 call op service
        verwerkerAutorisatieCache.getAutorisatiebundel(selectieAutorisatieBericht, 1);
        Mockito.verify(leveringsautorisatieService, times(1)).geefToegangLeveringsAutorisatie(Mockito.anyInt());
        //clear
        verwerkerAutorisatieCache.clear();
        verwerkerAutorisatieCache.getAutorisatiebundel(selectieAutorisatieBericht, 1);
        Mockito.verify(leveringsautorisatieService, times(2)).geefToegangLeveringsAutorisatie(Mockito.anyInt());

        //haal op met nieuwe versie
        verwerkerAutorisatieCache.getAutorisatiebundel(selectieAutorisatieBericht, 2);
        Mockito.verify(leveringsautorisatieService, times(3)).geefToegangLeveringsAutorisatie(Mockito.anyInt());
        verwerkerAutorisatieCache.getAutorisatiebundel(selectieAutorisatieBericht, 2);
        Mockito.verify(leveringsautorisatieService, times(3)).geefToegangLeveringsAutorisatie(Mockito.anyInt());

    }

    @Test
    public void testSelectieLijstCache() {
        final int dienstId = 1;
        SelectieLijst
                selectieLijst =
                new SelectieLijst(dienstId, ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER),
                        Sets.newHashSet("123"));
        Mockito.when(selectieLijstMakerService.maak(Mockito.anyInt(), Mockito.anyInt())).thenReturn(selectieLijst);

        final SelectieLijst selectieLijstUitCache = verwerkerAutorisatieCache.getSelectieLijst(1, 1, 1);

        assertEquals(selectieLijstUitCache.getWaarden(), selectieLijst.getWaarden());

        //haal nog een keer op, 1 call op service
        verwerkerAutorisatieCache.getSelectieLijst(dienstId, 1, 1);
        Mockito.verify(selectieLijstMakerService, times(1)).maak(Mockito.anyInt(), Mockito.anyInt());
        //clear
        verwerkerAutorisatieCache.clear();
        verwerkerAutorisatieCache.getSelectieLijst(dienstId, 1, 1);
        Mockito.verify(selectieLijstMakerService, times(2)).maak(Mockito.anyInt(), Mockito.anyInt());

        //haal op met nieuwe versie
        verwerkerAutorisatieCache.getSelectieLijst(dienstId, 2, 2);
        Mockito.verify(selectieLijstMakerService, times(3)).maak(Mockito.anyInt(), Mockito.anyInt());
        verwerkerAutorisatieCache.getSelectieLijst(dienstId, 2, 2);
        Mockito.verify(selectieLijstMakerService, times(3)).maak(Mockito.anyInt(), Mockito.anyInt());

    }

}
