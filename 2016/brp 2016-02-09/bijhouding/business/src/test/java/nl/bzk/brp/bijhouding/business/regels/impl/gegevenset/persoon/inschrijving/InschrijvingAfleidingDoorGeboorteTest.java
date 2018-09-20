/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.inschrijving;

import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.Afleidingsregel;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonAdresHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InschrijvingAfleidingDoorGeboorteTest {

    public static final DatumEvtDeelsOnbekendAttribuut DATUM_AANVANG_GELDIGHEID = new DatumEvtDeelsOnbekendAttribuut(20120101);

    private InschrijvingAfleidingDoorGeboorte inschrijvingAfleidingDoorGeboorte;
    private PersoonHisVolledigImpl kind;

    @Before
    public void init() {
        ActieModel actie = creeerActie();
        kind = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        final PersoonAdresHisVolledigImpl adresKind =
                new PersoonAdresHisVolledigImplBuilder(kind).nieuwStandaardRecord(20120101, null, 20120101)
                                                            .eindeRecord()
                                                            .build();
        kind.getAdressen().add(adresKind);
        inschrijvingAfleidingDoorGeboorte = new InschrijvingAfleidingDoorGeboorte(kind, actie);
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.VR00024a, inschrijvingAfleidingDoorGeboorte.getRegel());
    }

    @Test
    public void testLeidAf() {
        Assert.assertEquals(0, kind.getPersoonInschrijvingHistorie().getAantal());
        Assert.assertNull(kind.getPersoonInschrijvingHistorie().getActueleRecord());

        List<Afleidingsregel> resultaat = inschrijvingAfleidingDoorGeboorte.leidAf().getVervolgAfleidingen();

        Assert.assertTrue(resultaat.isEmpty());
        Assert.assertEquals(1, kind.getPersoonInschrijvingHistorie().getAantal());
        Assert.assertNotNull(kind.getPersoonInschrijvingHistorie().getActueleRecord());
        Assert.assertEquals(DATUM_AANVANG_GELDIGHEID,
                            kind.getPersoonInschrijvingHistorie().getActueleRecord().getDatumInschrijving());
    }

    @Test
    public void testLeidAfZonderAdres() {
        kind.getAdressen().clear();

        // Controleer de begin situatie (geen inschrijving historie, geen actuele inschrijving en geen adressen)
        Assert.assertEquals(0, kind.getPersoonInschrijvingHistorie().getAantal());
        Assert.assertNull(kind.getPersoonInschrijvingHistorie().getActueleRecord());
        Assert.assertTrue(kind.getAdressen().isEmpty());

        // Leid af
        inschrijvingAfleidingDoorGeboorte.leidAf();

        // Controleer dat er geen wijzigingen zijn op de begin situatie
        Assert.assertEquals(0, kind.getPersoonInschrijvingHistorie().getAantal());
        Assert.assertNull(kind.getPersoonInschrijvingHistorie().getActueleRecord());
        Assert.assertTrue(kind.getAdressen().isEmpty());
    }

    @Test
    public void testLeidAfZonderActueelAdres() {
        kind.getAdressen().clear();
        final PersoonAdresHisVolledigImpl adresKind =
            new PersoonAdresHisVolledigImplBuilder(kind).nieuwStandaardRecord(20120101, 20130101, 20120101)
                .eindeRecord().build();
        kind.getAdressen().add(adresKind);

        // Controleer de begin situatie (geen inschrijving historie, geen actuele inschrijving maar wel een adres)
        Assert.assertEquals(0, kind.getPersoonInschrijvingHistorie().getAantal());
        Assert.assertNull(kind.getPersoonInschrijvingHistorie().getActueleRecord());
        Assert.assertFalse(kind.getAdressen().isEmpty());

        // Leid af
        inschrijvingAfleidingDoorGeboorte.leidAf();

        // Controleer dat er geen wijzigingen zijn op de begin situatie
        Assert.assertEquals(0, kind.getPersoonInschrijvingHistorie().getAantal());
        Assert.assertNull(kind.getPersoonInschrijvingHistorie().getActueleRecord());
    }

    /**
     * Creeer een standaard actie.
     *
     * @return het actie model
     */
    private ActieModel creeerActie() {
        return new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_GEBOORTE),
            new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND), null, null, null),
            null, DATUM_AANVANG_GELDIGHEID, null, DatumTijdAttribuut.nu(), null);
    }
}
