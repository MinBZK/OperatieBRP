/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.leveringbepaling.filter;

import static org.mockito.Matchers.any;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.service.algemeen.VerstrekkingsbeperkingService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VerstrekkingsbeperkingfilterImplTest {

    @InjectMocks
    VerstrekkingsbeperkingfilterImpl verstrekkingsbeperkingfilter;

    @Mock
    VerstrekkingsbeperkingService verstrekkingsbeperkingService;


    @Test
    public void testMagLeverenDienstAttendering() throws Exception {
        Mockito.when(verstrekkingsbeperkingService.heeftGeldigeVerstrekkingsbeperking(any(), any())).thenReturn(false);
        Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.ATTENDERING);

        boolean magLeveren = verstrekkingsbeperkingfilter.magLeveren(TestBuilders.PERSOON_MET_HANDELINGEN, null, autorisatiebundel);

        Assert.assertTrue(magLeveren);
    }

    @Test
    public void testMagLeverenDienstAttendering_VerstrekkingsbeperkingAanwezig() throws Exception {
        Mockito.when(verstrekkingsbeperkingService.heeftGeldigeVerstrekkingsbeperking(any(), any())).thenReturn(true);
        Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.ATTENDERING);

        boolean magLeveren = verstrekkingsbeperkingfilter.magLeveren(TestBuilders.PERSOON_MET_HANDELINGEN, null, autorisatiebundel);

        Assert.assertFalse(magLeveren);
    }

    @Test
    public void testMagLeverenDienstSelectie() throws Exception {
        Mockito.when(verstrekkingsbeperkingService.heeftGeldigeVerstrekkingsbeperking(any(), any())).thenReturn(false);
        Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.SELECTIE);

        boolean magLeveren = verstrekkingsbeperkingfilter.magLeveren(TestBuilders.PERSOON_MET_HANDELINGEN, null, autorisatiebundel);

        Assert.assertTrue(magLeveren);
    }


    @Test
    public void testMagLeverenGeenAttenderingOfSelectieDienst() throws Exception {
        Mockito.when(verstrekkingsbeperkingService.heeftGeldigeVerstrekkingsbeperking(any(), any())).thenReturn(false);
        Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.GEEF_DETAILS_PERSOON);

        boolean magLeveren = verstrekkingsbeperkingfilter.magLeveren(TestBuilders.PERSOON_MET_HANDELINGEN, null, autorisatiebundel);

        Assert.assertTrue(magLeveren);
    }

    @Test
    public void testMagLeverenMutLevObvAfnemerindicatie() throws Exception {
        Mockito.when(verstrekkingsbeperkingService.heeftGeldigeVerstrekkingsbeperking(any(), any())).thenReturn(false);
        Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);

        boolean magLeveren = verstrekkingsbeperkingfilter.magLeveren(TestBuilders.PERSOON_MET_HANDELINGEN, null, autorisatiebundel);

        Assert.assertTrue(magLeveren);
    }

    @Test
    public void testMagLeverenMutLevObvAfnemerindicatie_VerstrekkingsbeperkingVorigeHndEnNu() throws Exception {
        Mockito.when(verstrekkingsbeperkingService.heeftGeldigeVerstrekkingsbeperking(any(), any())).thenReturn(true);
        Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);

        boolean magLeveren = verstrekkingsbeperkingfilter.magLeveren(TestBuilders.PERSOON_MET_HANDELINGEN, null, autorisatiebundel);

        Assert.assertFalse(magLeveren);
    }


    private Autorisatiebundel maakAutorisatiebundel(final SoortDienst srtDienst) {
        final Partij partij = TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build();
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, srtDienst);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        final Dienst dienst = AutAutUtil.zoekDienst(leveringsautorisatie, srtDienst);
        return new Autorisatiebundel(tla, dienst);
    }
}
