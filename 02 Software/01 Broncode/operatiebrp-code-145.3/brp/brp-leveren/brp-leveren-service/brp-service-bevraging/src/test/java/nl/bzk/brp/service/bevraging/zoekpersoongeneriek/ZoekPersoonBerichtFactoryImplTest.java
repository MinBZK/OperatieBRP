/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.zoekpersoongeneriek;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;
import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;
import static nl.bzk.brp.domain.element.ElementHelper.getObjectElement;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.BerichtElement;
import nl.bzk.brp.domain.berichtmodel.BerichtVerwerkingsResultaat;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.MeldingBepalerService;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.bevraging.algemeen.BevragingVerzoek;
import nl.bzk.brp.service.bevraging.zoekpersoon.ZoekPersoonVerzoek;
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

/**
 * Unit test voor {@link ZoekPersoonBerichtFactoryImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
public class ZoekPersoonBerichtFactoryImplTest {

    @InjectMocks
    private ZoekPersoonBerichtFactoryImpl zoekPersoonBerichtFactory;
    @Mock
    private VerwerkPersoonBerichtFactory stappenlijstUitvoerService;
    @Mock
    private PartijService partijService;
    @Mock
    private MeldingBepalerService meldingBepalerService;

    @Captor
    private ArgumentCaptor<MaakBerichtParameters> maakBerichtParametersArgumentCaptor;

    @Before
    public void before() {
        Mockito.when(meldingBepalerService.geefWaarschuwingen(Mockito.anyList())).thenReturn(Collections.EMPTY_LIST);
        BrpNu.set();
    }

    @Test
    public void maakZoekPersoonBericht() throws Exception {
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        final Partij partij = new Partij("testPartij", "000001");
        final ToegangLeveringsAutorisatie toegangLeveringsautorisatie = new ToegangLeveringsAutorisatie(new PartijRol(partij, Rol.AFNEMER),
                leveringsautorisatie);
        final Dienst dienst = new Dienst(new Dienstbundel(leveringsautorisatie), SoortDienst.ZOEK_PERSOON);
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(toegangLeveringsautorisatie, dienst);
        final BevragingVerzoek bevragingVerzoek = new ZoekPersoonVerzoek();
        bevragingVerzoek.getStuurgegevens().setReferentieNummer(UUID.randomUUID().toString());
        final List<Persoonslijst> persoonslijstList = new ArrayList<>();
        //@formatter:off
        final Persoonslijst persoonslijst = new Persoonslijst(MetaObject.maakBuilder()
            .metId(1)
            .metObjectElement(getObjectElement(Element.PERSOON))
            .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_GEBOORTE))
                .metRecord()
                    .metActieInhoud(TestVerantwoording.maakActie(23))
                    .metAttribuut(getAttribuutElement(Element.PERSOON_GEBOORTE_DATUM), 20000101)
                .eindeRecord()
            .eindeGroep()
            .build(),
            0L);
        //@formatter:on
        persoonslijstList.add(persoonslijst);
        final BijgehoudenPersoon bijgehoudenPersoon = new BijgehoudenPersoon.Builder(persoonslijst, new BerichtElement("test")).build();

        when(stappenlijstUitvoerService.maakBerichten(maakBerichtParametersArgumentCaptor.capture()))
                .thenAnswer(invocationOnMock -> {
                    final MaakBerichtParameters parameters = (MaakBerichtParameters) invocationOnMock.getArguments()[0];
                    final Map<Autorisatiebundel, List<BijgehoudenPersoon>> bijgehoudenPersoonList = new HashMap<>();
                    bijgehoudenPersoonList.put(autorisatiebundel, Lists.newArrayList(bijgehoudenPersoon));

                    return parameters.getBijgehoudenPersoonBerichtDecorator().build(bijgehoudenPersoonList);
                });

        final Partij brpPartij = TestPartijBuilder.maakBuilder().metCode(Partij.PARTIJ_CODE_BRP).build();
        when(partijService.geefBrpPartij()).thenReturn(brpPartij);

        final VerwerkPersoonBericht bericht = zoekPersoonBerichtFactory
                .maakZoekPersoonBericht(persoonslijstList, autorisatiebundel, bevragingVerzoek, null);

        assertThat(bericht.getBasisBerichtGegevens().getAdministratieveHandelingId(), is(nullValue()));
        assertThat(bericht.getBasisBerichtGegevens().getStuurgegevens().getTijdstipVerzending(), is(notNullValue()));
        assertThat(bericht.getBasisBerichtGegevens().getStuurgegevens().getZendendeSysteem(), is(BasisBerichtGegevens.BRP_SYSTEEM));
        assertThat(bericht.getBasisBerichtGegevens().getStuurgegevens().getZendendePartij(), is(brpPartij));
        assertThat(bericht.getBasisBerichtGegevens().getStuurgegevens().getCrossReferentienummer(),
                is(bevragingVerzoek.getStuurgegevens().getReferentieNummer()));
        assertThat(bericht.getBasisBerichtGegevens().getResultaat(),
                is(BerichtVerwerkingsResultaat.builder().metHoogsteMeldingsniveau(SoortMelding.GEEN.getNaam()).metVerwerking(
                        VerwerkingsResultaat.GESLAAGD).build()));
        assertThat(bericht.getBijgehoudenPersonen().size(), is(1));
    }

}
