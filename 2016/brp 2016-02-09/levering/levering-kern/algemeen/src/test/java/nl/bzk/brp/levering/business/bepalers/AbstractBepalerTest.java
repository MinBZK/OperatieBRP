/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bepalers;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAfgeleidAdministratiefModel;

import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonAdresHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonGeslachtsnaamcomponentHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Before;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;


public abstract class AbstractBepalerTest {

    protected static final DatumTijdAttribuut TSREG_1990 = DatumTijdAttribuut.bouwDatumTijd(1990, 8, 23, 0, 0, 0);
    protected static final DatumTijdAttribuut TSREG_2010 = DatumTijdAttribuut.bouwDatumTijd(2010, 6, 1, 0, 0, 0);
    private static final String ID = "iD";

    private final AdministratieveHandelingBericht admhndGeboorte;
    private final AdministratieveHandelingBericht admhndVerhuizing;
    private final PersoonHisVolledigImpl          testPersoon;

    private final ActieModel actieGeboorte;
    private final ActieModel actieVerhuizing;

    public AbstractBepalerTest() {
        admhndGeboorte   = Mockito.mock(AdministratieveHandelingBericht.class);
        admhndVerhuizing = Mockito.mock(AdministratieveHandelingBericht.class);

        actieGeboorte =
            new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_GEBOORTE),
                new AdministratieveHandelingModel(admhndGeboorte), StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA,
                new DatumEvtDeelsOnbekendAttribuut(new DatumAttribuut(TSREG_1990.getWaarde())), null, TSREG_1990, null);
        ReflectionTestUtils.setField(actieGeboorte, ID, 1L);
        actieVerhuizing =
            new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_ADRES),
                new AdministratieveHandelingModel(admhndVerhuizing), StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA,
                new DatumEvtDeelsOnbekendAttribuut(new DatumAttribuut(TSREG_2010.getWaarde())), null, TSREG_2010, null);
        ReflectionTestUtils.setField(actieVerhuizing, ID, 2L);

        testPersoon      = maakTestPersoonHisVolledig();
    }

    @Before
    public void setup() {
        Mockito.when(admhndGeboorte.getTijdstipRegistratie()).thenReturn(TSREG_1990);
        Mockito.when(admhndVerhuizing.getTijdstipRegistratie()).thenReturn(TSREG_2010);
    }

    /**
     * Maakt een test testPersoon his volledig.
     *
     * @return testPersoon his volledig impl
     */
    private PersoonHisVolledigImpl maakTestPersoonHisVolledig() {
        final PersoonHisVolledigImpl persoon = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
            .nieuwGeboorteRecord(actieGeboorte).gemeenteGeboorte(
                new GemeenteCodeAttribuut((short) 1230)).eindeRecord()
            .nieuwGeslachtsaanduidingRecord(actieGeboorte).geslachtsaanduiding(Geslachtsaanduiding.MAN)
            .eindeRecord()
            .voegPersoonGeslachtsnaamcomponentToe(
                new PersoonGeslachtsnaamcomponentHisVolledigImplBuilder(new VolgnummerAttribuut(1))
                    .nieuwStandaardRecord(actieGeboorte)
                    .stam("Jansen")
                    .eindeRecord().build())
            .voegPersoonAdresToe(
                new PersoonAdresHisVolledigImplBuilder().nieuwStandaardRecord(actieGeboorte)
                    .gemeente((short) 1230)
                    .woonplaatsnaam("Almere")
                    .naamOpenbareRuimte("Langestraat")
                    .huisnummer(23)
                    .eindeRecord().build())
            .nieuwBijhoudingRecord(actieGeboorte).bijhoudingspartij(1230).eindeRecord()
            .nieuwAfgeleidAdministratiefRecord(actieGeboorte)
                .administratieveHandeling(admhndGeboorte)
                .tijdstipLaatsteWijziging(TSREG_1990)
                .eindeRecord(100)

            .voegPersoonAdresToe(
                new PersoonAdresHisVolledigImplBuilder().nieuwStandaardRecord(actieVerhuizing)
                    .gemeente((short) 3310)
                    .woonplaatsnaam("Rotterdam")
                    .naamOpenbareRuimte("Kortestraat")
                    .huisnummer(31)
                    .eindeRecord().build())
            .nieuwBijhoudingRecord(actieVerhuizing).bijhoudingspartij(3310).eindeRecord()
            .nieuwAfgeleidAdministratiefRecord(actieVerhuizing).administratieveHandeling(admhndVerhuizing)
                .tijdstipLaatsteWijziging(TSREG_2010)
                .eindeRecord(200)
            .build();

        for (HisPersoonAfgeleidAdministratiefModel afgeleidAdministratiefModel : persoon.getPersoonAfgeleidAdministratiefHistorie()) {
            ReflectionTestUtils.setField(afgeleidAdministratiefModel.getAdministratieveHandeling(), ID, (long) afgeleidAdministratiefModel.getID() / 100);
            afgeleidAdministratiefModel.getAdministratieveHandeling().getActies().add(afgeleidAdministratiefModel.getVerantwoordingInhoud());
        }

        return persoon;
    }

    public final AdministratieveHandelingModel getAdmhndGeboorte() {
        final AdministratieveHandelingModel admhnd = new AdministratieveHandelingModel(admhndGeboorte);
        admhnd.getActies().add(actieGeboorte);

        ReflectionTestUtils.setField(admhnd, ID, 1L);
        return admhnd;
    }

    public final AdministratieveHandelingModel getAdmhndVerhuizing() {
        final AdministratieveHandelingModel admhnd = new AdministratieveHandelingModel(admhndVerhuizing);
        admhnd.getActies().add(actieVerhuizing);

        ReflectionTestUtils.setField(admhnd, ID, 2L);
        return admhnd;
    }

    public final PersoonHisVolledigImpl getTestPersoon() {
        return testPersoon;
    }
}
