/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.Lists;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.BerichtVerwerkingsResultaat;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.internbericht.verzendingmodel.SynchronisatieBerichtGegevens;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SynchronisatieBerichtGegevensFactoryImplTest {

    private static final int DATUM_AANVANG_MATERIELE_PERIODE = 20110101;
    private static final int PARTIJ_ID = 999;
    private static final int PARTIJ_ID_BRP = 998;
    private static final String BERICHT = "persoonbericht";
    private static final String BERICHT_GBA = "00000000Sv010000000000";
    private static final String PARTIJ_CODE = "000001";
    private static final String REF_NR = "refNr";
    private static final String ZENDENDE_SYSTEEM = "zendendeSysteem";
    private static final ZonedDateTime TS_VERZENDING = ZonedDateTime.of(2016, 9, 27, 14, 17, 0, 0, ZoneId.of("UTC"));

    private final Autorisatiebundel autorisatiebundel = TestAutorisaties.bundelMetRol(Rol.AFNEMER, maakDienst());
    private final Partij partij = TestPartijBuilder.maakBuilder().metId(PARTIJ_ID).metCode(PARTIJ_CODE).build();
    private final Partij partij_brp = TestPartijBuilder.maakBuilder().metId(PARTIJ_ID_BRP).metCode(Partij.PARTIJ_CODE_BRP).build();
    private final Dienst dienst = maakDienst();

    //@formatter:off
    private BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                        .metPartijCode(PARTIJ_CODE)
                        .metStuurgegevens()
                            .metReferentienummer(REF_NR)
                            .metTijdstipVerzending(TS_VERZENDING)
                            .metZendendePartij(partij_brp)
                            .metOntvangendePartij(partij)
                            .metZendendeSysteem(ZENDENDE_SYSTEEM)
                        .eindeStuurgegevens()
                        .metParameters()
                            .metDienst(dienst)
                            .metSoortSynchronisatie(SoortSynchronisatie.VOLLEDIG_BERICHT)
                        .eindeParameters()
                        .metResultaat(BerichtVerwerkingsResultaat.builder()
                                                    .metHoogsteMeldingsniveau(SoortMelding.GEEN.getNaam())
                                                    .metVerwerking(VerwerkingsResultaat.GESLAAGD)
                                                .build())
                        .build();
    //@formatter:on
    private final VerwerkPersoonBericht verwerkPersoonBericht = new VerwerkPersoonBericht(basisBerichtGegevens, autorisatiebundel, Lists.newArrayList());

    @InjectMocks
    private SynchronisatieBerichtGegevensFactoryImpl synchronisatieBerichtGegevensFactory;

    @Mock
    private BepaalGeleverdePersonenService bepaalGeleverdePersonenService;
    @Mock
    private MaakPersoonBerichtService maakPersoonBerichtService;
    @Mock
    private MaakPersoonBerichtService maakGbaPersoonBerichtService;


    @Before
    public void voorTest() throws Exception {
        final BepaalGeleverdePersonenService.Resultaat resultaat =
                new BepaalGeleverdePersonenService.Resultaat(DATUM_AANVANG_MATERIELE_PERIODE, Lists.newArrayList(), Lists.newArrayList());
        Mockito.when(maakPersoonBerichtService.maakPersoonBericht(verwerkPersoonBericht)).thenReturn(BERICHT);
        Mockito.when(maakGbaPersoonBerichtService.maakPersoonBericht(verwerkPersoonBericht)).thenReturn(BERICHT_GBA);
        Mockito.when(bepaalGeleverdePersonenService.bepaal(autorisatiebundel.getSoortDienst(), autorisatiebundel.getLeveringsautorisatieId(),
                verwerkPersoonBericht.getBijgehoudenPersonen().stream().map(BijgehoudenPersoon::getPersoonslijst).collect(Collectors.toList()),
                DATUM_AANVANG_MATERIELE_PERIODE)).thenReturn(resultaat);
    }

    @Test
    public void testMaakBericht() throws StapException {
        SynchronisatieBerichtGegevens synchronisatieBerichtGegevens =
                synchronisatieBerichtGegevensFactory.maak(verwerkPersoonBericht, autorisatiebundel, DATUM_AANVANG_MATERIELE_PERIODE);

        assertEquals(Stelsel.BRP, synchronisatieBerichtGegevens.getStelsel());
        assertEquals(SoortDienst.SYNCHRONISATIE_PERSOON, synchronisatieBerichtGegevens.getSoortDienst());
        assertNull(synchronisatieBerichtGegevens.getBrpEndpointURI());
        assertNull(synchronisatieBerichtGegevens.getProtocolleringsniveau());
        //tbv archivering
        assertEquals(Richting.UITGAAND, synchronisatieBerichtGegevens.getArchiveringOpdracht().getRichting());
        assertEquals(SoortSynchronisatie.VOLLEDIG_BERICHT, synchronisatieBerichtGegevens.getArchiveringOpdracht().getSoortSynchronisatie());
        assertEquals(SoortBericht.LVG_SYN_VERWERK_PERSOON, synchronisatieBerichtGegevens.getArchiveringOpdracht().getSoortBericht());
        assertEquals(REF_NR, synchronisatieBerichtGegevens.getArchiveringOpdracht().getReferentienummer());
        assertEquals(TS_VERZENDING, synchronisatieBerichtGegevens.getArchiveringOpdracht().getTijdstipVerzending());
        assertEquals(PARTIJ_ID, synchronisatieBerichtGegevens.getArchiveringOpdracht().getOntvangendePartijId().intValue());
        assertEquals(PARTIJ_ID_BRP, synchronisatieBerichtGegevens.getArchiveringOpdracht().getZendendePartijId().intValue());
        assertEquals(ZENDENDE_SYSTEEM, synchronisatieBerichtGegevens.getArchiveringOpdracht().getZendendeSysteem());
        assertEquals(dienst.getId(), synchronisatieBerichtGegevens.getArchiveringOpdracht().getDienstId());
        assertEquals(autorisatiebundel.getLeveringsautorisatieId(),
                synchronisatieBerichtGegevens.getArchiveringOpdracht().getLeveringsAutorisatieId().intValue());
        assertEquals(BERICHT, synchronisatieBerichtGegevens.getArchiveringOpdracht().getData());
        assertTrue(synchronisatieBerichtGegevens.getArchiveringOpdracht().getTeArchiverenPersonen().isEmpty());
        //tbv protocollering
        assertEquals(TS_VERZENDING, synchronisatieBerichtGegevens.getProtocolleringOpdracht().getDatumTijdEindeFormelePeriodeResultaat());
        assertEquals(dienst.getId(), synchronisatieBerichtGegevens.getProtocolleringOpdracht().getDienstId());
        assertEquals(autorisatiebundel.getToegangLeveringsautorisatie().getId(),
                synchronisatieBerichtGegevens.getProtocolleringOpdracht().getToegangLeveringsautorisatieId());
        assertEquals(SoortSynchronisatie.VOLLEDIG_BERICHT, synchronisatieBerichtGegevens.getProtocolleringOpdracht().getSoortSynchronisatie());
        assertEquals(TS_VERZENDING, synchronisatieBerichtGegevens.getProtocolleringOpdracht().getDatumTijdKlaarzettenLevering());
        assertEquals(DATUM_AANVANG_MATERIELE_PERIODE,
                synchronisatieBerichtGegevens.getProtocolleringOpdracht().getDatumAanvangMaterielePeriodeResultaat().intValue());
        assertTrue(synchronisatieBerichtGegevens.getProtocolleringOpdracht().getGeleverdePersonen().isEmpty());
    }

    @Test
    public void testMaakBericht_GBAStelsel() throws StapException {
        autorisatiebundel.getLeveringsautorisatie().setStelsel(Stelsel.GBA);

        SynchronisatieBerichtGegevens synchronisatieBerichtGegevens =
                synchronisatieBerichtGegevensFactory.maak(verwerkPersoonBericht, autorisatiebundel, DATUM_AANVANG_MATERIELE_PERIODE);

        assertEquals(Stelsel.GBA, synchronisatieBerichtGegevens.getStelsel());
        assertEquals(SoortDienst.SYNCHRONISATIE_PERSOON, synchronisatieBerichtGegevens.getSoortDienst());
        assertNull(synchronisatieBerichtGegevens.getBrpEndpointURI());
        assertNull(synchronisatieBerichtGegevens.getProtocolleringsniveau());
        //tbv archivering
        assertEquals(Richting.UITGAAND, synchronisatieBerichtGegevens.getArchiveringOpdracht().getRichting());
        assertEquals(SoortSynchronisatie.VOLLEDIG_BERICHT, synchronisatieBerichtGegevens.getArchiveringOpdracht().getSoortSynchronisatie());
        assertEquals(SoortBericht.SV01, synchronisatieBerichtGegevens.getArchiveringOpdracht().getSoortBericht());
        assertEquals(REF_NR, synchronisatieBerichtGegevens.getArchiveringOpdracht().getReferentienummer());
        assertEquals(TS_VERZENDING, synchronisatieBerichtGegevens.getArchiveringOpdracht().getTijdstipVerzending());
        assertEquals(PARTIJ_ID, synchronisatieBerichtGegevens.getArchiveringOpdracht().getOntvangendePartijId().intValue());
        assertEquals(PARTIJ_ID_BRP, synchronisatieBerichtGegevens.getArchiveringOpdracht().getZendendePartijId().intValue());
        assertEquals(ZENDENDE_SYSTEEM, synchronisatieBerichtGegevens.getArchiveringOpdracht().getZendendeSysteem());
        assertEquals(dienst.getId(), synchronisatieBerichtGegevens.getArchiveringOpdracht().getDienstId());
        assertEquals(autorisatiebundel.getLeveringsautorisatieId(),
                synchronisatieBerichtGegevens.getArchiveringOpdracht().getLeveringsAutorisatieId().intValue());
        assertEquals(BERICHT_GBA, synchronisatieBerichtGegevens.getArchiveringOpdracht().getData());
        assertTrue(synchronisatieBerichtGegevens.getArchiveringOpdracht().getTeArchiverenPersonen().isEmpty());
        //tbv protocollering
        assertEquals(TS_VERZENDING, synchronisatieBerichtGegevens.getProtocolleringOpdracht().getDatumTijdEindeFormelePeriodeResultaat());
        assertEquals(dienst.getId(), synchronisatieBerichtGegevens.getProtocolleringOpdracht().getDienstId());
        assertEquals(autorisatiebundel.getToegangLeveringsautorisatie().getId(),
                synchronisatieBerichtGegevens.getProtocolleringOpdracht().getToegangLeveringsautorisatieId());
        assertEquals(SoortSynchronisatie.VOLLEDIG_BERICHT, synchronisatieBerichtGegevens.getProtocolleringOpdracht().getSoortSynchronisatie());
        assertEquals(TS_VERZENDING, synchronisatieBerichtGegevens.getProtocolleringOpdracht().getDatumTijdKlaarzettenLevering());
        assertEquals(DATUM_AANVANG_MATERIELE_PERIODE,
                synchronisatieBerichtGegevens.getProtocolleringOpdracht().getDatumAanvangMaterielePeriodeResultaat().intValue());
        assertTrue(synchronisatieBerichtGegevens.getProtocolleringOpdracht().getGeleverdePersonen().isEmpty());
    }


    private Dienst maakDienst() {
        final Leveringsautorisatie levaut = TestAutorisaties.metSoortDienst(SoortDienst.SYNCHRONISATIE_PERSOON);
        final TestAutorisaties.GroepDefinitie groepDefinitie = new TestAutorisaties.GroepDefinitie();
        groepDefinitie.element = Element.PERSOON_VOORNAAM_STANDAARD;
        return TestAutorisaties.maakDienst(levaut, Element.ADMINISTRATIEVEHANDELING_SOORTNAAM, SoortDienst.SYNCHRONISATIE_PERSOON, groepDefinitie);
    }
}
