/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.persoonselectie;

import java.util.Collections;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.helper.TestDatumUtil;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.RegelValidatieService;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.VerstrekkingsbeperkingService;
import nl.bzk.brp.service.algemeen.blob.PersoonslijstService;
import nl.bzk.brp.service.algemeen.expressie.ExpressieService;
import nl.bzk.brp.service.dalapi.GeefDetailsPersoonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * SelecteerPersoonServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class SelecteerPersoonServiceImplTest {

    @Mock
    private PersoonslijstService persoonslijstService;
    @Mock
    private GeefDetailsPersoonRepository persoonJpaRepository;
    @Mock
    private RegelValidatieService regelValidatieService;
    @Mock
    private ExpressieService expressieService;
    @Mock
    private VerstrekkingsbeperkingService verstrekkingsbeperkingService;

    @InjectMocks
    private SelecteerPersoonServiceImpl selecteerPersoonService;

    @Test
    public void testSelecteerPersoonMetBsnHappyFlow() throws StapMeldingException {
        final String bsn = "000000001";
        final Long id = 1L;

        final Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L);
        Mockito.when(persoonslijstService.getById(id)).thenReturn(persoonslijst);
        Mockito.when(persoonJpaRepository.zoekIdsPersoonMetBsn(bsn)).thenReturn(Collections.singletonList(id));
        Mockito.when(regelValidatieService.valideerEnGeefMeldingen(Mockito.any())).thenReturn(Collections.emptyList());

        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel();

        selecteerPersoonService.selecteerPersoonMetBsn(bsn, autorisatiebundel);
    }

    @Test
    public void testSelecteerPersoonMetAnummerHappyFlow() throws StapMeldingException {
        final String anr = "0000000001";
        final Long id = 1L;

        final Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L);
        Mockito.when(persoonslijstService.getById(id)).thenReturn(persoonslijst);
        Mockito.when(persoonJpaRepository.zoekIdsPersoonMetAnummer(anr)).thenReturn(Collections.singletonList(id));
        Mockito.when(regelValidatieService.valideerEnGeefMeldingen(Mockito.any())).thenReturn(Collections.emptyList());
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel();
        selecteerPersoonService.selecteerPersoonMetANummer(anr, autorisatiebundel);
    }

    @Test(expected = StapMeldingException.class)
    public void testSelecteerPersoonMetNietBestaandeObjectSleutel() throws StapMeldingException {
        final Long id = 1L;

        final Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L);
        Mockito.when(persoonslijstService.getById(id)).thenReturn(null);
        Mockito.when(regelValidatieService.valideerEnGeefMeldingen(Mockito.any())).thenReturn(Collections.emptyList());
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel();
        selecteerPersoonService.selecteerPersoonMetId(id, autorisatiebundel);
    }

    @Test
    public void testSelecteerPersoonMetIdHappyFlow() throws StapMeldingException {
        final Long id = 1L;

        final Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L);
        Mockito.when(persoonslijstService.getById(id)).thenReturn(persoonslijst);
        Mockito.when(regelValidatieService.valideerEnGeefMeldingen(Mockito.any())).thenReturn(Collections.emptyList());
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel();
        selecteerPersoonService.selecteerPersoonMetId(id, autorisatiebundel);
    }

    @Test(expected = StapMeldingException.class)
    public void testSelecteerPseudoPersoon() throws StapMeldingException {
        final Long id = 1L;
        final Actie actieInhoud = TestVerantwoording.maakActie(1, TestDatumUtil.gisteren());
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON.getId())
            .metGroep()
                .metGroepElement(Element.PERSOON_BIJHOUDING.getId())
                .metRecord()
                .metActieInhoud(actieInhoud)
                .metAttribuut(Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE.getId(), NadereBijhoudingsaard.ACTUEEL.getCode())
                .eindeRecord()
            .eindeGroep()
            .metGroep()
                .metGroepElement(Element.PERSOON_IDENTITEIT.getId())
                .metRecord()
                .metAttribuut(Element.PERSOON_SOORTCODE.getId(), SoortPersoon.PSEUDO_PERSOON.getCode())
                .eindeRecord()
            .eindeGroep().build();
        //@formatter:on

        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);
        Mockito.when(persoonslijstService.getById(id)).thenReturn(persoonslijst);
        Mockito.when(regelValidatieService.valideerEnGeefMeldingen(Mockito.any())).thenReturn(Collections.emptyList());
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel();
        selecteerPersoonService.selecteerPersoonMetId(id, autorisatiebundel);
    }

    @Test(expected = StapMeldingException.class)
    public void testSelecteerPersoonMetBijhoudingsaardOnbekend() throws StapMeldingException {
        final Long id = 1L;
        final Actie actieInhoud = TestVerantwoording.maakActie(1, TestDatumUtil.gisteren());
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON.getId())
            .metGroep()
                .metGroepElement(Element.PERSOON_BIJHOUDING.getId())
                .metRecord()
                .metActieInhoud(actieInhoud)
                .metAttribuut(Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE.getId(), NadereBijhoudingsaard.ONBEKEND.getCode())
                .eindeRecord()
            .eindeGroep()
            .metGroep()
                .metGroepElement(Element.PERSOON_IDENTITEIT.getId())
                .metRecord()
                .metAttribuut(Element.PERSOON_SOORTCODE.getId(), SoortPersoon.INGESCHREVENE.getCode())
                .eindeRecord()
            .eindeGroep().build();
        //@formatter:on

        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);
        Mockito.when(persoonslijstService.getById(id)).thenReturn(persoonslijst);
        Mockito.when(regelValidatieService.valideerEnGeefMeldingen(Mockito.any())).thenReturn(Collections.emptyList());
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel();
        selecteerPersoonService.selecteerPersoonMetId(id, autorisatiebundel);
    }

    @Test(expected = StapMeldingException.class)
    public void testSelecteerPersoonMetBijhoudingsaardFout() throws StapMeldingException {
        final Long id = 1L;
        final Actie actieInhoud = TestVerantwoording.maakActie(1, TestDatumUtil.gisteren());
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON.getId())
            .metGroep()
                .metGroepElement(Element.PERSOON_BIJHOUDING.getId())
                .metRecord()
                .metActieInhoud(actieInhoud)
                .metAttribuut(Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE.getId(), NadereBijhoudingsaard.FOUT.getCode())
                .eindeRecord()
            .eindeGroep()
            .metGroep()
                .metGroepElement(Element.PERSOON_IDENTITEIT.getId())
                .metRecord()
                .metAttribuut(Element.PERSOON_SOORTCODE.getId(), SoortPersoon.INGESCHREVENE.getCode())
                .eindeRecord()
            .eindeGroep().build();
        //@formatter:on

        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);
        Mockito.when(persoonslijstService.getById(id)).thenReturn(persoonslijst);
        Mockito.when(regelValidatieService.valideerEnGeefMeldingen(Mockito.any())).thenReturn(Collections.emptyList());
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel();
        selecteerPersoonService.selecteerPersoonMetId(id, autorisatiebundel);
    }

    @Test(expected = StapMeldingException.class)
    public void testSelecteerPersoonMetBijhoudingsaardGewist() throws StapMeldingException {
        final Long id = 1L;
        final Actie actieInhoud = TestVerantwoording.maakActie(1, TestDatumUtil.gisteren());
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON.getId())
            .metGroep()
                .metGroepElement(Element.PERSOON_BIJHOUDING.getId())
                .metRecord()
                .metActieInhoud(actieInhoud)
                .metAttribuut(Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE.getId(), NadereBijhoudingsaard.GEWIST.getCode())
                .eindeRecord()
            .eindeGroep()
            .metGroep()
                .metGroepElement(Element.PERSOON_IDENTITEIT.getId())
                .metRecord()
                .metAttribuut(Element.PERSOON_SOORTCODE.getId(), SoortPersoon.INGESCHREVENE.getCode())
                .eindeRecord()
            .eindeGroep().build();
        //@formatter:on

        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);
        Mockito.when(persoonslijstService.getById(id)).thenReturn(persoonslijst);
        Mockito.when(regelValidatieService.valideerEnGeefMeldingen(Mockito.any())).thenReturn(Collections.emptyList());
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel();
        selecteerPersoonService.selecteerPersoonMetId(id, autorisatiebundel);
    }

    private Autorisatiebundel maakAutorisatiebundel() {
        final SoortDienst soortDienst = SoortDienst.GEEF_DETAILS_PERSOON;
        final Partij partij = TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build();
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);

        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);

        final Dienst dienst = AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst);
        return new Autorisatiebundel(tla, dienst);
    }
}
