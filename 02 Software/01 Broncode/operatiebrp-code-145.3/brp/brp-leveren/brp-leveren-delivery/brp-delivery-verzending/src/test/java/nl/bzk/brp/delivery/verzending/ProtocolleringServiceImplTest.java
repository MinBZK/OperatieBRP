/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.verzending;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Protocolleringsniveau;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.domain.internbericht.verzendingmodel.SynchronisatieBerichtGegevens;
import nl.bzk.brp.protocollering.domain.algemeen.LeveringPersoon;
import nl.bzk.brp.protocollering.domain.algemeen.ProtocolleringOpdracht;
import nl.bzk.brp.protocollering.service.algemeen.ProtocolleringService;
import org.hamcrest.Matchers;
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
public class ProtocolleringServiceImplTest {

    @InjectMocks
    private ProtocolleringServiceVerzendingImpl protocolleerStapImpl;
    @Mock
    private ProtocolleringService protocolleringService;

    private final ZonedDateTime tsReg = DatumUtil.nuAlsZonedDateTime();
    private final List<Long> persoonIDs = Arrays.asList(1L, 2L, 3L);
    private final Set<Long> persoonIDSet = new HashSet<>(persoonIDs);
    private long administratieveHandelingId = 1324;
    private int dienstId = 321;
    private SynchronisatieBerichtGegevens.Builder synchronisatieBerichtGegevensBuilder;

    @Before
    public void voorTest() {
        final ProtocolleringOpdracht protocolleringOpdracht = new ProtocolleringOpdracht();
        protocolleringOpdracht.setAdministratieveHandelingId(administratieveHandelingId);
        protocolleringOpdracht.setToegangLeveringsautorisatieId(1);
        protocolleringOpdracht.setDatumTijdEindeFormelePeriodeResultaat(tsReg);
        protocolleringOpdracht.setDienstId(dienstId);
        protocolleringOpdracht.setDatumTijdAanvangFormelePeriodeResultaat(tsReg);
        protocolleringOpdracht.setDatumTijdKlaarzettenLevering(tsReg);
        protocolleringOpdracht.setSoortSynchronisatie(SoortSynchronisatie.VOLLEDIG_BERICHT);
        protocolleringOpdracht.setGeleverdePersonen(Arrays.asList(new LeveringPersoon(1L, DatumUtil.nuAlsZonedDateTime()),
                new LeveringPersoon(2L, DatumUtil.nuAlsZonedDateTime()), new LeveringPersoon(3L, DatumUtil.nuAlsZonedDateTime())));

        final ArchiveringOpdracht archiveringOpdracht = new ArchiveringOpdracht(Richting.INGAAND, ZonedDateTime.now());
        archiveringOpdracht.setLeveringsAutorisatieId(1);
        archiveringOpdracht.setRolId(Rol.AFNEMER.getId());
        synchronisatieBerichtGegevensBuilder = SynchronisatieBerichtGegevens.builder()
                .metArchiveringOpdracht(archiveringOpdracht)
                .metSoortDienst(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING)
                .metProtocolleringsniveau(Protocolleringsniveau.GEEN_BEPERKINGEN)
                .metProtocolleringOpdracht(protocolleringOpdracht);
    }

    @Test
    public final void testProcess() throws Exception {

        protocolleerStapImpl.verwerkProtocollering(synchronisatieBerichtGegevensBuilder.build());

        final ArgumentCaptor<ProtocolleringOpdracht> argument = ArgumentCaptor.forClass(ProtocolleringOpdracht.class);
        Mockito.verify(protocolleringService).protocolleer(argument.capture());

        final ProtocolleringOpdracht protocolleringOpdracht = argument.getValue();
        Assert.assertThat(protocolleringOpdracht.getDatumTijdKlaarzettenLevering(), Matchers.not(Matchers.nullValue()));
        Assert.assertThat(protocolleringOpdracht.getDatumTijdAanvangFormelePeriodeResultaat(), Matchers.equalTo(tsReg));
        Assert.assertThat(protocolleringOpdracht.getSoortSynchronisatie(),
                Matchers.equalTo(SoortSynchronisatie.VOLLEDIG_BERICHT));
        Assert.assertThat(protocolleringOpdracht.getAdministratieveHandelingId(), Matchers.equalTo(administratieveHandelingId));

        final List<LeveringPersoon> personen = argument.getValue().getGeleverdePersonen();
        Assert.assertThat(personen.size(), Matchers.equalTo(persoonIDSet.size()));
    }

