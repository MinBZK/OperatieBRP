/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.migratie;

import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdresregelAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratieAttribuut;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonMigratieGroepBericht;
import org.junit.Assert;
import org.junit.Test;


public class BRBY0543Test {

    private LandGebied onbekend = new LandGebied(LandGebiedCodeAttribuut.ONBEKEND, null, null, null, null);
    private LandGebied afghanistan = new LandGebied(new LandGebiedCodeAttribuut((short) 235), null, null, null, null);

    @Test
    public void testVoerRegelUitBLRegel4GevuldEmigratieLandGebiedNietOnbekend() {
        final PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setMigratie(new PersoonMigratieGroepBericht());

        persoonBericht.getMigratie().setBuitenlandsAdresRegel4Migratie(new AdresregelAttribuut("derde berg links"));

        persoonBericht.getMigratie().setSoortMigratie(new SoortMigratieAttribuut(SoortMigratie.EMIGRATIE));

        persoonBericht.getMigratie().setLandGebiedMigratie(new LandGebiedAttribuut(afghanistan));

        final List<BerichtEntiteit> berichtEntiteits = new BRBY0543().voerRegelUit(null, persoonBericht, null, null);

        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testVoerRegelUitBLRegel6GevuldEmigratieLandGebiedOnbekend() {
        final PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setMigratie(new PersoonMigratieGroepBericht());

        persoonBericht.getMigratie().setBuitenlandsAdresRegel4Migratie(new AdresregelAttribuut("derde berg links"));

        persoonBericht.getMigratie().setSoortMigratie(new SoortMigratieAttribuut(SoortMigratie.EMIGRATIE));

        persoonBericht.getMigratie().setLandGebiedMigratie(new LandGebiedAttribuut(onbekend));

        final List<BerichtEntiteit> berichtEntiteits = new BRBY0543().voerRegelUit(null, persoonBericht, null, null);

        Assert.assertEquals(persoonBericht, berichtEntiteits.get(0));
    }

    @Test
    public void testVoerRegelUitBLRegel2GevuldEmigratieLandGebiedLeeg() {
        final PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setMigratie(new PersoonMigratieGroepBericht());

        persoonBericht.getMigratie().setBuitenlandsAdresRegel4Migratie(new AdresregelAttribuut("derde berg links"));

        persoonBericht.getMigratie().setSoortMigratie(new SoortMigratieAttribuut(SoortMigratie.EMIGRATIE));

        persoonBericht.getMigratie().setLandGebiedMigratie(null);

        final List<BerichtEntiteit> berichtEntiteits = new BRBY0543().voerRegelUit(null, persoonBericht, null, null);

        Assert.assertEquals(persoonBericht, berichtEntiteits.get(0));
    }

    @Test
    public void testVoerRegelUitBLRegel3GevuldImigratieLandGebiedOnbekend() {
        final PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setMigratie(new PersoonMigratieGroepBericht());

        persoonBericht.getMigratie().setBuitenlandsAdresRegel4Migratie(new AdresregelAttribuut("derde berg links"));

        persoonBericht.getMigratie().setSoortMigratie(new SoortMigratieAttribuut(SoortMigratie.IMMIGRATIE));

        persoonBericht.getMigratie().setLandGebiedMigratie(new LandGebiedAttribuut(onbekend));

        final List<BerichtEntiteit> berichtEntiteits = new BRBY0543().voerRegelUit(null, persoonBericht, null, null);

        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testVoerRegelUitGeenBLRegelGevuldEmigratieLandGebiedOnbekend() {
        final PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setMigratie(new PersoonMigratieGroepBericht());

        persoonBericht.getMigratie().setSoortMigratie(new SoortMigratieAttribuut(SoortMigratie.EMIGRATIE));

        persoonBericht.getMigratie().setLandGebiedMigratie(new LandGebiedAttribuut(onbekend));

        final List<BerichtEntiteit> berichtEntiteits = new BRBY0543().voerRegelUit(null, persoonBericht, null, null);

        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0543, new BRBY0543().getRegel());
    }
}
