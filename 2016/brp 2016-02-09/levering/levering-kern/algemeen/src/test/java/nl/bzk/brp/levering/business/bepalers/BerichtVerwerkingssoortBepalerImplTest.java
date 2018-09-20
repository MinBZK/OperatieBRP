/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bepalers;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.brp.levering.business.bepalers.impl.BerichtVerwerkingssoortBepalerImpl;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.basis.AbstractFormeelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.AbstractMaterieelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.VerantwoordingTbvLeveringMutaties;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.HuwelijkHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.RelatieHisVolledigView;
import nl.bzk.brp.model.levering.AdministratieveHandelingSynchronisatie;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAfgeleidAdministratiefModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIdentificatienummersModel;
import nl.bzk.brp.model.operationeel.kern.HisRelatieModel;
import nl.bzk.brp.util.hisvolledig.kern.HuwelijkHisVolledigImplBuilder;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class BerichtVerwerkingssoortBepalerImplTest {

    private static final long   ADMINISTRATIEVE_HANDELING_ID = 1;
    private static final String INTERNE_SET                  = "interneSet";

    @InjectMocks
    private final BerichtVerwerkingssoortBepaler berichtVerwerkingssoortBepaler = new BerichtVerwerkingssoortBepalerImpl();

    @Mock
    private ActieModel verantwoordingInhoud;

    @Mock
    private ActieModel verantwoordingVerval;

    @Mock
    private ActieModel verantwoordingAanpassingGeldigheid;

    @Mock
    private AdministratieveHandelingSynchronisatie administratieveHandelingSynchronisatieMock;

    @Mock
    private AdministratieveHandelingModel administratieveHandelingInhoudMock;

    @Mock
    private AdministratieveHandelingModel administratieveHandelingGeldigheidMock;

    @Mock(answer = org.mockito.Answers.CALLS_REAL_METHODS)
    private AbstractMaterieelHistorischMetActieVerantwoording materieelHistorischMetActieVerantwoording;

    @Mock(answer = org.mockito.Answers.CALLS_REAL_METHODS)
    private AbstractFormeelHistorischMetActieVerantwoording formeelHistorischMetActieVerantwoording;

    @Before
    public final void setup() {
        when(administratieveHandelingGeldigheidMock.getID()).thenReturn(2L);

        when(verantwoordingInhoud.getAdministratieveHandeling()).thenReturn(administratieveHandelingInhoudMock);
        when(administratieveHandelingInhoudMock.getID()).thenReturn(ADMINISTRATIEVE_HANDELING_ID);
        when(verantwoordingAanpassingGeldigheid.getAdministratieveHandeling()).thenReturn(administratieveHandelingGeldigheidMock);
    }

    @Test
    public final void testMaterieleHistorieT() {
        when(administratieveHandelingSynchronisatieMock.getID()).thenReturn(ADMINISTRATIEVE_HANDELING_ID);
        materieelHistorischMetActieVerantwoording.setVerantwoordingInhoud(verantwoordingInhoud);

        final Verwerkingssoort resultaat =
            berichtVerwerkingssoortBepaler.bepaalVerwerkingssoort(materieelHistorischMetActieVerantwoording, ADMINISTRATIEVE_HANDELING_ID);

        assertEquals(Verwerkingssoort.TOEVOEGING, resultaat);
    }

    @Test
    public final void testMaterieleHistorieMetGeldigheidT() {
        when(administratieveHandelingSynchronisatieMock.getID()).thenReturn(ADMINISTRATIEVE_HANDELING_ID);
        when(administratieveHandelingGeldigheidMock.getID()).thenReturn(ADMINISTRATIEVE_HANDELING_ID);
        materieelHistorischMetActieVerantwoording.setVerantwoordingInhoud(verantwoordingInhoud);
        materieelHistorischMetActieVerantwoording.setVerantwoordingAanpassingGeldigheid(verantwoordingAanpassingGeldigheid);

        final Verwerkingssoort resultaat =
            berichtVerwerkingssoortBepaler.bepaalVerwerkingssoort(materieelHistorischMetActieVerantwoording, ADMINISTRATIEVE_HANDELING_ID);

        assertEquals(Verwerkingssoort.TOEVOEGING, resultaat);
    }

    @Test
    public final void testMaterieleHistorieW() {
        materieelHistorischMetActieVerantwoording.setVerantwoordingInhoud(verantwoordingInhoud);
        materieelHistorischMetActieVerantwoording.setVerantwoordingAanpassingGeldigheid(verantwoordingAanpassingGeldigheid);

        when(administratieveHandelingSynchronisatieMock.getID()).thenReturn(2L);

        final Verwerkingssoort resultaat =
            berichtVerwerkingssoortBepaler.bepaalVerwerkingssoort(materieelHistorischMetActieVerantwoording, 2L);

        assertEquals(Verwerkingssoort.WIJZIGING, resultaat);
    }

    @Test
    public final void testMaterieleHistorieX() {
        materieelHistorischMetActieVerantwoording.setVerantwoordingInhoud(verantwoordingInhoud);
        materieelHistorischMetActieVerantwoording.setVerantwoordingVerval(verantwoordingVerval);
        when(verantwoordingVerval.getAdministratieveHandeling()).thenReturn(administratieveHandelingInhoudMock);
        when(administratieveHandelingSynchronisatieMock.getID()).thenReturn(ADMINISTRATIEVE_HANDELING_ID);

        final Verwerkingssoort resultaat =
            berichtVerwerkingssoortBepaler.bepaalVerwerkingssoort(materieelHistorischMetActieVerantwoording, ADMINISTRATIEVE_HANDELING_ID);

        assertEquals(Verwerkingssoort.VERVAL, resultaat);
    }

    @Test
    public final void testMaterieleHistorieI() {
        final HisPersoonIdentificatienummersModel hisPersoonIdentificatienummersModel =
            TestPersoonJohnnyJordaan.maak().getPersoonIdentificatienummersHistorie().getActueleRecord();
        hisPersoonIdentificatienummersModel.setVerantwoordingInhoud(verantwoordingInhoud);

        final Verwerkingssoort resultaat = berichtVerwerkingssoortBepaler.bepaalVerwerkingssoort(hisPersoonIdentificatienummersModel, 4L);

        assertEquals(Verwerkingssoort.IDENTIFICATIE, resultaat);
    }

    @Test
    public final void testMaterieleHistorieR() {
        final HisPersoonBijhoudingModel hisPersoonBijhoudingModel =
            TestPersoonJohnnyJordaan.maak().getPersoonBijhoudingHistorie().getActueleRecord();
        hisPersoonBijhoudingModel.setVerantwoordingInhoud(verantwoordingInhoud);

        final Verwerkingssoort resultaat = berichtVerwerkingssoortBepaler.bepaalVerwerkingssoort(hisPersoonBijhoudingModel, 4L);

        assertEquals(Verwerkingssoort.REFERENTIE, resultaat);
    }

    @Test
    public final void testFormeleHistorieT() {
        when(administratieveHandelingSynchronisatieMock.getID()).thenReturn(ADMINISTRATIEVE_HANDELING_ID);
        formeelHistorischMetActieVerantwoording.setVerantwoordingInhoud(verantwoordingInhoud);

        final Verwerkingssoort resultaat =
            berichtVerwerkingssoortBepaler.bepaalVerwerkingssoort(formeelHistorischMetActieVerantwoording, ADMINISTRATIEVE_HANDELING_ID);

        assertEquals(Verwerkingssoort.TOEVOEGING, resultaat);
    }

    @Test
    public final void testFormeleHistorieX() {
        formeelHistorischMetActieVerantwoording.setVerantwoordingInhoud(verantwoordingInhoud);
        formeelHistorischMetActieVerantwoording.setVerantwoordingVerval(verantwoordingVerval);
        when(verantwoordingVerval.getAdministratieveHandeling()).thenReturn(administratieveHandelingInhoudMock);
        when(administratieveHandelingSynchronisatieMock.getID()).thenReturn(ADMINISTRATIEVE_HANDELING_ID);

        final Verwerkingssoort resultaat =
            berichtVerwerkingssoortBepaler.bepaalVerwerkingssoort(formeelHistorischMetActieVerantwoording, ADMINISTRATIEVE_HANDELING_ID);

        assertEquals(Verwerkingssoort.VERVAL, resultaat);
    }

    @Test
    public final void testFormeleHistorieI() {
        final HisPersoonGeboorteModel hisPersoonGeboorteModel = TestPersoonJohnnyJordaan.maak().getPersoonGeboorteHistorie().getActueleRecord();
        hisPersoonGeboorteModel.setVerantwoordingInhoud(verantwoordingInhoud);

        final Verwerkingssoort resultaat = berichtVerwerkingssoortBepaler.bepaalVerwerkingssoort(hisPersoonGeboorteModel, 4L);

        assertEquals(Verwerkingssoort.IDENTIFICATIE, resultaat);
    }

    @Test
    public final void testFormeleHistorieR() {
        final HisPersoonAfgeleidAdministratiefModel hisPersoonAfgeleidAdministratiefModel =
            TestPersoonJohnnyJordaan.maak().getPersoonAfgeleidAdministratiefHistorie().getActueleRecord();
        hisPersoonAfgeleidAdministratiefModel.setVerantwoordingInhoud(verantwoordingInhoud);

        final Verwerkingssoort resultaat = berichtVerwerkingssoortBepaler.bepaalVerwerkingssoort(hisPersoonAfgeleidAdministratiefModel, 4L);

        assertEquals(Verwerkingssoort.REFERENTIE, resultaat);
    }

    @Test
    public final void testBepaalVerwerkingssoortPersoonEnAfgeleidAdministratiefW() {
        final PersoonHisVolledigImpl testPersoon = TestPersoonJohnnyJordaan.maak();
        final PersoonHisVolledigView persoonHisVolledigView = new PersoonHisVolledigView(testPersoon, null);

        final Verwerkingssoort resultaat = berichtVerwerkingssoortBepaler.bepaalVerwerkingssoortPersoon(4L, persoonHisVolledigView);

        assertEquals(Verwerkingssoort.WIJZIGING, resultaat);
    }

    @Test
    public final void testBepaalVerwerkingssoortPersoonEnAfgeleidAdministratiefT() {
        final PersoonHisVolledigImpl testPersoon = TestPersoonJohnnyJordaan.maak();
        final PersoonHisVolledigView persoonHisVolledigView = new PersoonHisVolledigView(testPersoon, null);

        final Verwerkingssoort resultaat =
            berichtVerwerkingssoortBepaler.bepaalVerwerkingssoortPersoon(ADMINISTRATIEVE_HANDELING_ID, persoonHisVolledigView);

        assertEquals(Verwerkingssoort.TOEVOEGING, resultaat);
    }

    @Test
    public final void testBepaalVerwerkingssoortPersoonEnAfgeleidAdministratiefI() {
        final PersoonHisVolledigImpl testPersoon = TestPersoonJohnnyJordaan.maak();
        final Set<AbstractFormeelHistorischMetActieVerantwoording> geboorteHistorieSet = new HashSet<>();
        ReflectionTestUtils.setField(testPersoon.getPersoonAfgeleidAdministratiefHistorie(), INTERNE_SET, geboorteHistorieSet);
        final PersoonHisVolledigView persoonHisVolledigView = new PersoonHisVolledigView(testPersoon, null);

        final Verwerkingssoort resultaat =
            berichtVerwerkingssoortBepaler.bepaalVerwerkingssoortPersoon(ADMINISTRATIEVE_HANDELING_ID, persoonHisVolledigView);

        assertEquals(Verwerkingssoort.IDENTIFICATIE, resultaat);
    }

    @Test
    public void testBepaalVerantwoordingVerval() {
        final VerantwoordingTbvLeveringMutaties mutaties = Mockito.mock(VerantwoordingTbvLeveringMutaties.class);
        final ActieModel actieModel = Mockito.mock(ActieModel.class);
        when(mutaties.getVerantwoordingVervalTbvLeveringMutaties()).thenReturn(actieModel);
        assertEquals(actieModel, ReflectionTestUtils.invokeMethod(berichtVerwerkingssoortBepaler, "bepaalVerantwoordingVerval", mutaties));
    }

    @Test
    public void testIsVervallenDoorDezeHandeling() {
        final Long administratieveHandelingId = 1L;

        final ActieModel actieModel = mock(ActieModel.class);
        final AdministratieveHandelingModel handelingModel = mock(AdministratieveHandelingModel.class);
        when(handelingModel.getID()).thenReturn(2L);
        when(actieModel.getAdministratieveHandeling()).thenReturn(handelingModel);

        assertFalse(isVervallenDoorDezeHandeling(null, null));
        assertFalse(isVervallenDoorDezeHandeling(administratieveHandelingId, null));
        assertFalse(isVervallenDoorDezeHandeling(administratieveHandelingId, actieModel));

        when(handelingModel.getID()).thenReturn(1L);
        assertTrue(isVervallenDoorDezeHandeling(administratieveHandelingId, actieModel));
    }

    private Boolean isVervallenDoorDezeHandeling(final Long administratieveHandelingId, final ActieModel actieModel) {
        return (Boolean) ReflectionTestUtils.invokeMethod(berichtVerwerkingssoortBepaler, "isVervallenDoorDezeHandeling", actieModel,
                                                          administratieveHandelingId);
    }

    @Test
    public void testIsWijziging() {
        final ActieModel verantwoordingInhoudActie = mock(ActieModel.class);
        final AdministratieveHandelingModel verantwoordingInhoudModel = mock(AdministratieveHandelingModel.class);
        when(verantwoordingInhoudActie.getAdministratieveHandeling()).thenReturn(verantwoordingInhoudModel);

        final ActieModel verantwoordingEindeGeldigheid = mock(ActieModel.class);
        final AdministratieveHandelingModel verantwoordingEindeGeldigheidModel = mock(AdministratieveHandelingModel.class);
        when(verantwoordingEindeGeldigheid.getAdministratieveHandeling()).thenReturn(verantwoordingEindeGeldigheidModel);

        final long administratieveHandelingId = 1;
        final long verantwoordingInhoudId = 2;

        assertFalse(isWijziging(null, null, null));
        assertFalse(isWijziging(verantwoordingInhoudActie, null, null));
        assertFalse(isWijziging(null, verantwoordingEindeGeldigheid, null));

        when(verantwoordingEindeGeldigheidModel.getID()).thenReturn(verantwoordingInhoudId);
        when(verantwoordingInhoudModel.getID()).thenReturn(verantwoordingInhoudId);
        assertFalse(isWijziging(verantwoordingInhoudActie, verantwoordingEindeGeldigheid, administratieveHandelingId));

        when(verantwoordingEindeGeldigheidModel.getID()).thenReturn(administratieveHandelingId);
        when(verantwoordingInhoudModel.getID()).thenReturn(administratieveHandelingId);
        assertFalse(isWijziging(verantwoordingInhoudActie, verantwoordingEindeGeldigheid, administratieveHandelingId));

        when(verantwoordingEindeGeldigheidModel.getID()).thenReturn(administratieveHandelingId);
        when(verantwoordingInhoudModel.getID()).thenReturn(verantwoordingInhoudId);
        assertTrue(isWijziging(verantwoordingInhoudActie, verantwoordingEindeGeldigheid, administratieveHandelingId));
    }

    private Boolean isWijziging(final ActieModel verantwoordingInhoudActie, final ActieModel verantwoordingEindeGeldigheid,
                                final Long administratieveHandelingId)
    {
        return (Boolean) ReflectionTestUtils.invokeMethod(berichtVerwerkingssoortBepaler, "isWijziging", verantwoordingInhoudActie,
                                                          verantwoordingEindeGeldigheid, administratieveHandelingId);
    }

    @Test
    public void testisToevoegingMetAanpassingGeldigheid() {
        final ActieModel verantwoordingInhoudActie = mock(ActieModel.class);
        final AdministratieveHandelingModel verantwoordingInhoudAdmHnd = mock(AdministratieveHandelingModel.class);
        when(verantwoordingInhoudActie.getAdministratieveHandeling()).thenReturn(verantwoordingInhoudAdmHnd);

        final ActieModel verantwoordingEindeGeldigheid = mock(ActieModel.class);
        final AdministratieveHandelingModel verantwoordingEindeGeldigheidModel = mock(AdministratieveHandelingModel.class);
        when(verantwoordingEindeGeldigheid.getAdministratieveHandeling()).thenReturn(verantwoordingEindeGeldigheidModel);

        final long administratieveHandelingId = 1;
        final long verantwoordingInhoudId = 2;

        assertFalse(isToevoegingMetAanpassingGeldigheid(null, null, null));
        assertFalse(isToevoegingMetAanpassingGeldigheid(verantwoordingInhoudActie, null, null));
        assertFalse(isToevoegingMetAanpassingGeldigheid(null, verantwoordingEindeGeldigheid, null));

        when(verantwoordingEindeGeldigheidModel.getID()).thenReturn(administratieveHandelingId);
        when(verantwoordingInhoudAdmHnd.getID()).thenReturn(verantwoordingInhoudId);

        when(verantwoordingEindeGeldigheidModel.getID()).thenReturn(verantwoordingInhoudId);
        when(verantwoordingInhoudAdmHnd.getID()).thenReturn(verantwoordingInhoudId);
        assertFalse(isToevoegingMetAanpassingGeldigheid(verantwoordingInhoudActie, verantwoordingEindeGeldigheid, administratieveHandelingId));

        when(verantwoordingEindeGeldigheidModel.getID()).thenReturn(administratieveHandelingId);
        when(verantwoordingInhoudAdmHnd.getID()).thenReturn(administratieveHandelingId);
        assertTrue(isToevoegingMetAanpassingGeldigheid(verantwoordingInhoudActie, verantwoordingEindeGeldigheid, administratieveHandelingId));
    }

    private Boolean isToevoegingMetAanpassingGeldigheid(final ActieModel verantwoordingInhoudActie, final ActieModel verantwoordingEindeGeldigheid,
                                                        final Long administratieveHandelingId)
    {
        return (Boolean) ReflectionTestUtils.invokeMethod(berichtVerwerkingssoortBepaler, "isToevoegingMetAanpassingGeldigheid", verantwoordingInhoudActie,
                                                          verantwoordingEindeGeldigheid, administratieveHandelingId);
    }

    @Test
    public void testIsToevoegingDoorDezeAdministratievehandeling() {
        final long administrativeHandelingModelId = 1;
        final ActieModel verantwoordingInhoudActie = mock(ActieModel.class);
        final ActieModel verantwoordingVervalActie = mock(ActieModel.class);
        final ActieModel verantwoordingEindeGeldigheid = mock(ActieModel.class);
        final AdministratieveHandelingSynchronisatie administratieveHandeling = mock(AdministratieveHandelingSynchronisatie.class);

        final AdministratieveHandelingModel verantwoordingInhoudModel = mock(AdministratieveHandelingModel.class);
        when(verantwoordingInhoudActie.getAdministratieveHandeling()).thenReturn(verantwoordingInhoudModel);

        when(administratieveHandeling.getID()).thenReturn(administrativeHandelingModelId);
        when(verantwoordingInhoudModel.getID()).thenReturn(administrativeHandelingModelId);
        assertTrue(isToevoegingDoorDezeAdministratievehandeling(verantwoordingInhoudActie, null, null, administrativeHandelingModelId));

        assertFalse(isToevoegingDoorDezeAdministratievehandeling(verantwoordingInhoudActie, verantwoordingVervalActie, null,
                                                                 administrativeHandelingModelId));
        assertFalse(isToevoegingDoorDezeAdministratievehandeling(verantwoordingInhoudActie, verantwoordingVervalActie, verantwoordingEindeGeldigheid,
                                                                 administrativeHandelingModelId));
        assertFalse(isToevoegingDoorDezeAdministratievehandeling(verantwoordingInhoudActie, null, verantwoordingEindeGeldigheid,
                                                                 administrativeHandelingModelId));

        when(verantwoordingInhoudModel.getID()).thenReturn(0 - administrativeHandelingModelId);
        assertFalse(isToevoegingDoorDezeAdministratievehandeling(verantwoordingInhoudActie, null, null, administrativeHandelingModelId));
    }

    private Boolean isToevoegingDoorDezeAdministratievehandeling(final ActieModel verantwoordingInhoudActie, final ActieModel verantwoordingVervalActie,
                                                                 final ActieModel verantwoordingEindeGeldigheid,
                                                                 final Long administratieveHandelingId)
    {
        return (Boolean) ReflectionTestUtils.invokeMethod(berichtVerwerkingssoortBepaler, "isToevoegingDoorDezeAdministratievehandeling",
                                                          verantwoordingInhoudActie, verantwoordingVervalActie, verantwoordingEindeGeldigheid,
                                                          administratieveHandelingId);
    }

    @Test
    public void zetVerwerkingssoortVervalBijVervallenGroepEnAlleVoorkomensVervallen() {
        final ActieModel actie1 = mock(ActieModel.class);
        final ActieModel actie2 = mock(ActieModel.class);
        final ActieModel actie3 = mock(ActieModel.class);
        final AdministratieveHandelingModel handeling1 = mock(AdministratieveHandelingModel.class);
        final AdministratieveHandelingModel handeling2 = mock(AdministratieveHandelingModel.class);
        final AdministratieveHandelingModel handeling3 = mock(AdministratieveHandelingModel.class);

        final Calendar registratieCalendar = Calendar.getInstance();
        registratieCalendar.add(Calendar.MONTH, -2);
        final Calendar vervalRegistratieCalendar = Calendar.getInstance();
        vervalRegistratieCalendar.add(Calendar.MONTH, -1);
        when(actie1.getTijdstipRegistratie()).thenReturn(new DatumTijdAttribuut(registratieCalendar.getTime()));
        when(actie2.getTijdstipRegistratie()).thenReturn(new DatumTijdAttribuut(vervalRegistratieCalendar.getTime()));
        when(actie1.getAdministratieveHandeling()).thenReturn(handeling1);
        when(actie2.getAdministratieveHandeling()).thenReturn(handeling2);
        when(actie3.getAdministratieveHandeling()).thenReturn(handeling3);
        when(handeling1.getID()).thenReturn(1L);
        when(handeling2.getID()).thenReturn(2L);
        when(handeling3.getID()).thenReturn(3L);

        final HuwelijkHisVolledigImpl huwelijkHisVolledig = new HuwelijkHisVolledigImplBuilder()
            .nieuwStandaardRecord(actie1).eindeRecord()
            .nieuwStandaardRecord(actie2).eindeRecord()
            .build();
        final HisRelatieModel actueleRecord = huwelijkHisVolledig.getRelatieHistorie().getActueleRecord();
        actueleRecord.setVerantwoordingVerval(actie3);
        actueleRecord.getFormeleHistorie().setDatumTijdVerval(new DatumTijdAttribuut(Calendar.getInstance().getTime()));
        final RelatieHisVolledigView relatieView = new HuwelijkHisVolledigView(huwelijkHisVolledig, null);

        final Verwerkingssoort verwerkingssoort = berichtVerwerkingssoortBepaler.bepaalVerwerkingssoortVoorRelaties(relatieView, 3L);

        assertThat(verwerkingssoort, is(Verwerkingssoort.VERVAL));
    }
}
