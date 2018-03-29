/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.algemeen;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.UUID;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.service.bevraging.detailspersoon.GeefDetailsPersoonVerzoek;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * GeefDetailsPersoonServiceImplTest.
 */

@RunWith(MockitoJUnitRunner.class)
public class GeneriekeBevragingVerzoekVerwerkerTest {

    private static final SoortDienst SOORT_DIENST = SoortDienst.ATTENDERING;
    private static final int LEV_AUT_ID = 1;
    private static final int ZENDENDE_PARTIJ_ID = 1;

    @InjectMocks
    private GeneriekeBevragingVerzoekVerwerker<BevragingVerzoek, BevragingResultaat> geefDetailsPersoonService;
    @Mock
    private Bevraging.MaakBerichtService<BevragingVerzoek, BevragingResultaat> maakGeefDetailsPersoonBerichtService;
    @Mock
    private Bevraging.ArchiveerBerichtService<BevragingVerzoek, BevragingResultaat> archiveerBerichtService;
    @Mock
    private Bevraging.ProtocolleerBerichtService<BevragingVerzoek, BevragingResultaat> protocolleerBerichtService;

    private GeefDetailsPersoonVerzoek bevragingVerzoek;

    @Before
    public void voorTest() {
        bevragingVerzoek = new GeefDetailsPersoonVerzoek();
        bevragingVerzoek.setSoortDienst(SOORT_DIENST);
        bevragingVerzoek.getParameters().setLeveringsAutorisatieId(Integer.toString(LEV_AUT_ID));
        bevragingVerzoek.getStuurgegevens().setZendendePartijCode(Integer.toString(ZENDENDE_PARTIJ_ID));
    }

    @Test
    public void testHappyFlow() throws Exception {

        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel();
        final BevragingResultaat berichtResultaat = new BevragingResultaat();
        berichtResultaat.setAutorisatiebundel(autorisatiebundel);
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                .metStuurgegevens()
                .metReferentienummer(UUID.randomUUID().toString())
                .metTijdstipVerzending(DatumUtil.nuAlsZonedDateTime())
                .eindeStuurgegevens()
                .build();
        berichtResultaat.setBericht(new VerwerkPersoonBericht(basisBerichtGegevens, null, Collections.emptyList()));

        when(maakGeefDetailsPersoonBerichtService.voerStappenUit(bevragingVerzoek)).thenReturn(berichtResultaat);

        final BevragingCallback<String> callback = new BevragingCallback<String>() {
            @Override
            public void verwerkResultaat(final BevragingVerzoek bevragingVerzoek, final BevragingResultaat resultaat) {
            }

            @Override
            public String getResultaat() {
                return "xml";
            }
        };
        geefDetailsPersoonService.verwerk(bevragingVerzoek, callback);

        final InOrder inOrder = inOrder(maakGeefDetailsPersoonBerichtService, archiveerBerichtService,
                protocolleerBerichtService);
        inOrder.verify(maakGeefDetailsPersoonBerichtService, times(1)).voerStappenUit(bevragingVerzoek);
        inOrder.verify(archiveerBerichtService, times(1)).archiveer(any(), any(), any());
        inOrder.verify(protocolleerBerichtService, times(1)).protocolleer(any(), any(), any());
    }

    @Test
    public void testHappyFlow_BerichtInResultaatIsNull() throws Exception {

        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel();
        final BevragingResultaat berichtResultaat = new BevragingResultaat();
        berichtResultaat.setAutorisatiebundel(autorisatiebundel);

        when(maakGeefDetailsPersoonBerichtService.voerStappenUit(bevragingVerzoek)).thenReturn(berichtResultaat);

        final BevragingCallback<String> callback = new BevragingCallback<String>() {
            @Override
            public void verwerkResultaat(final BevragingVerzoek bevragingVerzoek, final BevragingResultaat resultaat) {

            }

            @Override
            public String getResultaat() {
                return "xml";
            }
        };
        geefDetailsPersoonService.verwerk(bevragingVerzoek, callback);

        final InOrder inOrder = inOrder(maakGeefDetailsPersoonBerichtService, archiveerBerichtService,
                protocolleerBerichtService);
        inOrder.verify(maakGeefDetailsPersoonBerichtService, times(1)).voerStappenUit(bevragingVerzoek);
        inOrder.verify(archiveerBerichtService, times(1)).archiveer(any(), any(), any());
        inOrder.verify(protocolleerBerichtService, times(1)).protocolleer(any(), any(), any());
    }

    @Test(expected = RuntimeException.class)
    public void testExceptieMakenAntwoordBericht() throws Exception {
        doThrow(RuntimeException.class)
                .when(archiveerBerichtService).archiveer(any(), any(), any());
        final BevragingCallback<String> callback = new BevragingCallback<String>() {
            @Override
            public void verwerkResultaat(final BevragingVerzoek bevragingVerzoek, final BevragingResultaat resultaat) {

            }

            @Override
            public String getResultaat() {
                return "xml";
            }
        };
        geefDetailsPersoonService.verwerk(bevragingVerzoek, callback);
    }

    @Test(expected = RuntimeException.class)
    public void testExceptieArchiverenBericht() throws Exception {
        doThrow(RuntimeException.class).when(archiveerBerichtService).archiveer(any(), any(), any());
        final BevragingCallback<String> callback = new BevragingCallback<String>() {
            @Override
            public void verwerkResultaat(final BevragingVerzoek bevragingVerzoek, final BevragingResultaat resultaat) {

            }

            @Override
            public String getResultaat() {
                return "xml";
            }
        };
        geefDetailsPersoonService.verwerk(bevragingVerzoek, callback);
    }

    private Autorisatiebundel maakAutorisatiebundel() {
        final Partij partij = TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build();
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        final SoortDienst soortDienst = SoortDienst.ATTENDERING;
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);

        final Dienst dienst = AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst);
        return new Autorisatiebundel(tla, dienst);
    }


}
