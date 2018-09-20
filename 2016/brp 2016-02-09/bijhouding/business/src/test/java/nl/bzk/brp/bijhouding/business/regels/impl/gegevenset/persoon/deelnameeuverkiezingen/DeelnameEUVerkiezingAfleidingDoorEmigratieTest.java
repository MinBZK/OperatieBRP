/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.deelnameeuverkiezingen;

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
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonDeelnameEUVerkiezingenModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class DeelnameEUVerkiezingAfleidingDoorEmigratieTest {


    @Test
    public void testLeidAfGeenActueleDeelnameEuVerkiezingen() {
        PersoonHisVolledigImpl persoonHisVolledig = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwMigratieRecord(maakActie()).eindeRecord().build();

        final List<Afleidingsregel> afleidingsregels =
                new DeelnameEUVerkiezingAfleidingDoorEmigratie(persoonHisVolledig, maakActie()).leidAf().getVervolgAfleidingen();

        Assert.assertTrue(afleidingsregels.isEmpty());
    }

    @Test
    public void testLeidAfActueleDeelnameEuVerkiezingenMaarDeelnameIsNee() {
        PersoonHisVolledigImpl persoonHisVolledig = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwMigratieRecord(maakActie()).eindeRecord()
                .nieuwDeelnameEUVerkiezingenRecord(maakActie()).indicatieDeelnameEUVerkiezingen(false).eindeRecord()
                .build();

        final int aantal = persoonHisVolledig.getPersoonDeelnameEUVerkiezingenHistorie().getAantal();

        final List<Afleidingsregel> afleidingsregels =
                new DeelnameEUVerkiezingAfleidingDoorEmigratie(persoonHisVolledig, maakActie()).leidAf().getVervolgAfleidingen();

        Assert.assertTrue(afleidingsregels.isEmpty());
        Assert.assertEquals(aantal, persoonHisVolledig.getPersoonDeelnameEUVerkiezingenHistorie().getAantal());
        Assert.assertNotNull(persoonHisVolledig.getPersoonDeelnameEUVerkiezingenHistorie().getActueleRecord());

    }

    @Test
    public void testLeidAfActueleDeelnameEuVerkiezingen() {
        PersoonHisVolledigImpl persoonHisVolledig = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwMigratieRecord(maakActie()).eindeRecord()
                .nieuwDeelnameEUVerkiezingenRecord(maakActie()).indicatieDeelnameEUVerkiezingen(true).eindeRecord()
                .build();

        ActieModel veroorzakendeActie = maakActie();
        final List<Afleidingsregel> afleidingsregels =
                new DeelnameEUVerkiezingAfleidingDoorEmigratie(persoonHisVolledig, veroorzakendeActie).leidAf().getVervolgAfleidingen();

        Assert.assertTrue(afleidingsregels.isEmpty());
        Assert.assertEquals(1, persoonHisVolledig.getPersoonDeelnameEUVerkiezingenHistorie().getAantal());
        Assert.assertNull(persoonHisVolledig.getPersoonDeelnameEUVerkiezingenHistorie().getActueleRecord());
        Assert.assertTrue(persoonHisVolledig.getPersoonDeelnameEUVerkiezingenHistorie().isVervallen());

        final HisPersoonDeelnameEUVerkiezingenModel next =
                persoonHisVolledig.getPersoonDeelnameEUVerkiezingenHistorie().iterator().next();
        Assert.assertEquals(veroorzakendeActie, next.getVerantwoordingVerval());
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.VR00016a, new DeelnameEUVerkiezingAfleidingDoorEmigratie(null, null).getRegel());
    }

    private ActieModel maakActie() {
        return new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_MIGRATIE),
            new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                SoortAdministratieveHandeling.VERHUIZING_NAAR_BUITENLAND), null,
                null, null), null, new DatumEvtDeelsOnbekendAttribuut(20130701), null, DatumTijdAttribuut.nu(), null);
    }
}
