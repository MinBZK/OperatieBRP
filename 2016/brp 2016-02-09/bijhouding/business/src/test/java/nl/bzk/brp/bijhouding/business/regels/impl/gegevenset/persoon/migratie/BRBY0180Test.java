/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.migratie;

import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonMigratieGroepBericht;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class BRBY0180Test {

    private LandGebied ongeldigLandGebied = new LandGebied(null, null, null, new DatumEvtDeelsOnbekendAttribuut(20120101),
                                                   new DatumEvtDeelsOnbekendAttribuut(20130101));
    private LandGebied geldigLandGebied = new LandGebied(null, null, null, new DatumEvtDeelsOnbekendAttribuut(20120101), null);

    @Test
    public void testGeldigeLandGebied() {
        ActieModel actie = maakActie(20140101);
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setMigratie(new PersoonMigratieGroepBericht());
        persoonBericht.getMigratie().setLandGebiedMigratie(new LandGebiedAttribuut(geldigLandGebied));

        final List<BerichtEntiteit> fouteEntiteiten = new BRBY0180().voerRegelUit(null, persoonBericht, actie, null);
        Assert.assertTrue(fouteEntiteiten.isEmpty());
    }

    @Test
    public void testOnGeldigeLandGebied() {
        ActieModel actie = maakActie(20140101);
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setMigratie(new PersoonMigratieGroepBericht());
        persoonBericht.getMigratie().setLandGebiedMigratie(new LandGebiedAttribuut(ongeldigLandGebied));

        final List<BerichtEntiteit> fouteEntiteiten = new BRBY0180().voerRegelUit(null, persoonBericht, actie, null);
        Assert.assertEquals(1, fouteEntiteiten.size());
        Assert.assertEquals(fouteEntiteiten.get(0), persoonBericht);
    }

    @Test
    public void testGeldigeLandGebiedPeildatumValtInDePeriodeDatHetLandGeldigWas() {
        ActieModel actie = maakActie(20120601);
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setMigratie(new PersoonMigratieGroepBericht());
        persoonBericht.getMigratie().setLandGebiedMigratie(new LandGebiedAttribuut(ongeldigLandGebied));

        final List<BerichtEntiteit> fouteEntiteiten = new BRBY0180().voerRegelUit(null, persoonBericht, actie, null);
        Assert.assertTrue(fouteEntiteiten.isEmpty());
    }

    @Test
    public void testZonderMigratieGroep() {
        ActieModel actie = maakActie(20140101);
        PersoonBericht persoonBericht = new PersoonBericht();

        final List<BerichtEntiteit> fouteEntiteiten = new BRBY0180().voerRegelUit(null, persoonBericht, actie, null);
        Assert.assertTrue(fouteEntiteiten.isEmpty());
    }

    @Test
    public void testMetMigratieGroepZonderLandGebied() {
        ActieModel actie = maakActie(20140101);
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setMigratie(new PersoonMigratieGroepBericht());

        final List<BerichtEntiteit> fouteEntiteiten = new BRBY0180().voerRegelUit(null, persoonBericht, actie, null);
        Assert.assertTrue(fouteEntiteiten.isEmpty());
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0180, new BRBY0180().getRegel());
    }

    private ActieModel maakActie(final int datumAanvang) {
        final ActieModel actieModel = new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_MIGRATIE),
                                                     new AdministratieveHandelingModel(
                                                             new SoortAdministratieveHandelingAttribuut(
                                                                     SoortAdministratieveHandeling.VERHUIZING_NAAR_BUITENLAND),
                                                             null,
                                                             null, null), null, new DatumEvtDeelsOnbekendAttribuut(20130701), null,
                                                     DatumTijdAttribuut.nu(), null);
        ReflectionTestUtils.setField(actieModel, "datumAanvangGeldigheid", new DatumEvtDeelsOnbekendAttribuut(datumAanvang));
        return actieModel;
    }
}
