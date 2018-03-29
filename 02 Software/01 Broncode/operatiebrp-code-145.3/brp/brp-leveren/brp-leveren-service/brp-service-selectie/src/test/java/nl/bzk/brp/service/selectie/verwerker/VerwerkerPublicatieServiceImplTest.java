/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.verwerker;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Protocolleringsniveau;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.internbericht.selectie.SelectieAutorisatieBericht;
import nl.bzk.brp.domain.internbericht.selectie.SelectieFragmentSchrijfBericht;
import nl.bzk.brp.domain.internbericht.selectie.SelectieVerwerkTaakBericht;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.helper.TestDatumUtil;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * VerwerkerPublicatieServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class VerwerkerPublicatieServiceImplTest {

    @Mock
    private SelectieSchrijfTaakPublicatieService selectieSchrijfTaakPublicatieService;

    @Mock
    private LeveringsautorisatieService leveringsautorisatieService;

    @InjectMocks
    private VerwerkerPublicatieServiceImpl verwerkerPublicatieService;

    @Captor
    private ArgumentCaptor<List<SelectieFragmentSchrijfBericht>> schrijfTakenCaptor;


    @Test
    public void testHappyFlow() {
        final Dienst dienst = AutAutUtil.zoekDienst(TestAutorisaties.metSoortDienst(SoortDienst.SELECTIE), SoortDienst.SELECTIE);
        dienst.setSoortSelectie(SoortSelectie.STANDAARD_SELECTIE.getId());
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(TestAutorisaties.maak(Rol.AFNEMER, dienst), dienst);
        autorisatiebundel.getLeveringsautorisatie().setProtocolleringsniveau(Protocolleringsniveau.GEEN_BEPERKINGEN);

        SelectieAutorisatieBericht selectieAutorisatiebundel = new SelectieAutorisatieBericht();
        selectieAutorisatiebundel.setSelectietaakId(1);
        selectieAutorisatiebundel.setDienstId(autorisatiebundel.getDienst().getId());
        selectieAutorisatiebundel.setToegangLeveringsAutorisatieId(autorisatiebundel.getToegangLeveringsautorisatie().getId());

        final VerwerkPersoonResultaat verwerkPersoonResultaat = maakVerwerkPersoonResultaat(autorisatiebundel, selectieAutorisatiebundel);

        final SelectieVerwerkTaakBericht selectieTaak = new SelectieVerwerkTaakBericht();
        selectieTaak.setSelectieAutorisaties(Lists.newArrayList(selectieAutorisatiebundel));

        final Collection<VerwerkPersoonResultaat> resultaten = Lists.newArrayList(verwerkPersoonResultaat);

        Mockito.when(leveringsautorisatieService.geefToegangLeveringsAutorisatie(Mockito.anyInt()))
                .thenReturn(autorisatiebundel.getToegangLeveringsautorisatie());

        verwerkerPublicatieService.publiceerSchrijfTaken(selectieTaak, resultaten);

        Mockito.verify(selectieSchrijfTaakPublicatieService).publiceerSchrijfTaken(schrijfTakenCaptor.capture());

        final List<SelectieFragmentSchrijfBericht> schrijfTaken = schrijfTakenCaptor.getValue();
        Assert.assertTrue(schrijfTaken.size() == 1);
        final SelectieFragmentSchrijfBericht selectieFragmentSchrijfBericht = schrijfTaken.stream().findFirst().orElse(null);
        Assert.assertTrue(selectieFragmentSchrijfBericht.getProtocolleringPersonen().size() == 1);
    }


    @Test
    public void testGeenProtolleringPersonenBijhouder() {
        final Dienst dienst = AutAutUtil.zoekDienst(TestAutorisaties.metSoortDienst(SoortDienst.SELECTIE), SoortDienst.SELECTIE);
        dienst.setSoortSelectie(SoortSelectie.STANDAARD_SELECTIE.getId());
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(TestAutorisaties.maak(Rol.BIJHOUDINGSORGAAN_MINISTER, dienst), dienst);
        autorisatiebundel.getLeveringsautorisatie().setProtocolleringsniveau(Protocolleringsniveau.GEEN_BEPERKINGEN);

        SelectieAutorisatieBericht selectieAutorisatiebundel = new SelectieAutorisatieBericht();
        selectieAutorisatiebundel.setSelectietaakId(1);
        selectieAutorisatiebundel.setDienstId(autorisatiebundel.getDienst().getId());
        selectieAutorisatiebundel.setToegangLeveringsAutorisatieId(autorisatiebundel.getToegangLeveringsautorisatie().getId());

        final VerwerkPersoonResultaat verwerkPersoonResultaat = maakVerwerkPersoonResultaat(autorisatiebundel, selectieAutorisatiebundel);

        final SelectieVerwerkTaakBericht selectieTaak = new SelectieVerwerkTaakBericht();
        selectieTaak.setSelectieAutorisaties(Lists.newArrayList(selectieAutorisatiebundel));

        final Collection<VerwerkPersoonResultaat> resultaten = Lists.newArrayList(verwerkPersoonResultaat);

        Mockito.when(leveringsautorisatieService.geefToegangLeveringsAutorisatie(Mockito.anyInt()))
                .thenReturn(autorisatiebundel.getToegangLeveringsautorisatie());

        verwerkerPublicatieService.publiceerSchrijfTaken(selectieTaak, resultaten);

        Mockito.verify(selectieSchrijfTaakPublicatieService).publiceerSchrijfTaken(schrijfTakenCaptor.capture());

        final List<SelectieFragmentSchrijfBericht> schrijfTaken = schrijfTakenCaptor.getValue();
        Assert.assertTrue(schrijfTaken.size() == 1);
        final SelectieFragmentSchrijfBericht selectieFragmentSchrijfBericht = schrijfTaken.stream().findFirst().orElse(null);
        Assert.assertTrue(selectieFragmentSchrijfBericht.getProtocolleringPersonen().size() == 0);
    }

    @Test
    public void testGeenProtolleringPersonenGeheim() {
        final Dienst dienst = AutAutUtil.zoekDienst(TestAutorisaties.metSoortDienst(SoortDienst.SELECTIE), SoortDienst.SELECTIE);
        dienst.setSoortSelectie(SoortSelectie.STANDAARD_SELECTIE.getId());
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(TestAutorisaties.maak(Rol.AFNEMER, dienst), dienst);
        autorisatiebundel.getLeveringsautorisatie().setProtocolleringsniveau(Protocolleringsniveau.GEHEIM);

        SelectieAutorisatieBericht selectieAutorisatiebundel = new SelectieAutorisatieBericht();
        selectieAutorisatiebundel.setSelectietaakId(1);
        selectieAutorisatiebundel.setDienstId(autorisatiebundel.getDienst().getId());
        selectieAutorisatiebundel.setToegangLeveringsAutorisatieId(autorisatiebundel.getToegangLeveringsautorisatie().getId());

        final VerwerkPersoonResultaat verwerkPersoonResultaat = maakVerwerkPersoonResultaat(autorisatiebundel, selectieAutorisatiebundel);

        final SelectieVerwerkTaakBericht selectieTaak = new SelectieVerwerkTaakBericht();
        selectieTaak.setSelectieAutorisaties(Lists.newArrayList(selectieAutorisatiebundel));

        final Collection<VerwerkPersoonResultaat> resultaten = Lists.newArrayList(verwerkPersoonResultaat);

        Mockito.when(leveringsautorisatieService.geefToegangLeveringsAutorisatie(Mockito.anyInt()))
                .thenReturn(autorisatiebundel.getToegangLeveringsautorisatie());

        verwerkerPublicatieService.publiceerSchrijfTaken(selectieTaak, resultaten);

        Mockito.verify(selectieSchrijfTaakPublicatieService).publiceerSchrijfTaken(schrijfTakenCaptor.capture());

        final List<SelectieFragmentSchrijfBericht> schrijfTaken = schrijfTakenCaptor.getValue();
        Assert.assertTrue(schrijfTaken.size() == 1);
        final SelectieFragmentSchrijfBericht selectieFragmentSchrijfBericht = schrijfTaken.stream().findFirst().orElse(null);
        Assert.assertTrue(selectieFragmentSchrijfBericht.getProtocolleringPersonen().size() == 0);
    }

    private VerwerkPersoonResultaat maakVerwerkPersoonResultaat(Autorisatiebundel autorisatiebundel, SelectieAutorisatieBericht selectieAutorisatiebundel) {
        final VerwerkPersoonResultaat verwerkPersoonResultaat = new VerwerkPersoonResultaat();
        final Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.maakLeegPersoon().metGroep()
                .metGroepElement(Element.PERSOON_AFGELEIDADMINISTRATIEF.getId())
                .metRecord()
                .metId(1)
                .metActieInhoud(TestVerantwoording.maakActie(1, TestDatumUtil.gisteren()))
                .metAttribuut(Element.PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPLAATSTEWIJZIGING.getId(), DatumUtil.nuAlsZonedDateTime())
                .eindeRecord()
                .eindeGroep().eindeObject().build(), 1L);
        verwerkPersoonResultaat.setPersoonslijst(persoonslijst);
        verwerkPersoonResultaat.setAutorisatiebundel(autorisatiebundel);
        verwerkPersoonResultaat.setSelectieTaakId(1);
        verwerkPersoonResultaat.setPersoonFragment("persoon");
        return verwerkPersoonResultaat;
    }
}