    @Test
    public final void testProcessGeenProtocolleringsNiveau() throws Exception {
        synchronisatieBerichtGegevensBuilder.metProtocolleringsniveau(null);
        protocolleerStapImpl.verwerkProtocollering(synchronisatieBerichtGegevensBuilder.build());

        final ArgumentCaptor<ProtocolleringOpdracht> argument = ArgumentCaptor.forClass(ProtocolleringOpdracht.class);
        Mockito.verify(protocolleringService).protocolleer(argument.capture());

        final ProtocolleringOpdracht protocolleringOpdracht = argument.getValue();
        Assert.assertThat(protocolleringOpdracht.getDatumTijdKlaarzettenLevering(), Matchers.not(Matchers.nullValue()));
        Assert.assertThat(protocolleringOpdracht.getDatumTijdAanvangFormelePeriodeResultaat(), Matchers.equalTo(tsReg));
        Assert.assertThat(protocolleringOpdracht.getSoortSynchronisatie(),
                Matchers.equalTo(SoortSynchronisatie.VOLLEDIG_BERICHT));
        Assert.assertThat(protocolleringOpdracht.getAdministratieveHandelingId(), Matchers.equalTo(administratieveHandelingId));

        final List<LeveringPersoon> personen = argument.getValue().getGeleverdePersonen();
        Assert.assertThat(personen.size(), Matchers.equalTo(persoonIDSet.size()));
    }


    @Test
    public final void testProcessGeenProtocolleringIvmGeheimNiveau() throws Exception {
        synchronisatieBerichtGegevensBuilder.metProtocolleringsniveau(Protocolleringsniveau.GEHEIM);
        protocolleerStapImpl.verwerkProtocollering(synchronisatieBerichtGegevensBuilder.build());
        Mockito.verifyZeroInteractions(protocolleringService);
    }

    @Test
    public final void testProcessGeenProtocolleringIvmNiveauNull() throws Exception {
        synchronisatieBerichtGegevensBuilder.metProtocolleringsniveau(Protocolleringsniveau.GEHEIM);
        protocolleerStapImpl.verwerkProtocollering(synchronisatieBerichtGegevensBuilder.build());
        Mockito.verifyZeroInteractions(protocolleringService);
    }

    @Test
    public final void testProcessGeenProtocolleringIvmBijhouder() throws Exception {
        final ArchiveringOpdracht archiveringOpdracht = new ArchiveringOpdracht(Richting.INGAAND, ZonedDateTime.now());
        archiveringOpdracht.setLeveringsAutorisatieId(1);
        archiveringOpdracht.setRolId(Rol.BIJHOUDINGSORGAAN_COLLEGE.getId());
        synchronisatieBerichtGegevensBuilder.metArchiveringOpdracht(archiveringOpdracht);
        synchronisatieBerichtGegevensBuilder.metProtocolleringsniveau(Protocolleringsniveau.GEHEIM);
        protocolleerStapImpl.verwerkProtocollering(synchronisatieBerichtGegevensBuilder.build());
        Mockito.verifyZeroInteractions(protocolleringService);
    }


    @Test(expected = RuntimeException.class)
    public final void testProtocolleerLeveringMetOnbekendeFout() throws Exception {
        Mockito.doThrow(RuntimeException.class).when(protocolleringService)
                .protocolleer(Mockito.any(ProtocolleringOpdracht.class));
        protocolleerStapImpl.verwerkProtocollering(synchronisatieBerichtGegevensBuilder.build());
    }
}
