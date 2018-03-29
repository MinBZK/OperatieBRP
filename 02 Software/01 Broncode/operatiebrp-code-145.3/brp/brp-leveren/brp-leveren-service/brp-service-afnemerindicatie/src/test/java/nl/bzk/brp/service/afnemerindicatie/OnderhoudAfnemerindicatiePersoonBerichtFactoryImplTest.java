/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.afnemerindicatie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.when;

import com.google.common.collect.Lists;
import java.time.ZonedDateTime;
import java.util.Collections;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.MeldingBepalerService;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.maakbericht.VerwerkPersoonBerichtFactory;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
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
public class OnderhoudAfnemerindicatiePersoonBerichtFactoryImplTest {

    private static final ZonedDateTime NU = DatumUtil.nuAlsZonedDateTime();

    @InjectMocks
    private OnderhoudAfnemerindicatiePersoonBerichtFactoryImpl onderhoudAfnemerindicatiePersoonBerichtFactory;
    @Mock
    private VerwerkPersoonBerichtFactory stappenlijstUitvoerService;
    @Mock
    private PartijService partijService;
    @Mock
    private MeldingBepalerService meldingBepalerService;

    @Captor
    public ArgumentCaptor<MaakBerichtParameters> argumentCaptor;


    @Before
    public void voorTest() {
        when(meldingBepalerService.geefWaarschuwingen(Mockito.any(BijgehoudenPersoon.class))).thenReturn(Collections.emptyList());
        Mockito.when(stappenlijstUitvoerService.maakBerichten(any())).thenReturn(Lists.newArrayList(
                VerwerkPersoonBericht.LEEG_BERICHT
        ));
        Mockito.when(partijService.geefBrpPartij()).thenReturn(TestPartijBuilder.maakBuilder().metId(1).metCode("199903").build());
    }

    @Test
    public void testMaakBericht() {
        final Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.maakLeegPersoon().build(), 1L);
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(Stelsel.BRP);
        final SoortAdministratieveHandeling soortAdministratieveHandeling = SoortAdministratieveHandeling.CORRECTIE_HUWELIJK;
        final String berichtRef = "berichtReferentie";

        onderhoudAfnemerindicatiePersoonBerichtFactory.maakBericht(persoonslijst, autorisatiebundel, 20140101, NU, soortAdministratieveHandeling, berichtRef);

        Mockito.verify(stappenlijstUitvoerService).maakBerichten(argumentCaptor.capture());
        assertNotNull(argumentCaptor.getValue().getBijgehoudenPersoonBerichtDecorator());
        assertEquals(autorisatiebundel, argumentCaptor.getValue().getAutorisatiebundels().get(0));
        assertEquals(persoonslijst, argumentCaptor.getValue().getBijgehoudenPersonen().get(0));
    }

    @Test
    public void testMaakBericht_GBAStelsel() {
        final Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.maakLeegPersoon().build(), 1L);
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(Stelsel.GBA);
        final SoortAdministratieveHandeling soortAdministratieveHandeling = SoortAdministratieveHandeling.CORRECTIE_HUWELIJK;
        final String berichtRef = "berichtReferentie";

        onderhoudAfnemerindicatiePersoonBerichtFactory.maakBericht(persoonslijst, autorisatiebundel, 20140101, NU, soortAdministratieveHandeling, berichtRef);

        Mockito.verify(stappenlijstUitvoerService).maakBerichten(argumentCaptor.capture());
        assertNotNull(argumentCaptor.getValue().getBijgehoudenPersoonBerichtDecorator());
        assertEquals(autorisatiebundel, argumentCaptor.getValue().getAutorisatiebundels().get(0));
        assertEquals(persoonslijst, argumentCaptor.getValue().getBijgehoudenPersonen().get(0));
    }


    private Autorisatiebundel maakAutorisatiebundel(final Stelsel stelsel) {
        final Partij partij = TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build();
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        partij.addPartijRol(partijRol);
        final SoortDienst soortDienst = SoortDienst.ATTENDERING;
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        leveringsautorisatie.setStelsel(stelsel);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        final Dienst dienst = AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst);
        return new Autorisatiebundel(tla, dienst);
    }

}
