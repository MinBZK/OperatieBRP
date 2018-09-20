/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.migratie;

import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonMigratieGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class BRBY0540Test {

    private final LandGebied afghanistan = new LandGebied(new LandGebiedCodeAttribuut((short) 532), null, null, null, null);
    private final LandGebied nederland = new LandGebied(LandGebiedCodeAttribuut.NEDERLAND, null, null, null, null);

    private static final boolean HEEFT_BVP = true;
    private static final boolean HEEFT_GEEN_BVP = false;
    private static final boolean HEEFT_ACTIEVE_BVP = false;
    private static final boolean HEEFT_VERVALLEN_BVP = true;

    @Test
    public void testVoerRegelUitLandMigratieNietOpgegeven() {
        PersoonHisVolledigImpl persoon = maakPersoon(HEEFT_GEEN_BVP);
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setMigratie(new PersoonMigratieGroepBericht());

        final List<BerichtEntiteit> berichtEntiteits =
                new BRBY0540().voerRegelUit(new PersoonView(persoon), persoonBericht, null, null);

        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testVoerRegelUitLandMigratieIsNietNederland() {
        PersoonHisVolledigImpl persoon = maakPersoon(HEEFT_GEEN_BVP);
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setMigratie(new PersoonMigratieGroepBericht());
        persoonBericht.getMigratie().setLandGebiedMigratie(new LandGebiedAttribuut(afghanistan));

        final List<BerichtEntiteit> berichtEntiteits =
                new BRBY0540().voerRegelUit(new PersoonView(persoon), persoonBericht, null, null);

        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testVoerRegelUitLandMigratieIsNederland() {
        PersoonHisVolledigImpl persoon = maakPersoon(HEEFT_GEEN_BVP);
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setMigratie(new PersoonMigratieGroepBericht());
        persoonBericht.getMigratie().setLandGebiedMigratie(new LandGebiedAttribuut(nederland));

        final List<BerichtEntiteit> berichtEntiteits =
                new BRBY0540().voerRegelUit(new PersoonView(persoon), persoonBericht, null, null);

        Assert.assertEquals(persoonBericht, berichtEntiteits.get(0));
    }

    @Test
    public void testVoerRegelUitLandMigratieIsNederlandMaarPersoonHeeftBVP() {
        PersoonHisVolledigImpl persoon = maakPersoon(HEEFT_BVP);
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setMigratie(new PersoonMigratieGroepBericht());
        persoonBericht.getMigratie().setLandGebiedMigratie(new LandGebiedAttribuut(nederland));

        final List<BerichtEntiteit> berichtEntiteits =
                new BRBY0540().voerRegelUit(new PersoonView(persoon), persoonBericht, null, null);

        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testVoerRegelUitLandMigratieIsNietNederlandEnPersoonHeeftBVP() {
        PersoonHisVolledigImpl persoon = maakPersoon(HEEFT_BVP);
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setMigratie(new PersoonMigratieGroepBericht());
        persoonBericht.getMigratie().setLandGebiedMigratie(new LandGebiedAttribuut(afghanistan));

        final List<BerichtEntiteit> berichtEntiteits =
                new BRBY0540().voerRegelUit(new PersoonView(persoon), persoonBericht, null, null);

        Assert.assertTrue(berichtEntiteits.isEmpty());

    }

    @Test
    public void testVoerRegelUitLandMigratieIsNederlandEnPersoonHeeftVervallenBVP() {
        PersoonHisVolledigImpl persoon = maakPersoon(HEEFT_BVP, HEEFT_VERVALLEN_BVP);
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setMigratie(new PersoonMigratieGroepBericht());
        persoonBericht.getMigratie().setLandGebiedMigratie(new LandGebiedAttribuut(nederland));

        final List<BerichtEntiteit> berichtEntiteits =
                new BRBY0540().voerRegelUit(new PersoonView(persoon), persoonBericht, null, null);

        Assert.assertEquals(persoonBericht, berichtEntiteits.get(0));
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0540, new BRBY0540().getRegel());
    }

    private PersoonHisVolledigImpl maakPersoon(final boolean metBVP) {
        return maakPersoon(metBVP, HEEFT_ACTIEVE_BVP);
    }

    private PersoonHisVolledigImpl maakPersoon(final boolean metBVP, final boolean isBvpVervallen) {
        final PersoonHisVolledigImpl persoon = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        if (metBVP) {
            final PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigImpl indicatie =
                    new PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigImplBuilder(persoon)
                            .nieuwStandaardRecord(20120101).eindeRecord().build();
            if (isBvpVervallen) {
                indicatie.getPersoonIndicatieHistorie().verval(null, DatumTijdAttribuut.datumTijd(2010, 1, 1));
            }
            persoon.setIndicatieBijzondereVerblijfsrechtelijkePositie(indicatie);
        }
        return persoon;
    }
}
