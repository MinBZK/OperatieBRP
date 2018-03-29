/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.brp;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Protocolleringsniveau;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.BerichtElement;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.internbericht.verzendingmodel.SynchronisatieBerichtGegevens;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.protocollering.domain.algemeen.ProtocolleringOpdracht;
import nl.bzk.brp.service.algemeen.BepaalGeleverdePersonenService;
import nl.bzk.brp.service.algemeen.MaakPersoonBerichtService;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.mutatielevering.dto.Mutatiebericht;
import nl.bzk.brp.service.mutatielevering.dto.Mutatiehandeling;
import nl.bzk.brp.service.mutatielevering.dto.Mutatielevering;
import org.junit.Assert;
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
public class MaakBrpBerichtServiceImplTest {

    @InjectMocks
    private MaakBrpBerichtServiceImpl service;

    @Mock
    private MutatieleveringBerichtFactory mutatieleveringBerichtFactory;
    @Mock
    private BepaalGeleverdePersonenService bepaalGeleverdePersonenService;
    @Mock
    private MaakPersoonBerichtService maakPersoonBerichtService;
    @Captor
    private ArgumentCaptor<List<Persoonslijst>> persoonslijstCaptor;

    private final Partij brpPartij = TestPartijBuilder.maakBuilder().metId(20).metCode("000020").build();

    @BeforeClass
    public static void init() {
        BrpNu.set();
    }

    @Test
    public void testMaakBerichten() throws StapException {
        final MetaObject metaObject = TestBuilders.PERSOON_MET_HANDELINGEN.getMetaObject();

        final Long administratieveHandelingId = 19L;
        final SoortDienst soortDienst = SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE;
        final String afleverpunt = "localhost/endpoint";
        final String testBerichtStr = "test";

        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(soortDienst);
        final Mutatielevering mutatielevering = new Mutatielevering(autorisatiebundel, Maps.newHashMap());
        final Mutatiehandeling mutatiehandeling = new Mutatiehandeling(administratieveHandelingId, Maps.newHashMap());

        final ArrayList<Mutatielevering> list = Lists.newArrayList(mutatielevering);
        autorisatiebundel.getToegangLeveringsautorisatie().setAfleverpunt(afleverpunt);
        final BepaalGeleverdePersonenService.Resultaat resultaat = new BepaalGeleverdePersonenService.Resultaat(20100101, emptyList(), emptyList());
        Mockito.when(bepaalGeleverdePersonenService.bepaal(Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.any()))
                .thenReturn(resultaat);

        VerwerkPersoonBericht leverBericht = maakVerwerkBericht(metaObject, autorisatiebundel);

        when(mutatieleveringBerichtFactory.apply(Mockito.any(), Mockito.eq(mutatiehandeling))).thenReturn(Lists
                .newArrayList(leverBericht));
        when(maakPersoonBerichtService.maakPersoonBericht(leverBericht)).thenReturn(testBerichtStr);

        final List<Mutatiebericht> berichten = service.maakBerichten(list, mutatiehandeling);

        verify(bepaalGeleverdePersonenService)
                .bepaal(eq(soortDienst), eq(1), persoonslijstCaptor.capture(), eq(null));
        verify(maakPersoonBerichtService, Mockito.times(1))
                .maakPersoonBericht(eq(leverBericht));
        assertEquals(1, berichten.size());
        final List<Persoonslijst> persoonslijstList = persoonslijstCaptor.getValue();
        assertThat(persoonslijstList.size(), is(1));
        assertThat(persoonslijstList.get(0).getMetaObject(), is(metaObject));

        final Mutatiebericht mutatiebericht = berichten.get(0);
        //controleer stuurgegevens
        final SynchronisatieBerichtGegevens synchronisatieBerichtGegevens = mutatiebericht.getStuurgegevensBericht();

        Assert.assertNotNull(synchronisatieBerichtGegevens);

        final ArchiveringOpdracht archiveringOpdracht = synchronisatieBerichtGegevens.getArchiveringOpdracht();
        final ProtocolleringOpdracht protocolleringOpdracht = synchronisatieBerichtGegevens.getProtocolleringOpdracht();

        Assert.assertNotNull(archiveringOpdracht);
        Assert.assertNotNull(protocolleringOpdracht);

        //controleer synchronisatie bericht gegevens
        Assert.assertEquals(Stelsel.BRP, synchronisatieBerichtGegevens.getStelsel());
        Assert.assertEquals(soortDienst, synchronisatieBerichtGegevens.getSoortDienst());
        Assert.assertEquals(afleverpunt, synchronisatieBerichtGegevens.getBrpEndpointURI());
        Assert.assertEquals(autorisatiebundel.getLeveringsautorisatie().getProtocolleringsniveau(), synchronisatieBerichtGegevens.getProtocolleringsniveau());
        Assert.assertEquals(archiveringOpdracht, synchronisatieBerichtGegevens.getArchiveringOpdracht());
        Assert.assertEquals(protocolleringOpdracht, synchronisatieBerichtGegevens.getProtocolleringOpdracht());

        //controleer archivering bericht
        Assert.assertEquals(administratieveHandelingId, archiveringOpdracht.getAdministratieveHandelingId());
        Assert.assertEquals(brpPartij.getId(), archiveringOpdracht.getZendendePartijId());
        Assert.assertEquals(testBerichtStr, archiveringOpdracht.getData());
        Assert.assertEquals(autorisatiebundel.getLeveringsautorisatie().getId(), archiveringOpdracht.getLeveringsAutorisatieId());
        Assert.assertEquals(autorisatiebundel.getRol().getId(), archiveringOpdracht.getRolId().intValue());
        Assert.assertEquals(autorisatiebundel.getDienst().getId(), archiveringOpdracht.getDienstId());
        Assert.assertEquals(leverBericht.getBasisBerichtGegevens().getStuurgegevens().getZendendeSysteem(), archiveringOpdracht.getZendendeSysteem());
        Assert.assertEquals(leverBericht.getBasisBerichtGegevens().getStuurgegevens().getOntvangendePartij().getId(),
                archiveringOpdracht.getOntvangendePartijId());
        Assert.assertEquals(leverBericht.getBasisBerichtGegevens().getStuurgegevens().getOntvangendePartij().getId(),
                archiveringOpdracht.getOntvangendePartijId());
        Assert.assertEquals(leverBericht.getBasisBerichtGegevens().getStuurgegevens().getTijdstipVerzending(), archiveringOpdracht.getTijdstipVerzending());
        Assert.assertEquals(leverBericht.getBasisBerichtGegevens().getStuurgegevens().getReferentienummer(), archiveringOpdracht.getReferentienummer());
        Assert.assertEquals(leverBericht.getBasisBerichtGegevens().getParameters().getSoortSynchronisatie(), archiveringOpdracht.getSoortSynchronisatie());
        Assert.assertEquals(resultaat.getGeleverdePersoonIds(), Lists.newArrayList(archiveringOpdracht.getTeArchiverenPersonen()));

        //controleer levering bericht
        Assert.assertEquals(administratieveHandelingId, protocolleringOpdracht.getAdministratieveHandelingId());
        Assert.assertEquals(BrpNu.get().getDatum(), protocolleringOpdracht.getDatumTijdEindeFormelePeriodeResultaat());
        Assert.assertEquals(resultaat.getDatumAanvangMaterielePeriodeResultaat(), protocolleringOpdracht.getDatumAanvangMaterielePeriodeResultaat());
        Assert.assertEquals(autorisatiebundel.getDienst().getId(), protocolleringOpdracht.getDienstId());
        Assert.assertEquals(autorisatiebundel.getToegangLeveringsautorisatie().getId(), protocolleringOpdracht.getToegangLeveringsautorisatieId());
        Assert.assertEquals(leverBericht.getBasisBerichtGegevens().getParameters().getSoortSynchronisatie(), protocolleringOpdracht.getSoortSynchronisatie());
        Assert.assertEquals(leverBericht.getBasisBerichtGegevens().getStuurgegevens().getTijdstipVerzending(),
                protocolleringOpdracht.getDatumTijdKlaarzettenLevering());
        Assert.assertEquals(resultaat.getLeveringPersonen(), Lists.newArrayList(protocolleringOpdracht.getGeleverdePersonen()));

    }

