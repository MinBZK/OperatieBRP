/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.detailspersoon;

import static org.mockito.Matchers.any;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.services.objectsleutel.OngeldigeObjectSleutelException;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.DatumFormatterUtil;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.bevraging.algemeen.BevragingSelecteerPersoonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * OphalenGeefDetailsPersoonServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class OphalenGeefDetailsPersoonServiceImplTest {

    private static final int LEV_AUT_ID = 1;
    private static final SoortDienst SOORT_DIENST = SoortDienst.ATTENDERING;
    private static final int ZENDENDE_PARTIJ_ID = 1;
    private static final String ZENDENDE_PARTIJ_CODE = "000001";
    private static final String VALIDE_BSN = "426696955";
    private static final String VALIDE_ADMINISTRATIENUMMER = "9376096018";
    private static final String OBJECTSLEUTEL = "123";
    private static final ZonedDateTime ZDT_2014_01_01 = ZonedDateTime.of(2014, 1, 1, 12, 0, 0, 0, ZoneId.systemDefault());
    private static final ZonedDateTime ZDT_2015_01_01 = ZonedDateTime.of(2015, 1, 1, 12, 0, 0, 0, ZoneId.systemDefault());
    private static final ZonedDateTime ZDT_2016_01_01 = ZonedDateTime.of(2016, 1, 1, 12, 0, 0, 0, ZoneId.systemDefault());

    @InjectMocks
    private OphalenGeefDetailsPersoonServiceImpl ophalenPersoonService;
    @Mock
    private BevragingSelecteerPersoonService selecteerPersoonService;

    private GeefDetailsPersoonVerzoek bevragingVerzoek;
    private Autorisatiebundel autorisatiebundel;

    @Before
    public void voorTest() {
        bevragingVerzoek = new GeefDetailsPersoonVerzoek();
        bevragingVerzoek.setSoortDienst(SOORT_DIENST);
        bevragingVerzoek.getParameters().setLeveringsAutorisatieId(Integer.toString(LEV_AUT_ID));
        bevragingVerzoek.getStuurgegevens().setZendendePartijCode(Integer.toString(ZENDENDE_PARTIJ_ID));
        bevragingVerzoek.getParameters().setPeilMomentFormeelResultaat(
                DatumFormatterUtil.vanZonedDateTimeNaarXsdDateTime(ZDT_2015_01_01));

        final Partij partij = TestPartijBuilder.maakBuilder().metId(ZENDENDE_PARTIJ_ID).metCode(ZENDENDE_PARTIJ_CODE).build();
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);

        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, SOORT_DIENST);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);

        autorisatiebundel = new Autorisatiebundel(tla, AutAutUtil.zoekDienst(leveringsautorisatie, SOORT_DIENST));
    }

    @Test
    public void testHappyFlowZoekOpBsn() throws StapException {
        bevragingVerzoek.getIdentificatiecriteria().setBsn(VALIDE_BSN);
        final Persoonslijst persoonslijst = maakPersoon(ZDT_2015_01_01.minusDays(1));
        Mockito.when(selecteerPersoonService.selecteerPersoon(any(), any(), any(), any(), any())).thenReturn(persoonslijst);

        ophalenPersoonService.voerStapUit(bevragingVerzoek, autorisatiebundel);

        Mockito.verify(selecteerPersoonService).selecteerPersoon(VALIDE_BSN, null, null, String.valueOf(ZENDENDE_PARTIJ_ID),
                autorisatiebundel);
    }

    @Test
    public void testHappyFlowZoekOpBsnMetValideFormeelPeilMoment() throws StapException {
        bevragingVerzoek.getIdentificatiecriteria().setBsn(VALIDE_BSN);
        final Persoonslijst persoonslijst = maakPersoon(ZDT_2015_01_01.minusDays(1));
        Mockito.when(
                selecteerPersoonService.selecteerPersoon(VALIDE_BSN, null, null, String.valueOf(ZENDENDE_PARTIJ_ID), autorisatiebundel))
                .thenReturn(persoonslijst);

        ophalenPersoonService.voerStapUit(bevragingVerzoek, autorisatiebundel);
    }

    @Test
    public void testHappyFlowZoekOpBsnZonderFormeelPeilMoment() throws StapException {
        bevragingVerzoek.getParameters().setPeilMomentFormeelResultaat(null);
        bevragingVerzoek.getIdentificatiecriteria().setBsn(VALIDE_BSN);
        final Persoonslijst persoonslijst = maakPersoon(ZDT_2015_01_01.minusDays(1));
        Mockito.when(
                selecteerPersoonService.selecteerPersoon(VALIDE_BSN, null, null, String.valueOf(ZENDENDE_PARTIJ_ID), autorisatiebundel))
                .thenReturn(persoonslijst);

        ophalenPersoonService.voerStapUit(bevragingVerzoek, autorisatiebundel);
    }

    @Test
    public void testHappyFlowZoekOpBsnZonderTsGBASystematiek() throws StapException {
        bevragingVerzoek.getParameters().setPeilMomentFormeelResultaat(null);
        bevragingVerzoek.getIdentificatiecriteria().setBsn(VALIDE_BSN);
        final Persoonslijst persoonslijst = maakPersoon(null);
        Mockito.when(
                selecteerPersoonService.selecteerPersoon(VALIDE_BSN, null, null, String.valueOf(ZENDENDE_PARTIJ_ID), autorisatiebundel))
                .thenReturn(persoonslijst);

        ophalenPersoonService.voerStapUit(bevragingVerzoek, autorisatiebundel);
    }

    @Test
    public void testHappyFlowZoekOpANummer() throws StapException {
        bevragingVerzoek.getIdentificatiecriteria().setAnr(VALIDE_ADMINISTRATIENUMMER);
        final Persoonslijst persoonslijst = maakPersoon(ZDT_2015_01_01.minusDays(1));
        Mockito.when(selecteerPersoonService.selecteerPersoon(null, VALIDE_ADMINISTRATIENUMMER, null, String.valueOf(ZENDENDE_PARTIJ_ID),
                autorisatiebundel)).thenReturn(persoonslijst);

        ophalenPersoonService.voerStapUit(bevragingVerzoek, autorisatiebundel);

        Mockito.verify(selecteerPersoonService).selecteerPersoon(null, VALIDE_ADMINISTRATIENUMMER, null, String.valueOf(ZENDENDE_PARTIJ_ID),
                autorisatiebundel);
    }

    @Test
    public void testHappyFlowZoekOpObjectSleutel() throws OngeldigeObjectSleutelException, StapException {
        bevragingVerzoek.getIdentificatiecriteria().setObjectSleutel(OBJECTSLEUTEL);
        final Persoonslijst persoonslijst = maakPersoon(ZDT_2015_01_01.minusDays(1));
        Mockito.when(selecteerPersoonService.selecteerPersoon(null, null, OBJECTSLEUTEL, String.valueOf(ZENDENDE_PARTIJ_ID), autorisatiebundel))
                .thenReturn(persoonslijst);

        ophalenPersoonService.voerStapUit(bevragingVerzoek, autorisatiebundel);

        Mockito.verify(selecteerPersoonService)
                .selecteerPersoon(null, null, OBJECTSLEUTEL, String.valueOf(ZENDENDE_PARTIJ_ID), autorisatiebundel);
    }

    @Test(expected = StapMeldingException.class)
    public void testOngeldigFormeelPeilmomentTovGbaSystematiek() throws OngeldigeObjectSleutelException, StapException {
        bevragingVerzoek.getIdentificatiecriteria().setBsn(VALIDE_BSN);
        final Persoonslijst persoonslijst = maakPersoon(ZDT_2016_01_01);
        Mockito.when(selecteerPersoonService.selecteerPersoon(VALIDE_BSN, null, null, String.valueOf(ZENDENDE_PARTIJ_ID), autorisatiebundel))
                .thenReturn(persoonslijst);

        ophalenPersoonService.voerStapUit(bevragingVerzoek, autorisatiebundel);
    }

    private Persoonslijst maakPersoon(final ZonedDateTime tijdstipLaatsteWijzigingGBASystematiek) {
        final Actie actieInhoud = TestVerantwoording.maakActie(1, tijdstipLaatsteWijzigingGBASystematiek);
        //@formatter:off
        MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON.getId())
            .metId(1)
            .metGroep()
                .metGroepElement(Element.PERSOON_AFGELEIDADMINISTRATIEF.getId())
                    .metRecord()
                        .metId(1)
                        .metActieInhoud(actieInhoud)
                        .metAttribuut(Element.PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPLAATSTEWIJZIGINGGBASYSTEMATIEK.getId(),
                            tijdstipLaatsteWijzigingGBASystematiek)
                    .eindeRecord()
            .eindeGroep()
            .build();
        //@formatter:on
        return new Persoonslijst(persoon, 0L);
    }
}
