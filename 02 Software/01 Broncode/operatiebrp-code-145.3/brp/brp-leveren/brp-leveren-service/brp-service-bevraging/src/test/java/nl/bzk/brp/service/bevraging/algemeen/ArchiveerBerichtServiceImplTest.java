/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.algemeen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.ZonedDateTime;
import java.util.Collections;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.archivering.service.algemeen.ArchiefService;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.service.bevraging.detailspersoon.GeefDetailsPersoonVerzoek;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ArchiveerBerichtServiceImplTest {

    private static final ZonedDateTime ZDT_NU = ZonedDateTime.now();
    private static final short ZENDENDE_PARTIJ_ID = 1;
    private static final int LEV_AUT_ID = 2;
    private static final int DIENST_ID = 3;
    private static final int BRP_PARTIJ_ID = 4;

    @BeforeClass
    public static void before() {
        BrpNu.set(ZDT_NU);
    }

    @InjectMocks
    private ArchiveerBerichtServiceImpl<GeefDetailsPersoonVerzoek, BevragingResultaat> archiveerBerichtService;

    @Mock
    private ArchiefService archiefService;

    @Captor
    private ArgumentCaptor<ArchiveringOpdracht> archiveringOpdrachtArgCaptor;

    @Test
    public void testArchiveer() {
        final GeefDetailsPersoonVerzoek verzoek = new GeefDetailsPersoonVerzoek();
        verzoek.setXmlBericht("xmlIn");
        verzoek.getStuurgegevens().setReferentieNummer("refNrIn");
        verzoek.getStuurgegevens().setZendendSysteem("zendendeSysteem");
        verzoek.getStuurgegevens().setTijdstipVerzending(ZDT_NU);
        verzoek.setSoortDienst(SoortDienst.GEEF_DETAILS_PERSOON);
        final BevragingResultaat berichtResultaat = new BevragingResultaat();
        berichtResultaat.setAutorisatiebundel(maakAutorisatieBundel());
        berichtResultaat.setBrpPartij(TestPartijBuilder.maakBuilder().metId(BRP_PARTIJ_ID).metCode("123456").build());
        final AntwoordBerichtResultaat antwoordBerichtResultaat =
                new AntwoordBerichtResultaat("xmlUit", ZDT_NU, "refNrUit", Collections.emptyList());

        archiveerBerichtService.archiveer(verzoek, berichtResultaat, antwoordBerichtResultaat);

        Mockito.verify(archiefService, Mockito.times(2)).archiveer(archiveringOpdrachtArgCaptor.capture());
        //asserts ingaand
        final ArchiveringOpdracht opdrachtIn = archiveringOpdrachtArgCaptor.getAllValues().get(0);
        assertEquals("xmlIn", opdrachtIn.getData());
        assertEquals("refNrIn", opdrachtIn.getReferentienummer());
        assertEquals("zendendeSysteem", opdrachtIn.getZendendeSysteem());
        assertEquals(ZDT_NU, opdrachtIn.getTijdstipVerzending());
        assertNotNull(opdrachtIn.getTijdstipOntvangst());
        assertEquals(SoortBericht.LVG_BVG_GEEF_DETAILS_PERSOON, opdrachtIn.getSoortBericht());
        assertEquals(DIENST_ID, opdrachtIn.getDienstId().intValue());
        assertEquals(LEV_AUT_ID, opdrachtIn.getLeveringsAutorisatieId().intValue());
        assertEquals(ZENDENDE_PARTIJ_ID, opdrachtIn.getZendendePartijId().intValue());
        assertNull(opdrachtIn.getRolId());

        //assert uitgaand
        final ArchiveringOpdracht opdrachtUit = archiveringOpdrachtArgCaptor.getAllValues().get(1);
        assertEquals("xmlUit", opdrachtUit.getData());
        assertEquals(ZDT_NU, opdrachtUit.getTijdstipVerzending());
        assertEquals(SoortBericht.LVG_BVG_GEEF_DETAILS_PERSOON_R, opdrachtUit.getSoortBericht());
        assertEquals("refNrUit", opdrachtUit.getReferentienummer());
        assertEquals("refNrIn", opdrachtUit.getCrossReferentienummer());
        assertEquals(SoortMelding.GEEN, opdrachtUit.getHoogsteMeldingsNiveau());
        assertEquals(LEV_AUT_ID, opdrachtUit.getLeveringsAutorisatieId().intValue());
        assertEquals(BRP_PARTIJ_ID, opdrachtUit.getZendendePartijId().intValue());
        assertEquals(BasisBerichtGegevens.BRP_SYSTEEM, opdrachtUit.getZendendeSysteem());
        assertEquals(ZENDENDE_PARTIJ_ID, opdrachtUit.getOntvangendePartijId().intValue());
        assertEquals("BRP", opdrachtUit.getZendendeSysteem());
    }

    private Autorisatiebundel maakAutorisatieBundel() {
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        leveringsautorisatie.setId(LEV_AUT_ID);
        final TestAutorisaties.GroepDefinitie groepDefinitie = new TestAutorisaties.GroepDefinitie();
        groepDefinitie.element = Element.PERSOON_VOORNAAM_STANDAARD;
        final Dienst dienst = TestAutorisaties.maakDienst(leveringsautorisatie, null, SoortDienst.ATTENDERING, groepDefinitie);
        dienst.setId(DIENST_ID);
        final Autorisatiebundel autorisatiebundel = TestAutorisaties.bundelMetRol(Rol.AFNEMER, dienst);
        autorisatiebundel.getPartij().setId(ZENDENDE_PARTIJ_ID);
        return autorisatiebundel;
    }
}
