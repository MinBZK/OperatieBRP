/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.afgeleidadministratief;

import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.Afleidingsregel;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAfgeleidAdministratiefModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test VR00027a
 */
public class AfgeleidAdministratiefGroepVerwerkerTest {

    private AdministratieveHandelingModel administratieveHandeling;

    @Before
    public void init() {
        administratieveHandeling = new AdministratieveHandelingModel(null, null, null, null);
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.VR00027a, new AfgeleidAdministratiefGroepVerwerker(null, null, true).getRegel());
    }

    @Test
    public void testVerwerkingPersoonIsGeenHoofdPersoon() {
        final DatumTijdAttribuut tsReg = DatumTijdAttribuut.nu();
        final ActieModel actieModel = maakActie(tsReg);
        final PersoonHisVolledig persoonHisVolledig = maakPersoon();
        final List<Afleidingsregel> afleidingsregels =
                new AfgeleidAdministratiefGroepVerwerker(persoonHisVolledig, actieModel, false).leidAf().getVervolgAfleidingen();

        Assert.assertTrue(afleidingsregels.isEmpty());

        Assert.assertEquals(2, persoonHisVolledig.getPersoonAfgeleidAdministratiefHistorie().getAantal());

        final HisPersoonAfgeleidAdministratiefModel actueleRecord =
                persoonHisVolledig.getPersoonAfgeleidAdministratiefHistorie().getActueleRecord();

        Assert.assertEquals(2, actueleRecord.getSorteervolgorde().getWaarde().intValue());
        Assert.assertEquals(administratieveHandeling, actueleRecord.getAdministratieveHandeling());
        Assert.assertEquals(tsReg, actueleRecord.getTijdstipLaatsteWijziging());
        Assert.assertEquals(actieModel, actueleRecord.getVerantwoordingInhoud());
    }

    @Test
    public void testVerwerkingPersoonIsHoofdPersoon() {
        final DatumTijdAttribuut tsReg = DatumTijdAttribuut.nu();
        final ActieModel actieModel = maakActie(tsReg);
        final PersoonHisVolledig persoonHisVolledig = maakPersoon();
        final List<Afleidingsregel> afleidingsregels =
                new AfgeleidAdministratiefGroepVerwerker(persoonHisVolledig, actieModel, true).leidAf().getVervolgAfleidingen();

        Assert.assertTrue(afleidingsregels.isEmpty());

        Assert.assertEquals(2, persoonHisVolledig.getPersoonAfgeleidAdministratiefHistorie().getAantal());

        final HisPersoonAfgeleidAdministratiefModel actueleRecord =
                persoonHisVolledig.getPersoonAfgeleidAdministratiefHistorie().getActueleRecord();

        Assert.assertEquals(1, actueleRecord.getSorteervolgorde().getWaarde().intValue());
        Assert.assertEquals(administratieveHandeling, actueleRecord.getAdministratieveHandeling());
        Assert.assertEquals(tsReg, actueleRecord.getTijdstipLaatsteWijziging());
        Assert.assertEquals(actieModel, actueleRecord.getVerantwoordingInhoud());
    }

    public PersoonHisVolledig maakPersoon() {
        return new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwAfgeleidAdministratiefRecord(20120101)
                .eindeRecord().build();
    }

    public ActieModel maakActie(final DatumTijdAttribuut tsReg) {
        return new ActieModel(null, administratieveHandeling, null,
                              new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()), null, tsReg, null);
    }
}
