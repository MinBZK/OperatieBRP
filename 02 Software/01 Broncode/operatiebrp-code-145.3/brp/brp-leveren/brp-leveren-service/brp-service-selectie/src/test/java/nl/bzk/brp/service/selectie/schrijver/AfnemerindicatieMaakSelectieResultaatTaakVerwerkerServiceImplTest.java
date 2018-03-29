/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.schrijver;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.EffectAfnemerindicaties;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.internbericht.selectie.MaakSelectieResultaatTaak;
import nl.bzk.brp.domain.internbericht.selectie.SelectieTaakResultaat;
import nl.bzk.brp.domain.internbericht.selectie.TypeResultaat;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieService;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.selectie.publicatie.SelectieTaakResultaatPublicatieService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test voor {@link AfnemerindicatieMaakSelectieResultaatTaakVerwerkerServiceImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
public class AfnemerindicatieMaakSelectieResultaatTaakVerwerkerServiceImplTest {

    private static final int LEVERINGSAUTORISATIE_ID = 1;
    private static final int DIENST_ID = 2;
    private static final short PARTIJ_ID = (short) 3;
    private static final int TOEGANG_LEVERINGSAUTORISATIE_ID = 4;

    @InjectMocks
    private AfnemerindicatieMaakSelectieResultaatTaakVerwerkerServiceImpl service;
    @Mock
    private PartijService partijService;
    @Mock
    private LeveringsautorisatieService leveringsautorisatieService;
    @Mock
    private SelectieResultaatWriterFactory selectieResultaatWriterFactory;
    @Mock
    private SelectieTaakResultaatPublicatieService selectieTaakResultaatPublicatieService;

    private MaakSelectieResultaatTaak resultaatTaak;

    @Before
    public void before() throws Exception {
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        leveringsautorisatie.setId(LEVERINGSAUTORISATIE_ID);
        final Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
        final Dienst dienst = new Dienst(dienstbundel, SoortDienst.SELECTIE);
        dienst.setId(DIENST_ID);
        dienst.setEffectAfnemerindicaties(EffectAfnemerindicaties.PLAATSING);
        dienst.setIndVerzVolBerBijWijzAfniNaSelectie(true);
        dienstbundel.addDienstSet(dienst);
        leveringsautorisatie.addDienstbundelSet(dienstbundel);
        final Partij partij = new Partij("test", "000001");
        partij.setId(PARTIJ_ID);
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = new ToegangLeveringsAutorisatie(new PartijRol(partij,
                Rol.AFNEMER), leveringsautorisatie);
        toegangLeveringsAutorisatie.setId(TOEGANG_LEVERINGSAUTORISATIE_ID);

        resultaatTaak = new MaakSelectieResultaatTaak();
        resultaatTaak.setToegangLeveringsAutorisatieId(TOEGANG_LEVERINGSAUTORISATIE_ID);
        resultaatTaak.setDienstId(DIENST_ID);
        resultaatTaak.setSelectietaakId(1);
        resultaatTaak.setSelectieRunId(1);
        resultaatTaak.setAantalPersonen(20);
        when(leveringsautorisatieService.geefToegangLeveringsAutorisatie(resultaatTaak.getToegangLeveringsAutorisatieId()))
                .thenReturn(toegangLeveringsAutorisatie);
        when(partijService.geefBrpPartij()).thenReturn(TestPartijBuilder.maakBuilder().metCode("000001").build());
    }

    @Test
    public void testResultaatCorrectVerwerkt() throws Exception {
        final SelectieResultaatWriterFactory.TotalenBestandWriter totalenBestandWriter = mock(SelectieResultaatWriterFactory.TotalenBestandWriter.class);
        when(selectieResultaatWriterFactory.totalenWriterBrp(any(), any())).thenReturn(totalenBestandWriter);

        service.verwerk(resultaatTaak);

        final InOrder inOrder = inOrder(totalenBestandWriter, selectieTaakResultaatPublicatieService);
        inOrder.verify(totalenBestandWriter).schrijfTotalen(resultaatTaak.getAantalPersonen(), 1);
        final SelectieTaakResultaat selectieTaakResultaat = new SelectieTaakResultaat();
        selectieTaakResultaat.setType(TypeResultaat.SELECTIE_RESULTAAT_SCHRIJF);
        inOrder.verify(selectieTaakResultaatPublicatieService).publiceerSelectieTaakResultaat(refEq(selectieTaakResultaat));
    }

    @Test
    public void testFoutBijVerwerking() throws Exception {
        doThrow(new SelectieResultaatVerwerkException(null)).when(selectieResultaatWriterFactory).totalenWriterBrp(any(), any());

        service.verwerk(resultaatTaak);

        verify(selectieTaakResultaatPublicatieService).publiceerFout();
        verifyNoMoreInteractions(selectieTaakResultaatPublicatieService);
    }
}
