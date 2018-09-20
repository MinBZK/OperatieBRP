/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bepalers;

import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.expressies.literals.BooleanLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;
import nl.bzk.brp.levering.algemeen.service.ToekomstigeActieService;
import nl.bzk.brp.levering.business.bepalers.impl.PopulatieTransitieBepaler;
import nl.bzk.brp.levering.business.expressietaal.ExpressieService;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.*;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.*;
import nl.bzk.brp.model.basis.MaterieleHistorieImpl;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonPredikaatView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAfgeleidAdministratiefModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingModel;
import nl.bzk.brp.util.StamgegevenBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PopulatieTransitieBepalerTest extends AbstractBepalerTest {

    private static final String ID                                        = "iD";
    private static final String HIS_PERSOON_AFGELEID_ADMINISTRATIEF_LIJST = "hisPersoonAfgeleidAdministratiefLijst";

    @InjectMocks
    private final PopulatieBepaler populatieBepaler = new PopulatieTransitieBepaler();

    @Mock
    private ToekomstigeActieService toekomstigeActieService;

    @Mock
    private ExpressieService expressieService;

    private Leveringsautorisatie leveringsautorisatie;
    private Expressie            expressie;

    @Before
    public final void setup() {
        expressie = null;
        leveringsautorisatie = TestLeveringsautorisatieBuilder.maker().metNaam("TESTABO").maak();
    }

    @Test
    public final void persoonHeeftEenHandeling() throws ExpressieExceptie {
        final AdministratieveHandelingBericht administratieveHandelingBericht =
            mock(AdministratieveHandelingBericht.class);
        when(administratieveHandelingBericht.getTijdstipRegistratie()).thenReturn(TSREG_1990);
        when(expressieService.evalueer(any(Expressie.class), any(PersoonPredikaatView.class))).thenReturn(BooleanLiteralExpressie.WAAR);

        final AdministratieveHandelingModel administratieveHandeling = new AdministratieveHandelingModel(administratieveHandelingBericht);
        final ActieModel actie1 = new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_GEBOORTE),
            administratieveHandeling, null,
            new DatumEvtDeelsOnbekendAttribuut(new DatumAttribuut(TSREG_1990.getWaarde())), null, TSREG_1990, null);

        final PersoonHisVolledigImpl persoon = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
            .nieuwGeboorteRecord(actie1).gemeenteGeboorte(new GemeenteCodeAttribuut((short) 1230)).eindeRecord()
            .nieuwBijhoudingRecord(actie1).bijhoudingspartij(1230).eindeRecord()
            .nieuwAfgeleidAdministratiefRecord(actie1).administratieveHandeling(administratieveHandelingBericht)
            .eindeRecord()
            .build();

        final Populatie populatie =
            populatieBepaler.bepaalInUitPopulatie(persoon, administratieveHandeling, expressie, leveringsautorisatie);

        assertThat(populatie, is(Populatie.BETREEDT));
    }

    @Test
    public final void testNieuwPersoonBuiten() throws ExpressieExceptie {
        when(expressieService.evalueer(any(Expressie.class), any(PersoonPredikaatView.class)))
            .thenReturn(BooleanLiteralExpressie.ONWAAR);
        final PersoonHisVolledigImpl testPersoon = getTestPersoon();
        //Zorg dat de er maar 1 afgeleid administratief is...
        final Iterator<HisPersoonAfgeleidAdministratiefModel> hisPersoonAfgeleidAdministratiefIterator
            = ((Set<HisPersoonAfgeleidAdministratiefModel>) ReflectionTestUtils.getField(testPersoon,
            HIS_PERSOON_AFGELEID_ADMINISTRATIEF_LIJST)).iterator();
        hisPersoonAfgeleidAdministratiefIterator.next();
        hisPersoonAfgeleidAdministratiefIterator.remove();

        final Populatie populatie = populatieBepaler.bepaalInUitPopulatie(testPersoon, new AdministratieveHandelingModel(getAdmhndGeboorte()),
            expressie, leveringsautorisatie);

        assertThat(populatie, is(Populatie.BUITEN));
    }

    @Test
    public final void testNieuwPersoonBetreedt() throws ExpressieExceptie {
        final PersoonHisVolledigImpl testPersoon = getTestPersoon();
        when(expressieService.evalueer(any(Expressie.class), any(PersoonPredikaatView.class)))
            .thenReturn(BooleanLiteralExpressie.WAAR);

        //Zorg dat er maar 1 afgeleid administratief is...
        final Iterator<HisPersoonAfgeleidAdministratiefModel> hisPersoonAfgeleidAdministratiefIterator
            = ((Set<HisPersoonAfgeleidAdministratiefModel>) ReflectionTestUtils.getField(testPersoon, HIS_PERSOON_AFGELEID_ADMINISTRATIEF_LIJST)).iterator();
        hisPersoonAfgeleidAdministratiefIterator.next();
        hisPersoonAfgeleidAdministratiefIterator.remove();

        final Populatie populatie = populatieBepaler.bepaalInUitPopulatie(testPersoon, new AdministratieveHandelingModel(getAdmhndGeboorte()),
            expressie, leveringsautorisatie);

        assertThat(populatie, is(Populatie.BETREEDT));
    }

    @Test
    public final void testBestaandPersoonBetreedt() throws ExpressieExceptie {
        when(toekomstigeActieService.geefToekomstigeActieIdsPlusHuidigeHandeling(
            any(AdministratieveHandelingModel.class),
            any(PersoonHisVolledig.class))).thenReturn(new HashSet<>(Arrays.asList(new Long[]{ 2L })));
        when(expressieService.evalueer(any(Expressie.class), any(PersoonPredikaatView.class)))
            .thenReturn(BooleanLiteralExpressie.WAAR, BooleanLiteralExpressie.ONWAAR);

        final Populatie populatie = populatieBepaler.bepaalInUitPopulatie(getTestPersoon(), new AdministratieveHandelingModel(getAdmhndVerhuizing()),
            expressie, leveringsautorisatie);
        assertThat(populatie, is(Populatie.BETREEDT));
    }

    @Test
    public final void testBestaandPersoonVerlaat() throws ExpressieExceptie {
        when(toekomstigeActieService.geefToekomstigeActieIdsPlusHuidigeHandeling(
            any(AdministratieveHandelingModel.class),
            any(PersoonHisVolledig.class))).thenReturn(new HashSet<>(Arrays.asList(new Long[]{ 2L })));
        when(expressieService.evalueer(any(Expressie.class), any(PersoonPredikaatView.class)))
            .thenReturn(BooleanLiteralExpressie.ONWAAR, BooleanLiteralExpressie.WAAR);

        final Populatie populatie = populatieBepaler.bepaalInUitPopulatie(getTestPersoon(), new AdministratieveHandelingModel(getAdmhndVerhuizing()),
            expressie, leveringsautorisatie);

        assertThat(populatie, is(Populatie.VERLAAT));
    }

    @Test
    public final void testBestaandPersoonBinnen() throws ExpressieExceptie {
        when(expressieService.evalueer(any(Expressie.class), any(PersoonPredikaatView.class)))
            .thenReturn(BooleanLiteralExpressie.WAAR, BooleanLiteralExpressie.WAAR);

        final Populatie populatie = populatieBepaler.bepaalInUitPopulatie(getTestPersoon(), new AdministratieveHandelingModel(getAdmhndVerhuizing()),
            expressie, leveringsautorisatie);

        assertThat(populatie, is(Populatie.BINNEN));
    }

    @Test
    public final void testBestaandPersoonBuiten() throws ExpressieExceptie {
        when(expressieService.evalueer(any(Expressie.class), any(PersoonPredikaatView.class))).thenReturn(
            BooleanLiteralExpressie.ONWAAR, BooleanLiteralExpressie.ONWAAR);

        final Populatie populatie = populatieBepaler.bepaalInUitPopulatie(getTestPersoon(), new AdministratieveHandelingModel(getAdmhndVerhuizing()),
            expressie, leveringsautorisatie);

        assertThat(populatie, is(Populatie.BUITEN));
    }

    @Test
    public final void testBestaandPersoonExpressieEvalueertNaarNull() throws ExpressieExceptie {
        final Expressie nullExpressieResultaat = mock(Expressie.class);
        when(nullExpressieResultaat.isNull()).thenReturn(true);
        when(expressieService.evalueer(any(Expressie.class), any(PersoonPredikaatView.class))).thenReturn(
            nullExpressieResultaat, nullExpressieResultaat);

        final Populatie populatie = populatieBepaler
            .bepaalInUitPopulatie(getTestPersoon(), new AdministratieveHandelingModel(getAdmhndVerhuizing()), expressie, leveringsautorisatie);

        assertThat(populatie, is(Populatie.BUITEN));
    }

    /*
     * Deze test toont aan dat als (test)data niet correct is, het resultaat onverwacht kan zijn.
     *
     * Omdat er maar 2 afgeleid administratief records zijn, classificeert het systeem de vorige view als die van
     * 1992. De verwachte situatie is die van de laatste record van de bijhoudingsgemeente, en dat
     * het resultaat is: buiten de populatie.
     */
    @Test
    public final void persoonMetMeerdereBijhoudingGemeentes() throws ExpressieExceptie {
        when(expressieService.evalueer(any(Expressie.class), any(PersoonPredikaatView.class)))
            .thenReturn(BooleanLiteralExpressie.ONWAAR, BooleanLiteralExpressie.WAAR);

        final AdministratieveHandelingBericht administratieveHandelingBericht1 =
            mock(AdministratieveHandelingBericht.class);

        final AdministratieveHandelingModel administratieveHandeling1 = new AdministratieveHandelingModel(administratieveHandelingBericht1);
        ReflectionTestUtils.setField(administratieveHandeling1, ID, 1L);
        final DatumTijdAttribuut datumTijdRegistratieActie1 = DatumTijdAttribuut.bouwDatumTijd(1922, 2, 19);
        final ActieModel actie1 = new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_ADRES), administratieveHandeling1, null, null, null,
            datumTijdRegistratieActie1, null);
        ReflectionTestUtils.setField(actie1, ID, 1L);

        final PersoonHisVolledigImpl persoon = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
            .nieuwBijhoudingRecord(19920219, null, 19920219).bijhoudingspartij(977).eindeRecord()
            .nieuwAfgeleidAdministratiefRecord(19920219)
            .tijdstipLaatsteWijziging(DatumTijdAttribuut.bouwDatumTijd(1992, 2, 19, 0, 0, 0)).eindeRecord()

            .nieuwAfgeleidAdministratiefRecord(20000101)
            .tijdstipLaatsteWijziging(DatumTijdAttribuut.bouwDatumTijd(2000, 1, 1, 0, 0, 0)).eindeRecord()

            .nieuwAfgeleidAdministratiefRecord(20100101)
            .tijdstipLaatsteWijziging(DatumTijdAttribuut.bouwDatumTijd(2010, 1, 1, 0, 0, 0)).eindeRecord()

            .build();

        persoon.getPersoonBijhoudingHistorie().getActueleRecord().setVerantwoordingInhoud(actie1);

        final AdministratieveHandelingBericht administratieveHandelingBericht2 =
            mock(AdministratieveHandelingBericht.class);
        final AdministratieveHandelingModel administratieveHandeling2 = new AdministratieveHandelingModel(administratieveHandelingBericht2);
        ReflectionTestUtils.setField(administratieveHandeling2, ID, 20L);
        final long actieId2 = 2L;
        final int partijCode2 = 347;
        final DatumTijdAttribuut datumTijdRegistratieActie2 = DatumTijdAttribuut.bouwDatumTijd(2000, 1, 1);
        final int datumAanvangGeldigheid2 = 20000101;
        voegBijhoudingsPartijToe(persoon, administratieveHandeling2, actieId2, partijCode2, datumTijdRegistratieActie2, datumAanvangGeldigheid2);

        final AdministratieveHandelingBericht administratieveHandelingBericht3 =
            mock(AdministratieveHandelingBericht.class);
        final AdministratieveHandelingModel administratieveHandeling3 = new AdministratieveHandelingModel(administratieveHandelingBericht3);
        ReflectionTestUtils.setField(administratieveHandeling3, ID, 200L);
        final long actieId3 = 23L;
        final int partijCode3 = 977;
        final DatumTijdAttribuut datumTijdRegistratieActie3 = DatumTijdAttribuut.bouwDatumTijd(2010, 1, 1);
        final int datumAanvangGeldigheid3 = 20100101;
        voegBijhoudingsPartijToe(persoon, administratieveHandeling3, actieId3, partijCode3, datumTijdRegistratieActie3, datumAanvangGeldigheid3);

        when(toekomstigeActieService.geefToekomstigeActieIds(
            any(AdministratieveHandelingModel.class),
            any(PersoonHisVolledig.class))).thenReturn(new HashSet<>(Arrays.asList(new Long[]{ 23L })));
        when(toekomstigeActieService.geefToekomstigeActieIdsPlusHuidigeHandeling(
            any(AdministratieveHandelingModel.class),
            any(PersoonHisVolledig.class))).thenReturn(new HashSet<>(Arrays.asList(new Long[]{ 2L, 23L })));

        final Populatie populatie = populatieBepaler.bepaalInUitPopulatie(persoon, administratieveHandeling2, expressie, leveringsautorisatie);
        assertThat(populatie, is(Populatie.VERLAAT));
    }

    private void voegBijhoudingsPartijToe(final PersoonHisVolledigImpl persoon, final AdministratieveHandelingModel administratieveHandeling,
        final long actieId, final int partijCode, final DatumTijdAttribuut datumTijdRegistratieActie, final int datumAanvangGeldigheid)
    {
        final PartijAttribuut partijAttribuut = new PartijAttribuut(
            StamgegevenBuilder.bouwDynamischStamgegeven(Partij.class, new PartijCodeAttribuut(partijCode)));
        final ActieModel actie = new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_ADRES), administratieveHandeling, null, null, null,
            datumTijdRegistratieActie, null);
        ReflectionTestUtils.setField(actie, ID, actieId);
        final MaterieleHistorieImpl materieleHistorie = new MaterieleHistorieImpl();
        materieleHistorie.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(datumAanvangGeldigheid));
        final HisPersoonBijhoudingModel hisPersoonBijhoudingModel = new HisPersoonBijhoudingModel(persoon, partijAttribuut, null, null, null, actie,
            materieleHistorie);
        persoon.getPersoonBijhoudingHistorie().voegToe(hisPersoonBijhoudingModel);
    }

    @Test
    public void bepalenMetTerugwerkendeKracht() throws ExpressieExceptie {
        final PersoonHisVolledigImpl persoon = getTestPersoon();
        when(expressieService.evalueer(any(Expressie.class), any(PersoonPredikaatView.class))).thenReturn(BooleanLiteralExpressie.WAAR,
                                                                                                          NullValue.getInstance());
        final Set<Long> toekomstActieIds = new HashSet<>(Collections.singletonList(1L));
        final Set<Long> toekomstEnHuidigeActieIds = new HashSet<>(Arrays.asList(1L, 2L));

        Mockito.when(toekomstigeActieService.geefToekomstigeActieIds(any(AdministratieveHandelingModel.class), any(PersoonHisVolledig.class)))
            .thenReturn(toekomstActieIds);
        Mockito.when(toekomstigeActieService.geefToekomstigeActieIdsPlusHuidigeHandeling(any(AdministratieveHandelingModel.class),
            any(PersoonHisVolledig.class))).thenReturn(toekomstEnHuidigeActieIds);

        final Populatie populatie = populatieBepaler.bepaalInUitPopulatie(persoon, getAdmhndGeboorte(), expressie, leveringsautorisatie);
        Assert.assertThat(populatie, is(Populatie.BETREEDT));
    }
}
