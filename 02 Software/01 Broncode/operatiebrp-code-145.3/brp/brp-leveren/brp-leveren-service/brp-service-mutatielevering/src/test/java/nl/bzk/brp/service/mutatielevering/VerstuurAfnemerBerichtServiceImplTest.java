/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.internbericht.verzendingmodel.SynchronisatieBerichtGegevens;
import nl.bzk.brp.service.algemeen.PlaatsAfnemerBerichtService;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.mutatielevering.dto.Mutatiebericht;
import nl.bzk.brp.service.mutatielevering.dto.Mutatielevering;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class VerstuurAfnemerBerichtServiceImplTest {

    @InjectMocks
    private VerstuurAfnemerBerichtServiceImpl service;

    @Mock
    private PlaatsAfnemerBerichtService plaatsAfnemerBerichtService;

    @Test
    public void testGeenBerichten() throws StapException {
        service.verstuurBerichten(Lists.newArrayList());
        Mockito.verify(plaatsAfnemerBerichtService, Mockito.never()).plaatsAfnemerberichten(Mockito.any());
    }

    @Test
    public void testPlaatsBerichten() throws StapException {
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel();
        final Mutatielevering mutatielevering = new Mutatielevering(autorisatiebundel, Maps.newHashMap());
        final String xmlBericht = "brpbericht";
        final Mutatiebericht mutatiebericht = new Mutatiebericht(mutatielevering, null/*niet relevant*/, xmlBericht,
                SynchronisatieBerichtGegevens.builder().build());
        service.verstuurBerichten(Lists.newArrayList(
                mutatiebericht
        ));
        Mockito.verify(plaatsAfnemerBerichtService, Mockito.times(1))
                .plaatsAfnemerberichten(Mockito.any());
    }

    private Autorisatiebundel maakAutorisatiebundel() {
        final PartijRol partijRol = new PartijRol(TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build(), Rol.AFNEMER);
        final SoortDienst soortDienst = SoortDienst.ATTENDERING;
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        leveringsautorisatie.setId(1);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        return new Autorisatiebundel(tla, AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst));
    }

}
