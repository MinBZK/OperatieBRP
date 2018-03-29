/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.autorisatie;

import static java.util.Collections.singletonList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.service.cache.LeveringsAutorisatieCache;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class LeveringsautorisatieServiceImplTest {

    private static final int LEVERING_AUTORISATIE_ID = 12345;
    private static final String PARTIJ_CODE = "000123";

    @Mock
    private LeveringsAutorisatieCache leveringAutorisatieCache;

    @InjectMocks
    private LeveringsautorisatieServiceImpl toegangLeveringsautorisatieService;

    private final Partij partij = TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build();
    private final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
    private final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, SoortDienst.ATTENDERING);
    private final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);

    @Test
    public final void geefGeldigeLeveringsutorisaties() {
        when(leveringAutorisatieCache.geefAlleToegangleveringsautorisaties()).thenReturn(
                singletonList(tla));
        toegangLeveringsautorisatieService.geefToegangLeveringsAutorisaties();
        Mockito.verify(leveringAutorisatieCache, Mockito.times(1)).geefAlleToegangleveringsautorisaties();
    }

    @Test
    public final void geefLeveringautorisatie() {
        when(leveringAutorisatieCache.geefLeveringsautorisatie(LEVERING_AUTORISATIE_ID)).thenReturn(
                leveringsautorisatie);
        toegangLeveringsautorisatieService.geefLeveringautorisatie(LEVERING_AUTORISATIE_ID);
        Mockito.verify(leveringAutorisatieCache, Mockito.times(1)).geefLeveringsautorisatie(LEVERING_AUTORISATIE_ID);
    }

    @Test
    public final void geefToegangLeveringautorisatie() {
        when(leveringAutorisatieCache.geefToegangLeveringsautorisatie(LEVERING_AUTORISATIE_ID,
                PARTIJ_CODE)).thenReturn(
                tla);
        toegangLeveringsautorisatieService.geefToegangLeveringsAutorisatie(LEVERING_AUTORISATIE_ID, PARTIJ_CODE);
        Mockito.verify(leveringAutorisatieCache, Mockito.times(1)).geefToegangLeveringsautorisatie(
                LEVERING_AUTORISATIE_ID, PARTIJ_CODE);
    }

    @Test
    public void geeftToegangLeveringsautorisatieOpIdEnPartijCode() {
        when(leveringAutorisatieCache.geefToegangLeveringsautorisatie(1, "000001")).thenReturn(tla);

        assertThat(toegangLeveringsautorisatieService.geefToegangLeveringsAutorisatie(1, "000001"), is(tla));
    }

    @Test
    public void geeftToegangLeveringsautorisatieId() {
        when(leveringAutorisatieCache.geefToegangLeveringsautorisatie(1)).thenReturn(tla);

        assertThat(toegangLeveringsautorisatieService.geefToegangLeveringsAutorisatie(1), is(tla));
    }

    @Test
    public void geeftToegangLeveringsautorisatieOpPartij() {
        when(leveringAutorisatieCache.geefToegangleveringautorisatiesVoorGeautoriseerdePartij(partij.getCode()))
                .thenReturn(singletonList(tla));

        assertThat(toegangLeveringsautorisatieService.geefToegangLeveringAutorisaties(partij), CoreMatchers.hasItem(tla));
    }

    @Test
    public void dienstBestaat() {
        final Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
        final Dienst dienst = new Dienst(dienstbundel, SoortDienst.ATTENDERING);
        dienst.setId(1);
        when(leveringAutorisatieCache.geefDienst(1)).thenReturn(dienst);

        assertThat(toegangLeveringsautorisatieService.bestaatDienst(1), is(true));
    }

    @Test
    public void dienstBestaatNiet() {
        when(leveringAutorisatieCache.geefDienst(anyInt())).thenReturn(null);

        assertThat(toegangLeveringsautorisatieService.bestaatDienst(1), is(false));
    }
}