    private VerwerkPersoonBericht maakVerwerkBericht(MetaObject metaObject, Autorisatiebundel autorisatiebundel) {
        final List<BijgehoudenPersoon> bijgehoudenPersonenLijst = new ArrayList<>();
        for (final MetaObject persoon : singletonList(metaObject)) {
            final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);
            final BijgehoudenPersoon bijgehoudenPersoon = new BijgehoudenPersoon.Builder(persoonslijst, new BerichtElement("test"))
                    .build();
            bijgehoudenPersonenLijst.add(bijgehoudenPersoon);
        }
        //@formatter:off
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
            .metParameters()
            .metSoortSynchronisatie(SoortSynchronisatie.MUTATIE_BERICHT)
            .metDienst(autorisatiebundel.getDienst())
            .eindeParameters()
            .metStuurgegevens()
            .metReferentienummer("ref")
            .metOntvangendePartij(TestPartijBuilder.maakBuilder().metCode("000456").build())
            .metZendendePartij(brpPartij)
            .metTijdstipVerzending(DatumUtil.nuAlsZonedDateTime())
            .eindeStuurgegevens()
        .build();
         //@formatter:on
        return new VerwerkPersoonBericht(basisBerichtGegevens, autorisatiebundel, bijgehoudenPersonenLijst);
    }

    private Autorisatiebundel maakAutorisatiebundel(final SoortDienst soortDienst) {
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(soortDienst);
        leveringsautorisatie.setProtocolleringsniveau(Protocolleringsniveau.GEEN_BEPERKINGEN);
        final Partij partij = TestPartijBuilder.maakBuilder().metCode("000001").build();
        partij.setId((short) 1);
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        final Dienst dienst = AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst);
        return new Autorisatiebundel(tla, dienst);
    }
}
