/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.synchronisatie.persoon;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import com.google.common.collect.Lists;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BerichtElement;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.MeldingBepalerService;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.maakbericht.VerwerkPersoonBerichtFactory;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

/**
 * Unit test voor {@link SynchronisatieBerichtFactoryImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
public class SynchronisatieBerichtFactoryImplTest {

    @InjectMocks
    private SynchronisatieBerichtFactoryImpl service;

    @Mock
    private PartijService partijService;
    @Mock
    private VerwerkPersoonBerichtFactory stappenlijstUitvoerService;
    @Mock
    private MeldingBepalerService meldingBepalerService;

    private ZonedDateTime tijdstipRegistratie;

    @Before
    public void voorTest() {
        tijdstipRegistratie = DatumUtil.nuAlsZonedDateTime();
        BrpNu.set(tijdstipRegistratie);
        when(meldingBepalerService.geefWaarschuwingen(Mockito.any(BijgehoudenPersoon.class))).thenReturn(Collections.EMPTY_LIST);
    }

    @Test
    public void maakVolledigBericht() throws Exception {
        final PartijRol partijRol = new PartijRol(TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build(), Rol.AFNEMER);

        final SoortDienst soortDienst = SoortDienst.SYNCHRONISATIE_PERSOON;
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);

        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(tla, AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst));

        final MetaObject build = TestBuilders.maakLeegPersoon(1)
                .metObject()
                .metObjectElement(Element.PERSOON_ADRES.getId())
                .metGroep()
                .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                .metRecord()
                .metId(1)
                .metActieInhoud(TestVerantwoording.maakActie(1, tijdstipRegistratie))
                .metDatumAanvangGeldigheid(18 * 1000 * 10 * 10)
                .metAttribuut(Element.PERSOON_ADRES_HUISNUMMER.getId(), 1)
                .eindeRecord()
                .eindeGroep()
                .eindeObject()
                .build();
        //@formatter:on

        final Persoonslijst persoonslijst = new Persoonslijst(build, 0L);

        final BijgehoudenPersoon bijgehoudenPersoon = new BijgehoudenPersoon.Builder(persoonslijst, new BerichtElement("test")).build();

        Mockito.when(stappenlijstUitvoerService.maakBerichten(any())).thenAnswer(new Answer<List<VerwerkPersoonBericht>>() {
            @Override
            public List<VerwerkPersoonBericht> answer(final InvocationOnMock invocationOnMock) throws Throwable {
                final MaakBerichtParameters parameters = (MaakBerichtParameters) invocationOnMock.getArguments()[0];
                final Map<Autorisatiebundel, List<BijgehoudenPersoon>> map = new HashMap<>();
                map.put(autorisatiebundel, Lists.newArrayList(bijgehoudenPersoon));
                return parameters.getBijgehoudenPersoonBerichtDecorator().build(map);
            }
        });

        final VerwerkPersoonBericht bericht = service.apply(persoonslijst, autorisatiebundel);

        assertThat(bericht.getBasisBerichtGegevens().getCategorieNaam(), is("Levering"));
        assertThat(bericht.getBasisBerichtGegevens().getSoortNaam(), is("Synchronisatie persoon"));
        assertThat(bericht.getBasisBerichtGegevens().getAdministratieveHandelingId(), is(nullValue()));
        assertThat(bericht.getBasisBerichtGegevens().getPartijCode(), is("000001"));
        assertNotNull(bericht.getBasisBerichtGegevens().getTijdstipRegistratie());
        assertThat(bericht.getBasisBerichtGegevens().getParameters().getSoortSynchronisatie(), is(SoortSynchronisatie.VOLLEDIG_BERICHT));

        Mockito.verify(stappenlijstUitvoerService).maakBerichten(any());
    }


}
