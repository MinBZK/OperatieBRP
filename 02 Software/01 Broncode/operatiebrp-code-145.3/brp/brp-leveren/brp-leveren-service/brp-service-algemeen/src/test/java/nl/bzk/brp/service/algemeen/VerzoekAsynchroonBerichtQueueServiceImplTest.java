/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen;

import static org.mockito.Mockito.times;

import java.time.ZonedDateTime;
import java.util.Collections;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VerzoekAsynchroonBerichtQueueServiceImplTest {

    @InjectMocks
    private VerzoekAsynchroonBerichtQueueServiceImpl service;

    @Mock
    private PlaatsAfnemerBerichtService plaatsAfnemerBerichtService;

    @Mock
    private SynchronisatieBerichtGegevensFactory afnemerBerichtFactory;

    @Test
    public void testVoerStapUit() throws Exception {
        final SoortDienst soortDienst = SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING;
        final Partij partij = TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build();
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);

        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        toegangLeveringsAutorisatie.setId(10);

        final Dienst dienst = AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst);
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(toegangLeveringsAutorisatie, dienst);

        final ZonedDateTime nu = DatumUtil.nuAlsZonedDateTime();
        //@formatter:off
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
            .metTijdstipRegistratie(nu)
            .metSoortNaam("soortNaam")
            .metCategorieNaam("categorieNaam")
            .metAdministratieveHandelingId(1L)
            .metStuurgegevens()
                .metCrossReferentienummer("123")
                .metOntvangendePartij(TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build())
                .metReferentienummer("456")
                .metTijdstipVerzending(nu)
                .metZendendePartij(TestPartijBuilder.maakBuilder().metId(2).metCode("000002").build())
                .metZendendeSysteem("TEST2")
            .eindeStuurgegevens()
            .metParameters()
                .metSoortSynchronisatie(SoortSynchronisatie.MUTATIE_BERICHT)
            .metDienst(dienst)
            .eindeParameters().build();
        //@formatter:on
        final VerwerkPersoonBericht bericht = new VerwerkPersoonBericht(basisBerichtGegevens, null, Collections.emptyList());
        final int datumAanvangMaterielePeriode = DatumUtil.vandaag();
        service.plaatsQueueberichtVoorVerzoek(bericht, autorisatiebundel, datumAanvangMaterielePeriode);

        Mockito.verify(afnemerBerichtFactory, times(1)).maak(Mockito.any(), Mockito.any(), Mockito.anyInt());
        Mockito.verify(plaatsAfnemerBerichtService, times(1)).plaatsAfnemerberichten(Mockito.anyList());
    }
}
