/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.vrijbericht;

import static org.mockito.Mockito.times;

import java.time.ZonedDateTime;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.archivering.service.algemeen.ArchiefService;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.VrijBerichtAntwoordBericht;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VrijBerichtArchiveringServiceImplTest {

    private static final ZonedDateTime NU = DatumUtil.nuAlsZonedDateTime();
    private static final String VERZOEK_BERICHT = "xml";
    private static final String VERZOEK_REF_NR = "refNrVerzoek";
    private static final String VERZOEK_ZENDEND_SYSTEEM = "zendendSysteem";
    private static final String VERZOEK_ZENDER_VRIJBERICHT = "000123";
    private static final Long RESULTAAT_ADMHND_ID = 1L;
    private static final String RESULTAAT_REF_NR = "refNrResultaat";
    private static final String RESULTAAT_CROSSREF_NR = "crossRefNrResultaat";
    private static final String RESULTAAT_BERICHT = "resultaat";
    private static final String RESULTAAT_BRP_PARTIJCODE = "001234";
    private static final Short VERZOEK_ZENDER_VRIJBERICHT_ID = 2;

    @InjectMocks
    private VrijBerichtArchiveringServiceImpl vrijBerichtArchiveringService;
    @Mock
    private ArchiefService archiefService;
    @Mock
    private PartijService partijService;

    @Before
    public void voorTest() {
        BrpNu.set();

        final Partij brpPartij = TestPartijBuilder.maakBuilder().metId(1).metCode(RESULTAAT_BRP_PARTIJCODE).build();
        Mockito.when(partijService.geefBrpPartij()).thenReturn(brpPartij);
        final Partij zendendePartij = TestPartijBuilder.maakBuilder().metId(2).metCode(VERZOEK_ZENDER_VRIJBERICHT).build();
        Mockito.when(partijService.vindPartijIdOpCode(VERZOEK_ZENDER_VRIJBERICHT)).thenReturn(zendendePartij.getId());
    }

    @Test
    public void archiveer() {
        final VrijBerichtVerzoek verzoek = maakVerzoek();
        final VrijBerichtResultaat vrijBerichtResultaat = new VrijBerichtResultaat(verzoek);
        final VrijBerichtAntwoordBericht antwoordBericht = maakAntwoordBericht();

        vrijBerichtArchiveringService.archiveer(vrijBerichtResultaat, antwoordBericht, "resultaat");

        final ArgumentCaptor<ArchiveringOpdracht> archiveringsBerichtCaptor = ArgumentCaptor.forClass(ArchiveringOpdracht.class);

        Mockito.verify(archiefService, times(2)).archiveer(archiveringsBerichtCaptor.capture());
        final List<ArchiveringOpdracht> opgeslagenArchiveringsBerichten = archiveringsBerichtCaptor.getAllValues();

        //controleer verzoek
        final ArchiveringOpdracht verzoekArchiveringsOpdracht = opgeslagenArchiveringsBerichten.get(0);
        Assert.assertEquals(Richting.INGAAND, verzoekArchiveringsOpdracht.getRichting());
        Assert.assertEquals(VERZOEK_BERICHT, verzoekArchiveringsOpdracht.getData());
        Assert.assertEquals(VERZOEK_REF_NR, verzoekArchiveringsOpdracht.getReferentienummer());
        Assert.assertEquals(VERZOEK_ZENDEND_SYSTEEM, verzoekArchiveringsOpdracht.getZendendeSysteem());
        Assert.assertEquals(2, (int) verzoekArchiveringsOpdracht.getZendendePartijId());
        Assert.assertEquals(NU, verzoekArchiveringsOpdracht.getTijdstipVerzending());
        Assert.assertNotNull(verzoekArchiveringsOpdracht.getTijdstipOntvangst());
        Assert.assertEquals(SoortBericht.VRB_VRB_STUUR_VRIJ_BERICHT, verzoekArchiveringsOpdracht.getSoortBericht());

        //controleer antwoord(resultaat)bericht
        final ArchiveringOpdracht antwoordArchiveringsOpdracht = opgeslagenArchiveringsBerichten.get(1);
        Assert.assertEquals(Richting.UITGAAND, antwoordArchiveringsOpdracht.getRichting());
        Assert.assertEquals(SoortBericht.VRB_VRB_STUUR_VRIJ_BERICHT_R, antwoordArchiveringsOpdracht.getSoortBericht());
        Assert.assertEquals(1, (int) antwoordArchiveringsOpdracht.getZendendePartijId());
        Assert.assertEquals(BasisBerichtGegevens.BRP_SYSTEEM, antwoordArchiveringsOpdracht.getZendendeSysteem());
        Assert.assertEquals(RESULTAAT_REF_NR, antwoordArchiveringsOpdracht.getReferentienummer());
        Assert.assertEquals(RESULTAAT_CROSSREF_NR, antwoordArchiveringsOpdracht.getCrossReferentienummer());
        Assert.assertEquals(RESULTAAT_BERICHT, antwoordArchiveringsOpdracht.getData());
        Assert.assertEquals(NU, antwoordArchiveringsOpdracht.getTijdstipVerzending());
        Assert.assertEquals(VerwerkingsResultaat.GESLAAGD, antwoordArchiveringsOpdracht.getVerwerkingsresultaat());
        Assert.assertEquals(SoortMelding.GEEN, antwoordArchiveringsOpdracht.getHoogsteMeldingsNiveau());
        Assert.assertEquals(VERZOEK_ZENDER_VRIJBERICHT_ID, antwoordArchiveringsOpdracht.getOntvangendePartijId());
    }

    private VrijBerichtVerzoek maakVerzoek() {
        final VrijBerichtVerzoek verzoek = new VrijBerichtVerzoek();
        verzoek.setXmlBericht(VERZOEK_BERICHT);
        verzoek.getStuurgegevens().setReferentieNummer(VERZOEK_REF_NR);
        verzoek.getStuurgegevens().setZendendePartijCode(VERZOEK_ZENDER_VRIJBERICHT);
        verzoek.getStuurgegevens().setZendendSysteem(VERZOEK_ZENDEND_SYSTEEM);
        verzoek.getStuurgegevens().setTijdstipVerzending(NU);
        verzoek.getParameters().setZenderVrijBericht(VERZOEK_ZENDER_VRIJBERICHT);
        return verzoek;
    }

    private VrijBerichtAntwoordBericht maakAntwoordBericht() {
        //@formatter:off
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                .metAdministratieveHandelingId(RESULTAAT_ADMHND_ID)
                .metStuurgegevens()
                    .metTijdstipVerzending(NU)
                    .metReferentienummer(RESULTAAT_REF_NR)
                    .metCrossReferentienummer(RESULTAAT_CROSSREF_NR)
                .eindeStuurgegevens()
                .build();
        //@formatter:on
        return new VrijBerichtAntwoordBericht(basisBerichtGegevens);
    }
}
