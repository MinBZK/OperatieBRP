/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.stuf;

import static org.mockito.Matchers.any;

import java.time.ZonedDateTime;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.archivering.service.algemeen.ArchiefService;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.StufAntwoordBericht;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StufBerichtArchiveringServiceImplTest {

    static {
        BrpNu.set();
    }

    private static final String ZENDEND_SYSTEEM = "BRP";
    private static final String REF_NR = "RefNr";
    private static final String CROSS_REF_NR = "CrossRefNr";
    private static final int PARTIJ_ID = 99;
    private static final String RESULTAAT = "resultaat";
    private static final ZonedDateTime TS_VERZENDING = DatumUtil.nuAlsZonedDateTime();

    @InjectMocks
    private StufBerichtArchiveringServiceImpl stufBerichtArchiveringService;
    @Mock
    private ArchiefService archiefService;
    @Mock
    private PartijService partijService;

    @Before
    public void voorTest() {
        Mockito.when(partijService.geefBrpPartij()).thenReturn(TestPartijBuilder.maakBuilder().metId(PARTIJ_ID).metCode("000001").build());
        Mockito.doNothing().when(archiefService).archiveer(any());
    }

    @Captor final ArgumentCaptor<ArchiveringOpdracht> archiveringsBerichtCaptor = ArgumentCaptor.forClass(ArchiveringOpdracht.class);

    @Test
    public void archiveer() throws Exception {
        final Autorisatiebundel autorisatiebundel = TestAutorisaties.maakAutorisatiebundel(SoortDienst.GEEF_STUF_BG_BERICHT);
        final StufBerichtResultaat stufBerichtResultaat = maakStufBerichtResutaat(autorisatiebundel);
        final StufAntwoordBericht antwoordBericht = maakStufAntwoordBericht();

        stufBerichtArchiveringService.archiveer(stufBerichtResultaat, antwoordBericht, RESULTAAT);

        Mockito.verify(archiefService, Mockito.times(2)).archiveer(archiveringsBerichtCaptor.capture());
        List<ArchiveringOpdracht> archiveringOpdrachtList = archiveringsBerichtCaptor.getAllValues();
        //ingaand
        ArchiveringOpdracht ingaand = archiveringOpdrachtList.get(0);
        Assert.assertEquals(SoortBericht.STV_STV_GEEF_STUFBG_BERICHT, ingaand.getSoortBericht());
        Assert.assertEquals(ZENDEND_SYSTEEM, ingaand.getZendendeSysteem());
        Assert.assertEquals(REF_NR, ingaand.getReferentienummer());
        Assert.assertEquals(TS_VERZENDING, ingaand.getTijdstipVerzending());
        Assert.assertNotNull(ingaand.getTijdstipOntvangst());
        Assert.assertEquals(autorisatiebundel.getLeveringsautorisatieId(), ingaand.getLeveringsAutorisatieId().intValue());
        Assert.assertEquals(autorisatiebundel.getPartij().getId(), ingaand.getZendendePartijId());
        //uitgaand
        ArchiveringOpdracht uitgaand = archiveringOpdrachtList.get(1);
        Assert.assertEquals(SoortBericht.STV_STV_GEEF_STUFBG_BERICHT_R, uitgaand.getSoortBericht());
        Assert.assertEquals(PARTIJ_ID, uitgaand.getZendendePartijId().intValue());
        Assert.assertEquals(BasisBerichtGegevens.BRP_SYSTEEM, uitgaand.getZendendeSysteem());
        Assert.assertEquals(REF_NR, uitgaand.getReferentienummer());
        Assert.assertEquals(CROSS_REF_NR, uitgaand.getCrossReferentienummer());
        Assert.assertEquals(RESULTAAT, uitgaand.getData());
        Assert.assertEquals(TS_VERZENDING, uitgaand.getTijdstipVerzending());
        Assert.assertEquals(VerwerkingsResultaat.GESLAAGD, uitgaand.getVerwerkingsresultaat());
        Assert.assertEquals(SoortMelding.GEEN, uitgaand.getHoogsteMeldingsNiveau());
        Assert.assertEquals(autorisatiebundel.getPartij().getId(), uitgaand.getOntvangendePartijId());
        Assert.assertEquals(autorisatiebundel.getLeveringsautorisatieId(), uitgaand.getLeveringsAutorisatieId().intValue());
    }

    @Test
    public void archiveer_autorisatieBundelIsNull() throws Exception {
        final StufBerichtResultaat stufBerichtResultaat = maakStufBerichtResutaat(null);
        final StufAntwoordBericht antwoordBericht = maakStufAntwoordBericht();

        stufBerichtArchiveringService.archiveer(stufBerichtResultaat, antwoordBericht, RESULTAAT);

        Mockito.verify(archiefService, Mockito.times(2)).archiveer(archiveringsBerichtCaptor.capture());
        List<ArchiveringOpdracht> archiveringOpdrachtList = archiveringsBerichtCaptor.getAllValues();
        //ingaand
        ArchiveringOpdracht ingaand = archiveringOpdrachtList.get(0);
        Assert.assertEquals(SoortBericht.STV_STV_GEEF_STUFBG_BERICHT, ingaand.getSoortBericht());
        Assert.assertEquals(ZENDEND_SYSTEEM, ingaand.getZendendeSysteem());
        Assert.assertEquals(REF_NR, ingaand.getReferentienummer());
        Assert.assertEquals(TS_VERZENDING, ingaand.getTijdstipVerzending());
        Assert.assertNotNull(ingaand.getTijdstipOntvangst());
        Assert.assertNull(ingaand.getLeveringsAutorisatieId());
        Assert.assertNull(ingaand.getZendendePartijId());
        //uitgaand
        ArchiveringOpdracht uitgaand = archiveringOpdrachtList.get(1);
        Assert.assertEquals(SoortBericht.STV_STV_GEEF_STUFBG_BERICHT_R, uitgaand.getSoortBericht());
        Assert.assertEquals(PARTIJ_ID, uitgaand.getZendendePartijId().intValue());
        Assert.assertEquals(BasisBerichtGegevens.BRP_SYSTEEM, uitgaand.getZendendeSysteem());
        Assert.assertEquals(REF_NR, uitgaand.getReferentienummer());
        Assert.assertEquals(CROSS_REF_NR, uitgaand.getCrossReferentienummer());
        Assert.assertEquals("resultaat", uitgaand.getData());
        Assert.assertEquals(TS_VERZENDING, uitgaand.getTijdstipVerzending());
        Assert.assertEquals(VerwerkingsResultaat.GESLAAGD, uitgaand.getVerwerkingsresultaat());
        Assert.assertEquals(SoortMelding.GEEN, uitgaand.getHoogsteMeldingsNiveau());
        Assert.assertNull(uitgaand.getOntvangendePartijId());
        Assert.assertNull(uitgaand.getLeveringsAutorisatieId());
    }

    private StufAntwoordBericht maakStufAntwoordBericht() {
        //@formatter:off
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
            .metStuurgegevens()
                    .metZendendeSysteem(ZENDEND_SYSTEEM)
                    .metReferentienummer(REF_NR)
                    .metCrossReferentienummer(CROSS_REF_NR)
                    .metTijdstipVerzending(TS_VERZENDING)
            .eindeStuurgegevens()
        .build();
        //@formatter:on
        return new StufAntwoordBericht(basisBerichtGegevens, null);
    }

    private StufBerichtResultaat maakStufBerichtResutaat(final Autorisatiebundel autorisatiebundel) {
        final StufBerichtVerzoek stufBerichtVerzoek = new StufBerichtVerzoek();
        stufBerichtVerzoek.setXmlBericht("xmlBericht");
        stufBerichtVerzoek.getStuurgegevens().setZendendSysteem(ZENDEND_SYSTEEM);
        stufBerichtVerzoek.getStuurgegevens().setReferentieNummer(REF_NR);
        stufBerichtVerzoek.getStuurgegevens().setTijdstipVerzending(TS_VERZENDING);
        final StufBerichtResultaat stufBerichtResultaat = new StufBerichtResultaat(stufBerichtVerzoek);
        stufBerichtResultaat.setAutorisatiebundel(autorisatiebundel);
        return stufBerichtResultaat;
    }

}
