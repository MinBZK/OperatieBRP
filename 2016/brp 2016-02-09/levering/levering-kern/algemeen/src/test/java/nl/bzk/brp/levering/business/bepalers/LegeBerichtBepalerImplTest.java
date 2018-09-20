/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bepalers;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import nl.bzk.brp.levering.business.bepalers.impl.LegeBerichtBepalerImpl;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.MaterieleHistorieSet;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OntleningstoelichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.predikaat.AdministratieveHandelingDeltaPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaat.IsInOnderzoekPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAfgeleidAdministratiefModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIdentificatienummersModel;
import nl.bzk.brp.util.VerantwoordingTestUtil;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonAntwoordPersoon;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class LegeBerichtBepalerImplTest {

    private static final String ID = "iD";
    private static final long ADMINISTRATIEVE_HANDELING_ID = 123L;
    private static final long ONBESTAANDE_ADMINISTRATIEVE_HANDELING_ID = 98765432;
    private static final String INTERNE_SET = "interneSet";
    private static final String ATTRIBUTEN_GEWIJZIGD_EN_AUTORISATIE_OP_ATTRIBUUT =
        "bepaalOfAttributenGewijzigdZijnTovElkaarEnErAutorisatieIsOpHetAttribuut";
    private final LegeBerichtBepaler bepaler = new LegeBerichtBepalerImpl();

    @Test
    public final void testMagNietGeleverdWordenHeeftGeenAttributenDieGeleverdMogenWorden() {
        final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonJohnnyJordaan.maak();
        final PersoonHisVolledigView persoon =
            new PersoonHisVolledigView(persoonHisVolledig, new AdministratieveHandelingDeltaPredikaat(ONBESTAANDE_ADMINISTRATIEVE_HANDELING_ID));

        final boolean resultaat = bepaler.magPersoonGeleverdWorden(persoon);
        assertThat(resultaat, is(false));
    }

    @Test
    public final void testMagGeleverdWordenAttributenInOnderzoek() {
        final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonJohnnyJordaan.maak();
        final HisPersoonGeboorteModel actueleRecordGeboorte = persoonHisVolledig.getPersoonGeboorteHistorie().getActueleRecord();
        actueleRecordGeboorte.getDatumGeboorte().setInOnderzoek(true);
        actueleRecordGeboorte.getDatumGeboorte().setMagGeleverdWorden(true);
        final PersoonHisVolledigView persoon = new PersoonHisVolledigView(persoonHisVolledig, new IsInOnderzoekPredikaat());

        final boolean resultaat = bepaler.magPersoonGeleverdWorden(persoon);
        assertThat(resultaat, is(true));
    }

    @Test
    public final void testMagWelGeleverdWordenHeeftWijzigingenInIdentificerendeGroep() {
        final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonJohnnyJordaan.maak();
        final PersoonHisVolledigView persoon = new PersoonHisVolledigView(persoonHisVolledig, new AdministratieveHandelingDeltaPredikaat(3L));

        final boolean resultaat = bepaler.magPersoonGeleverdWorden(persoon);

        assertThat(resultaat, is(true));
    }

    @Test
    public final void testMagGeleverdWordenHeeftNietIdentificerendeGroepenDieGeleverdMogenWorden() {
        final PersoonHisVolledigImpl persoon = TestPersoonAntwoordPersoon.maakAntwoordPersoon();

        final AdministratieveHandelingModel administratieveHandeling = maakAdministratieveHandeling();
        final ActieModel actie = maakActie(administratieveHandeling);

        final HisPersoonAfgeleidAdministratiefModel eersteRecord = persoon.getPersoonAfgeleidAdministratiefHistorie().getActueleRecord();
        final HisPersoonAfgeleidAdministratiefModel gewijzigdeRecord = new HisPersoonAfgeleidAdministratiefModel(
            persoon, administratieveHandeling, administratieveHandeling.getTijdstipRegistratie(),
            // Exact hetzelfde
            eersteRecord.getSorteervolgorde(),
            // Exact hetzelfde
            eersteRecord.getIndicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig(),
            eersteRecord.getTijdstipLaatsteWijzigingGBASystematiek(),
            actie);

        persoon.getPersoonAfgeleidAdministratiefHistorie().voegToe(gewijzigdeRecord);

        // Geen predikaat dus alle historiegroepen zijn aanwezig
        final PersoonHisVolledigView persoonView = new PersoonHisVolledigView(persoon, null);

        // Autorisatie handmatig zetten
        for (final HisPersoonAfgeleidAdministratiefModel afgeleidAdministratief : persoonView.getPersoonAfgeleidAdministratiefHistorie()) {
            afgeleidAdministratief.getIndicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig().setMagGeleverdWorden(true);
        }

        final boolean resultaat = bepaler.magPersoonGeleverdWorden(persoonView);
        assertThat(resultaat, is(true));
    }

    @Test
    public final void testMagNietGeleverdWordenAfgeleidAdministratiefIndicatiesOngewijzigd() {
        final PersoonHisVolledigImpl persoon = TestPersoonAntwoordPersoon.maakAntwoordPersoon();

        final AdministratieveHandelingModel administratieveHandeling = maakAdministratieveHandeling();
        final ActieModel actie = maakActie(administratieveHandeling);

        final HisPersoonAfgeleidAdministratiefModel eersteRecord = persoon.getPersoonAfgeleidAdministratiefHistorie().getActueleRecord();
        final HisPersoonAfgeleidAdministratiefModel gewijzigdeRecord = new HisPersoonAfgeleidAdministratiefModel(
            persoon, administratieveHandeling, administratieveHandeling.getTijdstipRegistratie(),
            // Exact hetzelfde
            eersteRecord.getSorteervolgorde(),
            // Exact hetzelfde
            eersteRecord.getIndicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig(),
            eersteRecord.getTijdstipLaatsteWijzigingGBASystematiek(),
            actie);

        persoon.getPersoonAfgeleidAdministratiefHistorie().voegToe(gewijzigdeRecord);

        final PersoonHisVolledigView persoonView = new PersoonHisVolledigView(persoon,
                                                                              new AdministratieveHandelingDeltaPredikaat(ADMINISTRATIEVE_HANDELING_ID));

        // Autorisatie handmatig zetten
        for (final HisPersoonAfgeleidAdministratiefModel afgeleidAdministratief : persoonView.getPersoonAfgeleidAdministratiefHistorie()) {
            afgeleidAdministratief.getIndicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig().setMagGeleverdWorden(true);
        }

        final boolean resultaat = bepaler.magPersoonGeleverdWorden(persoonView);
        assertThat(resultaat, is(false));
    }

    @Test
    public final void testMagGeleverdWordenAfgeleidAdministratiefIndicatieOnverwerktGewijzigd() {
        final PersoonHisVolledigImpl persoon = TestPersoonAntwoordPersoon.maakAntwoordPersoon();

        final AdministratieveHandelingModel administratieveHandeling = maakAdministratieveHandeling();
        final ActieModel actie = maakActie(administratieveHandeling);

        final HisPersoonAfgeleidAdministratiefModel eersteRecord = persoon.getPersoonAfgeleidAdministratiefHistorie().getActueleRecord();
        final HisPersoonAfgeleidAdministratiefModel gewijzigdeRecord = new HisPersoonAfgeleidAdministratiefModel(
            persoon, administratieveHandeling, administratieveHandeling.getTijdstipRegistratie(),
            // Exact hetzelfde
            eersteRecord.getSorteervolgorde(),
            // Wijziging in indicatie
            new JaNeeAttribuut(!eersteRecord.getIndicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig().getWaarde()),
            eersteRecord.getTijdstipLaatsteWijzigingGBASystematiek(),
            actie);

        persoon.getPersoonAfgeleidAdministratiefHistorie().voegToe(gewijzigdeRecord);

        final PersoonHisVolledigView persoonView = new PersoonHisVolledigView(persoon,
                                                                              new AdministratieveHandelingDeltaPredikaat(ADMINISTRATIEVE_HANDELING_ID));

        // Autorisatie handmatig zetten
        for (final HisPersoonAfgeleidAdministratiefModel afgeleidAdministratief : persoonView.getPersoonAfgeleidAdministratiefHistorie()) {
            afgeleidAdministratief.getIndicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig().setMagGeleverdWorden(true);
        }

        final boolean resultaat = bepaler.magPersoonGeleverdWorden(persoonView);
        assertThat(resultaat, is(true));
    }

    @Test(expected = IllegalStateException.class)
    public final void testFoutmeldingAlsPersoonMaarEenAfgeleidAdministratiefRecordHeeft() {
        // Maak view van persoon die maar 1 afgeleid administratief record heeft.
        final PersoonHisVolledigImpl persoon = TestPersoonJohnnyJordaan.maak();
        final FormeleHistorieSet<HisPersoonGeboorteModel> persoonGeboorteHistorie = persoon.getPersoonGeboorteHistorie();
        ReflectionTestUtils.setField(persoonGeboorteHistorie, INTERNE_SET, new HashSet<>());
        final MaterieleHistorieSet<HisPersoonIdentificatienummersModel> persoonIdentificatienummersHistorie = persoon
            .getPersoonIdentificatienummersHistorie();
        ReflectionTestUtils.setField(persoonIdentificatienummersHistorie, INTERNE_SET, new HashSet<>());
        final MaterieleHistorieSet<HisPersoonBijhoudingModel> persoonBijhoudingHistorie = persoon.getPersoonBijhoudingHistorie();
        ReflectionTestUtils.setField(persoonBijhoudingHistorie, INTERNE_SET, new HashSet<>());
        final FormeleHistorieSet<HisPersoonAfgeleidAdministratiefModel> persoonAfgeleidAdministratiefHistorie =
            persoon.getPersoonAfgeleidAdministratiefHistorie();
        ReflectionTestUtils.setField(persoonAfgeleidAdministratiefHistorie, INTERNE_SET,
                                     new HashSet<>(Arrays.asList(persoonAfgeleidAdministratiefHistorie.getActueleRecord())));


        final PersoonHisVolledigView persoonView = new PersoonHisVolledigView(persoon, null);
        final boolean resultaat = bepaler.magPersoonGeleverdWorden(persoonView);
        assertThat(resultaat, is(true));
    }

    @Test
    public void heeftAndereJaNeeAttributen() {
        final JaNeeAttribuut jaAttribuut = new JaNeeAttribuut(true);
        jaAttribuut.setMagGeleverdWorden(true);
        final JaNeeAttribuut neeAttribuut = new JaNeeAttribuut(false);
        neeAttribuut.setMagGeleverdWorden(false);

        assertFalse((Boolean) ReflectionTestUtils.invokeMethod(bepaler, ATTRIBUTEN_GEWIJZIGD_EN_AUTORISATIE_OP_ATTRIBUUT, null, null));

        assertTrue((Boolean) ReflectionTestUtils.invokeMethod(bepaler, ATTRIBUTEN_GEWIJZIGD_EN_AUTORISATIE_OP_ATTRIBUUT, jaAttribuut, null));
        assertTrue((Boolean) ReflectionTestUtils.invokeMethod(bepaler, ATTRIBUTEN_GEWIJZIGD_EN_AUTORISATIE_OP_ATTRIBUUT, null, jaAttribuut));

        assertFalse((Boolean) ReflectionTestUtils.invokeMethod(bepaler, ATTRIBUTEN_GEWIJZIGD_EN_AUTORISATIE_OP_ATTRIBUUT, neeAttribuut, null));
        assertFalse((Boolean) ReflectionTestUtils.invokeMethod(bepaler, ATTRIBUTEN_GEWIJZIGD_EN_AUTORISATIE_OP_ATTRIBUUT, null, neeAttribuut));

        assertTrue((Boolean) ReflectionTestUtils.invokeMethod(bepaler, ATTRIBUTEN_GEWIJZIGD_EN_AUTORISATIE_OP_ATTRIBUUT, jaAttribuut, neeAttribuut));
    }

    /**
     * Maakt een actie.
     *
     * @param administratieveHandeling administratieve handeling
     * @return actie model
     */
    private ActieModel maakActie(final AdministratieveHandelingModel administratieveHandeling) {
        final ActieModel eenActie = new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), administratieveHandeling, null,
                                                   new DatumEvtDeelsOnbekendAttribuut(administratieveHandeling.getTijdstipRegistratie().naarDatum()),
                                                   null, administratieveHandeling.getTijdstipRegistratie(), null);
        eenActie.setMagGeleverdWorden(false);
        ReflectionTestUtils.setField(eenActie, ID, ADMINISTRATIEVE_HANDELING_ID);
        return eenActie;
    }

    /**
     * Maakt een administratieve handeling.
     *
     * @return administratieve handeling model
     */
    private AdministratieveHandelingModel maakAdministratieveHandeling() {
        final DatumTijdAttribuut tijdstipRegistratie = DatumTijdAttribuut.nu();
        final AdministratieveHandelingModel administratieveHandeling =
            VerantwoordingTestUtil.bouwAdministratieveHandeling(SoortAdministratieveHandeling.DUMMY, null, new OntleningstoelichtingAttribuut("Ontleend "
                    + "via de rechter."),
                                                                tijdstipRegistratie);
        ReflectionTestUtils.setField(administratieveHandeling, ID, ADMINISTRATIEVE_HANDELING_ID);
        return administratieveHandeling;
    }

}
